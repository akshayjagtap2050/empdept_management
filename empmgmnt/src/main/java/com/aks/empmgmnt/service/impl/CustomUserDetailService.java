package com.aks.empmgmnt.service.impl;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aks.empmgmnt.entity.User;
import com.aks.empmgmnt.exception.EmployeeNotFoundExcpetion;
import com.aks.empmgmnt.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> user = userRepository.findByUsername(username);
		
		if (user.isEmpty()) {
			throw new EmployeeNotFoundExcpetion("Invalid username or password.");
		}

		Set<GrantedAuthority> authorities = user.get().getRolList().stream()
				.map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

		return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
				user.get().getPassword(), authorities);

	}

}
