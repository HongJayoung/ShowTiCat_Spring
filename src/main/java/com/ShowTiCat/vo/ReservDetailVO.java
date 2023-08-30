package com.ShowTiCat.vo;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "reservDetail")
public class ReservDetailVO {

	 @EmbeddedId
	 ReservDetailMultikey reservDetail;
}
