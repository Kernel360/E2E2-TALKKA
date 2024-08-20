package com.talkka.server.bus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRouteRepository extends JpaRepository<BusRouteEntity, Long> {
	boolean existsByApiRouteId(String apiRouteId);

	List<BusRouteEntity> findAllByRouteNameStartingWithOrderByRouteNameAsc(String routeName);
}
