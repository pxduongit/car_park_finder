package com.app.demo.component;

import com.app.demo.common.util.coord.LatLonCoordinate;
import com.app.demo.common.util.coord.SVY21Coordinate;
import com.app.demo.repository.CarParkRepository;
import com.app.demo.repository.entity.CarParkEntity;
import com.app.demo.service.CarParkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.Reader;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitCarParkInformation implements CommandLineRunner {

    private final CarParkService carParkService;

    private final CarParkRepository carParkRepository;

    @Override
    public void run(String... args) {
        log.info("---- Init car park information ----");
        try {
            if (carParkService.getAll().size() == 0) {
                Iterable<CSVRecord> records = readCSV();
                for(CSVRecord record: records){
                    System.out.println(record.get("car_park_no"));
                    CarParkEntity carParkEntity = new CarParkEntity();
                    carParkEntity.setCarParkNo(record.get("car_park_no"));
                    carParkEntity.setAddress(record.get("address"));

                    Double xCoord = Double.valueOf(record.get("x_coord"));
                    Double yCoord = Double.valueOf(record.get("y_coord"));
                    carParkEntity.setXCoordinate(xCoord);
                    carParkEntity.setYCoordinate(yCoord);
                    LatLonCoordinate coordinate = new SVY21Coordinate(yCoord, xCoord).asLatLon();

                    carParkEntity.setLongitude(coordinate.getLongitude());
                    carParkEntity.setLatitude(coordinate.getLatitude());

                    GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);
                    factory.createPoint(new Coordinate(coordinate.getLongitude(),coordinate.getLatitude()));
                    Point comparisonPoint = factory.createPoint(new Coordinate(coordinate.getLongitude(),coordinate.getLatitude()));
                    carParkEntity.setCoordinate(comparisonPoint);

                    carParkEntity.setCarParkType(record.get("car_park_type"));
                    carParkEntity.setTypeOfParkingSystem(record.get("type_of_parking_system"));
                    carParkEntity.setShortTermParking(record.get("short_term_parking"));
                    carParkEntity.setFreeParking(record.get("free_parking"));
                    carParkEntity.setNightParking("YES".equals(record.get("night_parking")));
                    carParkEntity.setCarParkDecks(Integer.valueOf(record.get("car_park_decks")));
                    carParkEntity.setGantryHeight(Double.valueOf(record.get("gantry_height")));
                    carParkEntity.setCarParkBasement("Y".equals(record.get("car_park_basement")));
                    carParkRepository.save(carParkEntity);
                }
            }
        } catch (Exception e) {
            log.error("---- Exception while init car park information: {}, {}", e.getClass(), e.getMessage());
        }
        log.info("---- Init car park information ended ----");
    }


    static String[] HEADERS = { "car_park_no","address","x_coord","y_coord","car_park_type","type_of_parking_system","short_term_parking","free_parking","night_parking","car_park_decks","gantry_height","car_park_basement"};

    private static Iterable<CSVRecord> readCSV() throws Exception{
        Reader in = new FileReader("classpath:HDBCarparkInformation.csv");

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(HEADERS)
                .setSkipHeaderRecord(true)
                .build();

        Iterable<CSVRecord> records = csvFormat.parse(in);
        return records;
    }
}
