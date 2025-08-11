package  pss.common.regions.divitions;

import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;

public class FormCiudadCP extends JBaseForm {


  public FormCiudadCP() throws Exception {
  }

  public GuiCiudadCP GetWin() { return (GuiCiudadCP) getBaseWin(); }


  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemCombo( "pais", CHAR, REQ, "pais" , new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getPaises(one);
    	}
    });
    AddItemCombo( "provincia", CHAR, REQ, "provincia" , new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getProvincias(one);
    	}
    });
    AddItemCombo( "ciudad", CHAR, OPT, "ciudad_id" , new JControlWin() {
    	@Override
    	public JWins getRecords(boolean bOneRow) throws Exception {
    		return getCiudades(bOneRow);
    	}
    });
    AddItemEdit( "codpostal", CHAR, OPT, "cod_postal" );
  }
  public JWins getPaises(boolean one) throws Exception {
  	GuiPaises p = new GuiPaises();
  	if (one) {
  		p.getRecords().addFilter("pais", GetWin().GetcDato().getPais());
  	}
  	return p;
  }
  public JWins getProvincias(boolean one) throws Exception {
  	GuiProvincias p = new GuiProvincias();
		p.getRecords().addFilter("pais", GetWin().GetcDato().getPais());
  	if (one) {
  		p.getRecords().addFilter("provincia", GetWin().GetcDato().getProvincia());
  	}
  	return p;
  }
  public JWins getCiudades(boolean one) throws Exception {
  	GuiCiudades c = new GuiCiudades();
  	c.setModeWinLov(true);
		c.getRecords().addFilter("pais", GetWin().GetcDato().getPais());
		c.getRecords().addFilter("provincia", GetWin().GetcDato().getProvincia());
		c.SetVision("SINCP");
  	if (one) {
  		c.getRecords().addFilter("ciudad_id", GetWin().GetcDato().getCiudad());
  	} else {
    	c.setRefreshOnlyOnUserRequest(true);
  	}
  	return c;
  }
}  
