package com.klm.cases.df.test_data;

import java.util.ArrayList;
import java.util.List;

import com.klm.cases.df.model.Location;

import reactor.core.publisher.Mono;

public class OriginDestinationTestData {

	public Mono<List<Location>> getAirports(){
		List<Location> locations = new ArrayList<>();
		locations.add(new Location("code1","name1","desc1"));
		locations.add(new Location("code2","name2","desc2"));
		return Mono.just(locations);
	}
}
