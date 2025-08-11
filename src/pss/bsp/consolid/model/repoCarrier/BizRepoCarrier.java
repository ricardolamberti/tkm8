package pss.bsp.consolid.model.repoCarrier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import pss.bsp.consolid.model.liquidacion.detail.BizLiqDetail;
import pss.bsp.consolid.model.liquidacion.header.BizLiqHeader;
import pss.bsp.consolid.model.repoCarrier.detail.BizRepoCarrierDetail;
import pss.bsp.consolid.model.repoCarrier.detail.GuiRepoCarrierDetails;
import pss.bsp.consolid.model.repoCarrier.detailMonth.BizRepoCarrierDetailMonth;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.pdf.BizPDF;
import pss.bsp.pdf.mex.detalle.BizMexDetalle;
import pss.common.customList.config.relation.JRelations;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.JExec;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JMultiple;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JList;
import pss.tourism.carrier.BizCarrier;
import pss.tourism.interfaceGDS.Tools;

public class BizRepoCarrier extends JRecord {

  // -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JLong pId = new JLong();
	private JString pCompany = new JString();
	private JDate pDateFrom = new JDate();
	private JDate pDateTo = new JDate();
	private JMultiple pOrganizaciones = new JMultiple();
	private JMultiple pContratos = new JMultiple();
	private JMultiple pMeses = new JMultiple();
	public void setMeses(JList<String> id) throws Exception {
		pMeses.setValue(id);
	}
	public JList<String> getMeses() throws Exception {
		return pMeses.getValue();
	}
	public void setContratos(JList<String> id) throws Exception {
		pContratos.setValue(id);
	}
	public JList<String> getContratos() throws Exception {
		return pContratos.getValue();
	}
	public void setOrganizaciones(JList<String> id) throws Exception {
		pOrganizaciones.setValue(id);
	}
	public JList<String> getOrganizaciones() throws Exception {
		return pOrganizaciones.getValue();
	}
	public void setId(long id) throws Exception {
		pId.setValue(id);
	}
	public long getId() throws Exception {
		return pId.getValue();
	}
	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public void setCompany(String company) throws Exception {
		pCompany.setValue(company);
	}

	

	public java.util.Date getDateFrom() throws Exception {
		return pDateFrom.getValue();
	}

	public void setDateFrom(java.util.Date dateFrom) throws Exception {
		pDateFrom.setValue(dateFrom);
	}

	public java.util.Date getDateTo() throws Exception {
		return pDateTo.getValue();
	}

