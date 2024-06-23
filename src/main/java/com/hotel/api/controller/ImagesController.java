package com.hotel.api.controller;

import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.images.ImagesDto;
import com.hotel.api.dto.images.ImagesForHotelDto;
import com.hotel.api.form.images.CreateImagesForm;
import com.hotel.api.form.images.UpdateImagesForm;
import com.hotel.api.form.images.UpdateListImageForm;
import com.hotel.api.service.ImagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/image-of-room")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ImagesController extends ABasicController{

    @Autowired
    private ImagesService imagesService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('IM_AD')")
    public ApiMessageDto<String> addImagesKind(@Valid @RequestBody CreateImagesForm createImagesForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = imagesService.createImage(createImagesForm);
        return apiMessageDto;
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('IM_U')")
    public ApiMessageDto<String> updateImageKind(@Valid @RequestBody UpdateListImageForm updateImagesForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = imagesService.updateImage(updateImagesForm);
        return apiMessageDto;
    }

    @GetMapping(value = "/get-by-kind", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<ImagesDto>> getListImageByKind(@RequestParam("id") long id) {
        ApiMessageDto<List<ImagesDto>> apiMessageDto = imagesService.getListImages(id);
        return apiMessageDto;
    }
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasRole('IM_U')")
    public ApiMessageDto<String> delete(@PathVariable("id") long id) {
        ApiMessageDto<String> apiMessageDto = imagesService.deleteImage(id);
        return apiMessageDto;
    }
    @GetMapping(value = "/get-by-hotel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<ImagesForHotelDto>> getListImageByHotel(@RequestParam("hotelId") long hotelId) {
        ApiMessageDto<List<ImagesForHotelDto>> apiMessageDto = imagesService.findAllImagesByHotelId(hotelId);
        return apiMessageDto;
    }
}
