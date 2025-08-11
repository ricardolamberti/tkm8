package  pss.bsp.consolid.model.gruposDK.detail;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiGrupoDKDetail extends JWin {


  /**
   * Constructor de la Clase
   */
  public GuiGrupoDKDetail() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizGrupoDKDetail(); }
  public int GetNroIcono()   throws Exception { return 10036; }
  public String GetTitle()   throws Exception { return "Detalle"; 
  }
  public Class<? extends JBaseForm> getFormBase() throws Exception { 
return FormGrupoDKDetail.class; 
  }
  public String  getKeyField() throws Exception { return "id_detail"; }
  public String  getDescripField() { 
    	return "dk"; 
  }
  public BizGrupoDKDetail GetcDato() throws Exception { return (BizGrupoDKDetail) this.getRecord(); }

  
 }
