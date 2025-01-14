package com.hotel.api.controller;

import com.hotel.api.constant.UserBaseConstant;
import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.ErrorCode;
import com.hotel.api.dto.ResponseListDto;
import com.hotel.api.dto.address.AddressAdminDto;
import com.hotel.api.dto.address.AddressDto;
import com.hotel.api.form.address.CreateAddressForm;
import com.hotel.api.form.address.UpdateAddressForm;
import com.hotel.api.mapper.AddressMapper;
import com.hotel.api.model.Address;
import com.hotel.api.model.Nation;
import com.hotel.api.model.User;
import com.hotel.api.model.criteria.AddressCriteria;
import com.hotel.api.repository.AddressRepository;
import com.hotel.api.repository.NationRepository;
import com.hotel.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/address")
public class AddressController extends ABasicController {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private NationRepository nationRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/auto-complete")
    public ApiMessageDto<ResponseListDto<List<AddressDto>>> autoComplete(AddressCriteria addressCriteria){
        ApiMessageDto<ResponseListDto<List<AddressDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<AddressDto>> responseListDto = new ResponseListDto<>();
        Pageable pageable = PageRequest.of(0,10);
        addressCriteria.setStatus(UserBaseConstant.STATUS_ACTIVE);
        Page<Address> addresses = addressRepository.findAll(addressCriteria.getSpecification(), pageable);
        List<AddressDto> addressAdminDtos = addressMapper.fromEntityToAddressDtoAutoCompleteList(addresses.getContent());

        responseListDto.setContent(addressAdminDtos);
        responseListDto.setTotalPages(addresses.getTotalPages());
        responseListDto.setTotalElements(addresses.getTotalElements());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get address success");
        return apiMessageDto;
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADD_L')")
    public ApiMessageDto<ResponseListDto<List<AddressAdminDto>>> list(AddressCriteria addressCriteria, Pageable pageable){
        ApiMessageDto<ResponseListDto<List<AddressAdminDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<AddressAdminDto>> responseListDto = new ResponseListDto<>();
        Page<Address> addresses = addressRepository.findAll(addressCriteria.getSpecification(), pageable);
        List<AddressAdminDto> addressAdminDtos = addressMapper.fromEntityToAddressAdminDtoList(addresses.getContent());

        responseListDto.setContent(addressAdminDtos);
        responseListDto.setTotalPages(addresses.getTotalPages());
        responseListDto.setTotalElements(addresses.getTotalElements());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get list address success");
        return apiMessageDto;
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADD_V')")
    public ApiMessageDto<AddressAdminDto> get(@PathVariable Long id){
        ApiMessageDto<AddressAdminDto> apiMessageDto = new ApiMessageDto<>();
        Address address = addressRepository.findById(id).orElse(null);
        if(address == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ADDRESS_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Address not found");
            return apiMessageDto;
        }
        AddressAdminDto addressAdminDto = addressMapper.fromEntityToAddressAdminDto(address);

        apiMessageDto.setData(addressAdminDto);
        apiMessageDto.setMessage("Get address success");
        return apiMessageDto;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADD_C')")
    public ApiMessageDto<String> create(@Valid @RequestBody CreateAddressForm createAddressForm, BindingResult bindingResult){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Nation ward = nationRepository.findById(createAddressForm.getWardId()).orElse(null);
        if (ward == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.NATION_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Ward not found");
            return apiMessageDto;
        }
        Nation district = nationRepository.findById(createAddressForm.getDistrictId()).orElse(null);
        if (district == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.NATION_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("District not found");
            return apiMessageDto;
        }
        Nation province = nationRepository.findById(createAddressForm.getProvinceId()).orElse(null);
        if (province == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.NATION_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Province not found");
            return apiMessageDto;
        }
        User user = userRepository.findById(createAddressForm.getUserId()).orElse(null);
        if (user == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("User not found");
            return apiMessageDto;
        }
        Address address = addressMapper.fromCreateAddressFormToEntity(createAddressForm);
        address.setWard(ward);
        address.setDistrict(district);
        address.setProvince(province);
        address.setUser(user);
        addressRepository.save(address);

        apiMessageDto.setMessage("Create address success");
        return apiMessageDto;
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADD_U')")
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateAddressForm updateAddressForm, BindingResult bindingResult){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Address address = addressRepository.findById(updateAddressForm.getId()).orElse(null);
        if(address == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ADDRESS_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Address not found");
            return apiMessageDto;
        }
        Nation ward = nationRepository.findById(updateAddressForm.getWardId()).orElse(null);
        if (ward == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.NATION_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Ward not found");
            return apiMessageDto;
        }
        Nation district = nationRepository.findById(updateAddressForm.getDistrictId()).orElse(null);
        if (district == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.NATION_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("District not found");
            return apiMessageDto;
        }
        Nation province = nationRepository.findById(updateAddressForm.getProvinceId()).orElse(null);
        if (province == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.NATION_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Province not found");
            return apiMessageDto;
        }
        addressMapper.fromUpdateAddressFormToEntity(updateAddressForm, address);
        address.setWard(ward);
        address.setDistrict(district);
        address.setProvince(province);
        addressRepository.save(address);

        apiMessageDto.setMessage("Update address success");
        return apiMessageDto;
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADD_D')")
    public ApiMessageDto<String> delete(@PathVariable Long id){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Address address = addressRepository.findById(id).orElse(null);
        if(address == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ADDRESS_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Address not found");
            return apiMessageDto;
        }
        addressRepository.delete(address);

        apiMessageDto.setMessage("Delete address success");
        return apiMessageDto;
    }
}
