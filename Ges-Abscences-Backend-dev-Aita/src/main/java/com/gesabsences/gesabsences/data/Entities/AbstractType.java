package com.gesabsences.gesabsences.data.Entities;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AbstractType {

    @Id
    protected String id;
}
