package com.edu.reserva_salas.api.services;

import com.edu.reserva_salas.api.dto.mapper.RequestConverter;
import com.edu.reserva_salas.api.dto.mapper.RoomMapper;
import com.edu.reserva_salas.api.dto.request.RoomRequestDTO;
import com.edu.reserva_salas.api.dto.response.RoomResponseDTO;
import com.edu.reserva_salas.api.infrastructure.entity.Room;
import com.edu.reserva_salas.api.infrastructure.entity.RoomBuilder;
import com.edu.reserva_salas.api.infrastructure.entity.factories.Factory;
import com.edu.reserva_salas.api.repositories.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @InjectMocks
    RoomService roomService;
    @Mock
    RoomMapper roomMapper;
    @Mock
    RequestConverter requestConverter;
    @Mock
    Factory factory;
    @Mock
    RoomRepository roomRepository;

    Room room;
    RoomRequestDTO roomRequestDTO;
    RoomResponseDTO roomResponseDTO;

    @BeforeEach
    void setUp() {
        List<String> resources = List.of("resource 1", "resource 2");
        roomRequestDTO = new RoomRequestDTO("Nome", 23, resources, 'A');

        room = RoomBuilder.builder()
                .name(roomRequestDTO.getName())
                .capacity(roomRequestDTO.getCapacity())
                .resources(roomRequestDTO.getResources())
                .status(roomRequestDTO.getStatus()).build();

    }

    @Test
    void createRoom() {
        when(factory.createRoom(roomRequestDTO)).thenReturn(room);
        when(roomRepository.save(room)).thenReturn(room);
        when(requestConverter.toRoomResponseDTO(room)).thenReturn(roomResponseDTO);

        RoomResponseDTO result = roomService.createRoom(roomRequestDTO);

        assertEquals(roomResponseDTO, result);
        verify(factory, times(1)).createRoom(roomRequestDTO);
        verify(roomRepository, times(1)).save(room);
        verify(requestConverter, times(1)).toRoomResponseDTO(room);
        verifyNoMoreInteractions(factory, roomRepository, requestConverter);
    }
}