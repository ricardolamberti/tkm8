package  pss.common.personalData;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormEstadoCivil extends JBaseForm {

  public FormEstadoCivil() throws Exception {
  }

  public GuiEstadoCivil GetWin() { return (GuiEstadoCivil) getBaseWin(); }


  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	SetExitOnOk(true);
    AddItemEdit( "pais", CHAR, REQ, "id_pais" );
    AddItemEdit( "id_estadoCivil", CHAR, REQ, "id_estadoCivil" );
    AddItemEdit( "descripcion", CHAR, REQ, "descripcion" );
    AddItemEdit( "femenino", CHAR, REQ, "descrip_fem" );
    AddItemEdit( "masculino", CHAR, REQ, "descrip_masc" );
    //AddItem( requiereConyuge, CHAR, REQ, "requiereConyuge" );

  }
  
} 
