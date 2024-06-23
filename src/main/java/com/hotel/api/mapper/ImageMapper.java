package com.hotel.api.mapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotel.api.dto.images.ImagesDto;
import com.hotel.api.dto.images.ImagesForHotelDto;
import com.hotel.api.dto.kindOfRoom.KindOfRoomDto;
import com.hotel.api.model.Images;
import com.hotel.api.model.KindOfRoom;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring",uses = {KindOfRoomMapper.class})
public interface ImageMapper {

    @Mapping(source = "kindRoom",target = "kindRoom",qualifiedByName="fromEntityToKindOfRoomDtoAuto")
    @Mapping(source = "link",target = "link")
    @Mapping(source = "id",target = "id")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToImageDto")
    ImagesDto fromEntityImageToDto(Images images);

    @IterableMapping(elementTargetType = KindOfRoomDto.class , qualifiedByName ="fromEntityToImageDto" )
    List<ImagesDto> fromEntityToListDto(List<Images> list);

    @Mapping(source = "link",target = "link")
    @Mapping(source = "id",target = "id")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityImageForHotelToDto")
    ImagesForHotelDto fromEntityImageForHotelToDto(Images images);

    @IterableMapping(elementTargetType = KindOfRoomDto.class , qualifiedByName ="fromEntityImageForHotelToDto" )
    List<ImagesForHotelDto> fromEntityToListDtoForHotel(List<Images> list);
}
