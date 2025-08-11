package pss.core.win.tools.orders;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.forms.JBaseForm;

public class GuiWinsColumn extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiWinsColumn() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizWinsColumn(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "Columna"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormWinsColumn.class; }
  public String  getKeyField() throws Exception { return "field"; }
  public String  getDescripField() { return "descripcion_show"; }
  public BizWinsColumn GetcDato() throws Exception { return (BizWinsColumn) this.getRecord(); }

  public void createActionMap() throws Exception {
  	addAction(10,  "Asc", null, 15501, true, true).setSpecialSelector("select");
  	addAction(20,  "Desc", null, 15500, true, true).setSpecialSelector("select");;
 	//	super.createActionMap();
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
		if (a.getId()==10) return !GetcDato().getOrdenAsc();
		if (a.getId()==20) return GetcDato().getOrdenAsc();
  	return super.OkAction(a);
  }
  
	protected boolean checkActionOnDrop(BizAction a) throws Exception {

	return true;
}
    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==10) return new JActSubmit(this) {
			@Override
			public boolean isExitOnOk() throws Exception {
				return false;
			}
			@Override
			public void submit() throws Exception {
				GetcDato().setOrdenAsc(true);
			}
		};
		if (a.getId()==20) return new JActSubmit(this) {
			@Override
			public boolean isExitOnOk() throws Exception {
				return false;
			}
			@Override
			public void submit() throws Exception {
				GetcDato().setOrdenAsc(false);
			}
		};
		
		return super.getSubmitFor(a);
	}

 }
