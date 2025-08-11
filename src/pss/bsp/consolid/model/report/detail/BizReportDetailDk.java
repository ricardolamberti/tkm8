package pss.bsp.consolid.model.report.detail;

import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizReportDetailDk extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JLong pId = new JLong();
	private JLong pReportId = new JLong();
	private JBoolean pAdded = new JBoolean();
	private JString pCompany = new JString();
	private JString pType = new JString();
	private JString pDateOfTick = new JString();
	private JString pCtrip = new JString();
	private JString pTktNumber = new JString();
	private JString pPassagerName = new JString();
	private JString pFlightNro = new JString();
	private JString pOD = new JString();
	private JFloat pCtripAmount = new JFloat();
	private JFloat pTotal = new JFloat();
	private JFloat pFare = new JFloat();
	private JFloat pComision = new JFloat();
	private JFloat pFee = new JFloat();
	private JFloat pTotalPay = new JFloat();

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizReportDetailDk() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("id", pId);
		addItem("id_report", pReportId);
		addItem("company", pCompany);
		addItem("added",pAdded);
		addItem("type", pType);
		addItem("date_of_tick", pDateOfTick);
		addItem("ctrip", pCtrip);
		addItem("tkt_number", pTktNumber);
		addItem("passager_name", pPassagerName);
		addItem("flight_nro", pFlightNro);
		addItem("od", pOD);
		addItem("ctrip_amount", pCtripAmount);
		addItem("total", pTotal);
		addItem("fare", pFare);
		addItem("comision", pComision);
		addItem("fee", pFee);
		addItem("total_pay", pTotalPay);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "id", "ID", false, false, 64);
		addFixedItem(FIELD, "company", "Company", true, false, 100);
		addFixedItem(FIELD, "id_report", "Id Report", true, false, 64);
		addFixedItem(FIELD, "type", "Tipo", true, false, 100);
		addFixedItem(FIELD, "added", "Agregado", true, false, 2);
		addFixedItem(FIELD, "date_of_tick", "Fecha Ticket", true, false, 20);
		addFixedItem(FIELD, "ctrip", "CTrip", true, false, 50);
		addFixedItem(FIELD, "tkt_number", "Nro Boleto", true, false, 50);
		addFixedItem(FIELD, "passager_name", "Pasajero", true, false, 100);
		addFixedItem(FIELD, "flight_nro", "Vuelo", true, false, 20);
		addFixedItem(FIELD, "od", "OD", true, false, 20);
		addFixedItem(FIELD, "ctrip_amount", "Monto CTrip", true, false, 18, 2);
		addFixedItem(FIELD, "total", "Total", true, false, 18, 2);
		addFixedItem(FIELD, "fare", "Fare", true, false, 18, 2);
		addFixedItem(FIELD, "comision", "Comisión", true, false, 18, 2);
		addFixedItem(FIELD, "fee", "Fee", true, false, 18, 2);
		addFixedItem(FIELD, "total_pay", "Total a Pagar", true, false, 18, 2);
	}

	@Override
	public String GetTable() {
		return "TUR_DKS_REPORTS_DETAIL";
	}

	// -------------------------------------------------------------------------//
	// Getters y Setters
	// -------------------------------------------------------------------------//
	public void setId(long id) throws Exception {
		pId.setValue(id);
	}

	public long getId() throws Exception {
		return pId.getValue();
	}
	public void setAdded(boolean id) throws Exception {
		pAdded.setValue(id);
	}

	public boolean getAdded() throws Exception {
		return pAdded.getValue();
	}

	public void setReportId(long val) throws Exception {
		pReportId.setValue(val);
	}

	public long getReportId() throws Exception {
		return pReportId.getValue();
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public void setCompany(String company) throws Exception {
		pCompany.setValue(company);
	}

	public String getType() throws Exception {
		return pType.getValue();
	}

	public void setType(String type) throws Exception {
		pType.setValue(type);
	}

	public String getDateOfTick() throws Exception {
		return pDateOfTick.getValue();
	}

	public void setDateOfTick(String dateOfTick) throws Exception {
		pDateOfTick.setValue(dateOfTick);
	}

	public String getCtrip() throws Exception {
		return pCtrip.getValue();
	}

	public void setCtrip(String ctrip) throws Exception {
		pCtrip.setValue(ctrip);
	}

	public String getTktNumber() throws Exception {
		return pTktNumber.getValue();
	}

	public void setTktNumber(String tktNumber) throws Exception {
		pTktNumber.setValue(tktNumber);
	}

	public String getPassagerName() throws Exception {
		return pPassagerName.getValue();
	}

	public void setPassagerName(String passagerName) throws Exception {
		pPassagerName.setValue(passagerName);
	}

	public String getFlightNro() throws Exception {
		return pFlightNro.getValue();
	}

	public void setFlightNro(String flightNro) throws Exception {
		pFlightNro.setValue(flightNro);
	}

	public String getOD() throws Exception {
		return pOD.getValue();
	}

	public void setOD(String od) throws Exception {
		pOD.setValue(od);
	}

	public double getCtripAmount() throws Exception {
		return pCtripAmount.getValue();
	}

	public void setCtripAmount(double val) throws Exception {
		pCtripAmount.setValue(val);
	}

	public double getTotal() throws Exception {
		return pTotal.getValue();
	}

	public void setTotal(double val) throws Exception {
		pTotal.setValue(val);
	}

	public double getFare() throws Exception {
		return pFare.getValue();
	}

	public void setFare(double val) throws Exception {
		pFare.setValue(val);
	}

	public double getComision() throws Exception {
		return pComision.getValue();
	}

	public void setComision(double val) throws Exception {
		pComision.setValue(val);
	}

	public double getFee() throws Exception {
		return pFee.getValue();
	}

	public void setFee(double val) throws Exception {
		pFee.setValue(val);
	}

	public double getTotalPay() throws Exception {
		return pTotalPay.getValue();
	}

	public void setTotalPay(double val) throws Exception {
		pTotalPay.setValue(val);
	}
  public boolean readByBoleto(String company, String zBoleto ) throws Exception { 
    addFilter( "company",  company ); 
    addFilter( "tkt_number",  zBoleto ); 
      return read(); 
  } 
}
