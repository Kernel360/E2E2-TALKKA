-- 기본 DB / Schema 생성
DROP DATABASE IF EXISTS `TALKKA_DB`;
CREATE DATABASE IF NOT EXISTS `TALKKA_DB`;
USE `TALKKA_DB`;

# SCHEMA
# SOURCE /var/lib/mysql-files/schema.sql;
drop table if exists bus_route;
create table bus_route
(
    route_id           bigint auto_increment primary key,
    district_cd        varchar(3)   not null,
    n_peek_alloc       int          not null,
    peek_alloc         int          not null,
    route_type_cd      varchar(3)   not null,
    down_first_time    varchar(5)   not null,
    down_last_time     varchar(5)   not null,
    up_first_time      varchar(5)   not null,
    up_last_time       varchar(5)   not null,
    created_at         datetime(6)  not null,
    end_station_id     bigint       not null,
    start_station_id   bigint       not null,
    updated_at         datetime(6)  not null,
    end_mobile_no      varchar(10)  null,
    start_mobile_no    varchar(10)  null,
    company_tel        varchar(15)  null,
    company_id         varchar(20)  not null,
    api_route_id       varchar(40)  not null,
    company_name       varchar(50)  not null,
    route_name         varchar(50)  not null,
    route_type_name    varchar(50)  not null,
    end_station_name   varchar(100) not null,
    region_name        varchar(100) null,
    start_station_name varchar(100) not null
);

drop table if exists bus_location;
create table bus_location
(
    bus_location_id   bigint auto_increment primary key,
    end_bus           varchar(1)  not null,
    low_plate         varchar(1)  not null,
    plate_type        varchar(1)  not null,
    remain_seat_count smallint    not null,
    station_seq       smallint    not null,
    created_at        datetime(6) not null,
    api_route_id      bigint      null,
    api_station_id    bigint      not null,
    api_call_no       int         not null,
    plate_no          varchar(32) not null
);

drop table if exists bus_station;
create table bus_station
(
    station_id     bigint auto_increment primary key,
    center_yn      varchar(1)     not null,
    latitude       decimal(10, 7) not null,
    longitude      decimal(10, 7) not null,
    turn_yn        varchar(1)     not null,
    created_at     datetime(6)    not null,
    api_station_id varchar(40)    not null,
    region_name    varchar(100)   not null,
    station_name   varchar(100)   not null,
    district_cd    varchar(255)   not null
);

drop table if exists bus_route_station;
create table bus_route_station
(
    bus_route_station_id bigint auto_increment primary key,
    station_seq          smallint     not null,
    created_at           datetime(6)  not null default current_timestamp(6),
    route_id             bigint       null,
    station_id           bigint       null,
    station_name         varchar(100) not null
);

drop table if exists users;
create table users
(
    user_id        bigint auto_increment primary key,
    created_at     datetime(6)            not null,
    updated_at     datetime(6)            not null,
    email          varchar(30)            not null,
    oauth_provider varchar(30)            not null,
    nickname       varchar(50)            not null,
    access_token   varchar(255)           not null,
    name           varchar(255)           not null,
    grade          enum ('ADMIN', 'USER') null
);

drop table if exists bus_review;
create table bus_review
(
    bus_review_id        bigint auto_increment primary key,
    rating               int          not null,
    bus_route_station_id bigint       null,
    created_at           datetime(6)  not null,
    route_id             bigint       null,
    updated_at           datetime(6)  not null,
    user_id              bigint       null,
    content              varchar(400) null,
    time_slot            varchar(20)  not null
);

drop table if exists subway_review;
create table subway_review
(
    subway_review_id bigint auto_increment primary key,
    user_id          bigint       null,
    station_id       bigint       null,
    line_code        VARCHAR(4)   not null,
    updown           VARCHAR(1)   not null,
    content          varchar(400) null,
    time_slot        varchar(20)  not null,
    rating           int          not null,
    created_at       datetime(6)  not null,
    updated_at       datetime(6)  not null
);

drop table if exists subway_confusion;
CREATE TABLE subway_confusion
(
    subway_confusion_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    station_id          BIGINT                NULL,
    station_code        VARCHAR(10)           NOT NULL,
    station_name        VARCHAR(50)           NOT NULL,
    line_code           VARCHAR(4)            NOT NULL,
    day_type            VARCHAR(3)            NOT NULL,
    updown              VARCHAR(1)            NOT NULL,
    time_slot           VARCHAR(20)           NOT NULL,
    confusion           DOUBLE                NULL
);

