package  pss.common.personalData;

import pss.core.tools.JTools;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiPersonas extends JWins {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiPersonas() throws Exception {
  }

  @Override
	public int     GetNroIcono() throws Exception  { return 761; }
  @Override
	public String  GetTitle()    throws Exception  { return "Personas"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiPersona.class; }

  @Override
	public void createActionMap() throws Exception {
//    addActionNew     ( 1, "Nueva Persona" );
  }
  
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
  	zFiltros.addEditResponsive("Descripción", "description");
		zFiltros.addEditResponsive("Nro Doc", "nro_doc");
		zFiltros.addEditResponsive("Nro Fiscal", "nro_cui");
  }

  public boolean assignFilters(String field, String value) throws Exception {
		if (field.equals("description")) {
			this.getRecords().addFilter("busqueda", JTools.generateBusqueda(value),"ILIKE").setDynamic(true);
			return true;
		}
		return false;
	}
  
	@Override
	protected void asignFilterByControl(JFormControl control) throws Exception {
		if (control.getIdControl().equals("busqueda") && control.hasValue()) {
			this.getRecords().addFilter("busqueda", JTools.generateBusqueda(control.getValue()),"ILIKE").setDynamic(true);
			return;
		}

		super.asignFilterByControl(control);
	}
	
  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
     zLista.AddIcono("");
     zLista.AddColumnaLista("description");
     zLista.AddColumnaLista("TD", "descr_tipo_doc");
     zLista.AddColumnaLista("Nro Doc", "nro_doc");
     zLista.AddColumnaLista("Nro.Fiscal", "nro_cui");
     //zLista.AddColumnaLista("Nacimiento", "fecha_nacimiento");
     //zLista.AddColumnaLista("nro_cui");
     //zLista.AddColumnaLista("e_mail");
  }

}
