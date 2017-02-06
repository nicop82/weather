package com.mercadolibre.weather.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolibre.weather.dao.ClimaDiaDAO;
import com.mercadolibre.weather.dao.PlanetaDAO;
import com.mercadolibre.weather.model.ClimaDia;
import com.mercadolibre.weather.model.Planeta;
import com.mercadolibre.weather.model.TipoClima;
import com.mercadolibre.weather.service.ClimaServiceI;

@Service(ClimaServiceI.BEAN_NAME)
public class ClimaServiceImpl implements ClimaServiceI {
	
	@Autowired
	private PlanetaDAO planetaDAO;
	
	@Autowired
	private ClimaDiaDAO climaDiaDAO;
	
	private static final int NUMBER_OF_DECIMALS = 2;

	private static Integer MAX_DAYS = 10 * 365;

	private static String F_NAME = "Ferengis";
	private static Integer F_DISTANCE_KM = 500;
	private static Integer F_GRADES_PER_DAY = 1;
	private static Integer F_WISE = -1; // Sentido horario

	private static String B_NAME = "Betasoides";
	private static Integer B_DISTANCE_KM = 2000;
	private static Integer B_GRADES_PER_DAY = 3;
	private static Integer B_WISE = -1; // Sentido horario

	private static String V_NAME = "Vulcanos";
	private static Integer V_DISTANCE_KM = 1000;
	private static Integer V_GRADES_PER_DAY = 5;
	private static Integer V_WISE = 1; // Sentido anti-horario

	private static Double picoMaximo = 0d;
	private static List<Integer> picoDayList;

	private static Integer sequiaCounter = 0;
	private static Integer lluviaCounter = 0;
	private static Integer optimoCounter = 0;
	private static Integer diaNormalCounter = 0;

	private static Double solx = 0d;
	private static Double soly = 0d;
	private static Double fx;
	private static Double fy;
	private static Double bx;
	private static Double by;
	private static Double vx;
	private static Double vy;

	private Planeta planetaFerengis;
	private Planeta planetaVulcanos;
	private Planeta planetaBetasoides;
	
	/* (non-Javadoc)
	 * @see com.mercadolibre.weather.service.ClimaServiceI#init()
	 * 
	 * Crea los planeteas, calcula los climas de todos los días y guarda toda la información en la base de datos
	 */
	public StringBuffer init() {
		StringBuffer sb = new StringBuffer();
		
		this.initPlanetas();
		this.calcularClimaDia(sb);
		
		return sb;
	}

	/**
	 * Calcula el clima de todos los días y devuelve un StringBuffer con la información calculada
	 * 
	 * @param sb
	 */
	private void calcularClimaDia(StringBuffer sb) {
		for (int day = 1; day < MAX_DAYS; day++) {
			calcularUbicacion(day);
			calcularClimaDia(day);
		}
		sb.append("Total sequía: " + sequiaCounter + "\n");
		sb.append("Total lluvia: " + lluviaCounter + "\n");
		sb.append("Total óptimo: " + optimoCounter + "\n");
		sb.append("Total día normal: " + diaNormalCounter + "\n");
		sb.append("Pico máximo días: " + picoDayList + "\n");
		sb.append("Pico Máximo: " + picoMaximo + "\n");
	}

	/**
	 * Calcula el clima del día
	 * 
	 * @param day
	 */
	private void calcularClimaDia(int day) {
		ClimaDia climaDia = new ClimaDia();
		climaDia.setNumeroDia(day);
		
		if (planetasAlineados()) {
			// Planetas alineados
			if (planetasAlineadosConSol()) {
				// Planetas y sol alineados
				climaDia.setTipoClima(TipoClima.sequia);
				sequiaCounter++;
			} else {
				// Solo planetas alineados
				climaDia.setTipoClima(TipoClima.optimo);
				optimoCounter++;
			}
		} else {
			// Planetas forman triangulo
			if (trianguloContieneSol()) {
				// Sol dentro de triangulo
				// Calculamos el perímetro para saber cual es la máxima distancia entre planetas
				Double perimetro = getPerimetro();
				savePicoMaximo(day, perimetro);
				climaDia.setTipoClima(TipoClima.lluvia);
				climaDia.setCantidadLluvia(perimetro);
				lluviaCounter++;
			} else {
				// Sol fuera del triangulo No hace nada
				climaDia.setTipoClima(TipoClima.normal);
				diaNormalCounter++;
			}
		}
		climaDiaDAO.save(climaDia);
	}

	private void savePicoMaximo(int day, Double perimetro) {
		if (perimetro > picoMaximo) {
			picoMaximo = perimetro;
			picoDayList = new ArrayList<Integer>();
			picoDayList.add(day);
		} else if (perimetro == picoMaximo) {
			picoDayList.add(day);
		}
	}

	/**
	 * Calcula las ubicaciones (x,y) de los planetas
	 * 
	 * @param day
	 */
	private void calcularUbicacion(int day) {
		// Ferengis
		fx = calcularX(F_DISTANCE_KM, day, F_WISE, F_GRADES_PER_DAY);
		fy = calcularY(F_DISTANCE_KM, day, F_WISE, F_GRADES_PER_DAY);
		
		// Betasoides
		bx = calcularX(B_DISTANCE_KM, day, B_WISE, B_GRADES_PER_DAY);
		by = calcularY(B_DISTANCE_KM, day, B_WISE, B_GRADES_PER_DAY);
		
		// Vulcanos
		vx = calcularX(V_DISTANCE_KM, day, V_WISE, V_GRADES_PER_DAY);
		vy = calcularY(V_DISTANCE_KM, day, V_WISE, V_GRADES_PER_DAY);
	}
	
