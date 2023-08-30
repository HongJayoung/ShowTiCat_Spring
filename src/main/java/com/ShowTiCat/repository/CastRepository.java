package com.ShowTiCat.repository;

import org.springframework.data.repository.CrudRepository;

import com.ShowTiCat.vo.CastMultikey;
import com.ShowTiCat.vo.CastVO;

public interface CastRepository extends CrudRepository<CastVO, CastMultikey>{

}
