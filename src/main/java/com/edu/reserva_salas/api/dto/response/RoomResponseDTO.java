package com.edu.reserva_salas.api.dto.response;

import java.util.List;

public record RoomResponseDTO(String id, String name, int capacity, List<String> resources, char status) {

}
