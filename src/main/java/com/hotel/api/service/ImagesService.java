package com.hotel.api.service;

import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.ResponseListDto;
import com.hotel.api.dto.images.ImagesDto;
import com.hotel.api.dto.images.ImagesForHotelDto;
import com.hotel.api.form.images.CreateImagesForm;
import com.hotel.api.form.images.UpdateImagesForm;
import com.hotel.api.form.images.UpdateListImageForm;

import java.util.List;

public interface ImagesService {

    ApiMessageDto<String> createImage(CreateImagesForm createImagesForm);
    ApiMessageDto<String> updateImage(UpdateListImageForm updateImagesForm);

    ApiMessageDto<List<ImagesDto>> getListImages(Long id);
    ApiMessageDto<String> deleteImage(Long id);

    ApiMessageDto<List<ImagesForHotelDto>> findAllImagesByHotelId(Long hotelId);

}
