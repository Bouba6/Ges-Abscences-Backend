package com.gesabsences.gesabsences.utils.Mapper;

import com.gesabsences.gesabsences.data.Entities.Module;
import com.gesabsences.gesabsences.Web.Dto.Response.ModuleResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ModuleMapper {

    ModuleResponse toDto(Module module);
}
