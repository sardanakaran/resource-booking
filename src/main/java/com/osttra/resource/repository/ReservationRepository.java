package com.osttra.resource.repository;

import com.osttra.resource.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByEmployeeEmail(String employeeEmail);

    List<Reservation> findByDate(Date date);

}
