package com.ashu.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import com.ashu.DTO.ChangePasswordRequest;
import com.ashu.entity.User;

public interface UserService 
{
	public String createUser(User user,Authentication auth);

	public List<User> getAllUser();
	
	public User getUserById(long id);
	
	public String updateUser(long id,User updatedUser);
	
	public String deleteUser(long id) ;
	
	public Page<User> getUserPage(int page,int size);

	//for login
	public User getUserByEmail(String email);
	
	//for get Users of login manager only
	public List<User> getUsersByManager(Long managerId);
	
	//search user by name
	public List<User>searchUserByNameOrID(String keyword,Authentication auth);
	
	public Page<User>getManagerSpecificUser(int page,int size,Authentication auth);
	
	//change password
	public String changePassword(ChangePasswordRequest request,
            Authentication auth) ;
}
