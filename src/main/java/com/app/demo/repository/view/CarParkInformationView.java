package com.app.demo.repository.view;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface CarParkInformationView {

    String getAddress();

    Double getLatitude();

    Double getLongitude();

    Integer getTotalLots();

    Integer getAvailableLots();
}
