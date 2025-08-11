package  pss.common.event.mailing.type;

import pss.common.event.mailing.type.detail.GuiMailingPersonaTypeDetails;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiMailingPersonaType extends JWin {


  /**
   * Constructor de la Clase
   */
  public GuiMailingPersonaType() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizMailingPersonaType(); }
  public int GetNroIcono()   throws Exception { return 10036; }
  public String GetTitle()   throws Exception { return "Tipos"; 
  }
  public Class<? extends JBaseForm> getFormBase() throws Exception { 
return FormMailingPersonaType.class; 
  }
  public String  getKeyField() throws Exception { return "id_tipo"; }
  public String  getDescripField() { 
    	return "descripcion"; 
  }
  public BizMailingPersonaType GetcDato() throws Exception { return (BizMailingPersonaType) this.getRecord(); }

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
  
  public GuiMailingPersonaTypeDetails getDetalle() throws Exception {
  	GuiMailingPersonaTypeDetails devices = new GuiMailingPersonaTypeDetails();
  	devices.setRecords(GetcDato().getObjDetalle());
  	return devices;
  }
 }
