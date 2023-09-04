package com.ShowTiCat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ShowTiCat.vo.CastMultikey;
import com.ShowTiCat.vo.CastVO;

public interface CastRepository extends CrudRepository<CastVO, CastMultikey>{

	@Query(value = "select * from cast where show_show_code = ?1", nativeQuery = true)
	List<CastVO> findByshowCode(Long showCode);
}
