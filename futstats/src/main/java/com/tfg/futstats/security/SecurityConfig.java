package com.tfg.futstats.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tfg.futstats.security.jwt.JwtRequestFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
	public RepositoryUserDetailsService userDetailService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	private UnauthorizedHandlerJwt unauthorizedHandlerJwt;
    
    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

    @Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

    @Bean
	public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {

		http.authenticationProvider(authenticationProvider());
		http
				.securityMatcher("/api/**")
				.exceptionHandling(handling -> handling.authenticationEntryPoint(unauthorizedHandlerJwt));

		http.authorizeHttpRequests(authorize -> authorize

				.requestMatchers(HttpMethod.POST, "/api/v1/login").permitAll()
				.requestMatchers("/api/signin").permitAll()

				.requestMatchers(HttpMethod.POST, "/api/v1/leagues").hasAnyRole("admin")
				.requestMatchers(HttpMethod.DELETE, "/api/v1/leagues/*").hasAnyRole("admin")
                .requestMatchers(HttpMethod.PUT, "/api/v1/leagues/*").hasAnyRole("admin")

				.requestMatchers(HttpMethod.POST, "/api/v1/teams").hasAnyRole("admin")
				.requestMatchers(HttpMethod.DELETE, "/api/v1/teams/*").hasAnyRole("admin")
                .requestMatchers(HttpMethod.PUT, "/api/v1/teams/*").hasAnyRole("admin")

				.requestMatchers(HttpMethod.POST, "/api/v1/players").hasAnyRole("admin")
				.requestMatchers(HttpMethod.DELETE, "/api/v1/players/*").hasAnyRole("admin")
                .requestMatchers(HttpMethod.PUT, "/api/v1/players/*").hasAnyRole("admin")

                .requestMatchers(HttpMethod.POST, "/api/v1/matches").hasAnyRole("admin")
				.requestMatchers(HttpMethod.DELETE, "/api/v1/matches/*").hasAnyRole("admin")
                .requestMatchers(HttpMethod.PUT, "/api/v1/matches/*").hasAnyRole("admin")

				.requestMatchers(HttpMethod.POST, "/api/v1/playersMatch").hasAnyRole("admin")
				.requestMatchers(HttpMethod.DELETE, "/api/v1/playersMatch/*").hasAnyRole("admin")
				.requestMatchers(HttpMethod.PUT, "/api/v1/playersMatch/*").hasAnyRole("admin")

				.requestMatchers(HttpMethod.GET,"/api/v1/users").hasAnyRole("admin")
				.requestMatchers(HttpMethod.PUT,"/api/v1/users").hasAnyRole("admin","user")
				.requestMatchers(HttpMethod.POST,"/api/v1/users").hasAnyRole("admin","user")
				.requestMatchers(HttpMethod.DELETE,"/api/v1/users").hasAnyRole("admin","user")
				.requestMatchers("/api/v1/users/*").hasAnyRole("admin","user")

				.anyRequest().permitAll()
            )
            .formLogin(formLogin -> formLogin
						.loginPage("/login")
						.failureUrl("/login")
						.defaultSuccessUrl("/logged")
						.permitAll())
			.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login")
						.permitAll());

		// Disable Form login Authentication
		http.formLogin(formLogin -> formLogin.disable());

		// Disable CSRF protection (it is difficult to implement in REST APIs)
		http.csrf(csrf -> csrf.disable());

		// Disable Basic Authentication
		http.httpBasic(httpBasic -> httpBasic.disable());

		// Stateless session
		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// Add JWT Token filter
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();

	}
}
