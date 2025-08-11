package pss.common.terminals.drivers.WinsGrid;

import pss.common.layout.JFieldSetTicket;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class JRecordCustom extends JRecord {

	private JString pTipo = new JString();
	
	public void setTipo(String v) throws Exception {
		this.pTipo.setValue(v);
	}
	
	public JRecordCustom() throws Exception{
	}
	
	public boolean isTipoFooter() throws Exception {
		return this.pTipo.getValue().equals(JFieldSetTicket.REPORT_FOOTER);
	}
	

	
}
