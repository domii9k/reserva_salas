package com.edu.reserva_salas.api.services;

import com.edu.reserva_salas.api.controllers.ReservationController;
import com.edu.reserva_salas.api.dto.mapper.RequestConverter;
import com.edu.reserva_salas.api.dto.mapper.ReservationMapper;
import com.edu.reserva_salas.api.dto.pagination.Pagination;
import com.edu.reserva_salas.api.dto.request.ReservationRequestDTO;
import com.edu.reserva_salas.api.dto.response.ReservationResponseDTO;
import com.edu.reserva_salas.api.infrastructure.entity.Reservation;
import com.edu.reserva_salas.api.infrastructure.entity.Room;
import com.edu.reserva_salas.api.infrastructure.entity.enums.RoomStatus;
import com.edu.reserva_salas.api.infrastructure.entity.factories.Factory;
import com.edu.reserva_salas.api.infrastructure.exceptions.NotFoundException;
import com.edu.reserva_salas.api.repositories.ReservationRepository;
import com.edu.reserva_salas.api.repositories.RoomRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Validated
public class ReservationService {

    @Autowired
    private final ReservationRepository reservationRepository;
    @Autowired
    private final RequestConverter requestConverter;
    @Autowired
    private final ReservationMapper reservationMapper;
    @Autowired
    private final RoomRepository roomRepository;
    @Autowired
    private final Factory factory;

    public ReservationService(ReservationRepository reservationRepository, RequestConverter requestConverter, ReservationMapper reservationMapper, RoomRepository roomRepository, Factory factory) {
        this.reservationRepository = reservationRepository;
        this.requestConverter = requestConverter;
        this.reservationMapper = reservationMapper;
        this.roomRepository = roomRepository;
        this.factory = factory;
    }

    public ReservationResponseDTO createReservation(ReservationRequestDTO reservationRequestDTO) {
        Reservation reservation = factory.reserveRoom(reservationRequestDTO);
        validateReservation(reservation);
        validateRoomForReservation(reservationRequestDTO);
        return requestConverter.toReservationResponseDTO(reservationRepository.save(reservation));
    }

    public Pagination<ReservationResponseDTO> findAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<Reservation> page = reservationRepository.findAll(PageRequest.of(pageNumber, pageSize, sort));
        List<ReservationResponseDTO> list = page.stream().map(reservation -> {
            ReservationResponseDTO reservationResponseDTO = requestConverter.toReservationResponseDTO(reservation);

            Link selfLink = linkTo(methodOn(ReservationController.class).findOne(reservation.getId())).withSelfRel();
            reservationResponseDTO.add(selfLink);
            return reservationResponseDTO;
        }).toList();
        return new Pagination<>(list, page.getTotalElements(), page.getTotalPages());
    }

    public ReservationResponseDTO findOneResponse(String id) {
        Reservation reservation = findById(id);
        return requestConverter.toReservationResponseDTO(reservation);
    }

    public ReservationResponseDTO updateReservation(@NotNull String id, @Valid ReservationRequestDTO reservationRequestDTO) {
        Reservation reservation = findById(id);
        BeanUtils.copyProperties(reservationRequestDTO, reservation);
        return requestConverter.toReservationResponseDTO(reservationRepository.save(reservation));
    }

    public void deleteReservation(@NotNull String id) {
        Reservation reservation = findById(id);
        reservationRepository.delete(reservation);
    }

    private Reservation findById(String id) {
        return reservationRepository.findById(id).orElseThrow(() -> new NotFoundException("Reservation not found."));
    }

    private void validateReservation(Reservation reservation) {
        List<Reservation> reservations = reservationRepository.getReservationDateBetween(reservation.getRoomId(), reservation.getReservationDate(), reservation.getReservationEndDate());

        for (Reservation existingReservation : reservations) {
            if (!reservations.isEmpty()) {
                throw new IllegalStateException("Can't reserve this room, its already reserved from " + existingReservation.getReservationDate() + " to " + existingReservation.getReservationEndDate());
            }
        }

    }

    // verifying if the room exists and if its activated
    private void validateRoomForReservation(ReservationRequestDTO reservation) {
        Optional<Room> room = roomRepository.findById(reservation.getRoomId());

        if (room.isPresent()) { // if it exists
            if (room.get().getStatus() == RoomStatus.INACTIVE) { // if its inactive
                throw new IllegalStateException("Can't reserve this room. It is Inactive from the system");
            }
        } else {
            throw new NotFoundException("Room not found.");
        }
    }



}
