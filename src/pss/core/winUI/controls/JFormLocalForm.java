package pss.core.winUI.controls;

import pss.core.services.records.JRecord;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.win.actions.BizAction;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabResponsive;

public class JFormLocalForm  extends JFormTabResponsive {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JFormTabPanelResponsive oTabPane;
	private JFormPanelResponsive oRootPanel;
	private JBaseForm oForm;
	public void setForm(JBaseForm form) {
		oForm=form;
	}

	public JBaseForm getForm() {
		return oForm;
	}

	public JFormPanelResponsive getRootPanel() {
		if (oRootPanel==null) {
			oRootPanel=new JFormPanelResponsive();
			oRootPanel.setForm(getForm());
			oRootPanel.setWin(getForm().getBaseWin());
			oRootPanel.setFormatFields(getForm().getFormatFields());
		}
		return oRootPanel;
	}
	public void setRootPanel(JFormPanelResponsive oRootPanel) {
		this.oRootPanel = oRootPanel;
	}
	// -------------------------------------------------------------------------//
	// Metodos de acceso a las Propiedades de la Clase
	// -------------------------------------------------------------------------//
	public void setTabPane(JFormTabPanelResponsive zValue) {
		oTabPane=zValue;
	}
	// -------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------//
	public JFormLocalForm() {
	}

	// -------------------------------------------------------------------------//
	// Crear Lista
	// -------------------------------------------------------------------------//

	// -------------------------------------------------------------------------//
	// Blanqueo el campo
	// -------------------------------------------------------------------------//
	@Override
	public void clear() {
	}

	// -------------------------------------------------------------------------//
	// Protejo el campo
	// -------------------------------------------------------------------------//
	@Override
	public void disable() throws Exception {
		getRootPanel().disable();
	}

	// -------------------------------------------------------------------------//
	// Seteo el foco en el campo
	// -------------------------------------------------------------------------//
//	@Override
//	public void SetFocus() {
//	}

	// -------------------------------------------------------------------------//
	// Obtengo el valor del campo
	// -------------------------------------------------------------------------//
	@Override
	public String getSpecificValue() {
		return null;
	}

	// -------------------------------------------------------------------------//
	// Obtengo el valor del campo
	// -------------------------------------------------------------------------//
	@Override
	public boolean hasValue() {
		return true;
	}
	
	// -------------------------------------------------------------------------//
	// Edito el campo
	// -------------------------------------------------------------------------//
	@Override
	public void edit(String zModo) throws Exception {
		if (zModo==MODO_ALTA) {
			if (!(oTabPane==null||!oTabPane.hasTabs())) {
				if (!this.addPanelToTab()) return;
				return;
			}
		}
		getRootPanel().edit(zModo);
	}
	
	public JFormControl findControl(String row,String zName) throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getRootPanel().getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			JFormControl find = oCon.findControl(row,zName);
			if (find!=null) return find;
		}
		return null;
	}

	public JFormControl findControlByField(String zName, String operador) throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getRootPanel().getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			JFormControl finder = oCon.findControlByField(zName,operador);
			if (finder!= null) return finder;
		}

		return null;

	}
	private boolean addPanelToTab() throws Exception {
		oTabPane.removeTab("local_"+JTools.getValidFilename(getTitle()));
		if (hasAction()) {
			BizAction action=this.getCheckedAction();
			if (action==null) return false;
			
		}
		oTabPane.addTab("local_"+JTools.getValidFilename(getTitle()), this);

		return true;
	}

	// -------------------------------------------------------------------------//
	// De la base al control
	// -------------------------------------------------------------------------//
	@Override
	public void BaseToControl(String zModo, boolean userRequest) throws Exception {
		getRootPanel().BaseToControl(zModo, userRequest);
	}


	// -------------------------------------------------------------------------//
	// recarga los scripts
	// -------------------------------------------------------------------------//


	public void Remove() throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getRootPanel().getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			oCon.Remove();
			oCon = null;
		}
		super.Remove();
	}


	// -------------------------------------------------------------------------//
	// Editar Campos Ingresables
	// -------------------------------------------------------------------------//
	public void editIfPosible(String zModo, boolean partialRefresh) throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getRootPanel().getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			oCon.editIfPosible(zModo, partialRefresh);
		}
//		super.editIfPosible(zModo);
	}
	// -------------------------------------------------------------------------//
	// SetearFiltros
	// -------------------------------------------------------------------------//

	public void setearFiltros(JRecord zBD) throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getRootPanel().getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			oCon.setearFiltros(zBD);
		}
		super.setearFiltros(zBD);
	}
	
	
	// -------------------------------------------------------------------------//
	// recarga los scripts
	// -------------------------------------------------------------------------//
	public void refreshScripts() throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getRootPanel().getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			oCon.refreshScripts();
		}
	}
	// -------------------------------------------------------------------------//
	// recordDataToDefault
	// -------------------------------------------------------------------------//

	public void recordDataToDefault() throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getRootPanel().getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			oCon.recordDataToDefault();
		}
	}
	// -------------------------------------------------------------------------//
	// Fuerzo los datos preasignados
	// -------------------------------------------------------------------------//
	public void asignDefaultData() throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getRootPanel().getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			oCon.asignDefaultData();
		}
		super.asignDefaultData();
	}
	
	public void setForcedDefault(boolean a)  {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getRootPanel().getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			oCon.setForcedDefault(a);
		}
		super.setForcedDefault(a);
	}
	
	
	public void clearDefault() throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getRootPanel().getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			oCon.clearDefault();
		}
		super.clearDefault();
	}
}
