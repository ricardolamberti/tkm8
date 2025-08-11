package pss.bsp.consolid.model.repoDK;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import pss.bsp.consolid.model.gruposDK.BizGrupoDK;
import pss.bsp.consolid.model.gruposDK.detail.BizGrupoDKDetail;
import pss.bsp.consolid.model.liquidacion.detail.BizLiqDetail;
import pss.bsp.consolid.model.liquidacion.header.BizLiqHeader;
import pss.bsp.consolid.model.repoDK.detail.BizRepoDKDetail;
import pss.bsp.consolid.model.repoDK.detail.GuiRepoDKDetails;
import pss.bsp.consolid.model.repoDK.detailContrato.BizRepoDKDetailContrato;
import pss.bsp.consolid.model.repoDK.detailMonth.BizRepoDKDetailMonth;
import pss.bsp.consolid.model.repoDK.detailOrg.BizRepoDKDetailOrg;
import pss.bsp.consolid.model.trxOra.BizTrxOra;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.organization.BizOrganization;
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
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.tourism.carrier.BizCarrier;

public class BizRepoDK extends JRecord {

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
	public BizRepoDK() throws Exception {
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
		return "BSP_REPO_DK";
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
		BizRepoDKDetailContrato detClean3  = new BizRepoDKDetailContrato();
		detClean3.addFilter("company", getCompany());
		detClean3.addFilter("repo_dk_id", getId());
		detClean3.deleteMultiple();

		BizRepoDKDetailMonth detClean2  = new BizRepoDKDetailMonth();
		detClean2.addFilter("company", getCompany());
		detClean2.addFilter("repo_dk_id", getId());
		detClean2.deleteMultiple();

		BizRepoDKDetailOrg detClean4  = new BizRepoDKDetailOrg();
		detClean4.addFilter("company", getCompany());
		detClean4.addFilter("repo_dk_id", getId());
		detClean4.deleteMultiple();

		BizRepoDKDetail detClean  = new BizRepoDKDetail();
		detClean.addFilter("company", getCompany());
		detClean.addFilter("repo_dk_id", getId());
		detClean.deleteMultiple();
	}
	
	public HashMap<String,String> getGrupos() throws Exception {
		HashMap<String,String> hash = new HashMap<String,String>();
		JRecords<BizGrupoDK> agrups = new JRecords<BizGrupoDK>(BizGrupoDK.class);
		agrups.addFilter("use_repo_dk", true);
		JIterator<BizGrupoDK> itg = agrups.getStaticIterator();
		while (itg.hasMoreElements()) {
			BizGrupoDK grupo = itg.nextElement();
			JRecords<BizGrupoDKDetail> agrupsDet = new JRecords<BizGrupoDKDetail>(BizGrupoDKDetail.class);
			agrupsDet.addFilter("id_grupo", grupo.getIdGrupo());
			agrupsDet.addFilter("company", grupo.getCompany());
			JIterator<BizGrupoDKDetail> itd = agrupsDet.getStaticIterator();
			while (itd.hasMoreElements()) {
				BizGrupoDKDetail detail = itd.nextElement();
				hash.put(detail.getDK(), grupo.getIdentificador());
			}
		}
		return hash;
	}
	
