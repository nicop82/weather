package com.mercadolibre.weather.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "climadia")
public class ClimaDia {
	
	public ClimaDia () {
		
	}
	
	public ClimaDia(Integer nroDia, TipoClima tipoclima, Double cantLluvia) {
		this.dia = nroDia;
		this.tipoClima = tipoclima;
		this.cantidadLluvia = cantLluvia;
	}
	
	public ClimaDia(Integer nroDia, TipoClima tipoclima) {
		this.dia = nroDia;
		this.tipoClima = tipoclima;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	@Column(name = "numeroDia")
	private Integer dia;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipoClima")
	private TipoClima tipoClima;
	
	@Column(name = "cantidadLluvia")
	private Double cantidadLluvia;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getNumeroDia() {
		return dia;
	}

	public void setNumeroDia(Integer numeroDia) {
		this.dia = numeroDia;
	}

	public TipoClima getTipoClima() {
		return tipoClima;
	}

	public void setTipoClima(TipoClima tipoClima) {
		this.tipoClima = tipoClima;
	}

	public Double getCantidadLluvia() {
		return cantidadLluvia;
	}

	public void setCantidadLluvia(Double cantidadLluvia) {
		this.cantidadLluvia = cantidadLluvia;
	}
}
