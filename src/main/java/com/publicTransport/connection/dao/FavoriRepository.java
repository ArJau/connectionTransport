package com.publicTransport.connection.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import com.publicTransport.connection.model.Favori;

public interface FavoriRepository extends CrudRepository<Favori, Integer>{
	public List<Favori> findByUserId(Long userId);
	
	public Favori findByUserIdAndNoReseauId(Long userId, String noReseauId);
	
	public Favori findByUserIdAndReseauId(Long userId, String reseauId);

	public Favori findByUserIdAndReseauIdAndRouteId(Long userId, String reseauId, String routeId);

	public Favori findByUserIdAndReseauIdAndRouteIdAndStopId(Long userId, String reseauId, String routeId, String stopId);
	
	/*@Query("from Favori f where f.user.id=:user and f.reseauId=:reseauId and f.routeId=:routeId and f.stopId=:stopId")
	public Favori findFavoriStop(@Param("user") Long userId, @Param("reseauId") String reseauId, @Param("routeId") String routeId, 
			@Param("stopId") String stopId);
	
	@Query("from Favori f where f.user.id=:user and f.reseauId=:reseauId and f.routeId=:routeId")
	public Favori findFavoriLigne(@Param("user") Long userId, @Param("reseauId") String reseauId, @Param("routeId") String routeId);
	
	@Query("from Favori f where f.user.id=:user and f.reseauId=:reseauId")
	public Favori findFavoriReseau(@Param("user") Long userId, @Param("reseauId") String reseauId);*/
	
}


