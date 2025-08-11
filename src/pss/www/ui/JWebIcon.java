/*
 * Created on 18-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import pss.JPath;
import pss.core.winUI.icons.GuiIcon;
import pss.core.winUI.icons.GuiIconos;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.processing.JXMLRepresentable;

/**
 * 
 * 
 * Created on 18-jun-2003
 * @author PSS
 */

public class JWebIcon implements JXMLRepresentable {

  public static final int SOURCE_URL = 1;
  public static final int SOURCE_Pss = 2;
  public static final int SOURCE_SKIN = 3;
  public static final int SOURCE_RESPONSIVE = 4;
  public static final int SOURCE_PSSDATA = 5;


  private int iSource;
  private String sURL;
  private String style;
  private String classImage;

	public String getClassImage() {
		return classImage;
	}
	public void setClassImage(String classimg) {
		this.classImage = classimg;
	}
	public String getStyleImage() {
		return style;
	}
	public void setStyleImage(String style) {
		this.style = style;
	}
  public void setURL(String v) {
  	this.sURL=v;
  }
  public String getURL() throws Exception {
    return this.sURL;
  }
	public static JWebIcon findIcon(int nro) throws Exception {
		if (nro==0) return null;
		return JWebIcon.getPssIcon(GuiIconos.RESPONSIVE, nro);
	}
	public static JWebIcon getPssIcon(long size, int zIconNumber) throws Exception {
  	GuiIcon icono=null;
    if (size==GuiIconos.RESPONSIVE) {
    	icono = GuiIconos.GetGlobal().buscarIconoSize(size,zIconNumber);
      if (icono!=null){
      	String sFileName = icono.GetFile();
        return new JWebIcon(SOURCE_RESPONSIVE, sFileName);
      }
    }
  	icono = GuiIconos.GetGlobal().buscarIcono(size,zIconNumber);
    String sFileName = icono.GetFile();
    return new JWebIcon(SOURCE_Pss, sFileName);
  }
  public static JWebIcon getPssIcon(int zIconNumber) throws Exception {
    String sFileName = GuiIconos.GetGlobal().buscarIcono(zIconNumber).GetFile();
    return new JWebIcon(SOURCE_Pss, sFileName);
  }
  public static JWebIcon getPssDataResource(String zIconFile) throws Exception {
    return new JWebIcon(SOURCE_PSSDATA, zIconFile);
  }
  public static JWebIcon getPssIcon(String zIconFile) throws Exception {
    return new JWebIcon(SOURCE_Pss, zIconFile);
  }
  public static JWebIcon getIcon(String zWebAppRelativeURL) throws Exception {
    return new JWebIcon(SOURCE_URL, JPath.normalizePath(zWebAppRelativeURL));
  }
  public static JWebIcon getSkinIcon(String zSkinRelativeURL) throws Exception {
    return new JWebIcon(SOURCE_SKIN, JPath.normalizePath(zSkinRelativeURL));
  }
  public static JWebIcon getResponsiveIcon(String zSkinRelativeURL) throws Exception {
    return new JWebIcon(SOURCE_RESPONSIVE, zSkinRelativeURL);
  }

  public static JWebIcon getURLIcon(String zURL) throws Exception {
    return new JWebIcon(-1, JPath.normalizePath(zURL));
  }


  JWebIcon(int zSource, String zURL) {
    this.iSource = zSource;
    this.sURL = zURL;
  }


  public void destroy() {
    this.sURL = null;
  }


  public void toXML(JXMLContent zContent) throws Exception {
    zContent.startNode("icon");
    if (this.iSource==SOURCE_Pss) {
      zContent.setAttribute("source", "pss_icon");
    } else if (this.iSource==SOURCE_Pss) {
      zContent.setAttribute("source", "webapp_url");
    } else if (this.iSource==SOURCE_SKIN) {
      zContent.setAttribute("source", "skin_url");
    } else if (this.iSource==SOURCE_RESPONSIVE) {
      zContent.setAttribute("source", "responsive");
    } else if (this.iSource==SOURCE_PSSDATA) {
      zContent.setAttribute("source", "pssdata_resource");
    } else {
    	zContent.setAttribute("source", "");
    }
		if (getClassImage()!=null)
			zContent.setAttribute("class_image", getClassImage());
    zContent.setAttribute("style_image", this.getStyleImage());
    zContent.setAttribute("file", this.sURL);
    zContent.endNode("icon");
  }
  

}
