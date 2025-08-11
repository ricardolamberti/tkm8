package  pss.common.backup.settings;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiBackUpGroup extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiBackUpGroup() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizBackUpGroup(); }
  public int GetNroIcono()   throws Exception { return 941; }
  public String GetTitle()   throws Exception { return "Grupo de BackUp"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormBizBackUpGroup.class; }
  public String  getKeyField() throws Exception { return "company"; }
  public String  getDescripField() { return "grupo"; }
  public BizBackUpGroup GetcDato() throws Exception { return (BizBackUpGroup) this.getRecord(); }

	public void createActionMap() throws Exception {
		createActionQuery();
		createActionUpdate();
		createActionDelete();
		addAction(10, "Detalles", null, 10052, false, false, true, "Group").setRefreshAction(false);
	}
  
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==10) return new JActWins(this.getGruposDetalles());
		return super.getSubmitFor(a);
	}

	private JWins getGruposDetalles() throws Exception {
		GuiBackUpGroupDetails g=new GuiBackUpGroupDetails();
  	g.getRecords().addFilter("company", this.GetcDato().getCompany());
  	g.getRecords().addFilter("grupo", this.GetcDato().getGrupo());
  	g.getRecords().addOrderBy("grupo");
		return g;
	}
  
 }
