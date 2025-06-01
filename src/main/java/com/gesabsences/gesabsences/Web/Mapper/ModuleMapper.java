package com.gesabsences.gesabsences.Web.Mapper;

import com.gesabsences.gesabsences.data.Entities.Module;
import com.gesabsences.gesabsences.Web.Dto.Response.ModuleResponse;

import org.mapstruct.Mapper;
import org.springframework.context.annotation.Primary;

@Mapper(componentModel = "spring")
@Primary
public interface ModuleMapper {

    ModuleResponse toDto(Module module);
}
