package  pss.bsp.config.carrierGroups;

import pss.bsp.config.carrierGroups.detalle.GuiCarrierGroupDetails;
import pss.bsp.regions.detalle.GuiRegionDetails;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.win.submits.JActWinsSelect;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.carrier.BizCarrier;
import pss.tourism.carrier.GuiCarrier;
import pss.tourism.carrier.GuiCarriers;

public class GuiCarrierGroup extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiCarrierGroup() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizCarrierGroup(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "Grupo Líneas Aereas"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCarrierGroup.class; }
  public String  getKeyField() throws Exception { return "id_group"; }
  public String  getDescripField() { return "descripcion"; }
  public BizCarrierGroup GetcDato() throws Exception { return (BizCarrierGroup) this.getRecord(); }

  public void createActionMap() throws Exception {
 		super.createActionMap();
 		this.addAction(10, "Lineas Aereas", null, 10020, false, false);
// 		this.addAction(20, "Agregar Línea Aerea", null, 10020, true, true).setMulti(true);
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	 	return super.OkAction(a);
  }
    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActWins(this.getDetail());
//		if (a.getId() == 20)	return new JActWinsSelect(new GuiCarriers(), this,true);
		return super.getSubmitFor(a);
	}

	public JWins getDetail() throws Exception {
		GuiCarrierGroupDetails wins = new GuiCarrierGroupDetails();
		wins.getRecords().addFilter("company", GetcDato().getCompany());
		wins.getRecords().addFilter("id_group", GetcDato().getIdGroup());
		return wins;
	}
	
	@Override
	public JAct Drop(JBaseWin baseWin) throws Exception {
		if (baseWin instanceof GuiCarrier) {
			this.GetcDato().execProcessLineas(JRecords.createRecords(baseWin.GetBaseDato()));
		}
		if (baseWin instanceof GuiCarriers) {
			this.GetcDato().execProcessLineas((JRecords)baseWin.GetBaseDato());
		}
		return super.Drop(baseWin);
	}
	
}
	

