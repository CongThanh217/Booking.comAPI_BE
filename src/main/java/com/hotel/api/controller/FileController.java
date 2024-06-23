package com.hotel.api.controller;

import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.UploadFileDto;
import com.hotel.api.form.file.UploadFileForm;
import com.hotel.api.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/file")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FileController {
    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<UploadFileDto> upload(@Valid UploadFileForm uploadFileForm, BindingResult bindingResult) {
        ApiMessageDto<UploadFileDto> apiMessageDto = new ApiMessageDto<>();

        if (bindingResult.hasErrors()) {
            apiMessageDto.setMessage("Validation errors occurred");
            return apiMessageDto;
        }

        try {
            List<MultipartFile> files = uploadFileForm.getFiles();
            List<String> fileUrls = new ArrayList<>();

            for (MultipartFile file : files) {
                String fileUrl = cloudinaryService.upload(file);
                fileUrls.add(fileUrl);
            }

            UploadFileDto uploadFileDto = new UploadFileDto();
            uploadFileDto.setFileUrls(fileUrls);

            apiMessageDto.setData(uploadFileDto);
            apiMessageDto.setMessage("Files uploaded successfully");
        } catch (IOException e) {
            apiMessageDto.setMessage("Error uploading files: " + e.getMessage());
        }

        return apiMessageDto;
    }
}
