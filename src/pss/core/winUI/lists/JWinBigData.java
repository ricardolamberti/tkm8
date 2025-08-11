package pss.core.winUI.lists;

import pss.core.services.fields.JString;
import pss.core.tools.collections.JIterator;
import pss.core.win.IControl;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.IFormTable;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormControlResponsive;

public class JWinBigData  extends JWinList {
	public JWinBigData(JWins wins) throws Exception {
		super(wins);
	}
	
	public JWinBigData(JWins wins, IControl zControlWins) throws Exception {
		super(wins, zControlWins);
	}
	protected String serializedWins=null;
	protected IFormTable formTable;
	boolean responsive;
	boolean withIcono;
	
	long extraRows=10;
	long minumusRows=10;

	protected boolean selectedRows = false;
	protected boolean uploadOnlyEditable = false;

	public boolean isUploadOnlyEditable() {
		return uploadOnlyEditable;
	}
	public void setUploadOnlyEditable(boolean uploadOnEditable) {
		this.uploadOnlyEditable = uploadOnEditable;
	}
	public boolean isSelectedRows() {
		return selectedRows;
	}
	public void setSelectedRows(boolean selectedRows) {
		this.selectedRows = selectedRows;
	}

	public long getMinumusRows() {
		return minumusRows;
	}
	public void setMinumusRows(long minumusRows) {
		this.minumusRows = minumusRows;
	}
	
	public long getExtraRows() {
		return extraRows;
	}
	public void setExtraRows(long extraRows) {
		this.extraRows = extraRows;
	}
	public boolean isWithIcono() {
		return withIcono;
	}
	public void setWithIcono(boolean withIcono) {
		this.withIcono = withIcono;
	}
	public boolean isResponsive() {
		return responsive;
	}
	public void setResponsive(boolean responsive) {
		this.responsive = responsive;
	}
	public IFormTable getFormTable() {
		return formTable;
	}
	public void setFormTable(IFormTable formTable) {
		this.formTable = formTable;
	}
	
	public void ConfigurarFiltrosLista() throws Exception {
		this.getWins().ConfigurarFiltrosLista(this);
	}
	public void generate(JBaseForm form) throws Exception {
		super.generate(form);
	}
	
	

	public void ConfigurarColumnasLista() throws Exception {
		if (aColumnasLista!=null) return;
		
		if (getBaseForm()==null) {
			super.ConfigurarColumnasLista();
			return;
		}
		JWin winRef = this.getWins().getWinRef();
		winRef.getRecord().setLogicalParent(getBaseForm().getBaseWin().getRecord());
		winRef.setParent(this.getWins());
		winRef.getRecord().setStatic(true);
		JBaseForm form = winRef.buildLocalForm(JBaseForm.MODO_MODIFICACION);
		JFormControles controles = form.getControles();
		this.AddIcono("").setVisible(false);
		JIterator<JFormControl> it = controles.getRecursiveItems();
		while (it.hasMoreElements()) {
			JFormControl control = it.nextElement();
			if (control.getFixedProp()==null) continue;
			JColumnaLista col =new JColumnaLista(control.getProp()==null?JString.class:control.getProp().getClass());
			col.SetTitulo(control instanceof JFormControlResponsive?((JFormControlResponsive)control).getLabel():control.getFixedProp().GetDescripcion());
			if (!col.hasAlignment()&&!col.GetCampo().equals(""))
				col.setAlignmentFromType( winRef.getRecord().getObjectType(col.GetCampo()));
			col.setFixedProp(control.getFixedProp());
			col.setVisible(!control.isHide()&&(control.getComponent()==null||control.getComponent().getParent()!=null));
			if (winRef.getWidthCol(control.getFixedProp().GetCampo())!=0)
				col.setWidth(""+winRef.getWidthCol(control.getFixedProp().GetCampo()));
		  col.setControl(control);
		   
			this.AddColumnaLista(col);
		}
	}
		
}
