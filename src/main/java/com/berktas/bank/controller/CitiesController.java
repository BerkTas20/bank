package com.berktas.bank.controller;

import com.berktas.bank.model.Cities;
import com.berktas.bank.service.CitiesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CitiesController {
    private final CitiesService citiesService;

    public CitiesController(CitiesService citiesService) {
        this.citiesService = citiesService;
    }

    @GetMapping
    public ResponseEntity<List<Cities>> getAllCities(){
        return ResponseEntity.ok(citiesService.getAllCities());
    }

    @PostMapping
    public ResponseEntity<Cities> createCities(@RequestBody Cities cities){
        return ResponseEntity.ok(citiesService.createCities(cities));
    }
}
