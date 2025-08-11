package pss.www.platform.users.stadistics;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.www.platform.users.online.GuiOnlineUsers;

public class GuiStadistics  extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiStadistics() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizStadistics(); }
  @Override
	public int GetNroIcono()       throws Exception { return 745; }
  @Override
	public String GetTitle()       throws Exception { return "Estadisticas Online"; }
  public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormStadistics.class; }
  @Override
	public String getKeyField()   throws Exception { return "total_solic"; }
  @Override
	public String getDescripField()                  { return "total_solic"; }


 

  static GuiStadistics objStat= null;

  static public  GuiStadistics getStat()  {
  	try {
			if (objStat!=null) return objStat;
			return objStat=new GuiStadistics();
		} catch (Exception e) {
			return null;
		}
  }

  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizStadistics GetcDato() throws Exception {
    return (BizStadistics) this.getRecord();
  }
  
  @Override
  public void createActionMap() throws Exception {
		this.addActionQuery(JWin.ACTION_QUERY, "Consultar");
		this.addAction(20, "Usuarios Online", null, 718, true, false, true, "Group");
 }
  

  public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 20) return new JActWins(this.getUsuarioOnline());
  	return super.getSubmitFor(a);
  }
  
	private JWins getUsuarioOnline() throws Exception {
		GuiOnlineUsers users = new GuiOnlineUsers();
		return users;
	}

  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }
  
  public void addTotUsr(long cantidad) {
  	try {
			GetcDato().addTotUsr(cantidad);
		} catch (Exception e) {
			e.printStackTrace();
		}

  	
  }

}