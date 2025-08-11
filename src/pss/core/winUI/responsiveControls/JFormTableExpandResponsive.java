package pss.core.winUI.responsiveControls;

import pss.core.services.fields.JObjBDs;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.controls.IFormTable;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.lists.JWinGridExpand;
import pss.core.winUI.lists.JWinList;
import pss.www.platform.actions.JWebActionFactory;

public class JFormTableExpandResponsive extends JFormPanelResponsive implements IFormTable {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JWinGridExpand oWinLista;
	private BizAction oAction;
	private JWin oWin;
	private String sToolBar=JBaseWin.TOOLBAR_TOP;
	private boolean bListChanged=false;
  private Class jWinClass;
  private JBaseForm baseForm;
  private String campo;
  private int newAction;
  private int otherNewAction;
	private int updateAction;
  private String sizeRow;
  private String vision;
  private String model=JWinGridExpand.GRID1ROW;
  private boolean expanded;
	private long zoomtofit;
	String activo;
	private String rowToolbarPos = JBaseWin.TOOLBAR_TOP;

	public String getRowToolbarPos() {
		return rowToolbarPos;
	}
	public JFormTableExpandResponsive setRowToolbarPos(String toolbarPos) {
		this.rowToolbarPos = toolbarPos;
		return this;
	}
	public boolean isNewsEditable() {
		return true;
	}

	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
	// -------------------------------------------------------------------------//
	// Metodos de acceso a las Propiedades de la Clase
	// -------------------------------------------------------------------------//
  public String getModel() {
		return model;
	}
	public JFormTableExpandResponsive setModel(String model) {
		this.model = model;
		return this;
	}
	public boolean isZoomtofit() {
		return zoomtofit>0;
	}

	public JFormTableExpandResponsive setZoomtofit(long zoomtofit) {
		this.zoomtofit = zoomtofit;
		return this;
	}
	public long getZoomtofit() {
		return this.zoomtofit;
	}

	
  public boolean isExpanded() {
		return expanded;
	}
	public JFormTableExpandResponsive setExpanded(boolean zexpanded) {
		this.expanded = zexpanded;
		return this;
	}
	public String getVision() {
		return vision;
	}
	public JFormTableExpandResponsive setVision(String vision) {
		this.vision = vision;
		return this;
	}
	public JFormTableExpandResponsive addDropManager(String zone) throws Exception {
		super.addDropManager(zone, GetLista().getWins());
		return this;
	}
  public String getSizeRow() {
		return sizeRow;
	}
	public JFormControlResponsive setSizeRow(String sizeRow) {
		this.sizeRow = sizeRow;
		return this;
	}
	public int getUpdateAction() {
		return updateAction;
	}
	public void setUpdateAction(int updateAction) {
		this.updateAction = updateAction;
	}

	public int getNewAction() {
		return newAction;
	}
	public void setNewAction(int newAction) {
		this.newAction = newAction;
	}
	public int getOtherNewAction() {
		return otherNewAction;
	}
	public void setOtherNewAction(int newAction) {
		this.otherNewAction = newAction;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public Class getjWinClass() {
		return jWinClass;
	}
	public void setjWinClass(Class jWinClass) {
		this.jWinClass = jWinClass;
	}
	public JBaseForm getBaseForm() {
		return baseForm;
	}
	public void setBaseForm(JBaseForm baseForm) {
		this.baseForm = baseForm;
		try {
			this.setActivo(baseForm.getOwnerAction().getActionSource().getExtraData("meta_"+getName()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void setWin(JWin zValue) {
		oWin=zValue;
	}
	public JWin getWin( ) {
		return oWin;
	}

	public void SetWinLista(JWinList zValue) {
		oWinLista=(JWinGridExpand)zValue;
	}

	public void setAction(BizAction zValue) {
		oAction=zValue;
	}

  public String getName() {
  	try {
			String name = JTools.replace(campo, ".", "_")+"__l"+JWebActionFactory.getCurrentRequest().getLevel();
			return name+"_selecttab";
  	} catch (Exception e) {
		}
  	
  	return super.getName();
  }
	public void BaseToControl(String zModo, boolean userRequest) throws Exception {
		JIterator<JFormControl> it =getControles().GetItems();
		while (it.hasMoreElements()) {
			JFormControl ctrl = it.nextElement();
			if (!(ctrl instanceof JFormRowGridExpandResponsive)) continue;
			ctrl.BaseToControl(zModo, userRequest);
		}
	}
	public synchronized JWinGridExpand GetLista() throws Exception {
		if (oWinLista!=null) return oWinLista;
		JObjBDs bds = (JObjBDs)getWin().getRecord().getProp(getCampo());
		JRecords records = getBaseForm().isConsulta()?bds.getValue():bds.getRawValue();
		if (records==null) records = bds.getValue();
		
		JWins aWins = (JWins)getjWinClass().newInstance();
		aWins.setRecords(records);
		if (getVision()!=null)
			aWins.SetVision(getVision());
	
		oWinLista = new JWinGridExpand(aWins, null);
		oWinLista.setEmbedded(true);
		oWinLista.setFormTable(this);
		oWinLista.setExpanded(isExpanded());
		oWinLista.setModel(getModel());
		oWinLista.setResponsive(isResponsive());
		oWinLista.setEnabled(!ifReadOnly());
		oWinLista.generate(getBaseForm());

		return oWinLista;
	}

	public BizAction getAction() {
		return this.oAction;
	}

	// -------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------//
	public  JFormTableExpandResponsive() {
	}
	// -------------------------------------------------------------------------//
	// Crear Lista
	// -------------------------------------------------------------------------//

	// -------------------------------------------------------------------------//
	// Blanqueo el campo
	// -------------------------------------------------------------------------//


	// -------------------------------------------------------------------------//
	// Protejo el campo
	// -------------------------------------------------------------------------//
	@Override
	public void disable() throws Exception {
		GetLista().setEnabled(false);
	}

	public boolean isEditable()  {
		try {
			return GetLista().isEnabled();
		} catch (Exception e) {
			return super.isEditable();
		}
	}
	// -------------------------------------------------------------------------//
	// Seteo el foco en el campo
	// -------------------------------------------------------------------------//
	@Override
	public void SetFocus() {
	}

	// -------------------------------------------------------------------------//
	// Edito el campo
	// -------------------------------------------------------------------------//
	@Override
	public void edit(String zModo) throws Exception {

		if (zModo==MODO_ALTA) {
			this.changeListAttributes();
//			GetLista().crearRootPanel();
		} else {
			GetLista().setEnabled(true);
		}
	}

	private void restoreListAttributes() throws Exception {
		if (!this.bListChanged) return;
		GetLista().setToolbar(this.sToolBar);
		this.bListChanged=false;
	}

	private void changeListAttributes() throws Exception {
		this.sToolBar=GetLista().getToolbar();
		GetLista().SetFiltrosBar(false);
		GetLista().setToolbar(JBaseWin.TOOLBAR_NONE);
		this.bListChanged=true;
	}


	public BizAction getCheckedAction() throws Exception {
		return oWin.findActionByUniqueId(oAction.getIdAction());
	}

	// -------------------------------------------------------------------------//
	// De la base al control
	// -------------------------------------------------------------------------//
 @Override
	public boolean ifPreasignado() {
		return false;
	}

	public JFormControl AddItemRow(JFormControl control ) throws Exception {
		return getControles().AddControl(control);
	
	}
	
  @Override
	public String getSpecificValue() {
    return "";
  }

  @Override
  public void clear()  {
  }
  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public boolean hasValue() {
    return false;
  }

  @Override
	public void setValue( String zVal ) throws Exception {
  }

}
