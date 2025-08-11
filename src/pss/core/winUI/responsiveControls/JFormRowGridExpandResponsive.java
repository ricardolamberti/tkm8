package pss.core.winUI.responsiveControls;

import java.awt.Component;
import java.awt.Rectangle;

import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.winUI.controls.IFormTable;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;

public class JFormRowGridExpandResponsive extends JFormPanelResponsive implements IRow {

	final static public long UPDATE = 0;
	final static public long NEW = 1;
	final static public long DELETE = 2;
	final static public long NOEDIT = 3;
	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JBaseForm oForm;
	private IFormTable oTable;
  private long idRow;

	private JWin oWin;
	private long estado;
	private String provider;
	private String mode;
	


	private boolean expanded;


	private boolean resizable;
	private Rectangle rectangle;
	
	private BizAction action;
	
	public BizAction getAction() {
		return action;
	}

	public void setAction(BizAction action) {
		this.action = action;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public boolean isResizable() {
		return resizable;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public long getEstado() {
		return estado;
	}

	public void setEstado(long estado) {
		this.estado = estado;
	}
	// -------------------------------------------------------------------------//
	// Metodos de acceso a las Propiedades de la Clase
	// -------------------------------------------------------------------------//
	public IFormTable getTable() {
		return oTable;
	}

	public long getIdRow() {
		return idRow;
	}
	@Override
	public boolean isEditable() {
			try {
				return getTable().isEditable();
			} catch (Exception e) {
			  return super.isEditable();
			}
	}

	public void setIdRow(long idRow) {
		this.idRow = idRow;
	}


	public void setTable(IFormTable oTable) {
		this.oTable = oTable;
	}


	public JWin getWin() {
		return oWin;
	}


	public void setWin(JWin oWin) {
		this.oWin = oWin;
	}



	public void setForm(JBaseForm form) {
		oForm=form;
	}

	public JBaseForm GetForm() {
		return oForm;
	}
	

	@Override
	public Component getComponent() {
		return null;
	}

	// -------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------//
	public JFormRowGridExpandResponsive() {
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
//		oForm.setEnabled(true);
	}

	// -------------------------------------------------------------------------//
	// Seteo el foco en el campo
	// -------------------------------------------------------------------------//
//	@Override
//	public void SetFocus() {
//	}

	// -------------------------------------------------------------------------//
	// Edito el campo
	// -------------------------------------------------------------------------//
	@Override
	public void edit(String zModo) throws Exception {
		oForm.SetModo(zModo);
	}

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

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	// -------------------------------------------------------------------------//
	// De la base al control
	// -------------------------------------------------------------------------//
	@Override
	public void BaseToControl(String zModo, boolean userRequest) throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = GetForm().getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			oCon.BaseToControl(zModo, userRequest);
		}	
	}

	public JFormControl findControl(String row,String zName) throws Exception {
		if (row!=null) {
			if (!row.equals("dgf_"+getProvider()+"_fd"))
				return null;
		}
		return this.GetForm().getControles().findControl(null,zName) ;
	}
	@Override
	public void Remove() throws Exception {
	}
}
