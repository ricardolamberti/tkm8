package pss.bsp.consolid.model.rfnd;

import pss.bsp.consolid.model.rfnd.detail.GuiRfndDetails;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFileGenerate;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiRfnd extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiRfnd() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizRfnd(); }
  @Override
	public int GetNroIcono()       throws Exception { return 5052; }
  @Override
	public String GetTitle()       throws Exception { return "RFND"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormRfnd.class; }
  @Override
	public Class<? extends JBaseForm> getFormNew()     throws Exception { return FormNewRfnd.class; }
  @Override
	public String getKeyField()   throws Exception { return "id"; }
  @Override
	public String getDescripField()                  { return "date_to"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  public void createActionMap() throws Exception {
  	this.addAction(10, "RFND", null, 10020, false, false, false, "Group");		
   	this.addAction(20, "Interfaz", null, 10020, true, true, true, "Group");		
  	this.addAction(30, "Generar", null, 10020, true, true, true, "Group");		
   	this.createActionQuery();
		this.createActionDelete();
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
   	 	return super.OkAction(a);
  }
    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActWins(getDetailCuf());
		if (a.getId() == 30)	return new JActSubmit(this) {
  		public void submit() throws Exception {
  			 execGenerar();
  		}
  	};
	 	if (a.getId() == 20)	return new JActFileGenerate(this) {
  		public String generate() throws Exception {
  			return GetcDato().getInterfaz();
  		}
  	};
		return super.getSubmitFor(a);
	}
	public void execGenerar() throws Exception {
		GetcDato().execProcessGenerate();
	}
  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizRfnd GetcDato() throws Exception {
    return (BizRfnd) this.getRecord();
  }

  public JWins getDetailCuf() throws Exception {
  	GuiRfndDetails dets = new GuiRfndDetails();
  	dets.getRecords().addFilter("company", GetcDato().getCompany());
  	dets.getRecords().addFilter("id_rfnd", GetcDato().getId());
  	return dets;
  }
 
}
