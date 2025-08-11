package  pss.bsp.interfaces.copa.detalle;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormCopaDetalle extends JBaseForm {


private static final long serialVersionUID = 1245253775814L;

/**
   * Constructor de la Clase
   */
  public FormCopaDetalle() throws Exception {
   }

  public GuiCopaDetalle getWin() { return (GuiCopaDetalle) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
  	AddItemEdit( null, CHAR, REQ, "company" ).setVisible(false);
    AddItemEdit( null, CHAR, REQ, "owner" ).setVisible(false);
    AddItemEdit( null, CHAR, REQ, "idPDF" ).setVisible(false);
    AddItemEdit( null, UINT, REQ, "linea" ).setVisible(false);
    AddItemEdit( "Aerolinea", CHAR, REQ, "id_aerolinea" );
    AddItemEdit( "Origen", CHAR, REQ, "origen" );
    AddItemEdit( "Destino", CHAR, REQ, "destino" );
    AddItemEdit( "Marketing Id", CHAR, REQ, "marketing_id" );
    AddItemEdit( "fms", UFLOAT, REQ, "fms" );
  }


}  //  @jve:decl-index=0:visual-constraint="10,10" 
