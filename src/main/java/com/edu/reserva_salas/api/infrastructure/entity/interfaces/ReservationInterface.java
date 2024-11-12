package com.edu.reserva_salas.api.infrastructure.entity.interfaces;

import com.edu.reserva_salas.api.dto.request.ReservationRequestDTO;
import com.edu.reserva_salas.api.dto.response.ReservationResponseDTO;
import com.edu.reserva_salas.api.infrastructure.entity.Reservation;

public interface ReservationInterface{
    Reservation reserveRoom(ReservationRequestDTO reservationRequestDTO);
}
