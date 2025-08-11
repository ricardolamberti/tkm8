package pss.common.customForm;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiCustomFormFields extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiCustomFormFields() throws Exception {
  }


  @Override
	public int     GetNroIcono() throws Exception  { return 5104; } 
  @Override
	public String  GetTitle()    throws Exception  { return "Campos"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiCustomFormField.class; }
//  public JRecords ObtenerDatos() throws Exception { return new BizCtaSaldoFechas();}
  /**
   * Mapeo las acciones con las operaciones
   */
  @Override
	public void createActionMap() throws Exception {
    this.addActionNew( 1, "Nuevo Campo" );
  }
  

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("campo");
    zLista.AddColumnaLista("requerido");
    zLista.AddColumnaLista("read_only");
  }
  
//  public JWins getCtaContabs(JControlWin c) throws Exception {
//  	GuiCtaContabs g = new GuiCtaContabs();
//  	g.getRecords().addFilter("company", c.getFormControl().findControl("company").getValue());
//  	g.getRecords().addFilter("pais", c.getFormControl().findControl("pais").getValue());
//  	return g;
//  }

  
}
