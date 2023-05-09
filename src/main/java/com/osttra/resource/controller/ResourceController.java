package com.osttra.resource.controller;

import com.osttra.resource.model.Resource;
import com.osttra.resource.repository.ReservationRepository;
import com.osttra.resource.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ResourceController {

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    ReservationRepository reservationResource;

    @Autowired
    EntityManager entityManager;


    enum TYPE {AVAILABLE, RESERVED, ALL}

    ;

    @GetMapping("/seatsByDate")
    public ResponseEntity<List<Resource>> getSeats(@RequestParam String date, @RequestParam(defaultValue = "AVAILABLE") TYPE type) {
        try {
            List<Resource> resources = Collections.emptyList();
            switch (type){
                case AVAILABLE:
                    resources = entityManager.createQuery("select r from Resource r where not exists (" +
                            "select 1 from Reservation res where res.resource = r " +
                            "and res.date = :date)", Resource.class)
                            .setParameter("date", new SimpleDateFormat("yyyy-MM-dd").parse(date))
                            .getResultList();
                    break;
                case RESERVED:
                    resources = entityManager.createQuery("select r from Resource r where exists (" +
                            "select 1 from Reservation res where res.resource = r " +
                            "and res.date = :date)", Resource.class)
                            .setParameter("date", new SimpleDateFormat("yyyy-MM-dd").parse(date))
                            .getResultList();
                    break;
                case ALL:
                    resources = getResources(null);

            }

            return ResponseEntity.ok(resources);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/seats")
    public ResponseEntity<List<Resource>> getAllSeatResources(@RequestParam(required = false) String seatName) {
        try {
            List<Resource> seats = getResources(seatName);

            if (seats.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(seats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<Resource> getResources(@RequestParam(required = false) String seatName) {
        List<Resource> seats = new ArrayList<Resource>();

        if (seatName == null)
            resourceRepository.findAll().forEach(seats::add);
        else
            resourceRepository.findByNameContaining(seatName).forEach(seats::add);
        return seats;
    }

    @GetMapping("/seats/{id}")
    public ResponseEntity<Resource> getSeatResourceById(@PathVariable("id") long id) {
        Optional<Resource> seatData = resourceRepository.findById(id);

        if (seatData.isPresent()) {
            return new ResponseEntity<>(seatData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/seats")
    public ResponseEntity<Resource> createSeatResource(@RequestBody Resource seat) {
        try {
            Resource _seat = resourceRepository
                    .save(new Resource(seat.getName(), seat.getDescription(), true));
            return new ResponseEntity<>(_seat, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/seats/{id}")
    public ResponseEntity<Resource> updateSeatResource(@PathVariable("id") long id, @RequestBody Resource seat) {
        Optional<Resource> seatData = resourceRepository.findById(id);

        if (seatData.isPresent()) {
            Resource _seat = seatData.get();
            _seat.setName(seat.getName());
            _seat.setDescription(seat.getDescription());
            _seat.setDocStationEnabled(seat.isDocStationEnabled());
            return new ResponseEntity<>(resourceRepository.save(_seat), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/seats/{id}")
    public ResponseEntity<HttpStatus> deleteSeatResource(@PathVariable("id") long id) {
        try {
            resourceRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/seats")
    public ResponseEntity<HttpStatus> deleteAllSeatResources() {
        try {
            resourceRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/seats/docStationEnabled")
    public ResponseEntity<List<Resource>> findBydocStationEnabled() {
        try {
            List<Resource> seats = resourceRepository.findByDocStationEnabled(true);

            if (seats.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(seats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
