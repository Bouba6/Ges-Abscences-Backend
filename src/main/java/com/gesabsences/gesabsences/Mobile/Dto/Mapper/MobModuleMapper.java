package com.gesabsences.gesabsences.Mobile.Dto.Mapper;

import com.gesabsences.gesabsences.data.Entities.Module;
import com.gesabsences.gesabsences.Mobile.Dto.Response.ModuleResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MobModuleMapper {

    ModuleResponse toDto(Module module);
}
