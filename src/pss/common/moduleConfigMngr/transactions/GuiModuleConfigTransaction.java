package  pss.common.moduleConfigMngr.transactions;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiModuleConfigTransaction extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiModuleConfigTransaction() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizModuleConfigTransaction(); }
  public int GetNroIcono()   throws Exception { return 924; }
  public String GetTitle()   throws Exception { return "Transaccion Configuracion Modulo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormModuleConfigTransaction.class; }
  public String  getKeyField() throws Exception { return "module_id"; }
  public String  getDescripField() { return "module_id"; }
  public BizModuleConfigTransaction GetcDato() throws Exception { return (BizModuleConfigTransaction) this.getRecord(); }

 }
