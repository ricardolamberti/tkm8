package pss.www.platform.users.online;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.www.platform.users.history.GuiUserHistories;

public class GuiOnlineUser  extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiOnlineUser() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizOnlineUser(); }
  @Override
	public int GetNroIcono()       throws Exception { return 5601; }
  @Override
	public String GetTitle()       throws Exception { return "Usuarios Online"; }
  public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormOnlineUser.class; }
  @Override
	public String getKeyField()   throws Exception { return "user"; }
  @Override
	public String getDescripField()                  { return "user"; }





  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizOnlineUser GetcDato() throws Exception {
    return (BizOnlineUser) this.getRecord();
  }
  
  @Override
  public void createActionMap() throws Exception {
		this.addActionQuery(JWin.ACTION_QUERY, "Consultar");
		this.addActionDelete(JWin.ACTION_DELETE, "Logout");
		this.addAction(10, "Histórico", null, 209, false, false);
  }
  

  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActWins(this.getHistorico());
  	return super.getSubmitFor(a);
  }
  
  public JWins getHistorico() throws Exception {
  	GuiUserHistories g = new GuiUserHistories();
  	if (this.GetcDato().getUserHistories()!=null)
  		g.setRecords(this.GetcDato().getUserHistories());
  	else
    	g.SetEstatico(true);

  	return g; 
  }

  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }
}