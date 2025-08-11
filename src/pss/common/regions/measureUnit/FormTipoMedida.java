package  pss.common.regions.measureUnit;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormTipoMedida extends JBaseForm {

  public FormTipoMedida() {
  }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "Tipo"       , CHAR, REQ, "Tipo"      );
    AddItemEdit( "Descrip"    , CHAR, REQ, "descripcion"      );
    AddItemEdit( "Decimales"  , UINT, REQ, "decimales"      );
    AddItemTabPanel().AddItemTab(10);
  }


}

