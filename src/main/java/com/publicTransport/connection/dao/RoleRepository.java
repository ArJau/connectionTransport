package com.publicTransport.connection.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.publicTransport.connection.model.ERole;
import com.publicTransport.connection.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long>{
	Optional<Role> findByName(ERole name);
}