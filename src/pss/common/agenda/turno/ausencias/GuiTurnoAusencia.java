package  pss.common.agenda.turno.ausencias;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiTurnoAusencia extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiTurnoAusencia() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizTurnoAusencia(); }
  public int GetNroIcono()   throws Exception { return 2003; }
  public String GetTitle()   throws Exception { return "Ausencias"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormTurnoAusencia.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "razon"; }
  public BizTurnoAusencia GetcDato() throws Exception { return (BizTurnoAusencia) this.getRecord(); }
 
 }
