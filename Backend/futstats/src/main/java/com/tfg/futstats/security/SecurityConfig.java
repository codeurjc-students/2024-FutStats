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

        http.securityMatcher("/api/**")
            .exceptionHandling(handling -> handling.authenticationEntryPoint(unauthorizedHandlerJwt))

            .authorizeHttpRequests(auth -> auth
                // private endpoints
                .requestMatchers(HttpMethod.POST, "/api/v1/leagues/**", "/api/v1/teams/**", "/api/v1/players/**", "/api/v1/matches/**").hasRole("admin")
                .requestMatchers(HttpMethod.PUT, "/api/v1/leagues/**", "/api/v1/teams/**", "/api/v1/players/**", "/api/v1/matches/**").hasRole("admin")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/leagues/**", "/api/v1/teams/**", "/api/v1/players/**", "/api/v1/matches/**").hasRole("admin")
                .requestMatchers(HttpMethod.GET, "/api/v1/matches/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAnyRole("admin", "user")
                .requestMatchers(HttpMethod.POST, "/api/v1/users/**").hasRole("admin")
                .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasAnyRole("admin", "user")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasRole("admin")

                // public endpoints
                .anyRequest().permitAll()
            );

        http
            .formLogin(formLogin -> formLogin.disable()) 
            .csrf(csrf -> csrf.disable()) 
            .httpBasic(httpBasic -> httpBasic.disable()) 
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); 

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
