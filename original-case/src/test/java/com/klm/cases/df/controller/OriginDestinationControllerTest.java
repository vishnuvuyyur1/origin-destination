package com.klm.cases.df.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import com.klm.cases.df.service.OriginDestinationService;
import com.klm.cases.df.test_data.OriginDestinationTestData;

@WebMvcTest(controllers = OriginDestinationController.class)
public class OriginDestinationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private OriginDestinationService originDestinationService;
	private OriginDestinationTestData originDestinationTestData;
	
	@BeforeEach
	void init() throws IOException {
		originDestinationTestData = new OriginDestinationTestData();
	}
	
	@Test
	void getAirportsTest() throws Exception {
		given(originDestinationService.getAirports()).willReturn( originDestinationTestData.getAirports());
		MvcResult mvcResult =  mockMvc.perform(get("/origin-destination/airports")).andExpect(request().asyncStarted()).andDo(MockMvcResultHandlers.log()).andReturn();
		mockMvc.perform(asyncDispatch(mvcResult)).andExpect(status().isOk())
		.andExpect(jsonPath("$.length()", is(2)))
		.andExpect(jsonPath("$.[0].code", is("code1")))
		.andDo(MockMvcResultHandlers.print());
	    
		Mockito.verify(originDestinationService, Mockito.times(1)).getAirports();
	}

}