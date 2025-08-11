package pss.common.tableGenerator;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;



public class GuiTableGenerator extends JWin {

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiTableGenerator() throws Exception {
    setRecord( new BizTableGenerator() );
    setTitle("Generación automática");
    SetNroIcono  ( 121 );
    SetFormBase  ( FormTableGenerator.class );
  }
 
  @Override
  public Class<? extends JBaseForm> getFormBase() throws Exception {
  	return FormTableGenerator.class;
  }

}
