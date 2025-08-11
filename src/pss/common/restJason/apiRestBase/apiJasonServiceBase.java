package pss.common.restJason.apiRestBase;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pss.common.restJason.JServiceApiProcess;


@Path("/")
public class apiJasonServiceBase {   

    
	@POST
	@Path("/getToken")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getToken(apiUserData request) throws Exception {
	    try {
	        // Aquí va la lógica para generar el token
	        return JServiceApiProcess.makeOk("No implementado");
	        // resolver.openApp(); // falta la company
	        // String token = apiWebServiceTools.generateToken(request.getUsuario(), request.getPassword(), resolver.getCompany());
	        // return resolver.responseOk(token);
	        
	    } catch (Exception e) {
	        return JServiceApiProcess.makeError(e.getMessage());
	    }
	}
    
	@GET
	@Path("/getTest")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTest() throws Exception {
		try {
			return JServiceApiProcess.makeOk("GET TEST OK");
		} catch (Exception e) {
			return JServiceApiProcess.makeError(e.getMessage());
		}
	}
    
  @POST
  @Path("/postTest")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Response postTest() throws Exception {
		try {
			return JServiceApiProcess.makeOk("POST TEST OK");
		} catch (Exception e) {
			return JServiceApiProcess.makeError(e.getMessage());
		}
  }
    
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

}
