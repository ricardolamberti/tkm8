package pss.core.tools.virtualGraph;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormVirtualGraph extends JBaseForm {


  public FormVirtualGraph() throws Exception {
  }

  public GuiVirtualGraph getWin() { return (GuiVirtualGraph) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "company", CHAR, OPT, "company" ).hide();;
    AddItemEdit( "idC", CHAR, OPT, "id" );
    AddItemEdit( "linea", CHAR, OPT, "linea" );

    
  } 
} 
