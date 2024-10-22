package com.tfg.futstats;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.tfg.futstats.models.User;
import com.tfg.futstats.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.annotation.PostConstruct;

@Component
public class DatabaseInitializer {

	@Autowired
	private UserRepository ur;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	void init() throws IOException {

		// Admin a = new Admin("admin", passwordEncoder.encode("pass"));
		// ur.save(a);

		for (int i = 0; i < 10; i++) {

			User u = new User("user" + i, passwordEncoder.encode("pass"), "user");
			ur.save(u);

		}

		for (int i = 10; i < 20; i++) {

			User u = new User("admin" + i, passwordEncoder.encode("pass"), "admin");
			ur.save(u);
		}
	}

}
