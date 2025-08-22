package pss.tourism.dks;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.forms.JBaseForm;

public class GuiDk extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiDk() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizDk(); }
  @Override
	public int GetNroIcono()       throws Exception { return 1105; }
  @Override
	public String GetTitle()       throws Exception { return "Extra DK"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormDk.class; }
  @Override
	public String getKeyField()   throws Exception { return "id"; }
  @Override
	public String getDescripField()                  { return "dk"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  public void createActionMap() throws Exception {
 		super.createActionMap();
 		BizAction a =this.addAction(10, "Aplicar regla", null, 10020, true, true, true, "Group").setConfirmMessage(true);
 		a.setConfirmMessageDescription("Se aplicará la regla a todos los boletos que apliquen, la operación es irreversible. Confirma?");
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
   	 	return super.OkAction(a);
  }
    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActSubmit(this){
			@Override
			public void submit() throws Exception {
				setMessage( GetcDato().execChangeAll());
				super.submit();
			}
		};
 	return super.getSubmitFor(a);
	}


  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizDk GetcDato() throws Exception {
    return (BizDk) this.getRecord();
  }


}
