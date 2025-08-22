package pss.bsp.carpeta;

import java.net.URLEncoder;
import java.util.Date;

import pss.JPath;
import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.contrato.BizContrato;
import pss.bsp.contrato.GuiContrato;
import pss.bsp.contrato.consolidado.GuiContratoConsolidados;
import pss.common.customList.config.carpetas.BizCarpeta;
import pss.common.customList.config.carpetas.IContenidoCarpeta;
import pss.common.customList.config.customlist.BizCustomList;
import pss.common.event.action.BizSqlEventAction;
import pss.common.event.action.IActionData;
import pss.common.event.action.history.BizSqlEventHistory;
import pss.common.security.BizUsuario;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JRecords;
import pss.core.tools.JConcatenar;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;

public class BizBSPCarpeta extends BizCarpeta implements IActionData {
  public static final String FIJOS="FIJ";
  public static final String MENSUAL="MEN";
  public static final String SEMANAL="SEM";

	public BizBSPCarpeta() throws Exception {
		super();
	}
  public void processCompletarCarpetas() throws Exception {

  	getObjCarpetaFav();
  	getObjCarpetaSemanal();
  	getObjCarpetaMensual();
  	getObjCarpetaFijos();
 
  	processCompletarCarpetasFav();
  	processCompletarCarpetasFijas();
  	super.processCompletarCarpetas(); 
		
	}	

	public void processCompletarCarpetasFijas() throws Exception {
		if (!existeReporteConsolidado()) {
			addReporteConsolidado();
		}
		addReportesDetallado();
				
		
		
		
	}
	public void processCompletarCarpetasFav() throws Exception {
		long fav = getObjCarpetaFav().getSecuencia();
		JRecords<BizCustomList> recs = new JRecords<BizCustomList>(BizCustomList.class);
		recs.addFilter("company", BizUsuario.getUsr().getCompany());
		recs.addFixedFilter("where list_id not in (select customlist from lst_carpetasv2 where LST_CUSTOM_LISTV2.list_id=lst_carpetasv2.customlist and lst_carpetasv2.company='"+BizUsuario.getUsr().getCompany()+"' and lst_carpetasv2.especial='FAV' ) "+
				" and list_id in (select list_id from LST_CUSTOM_LISTV2_FAV where LST_CUSTOM_LISTV2_FAV.list_id=LST_CUSTOM_LISTV2.list_id and LST_CUSTOM_LISTV2_FAV.company='"+BizUsuario.getUsr().getCompany()+"'  and LST_CUSTOM_LISTV2_FAV.usuario='"+BizUsuario.getUsr().GetUsuario()+"') ");
		recs.addOrderBy("description","DESC");
		JIterator<BizCustomList>it = recs.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCustomList c = it.nextElement();
			BizCarpeta carpeta = new BizCarpeta();
			carpeta.setCompany(c.getCompany());
			carpeta.setListado(c.getListId());
			carpeta.setInvisible(c.isInvisible());
			carpeta.setEspecial("FAV");
			carpeta.setPadre(fav);
			carpeta.setDescripcion(c.getDescripcion());
			carpeta.processInsert();
		}
		
