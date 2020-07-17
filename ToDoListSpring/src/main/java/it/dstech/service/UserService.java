package it.dstech.service;

import java.util.List;
import it.dstech.models.User;

public interface UserService {
	List<User> findAll();

	Long count();

	void deleteById(Long userId);

	void add(User user);

	boolean findByUsername(User user);
	
	boolean findByUsernameOrEmail(User user);
}
