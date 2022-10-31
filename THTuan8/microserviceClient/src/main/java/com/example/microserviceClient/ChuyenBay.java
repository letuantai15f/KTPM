package com.example.microserviceClient;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "chuyenbay")
public class ChuyenBay {
	
	@Id
	@Column(name = "MaCB")
	private String maCB;
	
	@Column(name = "GaDi")
	private String gaDi;
	
	@Column(name = "GaDen")
	private String gaDen;
	
	@Column(name = "DoDai")
	private int doDai;

	@Column(name = "GioDi")
	private Time gioDi;
	
	@Column(name = "GioDen")
	private Time gioDen;
	
	@Column(name = "ChiPhi")
	private int chiPhi;
}