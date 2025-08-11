package pss.common.restJason.apiUser.apiUserDetails;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiApiUserDetail extends JWin {

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiApiUserDetail() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizApiUserDetail(); }
  @Override
	public int GetNroIcono()       throws Exception { return 10013; }
  @Override
	public String GetTitle()       throws Exception { return "Api User"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormApiUserDetail.class; }
  @Override
	public String getKeyField()   throws Exception { return "usuario"; }
  @Override
	public String getDescripField()                  { return "descr_usuario"; }


  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizApiUserDetail GetcDato() throws Exception {
    return (BizApiUserDetail) this.getRecord();
  }

  @Override
	public void createActionMap() throws Exception {
    this.createActionQuery();
    this.createActionUpdate();
    this.createActionDelete();
  
  }
  

  

}
