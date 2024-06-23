package com.hotel.api.service.impl;

import com.hotel.api.constant.UserBaseConstant;
import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.ErrorCode;
import com.hotel.api.dto.kindOfRoom.KindOfRoomDto;
import com.hotel.api.form.kindOfRoom.ChangeStatusKindForm;
import com.hotel.api.form.kindOfRoom.CreateKindRoomForm;
import com.hotel.api.form.kindOfRoom.UpdateKindRoomForm;
import com.hotel.api.mapper.EmptyRoomMapper;
import com.hotel.api.mapper.ImageMapper;
import com.hotel.api.mapper.KindOfRoomMapper;
import com.hotel.api.model.*;
import com.hotel.api.model.criteria.KindOfRoomCriteria;
import com.hotel.api.repository.*;
import com.hotel.api.service.KindOfRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class KindOfRoomImpl implements KindOfRoomService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private KindOfRoomMapper kindOfRoomMapper;
    @Autowired
    private KindOfRoomRepository kindOfRoomRepository;
    @Autowired
    private ImagesRepository imagesRepository;
    @Autowired
    private EmptyRoomRepository emptyRoomRepository;
    @Autowired
    private EmptyRoomMapper emptyRoomMapper;
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public ApiMessageDto<String> createKindOfRoom(CreateKindRoomForm createKindRoomForm,Long accountId) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        User user = userRepository.findByAccountId(accountId).orElse(null);
        Hotel hotel = hotelRepository.findByUserId(user.getId());
        if (hotel==null)
        {
            apiMessageDto.setMessage("Not found hotel");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.HOTEL_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        KindOfRoom kind = kindOfRoomMapper.fromKinRoomDtoToEntity(createKindRoomForm);
        kind.setHotel(hotel);
        kind.setStatus(UserBaseConstant.STATUS_LOCK);
        kindOfRoomRepository.save(kind);
        List<Images> imagesList = new ArrayList<>();
        for (String link:createKindRoomForm.getImages())
        {
            Images images = new Images();
            images.setLink(link);
            images.setKindRoom(kind);
            imagesList.add(images);
        }
        imagesRepository.saveAll(imagesList);
        apiMessageDto.setMessage("create kind of room success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<List<KindOfRoomDto>> getListKindByHotel(long accountId) {
        ApiMessageDto<List<KindOfRoomDto>> apiMessageDto = new ApiMessageDto<>();

        User user = userRepository.findByAccountId(accountId).orElse(null);
        if(user==null)
        {
            apiMessageDto.setMessage("Not found user");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        Hotel hotel = hotelRepository.findByUserId(user.getId());
        if (hotel==null)
        {
            apiMessageDto.setMessage("Not found hotel");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.HOTEL_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        List<KindOfRoom> list = kindOfRoomRepository.findByHotelId(hotel.getId());
        List<KindOfRoomDto> kindOfRoomList = kindOfRoomMapper.fromEntityToListDto(list);

        for (KindOfRoomDto kt : kindOfRoomList)
        {
            List<EmptyRoom> roomList = emptyRoomRepository.findAllByKindOfRoomId(kt.getId());
            kt.setRoomDtoList(emptyRoomMapper.fromEntityToListDto(roomList));

            List<Images> imagesList = imagesRepository.findAllByKindRoomId(kt.getId());
            kt.setImagesList(imageMapper.fromEntityToListDtoForHotel(imagesList));
        }
        apiMessageDto.setData(kindOfRoomList);
        apiMessageDto.setMessage("get kind of room success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> updateKindRoom(UpdateKindRoomForm updateKindRoomForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        KindOfRoom kind = kindOfRoomRepository.findById(updateKindRoomForm.getId()).orElse(null);
        if (kind==null)
        {
            apiMessageDto.setMessage("Not found kind of room");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.KIND_OF_ROOM_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        kindOfRoomMapper.UpdateHotelDtoToEntity(updateKindRoomForm,kind);
        kindOfRoomRepository.save(kind);
        apiMessageDto.setMessage("update success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<List<KindOfRoomDto>> getListPublic(long id) {
        ApiMessageDto<List<KindOfRoomDto>> apiMessageDto = new ApiMessageDto<>();
        List<KindOfRoomDto> list;
        List<KindOfRoom> roomList = kindOfRoomRepository.findAllByStatusAndHotelId(UserBaseConstant.STATUS_ACTIVE,id);
        list = kindOfRoomMapper.fromEntityToListDto(roomList);
        for (KindOfRoomDto kt : list)
        {
            List<EmptyRoom> emptyRoomList = emptyRoomRepository.findAllByKindOfRoomIdAndStatusAndEmptyRoomGreaterThan(kt.getId(),UserBaseConstant.STATUS_ACTIVE,0);
            kt.setRoomDtoList(emptyRoomMapper.fromEntityToListDto(emptyRoomList));
        }
        apiMessageDto.setMessage("get success");
        apiMessageDto.setData(list);

        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<KindOfRoomDto> getKindOfRoom(long id) {
        ApiMessageDto<KindOfRoomDto> apiMessageDto = new ApiMessageDto<>();
        KindOfRoom kind= kindOfRoomRepository.findById(id).orElse(null);
        if (kind ==null)
        {
            apiMessageDto.setMessage("Not found kind of room");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.KIND_OF_ROOM_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        KindOfRoomDto kindOfRoomDto= kindOfRoomMapper.fromEntityToDto(kind);
        apiMessageDto.setMessage("get kind of room success");
        apiMessageDto.setData(kindOfRoomDto);
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> changeStatus(ChangeStatusKindForm changeStatusKindForm) {
       ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
       KindOfRoom kind = kindOfRoomRepository.findById(changeStatusKindForm.getId()).orElse(null);
       if (kind==null)
       {
           apiMessageDto.setMessage("Not found kind of room");
           apiMessageDto.setResult(false);
           apiMessageDto.setCode(ErrorCode.KIND_OF_ROOM_ERROR_NOT_FOUND);
           return apiMessageDto;
       }
       kind.setStatus(changeStatusKindForm.getStatus());
       kindOfRoomRepository.save(kind);
       apiMessageDto.setMessage("change status success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<List<KindOfRoomDto>> getKindOfRangeTime(Long hotelId ,Date startDate, Date endDate) {

        ApiMessageDto<List<KindOfRoomDto>> apiMessageDto= new ApiMessageDto<>();

        // Chuyển đổi Date sang LocalDate
        LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Long distance = (long) (endLocalDate.getDayOfMonth()- startLocalDate.getDayOfMonth());
        List<KindOfRoom> kindOfRoomList = kindOfRoomRepository.findKindsWithEmptyRooms(hotelId,startDate,endDate,distance,UserBaseConstant.STATUS_ACTIVE);
        List<KindOfRoomDto> kindOfRoomDtos = kindOfRoomMapper.fromEntityToListDto(kindOfRoomList);
//        System.out.println("so luong loại: "+kindOfRoomList.size()+" distance "+distance);
        for (KindOfRoomDto kd : kindOfRoomDtos)
        {
            List<EmptyRoom> list = emptyRoomRepository.findEmptyRoomsBetweenDates(kd.getId(),startDate,endDate,UserBaseConstant.STATUS_ACTIVE);
            if (list.size() ==distance)
            {
                kd.setRoomDtoList(emptyRoomMapper.fromEntityToListDto(list));
            }

        }
        if(kindOfRoomDtos.size() ==0)
        {
            apiMessageDto.setMessage("There are currently no rooms available");
            return apiMessageDto;
        }
        apiMessageDto.setData(kindOfRoomDtos);
        apiMessageDto.setMessage("get success");
        return apiMessageDto;
    }

}
