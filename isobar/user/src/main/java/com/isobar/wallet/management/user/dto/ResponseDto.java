package com.isobar.wallet.management.user.dto;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class ResponseDto {
	
	private Integer statusCode;

    private String message;

	private String errorMessage;
    
	private Object responseBody;

}
