package  pss.common.moduleConfigMngr.actions;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiModuleConfigurationAction extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiModuleConfigurationAction() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizModuleConfigurationAction(); }
  public int GetNroIcono()   throws Exception { return 410; }
  public String GetTitle()   throws Exception { return "Accion Modulo Configuracion "; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormModuleConfigurationAction.class; }
  public String  getKeyField() throws Exception { return "action_sequence"; }
  public String  getDescripField() { return "company"; }
  public BizModuleConfigurationAction GetcDato() throws Exception { return (BizModuleConfigurationAction) this.getRecord(); }

 }
