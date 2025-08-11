package pss.bsp.bspBusiness;

import pss.common.regions.divitions.GuiPaisesLista;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.license.typeLicense.GuiTypeLicenses;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;

public class FormCompanyExtraData extends JBaseForm {


private static final long serialVersionUID = 1477575916081L;

  /**
   * Propiedades de la Clase
   */

  /**
   * Constructor de la Clase
   */
  public FormCompanyExtraData() throws Exception {
   }

  public GuiCompanyExtraData getWin() { return (GuiCompanyExtraData) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
 
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemCheck( "Desactivar", CHAR, OPT, "inactiva" ,"S" ,"N");
    AddItemEdit( "Company", CHAR, REQ, "company" ).setHide(true);
    AddItemEdit(  "Licencias", UINT, REQ, "licencias" );
    AddItemDateTime( "Fecha inicio", DATE, OPT, "fecha_incio" );
    AddItemDateTime("Fecha hasta" , DATE, OPT, "fecha_hasta" );
    AddItemCombo( "País", CHAR, REQ, "pais" , new GuiPaisesLista().addOrderAdHoc("descripcion", "ASC"));
    AddItemEdit( "Renovaciones", UINT, OPT, "renovaciones" ).SetValorDefault(0);
    AddItemCombo( "Tipo licencia", CHAR, REQ, "tipo_licencia", new GuiTypeLicenses() ).setFirstOcur(true);
    AddItemCombo( "Version", UINT, OPT, "tkmversion", new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		JMap<String, String> map = JCollectionFactory.createOrderedMap();
    		map.addElement("4", JLanguage.translate("versión 5 y 6"));
    		map.addElement("5", JLanguage.translate("versión 5"));
    		map.addElement("6", JLanguage.translate("versión 6"));
    		return JWins.createVirtualWinsFromMap(map);
    	}
    }).SetValorDefault("5");
    AddItemCheck("Dependiente",OPT,"dependant");
    AddItemEdit( "Código cliente", CHAR, OPT, "codigo_cliente" );
    AddItemCheck("Suspender correos",OPT,"suspender");
  } 
} 
