package pss.common.documentos.docEmail;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;

public class GuiMailConversaciones extends GuiDocEmails {


  /**
   * Constructor de la Clase
   */
  public GuiMailConversaciones() throws Exception {
  }

  
  public int     GetNroIcono() throws Exception  { return 63; } 
  public String  GetTitle()    throws Exception  { return "Conversaciones"; }
  public Class<? extends JWin>  GetClassWin()    { return GuiMailConversacion.class; }

  
  @Override
  public void ConfigurarFiltros(JFormFiltro filters) throws Exception {
  	filters.addEditResponsive("De/Para", "grupo_email").setFilterNeverHide(true).setOperator("like");
  	filters.addEditResponsive("Asunto", "doc_titulo").setFilterNeverHide(true).setOperator("like");
  	filters.addComboResponsive("Usuario", "doc_usuario", new JControlCombo() {
  		public JWins getRecords(boolean one) throws Exception {
  			return getUsuarios();
  		};
  	}, true).setFilterNeverHide(true);
  }
  
  @Override
  protected void asignFilterByControl(JFormControl control) throws Exception {
  	if (control.getIdControl().equals("no_respondidos")) {
  		if (control.getValue().equals("S"))
  			this.getRecords().addFilter("respuesta", false);
  		else
  			this.getRecords().clearFilter("respuesta");
  		return;
  	}
  		
  	super.asignFilterByControl(control);
  }
  

	public boolean forceCleanIdemHistory() throws Exception {
		return true;
	}

	public boolean isCleanHistory() throws Exception {
		return true;
	}
	

}
