package com.klm.cases.df.test_data;

import java.util.ArrayList;
import java.util.List;

import com.klm.cases.df.model.AirportsEmbedded;
import com.klm.cases.df.model.Embedded;
import com.klm.cases.df.model.Location;

import reactor.core.publisher.Mono;

public class OriginDestinationTestData {

	public Mono<List<Location>> getAirports(){
		List<Location> locations = new ArrayList<>();
		locations.add(new Location("code1","name1","desc1"));
		locations.add(new Location("code2","name2","desc2"));
		return Mono.just(locations);
	}
	
	public AirportsEmbedded getAirportsEmbedded(){
		AirportsEmbedded airportsEmbedded = new AirportsEmbedded();
		Embedded embedded = new Embedded();
		List<Location> locations = new ArrayList<>();
		locations.add(new Location("code1","name1","desc1"));
		locations.add(new Location("code2","name2","desc2"));
		embedded.setLocations(locations);
		airportsEmbedded.set_embedded(embedded);
		return airportsEmbedded;
	}
	
}
