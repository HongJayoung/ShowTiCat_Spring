package com.ShowTiCat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ShowTiCat.vo.ScheduleVO;

public interface ScheduleRepository extends JpaRepository<ScheduleVO, Long>{

	List<ScheduleVO> findAllByOrderByShowStart();
	
	@Query(value = "select * from schedule where show_show_code = ?1", nativeQuery = true)
	List<ScheduleVO> findByShowCode(Long showCode);

	@Query(value = "select * from schedule where place_place_id = ?1", nativeQuery = true)
	List<ScheduleVO> findByPlaceId(Long placeId);

	@Query(value = "select * from schedule where theater_theater_id = ?1", nativeQuery = true)
	List<ScheduleVO> findByTheaterId(Long theaterId);

}
