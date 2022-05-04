package com.publicTransport.connection.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.publicTransport.connection.model.Favori;

public interface FavoriRepository extends CrudRepository<Favori, Integer>{
	public List<Favori> findByUserId(Long userId);
	
	public Favori findByUserIdAndNoReseauId(Long userId, String noReseauId);
}


