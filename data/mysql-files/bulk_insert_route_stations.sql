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
