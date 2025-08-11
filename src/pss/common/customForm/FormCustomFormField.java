package pss.common.customForm;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormCustomFormField extends JBaseForm {


	/**
	 * Constructor de la Clase
	 */
	public FormCustomFormField() throws Exception {
	}

	public GuiCustomFormField GetWin() {
		return (GuiCustomFormField) getBaseWin();
	}

	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		JFormPanelResponsive col = this.AddItemColumn(6);
		col.AddItemEdit("company", CHAR, REQ, "company").hide();
		col.AddItemEdit("sec", INT, REQ, "secuencia").hide();
		col.AddItemCombo("Campo", CHAR, REQ, "campo", new JControlCombo() {
			public JWins getRecords(boolean one) throws Exception {
				return getCampos(one);
			}
		});
		col.AddItemCheck("Requerido", REQ, "requerido", 3);
		col.AddItemCheck("Read Only", REQ, "read_only", 3);
//		AddItem(jTabbedPane, 10);
//		AddItem(jTabbedPane, 20);
	}
	
	public JWins getCampos(boolean one) throws Exception {
		JBaseForm form = (JBaseForm)Class.forName(this.GetWin().GetcDato().getObjCustomForm().getFormulario()).newInstance();
		JWin win = (JWin)Class.forName(this.GetWin().GetcDato().getObjCustomForm().getClaseWin()).newInstance();
		form.SetWin(win);
		form.SetModo(JBaseForm.MODO_ALTA);
		form.InicializarPanel(win);
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		JIterator<JFormControl> iter = form.getControles().getRecursiveItems();
		while (iter.hasMoreElements()) {
			JFormControl c = iter.nextElement();
			if (!c.hasFixedProp()) continue;
			map.addElement(c.getFixedProp().GetCampo(), c.getFixedProp().GetCampo());
		}
		return JWins.createVirtualWinsFromMap(map);
	}

}  