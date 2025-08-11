package  pss.common.agenda.feriado;

import java.util.Date;

import pss.core.tools.JDateTools;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormFeriado extends JBaseForm {

  public FormFeriado() throws Exception {
  }

  public GuiFeriado getWin() { return (GuiFeriado) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "id", UINT, OPT, "id" );
    AddItemEdit( "company", CHAR, OPT, "company" );
    AddItemEdit( "usuario", CHAR, REQ, "descripcion" );
    AddItemEdit( "fechaInicio", DATETIME, REQ, "fecha_inicio" ).SetValorDefault(JDateTools.getDateStartDay(new Date()));
    AddItemEdit( "fechaFin", DATETIME, REQ, "fecha_fin" ).SetValorDefault(JDateTools.getDateEndDay(new Date()));
    AddItemEdit( "anualizar", CHAR, OPT, "anual");

  } 
}
