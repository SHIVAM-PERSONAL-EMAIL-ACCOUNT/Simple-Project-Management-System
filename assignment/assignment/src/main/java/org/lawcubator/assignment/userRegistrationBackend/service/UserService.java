package org.lawcubator.assignment.userRegistrationBackend.service;

import java.util.ArrayList;

import org.lawcubator.assignment.userRegistrationBackend.model.User;
import org.lawcubator.assignment.userRegistrationBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service Implementation that loads User specific data. 
 */
@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	/**
	 * Registers a new User to the database
	 * 
	 * @param userToBeSaved A new User to be saved to the database
	 * @return The User registered with the provided credentials
	 * @throws DataIntegrityViolationException if the User username and password match with any other User's 
	 * credentials in the database
	 */
	public User saveUser(User userToBeSaved) {
		User savedUser = userRepository.save(userToBeSaved);
		return savedUser;
	}
	
	/**
	 * Locates a user in the database with the given username
	 * 
	 * @param username Username of the user that needs to be located
	 * @return Requested user, or null if the User was not present
	 */
	public User findUserByUsername(String username) {
		User foundUser = userRepository.findByUsername(username);
		return foundUser;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findUserByUsername(username);
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}
}
