package com.hotel.api.mapper;

import com.hotel.api.dto.hotel.HotelDto;
import com.hotel.api.dto.hotel.HotelDtoAuto;
import com.hotel.api.form.hotel.CreateHotelForm;
import com.hotel.api.form.hotel.UpdateHotelForm;
import com.hotel.api.model.Hotel;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring",uses = {NationMapper.class})
public interface HotelMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "images", target = "images")
    @Mapping(source = "star", target = "stars")
    @Mapping(source = "address", target = "address")
    @BeanMapping(ignoreByDefault = true)
    Hotel fromHotelDtoToEntity(CreateHotelForm createHotelForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "images", target = "images")
    @Mapping(source = "stars", target = "stars")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "status",target = "status")
    @Mapping(source = "totalReview",target = "totalReview")
    @Mapping(source = "ward", target = "wardInfo", qualifiedByName = "fromEntityToAutoCompleteDto")
    @Mapping(source = "district", target = "districtInfo", qualifiedByName = "fromEntityToAutoCompleteDto")
    @Mapping(source = "province", target = "provinceInfo", qualifiedByName = "fromEntityToAutoCompleteDto")
    @Mapping(source = "user", target = "userInfo", qualifiedByName = "fromUserToUserDtoAutoComplete")
    @Named("fromEntityToHotelDto")
    @BeanMapping(ignoreByDefault = true)
    HotelDto fromEntityToDto(Hotel hotel);

    @IterableMapping(elementTargetType = HotelDto.class , qualifiedByName ="fromEntityToHotelDto" )
    List<HotelDto> fromEntityToListDto(List<Hotel> list);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "images", target = "images")
    @Mapping(source = "star", target = "stars")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "status",target = "status")
    @BeanMapping(ignoreByDefault = true)
    void UpdateHotelDtoToEntity(UpdateHotelForm updateHotelForm, @MappingTarget Hotel hotel);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "ward", target = "wardInfo", qualifiedByName = "fromEntityToAutoCompleteDto")
    @Mapping(source = "district", target = "districtInfo", qualifiedByName = "fromEntityToAutoCompleteDto")
    @Mapping(source = "province", target = "provinceInfo", qualifiedByName = "fromEntityToAutoCompleteDto")
    @Named("fromEntityToDtoForUser")
    @BeanMapping(ignoreByDefault = true)
    HotelDtoAuto fromEntityToDtoForUser(Hotel hotel);

}
