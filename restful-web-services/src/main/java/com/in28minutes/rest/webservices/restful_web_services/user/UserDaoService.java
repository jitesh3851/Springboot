package com.in28minutes.rest.webservices.restful_web_services.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	
	private static List<User> users = new ArrayList<>();
	private static int count = 1;
	static {
		users.add(new User(count++, "Adam", LocalDate.now().minusYears(30)));
		users.add(new User(count++, "Eve", LocalDate.now().minusYears(25)));
		users.add(new User(count++, "Jim", LocalDate.now().minusYears(20)));
	}
	
	public List<User> findAll(){
		return users;
	}

	public User getUser(int userId) {
		Optional<User> uop = users.stream().filter(u->u.getId().equals(userId)).findFirst();
		return uop.orElse(null);
	}

	public User createUser(User user) {
		user.setId(count++);
		users.add(user);
		return user;
	}

	public void deleteUser(int userId) {
		users.removeIf(u->u.getId().equals(userId));
	}

}
