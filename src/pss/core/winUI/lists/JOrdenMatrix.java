package pss.core.winUI.lists;

import pss.core.services.fields.JLong;
import pss.core.win.JWin;

public class JOrdenMatrix {
	private String campo;
	private long limite;
	private boolean asc;
	
	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

	public boolean isDesc() {
		return !asc;
	}

	public JOrdenMatrix(String zcampo,long zlimite) {
		campo=zcampo;
		limite=zlimite;
	}
	public JOrdenMatrix(String zcampo,long zlimite, boolean zasc) {
		campo=zcampo;
		limite=zlimite;
		asc = zasc;
	}
	
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public long getLimite() {
		return limite;
	}
	public void setLimite(long limite) {
		this.limite = limite;
	}
	
	public long getValueRank(JWin win) throws Exception {
		return ((JLong)win.getRecord().getPropRaw(getCampo())).getValue();
	}


}
