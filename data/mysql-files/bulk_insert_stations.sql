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
