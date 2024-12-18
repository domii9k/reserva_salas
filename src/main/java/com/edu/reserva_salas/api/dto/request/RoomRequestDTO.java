package com.edu.reserva_salas.api.dto.request;

import java.util.List;

import com.edu.reserva_salas.api.infrastructure.entity.enums.RoomStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RoomRequestDTO {

    @NotNull
    String name;
    @NotNull
    int capacity;
    @NotNull
    List<String> resources;

}
