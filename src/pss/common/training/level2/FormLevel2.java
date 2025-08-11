package pss.common.training.level2;

import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormLevel2 extends JBaseForm {


private static final long serialVersionUID = 1218486819468L;

  /**
   * Propiedades de la Clase
   */

  /**
   * Constructor de la Clase
   */
  public FormLevel2() throws Exception {
  }

  public GuiLevel2 GetWin() { return (GuiLevel2) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "id", LONG, OPT, "id" ).setVisible(false);
    AddItemEdit( "descripcion", CHAR, REQ, "descripcion" );
    AddItemWinLov( "winlov simple", CHAR, OPT, "winlov1" , new GuiPaisesLista()).setMultiple(false);
    AddItemWinLov( "winlov multiple", CHAR, OPT, "winlov2" , new GuiPaisesLista()).setMultiple(true);
    AddItemFile( "Archivo", CHAR, OPT, "file" );
    AddItemColor( "color", COLOUR, OPT, "color" );
    AddItemArea( "texto", CHAR, OPT, "texto" );
    AddItemMap( "mapa", CHAR, OPT, "mapa" );
    
  } 

}  //  @jve:decl-index=0:visual-constraint="-382,-30"
