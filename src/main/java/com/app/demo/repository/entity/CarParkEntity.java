package com.app.demo.repository.entity;

import com.app.demo.common.constant.TableName;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = TableName.TBL_CAR_PARK_INFORMATION, uniqueConstraints = {@UniqueConstraint(name = "UQ_CAR_PARK_NO", columnNames = {"car_park_no"})})
public class CarParkEntity extends BaseEntity {

    @Column(name = "car_park_no")
    private String carParkNo;

    @Column(name = "address")
    private String address;

    @Column(name = "x_coord")
    private Double xCoordinate;

    @Column(name = "y_coord")
    private Double yCoordinate;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "coordinate")
    private Point coordinate;

    @Column(name = "car_park_type")
    private String carParkType;

    @Column(name = "type_of_parking_system")
    private String typeOfParkingSystem;

    @Column(name = "short_term_parking")
    private String shortTermParking;

    @Column(name = "free_parking")
    private String freeParking;

    @Column(name = "night_parking")
    private Boolean nightParking;

    @Column(name = "car_park_decks")
    private Integer carParkDecks;

    @Column(name = "gantry_height")
    private Double gantryHeight;

    @Column(name = "car_park_basement")
    private Boolean carParkBasement;
}
