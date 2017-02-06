package com.mercadolibre.weather.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.mercadolibre.weather.model.ClimaDia;

/**
 *  Interface DAO que extiende de {@link org.springframework.data.repository.CrudRepository<Planeta, Long>} 
 * 	a cual ofrece los métodos CRUD necesarios para el dao. 
 * 	En vez de hacer un abstractDAO se extiende de esta interface.
 * 
 * @author nicolasp
 *
 */
@Transactional
public interface ClimaDiaDAO extends CrudRepository<ClimaDia, Long> {

	List<ClimaDia> findByDia(Integer dia);
	
	ClimaDia findFirstByOrderByCantidadLluviaDesc();
}
