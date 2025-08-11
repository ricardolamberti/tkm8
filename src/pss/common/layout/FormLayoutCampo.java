package pss.common.layout;

import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormLayoutCampo extends JBaseForm {

	public FormLayoutCampo() throws Exception {
	}

	
	public GuiLayoutCampo GetWin() {
		return (GuiLayoutCampo) getBaseWin();
	}

	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		JFormControlResponsive c;
		JFormPanelResponsive col = this.AddItemColumn();
		col.AddItemEdit("Company", CHAR, REQ, "company").hide();
		col.AddItemEdit("Pais", CHAR, REQ, "pais").hide();
		col.AddItemEdit("Layout", CHAR, REQ, "layout").hide();
		col.AddItemEdit("Secuencia", UINT, OPT, "secuencia").hide();

		col.AddItemEdit("Y", CHAR, REQ, "y", 1);
		col.AddItemEdit("X", CHAR, REQ, "x", 1);
		col.AddItemEdit("Long", ULONG, REQ, "long_max", 1);
		col.AddItemCheck("alFinal", REQ, "al_final", 1);
		col.AddItemEdit("Position", CHAR, OPT, "position", 1);


		col.AddItemCombo("Sector", CHAR, REQ, "sector", new JControlCombo() {
			public JWins getRecords(boolean one) throws Exception {
				return getSections(one);
			}
		}, 2).setRefreshForm(true);

		c=col.AddItemCombo("Tipo", CHAR, OPT, "tipo_campo", new JControlCombo() {
			public JWins getRecords(boolean one) throws Exception {
				return JWins.createVirtualWinsFromMap(getTipoMap());
			};
		},2);		
		c.SetValorDefault(BizLayoutCampo.CAMPO_CONSTANTE);
		c.setRefreshForm(true);

		col.AddItemCombo("Campo", CHAR, OPT, "campo", new JControlCombo() {
			public JWins getRecords(boolean one) throws Exception {
				return getCampos(one);
			}
		}, 3).setRefreshForm(true);
		col.AddItemEdit("Constante", CHAR, OPT, "constante", 3);

		col.AddItemEdit("Parametro", CHAR, OPT, "parametro", 2);
		col.AddItemEdit("Param2", CHAR, OPT, "parametro2", 2);
		
		col.AddItemEdit("Wrap", INT, OPT, "wrap", 1);		
		c=col.AddItemCombo("Alin", CHAR, OPT, "alineacion", new JControlCombo() {
			public JWins getRecords(boolean one) throws Exception {
				return JWins.createVirtualWinsFromMap(getAlinMap());
			};
		},2);		
		c.SetValorDefault("Left");
		col.AddItemCombo("Font", CHAR, OPT, "font_name", new JControlCombo() {
			public JWins getRecords(boolean one) throws Exception {
				return getFonts();
			}
		},2);
		col.AddItemEdit("Size", UINT, OPT, "font_size", 1);
		col.AddItemEdit("Fore", CHAR, OPT, "foreground", 1);
		col.AddItemEdit("Back", CHAR, OPT, "background", 1);
		col.AddItemEdit("Interline", CHAR, OPT, "interline", 1);
		col.AddItemEdit("Type", CHAR, OPT, "font_type", 1);
		
		col.AddItemCombo("FormatoPredef", CHAR, OPT, "formato_predefinido", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return JWins.CreateVirtualWins(BizLayoutCampo.ObtenerFormats());
			}
		}, 3);
		col.AddItemEdit("Formato", CHAR, OPT, "formato", 2);
		col.AddItemCombo("CampoCond", CHAR, OPT, "campo_condicion", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return getCamposCondicion();
			}
		}, 3);
		col.AddItemEdit("ParamCond", CHAR, OPT, "param_condicion",2);
		col.AddItemCombo("OperCond", CHAR, OPT, "oper_condicion", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return JWins.CreateVirtualWins(BizLayout.getOperadores());
			}
		}, 2);
		col.AddItemEdit("Condicion", CHAR, OPT, "condicion", 2);
	}

	private JWins getSections(boolean one) throws Exception {
		return JWins.createVirtualWinsFromMap(GetWin().GetcDato().getObjLayout().getAllSections());
	}

	private JMap<String, String> getTipoMap() throws Exception {
		JMap<String, String> map = JCollectionFactory.createMap();
		map.addElement(BizLayoutCampo.CAMPO_CONSTANTE, "Constante");
		map.addElement(BizLayoutCampo.CAMPO_PARTICULAR, "Particular");
		map.addElement(BizLayoutCampo.CAMPO_CONTROL, "Control");
		map.addElement(BizLayoutCampo.CAMPO_GENERAL, "General");
		return map;
	}

	private JMap<String, String> getAlinMap() throws Exception {
		JMap<String, String> map = JCollectionFactory.createMap();
		map.addElement("Left", "Left");
		map.addElement("Right", "Right");
		map.addElement("Center", "Center");
		return map;
	}

	
	/*
	 * @Override public void OnPostShow() throws Exception { checkControls(); }
	 * 
	 * public void checkControls() throws Exception { // checkControls(true); } public void checkControls(boolean zResetCampoControl) throws Exception { }
	 */// public void checkControls(boolean zResetCampoControl) throws Exception {
	// determinarTipoDato( GetControles().ControlByName("tipo_campo").GetValor(), zResetCampoControl );
	// String cond = GetControles().ControlByName("campo_condicion").GetValor();
	// visibleCondicion(!cond.equals(""));
	//
	// boolean visible = this.GetWin().GetcDato().isCustom(GetControles().ControlByName("formato_predefinido").GetValor());
	// Formato.setVisible(visible);
	//
	// }
	// private void visibleCondicion(boolean zValue) throws Exception {
	// lOper.setVisible(zValue);
	// lValor.setVisible(zValue);
	// Condicion.setVisible(zValue);
	// OperCond.setVisible(zValue);
	// lParam1.setVisible(zValue);
	// ParamCond.setVisible(zValue);
	// }
	// private void determinarTipoDato(String zTipo, boolean zResetCampoControl) throws Exception {
	//
	// JFormControl control = null;
	// String sCurrentSelectedCampo = null;
	//
	// JFormControl oCurrentCampoFormControl = GetControles().ControlByName("campo");
	// if (oCurrentCampoFormControl != null) {
	// sCurrentSelectedCampo = oCurrentCampoFormControl.GetValor();
	// }
	//
	// CampoConst.setVisible(false);
	// CampoDato.setVisible(false);
	// campo.setVisible(false);
	// CampoControl.setVisible(false);
	// Parametro.setVisible(false);
	// lParam.setVisible(false);
	// if ( zTipo.equals("Constante") ) {
	// oCampoConst = AddItem( CampoConst, CHAR, OPT, "campo");
	// CampoConst.setVisible(true);
	// control = oCampoConst;
	// }
	// if ( zTipo.equals("Dato Particular") ) {
	// oCampoDato = AddItem( CampoDato, CHAR, OPT, "campo", new JControlCombo() {public JWins GetCustomWins(boolean zOneRow) throws Exception {return getCampos();}});
	// CampoDato.setVisible(true);
	// Parametro.setVisible(true);
	// lParam.setVisible(true);
	// control = oCampoDato;
	// }
	// if ( zTipo.equals("Dato General") ) {
	// oCampoDatoGral = AddItem( campo, CHAR, OPT, "campo", new JControlCombo() {public JWins GetCustomWins(boolean zOneRow) throws Exception {return getCamposGenerales();}});
	// campo.setVisible(true);
	// Parametro.setVisible(false);
	// lParam.setVisible(false);
	// control = oCampoDatoGral;
	// }
	// if ( zTipo.equals("Control") ) {
	// oCampoControl = AddItem( CampoControl, CHAR, OPT, "campo", GuiLayout.ObtenerControles() );
	// CampoControl.setVisible(true);
	// control = oCampoControl;
	// }
	// if ( control == null ) return;
	// if ( isConsulta() ) {
	// control.BaseToControl(GetModo(), true);
	// control.Proteger();
	// } else {
	// control.Blanquear();
	// if (sCurrentSelectedCampo != null) {
	// control.SetValorDefault(sCurrentSelectedCampo);
	// control.SetearValorDefault();
	// }
	// control.BaseToControl(GetModo(), true);
	// control.Editar(GetModo());
	// }
	// }
