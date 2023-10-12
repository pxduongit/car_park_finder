package com.app.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarParkInformationDTO {

    @JsonProperty("distance")
    private Double distance;

    @JsonProperty("address")
    private String address;

    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("total_lots")
    private Integer totalLots;

    @JsonProperty("available_lots")
    private Integer availableLots;

}
