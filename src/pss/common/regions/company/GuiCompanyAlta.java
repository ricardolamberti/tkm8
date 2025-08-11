package  pss.common.regions.company;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.winUI.forms.JBaseForm;

public class GuiCompanyAlta extends JWin {


  public GuiCompanyAlta() throws Exception {}

  @Override
	public String getKeyField() { return "business"; }
  @Override
	public String getDescripField() { return "business"; }
  @Override
	public int GetNroIcono() { return 5000; }
  @Override
	public JRecord ObtenerDato() throws Exception { return new jbCompanyAlta(); }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCompanyAlta.class; }
  @Override
	public String GetTitle() throws Exception { return "Nueva Empresa"; }

  @Override
	public JAct getSubmit(BizAction a) throws Exception {
  	
  	if ( a.getId()==0 ) return new JActNew(getNewCompany(), 0) {
			public void submit() throws Exception {
  		}
			public JAct nextAction() throws Exception {
  			return new JActNew(getNewCompany(), 0);
  		};
  	};
  	return null;
  }
  
  public JWin getNewCompany() throws Exception {
  	if ( GetcDato().getCompanyBusiness().equals("")) return new GuiCompanyAlta();
    GuiNewCompany company = JCompanyBusinessModules.getInstanceFor(GetcDato().getCompanyBusiness()).getNewWinInstance();
    return company;
  }

  public jbCompanyAlta GetcDato() throws Exception {
  	return (jbCompanyAlta) this.getRecord();
  }

}
