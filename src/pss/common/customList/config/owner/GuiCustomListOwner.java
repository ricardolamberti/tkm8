package pss.common.customList.config.owner;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.forms.JBaseForm;

public class GuiCustomListOwner extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiCustomListOwner() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizCustomListOwner(); }
  public int GetNroIcono()   throws Exception { return 10064; }
  public String GetTitle()   throws Exception { return "Permisos"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCustomListOwner.class; }
  public String  getKeyField() throws Exception { return "secuencia"; }
  public String  getDescripField() { return "secuencia"; }
  public BizCustomListOwner GetcDato() throws Exception { return (BizCustomListOwner) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		createActionQuery();
		createActionUpdate();
		createActionDelete();
  }
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	return super.getSubmitFor(a);
  }

 }
