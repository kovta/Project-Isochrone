package com.kota.stratagem.webservice.mapper;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.webservice.filter.StratagemCrossOriginRequestFilter;

public class AdaptorExceptionMapper implements ExceptionMapper<AdaptorException> {

	@Context
	private HttpHeaders headers;

	@Override
	public Response toResponse(final AdaptorException e) {
		return Response.status(e.getErrorCode().getHttpStatusCode()).entity(e.build()) //
				.header(StratagemCrossOriginRequestFilter.ALLOW_ORIGIN, "*") //
				.header(StratagemCrossOriginRequestFilter.ALLOW_METHODS, "GET, POST, PUT, DELETE, OPTIONS, HEAD") //
				.header(StratagemCrossOriginRequestFilter.MAX_AGE, "1209600") //
				.header(StratagemCrossOriginRequestFilter.ALLOW_HEADERS, "x-requested-with, origin, content-type, accept, X-Codingpedia, authorization") //
				.header(StratagemCrossOriginRequestFilter.ALLOW_CREDENTIALS, "true") //
				.type(MediaType.APPLICATION_JSON).build();
	}

}
