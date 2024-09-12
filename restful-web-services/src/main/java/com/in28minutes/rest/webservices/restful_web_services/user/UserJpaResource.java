package com.in28minutes.rest.webservices.restful_web_services.user;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restful_web_services.jpa.PostRepository;
import com.in28minutes.rest.webservices.restful_web_services.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping(path="/jpa/users")
	public List<User> retrieveAllUsers(){
		return repository.findAll();
	}
	
	@GetMapping(path="/jpa/users/{userId}")
	public EntityModel<User> getUser(@PathVariable int userId) {
		 Optional<User> user = repository.findById(userId);
		 if(user.isEmpty()) {
			 throw new UserNotFound("userId:"+userId);
		 }
		 EntityModel<User> entityModel = EntityModel.of(user.get());
		 WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(
				 WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
		 entityModel.add(link.withRel("all-users"));
		return entityModel;
	}
	
	@PostMapping(path="/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		 User createdUser = repository.save(user);
		 URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
		 .path("/{userId}").buildAndExpand(createdUser.getId()).toUri();
		 ResponseEntity<User> responseEntity = ResponseEntity.created(uri).build();
		 return responseEntity;
	}
	
	@DeleteMapping(path="/jpa/users/{userId}")
	public void deleteUser(@PathVariable int userId) {
		repository.deleteById(userId);
	}
	
	@GetMapping(path="/jpa/users/{userId}/posts")
	public List<Post> retrievePostsForAUser(@PathVariable int userId) {
		Optional<User> user = repository.findById(userId);
		 if(user.isEmpty()) {
			 throw new UserNotFound("userId:"+userId);
		 }
		 return user.get().getPosts();
	}
	
	
	@PostMapping(path="/jpa/users/{userId}/posts")
	public ResponseEntity<User> createPostForUser(@PathVariable int userId, @Valid @RequestBody Post post) {
		Optional<User> user = repository.findById(userId);
		 if(user.isEmpty()) {
			 throw new UserNotFound("userId:"+userId);
		 }
		 post.setUser(user.get());
		 Post savedPost = postRepository.save(post);
		 
		 URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				 .path("/{postId}").buildAndExpand(savedPost.getId()).toUri();
				 ResponseEntity<User> responseEntity = ResponseEntity.created(uri).build();
				 return responseEntity;
	}
	

}
