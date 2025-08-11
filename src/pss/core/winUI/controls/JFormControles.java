package pss.core.winUI.controls;

import java.util.Iterator;

import javax.swing.JLabel;

import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.structure.RFilter;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.IRow;
import pss.core.winUI.responsiveControls.JFormCardResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

//========================================================================== //
// Clase para el manejo de un conjunto de controles Default
//========================================================================== //
public class JFormControles {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JList<JFormControl> aControles = JCollectionFactory.<JFormControl> createList();
	// private JList aLabels = null;
	private boolean labelsLoaded = false;

	private JFormControl o1Foco = null;
	private JBaseForm baseForm = null;
	private JFormPanelResponsive parent = null;

	public JFormPanelResponsive getParent() {
		return parent;
	}

	public void setParent(JFormPanelResponsive parent) {
		this.parent = parent;
	}
	public JIterator<JFormControl> GetItems() {
		return aControles.getIterator();
	}
	public JIterator<JFormControl> getRecursiveItems() {
		JList<JFormControl> aControles = JCollectionFactory.<JFormControl> createList();
		getRecursiveItems(GetItems(),aControles,false);
		return aControles.getIterator();
	}
	public JIterator<JFormControl> getRecursiveItemsWithParents() {
		JList<JFormControl> aControles = JCollectionFactory.<JFormControl> createList();
		getRecursiveItems(GetItems(),aControles,true);
		return aControles.getIterator();
	}

	public void getRecursiveItems(JIterator<JFormControl> it,JList<JFormControl> aControles,boolean includeParents) {
		while (it.hasMoreElements()) {
			JFormControl control = it.nextElement();
			if (control instanceof JFormPanelResponsive) {
				JFormPanelResponsive panel = (JFormPanelResponsive)  control;
				getRecursiveItems(panel.getControles().GetItems(),aControles ,includeParents);
				if (includeParents) aControles.addElement(control);
				continue;
			}
			aControles.addElement(control);
		}
	}
	public JFormControles generateEmbedded(JWin win,String field) throws Exception {
		return generateEmbedded(win,this.GetItems(),field);
	}	
	public JFormControles generateEmbedded(JWin win,JIterator<JFormControl> it,String field) throws Exception {
		while (it.hasMoreElements()) {
			JFormControl control = it.nextElement();
			if (control instanceof JFormPanelResponsive) {
				JFormPanelResponsive panel = (JFormPanelResponsive)  control;
				generateEmbedded(win,panel.getControles().GetItems(),field );
				continue;
			}
			String campo= control.getFixedProp().GetCampo();
			if (campo==null) continue;
			if (campo.startsWith(field+".")) continue;
			control.setFixedProp(win.getRecord().getFixedPropDeep(field+"."+campo));
			control.setProp(win.getRecord().getPropDeep(field+"."+campo));
		}
		return this;
	}

	
	// -------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------//
	public JFormControles(JBaseForm form,JFormPanelResponsive zParent) {
		this.baseForm = form;
		this.parent = zParent;
	}

	// -------------------------------------------------------------------------//
	// Devuelvo la cantidad de controles
	// -------------------------------------------------------------------------//
	public int Cantidad() {
		return aControles.size();
	}

	// -------------------------------------------------------------------------//
	// Agrego un control
	// -------------------------------------------------------------------------//
	public JFormControl AddControl(JFormControl zCon) throws Exception {
		return AddControl(zCon,null);
	}
	public JFormControl AddControl(JFormControl zCon,JFormControl zOldCon) throws Exception {
		if (zCon == null)
			return null;
		zCon.SetControles(this);
		zCon.setLayout();
		zCon.setIdControlIfNeed();
		if (zOldCon==null || !zOldCon.isRemoveIsDuplicate())
			aControles.addElement(zCon);
		else {
			if (aControles.indexOf(zOldCon)!=-1) {
				int pos = Remove(zOldCon);
				aControles.addElementAt(pos,zCon);
			} else {
				removeFieldFromBase(zOldCon);
				aControles.addElement(zCon);
			}
		}
		BizUsuario.retrieveSkinGenerator().customizeComponents(zCon);
		zCon.onAddToForm();
		return zCon;
	}
	
