package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;

public interface BusyFlightService {
	
	boolean validateRequest(BusyFlightsRequest busyFlightsRequest);

	String getAllFlights(BusyFlightsRequest busyFlightsRequest) throws Exception;
	
}
