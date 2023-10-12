package com.app.demo.client;

import com.app.demo.client.res.CarParkAvailabilityRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "${service.car-park-available.name}", url = "${service.car-park-available.host}")
public interface CarParkInformationClient {

    @GetMapping("/v1/transport/carpark-availability")
    CarParkAvailabilityRes getCarAvailability();
}
