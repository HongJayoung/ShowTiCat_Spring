package com.ShowTiCat.repository;

import org.springframework.data.repository.CrudRepository;

import com.ShowTiCat.vo.ReservDetailMultikey;
import com.ShowTiCat.vo.ReservDetailVO;

public interface ReservDetailRepository extends CrudRepository<ReservDetailVO, ReservDetailMultikey>{

}
