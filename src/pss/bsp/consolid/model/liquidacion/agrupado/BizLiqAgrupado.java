package pss.bsp.consolid.model.liquidacion.agrupado;

import java.util.Date;

import pss.JPath;
import pss.bsp.consolid.model.gruposDK.BizGrupoDK;
import pss.bsp.consolid.model.liquidacion.agrupado.report.GuiReporteResumen;
import pss.common.security.BizUsuario;
import pss.core.reports.JBDReportes;
import pss.core.reports.JReport;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;

public class BizLiqAgrupado extends JRecord {

	private JLong pLiquidacionId = new JLong();
	private JLong pId = new JLong();
	private JDate pFechaDesde = new JDate();
	private JDate pFechaHasta = new JDate();
	private JLong pGrupoDk = new JLong();
	private JString pOrganization = new JString();
	private JString pGroupDescrip = new JString() {
		public void preset() throws Exception {
			pGroupDescrip.setValue(getGrupoDescripcion());
		};
	};
	private JString pDescripcion = new JString();
	private JFloat pTotalContado = new JFloat();
	private JFloat pTotalComision = new JFloat();
	private JFloat pTotalTarjeta = new JFloat();
	private JFloat pTotalDocFiscales = new JFloat();
	private JFloat pTotalDocNoFiscales = new JFloat();
	private JString pCompany = new JString();
	private JString pEstado = new JString();
	private JDate pFechaLiquidacion = new JDate();
	private JString pMoneda = new JString();
	private JFloat pComisionNeto = new JFloat();
	private JFloat pComisionNoDevengada = new JFloat();
	private JFloat pComision = new JFloat();
	private JFloat pPorcIvaComision = new JFloat();
	private JFloat pIvaComision = new JFloat();
	private JFloat pTotalFacturado = new JFloat();
	private JDate pFechaPago = new JDate();
	private JDate pFechaComision = new JDate();
	private JString pCuenta = new JString();
	private JString pCBU = new JString();
	private JString pInfo = new JString();
	private JBoolean pPendiente = new JBoolean();
	
	// Getter & Setters methods

	public void setLiquidacionId(Long zValue) throws Exception { pLiquidacionId.setValue(zValue); }
	public Long getLiquidacionId() throws Exception { return pLiquidacionId.getValue(); }
	
	public void setId(Long zValue) throws Exception { pId.setValue(zValue); }
	public Long getId() throws Exception { return pId.getValue(); }
	
	public void setGrupoDK(Long zValue) throws Exception { pGrupoDk.setValue(zValue); }
	public Long getGrupoDK() throws Exception { return pGrupoDk.getValue(); }

	public String getGrupoDescripcion() throws Exception {
		if (getObjGrupoDK()==null) return "";
		return getObjGrupoDK().getDescripcion(); 
		
	}

	public void setPendiente(boolean zValue) throws Exception { pPendiente.setValue(zValue); }
	public boolean getPendiente() throws Exception { return pPendiente.getValue(); }

	public void setFechaDesde(Date zValue) throws Exception { pFechaDesde.setValue(zValue); }
	public Date getFechaDesde() throws Exception { return pFechaDesde.getValue(); }

	public void setFechaHasta(Date zValue) throws Exception { pFechaHasta.setValue(zValue); }
	public Date getFechaHasta() throws Exception { return pFechaHasta.getValue(); }

	public void setDescripcion(String zValue) throws Exception { pDescripcion.setValue(zValue); }
	public String getDescripcion() throws Exception { return pDescripcion.getValue(); }

	public void setCompany(String zValue) throws Exception { pCompany.setValue(zValue); }
	public String getCompany() throws Exception { return pCompany.getValue(); }

	public void setEstado(String zValue) throws Exception { pEstado.setValue(zValue); }
	public String getEstado() throws Exception { return pEstado.getValue(); }

	public void setTotalContado(double zValue) throws Exception { pTotalContado.setValue(zValue); }
	public double getTotalContado() throws Exception { return pTotalContado.getValue(); }

	public void setTotalTarjeta(double zValue) throws Exception { pTotalTarjeta.setValue(zValue); }
	public double getTotalTarjeta() throws Exception { return pTotalTarjeta.getValue(); }

	public void setFechaLiquidacion(Date zValue) throws Exception { pFechaLiquidacion.setValue(zValue); }
	public Date getFechaLiquidacion() throws Exception { return pFechaLiquidacion.getValue(); }

	public void setTotalFiscales(double zValue) throws Exception { pTotalDocFiscales.setValue(zValue); }
	public double getTotalFiscales() throws Exception { return pTotalDocFiscales.getValue(); }

