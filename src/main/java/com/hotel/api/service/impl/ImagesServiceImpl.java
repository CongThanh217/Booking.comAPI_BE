package com.hotel.api.service.impl;

import com.hotel.api.constant.UserBaseConstant;
import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.ErrorCode;
import com.hotel.api.dto.ResponseListDto;
import com.hotel.api.dto.images.ImagesDto;
import com.hotel.api.dto.images.ImagesForHotelDto;
import com.hotel.api.form.images.CreateImagesForm;
import com.hotel.api.form.images.UpdateImagesForm;
import com.hotel.api.form.images.UpdateListImageForm;
import com.hotel.api.mapper.ImageMapper;
import com.hotel.api.model.Hotel;
import com.hotel.api.model.Images;
import com.hotel.api.model.KindOfRoom;
import com.hotel.api.repository.HotelRepository;
import com.hotel.api.repository.ImagesRepository;
import com.hotel.api.repository.KindOfRoomRepository;
import com.hotel.api.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImagesServiceImpl implements ImagesService {

    @Autowired
    private  KindOfRoomRepository kindOfRoomRepository;
    @Autowired
    private ImagesRepository imagesRepository;
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public ApiMessageDto<String> createImage(CreateImagesForm createImagesForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();

        List<String> list = createImagesForm.getFilePath();
        List<Images> imagesList = new ArrayList<>();
        KindOfRoom kind = kindOfRoomRepository.findById(createImagesForm.getKindOfRoomId()).orElse(null);
        if (kind==null)
        {
            apiMessageDto.setMessage("Not found kind ");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.KIND_OF_ROOM_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        for(String l : list)
        {
            Images images = new Images();
            images.setLink(l);
            images.setKindRoom(kind);
            imagesList.add(images);
        }

        imagesRepository.saveAll(imagesList);
        apiMessageDto.setMessage("add images success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> updateImage(UpdateListImageForm updateImagesForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        List<UpdateImagesForm> list = updateImagesForm.getList();
        List<Images> imagesList = new ArrayList<>();
        for (UpdateImagesForm item: list)
        {
            Images images = imagesRepository.findById(item.getId()).orElse(null);
            if (images==null)
            {
                apiMessageDto.setMessage("Not found image ");
                apiMessageDto.setResult(false);
                apiMessageDto.setCode(ErrorCode.IMAGE_ERROR_NOT_FOUND);
                return apiMessageDto;
            }
            images.setLink(item.getFile());
            imagesList.add(images);
        }
        imagesRepository.saveAll(imagesList);
        apiMessageDto.setMessage("update image success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<List<ImagesDto>> getListImages(Long id) {
        ApiMessageDto<List<ImagesDto>> apiMessageDto = new ApiMessageDto<>();

        List<Images> list = imagesRepository.findAllByKindRoomId(id);
        List<ImagesDto> dtoList = imageMapper.fromEntityToListDto(list);
        apiMessageDto.setData(dtoList);
        apiMessageDto.setMessage("get lisst success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> deleteImage(Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();

        Images images = imagesRepository.findById(id).orElse(null);
        if(images==null)
        {
            apiMessageDto.setMessage("Not found image ");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.IMAGE_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        imagesRepository.delete(images);
        apiMessageDto.setMessage("delete image success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<List<ImagesForHotelDto>> findAllImagesByHotelId(Long hotelId) {
        ApiMessageDto<List<ImagesForHotelDto>> apiMessageDto = new ApiMessageDto<>();

        Hotel hotel = hotelRepository.findById(hotelId).orElse(null);
        if (hotel==null)
        {
            apiMessageDto.setMessage("Hotel not found");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.HOTEL_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        List<Images> imagesList = imagesRepository.findAllImagesByHotelId(hotelId);
        List<ImagesForHotelDto> dtoList = imageMapper.fromEntityToListDtoForHotel(imagesList);

        apiMessageDto.setData(dtoList);
        apiMessageDto.setMessage("get list image success");
        return apiMessageDto;
    }
}
