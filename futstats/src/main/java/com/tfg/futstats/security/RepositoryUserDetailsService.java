package com.tfg.futstats.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tfg.futstats.models.User;
import com.tfg.futstats.repositories.UserRepository;

@Service
public class RepositoryUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Value("${security.user}")
	private String name;

	@Value("${security.encodedPassword}")
	private String encodedPassword;

	@Override
	public UserDetails loadUserByUsername(String username) {

		User user;
		try {
			user = userRepository.findByNameIgnoreCase(username)
					.orElseThrow(() -> new UsernameNotFoundException("User not found"));

			List<GrantedAuthority> roles = new ArrayList<>();
			
			roles.add(new SimpleGrantedAuthority("ROLE_" + user.getRoles()));

			return new org.springframework.security.core.userdetails.User(user.getName(),
					user.getPassword(), roles);

		} catch (UsernameNotFoundException e) {

			if (!username.equals(name))
				throw new UsernameNotFoundException("User not found");

			List<GrantedAuthority> roles = new ArrayList<>();

			roles.add(new SimpleGrantedAuthority("ROLE_" + "admin"));

			return new org.springframework.security.core.userdetails.User(name,
					encodedPassword, roles);

		}

	}
}