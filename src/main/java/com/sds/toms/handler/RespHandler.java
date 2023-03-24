package com.sds.toms.handler;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.internal.MultiPartWriter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.model.Muser;
import com.sds.toms.pojo.ObjectResp;
import com.sds.toms.util.AppUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

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

	public static ObjectResp postObject(String url, Object obj) throws Exception {
		ObjectResp rsp = null;
		try {
			Client client = Client.create();
			client.setConnectTimeout(40 * 1000);
			client.setReadTimeout(40 * 1000);

			ObjectMapper mapper = new ObjectMapper();
			WebResource webResource = client.resource(url.trim());

			System.out.println("Req save : " + mapper.writeValueAsString(obj));

			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class,
					mapper.writeValueAsString(obj));

			String output = response.getEntity(String.class);
			System.out.println(output);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			mapper.setDateFormat(df);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			rsp = mapper.readValue(output, ObjectResp.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rsp;
	}

	public static ObjectResp putObject(String url, Object obj) {
		ObjectResp rsp = null;
		try {
			Client client = Client.create();
			client.setConnectTimeout(40 * 1000);
			client.setReadTimeout(40 * 1000);

			System.out.println("PUT url : " + url);
			ObjectMapper mapper = new ObjectMapper();
			System.out.println("Req Update : " + mapper.writeValueAsString(obj));
			WebResource webResource = client.resource(url.trim());
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class,
					mapper.writeValueAsString(obj));

			String output = response.getEntity(String.class);
			System.out.println(output);

			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			rsp = mapper.readValue(output, ObjectResp.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rsp;
	}

	public static ObjectResp delObject(String url, Object obj) {
		ObjectResp rsp = null;
		try {
			Client client = Client.create();
			client.setConnectTimeout(40 * 1000);
			client.setReadTimeout(40 * 1000);

			ObjectMapper mapper = new ObjectMapper();
			WebResource webResource = client.resource(url.trim());

			System.out.println("Req : " + mapper.writeValueAsString(obj));

			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class,
					mapper.writeValueAsString(obj));

			String output = response.getEntity(String.class);
			System.out.println(output);

			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			rsp = mapper.readValue(output, ObjectResp.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rsp;
	}

	public static ObjectResp responObj(String url, Object obj, String method, Muser user) throws Exception {
		ObjectResp rsp = null;
		try {
			Client client = Client.create();
			client.setConnectTimeout(40 * 1000);
			client.setReadTimeout(40 * 1000);

			ObjectMapper mapper = new ObjectMapper();
			WebResource webResource = client.resource(url.trim());
			System.out.println("url : " + url);

			ClientResponse response = null;
			if (method.equals(AppUtil.METHOD_GET)) {
				response = webResource.header("Authorization", "Bearer " + user.getToken())
						.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			} else if (method.equals(AppUtil.METHOD_POST)) {
				System.out.println("Req post : " + mapper.writeValueAsString(obj));
				response = webResource.header("Authorization", "Bearer " + user.getToken())
						.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, obj);
			} else if (method.equals(AppUtil.METHOD_PUT)) {
				System.out.println("Req put : " + obj);
				response = webResource.header("Authorization", "Bearer " + user.getToken())
						.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, obj);
			} else if (method.equals(AppUtil.METHOD_DEL)) {
				response = webResource.header("Authorization", "Bearer " + user.getToken())
						.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class, obj);
			}

			String output = response.getEntity(String.class);
			System.out.println(output);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			rsp = mapper.readValue(output, ObjectResp.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rsp;
	}

	public static ObjectResp postMedia(String url, String filepath, String filename, Muser user) {
		ObjectResp rsp = null;
		try {
			DefaultClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getClasses().add(MultiPartWriter.class);
			Client client = Client.create(clientConfig);
			client.setConnectTimeout(40 * 1000);
			client.setReadTimeout(40 * 1000);

			ObjectMapper mapper = new ObjectMapper();
			WebResource webResource = client.resource(url.trim());
			System.out.println("url : " + url);

			FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
			formDataMultiPart.field("file", new File(filepath), MediaType.APPLICATION_OCTET_STREAM_TYPE);
			formDataMultiPart.field("filename", filename);
			formDataMultiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);

			ClientResponse response = null;
			response = webResource.header("Authorization", "Bearer " + user.getToken()).type("multipart/form-data")
					.post(ClientResponse.class, formDataMultiPart);
			String output = response.getEntity(String.class);
			System.out.println("response : " + output);
			client.destroy();
			rsp = mapper.readValue(output, ObjectResp.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rsp;
	}
}
