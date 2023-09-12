package com.ShowTiCat.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation")
public class ReservationVO implements Serializable { 
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long reservationId;
	
	@ManyToOne
	MemberVO member;
	
	@CreationTimestamp
	private Timestamp reservationDate;
	
	@ManyToOne
	ScheduleVO schedule;
	
	private String payment;
	
	private Long totalPrice;
	
	private String payYn;
	
	@ManyToOne
	PointVO point;
	
	@Transient
	String[] seat;
}
