package it.dstech.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import it.dstech.models.Attivita;
import it.dstech.models.User;


public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDao registration);
    
    Long count();
    
    void deleteById(Long userId);
    
    void addActivities(User user, Attivita attivita);
	
}
