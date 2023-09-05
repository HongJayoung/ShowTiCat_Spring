package com.ShowTiCat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ShowTiCat.vo.PointVO;

public interface PointRepository extends CrudRepository<PointVO, Long>{

	@Query(value = "select * from point where member_member_id = ?1", nativeQuery = true)
	List<PointVO> findByMemberId(String memberId);

}
