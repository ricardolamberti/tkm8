package pss.common.documentos.docEmail;

import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiMailBorradores extends GuiDocEmails {

  public GuiMailBorradores() throws Exception {
  }

  public String  GetTitle()    throws Exception  { return "Borradores"; }

  
  
  @Override
  public void ConfigurarFiltros(JFormFiltro filters) throws Exception {
  	JFormControl c;
  	filters.addEditResponsive("Para", "mail_to").setFilterNeverHide(true).setOperator("like");
  	filters.addEditResponsive("Asunto", "doc_titulo").setFilterNeverHide(true).setOperator("like");
  	filters.addComboResponsive("Usuario", "doc_usuario", new JControlCombo() {
  		public JWins getRecords(boolean one) throws Exception {
  			return getUsuarios();
  		};
  	}, true).setFilterNeverHide(true);

  }
  
//  public int getImportance(int id) {
//  	return id==17?1:0;
//  }
  
}
