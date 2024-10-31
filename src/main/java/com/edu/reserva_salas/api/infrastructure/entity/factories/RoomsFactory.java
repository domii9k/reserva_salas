package com.edu.reserva_salas.api.infrastructure.entity.factories;

import com.edu.reserva_salas.api.infrastructure.entity.builders.ReservationBuilder;
import com.edu.reserva_salas.api.infrastructure.entity.builders.RoomBuilder;
import com.edu.reserva_salas.api.infrastructure.entity.enums.RequestTypeRoom;
import com.edu.reserva_salas.api.services.ReservationService;
import com.edu.reserva_salas.api.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

//classe factory para escolha do que criar
@Component
public class RoomsFactory {

    private final RoomService roomService;
    private final ReservationService reservationService;

    @Autowired
    public RoomsFactory(RoomService roomService, ReservationService reservationService) {
        this.roomService = roomService;
        this.reservationService = reservationService;
    }

    public Object createOrReserve(RequestTypeRoom type) throws ClassNotFoundException {
        return switch (type) {
            case NEWROOM -> roomService;
            case RESERVE -> reservationService;
            default ->
                    throw new ClassNotFoundException(MessageFormat.format("Tipo de requisição não existente.", type));
        };
    }

}
