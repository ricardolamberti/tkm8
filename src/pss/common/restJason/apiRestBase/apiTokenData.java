package pss.common.restJason.apiRestBase;

import java.util.Date;

public class apiTokenData {
	private apiUserData	userData;
	private Date expirationDate;
	private String errorMsg="";
	private String company;
	
	
	public apiTokenData() {
	}


	public apiUserData getUserData() {
		return userData;
	}


	public void setUserData(apiUserData userData) {
		this.userData = userData;
	}

	public void setUserData(String user, String password) {
		this.userData=new apiUserData(user, password);
	}
	
	
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date value) {
		this.expirationDate = value;
	}
	
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String value) {
		this.errorMsg = value;
	}
	
	public boolean hasErrorMsg() {
		return !this.errorMsg.equals("");
	}
	
	public String getCompany() {
		return company;
	}

	public void setCompany(String value) {
		this.company = value;
	}
	

	
}