package com.publicTransport.connection.security.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.publicTransport.connection.dao.UserRepository;
import com.publicTransport.connection.model.User;

/**
 * Implémentation de l'interface UserDetailsService. 
 * Cette classe permet d'aller chercher un utilisateur de l'application dans la base de donnée 
 * et de le transformer en un UserDetails. 
 * UserDetails est la class crée par spring boot sécurity qui permet de gérer 
 * l'authentification et la validation des utilisateurs.
 * @author Arnaud
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		return UserDetailsImpl.build(user);
	}
}