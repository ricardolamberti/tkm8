package pss.core.winUI.responsiveControls;

import pss.core.services.fields.JObjBDs;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.controls.IFormTable;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.lists.JWinGrid;
import pss.core.winUI.lists.JWinList;

public class JFormTableResponsive extends JFormPanelResponsive implements IFormTable {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JWinGrid oWinLista;
	private BizAction oAction;
	private JWin oWin;
	private String sToolBar=JBaseWin.TOOLBAR_TOP;
	private boolean bListChanged=false;
	private boolean bMultipleSelect=false;
	private Class jWinClass;
  private JBaseForm baseForm;
  private String campo;
  private int newAction;
  private int otherNewAction;
	private int updateAction;
	private String mode;
	private String vision;
	private String hideRows;
	
	private JMap<String,TableFilterManual> manualFilter;

	private boolean withIcono;
	private boolean newsEditable=true;
	private long extraRows=10;
	private long minumusRows=10;
	
	protected boolean selectedRows = false;
	protected boolean uploadOnEditable = false;

	public boolean isUploadOnEditable() {
		return uploadOnEditable;
	}
	public JFormTableResponsive setUploadOnlyEditable(boolean uploadOnEditable) {
		this.uploadOnEditable = uploadOnEditable;
		return this;
	}
	public boolean isSelectedRows() {
		return selectedRows;
	}
	public void setSelectedRows(boolean selectedRows) {
		this.selectedRows = selectedRows;
	}
	
	public String getRowToolbarPos() {
		return null;
	}

	// -------------------------------------------------------------------------//
	// Metodos de acceso a las Propiedades de la Clase
	// -------------------------------------------------------------------------//
	
	@Override
	public void initialize() throws Exception {
		setWithIcono(true);
		super.initialize();
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
	public JFormTableResponsive setWithIcono(boolean withIcono) {
		this.withIcono = withIcono;
		return this;
	}
	public String getHideRows() {
		return hideRows;
	}
	public boolean hasHideRows() {
		return hideRows!=null;
	}
	public void setHideRows(String hideRows) {
		this.hideRows = hideRows;
	}
  public boolean isMultipleSelect() {
		return bMultipleSelect;
	}
	public void setMultipleSelect(boolean bMultipleSelect) {
		this.bMultipleSelect = bMultipleSelect;
	}
	public boolean isNewsEditable() {
		return newsEditable;
	}
	public void setNewsEditable(boolean newsEditable) {
		this.newsEditable = newsEditable;
	}
		
  public JMap<String, TableFilterManual> getManualFilter() {
  	if (manualFilter==null) manualFilter = JCollectionFactory.createMap();
		return manualFilter;
	}
	public void setManualFilter(JMap<String, TableFilterManual> manualFilter) {
		this.manualFilter = manualFilter;
	}

	public void addFilterManual(String campo,String[] col,String op) {
  	TableFilterManual t = new TableFilterManual();
  	t.setField(campo);
  	t.setColumns(col);
  	t.setOperacion(op);
  	getManualFilter().addElement(campo, t);
	}

  public String getVision() {
		return vision;
	}
  public boolean hasVision() {
		return vision!=null;
	}
	public JFormTableResponsive setVision(String vision) {
		this.vision = vision;
		return this;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
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
	}
	public void setWin(JWin zValue) {
		oWin=zValue;
	}
	public JWin getWin( ) {
		return oWin;
	}

	public void SetWinLista(JWinList zValue) {
		oWinLista=(JWinGrid) zValue;
	}

	public void setAction(BizAction zValue) {
		oAction=zValue;
	}
	
	

	public void BaseToControl(String zModo, boolean userRequest) throws Exception {
		super.BaseToControl(zModo, userRequest);

	}
	boolean modifiedOnServer=false;
	
	public boolean isModifiedOnServer() {
		return modifiedOnServer;
	}
	public void setModifiedOnServer(boolean modifiedOnServer) {
		this.modifiedOnServer = modifiedOnServer;
	}
	public JWins getWins() throws Exception {
		JObjBDs bds = (JObjBDs)getWin().getRecord().getPropDeep(getCampo());
		setModifiedOnServer(bds.isModifiedOnServer());
		JRecords records;
//		if (getBaseForm().isConsulta() || !bds.useRawValue())
			records = bds.getValue();
//		else
//			records = bds.getRawValue();
		
//		if (records==null)
//			records = bds.getValue();

		JWins aWins = (JWins)getjWinClass().newInstance();
		aWins.setRecords(records);
		if (hasVision())
			aWins.SetVision(getVision());
		return aWins;
	}
	
	public synchronized JWinList GetLista() throws Exception {
		if (oWinLista!=null) return oWinLista;
		oWinLista = new JWinGrid(getWins(), null);
		oWinLista.setEmbedded(true);
		oWinLista.setFormTable(this);
		oWinLista.setWithIcono(isWithIcono());
		oWinLista.setResponsive(isResponsive());
		oWinLista.setEnabled(!ifReadOnly());
		oWinLista.setExtraRows(getExtraRows());
		oWinLista.setMinumusRows(getMinumusRows());
		oWinLista.setSelectedRows(isSelectedRows());
		oWinLista.setUploadOnlyEditable(isUploadOnEditable());
		oWinLista.generate(getBaseForm());
		oWinLista.setMultipleSelection(isMultipleSelect());
		return oWinLista;
	}

//	public JPanel GetListaPanel()  throws Exception {
//		return GetLista().getRootPanel();
//	}

	public BizAction getAction() {
		return this.oAction;
	}

	// -------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------//
	public  JFormTableResponsive() {
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
		GetLista().setEnabled(false);
	}
	

	@Override
	public void SetPreasignado(boolean zOk) {
			//ignore
	}
	
	// -------------------------------------------------------------------------//
	// Seteo el foco en el campo
	// -------------------------------------------------------------------------//
	@Override
	public void SetFocus() {
	}

	public void internalBuild() throws Exception {
		GetLista();
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
	public boolean isEditable()  {
		try {
			return GetLista().isEnabled();
		} catch (Exception e) {
			return super.isEditable();
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

	public BizAction getCheckedAction() throws Exception {
		return oWin.findActionByUniqueId(oAction.getIdAction());
	}

	// -------------------------------------------------------------------------//
	// De la base al control
	// -------------------------------------------------------------------------//
 @Override
	public boolean ifPreasignado() {
		return true;
	}

 public void editIfPosible(String zModo, boolean partialRefresh) throws Exception {
		if (this.ifReadOnly()) {
			this.disable();
			return;
		}
		if (zModo.equals(JBaseForm.MODO_CONSULTA)) {
			this.disable();
			return;
		}
		if ((zModo.equals(JBaseForm.MODO_MODIFICACION)||zModo.equals(JBaseForm.MODO_CONSULTA_ACTIVA))&&this.ifClave()) {
			this.disable();
			return;
		}
		if (zModo.equals(JBaseForm.MODO_ALTA)) {
			if (this.ifTieneDefault()) this.asignDefaultData();
			else this.clear();
		}
		this.edit(zModo);
	}

//	public String GetObjectType() {
//		if (getProp()!=null) return getProp().getObjectType();
//
//		// if (getFixedProp().getDato()!=null)
//		// return getFixedProp().GetType();
//
//		return "";
//	}

	public JFormControl AddItemRow(JFormControl control ) throws Exception {
		return getControles().AddControl(control);
	
	}

}
