package  pss.common.regions.company;

import pss.common.regions.currency.GuiMonedaPaises;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;


public class GuiCompanyCountry extends JWin {

	public GuiCompanyCountry() throws Exception {
	}


	@Override
	public JRecord ObtenerDato() throws Exception { return new BizCompanyCountry(); }
  @Override
	public String getKeyField() { return "pais"; }
  @Override
	public String getDescripField() { return "descr_country"; }
  @Override
	public String GetTitle() { return "Empresa-Pais"; }
  @Override
	public int GetNroIcono() { return 1; }
  @Override
	public Class<? extends JBaseForm> getFormBase() { return FormCompanyCountry.class; }


  @Override
	public void createActionMap() throws Exception {
  	super.createActionMap();
  	this.addAction(20, "Monedas", null, 41, true, false, true, "Group");
  }
  
  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if ( a.getId()==20) return new JActWins(this.getCompanyCurrencies());
  	return null;
  }

  
  public BizCompanyCountry GetcDato() throws Exception {
    return (BizCompanyCountry) getRecord();
  }
  
  
  @Override
	public String GetIconFile() throws Exception {
    return GetcDato().getObjCountry().getObjPais().GetIcono();
  }



  public JWins getCompanyCurrencies() throws Exception {
  	GuiMonedaPaises g = new GuiMonedaPaises();
  	g.getRecords().addFilter("company", this.GetcDato().getCompany());
  	g.getRecords().addFilter("pais", this.GetcDato().getCountry());
  	return g;
  }
  
  
}
