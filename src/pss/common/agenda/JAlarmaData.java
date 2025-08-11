
package pss.common.agenda;

import java.util.Date;

public class JAlarmaData {
	String company;
	String user;
	long cantAlarmas;
	String mensaje;
	Date lastCheck;
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public long getCantAlarmas() {
		return cantAlarmas;
	}
	public void setCantAlarmas(long cantAlarmas) {
		this.cantAlarmas = cantAlarmas;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Date getLastCheck() {
		return lastCheck;
	}
	public void setLastCheck(Date lastCheck) {
		this.lastCheck = lastCheck;
	}
	
	
}
