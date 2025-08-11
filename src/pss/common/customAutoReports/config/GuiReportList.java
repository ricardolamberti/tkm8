package pss.common.customAutoReports.config;

import pss.common.customAutoReports.scheduler.GuiAutoReportSchedules;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActReport;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiReportList extends JWin {

  /**
   * Constructor de la Clase
   */
  public GuiReportList() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizReportList(); }
  public int GetNroIcono()   throws Exception { return 10027; }
  public String GetTitle()   throws Exception { return "Reporte Automatico"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormReportList.class; }
  public String  getKeyField() throws Exception { return "report_id"; }
  public String  getDescripField() { return "report_name"; }
  public BizReportList GetcDato() throws Exception { return (BizReportList) this.getRecord(); }
  
//  public void setObjWinOwner(JWin value) {
//  	this.winOwner=value;
//  }

	public void createActionMap() throws Exception {
		createActionQuery();
		createActionUpdate();
		createActionDelete();
		addAction(10, "Campos", null, 90, false, false, true, "Group").setRefreshAction(false);
		addAction(15, "Schedule", null, 5043, false, false, true, "Group").setRefreshAction(false);
		addAction(20, "Ver Reporte", null, 5, true, true).setNuevaVentana(true);

	}
	

  
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==10) return new JActWins(this.getCampos());
		if (a.getId()==15) return new JActWins(this.getScheduler());
	  if (a.getId()==20)	return new JActReport(this.GetcDato().getFilledWinSource(),5) {
		  		public String generate() throws Exception {
		  			return super.generate();
				}
	  	};
		return super.getSubmitFor(a);
	}

	private JWins getCampos() throws Exception {
		GuiReportFields g=new GuiReportFields();
  	g.getRecords().addFilter("company", this.GetcDato().getCompany());
  	g.getRecords().addFilter("report_id", this.GetcDato().getIdReport());
  	g.getRecords().addOrderBy("orden");
		return g;
	}
	private JWins getScheduler() throws Exception {
		GuiAutoReportSchedules g=new GuiAutoReportSchedules();
  	g.getRecords().addFilter("company", this.GetcDato().getCompany());
  	g.getRecords().addFilter("report_id", this.GetcDato().getIdReport());
		return g;
	}
	
}
