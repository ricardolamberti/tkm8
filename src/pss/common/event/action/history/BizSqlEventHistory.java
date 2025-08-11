package pss.common.event.action.history;

import java.io.ByteArrayOutputStream;
import java.net.URLDecoder;
import java.util.Date;

import org.apache.commons.lang.CharEncoding;

import net.glxn.qrgen.image.ImageType;
import pss.JPath;
import pss.common.event.action.BizSqlEventAction;
import pss.common.event.action.IActionData;
import pss.core.data.BizPssConfig;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObjBD;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.biblioteca.BizOldBiblioteca;

public class BizSqlEventHistory extends JRecord {

  private JString pCompany = new JString();
  private JString pMensajeId = new JString();
  private JString pFundamento = new JString();
  private JString pQRcode = new JString();
  private JString pDestinatario = new JString();
  private JString pAccion = new JString();
  private JDateTime pFecha = new JDateTime();
  private JDateTime pFechaEnvio = new JDateTime();
  private JLong pIdaction = new JLong();
  private JLong pIdhistory = new JLong();
  private JString pIdevento = new JString();
  private JBoolean pRemitido = new JBoolean();
  private JString pArchivoAsociado = new JString();
  private JLong pContenido = new JLong();
  private JString pLink = new JString() {
  	public void preset() throws Exception {
  		pLink.setValue(getLink());
  	};
  };

  private JObjBD pObjEvento = new JObjBD() {
  	public void preset() throws Exception {
  		pObjEvento.setValue(getObjSqlEvent());
  	};
  };
  private JString pQRImagen = new JString() {
  	public void preset() throws Exception { 
  		pQRImagen.setValue(generateQR());
  	};
  };

  private JString pMensajeContenido = new JString() {
  	public void preset() throws Exception {
  		if (pMensajeContenido.isRawNotNull()) return ;
  		pMensajeContenido.setValue(getMensajeContenido());
  	};
  };

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setQR(String zValue) throws Exception {    pQRcode.setValue(zValue);  }
  public String getQR() throws Exception {     return pQRcode.getValue();  }
  public void setFundamento(String zValue) throws Exception {    pFundamento.setValue(zValue);  }
  public String getFundamento() throws Exception {     return pFundamento.getValue();  }
  public void setMensaje(String zValue) throws Exception {    pMensajeContenido.setValue(zValue);  }
  public String getMensaje() throws Exception { 
  	if (pMensajeContenido.getValue().startsWith("%"))
    	return URLDecoder.decode(pMensajeContenido.getValue(), CharEncoding.ISO_8859_1);  
  	return pMensajeContenido.getValue();  
  }
  public boolean isNullMensaje() throws Exception { return  pMensajeContenido.isNull(); } 
  public void setNullToMensaje() throws Exception {  pMensajeContenido.setNull(); } 
  public void setMensajeId(String zValue) throws Exception {    pMensajeId.setValue(zValue);  }
  public String getMensajeId() throws Exception {     return pMensajeId.getValue();  }
  public void setDestinatario(String zValue) throws Exception {    pDestinatario.setValue(zValue);  }
  public String getDestinatario() throws Exception {     return pDestinatario.getValue();  }
  public boolean isNullDestinatario() throws Exception { return  pDestinatario.isNull(); } 
  public void setNullToDestinatario() throws Exception {  pDestinatario.setNull(); } 
  public void setAccion(String zValue) throws Exception {    pAccion.setValue(zValue);  }
  public String getAccion() throws Exception {     return pAccion.getValue();  }
  public boolean isNullAccion() throws Exception { return  pAccion.isNull(); } 
  public void setNullToAccion() throws Exception {  pAccion.setNull(); } 
  public void setIdaction(long zValue) throws Exception {    pIdaction.setValue(zValue);  }
  public long getIdaction() throws Exception {     return pIdaction.getValue();  }
  public boolean isNullIdaction() throws Exception { return  pIdaction.isNull(); } 
  public void setNullToIdaction() throws Exception {  pIdaction.setNull(); } 
  public void setIdhistory(long zValue) throws Exception {    pIdhistory.setValue(zValue);  }
  public long getIdhistory() throws Exception {     return pIdhistory.getValue();  }
  public boolean isNullIdhistory() throws Exception { return  pIdhistory.isNull(); } 
  public void setNullToIdhistory() throws Exception {  pIdhistory.setNull(); } 
  public void setIdevento(String zValue) throws Exception {    pIdevento.setValue(zValue);  }
  public String getIdevento() throws Exception {     return pIdevento.getValue();  }
  public boolean isNullIdevento() throws Exception { return  pIdevento.isNull(); } 
  public void setNullToIdevento() throws Exception {  pIdevento.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setFecha(Date zValue) throws Exception {    pFecha.setValue(zValue);  }
  public Date getFecha() throws Exception {     return pFecha.getValue();  }
  public void setRemitido(boolean zValue) throws Exception {    pRemitido.setValue(zValue);  }
  public boolean getRemitido() throws Exception {     return pRemitido.getValue();  }
  public void setFechaEnvio(Date zValue) throws Exception {    pFechaEnvio.setValue(zValue);  }
  public Date getFechaEnvio() throws Exception {     return pFechaEnvio.getValue();  }
  public void setArchivoAsociado(String zValue) throws Exception {    pArchivoAsociado.setValue(zValue);  }
  public String getArchivoAsociado() throws Exception {     return pArchivoAsociado.getValue();  }

  public boolean hasFileElectronico() throws Exception {     return pContenido.isNotNull();  }

  /**
   * Constructor de la Clase
   */
  public BizSqlEventHistory() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "fecha", pFecha );
    this.addItem( "fecha_envio", pFechaEnvio );
    this.addItem( "fundamento", pFundamento );
    this.addItem( "mensaje", pMensajeId );
    this.addItem( "mensaje_contenido", pMensajeContenido );
    this.addItem( "destinatario", pDestinatario );
    this.addItem( "accion", pAccion );
    this.addItem( "remitido", pRemitido );
    this.addItem( "id_action", pIdaction );
    this.addItem( "id_history", pIdhistory );
    this.addItem( "id_evento", pIdevento );
    this.addItem( "qrcode", pQRcode );
    this.addItem( "contenido", pContenido );
    this.addItem( "archivo_asociado", pArchivoAsociado );
    this.addItem( "obj_evento", pObjEvento );
    this.addItem( "qr_imagen", pQRImagen );
    this.addItem( "qr_link", pLink);

  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_history", "Id history", false, false, 64 );
    this.addFixedItem( FIELD, "company", "compania", true, true, 15 );
    this.addFixedItem( FIELD, "id_action", "Id action", true, true, 64 );
    this.addFixedItem( FIELD, "id_evento", "Id evento", true, true, 64 );
    this.addFixedItem( FIELD, "fecha", "Fecha", true, true, 18 );
    this.addFixedItem( FIELD, "mensaje", "Mensaje", true, true, 4000 );
    this.addFixedItem( FIELD, "fundamento", "Fundamento", true, true, 4000 );
    this.addFixedItem( FIELD, "destinatario", "Destinatario", true, true, 250 );
    this.addFixedItem( FIELD, "accion", "Accion", true, true, 50 );
    this.addFixedItem( FIELD, "remitido", "remitido", true, false, 1);
    this.addFixedItem( FIELD, "fecha_envio", "Fecha envio", true, false, 18 );
    this.addFixedItem( FIELD, "qrcode", "QRCode", true, false, 2000 );
    this.addFixedItem( FIELD, "contenido", "Contenido", true, false, 18 );
    this.addFixedItem( VIRTUAL, "archivo_asociado", "Archivo asociado", true, false, 2000 );
    this.addFixedItem( VIRTUAL, "qr_imagen", "imagen", true, false, 4000 );
    this.addFixedItem( VIRTUAL, "qr_link", "link", true, false, 4000 );
    this.addFixedItem( VIRTUAL, "mensaje_contenido", "mensaje", true, false, 4000 );
    this.addFixedItem( RECORD, "obj_evento", "Obj.Evento", true, true, 50 ).setClase(BizSqlEventAction.class);
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "evt_sqleventos_history"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( long zIdhistory ) throws Exception { 
    addFilter( "id_history",  zIdhistory ); 
    return read(); 
  } 
  public boolean readByCode( String zQrCode ) throws Exception { 
    addFilter( "qrcode",  zQrCode ); 
    return read(); 
  } 
  
