package pss.common.restJason;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import pss.common.restJason.apiRestBase.apiStatus;


public class JServiceApiException implements ExceptionMapper<Exception> {

	  @Override
	  public Response toResponse(Exception ex) {
		  
		  apiStatus resp =  new apiStatus();
		  resp = new apiStatus(apiStatus.STATUS_CODE_ERROR,ex.getMessage());
		  return Response.serverError().entity(resp).build();
	  }
	}
