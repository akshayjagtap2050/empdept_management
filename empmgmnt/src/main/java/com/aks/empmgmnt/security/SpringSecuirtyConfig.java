package com.aks.empmgmnt.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aks.empmgmnt.handler.CustomAuthenticationSuccessHandler;
import com.aks.empmgmnt.service.impl.EmployeeServiceImpl;

@Configuration
@EnableWebSecurity
public class SpringSecuirtyConfig {

	Logger logger = LoggerFactory.getLogger(SpringSecuirtyConfig.class);

	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Bean // Influencing the filter chain, filtering what to allow and what to block, with
			// roles specification
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		logger.info("filterchain");
		http.authorizeHttpRequests((requests) -> requests.requestMatchers("/login", "/logout", "/public/**").permitAll()
				.requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/user/**").hasRole("USER").anyRequest()
				.authenticated())
				.formLogin((form) -> form.loginPage("/login").successHandler(customAuthenticationSuccessHandler)
						.permitAll());

		// we can add jwt filter
		// http.addFilterBefore(authenticationFilter,
		// UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean // publishing authentication manager bean directly, requires UserDetailsService
			// and password encoder
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

		return new ProviderManager(daoAuthenticationProvider);
	}

	@Bean // user detail service which provides a hardcoded user.
	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
		UserDetails userDetails = User.builder().passwordEncoder(encoder::encode).username("user").password("pwd")
				.roles("USER").build();
		return new InMemoryUserDetailsManager(userDetails);
	}

	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
