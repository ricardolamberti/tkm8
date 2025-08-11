package pss.bsp.contrato.detalle.prorrateo.tickets;

import java.net.URLEncoder;
import java.util.Date;

import org.apache.commons.codec.net.URLCodec;

import pss.JPath;
import pss.bsp.GuiModuloBSP;
import pss.common.customList.config.customlist.GuiCustomList;
import pss.common.event.action.BizSqlEventAction;
import pss.common.event.action.IActionData;
import pss.common.event.action.history.BizSqlEventHistory;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.regions.company.BizCompany;
import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObjBD;
import pss.core.services.fields.JString;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;
import pss.tourism.pnr.BizPNRTicket;

public class BizTicketProrrateo extends JRecord implements IActionData {

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public JString pCompany = new JString();
  public JString pCustomer = new JString();
  public JObjBD pTicket = new JObjBD();
  public JLong pId = new JLong();
  public JLong pLinea = new JLong();
  public JLong pIdContrato = new JLong();
  public JFloat pPorcentaje = new JFloat();
  public JString pCodigoMoneda = new JString();
  public JCurrency pComision = new JCurrency() 	{
  	public void preset() throws Exception {
		setSimbolo(pCodigoMoneda.isNotNull());
		setMoneda(pCodigoMoneda.getValue());
  	}
	};
  public JDate pFecha = new JDate();

  /**
   * Constructor de la Clase
   */
  public BizTicketProrrateo() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id", pId );
    this.addItem( "linea", pLinea );
    this.addItem( "customer", pCustomer );
    this.addItem( "moneda", pCodigoMoneda );
    this.addItem( "id_contrato", pIdContrato );
    this.addItem( "ticket", pTicket );
    this.addItem( "porcentaje", pPorcentaje );
    this.addItem( "comision", pComision );
    this.addItem( "fecha", pFecha );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "id", "Id", true, false, 32 );
    this.addFixedItem( KEY, "linea", "Linea", false, false, 32 );
    this.addFixedItem( FIELD, "id_contrato", "Contrato", true, true, 18 );
    this.addFixedItem( FIELD, "moneda", "moneda", true, true, 18 );
    this.addFixedItem( FIELD, "customer", "Cliente", true, true, 18 );
    this.addFixedItem( RECORD, "ticket", "Ticket", true, true, 18 ).setClase(BizPNRTicket.class);
    this.addFixedItem( FIELD, "porcentaje", "Porcentaje", true, true, 18,2 );
    this.addFixedItem( FIELD, "comision", "Comision", true, true, 18,2 );
    this.addFixedItem( FIELD, "fecha", "Fecha", true, false, 18 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return null; }

  public String getCustomer() throws Exception {
  	return pCustomer.getValue();
  }
  public String getCompany() throws Exception {
  	return pCompany.getValue();
  }
  public String getCodigoMoneda() throws Exception {
  	return pCodigoMoneda.getValue();
  }
  public void setCodigoMoneda(String value) throws Exception {
  	pCodigoMoneda.setValue(value);
  }

  public BizPNRTicket getObjTicket() throws Exception {
  	return (BizPNRTicket)pTicket.getValue();
  }
  

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public BizSqlEventHistory  generarAviso(JFilterMap a,BizSqlEventAction action,boolean vistaPrevia) throws Exception {
		BizSqlEventHistory hist= new BizSqlEventHistory();
  	hist.setFecha(new Date());
  	hist.setIdevento(""+action.getIdevento());
  	hist.setCompany(getCompany());
  	hist.setFundamento(action.getDescripcion());
  	hist.setMensajeUsuario(action.getMensajeUsuario());
  	hist.setIdaction(action.getIdaction());
  	hist.setAccion(action.getAction());
  	hist.setDestinatario(action.getDestinatario());
 		hist.setMensaje(action.getMensajeAviso(a,hist));
  	if (!vistaPrevia) hist.setArchivoAsociado(action.getArchivoAsociado(a,hist));
  	return hist;
  }
  
	public String getNotificacionAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist) throws Exception {
  	return "";
  }
	public String getCorreoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist) throws Exception {
		if (!action.isAdjunto() && !action.isAccionURL()) {
			if (action.isOutEXCEL()) {
				return URLEncoder.encode(GuiModuloBSP.getBSPConsolaUsuario().getHtmlView(71,"excel",a),"ISO-8859-1").replace("+", "%20");
			}
			if (action.isOutCSV()) {
				return URLEncoder.encode(GuiModuloBSP.getBSPConsolaUsuario().getHtmlView(71,"csv",a),"ISO-8859-1").replace("+", "%20");
			}
			if (action.isOutPDF()) {
				BizPlantilla l =BizCompany.getObjPlantillaById(getCompany(), action.getIdplantilla());
		  	return l.generateDocSimple(this,action);
			}
			return URLEncoder.encode(GuiModuloBSP.getBSPConsolaUsuario().getHtmlView(71,"html",a),"ISO-8859-1").replace("+", "%20");
		}
		// mando un correo
		return action.getObjPlantilla().generateDocSimple(hist,this);
  }
	
	public String getCorreoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist,String campo,String valor) throws Exception {
		return getCorreoAviso(a,action, hist);
	}
	public String getArchivoAsociadoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist,String campo,String valor) throws Exception {
		return getCorreoAviso(a,action, hist,campo,valor);
	}	
	public String getArchivoAsociadoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist) throws Exception {
		if (action.isOutPantalla()) {
			GuiCustomList l = new GuiCustomList();
			l.setRecord(this);
			String file = l.hashCode()+".html";
			String content = GuiModuloBSP.getBSPConsolaUsuario().getHtmlView(71,"html",a);
			JTools.writeStringToFile(content,JPath.PssPathTempFiles()+"/"+file);
			return file;
		}
		if (action.isOutEXCEL()) {
			GuiCustomList l = new GuiCustomList();
			l.setRecord(this);
			String file = l.hashCode()+".xls";
			String content = GuiModuloBSP.getBSPConsolaUsuario().getHtmlView(71,"excel",a);
			JTools.writeStringToFile(content,JPath.PssPathTempFiles()+"/"+file);
			return file;
		}
		if (action.isOutCSV()) {
			GuiCustomList l = new GuiCustomList();
			l.setRecord(this);
			String file = l.hashCode()+".csv";
			String content = GuiModuloBSP.getBSPConsolaUsuario().getHtmlView(71,"csv",a);
			JTools.writeStringToFile(content,JPath.PssPathTempFiles()+"/"+file);
			return file;
		}
		if (action.isOutPDF()) {
			BizPlantilla l =BizCompany.getObjPlantillaById(getCompany(), action.getIdplantilla());
	  	String s =  new URLCodec().decode(l.generateDocSimple(this,action));
			String file = "doc"+this.hashCode()+".html";
			String content = l.htmlToHtml(null,true,s);
			JTools.writeStringToFile(content,JPath.PssPathTempFiles()+"/"+file);
			
			return file;
		}
		
		return null;
  }


	@Override
	public String getDescripcion() throws Exception {
		return "Liquidación";
	}



	@Override
	public String hasGenerateAviso(BizSqlEventAction action) throws Exception {
		return null;
	}


	@Override
	public void mensajeEnviado(String marca) throws Exception {
	}


	@Override
	public boolean read(String company, String id, JFilterMap param) throws Exception {
		this.pCompany.setValue(company);
		return true;
	}


}
