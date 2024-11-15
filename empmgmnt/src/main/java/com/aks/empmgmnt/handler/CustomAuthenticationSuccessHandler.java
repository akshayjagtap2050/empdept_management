package com.aks.empmgmnt.handler;

import java.io.IOException;

import org.hibernate.mapping.Set;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		/*
		 * Set<String> roles =
		 * AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		 * 
		 * if (roles.contains("ROLE_ADMIN")) {
		 * response.sendRedirect("/admin/dashboard"); } else if
		 * (roles.contains("ROLE_USER")) { response.sendRedirect("/user/homepage"); }
		 * else { response.sendRedirect("/default"); }
		 */
		
		response.sendRedirect("/homepage");
	}

}