package pss.core.winUI.controls;

import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.IRow;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormRowGridResponsive;
import pss.core.winUI.responsiveControls.JFormTableResponsive;

public class JFormRow extends JFormControl implements IRow {

	final static public long UPDATE = 0;
	final static public long NEW = 1;
	final static public long DELETE = 2;
	final static public long NOEDIT = 3;
	final static public long LAST = 4;
		// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JBaseForm oForm;
	private IFormTable oTable;
  private long idRow;

	private JWin oWin;
	private long estado;
	private String provider;
	
	
	public static boolean isUpdate(String estado) {
		if (estado==null) return false;
		return (JTools.getLongFirstNumberEmbedded(estado))==UPDATE;
	}
	public static boolean isNew(String estado) {
		if (estado==null) return false;
		return (JTools.getLongFirstNumberEmbedded(estado))==NEW;
	}
	public static boolean isDelete(String estado) {
		if (estado==null) return false;
		return (JTools.getLongFirstNumberEmbedded(estado))==DELETE;
	}
	public static boolean isNoEdit(String estado) {
		if (estado==null) return false;
		return (JTools.getLongFirstNumberEmbedded(estado))==NOEDIT;
	}
	public static boolean isLast(String estado) {
		if (estado==null) return false;
		return (JTools.getLongFirstNumberEmbedded(estado))==LAST;
	}
	public static boolean isSelected(String estado) {
		if (estado==null) return false;
		return (JTools.getLongFirstNumberEmbedded(estado))==1;
	}
	public static long getSimpleStatus(String estado) {
		if (estado==null) return 0;
		if (estado.equals("")) return 0;
		return (JTools.getLongFirstNumberEmbedded(estado));
	}
	public static boolean isEmpty(String estado) {
		if (estado==null) return true;
		if (estado.equals("")) return true;
		return false;
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
	
//	public JPanel GetFormPanel() {
//		return oForm.getRootPanel();
//	}


	// -------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------//
	public JFormRow() {
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

	@Override
	public void Remove() throws Exception {
	}
	
	public JFormControl findControl(String row,String zName) throws Exception {
		if (row!=null) {
			if (!row.equals("dgf_"+getTable().getFixedProp().GetCampo()+"_row_"+getIdRow()+"_fd"))
				return null;
		}
		return this.GetForm().getControles().findControl(null,zName) ;
	}
	
	public JFormControlResponsive getResponsiveVersion(JFormTableResponsive parent) throws Exception {
		JFormRowGridResponsive newCtrl = new JFormRowGridResponsive();
		newCtrl.getDataComponente(this);
		newCtrl.setProvider(getProvider());
		newCtrl.setEstado(getEstado());
		newCtrl.setIdRow(getIdRow());
		newCtrl.setTable(parent);
		newCtrl.setWin(getWin());
		newCtrl.setForm(GetForm());
		return newCtrl;
	}

}
