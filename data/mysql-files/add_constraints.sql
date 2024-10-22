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

ALTER TABLE bookmark_detail
    ADD CONSTRAINT FK_BOOKMARK_DETAIL_ON_BOOKMARK FOREIGN KEY (bookmark_id) REFERENCES bookmark (bookmark_id);