		JRecords<BizCarpeta> carpetas = new JRecords<BizCarpeta>(BizCarpeta.class);
		carpetas.addFilter("company", BizUsuario.getUsr().getCompany());
		carpetas.addFilter("especial", "FAV");
		carpetas.addFixedFilter("where lst_carpetasv2.customlist is not null and customlist not in (select customlist from LST_CUSTOM_LISTV2_FAV where LST_CUSTOM_LISTV2_FAV.usuario='"+BizUsuario.getUsr().GetUsuario()+"' and LST_CUSTOM_LISTV2_FAV.list_id=lst_carpetasv2.customlist and LST_CUSTOM_LISTV2_FAV.company='"+BizUsuario.getUsr().getCompany()+"') ");
		carpetas.delete();

		
	}
  public boolean  existeReporteDetallado(BizContrato contrato) throws Exception {
  	BizCarpeta carp = new BizCarpeta();
  	carp.dontThrowException(true);
  	carp.addFilter("company", BizUsuario.getUsr().getCompany());
  	carp.addFilter("clase", GuiContrato.class.getName());
  	carp.addFilter("key_especial", contrato.getDescripcion());
  	carp.addFilter("especial",BizBSPCarpeta.FIJOS);
  	return carp.exists();
  }
  public void addReportesDetallado() throws Exception {
  	JRecords<BizContrato> contratos = new JRecords<BizContrato>(BizContrato.class);
  	contratos.addFilter("company", BizUsuario.getUsr().getCompany());
  	contratos.addFilter("active", "S");
  	JIterator<BizContrato> it = contratos.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizContrato contrato = it.nextElement();
  		if (!existeReporteDetallado(contrato)) {
  			addReporteDetallado(contrato);
  		}
  	}
  }
  public void removeReportesDetallado() throws Exception {
  	BizCarpeta fijos = getObjCarpetaFijos();
  	JRecords<BizCarpeta> carpetas = fijos.getContenidos();
  	JIterator<BizCarpeta> it = carpetas.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCarpeta reporte = it.nextElement();
  		BizBSPCarpeta bspReporte = new BizBSPCarpeta();
  		bspReporte.copyProperties(reporte);
  		if (!bspReporte.getClase().equals(GuiContrato.class.getName())) continue;
  		if (bspReporte.hasContratoAsoc()) continue;
  		bspReporte.processDelete();
  	}
  }
  public boolean hasContratoAsoc() throws Exception {
  	BizContrato c = new BizContrato();
  	c.dontThrowException(true);
  	return c.readByDescription(getCompany(), getKey());
  }
  public BizCarpeta addReporteDetallado(BizContrato contrato) throws Exception {
  	BizCarpeta carp = new BizCarpeta();
 		carp.setCompany(BizUsuario.getUsr().getCompany());
 		carp.setDescripcion("Situación contrato "+contrato.getDescripcion());
 		carp.setClase(GuiContrato.class.getName());
 		carp.setListado(contrato.getId());
 		carp.setKey(contrato.getDescripcion());
 		carp.setPadre(getObjCarpetaFijos().getSecuencia());
 		carp.setEspecial(BizBSPCarpeta.FIJOS);
 		carp.processInsert();
  	return carp;
  }	
  public boolean  existeReporteConsolidado() throws Exception {
  	BizCarpeta carp = new BizCarpeta();
  	carp.dontThrowException(true);
  	carp.addFilter("company", BizUsuario.getUsr().getCompany());
  	carp.addFilter("clase", GuiContratoConsolidados.class.getName());
  	carp.addFilter("especial",BizBSPCarpeta.FIJOS);
  	return carp.exists();
  }
  public BizCarpeta  addReporteConsolidado() throws Exception {
  	BizCarpeta carp = new BizCarpeta();
 		carp.setCompany(BizUsuario.getUsr().getCompany());
 		carp.setDescripcion("Contratos consolidados");
 		carp.setClase(GuiContratoConsolidados.class.getName());
 		carp.setListado(1);
 		carp.setPadre(getObjCarpetaFijos().getSecuencia());
 		carp.setEspecial(BizBSPCarpeta.FIJOS);
 		carp.processInsert();
  	return carp;
  }
  
  BizCarpeta objCarpetaFij;
  public BizCarpeta  getObjCarpetaFijos() throws Exception {
  	if (objCarpetaFij!=null) return objCarpetaFij;
  	BizCarpeta carp = new BizCarpeta();
  	carp.dontThrowException(true);
  	carp.addFilter("company", BizUsuario.getUsr().getCompany());
  	carp.addFilter("customlist", "null");
  	carp.addFilter("especial",BizBSPCarpeta.FIJOS);
  	if (!carp.read()) {
  		carp.setCompany(BizUsuario.getUsr().getCompany());
  		carp.setDescripcion("Reportes fijos");
  		carp.setEspecial(BizBSPCarpeta.FIJOS);
  		carp.processInsert();
  	};
  	return objCarpetaFij=carp;
  }
  BizCarpeta objCarpetaFav;
  public BizCarpeta  getObjCarpetaFav() throws Exception {
  	if (objCarpetaFav!=null) return objCarpetaFav;
  	BizCarpeta carp = new BizCarpeta();
  	carp.dontThrowException(true);
  	carp.addFilter("company", BizUsuario.getUsr().getCompany());
  	carp.addFilter("customlist", "null");
  	carp.addFilter("especial","FAV");
  	if (!carp.read()) {
  		carp.setCompany(BizUsuario.getUsr().getCompany());
  		carp.setDescripcion("Dashboard");
  		carp.setEspecial("FAV");
  		carp.processInsert();
    	
  	}
  	return objCarpetaFav= carp;
  }
  BizCarpeta objCarpetaSemanal;
  public BizCarpeta  getObjCarpetaSemanal() throws Exception {
  	if (objCarpetaSemanal!=null) return objCarpetaSemanal;
  	BizCarpeta carp = new BizCarpeta();
  	carp.dontThrowException(true);
  	carp.addFilter("company", BizUsuario.getUsr().getCompany());
  	carp.addFilter("customlist", "null");
  	carp.addFilter("especial",BizBSPCarpeta.SEMANAL);
  	if (!carp.read()) {
  		carp.setCompany(BizUsuario.getUsr().getCompany());
  		carp.setDescripcion("Informe Semanal");
  		carp.setEspecial(BizBSPCarpeta.SEMANAL);
  		carp.processInsert();
  		
  		// crear aviso
  		crearAvisosEmailSemanal(carp);

  	}
  	return objCarpetaSemanal=carp;
  }
  BizCarpeta objCarpetaMensual;
  public BizCarpeta  getObjCarpetaMensual() throws Exception {
  	if (objCarpetaMensual!=null) return objCarpetaMensual;
  	BizCarpeta carp = new BizCarpeta();
  	carp.dontThrowException(true);
  	carp.addFilter("company", BizUsuario.getUsr().getCompany());
  	carp.addFilter("customlist", "null");
  	carp.addFilter("especial",BizBSPCarpeta.MENSUAL);
  	if (!carp.read()) {
  		carp.setCompany(BizUsuario.getUsr().getCompany());
  		carp.setDescripcion("Informe mensual");
  		carp.setEspecial(BizBSPCarpeta.MENSUAL);
  		carp.processInsert();
  		
  		// crear aviso
  		crearAvisosEmailMensual(carp);
  	}
  	return objCarpetaMensual=carp;
  }
  
  public void crearAvisosEmailMensual(BizCarpeta carp) throws Exception {
  	BizSqlEventAction action = new BizSqlEventAction();
  	action.dontThrowException(true);
  	action.read(BizUsuario.getUsr().getCompany(), BizBSPCompany.class.getCanonicalName(), ""+carp.getSecuencia(), BizSqlEventAction.MENSUAL);
  	action.setCompany(BizUsuario.getUsr().getCompany());
  	action.setIdevento(""+carp.getSecuencia());
  	action.setClassevento(BizBSPCarpeta.class.getCanonicalName());
  	action.setTipoPeriodicidad(BizSqlEventAction.MENSUAL);
  	action.setUltimoDia(true);
  	action.setAdjunto(true);
  	action.setDescripcion("Informe mensual");
  	action.setAction(BizSqlEventAction.EMAIL);
  	action.setFormato(BizSqlEventAction.PANTALLA);
  	action.setCorreo("default");
  	action.processUpdateOrInsertWithCheck();
  }
  public void crearAvisosEmailSemanal(BizCarpeta carp) throws Exception {
  	BizSqlEventAction action = new BizSqlEventAction();
  	action.dontThrowException(true);
  	action.read(BizUsuario.getUsr().getCompany(), BizBSPCompany.class.getCanonicalName(), ""+carp.getSecuencia(), BizSqlEventAction.SEMANAL);
  	action.setCompany(BizUsuario.getUsr().getCompany());
  	action.setIdevento(""+carp.getSecuencia());
  	action.setClassevento(BizBSPCarpeta.class.getCanonicalName());
  	action.setTipoPeriodicidad(BizSqlEventAction.SEMANAL);
  	action.setViernes(true);
  	action.setAdjunto(true);
  	action.setDescripcion("Informe semanal");
  	action.setAction(BizSqlEventAction.EMAIL);
  	action.setFormato(BizSqlEventAction.PANTALLA);
  	action.setCorreo("default");
  	action.processUpdateOrInsertWithCheck();
  }  
	public void processcAddToFolder(BizCarpeta folder) throws Exception {
		
		if (folder.getEspecial().equals("FAV")) {
			this.processcCopyToFolder(getObjCarpetaFav());
			this.getObjCustomList().processMarcarComoFavorito();
			return;
		}
		if (folder.getEspecial().equals(BizBSPCarpeta.MENSUAL)) {
			this.processcCopyToFolder(getObjCarpetaMensual());
			return;
		}
		if (folder.getEspecial().equals(BizBSPCarpeta.SEMANAL)) {
			this.processcCopyToFolder(getObjCarpetaSemanal());
			return;
		}
		super.processcAddToFolder(folder);
	}

	public void processMoveToFolder(BizCarpeta folder) throws Exception {
		if (this.isCarpeta()) {
			throw new Exception("No se pueden enviar carpetas a carpetas especiales");
		}
		if (!this.getEspecial().equals("")) { // si sale de una carpeta especial
			if (this.getEspecial().equals("FAV")) {
				this.getObjCustomList().processDesmarcarComoFavorito();
				this.processDelete();
				return;
			}
			if (this.getEspecial().equals(BizBSPCarpeta.MENSUAL)) {
				this.processDelete();
				return;
			}
			if (this.getEspecial().equals(BizBSPCarpeta.SEMANAL)) {
				this.processDelete();
				return;
			}
		} 

		if (!folder.getEspecial().equals("")) { // si entra en una carpeta especial
			if (folder.getEspecial().equals("FAV")) {
				this.processcCopyToFolder(getObjCarpetaFav());
				this.getObjCustomList().processMarcarComoFavorito();
				return;
			}
			if (folder.getEspecial().equals(BizBSPCarpeta.MENSUAL)) {
				this.processcCopyToFolder(getObjCarpetaMensual());
				return;
			}
			if (folder.getEspecial().equals(BizBSPCarpeta.SEMANAL)) {
				this.processcCopyToFolder(getObjCarpetaSemanal());
				return;
			}
		}
	}


	public String generateInforme() throws Exception {
		JRecords<BizCarpeta> contenidos = getContenidos();
		String[] files = new String[(int)(contenidos.sizeStaticElements()*2)+1];
		int i=0;
		JIterator<BizCarpeta> it = contenidos.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCarpeta listadoCarpeta = it.nextElement();
			IContenidoCarpeta contenido = listadoCarpeta.getObjClass();
			String f = contenido.imprimir(null);
			if (f==null) continue;
			files[i++] = JPath.PssPathTempFiles() + "/" +f;
		}
		if (i==0) return null;
		String tempfile = getCompany() + "/concat" + this.hashCode() + ".html";
		JTools.MakeDirectory(JPath.PssPathTempFiles() + "/" +  BizUsuario.getUsr().getCompany());
		JConcatenar.concatenarHTML(files, JPath.PssPathTempFiles() + "/" + tempfile);
		return tempfile;
	}
	
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
				GuiBSPCarpeta l = new GuiBSPCarpeta();
				l.setRecord(this);
				String content =l.getHtmlView(10,"xls",a);
				return URLEncoder.encode(content,"ISO-8859-1").replace("+", "%20");
			}
			if (action.isOutCSV()) {
				GuiBSPCarpeta l = new GuiBSPCarpeta();
				l.setRecord(this);
				String content =l.getHtmlView(10,"csv",a);
				return URLEncoder.encode(content,"ISO-8859-1").replace("+", "%20");
			}
			if (action.isOutPDF()) {
				String file = this.generateInforme();
				if (file==null) return null;
				return URLEncoder.encode(JTools.readFileAsString(JPath.PssPathTempFiles()+"/"+file),"ISO-8859-1").replace("+", "%20");
			}
			String file = this.generateInforme();
			if (file==null) return null;
			return URLEncoder.encode(JTools.readFileAsString(JPath.PssPathTempFiles()+"/"+file),"ISO-8859-1").replace("+", "%20");
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
			String file = this.generateInforme();
			return file;
		}
		if (action.isOutEXCEL()) {
			GuiBSPCarpeta l = new GuiBSPCarpeta();
			l.setRecord(this);
			String file = l.hashCode()+".xlsx";//.xls
			String content =l.getHtmlView(10,"excel",a);
			JTools.writeStringToFile(content,JPath.PssPathTempFiles()+"/"+file);
			return file;
		}
		if (action.isOutCSV()) {
			GuiBSPCarpeta l = new GuiBSPCarpeta();
			l.setRecord(this);
			String file = l.hashCode()+".csv";
			String content =l.getHtmlView(10,"csv",a);
			JTools.writeStringToFile(content,JPath.PssPathTempFiles()+"/"+file);
			return file;
		}
		if (action.isOutPDF()) {
			String file = this.generateInforme();
			return file;
		}
		
		
		if (action.isAdjunto()) {
			String file = this.generateInforme();
			return file;
		}
		return null;
  }
	@Override
	public String hasGenerateAviso(BizSqlEventAction action) throws Exception {
		return null;
	}


	@Override
	public String getDescripcion() throws Exception {
		return super.getDescripcion();
	}
	@Override
	public void mensajeEnviado(String marca) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
