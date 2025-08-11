package  pss.bsp.consola.referencia;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiReferencia extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiReferencia() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizReferencia(); }
  public int GetNroIcono()   throws Exception { return (int)GetcDato().getIcono(); }
  public String GetTitle()   throws Exception { return "Referencia"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormReferencia.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizReferencia GetcDato() throws Exception { return (BizReferencia) this.getRecord(); }
  public void createActionMap() throws Exception {
  };
  
  
 }
