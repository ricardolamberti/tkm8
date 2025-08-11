package pss.common.restJason.apiReqHistory;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import pss.common.documentos.biblioteca.BizBiblioteca;
import pss.common.regions.entidad.BizEntidad;
import pss.common.restJason.JClientApiProcess;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.JExec;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JHtml;
import pss.core.services.fields.JLOB;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
public class BizApiRequestHistory extends JRecord  {

	public static String ERROR = "ERROR";
	public static String OK = "OK";
	public static String PENDING = "PEND";
	public static String VENCIDO = "VENC";
	
	
	
  private static final String HTML_NEW_LINE = "<br>";
	
  public static final String ENTRANTE ="E";
  public static final String SALIENTE ="S";

	//-------------------------------------------------------------------------//
	// Propiedades de la Clase
	//-------------------------------------------------------------------------//
	private JLong pId = new JLong();
	private JString pCompany = new JString();
	private JString pOper = new JString();
	private JString pType = new JString();
	private JString pUser = new JString();
	private JString pUrl = new JString();
	private JString pKey = new JString();
	private JString pRequestType = new JString();
	private JString pMethod = new JString();
	private JLong pEntidad = new JLong();
	private JLong pIdRequest = new JLong();
	private JLong pIdResponse = new JLong();
	private JString pStatus = new JString();
	private JString pStatusMessage = new JString();
	private JString pAddressSource = new JString();
	private JDateTime pCreationDate = new JDateTime();
	private JString pInternalKey = new JString();
	private JLOB pRequest = new JLOB();
	private JLOB pResponse = new JLOB();
	
  private JHtml pRequestHtml = new JHtml() {
  	public void preset() throws Exception {
  		setValue(getRequestConverHtml());
  	}
  };
  private JHtml pResponseHtml = new JHtml() {
  	public void preset() throws Exception {
  		setValue(getResponseConverHtml());
  	}
  };
	
	
	public long getId() throws Exception {return pId.getValue();	}
	public String getCompany() throws Exception {return pCompany.getValue();	}
	public String getUser() throws Exception {return pUser.getValue();	}
	public String getUrl() throws Exception {return pUrl.getValue();	}
	public String getMethod() throws Exception {return pMethod.getValue();	}
	public long getIdRequest() throws Exception {return pIdRequest.getValue();	}
	public long getIdResponse() throws Exception {return pIdResponse.getValue();	}
	public String getStatus() throws Exception {return pStatus.getValue();	}
	public String getStatusMessage() throws Exception {return pStatusMessage.getValue();	}
	public String getAddreddSource() throws Exception {return pAddressSource.getValue();	}
	public Date getCreationDate() throws Exception { return pCreationDate.getValue();	}
	public String getType() throws Exception {return pType.getValue();	}
	public String getOper() throws Exception {return pOper.getValue();	}
	public long getEntidad() throws Exception {return pEntidad.getValue();	}
	public String getKey() throws Exception {return pKey.getValue();	}

	public void setCompany(String value) {this.pCompany.setValue(value);}
	public void setUser(String value) {this.pUser.setValue(value);}
	public void setUrl(String value) {this.pUrl.setValue(value);}
	public void setMethod(String value) {this.pMethod.setValue(value);}
	public void setOper(String value) {this.pOper.setValue(value);}
	public void setType(String value) {this.pType.setValue(value);}
	public void setKey(String value) {this.pKey.setValue(value);}
	public void setRequest(String value) {this.pRequest.setValue(value);}
	public void setResponse(String value) {this.pResponse.setValue(value);}
	public void setIdRequest(long value) {this.pIdRequest.setValue(value);}
	public void setIdResponse(long value) {this.pIdResponse.setValue(value);}
	public void setStatus(String value) {this.pStatus.setValue(value);}
	public void setStatusMessage(String value) {this.pStatusMessage.setValue(value);}
	public void setAddressSource(String value) {this.pAddressSource.setValue(value);}
	public void setCreationDate(Date value) {this.pCreationDate.setValue(value);}
	public void setEntidad(long value) {this.pEntidad.setValue(value);}
	public void setInternalKey(String value) {this.pInternalKey.setValue(value);}


	//-------------------------------------------------------------------------//
	// Constructor de la Clase
	//-------------------------------------------------------------------------//
	public BizApiRequestHistory() throws Exception {}

