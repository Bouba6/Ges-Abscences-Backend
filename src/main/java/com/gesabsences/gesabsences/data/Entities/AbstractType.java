package com.gesabsences.gesabsences.data.Entities;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class AbstractType {

    @Id
    protected String id;
}
