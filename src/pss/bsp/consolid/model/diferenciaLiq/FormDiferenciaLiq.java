package pss.bsp.consolid.model.diferenciaLiq;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;

public class FormDiferenciaLiq extends JBaseForm {


private static final long serialVersionUID = 1247604035440L;


  /**
   * Constructor de la Clase
   */
  public FormDiferenciaLiq() throws Exception {
     }

  public GuiDiferenciaLiq getWin() { return (GuiDiferenciaLiq) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */

  
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null, CHAR, REQ, "id_liq" ).setHide(true);
    AddItemEdit( null, UINT, REQ, "linea" ).setHide(true);
    AddItemRow().AddItemCombo( "Estado", CHAR, OPT, "status" , JWins.CreateVirtualWins(GuiDiferenciaLiqs.ObtenerResultadosConsolidacion())).setSizeColumns(4).SetReadOnly(true);
    AddItemRow().AddItemEdit( "Boleto", CHAR, OPT, "boleto" ).setSizeColumns(4).SetReadOnly(true);
    AddItemRow().AddItemEdit( "Operacion", CHAR, OPT, "operacion" ).setSizeColumns(4).SetReadOnly(true);
    AddItemRow().AddItemEdit( "Tipo ruta", CHAR, OPT, "tipo_ruta" ).setSizeColumns(4).SetReadOnly(true);
    AddItemRow().AddItemEdit( "Aerolinea", CHAR, OPT, "id_aerolinea" ).setSizeColumns(4).SetReadOnly(true);
    AddItemRow().AddItemEdit( "Forma pago", CHAR, OPT, "formapago" ).setSizeColumns(4).SetReadOnly(true);
    AddItemRow().AddItemEdit( "Tarifa", CHAR, OPT, "tarifa" ).setSizeColumns(4).SetReadOnly(true);
    AddItemRow().AddItemEdit( "Contado", CHAR, OPT, "contado" ).setSizeColumns(4).SetReadOnly(true);
    AddItemRow().AddItemEdit( "Credito", CHAR, OPT, "credito" ).setSizeColumns(4).SetReadOnly(true);
    AddItemRow().AddItemEdit( "Comision", CHAR, OPT, "comision" ).setSizeColumns(4).SetReadOnly(true);
    AddItemRow().AddItemEdit( "Impuesto", CHAR, OPT, "impuesto" ).setSizeColumns(4).SetReadOnly(true);
    AddItemRow().AddItemEdit( "Total", CHAR, OPT, "total" ).setSizeColumns(4).SetReadOnly(true);
    
  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
