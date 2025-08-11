package  pss.common.regions.measureUnit;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormUnidadMedida extends JBaseForm {

  public FormUnidadMedida() {
  }


  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "Medida"    , CHAR, REQ, "Unidad_Medida"      );
    AddItemEdit( "Descrip"   , CHAR, REQ, "descripcion"      );
    AddItemEdit( "Abrev"     , CHAR, REQ, "Abreviatura"   );
    AddItemCombo( "Tipo", CHAR, REQ, "Tipo" , new GuiTipoMedidas());
    AddItemEdit( "Factor"  , UFLOAT, REQ, "factor"   );
  }


}

