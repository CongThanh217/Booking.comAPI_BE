package com.hotel.api.service;

import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.kindOfRoom.KindOfRoomDto;
import com.hotel.api.form.kindOfRoom.ChangeStatusKindForm;
import com.hotel.api.form.kindOfRoom.CreateKindRoomForm;
import com.hotel.api.form.kindOfRoom.UpdateKindRoomForm;
import com.hotel.api.model.criteria.KindOfRoomCriteria;

import java.util.Date;
import java.util.List;

public interface KindOfRoomService {

    ApiMessageDto<String> createKindOfRoom(CreateKindRoomForm createKindRoomForm,Long accountId);
    ApiMessageDto<List<KindOfRoomDto>> getListKindByHotel(long hotelId);
    ApiMessageDto<String> updateKindRoom(UpdateKindRoomForm updateKindRoomForm);
    ApiMessageDto<List<KindOfRoomDto>> getListPublic(long hotelId);

    ApiMessageDto<KindOfRoomDto> getKindOfRoom(long id);
    ApiMessageDto<String> changeStatus(ChangeStatusKindForm changeStatusKindForm);

    ApiMessageDto<List<KindOfRoomDto>> getKindOfRangeTime(Long hotelId ,Date startDate , Date endDate);
}
