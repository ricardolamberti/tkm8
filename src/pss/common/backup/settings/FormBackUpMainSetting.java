package  pss.common.backup.settings;

import pss.common.regions.company.GuiCompanies;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;

public class FormBackUpMainSetting extends JBaseForm {

  public FormBackUpMainSetting() throws Exception {
  }

  public GuiBackUpMainSetting getWin() { return (GuiBackUpMainSetting) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormControl oCtrl;
		AddItemCombo("company", CHAR, REQ, "company", new JControlWin() {
			@Override
			public JWins getRecords(boolean zOneRow) throws Exception {
				return getCompanies(zOneRow, getWin().GetcDato().getCompany());
			}
		});
		
    AddItemCheck( "monday", REQ, "monday").SetValorDefault(true);
    AddItemCheck( "tuesday", REQ, "tuesday").SetValorDefault(true);
    AddItemCheck( "wednesday", REQ, "wednesday").SetValorDefault(true);
    AddItemCheck( "thursday", REQ, "thursday").SetValorDefault(true);
    AddItemCheck( "friday", REQ, "friday").SetValorDefault(true);
    AddItemCheck( "saturday", REQ, "saturday").SetValorDefault(true);
    AddItemCheck( "sunday", REQ, "sunday").SetValorDefault(true);
    AddItemCheck( "active", REQ, "active").SetValorDefault(true);
  	oCtrl = AddItem( "time_to_run", CHAR, OPT, "time_to_run" );
  	oCtrl.SetValorDefault("03:00:00");
    AddItemEdit( "last_run", DATE, OPT, "last_run" );
    oCtrl = AddItemEdit( "output_path", CHAR, OPT, "output_path" );
    oCtrl.SetValorDefault("\\BackUp\\" + this.getWin().GetcDato().getCompany());
    this.AddItemTabPanel().AddItemTab(10);
  } 
		
	protected JWins getCompanies(boolean zOneRow, String zCompany) throws Exception {
		GuiCompanies wins=new GuiCompanies();
		if (zOneRow) {
			wins.getRecords().addFilter("company", zCompany);
		}
		return wins;
	}
  
	
}
