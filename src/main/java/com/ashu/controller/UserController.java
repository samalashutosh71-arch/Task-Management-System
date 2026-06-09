package com.ashu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ashu.DTO.ChangePasswordRequest;
import com.ashu.entity.User;
import com.ashu.repositary.UserRepositary;
import com.ashu.service.UserService;

import jakarta.validation.Valid;
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/saves")
public class UserController 
{
	@Autowired
	private UserService userServ;
	@Autowired
	private UserRepositary userRepo;
	

	@PostMapping("/createUser")
	public ResponseEntity<User> createUser(
	        @RequestBody User user,
	        Authentication auth) {

	    User saved = userServ.createUser(user, auth);

	    return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}
	//all user
	@GetMapping("getAllUser")
	public ResponseEntity<List<User>> getAllUser()
	{
		List<User>users=userServ.getAllUser();
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
		
		
	}
	
	//getUserById
	@GetMapping("/{id}")
	public ResponseEntity<User>getUserById(@PathVariable int id)
	{
		User user=userServ.getUserById(id);
		return new ResponseEntity<User>(user,HttpStatus.OK);
			
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<String>updateUserById(@PathVariable int id ,@RequestBody User updatedUser)
	{
		String msg=userServ.updateUser(id, updatedUser);
		return new ResponseEntity<String>(msg,HttpStatus.OK);
			
	}
	@DeleteMapping("delete/{id}")
	public ResponseEntity<String>deleteUserById(@PathVariable int id)
	{
		String msg=userServ.deleteUser(id);
		return new ResponseEntity<String>(msg,HttpStatus.OK);
			
	}
	
	//pagination
	
	@GetMapping("/paged")
	public ResponseEntity<Page<User>>getUserPage(@RequestParam int page,@RequestParam int size)
	{
		Page<User>userPage=userServ.getUserPage(page, size);
		return new ResponseEntity<Page<User>>(userPage,HttpStatus.OK);
			
	}
	
	//get manager team users only
	@GetMapping("/manager/users")
	public ResponseEntity<List<User>> getUsersForManager(Authentication auth) {

		   Long managerId = Long.parseLong(auth.getName()); 

		    List<User> users = userRepo.findByManagerId(managerId);



	    return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	
	//search user by id or name 
	@GetMapping("/search/users")
	public ResponseEntity<List<User>> searchUserByNameOrId(@RequestParam String keyword,Authentication auth) 
	{
		List<User> users = userServ.searchUserByNameOrID(keyword,auth);


	    return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	//specific user pagination
	@GetMapping("/manager/pagination")
	public ResponseEntity<Page<User>>getManagerSpecificUserPage(@RequestParam int pageNo,@RequestParam int pageSize,Authentication auth)
	{
		
		Page<User> page = userServ.getManagerSpecificUser(pageNo, pageSize, auth);
		   System.out.println(auth.getAuthorities());
		return new ResponseEntity<Page<User>>(page,HttpStatus.OK);
		
	}
	
	//chnage pass
	@PutMapping("/change-password")
	public ResponseEntity<String> changePassword(
	        @RequestBody @Valid ChangePasswordRequest request,
	        Authentication auth) {

	    userServ.changePassword(request, auth);

	    return ResponseEntity.ok("Password changed successfully");
	}

	
	
}
