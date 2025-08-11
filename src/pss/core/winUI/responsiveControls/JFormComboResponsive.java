package pss.core.winUI.responsiveControls;

import java.io.Serializable;

import pss.core.services.fields.JObjBD;
import pss.core.services.fields.JObject;
import pss.core.services.records.JRecord;
import pss.core.tools.PssLogger;
import pss.core.win.IControl;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;

public class JFormComboResponsive extends JFormControlResponsive {

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// INSTANCE VARIABLES
	//
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private String sDato;
	private JWin oWinDato;

	private boolean showKey;
	private boolean neverUseCache;

	public boolean isShowKey() {
		return showKey;
	}

	public JFormComboResponsive setShowKey(boolean showKey) {
		this.showKey = showKey;
		return this;
	}

	private String searchKey;
	private String searchDescrip;
	private String descripToShow;

	public String getSearchKey() {
		return searchKey;
	}

	public String getSearchDescrip() {
		return searchDescrip;
	}

	public JFormComboResponsive setSearchFields(String zSearchKey, String zSearchDescrip) {
		this.searchKey = zSearchKey;
		this.searchDescrip = zSearchDescrip;
		this.showKey = this.searchKey != null;
		return this;
	}
	public String getKeyFieldName(String defa) throws Exception {
		if (getSearchKey()==null) 
			return defa;
		return getSearchKey();
	}  
	public String getKeyValue(JWin win) throws Exception {
		if (getSearchKey() == null)
			return win.GetValorItemClave();
		return win.getRecord().getPropAsString(getSearchKey());
	}

	public String getDescriptionValue(JWin win) throws Exception {
		if (win == null)
			return null;
		if (this.getSearchDescrip() != null)
			return win.getRecord().getPropAsString(this.getSearchDescrip());
		return win.getDescripFieldValue();
	}

	public boolean isNeverUseCache() {
		return neverUseCache;
	}

	public JFormComboResponsive setNeverUseCache(boolean neverUseCache) {
		this.neverUseCache = neverUseCache;
		return this;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// CONSTRUCTORS
	//
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public JFormComboResponsive() throws Exception {

	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// METHODS
	//
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// getters / setters
	//

	public void SetControlWin(IControl zValor) {
		controlCombo = zValor;
	}

	private boolean bFirstOcur = false;

	public JFormComboResponsive setFirstOcur(boolean zValor) {
		bFirstOcur = zValor;
		return this;
	}

	public boolean isbFirstOcur() {
		return bFirstOcur;
	}

	public void SetWinsAsociado(JWins v) throws Exception {
		this.wins = v;
	}

	private void updateFromProp() throws Exception {
		if (getProp().isNull()) {
			this.sDato = null;
			this.oWinDato = null;
			return;
		}
		if (getProp().isRecord()) {
			if (this.oWinDato == null  || !getProp().getObjectValue().equals(oWinDato.getRecord())) {
				this.oWinDato = buildWin();
				this.oWinDato.setRecord((JRecord) getProp().getObjectValue());
				this.sDato = getKeyValue(oWinDato);
			}
		} else {
			if (this.sDato == null  || !getProp().toString().equals(this.sDato)) {
				this.sDato = getProp().toString();
				this.oWinDato=null;
			}

		}
	}

	public void clear() throws Exception {
		getProp().setNull();
		updateFromProp();
	}

	private JWins wins;
	private IControl controlCombo = null;

	public IControl getControlCombo() {
		if (controlCombo != null)
			return this.controlCombo;
		IControl c = new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return wins;
			}
		};
		return (this.controlCombo = c);
	}

	protected JWins getWins(boolean one) throws Exception {
		if (wins!=null) return wins;
		this.getControlCombo().setFormControl(this);
		try {
			JWins wins =  this.getControlCombo().getRecords(this, one);
			wins.setExtraFilter(getExtraFilter());
			return wins;
		} catch (Exception e) {
			PssLogger.logError(e);
			return null;
		}
	}

	public JFormControl SetValorDefault(Serializable zVal) throws Exception {
		return super.SetValorDefault(zVal);
	}

	@Override
	public JFormControl SetValorDefault(JObject zVal) throws Exception {
		return super.SetValorDefault(zVal);
	}

	@Override
	protected void asignDefaultValue(JObject value) throws Exception {
		if (value == null) {
			clear();
			return;
		}
		if (value instanceof JObjBD) {
			this.setValue((JRecord) value.getObjectValue());
		} else {
			super.asignDefaultValue(value);
		}
	}

	protected void asignDefaultValue(Object value) throws Exception {
		if (value == null) {
			clear();
			return;
		}
		if (value instanceof JRecord) {
			this.setValue((JRecord) value);
		}
		;
	}

	// -------------------------------------------------------------------------//
	// Seteo el valor del campo
	// -------------------------------------------------------------------------//
	@Override
	public void setValue(String zVal) throws Exception {
		if (zVal==null || zVal.equals("")) {
			clear();
			return;
		}

//		this.sDato = zVal;
		super.setValue(zVal);
		updateFromProp();
	}

	private JWin buildWin() throws Exception {
		return getControlCombo().buildWin();
	}

	@Override
	public void setValue(JRecord rec) throws Exception {
		if (rec == null) {
			clear();
			return;
		}
		super.setValue(rec);
		updateFromProp();
//		JWin win = buildWin();
//		win.setRecord(rec);
//		oWinDato = win;
//		sDato = getKeyValue(win);
//		super.setValue(rec);
	}

	@Override
	public void setValue(JWin win) throws Exception {
		if (win == null) {
			getProp().setNull();
			updateFromProp();
			return;
		}
//		oWinDato = win;
//		sDato = getKeyValue(win);
		super.setValue(win);
		updateFromProp();

	}

	@Override
	public JWin GetWinSelect() throws Exception {
		updateFromProp();
		return oWinDato;
	}

	
	// -------------------------------------------------------------------------//
	// Obtengo el valor del campo
	// -------------------------------------------------------------------------//
	@Override
	public String getSpecificValue() throws Exception {
		updateFromProp();
		
		if (oWinDato != null)
			return getKeyValue(oWinDato);
		
		if (isbFirstOcur() && this.sDato == null) {
			JWins wins = this.getWins(true);
			if (wins == null)
				return null;
			wins.toStatic();
			if (!wins.hasAnyRecord())
				return null;
			setValue((JWin)wins.getFirstRecord()); //set sDato
		}
		return this.sDato;
	}

	@Override
	public String getValueDescription() throws Exception {
		if (!this.hasValue())
			return "";
		if (oWinDato != null) {
			return getDescriptionValue(oWinDato);
		}

		JWins wins = this.getWins(true);
		wins.toStatic();
		if (wins == null)
			return null;
		if (!wins.hasAnyRecord())
			return null;
		return getDescriptionValue(wins.getFirstRecord());

	}

	// -------------------------------------------------------------------------//
	// Obtengo el valor del campo
	// -------------------------------------------------------------------------//
	@Override
	public boolean hasValue() throws Exception {
		return (getSpecificValue()!=null);
	}

}
