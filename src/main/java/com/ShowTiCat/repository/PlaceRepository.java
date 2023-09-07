package com.ShowTiCat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ShowTiCat.vo.PlaceVO;

public interface PlaceRepository extends JpaRepository<PlaceVO, Long>{

	List<PlaceVO> findAllByOrderByPlaceName();
}
