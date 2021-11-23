package com.klm.cases.df.controller;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klm.cases.df.model.FareDetail;
import com.klm.cases.df.model.Location;
import com.klm.cases.df.service.OriginDestinationService;

import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("/origin-destination")
public class OriginDestinationController {
	
	private static final String CODE_REGEX = "^[A-Za-z]{3}$";
	
	private final OriginDestinationService originDestinationService;
	public OriginDestinationController(OriginDestinationService originDestinationService) {
		this.originDestinationService = originDestinationService;
	}
	
	@GetMapping("/airports")
	public Mono<List<Location>> getAirports() {
		return originDestinationService.getAirports();
	}
	
	@GetMapping("/fare/{origin}/{destination}")
	public Mono<FareDetail> getFareDetail(@PathVariable @Pattern(regexp = CODE_REGEX) final String origin,
            @PathVariable @Pattern(regexp = CODE_REGEX) final String destination) {
		return originDestinationService.getFareDetail(origin, destination);
	}
}
