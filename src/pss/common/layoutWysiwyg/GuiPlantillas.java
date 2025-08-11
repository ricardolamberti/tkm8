package  pss.common.layoutWysiwyg;

import pss.common.security.BizUsuario;
import pss.common.security.GuiUsuarios;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormComboResponsive;

public class GuiPlantillas extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiPlantillas() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10036; } 
  public String  GetTitle()    throws Exception  { return "Plantillas" ; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiPlantilla.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Tipo" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddColumnaLista("descripcion");
  	zLista.AddColumnaLista("margen_der");
  	zLista.AddColumnaLista("margen_izq");
  	zLista.AddColumnaLista("margen_top");
  	zLista.AddColumnaLista("margen_bottom");
  	zLista.AddColumnaLista("borde");
  	zLista.AddColumnaLista("tipo_pagina");
  	zLista.AddColumnaLista("fondo");
  	zLista.AddColumnaLista("imprime_fondo");
  }
  
  @Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
  	zFiltros.addEditResponsive("Descripcion",JBaseForm.CHAR, "descripcion", "ilike").setPopupIcon(false);
  	JFormComboResponsive f=zFiltros.addComboResponsive("Usuario","usuario", new GuiUsuarios());
  	f.SetPermitirTodo(true);
  	f.SetValorDefault(BizUsuario.getUsr().GetUsuario());

	}
  
  @Override
  protected void asignFilterByControl(JFormControl control) throws Exception {
  	if (control.getIdControl().equals("usuario") ) {
  		if (!control.hasValue() || control.getValue().equals("") || control.getValue().equals("TODOS")) {
 		  	return;
  		} else {
  			this.getRecords().addFixedFilter("where LYT_PLANTILLA.id_tipo in (select LYT_PLANTILLA_PERMISOS.id_plantilla  from LYT_PLANTILLA_PERMISOS where "+
    				" LYT_PLANTILLA.id_tipo=LYT_PLANTILLA_PERMISOS.id_plantilla and "+
    				" (LYT_PLANTILLA_PERMISOS.id_owner='"+control.getValue()+"' or "+
    				" LYT_PLANTILLA_PERMISOS.id_owner='TODOS'))" ).setDynamic(true);
  			return;
  		}
  	}
  	super.asignFilterByControl(control);
  }
	
 }
