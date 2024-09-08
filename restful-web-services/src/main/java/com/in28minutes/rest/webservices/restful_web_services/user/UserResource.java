package com.in28minutes.rest.webservices.restful_web_services.user;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {
	
	@Autowired
	private UserDaoService service;
	
	@GetMapping(path="users")
	public List<User> retrieveAllUsers(){
		return service.findAll();
	}
	
	@GetMapping(path="users/{userId}")
	public User getUser(@PathVariable int userId) {
		 User user = service.getUser(userId);
		 if(user==null) {
			 throw new UserNotFound("userId:"+userId);
		 }
		return user;
	}
	
	@PostMapping(path="users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		 User createdUser = service.createUser(user);
		 URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
		 .path("/{userId}").buildAndExpand(createdUser.getId()).toUri();
		 ResponseEntity<User> responseEntity = ResponseEntity.created(uri).build();
		 return responseEntity;
	}
	
	@DeleteMapping(path="/users/{userId}")
	public void deleteUser(@PathVariable int userId) {
		service.deleteUser(userId);
	}

}
