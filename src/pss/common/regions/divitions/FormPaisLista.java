package  pss.common.regions.divitions;

import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIconGalerys;

public class FormPaisLista extends JBaseForm {

  public FormPaisLista() throws Exception {
  }


  public GuiPaisLista GetWin() {
    return (GuiPaisLista) this.getBaseWin();
  }


  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "pais", CHAR, REQ, "pais" );
    AddItemEdit( "descripcion", CHAR, REQ, "descripcion" );
    AddItemWinLov( "Icono", CHAR, OPT, "icono_file", new JControlWin() {
    	public JWins getRecords(boolean one) throws Exception {
    		return GuiIconGalerys.GetGlobal(); 
    	}
    });
    AddItemEdit( "gentilicio", CHAR, OPT, "gentilicio" );
  }

}  
