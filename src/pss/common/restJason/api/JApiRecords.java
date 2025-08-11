package pss.common.restJason.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import pss.common.restJason.JServiceApi;
import pss.common.restJason.JServiceApiProcess;
import pss.common.restJason.apiRestBase.apiBasicRequest;


@Path("/records")
public class JApiRecords extends JServiceApi {   
	
	@POST
	@Path("/getData")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getData(@Context HttpServletRequest httpReq, @Context UriInfo uriInfo, apiBasicRequest request) throws Exception {
	    JServiceApiProcess service = new JApiRecordsGet();
	    return service.process(this, uriInfo, httpReq, this.toString(request));
	}
  	
  	
    
}