	public void setDateTo(java.util.Date dateTo) throws Exception {
		pDateTo.setValue(dateTo);
	}

	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizRepoCarrier() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("id", pId);
		addItem("company", pCompany);
		addItem("date_from", pDateFrom);
		addItem("date_to", pDateTo);
		addItem("organizaciones", pOrganizaciones);
		addItem("contratos", pContratos);
		addItem("meses", pMeses);
		
	}

	@Override
	public void createFixedProperties() throws Exception {

		addFixedItem(KEY, "id", "id", false, false, 64);
		addFixedItem(FIELD, "company", "Company", true, false, 100);
		addFixedItem(FIELD, "date_from", "Fecha desde", true, false, 25);
		addFixedItem(FIELD, "date_to", "Fecha hasta", true, false, 25);
		addFixedItem(FIELD, "organizaciones", "Organizacioness", true, false, 2000);
		addFixedItem(FIELD, "contratos", "Contratos", true, false, 2000);
		addFixedItem(FIELD, "meses", "Meses", true, false, 2000);


	}


	@Override
	public String GetTable() {
		return "BSP_REPO_CARRIER";
	}

  
	@Override
	public void processUpdate() throws Exception {
		super.processUpdate();
	}
	
	@Override
	public void processDelete() throws Exception {
		processClean();
		super.processDelete();
	}

	@Override
	public void processInsert() throws Exception {
		super.processInsert();
		this.setId(getIdentity("id"));
		processGenerate();
	}
	
	public void execProcessGenerate() throws Exception {
		JExec oExec = new JExec(this, "processGenerate") {

			@Override
			public void Do() throws Exception {
				processGenerate();
			}
		};
		oExec.execute();
	}
	public void processClean() throws Exception {

		BizRepoCarrierDetailMonth detClean2  = new BizRepoCarrierDetailMonth();
		detClean2.addFilter("company", getCompany());
		detClean2.addFilter("repo_dk_id", getId());
		detClean2.deleteMultiple();

	
		BizRepoCarrierDetail detClean  = new BizRepoCarrierDetail();
		detClean.addFilter("company", getCompany());
		detClean.addFilter("repo_dk_id", getId());
		detClean.deleteMultiple();
	}
	public void processGenerate() throws Exception {
		processClean();
		new BizMexDetalle().completeDK();
		new BizPDF().procPendientes(getDateFrom(),getDateTo());

		JRecords<BizCarrier> carriers = new JRecords<BizCarrier>(BizCarrier.class);
		carriers.addFilter("cod_iata", "null","<>");
		carriers.convertToHash("cod_iata");
	
		HashMap<String, BizRepoCarrierDetail> mapCarriers = new HashMap<String, BizRepoCarrierDetail>();
		HashMap<String, BizRepoCarrierDetailMonth> mapCarrierMensual = new HashMap<String, BizRepoCarrierDetailMonth>();
			int pos=0;
		JList<String> meses = JCollectionFactory.createList();
		BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Analizando BSPs", pos++, 1000, false, null);
		

		JBaseRegistro regs=JBaseRegistro.recordsetFactory();
		String sql="select TACN,AGTN,fecha,company,STAT,TRNC,count(*) as cantidad,sum(COBL) as COBL,sum(EFCO) as EFCO,sum(CASH)+sum(CRED) as total ";
		sql+=" FROM BSP_PDF_MEX_DETALLE ";
		sql+=" WHERE company='"+getCompany()+"' AND tacn is not null AND trnc <> 'SPDR' AND ";
//		sql+="  		 periodo>='"+JDateTools.buildPeriod(getDateFrom())+"' AND ";
//		sql+="  		 periodo<='"+JDateTools.buildPeriod(getDateTo())+"' ";
		sql+="  		 fecha>='"+(getDateFrom())+"' AND ";
		sql+="  		 fecha<='"+(getDateTo())+"' ";
		sql+=" GROUP BY company,TACN,AGTN,TRNC,STAT,fecha";
		regs.ExecuteQuery(sql);
		while (regs.next()) {
//			regs.CampoAsStr("company");
			BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Analizando BSPs para Carrier: "+regs.CampoAsStr("TACN"), pos++, 1000, false, null);
					
			if (regs.CampoAsStr("TACN").equals("911") ||
					regs.CampoAsStr("TACN").equals("954") ||
					regs.CampoAsStr("TACN").equals("952") ||
					regs.CampoAsStr("TACN").equals("799")) continue;
			BizCarrier carrier = (BizCarrier)carriers.findInHash("cod_iata", ""+JTools.getLongFirstNumberEmbedded(regs.CampoAsStr("TACN")));
			String TACN = carrier==null?"DESCONOCIDO#"+regs.CampoAsStr("TACN"):carrier.getCarrier();
			String keyCarriersMens = TACN+"_"+JDateTools.DateToString(JDateTools.getFirstDayOfMonth(regs.CampoAsDate("fecha")),"yyyymmdd");
			BizRepoCarrierDetail detCarrier = mapCarriers.get(TACN);
			if ( detCarrier==null) {
				detCarrier = new BizRepoCarrierDetail();
				detCarrier.setRepoCarrierId(getId());
				detCarrier.setCompany(getCompany());
				detCarrier.setCarrier(TACN);
				if (!TACN.startsWith("DESCONOCIDO") && detCarrier.getObjCarrier()!=null) {
					detCarrier.setAerolinea(detCarrier.getObjCarrier().getDescription());
					detCarrier.setCodAerolinea(detCarrier.getObjCarrier().getCodIata());
				}
				mapCarriers.put(TACN, detCarrier);

			}
			BizRepoCarrierDetailMonth detCarrierMonth = mapCarrierMensual.get(keyCarriersMens);
			if ( detCarrierMonth==null) {
				detCarrierMonth = new BizRepoCarrierDetailMonth();
				detCarrierMonth.setRepoCarrierId(getId());
				detCarrierMonth.setCompany(getCompany());
				String mes=JDateTools.getMonthDescr(JDateTools.getFirstDayOfMonth(regs.CampoAsDate("fecha")));
//				detCarrierMonth.setPeriodDesde(JDateTools.buildPeriod(JDateTools.getInicioPeriodo(regs.CampoAsStr("periodo"))));
//				detCarrierMonth.setPeriodHasta(JDateTools.buildPeriod(JDateTools.getFinPeriodo(regs.CampoAsStr("periodo"))));
				detCarrierMonth.setPeriodDesde(JDateTools.DateToString(JDateTools.getFirstDayOfMonth(regs.CampoAsDate("fecha")),"yyyymmdd"));
				detCarrierMonth.setPeriodHasta(JDateTools.DateToString(JDateTools.getDateEndDay(regs.CampoAsDate("fecha")),"yyyymmdd"));
				detCarrierMonth.setMes(mes);
				mapCarrierMensual.put(keyCarriersMens, detCarrierMonth);
				if (!meses.containsElement(mes))
						meses.addElement(mes);

			}
		
			
			detCarrier.setVentaGlobalNeto(detCarrier.getVentaGlobalNeto()+regs.CampoAsFloat("COBL"));
			detCarrier.setVentaGlobalTotal(detCarrier.getVentaGlobalTotal()+regs.CampoAsFloat("total"));
				
			String trnc = regs.CampoAsStr("TRNC");
			if (trnc.equals("TKTT") || trnc.startsWith("EMD")) {
				detCarrierMonth.setBoletosTkts(detCarrierMonth.getBoletosTkts()+(trnc.equals("TKTT")?regs.CampoAsInt("cantidad"):0));
				detCarrierMonth.setBoletosEmds(detCarrierMonth.getBoletosEmds()+(trnc.startsWith("EMD")?regs.CampoAsInt("cantidad"):0));
				detCarrierMonth.setBoletosTotal(detCarrierMonth.getBoletosTotal()+regs.CampoAsInt("cantidad"));
				detCarrierMonth.setVentaBspNeto(detCarrierMonth.getVentaBspNeto()+regs.CampoAsFloat("COBL"));
				detCarrierMonth.setVentaBspTotal(detCarrierMonth.getVentaBspTotal()+regs.CampoAsFloat("total"));
				detCarrierMonth.setComision(detCarrierMonth.getComision()+regs.CampoAsFloat("EFCO"));
      	detCarrier.setTtlIngresos(detCarrier.getTtlIngresos()+regs.CampoAsFloat("EFCO"));
			
			} else if (trnc.equals("RFND")) {
				detCarrierMonth.setVentaBspRfnd(detCarrierMonth.getVentaBspRfnd()+regs.CampoAsFloat("total"));
			
			} else if (trnc.startsWith("ADM")) {
				detCarrierMonth.setVentaBspAdma(detCarrierMonth.getVentaBspAdma()+regs.CampoAsFloat("total"));
			
			}

			

			
		}
		regs.close();
		BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Analizando RFND", pos++, 1000, false, null);
	
		
		JRecords<BizLiqDetail> detLiqs = new JRecords<BizLiqDetail>(BizLiqDetail.class);
		detLiqs.addJoin(JRelations.JOIN,"bsp_liquidation_acum","bsp_liquidation_acum.company=bsp_liquidation_detail.company and bsp_liquidation_acum.liquidacion_id=bsp_liquidation_detail.liquidacion_id");
		detLiqs.addFilter("bsp_liquidation_acum","estado", BizLiqHeader.PUBLICADO,"=");
		detLiqs.addFilter("company", getCompany());
		detLiqs.addFilter("tipo_doc", "RFN");
//		detLiqs.addFilter("fecha_doc",JDateTools.getInicioPeriodo(JDateTools.buildPeriod(getDateFrom())),">=");
//		detLiqs.addFilter("fecha_doc",JDateTools.getFinPeriodo(JDateTools.buildPeriod(getDateTo())),"<=");
		detLiqs.addFilter("fecha_doc",getDateFrom(),">=");
		detLiqs.addFilter("fecha_doc",getDateTo(),"<=");
		detLiqs.readAll();
		while (detLiqs.nextRecord()) {
			BizLiqDetail det = detLiqs.getRecord();
			if (!det.getTipoDoc().equals("RFN")) continue;
			String TACN=det.getPrestador();
			if (Tools.isOnlyNumbers(TACN,false)) {
				BizCarrier carrier = (BizCarrier)carriers.findInHash("cod_iata", ""+JTools.getLongFirstNumberEmbedded(TACN));
				if (carrier!=null)
					TACN=carrier.getCarrier();
			}

			BizRepoCarrierDetail detCarrier = mapCarriers.get(TACN);
			if ( detCarrier==null) {
				detCarrier = new BizRepoCarrierDetail();
				detCarrier.setRepoCarrierId(getId());
				detCarrier.setCompany(getCompany());
				detCarrier.setCarrier(TACN);
				if (!TACN.startsWith("DESCONOCIDO") && detCarrier.getObjCarrier()!=null) {
					detCarrier.setAerolinea(detCarrier.getObjCarrier().getDescription());
					detCarrier.setCodAerolinea(detCarrier.getObjCarrier().getCodIata());
				}
				mapCarriers.put(TACN, detCarrier);
			}

			detCarrier.setReembolsos(det.getImporte());
		}
		detLiqs.closeRecord();
		

		BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Analizando Contratos", pos++, 1000, false, null);
		
		regs=JBaseRegistro.recordsetFactory();
		sql="SELECT ";
		sql+=" TUR_PNR_BOLETO.CodigoAerolinea as carrier,  ";
		sql+=" TUR_PNR_BOLETO.numeroboleto as numeroboleto,  ";
		sql+=" TUR_PNR_BOLETO.contratos_back as contratos_back,  ";
		sql+=" TUR_PNR_BOLETO.tarifa_factura_local as tarifa_factura_local  ";
		sql+=" FROM  ";
		sql+=" TUR_PNR_BOLETO  ";
		sql+=" JOIN BSP_LIQUIDATION_DETAIL ON TUR_PNR_BOLETO.COMPANY = BSP_LIQUIDATION_DETAIL.COMPANY  ";
		sql+=" AND TUR_PNR_BOLETO.NUMEROBOLETO = BSP_LIQUIDATION_DETAIL.NRO_BOLETO  ";
		sql+=" WHERE  ";
		sql+=" TUR_PNR_BOLETO.COMPANY = '"+getCompany()+"'  ";
//		sql+=" AND TUR_PNR_BOLETO.CREATION_DATE_DATE >= '"+JDateTools.DateToString(JDateTools.getInicioPeriodo(JDateTools.buildPeriod(getDateFrom())),"dd/MM/yyyy")+"'  ";
//		sql+=" AND TUR_PNR_BOLETO.CREATION_DATE_DATE <= '"+JDateTools.DateToString(JDateTools.getFinPeriodo(JDateTools.buildPeriod(getDateTo())),"dd/MM/yyyy")+"'  ";
		sql+=" AND TUR_PNR_BOLETO.CREATION_DATE_DATE >= '"+JDateTools.DateToString(getDateFrom(),"dd/MM/yyyy")+"'  ";
		sql+=" AND TUR_PNR_BOLETO.CREATION_DATE_DATE <= '"+JDateTools.DateToString(getDateTo(),"dd/MM/yyyy")+"'  ";
		sql+=" AND TUR_PNR_BOLETO.VOID = 'N'  ";
		sql+=" AND (TUR_PNR_BOLETO.REFUND is null or TUR_PNR_BOLETO.REFUND = 'N' ) ";
		
		regs.ExecuteQuery(sql);
		while (regs.next()) {
//			regs.CampoAsStr("company");
			//BizPNRTicket det = detTkts.getRecord();}
			if (regs.CampoAsStr("contratos_back")==null) continue;
			List<String> contratos = Arrays.asList(regs.CampoAsStr("contratos_back").split(","));
			double porc=0;
			double prem=0;
			String descrip="";
			BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Analizando Contratos boleto:"+ regs.CampoAsStr("numeroboleto"), pos++, 1000, false, null);
			for (String idContrato:contratos) {
				if (idContrato.equals("")) continue;
				BizDetalle detalle = getObjContrato(Long.parseLong(idContrato));
				if (detalle==null) continue;
				descrip=regs.CampoAsStr("carrier");
				
				double porcentaje = detalle.getCalculePorcPorBoleto(regs.CampoAsFloat("tarifa_factura_local"));
				if (porcentaje==0) continue;
				String TACN= regs.CampoAsStr("carrier");
				if (TACN.indexOf("16")!=-1)
					PssLogger.logInfo("logpoint");
				if (TACN.equals(""))continue;
				BizRepoCarrierDetail detCarrier = mapCarriers.get(TACN);
				if ( detCarrier==null) {
					detCarrier = new BizRepoCarrierDetail();
					detCarrier.setRepoCarrierId(getId());
					detCarrier.setCompany(getCompany());
					detCarrier.setCarrier(TACN);
					if (!TACN.startsWith("DESCONOCIDO") && detCarrier.getObjCarrier()!=null) {
						detCarrier.setAerolinea(detCarrier.getObjCarrier().getDescription());
						detCarrier.setCodAerolinea(detCarrier.getObjCarrier().getCodIata());
					}
					mapCarriers.put(TACN, detCarrier);
				}
				
			
				prem+= detalle.getCalculeGananciaPorBoleto(regs.CampoAsFloat("tarifa_factura_local"),porcentaje);
				detCarrier.setComision(detCarrier.getComision()+prem);
				detCarrier.setTotal(detCarrier.getTotal()+prem);
				detCarrier.setTtlIngresos(detCarrier.getTtlIngresos()+prem);
			}
		}
		
		for (String aDK:mapCarriers.keySet()) {
			BizRepoCarrierDetail det = mapCarriers.get(aDK);
			det.processInsert();
		}
		for (String aDKMens:mapCarrierMensual.keySet()) {
			BizRepoCarrierDetailMonth detMens = mapCarrierMensual.get(aDKMens);
			String aDK=aDKMens.substring(0,aDKMens.indexOf("_"));
			PssLogger.logInfo("Carrier: "+aDK);
			BizRepoCarrierDetail det = mapCarriers.get(aDK);
			
			detMens.setRepoCarrierDetailId(det.getId());
			detMens.setRepoCarrierId(getId());
			detMens.processInsert();
			
		}

		
		
		for (String aDK:mapCarriers.keySet()) {
			BizRepoCarrierDetail det = mapCarriers.get(aDK);
			det.processUpdate();
		}
		pMeses.setValue(meses);
		processUpdate();
	}
	public BizDetalle getObjContrato(long idContrato) throws Exception {
		BizDetalle det = new BizDetalle();
		det.dontThrowException(true);
		if (!det.read(idContrato)) return null;
		return det;
	}

  public boolean read( long zId ) throws Exception { 
    addFilter( "id",  zId ); 
    return read(); 
  }

	public String getInterfaz() throws Exception {
//		JRecords<BizOrganization> orgs = new JRecords<BizOrganization>(BizOrganization.class);
//		orgs.addFilter("company", getCompany());
//		JIterator<BizOrganization> it = orgs.getStaticIterator();
//		List<SimpleEntry<String, byte[]>> hojas = new ArrayList();
//		while (it.hasMoreElements()) {
//			BizOrganization org = it.nextElement();
//			GuiRfndDetails details = new GuiRfndDetails();
//			details.addFilterAdHoc("company", getCompany());
//			details.addFilterAdHoc("organization", org.getCodeOrganization());
//			details.getRecords().addFilter("repo_dk_id", getId());
//			byte[] excel1 = details.toExcelBytes();
//			hojas.add(new SimpleEntry<>(org.getCodeOrganization(),excel1));
//		}
//		byte[] mergedExcel = JWinsExcel.mergeExcels(hojas);
//
//		String outputPath = "interfaz.xlsx";
//		try (FileOutputStream out = new FileOutputStream(outputPath)) {
//			out.write(mergedExcel);
//		}
		GuiRepoCarrierDetails dets = new GuiRepoCarrierDetails();
		dets.getRecords().addFilter("company", getCompany());
		dets.getRecords().addFilter("repo_dk_id", getId());
		dets.SetVision(""+ getId());
  	

		return dets.toExcel();
	}
}
