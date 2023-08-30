package com.ShowTiCat.vo;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name = "member")
public class MemberVO implements Serializable { 
	private static final long serialVersionUID = 1L;
	
	@Id
	private String memberId;
	
	private String pw;
	
	private String name;
	
	private String email;
	
	private String phone;
	
	private Date birth;
	
	private String gender;
	
	@CreationTimestamp
	private Timestamp regDate;
	
	private Long mPoint;
	
	@ManyToOne
	PlaceVO place;

}
