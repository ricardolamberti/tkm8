package  pss.common.agenda.turno.laborables;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiTurnoLaborable extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiTurnoLaborable() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizTurnoLaborable(); }
  public int GetNroIcono()   throws Exception { return 2003; }
  public String GetTitle()   throws Exception { return "Horario laboral"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormTurnoLaborable.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizTurnoLaborable GetcDato() throws Exception { return (BizTurnoLaborable) this.getRecord(); }
 
 }
