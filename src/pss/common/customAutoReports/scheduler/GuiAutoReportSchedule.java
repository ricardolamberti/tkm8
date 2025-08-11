package pss.common.customAutoReports.scheduler;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.forms.JBaseForm;

public class GuiAutoReportSchedule extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiAutoReportSchedule() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizAutoReportSchedule(); }
  public int GetNroIcono()   throws Exception { return 941; }
  public String GetTitle()   throws Exception { return "Configuración de Scheduler"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormAutoReportSchedule.class; }
  public String  getKeyField() throws Exception { return "company"; }
  public String  getDescripField() { return "report_id"; }
  public BizAutoReportSchedule GetcDato() throws Exception { return (BizAutoReportSchedule) this.getRecord(); }

	public void createActionMap() throws Exception {
		createActionQuery();
		createActionUpdate();
		createActionDelete();
		this.addAction(20, "Ejecutar", null, 48, true, true).setConfirmMessage(true);
		this.addAction(30, "Ejecutar con Act Fecha", null, 48, true, true).setConfirmMessage(true);
	}
  
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==20) return new JActSubmit(this, 20) {
			public void submit() throws Exception {
				GetcDato().execProcessReport(false);
			}
		};		
		if (a.getId()==30) return new JActSubmit(this, 20) {
			public void submit() throws Exception {
				GetcDato().execProcessReport(true);
			}
		};		
		return super.getSubmitFor(a);
	}

	
 }
