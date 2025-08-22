package pss.bsp.consolid.model.liquidacion.header;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.consolid.model.clientes.BizCliente;
import pss.bsp.consolid.model.diferenciaLiq.BizDiferenciaLiq;
import pss.bsp.consolid.model.gruposDK.BizGrupoDK;
import pss.bsp.consolid.model.gruposDK.detail.BizGrupoDKDetail;
import pss.bsp.consolid.model.liquidacion.acumulado.BizLiqAcum;
import pss.bsp.consolid.model.liquidacion.agrupado.BizLiqAgrupado;
import pss.bsp.consolid.model.liquidacion.detail.BizLiqDetail;
import pss.bsp.consolid.model.liquidacion.errors.BizLiqError;
import pss.bsp.consolid.model.trxOra.BizTrxOra;
import pss.bsp.consolidador.IConciliable;
import pss.bsp.consolidador.IConsolidador;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.prorrateo.BizProrrateo;
import pss.bsp.dk.BizClienteDK;
import pss.bsp.organization.BizOrganization;
import pss.bsp.pdf.BizPDF;
import pss.bsp.pdf.mex.detalle.BizMexDetalle;
import pss.common.customList.config.relation.JRelations;
import pss.common.event.mailing.type.BizMailingPersonaType;
import pss.common.event.mailing.type.GuiMailingPersonaTypes;
import pss.common.event.mailing.type.detail.BizMailingPersonaTypeDetail;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.JExec;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JPair;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JOrderedMap;
import pss.tourism.carrier.BizCarrier;
import pss.tourism.interfaceGDS.oracle.TicketTransaction;
import pss.tourism.pnr.BizPNRTicket;
import pss.tourism.pnr.GuiPNRTickets;

public class BizLiqHeader extends JRecord {
	
	public static final String EN_PROCESO = "EN_PROCESO";
	public static final String PUBLICADO = "PUBLICADO";
	public static final String PEND_CONCILIAR = "PEND.CONCILIAR";
	public static final String PEND_LIQUIDAR = "PEND.LIQUIDAR";
	public static final String PEND_PUBLICAR = "PEND.PUBLICAR";
 
	private JLong pLiquidacionId = new JLong();
	private JLong pGrupoCliente = new JLong();
	private JDate pFechaDesde = new JDate();
	private JDate pFechaHasta = new JDate();
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

	// Getter & Setters methods

	public void setLiquidacionId(Long zValue) throws Exception {
		pLiquidacionId.setValue(zValue);
	}

	public Long getLiquidacionId() throws Exception {
		return pLiquidacionId.getValue();
	}
	public void setGrupoCliente(Long zValue) throws Exception {
		pGrupoCliente.setValue(zValue);
	}

	public Long getGrupoCliente() throws Exception {
		return pGrupoCliente.getValue();
	}
	public boolean getGrupoClienteIsNull() throws Exception {
		return pGrupoCliente.isNull();
	}
	public void setFechaDesde(Date zValue) throws Exception {
		pFechaDesde.setValue(zValue);
	}

	public Date getFechaDesde() throws Exception {
		return pFechaDesde.getValue();
	}

	public void setFechaHasta(Date zValue) throws Exception {
		pFechaHasta.setValue(zValue);
	}

	public Date getFechaHasta() throws Exception {
		return pFechaHasta.getValue();
	}

	public void setDescripcion(String zValue) throws Exception {
		pDescripcion.setValue(zValue);
	}

	public String getDescripcion() throws Exception {
		return pDescripcion.getValue();
	}

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public void setEstado(String zValue) throws Exception {
		pEstado.setValue(zValue);
	}

	public String getEstado() throws Exception {
		return pEstado.getValue();
	}

	public void setTotalContado(double zValue) throws Exception {
		pTotalContado.setValue(zValue);
	}

	public double getTotalContado() throws Exception {
		return pTotalContado.getValue();
	}

	public void setTotalTarjeta(double zValue) throws Exception {
		pTotalTarjeta.setValue(zValue);
	}

	public double getTotalTarjeta() throws Exception {
		return pTotalTarjeta.getValue();
	}

	public void setFechaLiquidacion(Date zValue) throws Exception {
		pFechaLiquidacion.setValue(zValue);
	}

	public Date getFechaLiquidacion() throws Exception {
		return pFechaLiquidacion.getValue();
	}

	public void setTotalFiscales(double zValue) throws Exception {
		pTotalDocFiscales.setValue(zValue);
	}

	public double getTotalFiscales() throws Exception {
		return pTotalDocFiscales.getValue();
	}

	public void setTotalNoFiscales(double zValue) throws Exception {
		pTotalDocNoFiscales.setValue(zValue);
	}

	public double getTotalNoFiscales() throws Exception {
		return pTotalDocNoFiscales.getValue();
	}

	public void setMoneda(String zValue) throws Exception {
		pMoneda.setValue(zValue);
	}

	public String getMoneda() throws Exception {
		return pMoneda.getValue();
	}

	public void setFechaPago(Date zValue) throws Exception {
		pFechaPago.setValue(zValue);
	}

	public Date getFechaPago() throws Exception {
		return pFechaPago.getValue();
	}

	public void setFechaComision(Date zValue) throws Exception {
		pFechaComision.setValue(zValue);
	}

	public Date getFechaComision() throws Exception {
		return pFechaComision.getValue();
	}

	public void setCuenta(String zValue) throws Exception {
		pCuenta.setValue(zValue);
	}

	public String getCuenta() throws Exception {
		return pCuenta.getValue();
	}

	public void setCBU(String zValue) throws Exception {
		pCBU.setValue(zValue);
	}

	public String getCBU() throws Exception {
		return pCBU.getValue();
	}

	public void setInfo(String zValue) throws Exception {
		pInfo.setValue(zValue);
	}

	public String getInfo() throws Exception {
		return pInfo.getValue();
	}
	public void setTotalFacturado(double zValue) throws Exception {
		pTotalFacturado.setValue(zValue);
	}

	public double getTotalFacurado() throws Exception {
		return pTotalFacturado.getValue();
	}
	public void setTotalComision(double zValue) throws Exception {
		pTotalComision.setValue(zValue);
	}

	public double getTotalComision() throws Exception {
		return pTotalComision.getValue();
	}

	public void setComisionNeto(double zValue) throws Exception {
		pComisionNeto.setValue(zValue);
	}

	public double getComisionNeto() throws Exception {
		return pComisionNeto.getValue();
	}

	public void setComisionNoDevengada(double zValue) throws Exception {
		pComisionNoDevengada.setValue(zValue);
	}

	public double getComisionNoDevengada() throws Exception {
		return pComisionNoDevengada.getValue();
	}

	public void setComision(double zValue) throws Exception {
		pComision.setValue(zValue);
	}

	public double getComision() throws Exception {
		return pComision.getValue();
	}

	public void setPorcIvaComision(double zValue) throws Exception {
		pPorcIvaComision.setValue(zValue);
	}

	public double getPorcIvaComision() throws Exception {
		return pPorcIvaComision.getValue();
	}

	public void setIvaComision(double zValue) throws Exception {
		pIvaComision.setValue(zValue);
	}

