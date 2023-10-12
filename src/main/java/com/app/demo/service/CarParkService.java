package com.app.demo.service;

import com.app.demo.model.dto.CarParkInformationDTO;
import com.app.demo.model.dto.PageResponse;
import com.app.demo.model.mapper.CarParkInformationMapper;
import com.app.demo.model.vo.SearchNearestCarParkVO;
import com.app.demo.repository.CarParkRepository;
import com.app.demo.repository.entity.CarParkEntity;
import com.app.demo.repository.view.CarParkInformationView;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarParkService {

    private final CarParkRepository carParkRepository;

    public List<CarParkEntity> getAll(){
        return carParkRepository.findAll();
    }

    public PageResponse<CarParkInformationDTO> searchNearest(SearchNearestCarParkVO searchObj){

        PageRequest pageRequest = PageRequest.of(searchObj.getPage(), searchObj.getPerPage());

        GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);
        factory.createPoint(new Coordinate(searchObj.getLongitude(),searchObj.getLatitude()));
        Point comparisonPoint = factory.createPoint(new Coordinate(searchObj.getLongitude(),searchObj.getLatitude()));

        Page<CarParkInformationView> temp = carParkRepository.findNearestCarPark(comparisonPoint, pageRequest);
        return CarParkInformationMapper.INSTANCE.convertToDTO(temp);
    }

}
