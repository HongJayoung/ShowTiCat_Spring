package com.ShowTiCat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ShowTiCat.vo.CastMultikey;
import com.ShowTiCat.vo.CastVO;

public interface CastRepository extends JpaRepository<CastVO, CastMultikey>{

	@Query(value = "select * from cast where show_show_code = ?1", nativeQuery = true)
	List<CastVO> findByshowCode(Long showCode);

	@Modifying
	@Query(value = "delete from cast where show_show_code = ?1", nativeQuery = true)
	void deleteByShowCode(Long showCode);
}
