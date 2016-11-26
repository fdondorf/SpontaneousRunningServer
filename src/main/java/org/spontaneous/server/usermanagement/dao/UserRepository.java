package org.spontaneous.server.usermanagement.dao;

import java.util.List;

import org.spontaneous.server.usermanagement.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<UserEntity, Long>{

	 UserEntity findByEmail(String email);
	 
	 List<UserEntity> findByLastName(String lastname);
}
