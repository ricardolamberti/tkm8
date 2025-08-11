package pss.common.regions.divitions;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;

public class FormCiudad extends JBaseForm {
  

  public GuiCiudad GetWin() { return (GuiCiudad) this.getBaseWin(); }

  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "pais", CHAR, REQ, "pais" );
    AddItemEdit( "provincia", CHAR, REQ, "provincia" );
    AddItemEdit( "ciudad", CHAR, OPT, "ciudad_id" );
    AddItemCombo( "departamento", CHAR, OPT, "departamento_id" , new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getDepartamentos(one);
    	}
    }).SetReadOnly(true);
    AddItemEdit( "descripcion", CHAR, REQ, "ciudad" );
    AddItemEdit( "abbr", CHAR, OPT, "abreviatura" );
    AddItemTabPanel().AddItemTab(40 );
    // lciudad.setText(GetWin().getTipoCiudad());
  }

  public JWins getDepartamentos(boolean one) throws Exception {
  	return new  GuiDepartamentos();
  }
  


}  
