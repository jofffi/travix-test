package com.travix.medusa.busyflights;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class IndexApiTest {
	
	@InjectMocks
	private IndexApi indexApi;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		indexApi = context.getBean(IndexApi.class);
	}
	
	@Test
	public void testSearch_success() {
		try {
			MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/search")
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
					.content("{\"Origin\":\"AMS\",\"Destination\":\"LHR\",\"NumberOfPassengers\":1,\"DepartureDate\":\"2022-03-06\",\"ReturnDate\":\"2022-03-08\"}"))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
			System.out.println("RESULT: "+mvcResult.getResponse().getContentAsString());
			assertNotNull(mvcResult.getResponse().getContentAsString());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
