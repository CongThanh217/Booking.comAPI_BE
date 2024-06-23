package com.hotel.api.form.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel
public class UpdateUserForm {

    @NotNull(message = "id cant not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long  id;
    @NotEmpty(message = "name cant not be null")
    @ApiModelProperty(name = "name", required = true)
    private String fullName;
    @ApiModelProperty(name = "phone")
    private String phone;
    @Email
    @ApiModelProperty(name = "email")
    private String email;
    @ApiModelProperty(name = "password")
    private String password;
    @ApiModelProperty(name = "avatarPath")
    private String avatarPath;
}
