package pss.common.restJason.api;


import java.util.List;
import java.util.Map;

public class JApiRecordsResponse {
	
	private String action;
//	private apiStatus status;
	private Map<String,String> filters;
  private List<Map<String,Object>> records;

//  public apiStatus getObjStatus() throws Exception {
//  	if (this.status!=null) return this.status;
//  	return (this.status=new apiStatus());
//  }
  
//	public void pushError(String msg) throws Exception {
//		this.getObjStatus().pushError(msg);
//	}

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


}
