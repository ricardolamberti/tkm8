package pss.common.security;

import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormUsuarioFlat extends JBaseForm {

	public GuiUsuario getWin() {
		return (GuiUsuario) this.getBaseWin();
	}
	public BizUsuario getUsuario() throws Exception {
		return this.getWin().GetcDato();
	}
	
	public FormUsuarioFlat() {
	}
	
	@Override
	public void InicializarPanelHeader(JWin zBase) throws Exception {
		JFormPanelResponsive col = this.AddItemColumn();
		col.AddItemLabel("Usuario", 3);
		col.AddItemLabel("Clave", 1);
		col.AddItemLabel("Ultimo Ingreso", 2);
	}

	@Override
	public void InicializarPanel(JWin zBase) throws Exception {
		JFormPanelResponsive col = this.AddItemColumn();
		JFormPanelResponsive col1 = col.AddItemColumn(3);
		JFormPanelResponsive colA = col1.AddItemColumn(2);
		colA.addButton(this.getWin().GetNroIcono(), null, false);
		JFormPanelResponsive colB = col1.AddItemColumn(10);
		int size = 12;
		if (this.getWin().hasDropListener()) {
			colB.addButton(807, 4, 2); size=10;
		}
		colB.addButton(this.getUsuario().GetUsuario(), 1, false, size);
		colB.AddItemLabel(JTools.capitalizeAll(this.getUsuario().GetDescrip())).bold().color("#9a2e2e").size(13);
		
		JFormPanelResponsive line = col.AddItemColumn(1).AddInLinePanel();
		line.addButton(65, 14, 0); //cambiar clave
		line.addButton(5512, 20, 0); //caducar
		line.addButton(5513, 25, 0); //blanquear

		JFormPanelResponsive col2 = col.AddItemColumn(2);
		col2.AddItemLabel(JDateTools.DateToString(this.getUsuario().getFechaUltimoIngreso(), "dd/MM/yyyy"));
		col2.AddItemLabel(this.getUsuario().getLoginSource());
		

		JFormPanelResponsive col3 = col.AddItemColumn(2);
		line = col3.AddInLinePanel();
		line.addButton(6104, 8, 0); // reactivar
		line.addButton(5136, 9, 0); // deseactivar
		line.addButton(10003, 100, 0); // firma 
		line.addButton(10032, 90, 0); //web config
		line.addButton(10085, 230, 0); //Autorizar x Ldap
		line.addButton(10086, 240, 0); //No Autorizar x Ldap

	}
	
}
