package pss.common.documentos.docEmail;

import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiMailRecives extends GuiDocEmails {


  /**
   * Constructor de la Clase
   */
  public GuiMailRecives() throws Exception {
  }

  public String  GetTitle()    throws Exception  { return "Recibidos"; }

  
  
  @Override
  public void ConfigurarFiltros(JFormFiltro filters) throws Exception {
  	JFormControl c;
  	filters.addEditResponsive("De ", "mail_from").setFilterNeverHide(true).setOperator("like");
  	filters.addEditResponsive("Asunto", "doc_titulo").setFilterNeverHide(true).setOperator("like");
  	filters.addComboResponsive("Usuario", "doc_usuario", new JControlCombo() {
  		public JWins getRecords(boolean one) throws Exception {
  			return getUsuarios();
  		};
  	}, true).setFilterNeverHide(true);

  	c=filters.addCheckResponsive("No Leidos", "leido");
  	c.setRefreshForm(true);
  	c.setIdControl("no_leidos");;
  	c=filters.addCheckResponsive("No Respondidos", "respuesta");
  	c.setIdControl("no_respondidos");
  	c.setRefreshForm(true);
  }
  

}
