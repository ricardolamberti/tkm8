package  pss.common.agenda.historia;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiEventoHistoria extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiEventoHistoria() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizEventoHistoria(); }
  public int GetNroIcono()   throws Exception { return 10058; }
  public String GetTitle()   throws Exception { return "Evento Historia"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormEventoHistoria.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "texto"; }
  public BizEventoHistoria GetcDato() throws Exception { return (BizEventoHistoria) this.getRecord(); }
  public void createActionMap() throws Exception {
  }
 }
