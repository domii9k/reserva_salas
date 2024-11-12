package com.edu.reserva_salas.api.infrastructure.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class Users {

    @Id
    private String id;
    private String name;

    public Users(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
