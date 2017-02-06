package com.mercadolibre.weather.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mercadolibre.weather.service.ClimaServiceI;
import com.mercadolibre.weather.service.impl.ClimaServiceImpl;

@Configuration
public class SpringContext {

	@Bean(name = ClimaServiceI.BEAN_NAME)
	public ClimaServiceI getClimaServiceI() {
		return new ClimaServiceImpl();
	}
	
//	@Bean(name = PlanetaDAOI.BEAN_NAME)
//	public PlanetaDAOI getPedidoDAOI() {
//		return new PlanetaDAOImpl();
//	}
}
