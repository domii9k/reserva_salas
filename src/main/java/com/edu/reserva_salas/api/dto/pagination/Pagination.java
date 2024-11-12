package com.edu.reserva_salas.api.dto.pagination;

import  java.util.List;

public record Pagination<T>(List<T> list, Long totalRecords, int totalPages) {

}
