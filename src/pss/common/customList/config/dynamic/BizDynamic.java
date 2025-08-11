package  pss.common.customList.config.dynamic;

import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.customlist.JCorteControl.JValorCorte;
import pss.core.services.records.JRecord;

public class BizDynamic extends JRecord {

	private BizCustomList customList;
	private JValorCorte valorCorte;
	
	public JValorCorte getValorCorte() {
		return valorCorte;
	}

	public void setValorCorte(JValorCorte valorCorte) {
		this.valorCorte = valorCorte;
	}

	public void setCustomList(BizCustomList customList) {
		this.customList = customList;
	}

	public BizCustomList getCustomList() {
		return customList;
	}

	/**
	 * Constructor de la Clase
	 */
	public BizDynamic() throws Exception {
	}

	public void createProperties() throws Exception {
		
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {

	}
	@Override
	protected boolean loadForeignFields() throws Exception {
		return true;
	}
	public void copyProperties(JRecord zBD) throws Exception {
		this.copyProperties(zBD, true);
		customList = ((BizDynamic)zBD).getCustomList();
	}
	

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "";
	}




}
