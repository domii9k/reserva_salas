package com.edu.reserva_salas.api.dto.response;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;


@Getter
public class RoomResponseDTO extends RepresentationModel<RoomResponseDTO> {
    private final String id;
    private final String name;
    private final int capacity;
    private final List<String> resources;
    private final char status;


    public RoomResponseDTO(String id, String name, int capacity, List<String> resources, char status) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.resources = resources;
        this.status = status;
    }
}
