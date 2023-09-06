package com.ShowTiCat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ShowTiCat.vo.MemberVO;

public interface MemberRepository extends JpaRepository<MemberVO, String>{

}
