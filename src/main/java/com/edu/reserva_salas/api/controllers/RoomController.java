package com.edu.reserva_salas.api.controllers;

import com.edu.reserva_salas.api.dto.request.RoomRequestDTO;
import com.edu.reserva_salas.api.infrastructure.entity.enums.RoomStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edu.reserva_salas.api.dto.pagination.Pagination;
import com.edu.reserva_salas.api.dto.response.RoomResponseDTO;
import com.edu.reserva_salas.api.services.RoomService;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    RoomService roomService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Pagination<RoomResponseDTO> findAll(
            @RequestParam(name = "pag", defaultValue = "0") @PositiveOrZero int pageNumber,
            @RequestParam(name = "size", defaultValue = "10") @Positive @Max(50) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "capacity") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
            @RequestParam(name = "status", defaultValue = "ACTIVE") RoomStatus status) {
        return roomService.getAllRooms(pageNumber, pageSize, sortBy, sortDir, status);

    }

    @GetMapping("/{id}")
    public RoomResponseDTO findOne(@PathVariable String id){
        return roomService.findOneResponse(id);
    }

    @PostMapping
    public RoomResponseDTO create(@RequestBody RoomRequestDTO roomRequestDTO){
        return roomService.createRoom(roomRequestDTO);
    }

    @PutMapping("/{id}")
    public RoomResponseDTO update (@PathVariable String id, @RequestBody RoomRequestDTO roomRequestDTO){
        return roomService.updateRoom(id, roomRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id){
        roomService.deleteRoom(id);
    }

    @PatchMapping("/disable/{id}")
    public ResponseEntity<String> deactivate(@PathVariable String id, @RequestParam RoomStatus statusParam){return roomService.deactivateRoom(id, statusParam);}

}
