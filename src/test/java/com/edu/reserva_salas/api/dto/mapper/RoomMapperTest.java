package com.edu.reserva_salas.api.dto.mapper;

import com.edu.reserva_salas.api.dto.request.RoomRequestDTO;
import com.edu.reserva_salas.api.infrastructure.entity.Room;
import com.edu.reserva_salas.api.infrastructure.entity.RoomBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoomMapperTest {

    private final RoomMapper roomMapper = new RoomMapper();

    @Test
    @DisplayName("Should convert room to dto")
    void toRoom_ShouldConvertRoomToDto() {
        RoomRequestDTO roomRequestDTO = RoomRequestDTO.builder()
                .name("Room 101")
                .capacity(30)
                .resources(List.of("Projector", "Whiteboard"))
                .build();

        Room room = roomMapper.toRoom(roomRequestDTO);

        assertNotNull(room);
        assertNotNull(room.getId());
        assertEquals(roomRequestDTO.getName(), room.getName());
        assertEquals(roomRequestDTO.getCapacity(), room.getCapacity());
        assertEquals(roomRequestDTO.getResources(), room.getResources());
    }

    @Test
    @DisplayName("Should convert dto to room")
    void toRoomRequestDTO_ShouldConvertDtoToRoom() {
        Room room = RoomBuilder.builder()
                .id("room123")
                .name("Room 101")
                .capacity(30)
                .resources(List.of("Projector", "Whiteboard"))
                .build();

        RoomRequestDTO roomRequestDTO = roomMapper.toRoomRequestDTO(room);

        assertNotNull(roomRequestDTO);
        assertEquals(room.getName(), roomRequestDTO.getName());
        assertEquals(room.getCapacity(), roomRequestDTO.getCapacity());
        assertEquals(room.getResources(), roomRequestDTO.getResources());
    }
}