	private Double calcularX(Integer distance, Integer day, Integer wise, Integer grades) {
		Double result = distance * Math.cos(Math.toRadians(day * wise * grades));
		return result;		
	}
	
	private Double calcularY(Integer distance, Integer day, Integer wise, Integer grades) {
		Double result = distance * Math.sin(Math.toRadians(day * wise * grades));
		return result;		
	}

	/**
	 * Crea los planetas
	 * 
	 * 
	 */
	private void initPlanetas() {
		planetaFerengis = new Planeta(F_NAME, F_GRADES_PER_DAY, F_WISE, F_DISTANCE_KM);
		planetaVulcanos = new Planeta(V_NAME, V_GRADES_PER_DAY, V_WISE, V_DISTANCE_KM);
		planetaBetasoides = new Planeta(B_NAME, B_GRADES_PER_DAY, B_WISE, B_DISTANCE_KM);
		
		planetaFerengis = planetaDAO.save(planetaFerengis);
		planetaVulcanos = planetaDAO.save(planetaVulcanos);
		planetaBetasoides = planetaDAO.save(planetaBetasoides);
	}

	/**
	 * Calcula el perímetro del triángulo formado por los tres planetas
	 * 
	 * @return
	 */
	private static Double getPerimetro() {
		Double distFB = Math.sqrt(Math.pow(bx - fx, 2) + Math.pow(by - fy, 2));
		Double distBV = Math.sqrt(Math.pow(vx - bx, 2) + Math.pow(vy - by, 2));
		Double distFV = Math.sqrt(Math.pow(vx - fx, 2) + Math.pow(vy - fy, 2));
		return distFB + distBV + distFV;
	}

	/**
	 * Calcula su el triangulo contiene dentro el Sol 
	 * 
	 * @return
	 */
	private static boolean trianguloContieneSol() {
		boolean result = false;
		// Calcular la orientación del triángulo. si todos son mayor a cero o
		// todos son menor a cero el punto se encuentra dentro del triangulo ya
		// que tienen misma orientación.
		// Formula orientación = (A1.x - A3.x) * (A2.y - A3.y) - (A1.y - A3.y) * (A2.x - A3.x)
		Double orientacionFBV = (fx - solx) * (by - soly) - (fy - soly) * (bx - solx);
		Double orientacionFBSol = (fx - solx) * (by - soly) - (fy - soly) * (bx - solx);
		Double orientacionFVSol = (fx - solx) * (vy - soly) - (fy - soly) * (vx - solx);
		Double orientacionBVSol = (bx - solx) * (vy - soly) - (by - soly) * (vx - solx);
		if ((orientacionFBV > 0 && orientacionFBSol > 0 && orientacionFVSol > 0 && orientacionBVSol > 0)
				|| (orientacionFBV < 0 && orientacionFBSol < 0 && orientacionFVSol < 0 && orientacionBVSol < 0)) {
			result = true;
		}
		return result;
	}

	/**
	 * Calcula si uno de los planetas esta alineado con el sol por medio de si pendiente
	 * 
	 * @return
	 */
	private static boolean planetasAlineadosConSol() {

		Double tangFB = (by - fy) / (bx - fx);
		Double tangFSol = (soly - fy) / (solx - fx);

		return (Math.abs(round(tangFB) - round(tangFSol)) < 0.0101)
				|| (Math.abs(round(tangFB)) > 1E10 && Math.abs(round(tangFSol)) > 1E10);
	}

	/**
	 * Calcula si los tres planetas están alineados según su pendiente
	 * 
	 * @return
	 */
	private static boolean planetasAlineados() {
		Double tangFB = (by - fy) / (bx - fx);
		Double tangVB = (by - vy) / (bx - vx);

		// Tomamos un delta de diferencia para tomarlo como iguales (0.0101 es la menor diferencia entre días)
		return (Math.abs(round(tangFB) - round(tangVB)) < 0.0101)
				|| (Math.abs(round(tangFB)) > 1E10 && Math.abs(round(tangVB)) > 1E10);
	}

	public static double round(double value) {
		long factor = (long) Math.pow(10, NUMBER_OF_DECIMALS); // NUMBER_OF_DECIMALS = 2
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@Override
	public Boolean isInit() {
		return (climaDiaDAO.count() > 0) && planetaDAO.count() > 0;
	}

	@Override
	public boolean diaExists(Integer dia) {
		return climaDiaDAO.findByDia(dia).size() == 1;
	}

	@Override
	public ClimaDia getClimaDia(Integer dia) {
		return climaDiaDAO.findByDia(dia).get(0);
	}

	@Override
	public List<Planeta> getPlanetas() {
		return (List<Planeta>) planetaDAO.findAll();
	}

	@Override
	public ClimaDia getPicoMaximo() {
		return climaDiaDAO.findFirstByOrderByCantidadLluviaDesc();
	}
}
