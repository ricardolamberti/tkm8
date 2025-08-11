package  pss.common.agenda.turno.ausencias;

import java.util.Date;

import pss.core.tools.JDateTools;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormTurnoAusencia extends JBaseForm {



  /**
   * Constructor de la Clase
   */
  public FormTurnoAusencia() throws Exception {
   }

  public GuiTurnoAusencia getWin() { return (GuiTurnoAusencia) getBaseWin(); }

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
    AddItemEdit( "Razón", CHAR, REQ, "razon" );
    AddItemDateTime( "Fecha desde", DATETIME, REQ, "fecha_desde" ).SetValorDefault(JDateTools.getDateStartDay(new Date()));
    AddItemDateTime( "Fecha hasta", DATETIME, REQ, "fecha_hasta" ).SetValorDefault(JDateTools.getDateEndDay(new Date()));
    
  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
