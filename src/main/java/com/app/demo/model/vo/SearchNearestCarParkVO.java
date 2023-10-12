package com.app.demo.model.vo;

import lombok.*;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class SearchNearestCarParkVO extends SearchPageVO {

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

}
