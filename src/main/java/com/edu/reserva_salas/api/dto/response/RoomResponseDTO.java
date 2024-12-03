package com.edu.reserva_salas.api.dto.response;

import com.edu.reserva_salas.api.infrastructure.entity.enums.RoomStatus;
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
    private final RoomStatus status;


    public RoomResponseDTO(String id, String name, int capacity, List<String> resources, RoomStatus status) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.resources = resources;
        this.status = status;
    }
}
