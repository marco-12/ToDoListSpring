package it.dstech.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.dstech.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	public User findByUsername(String username);
	
	public User findByUsernameOrEmail(String username, String email);

}
