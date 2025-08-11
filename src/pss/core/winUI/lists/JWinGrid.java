package pss.core.winUI.lists;

import pss.core.JAplicacion;
import pss.core.services.fields.JString;
import pss.core.tools.collections.JIterator;
import pss.core.win.IControl;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.controls.IFormTable;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.controls.JFormRow;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormRowGridResponsive;

public class JWinGrid extends JWinList {
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

	public JWinGrid(JWins zWins) throws Exception {
		super(zWins);
	}
	public JWinGrid(JWins wins, IControl zControlWins) throws Exception {
		super(wins, zControlWins);
	}
	
	public JFormControl buildRow(int rowpos,JWin win,long estado,JBaseForm form) throws Exception {
		if (isResponsive()) {
			JFormRowGridResponsive formRow = new JFormRowGridResponsive();
			formRow.setIdRow(rowpos);
			formRow.setEstado(estado);
			formRow.setForm(form);
			formRow.setProvider( form.getProviderName()+"_"+getFormTable().getCampo()+"_row_"+rowpos);
			formRow.setTable(getFormTable());
			formRow.setWin(win);
			win.getRecord().setRowId(""+rowpos);
			return formRow;
		} else {
			JFormRow formRow = new JFormRow();
			formRow.setIdRow(rowpos);
			formRow.setEstado(estado);
			formRow.setForm(form);
			formRow.setProvider( form.getProviderName()+"_"+getFormTable().getCampo()+"_row_"+rowpos);
			formRow.setTable(getFormTable());
			formRow.setWin(win);
			win.getRecord().setRowId(""+rowpos);
			return formRow;
			
		}
		
	}

	public void generate(JBaseForm mainform) throws Exception {
		JBaseForm form;
		int hayNew=0;
		int j=0;
		super.generate(mainform);
		if (JAplicacion.GetApp().ifAppFrontEndWeb()) {
			boolean isRefresh = getBaseForm().isPartialRefreshFormAction();
			this.getWins().setParent(mainform.getBaseWin());
			ConfigurarColumnasLista();
			JIterator<JWin> itw=this.getWins().getStaticIterator();
			int rowpos=0;
			while (itw.hasMoreElements()) {
				JWin win=itw.nextElement();
//				if (!win.getRecord().isStatic()) { // cuando es un formlov no se llenan todas las columans solo la que interesa, estas se descartan
//					if (win.getRecord().detectIsComplete()) 
//						j++;
//					getFormTable().AddItemRow(buildRow(rowpos,win,JFormRow.NOEDIT,win.buildLocalForm(JBaseForm.MODO_MODIFICACION)));
//					rowpos++;
//					continue;
//				}
				long lStatus; 

				String status= win.getRecord().getExtraData("internal_estado_fila");
					if (JFormRow.isEmpty(status)) {
						lStatus=JFormRow.UPDATE;
						
//						if (!win.getRecord().detectIsComplete()) 
//							hayNew++;
//						if (JFormRow.isLast(status)&&hayNew<=9) 
//							lStatus=JFormRow.NEW;
					} else {
						lStatus=JFormRow.getSimpleStatus(status);
						
//						if ((lStatus==JFormRow.NEW||lStatus==JFormRow.LAST)&&!win.getRecord().detectIsComplete()) 
//							hayNew++;
						if (JFormRow.isLast(status)&&hayNew<getExtraRows()) {
							lStatus=JFormRow.NEW;
						}
					}
					if (!win.getRecord().detectIsComplete()) 
						hayNew++;
					else
						hayNew=0;
				 
				if (!addRow(lStatus,win,mainform,rowpos)) break;
				rowpos++;
			} 
			if (isEnabled() && !mainform.GetModo().equals(JBaseForm.MODO_CONSULTA)) {
					if (getFormTable().isNewsEditable()) {
						for (int i =hayNew;i<getExtraRows();i++) {
								if (!addRow((i==getExtraRows()-1?JFormRow.LAST:JFormRow.NEW),null,mainform,rowpos))break;
	  						rowpos++;
						}
				}
			}
			if (mainform.GetModo().equals(JBaseForm.MODO_CONSULTA)) this.getFormTable().disable();
			else this.getFormTable().edit(JBaseForm.MODO_ALTA);
		}
	}
	
	public boolean addRow(long lStatus,JWin win,JBaseForm mainform,int rowpos) throws Exception {
		JBaseForm form;
		String modo=null;
		boolean ok; 
		//determine action
		BizAction oAction = null;
		if (lStatus==JFormRow.NEW||lStatus==JFormRow.LAST) {
			if (getFormTable().getNewAction()!=-1)
				oAction = this.getBaseForm().getBaseWin().findAction(getFormTable().getNewAction());
			else
				oAction = this.getWins().findAction(1);
			if (oAction!=null && !this.getWins().OkAction(oAction)) oAction=null;
			if (oAction!=null && !this.getBaseForm().getBaseWin().OkAction(oAction)) oAction=null;
			if (oAction==null) return false;
			if (win==null) 
				win=oAction.getSubmit().getWinResult();
			
			modo=JBaseForm.MODO_ALTA;
		} 
	//	if (oAction==null)
			oAction=win.findAction(getFormTable().getUpdateAction());
		ok= (oAction!=null && win.OkAction(oAction)) ;

		if (oAction==null || win==null)
			return false;
		// determine modo
		if (modo==null) {
			modo = !mainform.GetModo().equals(JBaseForm.MODO_CONSULTA)&&isEnabled()?JBaseForm.MODO_MODIFICACION:JBaseForm.MODO_CONSULTA;
			if (lStatus==JFormRow.DELETE) {
				modo=JBaseForm.MODO_CONSULTA;
				ok=false;
			}
			else if (mainform.GetModo().equals(JBaseForm.MODO_CONSULTA) || !ok) {
				lStatus=JFormRow.NOEDIT;
				modo=JBaseForm.MODO_CONSULTA;
				ok=false;
			}			
		}
		win.getRecord().setLogicalParent(getBaseForm().getBaseWin().getRecord());

		// build form
		if (oAction==null || !ok) {
			form=win.buildLocalForm(modo);
			form.setOwnerAction(oAction.getSubmit());
		} else {
			form =oAction.getSubmit().preserveFieldChange(getBaseForm().getOwnerAction(),rowpos,oAction.getProviderName()).getForm().build();
		
		}
		form.setPartialRefreshFormAction(getBaseForm().isPartialRefreshFormAction());
		getFormTable().AddItemRow(buildRow(rowpos,win,lStatus,form));
		return true;
	}
	
	public void ConfigurarFiltrosLista() throws Exception {
		this.getWins().ConfigurarFiltrosLista(this);
	}
	
	public void ConfigurarColumnasLista() throws Exception {
		if (aColumnasLista!=null) return ;
		JWin winRef = this.getWins().getWinRef();
		winRef.getRecord().setLogicalParent(getBaseForm().getBaseWin().getRecord());
		winRef.setParent(this.getWins());
		winRef.getRecord().setStatic(true);
		JBaseForm form = winRef.buildLocalForm(JBaseForm.MODO_CONSULTA);
		JFormControles controles = form.getControles();
		this.AddIcono("").setVisible(isWithIcono());
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
			this.AddColumnaLista(col);
		}
		
	}
	


}