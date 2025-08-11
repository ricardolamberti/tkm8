package pss.www.ui;

import pss.www.platform.content.generators.JXMLContent;

public class JWebElementList extends JWebViewInternalComposite implements JWebIconHolder {

	JWebIcon icono;
	
  public void setIcon(JWebIcon icono) {
		this.icono = icono;
	}

	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
  }

  @Override
	public String getComponentType() {
    return "li";
  }

	@Override
	public JWebIcon getIcon() {
		return icono;
	}
  public JWebIcon getMaximizeIcon() {
		return null;
	}
}
