package com.travix.medusa.busyflights;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.service.BusyFlightService;

@Controller
public class IndexApi {
	
	@Autowired
	BusyFlightService busyFlightSerive;
	
	@RequestMapping("/search")
	public ResponseEntity<?> search(@ModelAttribute("model") BusyFlightsRequest busyFlightsRequest) {
		String searchResData = null;
		boolean validate = busyFlightSerive.validateRequest(busyFlightsRequest);
		
		if(validate) {
			try {
				searchResData = busyFlightSerive.getAllFlights(busyFlightsRequest);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(searchResData, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(searchResData, HttpStatus.OK);
		}
		return new ResponseEntity<>(searchResData, HttpStatus.FORBIDDEN);
	}

}