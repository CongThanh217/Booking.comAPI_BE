package com.hotel.api.dto.address;

import com.hotel.api.dto.ABasicAdminDto;
import com.hotel.api.dto.nation.NationAdminDto;
import com.hotel.api.dto.user.UserDto;
import lombok.Data;

@Data
public class AddressAdminDto extends ABasicAdminDto {
    private String address;
    private NationAdminDto wardInfo;
    private NationAdminDto districtInfo;
    private NationAdminDto provinceInfo;
    private String name;
    private String phone;
    private UserDto userInfo;
}
