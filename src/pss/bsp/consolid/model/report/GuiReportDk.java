package pss.bsp.consolid.model.report;

import pss.bsp.consolid.model.report.detail.GuiReportDetailDks;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFileGenerate;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiReportDk extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiReportDk() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizReportDk(); }
  @Override
	public int GetNroIcono()       throws Exception { return 5052; }
  @Override
	public String GetTitle()       throws Exception { return "Report DK"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormReportDk.class; }
  @Override
	public Class<? extends JBaseForm> getFormNew()     throws Exception { return FormNewReportDk.class; }
  @Override
	public String getKeyField()   throws Exception { return "id"; }
  @Override
	public String getDescripField()                  { return "original_report"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  public void createActionMap() throws Exception {
  	this.addAction(10, "Issue", null, 10020, false, false, false, "Group");		
  	this.addAction(20, "Refund", null, 10020, false, false, false, "Group");		
  	this.addAction(34, "Interfaz Excel", null, 10020, true, true, true, "Group");		
		this.createActionQuery();
		this.createActionDelete();
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
   	 	return super.OkAction(a);
  }
    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActWins(getDetailIssue());
		if (a.getId() == 20)	return new JActWins(getDetailRefund());
	 	if (a.getId() == 34)	return new JActFileGenerate(this) {
  		public String generate() throws Exception {
  			return getInterfaz4();
  		}
  	};
  	
		return super.getSubmitFor(a);
	}

	public String getInterfaz4() throws Exception {
		GetcDato().execProcessGenerate();
		return GetcDato().getFileElectronico();
	}
  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizReportDk GetcDato() throws Exception {
    return (BizReportDk) this.getRecord();
  }

  public JWins getDetailIssue() throws Exception {
  	GuiReportDetailDks dets = new GuiReportDetailDks();
  	dets.getRecords().addFilter("company", GetcDato().getCompany());
  	dets.getRecords().addFilter("id_report", GetcDato().getId());
  	dets.getRecords().addFilter("type", "ISSUE");
  	return dets;
  }
  public JWins getDetailRefund() throws Exception {
  	GuiReportDetailDks dets = new GuiReportDetailDks();
  	dets.getRecords().addFilter("company", GetcDato().getCompany());
  	dets.getRecords().addFilter("id_report", GetcDato().getId());
  	dets.getRecords().addFilter("type", "REFUND");
  	return dets;
  }
}
