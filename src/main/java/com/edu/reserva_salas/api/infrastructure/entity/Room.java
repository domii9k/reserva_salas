package com.edu.reserva_salas.api.infrastructure.entity;
//classe para criação de salas

import java.util.List;

import com.edu.reserva_salas.api.infrastructure.entity.enums.RoomStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import org.springframework.data.annotation.Id;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "rooms")
public class Room {

    @Id
    private String id;
    private String name;
    private Integer capacity;
    private List<String> resources; //lista de recursos disponiveis na sala
    private RoomStatus status;

    protected  Room() {} //construtor protegido para evitar instanciação direta
}
