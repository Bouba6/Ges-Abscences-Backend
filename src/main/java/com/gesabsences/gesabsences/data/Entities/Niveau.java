package com.gesabsences.gesabsences.data.Entities;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.gesabsences.gesabsences.data.Enum.NiveauState;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
// @Document("niveau")
public class Niveau extends AbstractType {

    @DBRef
    private List<Classe> classes;

    private NiveauState niveauState;
}
