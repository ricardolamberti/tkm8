package pss.common.restJason;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

public class JServiceApi {	
	
  @POST
  @Path("/echo")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Response echo(@QueryParam("value") String valor ) throws Exception {
		final String json = "{\n"
	                + "    \"response\": \"" + valor  + "\" \n"
	                + "}";
		try {
			return JServiceApiProcess.makeOk(json);		    	
		} catch (Exception e) {
			return JServiceApiProcess.makeError(e.getMessage());
		}
  }
	
  
  public String toString(JServiceApiRequest request) throws Exception {
  	return new Gson().toJson(request);
  }
  
}
