package com.talkka.server.admin.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CollectBusRouteRepository extends JpaRepository<CollectBusRouteEntity, Long> {

	@Query("select c from collect_bus_route c where c.route.id = :routeId")
	Optional<CollectBusRouteEntity> findByRouteId(@Param("routeId") Long routeId);

	@Modifying
	@Transactional
	@Query("delete from collect_bus_route c where c.route.id = :routeId")
	void deleteByRouteId(@Param("routeId") Long routeId);
}

