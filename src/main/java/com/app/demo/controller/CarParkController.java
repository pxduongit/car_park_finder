package com.app.demo.controller;

import com.app.demo.model.dto.CarParkInformationDTO;
import com.app.demo.model.dto.PageResponse;
import com.app.demo.model.vo.SearchNearestCarParkVO;
import com.app.demo.service.CarParkService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@RestController
@Validated
@RequiredArgsConstructor
public class CarParkController {

    private final CarParkService carParkService;

    @GetMapping("/carparks/nearest")
    public PageResponse<CarParkInformationDTO> getNearestCarPark(
            @RequestParam(name = "latitude") Double latitude,
            @RequestParam(name = "longitude") Double longitude,
            @RequestParam(required = false, defaultValue = "2", name = "page") @Min(value = 0, message = "Invalid value") Integer page,
            @RequestParam(required = false, defaultValue = "20", name = "per_page") @Min(value = 1, message = "Invalid value") Integer perPage
    ) {
        SearchNearestCarParkVO searchObj = SearchNearestCarParkVO.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
        searchObj.setPage(page);
        searchObj.setPerPage(perPage);
        return carParkService.searchNearest(searchObj);
    }
}
