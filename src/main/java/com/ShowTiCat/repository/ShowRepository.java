package com.ShowTiCat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ShowTiCat.vo.ShowVO;

public interface ShowRepository extends CrudRepository<ShowVO, Long>{

	@Query(value = "select * from show where category = ?1", nativeQuery = true)
	List<ShowVO> findByCategory(String category);
}
