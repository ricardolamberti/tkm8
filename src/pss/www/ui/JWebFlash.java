package pss.www.ui;

import pss.JPath;
import pss.core.winUI.icons.GuiIconos;
import pss.www.platform.content.generators.JXMLContent;

public class JWebFlash extends JWebViewFixedComponent {


	  public static final int SOURCE_URL = 1;
	  public static final int SOURCE_Pss = 2;
	  public static final int SOURCE_SKIN = 3;

	  public static final String QUALITY_HIGH = "HIGH";
	  public static final String QUALITY_MEDIUM = "MEDIUM";
	  public static final String QUALITY_LOW = "LOW";

	  private long iSource;
	  private String sURL;
	  private String link;
	  private boolean loop = true;
	  private String quality = QUALITY_HIGH;


	  public static JWebFlash getPssIcon(long size,int zIconNumber) throws Exception {
	    String sFileName = GuiIconos.GetGlobal().buscarIcono(size,zIconNumber).GetFile();
	    return new JWebFlash(SOURCE_Pss, sFileName.toLowerCase(),null);
	  }
	  public static JWebFlash getPssIcon(String zIconFile) throws Exception {
	    return new JWebFlash(SOURCE_Pss, zIconFile.toLowerCase(),null);
	  }
	  public static JWebFlash getIcon(String zWebAppRelativeURL) throws Exception {
	    return new JWebFlash(SOURCE_URL, JPath.normalizePath(zWebAppRelativeURL),null);
	  }
	  public static JWebFlash getSkinIcon(String zSkinRelativeURL) throws Exception {
	    return new JWebFlash(SOURCE_SKIN, JPath.normalizePath(zSkinRelativeURL),null);
	  }



	  JWebFlash(long zSource, String zURL, String zLink) {
	    this.iSource = zSource;
	    this.sURL = zURL;
	    this.link = zLink;
	  }


	  @Override
		public void destroy() {
	    this.sURL = null;
	  }




	@Override
	public String getComponentType() {
		return "flash";
	}

	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
	    zContent.startNode("flash");
	    if (this.iSource==SOURCE_Pss) {
	      zContent.setAttribute("source", "pss_icon");
	    } else if (this.iSource==SOURCE_Pss) {
	      zContent.setAttribute("source", "webapp_url");
	    } else {
	      zContent.setAttribute("source", "skin_url");
	    }
	    zContent.setAttribute("file", this.sURL);
	    if (this.getSize().width!=-1) zContent.setAttribute("width", this.getSize().width);
	    if (this.getSize().height!=-1) zContent.setAttribute("height", this.getSize().height);
	    zContent.setAttribute("loop", this.isLoop()?"true":"false");
	    zContent.setAttribute("quality", this.getQuality());
	    if (this.getLink()!=null) zContent.setAttribute("link", this.getLink());
	    zContent.endNode("flash");
	}
	public boolean isLoop() {
		return loop;
	}
	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}



}
