package  pss.bsp.regions.detalle;

import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormRegionDetail extends JBaseForm {


private static final long serialVersionUID = 1245253307166L;

/**
   * Constructor de la Clase
   */
  public FormRegionDetail() throws Exception {
  }

  public GuiRegionDetail getWin() { return (GuiRegionDetail) getBaseWin(); }

 
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemCombo( "Pais", CHAR, REQ, "pais" , new GuiPaisesLista());
    AddItemEdit( null, CHAR, OPT, "id" );
    AddItemEdit( null, CHAR, OPT, "id_region" );
    AddItemEdit( null, CHAR, OPT, "company" );

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