	public void setTotalNoFiscales(double zValue) throws Exception { pTotalDocNoFiscales.setValue(zValue); }
	public double getTotalNoFiscales() throws Exception { return pTotalDocNoFiscales.getValue(); }

	public void setTotalFacturado(double zValue) throws Exception { pTotalFacturado.setValue(zValue); }
	public double getTotalFacturado() throws Exception { return pTotalFacturado.getValue(); }

	public void setMoneda(String zValue) throws Exception { pMoneda.setValue(zValue); }
	public String getMoneda() throws Exception { return pMoneda.getValue(); }

	public void setFechaPago(Date zValue) throws Exception { pFechaPago.setValue(zValue); }
	public Date getFechaPago() throws Exception { return pFechaPago.getValue(); }

	public void setFechaComision(Date zValue) throws Exception { pFechaComision.setValue(zValue); }
	public Date getFechaComision() throws Exception { return pFechaComision.getValue(); }
	
	public void setOrganization(String zValue) throws Exception { pOrganization.setValue(zValue); }
	public String getOrganization() throws Exception { return pOrganization.getValue(); }
 
	public void setCuenta(String zValue) throws Exception { pCuenta.setValue(zValue); }
	public String getCuenta() throws Exception { return pCuenta.getValue(); }

	public void setCBU(String zValue) throws Exception { pCBU.setValue(zValue); }
	public String getCBU() throws Exception { return pCBU.getValue(); }

	public void setInfo(String zValue) throws Exception { pInfo.setValue(zValue); }
	public String getInfo() throws Exception { return pInfo.getValue(); }

	public void setTotalComision(double zValue) throws Exception { pTotalComision.setValue(zValue); }
	public double getTotalComision() throws Exception { return pTotalComision.getValue(); }

	public void setComisionNeto(double zValue) throws Exception { pComisionNeto.setValue(zValue); }
	public double getComisionNeto() throws Exception { return pComisionNeto.getValue(); }

	public void setComisionNoDevengada(double zValue) throws Exception { pComisionNoDevengada.setValue(zValue); }
	public double getComisionNoDevengada() throws Exception { return pComisionNoDevengada.getValue(); }

	public void setComision(double zValue) throws Exception { pComision.setValue(zValue); }
	public double getComision() throws Exception { return pComision.getValue(); }

	public void setPorcIvaComision(double zValue) throws Exception { pPorcIvaComision.setValue(zValue); }
	public double getPorcIvaComision() throws Exception { return pPorcIvaComision.getValue(); }

	public void setIvaComision(double zValue) throws Exception { pIvaComision.setValue(zValue); }
	public double getIvaComision() throws Exception { return pIvaComision.getValue(); }

