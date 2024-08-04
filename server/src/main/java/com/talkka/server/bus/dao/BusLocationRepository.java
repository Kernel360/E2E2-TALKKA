package com.talkka.server.bus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface BusLocationRepository extends JpaRepository<BusLocationEntity, Long> {
}
