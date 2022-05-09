package com.publicTransport.connection.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.publicTransport.connection.dao.FavoriRepository;
import com.publicTransport.connection.model.Favori;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RequestMapping("/api/favoris")
public class FavoriController {

	@Autowired
	FavoriRepository favoriRepo;
	
	@PostMapping("/save")
	public @ResponseBody void saveFavori(@RequestBody Iterable<Favori> lstFavori){
		for (Favori favori : lstFavori) {
			Favori optFavori = findFavori(favori);
			if (optFavori==null) {
				favoriRepo.save(favori);
			}
		}
	}
	
	@PostMapping("/delete")
	public @ResponseBody void deleteFavori( @RequestBody Iterable<Favori> lstFavori){
		for (Favori favori : lstFavori) {
			Favori optFavori = findFavori(favori);
			if (optFavori!=null) {
				favoriRepo.delete(optFavori);
			}
		}
	}
	
	@GetMapping(path = "/{userId}")
	public @ResponseBody Iterable<Favori> getFavori(@PathVariable Long userId){
		return favoriRepo.findByUserId(userId);
	}
	
	private Favori findFavori(Favori favori) {
		if (!isNullOuVide(favori.getNoReseauId())) {
			return favoriRepo.findByUserIdAndNoReseauId(favori.getUser().getId(), favori.getNoReseauId());
		}else if (!isNullOuVide(favori.getReseauId())) {
			return favoriRepo.findByUserIdAndReseauIdAndRouteIdAndStopId(favori.getUser().getId(), favori.getReseauId(), favori.getRouteId(),  favori.getStopId() );
		}
		
		return null;
		
			
	}
	
	private boolean isNullOuVide(String val) {
		return val==null || "".equals(val);
	}
	
}
