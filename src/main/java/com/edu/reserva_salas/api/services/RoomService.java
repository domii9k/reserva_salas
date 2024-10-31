package com.edu.reserva_salas.api.services;

import java.util.List;

import com.edu.reserva_salas.api.dto.mapper.ReservationMapper;
import com.edu.reserva_salas.api.infrastructure.entity.Reservation;
import com.edu.reserva_salas.api.infrastructure.entity.Room;
import com.edu.reserva_salas.api.infrastructure.entity.builders.ReservationBuilder;
import com.edu.reserva_salas.api.infrastructure.entity.builders.RoomBuilder;
import com.edu.reserva_salas.api.infrastructure.entity.factories.RoomsFactory;
import com.edu.reserva_salas.api.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;

import com.edu.reserva_salas.api.dto.mapper.RequestConverter;
import com.edu.reserva_salas.api.dto.mapper.RoomMapper;
import com.edu.reserva_salas.api.dto.pagination.Pagination;
import com.edu.reserva_salas.api.dto.request.RoomRequestDTO;
import com.edu.reserva_salas.api.dto.response.RoomResponseDTO;
import com.edu.reserva_salas.api.repositories.RoomRepository;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Service
@Validated
public class RoomService implements CreateRoomReservationService{

    @Autowired
    private final RoomRepository roomRepository;
    @Autowired
    private final ReservationRepository reservationRepository;
    @Autowired
    private final RoomMapper roomMapper;
    @Autowired
    private final RequestConverter requestConverter;
    @Autowired
    private final ReservationMapper reservationMapper;

    public RoomService(RoomRepository roomRepository, ReservationRepository reservationRepository, RoomMapper roomMapper, RequestConverter requestConverter, ReservationMapper reservationMapper) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.roomMapper = roomMapper;
        this.requestConverter = requestConverter;
        this.reservationMapper = reservationMapper;
    }

    // salva uma nova sala
    public RoomResponseDTO createRoom(RoomRequestDTO roomRequest) {
        Room room = roomRepository.save(roomMapper.toRoom(roomRequest));
        return requestConverter.toRoomResponseDTO(room);
    }

    /*public Object createOrReserve(RoomsFactory.RoomType roomType) {
        Object builder = RoomsFactory.createOrReserve(roomType);

        if (builder instanceof RoomBuilder) {
            Room room = roomRepository.save(((RoomBuilder) builder).build());
            return requestConverter.toRoomResponseDTO(room);
        }
        if (builder instanceof ReservationBuilder) {
            Reservation reservation = reservationRepository.save(((ReservationBuilder) builder).build());
            return requestConverter.toReservationResponseDTO(reservation);
        }
        return "Deu errado";
    }*/

    // retorna uma lista de salas
    public Pagination<RoomResponseDTO> getAllRooms(
            @RequestParam(name = "pag", defaultValue = "0") @PositiveOrZero int pageNumber,
            @RequestParam(name = "size", defaultValue = "10") @Positive @Max(50) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "capacity") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
            @RequestParam(name = "status", defaultValue = "true") char status) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<Room> page = roomRepository.findAllByStatus(status, PageRequest.of(pageNumber, pageSize, sort));
        List<RoomResponseDTO> list = page.stream().map(requestConverter::toRoomResponseDTO).toList();
        return new Pagination<>(list, page.getTotalElements(), page.getTotalPages());

    }

    @Override
    public Object create() {
        return null;
    }
}
