package com.klm.cases.df.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klm.cases.df.test_data.OriginDestinationTestData;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import reactor.test.StepVerifier;

public class OriginDestinationServiceTest {
	private OriginDestinationTestData originDestinationTestData;
	private OriginDestinationService originDestinationService;
	private MockWebServer mockWebServer;
	private ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	void init() throws IOException {
		originDestinationTestData = new OriginDestinationTestData();
		 this.mockWebServer = new MockWebServer();
		 this.mockWebServer.start();
		 originDestinationService = new OriginDestinationService(mockWebServer.url("/").toString(),"user","pwd");
	}
	
	@AfterEach
	void tearDown() throws IOException {
		this.mockWebServer.shutdown();
	}

	@Test
	public void getAirportsTest() throws Exception {
		//Mock the call and add a response
		mockWebServer.enqueue(new MockResponse()
	    	      .setBody(mapper.writeValueAsString(originDestinationTestData.getAirportsEmbedded()))
	    	      .addHeader("Content-Type", "application/json"));

		// Asserting response
		StepVerifier.create(originDestinationService.getAirports()).assertNext(res -> {
			assertNotNull(res);
			assertEquals(2, res.size());
			assertEquals("code1", res.get(0).getCode());
		}).verifyComplete();
		
		RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/airports", recordedRequest.getPath());
	}
}