  public void clean() throws Exception {
  	objEvent=null;
  	objEventAct=null;
  };
  IActionData objEvent;
  public IActionData getObjSqlEvent() throws Exception {
  	return objEvent=getObjSqlEventAction().getObjSqlEvent();
  }
  BizSqlEventAction objEventAct;
  public BizSqlEventAction getObjSqlEventAction() throws Exception {
  	if (objEventAct!=null) return objEventAct;
  	BizSqlEventAction s = new BizSqlEventAction();
  	s.dontThrowException(true);
  	if (!s.read(getIdaction())) return null;
  	return objEventAct=s;
  }
  public String getLink() throws Exception {
  	return BizPssConfig.getPssConfig().getUrlTotal()+"/publicweb/"+pQRcode.getValue();
  }
  public String generateLinkQR(String file) throws Exception {
  	if (pQRcode.isNotNull()) 
		  return getLink();
		String semilla = ""+(JTools.randInt(10000, 600000)*7l);
		semilla += System.currentTimeMillis();
		semilla += (JTools.randInt(7777, 66666)*13l);
		semilla = "informe_"+getIdaction()+"_"+JTools.AsciiToHex(semilla.length()<20?JTools.LPad(semilla, 20, "A"):semilla.substring(0,20))+file;
		this.pQRcode.setValue(semilla);
		return getLink();
  }

