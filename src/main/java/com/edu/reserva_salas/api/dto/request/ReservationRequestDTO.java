package com.edu.reserva_salas.api.dto.request;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record ReservationRequestDTO(
        String roomId,
        String userId,
        LocalDate reservationDate,
        LocalDate reservationEndDate) {

}
