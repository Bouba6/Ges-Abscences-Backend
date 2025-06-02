package com.gesabsences.gesabsences.Mobile.Dto.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.gesabsences.gesabsences.Mobile.Dto.Response.VigileResponse;
import com.gesabsences.gesabsences.data.Entities.Vigile;

@Mapper(componentModel = "spring")
public interface MobVigileMapper {
    

    
    VigileResponse toDto(Vigile vigile);
    
   
}
