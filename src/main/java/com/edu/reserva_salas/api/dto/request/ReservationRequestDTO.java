package com.edu.reserva_salas.api.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ReservationRequestDTO {
    @NotNull
    String roomId;
    @NotNull
    String userId;
    @NotNull
    LocalDate reservationDate;
    @NotNull
    LocalDate reservationEndDate;

}
