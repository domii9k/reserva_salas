package com.edu.reserva_salas.api.services;

import java.util.List;

import com.edu.reserva_salas.api.infrastructure.entity.Room;
import com.edu.reserva_salas.api.infrastructure.entity.factories.Factory;
import com.edu.reserva_salas.api.infrastructure.entity.interfaces.RoomInterface;

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
public class RoomService {

    @Autowired
    private final RoomRepository roomRepository;
    @Autowired
    private final RoomMapper roomMapper;
    @Autowired
    private final RequestConverter requestConverter;
    @Autowired
    private final Factory factory;

    public RoomService(RoomRepository roomRepository, RoomMapper roomMapper, RequestConverter requestConverter, Factory factory) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
        this.requestConverter = requestConverter;
        this.factory = factory;
    }

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

    public RoomResponseDTO createRoom(RoomRequestDTO roomRequestDTO){
        Room room = factory.createRoom(roomRequestDTO);
        return requestConverter.toRoomResponseDTO(roomRepository.save(room));
    }




}
