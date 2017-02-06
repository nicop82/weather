package com.mercadolibre.weather.service.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.weather.model.ClimaDia;
import com.mercadolibre.weather.model.ClimaDiaRest;
import com.mercadolibre.weather.model.Planeta;
import com.mercadolibre.weather.service.ClimaServiceI;

@RestController
public class ClimaRestService {

	@Autowired
	private ClimaServiceI climaService;

	/**
	 * /init: Crea los planetas, calcula los climas de todos los días a 10 años 
	 * y guarda toda la información en la base de datos. Devuelve el resultado del pronóstico  
	 * 
	 * @return
	 */
	@RequestMapping("/init")
	public StringBuffer init() {
		StringBuffer sb = new StringBuffer();
		try {
			if (!climaService.isInit()) {
				sb = climaService.init();
			}
		} catch (Exception e) {
			sb.delete(0, sb.length());
			sb.append("A ocurrido un error inesperado en el sistema");
		}
		return sb;
	}

	/**
	 * Devuelve el clima en un día en particular
	 * 
	 * @param dia
	 * @return
	 */
	@RequestMapping("/clima")
	public ClimaDiaRest clima(@RequestParam(value="dia") Integer dia) {
		ClimaDiaRest climaDiaRest = new ClimaDiaRest();
		try {
			if (climaService.diaExists(dia)) {
				ClimaDia climaDia = climaService.getClimaDia(dia);
				climaDiaRest.setDia(climaDia.getNumeroDia());
				climaDiaRest.setClima(climaDia.getTipoClima().name());
				climaDiaRest.setStatus("ok");
			} else {
				climaDiaRest.setDia(null);
				climaDiaRest.setClima(null);
				climaDiaRest.setStatus("Dia inexistente");
			}
		} catch (Exception e) {
			System.out.println("Error inesperado en ClimaRestService.clima():" + e);
			climaDiaRest.setDia(null);
			climaDiaRest.setClima(null);
			climaDiaRest.setStatus("error");
		}
		return climaDiaRest;
	}
	
	/**
	 * Devuelve una Lista de planetas creados en la base
	 * 
	 * @return
	 */
	@RequestMapping("/planetas")
	public List<Planeta> planetas() {
		List<Planeta> planetaList = new ArrayList<Planeta>();
		try {
			planetaList = climaService.getPlanetas();
		} catch (Exception e) {
			System.out.println("Error inesperado en ClimaRestService.planetas():" + e);
		}
		return planetaList;
	}
}
