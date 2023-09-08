package com.ShowTiCat.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.ShowTiCat.vo.MemberVO;

public interface SecurityService extends UserDetailsService{

	//security 사용자 인증
	//!!반드시 구현해야함!!
	UserDetails loadUserByUsername(String id);
	
	//중복아이디 체크
	MemberVO checkId(String id);
	
	//회원가입
	MemberVO insertMember(MemberVO member);
}
 