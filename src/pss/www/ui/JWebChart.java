package pss.www.ui;

import java.io.File;

import pss.common.regions.multilanguage.JLanguage;
import pss.www.platform.applications.JApplicationContext;
import pss.www.platform.applications.JWebApplicationContext;
import pss.www.platform.applications.JWebApplicationSession;
import pss.www.platform.content.generators.JXMLContent;


public abstract class JWebChart extends JWebViewFixedComponent {
  
  private String sTitle = "";
  private String sSubTitle = "";
  protected String sPath;
  
  public JWebChart() {
    this.setSize(300,200);
  }
  
  
  @Override
	public void destroy() {
    this.sTitle = null;
    this.sSubTitle = null;
    this.sPath = null;
    super.destroy();
  }
  
  @Override
	public String getComponentType() {
    return "chart";
  }
  public String getTitle() {
    return this.sTitle;
  }
  public void setTitle(String zTitle) {
    this.sTitle = zTitle;
  }

  public String getSubTitle() {
    return this.sSubTitle;
  }
  public void setSubTitle(String zSubTitle) {
    this.sSubTitle = zSubTitle;
  }

  
  /**
   * Exports the image as jpg 
   */ 
  public void exportAsImage(JApplicationContext zContext, JWebApplicationSession zSession) throws Exception {
    JWebApplicationContext oContext = ((JWebApplicationContext)zContext);
    String sFileName = zSession.getId() +"-TIME-"+ System.currentTimeMillis() + ".jpg";
    sFileName = sFileName.replace(':', '-');
    File oFile = oContext.getTransientFileSystem().createNewFile(this.getClass(), System.currentTimeMillis() + 60000, sFileName);
    
    this.doExportAsImage(oFile);
    
    this.sPath = oContext.getWebappRelativePath(oFile.getPath());
  }
  /**
   * Override setting the relative path 
   */
  @Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
    this.exportAsImage(zContent.getGenerator().getContext(), zContent.getGenerator().getSession());
    zContent.setAttribute("chart_image_path", this.sPath);
  }

  protected String getTitleForGeneration() {
    return JLanguage.translate(this.getTitle());
  }

  protected String getSubTitleForGeneration() {
    return JLanguage.translate(this.getSubTitle());
  }

//////////////////////////////////////////////////////////////////////////////
// methods to override
//////////////////////////////////////////////////////////////////////////////
    
  protected abstract void doExportAsImage(File zFile) throws Exception;

}
