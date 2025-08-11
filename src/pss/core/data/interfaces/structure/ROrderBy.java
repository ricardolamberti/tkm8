package pss.core.data.interfaces.structure;

import java.io.Serializable;

public class ROrderBy  implements Serializable {
	
  String sTabla;
  String sCampo;
  String sOrden;
  boolean dynamic=false;
  IOrderByControl control;


	public ROrderBy() {}

  public String GetTabla() { return sTabla; }
  public String GetCampo() { return sCampo;}
  public String GetOrden() { return sOrden; }
  public void setDynamic(boolean value) { this.dynamic=value;}
  public boolean isDynamic() { return this.dynamic;}
  public IOrderByControl getControl() {	return control;}

}

