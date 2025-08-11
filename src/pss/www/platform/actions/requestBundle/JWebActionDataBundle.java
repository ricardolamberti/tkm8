/*
 * Created on 17-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.requestBundle;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.processing.JXMLRepresentable;

/**
 * A Web action data is intended to be the exchange currency between the server
 * and the client in every action which is a request to the server.  
 * 
 * Created on 17-jun-2003
 * @author PSS
 */

public class JWebActionDataBundle implements JXMLRepresentable {

  private JMap<String, JWebActionData> oMap;
  
  
  public JWebActionDataBundle() {
    this.oMap = JCollectionFactory.createOrderedMap();
  }

  public boolean isEmpty() {
    return this.oMap.isEmpty(); 
  }
  public void clear() {
    if (this.oMap != null) {
      this.oMap.removeAllElements();
      }
  }

  public void destroy() {
    if (this.oMap != null) {
      this.oMap.removeAllElements();
      this.oMap = null;
    }
  }


  public void toXML(JXMLContent zContent) throws Exception {
    zContent.startNode("data_bundle");
    JIterator<JWebActionData> oGroupsIt = this.oMap.getValueIterator();
    while (oGroupsIt.hasMoreElements()) {
      oGroupsIt.nextElement().toXML(zContent);
    }
    zContent.endNode("data_bundle");
  }
  
  public void addData(JWebActionData zData) {
    this.oMap.addElement(zData.getId(), zData);
  }
    
  public JWebActionData addData(String zGroupId) {
    JWebActionData oGroup = new JWebActionData(zGroupId);
    this.oMap.addElement(zGroupId, oGroup);
    return oGroup;
  }
  public JWebActionData getData(String zGroupId) {
    return this.oMap.getElement(zGroupId);
  }
  public JIterator<JWebActionData> getDataIterator() {
    return this.oMap.getValueIterator();
  }

}
