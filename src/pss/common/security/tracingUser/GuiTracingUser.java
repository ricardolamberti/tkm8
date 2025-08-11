package  pss.common.security.tracingUser;

import pss.common.regions.company.GuiCompany;
import pss.common.security.BizUsuario;
import pss.common.security.GuiUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.winUI.forms.JBaseForm;

public class GuiTracingUser extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiTracingUser() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizTracingUser(); }
  public int GetNroIcono()   throws Exception { return 10051; }
  public String GetTitle()   throws Exception { return "usuario"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { 
  	return FormTracingUser.class; 
  }
  public String  getKeyField() throws Exception { return "id_tracing"; }
  public String  getDescripField() { return "email"; }
  public BizTracingUser GetcDato() throws Exception { return (BizTracingUser) this.getRecord(); }

  
  @Override
  public void createActionMap() throws Exception {
  	this.addAction(10, "Usuario", null, 10027, true, true );
  	this.addAction(20, "Empresa", null, 10027, true, true );
     	super.createActionMap();
  }
  
  
 
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==10) return !GetcDato().isNullUsuario();
  	if (a.getId()==20) return !GetcDato().isNullCompany();
  	return super.OkAction(a);
  }
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId() == 10) return new JActQuery(getObjUsuario());
  	if (a.getId() == 20) return new JActQuery(getObjCompany());
    return super.getSubmitFor(a);
  }	
  
  public GuiCompany getObjCompany() throws Exception {
  	GuiCompany cpny = (GuiCompany)BizUsuario.getUsr().getObjBusiness().getWinInstance();
  	cpny.GetcDato().dontThrowException(true);
  	cpny.GetcDato().Read(GetcDato().getCompany());
  	return cpny;
  }
  public GuiUsuario getObjUsuario() throws Exception {
  	GuiUsuario usr = (GuiUsuario)BizUsuario.getUsr().getObjBusiness().getUserWinInstance();
  	usr.GetcDato().dontThrowException(true);
  	usr.GetcDato().Read(GetcDato().getUsuario());
  	return usr;
  }

 }
