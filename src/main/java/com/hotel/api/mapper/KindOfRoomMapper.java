package com.hotel.api.mapper;

import com.hotel.api.dto.kindOfRoom.KindOfRoomDto;
import com.hotel.api.dto.kindOfRoom.KindOfRoomDtoAuto;
import com.hotel.api.form.kindOfRoom.CreateKindRoomForm;
import com.hotel.api.form.kindOfRoom.UpdateKindRoomForm;
import com.hotel.api.model.KindOfRoom;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface KindOfRoomMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "roomNumber", target = "roomNumber")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "saleOff", target = "saleOff")
    @Mapping(source = "numberOfPeople", target = "numberOfPeople")
    @Mapping(source = "numberOfBed", target = "numberOfBed")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    KindOfRoom fromKinRoomDtoToEntity(CreateKindRoomForm createKindRoomForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "roomNumber", target = "roomNumber")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "saleOff", target = "saleOff")
    @Mapping(source = "numberOfPeople", target = "numberOfPeople")
    @Mapping(source = "numberOfBed", target = "numberOfBed")
    @Mapping(source = "status", target = "status")
    @Named("fromEntityToKindOfRoomDto")
    @BeanMapping(ignoreByDefault = true)
    KindOfRoomDto fromEntityToDto(KindOfRoom kind);

    @IterableMapping(elementTargetType = KindOfRoomDto.class , qualifiedByName ="fromEntityToKindOfRoomDto" )
    List<KindOfRoomDto> fromEntityToListDto(List<KindOfRoom> list);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Named("fromEntityToKindOfRoomDtoAuto")
    @BeanMapping(ignoreByDefault = true)
    KindOfRoomDtoAuto fromEntityToDtoAuto(KindOfRoom kind);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "roomNumber", target = "roomNumber")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "saleOff", target = "saleOff")
    @Mapping(source = "numberOfPeople", target = "numberOfPeople")
    @Mapping(source = "numberOfBed", target = "numberOfBed")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    void UpdateHotelDtoToEntity(UpdateKindRoomForm updateKindRoomForm, @MappingTarget KindOfRoom kindOfRoom);
}
