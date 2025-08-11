package  pss.common.personalData.types;

import pss.common.personalData.GuiEstadosCiviles;
import pss.common.personalData.GuiPersona;
import pss.common.personalData.GuiTiposDoc;
import pss.common.regions.divitions.GuiPaisesLista;
import pss.common.security.BizUsuario;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;

public abstract class FormPersona extends JBaseForm {
	
	private static final long serialVersionUID = 1L;

  public GuiPersona GetWin() {
    return (GuiPersona) GetBaseWin();
  }


  public JWins ObtenerPaises(boolean zOneRow) throws Exception {
//    getControles().findControl("tipo_doc").clear();
//    getControles().findControl("estado_civil").clear(); RJL no es el lugar para limpiar variables....
    return new GuiPaisesLista();
  }
  public JWins ObtenerPaisesMasUsados() throws Exception {
  	GuiPaisesLista paises =  new GuiPaisesLista();
  	paises.addFilterAdHoc("pais", BizUsuario.getUsr().getBirthCountryId());
  	return paises;
}


  public JWins ObtenerTiposDocs(boolean zOneRow) throws Exception {
    GuiTiposDoc oTiposDoc = new GuiTiposDoc();
    if ( zOneRow ) {
    	oTiposDoc.getRecords().addFilter("pais", GetWin().GetcDato().GetNacionalidad());
      oTiposDoc.getRecords().addFilter("tipo_doc", GetWin().GetcDato().GetTipoDoc());
    } else {
      oTiposDoc.getRecords().addFilter("pais", getControles().findControl("nacionalidad").getValue());
    }
    return oTiposDoc;
  }

  public JWins ObtenerEstadoCivil(boolean zOneRow) throws Exception {
    GuiEstadosCiviles oEstadoCivil = new GuiEstadosCiviles();
    if ( zOneRow ) {
      oEstadoCivil.getRecords().addFilter("id_estadocivil", GetWin().GetcDato().GetEstadoCivil());
    } else {
      oEstadoCivil.getRecords().addFilter("id_pais", getControles().findControl("nacionalidad").getValue());
    }
    return oEstadoCivil;
  }
  
  @Override
  public void checkControls(String sControlId) throws Exception {
  	if (sControlId.contentEquals("nacionalidad")) {
  		changePais();
  	}
  	super.checkControls(sControlId);
  }
  public void changePais() throws Exception {
	  getControles().findControl("tipo_doc").clear();
	  getControles().findControl("estado_civil").clear();
  }
}
