package pss.common.mail;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;


public class FormConsolaMail extends JBaseForm {

	  public FormConsolaMail() throws Exception {
	  }

	  public GuiConsolaMail getWin() { return (GuiConsolaMail) getBaseWin(); }

	  public void InicializarPanel( JWin zWin ) throws Exception {
	  	JFormTabPanelResponsive tab = this.AddItemTabPanel();
	  	tab.AddItemTab(10);
	  	tab.AddItemTab(20);
	  	tab.AddItemTab(30);
	  }

	}
