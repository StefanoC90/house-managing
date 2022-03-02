package com.oneandone.housemanaging.mapper;

import com.oneandone.housemanaging.entity.HouseEntity;
import com.oneandone.housemanaging.model.House;
import org.springframework.stereotype.Component;

@Component
public final class HouseMapper {
    public static HouseEntity toEntity(House house) {
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setId(house.getId());
        houseEntity.setOwnerName(house.getOwnerName());
        houseEntity.setSize(house.getSize());
        houseEntity.setAddress(house.getAddress());
        return houseEntity;
    }

    public static House toModel(HouseEntity houseEntity) {
        House house = new House();
        house.setId(houseEntity.getId());
        house.setOwnerName(houseEntity.getOwnerName());
        house.setSize(houseEntity.getSize());
        house.setAddress(houseEntity.getAddress());
        return house;
    }
}
