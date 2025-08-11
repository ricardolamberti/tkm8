package pss.common.customAutoReports.scheduler;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;

public class FormAutoReportSchedule extends JBaseForm {

  public FormAutoReportSchedule() throws Exception {
  }

  public GuiAutoReportSchedule getWin() { return (GuiAutoReportSchedule) getBaseWin(); }


  public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormControl oCtrl;
		AddItemEdit("company", CHAR, REQ, "company");
		AddItemEdit("reportId", CHAR, REQ, "report_id");
	  oCtrl = AddItemCombo( "jtype", CHAR, REQ, "rep_type", new JControlCombo() {
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getTypes(one);
	    	}
	    });
	  oCtrl.setRefreshForm(true);
	  AddItemCombo( "jsubtype", CHAR, OPT, "rep_subtype", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return getSubTypes(one);
    	}
    });
    AddItemCheck( "monday", OPT, "monday" );
    AddItemCheck( "tuesday", OPT, "tuesday");
    AddItemCheck( "wednesday", OPT, "wednesday" );
    AddItemCheck( "thursday", OPT, "thursday");
    AddItemCheck( "friday", OPT, "friday");
    AddItemCheck( "saturday", OPT, "saturday");
    AddItemCheck( "sunday", OPT, "sunday");
    AddItemCheck( "active", REQ, "active");
  	oCtrl = AddItemEdit( "time_to_run", CHAR, OPT, "time_to_run" );
  	oCtrl.SetValorDefault("03:00:00");
   // AddItem( last_run, DATETIME, OPT, "last_run" );
  } 
  
  
  
  private JWins getTypes(boolean one) throws Exception {
  		return JWins.CreateVirtualWins(this.getWin().GetcDato().getTiposSchedule());
  }
  
  private JWins getSubTypes(boolean one) throws Exception {
		return JWins.CreateVirtualWins(this.getWin().GetcDato().getSubTiposSchedule(this.findControl("rep_type").getValue()));
}
  
  @Override
	public void checkControls(String controlId) throws Exception {
		if (controlId.equals("rep_type")) {
			if (this.findControl("rep_type").getValue().equals(BizAutoReportSchedule.TYPE_WEEKLY)) {
				this.findControl("monday").SetReadOnly(false);
				this.findControl("tuesday").SetReadOnly(false);
				this.findControl("wednesday").SetReadOnly(false);
				this.findControl("thursday").SetReadOnly(false);
				this.findControl("friday").SetReadOnly(false);
				this.findControl("saturday").SetReadOnly(false);
				this.findControl("sunday").SetReadOnly(false);
			}else{
				this.findControl("monday").SetReadOnly(true);
				this.findControl("tuesday").SetReadOnly(true);
				this.findControl("wednesday").SetReadOnly(true);
				this.findControl("thursday").SetReadOnly(true);
				this.findControl("friday").SetReadOnly(true);
				this.findControl("saturday").SetReadOnly(true);
				this.findControl("sunday").SetReadOnly(true);
				
			}
			this.refreshDataNewMode();
		}
  }
	
}