	public void processGenerate() throws Exception {
		processClean();
		new BizMexDetalle().completeDK();
		new BizPDF().procPendientes(getDateFrom(),getDateTo());
		HashMap<String,String> grupos=getGrupos();
		JRecords<BizCarrier> carriers = new JRecords<BizCarrier>(BizCarrier.class);
		carriers.addFilter("cod_iata", "null","<>");
		carriers.convertToHash("cod_iata");
	
		HashMap<String, BizRepoDKDetail> dks = new HashMap<String, BizRepoDKDetail>();
		HashMap<String, BizRepoDKDetailMonth> dksMens = new HashMap<String, BizRepoDKDetailMonth>();
		HashMap<String, BizRepoDKDetailOrg> dksOrgs = new HashMap<String, BizRepoDKDetailOrg>();
		HashMap<String, BizRepoDKDetailContrato> dksContratos = new HashMap<String, BizRepoDKDetailContrato>();
		int pos=0;
		JList<String> orgs = JCollectionFactory.createList();
		JList<String> contrs = JCollectionFactory.createList();
		JList<String> meses = JCollectionFactory.createList();
		BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Analizando BSPs", pos++, 1000, false, null);
		

		JBaseRegistro regs=JBaseRegistro.recordsetFactory();
		String sql="select TACN,AGTN,fecha,company,STAT,dk,TRNC,count(*) as cantidad,sum(COBL) as COBL,sum(EFCO) as EFCO,sum(CASH)+sum(CRED) as total ";
		sql+=" FROM BSP_PDF_MEX_DETALLE ";
		sql+=" WHERE company='"+getCompany()+"' AND tacn is not null AND ";
//		sql+="  		 periodo>='"+JDateTools.buildPeriod(getDateFrom())+"' AND ";
//		sql+="  		 periodo<='"+JDateTools.buildPeriod(getDateTo())+"' ";
		sql+="  		 fecha>='"+(getDateFrom())+"' AND ";
		sql+="  		 fecha<='"+(getDateTo())+"' AND ";
		sql+="  		 TRNC in ('TKTT','EMD') ";
		
		sql+=" GROUP BY company,TACN,AGTN,TRNC,STAT,fecha,dk";
		regs.ExecuteQuery(sql);
		while (regs.next()) {
//			regs.CampoAsStr("company");
			BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Analizando BSPs para DK: "+regs.CampoAsStr("dk"), pos++, 1000, false, null);
			
			BizCarrier carrier = (BizCarrier)carriers.findInHash("cod_iata", ""+JTools.getLongFirstNumberEmbedded(regs.CampoAsStr("TACN")));
			String DK = regs.CampoAsStr("dk")==null?"DESCONOCIDO":regs.CampoAsStr("dk");
			String grupo = grupos.get(DK);
			if (grupo!=null) {
				DK = grupo;
			}
			String organization = BizOrganization.readByIata(getCompany(), regs.CampoAsStr("AGTN"));
			//String keyDksMens = DK+"_"+JDateTools.buildPeriod(JDateTools.getInicioPeriodo(regs.CampoAsStr("periodo")));
			String keyDksMens = DK+"_"+JDateTools.DateToString(JDateTools.getFirstDayOfMonth(regs.CampoAsDate("fecha")),"yyyyMMdd");
			String keyDksOrgs = DK+"_"+(organization==null?"DESCONOCIDO":organization);
			BizRepoDKDetail detDK = dks.get(DK);
			if ( detDK==null) {
				detDK = new BizRepoDKDetail();
				detDK.setRepoDKId(getId());
				detDK.setCompany(getCompany());
				detDK.setDk(DK);
				if (!DK.startsWith("DESCONOCIDO") && detDK.getObjClienteDK()!=null) {
					detDK.setRazonSocial(detDK.getObjClienteDK().getDescripcion());
				}
				dks.put(DK, detDK);

			}
			BizRepoDKDetailMonth detDKMonth = dksMens.get(keyDksMens);
			if ( detDKMonth==null) {
				detDKMonth = new BizRepoDKDetailMonth();
				detDKMonth.setRepoDKId(getId());
				detDKMonth.setCompany(getCompany());
			//	String mes=JDateTools.getMonthDescr(JDateTools.getFinPeriodo(regs.CampoAsStr("periodo")));
				String mes=JDateTools.getMonthDescr(JDateTools.getFirstDayOfMonth(regs.CampoAsDate("fecha")));
//				detDKMonth.setPeriodDesde(JDateTools.buildPeriod(JDateTools.getInicioPeriodo(regs.CampoAsStr("periodo"))));
//				detDKMonth.setPeriodHasta(JDateTools.buildPeriod(JDateTools.getFinPeriodo(regs.CampoAsStr("periodo"))));
				detDKMonth.setPeriodDesde(JDateTools.DateToString(JDateTools.getFirstDayOfMonth(regs.CampoAsDate("fecha")),"yyyymmdd"));
				detDKMonth.setPeriodHasta(JDateTools.DateToString(JDateTools.getDateEndDay(regs.CampoAsDate("fecha")),"yyyymmdd"));
				detDKMonth.setMes(mes);
				dksMens.put(keyDksMens, detDKMonth);
				if (!meses.containsElement(mes))
						meses.addElement(mes);

			}
			BizRepoDKDetailOrg detDKOrg = dksOrgs.get(keyDksOrgs);
			if ( detDKOrg==null) {
				detDKOrg = new BizRepoDKDetailOrg();
				detDKOrg.setRepoDKId(getId());
				detDKOrg.setCompany(getCompany());
				detDKOrg.setOrg(organization);
				dksOrgs.put(keyDksOrgs, detDKOrg);
				if (!orgs.containsElement(organization))
					orgs.addElement(organization);
			}
			
			
			detDK.setVentaGlobalNeto(detDK.getVentaGlobalNeto()+regs.CampoAsFloat("COBL"));
			detDK.setVentaGlobalTotal(detDK.getVentaGlobalTotal()+regs.CampoAsFloat("total"));
				
			String trnc = regs.CampoAsStr("TRNC");
			if (trnc.equals("TKTT") || trnc.startsWith("EMD")) {
//				if (carrier !=null && (carrier.getCarrier().equals("VB") ||carrier.getCarrier().equals("VL"))) {
//					detDKMonth.setBoletosBajoCosto(detDKMonth.getBoletosBajoCosto()+1);
//				} else {
					detDKMonth.setBoletosTkts(detDKMonth.getBoletosTkts()+(trnc.equals("TKTT")?regs.CampoAsInt("cantidad"):0));
					detDKMonth.setBoletosEmds(detDKMonth.getBoletosEmds()+(trnc.startsWith("EMD")?regs.CampoAsInt("cantidad"):0));
//				}
				detDKMonth.setBoletosTotal(detDKMonth.getBoletosTotal()+regs.CampoAsInt("cantidad"));
//				if (carrier !=null && (carrier.getCarrier().equals("VB") ||carrier.getCarrier().equals("VL"))) {
//					detDKMonth.setBajoCostoNeto(detDKMonth.getBajoCostoNeto()+regs.CampoAsFloat("COBL"));
//					detDKMonth.setBajoCostoTotal(detDKMonth.getBajoCostoTotal()+regs.CampoAsFloat("total"));
//					
//				} else {
					detDKMonth.setVentaBspNeto(detDKMonth.getVentaBspNeto()+regs.CampoAsFloat("COBL"));
					detDKMonth.setVentaBspTotal(detDKMonth.getVentaBspTotal()+regs.CampoAsFloat("total"));

//				}							
				if (regs.CampoAsStr("STAT").equals("I")) {
					detDKOrg.setBrutaInternacional(detDKOrg.getBrutaInternacional()+regs.CampoAsFloat("EFCO"));
					detDKOrg.setUpFrontBspInternacional(detDKOrg.getUpFrontBspInternacional()+regs.CampoAsFloat("EFCO"));
					detDK.setTtlIngresos(detDK.getTtlIngresos()+regs.CampoAsFloat("EFCO"));
			} else {
					detDKOrg.setBrutaDomestico(detDKOrg.getBrutaDomestico()+regs.CampoAsFloat("EFCO"));
					detDKOrg.setUpFrontBspDomestico(detDKOrg.getUpFrontBspDomestico()+regs.CampoAsFloat("EFCO"));
					detDK.setTtlIngresos(detDK.getTtlIngresos()+regs.CampoAsFloat("EFCO"));
				
				}
			}

			

			
		}
		regs.close();
		BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Buscar bajo costo", pos++, 1000, false, null);
	
//		select apps.CNS_TICKET_MINING_SS_V.*
//		 from apps.CNS_TICKET_MINING_SS_V  
//		 where apps.CNS_TICKET_MINING_SS_V.fecha_emision = '20250104W' and
//		 tipo_servicio in ('LBN','LBI')
		JRecords<BizTrxOra> regsOra= new JRecords<BizTrxOra>(BizTrxOra.class);
		regsOra.addFilter("fecha_emision", getDateFrom(),">=");
		regsOra.addFilter("fecha_emision", getDateTo(),"<=");
		regsOra.addFixedFilter("where tipo_servicio in ('LBN','LBI') ");
		regsOra.readAll();
		while (regsOra.nextRecord()) {
			BizTrxOra ora = regsOra.getRecord();
			BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Buscar bajo costo para DK: "+ora.getDk(), pos++, 1000, false, null);
			
			//BizCarrier carrier = (BizCarrier)carriers.findInHash("cod_iata", ""+JTools.getLongFirstNumberEmbedded(ora.getIata()));
			String DK = ora.getDk()==null?"DESCONOCIDO "+ora.getBoleto():ora.getDk();
			String grupo = grupos.get(DK);
			if (grupo!=null) {
				DK = grupo;
			}
			String organization = ora.getOrg();
			//String keyDksMens = DK+"_"+JDateTools.buildPeriod(JDateTools.getInicioPeriodo(regs.CampoAsStr("periodo")));
			String keyDksMens = DK+"_"+JDateTools.DateToString(JDateTools.getFirstDayOfMonth(ora.getFechaEmision()),"yyyyMMdd");
			String keyDksOrgs = DK+"_"+(organization==null?"DESCONOCIDO":organization);
			BizRepoDKDetail detDK = dks.get(DK);
			if ( detDK==null) {
				detDK = new BizRepoDKDetail();
				detDK.setRepoDKId(getId());
				detDK.setCompany(getCompany());
				detDK.setDk(DK);
				if (!DK.startsWith("DESCONOCIDO") && detDK.getObjClienteDK()!=null) {
					detDK.setRazonSocial(detDK.getObjClienteDK().getDescripcion());
				}
				dks.put(DK, detDK);

			}
			BizRepoDKDetailMonth detDKMonth = dksMens.get(keyDksMens);
			if ( detDKMonth==null) {
				detDKMonth = new BizRepoDKDetailMonth();
				detDKMonth.setRepoDKId(getId());
				detDKMonth.setCompany(getCompany());
			//	String mes=JDateTools.getMonthDescr(JDateTools.getFinPeriodo(regs.CampoAsStr("periodo")));
				String mes=JDateTools.getMonthDescr(JDateTools.getFirstDayOfMonth(ora.getFechaEmision()));
//				detDKMonth.setPeriodDesde(JDateTools.buildPeriod(JDateTools.getInicioPeriodo(regs.CampoAsStr("periodo"))));
//				detDKMonth.setPeriodHasta(JDateTools.buildPeriod(JDateTools.getFinPeriodo(regs.CampoAsStr("periodo"))));
				detDKMonth.setPeriodDesde(JDateTools.DateToString(JDateTools.getFirstDayOfMonth(ora.getFechaEmision()),"yyyymmdd"));
				detDKMonth.setPeriodHasta(JDateTools.DateToString(JDateTools.getDateEndDay(ora.getFechaEmision()),"yyyymmdd"));
				detDKMonth.setMes(mes);
				dksMens.put(keyDksMens, detDKMonth);
				if (!meses.containsElement(mes))
						meses.addElement(mes);

			}
			BizRepoDKDetailOrg detDKOrg = dksOrgs.get(keyDksOrgs);
			if ( detDKOrg==null) {
				detDKOrg = new BizRepoDKDetailOrg();
				detDKOrg.setRepoDKId(getId());
				detDKOrg.setCompany(getCompany());
				detDKOrg.setOrg(organization);
				dksOrgs.put(keyDksOrgs, detDKOrg);
				if (!orgs.containsElement(organization))
					orgs.addElement(organization);
			}
			
			
			detDK.setVentaGlobalNeto(detDK.getVentaGlobalNeto()+ora.getTarifa());
			detDK.setVentaGlobalTotal(detDK.getVentaGlobalTotal()+ora.getTotal());
				
			detDKMonth.setBoletosBajoCosto(detDKMonth.getBoletosBajoCosto()+1);
			detDKMonth.setBoletosTotal(detDKMonth.getBoletosTotal()+1);
			detDKMonth.setBajoCostoNeto(detDKMonth.getBajoCostoNeto()+ora.getTarifa());
			detDKMonth.setBajoCostoTotal(detDKMonth.getBajoCostoTotal()+ora.getTotal());
				
		
			

			
		}
		regsOra.closeRecord();
		
		 
		
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
			String dk=det.getDk();
			String grupo = grupos.get(dk);
			if (grupo!=null) {
				dk = grupo;
			}
			BizRepoDKDetail detDK = dks.get(dk);
			if ( detDK==null) {
				detDK = new BizRepoDKDetail();
				detDK.setRepoDKId(getId());
				detDK.setCompany(getCompany());
				detDK.setDk(dk);
				if (!dk.startsWith("DESCONOCIDO") && detDK.getObjClienteDK()!=null) {
					detDK.setRazonSocial(detDK.getObjClienteDK().getDescripcion());
				}
				dks.put(dk, detDK);
			}
			String org=det.getOrganization();
			String keyDksOrgs = dk+"_"+(org==null?"DESCONOCIDO":org);
			BizRepoDKDetailOrg detDKOrg = dksOrgs.get(keyDksOrgs);
			if ( detDKOrg==null) {
				detDKOrg = new BizRepoDKDetailOrg();
				detDKOrg.setRepoDKId(getId());
				detDKOrg.setCompany(getCompany());
				detDKOrg.setOrg(org);
				dksOrgs.put(keyDksOrgs, detDKOrg);
				if (!orgs.containsElement(org))
					orgs.addElement(org);
			}
			detDKOrg.setNoDevReembolsos(det.getImporte());
		}
		detLiqs.closeRecord();
		
//		JBaseRegistro regsT=JBaseRegistro.recordsetFactory();
//		String sqlT="select company,customer_id_reducido,periodo,CodigoAerolinea, sum(back_importe)";
//		sqlT+=" FROM TUR_PNR_BOLETO ";
//		sqlT+=" WHERE company='"+getCompany()+"' AND ";
//		sqlT+="  		 creation_date_date>='"+JDateTools.DateToString(JDateTools.getInicioPeriodo(JDateTools.buildPeriod(getDateFrom())),"yyyy-MM-dd")+"' AND ";
//		sqlT+="  		 creation_date_date<='"+JDateTools.DateToString(JDateTools.getFinPeriodo(JDateTools.buildPeriod(getDateTo())),"yyyy-MM-dd")+"' ";
//		sqlT+=" GROUP BY company,customer_id_reducido,periodo,CodigoAerolinea";
//		regsT.ExecuteQuery(sqlT);
//		while (regsT.next()) {
//			
//		}
		BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Analizando Contratos", pos++, 1000, false, null);
		
