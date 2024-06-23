package com.hotel.api.form.images;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateImagesForm {

    @NotNull(message = "filePath is required")
    @ApiModelProperty(name = "filePath", required = true)
    List<String> filePath;
    @NotNull(message = "kindOfRoomId is required")
    @ApiModelProperty(name = "kindOfRoomId", required = true)
    private Long kindOfRoomId;
}
