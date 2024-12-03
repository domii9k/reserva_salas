package com.edu.reserva_salas.api.repositories;

import com.edu.reserva_salas.api.infrastructure.entity.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.edu.reserva_salas.api.infrastructure.entity.Reservation;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String>{

    //a custom query to find if the room its already reserved.
    @Query("{'roomId': ?0, $and: [ { 'reservationDate': { $lte: ?2 } }, { 'reservationEndDate': { $gte: ?1 } } ]}")
    List<Reservation> getReservationDateBetween(String id, LocalDateTime reservationDate, LocalDateTime reservationEndDate);

}
