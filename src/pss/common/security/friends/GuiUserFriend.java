package pss.common.security.friends;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiUserFriend extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiUserFriend() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizUserFriend(); }
  @Override
	public int GetNroIcono()       throws Exception { return 10074; }
  @Override
	public String GetTitle()       throws Exception { return "Usuario"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormUserFriend.class; }
  @Override
	public String getKeyField()   throws Exception { return "user_friend"; }
  @Override
	public String getDescripField()                  { return "descr_friend"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionQuery( 1, "Consultar" );
//    addActionUpdate( 2, "Modificar" );
    addActionDelete ( 3, "Eliminar"  );
  }




  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizUserFriend GetcDato() throws Exception {
    return (BizUserFriend) this.getRecord();
  }


}