  @Override
  public void processInsert() throws Exception {
  	if (pArchivoAsociado.isNotNull()) {
  		String filename = pArchivoAsociado.getValue();
  		if (pArchivoAsociado.getValue().length()>200) {
  			// bug en algunos casos quedo el archivo y no el nombre
  			filename = "rep"+filename.hashCode()+".html";
				JTools.writeStringToFile(URLDecoder.decode(pArchivoAsociado.getValue()), JPath.PssPathTempFiles() + "/" + filename);
  		}
  		String source=JPath.PssPathTempFiles()+"/"+filename;
  		int lastPunto = source.lastIndexOf('.')+1;
  		String type = source.substring(lastPunto);				
  		BizOldBiblioteca biblo = new BizOldBiblioteca();
	  	biblo.setContentByFilename(getCompany(),source, type);
	  	biblo.processInsert();
	  	this.pContenido.setValue(biblo.getId());
 	  }
  	if (pMensajeId.isNull()) {
  		String filename = "";
  		filename = "repmsg"+this.hashCode()+".html";
			JTools.writeStringToFile(URLDecoder.decode(pMensajeContenido.getValue()), JPath.PssPathTempFiles() + "/" + filename);
  		String source=JPath.PssPathTempFiles()+"/"+filename;
  		int lastPunto = source.lastIndexOf('.')+1;
  		String type = source.substring(lastPunto);				
  		BizOldBiblioteca biblo = new BizOldBiblioteca();
	  	biblo.setContentByFilename(getCompany(),source, type);
	  	biblo.processInsert();
	  	this.pMensajeId.setValue("MSG:"+biblo.getId());
  	}
  	super.processInsert();
  }
  @Override
  public void processUpdate() throws Exception {
  	BizSqlEventHistory old= new BizSqlEventHistory();
  	old.dontThrowException(true);
  	if (old.read(getIdhistory())) {
  		old.setDestinatario(getDestinatario());
    	old.update();
    	return;
  	}
    super.processUpdate();
  }
  
  public String getMensajeContenido() throws Exception {
  	if (pMensajeId.isNull()) return pMensajeContenido.getRawValue();
  	if (!pMensajeId.getValue().startsWith("MSG:")) {
  		return pMensajeId.getValue();
  	} else {
  		return JTools.byteVectorToString(getObjBibliotecaMsg().getContentAsByteArray(getCompany()));
  	}
  }
  public void processDelete() throws Exception {
		if (pContenido.isNotNull()) {
			BizOldBiblioteca biblo = new BizOldBiblioteca();
			biblo.dontThrowException(true);
			if (biblo.read(pContenido.getValue()))
				biblo.processDelete();
		}
		if (pMensajeId.isNotNull()) {
			try {
				BizOldBiblioteca biblo = new BizOldBiblioteca();
				biblo.dontThrowException(true);
				if (biblo.read(JTools.getLongFirstNumberEmbedded(pMensajeId.getValue())))
					biblo.processDelete();
			} catch (Exception e) {
				PssLogger.logError(e);
			}
		}
		super.processDelete();
	}

  JLong pIdBiblio = new JLong();
	public void setIdArchivo(long zValue) throws Exception {    pIdBiblio.setValue(zValue);  }
	public boolean isNullIdArchivo() throws Exception {    return pIdBiblio.isNull();  }
	public long getIdArchivo() throws Exception {     return pIdBiblio.getValue();  }
	 
	
  BizOldBiblioteca biblio = null;
  BizOldBiblioteca biblioMsg = null;
  public String getFileElectronico() throws Exception {
  	if (this.pContenido.isNull()) return null;
  	return this.getObjBiblioteca().getContentAsFilename(getCompany());

  }
  public String getFileElectronico(String company,String titulo) throws Exception {
  	if (this.pContenido.isNull()) return null;
  	BizOldBiblioteca biblio =this.getObjBiblioteca();
  	return biblio.getContentAsFilename(company,titulo);

  }

  public BizOldBiblioteca getObjBiblioteca() throws Exception {
  	if (this.pContenido.isNull()) return null;
  	if (this.biblio!=null) return this.biblio;
  	BizOldBiblioteca b = new BizOldBiblioteca();
  	b.read(this.pContenido.getValue());
  	return (this.biblio=b);
  }
  
  public BizOldBiblioteca getObjBibliotecaMsg() throws Exception {
  	if (!this.pMensajeId.getValue().startsWith("MSG")) return null;
  	if (this.biblioMsg!=null) return this.biblioMsg;
  	BizOldBiblioteca b = new BizOldBiblioteca();
  	b.read(JTools.getLongFirstNumberEmbedded(this.pMensajeId.getValue()));
  	return (this.biblioMsg=b);
  }
  
	public void execProcessReenviar() throws Exception {
		JExec oExec = new JExec(this, "processReenviar") {

			@Override
			public void Do() throws Exception {
				processReenviar();
			}
		};
		oExec.execute();
	}
  
  public void processReenviar() throws Exception {
//  	setFecha(new Date());
//  	setMensaje(getObjSqlEventAction().generateMessage(this));
//  	processUpdate();
  	getObjSqlEventAction().send(null,this);

  }
  public String generateQR() throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JTools.generateQRCode(getLink(),out,ImageType.PNG,500,500);
		String outer= "data:image/png;base64,"+(java.util.Base64.getEncoder().encodeToString(out.toByteArray()).replaceAll("\r\n", ""));
		return outer;
	}
}
