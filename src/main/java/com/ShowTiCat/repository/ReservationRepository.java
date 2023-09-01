package com.ShowTiCat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ShowTiCat.vo.ReservationVO;
import com.ShowTiCat.vo.ReviewVO;

public interface ReservationRepository extends CrudRepository<ReservationVO, Long>{

	@Query(value = "select * from reservation where member_member_id = ?1", nativeQuery = true)
	List<ReservationVO> findByMemberId(String memberId);
}
