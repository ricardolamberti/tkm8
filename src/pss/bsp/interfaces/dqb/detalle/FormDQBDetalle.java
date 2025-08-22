package  pss.bsp.interfaces.dqb.detalle;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormDQBDetalle extends JBaseForm {


private static final long serialVersionUID = 1245253775814L;



/**
   * Constructor de la Clase
   */
  public FormDQBDetalle() throws Exception {
   }

  public GuiDQBDetalle getWin() { return (GuiDQBDetalle) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
 
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "owner" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "idPDF" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "linea" ).setHide(true);
    AddItemEdit( "Placa", CHAR, OPT, "id_aerolinea" ).setSizeColumns(3).setReadOnly(true);
    AddItemDateTime( "Fecha", DATE, OPT, "fecha" ).setSizeColumns(3).setReadOnly(true);
    AddItemEdit( "Gds", CHAR, OPT, "gds" ).setSizeColumns(3).setReadOnly(true);
    AddItemEdit( "Tipo doc.", CHAR, OPT, "tipo_doc" ).setSizeColumns(3).setReadOnly(true);
    AddItemEdit( "Boleto", CHAR, OPT, "boleto" ).setSizeColumns(3).setReadOnly(true);
    AddItemEdit( "PNR", CHAR, OPT, "pnr" ).setSizeColumns(3).setReadOnly(true);
    AddItemEdit( "Status", CHAR, OPT, "status" ).setSizeColumns(3).setReadOnly(true);

  }



}  //  @jve:decl-index=0:visual-constraint="10,10" 
