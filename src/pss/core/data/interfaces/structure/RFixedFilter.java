package pss.core.data.interfaces.structure;

import java.io.Serializable;

public class RFixedFilter  implements Serializable {
	
  private String sFiltro;
  private boolean dynamic=false;
  private String sTableAsoc;


	public RFixedFilter() {}

  public String getFiltro() { return sFiltro; }
  public void setFixedFilter(String value) { sFiltro=value;}
  public void setDynamic(boolean value) { this.dynamic=value;}
  public boolean isDynamic() { return this.dynamic;}
  public String getsTableAsoc() {return sTableAsoc;}
	public void setsTableAsoc(String sTableAsoc) {		this.sTableAsoc = sTableAsoc;	}

  
}
