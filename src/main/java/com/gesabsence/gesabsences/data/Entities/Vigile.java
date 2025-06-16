package com.gesabsences.gesabsences.data.Entities;

<<<<<<< HEAD:src/main/java/com/gesabsence/gesabsences/data/Entities/Vigile.java
public class agent {
    
=======
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Vigile extends Personne {

    @DBRef
    private User user;
>>>>>>> origin/master:src/main/java/com/gesabsences/gesabsences/data/Entities/Vigile.java
}
