package com.app.demo.client.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarParkData {

    @JsonProperty("carpark_number")
    private String carParkNumber;

    @JsonProperty("update_datetime")
    private Date updateDatetime;

    @JsonProperty("carpark_info")
    private List<CarParkInformation> carParkInfo;
}
