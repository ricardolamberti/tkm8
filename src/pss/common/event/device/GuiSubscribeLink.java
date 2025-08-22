package pss.common.event.device;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActExternalLink;
import pss.core.winUI.forms.JBaseForm;

public class GuiSubscribeLink extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiSubscribeLink() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizSubscribeLink(); }
  public int GetNroIcono()   throws Exception { return 900; }
  public String GetTitle()   throws Exception { return "Type Device"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormSubscribeLink.class; }
  public String  getKeyField() throws Exception { return "id_typedevice"; }
  public String  getDescripField() { return "usuario"; }
  public BizSubscribeLink GetcDato() throws Exception { return (BizSubscribeLink) this.getRecord(); }

	
  public void createActionMap() throws Exception {
   	addAction(10,   "suscribir", null, 6011, false, false).setNuevaVentana(true).setPostFunction("goRefresh()");
	}  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	return super.OkAction(a);
  }
	
	
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
	 	if (a.getId()==10) return new JActExternalLink(suscribir());
  	return super.getSubmitFor(a);
  }
  
  public String suscribir() throws Exception {
  	return GetcDato().execProcessSubscribir();
  }
  
 }
