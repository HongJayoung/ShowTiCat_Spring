package com.ShowTiCat.repository;

import org.springframework.data.repository.CrudRepository;

import com.ShowTiCat.vo.MemberVO;

public interface MemberRepository extends CrudRepository<MemberVO, String>{

}
