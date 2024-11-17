package com.aks.empmgmnt.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.aks.empmgmnt.handler.CustomAuthenticationSuccessHandler;
import com.aks.empmgmnt.service.impl.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SpringSecuirtyConfig {

	Logger logger = LoggerFactory.getLogger(SpringSecuirtyConfig.class);

	// create own userDetailService
	@Autowired
	CustomUserDetailService customUserDetailService;

	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	

	@Bean // Influencing the filter chain, filtering what to allow and what to block, with
			// roles specification
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		logger.info("filterchain");
		http.authorizeHttpRequests(authorize ->
		authorize.anyRequest().authenticated())
		.formLogin(
				(form) -> form.loginPage("/emp/login")
				.successHandler(customAuthenticationSuccessHandler)
				.permitAll());

		// http.addFilterBefore(authenticationFilter,
		// UsernamePasswordAuthenticationFilter.class)
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(customUserDetailService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

}
