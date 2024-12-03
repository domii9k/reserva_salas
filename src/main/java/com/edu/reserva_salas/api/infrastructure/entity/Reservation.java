package com.edu.reserva_salas.api.infrastructure.entity;

//classe para criação de reserva de salas

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Document(collection = "reservation")
public class Reservation {
    @Id
    private String id;
    private String roomId;
    private String userId;
    private LocalDateTime reservationDate;
    private LocalDateTime reservationEndDate;

    protected  Reservation() {}


    

}
