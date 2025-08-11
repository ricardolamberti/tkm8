package  pss.common.backup.settings;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiBackUpGroupDetail extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiBackUpGroupDetail() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizBackUpGroupDetail(); }
  public int GetNroIcono()   throws Exception { return 925; }
  public String GetTitle()   throws Exception { return "Detalle"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormBackUpGroupDetail.class; }
  public String  getKeyField() throws Exception { return "company"; }
  public String  getDescripField() { return "tabla"; }
  public BizBackUpGroupDetail GetcDato() throws Exception { return (BizBackUpGroupDetail) this.getRecord(); }

	public void createActionMap() throws Exception {
		createActionQuery();
		createActionUpdate();
		createActionDelete();
		addAction(10, "Criterios", null, 10052, false, false, true, "Group").setRefreshAction(false);
	}
  
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==10) return new JActWins(this.getCriterios());
		return super.getSubmitFor(a);
	}

	private JWins getCriterios() throws Exception {
		GuiBackUpGroupDetailCriterios g=new GuiBackUpGroupDetailCriterios();
  	g.getRecords().addFilter("company", this.GetcDato().getCompany());
  	g.getRecords().addFilter("grupo", this.GetcDato().getGrupo());
  	g.getRecords().addFilter("tabla", this.GetcDato().getTabla());
  	g.getRecords().addOrderBy("tabla");
		return g;
	}
  
  
 }
