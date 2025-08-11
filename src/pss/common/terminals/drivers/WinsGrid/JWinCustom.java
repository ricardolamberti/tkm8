package pss.common.terminals.drivers.WinsGrid;

import java.awt.Color;

import pss.core.services.records.JRecord;
import pss.core.tools.JTools;
import pss.core.win.JWin;

public class JWinCustom extends JWin {
	
	@Override
	public JRecord ObtenerDato() throws Exception {
		return new JRecordCustom(); 
	}
	
	@Override
	public int GetNroIcono() throws Exception {
		return 718;
	}
	
	public JRecordCustom GetcDato() throws Exception {
		return (JRecordCustom) this.getRecord();
	}
	
	@Override
	public String getFieldBackground(String zColName) throws Exception {
		if (this.GetcDato().isTipoFooter()) return JTools.ColorToString(Color.blue);
		return super.getFieldBackground(zColName);
	}
	
	@Override
	public void createActionMap() throws Exception {
	}

}
