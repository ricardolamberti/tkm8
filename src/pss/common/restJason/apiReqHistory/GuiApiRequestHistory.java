package pss.common.restJason.apiReqHistory;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.forms.JBaseForm;

public class GuiApiRequestHistory extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiApiRequestHistory() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizApiRequestHistory(); }
  @Override
	public int GetNroIcono()       throws Exception { return 6043; }
  @Override
	public String GetTitle()       throws Exception { return "API History"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormApiRequestHistory.class; }
  @Override
  public Class<? extends JBaseForm> getFormFlat() throws Exception {
  	return FormApiRequestHistoryFlat.class;
  }
  @Override
	public String getKeyField()   throws Exception { return "id"; }
  @Override
	public String getDescripField()                  { return "description"; }


  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizApiRequestHistory GetcDato() throws Exception {
    return (BizApiRequestHistory) this.getRecord();
  }

  @Override
	public void createActionMap() throws Exception {
    this.createActionQuery();
//    this.createActionUpdate();
    this.addAction(10, "Reenviar", null, 1, false, false);
  }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==10) return this.GetcDato().isError() && this.GetcDato().isSaliente();
  	return super.OkAction(a);
  }
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if ( a.getId()==10 ) return new JActSubmit(this, 10) {
  		public void execSubmit() throws Exception {
  			GetcDato().processMarcarPendiente();
  		}
  	};
  	return null;
  }
  
 /* public JWins getDetails() throws Exception {
  	GuiGroupTarifarioDetails wins = new GuiGroupTarifarioDetails();
  	wins.setRecords(this.GetcDato().getDetails());
  	return wins;
  }*/
  
  

}
