package com.app.demo.repository;

import com.app.demo.model.dto.CarParkInformationDTO;
import com.app.demo.repository.entity.CarParkEntity;
import com.app.demo.repository.view.CarParkInformationView;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarParkRepository extends JpaRepository<CarParkEntity, Long>, JpaSpecificationExecutor<CarParkEntity> {

    Optional<CarParkEntity> findByCarParkNo(String carParkNo);

    @Query(value = "SELECT ST_Distance(CAST (coordinate AS geometry) , CAST (:coordinate AS geometry)) distance, " +
            "\taddress,\n" +
            "\tlatitude,\n" +
            "\tlongitude,\n" +
            "\ttotal_lots as totalLots,\n" +
            "\tavailable_lots as availableLots\n" +
            "FROM (SELECT\n" +
            "\ttcpi.address,\n" +
            "\ttcpi.latitude As latitude,\n" +
            "\ttcpi.longitude AS longitude,\n" +
            "\ttcpi.coordinate,\n" +
            "\tSUM(tcpa.total_lots) total_lots,\n" +
            "\tSUM(tcpa.lots_available) available_lots\n" +
            "FROM\n" +
            "\ttbl_car_park_information tcpi\n" +
            "LEFT JOIN tbl_car_parking_availability tcpa ON\n" +
            "\ttcpa.car_park_id = tcpi.id\n" +
            "GROUP BY\n" +
            "\ttcpi.address,\n" +
            "\tlatitude,\n" +
            "\tlongitude,\n" +
            "\tcoordinate) order by distance",
            countQuery = "select count(1) from tbl_car_park_information",
            nativeQuery = true)
    Page<CarParkInformationView> findNearestCarPark(Point coordinate, PageRequest pageRequest);
}
