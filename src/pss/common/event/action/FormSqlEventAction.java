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

public class FormSqlEventAction extends JBaseForm {

  public FormSqlEventAction() throws Exception {
  }

  public GuiSqlEventAction getWin() { return (GuiSqlEventAction) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "company", CHAR, OPT, "company" );
    AddItemEdit( "id_action", UINT, OPT, "id_action" );
    AddItemEdit( "id_evento", UINT, OPT, "id_evento" );
    AddItemEdit( "class_evento", CHAR, OPT, "class_evento" );
    AddItemEdit( "parametros", CHAR, OPT, "parametros" );
    AddItemEdit( "extradata", CHAR, OPT, "extradata" );
    AddItemCombo( "action", CHAR, REQ, "action" , JWins.createVirtualWinsFromMap(BizSqlEventAction.getActions())).setRefreshForm(true);
    JFormControl c=AddItemCombo( "Formato", CHAR, REQ, "formato" , JWins.createVirtualWinsFromMap(BizSqlEventAction.getTipoSalidas()));
    c.SetValorDefault(BizSqlEventAction.PANTALLA);
    c.setRefreshForm(true);
    AddItemCombo( "usuario", CHAR, OPT, "usuario" , new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getUsuarios(one);
    	}
    }).SetValorDefault(BizUsuario.getUsr().GetUsuario());
    AddItemEdit( "descripcion", CHAR, OPT, "descripcion" );
   c=AddItemEdit( "correo", CHAR, OPT, "correo" );
    c.SetValorDefault(BizUsuario.getUsr().getObjBusiness().getEmailDefault());
    AddItemEdit( "telefono", CHAR, OPT, "telefono" );
    AddItemEdit( "Adjunto", OPT, "adjunto" );
    
    AddItemCombo( "Mailing", CHAR, OPT, "mailing" , new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return JWins.createVirtualWinsFromMap(BizMailingPersona.getTipos());
    	}
    }).SetValorDefault(BizUsuario.getUsr().GetUsuario());

    
    BizPlantilla pl = BizCompany.getObjPlantilla(BizUsuario.getUsr().getCompany(), "sys_email");
     c=AddItemWinLov( "Plantilla", LONG, OPT, "id_plantilla" , new JControlWin() {
    	@Override
    	public JWins getRecords(boolean bOneRow) throws Exception {
    		return getPlantillas(bOneRow);
    	}
    });
     c.SetReadOnly(true);
     c.setVisible(false);
    if (pl!=null) c.SetValorDefault(pl.getIdtipo());

    c = AddItemCombo( "Periodicidad", CHAR, OPT, "tipo_periodicidad" , new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean bOneRow) throws Exception {
    		return getPeriodicidad(bOneRow);
    	}
    });
    c.setRefreshForm(true);
    c.SetValorDefault(BizSqlEventAction.SOLOUNAVEZ);
    AddItemEdit( "Hora",CHAR, OPT, "hora" ).SetValorDefault("00:00:00");
    c=AddItemEdit( "todos los días de la semana", OPT, "semana_todos" );
    c.setRefreshForm(true);
    AddItemEdit( "Lunes", OPT, "lunes" );
    AddItemEdit( "Martes", OPT, "martes" );
    AddItemEdit( "Miercoles", OPT, "miercoles" );
    AddItemEdit( "Jueves", OPT, "jueves" );
    AddItemEdit( "Viernes", OPT, "viernes" );
    AddItemEdit( "Sabado", OPT, "sabado" );
    AddItemEdit( "Domingo", OPT, "domingo" );
    c=AddItemCheck( "Todos los dias", OPT, "dias_todos" );
    c.setRefreshForm(true);
    AddItemEdit( "1", OPT, "d1" );
    AddItemEdit( "2", OPT, "d2" );
    AddItemEdit( "3", OPT, "d3" );
    AddItemEdit( "4", OPT, "d4" );
    AddItemEdit( "5", OPT, "d5" );
    AddItemEdit( "6", OPT, "d6" );
    AddItemEdit( "7", OPT, "d7" );
    AddItemEdit( "8", OPT, "d8" );
    AddItemEdit( "9", OPT, "d9" );
    AddItemEdit( "10", OPT, "d10" );
    AddItemEdit( "11", OPT, "d11" );
    AddItemEdit( "12", OPT, "d12" );
    AddItemEdit( "13", OPT, "d13" );
    AddItemEdit( "14", OPT, "d14" );
    AddItemEdit( "15", OPT, "d15" );
    AddItemEdit( "16", OPT, "d16" );
    AddItemEdit( "17", OPT, "d17" );
    AddItemEdit( "18", OPT, "d18" );
    AddItemEdit( "19", OPT, "d19" );
    AddItemEdit( "20", OPT, "d20" );
    AddItemEdit( "21", OPT, "d21" );
    AddItemEdit( "22", OPT, "d22" );
    AddItemEdit( "23", OPT, "d23" );
    AddItemEdit( "24", OPT, "d24" );
    AddItemEdit( "25", OPT, "d25" );
    AddItemEdit( "26", OPT, "d26" );
    AddItemEdit( "27", OPT, "d27" );
    AddItemEdit( "28", OPT, "d28" );
    AddItemEdit( "29", OPT, "d29" );
    AddItemEdit( "30", OPT, "d30" );
    AddItemEdit( "31", OPT, "d31" );
    c=AddItemEdit( "todos los meses", OPT, "mes_todos" );
    c.setRefreshForm(true);
    AddItemEdit( "Enero", OPT, "m1" );
    AddItemEdit( "Febrero", OPT, "m2" );
    AddItemEdit( "Marzo", OPT, "m3" );
    AddItemEdit( "Abril", OPT, "m4" );
    AddItemEdit( "Mayo", OPT, "m5" );
    AddItemEdit( "Junio", OPT, "m6" );
    AddItemEdit( "Julio", OPT, "m7" );
    AddItemEdit( "Agosto", OPT, "m8" );
    AddItemEdit( "Septiembre", OPT, "m9" );
    AddItemEdit( "Octubre", OPT, "m10" );
    AddItemEdit( "Noviembre", OPT, "m11" );
    AddItemEdit( "Diciembre", OPT, "m12" );
    this.AddItemTabPanel().AddItemTab(10);
  }
  
  	@Override
	public void OnPostShow() throws Exception {
		checkControls("");
	}
	@Override
	public void checkControls(String sControlId) throws Exception {
		if (sControlId.equals("action") || sControlId.equals("")) {
			String val = getControles().findControl("action").getValue();
			getControles().findControl("tipo_periodicidad").edit(GetModo());
			if (val.equals(BizSqlEventAction.EMAIL)) {
				getControles().findControl("usuario").disable();
				getControles().findControl("usuario").setValue("");
				getControles().findControl("telefono").disable();
				getControles().findControl("telefono").setValue("");
				getControles().findControl("mailing").disable();
				getControles().findControl("mailing").setValue("");
				getControles().findControl("correo").edit(GetModo());
			}  else if (val.equals(BizSqlEventAction.AVISO)) {
				getControles().findControl("usuario").edit(GetModo());
				getControles().findControl("telefono").disable();
				getControles().findControl("telefono").setValue("");
				getControles().findControl("correo").disable();
				getControles().findControl("correo").setValue("");
				getControles().findControl("mailing").disable();
				getControles().findControl("mailing").setValue("");
			} else if (val.equals(BizSqlEventAction.NOTIF)) {
				getControles().findControl("usuario").edit(GetModo());
				getControles().findControl("telefono").disable();
				getControles().findControl("telefono").setValue("");
				getControles().findControl("correo").disable();
				getControles().findControl("correo").setValue("");
				getControles().findControl("mailing").disable();
				getControles().findControl("mailing").setValue("");
			} else if (val.equals(BizSqlEventAction.SMS)) {
				getControles().findControl("telefono").edit(GetModo());
				getControles().findControl("usuario").disable();
				getControles().findControl("usuario").setValue("");
				getControles().findControl("correo").disable();
				getControles().findControl("correo").setValue("");
				getControles().findControl("mailing").disable();
				getControles().findControl("mailing").setValue("");
			} else if (val.equals(BizSqlEventAction.URL)) {
				getControles().findControl("usuario").disable();
				getControles().findControl("usuario").setValue("");
				getControles().findControl("telefono").disable();
				getControles().findControl("telefono").setValue("");
				getControles().findControl("mailing").disable();
				getControles().findControl("mailing").setValue("");
				getControles().findControl("correo").edit(GetModo());
			}  else if (val.equals(BizSqlEventAction.MAILING)) {
				getControles().findControl("usuario").disable();
				getControles().findControl("usuario").setValue("");
				getControles().findControl("telefono").disable();
				getControles().findControl("telefono").setValue("");
				getControles().findControl("correo").disable();
				getControles().findControl("correo").setValue("");
				getControles().findControl("mailing").edit(GetModo());
			} else if (val.equals(BizSqlEventAction.DOWNLOAD)) {
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
			getControles().findControl("tipo_periodicidad").edit(GetModo());
			getControles().findControl("hora").edit(GetModo());
			if (per.equals(BizSqlEventAction.SEMANAL)){
				getControles().findControl("semana_todos").edit(GetModo());
				getControles().findControl("lunes").edit(GetModo());
				getControles().findControl("martes").edit(GetModo());
				getControles().findControl("miercoles").edit(GetModo());
				getControles().findControl("jueves").edit(GetModo());
				getControles().findControl("viernes").edit(GetModo());
				getControles().findControl("sabado").edit(GetModo());
				getControles().findControl("domingo").edit(GetModo());
			}
			if (per.equals(BizSqlEventAction.ANUAL) || per.equals(BizSqlEventAction.MENSUAL)){
				getControles().findControl("dias_todos").edit(GetModo());
				for (int i=1;i<32;i++)
					getControles().findControl("d"+i).edit(GetModo());
			}

			if (per.equals(BizSqlEventAction.ANUAL)){
				getControles().findControl("mes_todos").edit(GetModo());
				for (int i=1;i<13;i++)
					getControles().findControl("m"+i).edit(GetModo());
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
		return JWins.createVirtualWinsFromMap(BizSqlEventAction.getPeriodicidadRestringida());
	}
	
	public JWins getPlantillas(boolean one) throws Exception {
		GuiPlantillas wins = new GuiPlantillas();
		wins.setModeWinLov(true);
		if (one) wins.getRecords().addFilter("id", getWin().GetcDato().getIdplantilla());
		//wins.getRecords().addFilter("company", getWin().GetcDato().getCompany());
		return wins;
	}
}
