package com.edu.reserva_salas.api.services;

import com.edu.reserva_salas.api.controllers.ReservationController;
import com.edu.reserva_salas.api.dto.mapper.RequestConverter;
import com.edu.reserva_salas.api.dto.pagination.Pagination;
import com.edu.reserva_salas.api.dto.request.ReservationRequestDTO;
import com.edu.reserva_salas.api.dto.response.ReservationResponseDTO;
import com.edu.reserva_salas.api.infrastructure.entity.*;
import com.edu.reserva_salas.api.infrastructure.entity.enums.RoomStatus;
import com.edu.reserva_salas.api.infrastructure.entity.factories.Factory;
import com.edu.reserva_salas.api.repositories.ReservationRepository;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @InjectMocks
    ReservationService reservationService;
    @Mock
    RequestConverter requestConverter;
    @Mock
    ReservationRepository reservationRepository;
    @Mock
    RoomRepository roomRepository;
    @Mock
    Factory factory;

    Room room;
    Reservation reservation;
    ReservationRequestDTO reservationRequestDTO;
    ReservationRequestDTO reservationRequestUpdateDTO;
    ReservationResponseDTO reservationResponseDTO;
    ReservationResponseDTO reservationUpdatedResponseDTO;

    @BeforeEach
    void setUp() {
        List<String> resources = List.of("resource 1", "resource 2");
        room = room = RoomBuilder.builder()
                .id(UUID.randomUUID().toString())
                .name("Meeting Room 12")
                .capacity(20)
                .resources(resources)
                .status(RoomStatus.ACTIVE).build();
        Users user = new Users(UUID.randomUUID().toString(), "Jonas", "jonas@gmail.com");

        //creating new reservation
        reservationRequestDTO = new ReservationRequestDTO(room.getId(), user.getId(), LocalDateTime.now(), LocalDateTime.of(2024,11,13,15,45));
        reservation = ReservationBuilder.builder()
                .id(UUID.randomUUID().toString())
                .userId(reservationRequestDTO.getUserId())
                .roomId(reservationRequestDTO.getRoomId())
                .startDate(reservationRequestDTO.getReservationDate())
                .endDate(reservationRequestDTO.getReservationEndDate())
                .build();
        reservationResponseDTO = new ReservationResponseDTO(reservation.getId(), reservation.getRoomId(), reservation.getUserId(), reservation.getReservationDate(), reservation.getReservationEndDate());

        //to update a reservation
        reservationRequestUpdateDTO = new ReservationRequestDTO(room.getId(), user.getId(), LocalDateTime.now(), LocalDateTime.of(2024, 11, 13, 15, 30));
        reservationUpdatedResponseDTO = new ReservationResponseDTO(reservation.getId(), reservationRequestUpdateDTO.getRoomId(), reservationRequestUpdateDTO.getUserId(), reservationRequestUpdateDTO.getReservationDate(), reservationRequestUpdateDTO.getReservationEndDate());
    }

    @Test
    @DisplayName("Should create and return a new reservation")
    void createReservation_ShouldCreateAndReturnANewReservation() {
        when(roomRepository.findById(reservationRequestDTO.getRoomId())).thenReturn(Optional.of(room));
        when(factory.reserveRoom(reservationRequestDTO)).thenReturn(reservation);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(requestConverter.toReservationResponseDTO(reservation)).thenReturn(reservationResponseDTO);

        ReservationResponseDTO result = reservationService.createReservation(reservationRequestDTO);

        assertEquals(reservationResponseDTO, result);
        assertEquals(room.getId(), reservationRequestDTO.getRoomId(), "IDs da sala não coincidem");
        verify(reservationRepository, times(1)).getReservationDateBetween(reservationRequestDTO.getRoomId(), reservation.getReservationDate(), reservation.getReservationEndDate());
        verify(roomRepository, times(1)).findById(reservation.getRoomId());
        verify(factory, times(1)).reserveRoom(reservationRequestDTO);
        verify(reservationRepository, times(1)).save(reservation);
        verify(requestConverter, times(1)).toReservationResponseDTO(reservation);
        verifyNoMoreInteractions(reservationRepository, requestConverter);
    }

    @Test
    @DisplayName("Should find and return one reservation registered")
    void findAll_ShouldFindOneReservation() {

        //filters
        int pageNumber = 0;
        int pageSize = 1;
        String sortBy = "reservationDate";
        String sortDir = "ASC";
        char status = 'A';

        Page<Reservation> reservationPage = new PageImpl<>(List.of(reservation));

        when(reservationRepository.findAll(any(PageRequest.class))).thenReturn(reservationPage);
        when(requestConverter.toReservationResponseDTO(reservation)).thenReturn(reservationResponseDTO);

        Pagination<ReservationResponseDTO> result = reservationService.findAll(pageNumber, pageSize, sortBy, sortDir);

        assertNotNull(result); //assert that the result is not null
        assertEquals(reservationPage.getSize(), result.list().size());

        String expectedLink = linkTo(methodOn(ReservationController.class).findOne(reservation.getId())).withSelfRel().getHref();
        assertEquals(expectedLink, result.list().get(0).getLink("self").get().getHref());
    }

    @Test
    @DisplayName("Should find and return one reservation by its ID")
    void findOneResponse_ShouldFindAndReturnOneReservation() {
        when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));
        when(requestConverter.toReservationResponseDTO(reservation)).thenReturn(reservationResponseDTO);

        ReservationResponseDTO result = reservationService.findOneResponse(reservation.getId());

        assertEquals(reservationResponseDTO, result);
        verify(reservationRepository, times(1)).findById(reservation.getId());
        verify(requestConverter, times(1)).toReservationResponseDTO(reservation);
    }

    @Test
    @DisplayName("Should update and return an existing reservation")
    void updateReservation_ShouldUpdateAndReturnAnExistingReservation() {
        when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(requestConverter.toReservationResponseDTO(reservation)).thenReturn(reservationUpdatedResponseDTO);

        ReservationResponseDTO result = reservationService.updateReservation(reservation.getId(), reservationRequestUpdateDTO);

        assertEquals(reservationUpdatedResponseDTO, result);
        verify(reservationRepository, times(1)).findById(reservation.getId());
        verify(reservationRepository, times(1)).save(reservation);
        verify(requestConverter, times(1)).toReservationResponseDTO(reservation);
        verifyNoMoreInteractions(reservationRepository, requestConverter);
    }

    @Test
    @DisplayName("Should delete a reservation and return nothing")
    void deleteReservation() {
        when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));

        reservationService.deleteReservation(reservation.getId());

        verify(reservationRepository, times(1)).findById(reservation.getId());
        verify(reservationRepository, times(1)).delete(reservation);
        verifyNoMoreInteractions(reservationRepository);
    }

}
