package com.ShowTiCat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ShowTiCat.vo.ReservationVO;

public interface ReservationRepository extends JpaRepository<ReservationVO, Long>{

	@Query(value = "select * from reservation where member_member_id = ?1", nativeQuery = true)
	List<ReservationVO> findByMemberId(String memberId);

	@Query(value = "select * from reservation where schedule_schedule_id = ?1", nativeQuery = true)
	List<ReservationVO> findByScheduleId(Long scheduleId);
}
