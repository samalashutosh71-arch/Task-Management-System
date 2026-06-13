

package com.ashu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ashu.DTO.ChangePasswordRequest;
import com.ashu.customexception.ResourceNotFoundException;
import com.ashu.entity.User;
import com.ashu.repositary.UserRepositary;
@Service
public class UserServiceImpl implements UserService
{
	 //@Autowired
	 //private PasswordEncoder passwordEncoder;
	 
	@Autowired
	private UserRepositary userRepo;

	
	@Override
	public User createUser(User user, Authentication auth) {

	    Long managerId = Long.parseLong(auth.getName());  //auth class method 
	 

	    User manager = userRepo.findById(managerId)
	            .orElseThrow(() -> new RuntimeException("Manager not found"));

	    //set the manager to user
	    user.setManager(manager);
	    
	    


	    // set task
	    if (user.getTasks() != null) {
	        user.getTasks().forEach(task -> {
	            task.setUser(user);
	        });
	    }
	    String rawPassword = user.getName().substring(0,4) + "@123";
	    user.setPassword(rawPassword);
	    //  user.setPassword(passwordEncoder.encode(rawPassword));
	   
	    return userRepo.save(user);
	   

	}

	@Override
	public List<User> getAllUser() 
	{
		List<User> users = userRepo.findAll();
		return users;
		
		
	}
	@Override
	public User getUserById(long id) 
	{
		User user=userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with id: " + id));
		
		return user;
	}
	@Override
	public String updateUser(long id, User updatedUser) {
		User user=userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with id: " + id));
		user.setName(updatedUser.getName());
	    user.setEmail(updatedUser.getEmail());
	    user.setRole(updatedUser.getRole());
	    userRepo.save(user);
		return "User is Updated for id: "+user.getId();
	}
	
	//delete
	@Override
	public String deleteUser(long id) {
		User user=userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with id: " + id));
		userRepo.deleteById(id);
		return "user deleted with Task for userid :"+user.getId();
	}
	
	//pagination user
	@Override
	public Page<User> getUserPage(int page, int size)
	{
		Pageable pageable = PageRequest.of(page, size);
		Page<User>userPage=userRepo.findAll(pageable);
		
		return userPage;
	}
	//pagination manger specific user
	 
	public Page<User>getManagerSpecificUser(int page,int size,Authentication auth)
	{
		Long managerId=Long.parseLong(auth.getName());
		Pageable pageable=PageRequest.of(page, size,Sort.by("name").ascending());
		
		Page<User> specificPage = userRepo.findByManagerId(managerId, pageable);  
		return specificPage;
	     
	}
	
	//findByMail
	@Override
	public User getUserByEmail(String email) 
	{
		return userRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("no user found"));

	}
	@Override
	public List<User> getUsersByManager(Long managerId) {
		// TODO Auto-generated method stub
		return userRepo.findByManagerId(managerId);
	}
	
	@Override
	public List<User> searchUserByNameOrID(String keyword,Authentication auth) 
	{
		
		  Long managerId = Long.parseLong(auth.getName());

		    if (keyword == null || keyword.trim().isEmpty()) {
		        throw new ResourceNotFoundException("Keyword cannot be empty");
		    }

		    keyword = keyword.trim();

		    //ID search //d+ use to checks whether the keyword contains only digits (0-9).
		    if (keyword.matches("\\d+")) {

		        User user = userRepo.findById(Long.parseLong(keyword))
		                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

		        // ensure belongs to same manager if no manager match or no maneger assign then emptylist
		        if (user.getManager() == null ||
		            !user.getManager().getId().equals(managerId)) {
		            return List.of();
		        }

		        return List.of(user);
		    }

		    // Name search (only manager's users)
		    return userRepo.searchByNameAndManager(keyword, managerId);
		
	}
	
	//userpasswordchange
	@Override
	public String changePassword(ChangePasswordRequest request,
	                             Authentication auth) {
//login user/manager id
	    Long userId = Long.parseLong(auth.getName());

	    User user = userRepo.findById(userId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("User Not Found"));

	    // Check old password
	    if (!user.getPassword().equals(request.getOldPassword())) {
	        throw new RuntimeException("Old Password is Incorrect");
	    }

	    // Check confirm password
	    if (!request.getNewPassword()
	            .equals(request.getConfirmPassword())) {

	        throw new RuntimeException(
	                "New Password and Confirm Password do not Match");
	    }

	    // Update password
	    user.setPassword(request.getNewPassword());

	    userRepo.save(user);

	    return "Password Changed Successfully";
	}

}
