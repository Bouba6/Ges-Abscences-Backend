package com.gesabsences.gesabsences.data.Entities;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// @Document("module")
public class Module extends AbstractType {

    private String nom;
    private int coef;

    @DBRef
    private List<Professeur> professeurs;

    @DBRef
    private List<Cours> cours;
}
