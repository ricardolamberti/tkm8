package pss.common.tableGenerator;

import java.awt.Dimension;
import java.awt.Rectangle;

import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;


public class FormTable extends JBaseForm {
  //-------------------------------------------------------------------------//
  // Constructor de la clase
  //-------------------------------------------------------------------------//
  public FormTable()  {
  }

  //-------------------------------------------------------------------------//
  // Dibujo el form
  //-------------------------------------------------------------------------//
  protected void jbInit() throws Exception {
  }

  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "Tabla"   , CHAR , REQ,  "TABLE_NAME"    );

  }


}