	/**
	 * Constructor de la Clase
	 */
	public BizLiqAgrupado() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("liquidacion_id", pLiquidacionId);
		this.addItem("id", pId);
		this.addItem("organization", pOrganization);
		this.addItem("grupo_dk", pGrupoDk);
		this.addItem("grupo_descr", pGroupDescrip);
		this.addItem("fecha_desde", pFechaDesde);
		this.addItem("fecha_hasta", pFechaHasta);
		this.addItem("fecha_liq", pFechaLiquidacion);
		this.addItem("descripcion", pDescripcion);
		this.addItem("company", pCompany);
		this.addItem("estado", pEstado);
		this.addItem("total_contado", pTotalContado);
		this.addItem("total_tarjeta", pTotalTarjeta);
		this.addItem("total_fiscal", pTotalDocFiscales);
		this.addItem("total_nofiscal", pTotalDocNoFiscales);
		this.addItem("moneda", pMoneda);
		this.addItem("comision_neto", pComisionNeto);
		this.addItem("comision_no_devengada", pComisionNoDevengada);
		this.addItem("comision", pComision);
		this.addItem("porc_iva_comision", pPorcIvaComision);
		this.addItem("iva_comision", pIvaComision);
		this.addItem("total_comision", pTotalComision);
		this.addItem("total_facturado", pTotalFacturado);
		this.addItem("fecha_pago", pFechaPago);
		this.addItem("fecha_comision", pFechaComision);
		this.addItem("cuenta", pCuenta);
		this.addItem("cbu", pCBU);
		this.addItem("info", pInfo);
		this.addItem("pendiente", pPendiente);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "id", "id ID", false, false, 20);
		this.addFixedItem(FIELD, "liquidacion_id", "Liquidacion ID", true, true, 20);
		this.addFixedItem(FIELD, "grupo_dk", "Grupo DK", true, true, 18);
    this.addFixedItem(FIELD, "organization", "Organización", true, false, 50);
		this.addFixedItem(VIRTUAL, "grupo_descr", "Grupo DK", true, true, 200);
		this.addFixedItem(FIELD, "fecha_desde", "Fecha Desde", true, false, 18);
		this.addFixedItem(FIELD, "fecha_hasta", "Fecha Hasta", true, false, 18);
		this.addFixedItem(FIELD, "fecha_liq", "Fecha Liquidacion", true, false, 18);
		this.addFixedItem(FIELD, "descripcion", "Descripcion", true, false, 400);
		this.addFixedItem(FIELD, "company", "Company", true, true, 10);
		this.addFixedItem(FIELD, "estado", "Estado", true, true, 40);
		this.addFixedItem(FIELD, "total_contado", "Total contado", true, false, 18, 2);
		this.addFixedItem(FIELD, "total_tarjeta", "Total tarjeta", true, false, 19, 2);
		this.addFixedItem(FIELD, "total_fiscal", "Total Fiscal", true, false, 19, 2);
		this.addFixedItem(FIELD, "total_nofiscal", "Total No Fiscal", true, false, 19, 2);
		this.addFixedItem(FIELD, "moneda", "Moneda", true, true, 10);
		this.addFixedItem(FIELD, "comision_neto", "Comision Neto", true, false, 19, 2);
		this.addFixedItem(FIELD, "comision_no_devengada", "Comision No Devengada", true, false, 19, 2);
		this.addFixedItem(FIELD, "comision", "Comision", true, false, 19, 2);
		this.addFixedItem(FIELD, "porc_iva_comision", "Porcentaje IVA Comision", true, false, 19, 2);
		this.addFixedItem(FIELD, "iva_comision", "IVA Comision", true, false, 19, 2);
		this.addFixedItem(FIELD, "total_comision", "Total Comision", true, false, 19, 2);
		this.addFixedItem(FIELD, "total_facturado", "Total Facturado", true, false, 19, 2);
		this.addFixedItem(FIELD, "fecha_pago", "Fecha Pago", true, false, 18);
		this.addFixedItem(FIELD, "fecha_comision", "Fecha Comision", true, false, 18);
		this.addFixedItem(FIELD, "cuenta", "Cuenta", true, true, 20);
		this.addFixedItem(FIELD, "cbu", "CBU", true, true, 22);
		this.addFixedItem(FIELD, "info", "Info Adicional", true, true, 255);
		this.addFixedItem(FIELD, "pendiente", "Pend.", true, false, 1);
	}


  /**
   * Returns the table name
   */
  public String GetTable() { return "bsp_liquidation_agrup"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( long zId, String dk ) throws Exception { 
    addFilter( "liquidacion_id",  zId ); 
    addFilter( "grupo_dk",  dk ); 
    return read(); 
  } 

//	public void execPublicar() throws Exception {
//		JExec oExec = new JExec(this, "procPublicar") {
//
//			@Override
//			public void Do() throws Exception {
//				procPublicar();
//			}
//		};
//		oExec.execute();
//	}
//	public void execDespublicar() throws Exception {
//		JExec oExec = new JExec(this, "procDespublicar") {
//
//			@Override
//			public void Do() throws Exception {
//				procDespublicar();
//				
//			}
//		};
//		oExec.execute();
//	}
//	public String execMarcar() throws Exception {
//		JExec oExec = new JExec(this, "procMarcar") {
//
//			@Override
//			public void Do() throws Exception {
//				setOutput(procMarcar());
//			}
//		};
//		oExec.execute();
//		return (String)oExec.getOutput();
//	}
//	public void execDesmarcar() throws Exception {
//		JExec oExec = new JExec(this, "procDesmarcar") {
//
//			@Override
//			public void Do() throws Exception {
//				procDesmarcar();
//			}
//		};
//		oExec.execute();
//	}

//	public void procPublicar() throws Exception {
//		setEstado(BizLiqHeader.PUBLICADO);
//		setFechaLiquidacion(new Date());
//		processUpdate();
//
//	}
//	public void procDespublicar() throws Exception {
//		setEstado(BizLiqHeader.EN_PROCESO);
//		processUpdate();
//	}

  
  BizGrupoDK objGrupoDk;
  
  public BizGrupoDK getObjGrupoDK() throws Exception {
  	if (objGrupoDk!=null) return objGrupoDk;
  	BizGrupoDK biz = new BizGrupoDK();
  	biz.dontThrowException(true);
  	biz.addFilter("company", this.getCompany());
  	biz.addFilter("id_grupo", this.getGrupoDK());
  	if (biz.read())
  		return objGrupoDk=biz;
  	return  null;
	}
  
//	public String procMarcar() throws Exception {
//		setPendiente(true);
//		processUpdate();
//		BizMailingPersona objMail=getObjMailing();
//		if (objMail==null) return "Mail desconocido ("+getDK()+")";
//		if (objMail.getMail()==null) return "Mail desconocido ("+getDK()+")";
//		if (objMail.getMail().isEmpty()) return "Mail desconocido ("+getDK()+")";
//		 
//		return null;
//	}
//	public void procDesmarcar() throws Exception {
//		setPendiente(false);
//		processUpdate();
//	}
//  public BizSqlEventHistory  generarAviso(JFilterMap a,BizSqlEventAction action) throws Exception {
//		BizSqlEventHistory hist= new BizSqlEventHistory();
//  	hist.setFecha(new Date());
//  	hist.setIdevento(""+action.getIdevento());
//  	hist.setCompany(getCompany());
//  	hist.setFundamento(action.getDescripcion());
//  	hist.setIdaction(action.getIdaction());
//  	hist.setAccion(action.getAction());
//  	hist.setDestinatario(action.getDestinatario());
// 		hist.setMensaje(action.getMensajeAviso(a,hist));
// 		String file = JPath.PssPathTempFiles() + "/" +exportarAPdf();
//  	hist.setArchivoAsociado(file);
//  	return hist;
//  }
  
//  public BizSqlEventAction getSqlEventAction() throws Exception {
//  	BizSqlEventAction sql = new BizSqlEventAction();
//		sql.addFilter("company", BizBSPUser.getUsrBSP().getCompany());
//		sql.addFilter("class_evento", BizLiqAgrupado.class.getCanonicalName());
//		sql.addFilter("id_evento", BizBSPUser.getUsrBSP().getCompany());
//		sql.addFilter("descripcion", "Liquidación");
//		sql.addFilter("parametros","");
//   	BizPlantilla p = BizCompany.getObjPlantilla(getCompany(), "sys_emailconsolid");
//   	sql.setIdplantilla(p.getId());
//		return sql;
//	
//	 }
//  
//	public void sendMail() throws Exception {
//		BizMailingPersona pers = new BizMailingPersona();
//		pers.dontThrowException(true);
//		if (!pers.read(BizMailingPersona.CLIENTE_REDUCIDO, getCompany() , getDK())) return;
//		String mail = pers.getMail();
//		if (mail==null||mail.isEmpty()) return;
//		BizSqlEventAction action = getSqlEventAction();
//		BizSqlEventHistory hist = generarAviso(null,action);
//		String msg = new URLCodec().decode(this.getCorreoAviso(null,action,hist));
//		getObjMailSender().send(mail,"Liquidación "+getFechaDesde()+"-"+getFechaHasta(),msg, hist.getArchivoAsociado());
//
//		
//	}
	
//	public String getCorreoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist) throws Exception {
//		return action.getObjPlantilla().generateDocSimple(hist,this);
//  }
//	
//	BizUsrMailSender objMailSender;
//
//  public BizUsrMailSender getObjMailSender() throws Exception {
//  	if (objMailSender!=null) return objMailSender;
//  	BizUsrMailSender u = new BizUsrMailSender();
//  	u.read(3);//RJL hardcore
//  	return objMailSender=u;
//  }
	
	public String   exportarAExcel() throws Exception {
		JBDReportes report = (JBDReportes) this.createDataSource().getRecord();
		String fileName = BizUsuario.getUsr().getCompany() + "/" + report.toString() + ".xlsx";
		fileName = fileName.replace("[]null", "");
		JTools.createDirectories(JPath.PssPathTempFiles(), fileName);
		report.setType(JReport.REPORTES_EXCEL);
		report.setOutputFile(JPath.PssPathTempFiles() + "/" + fileName);
		report.execProcessInsert();
		return fileName;
	}
	public String   exportarAPdf() throws Exception {
		JBDReportes report = (JBDReportes) this.createDataSource().getRecord();
		String fileName = BizUsuario.getUsr().getCompany() + "/" + report.toString() + ".pdf";
		fileName = fileName.replace("[]null", "");
		JTools.createDirectories(JPath.PssPathTempFiles(), fileName);
		report.setType(JReport.REPORTES_PDF);
		report.setOutputFile(JPath.PssPathTempFiles() + "/" + fileName);
		report.imprimir();
		return fileName;
	}

  public GuiReporteResumen createDataSource() throws Exception{
  	GuiReporteResumen a = new GuiReporteResumen();
  	a.GetcDato().setCompany(this.getCompany());
  	a.GetcDato().setDK(this.getGrupoDK());
  	a.GetcDato().setLiquidacionId(this.getLiquidacionId());
  	a.GetcDato().getHeader();
    a.GetcDato().getRecords();
    return a;
 }
	
}
