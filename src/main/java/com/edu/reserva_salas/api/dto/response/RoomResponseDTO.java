package com.edu.reserva_salas.api.dto.response;

import java.util.List;

import com.edu.reserva_salas.api.infrastructure.entity.Resource;

public record RoomResponseDTO(String id, String name, int capacity, List<Resource> resources, char status) {

}
