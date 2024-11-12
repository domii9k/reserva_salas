package com.edu.reserva_salas.api.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
public class ReservationResponseDTO extends RepresentationModel<ReservationResponseDTO> {
    private final String id;
    private final String roomId;
    private final String userId;
    private final LocalDate reservationDate;
    private final LocalDate reservationEndDate;

    public ReservationResponseDTO(String id, String roomId, String userId, LocalDate reservationDate, LocalDate reservationEndDate) {
        this.id = id;
        this.roomId = roomId;
        this.userId = userId;
        this.reservationDate = reservationDate;
        this.reservationEndDate = reservationEndDate;

    }


}
