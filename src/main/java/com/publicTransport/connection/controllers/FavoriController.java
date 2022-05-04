package com.publicTransport.connection.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.publicTransport.connection.dao.FavoriRepository;
import com.publicTransport.connection.model.Favori;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/favoris")
public class FavoriController {

	@Autowired
	FavoriRepository favoriRepo;
	
	@PostMapping("/save")
	public @ResponseBody void saveFavori(@RequestBody Iterable<Favori> lstFavori){
		for (Favori favori : lstFavori) {
			Favori optFavori = favoriRepo.findByUserIdAndNoReseauId(favori.getUser().getId(), favori.getNoReseauId());
			if (optFavori==null)
				favoriRepo.save(favori);
		}
	}
	
	@PostMapping("/delete")
	public @ResponseBody void deleteFavori( @RequestBody Iterable<Favori> lstFavori){
		for (Favori favori : lstFavori) {
			favori = favoriRepo.findByUserIdAndNoReseauId(favori.getUser().getId(), favori.getNoReseauId());
			favoriRepo.delete(favori);
		}
	}
	
	@GetMapping(path = "/{userId}")
	public @ResponseBody Iterable<Favori> getFavori(@PathVariable Long userId){
		return favoriRepo.findByUserId(userId);
	}
	
}
