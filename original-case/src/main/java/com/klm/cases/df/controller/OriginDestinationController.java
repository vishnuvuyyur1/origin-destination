package com.klm.cases.df.controller;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klm.cases.df.exception.OriginalCaseException;
import com.klm.cases.df.model.FareDetail;
import com.klm.cases.df.model.Location;
import com.klm.cases.df.service.OriginDestinationService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Validated
@Slf4j
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
		try {
			return originDestinationService.getAirports();
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new OriginalCaseException(ex.getMessage(), ex);
		}
	}
	
	@GetMapping("/fare/{origin}/{destination}")
	public Mono<FareDetail> getFareDetail(@PathVariable @Pattern(regexp = CODE_REGEX) final String origin,
            @PathVariable @Pattern(regexp = CODE_REGEX) final String destination) {
		try {
			return originDestinationService.getFareDetail(origin, destination);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new OriginalCaseException(ex.getMessage(), ex);
		}
	}
}
