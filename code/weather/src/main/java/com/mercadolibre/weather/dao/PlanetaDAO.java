package com.mercadolibre.weather.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.mercadolibre.weather.model.Planeta;


/**
 * Interface DAO que extiende de {@link org.springframework.data.repository.CrudRepository<Planeta, Long>} 
 * la cual ofrece los métodos CRUD necesarios para el dao. 
 * En vez de hacer un abstractDAO se extiende de esta interface.
 * 
 * @author nicolasp
 *
 */
@Transactional
public interface PlanetaDAO extends CrudRepository<Planeta, Long> { 

}
