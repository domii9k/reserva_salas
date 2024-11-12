package com.edu.reserva_salas.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.edu.reserva_salas.api.dto.pagination.Pagination;
import com.edu.reserva_salas.api.dto.response.RoomResponseDTO;
import com.edu.reserva_salas.api.services.RoomService;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("/rooms")
public class RoomControllerTest {

    @Autowired
    RoomService roomService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Pagination<RoomResponseDTO> findAll(
            @RequestParam(name = "pag", defaultValue = "0") @PositiveOrZero int pageNumber,
            @RequestParam(name = "size", defaultValue = "10") @Positive @Max(50) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "capacity") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
            @RequestParam(name = "status", defaultValue = "A") char status) {
        return roomService.getAllRooms(pageNumber, pageSize, sortBy, sortDir, status);

    }


}
