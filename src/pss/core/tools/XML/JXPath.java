package pss.core.tools.XML;

import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class JXPath {

  private Element node = null;

  /**
   * Constructor
   */
  public JXPath() {}

  public JXPath(Element node) {
    this.node = node;
  }

  public void setNode(Element node) {
    this.node = node;
  }

  public XObject eval(String xstring) throws Exception {
    return XPathAPI.eval(this.node, xstring);
  }

  public NodeList list(String xstring) throws Exception {
    return this.eval(xstring).nodelist();
  }

  public Element node(String xstring) throws Exception {
    return (Element)this.eval(xstring).nodeset().nextNode();
  }

  public String attribute(String xstring) throws Exception {
    return this.eval(xstring).str();
  }

}
