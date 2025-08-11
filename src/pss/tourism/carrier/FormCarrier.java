package pss.tourism.carrier;

import java.awt.Dimension;
import java.awt.Rectangle;

import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;

public class FormCarrier extends JBaseForm {

private static final long serialVersionUID = 1L;

  /**
   * Propiedades de la Clase
   */



  /**
   * Constructor de la Clase
   */
  public FormCarrier() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiCarrier GetWin() { return (GuiCarrier) GetBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {

  }
  /**
   * Linkeo los campos con la variables del form
   */
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	AddItemRow().AddItemEdit("Código", CHAR, REQ, "carrier" );
    AddItemRow().AddItemEdit("Descripción",  CHAR, REQ, "description" );
    AddItemRow().AddItemEdit("Cod. IATA",  CHAR, OPT, "cod_iata" );
    
    AddItemRow().AddItemCombo("Módulo", CHAR, OPT, "cod_analisis", JWins.createVirtualWinsFromMap(BizCarrier.getLogicas()));
    AddItemRow().AddItemEdit("DK",  CHAR, OPT, "dk" ).setSizeColumns(4);
    AddItemRow().AddItemEdit("eMail",  CHAR, OPT, "email" ).setSizeColumns(12);
    AddItemRow().AddItemEdit("Uso CFDI",  CHAR, OPT, "cfdi" ).setSizeColumns(4);
    AddItemRow().AddItemEdit("Forma pago",  CHAR, OPT, "forma_pago" ).setSizeColumns(4);
    
  }

}  //  @jve:decl-index=0:visual-constraint="10,10" 

