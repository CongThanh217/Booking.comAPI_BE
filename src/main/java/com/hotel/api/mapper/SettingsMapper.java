package com.hotel.api.mapper;

import com.hotel.api.dto.settings.SettingsAutoCompleteDto;
import com.hotel.api.dto.settings.SettingsDto;
import com.hotel.api.form.settings.CreateSettingsForm;
import com.hotel.api.form.settings.UpdateSettingsForm;
import com.hotel.api.model.Settings;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SettingsMapper {

    @Mapping(source = "settingKey", target = "settingKey")
    @Mapping(source = "settingValue", target = "settingValue")
    @Mapping(source = "groupName", target = "groupName")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "isSystem", target = "isSystem")
    @Mapping(source = "isEditable", target = "isEditable")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromCreateSettingsFormToEntity")
    Settings fromCreateSettingsFormToEntity(CreateSettingsForm createSettingsForm);

    @Mapping(source = "settingValue", target = "settingValue")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromUpdateSettingsFormToEntity")
    void fromUpdateSettingsFormToEntity(UpdateSettingsForm updateSettingsForm, @MappingTarget Settings settings);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "settingKey", target = "settingKey")
    @Mapping(source = "settingValue", target = "settingValue")
    @Mapping(source = "groupName", target = "groupName")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "isSystem", target = "isSystem")
    @Mapping(source = "isEditable", target = "isEditable")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "createdDate", target = "createdDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToSettingsDto")
    SettingsDto fromEntityToSettingsDto(Settings settings);

    @IterableMapping(elementTargetType = SettingsDto.class, qualifiedByName = "fromEntityToSettingsDto")
    List<SettingsDto> fromEntityListToSettingsDtoList(List<Settings> settings);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "settingKey", target = "settingKey")
    @Mapping(source = "groupName", target = "groupName")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToSettingsAutoCompleteDto")
    SettingsAutoCompleteDto fromEntityToSettingsAutoCompleteDto(Settings settings);

    @IterableMapping(elementTargetType = SettingsAutoCompleteDto.class, qualifiedByName = "fromEntityToSettingsAutoCompleteDto")
    List<SettingsAutoCompleteDto> fromEntityListToSettingsAutoCompleteDtoList(List<Settings> settings);
}
