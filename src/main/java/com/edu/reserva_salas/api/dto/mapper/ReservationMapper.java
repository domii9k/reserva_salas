package com.edu.reserva_salas.api.dto.mapper;

import org.springframework.stereotype.Component;

import com.edu.reserva_salas.api.dto.request.ReservationRequestDTO;
import com.edu.reserva_salas.api.infrastructure.entity.Reservation;
import com.edu.reserva_salas.api.infrastructure.entity.builders.ReservationBuilder;

import java.util.UUID;

@Component
public class ReservationMapper {

    public Reservation toReservation(ReservationRequestDTO reservationRequestDTO, String userId, String roomId){
        return ReservationBuilder.builder()
        .id(UUID.randomUUID().toString())
        .userId(userId)
        .roomId(roomId)
        .startDate(reservationRequestDTO.reservationDate())
        .endDate(reservationRequestDTO.reservationEndDate())
        .build();
    }

    public  ReservationRequestDTO toReservationRequestDTO(Reservation reservation) {
        return ReservationRequestDTO.builder()
        .userId(reservation.getUserId())
        .roomId(reservation.getRoomId())
        .reservationDate(reservation.getReservationDate())
        .reservationEndDate(reservation.getReservationEndDate())
        .build();


    }
}
