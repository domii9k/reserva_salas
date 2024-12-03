package com.edu.reserva_salas.api.dto.mapper;

import com.edu.reserva_salas.api.dto.request.ReservationRequestDTO;
import com.edu.reserva_salas.api.infrastructure.entity.Reservation;
import com.edu.reserva_salas.api.infrastructure.entity.ReservationBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservationMapperTest {

    private final ReservationMapper reservationMapper = new ReservationMapper();

    @Test
    @DisplayName("Should convert the dto to reservation")
    void toReservation_ShouldConvertDtoToReservation() {
        String userId = "user123";
        String roomId = "room456";
        ReservationRequestDTO reservationRequestDTO = ReservationRequestDTO.builder()
                .reservationDate(LocalDateTime.of(2024, 12, 1, 10, 0))
                .reservationEndDate(LocalDateTime.of(2024, 12, 1, 12, 0))
                .build();

        Reservation reservation = reservationMapper.toReservation(reservationRequestDTO, userId, roomId);

        assertNotNull(reservation);
        assertNotNull(reservation.getId());
        assertEquals(userId, reservation.getUserId());
        assertEquals(roomId, reservation.getRoomId());
        assertEquals(reservationRequestDTO.getReservationDate(), reservation.getReservationDate());
        assertEquals(reservationRequestDTO.getReservationEndDate(), reservation.getReservationEndDate());

    }

    @Test
    @DisplayName("Should convert the reservation to dto")
    void toReservationRequestDTO_ShouldConvertReservation() {
        Reservation reservation = ReservationBuilder.builder()
                .id("reservation789")
                .userId("user123")
                .roomId("room456")
                .startDate(LocalDateTime.of(2024, 12, 1, 10, 0))
                .endDate(LocalDateTime.of(2024, 12, 1, 12, 0))
                .build();

        ReservationRequestDTO reservationRequestDTO = reservationMapper.toReservationRequestDTO(reservation);

        assertNotNull(reservationRequestDTO);
        assertEquals(reservation.getUserId(), reservationRequestDTO.getUserId());
        assertEquals(reservation.getRoomId(), reservationRequestDTO.getRoomId());
        assertEquals(reservation.getReservationDate(), reservationRequestDTO.getReservationDate());
        assertEquals(reservation.getReservationEndDate(), reservationRequestDTO.getReservationEndDate());

    }
}