package pss.common.version.storeAndForward.detail;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiStoreForward extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiStoreForward() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizStoreForward(); }
  public int GetNroIcono()   throws Exception { return 10039; }
  public String GetTitle()   throws Exception { return "Envios a HO"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormStoreForward.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "key_message"; }
  public BizStoreForward GetcDato() throws Exception { return (BizStoreForward) this.getRecord(); }

  
	@Override
	public void createActionMap() throws Exception {
		createActionQuery();
	}
 

 }
