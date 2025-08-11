package pss.common.restJason.apiRestBase;

import java.util.Map;


import javax.xml.bind.annotation.XmlRootElement;

import pss.common.restJason.JServiceApiRequest;

@XmlRootElement (name="apiBasicRequest")
public class apiBasicRequest extends JServiceApiRequest {
	private apiUserData user;
	private String action;
	private boolean showDetails;
	private String vision;

	
	private Map<String,String>	filters;

	public apiUserData getUser() {
		return user;
	}

	public void setUser(apiUserData user) {
		this.user = user;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
		
	public boolean getShowDetails() {
		return showDetails;
	}

	public void setShowDetails(boolean value) {
		this.showDetails = value;
	}

	public String getVision() {
		if (vision==null) return "";
		return vision;
	}

	public void setVision(String value) {
		this.vision = value;
	}
	
	public Map<String,String> getFilters() {
		return filters;
	}

	public void addFilter(String key, String value) {
		this.filters.put(key, value);
	}

	
	public void setFilters(Map<String,String> filtro_lista) {
		this.filters = filtro_lista;
	}
	
	

}
