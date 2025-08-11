package pss.core.winUI.icons;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormIcon extends JBaseForm {

  public FormIcon() {
  }



  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "numero"     , UINT, "OPT",  "nro_icono"    );
    AddItemEdit( "Descrip" , CHAR, REQ,  "descripcion");
    AddItemCombo( "File"    , CHAR, REQ,  "nombre_file", GuiIconGalerys.GetGlobal()  );
  }

}

