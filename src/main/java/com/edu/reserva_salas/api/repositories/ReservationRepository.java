package com.edu.reserva_salas.api.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.edu.reserva_salas.api.infrastructure.entity.Reservation;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String>{

}
