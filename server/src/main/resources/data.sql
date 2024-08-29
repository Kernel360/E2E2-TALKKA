-- BULK INSERT (STATIC DATA)
# SOURCE /var/lib/mysql-files/bulk_insert_routes.sql;
-- 1. 임시 테이블 생성
DROP TABLE IF EXISTS temp_bus_route;
CREATE TABLE temp_bus_route
(
    route_id           BIGINT,
    route_name         VARCHAR(100),
    route_type_cd      VARCHAR(10),
    route_type_name    VARCHAR(100),
    start_station_id   BIGINT,
    start_station_name VARCHAR(100),
    start_mobile_no    VARCHAR(50),
    end_station_id     BIGINT,
    end_station_name   VARCHAR(100),
    end_mobile_no      VARCHAR(50),
    region_name        VARCHAR(100),
    district_cd        VARCHAR(10),
    up_first_time      VARCHAR(20),
    up_last_time       VARCHAR(20),
    down_first_time    VARCHAR(20),
    down_last_time     VARCHAR(20),
    peek_alloc         INT,
    n_peek_alloc       INT,
    company_id         BIGINT,
    company_name       VARCHAR(100),
    company_tel        VARCHAR(50)
);

-- 2. CSV 데이터 로드 (임시 테이블에)
LOAD DATA INFILE '/var/lib/mysql-files/bus_route.csv'
    INTO TABLE temp_bus_route
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES
    (route_id, route_name, route_type_cd, route_type_name, start_station_id, start_station_name, start_mobile_no,
     end_station_id, end_station_name, end_mobile_no, region_name, district_cd, up_first_time, up_last_time,
     down_first_time, down_last_time, peek_alloc, n_peek_alloc, company_id, company_name, company_tel);

-- 3. 중복 제거 후 최종 테이블에 삽입
INSERT INTO bus_route (api_route_id, route_name, route_type_cd, route_type_name, start_station_id, start_station_name,
                       start_mobile_no, end_station_id, end_station_name, end_mobile_no, region_name, district_cd,
                       up_first_time, up_last_time, down_first_time, down_last_time, peek_alloc, n_peek_alloc,
                       company_id, company_name, company_tel, created_at, updated_at)
SELECT DISTINCT route_id,
                route_name,
                route_type_cd,
                route_type_name,
                start_station_id,
                start_station_name,
                start_mobile_no,
                end_station_id,
                end_station_name,
                end_mobile_no,
                region_name,
                district_cd,
                up_first_time,
                up_last_time,
                down_first_time,
                down_last_time,
                peek_alloc,
                n_peek_alloc,
                company_id,
                company_name,
                company_tel,
                NOW(),
                NOW()
FROM temp_bus_route;

-- 4. 임시 테이블 삭제
DROP TABLE temp_bus_route;



# SOURCE /var/lib/mysql-files/bulk_insert_stations.sql;
-- 1. 임시 테이블 생성
DROP TABLE IF EXISTS temp_bus_station;
CREATE TABLE temp_bus_station
(
    station_id   BIGINT,
    station_name VARCHAR(100),
    mobile_no    VARCHAR(50),
    region_name  VARCHAR(100),
    district_cd  VARCHAR(10),
    center_yn    CHAR(1),
    turn_yn      CHAR(1),
    x            DECIMAL(11, 8),
    y            DECIMAL(11, 8)
);

-- 2. CSV 데이터 로드 (임시 테이블에)
LOAD DATA INFILE '/var/lib/mysql-files/bus_station.csv'
    INTO TABLE temp_bus_station
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES
    (station_id, station_name, mobile_no, region_name, district_cd, center_yn, turn_yn, x, y);

-- 3. 중복 제거 후 최종 테이블에 삽입
INSERT INTO bus_station (api_station_id, station_name, region_name, district_cd, center_yn, turn_yn,
                         latitude, longitude, created_at)
SELECT DISTINCT station_id,
                station_name,
                region_name,
                district_cd,
                center_yn,
                turn_yn,
                y AS latitude,
                x AS longitude,
                NOW()
FROM temp_bus_station;

-- 4. 임시 테이블 삭제
DROP TABLE temp_bus_station;


# SOURCE /var/lib/mysql-files/bulk_insert_route_stations.sql;
-- 1. 임시 테이블 생성
DROP TABLE IF EXISTS temp_bus_route_station;
CREATE TABLE temp_bus_route_station
(
    route_id     BIGINT,
    station_id   BIGINT,
    station_seq  SMALLINT,
    station_name VARCHAR(100),
    mobile_no    VARCHAR(50),                       -- 필요에 따라 추가
    region_name  VARCHAR(100),                      -- 필요에 따라 추가
    district_cd  VARCHAR(10),                       -- 필요에 따라 추가
    center_yn    CHAR(1),                           -- 필요에 따라 추가
    turn_yn      CHAR(1),                           -- 필요에 따라 추가
    x            DECIMAL(11, 8),                    -- 필요에 따라 추가
    y            DECIMAL(11, 8),                    -- 필요에 따라 추가
    created_at   DATETIME DEFAULT CURRENT_TIMESTAMP -- 생성일자 추가
);

-- 2. CSV 데이터 로드 (열 순서를 명시적으로 지정)
LOAD DATA INFILE '/var/lib/mysql-files/bus_route_station.csv'
    INTO TABLE temp_bus_route_station
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS
    (route_id, station_id, station_seq, station_name, mobile_no, region_name, district_cd, center_yn, turn_yn, x, y);
-- 열 순서를 명시적으로 지정합니다.

-- 3. bus_route_station 테이블에 데이터 삽입
INSERT INTO bus_route_station (route_id, station_id, station_seq, station_name, created_at)
SELECT b.route_id, s.station_id AS station_id, t.station_seq, t.station_name, NOW()
FROM temp_bus_route_station t
         JOIN bus_route b ON t.route_id = b.api_route_id
         JOIN bus_station s ON t.station_id = s.api_station_id;
-- station_id를 매핑합니다.

-- 4. 임시 테이블 삭제
DROP TABLE temp_bus_route_station;

-- 1. 버스 위치 정보 데이터 삽입
-- 2. CSV 데이터 로드 (임시 테이블에)
LOAD DATA INFILE '/var/lib/mysql-files/bus_location.csv'
    INTO TABLE bus_location
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
--     IGNORE 1 LINES
    (bus_location_id, api_route_id, api_station_id, station_seq, end_bus, low_plate, plate_no, plate_type,
     remain_seat_count, created_at);

-- 1. 버스별 운행 정보 삽입
-- 2. CSV 데이터 로드 (임시 테이블에)
-- 헤더가 있는 경우 IGNORE 1 LINES 활성화
LOAD DATA INFILE '/var/lib/mysql-files/bus_plate_statistic.csv'
    INTO TABLE bus_plate_statistic
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
#     IGNORE 1 LINES
    (end_time, start_time, epoch_day, id, route_id, plate_no, plate_type);

-- 1. 버스 좌석 정보 데이터 삽입
-- 2. CSV 데이터 로드 (임시 테이블에)
-- 헤더가 있는 경우 IGNORE 1 LINES 활성화
LOAD DATA INFILE '/var/lib/mysql-files/bus_remain_seat.csv'
    INTO TABLE bus_remain_seat
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
#     IGNORE 1 LINES
    (empty_seat, plate_type, station_seq, time, created_at, epoch_day, id, plate_statistic_id, route_id, station_id,
     plate_no);
