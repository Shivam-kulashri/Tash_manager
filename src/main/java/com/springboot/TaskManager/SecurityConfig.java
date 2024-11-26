package com.springboot.TaskManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springboot.TaskManager.service.UserSecurityService;

/*
 * This class will bring JWT filter to Spring and integrate everything.
 * Along with this, this class will also point Spring to User table
 * */
@Configuration
public class SecurityConfig {
	@Autowired
	private UserSecurityService userSecurityService;

	@Autowired
	private JwtFilter jwtFilter;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf) -> csrf.disable()).authorizeHttpRequests(authorize -> authorize
				// .requestMatchers(HttpMethod.GET, "/auth/login").authenticated()
				// Auth
				.requestMatchers(HttpMethod.GET, "/api/token").permitAll()
				.requestMatchers(HttpMethod.POST, "/auth/sign-up").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/hello").authenticated()

				.requestMatchers(HttpMethod.POST, "/task/add").hasAuthority("User")
				.requestMatchers(HttpMethod.POST, "/task/all").hasAuthority("User")
				.requestMatchers(HttpMethod.POST, "/task/getById/{id}").hasAuthority("User")
				.requestMatchers(HttpMethod.POST, "/task/update/{id}").hasAuthority("User")
				.requestMatchers(HttpMethod.POST, "/task/deletebyid/{id}").hasAuthority("User").anyRequest()
				.permitAll())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userSecurityService);
		authenticationProvider.setPasswordEncoder(getEncoder());
		return authenticationProvider;
	}
}
/*
 * Spring needs a middleware to understand and decode jwt token
 * 
 */