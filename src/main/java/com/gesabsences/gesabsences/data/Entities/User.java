package com.gesabsences.gesabsences.data.Entities;

import org.springframework.data.mongodb.core.mapping.Document;

import com.gesabsences.gesabsences.data.Enum.Role;

import lombok.Data;

@Data
@Document(collection = "users")
public class User extends Personne {

    private String password;
    private Role role;
    private String Login;
}
