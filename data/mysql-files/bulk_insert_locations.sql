-- 1. CSV 데이터 로드
LOAD DATA INFILE '/var/lib/mysql-files/bus_location.csv'
    INTO TABLE bus_location
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES
    (bus_location_id, api_route_id, api_station_id, station_seq, end_bus, low_plate, plate_no, plate_type,
     remain_seat_count, created_at);