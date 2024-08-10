package com.talkka.server.subway.dao;

import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.stereotype.Repository;

@Repository
public interface SubwayTimetableRepository extends JpaAttributeConverter<SubwayTimetableEntity, Long> {
}
