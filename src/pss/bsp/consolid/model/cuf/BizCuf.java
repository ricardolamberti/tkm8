package pss.bsp.consolid.model.cuf;

import java.io.FileOutputStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pss.JPath;
import pss.bsp.consolid.model.cuf.detail.BizCufDetail;
import pss.bsp.consolid.model.cuf.detail.GuiCufDetails;
import pss.bsp.organization.BizOrganization;
import pss.bsp.pdf.BizPDF;
import pss.bsp.pdf.mex.detalle.BizMexDetalle;
import pss.core.services.JExec;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWinsExcel;
import pss.tourism.carrier.BizCarrier;

public class BizCuf extends JRecord {

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
	public BizCuf() throws Exception {
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
		return "BSP_CUF";
	}

  
	@Override
	public void processUpdate() throws Exception {
		super.processUpdate();
	}
	
	@Override
	public void processDelete() throws Exception {
		BizCufDetail det  = new BizCufDetail();
		det.addFilter("company", getCompany());
		det.addFilter("id_cuf", getId());
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
		BizCufDetail detClean  = new BizCufDetail();
		detClean.addFilter("company", getCompany());
		detClean.addFilter("id_cuf", getId());
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
		dets.addFilter("TACN","null","<>");
		dets.readAll();
		while (dets.nextRecord()) {
			BizMexDetalle det = dets.getRecord();
			BizCarrier carrier = (BizCarrier)carriers.findInHash("cod_iata", ""+JTools.getLongFirstNumberEmbedded(det.getTACN()));
			if (carrier==null) continue;
			BizCufDetail detCuf = new BizCufDetail();
			detCuf.setCufId(getId());
			detCuf.setCompany(getCompany());
			detCuf.setTACN(det.getTACN());
			detCuf.setEFCO(det.getEFCO());
			detCuf.setAgente(det.getAGTN());
			detCuf.setDK(carrier.getDK());
			detCuf.setAL(carrier.getCarrier());
			detCuf.setRuta("COMISION BSP "+JDateTools.DateToString(det.getObjCabecera().getPeriodoDesde(),"dd")+"-"+
					JDateTools.DateToString(det.getObjCabecera().getPeriodoHasta(),"ddMMM/yy")+" "+
					carrier.getCarrier()+" "+det.getAGTN()
			);
			detCuf.setCorreo(carrier.getEmail());
			detCuf.setUsoCFDI(carrier.getCfdi());
			detCuf.setFormaPago(carrier.getFormaPago().isEmpty()?"COMP":carrier.getFormaPago());
			detCuf.setOrganizacion(BizOrganization.readByIata(getCompany(), det.getAGTN()));
			detCuf.processInsert();
		}
		dets.closeRecord();
	}
	public String getInterfaz() throws Exception {
		JRecords<BizOrganization> orgs = new JRecords<BizOrganization>(BizOrganization.class);
		orgs.addFilter("company", getCompany());
		JIterator<BizOrganization> it = orgs.getStaticIterator();
		List<SimpleEntry<String, byte[]>> hojas = new ArrayList();
		while (it.hasMoreElements()) {
			BizOrganization org = it.nextElement();
			GuiCufDetails details = new GuiCufDetails();
			details.getRecords().addFilter("company", getCompany());
			details.getRecords().addFilter("organizacion", org.getCodeOrganization());
			details.getRecords().addFilter("id_cuf", getId());
			byte[] excel1 = details.toExcelBytes();
			hojas.add(new SimpleEntry<>(org.getCodeOrganization(),excel1));
		}
		byte[] mergedExcel = JWinsExcel.mergeExcels(hojas);

		String outputPath = "interfazCuf_"+JDateTools.dateToStr(new Date(), "ddMMyyyyhhmmss")+".xlsx";
		try (FileOutputStream out = new FileOutputStream(JPath.PssPathTempFiles()+ "/"+outputPath)) {
			out.write(mergedExcel);
			out.close();
		}

		return outputPath;
	}
}
