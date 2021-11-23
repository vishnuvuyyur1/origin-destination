package com.klm.cases.df.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.klm.cases.df.model.FareDetail;
import com.klm.cases.df.model.Location;
import com.klm.cases.df.repository.MockServiceRepository;

import reactor.core.publisher.Mono;

@Service
public class OriginDestinationService implements IOriginDestinationService{
	private MockServiceRepository mockServiceRepository;

	public OriginDestinationService(MockServiceRepository mockServiceRepository) {
		this.mockServiceRepository = mockServiceRepository;
	}
	
	@Override
	public Mono<List<Location>> getAirports() {
		return mockServiceRepository.getAirports();
	}
	
	//Parallel service calls 
	@Override
	public Mono<FareDetail> getFareDetail(String originCode, String destinationCode) {
		return Mono.zip(mockServiceRepository.getFare(originCode,destinationCode), mockServiceRepository.getAirport(originCode), mockServiceRepository.getAirport(destinationCode)).flatMap(tuple ->{
			FareDetail fareDetail = new FareDetail();
			fareDetail.setFare(tuple.getT1());
			fareDetail.setOrigin(tuple.getT2());
			fareDetail.setDestination(tuple.getT3());
			 return Mono.just(fareDetail);
		});
	}
}