//	public void tipoChange() {
//		try {
//			checkControls();
//		} catch (Exception ex) {
//		}
//	}

	private JWins getCamposCondicion() throws Exception {
		JWins oWins=getCamposGenerales();
		oWins.AppendDatos(getCampos());
		return oWins;
	}

	private JWins getCampos() throws Exception {
		String fieldSet=GetWin().GetcDato().getObjLayout().pCamposSet.getValue();
		String ident=GetWin().GetcDato().getObjLayout().pIdent.getValue();
		String section=getControles().findControl("sector").getValue();
		return JWins.CreateVirtualWins(JRecords.createVirtualFormMap(JFieldSet.getSet(fieldSet, ident).getAllFields(section)));
	}

	private JWins getCamposGenerales() throws Exception {
		return JWins.CreateVirtualWins(BizLayout.getGeneralFields());
	}

	/*
	 * void CampoCond_itemStateChanged(ItemEvent e) { try { if ( !isIdle() ) return; if (e.getStateChange() == ItemEvent.SELECTED ) checkControls(false); } catch ( Exception ex ) { }}
	 */
	/*
	 * public JWins ObtenerCamposCond(boolean zOneRow) throws Exception { GuiLayoutCampos oCampos = new GuiLayoutCampos(); if ( zOneRow ) { oCampos.GetDatos().SetFiltros("pais", GetWin().GetcDato().pPais.GetValor()); oCampos.GetDatos().SetFiltros("layout", GetWin().GetcDato().pLayout.GetValor()); oCampos.GetDatos().SetFiltros("secuencia", GetWin().GetcDato().pSecuencia.GetValor()); } else { oCampos.GetDatos().SetFiltros("pais", GetControles().ControlByName("pais").GetValor()); oCampos.GetDatos().SetFiltros("layout", GetControles().ControlByName("layout").GetValor()); oCampos.GetDatos().SetFiltros("tipo_campo", "Dato Particular"); } return oCampos; }
	 */
	/*
	 * void FormatoPredef_itemStateChanged(ItemEvent e) { try { if ( !isIdle() ) return; if (e.getStateChange() == ItemEvent.SELECTED ) checkControls(false); } catch ( Exception ex ) { }}
	 */

	public JWins getCampos(boolean one) throws Exception {
		String tipoCampo="";
		if (one) tipoCampo=GetWin().GetcDato().getTipoCampo();
		else tipoCampo=this.getControles().findControl("tipo_campo").getValue();
		if (tipoCampo.equals(BizLayoutCampo.CAMPO_GENERAL)) {
			return getCamposGenerales();
		}
		if (tipoCampo.equals(BizLayoutCampo.CAMPO_PARTICULAR)) {
			if (!this.getControles().findControl("sector").getValue().equals("")) return getCampos();
		}
		if (tipoCampo.equals(BizLayoutCampo.CAMPO_CONTROL)) {
			return ObtenerControles();
		}
		return JWins.CreateVirtualWins(JRecords.createVirtualBDs());
	}

	public JWins ObtenerControles() throws Exception {
		JRecords<BizVirtual> oControles=JRecords.createVirtualBDs();
		oControles.addItem(JRecord.virtualBD(BizLayout.CONTROL_NEW_LINE, "Nueva Linea", 1));
		oControles.addItem(JRecord.virtualBD(BizLayout.CONTROL_NEW_PAGE, "Nueva Pagina", 1));
		oControles.addItem(JRecord.virtualBD(BizLayout.CONTROL_SECTOR_LINES, "Sector Lines", 1));
		oControles.addItem(JRecord.virtualBD(BizLayout.CONTROL_HEADER_LINES, "Header Lines", 1));
		oControles.addItem(JRecord.virtualBD(BizLayout.CONTROL_PAGE_LINES, "Page Lines", 1));
		oControles.addItem(JRecord.virtualBD(BizLayout.CONTROL_BREAK_ON, "Break On", 1));
		oControles.addItem(JRecord.virtualBD(BizLayout.CONTROL_STOPSECTOR_ON, "Stop On", 1));		
		oControles.addItem(JRecord.virtualBD(BizLayout.CONTROL_TAB, "Tab", 1));
		oControles.addItem(JRecord.virtualBD(BizLayout.CONTROL_CAMPO, "Campo", 1));
		return JWins.CreateVirtualWins(oControles);
	}
	private JWins getFonts() throws Exception {
		return JWins.createVirtualWinsFromMap(JFonts.getFonts());
	}

}