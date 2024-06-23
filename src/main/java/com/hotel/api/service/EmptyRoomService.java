package com.hotel.api.service;

import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.emptyRoom.EmptyRoomDto;
import com.hotel.api.form.emptyRoom.CreateListEmptyRoomForm;
import com.hotel.api.form.emptyRoom.UpdateOneEmptyRoomForm;
import com.hotel.api.model.criteria.EmptyRoomCriteria;

import java.util.List;

public interface EmptyRoomService {

    ApiMessageDto<String> createEmptyRoom(CreateListEmptyRoomForm createListEmptyRoomForm);
    ApiMessageDto<String> updateEmptyRoom(UpdateOneEmptyRoomForm updateOneEmptyRoomForm);
    ApiMessageDto<List<EmptyRoomDto>> getListEmptyRoom(EmptyRoomCriteria emptyRoomCriteria);

    ApiMessageDto<EmptyRoomDto> getEmptyRoom(Long emptyRoomId);

}
