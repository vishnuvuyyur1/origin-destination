package com.klm.cases.df.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.klm.cases.df.model.AirportsEmbedded;
import com.klm.cases.df.model.Fare;
import com.klm.cases.df.model.FareDetail;
import com.klm.cases.df.model.Location;

import reactor.core.publisher.Mono;

@Service
public class OriginDestinationService {
private final WebClient webClient;
	
@Value("${mock.api.user}")
private String apiUser;

@Value("${mock.api.password}")
private String apiPassword;

private static final String AIRPORTS_PATH = "/airports";
private static final String FARES_PATH = "/fares";

	public OriginDestinationService(@Value("${mock.api.baseurl}") String apiBaseURL, WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl(apiBaseURL).build();
	}
	
	public Mono<List<Location>> getAirports() {
		return this.webClient.get()
                .uri(AIRPORTS_PATH)
                .headers(headers -> headers.setBasicAuth(apiUser, apiPassword))
                .retrieve()
                .bodyToMono(AirportsEmbedded.class).map(resp -> resp.get_embedded().getLocations());
	}
	
	public Mono<Fare> getFare(String originCode, String destinationCode) {
		return this.webClient.get()
                .uri(FARES_PATH + "/" + originCode + "/" + destinationCode)
                .headers(headers -> headers.setBasicAuth(apiUser, apiPassword))
                .retrieve()
                .bodyToMono(Fare.class);
	}
	
	
	public Mono<Location> getAirport(String airportCode) {
		return this.webClient.get()
                .uri(AIRPORTS_PATH+ "/" + airportCode)
                .headers(headers -> headers.setBasicAuth(apiUser, apiPassword))
                .retrieve()
                .bodyToMono(Location.class);
	}
	
	//Parallel service calls 
	public Mono<FareDetail> getFareDetail(String originCode, String destinationCode) {
		return Mono.zip(getFare(originCode,destinationCode), getAirport(originCode), getAirport(destinationCode)).flatMap(tuple ->{
			FareDetail fareDetail = new FareDetail();
			fareDetail.setFare(tuple.getT1());
			fareDetail.setOrigin(tuple.getT2());
			fareDetail.setDestination(tuple.getT3());
			 return Mono.just(fareDetail);
		});
	}

}
