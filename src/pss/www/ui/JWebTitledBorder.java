package pss.www.ui;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import pss.www.platform.content.generators.JXMLContent;

public class JWebTitledBorder  extends JWebViewInternalComposite implements JWebLabelHolder {

  private String sLabel;
  
  public JWebTitledBorder() {
  }
  public JWebTitledBorder(String zLabel) {
    this.sLabel = zLabel;
  }

  @Override
	public void destroy() {
    this.sLabel = null;
    super.destroy();
  }


  @Override
	public String getComponentType() {
    return "titled_border";
  }

//  @Override
//	protected void componentToXML(JXMLContent zContent) throws Exception {
//  }

  public String getLabel() {
    return this.sLabel;
  }

  public void setLabel(String string) {
    this.sLabel = string;
  }

  public static JWebTitledBorder create(JWebViewComposite parent, JPanel zComp) throws Exception {
    JWebTitledBorder webBorder = new JWebTitledBorder();
    if (zComp.getBorder() instanceof TitledBorder)
    	webBorder.setLabel(((TitledBorder)zComp.getBorder()).getTitle());
    webBorder.takeAttributesForm(parent,zComp);
    if (parent!=null)
    	parent.addChild(webBorder.getObjectName(), webBorder);
    return webBorder;
  }

//	@Override
//	public void takeAttributesForm(Component comp) throws Exception {
//		int offsetX=0, offsetY=0;
//		Component parent = comp.getParent();
//		while (parent!=null && parent instanceof JPanel) {
//			offsetX+=parent.getX();
//			offsetY+=parent.getY();
//			parent = parent.getParent();
//		}
//		this.setSize(comp.getSize().width, comp.getSize().height-5);
//		this.setLocation(comp.getX()+offsetX, comp.getY()+offsetY);
//		Insets oMargin = UITools.borders().getBorderInsets(comp);
//		this.setMargins(oMargin.left, oMargin.top, oMargin.right, oMargin.bottom);
//		this.setVisible(this.isVisible(comp));
//	}
	
  @Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
  }

	
}
