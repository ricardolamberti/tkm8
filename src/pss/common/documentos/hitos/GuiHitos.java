package pss.common.documentos.hitos;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiHitos extends JWins {


  /**
   * Constructor de la Clase
   */
  public GuiHitos() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10017; } 
  public String  GetTitle()    throws Exception  { return "Hitos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiHito.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
 //   addActionNew( 1, "Nuevo Registro" );
  }


	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addComboResponsive("Tipo hito", "id_tipo_hito", JWins.createVirtualWinsFromMap(BizHito.getTipoHitos()));
		
		JFormControl c;
		c = zFiltros.addCDateResponsive("Fecha Desde", "fecha");
		c.setOperator(">=");
		c.setIdControl("fecha_desde");
		c.clear();
		//c.SetValorDefault(JDateTools.getFirstDayOfYear(new Date()));
		c = zFiltros.addCDateResponsive("Fecha Hasta", "fecha");
		c.setOperator("<=");
		c.setIdControl("fecha_hasta");
		//c.SetValorDefault(new Date());
		
	  super.ConfigurarFiltros(zFiltros);
	}

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("fecha");
    zLista.AddColumnaLista("descr_tipohito");
    zLista.AddColumnaLista("id_operador");
    zLista.AddColumnaLista("observaciones");
  }

}
