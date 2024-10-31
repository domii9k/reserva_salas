package com.edu.reserva_salas.api.dto.response;

import java.time.LocalDate;

public record ReservationResponseDTO(
        String id,
        String roomId,
        String userId,
        LocalDate reservationDate,
        LocalDate reservationEndDate) {
}
