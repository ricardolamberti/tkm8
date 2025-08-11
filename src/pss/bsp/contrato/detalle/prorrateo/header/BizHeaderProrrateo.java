package pss.bsp.contrato.detalle.prorrateo.header;

import java.net.URLEncoder;
import java.util.Date;

import org.apache.commons.codec.net.URLCodec;

import pss.JPath;
import pss.bsp.GuiModuloBSP;
import pss.bsp.contrato.detalle.prorrateo.cliente.BizClienteProrrateos;
import pss.bsp.contrato.detalle.prorrateo.cliente.GuiClienteProrrateos;
import pss.common.customList.config.customlist.GuiCustomList;
import pss.common.event.action.BizSqlEventAction;
import pss.common.event.action.IActionData;
import pss.common.event.action.history.BizSqlEventHistory;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.regions.company.BizCompany;
import pss.core.services.fields.JIntervalDate;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JString;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;

public class BizHeaderProrrateo extends JRecord implements IActionData {

  protected JString pCompany = new JString();
  protected JIntervalDate pPeriodo = new JIntervalDate();
  
 protected JObjBDs<BizHeaderProrrateo> pTickets = new JObjBDs();
	  
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Constructor de la Clase
   */
  public BizHeaderProrrateo() throws Exception {
  }

  public void processInsertClon() throws Exception {
		super.processInsert();
	}

  public String getCompany() throws Exception {
  	return pCompany.getValue();
  }
  public void setTickets(BizClienteProrrateos tkts) throws Exception {
   pTickets.setValue(tkts);
  }

  public void createProperties() throws Exception {
     this.addItem( "company", pCompany );
   	 this.addItem( "periodo", pPeriodo );
     this.addItem( "tickets", pTickets );
    
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
  	 this.addFixedItem( KEY, "company", "company", true, true, 18 );
  	 this.addFixedItem( FIELD, "periodo", "periodo", true, true, 200 );
     this.addFixedItem( RECORDS, "tickets", "Tickets", true, true, 18 ).setClase(BizHeaderProrrateo.class);

  }
  
  /**
   * Returns the table name
   */
  public String GetTable() { return ""; }


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
	public boolean read(String company, String id, JFilterMap params) throws Exception {
		this.pCompany.setValue(company);
		GuiClienteProrrateos tkts = new GuiClienteProrrateos();
		tkts.getRecords().addFilter("company", company);
		tkts.asignFiltersFromFilterMap(params);
//		if (params.getFilterValue("periodo")!=null) {
//			tkts.getRecords().addFilter("periodo", params.getFilterValue("periodo"));
//		}
		this.pTickets.setValue(tkts.getRecords());
		return true;
	}


}
