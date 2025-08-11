package pss.bsp.consolid.model.mit;

import pss.bsp.consolid.model.mit.conciliacion.GuiMitConciliacions;
import pss.bsp.consolid.model.mit.detail.GuiMitDetails;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.BizPNRTicket;
import pss.tourism.pnr.GuiPNRTicket;

public class GuiMit extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiMit() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizMit(); }
  @Override
	public int GetNroIcono()       throws Exception { return 5052; }
  @Override
	public String GetTitle()       throws Exception { return "MIT"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormMit.class; }
  @Override
	public Class<? extends JBaseForm> getFormNew()     throws Exception { return FormNewMit.class; }
  @Override
	public String getKeyField()   throws Exception { return "id"; }
  @Override
	public String getDescripField()                  { return "original_report"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  public void createActionMap() throws Exception {
  	this.addAction(10, "MIT", null, 10020, false, false, false, "Group");		
  	this.addAction(20, "Conciliación", null, 10020, false, false, false, "Group");		
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
		if (a.getId() == 10)	return new JActWins(getDetailMit());
		if (a.getId() == 20)	return new JActWins(getConciliacionMit());
		if (a.getId() == 30)	return new JActSubmit(this) {
  		public void submit() throws Exception {
  			 execGenerar();
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
  public BizMit GetcDato() throws Exception {
    return (BizMit) this.getRecord();
  }

  public JWins getDetailMit() throws Exception {
  	GuiMitDetails dets = new GuiMitDetails();
  	dets.getRecords().addFilter("company", GetcDato().getCompany());
  	dets.getRecords().addFilter("id_report", GetcDato().getId());
  	return dets;
  }
  public JWins getConciliacionMit() throws Exception {
  	GuiMitConciliacions dets = new GuiMitConciliacions();
  	dets.getRecords().addFilter("company", GetcDato().getCompany());
  	dets.getRecords().addFilter("id_mit", GetcDato().getId());
  	return dets;
  }
 

	
	
}
