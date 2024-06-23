package com.hotel.api.dto.user;

import com.hotel.api.dto.account.AccountAutoCompleteDto;
import lombok.Data;

@Data
public class UserAutoCompleteDto {

    private Long id;
    private AccountAutoCompleteDto accountAutoCompleteDto;
}