	public JFormControl removeFieldFromBase( JFormControl sCampo) throws Exception {
		if (sCampo==null) return null;
		return getBaseForm().getInternalPanel().removeField(sCampo);
	}

	public void AddControles(JBaseForm zForm) throws Exception {
		JIterator<JFormControl> it = zForm.getControles().GetItems();
		while (it.hasMoreElements()) {
			JFormControl control = it.nextElement();
			control.SetControles(this);
			aControles.addElement(control);
		}
		
	}

	// -------------------------------------------------------------------------//
	// Borro todos los controles
	// -------------------------------------------------------------------------//
	public void cleanUp() {
		aControles.removeAllElements();
	}

	public JBaseForm getBaseForm() {
		return this.baseForm;
	}

	// -------------------------------------------------------------------------//
	// Busco un control por nombre
	// -------------------------------------------------------------------------//
	public JFormCardResponsive findCard(String zName) throws Exception {
		return findCard(null,zName, false);
	}

	public JFormControl findCard(String row,String zName) throws Exception {
		return findCard(row, zName, false);
	}
	
	

	public JFormCardResponsive findCard(String row,String zName,  boolean zErrorIfNotFound) throws Exception {
		String name = zName.trim();
		boolean bEncontro = false;
		JFormControl oControl = null;
		Iterator<JFormControl> iterator = aControles.iterator();
		while (iterator.hasNext()) {
			oControl = iterator.next();
			JFormCardResponsive find = oControl.findCard(row,zName);
			if (find!=null) 
				return find;
		}

		if (zErrorIfNotFound && bEncontro == false)
			JExcepcion.SendError("No se encontró Control id " + zName);

		if (zErrorIfNotFound && !oControl.hasValue())
			JExcepcion.SendError("Debe ingresar un valor al campo: {" + JLanguage.translate(oControl.getFixedProp().GetDescripcion()) + "}");

		if (!bEncontro)
			oControl = null;

		return null;
	}
	public JFormControl findControl(String zName) throws Exception {
		return findControl(null,zName, false);
	}
	public JFormControl findControl(String row,String zName) throws Exception {
		return findControl(row, zName, false);
	}
	public JFormControl findControl(String row,String zName,  boolean zErrorIfNotFound) throws Exception {
		String name = zName.trim();
		boolean bEncontro = false;
		JFormControl oControl = null;
		Iterator<JFormControl> iterator = aControles.iterator();
		while (iterator.hasNext()) {
			oControl = iterator.next();
			JFormControl find = oControl.findControl(row,zName);
			if (find!=null) 
				return find;
		}

		if (zErrorIfNotFound && bEncontro == false)
			JExcepcion.SendError("No se encontró Control id " + zName);

		if (zErrorIfNotFound && !oControl.hasValue())
			JExcepcion.SendError("Debe ingresar un valor al campo: {" + JLanguage.translate(oControl.getFixedProp().GetDescripcion()) + "}");

		if (!bEncontro)
			oControl = null;

		return oControl;
	}
	
	public IRow findRow(String row) throws Exception {
		IRow bEncontro = null;
		JFormControl oControl = null;
		Iterator<JFormControl> iterator = aControles.iterator();
		while (iterator.hasNext()) {
			oControl = iterator.next();
			if (oControl instanceof IRow) {
				IRow frow = ((IRow)oControl);
				if (row!=null) {
					if (row.equals("dgf_"+frow.getProvider()+"_fd")){
						bEncontro = frow;
						break;						
					}
				}
			}
			else if (oControl instanceof JFormPanelResponsive) {
				bEncontro = ((JFormPanelResponsive) oControl).getControles().findRow(row);
				if (bEncontro!=null)
					break;
			}
		}


		return (IRow)bEncontro;
	}
	
	
//	public JFormControl findControlByField(String zName, String operador) throws Exception {
//		return findControlByField(zName, operador, false);
//	}
	
