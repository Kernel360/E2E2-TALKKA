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
