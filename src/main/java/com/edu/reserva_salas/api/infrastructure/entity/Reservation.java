package com.edu.reserva_salas.api.infrastructure.entity;

//classe para criação de reserva de salas

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "reservation")
public class Reservation {

    @Id
    private String id;
    private String roomId;
    private String userId;
    private LocalDate reservationDate;
    private LocalDate reservationEndDate;

    protected  Reservation() {}


    

}
