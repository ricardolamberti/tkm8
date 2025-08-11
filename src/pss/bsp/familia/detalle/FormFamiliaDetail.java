package  pss.bsp.familia.detalle;

import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.carrier.GuiCarriers;

public class FormFamiliaDetail extends JBaseForm {


private static final long serialVersionUID = 1245253307166L;


/**
   * Constructor de la Clase
   */
  public FormFamiliaDetail() throws Exception {
   }

  public GuiFamiliaDetail getWin() { return (GuiFamiliaDetail) getBaseWin(); }

 
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemWinLov( "Aerolinea", CHAR, REQ, "aerolinea" , new JControlWin() {
    	@Override
    	public JWins getRecords(boolean bOneRow) throws Exception {
    		return getAerolineas(bOneRow);
    	}
    });
    AddItemEdit( null, CHAR, OPT, "id" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_familia" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( "Letra", CHAR, REQ, "letra" );
    AddItemEdit( "Prioridad (el sistema tomará el de prioridad mas alta)", CHAR, REQ, "prioridad" );

  }

  public JWins getAerolineas(boolean one) throws Exception {
  	GuiCarriers carrs= new GuiCarriers();
  	if (one) {
  		carrs.getRecords().addFilter("carrier", getWin().GetcDato().getCarrier());
  	}
  	carrs.setModeWinLov(true);
  	return carrs;
  }

}  //  @jve:decl-index=0:visual-constraint="10,10" 
