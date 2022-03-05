package com.travix.medusa.busyflights.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;

@Service("HttpServiceResponse")
public class HttpServiceResponse {

	public static final Logger logger = LoggerFactory.getLogger(HttpServiceResponse.class);

	
	@SuppressWarnings("unused")
	public String sendCrazyRequest(CrazyAirRequest crazyReq, String crazyUrl) throws Exception {
		String crazyHTTP = "http";
		String crazyPort = "80";
		BufferedReader in;
		StringBuffer response = null;
		try {
			URL objUrl = new URL(crazyUrl);
			InetSocketAddress proxyinet = new InetSocketAddress(crazyHTTP, Integer.parseInt(crazyPort));
			Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyinet);
			HttpURLConnection con = (HttpURLConnection) objUrl.openConnection(proxy);
			con.setRequestMethod("GET");
			int responsecode = con.getResponseCode();
			logger.info("CrzyAirResponse for request{} :", crazyReq);
			logger.info("CrzyAirResponse response code :", responsecode);

			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			if(null != response) {
				return response.toString();
			}
		} catch (Exception e) {
			throw e;
		}
		return crazyJson;
	}

	@SuppressWarnings("unused")
	public String sendToughRequest(ToughJetRequest toughReq, String toughUrl) throws Exception {
		String crazyHTTP = "http";
		String crazyPort = "82";
		BufferedReader in;
		StringBuffer response = null;
		try {
			URL objUrl = new URL(toughUrl);
			InetSocketAddress proxyinet = new InetSocketAddress(crazyHTTP, Integer.parseInt(crazyPort));
			Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyinet);
			HttpURLConnection con = (HttpURLConnection) objUrl.openConnection(proxy);
			con.setRequestMethod("GET");
			int responsecode = con.getResponseCode();
			logger.info("ToughJetResponse for request{} :", toughReq);
			logger.info("ToughJetResponse response code :", responsecode);

			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			if(null != response) {
				return response.toString();
			}
		} catch (Exception e) {
			throw e;
		}
		return toughJson;
	}
	
	
	public String crazyFlight1 = "{\"airline\":\"Jet Airways\",\"price\":\"100\",\"cabinclass\":\"B\",\"departureAirportCode\":\"LHR\",\"destinationAirportCode\":\"AMS\","
			+ "\"departureDate\":\"2022-03-06 10:10\",\"arrivalDate\":\"2022-03-08 12:30\"}";
	public String crazyFlight2 = "{\"airline\":\"Jet Airways\",\"price\":\"90\",\"cabinclass\":\"B\",\"departureAirportCode\":\"LHR\",\"destinationAirportCode\":\"AMS\","
			+ "\"departureDate\":\"2022-03-07 10:10\",\"arrivalDate\":\"2022-03-09 12:30\"}";
	public String crazyFlight3 = "{\"airline\":\"Jet Airways\",\"price\":\"95\",\"cabinclass\":\"B\",\"departureAirportCode\":\"LHR\",\"destinationAirportCode\":\"AMS\","
			+ "\"departureDate\":\"2022-03-08 10:10\",\"arrivalDate\":\"2022-03-10 12:30\"}";
	public String crazyJson = "["+crazyFlight1+","+ crazyFlight2+","+crazyFlight3+"]";
	
	public String toughFlight1 = "{\"carrier\":\"British Airways\",\"basePrice\":\"240\",\"tax\":\"50\",\"discount\":\"10\",\"departureAirportName\":\"LHR\","
			+ "\"arrivalAirportName\":\"AMS\",\"outboundDateTime\":\"2022-03-06 9:20\",\"inboundDateTime\":\"2022-03-08 10:50\"}";
	public String toughFlight2 = "{\"carrier\":\"British Airways\",\"basePrice\":\"250\",\"tax\":\"40\",\"discount\":\"5\",\"departureAirportName\":\"LHR\","
			+ "\"arrivalAirportName\":\"AMS\",\"outboundDateTime\":\"2022-03-07 9:20\",\"inboundDateTime\":\"2022-03-09 10:50\"}";
	public String toughFlight3 = "{\"carrier\":\"British Airways\",\"basePrice\":\"230\",\"tax\":\"30\",\"discount\":\"8\",\"departureAirportName\":\"LHR\","
			+ "\"arrivalAirportName\":\"AMS\",\"outboundDateTime\":\"2022-03-08 9:20\",\"inboundDateTime\":\"2022-03-10 10:50\"}";
	public String toughJson = "["+toughFlight1+","+ toughFlight2+","+toughFlight3+"]";

}
