package com.app.demo.repository.entity;

import com.app.demo.common.constant.TableName;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = TableName.TBL_CAR_PARKING_AVAILABILITY)
public class CarParkingAvailabilityEntity extends BaseEntity{

    @Column(name = "car_park_id")
    private Long carParkId;

    @Column(name = "code")
    private String code;

    @Column(name = "car_park_no")
    private String carParkNo;

    @Column(name = "update_datetime")
    private Date updateDatetime;

    @Column(name = "lot_type")
    private String lotType;

    @Column(name = "total_lots")
    private Integer totalLot;

    @Column(name = "lots_available")
    private Integer lotsAvailable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_park_id", insertable = false, updatable = false)
    private CarParkEntity carPark;
}
