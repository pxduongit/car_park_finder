# Car-Park-Finder -  Find the nearest Car Park by coordinate
Exercise that provide API that return list car park sorted by distance ascending, include other information such as coordinate, total lots, available lots of car park .

## Installing Prerequisites
- Java 11
- Maven 3.9.4
- PostgreSQl 16
  - PostgreSQL with Postgis extension, deploy on Docker container
      + build PostgreSQL container: Run below script step by step.
            
```docker pull postgres```
        
```docker run -d --name postgresCont -p 5432:5432 -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=123456a@ postgres```

Enter Docker container terminal via Docker exec command and install Postgis Extension
```apt install postgis postgresql-16-postgis-3```

Connect to Database and run this script on database postgres
```CREATE EXTENSION postgis;```
    
- Current API port is ```8088```
- Database will be re-created each time application start due to ```spring.jpa.hibernate.ddl-auto= create``` config. It's for demo application only. In product, we should remove this.

## Source-Code Structure

Source code have 3 main point to meet the requirement.

### 1. Init Car-park information from csv file
At starting of application, ```InitCarParkInformation``` class will read csv file from resources folder named ```HDBCarparkInformation.csv``` and insert all car-park information to DB. We have ```x_coord``` and ```y_coord``` column present coordinate in SVY21 format, so we need to convert them into WGS84 first and then save in DB with Latitude, Longitude and Coordinate in geometry type for easy query and access.

### 2. Update Car-park lots availability Job
A Job that call Singapore GOV API one time every 1 minute, get data of total lots and available lots of car-park. New data will be updated in DB with updated timestamp. Scheduler can be config in ```application.properties```

### 3. API return list nearest car-park
API that get Latitude and Longitude and return list car park sorted by distance ascending. We use ST_Distance function of Postgis to calculate distance from a point to car-park coordinate. The result will summary total lot and available lots of all lot type in car-park as well.

## Reference

- Source code use an opensource to convert coordinate from 3414(SVY21) to 4326(WGS84)
- Link: https://github.com/cgcai/SVY21

## Need to improve in future
- Add filter to list result by lot_type, max_distance, ...
- Enhance data by batch. Insert and update list data by batch
- Store Car-park availability history if we need other business in the future. We also need to keep history data for about 1 week or same.
- Implement liquibase for Database structure initial and data migrate in further.
