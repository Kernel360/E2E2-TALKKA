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
    route_id          bigint      null,
    station_id        bigint      not null,
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
    subway_review_id     bigint auto_increment primary key,
    user_id              bigint       null,
    station_id           bigint       null,
    line_code            VARCHAR(4)   not null,
    updown               VARCHAR(1)   not null,
    content              varchar(400) null,
    time_slot            varchar(20)  not null,
    rating               int          not null,
    created_at           datetime(6)  not null,
    updated_at           datetime(6)  not null
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