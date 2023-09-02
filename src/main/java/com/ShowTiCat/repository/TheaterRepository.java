package com.ShowTiCat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ShowTiCat.vo.TheaterVO;

public interface TheaterRepository extends CrudRepository<TheaterVO, Long>{

	@Query(value = "select * from theater where place_place_id = ?1", nativeQuery = true)
	List<TheaterVO> findByPlaceId(Long placeId);
}
