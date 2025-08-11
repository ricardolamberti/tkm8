package pss.common.customLogin;

import pss.JPath;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActUpdate;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiCustomLogin extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiCustomLogin() throws Exception {
  }


  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizCustomLogin(); }
  @Override
	public int GetNroIcono()   throws Exception { return 755; }
  @Override
	public String GetTitle()   throws Exception { return "Login customizable"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCustomLogin.class; }
  @Override
	public String  getKeyField() throws Exception { return "id"; }
  @Override
	public String  getDescripField() { return "Descripcion"; }
  public BizCustomLogin GetcDato() throws Exception { return (BizCustomLogin) this.getRecord(); }


	@Override
	public void createActionMap() throws Exception {
		this.createActionQuery();
		this.createActionUpdate();
		this.createActionDelete();
		this.addAction(18, "Agregar Logo", null, 5060, true, true, true, "Group" );
		this.addAction(19, "Agregar Fondo", null, 5060, true, true, true, "Group" );

		this.addAction(10, "Detalle", null, 953, true, false, true, "Group");
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10) return new JActWins(this.getDetalles());
		if (a.getId() == 18 ) return new JActUpdate(this.getLogo(), JWin.ACTION_UPDATE);
		if (a.getId() == 19 ) return new JActUpdate(this.getFondo(), JWin.ACTION_UPDATE);

		return null;
	}
  public JWin getLogo() throws Exception {
  	GuiCustomLoginLogo c = new GuiCustomLoginLogo();
  	c.GetcDato().setDirectory(JPath.PssPathData()+"/"+JPath.PssPathLogos());
  	return c;
  }
  public JWin getFondo() throws Exception {
  	GuiCustomLoginLogo c = new GuiCustomLoginLogo();
  	c.GetcDato().setDirectory(JPath.PssImages().substring(5)+"/backgrounds/");
  	return c;
  }
	public JWins getDetalles() throws Exception {
		GuiCustomLoginComponents wins = new GuiCustomLoginComponents();
		wins.getRecords().addFilter("id",this.GetcDato().getId());
		wins.getRecords().addOrderBy("page");
		wins.getRecords().addOrderBy("action");
		wins.getRecords().addOrderBy("step");
		return wins;
	}
 }
