package pss.common.personalData;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormEstadoCivilEmbedded extends JBaseForm {



  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public FormEstadoCivilEmbedded() throws Exception {
 }

  public GuiEstadoCivil GetWin() { return (GuiEstadoCivil) getBaseWin(); }



 

  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	SetExitOnOk(true);
    AddItemEdit( null, CHAR, OPT, "id_pais" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_estadoCivil" ).setHide(true);
    AddItemEdit( "descripcion", CHAR, REQ, "descripcion" );
//    AddItem( femenino, CHAR, REQ, "descrip_fem" );
//    AddItem( masculino, CHAR, REQ, "descrip_masc" );
    //AddItem( requiereConyuge, CHAR, REQ, "requiereConyuge" );

  }
}  //  @jve:decl-index=0:visual-constraint="10,10"
