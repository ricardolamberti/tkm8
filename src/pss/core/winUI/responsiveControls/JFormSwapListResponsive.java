package pss.core.winUI.responsiveControls;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.GuiVirtual;
import pss.core.win.IControl;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JActWinsSelect;

public class JFormSwapListResponsive extends JFormControlResponsive {

	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	// datos 
	BizAction action;
	JWin winAction;
	private JWins oWins;
	String fieldKeySource;
	String fieldKeyOptions;

	// source
	private JWins oWinsSource;
  

	private IControl oControlWins;
	
	
	private String oDato; 

	public JFormSwapListResponsive SetReadOnly(boolean zValor) {
		super.SetReadOnly(zValor);
		return this;
	}
	private String campo;
	

	private JWin winCampo;
	private Class winClassCampo;
	private String mode;
	

	public String getMode() {
		return mode;
	}

	public JFormSwapListResponsive setMode(String mode) {
		this.mode = mode;
		return this;
	}
	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public JWin getWinCampo() {
		return winCampo;
	}

	public void setWinCampo(JWin winCampo) {
		this.winCampo = winCampo;
	}

	public Class getWinClassCampo() {
		return winClassCampo;
	}

	public void setWinClassCampo(Class winClassCampo) {
		this.winClassCampo = winClassCampo;
	}


	public String getFieldKeySource() {
		return fieldKeySource;
	}

	public void setFieldKeySource(String fieldKeySource) {
		this.fieldKeySource = fieldKeySource;
	}

	public String getFieldKeyOptions() {
		return fieldKeyOptions;
	}

	public void setFieldKeyOptions(String fieldKeyOptions) {
		this.fieldKeyOptions = fieldKeyOptions;
	}
	private JMap<String,JList<GuiVirtual>> aItems=JCollectionFactory.createOrderedMap();
	// -------------------------------------------------------------------------//
	// Auxiliares Web
	// -------------------------------------------------------------------------//

	private boolean editado=false;

	// -------------------------------------------------------------------------//
	// Metodos de acceso a las Propiedades de la Clase
	// -------------------------------------------------------------------------//
	public void setControlWins(IControl zValue) {
		oControlWins=zValue;
		if (oControlWins!=null)
			oControlWins.setFormControl(this);
	}
 	public JWins getWinsFromAction() throws Exception {
		return ((JActWinsSelect)winAction.getSubmit(action)).getWinsResult();
	}
 	

	
	public void setWins(JWins zValue) {
		oWins=zValue;
	}
	public JWins getWinsSource() {
		return oWinsSource;
	}
	public void setWinsSource(JWins oWinsSource) {
		this.oWinsSource = oWinsSource;
	}

	// -------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------//
	public JFormSwapListResponsive() {
	}
	// -------------------------------------------------------------------------//
	// Blanqueo el campo
	// -------------------------------------------------------------------------//
	@Override
	public void clear() throws Exception {
		try {
		} catch (Exception Ex) {
		}
	}


	// -------------------------------------------------------------------------//
	// Edito el campo
	// -------------------------------------------------------------------------//
	@Override
	public void edit(String zModo) throws Exception {
		editado=true;
		super.edit(zModo);
	}

	// -------------------------------------------------------------------------//
	// Obtengo el valor del campo
	// -------------------------------------------------------------------------//
	@Override
	public String getSpecificValue() throws Exception {
		return oDato;
	}

	@Override
	public boolean hasValue() {
		try {
			return !getValue().equals("");
		} catch (Exception e) {
			return false;
		}
	}

	private void setVoidValue() throws Exception {
		oDato="";
	}

	
	@Override
	public void setValue(String zVal) throws Exception {
			oDato=zVal;
	}

	
	public IControl getControlWins() throws Exception {
		return oControlWins;
	}


	@Override
	public String getValueDescription() throws Exception {
		return oDato;
	}


	
	
	public BizAction getAction() {
		return action;
	}

	
	public void setAction(JWin winAction,BizAction action) {
		this.action=action;
		this.winAction = winAction;
	}
	
}
