package com.ShowTiCat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ShowTiCat.vo.TheaterVO;

public interface TheaterRepository extends JpaRepository<TheaterVO, Long>{

	@Query(value = "select * from theater where place_place_id = ?1 order by 1", nativeQuery = true)
	List<TheaterVO> findByPlaceId(Long placeId);

	@Modifying
	@Query(value = "delete from theater where place_place_id = ?1", nativeQuery = true)
	void deleteByPlaceId(Long placeId);
}
