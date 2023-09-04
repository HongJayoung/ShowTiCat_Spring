package com.ShowTiCat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ShowTiCat.vo.ReviewVO;

public interface ReviewRepository extends CrudRepository<ReviewVO, Long>{

	@Query(value = "select * from review where member_member_id = ?1", nativeQuery = true)
	List<ReviewVO> findByMemberId(String memberId);
	
	@Query(value = "select * from review where show_show_code = ?1", nativeQuery = true)
	List<ReviewVO> findByshowCode(Long showCode);
}
