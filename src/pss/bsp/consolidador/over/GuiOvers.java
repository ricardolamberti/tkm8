package pss.bsp.consolidador.over;

import pss.bsp.consolidador.consolidacion.detalle.BizConsolidacion;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.totalizer.JTotalizer;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormComboResponsive;

public class GuiOvers extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiOvers() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10006; } 
  public String  GetTitle()    throws Exception  { return "Analisis Over"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiOver.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
 	 //   addActionNew( 1, "Nuevo Registro" );
   	
 	  }


  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {

		JFormComboResponsive c = zFiltros.addComboResponsive(
				"Resultados conciliacion",
				"status", this.getTiposStatus(), true);
		c.setRefreshForm(true);
	//	c.setPreferredWidth(400);
		
	}
	
	public void createTotalizer(JWinList oLista) throws Exception {
		oLista.addTotalizerText("boleto", "Totales"); // un texto
		oLista.addTotalizer("over_ped", 2, JTotalizer.OPER_SUM); // la suma del
		oLista.addTotalizer("over_rec", 2, JTotalizer.OPER_SUM); // la suma del campo
		oLista.addTotalizer("over_dif", 2, JTotalizer.OPER_SUM); // la suma del campo
	}


	private JWins getTiposStatus() throws Exception {
		return JWins.CreateVirtualWins(BizConsolidacion.ObtenerResultadosAnalisis());
	}
	@Override


  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
  	zLista.AddColumnaLista("boleto");
  	zLista.AddColumnaLista("fecha");
  	zLista.AddColumnaLista("id_aerolinea");
  	zLista.AddColumnaLista("over_ped");
  	zLista.AddColumnaLista("over_rec");
  	zLista.AddColumnaLista("over_dif");
  	zLista.AddColumnaLista("observaciones");
  }
  }
