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
public class SignUpUserForm {

    @ApiModelProperty(name = "email")
    @Email
    private String email;
    @NotEmpty(message = "userName cant not be null")
    @ApiModelProperty(name = "userName",required = true)
    private String userName;
    @NotEmpty(message = "password cant not be null")
    @ApiModelProperty(name = "password", required = true)
    private String password;
    @NotNull(message = "kind cant not be null")
    @ApiModelProperty(name = "kind",required = true)
    private Integer kind;

}
