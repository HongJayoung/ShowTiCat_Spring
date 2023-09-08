package com.ShowTiCat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ShowTiCat.vo.MemberVO;

public interface MemberRepository extends JpaRepository<MemberVO, String>{

	@Query(value = "select * from member where name = ?1 and email =?2", nativeQuery = true)
	MemberVO findbyNameAndEmail(String name, String email);

	@Query(value = "select * from member where member_id =?1 and name = ?2 and email =?3", nativeQuery = true)
	MemberVO findbyIdAndNameAndEmail(String memberId, String name, String email);

}
