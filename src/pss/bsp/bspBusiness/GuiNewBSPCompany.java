package  pss.bsp.bspBusiness;

import pss.common.regions.company.GuiNewCompany;
import pss.core.services.records.JRecord;
import pss.core.winUI.forms.JBaseForm;

public class GuiNewBSPCompany extends GuiNewCompany {

  public GuiNewBSPCompany() throws Exception {
  }
  
  public JRecord ObtenerDato() throws Exception {
  	return new BizNewBSPCompany();
  }
  public BizNewBSPCompany GetcDato() throws Exception { return (BizNewBSPCompany) this.getRecord(); }

  public Class<? extends JBaseForm> getFormBase() throws Exception { 
  	return FormNewBSPCompany.class; 
  }

}

