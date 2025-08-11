
package pss.common.agenda;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import pss.common.agenda.evento.BizEvento;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;

public class JAgenda {

	private static JAgenda alarmaServer;
	
	private Map<String,JAlarmaData>  cacheAlarmas;
	
	public Map<String,JAlarmaData> getCacheMap() throws Exception {
		if (cacheAlarmas!=null) return cacheAlarmas;
		cacheAlarmas = new HashMap<String,JAlarmaData>(); 
		return cacheAlarmas;
	}
	
	public static JAgenda getAlarmaServer() throws Exception {
		if (alarmaServer!=null) return alarmaServer;
		JAgenda m = new JAgenda();
		return (alarmaServer = m);
	}
	public void clearCache(String company,String usuario) throws Exception {
		String key = getKey(company,usuario);
		getCacheMap().remove(key);
	}
	
	public boolean hasAlarma() throws Exception {
		JAlarmaData data = getAlarmaData();
		if (data==null) return false;
		return (data.cantAlarmas!=0);
	}
	
	public long getCantAlarma() throws Exception {
		JAlarmaData data = getAlarmaData();
		if (data==null) return 0;
		return data.cantAlarmas;
	}	
	public String getMessage() throws Exception {
		JAlarmaData data = getAlarmaData();
		if (data==null) return "";
		return data.mensaje;
	}	
	
	
	private JAlarmaData getAlarmaData() throws Exception {
		if (BizUsuario.getUsr()==null) return null;
		JAlarmaData data = getCacheMap().get(getKey(BizUsuario.getUsr().getCompany(),BizUsuario.getUsr().GetUsuario()));
		if (data!=null) {
			Calendar c = new GregorianCalendar();
			c.setTime(new Date());
			c.add(Calendar.MINUTE, -5);
			if (c.getTime().before(data.lastCheck)) return data;
		}
		detectCantAlarma() ;
		return getCacheMap().get(getKey(BizUsuario.getUsr().getCompany(),BizUsuario.getUsr().GetUsuario()));
	}
	
	private String getKey(String c,String u) {
		return c+"__"+u;
	}
	
	
	private void detectCantAlarma() throws Exception {
		if (BizUsuario.getUsr()==null) return ;

		JRecords<BizEvento> alarmas= new JRecords<BizEvento>(BizEvento.class);
		alarmas.addFilter("company", BizUsuario.getUsr().getCompany());
		alarmas.addFilter("user_alarma", BizUsuario.getUsr().GetUsuario());
		alarmas.addFilter("readed_alarma", false);
		alarmas.addFilter("has_alarma", true);
		alarmas.addFilter("estado", BizEvento.ACTIVA);
		alarmas.addFilter("fecha_alarma", JDateTools.getDateEndDay(new Date()), "<=");
		alarmas.addFilter("fecha_alarma", JDateTools.getDateStartDay(new Date()), ">=");
		alarmas.addOrderBy("fecha_alarma", "ASC");
		alarmas.toStatic();
		int nroAlarmas=0;
		String mensaje="";
		alarmas.firstRecord();
		while (alarmas.nextRecord()) {
			BizEvento evento = alarmas.getRecord();
			if (nroAlarmas==0) mensaje = "Alarmas de hoy: ";
			else mensaje +=" |";
			if (nroAlarmas<3) 
				mensaje += evento.getTitulo();
			nroAlarmas++;
//			evento.setReadedAlarma(true);
//			evento.execProcessUpdate();
		}
		if (nroAlarmas>=3) 
			mensaje += " | y "+(nroAlarmas-3)+" alarmas más.";
			
		JAlarmaData maildata = new JAlarmaData();
		
		maildata.setCompany( BizUsuario.getUsr().getCompany() );
		maildata.setUser( BizUsuario.getUsr().GetUsuario() );
		maildata.setCantAlarmas( nroAlarmas );
		maildata.setMensaje( mensaje );
		maildata.setLastCheck( new Date() ); 

		String key = getKey(maildata.company,maildata.user);
		getCacheMap().remove(key);
		getCacheMap().put(key, maildata);
		
	}
	
	public void addSimpleAlarm(String message) throws Exception {
		JAlarmaData maildata = new JAlarmaData();
		
		maildata.setCompany( BizUsuario.getUsr().getCompany() );
		maildata.setUser( BizUsuario.getUsr().GetUsuario() );
		maildata.setCantAlarmas( 1 );
		maildata.setMensaje( message );
		maildata.setLastCheck( new Date() ); 

		String key = getKey(maildata.company,maildata.user);
		getCacheMap().remove(key);
		getCacheMap().put(key, maildata);
	}
	

	
	
	
}
