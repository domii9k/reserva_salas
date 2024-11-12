package com.edu.reserva_salas.api.services;

import java.util.List;
import java.util.NoSuchElementException;

import com.edu.reserva_salas.api.controllers.RoomController;
import com.edu.reserva_salas.api.infrastructure.entity.Room;
import com.edu.reserva_salas.api.infrastructure.entity.factories.Factory;

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

import com.edu.reserva_salas.api.dto.mapper.RequestConverter;
import com.edu.reserva_salas.api.dto.mapper.RoomMapper;
import com.edu.reserva_salas.api.dto.pagination.Pagination;
import com.edu.reserva_salas.api.dto.request.RoomRequestDTO;
import com.edu.reserva_salas.api.dto.response.RoomResponseDTO;
import com.edu.reserva_salas.api.repositories.RoomRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Validated
public class RoomService {

    @Autowired
    private final RoomRepository roomRepository;
    @Autowired
    private final RequestConverter requestConverter;
    @Autowired
    private final Factory factory;

    public RoomService(RoomRepository roomRepository, RequestConverter requestConverter, Factory factory) {
        this.roomRepository = roomRepository;
        this.requestConverter = requestConverter;
        this.factory = factory;
    }

    public Pagination<RoomResponseDTO> getAllRooms(int pageNumber, int pageSize, String sortBy, String sortDir, char status) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<Room> page = roomRepository.findAllByStatus(status, PageRequest.of(pageNumber, pageSize, sort));

        List<RoomResponseDTO> list = page.stream().map(room -> {
            RoomResponseDTO roomResponseDTO = requestConverter.toRoomResponseDTO(room);

            //adding heteoas link
            Link selfLink = linkTo(methodOn(RoomController.class).findOne(room.getId())).withSelfRel();

            roomResponseDTO.add(selfLink);
            return roomResponseDTO;
        }).toList();
        return new Pagination<>(list, page.getTotalElements(), page.getTotalPages());

    }

    public RoomResponseDTO findOneResponse(@NotNull String id) {
        Room room = findById(id);
        return requestConverter.toRoomResponseDTO(room);
    }

    public RoomResponseDTO createRoom(@Valid RoomRequestDTO roomRequestDTO) {
        Room room = factory.createRoom(roomRequestDTO);
        return requestConverter.toRoomResponseDTO(roomRepository.save(room));
    }

    public RoomResponseDTO updateRoom(@NotNull String id, @Valid RoomRequestDTO roomRequestDTO){
        Room room = findById(id);
        BeanUtils.copyProperties(roomRequestDTO, room);
        return requestConverter.toRoomResponseDTO(roomRepository.save(room));
    }

    public void deleteRoom(@NotNull String id){
        Room room = findById(id);
        roomRepository.delete(room);
    }

    private Room findById(String id) {
        return roomRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Objeto nao encontrado"));
    }


}
