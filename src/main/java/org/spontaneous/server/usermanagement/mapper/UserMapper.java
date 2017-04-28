package org.spontaneous.server.usermanagement.mapper;

import org.spontaneous.server.client.service.UserModel;
import org.spontaneous.server.usermanagement.entity.UserEntity;

public class UserMapper {

	public static UserModel mapUserEntityToUserModel(UserEntity userEntity) {
		
		UserModel userModel = new UserModel();
		userModel.setId(userEntity.getId());
		userModel.setLastname(userEntity.getLastName());
		userModel.setEmail(userEntity.getEmail());
		userModel.setGender(userEntity.getGender().getName());
		userModel.setImage(userEntity.getImage());
		userModel.setFirstname(userEntity.getFirstName());

		return userModel;
	}
	
}
