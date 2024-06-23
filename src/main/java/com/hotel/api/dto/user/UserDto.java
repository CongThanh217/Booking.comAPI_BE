package com.hotel.api.dto.user;

import com.hotel.api.dto.account.AccountDto;
import lombok.Data;

import java.util.Date;

@Data
public class UserDto {

    private Long id;
    private Date birthday;
    private AccountDto account;

}