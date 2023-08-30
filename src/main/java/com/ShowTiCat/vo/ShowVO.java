package com.ShowTiCat.vo;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "show")
public class ShowVO implements Serializable { 
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long showCode;
	
	private String showName;
	
	private String director;
	
	private String trailer;
	
	private Date openingDate;
	
	private Long runningTime;
	
	private String category;
	
	private String summary;
	
	private String poster;
	
	private Long price;
	
}
