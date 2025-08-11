package  pss.common.backup.settings;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiBackUpMainSetting extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiBackUpMainSetting() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizMainSetting(); }
  public int GetNroIcono()   throws Exception { return 941; }
  public String GetTitle()   throws Exception { return "Configuración de BackUp"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormBackUpMainSetting.class; }
  public String  getKeyField() throws Exception { return "company"; }
  public String  getDescripField() { return "company"; }
  public BizMainSetting GetcDato() throws Exception { return (BizMainSetting) this.getRecord(); }

	public void createActionMap() throws Exception {
		createActionQuery();
		createActionUpdate();
		createActionDelete();
		addAction(10, "Grupos", null, 941, false, false, true, "Group").setRefreshAction(false);
	}
  
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==10) return new JActWins(this.getGrupos());
		return super.getSubmitFor(a);
	}

	private JWins getGrupos() throws Exception {
		GuiBackUpGroups g=new GuiBackUpGroups();
  	g.getRecords().addFilter("company", this.GetcDato().getCompany());
  	g.getRecords().addOrderBy("company");
		return g;
	}
	
 }
