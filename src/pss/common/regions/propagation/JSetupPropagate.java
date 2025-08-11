package  pss.common.regions.propagation;

/**
 * <p>Title: Pss project</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JList;

public class JSetupPropagate {
  private boolean bPropagate = false;

  private boolean bPropagateChildren = true;
  private boolean bPropagateParent = true;
  private boolean bPropagateMaster = true;

  private JList<String> oNodes= null;

  public JSetupPropagate() {
  }

  public void setPropagate(boolean zPropagate) throws Exception {bPropagate = zPropagate;}
  public void setPropagateChildren(boolean zPropagate) throws Exception {bPropagateChildren = zPropagate;}
  public void setPropagateParent(boolean zPropagate)  throws Exception {bPropagateParent = zPropagate;}
  public void setPropagateMaster(boolean zPropagate)  throws Exception {bPropagateMaster = zPropagate;}

  public void addNode(String zNode) throws Exception {
    if (oNodes==null) oNodes = JCollectionFactory.createList();
    oNodes.addElement(zNode);
  }

  public boolean isPropagate() throws Exception { return bPropagate; }
  public boolean isPropagateToChildren() throws Exception { return bPropagateChildren; }
  public boolean isPropagateToParent() throws Exception { return bPropagateParent; }
  public boolean isPropagateToMaster() throws Exception { return bPropagateMaster; }
  public boolean isPropagateToNode(String zNode) throws Exception {
      if (oNodes==null) return true;
      return oNodes.containsElement(zNode);
  }


}
