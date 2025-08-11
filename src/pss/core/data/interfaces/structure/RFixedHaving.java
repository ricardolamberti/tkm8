package pss.core.data.interfaces.structure;

import java.io.Serializable;

public class RFixedHaving  implements Serializable {
	
  private String sFiltro;
  private boolean dynamic=false;

  public RFixedHaving() {}

  public String getFiltro() { return sFiltro; }
  public void setFixedFilter(String value) { sFiltro=value;}
  public void setDynamic(boolean value) { this.dynamic=value;}
  public boolean isDynamic() { return this.dynamic;}
  
  
}
