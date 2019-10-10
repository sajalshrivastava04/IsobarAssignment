package com.isobar.wallet.management.user.utils;

import java.net.URI;
import java.util.Collections;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.isobar.wallet.management.user.dto.ResponseDto;

import lombok.AccessLevel;


@Component
public class RestUtil {
	
	@Autowired
	RestTemplate restTemplate;
	
	private static HttpHeaders getHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		return headers;
	}
	public  ResponseDto invokeServiceAPI(String endpointURL,HttpMethod httpMethodName, Object postObject,Class<?> returnObject) {
		HttpEntity<Object> entity;
		ResponseEntity<?> response;
		ResponseEntity<String> msErrordata;
		HttpHeaders headers = getHttpHeaders();
		URI uri = UriComponentsBuilder.fromHttpUrl(endpointURL).build().encode().toUri();
		if (httpMethodName.equals(HttpMethod.POST) || httpMethodName.equals(HttpMethod.PUT)
				|| httpMethodName.equals(HttpMethod.DELETE)) {
			entity = new HttpEntity<>(postObject, headers);
		} else {
			entity = new HttpEntity<>(headers);
		}
		response = restTemplate.exchange(uri, httpMethodName, entity, returnObject);
		return (ResponseDto) response.getBody();
	
	}
	
	public static <T> Object objectToObjectConverter(Object objects, Class<T> type) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull()).setSkipNullEnabled(true).setAmbiguityIgnored(true);
		return mapper.map(objects, type);

	}

}
