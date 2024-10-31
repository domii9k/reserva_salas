package com.edu.reserva_salas.api.dto.request;

import java.util.List;

import com.edu.reserva_salas.api.infrastructure.entity.Resource;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomRequestDTO {

    String name;
    int capacity;
    List<Resource> resources;
    char status;

}
