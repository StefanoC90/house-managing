package com.oneandone.housemanaging.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.oneandone.housemanaging.entity.HouseEntity;
import com.oneandone.housemanaging.model.House;
import com.oneandone.housemanaging.repository.HouseRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class HouseServiceTest {

    @InjectMocks
    private HouseService houseService;

    @Mock
    private HouseRepository houseRepository;

    @Test
    void getAllHouse() {
        HouseEntity houseEntity1 = new HouseEntity();
        houseEntity1.setId(1);
        houseEntity1.setOwnerName("John");
        houseEntity1.setSize(1);
        houseEntity1.setAddress("address");
        HouseEntity houseEntity2 = new HouseEntity();
        houseEntity2.setOwnerName("Anna");
        houseEntity2.setSize(2);
        houseEntity2.setAddress("address2");
        when(houseRepository.findAll()).thenReturn(List.of(houseEntity1, houseEntity2));

        List<House> houses = houseService.getAllHouse(false);

        assertThat(houses).hasSize(2);
    }

    @Test
    void getHouseById() {
        HouseEntity houseEntity1 = new HouseEntity();
        houseEntity1.setId(1);
        houseEntity1.setOwnerName("John");
        houseEntity1.setSize(1D);
        houseEntity1.setAddress("address");
        when(houseRepository.findById(1)).thenReturn(Optional.of(houseEntity1));

        House result = houseService.getHouseById(1);

        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getOwnerName()).isEqualTo("John");
        assertThat(result.getSize()).isEqualTo(1D);
        assertThat(result.getAddress()).isEqualTo("address");
    }

    @Test
    void getHouseByIdNotFound() {
        when(houseRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> houseService.getHouseById(1))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessage("404 NOT_FOUND \"House not found\"");
    }

    @Test
    void createHouse() {
        HouseEntity houseEntity1 = new HouseEntity();
        houseEntity1.setId(1);
        houseEntity1.setOwnerName("John");
        houseEntity1.setSize(1D);
        houseEntity1.setAddress("address");
        House house = new House();
        house.setId(1);
        house.setOwnerName("John");
        house.setSize(1D);
        house.setAddress("address");
        when(houseRepository.save(any())).thenReturn(houseEntity1);

        House result = houseService.createHouse(house);

        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getOwnerName()).isEqualTo("John");
        assertThat(result.getSize()).isEqualTo(1D);
        assertThat(result.getAddress()).isEqualTo("address");
    }

    @Test
    void updateHouse() {
        HouseEntity houseEntity1 = new HouseEntity();
        houseEntity1.setId(1);
        houseEntity1.setOwnerName("John");
        houseEntity1.setSize(1D);
        houseEntity1.setAddress("address");
        House house = new House();
        house.setId(1);
        house.setOwnerName("John");
        house.setSize(1D);
        house.setAddress("address");
        when(houseRepository.findById(1)).thenReturn(Optional.of(houseEntity1));
        when(houseRepository.save(any())).thenReturn(houseEntity1);

        House result = houseService.updateHouse(house, 1);

        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getOwnerName()).isEqualTo("John");
        assertThat(result.getSize()).isEqualTo(1D);
        assertThat(result.getAddress()).isEqualTo("address");
    }

    @Test
    void updateHouseNotFound() {
        when(houseRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> houseService.updateHouse(null, 1))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessage("404 NOT_FOUND \"House not found to update\"");
    }

    @Test
    void deleteHouse() {
        houseService.deleteHouse(1);

        verify(houseRepository).deleteById(1);
    }

    @Test
    void deleteHouseNotFound() {
        doThrow(EmptyResultDataAccessException.class).when(houseRepository).deleteById(1);

        assertThatThrownBy(() -> houseService.deleteHouse(1))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessage("404 NOT_FOUND \"House not found to delete\"");
    }
}
