package com.ShowTiCat.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.extern.java.Log;

@Log
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	MemberServiceImpl memberService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // Spring Security에서 제공하는 비밀번호 암호화 객체
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		//System.out.println("configureGlobal...." + auth);
		auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/images/**");

	}

	protected void configure(HttpSecurity http) throws Exception {
		//http.csrf().disable(); //cfor 설정 해제 //초기 개발 시에만 설정
		
		http.headers().frameOptions().sameOrigin();
		
		http.authorizeRequests()
				 //.antMatchers("/**").access("hasRole('USER')") //USER 권한을 가진 경우 접속 허용
				.antMatchers("/ShowTiCat/**", "/show/**",  "/place/**", "/reservation").permitAll() //누구나 접속 가능
				.anyRequest().authenticated(); //위에서 설정한 경로 이외의 나머지는 무조건 인증을 완료해야 접근이 가능
				
		http.formLogin()
				.loginPage("/ShowTiCat/login") //로그인 페이지
				.loginProcessingUrl("/ShowTiCat/login") //로그인 버튼을 누르면 이동하는 페이지
				.successHandler( // 로그인 성공 후 핸들러
					new AuthenticationSuccessHandler() { // 익명 객체 사용
						@Override
						public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
							if(authentication.getName().equals("admin")) {
								response.sendRedirect("/ShowTiCat/admin");
							} else {
								response.sendRedirect("/ShowTiCat");
							}
						}
					})
				.failureHandler( // 로그인 실패 후 핸들러
					new AuthenticationFailureHandler() { // 익명 객체 사용
						@Override
						public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
							System.out.println("exception: " + exception.getMessage());
							response.sendRedirect("/ShowTiCat/login");
						}
					});
				//.defaultSuccessUrl("/ShowTiCat");

		http.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //로그아웃 요청이 들어오면 
				.logoutSuccessUrl("/ShowTiCat") // 로그아웃 성공 시 주소
				.invalidateHttpSession(true); //세션 초기화
		
		http.exceptionHandling().accessDeniedPage("/ShowTiCat/accessDenied"); 
	}

}
