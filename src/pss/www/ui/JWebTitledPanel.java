/*
 * Created on 18-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import pss.www.platform.content.generators.JXMLContent;


public class JWebTitledPanel extends JWebPanel implements JWebTitledComponent {


  private JWebLabel oTitleLabel;
  private JWebPanel oContainerPanel;
  
  
  public JWebTitledPanel(){
  }
  
  public JWebTitledPanel(String zLabel) throws Exception {
    this.getTitleLabel().setLabel(zLabel);
  }

  public JWebTitledPanel(String zLabel, JWebIcon zIcon) throws Exception {
    this.getTitleLabel().setLabel(zLabel);
    this.getTitleLabel().setIcon(zIcon);
  }

  @Override
	public void destroy() {
    this.oTitleLabel = null;
    this.oContainerPanel = null;
    super.destroy();
  }
  

  @Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
  }

  @Override
	protected void build() throws Exception {
//    JWebBorderLayout oLayout = new JWebBorderLayout();
    this.oContainerPanel = new JWebPanel();
//    oLayout.setCenterComponent(this.oContainerPanel);
    this.oTitleLabel = new JWebLabel();
//    oLayout.setTopComponent(this.oTitleLabel);
    this.setSkinStereotype("titled_panel");
    this.oTitleLabel.setSkinStereotype("titled_panel_label");
//    super.setLayout(oLayout);
    super.addChild("title_label", this.oTitleLabel);
    super.addChild("container_panel", this.oContainerPanel);
  }

  @Override
  public void addChild(String zChildName, JWebViewComponent zComponent) throws Exception {
    throw new RuntimeException("Cannot add children to a titled panel. Add them to the container panel.");
  }
  
//  @Override
//	public void setLayout(JWebCompositeLayout zLayout) {
//    // do nothing
//  }

  public String getTitle() throws Exception {
    this.ensureIsBuilt();
    return this.oTitleLabel.getLabel();
  }
  
  public JWebLabel getTitleLabel() throws Exception {
    this.ensureIsBuilt();
    return this.oTitleLabel;
  }

  public JWebPanel getContainerPanel() throws Exception {
    this.ensureIsBuilt();
    return this.oContainerPanel;
  }
  
}
