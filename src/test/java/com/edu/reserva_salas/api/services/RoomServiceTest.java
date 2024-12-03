package com.edu.reserva_salas.api.services;

import com.edu.reserva_salas.api.controllers.RoomController;
import com.edu.reserva_salas.api.dto.mapper.RequestConverter;
import com.edu.reserva_salas.api.dto.mapper.RoomMapper;
import com.edu.reserva_salas.api.dto.pagination.Pagination;
import com.edu.reserva_salas.api.dto.request.RoomRequestDTO;
import com.edu.reserva_salas.api.dto.response.RoomResponseDTO;
import com.edu.reserva_salas.api.infrastructure.entity.Room;
import com.edu.reserva_salas.api.infrastructure.entity.RoomBuilder;
import com.edu.reserva_salas.api.infrastructure.entity.enums.RoomStatus;
import com.edu.reserva_salas.api.infrastructure.entity.factories.Factory;
import com.edu.reserva_salas.api.repositories.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//Tests with JUnit5, Mockito and Jacoco
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
    RoomRequestDTO updatedRoom;
    RoomResponseDTO updatedRoomResponseDTO;

    @BeforeEach
    void setUp() {
        List<String> resources = List.of("resource 1", "resource 2");
        //actual
        roomRequestDTO = new RoomRequestDTO("Nome", 23, resources);

        room = RoomBuilder.builder()
                .id(UUID.randomUUID().toString())
                .name(roomRequestDTO.getName())
                .capacity(roomRequestDTO.getCapacity())
                .resources(roomRequestDTO.getResources())
                .status(RoomStatus.ACTIVE).build();

        roomResponseDTO = new RoomResponseDTO(room.getId(), roomRequestDTO.getName(), roomRequestDTO.getCapacity(), room.getResources(), room.getStatus());

        //update tests
        updatedRoom = new RoomRequestDTO("Updated", 23, resources);
        updatedRoomResponseDTO = new RoomResponseDTO(room.getId(), updatedRoom.getName(), updatedRoom.getCapacity(), updatedRoom.getResources(), room.getStatus());
    }

    @Test
    @DisplayName("Should create and return a new Room")
    void createRoom_ShouldCreateANewRoom() {
        when(factory.createRoom(roomRequestDTO)).thenReturn(room);
        when(roomRepository.save(room)).thenReturn(room);
        when(requestConverter.toRoomResponseDTO(room)).thenReturn(roomResponseDTO);

        RoomResponseDTO result = roomService.createRoom(roomRequestDTO);

        assertEquals(roomResponseDTO, result);
        verify(roomRepository, times(1)).findByName(room.getName());
        verify(factory, times(1)).createRoom(roomRequestDTO);
        verify(roomRepository, times(1)).save(room);
        verify(requestConverter, times(1)).toRoomResponseDTO(room);
        verifyNoMoreInteractions(factory, roomRepository, requestConverter);
    }

    @Test
    @DisplayName("Should update and return an existing room")
    void updateRoom_ShouldUpdateAnExistingRoom() {
        when(roomRepository.findById(room.getId())).thenReturn(Optional.of(room));
        when(roomRepository.save(room)).thenReturn(room);
        when(requestConverter.toRoomResponseDTO(room)).thenReturn(updatedRoomResponseDTO);

        RoomResponseDTO result = roomService.updateRoom(room.getId(), updatedRoom);

        assertEquals(updatedRoomResponseDTO, result);
        assertEquals(result.getName(), room.getName());
        verify(roomRepository, times(1)).findById(room.getId());
        verify(roomRepository, times(1)).save(room);
        verify(requestConverter, times(1)).toRoomResponseDTO(room);
        verifyNoMoreInteractions(roomRepository, requestConverter);
    }

    @Test
    @DisplayName("Should delete a room and return nothing")
    void deleteRoom_ShouldDeleteARoom() {
        when(roomRepository.findById(room.getId())).thenReturn(Optional.of(room));

        roomService.deleteRoom(room.getId());

        verify(roomRepository, times(1)).delete(room);
        verify(roomRepository, times(1)).findById(room.getId());
        verifyNoMoreInteractions(roomRepository);
    }

    @Test
    @DisplayName("Should return a pagination list of rooms")
    void getAllRooms_ShouldReturnAPaginationListOfRooms() {

        //filters
        int pageNumber=0;
        int pageSize=1;
        String sortBy="name";
        String sortDir="ASC";

        Page<Room> roomPage = new PageImpl<>(List.of(room));

        when(roomRepository.findAllByStatus(eq(RoomStatus.ACTIVE), any(PageRequest.class))).thenReturn(roomPage);
        when(requestConverter.toRoomResponseDTO(room)).thenReturn(roomResponseDTO);

        Pagination<RoomResponseDTO> result = roomService.getAllRooms(pageNumber, pageSize,sortBy, sortDir,RoomStatus.ACTIVE);

        assertNotNull(result); //assert that the result is not null
        assertEquals(roomPage.getSize(), result.list().size());  //compares the size of roomPage and the size of result

        String expectedLink = linkTo(methodOn(RoomController.class).findOne(room.getId().toString())).withSelfRel().getHref();
        assertEquals(expectedLink, result.list().get(0).getLink("self").get().getHref()); // compares the expected link to the link that was return
    }

    @Test
    @DisplayName("Should find a room and convert it to DTO")
    void findOneResponse_ShouldConvertItToDTO() {
        when(roomRepository.findById(room.getId())).thenReturn(Optional.of(room));
        when(requestConverter.toRoomResponseDTO(room)).thenReturn(roomResponseDTO);

        RoomResponseDTO response = roomService.findOneResponse(room.getId());

        assertEquals(roomResponseDTO, response);
        verify(roomRepository, times(1)).findById(room.getId());
        verify(requestConverter, times(1)).toRoomResponseDTO(room);
        verifyNoMoreInteractions(roomRepository, requestConverter);

    }

    @Test
    @DisplayName("Should deactivate or activate a room and return status OK")
    void deactivateRoom_ShouldDeactivateARoomWithStatusOK() {
        when(roomRepository.findById(room.getId())).thenReturn(Optional.of(room));
        when(roomRepository.save(room)).thenReturn(room);

        ResponseEntity<String> response = roomService.deactivateRoom(room.getId(), RoomStatus.INACTIVE);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.toString(), response.getStatusCode().toString());
        verify(roomRepository, times(1)).findById(room.getId());
        verify(roomRepository, times(1)).save(room);
        assertEquals(RoomStatus.INACTIVE, room.getStatus());
        assertEquals("Room successfully deactivated.", response.getBody());
    }





    }