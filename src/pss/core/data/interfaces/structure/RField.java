package pss.core.data.interfaces.structure;

import java.io.Serializable;

public class RField implements Serializable {
	
  String sTabla;
  String sCampo;
  String sRename;
  String sValor;
  String sTipo;
  boolean bAuto;
  boolean bOnlySelects;

  public RField() {}

  public String GetTabla() { return sTabla; }
  public String GetCampo() { return sCampo; }
  public String GetRename() { return sRename; }
  public String GetValor() { return sValor; }
  public String GetTipo()  { return sTipo;  }
  public boolean ifAutonumerico() { return bAuto; }
  public boolean ifOnlySelects() { return bOnlySelects; }
  public void setTabla(String value) { sTabla=value; }
  public boolean hasTable() throws Exception { return this.GetTabla()!=null && !this.GetTabla().equals("");}
  public boolean hasRename() throws Exception { return this.GetRename()!=null && !this.GetRename().equals("");}
  
}
