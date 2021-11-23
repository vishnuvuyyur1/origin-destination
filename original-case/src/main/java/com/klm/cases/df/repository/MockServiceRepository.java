package com.klm.cases.df.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import com.klm.cases.df.model.AirportsEmbedded;
import com.klm.cases.df.model.Fare;
import com.klm.cases.df.model.Location;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
public class MockServiceRepository {
	private final WebClient webClient;
	private String apiUser;
	private String apiPassword;

	private static final String AIRPORTS_PATH = "/airports";
	private static final String FARES_PATH = "/fares";
	
	public MockServiceRepository(@Value("${mock.api.baseurl}") String apiBaseURL, @Value("${mock.api.user}") String username,  
			@Value("${mock.api.password}") String password) {
		this.webClient = WebClient.builder().baseUrl(apiBaseURL).build();
		this.apiUser = username;
		this.apiPassword = password;
	}
	
	public Mono<List<Location>> getAirports() {
		return this.webClient.get()
                .uri(AIRPORTS_PATH)
                .headers(headers -> headers.setBasicAuth(apiUser, apiPassword))
                .retrieve()
                .bodyToMono(AirportsEmbedded.class).map(resp -> resp.get_embedded().getLocations());
	}
	
	public Mono<Fare> getFare(String originCode, String destinationCode) {
		log.info("check for parallel call");
		return this.webClient.get()
                .uri(FARES_PATH + "/" + originCode + "/" + destinationCode)
                .headers(headers -> headers.setBasicAuth(apiUser, apiPassword))
                .retrieve()
                .bodyToMono(Fare.class);
	}
	
	public Mono<Location> getAirport(String airportCode) {
		log.info("check for parallel call");
		return this.webClient.get()
                .uri(AIRPORTS_PATH+ "/" + airportCode)
                .headers(headers -> headers.setBasicAuth(apiUser, apiPassword))
                .retrieve()
                .bodyToMono(Location.class);
	}

}
