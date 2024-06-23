package com.hotel.api.controller;

import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.emptyRoom.EmptyRoomDto;
import com.hotel.api.form.emptyRoom.CreateListEmptyRoomForm;
import com.hotel.api.form.emptyRoom.UpdateOneEmptyRoomForm;
import com.hotel.api.model.criteria.EmptyRoomCriteria;
import com.hotel.api.service.impl.EmptyRoomImpl;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/v1/empty-room")
public class EmptyRoomController {

    @Autowired
    private EmptyRoomImpl emptyRoomService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('EMPTY_A')")
    public ApiMessageDto<String> createEmpTyRoom(@Valid @RequestBody CreateListEmptyRoomForm createListEmptyRoomForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = emptyRoomService.createEmptyRoom(createListEmptyRoomForm);
        return apiMessageDto;
    }
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('EMPTY_U')")
    public ApiMessageDto<String> updateEmptyRoom(@Valid @RequestBody UpdateOneEmptyRoomForm updateOneEmptyRoomForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = emptyRoomService.updateEmptyRoom(updateOneEmptyRoomForm);
        return apiMessageDto;
    }
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<EmptyRoomDto>> getListEmptyRoom(@Valid EmptyRoomCriteria emptyRoomCriteria, BindingResult bindingResult) {
        ApiMessageDto<List<EmptyRoomDto>> apiMessageDto = emptyRoomService.getListEmptyRoom(emptyRoomCriteria);
        return apiMessageDto;
    }

    @GetMapping(value = "/get-by-id/{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<EmptyRoomDto> getListEmptyRoom(@PathVariable("roomId") Long roomId) {
        ApiMessageDto<EmptyRoomDto> apiMessageDto = emptyRoomService.getEmptyRoom(roomId);
        return apiMessageDto;
    }
}
