package org.spontaneous.server.usermanagement.dao;

import org.spontaneous.server.usermanagement.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;


public interface RoleRepository extends CrudRepository<RoleEntity, Long>{

	 RoleEntity findByName(String name);
	 
}
