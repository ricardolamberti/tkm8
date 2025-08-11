package pss.common.restJason;

import pss.core.tools.JExcepcion;

public class JServiceApiRequest {

	private String jsonRequest = "";
	private String sCompany;
	private String token;
	
	public void setCompany(String v) { this.sCompany = v; }
	public String getCompany() { return this.sCompany ; }
	public void setJsonRequest(String v) { this.jsonRequest = v; }
	public void getJsonRequest(String v) { this.jsonRequest = v; }
	public String getToken() { return this.token ; }
	public void setToken(String v) { this.token = v; }
	
	
	public static void validateEmptyNullField(String name, String value) throws Exception {
		if (value==null || value.isEmpty())
			JExcepcion.SendError(name +" no puede ser vacío ó blanco");
	}


}
