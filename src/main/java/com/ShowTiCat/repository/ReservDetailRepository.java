package com.ShowTiCat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ShowTiCat.vo.ReservDetailMultikey;
import com.ShowTiCat.vo.ReservDetailVO;

public interface ReservDetailRepository extends JpaRepository<ReservDetailVO, ReservDetailMultikey>{

}
