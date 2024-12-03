package com.edu.reserva_salas.api.dto.mapper;

import org.springframework.stereotype.Component;

import com.edu.reserva_salas.api.dto.request.RoomRequestDTO;
import com.edu.reserva_salas.api.infrastructure.entity.Room;
import com.edu.reserva_salas.api.infrastructure.entity.RoomBuilder;

import java.util.UUID;

@Component
public class RoomMapper {



    public Room toRoom(RoomRequestDTO roomRequestDTO) {
        return RoomBuilder.builder()
                .id(UUID.randomUUID().toString())
                .name(roomRequestDTO.getName())
                .capacity(roomRequestDTO.getCapacity())
                .resources(roomRequestDTO.getResources())
                .build();
    }

    public RoomRequestDTO toRoomRequestDTO(Room room) {
        return RoomRequestDTO.builder()
                .name(room.getName())
                .capacity(room.getCapacity())
                .resources(room.getResources())
                .build();
    }

}
