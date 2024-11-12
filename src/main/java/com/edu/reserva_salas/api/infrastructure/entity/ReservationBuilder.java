package com.edu.reserva_salas.api.infrastructure.entity;

import org.bson.types.ObjectId;

import java.time.LocalDate;

//classe builder para instancia de reservas
public class ReservationBuilder {

    private Reservation reservation;

    public static ReservationBuilder builder() {
        ReservationBuilder builder = new ReservationBuilder();
        builder.reservation = new Reservation();
        return builder;

    }

    public ReservationBuilder id(String id) {
        this.reservation.setId(id);
        return this;
    }

    public ReservationBuilder userId(String userId) {
        this.reservation.setUserId(userId);
        return this;
    }

    public ReservationBuilder roomId(String roomId) {
        this.reservation.setRoomId(roomId);
        return this;
    }

    public ReservationBuilder startDate(LocalDate startDate) {
        this.reservation.setReservationDate(startDate);
        return this;
    }

    public ReservationBuilder endDate(LocalDate endDate) {
        this.reservation.setReservationEndDate(endDate);
        return this;
    }

    public Reservation build() {
        return this.reservation;
    }

}