	@Override
	public void createProperties() throws Exception {
		addItem("id", pId);
		addItem("company", pCompany);
		addItem("usuario", pUser);
		addItem("url", pUrl);
		addItem("method", pMethod);
		addItem("oper", pOper);
		addItem("entidad", pEntidad);
		addItem("type", pType);
		addItem("key", pKey);
		addItem("id_request", pIdRequest);
		addItem("id_response", pIdResponse);
		addItem("status", pStatus);
		addItem("status_msg", pStatusMessage);
		addItem("address_source", pAddressSource);
		addItem("creation_date", pCreationDate);
		addItem("request_html", pRequestHtml);
		addItem("response_html", pResponseHtml);
	}
	
	
	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem( KEY, "id", "ID", false, false, 18);
		addFixedItem( FIELD, "company", "Empresa", true, true, 15);
		addFixedItem( FIELD, "usuario", "user", true, true, 15);
		addFixedItem( FIELD, "url", "URL", true, true, 400);
		addFixedItem( FIELD, "id_request", "Request", true, false, 18);
		addFixedItem( FIELD, "id_response", "Response", true, false, 18);
		addFixedItem( FIELD, "method", "Método", true, true, 1000);
		addFixedItem( FIELD, "oper", "Operación", true, false, 1);
		addFixedItem( FIELD, "key", "Key", true, false, 30);
		addFixedItem( FIELD, "type", "Tipo", true, false, 10);
		addFixedItem( FIELD, "entidad", "Entidad", true, false, 18);
		addFixedItem( FIELD, "status", "Status", true, false, 50);
		addFixedItem( FIELD, "status_msg", "Status Msg", true, false, 4000);
		addFixedItem( FIELD, "address_source", "Address", true, false, 50);
		addFixedItem( FIELD, "creation_date", "Fecha Creación", true, true, 18);
		addFixedItem( VIRTUAL, "request_html", "request html", true, false, 10000);
		addFixedItem( VIRTUAL, "response_html", "response html", true, false, 10000);
	}

	@Override
	public String GetTable() {
		return "API_JSON_REQ_HISTORY";
	}

	public boolean read(long id) throws Exception {
		addFilter("id", id);
		this.read();
		return true;
	}

	public boolean isEntrante() throws Exception {
		return this.pOper.getValue().equals(ENTRANTE);
	}

	public boolean isSaliente() throws Exception {
		return this.pOper.getValue().equals(SALIENTE);
	}

	public boolean isError() throws Exception {
		return this.pStatus.getValue().equals(ERROR);
	}

	public boolean isOk() throws Exception {
		return this.pStatus.getValue().equals(OK);
	}

	public boolean isPendiente() throws Exception {
		return this.pStatus.getValue().equals(PENDING);
	}

	public void processSAF() throws Exception {
		if (this.pUser.isEmpty()) this.pUser.setValue(BizUsuario.getUsr().GetUsuario());
		this.checkKey();
		this.pStatus.setValue(PENDING);
		this.pOper.setValue(SALIENTE);
		this.processInsert();
//		JApiRobot.startRobot();
	}
	
	public void processInsert() throws Exception {
		this.setCreationDate(JDateTools.CurrentDateTimeToDate());
		this.pushRequest();
		this.pushResponse();
		super.insert();
		pId.setValue(this.getIdentity("id"));
	}

	
//	private void updateRequest() throws Exception {
//		// pone el body en biblioteca
//		if (this.getIdRequest()==0L) return ;
//		BizBiblioteca biblo = this.getObjRequest();
//		biblo.setContenido(this.pRequest.getValue());
//		biblo.updateContent();
//		biblo.update();
//	}

