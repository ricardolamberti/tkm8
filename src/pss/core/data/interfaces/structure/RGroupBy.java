package pss.core.data.interfaces.structure;

import java.io.Serializable;

public class RGroupBy implements Serializable {
	
  String sTabla;
  String sCampo;

  public RGroupBy() {}

  public String GetTabla() { return sTabla; }
  public String GetCampo() { return sCampo; }
  public boolean hasTable() {
  	if (sTabla==null) return false;
  	if (sTabla.equals("")) return false;
  	return true;
  }
}
