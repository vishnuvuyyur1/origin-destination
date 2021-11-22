package com.klm.cases.df.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klm.cases.df.model.FareDetail;
import com.klm.cases.df.model.Location;
import com.klm.cases.df.service.OriginDestinationService;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/origin-destination")
public class OriginDestinationController {
	
	private final OriginDestinationService originDestinationService;
	public OriginDestinationController(OriginDestinationService originDestinationService) {
		this.originDestinationService = originDestinationService;
	}
	
	@GetMapping("/airports")
	public Mono<List<Location>> getAirports() {
		return originDestinationService.getAirports();
	}
	
	@GetMapping("/fare/{origin}/{destination}")
	public Mono<FareDetail> getFareDetail(@PathVariable("origin") final String originCode,
            @PathVariable("destination") final String destinationCode) {
		return originDestinationService.getFareDetail(originCode, destinationCode);
	}
}
