package com.oneandone.housemanaging.controller;

import com.oneandone.housemanaging.model.House;
import com.oneandone.housemanaging.service.HouseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest/house")
public class HouseController {

    private HouseService houseService;

    @Autowired
    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<House> getHouse(@RequestParam(value = "reverseOrder") boolean reverseOrder) {
        return houseService.getAllHouse(reverseOrder);
    }

    @GetMapping("/{id}")
    public House getHouseById(@PathVariable int id) {
        return houseService.getHouseById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public House createHouse(@Valid @RequestBody House house) {
        return houseService.createHouse(house);
    }

    @PutMapping("/{id}")
    public House updateHouse(@Valid @RequestBody House house, @PathVariable int id) {
        return houseService.updateHouse(house, id);
    }

    @DeleteMapping("/{id}")
    public void deleteHouse(@PathVariable int id) {
        houseService.deleteHouse(id);
    }
}
