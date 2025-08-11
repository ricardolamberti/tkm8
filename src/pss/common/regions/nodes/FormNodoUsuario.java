package  pss.common.regions.nodes;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormNodoUsuario extends JBaseForm {

  public FormNodoUsuario() throws Exception {
  }


  public GuiNodoUsuario GetWin() {
    return (GuiNodoUsuario) getBaseWin();
  }


  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormPanelResponsive col = this.AddItemColumn(3);
    col.AddItemEdit( "company", CHAR, REQ, "company").hide();
    col.AddItemEdit( "Usuario", CHAR, REQ, "usuario");
    col.AddItemCombo( "Sucursal", CHAR, REQ, "nodo"   , new JControlCombo() {
    	public JWins getRecords(boolean zOneRow) throws Exception {
    		return ObtenerNodos(zOneRow);
    	}
    } );
  }

  private JWins ObtenerNodos(boolean zOneRow) throws Exception {
    GuiNodos oSucursal = new GuiNodos();
    if ( zOneRow ) {
      oSucursal.getRecords().addFilter("company", GetWin().GetcDato().getCompany());
      oSucursal.getRecords().addFilter("nodo", GetWin().GetcDato().getNode());
    } else {
      oSucursal.getRecords().addFilter("company", getControles().findControl("company").getValue());
    }
    return oSucursal;
  }


}  
