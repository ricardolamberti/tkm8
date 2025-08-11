package pss.common.regions.divitions;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormPaisListaEmbedded extends JBaseForm {


  public GuiPaisLista GetWin() {
    return (GuiPaisLista) this.getBaseWin();
  }


  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "Código pais", CHAR, REQ, "pais" );
    AddItemEdit( "Descripción", CHAR, REQ, "descripcion" );
  }


}  //  @jve:decl-index=0:visual-constraint="10,10"
