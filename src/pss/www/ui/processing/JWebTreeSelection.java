/*
 * Created on 02-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.processing;

import pss.core.tools.collections.JList;

/**
 * A tree selection. Selection levels start at 0.
 * 
 * Created on 02-jul-2003
 * @author PSS
 */

public class JWebTreeSelection {

  private String sTreeId;
  private JList<String> oLevelValues;

  public JWebTreeSelection(String zTreeId, JList<String> zLevelValues) {
    this.sTreeId = zTreeId;
    this.oLevelValues = zLevelValues;
  }
  
  
  public String getTreeId() {
    return this.sTreeId;
  }
  
  public boolean isLevelSelected(int zLevel) {
    return zLevel < this.oLevelValues.size();
  }
  
  public int getDeepestSelectedLevel() {
    return this.oLevelValues.size()-1;
  }
  
  public String getLevelValue(int zLevel) {
    return this.oLevelValues.getElementAt(zLevel);
  }
  
  
  public String asActionDataString() {
    StringBuffer oBuff = new StringBuffer();
    oBuff.append("tree=").append(this.getTreeId());
    int iCount = this.oLevelValues.size();
    for (int i = 0; i < iCount; i++) {
      oBuff.append(",lvl").append(i).append('=').append(this.oLevelValues.getElementAt(i));
    }
    return oBuff.toString();
  }
  

}