		regs=JBaseRegistro.recordsetFactory();
		sql="SELECT ";
		sql+=" BSP_LIQUIDATION_DETAIL.DK as dk,  ";
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
				String dk= regs.CampoAsStr("dk");
				String grupo = grupos.get(dk);
				if (grupo!=null) {
					dk = grupo;
				}
			
				String keyDksContrato = dk+"_"+descrip;
				double porcentaje = detalle.getCalculePorcPorBoleto(regs.CampoAsFloat("tarifa_factura_local"));
				if (porcentaje==0) continue;
				if (dk.equals(""))continue;
				BizRepoDKDetail detDK = dks.get(dk);
				if ( detDK==null) {
					detDK = new BizRepoDKDetail();
					detDK.setRepoDKId(getId());
					detDK.setCompany(getCompany());
					detDK.setDk(dk);
					if (!dk.startsWith("DESCONOCIDO") && detDK.getObjClienteDK()!=null) {
						detDK.setRazonSocial(detDK.getObjClienteDK().getDescripcion());
					}
					dks.put(dk, detDK);
				}
				BizRepoDKDetailContrato detDKContrato = dksContratos.get(keyDksContrato);
				if ( detDKContrato==null) {
					detDKContrato = new BizRepoDKDetailContrato();
					detDKContrato.setRepoDKId(getId());
					detDKContrato.setCompany(getCompany());
					detDKContrato.setContrato(descrip);
					dksContratos.put(keyDksContrato, detDKContrato);
					if (!contrs.containsElement(descrip))
							contrs.addElement(descrip);
				}
			
