package com.edu.reserva_salas.api.infrastructure.entity.factories;

import com.edu.reserva_salas.api.dto.request.ReservationRequestDTO;
import com.edu.reserva_salas.api.dto.request.RoomRequestDTO;
import com.edu.reserva_salas.api.infrastructure.entity.Reservation;
import com.edu.reserva_salas.api.infrastructure.entity.ReservationBuilder;
import com.edu.reserva_salas.api.infrastructure.entity.Room;
import com.edu.reserva_salas.api.infrastructure.entity.RoomBuilder;
import com.edu.reserva_salas.api.infrastructure.entity.enums.RoomStatus;
import com.edu.reserva_salas.api.infrastructure.entity.interfaces.ReservationInterface;
import com.edu.reserva_salas.api.infrastructure.entity.interfaces.RoomInterface;
import org.springframework.stereotype.Component;

//classe factory para escolha do que criar
@Component
public class Factory implements RoomInterface, ReservationInterface {

    @Override
    public Reservation reserveRoom(ReservationRequestDTO reservationRequestDTO) {
        return ReservationBuilder.builder()
                .userId(reservationRequestDTO.getUserId())
                .roomId(reservationRequestDTO.getRoomId())
                .startDate(reservationRequestDTO.getReservationDate())
                .endDate(reservationRequestDTO.getReservationEndDate())
                .build();
    }

    @Override
    public Room createRoom(RoomRequestDTO roomRequestDTO) {
        return RoomBuilder.builder()
                .name(roomRequestDTO.getName())
                .capacity(roomRequestDTO.getCapacity())
                .resources(roomRequestDTO.getResources())
                .status(RoomStatus.ACTIVE).build();
    }
}
