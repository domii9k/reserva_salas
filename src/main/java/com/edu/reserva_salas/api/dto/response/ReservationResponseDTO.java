package com.edu.reserva_salas.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
public class ReservationResponseDTO extends RepresentationModel<ReservationResponseDTO> {
    private final String id;
    private final String roomId;
    private final String userId;
    private final LocalDateTime reservationDate;
    private final LocalDateTime reservationEndDate;

    public ReservationResponseDTO(String id, String roomId, String userId, @NotNull LocalDateTime reservationDate, @NotNull LocalDateTime reservationEndDate) {
        this.id = id;
        this.roomId = roomId;
        this.userId = userId;
        this.reservationDate = reservationDate;
        this.reservationEndDate = reservationEndDate;

    }


}
