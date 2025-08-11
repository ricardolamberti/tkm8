package  pss.common.agenda.turno;

import pss.common.agenda.evento.BizEvento;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormTurno extends JBaseForm {



  /**
   * Constructor de la Clase
   */
  public FormTurno() throws Exception {
 }
	
  public GuiTurno getWin() { return (GuiTurno) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_agenda").setHide(true);
    AddItemEdit( null, CHAR, OPT, "owner").setHide(true);
    AddItemEdit( "Descripción", CHAR, REQ, "descripcion" );
    AddItemEdit( "Tiempo turnos (minutos)", LONG, REQ, "tiempo_turnos" ).SetValorDefault(25);
    AddItemEdit( "Tiempo separación turnos (minutos)", LONG, REQ, "tiempo_separacion_turnos" ).SetValorDefault(5);
    AddItemCheck( "Admite hitos", CHAR, "use_hito" ).SetValorDefault(false);
    AddItemCheck( "Admite Alarmas", CHAR, "use_alarma" ).SetValorDefault(false);
    AddItemCheck( "Admite Participantes", CHAR, "use_participantes" ).SetValorDefault(false);
    AddItemCombo( "Estado incial", CHAR, OPT, "estado_inicial" ).SetValorDefault(BizEvento.ACTIVA);
    AddItemCombo( "Tipo evento por defecto", LONG, OPT, "id_tipo_evento_default" ).setFirstOcur(true);
    if (isAlta()) return;
    JFormTabPanelResponsive tabpanels = AddItemTabPanel();
    tabpanels.AddItemList( 10);
    tabpanels.AddItemList( 20); 
  }


}  //  @jve:decl-index=0:visual-constraint="10,10" 
