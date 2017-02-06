package com.mercadolibre.weather;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClimaServiceTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testInit() throws Exception {
		this.mockMvc.perform(get("/init")).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void testGetClimaDia() throws Exception {
		this.mockMvc.perform(get("/clima").param("dia", "56690")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string("{\"dia\":null,\"clima\":null,\"status\":\"Dia inexistente\"}"));
	}

}
