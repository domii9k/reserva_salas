package com.edu.reserva_salas.api.controllers;

import com.edu.reserva_salas.api.dto.response.ReservationResponseDTO;
import com.edu.reserva_salas.api.dto.response.RoomResponseDTO;
import com.edu.reserva_salas.api.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    ReservationService reservationService;

    @GetMapping("/{id}")
    public ReservationResponseDTO findOne(@PathVariable String id){
        return reservationService.findOneResponse(id);
    }




}
