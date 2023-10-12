package com.app.demo.repository;

import com.app.demo.repository.entity.CarParkingAvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface CarParkingAvailabilityRepository extends JpaRepository<CarParkingAvailabilityEntity, Long> {

    List<CarParkingAvailabilityEntity> findAllByCodeIn(Collection<String> codes);

}
