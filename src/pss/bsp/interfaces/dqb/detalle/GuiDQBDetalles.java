package pss.bsp.interfaces.dqb.detalle;

import pss.common.regions.multilanguage.JLanguage;
import pss.core.services.fields.JObject;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiDQBDetalles extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiDQBDetalles() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10020; } 
  public String  GetTitle()    throws Exception  { return "Detalles parseo"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiDQBDetalle.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
//    addActionNew( 1, "Nuevo Registro" );
  }

 

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
    zLista.AddColumnaLista("id_aerolinea");
    zLista.AddColumnaLista("fecha");
    zLista.AddColumnaLista("gds");
    zLista.AddColumnaLista("tipo_doc");
    zLista.AddColumnaLista("boleto");
    zLista.AddColumnaLista("pnr");
    zLista.AddColumnaLista("status");
    zLista.AddColumnaLista("consolidado");
    zLista.AddColumnaLista("code_cons");
       
  }

	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addComboResponsive("Resultado", "code_cons", new JControlCombo() {
			@Override
			public JWins getRecords(Object zSource, boolean zOneRow) throws Exception {
				return getTiposErrores();
			}
		}, true);
		zFiltros.addEditResponsive("Boleto", JObject.JSTRING, "boleto");
		zFiltros.addEditResponsive("Tipo doc", JObject.JSTRING, "tipo_doc");
		zFiltros.addEditResponsive("pnr", JObject.JSTRING, "pnr");
		zFiltros.addEditResponsive("Placa",  JObject.JSTRING, "id_aerolinea");
		zFiltros.addEditResponsive("Gds", JObject.JSTRING, "gds");
		zFiltros.addCDateResponsive("fecha", "fecha");
		
	}
	protected JWins getTiposErrores() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement(BizDQBDetalle.ERROR, JLanguage.translate("Error"));
		map.addElement(BizDQBDetalle.OK, JLanguage.translate("Ok"));
		return JWins.createVirtualWinsFromMap(map);
	}
	@Override
	protected void asignFilterByControl(JFormControl control) throws Exception {
		// a la aerolinea hay que agrgarle un 0
		if (control.getName().equals("id_aerolinea") && control.hasValue()) {
			getRecords().addFilter("id_aerolinea", JTools.LPad(control.findValue(), 3, "0"));
			return;
		}
		super.asignFilterByControl(control);
	}



	
	
}
