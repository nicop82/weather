package com.mercadolibre.weather.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "planeta")
public class Planeta {
	
	public Planeta() {
		
	}
	
	public Planeta(String nombre, Integer velocidad, Integer sentido, Integer dist) {
		this.nombre = nombre;
		this.velocidadAngular = velocidad;
		this.sentidoMovimiento = sentido;
		this.distancia = dist;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "velocidadAngular")
	private Integer velocidadAngular;
	
	@Column(name = "sentidoMovimiento")
	private Integer sentidoMovimiento; // horario= -1 | anti-horario = 1
	
	@Column(name = "distancia")
	private Integer distancia; // al sol en km

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getVelocidadAngular() {
		return velocidadAngular;
	}

	public void setVelocidadAngular(Integer velocidadAngular) {
		this.velocidadAngular = velocidadAngular;
	}

	public Integer getSentidoMovimiento() {
		return sentidoMovimiento;
	}

	public void setSentidoMovimiento(Integer sentidoMovimiento) {
		this.sentidoMovimiento = sentidoMovimiento;
	}
	
	public Integer getDistancia() {
		return distancia;
	}

	public void setDistancia(Integer distancia) {
		this.distancia = distancia;
	}
}
