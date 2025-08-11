package  pss.common.security.license.license;

import pss.common.security.license.typeLicense.GuiTypeLicenses;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;

public class FormLicense extends JBaseForm {


private static final long serialVersionUID = 1246199349219L;

  /**
   * Propiedades de la Clase
   */

  /**
   * Constructor de la Clase
   */
  public FormLicense() throws Exception {
  }

  public GuiLicense getWin() { return (GuiLicense) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setVisible(false);
    AddItemEdit( "Licencia", UINT, REQ, "id_license" );
    AddItemCombo( "Tipo Licencia", CHAR, REQ, "id_typeLicense" , new JControlCombo() {
	    @Override
	    public JWins getRecords(boolean one) throws Exception {
	    	return getTiposLicencias(one);
	    }
	    });
    AddItemEdit( "Clave", UINT, REQ, "clave" );
    
    autoBuildForm(getInternalPanel(), zWin);


  } 
  
	  
  public JWins getTiposLicencias(boolean one)  throws Exception {
  	GuiTypeLicenses guis = new GuiTypeLicenses();
   	if (one) {
	  	guis.getRecords().addFilter("company", this.getWin().GetcDato().getCompany());
	  	guis.getRecords().addFilter("id_typeLicense", this.getWin().GetcDato().getIdtypelicense());
  	} else {
	  	guis.getRecords().addFilter("company", this.findControl("company").getValue());
  	}
  		
  	return guis;
  }

  
}  //  @jve:decl-index=0:visual-constraint="10,10" 
