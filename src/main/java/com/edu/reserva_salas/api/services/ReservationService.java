package com.edu.reserva_salas.api.services;

import com.edu.reserva_salas.api.dto.mapper.RequestConverter;
import com.edu.reserva_salas.api.dto.mapper.ReservationMapper;
import com.edu.reserva_salas.api.dto.request.ReservationRequestDTO;
import com.edu.reserva_salas.api.dto.response.ReservationResponseDTO;
import com.edu.reserva_salas.api.infrastructure.entity.Reservation;
import com.edu.reserva_salas.api.infrastructure.entity.factories.Factory;
import com.edu.reserva_salas.api.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ReservationService{

    @Autowired
    private final ReservationRepository reservationRepository;
    @Autowired
    private final ReservationMapper reservationMapper;
    @Autowired
    private final RequestConverter requestConverter;
    @Autowired
    private final Factory factory;

    public ReservationService(ReservationRepository reservationRepository, ReservationMapper reservationMapper, RequestConverter requestConverter, Factory factory) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.requestConverter = requestConverter;
        this.factory = factory;
    }



    public ReservationResponseDTO createReservation(ReservationRequestDTO reservationRequestDTO){
        Reservation reservation = factory.reserveRoom(reservationRequestDTO);
        return requestConverter.toReservationResponseDTO(reservationRepository.save(reservation));
    }
}
