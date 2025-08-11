package pss.www.platform.users.history;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;

public class GuiUserHistory  extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiUserHistory() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizUserHistory(); }
  @Override
	public int GetNroIcono()       throws Exception { return 5601; }
  @Override
	public String GetTitle()       throws Exception { return "Historial"; }
//  public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormProductType.class; }


 



  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizUserHistory GetcDato() throws Exception {
    return (BizUserHistory) this.getRecord();
  }
  
  @Override
  public void createActionMap() throws Exception {
  }
  


}