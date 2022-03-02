package com.oneandone.housemanaging.service;

import com.oneandone.housemanaging.entity.HouseEntity;
import com.oneandone.housemanaging.mapper.HouseMapper;
import com.oneandone.housemanaging.model.House;
import com.oneandone.housemanaging.repository.HouseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class HouseService {

    private HouseRepository houseRepository;

    @Autowired
    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public List<House> getAllHouse(Boolean reverseOrder) {
        Iterable<HouseEntity> houseEntityIterable = houseRepository.findAll();
        List<HouseEntity> houseList = StreamSupport.stream(houseEntityIterable.spliterator(), false)
                .collect(Collectors.toList());

        if (reverseOrder) {
            return houseList.stream()
                    .map(HouseMapper::toModel)
                    .sorted((h1, h2) -> h2.getId() - h1.getId())
                    .collect(Collectors.toList());
        }

        return houseList.stream()
                .map(HouseMapper::toModel)
                .collect(Collectors.toList());
    }

    public House getHouseById(int id) {
        return houseRepository.findById(id)
                .map(HouseMapper::toModel)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "House not found"));
    }

    public House createHouse(House house) {
        HouseEntity houseEntity = HouseMapper.toEntity(house);
        HouseEntity houseEntityPersisted = houseRepository.save(houseEntity);
        return HouseMapper.toModel(houseEntityPersisted);
    }

    public House updateHouse(House house, int id) {
        houseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "House not found to update"));

        HouseEntity houseEntity = HouseMapper.toEntity(house);
        houseEntity.setId(id);
        HouseEntity houseEntityPersisted = houseRepository.save(houseEntity);
        return HouseMapper.toModel(houseEntityPersisted);
    }

    public void deleteHouse(int id) {
        try {
            houseRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "House not found to delete");
        }
    }
}