drop table if exists subway_station;
CREATE TABLE subway_station
(
    station_id   BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    station_name VARCHAR(50)           NOT NULL,
    station_code VARCHAR(10)           NOT NULL,
    line_code    VARCHAR(4)            NOT NULL
);

drop table if exists subway_timetable;
CREATE TABLE subway_timetable
(
    subway_timetable_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    station_id          BIGINT                NOT NULL,
    station_code        VARCHAR(10)           NOT NULL,
    station_name        VARCHAR(50)           NOT NULL,
    line_code           VARCHAR(4)            NOT NULL,
    day_type            VARCHAR(3)            NOT NULL,
    updown              VARCHAR(1)            NOT NULL,
    is_express          VARCHAR(1)            NOT NULL,
    train_code          VARCHAR(10)           NOT NULL,
    arrival_time        time                  NOT NULL,
    depart_time         time                  NOT NULL,
    start_station_name  VARCHAR(50)           NOT NULL,
    end_station_name    VARCHAR(50)           NOT NULL
);

DROP TABLE IF EXISTS bookmark;
CREATE TABLE bookmark
(
    bookmark_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name        VARCHAR(255)          NOT NULL,
    user_id     BIGINT                NULL,
    created_at  datetime              NOT NULL,
    updated_at  datetime              NOT NULL
);


DROP TABLE IF EXISTS bookmark_detail;
CREATE TABLE bookmark_detail
(
    bookmark_detail_id   BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    seq                  INT                   NOT NULL,
    type                 VARCHAR(10)           NOT NULL,
    bookmark_id          BIGINT                NULL,
    subway_station_id    BIGINT                NULL,
    subway_updown        VARCHAR(255)          NULL,
    bus_route_station_id BIGINT                NULL,
    created_at           datetime              NOT NULL
);


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


-- BULK INSERT 이후 DB 의 FK CONSTRAINT 를 잡아줌
# SOURCE /var/lib/mysql-files/add_constraints.sql;
-- bus_route_station 테이블의 외래 키 추가
ALTER TABLE talkka_db.bus_route_station
    ADD CONSTRAINT FKf66j4cjj3igamxvlxvbvm3shp
        FOREIGN KEY (route_id) REFERENCES talkka_db.bus_route (route_id),
    ADD CONSTRAINT FKh3jd7m358dvb09j9qx8xxtn2m
        FOREIGN KEY (station_id) REFERENCES talkka_db.bus_station (station_id);

-- subway_confusion 테이블의 외래 키 추가
ALTER TABLE talkka_db.subway_confusion
    ADD CONSTRAINT FK8m94gtsyhkedmu7vbk0ky3i8l
        FOREIGN KEY (station_id) REFERENCES talkka_db.subway_station (station_id);

-- subway_timetable 테이블의 외래 키 추가
ALTER TABLE talkka_db.subway_timetable
    ADD CONSTRAINT FK3tuvka1c7yr4535ac60blmamh
        FOREIGN KEY (station_id) REFERENCES talkka_db.subway_station (station_id);

-- bus_review 테이블의 외래 키 추가
ALTER TABLE talkka_db.bus_review
    ADD CONSTRAINT FK23ju1igohysqlytq7p8wa6tax
        FOREIGN KEY (bus_route_station_id) REFERENCES talkka_db.bus_route_station (bus_route_station_id),
    ADD CONSTRAINT FKmh0qi58cvoq8ivcrmstjoauw5
        FOREIGN KEY (route_id) REFERENCES talkka_db.bus_route (route_id),
    ADD CONSTRAINT FKojkl7pwovnccu0txrbvxk97jd
        FOREIGN KEY (user_id) REFERENCES talkka_db.users (user_id);

-- subway_review 테이블의 외래 키 추가
ALTER TABLE talkka_db.subway_review
    ADD CONSTRAINT FKjbnhvh0i4ey6y3bt2ipouhw41
        FOREIGN KEY (station_id) REFERENCES talkka_db.subway_station (station_id),
    ADD CONSTRAINT FKt1tn431cfkx0p8qvx8k6hd6i5
        FOREIGN KEY (user_id) REFERENCES talkka_db.users (user_id);

-- bookmark 테이블의 외래 키 추가
ALTER TABLE bookmark
    ADD CONSTRAINT FK_BOOKMARK_ON_USER FOREIGN KEY (user_id) REFERENCES users (user_id);

-- bookmark_detail 테이블의 외래 키 추가
ALTER TABLE bookmark_detail
    ADD CONSTRAINT FK_BOOKMARK_DETAIL_ON_BOOKMARK FOREIGN KEY (bookmark_id) REFERENCES bookmark (bookmark_id);