package com.adil.MunzMade.Services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.adil.MunzMade.Helper.Constants;
import com.adil.MunzMade.Helper.ResourceNotFoundExcepiton;
import com.adil.MunzMade.Model.User;
import com.adil.MunzMade.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(getClass());



    public User addUser(User user) {


	if (repository.findByEmail(user.getEmail()).isPresent()) {

		throw new IllegalStateException("User already exists with this email!");
	}

        String id = UUID.randomUUID().toString();

        user.setId(id);

        // encode the password

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // set user default role

        user.setRoleList(List.of(Constants.ROLE_USER));

        // set a default profile picture
	
        user.setProfilePic("/images/user.png");
	
	
	// String emailToken = UUID.randomUUID().toString();
	
	// user.setEmailToken(emailToken);

        User savedUser = repository.save(user);

	// String emailLink = Helper.getLinkForEmailVerification(emailToken);

	// emailService.sendEmail(savedUser.getEmail(), "Verify Your Account - MunzMade", 
	// "Hi " + savedUser.getName() + ",\n\n" + 
	// "Click the link below to verify your account:\n\n" + 
	// emailLink + "\n\n" +
	// "Welcome to MunzMade!\n" +
	// "Adil");

	return savedUser;
	
    }

    public Optional<User> getUserById(String id) {

        return repository.findById(id);
    }

    public User updateUser(String id, User user) {

        User user1 = repository.findById(id).orElseThrow(()-> new ResourceNotFoundExcepiton("USER NOT FOUND!"));

        return repository.save(user1);
    }

    public void deleteUser(String id) {

        repository.findById(id).orElseThrow(()-> new ResourceNotFoundExcepiton("USER NOT FOUND!"));

        repository.deleteById(id);
    }

    public boolean userExists(String id, String email) {


        User user1 = repository.findById(id).orElse(null);

        return user1 != null ? true : false;
    }


    public User getUserByEmail(String email) {

	User user = new User();

	logger.info("Fetched User object: {}", user);

        return repository.findByEmail(email).orElse(null);
    }

//     public User getUserByEmailToken(String emailToken) {

// 	return repository.findUserByEmailToken(emailToken).orElse(null);
//     }

}
