package com.hotel.api.controller;

import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.ErrorCode;
import com.hotel.api.dto.kindOfRoom.KindOfRoomDto;
import com.hotel.api.form.kindOfRoom.ChangeStatusKindForm;
import com.hotel.api.form.kindOfRoom.CreateKindRoomForm;
import com.hotel.api.form.kindOfRoom.UpdateKindRoomForm;
import com.hotel.api.model.criteria.KindOfRoomCriteria;
import com.hotel.api.service.KindOfRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/kind-of-room")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class KindOfRoomController extends ABasicController{
    @Autowired
    private KindOfRoomService kindOfRoomService;
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('KIND_AD')")
    public ApiMessageDto<String> createKindOfRoom(@Valid @RequestBody CreateKindRoomForm createKindRoomForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Long accountId = getCurrentUser();
        if(accountId==null)
        {
            apiMessageDto.setMessage("Please create an account to post your hotel");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        apiMessageDto = kindOfRoomService.createKindOfRoom(createKindRoomForm,accountId);
        return apiMessageDto;
    }
    @GetMapping(value = "/list-by-hotel", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('KIND_V')")
    public ApiMessageDto<List<KindOfRoomDto>> getListHotel() {
        Long account = getCurrentUser();
        ApiMessageDto<List<KindOfRoomDto>> apiMessageDto = kindOfRoomService.getListKindByHotel(account);
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('KIND_U')")
    public ApiMessageDto<String> updateKind(@Valid @RequestBody UpdateKindRoomForm updateKindRoomForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = kindOfRoomService.updateKindRoom(updateKindRoomForm);
        return apiMessageDto;
    }

    @GetMapping(value = "/list-public/{hotelId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<KindOfRoomDto>> getListHotelPublic(@PathVariable("hotelId") Long id) {
        ApiMessageDto<List<KindOfRoomDto>> apiMessageDto = kindOfRoomService.getListPublic(id);
        return apiMessageDto;
    }
    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<KindOfRoomDto> getKindOfRoom(@PathVariable("id") Long id) {
        ApiMessageDto<KindOfRoomDto> apiMessageDto = kindOfRoomService.getKindOfRoom(id);
        return apiMessageDto;
    }
    @PutMapping(value = "/change-status", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('KIND_C')")
    public ApiMessageDto<String> getKindOfRoom(@Valid @RequestBody ChangeStatusKindForm changeStatusKindForm,BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = kindOfRoomService.changeStatus(changeStatusKindForm);
        return apiMessageDto;
    }

    @GetMapping(value = "/get-emptyRoom", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<KindOfRoomDto>> getEmptyRoom(@RequestParam("hotelId") Long hotelId ,@RequestParam("startDate") Date startDate,@RequestParam("endDate") Date endDate) {
        ApiMessageDto<List<KindOfRoomDto>> apiMessageDto = kindOfRoomService.getKindOfRangeTime( hotelId , startDate,  endDate);
        return apiMessageDto;
    }

}
