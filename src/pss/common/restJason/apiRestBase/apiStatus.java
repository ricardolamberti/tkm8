package pss.common.restJason.apiRestBase;

import java.io.Serializable;

import javax.ws.rs.core.Response;

import pss.core.tools.JExcepcion;


public class apiStatus implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String	STATUS_CODE_OK		= "200";
	public static final String	STATUS_CODE_ERROR	= "500";

	private String	status_code = STATUS_CODE_OK;
	private String	status_msg = "TODO OK";
//	private String company;
	
	public apiStatus() {
	}
	
	public apiStatus(String code, String message) {
		this.status_code = code;
		this.status_msg = message;
	}

	public boolean isOk() throws Exception {
		return this.getStatusCode().equals(apiStatus.STATUS_CODE_OK);
	}

	public boolean isError() throws Exception {
		return this.getStatusCode().equals(apiStatus.STATUS_CODE_ERROR);
	}
	
	public String getStatusCode() {
		return status_code;
	}

	public void setStatusCode(String status_code) {
		this.status_code = status_code;
	}

	public String getStatusMsg() {
		return status_msg;
	}

	public void setStatusMsg(String status_msg) {
		this.status_msg = status_msg;
	}
	
	public void setStatus(String code, String message) {
		this.status_code = code;
		this.status_msg = message;
	}
	public static apiStatus makeOk(String msg) throws Exception {
		return make(STATUS_CODE_OK, msg);
	}
	public static apiStatus makeError(String msg) throws Exception {
		return make(STATUS_CODE_ERROR, msg);
	}
	public static apiStatus make(String status, String msg) throws Exception {
		apiStatus st = new apiStatus();
		st.setStatusCode(status);
		st.setStatusMsg(msg);
		return st;
	}

	
	
//	public String getCompany() {
//		return company;
//	}
//
//	public void setCompany(String value) {
//		this.company = value;
//	}
	

}
