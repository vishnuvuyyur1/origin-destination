package com.klm.cases.df.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.klm.cases.df.repository.MockServiceRepository;
import com.klm.cases.df.test_data.OriginDestinationTestData;

import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class OriginDestinationServiceTest {
	@InjectMocks
	private  OriginDestinationService  originDestinationService;
	
	@Mock
	private MockServiceRepository mockServiceRepository;
	
	private OriginDestinationTestData originDestinationTestData;
	
	@BeforeEach
	void init() throws IOException {
		originDestinationTestData = new OriginDestinationTestData();
	}
	
	@Test
	public void getAirportsTest() throws Exception {
		when(mockServiceRepository.getAirports()).thenReturn(originDestinationTestData.getAirports());
		// Asserting response
				StepVerifier.create(originDestinationService.getAirports()).assertNext(res -> {
					assertNotNull(res);
					assertEquals(2, res.size());
					assertEquals("code1", res.get(0).getCode());
					assertEquals("desc1", res.get(0).getDescription());
					assertEquals("name1", res.get(0).getName());
				}).verifyComplete();
	}
}
