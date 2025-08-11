package  pss.common.agenda.turno.laborables;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;

public class FormTurnoLaborable extends JBaseForm {


private static final long serialVersionUID = 1352314950136L;

  /**
   * Propiedades de la Clase
   */


/**
   * Constructor de la Clase
   */
  public FormTurnoLaborable() throws Exception {
   }

  public GuiTurnoLaborable getWin() { return (GuiTurnoLaborable) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, UINT, OPT, "id" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_agenda" ).setHide(true);
    AddItemCombo( "Día semana", LONG, REQ, "dia" , JWins.createVirtualWinsFromMap(BizTurnoLaborable.getDias()));
    AddItemDateTime("Hora desde", HOUR, REQ, "hora_desde" ).SetValorDefault("09:00:00");
    AddItemDateTime("Hora hasta", HOUR, REQ, "hora_hasta" ).SetValorDefault("18:00:00");
    AddItemEdit( "Duración turnos (minutos)", LONG, OPT, "duracion" ).SetValorDefault(0);
    AddItemEdit( "Separación turnos (minutos)", LONG, OPT, "separacion" ).SetValorDefault(0);

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
