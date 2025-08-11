package pss.common.restJason.apiRestBase;


import java.util.List;
import java.util.Map;

public class apiBasicResponse  {
	private String action;
	private apiStatus status;
	private Map<String,String> filters;
    private List<Map<String,Object>> records;
    //private List<Map<String,Object>> otraData;

	public void setStatus(String zStatusCode, String zStatusMsg) throws Exception {
		this.status = new apiStatus();
		this.status.setStatus_code(zStatusCode);
		this.status.setStatus_msg(zStatusMsg);
	}
	
	
    public apiStatus getStatus() {
        return status;
    }
    public void setStatus(apiStatus status) {
        this.status = status;
    }
	
    public String getAction() {
        return action;
    }
    public void setAction(String valor) {
        this.action = valor;
    }
    
    public List<Map<String,Object>> getRecords() {
        return records;
    }
    public void setRecords(List<Map<String,Object>> value) {
        this.records = value;
    }   
    
	public Map<String,String> getFilters() {
		return filters;
	}

	public void setFilters(Map<String,String> filtro_lista) {
		this.filters = filtro_lista;
	}

	/* public List<Map<String,Object>> getOtraData() {
    	return otraData;
	}
	public void setOtraData(List<Map<String,Object>> userData) {
	    this.otraData = userData;
	}*/ 


}
