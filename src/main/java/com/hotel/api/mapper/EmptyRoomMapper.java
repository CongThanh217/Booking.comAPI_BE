package com.hotel.api.mapper;

import com.hotel.api.dto.emptyRoom.EmptyRoomDto;
import com.hotel.api.form.emptyRoom.CreateOneEmptyRoomForm;
import com.hotel.api.form.emptyRoom.UpdateOneEmptyRoomForm;
import com.hotel.api.model.EmptyRoom;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmptyRoomMapper {

    @Mapping(source = "emptyRoom", target = "emptyRoom")
    @Mapping(source = "price", target = "price")
    @BeanMapping(ignoreByDefault = true)
    EmptyRoom formToEmptyRoomEntity(CreateOneEmptyRoomForm emptyRoom);

    @Mapping(source = "id", target = "emptyRoomId")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "emptyRoom", target = "emptyRoom")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDtoEmptyRoom")
    EmptyRoomDto fromEntityToDto(EmptyRoom emptyRoom);

    @IterableMapping(elementTargetType = EmptyRoomDto.class , qualifiedByName ="fromEntityToDtoEmptyRoom" )
    List<EmptyRoomDto> fromEntityToListDto(List<EmptyRoom> list);

    @Mapping(source = "emptyRoomNumber", target = "emptyRoom")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    void UpdatEmptyRoom(UpdateOneEmptyRoomForm updateKindRoomForm, @MappingTarget EmptyRoom emptyRoom);
}
