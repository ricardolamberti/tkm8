package  pss.common.personalData.types;

import pss.common.personalData.BizPersona;
import pss.common.personalData.GuiPersona;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormPersonaFisica extends FormPersona {
	
  public FormPersonaFisica() throws Exception {
  }

  @Override
	public GuiPersona GetWin() {
    return (GuiPersona) getBaseWin();
  }


  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "persona"  , UINT, OPT, "persona" );
    AddItemEdit( "apellido" , CHAR, REQ, "apellido" );
    AddItemEdit( "nombre" , CHAR, OPT, "nombre" );
    JFormControl oCtrl = AddItemCombo( "nacionalidad" , CHAR, OPT, "nacionalidad", new JControlCombo() {@Override
		public JWins getRecords(boolean zOneRow) throws Exception {return ObtenerPaises(zOneRow); }} );
//    oCtrl.addDependencies("tipo_doc");
//    oCtrl.addDependencies("estado_civil");
    AddItemDateTime( "fecha Nacimiento", DATE, OPT, "fecha_nacimiento" );
    AddItemCombo( "tipo doc" , CHAR, OPT, "tipo_doc", new JControlCombo() {@Override
		public JWins getRecords(boolean zOneRow) throws Exception {return ObtenerTiposDocs(zOneRow); }} );
    AddItemEdit( "nro_doc"  , CHAR, OPT, "nro_doc" );
    AddItemEdit( "pasaporte", CHAR, OPT, "pasaporte" );
    AddItemEdit( "vtoPasaporte", DATE, OPT, "vto_pasaporte" );
    AddItemCombo( "estado_civil", CHAR, OPT, "estado_civil", new JControlCombo() {@Override
		public JWins getRecords(boolean zOneRow) throws Exception {return ObtenerEstadoCivil(zOneRow); }} );
    AddItemEdit( "cantidad_hijos", UINT, OPT, "cantidad_hijos" );
		this.AddItemCombo("Sexo", CHAR, REQ, "sexo", new JControlCombo() {
			public JWins getRecords(boolean one) throws Exception {
				return JWins.createVirtualWinsFromMap(getMapSexo());
			};
		}, 3).SetValorDefault(BizPersona.SEXO_MASCULINO);
    AddItemArea( "Observacion", CHAR, OPT, "observaciones" );
    AddItemEdit( "email"    , CHAR, OPT, "e_mail" );
    AddItemEdit( "web", CHAR, OPT, "web" );
//    AddItem( getIdSistemaExterno(), CHAR, OPT, "id_sistema_externo" );
    if (isAlta()) return;
    JFormTabPanelResponsive tab = this.AddItemTabPanel();
    tab.AddItemTab(10);
    tab.AddItemTab(12);
  }

	private JMap<String, String> getMapSexo() throws Exception {
		JMap<String, String> map = JCollectionFactory.createMap();
		map.addElement(BizPersona.SEXO_MASCULINO, "Masculino");
		map.addElement(BizPersona.SEXO_FEMENINO, "Femenino");
		return map;
	}

}

