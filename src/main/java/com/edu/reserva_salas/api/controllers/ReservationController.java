package com.edu.reserva_salas.api.controllers;

import com.edu.reserva_salas.api.dto.pagination.Pagination;
import com.edu.reserva_salas.api.dto.request.ReservationRequestDTO;
import com.edu.reserva_salas.api.dto.response.ReservationResponseDTO;
import com.edu.reserva_salas.api.services.ReservationService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    ReservationService reservationService;

    @GetMapping
    public Pagination<ReservationResponseDTO> findAll(@RequestParam(name = "pag", defaultValue = "0") @PositiveOrZero int pageNumber,
                                                      @RequestParam(name = "size", defaultValue = "10") @Positive @Max(50) int pageSize,
                                                      @RequestParam(value = "sortBy", defaultValue = "reservationDate") String sortBy,
                                                      @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir) {
        return reservationService.findAll(pageNumber, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ReservationResponseDTO findOne(@PathVariable String id) {
        return reservationService.findOneResponse(id);
    }

    @PostMapping
    public ReservationResponseDTO create(@RequestBody ReservationRequestDTO reservationRequestDTO) {
        return reservationService.createReservation(reservationRequestDTO);
    }

    @PutMapping("/{id}")
    public ReservationResponseDTO update(@PathVariable String id, @RequestBody ReservationRequestDTO reservationRequestDTO) {
        return reservationService.updateReservation(id, reservationRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        reservationService.deleteReservation(id);
    }
}
