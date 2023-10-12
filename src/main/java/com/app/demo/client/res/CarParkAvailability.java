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
public class CarParkAvailability {

    @JsonProperty("timestamp")
    private Date timestamp;

    @JsonProperty("carpark_data")
    private List<CarParkData> carParkData;
}
