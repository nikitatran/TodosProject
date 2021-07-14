package com.cognixia.jump.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	// configuration for the authentication (who are you?)
	@Override
	protected void configure( AuthenticationManagerBuilder auth ) throws Exception {
		
		auth.inMemoryAuthentication()
			.withUser("user1")
			.password( passwordEncoder().encode("123") )
			.roles("USER");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	// which users have access to which uri paths
	@Override
	protected void configure( HttpSecurity http ) throws Exception {
		
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/api/user").hasRole("user")
			.antMatchers("/api/useraccess").hasAnyRole("USER")
			.antMatchers("/api/all").permitAll()
			.antMatchers(HttpMethod.GET, "/api/todo").hasRole("USER")
			.antMatchers("/**").hasRole("USER")
			.and().httpBasic();
	}
}
