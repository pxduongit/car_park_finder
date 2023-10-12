package com.app.demo.client.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarParkInformation {

    @JsonProperty("total_lots")
    private String totalLots;

    @JsonProperty("lot_type")
    private String lotType;

    @JsonProperty("lots_available")
    private String lotsAvailable;
}
