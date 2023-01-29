package com.sds.toms.handler;

import javax.ws.rs.core.MediaType;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.pojo.ObjectResp;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RespHandler {

	public static ObjectResp getObject(String url) {
		System.out.println("GET url : " + url);
		ObjectResp rsp = null;
		try {
			Client client = Client.create();
			client.setConnectTimeout(40 * 1000);
			client.setReadTimeout(40 * 1000);

			WebResource webResource = client.resource(url.trim());
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);

			String output = response.getEntity(String.class);
			System.out.println("output :");
			System.out.println(output);
			ObjectMapper mapper = new ObjectMapper();

			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			rsp = mapper.readValue(output, ObjectResp.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rsp;
	}
	
}
