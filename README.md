# Proyecto de cambio climático para Mercado Libre
El proyecto consiste en un proyecto Maven que ofrece servicios REST. Dichos servicios se conectan con una base de datos HSQLCB en memoria donde guarda los datos de los días. 

# Tecnologías
- Java 1.8
- Maven
- Spring boot
- Hibernate
- HSQLDB
- Spring MVC
- Spring Injection

# Estructura de carpetas
- README.md
- Arquitectura.pdf: descripción de arquitectura/tecnologa del proyecto general del proyecto. 
- Code/weather: código del proyecto.

## Proyecto Wheater
Proyecto Maven/JAVA que expone servicios Rest utilizando una base de datos en memoria para facilitar su desarrollo y testeo. 

### Servicios expuestos
- /init: Inicializa el sistema. Se debe correr primero. Crea los planetas, calcula los climas de todos los días a 10 años y guarda toda la información en la base de datos. Devuelve el resultado del pronóstico calculado. 

URL: [http://localhost:8080/init](http://localhost:8080/init)

Ejemplo:
	Total sequía: 40
	Total lluvia: 609
	Total óptimo: 40
	Total día normal: 2960
	Pico máximo días: [570]
	Pico Máximo: 5948.526948796585

- /clima: Recibe un día dentro de los 10 años como parámetro y devuelve la condición cliḿática. 

URL: [http://localhost:8080/clima?dia=566](http://localhost:8080/clima?dia=566)

Ejemplo:
	{"dia":566,"clima":"normal","status":"ok"}

- /planetas: 

URL:[http://localhost:8080/planetas](http://localhost:8080/planetas)

Ejemplo:
	[
		{"id":10,"nombre":"Ferengis","velocidadAngular":1,"sentidoMovimiento":-1,"distancia":500},
		{"id":11,"nombre":"Vulcanos","velocidadAngular":5,"sentidoMovimiento":1,"distancia":1000},
		{"id":12,"nombre":"Betasoides","velocidadAngular":3,"sentidoMovimiento":-1,"distancia":2000}
	]

### Para agregar a IDE
Para agregar el projecto se debe agregar con un proyecto Maven existente.

### Correr en ambiente de desarrollo
El proyecto utiliza Spring boot para correr de forma local.
- $ cd weather
- $./mvnw spring-boot:run
- Para Debug: $./mvnw spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"

### Generar war para correr en server
- cd weather
- $./mvnw clean package

### Tests
Los tests se encuentran en com.mercadolibre.weather.ClimaServiceTest. Los mismos son tests de integración que se encargan de levantar los servicios a testear, llamar a dichos servicios e inspeccionar la respuesta. 
Para correr los test:
- $ cd weather
- $./mvnw test
