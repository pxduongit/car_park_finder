package com.app.demo.model.mapper;

import com.app.demo.model.dto.CarParkInformationDTO;
import com.app.demo.model.dto.PageResponse;
import com.app.demo.repository.view.CarParkInformationView;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CarParkInformationMapper {

    CarParkInformationMapper INSTANCE = Mappers.getMapper(CarParkInformationMapper.class);

    CarParkInformationDTO convertToDTO(CarParkInformationView view);

    default List<CarParkInformationDTO> convertToDTO(List<CarParkInformationView> listEntities) {
        if (CollectionUtils.isEmpty(listEntities)) {
            return List.of();
        }
        return listEntities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    default PageResponse<CarParkInformationDTO> convertToDTO(Page<CarParkInformationView> page) {
        PageResponse<CarParkInformationDTO> pageResponse = new PageResponse<>(page);
        List<CarParkInformationDTO> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(page.getContent())) {
            list = page.getContent().stream().map(this::convertToDTO).collect(Collectors.toList());
        }
        pageResponse.setContent(list);
        return pageResponse;
    }
}
