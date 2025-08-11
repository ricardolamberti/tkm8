package pss.common.publicity;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiCampaign extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiCampaign() throws Exception {
  }


  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizCampaign(); }
  @Override
	public int GetNroIcono()   throws Exception { return 755; }
  @Override
	public String GetTitle()   throws Exception { return "Campaña publicitaria"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCampaign.class; }
  @Override
	public String  getKeyField() throws Exception { return "id"; }
  @Override
	public String  getDescripField() { return "Descripcion"; }
  public BizCampaign GetcDato() throws Exception { return (BizCampaign) this.getRecord(); }


	@Override
	public void createActionMap() throws Exception {
		this.addActionQuery(1, "Consultar");
		this.addAction(10, "Detalle", null, 953, true, false, true, "Group");
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10) return new JActWins(this.getDetalles());
		return null;
	}

	public JWins getDetalles() throws Exception {
		GuiSpots wins = new GuiSpots();
		wins.getRecords().addFilter("id",this.GetcDato().getId());
		wins.getRecords().addOrderBy("page");
		wins.getRecords().addOrderBy("action");
		wins.getRecords().addOrderBy("step");
		return wins;
	}
 }
