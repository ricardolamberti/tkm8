package  pss.common.agenda.participantes;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiEventoParticipante extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiEventoParticipante() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizEventoParticipante(); }
  public int GetNroIcono()   throws Exception { return 15008; }
  public String GetTitle()   throws Exception { return "Participante"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormEventoParticipante.class; }
  public String  getKeyField() throws Exception { return "id_persona"; }
  public String  getDescripField() { return "rol"; }
  public BizEventoParticipante GetcDato() throws Exception { return (BizEventoParticipante) this.getRecord(); }

 }
