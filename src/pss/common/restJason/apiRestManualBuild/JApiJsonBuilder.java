package pss.common.restJason.apiRestManualBuild;

import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JApiJsonBuilder extends JSonObject {
	
	public JApiJsonBuilder() throws Exception {}
	
	public JApiJsonBuilder(String title) throws Exception {
		this.setTitle(title);
	}
	
	
	public String getResponseStringToSend() throws Exception {
		JsonObject json = this.getConvertedJasonObject();

		return new Gson().toJson(json);
	}
	
	
	public Response getResponse() throws Exception {
		String respuesta = this.getResponseStringToSend();
		return Response.ok().entity(respuesta).build();
	}
	

}