	public double getIvaComision() throws Exception {
		return pIvaComision.getValue();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizLiqHeader() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("liquidacion_id", pLiquidacionId);
		this.addItem("grupo_cliente", pGrupoCliente);
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
		//this.addItem("fecha_liquidacion", pFechaLiquidacion);
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
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "liquidacion_id", "Liquidacion ID", false, false, 20);
		this.addFixedItem(FIELD, "grupo_cliente", "Grupo clientes", true, false, 64);
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
		//this.addFixedItem(FIELD, "fecha_liquidacion", "Fecha Liquidacion", true, false, 18);
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
		this.addFixedItem(FIELD, "cuenta", "Cuenta", true, false, 20);
		this.addFixedItem(FIELD, "cbu", "CBU", true, false, 22);
		this.addFixedItem(FIELD, "info", "Info Adicional", true, false, 255);
	}
	
  @Override
  public void createControlProperties() throws Exception {
  	this.addControlsProperty("grupo_cliente", createControlCombo(GuiMailingPersonaTypes.class,"id_tipo", new JPair<String, String>("company","company") ));
  	super.createControlProperties();
  }

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "bsp_liquidation";
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(long zId) throws Exception {
		addFilter("liquidacion_id", zId);
		return read();
	}

	@Override
	public void processInsert() throws Exception {
		if (pEstado.isNull())
			pEstado.setValue(BizLiqHeader.PEND_CONCILIAR);
		String descr = "Período " + JDateTools.DateToString(getFechaDesde()) + " hasta " + JDateTools.DateToString(getFechaHasta());
		if (!getGrupoClienteIsNull() && getObjGrupoCliente()!=null) {
			descr += " "+getObjGrupoCliente().getDescripcion();
		}
		pDescripcion.setValue(descr);
		getInfoLastLiquidacion();
		setMoneda(BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getCurrency());
		JBaseRegistro regs=JBaseRegistro.recordsetFactory();
		
		BizLiqHeader other = new BizLiqHeader();
		other.dontThrowException(true);
		other.addFilter("company", getCompany());
		other.addFilter("grupo_cliente", getGrupoCliente());
		other.addFixedFilter("where ('"+JDateTools.DateToString( getFechaDesde())+"', '"+JDateTools.DateToString(getFechaHasta())+"') OVERLAPS (fecha_desde, fecha_hasta) ");
		if (other.read()) throw new Exception("Ya existe liq.en ese periodo ["+other.getDescripcion()+"] ");
		
		super.processInsert();
	}
	private void getInfoLastLiquidacion() throws Exception {
		JRecords<BizLiqHeader> lastLiqs = new JRecords<BizLiqHeader>(BizLiqHeader.class);
		lastLiqs.addFilter("company", getCompany());
		lastLiqs.addOrderBy("liquidacion_id","desc");
		lastLiqs.setTop(1);
		JIterator<BizLiqHeader> it=lastLiqs.getStaticIterator();
		if (!it.hasMoreElements()) return;
		BizLiqHeader lastLiq=it.nextElement();
		this.setCuenta(lastLiq.getCuenta());
		this.setCBU(lastLiq.getCBU());
		this.setInfo(lastLiq.getInfo());
	}
	@Override
	public void processUpdate() throws Exception {
		String descr = "Período " + JDateTools.DateToString(getFechaDesde()) + " hasta " + JDateTools.DateToString(getFechaHasta());
		if (!getGrupoClienteIsNull() && getObjGrupoCliente()!=null) {
			descr += " "+getObjGrupoCliente().getDescripcion();
		}
		pDescripcion.setValue(descr);
		super.processUpdate();
	}
	transient BizMailingPersonaType objType;
	
  public BizMailingPersonaType getObjGrupoCliente() throws Exception {
  	if (getGrupoClienteIsNull()) return null;
  	if (objType!=null) return objType;
  	BizMailingPersonaType obj = new BizMailingPersonaType();
  	obj.dontThrowException(true);
  	if (!obj.Read(getGrupoCliente()))
  		return null;
  	return objType=obj;
  }
	@Override
	public void processDelete() throws Exception {
		BizLiqDetail cons = new BizLiqDetail();
		cons.addFilter("company", getCompany());
		cons.addFilter("liquidacion_id", getLiquidacionId());
		cons.addFilter("origen", "TKM");
		cons.deleteMultiple();
		BizLiqError errors = new BizLiqError();
		errors.addFilter("company", getCompany());
		errors.addFilter("liquidacion_id", getLiquidacionId());
		errors.deleteMultiple();
		BizLiqAcum acums = new BizLiqAcum();
		acums.addFilter("company", getCompany());
		acums.addFilter("liquidacion_id", getLiquidacionId());
		acums.deleteMultiple();
		super.processDelete();
	}
	public void execProcLimpiar() throws Exception {
		JExec oExec = new JExec(this, "procLimpiar") {

			@Override
			public void Do() throws Exception {
				procLimpiar();
			}
		};
		oExec.execute();
	}
	public void execConciliar() throws Exception {
		JExec oExec = new JExec(this, "execConciliar") {

			@Override
			public void Do() throws Exception {
				procConciliar();
			}
		};
		oExec.execute();
	}

	public void execLiquidar() throws Exception {
		JExec oExec = new JExec(this, "procLiquidar") {

			@Override
			public void Do() throws Exception {
				procLiquidar();
			}
		};
		oExec.execute();
	}
	public void execPublicar(boolean sendMail) throws Exception {
		JExec oExec = new JExec(this, "procPublicar") {

			@Override
			public void Do() throws Exception {
				procPublicar(sendMail);
			}
		};
		oExec.execute();
	}
	public void execDespublicar() throws Exception {
		JExec oExec = new JExec(this, "procDespublicar") {

			@Override
			public void Do() throws Exception {
				procDespublicar();
				
			}
		};
		oExec.execute();
	}
	public void execCompletarTrxFaltantes(String company) throws Exception {
		JExec oExec = new JExec(this, "completeTrXFaltantes") {

			@Override
			public void Do() throws Exception {
				loadOracleTrx(company);
				completeTrXFaltantes();
				
			}
		};
		oExec.execute();
	}
	int pos=0;
	
	public boolean isDKActive(String dk) throws Exception {
		if (dk.equals("VOD705")) return false;
		if (dk.equals("VOI705")) return false;
		if (!clientes.isEmpty() && !clientes.contains(dk)) return false;
		if (!clientesNegativa.isEmpty() && clientesNegativa.contains(dk)) return false;
		return true;// desharcodear

	}
	
	List<String> clientes = new ArrayList<String>();
	String strClientes = "";
	List<String> clientesNegativa = new ArrayList<String>();
	String strClientesNegativa = "";
	public void loadClientes() throws Exception {
		if (getGrupoClienteIsNull()) {

			return;
		}
		BizMailingPersonaType tipo = new BizMailingPersonaType();
		tipo.dontThrowException(true);
		if (!tipo.Read(getGrupoCliente())) {
			return;
		}
		
		if (tipo.getTipo().equals(BizMailingPersonaType.TODOS)) {
			
		} else if (tipo.getTipo().equals(BizMailingPersonaType.INCLUIDOS)) {
			JRecords<BizMailingPersonaTypeDetail> recs = new JRecords<BizMailingPersonaTypeDetail>(BizMailingPersonaTypeDetail.class); 
			recs.addFilter("id_tipo", getGrupoCliente());
			JIterator<BizMailingPersonaTypeDetail> it = recs.getStaticIterator();
			while (it.hasMoreElements()) {
				BizMailingPersonaTypeDetail det = it.nextElement();
				clientes.add(det.getDK());
				strClientes += (strClientes.equals("")?"":",")+"'"+det.getDK()+"'";
			}
		} else if (tipo.getTipo().equals(BizMailingPersonaType.NO_INCLUIDS)) {
			JRecords<BizMailingPersonaTypeDetail> recs = new JRecords<BizMailingPersonaTypeDetail>(BizMailingPersonaTypeDetail.class); 
			recs.addFilter("id_tipo", getGrupoCliente());
			JIterator<BizMailingPersonaTypeDetail> it = recs.getStaticIterator();
			while (it.hasMoreElements()) {
				BizMailingPersonaTypeDetail det = it.nextElement();
				clientesNegativa.add(det.getDK());
				strClientesNegativa += (strClientesNegativa.equals("")?"":",")+"'"+det.getDK()+"'";
			}
		} else if (tipo.getTipo().equals(BizMailingPersonaType.TODOS_NO_INCLUIDOS)) {
			JRecords<BizMailingPersonaTypeDetail> recs = new JRecords<BizMailingPersonaTypeDetail>(BizMailingPersonaTypeDetail.class); 
			JIterator<BizMailingPersonaTypeDetail> it = recs.getStaticIterator();
			while (it.hasMoreElements()) {
				BizMailingPersonaTypeDetail det = it.nextElement();
				clientesNegativa.add(det.getDK());
				strClientesNegativa += (strClientesNegativa.equals("")?"":",")+"'"+det.getDK()+"'";
			}
		}

	}
	public void procConciliar() throws Exception {
		procLimpiarAcum();
		procLimpiarAgrup();
		setTotalContado(0); 
		setTotalTarjeta(0);
		setTotalComision(0);
		setTotalFiscales(0);
		setTotalNoFiscales(0);
		setTotalFacturado(0);
		setPorcIvaComision(16);//desharcodear
		setFechaLiquidacion(new Date());
		if (pCompany.isNull()) setCompany(BizUsuario.getUsr().getCompany());
		setMoneda(BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getCurrency());
		BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Iniciando conciliación", 1, 100, false, null);
			loadClientes();
		loadOracle();
		BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Completar TRX faltantes", pos, mapTxtOraAll.size(), false, null);
		completeTrXFaltantes();
		BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Analizando contratos", pos, mapTxtOraAll.size(), false, null);
		
		HashMap<String, String> docs = new HashMap<String, String>();
		HashMap<Long, String> tktsProc = new HashMap<Long, String>();
		
		JRecords<BizLiqDetail> cons = new JRecords<BizLiqDetail>(BizLiqDetail.class);
		cons.addFilter("company", getCompany());
		cons.addFilter("liquidacion_id", getLiquidacionId());
		cons.addFilter("origen", "XSLX");
		JIterator<BizLiqDetail> it= cons.getStaticIterator();
		while (it.hasMoreElements()) {
			BizLiqDetail liq = it.nextElement();
			BizTrxOra ora = new BizTrxOra();
			ora.dontThrowException(true);
			if (liq.isNullOrganization()) {
				if (ora.readByTipoNroDoc(liq.getTipoDoc(),liq.getNroDoc())) {
					if (!ora.getOrg().isEmpty()) {
						liq.setOrganization(ora.getOrg());
						liq.processUpdate();						
					}
				}				
			}

			docs.put(liq.getTipoDoc()+"-"+liq.getNroDoc()+"-"+liq.getCustomerTrxId(), liq.getNroDoc());
			setTotalContado(getTotalContado() + liq.getContado());
			setTotalTarjeta(getTotalTarjeta() + liq.getTarjeta());
			setTotalFiscales(getTotalFiscales() + liq.getFiscal());
			setTotalNoFiscales(getTotalNoFiscales() + liq.getNoFiscal());
			setTotalFacturado(getTotalFacurado()+liq.getContado()+liq.getTarjeta());
		}

		// buscar todos los tickets del periodo que pertenezcan a un contrato
		// prorateable
//		JRecords<BizDetalle> recs = new JRecords<BizDetalle>(BizDetalle.class);
//		recs.addJoin(JRelations.JOIN, "bsp_contrato", "bsp_contrato_detalle.company = bsp_contrato.company and bsp_contrato_detalle.id=bsp_contrato.id");
//		recs.addFilter("company", getCompany());
//		recs.addFilter("bsp_contrato", "fecha_hasta", JDateTools.getDateStartDay(getFechaDesde()), "JDATE", ">=", "AND", "(");
//		recs.addFilter("bsp_contrato", "fecha_desde", JDateTools.getDateEndDay(getFechaHasta()), "JDATE", "<=", "OR", ")");
//		recs.addOrderBy("bsp_contrato","id","asc");
//		recs.addOrderBy("prioridad","asc");
//		int row = 0;
//		JIterator<BizDetalle> itd = recs.getStaticIterator();
//		while (itd.hasMoreElements()) {
//			BizDetalle det = itd.nextElement();
//			if (!det.isUpfront())
//				continue;
//			BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Analizando contrato "+det.getDescripcion(), pos++, mapTxtOraAll.size(), false, null);
//			fillDetalle(det, docs, tktsProc);
//		}
		BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Analizando tickets ", pos++, mapTxtOraAll.size(), false, null);
		fillAllTickets(docs, tktsProc);
		BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Analizando Documentos ", pos++, mapTxtOraAll.size(), false, null);
		fillOracle(docs, tktsProc);
		
		setComisionNeto(getComision()+getComisionNoDevengada());
		setIvaComision(getComisionNeto()*(getPorcIvaComision()/100.0));
		setTotalComision(getComisionNeto()+getIvaComision());
		setEstado(BizLiqHeader.PEND_LIQUIDAR);
		processUpdate();
	}

	
	public void completeTrXFaltantes() throws Exception {
		Iterator<BizTrxOra> itO = mapTxtOraAll.iterator();
		while (itO.hasNext()) {
			BizTrxOra tktOra = itO.next();

			if (!(isDocPermitidoOracle(tktOra.getTipoDocumento(),tktOra.getTipoServicio())))
				continue;
			if (!isDocConciliable(tktOra.getTipoDocumento(),tktOra.getTipoServicio())) {  
				BizPNRTicket tkt = new BizPNRTicket();
				tkt.dontThrowException(true);
				if (tkt.ReadByBoleto(getCompany(), tktOra.getBoleto())) {
					if (tkt.getGDS().equals("ORACLE")) {
						tkt.processDelete();
					}
				}

				continue;
			}
			if (!isDKActive(tktOra.getDk()))
				continue;
			String nroBoleto = tktOra.getBoleto();
			if (nroBoleto.endsWith("R"))
				nroBoleto = nroBoleto.substring(0,nroBoleto.length()-1);
			if (!JTools.isNumberPure(nroBoleto)) {
				PssLogger.logInfo("Detectado ["+nroBoleto+"] Descartado!");
				continue;
			}

			TicketTransaction newTrx = new TicketTransaction();
			newTrx.buildTrx(this.getCompany(),nroBoleto, tktOra);
			
		}
	
	}
	
	public void fillDetalle(BizDetalle detalle, HashMap<String, String> docs, HashMap<Long, String> tktsProc) throws Exception {
		detalle.setFechaDesdeCalculo(getFechaDesde());
		detalle.setFechaCalculo(getFechaHasta());
		if (!(detalle.getDetalleGanancia() instanceof GuiPNRTickets))
			return;
		JOrderedMap<String, Double> acumPremios = JCollectionFactory.createOrderedMap();
 
		double comdefa = 0;
		JRecords<BizProrrateo> prs = new JRecords<BizProrrateo>(BizProrrateo.class);
		prs.addFilter("company", detalle.getCompany());
		prs.addFilter("contrato", detalle.getId());
		prs.addFilter("detalle", detalle.getLinea());
		prs.readAll();
		boolean find = false;
		while (prs.nextRecord()) {
			BizProrrateo pr = prs.getRecord();
			JList<String> cls = pr.getCliente();
			JIterator<String> clsit = cls.getIterator();
			while (clsit.hasMoreElements()) {
				String cl = clsit.nextElement();
				acumPremios.addElement(cl, pr.getComision());
			}
		
			if (cls.isEmpty())
				comdefa = pr.getComision();
			find = true;
		}
		if (!find)
			return;

			GuiPNRTickets tkts = (GuiPNRTickets) detalle.getDetalleGanancia();
		int id = 0;
		tkts.readAll();
//		JIterator<BizPNRTicket> it = tkts.getRecords().getStaticIterator();
		while (tkts.getRecords().nextRecord()) {
			BizPNRTicket tkt = (BizPNRTicket) tkts.getRecords().getRecord();
      if (tkt.getNumeroboleto().equals("3518500057"))
      	PssLogger.logInfo("log point");
			if (tkt.getNumeroboleto().equals(""))
				continue;
			if (tktsProc.get(tkt.getId()) != null)
				continue;
			String cl = tkt.getCustomerIdReducido();

			BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Analizando contrato "+detalle.getDescripcion()+" boleto "+tkt.getNumeroboleto(), pos++, mapTxtOraAll.size(), false, null);
			
			if (mapTxtOra.get(tkt.getNumeroboleto()) != null ) {
				Iterator<BizTrxOra> itO = mapTxtOra.get(tkt.getNumeroboleto()).iterator();
				while (itO.hasNext()) {
					BizTrxOra tktOra = itO.next();

					if (!(isDocPermitidoOracle(tktOra.getTipoDocumento(),tktOra.getTipoServicio())))
						continue;
					if (!(isDocConciliable(tktOra.getTipoDocumento(),tktOra.getTipoServicio())))
						continue;
					if (!isDKActive(tktOra.getDk()))
						continue;
						
					saveDetail(tktOra,tkt,docs,tktsProc,acumPremios);
	
				}
			} else {
				fillByTktSearchInOracle(tkt,docs,tktsProc,acumPremios);

			}

		}
		prs.closeRecord();
		tkts.getRecords().closeRecord();
	}

	static public boolean isDocFiscal(String doc) {
		if (doc.indexOf("FCE")!=-1)
			return true;
		if (doc.indexOf("NCE")!=-1)
			return true;
		return false;
	}
