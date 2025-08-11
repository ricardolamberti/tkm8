package  pss.common.event.mailing.type.detail;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiMailingPersonaTypeDetail extends JWin {


  /**
   * Constructor de la Clase
   */
  public GuiMailingPersonaTypeDetail() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizMailingPersonaTypeDetail(); }
  public int GetNroIcono()   throws Exception { return 10036; }
  public String GetTitle()   throws Exception { return "Detalle"; 
  }
  public Class<? extends JBaseForm> getFormBase() throws Exception { 
return FormMailingPersonaTypeDetail.class; 
  }
  public String  getKeyField() throws Exception { return "id_detail"; }
  public String  getDescripField() { 
    	return "dk"; 
  }
  public BizMailingPersonaTypeDetail GetcDato() throws Exception { return (BizMailingPersonaTypeDetail) this.getRecord(); }

  
 }
