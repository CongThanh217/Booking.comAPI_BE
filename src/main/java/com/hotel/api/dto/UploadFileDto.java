package com.hotel.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class UploadFileDto {
    private List<String> fileUrls;
}
