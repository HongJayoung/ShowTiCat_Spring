package com.ShowTiCat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ShowTiCat.vo.ReservDetailMultikey;
import com.ShowTiCat.vo.ReservDetailVO;

public interface ReservDetailRepository extends JpaRepository<ReservDetailVO, ReservDetailMultikey>{
	
	@Query(value = "select seat_num from reserv_detail JOIN RESERVATION r on(RESERVATION_ID = reservation_reservation_id) "
					+ "WHERE SCHEDULE_SCHEDULE_ID = ?1", nativeQuery = true)
	List<String> findByScheduleId(Long scheduleId);
}
