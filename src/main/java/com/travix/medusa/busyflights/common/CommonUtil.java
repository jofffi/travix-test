package com.travix.medusa.busyflights.common;

import java.util.List;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;

public class CommonUtil {
	
    public List<CrazyAirResponse> jsonToObjectCrazyAir(String responseJSON) throws Exception {
		List<CrazyAirResponse> list = null;
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			list = mapper.readValue(responseJSON, new TypeReference<List<CrazyAirResponse>>(){});
		} catch (Exception e) {
			throw e;
		}
		
		return list;
	}
    
    public List<ToughJetResponse> jsonToObjectToughJet(String responseJSON) throws Exception {
		List<ToughJetResponse> list = null;
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			list = mapper.readValue(responseJSON, new TypeReference<List<ToughJetResponse>>(){});
		} catch (Exception e) {
			throw e;
		}
		
		return list;
	}
		
}
