package  pss.common.regions.divitions;

import pss.common.regions.currency.GuiMonedas;
import pss.common.regions.multilanguage.JLanguage;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormPais extends JBaseForm {

  public FormPais() throws Exception {
  }

  public GuiPais getWin() {
    return (GuiPais) this.getBaseWin();
  }


  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    this.AddItemEdit( "pais", CHAR, REQ, "pais");
    this.AddItemEdit( "Division", CHAR, OPT, "division");
    this.AddItemEdit( "Sub_Division", CHAR, OPT, "ciudad" );
    this.AddItemCheck( "UsaTablaCiudad", CHAR, "usar_tabla_ciudad");
    this.AddItemEdit( "Sub_Sub_Division", CHAR, OPT, "localidad" );
    this.AddItemCheck( "UsaTablaLoc", CHAR, "usar_tabla_localidad");
    this.AddItemCombo( "MonedaLocal", CHAR, OPT, "moneda_local", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return new GuiMonedas();
    	};
    });
    this.AddItemCombo( "Moneda Principal", CHAR, OPT, "moneda_main", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return new GuiMonedas();
    	};
    });
    this.AddItemCombo( "Lenguaje", CHAR, OPT, "lenguaje_default", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return JWins.CreateVirtualWins(JLanguage.ObtenerIdiomas());
    	}
    });

    this.AddItemEdit("LongTimeFormat",CHAR, OPT, "long_time_format");
    this.AddItemEdit("ShortTimeFormat",CHAR, OPT, "short_time_format");
    this.AddItemEdit("LongDateFormat",CHAR, OPT, "long_date_format");
    this.AddItemEdit("ShortDateFormat",CHAR, OPT, "short_date_format");
    this.AddItemEdit("ThousandSeparator",CHAR, OPT, "grouping_separator");
    this.AddItemEdit("DecimalSeparator",CHAR, OPT, "decimal_separator");

    //jTabbedPane1.add(Formatos, "Formatos");
//    AddItem( jTabbedPane1, 15 ); // Monedas
    JFormTabPanelResponsive tab = this.AddItemTabPanel();
    tab.AddItemTab(16 ); // Tipos Documentos
    tab.AddItemTab(17 ); // Divisiones
    tab.AddItemTab(18 ); // Estado Civil
    tab.AddItemTab(19 ); // Tipo Unidades
    tab.AddItemTab(20 ); // Ciudades
    tab.AddItemTab(22 ); // Tipo cuits


  }
  
}