	public JFormControl findControlByField(String zName, String operador) throws Exception {
//		String opFilter = operador==null?"=":operador.trim();
		JFormControl c = null;
		JIterator<JFormControl> iter = aControles.getIterator();
		while (iter.hasMoreElements()) {
			c = iter.nextElement();
			JFormControl finder = c.findControlByField(zName,operador);
			if (finder!= null) return finder;
		}

		return null;
	}

	// -------------------------------------------------------------------------//
	// Mando todo a la base
	// -------------------------------------------------------------------------//

	public void ControlToBase() throws Exception {
		// Enumeration oEnum = aControles.elements();
		JFormControl oCon = null;

		Iterator<JFormControl> oIterator = aControles.iterator();
		while (oIterator.hasNext()) {
			oCon = oIterator.next();
			oCon.ControlToBase();
		}
	}

	public void ControlToBaseWithinCheck() throws Exception {
		// Enumeration oEnum = aControles.elements();
		JFormControl oCon = null;

		Iterator<JFormControl> oIterator = aControles.iterator();
		while (oIterator.hasNext()) {
			oCon = oIterator.next();
			oCon.ControlToBaseWithinCheck();
		}
	}
	
	

	// -------------------------------------------------------------------------//
	// Mando todo al control
	// -------------------------------------------------------------------------//
//	public void BaseToControlAndEdit(String zModo, boolean subListas) throws Exception {
//		BaseToControl(zModo, subListas);
//		EditarCamposIngresables(zModo, false);
//	}

	public void BaseToControl(String zModo, boolean userRequest) throws Exception {
		JFormControl oCon = null;
		Iterator<JFormControl> oIterator = aControles.iterator();
		while (oIterator.hasNext()) {
			oCon = oIterator.next();
			oCon.BaseToControl(zModo, userRequest);
		}
	}

	// -------------------------------------------------------------------------//
	// Blanquea todos los controles
	// -------------------------------------------------------------------------//
	public void clearAll() throws Exception {
		JFormControl oCon = null;
		Iterator<JFormControl> oIterator = aControles.iterator();
		while (oIterator.hasNext()) {
			oCon = oIterator.next();
			oCon.clear();
		}
	}

	// -------------------------------------------------------------------------//
	// Proteger todos los controles
	// -------------------------------------------------------------------------//
	public void disableAll() throws Exception {
		JFormControl oCon = null;
		Iterator<JFormControl> oIterator = aControles.iterator();
		while (oIterator.hasNext()) {
			oCon = oIterator.next();
			oCon.disable();
		}
	}

	// -------------------------------------------------------------------------//
	// Editar todos los controles
	// -------------------------------------------------------------------------//
	public void editAll() throws Exception {
		JFormControl oCon = null;
		Iterator<JFormControl> oIterator = aControles.iterator();
		while (oIterator.hasNext()) {
			oCon = oIterator.next();
			oCon.edit(JBaseForm.MODO_ALTA);
		}
	}

	// -------------------------------------------------------------------------//
	// recarga los scripts
	// -------------------------------------------------------------------------//
	public void refreshScripts() throws Exception {
		JFormControl oCon = null;
		Iterator<JFormControl> oIterator = aControles.iterator();
		while (oIterator.hasNext()) {
			oCon = oIterator.next();
			oCon.refreshScripts();
		}
	}

	public void RemoveAll() throws Exception {
		JFormControl oCon = null;
		Iterator<JFormControl> oIterator = aControles.iterator();
		while (oIterator.hasNext()) {
			oCon = oIterator.next();
			oCon.Remove();
			oCon = null;
		}
	}

	public int removeById(String field) throws Exception {
		JFormControl c = this.findControlByField(field,null);
		if (c == null)
			return -1;
		return this.Remove(c);
	}
	public JFormControl removeByField(JFormControl field) throws Exception {
		JFormControl c = null;
		JIterator<JFormControl> iter = aControles.getIterator();
		while (iter.hasMoreElements()) {
			c = iter.nextElement();
			boolean find = aControles.containsElement(field);
			if (find) {
				aControles.removeElement(field);
				break;
			}
		}

		return null;
	}



