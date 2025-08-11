package pss.bsp.consolid.model.mit.detail;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.GuiPNRTickets;

public class GuiMitDetail extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiMitDetail() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizMitDetail(); }
  @Override
	public int GetNroIcono()       throws Exception { return 5052; }
  @Override
	public String GetTitle()       throws Exception { return "Mit detalle"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormMitDetail.class; }
   @Override
	public String getKeyField()   throws Exception { return "id"; }
  @Override
	public String getDescripField()                  { return "pnr"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  public void createActionMap() throws Exception {
//  	this.addAction(34, "Interfaz Excel", null, 10020, true, true, true, "Group");		
  	addAction(50, "Ver Boletos", null, 5129, true, true);
	  		super.createActionMap();
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
   	 	return super.OkAction(a);
  }
    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
//	 	if (a.getId() == 34)	return new JActFileGenerate(this) {
//  		public String generate() throws Exception {
//  			return getInterfaz4();
//  		}
//  	};
		 if (a.getId()==50) return new JActWins(this.getObjBoletos());
	  	
		return super.getSubmitFor(a);
	}

//	public String getInterfaz4() throws Exception {
//		return GetcDato().getFileElectronico();
//	}
  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizMitDetail GetcDato() throws Exception {
    return (BizMitDetail) this.getRecord();
  }
  public GuiPNRTickets getObjBoletos() throws Exception {
		GuiPNRTickets a = new GuiPNRTickets();
		a.getRecords().addFilter("company", GetcDato().getCompany());
		String pnrFull = GetcDato().getPnr();
		if (pnrFull == null || !pnrFull.contains("/")) {
			a.getRecords().setStatic(true);
			return a;
		}
		String pnrKey = pnrFull.substring(pnrFull.indexOf("/") + 1).trim();
		a.getRecords().addFilter("codigopnr", pnrKey);
		return a;
	}

}
