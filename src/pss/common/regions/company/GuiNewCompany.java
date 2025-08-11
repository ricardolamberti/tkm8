package  pss.common.regions.company;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.winUI.forms.JBaseForm;

public class GuiNewCompany extends JWin {

  public GuiNewCompany() throws Exception {
  }
  
  public JRecord ObtenerDato() throws Exception {
  	return new BizNewCompany();
  }
  public Class<? extends JBaseForm> getFormBase() throws Exception { 
  	return FormCompany.class; 
  }
  

  
  @Override
  public String GetTitle() throws Exception {
  	return "Nueva Compañia";
  }

	@Override
	public int GetNroIcono() throws Exception {
		return 5000;
	}

  
  @Override
	public void createActionMap() throws Exception {
  	super.createActionMap();
  	this.addAction(6, "Alta Empresa", null, 5000, false, false);
  }
  
  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
   	if ( a.getId()==6 ) return new JActNew(this, 0);
    return null;
  }

}