	// -------------------------------------------------------------------------//
	// Si hay algun control tipo form lista
	// -------------------------------------------------------------------------//
	public boolean TieneListas() {
		JFormControl oCon = null;
		Iterator<JFormControl> oIterator = aControles.iterator();
		while (oIterator.hasNext()) {
			oCon = oIterator.next();
			if (oCon.getClass().equals(JFormLista.class))
				return true;
		}
		return false;
	}

	// -------------------------------------------------------------------------//
	// Editar Campos Ingresables
	// -------------------------------------------------------------------------//
	public void EditarCamposIngresables(String zModo, boolean partialRefresh) throws Exception {
		Iterator<JFormControl> oIterator = aControles.iterator();
		while (oIterator.hasNext()) {
			JFormControl oCon = oIterator.next();
			oCon.editIfPosible(zModo, partialRefresh);
		}
	}

	public JFormControl getFirstFocusableComponent() {
		return this.o1Foco;
	}

	public void EditarCamposQuery() throws Exception {
		Iterator<JFormControl> oIterator = aControles.iterator();
		while (oIterator.hasNext()) {
			JFormControl oCon = oIterator.next();
			oCon.clear();
			if (oCon.ifCampoQuery()) {
				oCon.clear();
				oCon.edit("A");
			} else
				oCon.disable();
		}
	}

	// -------------------------------------------------------------------------//
	// Fuerzo los datos preasignados
	// -------------------------------------------------------------------------//
	public void asignDefaultData() throws Exception {
		Iterator<JFormControl> oIterator = aControles.iterator();
		while (oIterator.hasNext()) {
			JFormControl oCon = oIterator.next();
			oCon.asignDefaultData();
		}
	}
	
	public void asignDefaultDataActive() throws Exception {
		Iterator<JFormControl> oIterator = aControles.iterator();
		while (oIterator.hasNext()) {
			JFormControl oCon = oIterator.next();
			oCon.asignDefaultDataActive();
		}
	}
	

	public void recordDataToDefault() throws Exception {
		Iterator<JFormControl> oIterator = aControles.iterator();
		while (oIterator.hasNext()) {
			JFormControl oCon = oIterator.next();
			oCon.recordDataToDefault();
		}
	}

	public void protectDataWithFilters(JRecord zBD) throws Exception {
		if (zBD == null)
			return;
		if (!zBD.getStructure().hasFilters())
			return;
		JIterator<RFilter> oIter = zBD.getStructure().getFilters().getIterator();
		while (oIter.hasMoreElements()) {
			RFilter filter = oIter.nextElement();
			JFormControl oFormControl = this.findControlByField(filter.getField(), filter.getOperator());
			if (oFormControl != null)
				oFormControl.disable();
		}
	}

	// -------------------------------------------------------------------------//
	// Fuerzo los datos preasignados
	// -------------------------------------------------------------------------//
	public void SetearFiltros(JRecord zBD) throws Exception {
		zBD.clearFilters();
		Iterator<JFormControl> oIterator = aControles.iterator();
		while (oIterator.hasNext()) {
			JFormControl oCon = oIterator.next();
			oCon.setearFiltros(zBD);
		}
	}

	public int Remove(JFormControl zFormControl) throws Exception {
		int pos = aControles.indexOf(zFormControl);
		aControles.removeElement(zFormControl);
		return pos;
	}

	public JIterator<JFormControl> getAllItems() throws Exception {
		return aControles.getIterator();
	}


	public void addLabel(JLabel label) throws Exception {
		JFormLabel control = new JFormLabel(label);
		control.setForcedValue(label.getText());
		control.setIdControl("label-" + label.hashCode());
		aControles.addElement(control);
	}

	public JFormControl findLabel(JLabel label) throws Exception {
		return this.findControl("label-" + label.hashCode());
	}




}
