package pss.bsp.consolid.model.rfnd;

import pss.bsp.consolid.model.liquidacion.detail.BizLiqDetail;
import pss.bsp.consolid.model.rfnd.detail.BizRfndDetail;
import pss.bsp.consolid.model.rfnd.detail.GuiRfndDetails;
import pss.bsp.pdf.BizPDF;
import pss.bsp.pdf.mex.detalle.BizMexDetalle;
import pss.core.services.JExec;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.tourism.carrier.BizCarrier;
import pss.tourism.pnr.BizPNRTicket;

public class BizRfnd extends JRecord {

  // -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JLong pId = new JLong();
	private JString pCompany = new JString();
	private JDate pDateFrom = new JDate();
	private JDate pDateTo = new JDate();
	
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
	public BizRfnd() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("id", pId);
		addItem("company", pCompany);
		addItem("date_from", pDateFrom);
		addItem("date_to", pDateTo);
		
	}

	@Override
	public void createFixedProperties() throws Exception {

		addFixedItem(KEY, "id", "id", false, false, 64);
		addFixedItem(FIELD, "company", "Company", true, false, 100);
		addFixedItem(FIELD, "date_from", "Fecha desde", true, false, 25);
		addFixedItem(FIELD, "date_to", "Fecha hasta", true, false, 25);
	  

	}


	@Override
	public String GetTable() {
		return "BSP_REPO_RFND";
	}

  
	@Override
	public void processUpdate() throws Exception {
		super.processUpdate();
	}
	
	@Override
	public void processDelete() throws Exception {
		BizRfndDetail det  = new BizRfndDetail();
		det.addFilter("company", getCompany());
		det.addFilter("id_rfnd", getId());
		det.deleteMultiple();
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
		BizRfndDetail detClean  = new BizRfndDetail();
		detClean.addFilter("company", getCompany());
		detClean.addFilter("id_rfnd", getId());
		detClean.deleteMultiple();
	}
	public void processGenerate() throws Exception {
		processClean();
		new BizPDF().procPendientes(getDateFrom(),getDateTo());

		JRecords<BizCarrier> carriers = new JRecords<BizCarrier>(BizCarrier.class);
		carriers.addFilter("cod_iata", "null","<>");
		carriers.convertToHash("cod_iata");
	
		JRecords<BizMexDetalle> dets = new JRecords<BizMexDetalle>(BizMexDetalle.class);
		dets.addFilter("company", getCompany());
		dets.addFilter("fecha",getDateFrom(),">=");
		dets.addFilter("fecha",getDateTo(),"<=");
		dets.addFilter("TRNC","RFND");
		dets.readAll();
		while (dets.nextRecord()) {
			BizMexDetalle det = dets.getRecord();
			BizPNRTicket tkt = new BizPNRTicket();
			tkt.dontThrowException(true);
			if (!tkt.ReadByBoleto(det.getCompany(), det.getBoletoRfnd())) {
				tkt = null;
			}
			BizLiqDetail liqDetail = new BizLiqDetail();
			liqDetail.dontThrowException(true);
			if (!liqDetail.readByBoleto(det.getCompany(), det.getBoletoRfnd())) {
				liqDetail = null;
			}
			
			
		
			BizCarrier carrier = (BizCarrier)carriers.findInHash("cod_iata", ""+JTools.getLongFirstNumberEmbedded(det.getTACN()));
	
			BizRfndDetail detRfnd = new BizRfndDetail();
			detRfnd.setRfndId(getId());
			detRfnd.setCompany(getCompany());
			detRfnd.setTipo(det.getTRNC());
			detRfnd.setIata(det.getAGTN());
			detRfnd.setDK(tkt==null?det.getDK():tkt.getCustomerIdReducido());
			detRfnd.setSolicita("ECASTILLO");
			detRfnd.setPaxNameHot(tkt==null?"Boleto no encontrado":tkt.getNombrePasajero());
			detRfnd.setConcepto("REM FOLIO "+det.getTDNR()+ " BOLETO "+det.getBoletoRfnd());
			detRfnd.setServicio(liqDetail==null?"Liq. no encontrada":liqDetail.getTipoServicio());
			detRfnd.setLA(tkt==null?(carrier==null?"":carrier.getCarrier()):tkt.getLineaAerea());
			detRfnd.setFolioBsp(det.getTDNR());
			detRfnd.setBoleto(det.getBoletoRfnd());
			detRfnd.setFPag(det.getCASH()==0?"Tarjeta":"Cash");
			detRfnd.setTarifa(det.getCOBL()+det.getPEN());
			detRfnd.setIva(0);
			detRfnd.setTua((det.getCASH()+det.getCRED())-(det.getCOBL()+det.getPEN()));
			detRfnd.setTotal(det.getCASH()+det.getCRED());
			detRfnd.setPNR(tkt==null?"":tkt.getCodigopnr());
			detRfnd.setRuta(tkt==null?"":tkt.getAirIntinerary());
			detRfnd.setPctCom(liqDetail==null?0:liqDetail.getPorcComision());
			detRfnd.setComision(liqDetail==null?0:liqDetail.getComision());
			detRfnd.processInsert();
		}
		dets.closeRecord();
	}
	public String getInterfaz() throws Exception {
		GuiRfndDetails details = new GuiRfndDetails();
		details.getRecords().addFilter("company", getCompany());
		details.getRecords().addFilter("id_rfnd", getId());

		return details.toExcel();
	}
}
