package pss.common.event.action;

import java.io.Serializable;

import pss.common.event.action.history.BizSqlEventHistory;
import pss.core.services.records.JFilterMap;

public interface IActionData extends Serializable {
	public String getNotificacionAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist) throws Exception;
	public String getCorreoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist) throws Exception;
	public String getCorreoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist,String campo,String valor) throws Exception;
	public String getArchivoAsociadoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist) throws Exception;
	public String getArchivoAsociadoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist,String campo,String valor) throws Exception;
  public BizSqlEventHistory  generarAviso(JFilterMap a,BizSqlEventAction action,boolean vistaPrevia) throws Exception;
	public String hasGenerateAviso(BizSqlEventAction action) throws Exception; 
	public String getDescripcion() throws Exception; 
	public boolean read(String company,String id, JFilterMap param) throws Exception; 
	public void unSerialize(String zData) throws Exception; 
	public void mensajeEnviado(String marca) throws Exception;

}
