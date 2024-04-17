package com.alianz.practice.alianz_practice.config;

import org.apache.tomcat.util.net.DispatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

	@Autowired
	JWTAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	AuthenticationProvider authenticationProvider;

	@Value("${alianz.security.enabled}")
	public boolean ALLIANZ_SECURITY_ENABLED;

	private static final String AUTH_WHITELIST = "/alianz/auth/**";

	private static final String H2_CONSOLE = "/h2-console/**";

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		if (ALLIANZ_SECURITY_ENABLED) {
			http.cors()
					.and()
					.csrf()
					.disable()
					.authorizeHttpRequests()
					.requestMatchers(AUTH_WHITELIST, H2_CONSOLE)	
					.permitAll()
					.anyRequest()
					.authenticated()
					.and()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
					.authenticationProvider(authenticationProvider)
					.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
			return http.build();
		} else {
			return http
					.authorizeRequests()
					.anyRequest().permitAll()
					.and()
					.csrf().disable()
					.headers().frameOptions().disable()
					.and()
					.build();
		}
	}

	public void setALLIANZ_SECURITY_ENABLED(boolean aLLIANZ_SECURITY_ENABLED) {
		ALLIANZ_SECURITY_ENABLED = aLLIANZ_SECURITY_ENABLED;
	}

	
}
