package pss.core.winUI.controls;

import pss.core.services.fields.JObject;
import pss.core.services.records.JProperty;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.lists.JWinList;

public interface IFormTable {

	int getUpdateAction();

	void setUpdateAction(int updateAction);

	int getNewAction();
	int getOtherNewAction();

	void setNewAction(int newAction);
	
	String getCampo();
	public JObject getProp();
	 public String getName();
	public JProperty getFixedProp() ;

	void setCampo(String campo);

	Class getjWinClass();

	void setjWinClass(Class jWinClass);

	JBaseForm getBaseForm();
	public String getRowToolbarPos() ;
	void setBaseForm(JBaseForm baseForm);
	public boolean isNewsEditable() ;
	void setWin(JWin zValue);

	JWin getWin();
	public JFormControl AddItemRow(JFormControl control ) throws Exception;

	void SetWinLista(JWinList zValue);

	void setAction(BizAction zValue);

	void BaseToControl(String zModo, boolean userRequest) throws Exception;

	JWinList GetLista() throws Exception;

//	JPanel GetListaPanel() throws Exception;

	BizAction getAction();

	// -------------------------------------------------------------------------//
	// Blanqueo el campo
	// -------------------------------------------------------------------------//
	void clear();

	// -------------------------------------------------------------------------//
	// Protejo el campo
	// -------------------------------------------------------------------------//
	void disable() throws Exception;

	// -------------------------------------------------------------------------//
	// Seteo el foco en el campo
	// -------------------------------------------------------------------------//
	void SetFocus();

	// -------------------------------------------------------------------------//
	// Edito el campo
	// -------------------------------------------------------------------------//
	void edit(String zModo) throws Exception;

	// -------------------------------------------------------------------------//
	// Obtengo el valor del campo
	// -------------------------------------------------------------------------//
	String getSpecificValue();

	// -------------------------------------------------------------------------//
	// Obtengo el valor del campo
	// -------------------------------------------------------------------------//
	boolean hasValue();

	BizAction getCheckedAction() throws Exception;

	// -------------------------------------------------------------------------//
	// De la base al control
	// -------------------------------------------------------------------------//
	boolean ifPreasignado();

	boolean isEditable() throws Exception;
}