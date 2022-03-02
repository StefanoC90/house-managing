package com.oneandone.housemanaging.repository;

import com.oneandone.housemanaging.entity.HouseEntity;
import org.springframework.data.repository.CrudRepository;

public interface HouseRepository extends CrudRepository<HouseEntity, Integer> {
}
