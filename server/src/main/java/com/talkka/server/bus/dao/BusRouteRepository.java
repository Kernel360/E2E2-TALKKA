package com.talkka.server.bus.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRouteRepository extends JpaRepository<BusRouteEntity, Long> {

	boolean existsByApiRouteId(String apiRouteId);

	Optional<BusRouteEntity> findByApiRouteId(String apiRouteId);

	List<BusRouteEntity> findByRouteNameLikeOrderByRouteNameAsc(String routeName);

}
