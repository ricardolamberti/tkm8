package pss.common.remotedesktop;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;

public class FormRemoteDesktop extends JBaseForm {

  public FormRemoteDesktop() throws Exception {
  }

  public GuiRemoteDesktop getWin() { return (GuiRemoteDesktop) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "Directorio de trabajo", CHAR, OPT, "wd" ,10);
    AddItemEdit( "Timeout",LONG, OPT, "timeout" ,2).SetValorDefault(60000);
    JFormFieldsetResponsive upl = AddItemFieldset("Upload");
    upl.AddItemFile( "Upload", CHAR, OPT, "upload" ).setSizeColumns(10);
    upl.AddItemButton("Upload", 20, true).setSizeColumns(2).setResponsiveClass("btn btn-primary");
    JFormFieldsetResponsive cmd = AddItemFieldset("Ejecutar");
    cmd.AddItemRow().AddItemCheck( null,  CHAR, OPT, "cmd","S","N","CMD","SSH" ).setSizeColumns(2).SetValorDefault(true);
    cmd.AddItemEdit( null, CHAR, OPT, "comando" ,10);
    cmd.AddItemButton("Run", 10, true).setSizeColumns(2).setResponsiveClass("btn btn-primary");
    cmd.AddItemArea( "resultado", CHAR, OPT, "resultado" ).setHeight(400);
  }
  
  @Override
  public void checkControls() throws Exception {

  	super.checkControls();
  }
} 
