package pss.common.version.storeAndForward;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiStoreForwardHeader extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiStoreForwardHeader() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizStoreForwardHeader(); }
  public int GetNroIcono()   throws Exception { return 10039; }
  public String GetTitle()   throws Exception { return "Envios a HO"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormStoreForwardHeader.class; }
  public String  getKeyField() throws Exception { return "id_pack"; }
  public String  getDescripField() { return "description"; }
  public BizStoreForwardHeader GetcDato() throws Exception { return (BizStoreForwardHeader) this.getRecord(); }
 
  
	@Override
	public void createActionMap() throws Exception {
		createActionQuery();
	}


 }