					prem+= detalle.getCalculeGananciaPorBoleto(regs.CampoAsFloat("tarifa_factura_local"),porcentaje);
				detDKContrato.setComision(detDKContrato.getComision()+prem);
			}
		}
		
		for (String aDK:dks.keySet()) {
			BizRepoDKDetail det = dks.get(aDK);
			det.processInsert();
		}
		for (String aDKMens:dksMens.keySet()) {
			BizRepoDKDetailMonth detMens = dksMens.get(aDKMens);
			String aDK=aDKMens.substring(0,aDKMens.indexOf("_"));
			PssLogger.logInfo("DK: "+aDK);
			BizRepoDKDetail det = dks.get(aDK);
			
			detMens.setRepoDKDetailId(det.getId());
			detMens.setRepoDKId(getId());
			detMens.processInsert();
			
		}
		for (String aDKOrgs:dksOrgs.keySet()) {
			BizRepoDKDetailOrg detOrgs = dksOrgs.get(aDKOrgs);
			String aDK=aDKOrgs.substring(0,aDKOrgs.indexOf("_"));
			PssLogger.logInfo("DK: "+aDK);
			BizRepoDKDetail det = dks.get(aDK);
			
			detOrgs.setRepoDKDetailId(det.getId());
			detOrgs.setRepoDKId(getId());
			detOrgs.processInsert();
			
		}
		for (String aDKContratos:dksContratos.keySet()) {
			BizRepoDKDetailContrato detOrgs = dksContratos.get(aDKContratos);
			String aDK=aDKContratos.substring(0,aDKContratos.indexOf("_"));
			PssLogger.logInfo("DK: "+aDK);
			BizRepoDKDetail det = dks.get(aDK);
			
			detOrgs.setRepoDKDetailId(det.getId());
			detOrgs.setRepoDKId(getId());
			detOrgs.processInsert();
			det.setTotal(det.getTotal()+detOrgs.getComision());
			det.setTtlIngresos(det.getTtlIngresos()+detOrgs.getComision());
		}
		for (String aDK:dks.keySet()) {
			BizRepoDKDetail det = dks.get(aDK);
			det.processUpdate();
		}
		pContratos.setValue(contrs);
		pOrganizaciones.setValue(orgs);
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
		GuiRepoDKDetails dets = new GuiRepoDKDetails();
		dets.getRecords().addFilter("company", getCompany());
		dets.getRecords().addFilter("repo_dk_id", getId());
		dets.SetVision(""+ getId());
  	
		return dets.toExcel();
	}
}
