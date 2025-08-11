package pss.common.event.action;

import pss.common.event.mailing.BizMailingPersona;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.layoutWysiwyg.GuiPlantillas;
import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.common.security.GuiUsuarios;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormSqlEventActionNew  extends JBaseForm {


  public FormSqlEventActionNew() throws Exception {
  }

  public GuiSqlEventAction getWin() { return (GuiSqlEventAction) getBaseWin(); }

 
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormPanelResponsive row1= AddItemRow(6);
  	JFormPanelResponsive row2= AddItemRow(6);
  	row1.AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
  	row1.AddItemEdit( null, UINT, OPT, "id_action" ).setHide(true);
  	row1.AddItemEdit( null, UINT, OPT, "id_evento" ).setHide(true);
    row1.AddItemEdit( null, CHAR, OPT, "class_evento" ).setHide(true);
    row1.AddItemEdit( null, CHAR, OPT, "parametros" ).setHide(true);
    row1.AddItemEdit( null, CHAR, OPT, "extradata" ).setHide(true);
    row1.AddItemCombo( "Acción", CHAR, REQ, "action" , JWins.createVirtualWinsFromMap(BizSqlEventAction.getActions())).setFirstOcur(true).setRefreshForm(true);
    JFormControl c=row1.AddItemCombo( "Formato", CHAR, REQ, "formato" , JWins.createVirtualWinsFromMap(BizSqlEventAction.getTipoSalidas())).setSizeColumns(9);
    c.SetValorDefault(BizSqlEventAction.PANTALLA);
    c.setRefreshForm(true);
    c=row1.AddItemCheck( "Adjunto", OPT, "adjunto" ).setSizeColumns(3);
    c.setRefreshForm(true);
    
    row1.AddItemCombo( "Usuario", CHAR, OPT, "usuario" , new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getUsuarios(one);
    	}
    }).SetValorDefault(BizUsuario.getUsr().GetUsuario());
    row1.AddItemEdit( "Titulo", CHAR, OPT, "descripcion" );
    c=row1.AddItemEdit( "Correo", CHAR, OPT, "correo" );
    c.SetValorDefault(BizUsuario.getUsr().getObjBusiness().getEmailDefault());
    row1.AddItemEdit( "Teléfono", CHAR, OPT, "telefono" ).setHide(true);

    row1.AddItemCombo( "Mailing", CHAR, OPT, "mailing" , new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return JWins.createVirtualWinsFromMap(BizMailingPersona.getTipos());
    	}
    }).SetValorDefault(BizUsuario.getUsr().GetUsuario());

    
    BizPlantilla pl = BizCompany.getObjPlantilla(BizUsuario.getUsr().getCompany(), "sys_email");
     c=row1.AddItemWinLov( "Planilla", LONG, OPT, "id_plantilla" , new JControlWin() {
    	@Override
    	public JWins getRecords(boolean bOneRow) throws Exception {
    		return getPlantillas(bOneRow);
    	}
    });
     c.SetReadOnly(true);
     c.setVisible(false);
    if (pl!=null) c.SetValorDefault(pl.getIdtipo());

    JFormFieldsetResponsive panel = row1.AddItemFieldset("");
    c = panel.AddItemCombo( "Periodicidad", CHAR, OPT, "tipo_periodicidad" , new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean bOneRow) throws Exception {
    		return getPeriodicidad(bOneRow);
    	}
    });
    c.setRefreshForm(true);
    c.SetValorDefault(BizSqlEventAction.SOLOUNAVEZ);
    panel.AddItemDateTime( "Hora",HOUR, OPT, "hora" ).SetValorDefault("00:00:00");
    c=panel.AddItemCheck( "Día de la semana", OPT, "semana_todos" );
    c.setRefreshForm(true);
    panel.AddItemCheck( "Lunes", OPT, "lunes" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(4);
    panel.AddItemCheck( "Martes", OPT, "martes" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(4);
    panel.AddItemCheck( "Miercoles", OPT, "miercoles" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(4);
    panel.AddItemCheck( "Jueves", OPT, "jueves" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(4);
    panel.AddItemCheck( "Viernes", OPT, "viernes" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(4);
    panel.AddItemCheck( "Sabado", OPT, "sabado" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(4);
    panel.AddItemCheck( "Domingo", OPT, "domingo" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(4);
    c=panel.AddItemCheck( "Días del mes", OPT, "dias_todos" );
    c.setRefreshForm(true);
    panel.AddItemCheck( "1", OPT, "d1" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "2", OPT, "d2" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "3", OPT, "d3" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "4", OPT, "d4" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "5", OPT, "d5" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "6", OPT, "d6" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "7", OPT, "d7" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "8", OPT, "d8" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "9", OPT, "d9" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "10", OPT, "d10" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "11", OPT, "d11" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "12", OPT, "d12" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "13", OPT, "d13" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "14", OPT, "d14" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "15", OPT, "d15" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "16", OPT, "d16" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "17", OPT, "d17" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "18", OPT, "d18" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "19", OPT, "d19" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "20", OPT, "d20" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "21", OPT, "d21" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "22", OPT, "d22" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "23", OPT, "d23" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "24", OPT, "d24" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "25", OPT, "d25" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "26", OPT, "d26" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "27", OPT, "d27" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "28", OPT, "d28" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "29", OPT, "d29" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "30", OPT, "d30" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(1);
    panel.AddItemCheck( "Último día", OPT, "d31" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(2);
    c=panel.AddItemCheck( "Mes del año", OPT, "mes_todos" );
    c.setRefreshForm(true);
    panel.AddItemCheck( "Enero", OPT, "m1" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(3);
    panel.AddItemCheck( "Febrero", OPT, "m2" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(3);
    panel.AddItemCheck( "Marzo", OPT, "m3" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(3);
    panel.AddItemCheck( "Abril", OPT, "m4" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(3);
    panel.AddItemCheck( "Mayo", OPT, "m5" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(3);
    panel.AddItemCheck( "Junio", OPT, "m6" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(3);
    panel.AddItemCheck( "Julio", OPT, "m7" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(3);
    panel.AddItemCheck( "Agosto", OPT, "m8" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(3);
    panel.AddItemCheck( "Septiembre", OPT, "m9" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(3);
    panel.AddItemCheck( "Octubre", OPT, "m10" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(3);
    panel.AddItemCheck( "Noviembre", OPT, "m11" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(3);
    panel.AddItemCheck( "Diciembre", OPT, "m12" ).setStyle(JFormCheckResponsive.STYLE_IOS).setSizeColumns(3);

    row2.AddCardPanel(20).setDiferido(true);
    
//	    JFormHtmlTextAreaResponsive cr=row2.AddItemHtml("Mensaje", CHAR, REQ, "mensaje" );
//	    cr.setHeight(800);
//	    cr.setKeepHeight(true);
//	    cr.setKeepWidth(true);
//	    cr.SetReadOnly(true);
   }
  
//	  @Override
//	  public boolean forceConvertToResponsive() throws Exception {
//	  	return false;
//	  }

	@Override
	public void OnPostShow() throws Exception {
		checkControls("");
	}
	@Override
	public void checkControls(String sControlId) throws Exception {
		String valA = getControles().findControl("action").getValue();
		String valF = getControles().findControl("formato").getValue();
		if (sControlId.equals("formato")||sControlId.equals("adjunto") ||sControlId.equals("action") || sControlId.equals("")) {
			if (valF.equals(BizSqlEventAction.EXCEL)) {
				getControles().findControl("adjunto").disable();
				getControles().findControl("adjunto").setValue("S");
			} else if (valF.equals(BizSqlEventAction.CSV)) {
				getControles().findControl("adjunto").disable();
				getControles().findControl("adjunto").setValue("S");
			} else if (valA.equals(BizSqlEventAction.URL)) {
				getControles().findControl("adjunto").disable();
				getControles().findControl("adjunto").setValue("N");
			} else {
				getControles().findControl("adjunto").enable();
			}

			getControles().findControl("formato").enable();
			getControles().findControl("tipo_periodicidad").enable();

			if (valA.equals(BizSqlEventAction.EMAIL)) {
				getControles().findControl("usuario").disable();
				getControles().findControl("usuario").setValue("");
				getControles().findControl("telefono").disable();
				getControles().findControl("telefono").setValue("");
				getControles().findControl("mailing").disable();
				getControles().findControl("mailing").setValue("");
				getControles().findControl("correo").enable();
			}  else if (valA.equals(BizSqlEventAction.AVISO)) {
				getControles().findControl("usuario").enable();
				getControles().findControl("telefono").disable();
				getControles().findControl("telefono").setValue("");
				getControles().findControl("correo").disable();
				getControles().findControl("correo").setValue("");
				getControles().findControl("mailing").disable();
				getControles().findControl("mailing").setValue("");
			} else if (valA.equals(BizSqlEventAction.NOTIF)) {
				getControles().findControl("usuario").enable();
				getControles().findControl("telefono").disable();
				getControles().findControl("telefono").setValue("");
				getControles().findControl("correo").disable();
				getControles().findControl("correo").setValue("");
				getControles().findControl("mailing").disable();
				getControles().findControl("mailing").setValue("");
			} else if (valA.equals(BizSqlEventAction.URL)) {
				getControles().findControl("usuario").disable();
				getControles().findControl("usuario").setValue("");
				getControles().findControl("telefono").disable();
				getControles().findControl("telefono").setValue("");
				getControles().findControl("mailing").disable();
				getControles().findControl("mailing").setValue("");
				getControles().findControl("correo").enable();
			}  else if (valA.equals(BizSqlEventAction.MAILING)) {
				getControles().findControl("usuario").disable();
				getControles().findControl("usuario").setValue("");
				getControles().findControl("telefono").disable();
				getControles().findControl("telefono").setValue("");
				getControles().findControl("correo").disable();
				getControles().findControl("correo").setValue("");
				getControles().findControl("formato").disable();
				getControles().findControl("formato").setValue(BizSqlEventAction.PDF);
				getControles().findControl("mailing").enable();
			} else if (valA.equals(BizSqlEventAction.DOWNLOAD)) {
				getControles().findControl("usuario").disable();
				getControles().findControl("usuario").setValue("");
				getControles().findControl("telefono").disable();
				getControles().findControl("telefono").setValue("");
				getControles().findControl("correo").disable();
				getControles().findControl("correo").setValue("");
				getControles().findControl("mailing").disable();
				getControles().findControl("mailing").setValue("");
				getControles().findControl("tipo_periodicidad").disable();
				getControles().findControl("tipo_periodicidad").setValue(BizSqlEventAction.SOLOUNAVEZ);
				getControles().findControl("adjunto").disable();
				getControles().findControl("adjunto").setValue("N");
			}
			if (sControlId.equals("")) {
				getWin().GetcDato().clean();
				getWin().GetcDato().setFormato(valF);
				getWin().GetcDato().setAction(valA);
				getWin().GetcDato().setAdjunto(getControles().findControl("adjunto").getValue().equals("S"));
	//			GetControles().findControl("mensaje").setValue(getWin().GetcDato().getPreviewMensaje());
				
			}
		}
		if (sControlId.equals("tipo_periodicidad") || 
				sControlId.equals("")) {
			refreshPeriodicidad();
		}
		if (sControlId.equals("semana_todos")) {
			String val = getControles().findControl("semana_todos").getValue();
			getControles().findControl("lunes").setValue(val);
			getControles().findControl("martes").setValue(val);
			getControles().findControl("miercoles").setValue(val);
			getControles().findControl("jueves").setValue(val);
			getControles().findControl("viernes").setValue(val);
			getControles().findControl("sabado").setValue(val);
			getControles().findControl("domingo").setValue(val);
		
		}
		if (sControlId.equals("mes_todos")) {
			String val = getControles().findControl("mes_todos").getValue();
			for (int i=1;i<13;i++)
				getControles().findControl("m"+i).setValue(val);
		
		}
		if (sControlId.equals("dias_todos")) {
			String val = getControles().findControl("dias_todos").getValue();
			for (int i=1;i<32;i++)
				getControles().findControl("d"+i).setValue(val);;
		
		}
		super.checkControls(sControlId);
	}
	public void disablePeriodicidad() throws Exception {
		getControles().findControl("hora").disable();
		getControles().findControl("semana_todos").disable();
		getControles().findControl("dias_todos").disable();
		getControles().findControl("mes_todos").disable();
		getControles().findControl("lunes").disable();
		getControles().findControl("martes").disable();
		getControles().findControl("miercoles").disable();
		getControles().findControl("jueves").disable();
		getControles().findControl("viernes").disable();
		getControles().findControl("sabado").disable();
		getControles().findControl("domingo").disable();
		for (int i=1;i<13;i++)
			getControles().findControl("m"+i).disable();
		for (int i=1;i<32;i++)
			getControles().findControl("d"+i).disable();
	}
	public void refreshPeriodicidad() throws Exception {
		disablePeriodicidad();
		String per = getControles().findControl("tipo_periodicidad").getValue();
		if (!per.equals(BizSqlEventAction.SOLOUNAVEZ) && !per.equals(BizSqlEventAction.LIMITE)) {
			getControles().findControl("tipo_periodicidad").enable();
			getControles().findControl("hora").enable();
			if (per.equals(BizSqlEventAction.SEMANAL)){
				getControles().findControl("semana_todos").enable();
				getControles().findControl("lunes").enable();
				getControles().findControl("martes").enable();
				getControles().findControl("miercoles").enable();
				getControles().findControl("jueves").enable();
				getControles().findControl("viernes").enable();
				getControles().findControl("sabado").enable();
				getControles().findControl("domingo").enable();
			}
			if (per.equals(BizSqlEventAction.ANUAL) || per.equals(BizSqlEventAction.MENSUAL)){
				getControles().findControl("dias_todos").enable();
				for (int i=1;i<32;i++)
					getControles().findControl("d"+i).enable();
			}

			if (per.equals(BizSqlEventAction.ANUAL)){
				getControles().findControl("mes_todos").enable();
				for (int i=1;i<13;i++)
					getControles().findControl("m"+i).enable();
			}	
		}			

	}
	public JWins getUsuarios(boolean one) throws Exception {
		GuiUsuarios wins = new GuiUsuarios();
		if (one) wins.getRecords().addFilter("usuario", getWin().GetcDato().getUsuario());
		wins.getRecords().addFilter("company", getWin().GetcDato().getCompany());
		return wins;
	}
	public JWins getPeriodicidad(boolean one) throws Exception {
		return JWins.createVirtualWinsFromMap(BizSqlEventAction.getPeriodicidad());
	}
	
	public JWins getPlantillas(boolean one) throws Exception {
		GuiPlantillas wins = new GuiPlantillas();
		wins.setModeWinLov(true);
		if (one) wins.getRecords().addFilter("id", getWin().GetcDato().getIdplantilla());
		//wins.getRecords().addFilter("company", getWin().GetcDato().getCompany());
		return wins;
	}


}
