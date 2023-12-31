package com.ShowTiCat.security;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ShowTiCat.repository.MemberRepository;
import com.ShowTiCat.vo.MemberVO;

@Service
public class MemberServiceImpl implements SecurityService{
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private MemberRepository mRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder; // security config에서 Bean생성
	
	
	public  boolean passwordCompare(CharSequence pw, MemberVO member) {
		return  passwordEncoder.matches(pw, member.getPw());
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		//filter는 조건에 맞는것만 선택
		//map: 변형한다. 
	 
		UserDetails member = mRepo.findById(id)
				.filter(m -> m != null).map(m -> new SecurityUser(m)).get();
		httpSession.setAttribute("user", mRepo.findById(id).get());
		return member;
	}

	@Override
	public MemberVO checkId(String id) {
		MemberVO member = mRepo.findById(id).orElse(null);
		return member;
	}
	
	@Override
	public Boolean checkPw(MemberVO member, String pw) {
		return passwordCompare(pw, member);
	}
	
	@Override
	@Transactional
	public MemberVO insertMember(MemberVO member) {
		// 비밀번호 암호화...암호화되지않으면 로그인되지않는다.
		String pwd = passwordEncoder.encode(member.getPw());
		member.setPw(pwd);
		return mRepo.save(member);
	}
	 
}
 