package com.app.demo.scheduler;

import com.app.demo.client.CarParkInformationClient;
import com.app.demo.client.res.CarParkAvailability;
import com.app.demo.client.res.CarParkAvailabilityRes;
import com.app.demo.client.res.CarParkData;
import com.app.demo.client.res.CarParkInformation;
import com.app.demo.common.util.CollectionUtil;
import com.app.demo.repository.CarParkRepository;
import com.app.demo.repository.CarParkingAvailabilityRepository;
import com.app.demo.repository.entity.CarParkEntity;
import com.app.demo.repository.entity.CarParkingAvailabilityEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateCarParkAvailabilityJob {

    private final CarParkInformationClient carParkInformationClient;

    private final CarParkingAvailabilityRepository carParkingAvailabilityRepository;

    private final CarParkRepository carParkRepository;

    @Scheduled(cron = "${schedule.updateCarParkAvailability.cron.express}")
    public void execute() {
        try {
            log.info("Start update car park availability job");
            CarParkAvailabilityRes carParkAvailabilityRes = carParkInformationClient.getCarAvailability();
            if(!CollectionUtils.isEmpty(carParkAvailabilityRes.getItems())){

                //todo enhance case item has more than 1 element and timestamp is difference

                // We will flat list car parking availability for easy processing
                List<CarParkingAvailabilityEntity> listParkingFlat = new ArrayList<>();
                for(CarParkAvailability carParkAvailability : carParkAvailabilityRes.getItems()){

                    // todo enhance save last timestamp update, and check if data already update

                    for(CarParkData carParkData : carParkAvailability.getCarParkData()){
                        for(CarParkInformation carParkInformation : carParkData.getCarParkInfo()){
                            CarParkingAvailabilityEntity entity = CarParkingAvailabilityEntity.builder()
                                    .carParkNo(carParkData.getCarParkNumber())
                                    .lotType(carParkInformation.getLotType())
                                    // each car lot with available status will have an unique code.
                                    // So that it will be easy check or query if data is exists.
                                    // And if data size is large, we can have index for this row in DB
                                    .code(String.format("%s|%s", carParkData.getCarParkNumber(), carParkInformation.getLotType()))
                                    .lotsAvailable(Integer.valueOf(carParkInformation.getLotsAvailable()))
                                    .totalLot(Integer.valueOf(carParkInformation.getTotalLots()))
                                    .updateDatetime(carParkData.getUpdateDatetime())
                                    .build();
                            listParkingFlat.add(entity);
                        }
                    }
                }

                // Get list exists car parking available for update or insert history in further.
                // We should split size query to 1000 element part due to limit of some DB engine like Oracle, ...
                List<CarParkingAvailabilityEntity> existEntities = new ArrayList<>();
                int subSize = 1000;
                CollectionUtil.splitAndConsume(
                        listParkingFlat.stream().map(CarParkingAvailabilityEntity::getCode).collect(Collectors.toList()),
                        subSize,
                        true,
                        subCodes -> existEntities.addAll(carParkingAvailabilityRepository.findAllByCodeIn(subCodes)));
                Map<String, CarParkingAvailabilityEntity> carParkingAvailabilityEntityMap = existEntities.stream().
                        collect(Collectors.toMap(CarParkingAvailabilityEntity::getCode, entity -> entity));

                // For exists car park available, we update. With other, we create new one.
                for(CarParkingAvailabilityEntity entity: listParkingFlat){
                    CarParkingAvailabilityEntity existEntity = carParkingAvailabilityEntityMap.get(entity.getCode());

                    if(Objects.nonNull(existEntity)){
                        // case exists car_park_no with lot_type, we can update and can save to history in further.
                        existEntity.setLotsAvailable(entity.getLotsAvailable());
                        existEntity.setTotalLot(entity.getTotalLot());
                        existEntity.setUpdateDatetime(entity.getUpdateDatetime());
                        carParkingAvailabilityRepository.save(existEntity);
                    }else{
                        //case new car_park_no and lot_type, we create new one
                        CarParkEntity carParkEntity = carParkRepository.findByCarParkNo(entity.getCarParkNo()).orElse(null);
                        if(Objects.nonNull(carParkEntity)){
                            entity.setCarParkId(carParkEntity.getId());
                            carParkingAvailabilityRepository.save(entity);
                        }
                    }
                }
            }
            log.info("End update car park availability job");
        } catch (Exception e) {
            log.info("Error update car park availability job: ", e);
        }
    }
}
