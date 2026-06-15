package com.seichigo.configue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}//패스워드 암호화 시키는 작업

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.formLogin(login-> login
				.loginPage("/user/login")
				.defaultSuccessUrl("/")
				.permitAll() //누구나 접근 가능
			)
			.logout(logout->logout //이 변수 역시 아무렇게나 적어도 상관없다
					//.logoutUrl("/logout") //post 방식
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
					.logoutSuccessUrl("/")
					.permitAll()
			)
			.authorizeHttpRequests(auth->auth //이 변수는 아무렇게나 적어도 상관없다. 참조변수이기에
					.requestMatchers("/port/write").hasRole("ADMIN")
					.requestMatchers("/port/edit").hasRole("ADMIN")
					.requestMatchers("/port/delete").hasRole("ADMIN")
					.anyRequest().permitAll()
					//관리자만 허용
			);
		
		return http.build();
	}
	
}
