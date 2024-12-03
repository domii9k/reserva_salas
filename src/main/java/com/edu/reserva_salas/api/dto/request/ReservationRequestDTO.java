package com.edu.reserva_salas.api.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@AllArgsConstructor
public class ReservationRequestDTO {
    @NotNull
    String roomId;
    @NotNull
    String userId;
    @NotNull
    LocalDateTime reservationDate;
    @NotNull
    LocalDateTime reservationEndDate;

}
