package  pss.common.event.mailing.type;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormMailingPersonaType extends JBaseForm {


private static final long serialVersionUID = 1218223408500L;

/**
   * Constructor de la Clase
   */
  public FormMailingPersonaType() throws Exception {
  }

  public GuiMailingPersonaType GetWin() { return (GuiMailingPersonaType) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
  	
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_tipo" ).setHide(true);
    AddItemEdit( "Denominacion", CHAR, REQ, "descripcion" );
    AddItemCombo( "Tipo", CHAR, REQ, "tipo" );
   // AddItemEdit( "Modelo reporte", CHAR, OPT, "reporte" );
      
    AddItemTabPanel().AddItemList(10);
  }
}  //  @jve:decl-index=0:visual-constraint="80,14"
