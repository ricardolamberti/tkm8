package pss.common.customAutoReports.config;

import pss.common.security.BizUsuario;
import pss.common.security.GuiUsuarios;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormReportList extends JBaseForm {

  public FormReportList() throws Exception {
  }

  public GuiReportList getWin() { return (GuiReportList) getBaseWin(); }


  public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormControl oControl;
    AddItemEdit( "company", CHAR, REQ, "company" );
    AddItemEdit( "reportId", ULONG, OPT, "report_id" );
    AddItemCombo( "report_name", CHAR, REQ, "report_name", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return getReportList(one);
    	}
    });
    oControl = AddItemCombo( "report_type", CHAR, REQ, "report_type", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return getReportType(one);
    	}
    });
    oControl.setRefreshForm(true);
    oControl.SetValorDefault(BizReportList.TYPE_REPORT);
    AddItemEdit( "report_fantasy_name", CHAR, OPT, "report_fantasy_name" );
    oControl = AddItemEdit( "winActionId", ULONG, OPT, "report_win_action_id" );
  	oControl.setVisible(false);
    if (this.getWin().GetcDato().getReportType().equals(BizReportList.TYPE_JWINACTION)) {
    	oControl.setVisible(true);
    }
		AddItemCombo( "user", CHAR, REQ, "report_user", new JControlWin() {
			@Override
			public JWins getRecords(boolean zOneRow) throws Exception {
				return getUsuarios(zOneRow);
			}
		});    
    AddItemArea( "mail_list", CHAR, OPT, "report_mail_list" );
   // AddItem( mail_list_bcc.getTextArea(), CHAR, OPT, "report_mail_list_bcc" );
    JFormTabPanelResponsive tab = this.AddItemTabPanel();
    tab.AddItemTab(10);
    tab.AddItemTab(15);
  }
  
  @Override
  	public void checkControls(String sControlId) throws Exception {
  		if (sControlId.equals("report_type")) {
  			if (this.getControles().findControl("report_type").getValue().equals(BizReportList.TYPE_JWINACTION)) {
      		this.getControles().findControl("report_win_action_id").setVisible(true);
  			} else {
      		this.getControles().findControl("report_win_action_id").setVisible(false);
  			}
  		}
  		super.checkControls(sControlId);
  	}
  
  private JWins getReportList(boolean one) throws Exception {
 		return JWins.CreateVirtualWins(this.getWin().GetcDato().getReportList());
  }
  
  private JWins getReportType(boolean one) throws Exception {
 		return JWins.createVirtualWinsFromMap(BizReportList.getTipoReportes());
  }

  private JWins getUsuarios(boolean zOneRow) throws Exception {
		GuiUsuarios wins= new GuiUsuarios();
		if (!BizUsuario.getUsr().isAdminUser()) 
			wins.getRecords().addFilter("company", this.findControl("company").getValue());
		if (zOneRow) {
			wins.getRecords().addFilter("usuario", this.getWin().GetcDato().getUser());
		}
		return wins;
	}


} 
