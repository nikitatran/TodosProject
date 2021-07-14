package com.cognixia.jump.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	//configuration for the authentication (who are you?)
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		.withUser("user1")
		.password( passwordEncoder().encode("123") )
		.roles("USER");
	}
	
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
//		.antMatchers("/api/user").hasRole("USER")
//		.antMatchers("/api/useraccess").hasAnyRole("USER")
//		.antMatchers("/api/all").permitAll()
//		.antMatchers(HttpMethod.GET, "/api/todo").hasRole("USER")
		.antMatchers("/**").hasRole("USER")
		.and().httpBasic();
	
	}
}
