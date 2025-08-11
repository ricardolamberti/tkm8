package pss.bsp.contrato.detalle.nivel;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormComboResponsive;
import pss.core.winUI.responsiveControls.JFormControlResponsive;

public class FormNivel extends JBaseForm {


private static final long serialVersionUID = 1446899454830L;


/**
   * Constructor de la Clase
   */
  public FormNivel() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiNivel getWin() { return (GuiNivel) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
   
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true).SetValorDefault(BizUsuario.getUsr().getCompany());
    AddItemEdit( null, LONG, OPT, "id" ).setHide(true);
    AddItemEdit( null, LONG, OPT, "linea" ).setHide(true);
    AddItemEdit( null, LONG, OPT, "id_nivel" ).setHide(true);
    JFormComboResponsive c = AddItemCombo( "Tipo Objetivo", CHAR, OPT, "tipo_nivel",JWins.createVirtualWinsFromMap(BizNivel.getTiposObjetivos()) );
		c.SetValorDefault(getWin().GetcDato().getDefaultTipoObjetivo());
		if (!getWin().GetcDato().detectIsComplete())
			getWin().GetcDato().setTipoObjetivo(getWin().GetcDato().getDefaultTipoObjetivo());
    c.setRefreshForm(true);
    AddItemEdit( "Objetivo", FLOAT, OPT, "valor" ).setPlaceHolder("Objetivo");
    AddItemEdit( "Parametro 1", FLOAT, OPT, "param1" );
    AddItemEdit( "Parametro 2", FLOAT, OPT, "param2" );
    AddItemEdit( "Parametro 3", FLOAT, OPT, "param3" );
    if (!getWin().GetVision().equals(BizNivel.NIVEL_ONLYMETA)) {
	    c = AddItemCombo( "Tipo Premio", CHAR, OPT, "tipo_premio",JWins.createVirtualWinsFromMap(BizNivel.getTiposPremios()) );
			c.SetValorDefault(getWin().GetcDato().getDefaultTipoPremio());
			if (!getWin().GetcDato().detectIsComplete())
				getWin().GetcDato().setTipoPremio(getWin().GetcDato().getDefaultTipoPremio());
	    c.setRefreshForm(true);
	    AddItemEdit( "Premio", FLOAT, OPT, "premio" );
	    AddItemEdit( "Parametro 4", FLOAT, OPT, "param4" );
	    AddItemEdit( "Parametro 5", FLOAT, OPT, "param5" );
	    AddItemEdit( "Parametro 6", FLOAT, OPT, "param6" );
    }
  }

  @Override
	public void OnPostShow() throws Exception {
		checkControls("");
		super.OnPostShow();
	}

	@Override
	public void checkControls(String sControlId) throws Exception {
		String nivel=getControles().findControl("tipo_nivel").getValue();
		if (nivel==null) return;
		getWin().GetcDato().setTipoObjetivo(nivel);
		ITipoNivel tipoNivel=getWin().GetcDato().getTipoObjetivoInstance();
		String descr=tipoNivel.getParametro(0, getWin().GetcDato());
		if (descr==null) {
			getControles().findControl("valor").SetReadOnly(true);
		} else {
			getControles().findControl("valor").SetReadOnly(false);
		}
		descr=tipoNivel.getParametro(1, getWin().GetcDato());
		Double defa=tipoNivel.getValorDefault(1, getWin().GetcDato());
		boolean editable=tipoNivel.getEditable(1, getWin().GetcDato());
		
		if (descr==null) {
			getControles().findControl("param1").hide();
	//		lvalor1.setVisible(false);
		} else {
			JFormControlResponsive ctrl = (JFormControlResponsive)getControles().findControl("param1");
			if (editable)
				ctrl.edit();
			else
				ctrl.disable();
			if (defa!=null) ctrl.SetValorDefault(defa);
			ctrl.setPlaceHolder(descr);
			ctrl.setLabel(descr);
//			lvalor1.setVisible(true);
	//		lvalor1.setText(descr);
		}
		descr=tipoNivel.getParametro(2, getWin().GetcDato());
		defa=tipoNivel.getValorDefault(2, getWin().GetcDato());
		editable=tipoNivel.getEditable(2, getWin().GetcDato());
		if (descr==null) {
			getControles().findControl("param2").hide();
	//		lvalor2.setVisible(false);
		} else {
			JFormControlResponsive ctrl = (JFormControlResponsive)getControles().findControl("param2");
			if (editable)
				ctrl.edit();
			else
				ctrl.disable();
			if (defa!=null) ctrl.SetValorDefault(defa);
			ctrl.setPlaceHolder(descr);
			ctrl.setLabel(descr);

//			lvalor2.setVisible(true);
//			lvalor2.setText(descr);
		}

		descr=tipoNivel.getParametro(3, getWin().GetcDato());
		defa=tipoNivel.getValorDefault(3, getWin().GetcDato());
		editable=tipoNivel.getEditable(3, getWin().GetcDato());

		if (descr==null) {
			getControles().findControl("param3").hide();
//			lvalor3.setVisible(false);
		} else {
			JFormControlResponsive ctrl = (JFormControlResponsive)getControles().findControl("param3");
			if (editable)
				ctrl.edit();
			else
				ctrl.disable();
			if (defa!=null) ctrl.SetValorDefault(defa);
			ctrl.setPlaceHolder(descr);
			ctrl.setLabel(descr);
//			lvalor3.setVisible(true);
//			lvalor3.setText(descr);
		}
		if (getControles().findControl("tipo_premio")==null) return;
		nivel=getControles().findControl("tipo_premio").getValue();
		if (nivel==null) return;
		getWin().GetcDato().setTipoPremio(nivel);
		ITipoPremio tipoPremio=getWin().GetcDato().getTipoPremioInstance();
		descr=tipoPremio.getParametro(0, getWin().GetcDato());
		defa=tipoPremio.getValorDefault(0, getWin().GetcDato());
		editable=tipoPremio.getEditable(0, getWin().GetcDato());

		if (descr==null) {
			getControles().findControl("premio").SetReadOnly(true);
		} else {
			getControles().findControl("premio").SetReadOnly(false);
		}
		descr=tipoPremio.getParametro(4, getWin().GetcDato());
		defa=tipoPremio.getValorDefault(4, getWin().GetcDato());
		editable=tipoPremio.getEditable(4, getWin().GetcDato());
		if (descr==null) {
			getControles().findControl("param4").hide();
//			lvalor4.setVisible(false);
		} else {
			JFormControlResponsive ctrl = (JFormControlResponsive)getControles().findControl("param4");
			if (editable)
				ctrl.edit();
			else
				ctrl.disable();
			if (defa!=null) ctrl.SetValorDefault(defa);
			ctrl.setPlaceHolder(descr);
			ctrl.setLabel(descr);
//			lvalor4.setVisible(true);
//			lvalor4.setText(descr);
		}
		descr=tipoPremio.getParametro(5, getWin().GetcDato());
		defa=tipoPremio.getValorDefault(5, getWin().GetcDato());
		editable=tipoPremio.getEditable(5, getWin().GetcDato());
		if (descr==null) {
			getControles().findControl("param5").hide();
//			lvalor5.setVisible(false);
		} else {
			JFormControlResponsive ctrl = (JFormControlResponsive)getControles().findControl("param5");
			if (editable)
				ctrl.edit();
			else
				ctrl.disable();
			if (defa!=null) ctrl.SetValorDefault(defa);
			ctrl.setPlaceHolder(descr);
			ctrl.setLabel(descr);
//			lvalor5.setVisible(true);
//			lvalor5.setText(descr);
		}

		descr=tipoPremio.getParametro(6, getWin().GetcDato());
		defa=tipoPremio.getValorDefault(6, getWin().GetcDato());
		editable=tipoPremio.getEditable(6, getWin().GetcDato());
		if (descr==null) {
			getControles().findControl("param6").hide();
//			lvalor6.setVisible(false);
		} else {
			JFormControlResponsive ctrl = (JFormControlResponsive)getControles().findControl("param6");
			if (editable)
				ctrl.edit();
			else
				ctrl.disable();
			if (defa!=null) ctrl.SetValorDefault(defa);
			ctrl.setPlaceHolder(descr);
			ctrl.setLabel(descr);
//			lvalor6.setVisible(true);
//			lvalor6.setText(descr);
		}

		super.checkControls(sControlId);
	}
}  //  @jve:decl-index=0:visual-constraint="10,10" 