//	static public boolean isDocConciliable(String doc,String tipoServicio) {
//		if (doc.equals("FCE"))
//			return false;
//		if (doc.equals("NCE"))
//			return false;
//		if (tipoServicio.equals("M1"))
//			return false;
//		return true;
//	}
	static public boolean isDocConciliable(String doc,String tipoServicio) {
	
		if (doc.equals("FCE")||doc.equals("NCE")||doc.equals("XLD FCE")) {
			if (tipoServicio.equals("HCO")||
					tipoServicio.equals("CSA")||
					tipoServicio.equals("CAB")||
					tipoServicio.equals("CRE")||
					tipoServicio.equals("CRV")||
					tipoServicio.equals("E2")||
					tipoServicio.equals("ND")||
					tipoServicio.equals("FSC")||
					tipoServicio.equals("M1")||
					tipoServicio.equals("EC")||
					tipoServicio.equals("SBS")||
					tipoServicio.equals("CCO")||
					tipoServicio.equals("RAC")||
					tipoServicio.equals("SCO")||
					tipoServicio.equals("TCO")||
					tipoServicio.equals("ECO")||
					tipoServicio.equals("OSC")||
					tipoServicio.equals("PCO")) 
				return false;		
		}
	
		
		return true;
	}
	static public boolean isDocAdmintNoConciliable(String doc,String tipoServicio) {
		
		if (doc.equals("FCE")||doc.equals("NCE")||doc.equals("XLD FCE")) {
			if (tipoServicio.equals("HCO")||
					tipoServicio.equals("CSA")||
					tipoServicio.equals("CAB")||
					tipoServicio.equals("CRE")||
					tipoServicio.equals("CRV")||
					tipoServicio.equals("E2")||
					tipoServicio.equals("ND")||
					tipoServicio.equals("FSC")||
					tipoServicio.equals("M1")||
					tipoServicio.equals("EC")||
					tipoServicio.equals("SBS")||
					tipoServicio.equals("CCO")||
					tipoServicio.equals("RAC")||
					tipoServicio.equals("SCO")||
					tipoServicio.equals("TCO")||
					tipoServicio.equals("TC")||
					tipoServicio.equals("ECO")||
					tipoServicio.equals("OSC")||
					tipoServicio.equals("PCO")) 
				return true;		
		}
		if (doc.equals("FCE")||doc.equals("RFN")||doc.equals("STE")) {
			if (tipoServicio.equals("HCO")||
					tipoServicio.equals("CSA")||
					tipoServicio.equals("LBI")||
					tipoServicio.equals("LBN")||
					tipoServicio.equals("LDE")||
					tipoServicio.equals("LIE")||
					tipoServicio.equals("M1")||
					tipoServicio.equals("SCO")||
					tipoServicio.equals("EMG")||
					tipoServicio.equals("TCO")||
					tipoServicio.equals("TC")||
					tipoServicio.equals("LI")||
					tipoServicio.equals("LD")||
					tipoServicio.equals("CCO")||
					tipoServicio.equals("RAC")||
					tipoServicio.equals("ECO")||
					tipoServicio.equals("OSC")||
					tipoServicio.equals("PCO")) {
				return true;	
			}
		}
		
		return false;
	}
	static public boolean isDocPermitido(String doc) {
		if (doc.equals("STE"))
			return true;
		if (doc.equals("RFN"))
			return true;
		if (doc.equals("FCE"))
			return true;
		if (doc.equals("DXE"))
			return true;
		return false;
	}
	static public boolean isDocPermitidoOracle(String doc,String tipoServicio) {
		if (!tipoServicio.equals("HCO")&&
				!tipoServicio.equals("CSA")&&
				!tipoServicio.equals("LBI")&&
				!tipoServicio.equals("LBN")&&
				!tipoServicio.equals("LDE")&&
				!tipoServicio.equals("LIE")&&
				!tipoServicio.equals("M1")&&
				!tipoServicio.equals("SCO")&&
				!tipoServicio.equals("EMG")&&
				!tipoServicio.equals("TCO")&&
				!tipoServicio.equals("LI")&&
				!tipoServicio.equals("LD")&&
				!tipoServicio.equals("CCO")&&
				!tipoServicio.equals("RAC")&&
				!tipoServicio.equals("ECO")&&
				!tipoServicio.equals("OSC")&&
				!tipoServicio.equals("PCO")) 
			return false;
		if (doc.equals("STE"))
			return true;
		if (doc.equals("RFN"))
			return true;
		if (doc.equals("FCE"))
			return true;
		if (doc.equals("DXE"))
			return true;
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	static public boolean isDevolucion(String doc) {
		if (doc.indexOf("RFN")!=-1)
			return true; 
//		if (doc.indexOf("DXE")!=-1)
//			return true;
		return false;
	}

	static public boolean isContado(String doc) {
		if (doc.equals("CH"))
			return true;
		if (doc.equals("NI"))
			return true;
		if (doc.equals("TR"))
			return true;
		if (doc.equals("CA"))
			return true;
		return false;
	}

	Map<String, List<BizTrxOra>> mapTxtOra = null;
	List<BizTrxOra> mapTxtOraAll = null;


	public void loadOracle() throws Exception { 
    if (mapTxtOra != null)
        return;

    // Inicializar estructuras
    mapTxtOraAll = new ArrayList<>();
    mapTxtOra = new HashMap<>();
    Set<String> boletosConR = new HashSet<>(); // Almacenar los boletos que tienen una versión con "R"

    // Crear el conjunto de registros
    JRecords<BizTrxOra> trxOracle = new JRecords<>(BizTrxOra.class);
    trxOracle.addFixedFilter("WHERE ("
 //   		+ (strClientes.equals("")?"":" dk in ( "+strClientes+") and ") la mierda de oracle es mas rapido leer todo a que haya un in o not in 
        + "(fecha_emision BETWEEN "
        + "TO_DATE('" + getFechaDesde() + "', 'YYYY-MM-DD') "
        + "AND TO_DATE('" + getFechaHasta() + " 23:59:59', 'YYYY-MM-DD HH24:MI:SS'))) ");

//    		+ " OR "
//            + "(fecha_factura BETWEEN TO_DATE('" + getFechaDesde() + "', 'YYYY-MM-DD') "
//            + "AND TO_DATE('" + getFechaHasta() + "', 'YYYY-MM-DD')))");

    // Iterar sobre los registros y agruparlos
    trxOracle.readAll();
    while (trxOracle.nextRecord()) {
        BizTrxOra tktOra = trxOracle.getRecord();
	      if (tktOra.getFactura().equals("3650486"))
	      	PssLogger.logInfo("log point");
	      if (tktOra.getBoleto().startsWith("3518500057"))
        	PssLogger.logInfo("log point");
	      if (!isDKActive(tktOra.getDk())) continue;
        mapTxtOraAll.add(tktOra);

        if (!isDocPermitido(tktOra.getTipoDocumento())) continue;
 
        // Guardar en la lista completa
 
        // Usar el boleto tal como viene
        String boletoKey = tktOra.getBoleto();
        if (boletoKey.isEmpty()) continue;
        if (!JTools.isNumberPure(tktOra.getBoleto().replace("R", ""))) continue;
        boolean tieneR = boletoKey.endsWith("R");
        String boletoBase = tieneR ? boletoKey.substring(0, boletoKey.length() - 1) : boletoKey;

        // Agregar a la lista de boletos agrupados con su clave exacta
        mapTxtOra.computeIfAbsent(boletoKey, k -> new ArrayList<>()).add(tktOra);

        // Si el boleto tiene "R", registrar su base
        if (tieneR) {
            boletosConR.add(boletoBase);
        }
    }
    trxOracle.closeRecord();
    // Segunda pasada: eliminar boletos sin "R" en mapTxtOra si hay una versión con "R"
    for (String boletoBase : boletosConR) {
      // Obtenemos la lista en la clave "boletoBase" (sin R) 
      List<BizTrxOra> sinRList = mapTxtOra.get(boletoBase);
      if (sinRList != null) {
          // Eliminamos únicamente los de tipo STE o DXE
          sinRList.removeIf(tkt -> 
                 {
									try {
										return "STE".equals(tkt.getTipoDocumento()) || "DXE".equals(tkt.getTipoDocumento());
									} catch (Exception e) {
										return false;
									}
								});
          
          // Si la lista queda vacía, removemos la clave
          if (sinRList.isEmpty()) {
              mapTxtOra.remove(boletoBase);
          }
      }
  }


    // También eliminar boletos sin "R" en mapTxtOraAll
//    mapTxtOraAll.removeIf(tktOra -> {
//        String boletoKey=null;
//				try {
//					boletoKey = tktOra.getBoleto();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//        String boletoBase = boletoKey.endsWith("R") ? boletoKey.substring(0, boletoKey.length() - 1) : boletoKey;
//        return boletosConR.contains(boletoBase) && !boletoKey.endsWith("R");
//    });
    mapTxtOraAll.removeIf(tkt -> {
      String boletoKey;
			try {
				boletoKey = tkt.getBoleto();

      if (boletoKey == null || boletoKey.isEmpty()) {
          return false;
      }
      boolean endsWithR = boletoKey.endsWith("R");
      String base = endsWithR
              ? boletoKey.substring(0, boletoKey.length() - 1)
              : boletoKey;

      // Solo eliminar si este no termina en R, base ∈ boletosConR, 
      // y tipoDocumento es STE o DXE
      return (!endsWithR)
          && boletosConR.contains(base)
          && ("STE".equals(tkt.getTipoDocumento()) || "DXE".equals(tkt.getTipoDocumento()));
			} catch (Exception e) {
				return  false;
			}   }); 
    // Tercera pasada: Ajustar las claves de los boletos con "R" en mapTxtOra eliminando la "R"
    Map<String, List<BizTrxOra>> mapAjustado = new HashMap<>();
    for (Map.Entry<String, List<BizTrxOra>> entry : mapTxtOra.entrySet()) {
        String boletoKey = entry.getKey();
        String boletoBase = boletoKey.endsWith("R") ? boletoKey.substring(0, boletoKey.length() - 1) : boletoKey;

        // Ajustar la clave final (quitar la "R")
        mapAjustado.put(boletoBase, entry.getValue());
    }

    // Reemplazar la estructura con la versión ajustada
    mapTxtOra = mapAjustado;
}

	public void loadOracleTrx(String company) throws Exception { 
		setCompany(company);
    // Inicializar estructuras
    mapTxtOraAll = new ArrayList<BizTrxOra>();

    // Crear el conjunto de registros
    JRecords<BizTrxOra> trxOracle = new JRecords<>(BizTrxOra.class);
    trxOracle.addFilter("fecha_emision", JDateTools.addDays(new Date(), -30), ">=");
    trxOracle.addFilter("fecha_emision", new Date(), "<=");
 
    // Iterar sobre los registros
    JIterator<BizTrxOra> itO = trxOracle.getStaticIterator();
    while (itO.hasMoreElements()) {
        BizTrxOra tktOra = itO.nextElement();
				if (!(isDocPermitidoOracle(tktOra.getTipoDocumento(),tktOra.getTipoServicio())))
					continue;
				mapTxtOraAll.add(tktOra); 
    
    }
	}

	public void fillAllTickets(HashMap<String, String> docs, HashMap<Long, String> tktsProc) throws Exception {

		GuiPNRTickets tkts = new GuiPNRTickets();
		tkts.getRecords().addFilter("company", getCompany());
		tkts.getRecords().addFilter("creation_date_date", getFechaDesde(), ">=");
		tkts.getRecords().addFilter("creation_date_date", getFechaHasta(), "<=");
	   if (!strClientes.isEmpty())
	  	 tkts.getRecords().addFixedFilter("where customer_id_reducido in ( "+strClientes+") ");
	   if (!strClientesNegativa.isEmpty())
	  	 tkts.getRecords().addFixedFilter("where customer_id_reducido not in ( "+strClientesNegativa+") ");
		int id = 0;
		tkts.readAll();
		while (tkts.getRecords().nextRecord()) {
			BizPNRTicket tkt = (BizPNRTicket) tkts.getRecords().getRecord();
      if (tkt.getNumeroboleto().equals("3518500057"))
      	PssLogger.logInfo("log point");
      if (tkt.getNumeroboleto().equals(""))
				continue;
			boolean findOra = mapTxtOra.get(tkt.getNumeroboleto()) != null;


//			if (getFechaDesde().after(tkt.getCreationDate()))
//				continue;
//			if (getFechaHasta().before(tkt.getCreationDate()))
//				continue;
			if (tktsProc.get(tkt.getId()) != null)
				continue;



			if (findOra) {
				Iterator<BizTrxOra> itO = mapTxtOra.get(tkt.getNumeroboleto()).iterator();
				while (itO.hasNext()) {
					BizTrxOra tktOra = itO.next();
		      if (tktOra.getFactura().equals("195433"))
		      	PssLogger.logInfo("log point");
					if (!(isDocPermitidoOracle(tktOra.getTipoDocumento(),tktOra.getTipoServicio())))
						continue;
					if (!(isDocConciliable(tktOra.getTipoDocumento(),tktOra.getTipoServicio())))
						continue;
					if (!isDKActive(tktOra.getDk()))
						continue;
					BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Analizando tickets boleto "+tkt.getNumeroboleto(), pos++, mapTxtOraAll.size(), false, null);
				
				
					saveDetail(tktOra,tkt,docs,tktsProc,null);
				}
			}else {			
				fillByTktSearchInOracle(tkt,docs,tktsProc,null);

			}

		}
		tkts.getRecords().closeRecord();
	}
	
	public void fillByTktSearchInOracle(BizPNRTicket tkt,HashMap<String, String> docs, HashMap<Long, String> tktsProc,JOrderedMap<String, Double> acumPremios) throws Exception {
		JRecords<BizTrxOra> findOrac = new JRecords<BizTrxOra>(BizTrxOra.class);
		findOrac.dontThrowException(true);
		findOrac.addFixedFilter("where tipo_documento in ( 'STE','RFN','FCE','DXE') ");
		findOrac.addFilter("boleto", tkt.getNumeroboleto()+"R");
		findOrac.readAll();
		if (!findOrac.exists()) {
			findOrac.closeRecord();
			findOrac.clearFilters();
			findOrac.addFixedFilter("where tipo_documento in ( 'STE','RFN','FCE','DXE') ");
			findOrac.addFilter("boleto", tkt.getNumeroboleto());
			findOrac.readAll();
		}
		if (findOrac.exists()) {
			JIterator<BizTrxOra> iter = findOrac.getStaticIterator();
			while (iter.hasMoreElements()) {
				BizTrxOra trxOra = iter.nextElement();
				if (trxOra.getFactura().equals("195433"))
					PssLogger.logInfo("log point");
				if (!JDateTools.between(trxOra.getFechaEmision(), JDateTools.getDateStartDay(getFechaDesde()), JDateTools.getDateEndDay(getFechaHasta())))
					continue; // fuera de fecha

				if (!(isDocPermitidoOracle(trxOra.getTipoDocumento(), trxOra.getTipoServicio())))
					continue;
				if (!(isDocConciliable(trxOra.getTipoDocumento(), trxOra.getTipoServicio())))
					continue;
				if (!isDKActive(trxOra.getDk()))
					continue;
				saveDetail(trxOra, tkt, docs, tktsProc, acumPremios);
			}
		} else {
			tktsProc.put(tkt.getId(), tkt.getNumeroboleto());
			BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Analizando Falta trx Oracle "+tkt.getNumeroboleto(), pos++, mapTxtOraAll.size(), false, null);
			
			fillInforme(tkt, null, "Falta TRX en oracle " + tkt.getNumeroboleto() + (tkt.isVoided() ? "(Anulado)" : ""), null);

		}
		findOrac.closeRecord();
	}

	
	public void saveDetail(BizTrxOra tktOra,BizPNRTicket tkt,HashMap<String, String> docs, HashMap<Long, String> tktsProc,JOrderedMap<String, Double> acumPremios) throws Exception {
		String cl = tkt.getCustomerIdReducido();
    if (tkt.getNumeroboleto().equals("3518500057"))
    	PssLogger.logInfo("log point");
		if (cl == null || cl.isEmpty())
			cl = tktOra.getDk();
		if (!cl.equals(tktOra.getDk()))
			cl = tktOra.getDk();
		if (cl == null || cl.isEmpty())
			return;
		if (!isDKActive(cl))
			return;
		if (docs.get(tktOra.getUniqueKey())!=null) {
			return;
		}
//		if (!tktOra.getDk().equals(tkt.getCustomerIdReducido())) {
//			BizMailingPersona pers = new BizMailingPersona();
//			pers.dontThrowException(true);
//			if (pers.read(BizMailingPersona.CLIENTE_REDUCIDO, tkt.getCompany(), tkt.getCustomerIdReducido())) {
//				if (pers.isNullNumero() || pers.getNumero().isEmpty() ||  !pers.getNumero().equals(tktOra.getDk())) {
//					if (pers.isNullNumero() || pers.getNumero().isEmpty() ) {
//						pers.setNumero(tktOra.getDk());
//						pers.update();
//					}  else {
//						tktsProc.put(tkt.getId(), tkt.getNumeroboleto());
//						
//					}								
//				}
//
//			}
//		}
		// correccion por bug de ndc, que no viene la discriminacion del iva
		if (tkt.getGDS().equals("NDC") && tkt.getImpuestosTotal()==tktOra.getIva()+tktOra.getTua()) {
			if (tkt.getIvaFactura()!=tktOra.getIva()) {
				tkt.setIvaFactura(tktOra.getIva());
				tkt.setImpuestoFactura(tktOra.getTua());
				tkt.setIva(tkt.getIva()-tkt.getIvaFactura()+tktOra.getIva());
				tkt.setImpuestos(tkt.getIva()-tkt.getImpuestoFactura()+tktOra.getTua());
				tkt.update();
			}
		}
		boolean faltaVoid =false;
		boolean faltanteDXE=false;
		boolean faltanteSTE=false;
		boolean diferenciaDXE=false;
		if (tkt.isVoided()) {
			// check DXE
			boolean findD=false;
			boolean findDSTE = false;
			BizTrxOra findOrac = new BizTrxOra();
			BizTrxOra findOracSTE = new BizTrxOra();
			if (tktOra.getTipoDocumento().equals("DXE")) {
				 findOrac = tktOra;
				 findD=true;
			} else {
				findOrac = new BizTrxOra();
				findOrac.dontThrowException(true);
				findOrac.addFixedFilter("where tipo_documento in ( 'DXE') ");
				findOrac.addFilter("boleto", tktOra.getBoleto()+"R");
				findD = findOrac.read();
				if (!findD) {
					findOrac = new BizTrxOra();
					findOrac.dontThrowException(true);
					findOrac.addFixedFilter("where tipo_documento in ( 'DXE') ");
					findOrac.addFilter("boleto", tktOra.getBoleto());
					findD = findOrac.read();
				}
			}
			if (tktOra.getTipoDocumento().equals("STE")) {
				 findOracSTE = tktOra;
				 findDSTE=true;
			} else {
				findOracSTE.clearFilters();
				findOracSTE.dontThrowException(true);
				findOracSTE.addFixedFilter("where tipo_documento in ( 'STE') ");
				findOracSTE.addFilter("boleto", tktOra.getBoleto()+"R");
				findDSTE = findOracSTE.read();
				if (!findDSTE) {
					findOracSTE.clearFilters();
					findOracSTE.dontThrowException(true);
					findOracSTE.addFixedFilter("where tipo_documento in ( 'STE') ");
					findOracSTE.addFilter("boleto", tktOra.getBoleto());
					findDSTE = findOracSTE.read();
				}
			}
			if (!findD) {
				faltanteDXE=true;
			} else {
				docs.put(findOrac.getUniqueKey(), tktOra.getFactura());

			}
			if (!findDSTE) {
				faltanteSTE=true;
			} else {
				docs.put(findOracSTE.getUniqueKey(), tktOra.getFactura());
			}
			if (findDSTE && findD) {
				diferenciaDXE = findOrac.getImporte()+findOracSTE.getImporte()!=0;
			}
			if (faltanteDXE && faltanteSTE)
				fillInforme(tkt,tktOra,"Falta STE y DXE en Oracle", BizLiqError.CR_WARNING);
			else if (faltanteDXE)
				fillInforme(tkt,tktOra,"Falta DXE en Oracle", BizLiqError.CR_WARNING);
			else if (faltanteSTE)
				fillInforme(tkt,tktOra,"Falta STE en Oracle", BizLiqError.CR_WARNING);
			else if (diferenciaDXE)
				fillInforme(tkt,tktOra,"STE y DXE dif.importe", BizLiqError.CR_WARNING);
			return;
		} else {
			if (tktOra.getTipoDocumento().equals("DXE")) {
				faltaVoid = !tkt.isVoided() && (tktOra.getTarifa()+tkt.getTarifaFacturaLocal()==0);
			}
		}
		
		BizLiqDetail detail = new BizLiqDetail();
		detail.setCompany(getCompany());
		detail.setDk(tktOra.getDk());
		detail.setClase(tkt.getClase());
		detail.setNroBoleto(tkt.getNumeroboleto());
		detail.setLiquidacionId(getLiquidacionId());
		if (tktOra==null)
			detail.setOrganization(BizOrganization.readByIata(getCompany(), tkt.getNroIata()));
		else
			detail.setOrganization(tktOra.getOrg());
		detail.setClases(tkt.getClassIntinerary());
		detail.setPrestador(tkt.getCarrier());
		detail.setTarifa(tktOra.getTarifa());
		detail.setTipo(tkt.getTipoOperacion());
		detail.setOrigen("TKM");
		detail.setInterfaceId(tkt.getId());
		detail.setReserva(tkt.getCodigopnr());
		detail.setRuta(tkt.getMiniRoute());
		detail.setPasajero(tkt.getNombrePasajero());
		detail.setLineas(tkt.getAirIntinerary());

		detail.setTipoDoc(tktOra.getTipoDocumento());
		detail.setNroDoc(tktOra.getFactura());
		detail.setAgente(tktOra.getAgenteEmite());
		docs.put(tktOra.getUniqueKey(), tktOra.getFactura());
		tktsProc.put(tkt.getId(), tkt.getNumeroboleto());
		long signo = isDevolucion(tktOra.getTipoDocumento())  ? -1 : 1;
		detail.setTipoServicio(getServicio(tktOra.getTipoServicio()));
		detail.setFechaDoc(tktOra.getFechaFactura()); // corregir
		detail.setIva(/*signo * */(tktOra.getIva())); // corregir
		detail.setTua(/*signo **/ (tktOra.getTua())); // corregir
		detail.setImporte(/*signo * */(tktOra.getTotalFactura()));
		double importeContado = tkt.getTarifaTotalFactura()-tkt.getMontoTarjeta();

	
		
//	double contado = /*signo * */(isContado(tktOra.getFormaPago()) ? detail.getImporte() : 0);
//	double tarjeta = /*signo * */(!isContado(tktOra.getFormaPago()) ? detail.getImporte() : 0);
	double contado = 0;
	double tarjeta = 0;

	String tipoPagoRaw = tktOra.getGlobalAttribute19(); // Ej: "CCAX,CA,"
	String importePagoRaw = tktOra.getGlobalAttribute6(); // Ej: "28776,9209,"

	if (tipoPagoRaw != null && importePagoRaw != null) {
	    String[] tipos = tipoPagoRaw.split(",");
	    String[] importes = importePagoRaw.split(",");
	    for (int i = 0; i < tipos.length && i < importes.length; i++) {
	        String tipo = tipos[i].trim();
	        String impStr = importes[i].trim();
	        if (!impStr.isEmpty()) {
	            try {
	                double imp = Double.parseDouble(impStr);
	                if (isContado(tipo)) {
	                    contado += imp;
	                } else {
	                    tarjeta += imp;
	                }
	            } catch (NumberFormatException e) {
	                // opcional: log o ignorar importe inválido
	            }
	        }
	    }
	}
	detail.setContado(contado);
	detail.setTarjeta(tarjeta);
	
		setTotalContado(getTotalContado() + contado);
		setTotalTarjeta(getTotalTarjeta() + tarjeta);
		detail.setContado(contado);
		detail.setTarjeta(tarjeta);
		detail.setFiscal((isDocFiscal(tktOra.getTipoDocumento()) ? contado + tarjeta : 0));
		detail.setNoFiscal((!isDocFiscal(tktOra.getTipoDocumento()) ? contado + tarjeta : 0));
		detail.setNoDevengado(0);
		setTotalFiscales(getTotalFiscales() + detail.getFiscal());
		setTotalNoFiscales(getTotalNoFiscales() + detail.getNoFiscal());
		setTotalFacturado(getTotalFacurado()+contado+tarjeta);

		detail.setFormaPago(tktOra.getFormaPago());
		detail.setSaldo(contado);
		detail.setIncentivo(0);
		detail.setPorcIncentivo(0);
//		if (isDevolucion(tktOra.getTipoDocumento())) {
//			BizLiqDetail oldDetail = new BizLiqDetail();
//			oldDetail.dontThrowException(true);
//			if (oldDetail.readByBoleto(tktOra.getBoleto())) {
//		
//				detail.setPorcComision(oldDetail.getPorcComision());
//				detail.setComision(JTools.rd(detail.getTarifa() * (detail.getPorcComision()/100.0) /* * signo*/, 2));
//				
//				setComisionNoDevengada(getComisionNoDevengada()+detail.getComision());;
//				
//				
//			} else {
//				BizLiqError error = new BizLiqError();
//				error.setCompany(getCompany());
//				error.setLiquidacionId(getLiquidacionId());
//				error.setError("Falta TRX en Historico " + tktOra.getBoleto());
//				error.setType(BizLiqError.CR_FALTANTEHIST);
//				error.processInsert();
//			}
//		} else {
//			Double porc = 0.0;
//			if (acumPremios!=null) {
//				 porc = acumPremios.getElement(cl);
//					if (porc == null) {
//						porc = acumPremios.getElement("ALL");
//					}
//					if (porc == null)
//						porc = 0.0;
//
//					detail.setPorcComision(porc);
//					detail.setComision(JTools.rd(detail.getTarifa() * (porc/100.0) /* * signo*/, 2));
//					setComision(getComision()+detail.getComision());
//				
//			} else {
//				porc =tkt.getPorcentajeover();
//				detail.setPorcComision(porc);
//				detail.setComision(JTools.rd(detail.getTarifa() * (porc/100.0), 2));
//				setComision(getComision()+detail.getComision());
//						
//			}
			double porc =tkt.getPorcentajeProrrateoOver();
			detail.setPorcComision(porc);
			detail.setComision(JTools.rd(detail.getTarifa() * (porc/100.0), 2));
			setComision(getComision()+detail.getComision());
	

		detail.processInsert();
		fillInforme(tkt,tktOra,faltaVoid?"Falta voidear en TKM":null, faltaVoid?BizLiqError.CR_VOID:null);
	}
	


	public void fillOracle(HashMap<String, String> docs, HashMap<Long, String> tktsProc) throws Exception {
		Iterator<BizTrxOra> itO = mapTxtOraAll.iterator();
		while (itO.hasNext()) {
			BizTrxOra tktOra = itO.next();
			if (!isDKActive(tktOra.getDk()))
				continue;
			String boletoKey =  tktOra.getBoleto().endsWith("R")
          ? tktOra.getBoleto().substring(0, tktOra.getBoleto().length() - 1)
          : tktOra.getBoleto();
      if (tktOra.getBoleto().equals("3518500057"))
      	PssLogger.logInfo("log point");
			if (!JDateTools.between(tktOra.getFechaEmision(), JDateTools.getDateStartDay(getFechaDesde()), JDateTools.getDateEndDay(getFechaHasta())))
				continue; //fuera de fecha

 //    	PssLogger.logInfo("check  point " + boletoKey);
//			if (tktOra.getLineaAerea().length() != 2) {
//				continue; //hotel
//			}

			if (docs.get(tktOra.getUniqueKey())!=null) {
				continue;
			}
			BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Analizando oracle boleto "+tktOra.getBoleto(), pos++, mapTxtOraAll.size(), false, null);

			if (isDocAdmintNoConciliable(tktOra.getTipoDocumento(),tktOra.getTipoServicio())) {
				BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Analizando documento  "+tktOra.getTipoDocumento()+ "-"+tktOra.getFactura(), pos++, mapTxtOraAll.size(), false, null);

				BizPNRTicket tkt = new BizPNRTicket();
				tkt.dontThrowException(true);
				if (tktOra.getTipoDocumento().equals("STE") && tkt.ReadByBoleto(getCompany(), boletoKey)) {
					if (tkt.isVoided())
						continue;
				}
				BizLiqDetail detail = new BizLiqDetail();
				detail.setCompany(getCompany());
				detail.setDk(tktOra.getDk());
				detail.setClase(tktOra.getClase());
				detail.setNroBoleto(boletoKey);
				detail.setLiquidacionId(getLiquidacionId());
				detail.setOrganization( tktOra.getOrg());
				// detail.setClases(tktOra.getClase());
				detail.setPrestador(tktOra.getLineaAerea());
				detail.setTarifa(tktOra.getTarifa());
				detail.setTipo(tktOra.getPccEmite());
				detail.setCustomerTrxId(tktOra.getCustomerTrxId());
				detail.setOrigen("TKM");
				// detail.setInterfaceId(tktOra.getInterfaceId());
				detail.setReserva(tktOra.getReferencia());
				detail.setRuta(tktOra.getRuta());
				detail.setPasajero(tktOra.getPasajero());
				// detail.setLineas(tktOra.getAerolinea());

				detail.setTipoDoc(tktOra.getTipoDocumento());
				detail.setNroDoc(tktOra.getFactura());
				detail.setAgente(tktOra.getAgenteEmite());
				docs.put(tktOra.getUniqueKey(), tktOra.getFactura());
//				long signo = isDevolucion(tktOra.getTipoDocumento())  ? -1 : 1;
				detail.setTipoServicio(getServicio(tktOra.getTipoServicio()));
				detail.setFechaDoc(tktOra.getFechaFactura()); // corregir
				detail.setIva(/*signo **/ (tktOra.getIva())); // corregir
				detail.setTua(/*signo * */(tktOra.getTua())); // corregir
				detail.setImporte(/*signo **/ (tktOra.getTotalFactura()));

				double contado = /*signo * */(isContado(tktOra.getFormaPago()) ? detail.getImporte() : 0);
				double tarjeta = /*signo * */(!isContado(tktOra.getFormaPago()) ? detail.getImporte() : 0);
/*				double contado = 0;
				double tarjeta = 0;

				String tipoPagoRaw = tktOra.getGlobalAttribute19(); // Ej: "CCAX,CA,"
				String importePagoRaw = tktOra.getGlobalAttribute6(); // Ej: "28776,9209,"

				if (tipoPagoRaw != null && importePagoRaw != null) {
				    String[] tipos = tipoPagoRaw.split(",");
				    String[] importes = importePagoRaw.split(",");
				    for (int i = 0; i < tipos.length && i < importes.length; i++) {
				        String tipo = tipos[i].trim();
				        String impStr = importes[i].trim();
				        if (!impStr.isEmpty()) {
				            try {
				                double imp = Double.parseDouble(impStr);
				                if (isContado(tipo)) {
				                    contado += imp;
				                } else {
				                    tarjeta += imp;
				                }
				            } catch (NumberFormatException e) {
				                // opcional: log o ignorar importe inválido
				            }
				        }
				    }
				}*/
				detail.setContado(contado);
				detail.setTarjeta(tarjeta);
				
				setTotalContado(getTotalContado() + contado);
				setTotalTarjeta(getTotalTarjeta() + tarjeta);
				
				detail.setContado(contado);
				detail.setTarjeta(tarjeta);
				detail.setFiscal((isDocFiscal(tktOra.getTipoDocumento()) ? contado + tarjeta : 0));
				detail.setNoFiscal((!isDocFiscal(tktOra.getTipoDocumento()) ? contado + tarjeta : 0));
				detail.setNoDevengado(0);
				setTotalFiscales(getTotalFiscales() + detail.getFiscal());
				setTotalNoFiscales(getTotalNoFiscales() + detail.getNoFiscal());
				setTotalFacturado(getTotalFacurado()+contado);

				detail.setFormaPago(tktOra.getFormaPago());
				detail.setSaldo(contado);
				detail.setIncentivo(0);
				detail.setPorcIncentivo(0);

				detail.setPorcComision(0);
				detail.setComision(0);
				setComision(getComision()+detail.getComision());
				boolean faltaVoid=false;
				if (detail.getTipoDoc().equals("DXE")) {
				  tkt = new BizPNRTicket();
					tkt.dontThrowException(true);
					if (tkt.ReadByBoleto(getCompany(), detail.getNroBoleto())) {
						faltaVoid = !tkt.isVoided() && (tktOra.getTarifa()+tkt.getTarifaFacturaLocal()==0);
				}
				}

				detail.processInsert();
				fillInforme(null, tktOra, faltaVoid?"Falta voidear en TKM":"Doc.no conciliable", faltaVoid?BizLiqError.CR_VOID:BizLiqError.CR_OK);
				continue;
			}
			
			
			if (isDevolucion(tktOra.getTipoDocumento())) {
				BizLiqDetail oldDetail = new BizLiqDetail();
				boolean findHistorico=false;
				oldDetail.dontThrowException(true);
				if (oldDetail.readByBoleto(getCompany(),boletoKey)) {
					findHistorico=true;
				}
					BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Analizando documento  "+tktOra.getTipoDocumento()+ "-"+tktOra.getFactura(), pos++, mapTxtOraAll.size(), false, null);
					BizLiqDetail detail = new BizLiqDetail();
					detail.setCompany(getCompany());
					detail.setDk(tktOra.getDk());
					detail.setClase(tktOra.getClase());
					detail.setNroBoleto(tktOra.getBoleto());
					detail.setLiquidacionId(getLiquidacionId());
					detail.setOrganization(tktOra.getOrg());
					detail.setClases(tktOra.getClase());
					detail.setPrestador(tktOra.getLineaAerea());
					detail.setTarifa(tktOra.getTarifa());
					detail.setLineas(tktOra.getLineaAerea());
					detail.setTipo(tktOra.getPccEmite());
					detail.setCustomerTrxId(tktOra.getCustomerTrxId());
					detail.setOrigen("TKM");
					if (findHistorico)
						detail.setInterfaceId(oldDetail.getInterfaceId());
					detail.setReserva(tktOra.getReferencia());
					detail.setRuta(tktOra.getRuta());
					detail.setPasajero(tktOra.getPasajero());
					// detail.setLineas(tktOra.getAerolinea());

					detail.setTipoDoc(tktOra.getTipoDocumento());
					detail.setNroDoc(tktOra.getFactura());

					detail.setTipoDoc(tktOra.getTipoDocumento());
					detail.setNroDoc(tktOra.getFactura());
					docs.put(tktOra.getUniqueKey(), tktOra.getFactura());
					long signo = tktOra.getTipoDocumento().equals("RFN")|| tktOra.getTipoDocumento().equals("DXE")  ? -1 : 1;
					detail.setTipoServicio(getServicio(tktOra.getTipoServicio()));
					detail.setFechaDoc(tktOra.getFechaFactura()); // corregir
					detail.setIva(/*signo **/(tktOra.getIva())); // corregir
					detail.setTua(/*signo **/ (tktOra.getTua())); // corregir
					detail.setImporte(/*signo * */(tktOra.getTotalFactura()));

//				double contado = /*signo * */ (isContado(tktOra.getFormaPago()) ? detail.getImporte() : 0);
//				double tarjeta = /*signo * */ (!isContado(tktOra.getFormaPago()) ? detail.getImporte() : 0);
				double contado = 0;
				double tarjeta = 0;

				String tipoPagoRaw = tktOra.getGlobalAttribute19(); // Ej: "CCAX,CA,"
				String importePagoRaw = tktOra.getGlobalAttribute6(); // Ej: "28776,9209,"

				if (tipoPagoRaw != null && importePagoRaw != null) {
				    String[] tipos = tipoPagoRaw.split(",");
				    String[] importes = importePagoRaw.split(",");
				    for (int i = 0; i < tipos.length && i < importes.length; i++) {
				        String tipo = tipos[i].trim();
				        String impStr = importes[i].trim();
				        if (!impStr.isEmpty()) {
				            try {
				                double imp = Double.parseDouble(impStr);
				                if (isContado(tipo)) {
				                    contado += imp;
				                } else {
				                    tarjeta += imp;
				                }
				            } catch (NumberFormatException e) {
				                // opcional: log o ignorar importe inválido
				            }
				        }
				    }
				}
				detail.setContado(contado);
				detail.setTarjeta(tarjeta);

				setTotalContado(getTotalContado() + contado);
					setTotalTarjeta(getTotalTarjeta() + tarjeta);
					detail.setContado(contado);
					detail.setTarjeta(tarjeta);
					detail.setFiscal((isDocFiscal(tktOra.getTipoDocumento()) ? contado + tarjeta : 0));
					detail.setNoFiscal((!isDocFiscal(tktOra.getTipoDocumento()) ? contado + tarjeta : 0));
					detail.setNoDevengado(0);
					setTotalFiscales(getTotalFiscales() + detail.getFiscal());
					setTotalNoFiscales(getTotalNoFiscales() + detail.getNoFiscal());
					setTotalFacturado(getTotalFacurado()+contado+tarjeta);
					
					detail.setFormaPago(tktOra.getFormaPago());
					detail.setSaldo(contado);
					detail.setIncentivo(0);
					detail.setPorcIncentivo(0);
			
//					if (findHistorico)
//						detail.setPorcComision(oldDetail.getPorcComision());
//					else
						detail.setPorcComision(0);
					detail.setComision(JTools.rd(detail.getTarifa() * (detail.getPorcComision()/100.0) /* * signo*/, 2));
					
					setComisionNoDevengada(getComisionNoDevengada()+detail.getComision());;
					if (detail.getTipoDoc().equals("NPD"))
						PssLogger.logInfo("log point");

					detail.processInsert();
					// a pedido de edgar castillo sosa 4/2/25
					findHistorico=false;
					fillInforme(null, tktOra, findHistorico?"Falta TRX en Historico " + tktOra.getBoleto():"",findHistorico?BizLiqError.CR_FALTANTEHIST:BizLiqError.CR_OK);
				

			} else {
				if (tktOra.getBoleto().isEmpty()) continue;
				if (tktOra.getRuta().indexOf("BOLETO CANCELADO")!=-1) {
					continue;
				}
				String nroBoleto = tktOra.getBoleto();
				if (nroBoleto.isEmpty())
					continue;
				if (tktOra.getLineaAerea().isEmpty())
					continue;
				if (tktOra.getTipoServicio().equals("M1"))
					continue;
				if (tktOra.getTipoServicio().equals("M2"))
					continue;
				if (nroBoleto.endsWith("R"))
					nroBoleto = nroBoleto.substring(0,nroBoleto.length()-1);
				if (!JTools.isNumberPure(nroBoleto))
					continue;
				if (!(isDocPermitidoOracle(tktOra.getTipoDocumento(),tktOra.getTipoServicio())))
					continue;
				BizPNRTicket tkt = new BizPNRTicket();
				tkt.dontThrowException(true);
				if (!tkt.ReadByBoleto(getCompany(), nroBoleto)) {
					fillInforme(null, tktOra, "Falta TRX en TKM " + tktOra.getBoleto()+" referenciando en "+tktOra.getTipoDocumento()+"-"+tktOra.getFactura(), null);
				} else {
//					if (tktOra.getTipoDocumento().equals("STE")) {
//						if (tkt.getCreationDate()==null)
//							continue;
//						if (getFechaHasta().after(tkt.getCreationDate()))
//							continue;
//						if (getFechaDesde().before(tkt.getCreationDate()))
//							continue;
//					}
					saveDetail(tktOra,tkt,docs,tktsProc,null);

				}
				


			}
		}
	}

	public void procLimpiar() throws Exception {
		BizLiqDetail cons = new BizLiqDetail();
		cons.addFilter("company", getCompany());
		cons.addFilter("liquidacion_id", getLiquidacionId());
		cons.addFilter("origen", "TKM");
		cons.deleteMultiple();
		BizLiqError errors = new BizLiqError();
		errors.addFilter("company", getCompany());
		errors.addFilter("liquidacion_id", getLiquidacionId());
		errors.deleteMultiple();
	}

	public void procLimpiarAcum() throws Exception {
		BizLiqAcum cons = new BizLiqAcum();
		cons.addFilter("company", getCompany());
		cons.addFilter("liquidacion_id", getLiquidacionId());
		cons.deleteMultiple();
	}
	public void procLimpiarAgrup() throws Exception {
		BizLiqAgrupado cons = new BizLiqAgrupado();
		cons.addFilter("company", getCompany());
		cons.addFilter("liquidacion_id", getLiquidacionId());
		cons.deleteMultiple();
	}
	


	public void procLiquidar() throws Exception {
		procLimpiarAcum();
		procLimpiarAgrup();
		loadClientes();
//		setFechaLiquidacion(new Date());
		setEstado(BizLiqHeader.PEND_PUBLICAR);
		processUpdate();
		String sql = "";
		sql += " select b.dk as descripcion,count(*) as valor, b.organization as icono_string,1 as icono from  ";
		sql += " bsp_liquidation_detail b ";
		sql += " where b.company='" + getCompany() + "' and b.liquidacion_id = " + getLiquidacionId() + "	group by b.dk,b.organization ";
		JRecords<BizVirtual> virt = new JRecords<BizVirtual>(BizVirtual.class);
		virt.SetSQL(sql);
		JIterator<BizVirtual> it = virt.getStaticIterator();
		while (it.hasMoreElements()) {
			BizVirtual dks = it.nextElement();
			procGenerarAcumulado(dks.getDescrip(),dks.getIconoString());
		}
		
		
		JRecords<BizGrupoDK> agrups = new JRecords<BizGrupoDK>(BizGrupoDK.class);
		agrups.addFilter("use_liq", true);
		JIterator<BizGrupoDK> itg = agrups.getStaticIterator();
		while (itg.hasMoreElements()) {
			BizGrupoDK grupo = itg.nextElement();
			boolean find = false;
			JRecords<BizGrupoDKDetail> agrupsDetail = grupo.getObjDetalle();
			JIterator<BizGrupoDKDetail> itgd = agrupsDetail.getStaticIterator();
			while (itgd.hasMoreElements()) {
				BizGrupoDKDetail grupoDet = itgd.nextElement();
				find |=isDKActive(grupoDet.getDK());
			}
			
			if (find)
				procGenerarAgrupado(grupo);		
		}
	}
	public void procPublicar(boolean sendMail) throws Exception {
		setEstado(BizLiqHeader.PUBLICADO);
		processUpdate();
		JRecords<BizLiqAcum> recs = new JRecords<BizLiqAcum>(BizLiqAcum.class);
		recs.addFilter("company", getCompany());
		recs.addFilter("liquidacion_id", getLiquidacionId());
		JIterator<BizLiqAcum> it = recs.getStaticIterator();
		while (it.hasMoreElements()) {
			BizLiqAcum liq = it.nextElement();
			liq.setEstado(BizLiqHeader.PUBLICADO);
			liq.setFechaLiquidacion(getFechaLiquidacion());
			liq.processUpdate();
			if (sendMail)
				liq.sendMail();
		}
		JRecords<BizLiqAgrupado> recsAgr = new JRecords<BizLiqAgrupado>(BizLiqAgrupado.class);
		recsAgr.addFilter("company", getCompany());
		recsAgr.addFilter("liquidacion_id", getLiquidacionId());
		JIterator<BizLiqAgrupado> itA = recsAgr.getStaticIterator();
		while (itA.hasMoreElements()) {
			BizLiqAgrupado liq = itA.nextElement();
			liq.setEstado(BizLiqHeader.PUBLICADO);
			liq.setFechaLiquidacion(getFechaLiquidacion());
			liq.processUpdate();
		}
	}
	public void procDespublicar() throws Exception {
		setEstado(BizLiqHeader.PEND_CONCILIAR);
		processUpdate();
		JRecords<BizLiqAcum> recs = new JRecords<BizLiqAcum>(BizLiqAcum.class);
		recs.addFilter("company", getCompany());
		recs.addFilter("liquidacion_id", getLiquidacionId());
		JIterator<BizLiqAcum> it = recs.getStaticIterator();
		while (it.hasMoreElements()) {
			BizLiqAcum liq = it.nextElement();
			liq.setEstado(BizLiqHeader.EN_PROCESO);
			liq.processUpdate();
		}
		JRecords<BizLiqAgrupado> recsAgr = new JRecords<BizLiqAgrupado>(BizLiqAgrupado.class);
		recsAgr.addFilter("company", getCompany());
		recsAgr.addFilter("liquidacion_id", getLiquidacionId());
		JIterator<BizLiqAgrupado> itA = recsAgr.getStaticIterator();
		while (itA.hasMoreElements()) {
			BizLiqAgrupado liq = itA.nextElement();
			liq.setEstado(BizLiqHeader.EN_PROCESO);
			liq.setFechaLiquidacion(getFechaLiquidacion());
			liq.processUpdate();
		}
	}
	public void procGenerarAcumulado(String dk,String org) throws Exception {
		JRecords<BizLiqDetail> detail = new JRecords<BizLiqDetail>(BizLiqDetail.class);
		detail.addFilter("company", getCompany());
		detail.addFilter("liquidacion_id", getLiquidacionId());
		detail.addFilter("dk", dk);
		detail.addFilter("organization", org);
		BizClienteDK mailing = new BizClienteDK();
		mailing.dontThrowException(true);
		mailing.ReadByDK( getCompany(), dk );
		BizCliente clienteOra = new BizCliente();
		clienteOra.dontThrowException(true);
		boolean findClienteOra = clienteOra.read(dk);
		BizLiqAcum acum = new BizLiqAcum();
		acum.setCompany(getCompany());
		acum.setLiquidacionId(getLiquidacionId());
		acum.setDK(dk);
		acum.setCBU(mailing.isNullCtaBancaria()? getCBU():mailing.getCtaBancaria());
		acum.setCuenta(mailing.isNullCtaClave()? getCuenta():mailing.getCtaClave());
		acum.setFechaComision(getFechaComision());
		acum.setFechaPago(getFechaPago());
		acum.setFechaDesde(getFechaDesde());
		acum.setFechaHasta(getFechaHasta());
		acum.setEstado(BizLiqHeader.EN_PROCESO);
		acum.setFechaLiquidacion(getFechaLiquidacion());
		acum.setInfo(getInfo());
		acum.setTotalComision(0);
		acum.setTotalContado(0);
		acum.setTotalTarjeta(0);
		acum.setTotalTax(0);
		acum.setTotalFacturado(0);
		acum.setTotalNoFiscales(0);
		acum.setTotalFiscales(0);
		acum.setDescripcion(mailing.getDescripcion());
		acum.setDKCode(findClienteOra?clienteOra.getRefBancaria():mailing.getCode());
		acum.setComisionNeto(0);
		acum.setComision(0);
		acum.setComisionNoDevengada(0);
		acum.setIvaComision(0);
		acum.setPorcIvaComision(mailing.isNullIva()?getPorcIvaComision():mailing.getIva());
		acum.setMoneda(BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getCurrency());


		JIterator<BizLiqDetail> it = detail.getStaticIterator();
		while (it.hasMoreElements()) {
			BizLiqDetail det = it.nextElement();
			acum.setOrganization(det.getOrganization());
			acum.setTotalContado(acum.getTotalContado() + det.getContado());
			acum.setTotalTarjeta(acum.getTotalTarjeta() + det.getTarjeta());
			acum.setTotalTax(acum.getTotalTax() + det.getIva()+det.getTua());
			acum.setTotalFiscales(acum.getTotalFiscales() + det.getFiscal());
			acum.setTotalNoFiscales(acum.getTotalNoFiscales() + det.getNoFiscal());
			acum.setComisionNoDevengada(acum.getComisionNoDevengada() + det.getNoDevengado());
			acum.setComisionNeto(acum.getComisionNeto() + det.getComision());
			acum.setTotalFacturado(acum.getTotalFacturado() + det.getImporte());
			
		}
		acum.setComision(acum.getComisionNeto() + acum.getComisionNoDevengada());
		acum.setIvaComision((acum.getComision() * acum.getPorcIvaComision() / 100));
		acum.setTotalComision(acum.getComision() + acum.getComisionNoDevengada() + acum.getIvaComision());
		acum.processInsert();
	}
	public void procGenerarAgrupado(BizGrupoDK dk) throws Exception {
		JRecords<BizLiqDetail> detail = new JRecords<BizLiqDetail>(BizLiqDetail.class);
		detail.addFilter("company", getCompany());
		detail.addFilter("liquidacion_id", getLiquidacionId());
		detail.addJoin(JRelations.JOIN, "BSP_GRUPO_DK_DETAIL", "BSP_GRUPO_DK_DETAIL.dk=BSP_LIQUIDATION_DETAIL.dk and BSP_GRUPO_DK_DETAIL.company=BSP_LIQUIDATION_DETAIL.company");
		detail.addJoin(JRelations.JOIN, "BSP_GRUPO_DK", "BSP_GRUPO_DK.id_grupo=BSP_GRUPO_DK_DETAIL.id_grupo and BSP_GRUPO_DK.company=BSP_GRUPO_DK_DETAIL.company");
		detail.addFilter("BSP_GRUPO_DK", "id_grupo", dk.getIdGrupo(),"=");
	
		String viewAllDk =  dk.getDetalleViewAll();
		BizClienteDK mailing = null;
		if (viewAllDk!=null) {
			mailing = new BizClienteDK();
			mailing.dontThrowException(true);
			mailing.ReadByDK( getCompany(), viewAllDk);

		}

		BizLiqAgrupado agrup = new BizLiqAgrupado();
		agrup.setCompany(getCompany());
		agrup.setLiquidacionId(getLiquidacionId());
		agrup.setGrupoDK(dk.getIdGrupo());
		agrup.setCBU(mailing==null || mailing.isNullCtaBancaria()? getCBU():mailing.getCtaBancaria());
		agrup.setCuenta(mailing==null || mailing.isNullCtaClave()? getCuenta():mailing.getCtaClave());
		agrup.setFechaComision(getFechaComision());
		agrup.setFechaPago(getFechaPago());
		agrup.setFechaDesde(getFechaDesde());
		agrup.setFechaHasta(getFechaHasta());
		agrup.setEstado(BizLiqHeader.EN_PROCESO);
		agrup.setFechaLiquidacion(getFechaLiquidacion());
		agrup.setInfo(getInfo());
		agrup.setTotalComision(0);
		agrup.setTotalContado(0);
		agrup.setTotalTarjeta(0);
		agrup.setTotalFacturado(0);
		agrup.setTotalNoFiscales(0);
		agrup.setTotalFiscales(0);
		agrup.setDescripcion(dk.getDescripcion());
		agrup.setComisionNeto(0);
		agrup.setComision(0);
		agrup.setComisionNoDevengada(0);
		agrup.setIvaComision(0);
		agrup.setPorcIvaComision(0);
		agrup.setMoneda(BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getCurrency());


		JIterator<BizLiqDetail> it = detail.getStaticIterator();
		while (it.hasMoreElements()) {
			BizLiqDetail det = it.nextElement();
			agrup.setOrganization(det.getOrganization());
			agrup.setTotalContado(agrup.getTotalContado() + det.getContado());
			agrup.setTotalTarjeta(agrup.getTotalTarjeta() + det.getTarjeta());
			agrup.setTotalFiscales(agrup.getTotalFiscales() + det.getFiscal());
			agrup.setTotalNoFiscales(agrup.getTotalNoFiscales() + det.getNoFiscal());
			agrup.setComisionNoDevengada(agrup.getComisionNoDevengada() + det.getNoDevengado());
			agrup.setComisionNeto(agrup.getComisionNeto() + det.getComision());
			agrup.setTotalFacturado(agrup.getTotalFacturado() + det.getImporte());
			
		}
		agrup.setComision(agrup.getComisionNeto() + agrup.getComisionNoDevengada());
		agrup.setIvaComision((agrup.getComision() * agrup.getPorcIvaComision() / 100));
		agrup.setTotalComision(agrup.getComision() + agrup.getComisionNoDevengada() + agrup.getIvaComision());
		agrup.processInsert();
	}

	private BizLiqError fillInforme( BizPNRTicket tkt, BizTrxOra  ora, String strError,String typeError) throws Exception {
		BizLiqError error = new BizLiqError();
		error.setCompany(getCompany());
		error.setLiquidacionId(getLiquidacionId());
		error.setError(strError);

		if (ora!=null) fillErrorWithOrcl(error, ora);
		if (tkt!=null) fillErrorWithTkm(error, tkt);
	
		if (typeError!=null) error.setType(typeError);
		else if (tkt==null) error.setType(BizLiqError.CR_FALTANTETKM);
		else if (ora==null) error.setType(BizLiqError.CR_FALTANTEORCL);
		else {
			String diferencia = "";
			boolean difTarifa = (Math.abs(Math.abs(error.getTarifa())-Math.abs(error.getTarifaTkm())))>1;
			if (difTarifa) diferencia += (diferencia.equals("")?"":", ")+ " Dif.Tarifa Base";
			boolean difImporte = (Math.abs(Math.abs(error.getImporte()))-Math.abs(error.getImporteTkm()))>1;
			if (difImporte) diferencia += (diferencia.equals("")?"":", ")+ " Dif.Importe";
			boolean difIva = (Math.abs(Math.abs(error.getIva())-(Math.abs(error.getIvaTkm()))))>1;
			if (difIva) diferencia += (diferencia.equals("")?"":", ")+ " Dif.Imp IVA";
			boolean difTUA = (Math.abs(Math.abs(error.getTua())-Math.abs(error.getTuaTkm())))>1;
			if (difTUA) diferencia += (diferencia.equals("")?"":", ")+ " Dif.Imp.TUA";
			boolean difIATA = !error.getIata().equals(error.getIataTkm());
			if (difIATA) diferencia += (diferencia.equals("")?"":", ")+ " Dif.IATA";
			boolean difTipoPago = error.getTarifa()!=0&& isContado(error.getFormaPago())!=(isContado(error.getFormaPagoTkm()));
			if (difTipoPago) diferencia += (diferencia.equals("")?"":", ")+ " Dif.Forma Pago";
			boolean difClase = !error.getClase().equals(error.getClaseTkm());
			if (difClase) diferencia += (diferencia.equals("")?"":", ")+ " Dif.Clase";
			boolean difCarrier = !error.getPrestador().equals(error.getPrestadorTkm());
			if (difCarrier) diferencia += (diferencia.equals("")?"":", ")+ " Dif.Carrier";
			
			
			boolean difTipoServicio = tkt.getTipoOperacion().equals("EMD")?false:!( error.getTipoServicio().equals("LI")||error.getTipoServicio().equals("LD"))?false:!error.getTipoServicio().equals(error.getTipoServicioTkm());
			if (difTipoServicio) diferencia += (diferencia.equals("")?"":", ")+ " Dif.Tipo Servicio";
			
			boolean dif = difTipoServicio || difTarifa || difImporte || difIva || difTUA || difIATA || difTipoPago|| difCarrier;
			
			if (dif) {
				error.setType(BizLiqError.CR_DIFERENCIA);
				error.setError("WARNING! Diferencia en: "+diferencia);
			}
			else error.setType(BizLiqError.CR_OK);
		}
		
		
		if (tkt!=null) error.setInterfaceId(tkt.getId());
		error.processInsert();	
		return error;
	}
  public static boolean compareRoutes(String route1, String route2) {
    // Normalizar ambas rutas
    String normalizedRoute1 = normalizeRoute(route1);
    String normalizedRoute2 = normalizeRoute(route2);

    // Comparar rutas normalizadas
    return normalizedRoute1.equals(normalizedRoute2);
	}
	
  private static String normalizeRoute(String route) {
    // Separar aeropuertos por delimitadores conocidos
    String[] segments = route.split("[()|/]");

    List<String> normalizedAirports = new ArrayList<>();
    for (String segment : segments) {
        // Limpiar espacios y asegurar mayúsculas
        String airport = segment.trim().toUpperCase();
        // Validar que el aeropuerto tenga exactamente 3 letras
        if (airport.matches("[A-Z]{3}") && (normalizedAirports.isEmpty() || !airport.equals(normalizedAirports.get(normalizedAirports.size() - 1)))) {
            normalizedAirports.add(airport);
        }
    }

    // Unir los aeropuertos con "/"
    return String.join("/", normalizedAirports);
  }
  
	private void fillErrorWithTkm(BizLiqError detail, BizPNRTicket tkt) throws Exception {
	  if (detail.getDk().equals(""))
	  	detail.setDk(tkt.getCustomerIdReducido());
		detail.setClaseTkm(tkt.getClase());
		detail.setNroBoleto(tkt.getNumeroboleto());
		detail.setPrestadorTkm(tkt.getCarrier());
		detail.setTarifaTkm(tkt.getTarifaFacturaLocal());
		detail.setTipo(tkt.getTipoOperacion());
		detail.setOrigen("TKM");
		if (detail.isNullOrganization() || detail.getOrganization().equals(""))
			detail.setOrganization(BizOrganization.readByIata(getCompany(), tkt.getNroIata()));

		detail.setInterfaceId(tkt.getId());
		detail.setReserva(tkt.getCodigopnr());
		detail.setRutaTkm(tkt.getAirIntinerary().replaceAll("-", "/"));
		detail.setPasajero(tkt.getNombrePasajero());
		detail.setLineas(tkt.getCarrierIntinerary());
		detail.setLineasTkm(tkt.getCarrierIntinerary());
		detail.setIataTkm(tkt.getNroIata());
		detail.setGDSTkm(tkt.getGDS());
		detail.setEmisionTkm(tkt.getOfficeId());
		detail.setNroTarjetaTkm(tkt.getNumeroTarjetaEnmascarada());
		detail.setFechaSalidaTkm(tkt.getDepartureDate());
		detail.setFechaFinTkm(tkt.getEndTravelDate());
		detail.setOrigenTkm(tkt.getAeropuertoOrigen());
		detail.setDestinoTkm(tkt.getAeropuertoDestino());
		detail.setTipoServicioTkm(tkt.isInternacional()?"LI":"LD");
		if (detail.getFechaDoc()==null)
			detail.setFechaDoc(tkt.getCreationDateAir());
		detail.setIvaTkm(tkt.getIvaFactura());
		detail.setTuaTkm(tkt.getImpuestoFactura()); 
		detail.setImporteTkm(tkt.getTarifaTotalFactura());
		detail.setContado(tkt.getTarifaTotalFactura()-tkt.getMontoTarjeta());
		detail.setTarjeta(tkt.getMontoTarjeta());
		detail.setFormaPagoTkm(!tkt.getNumeroTarjetaEnmascarada().isEmpty()?"CC"+tkt.getNombreTarjeta():"TR");
		detail.setSaldoTkm(detail.getContado());
		detail.setComision(tkt.getImporteover());
		detail.setPorcComision(tkt.getPorcentajeover());

	}
	
	public String getServicio(String tipo) {
		if (tipo.indexOf("LI")!=-1) return "LI";
		if (tipo.indexOf("LD")!=-1) return "LD";
		return tipo;
	}
	private void fillErrorWithOrcl(BizLiqError detail, BizTrxOra  tkt) throws Exception {
		detail.setDk(tkt.getDk());
		detail.setClase(tkt.getClase());
		detail.setNroBoleto(tkt.getBoleto());
		detail.setClases(tkt.getClase());
		detail.setPrestador(tkt.getLineaAerea());
		detail.setTarifa(tkt.getTarifa());
		detail.setOrigen("ORACLE");
		detail.setReserva(tkt.getReferencia());
		detail.setRuta(tkt.getRuta());
		detail.setPasajero(tkt.getPasajero());
		detail.setOrganization(tkt.getOrg());

		
		detail.setTipoDoc(tkt.getTipoDocumento());
		detail.setNroDoc(tkt.getFactura());
		detail.setTipoServicio(getServicio(tkt.getTipoServicio()));
		detail.setFechaDoc(tkt.getFechaFactura());
		detail.setIva(tkt.getIva());
		detail.setIata(tkt.getIata().replace(" ", ""));
		detail.setTua(tkt.getTua()); 
		detail.setImporte(tkt.getImporte());
		detail.setFormaPago(tkt.getFormaPago());
		double contado =  (isContado(tkt.getFormaPago()) ? detail.getImporte() : 0);
		double tarjeta =  (!isContado(tkt.getFormaPago()) ? detail.getImporte() : 0);
		detail.setContado(contado);
		detail.setTarjeta(tarjeta);

		detail.setFormaPago(tkt.getFormaPago());
		detail.setSaldo(detail.getContado());

	}
	
	JList<String> boletos;
	JRecords<BizCarrier> carriers;
	
	public void execProcConsolidarLiquidacion() throws Exception {
		JExec oExec = new JExec(this, "consolidarLiquidacion") {

			@Override
			public void Do() throws Exception {
				consolidarLiquidacion();
			}
		};
		oExec.execute();
	}

	public void procCleanConsolidar() throws Exception {
		BizDiferenciaLiq cons = new BizDiferenciaLiq();
		cons.addFilter("company", getCompany());
		cons.addFilter("id_liq", getLiquidacionId());
		cons.deleteMultiple();
	}
	public void consolidarLiquidacion() throws Exception {
		procCleanConsolidar();
		new BizPDF().procPendientes(getFechaDesde(),getFechaHasta());
		loadClientes();
		boletos = JCollectionFactory.createList();
		carriers = new JRecords<BizCarrier>(BizCarrier.class);
		carriers.addFilter("cod_iata", "null","<>");
		carriers.convertToHash("cod_iata");

		JRecords<BizLiqDetail> detail = new JRecords<BizLiqDetail>(BizLiqDetail.class);
		detail.addFilter("company", getCompany());
		detail.addFilter("liquidacion_id", getLiquidacionId());
		detail.addFilter("tipo_doc", "STE");
			JIterator<BizLiqDetail> it = detail.getStaticIterator();
			int pos=0;
			int dif=0;
		while (it.hasMoreElements()) {
			BizLiqDetail regLiq = (BizLiqDetail) it.nextElement();
			IConciliable regBSP = obtenerBsp(regLiq);
			if (regBSP!=null)
				dif+=compareDiferencia(regLiq,regBSP);
			else
				faltanteEnBSP(regLiq);
			BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Conciliando boleto "+regLiq.getNroBoleto()+" Diferencias: "+dif, pos++, detail.getStaticItems().size(), false, null);


		}
		buscarBoletosFaltantesEnLiq(this.getFechaDesde(), this.getFechaHasta());

	}

	private IConciliable obtenerBsp(BizLiqDetail liq) throws Exception {
		String boleto = JTools.getNumberEmbedded(liq.getNroBoleto());

		JRecords<BizMexDetalle> detail = new JRecords<BizMexDetalle>(BizMexDetalle.class);
		detail.addFilter("company", getCompany());
		detail.addFilter("boleto", boleto);
	
		JIterator<BizMexDetalle> itc = detail.getStaticIterator();
		if (!itc.hasMoreElements())
			return null;
		boletos.addElement(boleto);
		IConciliable res = (IConciliable) itc.nextElement();
		detail.closeRecord();
		return res;

	}
	public int compareDiferencia(BizLiqDetail regLiq, IConciliable regBsp) throws Exception {
		BizDiferenciaLiq cons = new BizDiferenciaLiq();
		cons.setCompany(getCompany());
		cons.setIdLiq(getLiquidacionId());
		String moneda = regLiq.getStringValue(IConciliable.MONEDA, true);

		cons.setBoleto(regLiq.getNroBoleto());
	
		String tipoRutaLiq = regLiq.getTipoServicio();
		if (tipoRutaLiq.indexOf("LI")!=-1) tipoRutaLiq="I";
		if (tipoRutaLiq.indexOf("LD")!=-1) tipoRutaLiq="D";
 		String tipoRutaBsp = (regBsp.getStringValue(IConciliable.TIPO_RUTA,false));
 		boolean diftipoRuta = (tipoRutaLiq==null && tipoRutaBsp==null)?true:(tipoRutaLiq==null || tipoRutaBsp==null)?false: !(JTools.subStr(tipoRutaLiq,0,3).equals(JTools.subStr(tipoRutaBsp,0,3)));
		cons.setTipoRuta((tipoRutaLiq==null && tipoRutaBsp==null)?"":!diftipoRuta?(tipoRutaLiq!=null?tipoRutaLiq:(tipoRutaBsp!=null?tipoRutaBsp:"")):"Liq: "+(tipoRutaLiq!=null?tipoRutaLiq:"")+" <> BSP: "+(tipoRutaBsp!=null?tipoRutaBsp:""));
			
		String operacionLiq = regLiq.getTipoServ();
		if (regLiq.getRuta().indexOf("CANCELADO")!=-1 || regLiq.getPasajero().indexOf("CANCELADO")!=-1) operacionLiq="CANX";
 		String operacionBsp = (regBsp.getStringValue(IConciliable.OPERACION,false));
 		boolean difoperacion = (operacionLiq==null && operacionBsp==null)?true:(operacionLiq==null || operacionBsp==null)?false: !(JTools.subStr(operacionLiq,0,3).equals(JTools.subStr(operacionBsp,0,3)));
		cons.setOperacion((operacionLiq==null && operacionBsp==null)?"":!difoperacion?(operacionLiq!=null?operacionLiq:(operacionBsp!=null?operacionBsp:"")):"Liq: "+(operacionLiq!=null?operacionLiq:"")+" <> BSP: "+(operacionBsp!=null?operacionBsp:""));
	
		String aerolineaLiq = regLiq.getPrestador();
		BizCarrier carrier = (BizCarrier)carriers.findInHash(regBsp.getStringValue(IConciliable.ID_AEROLINEA,false));
		String aerolineaBsp = carrier==null?regBsp.getStringValue(IConciliable.ID_AEROLINEA,false):""+carrier.getCarrier();
		boolean difAerolinea = (operacionBsp.startsWith("CAN") || (aerolineaLiq==null && aerolineaBsp==null))?false:(aerolineaLiq==null || aerolineaBsp==null)?true: !(aerolineaLiq.equals(aerolineaBsp));
		cons.setIdaerolinea(((aerolineaLiq==null && aerolineaBsp==null)?"":(!difAerolinea?(aerolineaLiq!=null?aerolineaLiq:(aerolineaBsp!=null?aerolineaBsp:"")):"Liq: "+(aerolineaLiq!=null?aerolineaLiq:"")+" <> BSP: "+(aerolineaBsp!=null?aerolineaBsp:""))));
			
		
		Double tarifaLiq = regLiq.getDoubleValue(IConciliable.TARIFA, moneda, true);
		Double tarifaBsp = (regBsp.getDoubleValue(IConciliable.TARIFA, moneda, true));
		Double difTarifa = (operacionBsp.startsWith("CAN") ||(tarifaLiq==null && tarifaBsp==null))?0:(tarifaLiq==null || tarifaBsp==null)?-1: (tarifaLiq.doubleValue()-tarifaBsp.doubleValue());
		cons.setTarifa((tarifaLiq==null && tarifaBsp==null)?"":difTarifa<0.01?(tarifaLiq!=null?""+JTools.rd(tarifaLiq.doubleValue(),2)+"":tarifaBsp!=null?""+JTools.rd(tarifaBsp.doubleValue(),2)+"":""):"Liq: "+JTools.rd(tarifaLiq!=null?tarifaLiq.doubleValue():0f,2)+" <> "+"BSP: "+JTools.rd((tarifaBsp!=null?tarifaBsp.doubleValue():0f),2));
		
		Double contadoLiq = regLiq.getDoubleValue(IConciliable.CONTADO, moneda, true);
		Double contadoBsp = (regBsp.getDoubleValue(IConciliable.CONTADO, moneda, true));
		Double contadoBrutoBsp = (regBsp.getDoubleValue(IConciliable.CONTADO_BRUTO, moneda, true));
		Double difContado = (operacionBsp.startsWith("CAN") ||(contadoLiq==null && contadoBsp==null))?0:(contadoLiq==null || contadoBsp==null)?-1: (contadoLiq.doubleValue()-contadoBsp.doubleValue());
		cons.setContado((contadoLiq==null && contadoBsp==null)?"":difContado<0.01?(contadoLiq!=null?""+JTools.rd(contadoLiq.doubleValue(),2)+"":contadoBsp!=null?""+JTools.rd(contadoBsp.doubleValue(),2)+"":""):"Liq: "+JTools.rd(contadoLiq!=null?contadoLiq.doubleValue():0f,2)+" <> "+"BSP: "+JTools.rd((contadoBsp!=null?contadoBsp.doubleValue():0f),2));
		
		Double tarjetaLiq = regLiq.getDoubleValue(IConciliable.TARJETA, moneda, true);
		Double tarjetaBsp = (regBsp.getDoubleValue(IConciliable.TARJETA, moneda, true));
		Double tarjetaBrutoBsp = (regBsp.getDoubleValue(IConciliable.TARJETA_BRUTO, moneda, true));
		Double difTarjeta = (operacionBsp.startsWith("CAN") ||(tarjetaLiq==null && tarjetaBsp==null))?0:(tarjetaLiq==null || tarjetaBsp==null)?-1: (tarjetaLiq.doubleValue()-tarjetaBsp.doubleValue());
		cons.setCredito((tarjetaLiq==null && tarjetaBsp==null)?"":difTarjeta<0.01?(tarjetaLiq!=null?""+JTools.rd(tarjetaLiq.doubleValue(),2)+"":tarjetaBsp!=null?""+JTools.rd(tarjetaBsp.doubleValue(),2)+"":""):"Liq: "+JTools.rd(tarjetaLiq!=null?tarjetaLiq.doubleValue():0f,2)+" <> "+"BSP: "+JTools.rd((tarjetaBsp!=null?tarjetaBsp.doubleValue():0f),2));

		Double comisionLiq = regLiq.getDoubleValue(IConciliable.COMISION, moneda, true);
		Double comisionBsp = (regBsp.getDoubleValue(IConciliable.COMISION, moneda, true));
		Double difComision = (operacionBsp.startsWith("CAN") ||(comisionLiq==null && comisionBsp==null))?0:(comisionLiq==null || comisionBsp==null)?-1: (comisionLiq.doubleValue()-comisionBsp.doubleValue());
		cons.setComision((comisionLiq==null && comisionBsp==null)?"":difComision<1?(comisionLiq!=null?""+JTools.rd(comisionLiq.doubleValue(),2)+"":comisionBsp!=null?""+JTools.rd(comisionBsp.doubleValue(),2)+"":""):"Liq: "+JTools.rd(comisionLiq!=null?comisionLiq.doubleValue():0f,2)+" <> "+"BSP: "+JTools.rd((comisionBsp!=null?comisionBsp.doubleValue():0f),2));

  	Double impuestoLiq = regLiq.getDoubleValue(IConciliable.IMPUESTO_ACUM, moneda, true);
		Double impuestoBsp = (regBsp.getDoubleValue(IConciliable.IMPUESTO_ACUM, moneda, true));
		Double difImpuesto = (operacionBsp.startsWith("CAN") ||(impuestoLiq==null && impuestoBsp==null))?0:(impuestoLiq==null || impuestoBsp==null)?-1: (impuestoLiq.doubleValue()-impuestoBsp.doubleValue());
		cons.setImpuesto((impuestoLiq==null && impuestoBsp==null)?"":difImpuesto<0.01?(impuestoLiq!=null?""+JTools.rd(impuestoLiq.doubleValue(),2)+"":impuestoBsp!=null?""+JTools.rd(impuestoBsp.doubleValue(),2)+"":""):"Liq: "+JTools.rd(impuestoLiq!=null?impuestoLiq.doubleValue():0f,2)+" <> "+"BSP: "+JTools.rd((impuestoBsp!=null?impuestoBsp.doubleValue():0f),2));
		
  	Double totalLiq = regLiq.getDoubleValue(IConciliable.TOTAL, moneda, true);
		Double totalBsp = (regBsp.getDoubleValue(IConciliable.TOTAL, moneda, true));
		Double difTotal = (operacionBsp.startsWith("CAN") ||(totalLiq==null && totalBsp==null))?0:(totalLiq==null || totalBsp==null)?-1: (totalLiq.doubleValue()-totalBsp.doubleValue());
		cons.setTotal((totalLiq==null && totalBsp==null)?"":difTotal<0.01?(totalLiq!=null?""+JTools.rd(totalLiq.doubleValue(),2)+"":totalBsp!=null?""+JTools.rd(totalBsp.doubleValue(),2)+"":""):"Liq: "+JTools.rd(totalLiq!=null?totalLiq.doubleValue():0f,2)+" <> "+"BSP: "+JTools.rd((totalBsp!=null?totalBsp.doubleValue():0f),2));
		
		String formapagoLiq = (regLiq.getStringValue(IConciliable.TIPO_TARJETA,false));
		if (formapagoLiq!=null && formapagoLiq.indexOf("CC")!=-1) formapagoLiq=formapagoLiq.replaceAll("CC", "");
		if (formapagoLiq!=null && formapagoLiq.indexOf("TR")!=-1) formapagoLiq="CA";
		if (formapagoLiq!=null && formapagoLiq.indexOf("NI")!=-1) formapagoLiq="CA";
		if (formapagoLiq!=null && formapagoLiq.indexOf("CH")!=-1) formapagoLiq="CA";
 		String formapagoBsp = (regBsp.getStringValue(IConciliable.TIPO_TARJETA,false));
 		if (formapagoBsp==null || formapagoBsp.trim().equals(""))  formapagoBsp=contadoBrutoBsp.doubleValue()>0||(contadoBrutoBsp.doubleValue()==0&&tarjetaBrutoBsp.doubleValue()==0)?"CA":"";
 		boolean difFormapago = (operacionBsp.startsWith("CAN") ||(formapagoLiq==null && formapagoBsp==null))?false:(formapagoLiq==null || formapagoBsp==null)?true: !(formapagoLiq.equals(formapagoBsp));
		cons.setFormaPago((formapagoLiq==null && formapagoBsp==null)?"":!difFormapago?(formapagoLiq!=null?formapagoLiq:(formapagoBsp!=null?formapagoBsp:"")):"Liq: "+(formapagoLiq!=null?formapagoLiq:"")+" <> "+(formapagoBsp!=null?formapagoBsp:""));

		
		boolean hayDiferencia = diftipoRuta || difAerolinea || difFormapago  || difTarifa > 0.1  || difContado > 0.1  || difComision > 1  || difImpuesto > .1  || difImpuesto > .1;
		cons.setStatus(hayDiferencia ? IConsolidador.DISTINCT : IConsolidador.OK);
		
		cons.processInsert();
		return hayDiferencia?1:0;
	}

	public void buscarBoletosFaltantesEnLiq(Date fechaDesde, Date fechaHasta) throws Exception {
			JRecords<BizMexDetalle> details = new JRecords<BizMexDetalle>(BizMexDetalle.class);
			details.addFilter("company", getCompany());
			details.addFilter("fecha", fechaDesde, ">=");
			details.addFilter("fecha", fechaHasta, "<=");
		   if (!strClientes.isEmpty())
		  	 details.addFixedFilter("where dk in ( "+strClientes+") ");
		   if (!strClientesNegativa.isEmpty())
		  	 details.addFixedFilter("where dk not in ( "+strClientesNegativa+") ");
			details.readAll();
			while (details.nextRecord()) {
				BizMexDetalle conc =  details.getRecord();
				String boleto = conc.getStringValue(IConciliable.BOLETOS, true);
				if (boletos.containsElement(boleto)) continue;
				BizDiferenciaLiq cons = new BizDiferenciaLiq();
				cons.setCompany(getCompany());
				cons.setIdLiq(getLiquidacionId());
					
				cons.setStatus( IConsolidador.ONLY_BSP);
				cons.setBoleto(boleto);
				cons.setTipoRuta(conc.getSTAT());
				cons.setIdaerolinea(conc.getCarrier());
				cons.setOperacion(conc.getTRNC());

				cons.setTarifa(""+JTools.rd(conc.getDoubleValue(IConciliable.TARIFA, null, true),2));
				cons.setContado(""+JTools.rd(conc.getDoubleValue(IConciliable.CONTADO, null, true),2));
				cons.setCredito(""+JTools.rd(conc.getDoubleValue(IConciliable.TARJETA, null, true),2));
				cons.setComision(""+JTools.rd(conc.getDoubleValue(IConciliable.COMISION, null, true),2));
				cons.setImpuesto(""+JTools.rd(conc.getDoubleValue(IConciliable.IMPUESTO_ACUM, null, true),2));
				cons.setTotal(""+JTools.rd(conc.getDoubleValue(IConciliable.TOTAL, null, true),2));
				cons.setFormaPago(""+conc.getStringValue(IConciliable.TIPO_TARJETA, true));
				
				cons.processInsert();
			}			


	}
	
	public void faltanteEnBSP(BizLiqDetail det) throws Exception {
			BizDiferenciaLiq cons = new BizDiferenciaLiq();
			cons.setCompany(getCompany());
			cons.setIdLiq(getLiquidacionId());
				
			cons.setStatus( IConsolidador.ONLY_LIQ);
			cons.setBoleto(det.getNroBoleto());
			cons.setTipoRuta(det.getTipoServicio());
			cons.setIdaerolinea(det.getPrestador());
			cons.setOperacion(det.getTipoServ());
		
			cons.setTarifa(""+JTools.rd(det.getDoubleValue(IConciliable.TARIFA, null, true),2));
			cons.setContado(""+JTools.rd(det.getDoubleValue(IConciliable.CONTADO, null, true),2));
			cons.setCredito(""+JTools.rd(det.getDoubleValue(IConciliable.TARJETA, null, true),2));
			cons.setComision(""+JTools.rd(det.getDoubleValue(IConciliable.COMISION, null, true),2));
			cons.setImpuesto(""+JTools.rd(det.getDoubleValue(IConciliable.IMPUESTO_ACUM, null, true),2));
			cons.setTotal(""+JTools.rd(det.getDoubleValue(IConciliable.TOTAL, null, true),2));
			if (det.getStringValue(IConciliable.TIPO_TARJETA, true).equals(""))
				cons.setFormaPago(det.getDoubleValue(IConciliable.CONTADO_BRUTO, null, true)>0?"CA":"");
			else
				cons.setFormaPago(""+det.getStringValue(IConciliable.TIPO_TARJETA, true));
			
			cons.processInsert();
		}			





	
}
