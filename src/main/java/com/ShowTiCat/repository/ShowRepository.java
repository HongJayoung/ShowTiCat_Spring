package com.ShowTiCat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ShowTiCat.vo.ShowVO;

public interface ShowRepository extends JpaRepository<ShowVO, Long>{

	@Query(value = "select * from show where category = ?1", nativeQuery = true)
	List<ShowVO> findByCategory(String category);

	List<ShowVO> findByShowNameContaining(String word);
}
