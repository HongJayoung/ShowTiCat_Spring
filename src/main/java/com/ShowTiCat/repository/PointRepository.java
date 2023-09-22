package com.ShowTiCat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ShowTiCat.vo.PointVO;

public interface PointRepository extends JpaRepository<PointVO, Long>{

	@Query(value = "select * from point where member_member_id = ?1 order by 1 desc", nativeQuery = true)
	List<PointVO> findByMemberId(String memberId);

}
