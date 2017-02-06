package com.mercadolibre.weather.service;

import java.util.List;

import com.mercadolibre.weather.model.ClimaDia;
import com.mercadolibre.weather.model.Planeta;

public interface ClimaServiceI {
	
	static String BEAN_NAME = "climaService";
	
	public StringBuffer init();
	
	public Boolean isInit();

	public boolean diaExists(Integer dia);

	public ClimaDia getClimaDia(Integer dia);
	
	public List<Planeta> getPlanetas();

	public ClimaDia getPicoMaximo();
}
