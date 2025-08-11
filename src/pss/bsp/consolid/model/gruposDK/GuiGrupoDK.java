package  pss.bsp.consolid.model.gruposDK;

import pss.bsp.consolid.model.gruposDK.detail.GuiGrupoDKDetails;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiGrupoDK extends JWin {


  /**
   * Constructor de la Clase
   */
  public GuiGrupoDK() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizGrupoDK(); }
  public int GetNroIcono()   throws Exception { return 10036; }
  public String GetTitle()   throws Exception { return "Grupo DK"; 
  }
  public Class<? extends JBaseForm> getFormBase() throws Exception { 
return FormGrupoDK.class; 
  }
  public String  getKeyField() throws Exception { return "id_grupo"; }
  public String  getDescripField() { 
    	return "descripcion"; 
  }
  public BizGrupoDK GetcDato() throws Exception { return (BizGrupoDK) this.getRecord(); }

  public void createActionMap() throws Exception {
		addAction(10,   "Detalle", null, 900, true, true);
		super.createActionMap();
	}  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	return super.OkAction(a);
  }
	
	
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActWins(getDetalle());
 
  	return super.getSubmitFor(a);
  }
  
  public GuiGrupoDKDetails getDetalle() throws Exception {
  	GuiGrupoDKDetails devices = new GuiGrupoDKDetails();
  	devices.setRecords(GetcDato().getObjDetalle());
  	return devices;
  }
 }