//	private void updateResponse() throws Exception {
//		// pone el body en biblioteca
//		if (this.getIdResponse()==0L) return ;
//		BizBiblioteca biblo = this.getObjRequest();
//		biblo.setContenido(this.pResponse.getValue());
//		biblo.updateContent();
//		biblo.update();
//	}
	
	public static JMap<String, String> getStatusMap() throws Exception {
  	JMap<String, String> map = new JCollectionFactory().createMap();
  	map.addElement(BizApiRequestHistory.ERROR, "Error");
  	map.addElement(BizApiRequestHistory.OK, "Ok");
  	map.addElement(BizApiRequestHistory.PENDING, "Pendiente");
  	return map;
	}	

	public static JMap<String, String> getOperMap() throws Exception {
  	JMap<String, String> map = new JCollectionFactory().createMap();
  	map.addElement(BizApiRequestHistory.ENTRANTE, "Entrantes");
  	map.addElement(BizApiRequestHistory.SALIENTE, "Salientes");
  	return map;
	}	
 	
	public String getRequestConverHtml() throws Exception {
		return BizApiRequestHistory.toHmml(this.findRequest());
	}
	
	public String getResponseConverHtml() throws Exception {
		return BizApiRequestHistory.toHmml(this.findResponse());
	}

  
  public static String toHmml(String rawText) {
    String result = rawText;
    if(StringUtils.isNotEmpty(rawText)) {
    		result = result.replace(":{", ":{" + HTML_NEW_LINE + "\n");
        result = result.replaceAll("}", "}" + HTML_NEW_LINE + "\n");
        result = result.replaceAll(",", "," + HTML_NEW_LINE + "\n");  				
    }
    return result;
	}

  
  
	public boolean isStatusOK() throws Exception {
		return this.getStatus().equals(BizApiRequestHistory.OK);
	}
  
	public boolean isStatusError() throws Exception {
		return this.getStatus().equals(BizApiRequestHistory.ERROR);
	}

	public boolean isStatusPendiente() throws Exception {
		return this.getStatus().equals(BizApiRequestHistory.PENDING);
	}

	public boolean isStatusVencido() throws Exception {
		return this.getStatus().equals(BizApiRequestHistory.VENCIDO);
	}

	public void execProcessEnviar() throws Exception {
		JExec exec = new JExec(null, null) {
			public void Do() throws Exception {
				processEnviar();
			}
		};
		exec.execute();
	}
	
	public void processEnviar() throws Exception {
		JClientApiProcess cp = new JClientApiProcess();
		cp.setBody(this.getObjRequest().getContenido());
		cp.setCompany(this.getCompany());
		cp.setURL(this.getUrl());
		cp.setMethod(this.getMethod());
		try {
			cp.execute(this.getType());
			this.processMarcarOk();
		} catch (Exception e) {
			this.processError(e);
		}
	}
		
	public void processMarcarOk() throws Exception {
		BizApiRequestHistory h = new BizApiRequestHistory();
		h.read(this.getId());
		h.setStatus(OK);
		h.update();
		this.setStatus(h.getStatus());
	}

	public void processMarcarPendiente() throws Exception {
		BizApiRequestHistory h = new BizApiRequestHistory();
		h.read(this.getId());
		h.setStatus(PENDING);
		h.update();
		this.setStatus(h.getStatus());
	}

	public void processError(Exception e) throws Exception {
		BizApiRequestHistory h = new BizApiRequestHistory();
		h.read(this.getId());
		h.setStatus(ERROR);
		h.setStatusMessage(JTools.trunc(e.getMessage(), 3500));
		h.update();
		this.setStatus(h.getStatus());
	}

	public String getUrlTrunc() throws Exception {
		String url = this.getUrl();
		int idx = url.indexOf("?");
		if (idx!=-1) url=url.substring(0, idx);
		return JTools.trunc(url, 80);
	}
	
	private BizEntidad entidad;
	public BizEntidad getObjEntidad() throws Exception {
		if (this.entidad!=null) return this.entidad;
		BizEntidad e = new BizEntidad();
		e.read(this.getEntidad());
		return (this.entidad=e);
	}
	
	public String getDescrEntidad() throws Exception {
		if (this.getEntidad()==0L) return "";
		return this.getObjEntidad().getDescripcion();
	}

	public String findOther() throws Exception {
		if (this.isSaliente()) return this.getDescrEntidad();
		return this.getAddreddSource();
	}

	public String findRequest() throws Exception {
		if (this.pRequest.isNotNull()) return this.pRequest.getValue();
		if (this.pIdRequest.getValue()==0L) return "";
		try {
			return this.getObjRequest().findContents();
		} catch (Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}
	
	public String findResponse() throws Exception {
		if (this.pResponse.isNotNull()) return this.pResponse.getValue();
		if (this.pIdResponse.getValue()==0L) return "";
		try {
			return this.getObjResponse().findContents();
		} catch (Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}
	

	private BizBiblioteca request;
	public BizBiblioteca getObjRequest() throws Exception {
		if (this.request!=null) return this.request;
		BizBiblioteca b = new BizBiblioteca();
		b.read(this.pIdRequest.getValue());
		return (this.request=b);
	}

	private BizBiblioteca response;
	public BizBiblioteca getObjResponse() throws Exception {
		if (this.response!=null) return this.response;
		BizBiblioteca b = new BizBiblioteca();
		b.read(this.pIdResponse.getValue());
		return (this.response=b);
	}
	
	public void pushRequest() throws Exception {
		if (this.pRequest.isEmpty()) return;
		// pone el request en biblioteca
		BizBiblioteca biblo = new BizBiblioteca();
		biblo.setCompany(this.getCompany());
		biblo.setTipo("json");
		biblo.setContenido(this.pRequest.getValue());
		biblo.captureContent("req_"+this.getMethod());
		biblo.processInsert();
		this.pRequest.setNull(); // quedo en biblo
		this.pIdRequest.setValue(biblo.getId());
		this.request=biblo;
	}

	public void pushResponse() throws Exception {
		if (this.pResponse.isEmpty()) return;
		// pone el request en biblioteca
		BizBiblioteca biblo = new BizBiblioteca();
		biblo.setCompany(this.getCompany());
		biblo.setTipo("json");
		biblo.setContenido(this.pResponse.getValue());
		biblo.captureContent("res_"+this.getMethod());
		biblo.processInsert();
		this.pResponse.setNull(); // quedo en biblo
		this.pIdResponse.setValue(biblo.getId());
		this.response=biblo;
	}

	public void checkKey() throws Exception {
		if (this.pKey.isEmpty()) return;
		JRecords<BizApiRequestHistory> recs = new JRecords<BizApiRequestHistory>(BizApiRequestHistory.class);
		recs.addFilter("company", this.getCompany());
		recs.addFilter("key", this.getKey());
		recs.addFilter("status", "('"+PENDING+"', '"+ERROR+"')", "IN");
		recs.readAll();
		JIterator<BizApiRequestHistory> iter = recs.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizApiRequestHistory h = iter.nextElement();
			h.setStatus(VENCIDO);
			h.update();
		}
	}
	
}

