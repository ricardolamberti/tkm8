package pss.common.customAutoReports.config;

import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;

public class BizReportField extends JRecord {

	private JString pCompany = new JString();
	private JLong pReportId = new JLong();
	private JLong pOrden = new JLong();
	private JString pCampo = new JString();
	private JString pValue = new JString();
	private JString pDescrCampo = new JString() {
		public void preset() throws Exception {
			pDescrCampo.setValue(getDescrCampo());
		}
	};
	private JString pScreenValue = new JString();
	
	private JString pcombo_value = new JString();
	private JString pcheck_value = new JString();
	private JString pedit_value = new JString();
	private JString plov_value = new JString();
	
	private BizReportList reportList;

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}


	public void setReportId(long zValue) throws Exception {
		pReportId.setValue(zValue);
	}

	public long getReportId() throws Exception {
		return pReportId.getValue();
	}
	
	public String getValue() throws Exception {
		return pValue.getValue();
	}

	public void setValue(String zValue) throws Exception {
		pValue.setValue(zValue);
	}

	public void setCampo(String zValue) throws Exception {
		pCampo.setValue(zValue);
	}

	public String getCampo() throws Exception {
		return pCampo.getValue();
	}

	public void setScreenValue(String zValue) throws Exception {
		pScreenValue.setValue(zValue);
	}

	public String getScreenValue() throws Exception {
		return pScreenValue.getValue();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizReportField() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("report_id", pReportId);
		this.addItem("campo", pCampo);
		this.addItem("valor", pValue);
		this.addItem("orden", pOrden);
		this.addItem("screen_value", pScreenValue);
		this.addItem("descr_campo", pDescrCampo);
		this.addItem("combo_value", pcombo_value);
		this.addItem("check_value", pcheck_value);
		this.addItem("edit_value", pedit_value);
		this.addItem("lov_value", plov_value);
		
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Company", true, true, 15);
		this.addFixedItem(KEY, "report_id", "Report Id", true, true, 18);
		this.addFixedItem(KEY, "campo", "Campo", true, false, 100);
		this.addFixedItem(FIELD, "valor", "Valor", true, false, 200);
		this.addFixedItem(FIELD, "orden", "Orden", true, true, 5);
		this.addFixedItem(FIELD, "screen_value", "Valor", true, false, 200);
		this.addFixedItem(VIRTUAL, "descr_campo", "Campo", true, true, 100);
		this.addFixedItem(VIRTUAL, "combo_value", "combo_value", true, true, 100);
		this.addFixedItem(VIRTUAL, "check_value", "check_value", true, true, 100);
		this.addFixedItem(VIRTUAL, "edit_value", "edit_value", true, true, 100);
		this.addFixedItem(VIRTUAL, "lov_value", "lov_value", true, true, 100);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "REPORT_AUTO_FIELD";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(String zCompany, long zReportId, String zCampo) throws Exception {
		addFilter("company", zCompany);
		addFilter("report_id", zReportId);
		addFilter("campo", zCampo);
		return read();
	}

	
	public BizReportList getObjReportList() throws Exception {
		if (this.reportList != null) return this.reportList;
		BizReportList r = new BizReportList();
		r.read(this.pCompany.getValue(), this.pReportId.getValue());
		return (this.reportList = r);
	}

	public String getDescrCampo() throws Exception {
		String s = "";
		s+=!this.hasCampo()?"*":this.getCamposGallery().getElement(this.pCampo.getValue());
		return s;
	}


	public void processInsert() throws Exception {
		if (pOrden.isNull()) {
			BizReportField max = new BizReportField();
			max.addFilter("company", this.pCompany.getValue());
			max.addFilter("report_id", this.pReportId.getValue());
			pOrden.setValue(max.SelectMaxLong("orden") + 1);
		}
		this.findWhoHasValueAndUpdate();
		super.processInsert();
	}
	
	@Override
	public void processUpdate() throws Exception {
		this.findWhoHasValueAndUpdate();
		super.processUpdate();
	}
	
	private void findWhoHasValueAndUpdate() throws Exception {
		if (!pcombo_value.getValue().equals("")) {
			pValue.setValue(pcombo_value.getValue());
			return;
		}
		if (!pedit_value.getValue().equals("")) { 
			pValue.setValue(pedit_value.getValue());
			pScreenValue.setValue(pedit_value.getValue());
			return;
		}
		if (!plov_value.getValue().equals("")) {
			pValue.setValue(plov_value.getValue());
			return;
		}
		if (!pcheck_value.getValue().equals("")) {//este siempre tiene valor, así que hay que dejarlo último. 
			pValue.setValue(pcheck_value.getValue());
			pScreenValue.setValue(pcheck_value.getValue());
			return;
		}
		
	}
	
  
  public JMap<String, String> getCamposGallery() throws Exception {
  	return this.getObjReportList().getCamposGallery();
  }
  
  public JFormControl getFieldControl(String zControlId) throws Exception {
  	return this.getObjReportList().getControles().findControl(zControlId);
  }
  	
  public JWins getWinsFromConrol(String zControlId) throws Exception {
  	return  this.getObjReportList().getWinsFromConrol(zControlId);
  }
  
  public boolean hasCampo() throws Exception {
  	return this.pCampo.isNotNull();
  }
  
   
  
}
