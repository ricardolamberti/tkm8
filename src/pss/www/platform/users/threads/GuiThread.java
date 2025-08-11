package pss.www.platform.users.threads;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiThread  extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiThread() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizThread(); }
  @Override
	public int GetNroIcono()       throws Exception { return 5601; }
  @Override
	public String GetTitle()       throws Exception { return "Thread"; }
  public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormThread.class; }
  @Override
	public String getKeyField()   throws Exception { return "id"; }
  @Override
	public String getDescripField()                  { return "description"; }


  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizThread GetcDato() throws Exception {
    return (BizThread) this.getRecord();
  }
  
  @Override
  public void createActionMap() throws Exception {
		this.addActionQuery(JWin.ACTION_QUERY, "Consultar").setModal(true);
		this.addActionDelete(JWin.ACTION_DELETE, "Interrupt");
  }
  

  public JAct getSubmitFor(BizAction a) throws Exception {
  	return super.getSubmitFor(a);
  }
  


  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }
}