package com.edu.reserva_salas.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.edu.reserva_salas.api.dto.response.ReservationResponseDTO;
import com.edu.reserva_salas.api.dto.response.RoomResponseDTO;
import com.edu.reserva_salas.api.infrastructure.entity.Reservation;
import com.edu.reserva_salas.api.infrastructure.entity.Room;

@Mapper(componentModel = "spring")
public interface RequestConverter {

    @Mapping(target = "id", source = "room.id")
    @Mapping(target = "name", source = "room.name")
    @Mapping(target = "capacity", source = "room.capacity")
    @Mapping(target = "resources", source = "room.resources")
    @Mapping(target = "status", source = "room.status")
    RoomResponseDTO toRoomResponseDTO(Room room);

    @Mapping(target = "id", source = "reservation.id")
    @Mapping(target = "roomId", source = "reservation.roomId")
    @Mapping(target = "userId", source = "reservation.userId")
    @Mapping(target = "reservationDate", source = "reservation.reservationDate")
    @Mapping(target = "reservationEndDate", source = "reservation.reservationEndDate")
    ReservationResponseDTO toReservationResponseDTO(Reservation reservation);
    
} 
