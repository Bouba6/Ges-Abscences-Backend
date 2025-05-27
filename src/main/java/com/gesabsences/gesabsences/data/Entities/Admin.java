package com.gesabsences.gesabsences.data.Entities;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("Admin")
public class Admin extends Personne {

    @DBRef
    private User user;
}
