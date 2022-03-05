package com.travix.medusa.busyflights.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.busyflights.common.CommonUtil;
import com.travix.medusa.busyflights.common.HttpServiceResponse;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;

@Service("BusyFlightService")
public class BusyFlightServiceImpl implements BusyFlightService{
	
	@Autowired
	HttpServiceResponse httpService;
	
	@Autowired
	CommonUtil commonUtil;
	
	private static String responceData;
	
	private static final String CRAZYAIR = "Crazy Air";
	private static final String TOUGHAIR = "Tough Jet";
	List<CrazyAirResponse> crazyAirList;
	List<ToughJetResponse> toughJetList;
	
	public boolean validateRequest(BusyFlightsRequest busyFlightsRequest) {
		if(busyFlightsRequest.getOrigin().isBlank()) {
			return false;
		}
		
		if(busyFlightsRequest.getDestination().isBlank()) {
			return false;
		}
		
		if(busyFlightsRequest.getReturnDate().isBlank()) {
			return false;
		}
		
		if(busyFlightsRequest.getDepartureDate().isBlank()) {
			return false;
		}
		
		if(busyFlightsRequest.getNumberOfPassengers() == 0) {
			return false;
		}
		return true;
	}
	
	// Call API Service to get Data
	public String getAllFlights(BusyFlightsRequest busyFlightsRequest) throws Exception {
		
		CrazyAirRequest crazyRequest = new CrazyAirRequest();
		crazyRequest.setOrigin(busyFlightsRequest.getOrigin());
		crazyRequest.setDestination(busyFlightsRequest.getDestination());
		crazyRequest.setDepartureDate(busyFlightsRequest.getDepartureDate());
		crazyRequest.setReturnDate(busyFlightsRequest.getReturnDate());
		crazyRequest.setPassengerCount(busyFlightsRequest.getNumberOfPassengers());

		ToughJetRequest toughJetRequest = new ToughJetRequest();
		toughJetRequest.setFrom(busyFlightsRequest.getOrigin());
		toughJetRequest.setTo(busyFlightsRequest.getDestination());
		toughJetRequest.setOutboundDate(busyFlightsRequest.getDepartureDate());
		toughJetRequest.setInboundDate(busyFlightsRequest.getReturnDate());
		toughJetRequest.setNumberOfAdults(busyFlightsRequest.getNumberOfPassengers());
	
		try {
			// Crazy Service Method for data invoke 
			if (null != (httpService.sendCrazyRequest(crazyRequest, "crazyUrl"))) {
				crazyAirList = (List<CrazyAirResponse>) commonUtil.jsonToObjectCrazyAir(httpService.sendCrazyRequest(crazyRequest, "crazyUrl"));
			} 
			// ToughJet Service Method for data invoke
			if (null != (httpService.sendToughRequest(toughJetRequest, "toughUrl"))) {
				toughJetList = (List<ToughJetResponse>) commonUtil.jsonToObjectToughJet(httpService.sendToughRequest(toughJetRequest, "toughUrl"));
			} 
			// Sort List and Convert List to JSON Format for the response 
			responceData = convertListToJson(crazyAirList, toughJetList, busyFlightsRequest);
		} catch (Exception e) {
			throw e;
		}

		return responceData;
	}
	
	private  String convertListToJson(List<CrazyAirResponse> crazyAirList, List<ToughJetResponse> toughJetList, BusyFlightsRequest busyFlightsRequest) throws IOException {
		List<Object> allFlightsList = new ArrayList<Object>();
		List<BusyFlightsResponse> reponseList = new ArrayList<BusyFlightsResponse>();
		String flightsData = null;
		
		// merging two list 
		allFlightsList.addAll((Collection<? extends Object>) crazyAirList);
        allFlightsList.addAll((Collection<? extends Object>) toughJetList);	
        reponseList = createBusyFlightsResponse(allFlightsList, busyFlightsRequest);
		
		try {
        	ObjectMapper mapper = new ObjectMapper();
        	mapper.writeValue(System.out, reponseList);
        	flightsData =	mapper.writeValueAsString(reponseList);
		} catch (IOException e) {
			throw e;
		}
		return flightsData;
	}

	private List<BusyFlightsResponse> createBusyFlightsResponse(List<Object> allFlightsList, BusyFlightsRequest busyFlightsRequest) {
		BusyFlightsResponse busyFlightsResponse = null;
		List<BusyFlightsResponse> responseList = new ArrayList<BusyFlightsResponse>();
		
		for (Object object : allFlightsList) {
			if (object instanceof CrazyAirResponse){
				busyFlightsResponse = new BusyFlightsResponse();
				busyFlightsResponse.setAirline(((CrazyAirResponse) object).getAirline());
				busyFlightsResponse.setSupplier(CRAZYAIR);
				busyFlightsResponse.setFare(((CrazyAirResponse) object).getPrice());
				busyFlightsResponse.setDepartureAirportCode(((CrazyAirResponse) object).getDepartureAirportCode());
				busyFlightsResponse.setDestinationAirportCode(((CrazyAirResponse) object).getDestinationAirportCode());
				busyFlightsResponse.setDepartureDate(busyFlightsRequest.getDepartureDate());
				busyFlightsResponse.setArrivalDate(busyFlightsRequest.getReturnDate());
				responseList.add(busyFlightsResponse);
				
			}else if(object instanceof ToughJetResponse){
				busyFlightsResponse = new BusyFlightsResponse();
				busyFlightsResponse.setAirline(((ToughJetResponse) object).getCarrier());
				busyFlightsResponse.setSupplier(TOUGHAIR);
				double tax = ((ToughJetResponse) object).getTax();
				double orignalPrice = ((ToughJetResponse) object).getBasePrice();
				double discount = 1 - (((ToughJetResponse) object).getDiscount()/100) ;
				busyFlightsResponse.setFare((orignalPrice*discount)+tax);
				busyFlightsResponse.setDepartureAirportCode(((ToughJetResponse) object).getDepartureAirportName());
				busyFlightsResponse.setDestinationAirportCode(((ToughJetResponse) object).getArrivalAirportName());
				busyFlightsResponse.setDepartureDate(busyFlightsRequest.getDepartureDate());
				busyFlightsResponse.setArrivalDate(busyFlightsRequest.getReturnDate());
				responseList.add(busyFlightsResponse);
			}
		}
		return responseList;
	}
	
			
}
