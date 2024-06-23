package com.hotel.api.service.impl;

import com.hotel.api.constant.UserBaseConstant;
import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.ErrorCode;
import com.hotel.api.dto.emptyRoom.EmptyRoomDto;
import com.hotel.api.form.booking.CreateBookingForm;
import com.hotel.api.form.emptyRoom.CreateListEmptyRoomForm;
import com.hotel.api.form.emptyRoom.CreateOneEmptyRoomForm;
import com.hotel.api.form.emptyRoom.UpdateOneEmptyRoomForm;
import com.hotel.api.mapper.EmptyRoomMapper;
import com.hotel.api.model.EmptyRoom;
import com.hotel.api.model.KindOfRoom;
import com.hotel.api.model.criteria.EmptyRoomCriteria;
import com.hotel.api.repository.EmptyRoomRepository;
import com.hotel.api.repository.KindOfRoomRepository;
import com.hotel.api.service.EmptyRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class EmptyRoomImpl implements EmptyRoomService {

    @Autowired
    private  KindOfRoomRepository kindOfRoomRepository;
    @Autowired
    private EmptyRoomMapper emptyRoomMapper;
    @Autowired
    private EmptyRoomRepository emptyRoomRepository;

    @Override
    public ApiMessageDto<String> createEmptyRoom(CreateListEmptyRoomForm createListEmptyRoomForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        KindOfRoom kind = kindOfRoomRepository.findById(createListEmptyRoomForm.getKindOfRoomId()).orElse(null);
        if(kind ==null)
        {
            apiMessageDto.setMessage("Not found kind of room");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.KIND_OF_ROOM_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(createListEmptyRoomForm.getCreateOneEmptyRoomForm().getStartDate());
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        EmptyRoom emptyRoom = emptyRoomRepository.findByStartDate(day,kind.getId());
//        if (emptyRoom !=null)
//        {
//            apiMessageDto.setMessage("room already exists ");
//            apiMessageDto.setResult(false);
//            return apiMessageDto;
//        }
        List<EmptyRoom> roomList = new ArrayList<>();

        Date startDate = createListEmptyRoomForm.getCreateOneEmptyRoomForm().getStartDate();;
        Date endDate = createListEmptyRoomForm.getCreateOneEmptyRoomForm().getEndDate();

        // chuyển Date sang LocalDate
        LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate currentDate = start;
        while (!currentDate.isAfter(end)) {
            // Start time for the day: 08:00
            LocalDateTime intervalStart = currentDate.atTime(14, 0);
            // End time for the next day: 11:00
            LocalDateTime intervalEnd = currentDate.plusDays(1).atTime(12, 0);

            // chuyen LocalDateTime quay lai Date
            Date startDateTime = Date.from(intervalStart.atZone(ZoneId.systemDefault()).toInstant());
            Date endDateTime = Date.from(intervalEnd.atZone(ZoneId.systemDefault()).toInstant());

            int day = intervalStart.toLocalDate().getDayOfMonth();


            EmptyRoom emptyRoomExisted = emptyRoomRepository.findByStartDate(day,kind.getId());
            if (emptyRoomExisted !=null)
            {
                apiMessageDto.setMessage("room existed during the day : "+ day);
                apiMessageDto.setResult(false);
                return apiMessageDto;
            }
            if (kind.getRoomNumber()<createListEmptyRoomForm.getCreateOneEmptyRoomForm().getEmptyRoom())
            {
                apiMessageDto.setMessage("The number of available rooms has exceeded the number of available rooms in the day : "+ day);
                apiMessageDto.setResult(false);
                return apiMessageDto;
            }
            roomList.add(createEmptyRoom(createListEmptyRoomForm, startDateTime, endDateTime,kind));

            // chuyen den ngay tiep theo
            currentDate = currentDate.plusDays(1);
        }
        emptyRoomRepository.saveAll(roomList);
        apiMessageDto.setMessage("add success");
        return apiMessageDto;
    }
    private EmptyRoom createEmptyRoom(CreateListEmptyRoomForm createListEmptyRoomForm, Date startDate, Date endDate,KindOfRoom kind) {
        EmptyRoom room = new EmptyRoom();
        room.setStatus(createListEmptyRoomForm.getCreateOneEmptyRoomForm().getStatus());
        room.setKindOfRoom(kind);
        room.setStartDate(startDate);
        room.setEndDate(endDate);
        room.setPrice(createListEmptyRoomForm.getCreateOneEmptyRoomForm().getPrice());
        room.setEmptyRoom(createListEmptyRoomForm.getCreateOneEmptyRoomForm().getEmptyRoom());
        return room;
    }
    @Override
    public ApiMessageDto<String> updateEmptyRoom(UpdateOneEmptyRoomForm updateOneEmptyRoomForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        List<EmptyRoom> roomList = new ArrayList<>();
        for (Long idEmptyRoom : updateOneEmptyRoomForm.getEmptyRoomId())
        {
            EmptyRoom emptyRoom = emptyRoomRepository.findById(idEmptyRoom).orElse(null);
            if (emptyRoom==null)
            {
                apiMessageDto.setResult(false);
                apiMessageDto.setMessage("Not found emptyRoom");
                return apiMessageDto;
            }
            if (emptyRoom.getKindOfRoom().getRoomNumber() < updateOneEmptyRoomForm.getEmptyRoomNumber())
            {
                apiMessageDto.setMessage("The number of available rooms has exceeded the number of available rooms in the day " + emptyRoom.getStartDate());
                apiMessageDto.setResult(false);
                return apiMessageDto;
            }
            emptyRoomMapper.UpdatEmptyRoom(updateOneEmptyRoomForm,emptyRoom);
            roomList.add(emptyRoom);
        }
        emptyRoomRepository.saveAll(roomList);
        apiMessageDto.setMessage("update success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<List<EmptyRoomDto>> getListEmptyRoom(EmptyRoomCriteria emptyRoomCriteria) {
        ApiMessageDto<List<EmptyRoomDto>> apiMessageDto= new ApiMessageDto<>();
        List<EmptyRoom> emptyRoomList = emptyRoomRepository.findAll(emptyRoomCriteria.getSpecification());
        List<EmptyRoomDto> emptyRoomDtos = emptyRoomMapper.fromEntityToListDto(emptyRoomList);
        apiMessageDto.setData(emptyRoomDtos);
        apiMessageDto.setMessage("get success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<EmptyRoomDto> getEmptyRoom(Long emptyRoomId) {
        ApiMessageDto<EmptyRoomDto> apiMessageDto = new ApiMessageDto<>();
        EmptyRoom emptyRoom = emptyRoomRepository.findById(emptyRoomId).orElse(null);
        if(emptyRoom==null)
        {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Not found emptyRoom");
            return apiMessageDto;
        }
        EmptyRoomDto emptyRoomDto = emptyRoomMapper.fromEntityToDto(emptyRoom);
        apiMessageDto.setData(emptyRoomDto);
        apiMessageDto.setMessage("get emptyRoom success");
        return apiMessageDto;
    }
}
