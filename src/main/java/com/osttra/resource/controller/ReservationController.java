package com.osttra.resource.controller;

import com.osttra.resource.model.Reservation;
import com.osttra.resource.model.ReservationRequest;
import com.osttra.resource.model.Resource;
import com.osttra.resource.repository.ReservationRepository;
import com.osttra.resource.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ReservationController {

    @Autowired
    ReservationRepository reservationResource;

    @Autowired
    private ResourceRepository resourceRepository;


    @GetMapping("/ping")
    public ResponseEntity<List<Reservation>> ping() {
        try {
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/reservation/date/{date}")
    public ResponseEntity<List<Reservation>> getReservationsByDate(@PathVariable("date") String date) {
        try {
            List<Reservation> reservations;
            reservations = reservationResource.findByDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reservation/user/{emailId}")
    public ResponseEntity<List<Reservation>> getReservationsByEmail(@PathVariable("emailId") String email) {

        List<Reservation> reservationList = reservationResource.findByEmployeeEmail(email);
        return new ResponseEntity<>(reservationList, HttpStatus.OK);
    }

    @PostMapping("/reservation")
    public ResponseEntity<Reservation> reserve(@RequestBody ReservationRequest reservationRequest) {
        try {
            Optional<Resource> byId = resourceRepository.findById(reservationRequest.getResourceId());
            if (!byId.isPresent())
                return ResponseEntity.badRequest().build();
            Reservation booking = reservationResource.save(new Reservation(reservationRequest.getDate(),
                    reservationRequest.getEmployeeEmail(), reservationRequest.getEmployeeName(), byId.get()));
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/reservation")
    public ResponseEntity<Resource> release(@RequestParam Long reservationId) {
        try {
            reservationResource.deleteById(reservationId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
