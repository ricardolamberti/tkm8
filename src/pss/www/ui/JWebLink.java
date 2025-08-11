/*
 * Created on 12-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import java.awt.event.KeyEvent;

import pss.common.regions.multilanguage.JLanguage;
import pss.www.platform.content.generators.JXMLContent;



public class JWebLink extends JWebAbstractActionView {

	public static final String MODE_BUTTONGROUP = "buttongroup"; 
	public static final String MODE_BUTTON = "button"; 
	public static final String MODE_LINK = "link"; 
	
	public static final String POSICON_RIGHT_NODIV = "pull-right nodiv"; 
	public static final String POSICON_RIGHT = "pull-right"; 
	public static final String POSICON_LEFT = "pull-left"; 
	public static final String POSICON_DOWN = "pull-down"; 
	
  private boolean bOpensNewWindow;
  private boolean bOpensNewSession;
  private boolean bSubmit = false;
  private boolean bCancel = false;
  private boolean bSubmitAfterBack = true;
  private long lImportant = 0;
  private String sTitle;
  private String sSubTitle;
  private String sMode;
  private String styleImage;
	private String classPositionIcon=POSICON_RIGHT_NODIV;

  public String getClassPositionIcon() {
		return classPositionIcon;
	}
	public void setClassPositionIcon(String classPositionIcon) {
		this.classPositionIcon = classPositionIcon;
	}
	public String getStyleImage() {
		return styleImage;
	}
	public void setStyleImage(String style) {
		this.styleImage = style;
	}

	public String getMode() {
		return sMode;
	}

	public void setMode(String sMode) {
		this.sMode = sMode;
	}

	public long getImportant() {
		return lImportant;
	}

	public void setImportant(long lImportant) {
		this.lImportant = lImportant;
	}

	 public String getTitle() {
			return sTitle;
		}

		public void setTitle(String sTitle) {
			this.sTitle = sTitle;
		}

		public String getSubTitle() {
			return sSubTitle;
		}

		public void setSubTitle(String sSubTitle) {
			this.sSubTitle = sSubTitle;
		}

	private String accessKey = "";
  private long functionKey = 0;


  public long getFunctionKey() {
		return functionKey;
	}

	public void setFunctionKey(long functionKey) {
		this.functionKey = functionKey;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(long accessKey) {
		if (accessKey>=KeyEvent.VK_A && accessKey>=KeyEvent.VK_Z) this.accessKey = ""+accessKey;
		this.functionKey=0;
		if (accessKey==KeyEvent.VK_F1) this.functionKey=1;
		if (accessKey==KeyEvent.VK_F2) this.functionKey=2;
		if (accessKey==KeyEvent.VK_F3) this.functionKey=3;
		if (accessKey==KeyEvent.VK_F4) this.functionKey=4;
		if (accessKey==KeyEvent.VK_F5) this.functionKey=5;
		if (accessKey==KeyEvent.VK_F6) this.functionKey=6;
		if (accessKey==KeyEvent.VK_F7) this.functionKey=7;
		if (accessKey==KeyEvent.VK_F8) this.functionKey=8;
		if (accessKey==KeyEvent.VK_F9) this.functionKey=9;
		if (accessKey==KeyEvent.VK_F10) this.functionKey=10;
		if (accessKey==KeyEvent.VK_F11) this.functionKey=11;
		if (accessKey==KeyEvent.VK_F12) this.functionKey=12;
		
		if (this.functionKey!=0 && getToolTip().indexOf("(F")==-1) setToolTip(getToolTip()+" (F"+this.functionKey+")");
	}

	public JWebLink() {
  	
  }

  public JWebLink(String zLabel) {
    super();
    this.setLabel(zLabel);
  }

  @Override
	public String getComponentType() {
    return "link";
  }

  public boolean isOpensNewWindow() {
    return this.bOpensNewWindow;
  }

  public void setOpensNewWindow(boolean b) {
    this.bOpensNewWindow = b;
  }
  public boolean isOpensNewSession() {
    return this.bOpensNewSession;
  }

  public void setOpensNewSession(boolean b) {
    this.bOpensNewWindow = b;
  }
  public boolean isOpensNewSession(boolean b) {
    return this.bOpensNewSession;
  }
  @Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
    zContent.setAttribute("opens_new_window", this.isOpensNewWindow());
    zContent.setAttribute("opens_new_session", this.isOpensNewSession());
    zContent.setAttribute("is_submit", this.isSubmit());
	  if (sTitle!=null) zContent.setAttribute("title", JLanguage.translate(getTitle()));
    if (sSubTitle!=null) zContent.setAttribute("subtitle", JLanguage.translate(getSubTitle()));
    if (this.getImportant()!=0) 
    	zContent.setAttribute("important", this.getImportant());
    zContent.setAttribute("is_cancel", this.isCancel());
    zContent.setAttribute("mode", this.getMode());
    if ( this.getFunctionKey()!=0) zContent.setAttribute("functionKey", this.getFunctionKey());
    zContent.setAttribute("key", this.getAccessKey());
		if (styleImage!=null) 
			zContent.setAttribute("style_image", styleImage);
		if (classPositionIcon!=null) 
			zContent.setAttribute("class_position_icon", classPositionIcon);

    super.componentToXML(zContent);
  }

	
	public boolean isSubmit() {
		return bSubmit;
	}

	
	public void setSubmit(boolean submit) {
		bSubmit=submit;
	}

	
	public boolean isSubmitAfterBack() {
		return bSubmitAfterBack;
	}

	
	public void setSubmitAfterBack(boolean submit) {
		bSubmitAfterBack=submit;
	}

	
	public boolean isCancel() {
		return bCancel;
	}

	
	public void setCancel(boolean cancel) {
		bCancel=cancel;
	}

	

}
