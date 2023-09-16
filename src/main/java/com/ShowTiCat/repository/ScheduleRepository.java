package com.ShowTiCat.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ShowTiCat.vo.PlaceVO;
import com.ShowTiCat.vo.ScheduleVO;

public interface ScheduleRepository extends JpaRepository<ScheduleVO, Long>{

	List<ScheduleVO> findAllByOrderByShowStart();
	
	@Query(value = "select * from schedule where show_show_code = ?1", nativeQuery = true)
	List<ScheduleVO> findByShowCode(Long showCode);

	@Query(value = "select * from schedule where place_place_id = ?1", nativeQuery = true)
	List<ScheduleVO> findByPlaceId(Long placeId);

	@Query(value = "select * from schedule where theater_theater_id = ?1", nativeQuery = true)
	List<ScheduleVO> findByTheaterId(Long theaterId);
	
	@Query(value = "select * from schedule where place_place_id = ?1 and show_start < ?2 and show_start >= current_timestamp "
							+ "order by show_show_code, theater_theater_id, show_start", nativeQuery = true)
	List<ScheduleVO> findByPlaceIfToday(Long placeId, Date dayAfter);
	
	@Query(value = "select * from schedule where place_place_id = ?1 and show_start >= ?2 and show_start < ?3 "
							+ "order by show_show_code, theater_theater_id, show_start", nativeQuery = true)
	List<ScheduleVO> findByPlaceAndShowStartBetween(PlaceVO place, Date day, Date dayAfter);
	
	@Query(value = "select * from schedule where show_show_code = ?1 and  place_place_id = ?2 and show_start >= ?3 and show_start < ?4 "
			+ "order by show_show_code, theater_theater_id, show_start", nativeQuery = true)
	List<ScheduleVO> findSchedule(Long showCode, Long placeId, Date day, Date dayAfter);
}
