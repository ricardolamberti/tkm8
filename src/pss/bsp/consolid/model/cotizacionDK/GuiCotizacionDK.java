package  pss.bsp.consolid.model.cotizacionDK;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiCotizacionDK extends JWin {


  /**
   * Constructor de la Clase
   */
  public GuiCotizacionDK() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizCotizacionDK(); }
  public int GetNroIcono()   throws Exception { return 10036; }
  public String GetTitle()   throws Exception { return "Cotizacion DK"; 
  }
  public Class<? extends JBaseForm> getFormBase() throws Exception { 
return FormCotizacionDK.class; 
  }
  public String  getKeyField() throws Exception { return "id_cot"; }
  public String  getDescripField() { 
    	return "dk"; 
  }
  public BizCotizacionDK GetcDato() throws Exception { return (BizCotizacionDK) this.getRecord(); }

  public void createActionMap() throws Exception {
			super.createActionMap();
	}  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	return super.OkAction(a);
  }
	
	
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  
  	return super.getSubmitFor(a);
  }
  

 }
