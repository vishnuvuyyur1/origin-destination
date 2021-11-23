package com.klm.cases.df.service;

import java.util.List;

import com.klm.cases.df.model.FareDetail;
import com.klm.cases.df.model.Location;

import reactor.core.publisher.Mono;

public interface IOriginDestinationService {
	 Mono<List<Location>> getAirports();
	 Mono<FareDetail> getFareDetail(String originCode, String destinationCode);
}
