/*
 * Created on 03-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.common.security;

import com.f1j.xml.dom.it;

import pss.common.security.type.BizUsuarioTipo;
import pss.core.data.BizPssConfig;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;

public class BizWebUserProfile extends JRecord {


  public JString pSkin = new JString();
  public JString pSkinMobile = new JString();
  public JString pHomePage = new JString();
  public JString pUsuario = new JString();
  public JLong pDefaultPageSize = new JLong();
  public JLong pMaxMatrix = new JLong();

  public BizWebUserProfile() throws Exception {
  }
  @Override
	public String GetTable() {
    return "SEG_USER_PREFERENCE";
  }


  @Override
	public void createProperties() throws Exception {
		addItem("USUARIO", pUsuario);
		addItem("DEFAULT_PAGESIZE", pDefaultPageSize);
		addItem("MAX_MATRIX", pMaxMatrix);
		addItem("SKIN", pSkin);
		addItem("SKIN_MOBILE", pSkinMobile);
		addItem("HOME_PAGE", pHomePage);
  }

  @Override
	public void createFixedProperties() throws Exception {
		addFixedItem( KEY, "USUARIO", "Usuario", true, true, BizSegConfiguracion.C_MAX_USERNAME_LENGTH);
		addFixedItem( FIELD, "DEFAULT_PAGESIZE", "Default pagesize", true, false, 10);
		addFixedItem( FIELD, "MAX_MATRIX", "Max matriz", true, false, 10);
		addFixedItem( FIELD, "SKIN", "Skin", true, true, 50);
		addFixedItem( FIELD, "SKIN_MOBILE", "Skin Mobile", true, false, 50);
		addFixedItem( FIELD, "HOME_PAGE", "Home Page", true, false, 50);
  }
  public String getSkinMobileName() throws Exception {
    return pSkinMobile.getValue();
  }
  public boolean hasSkinMobileName() throws Exception {
    return pSkinMobile.isNotNull();
  }
  public String getSkinName() throws Exception {
    return pSkin.getValue();
  }
  public void setUsuario(String zVal) {
    pUsuario.setValue(zVal);
  }
  public int getWinListPagination() throws Exception {
  	if (pDefaultPageSize.isNull()) return 100;
    return (int)pDefaultPageSize.getValue();
  }
  public void setDefaultPagesize(long zVal) {
    pDefaultPageSize.setValue(zVal);
  }
  public long getDefaultPagesize() throws Exception {
  	if (pDefaultPageSize.isNull()) return 100;
    return pDefaultPageSize.getValue();
  }
  public void setMaxMatrix(long zVal) {
  	pMaxMatrix.setValue(zVal);
  }
  public long getMaxMatrix() throws Exception {
  	if (pMaxMatrix.isNull()) return 1000;
    return pMaxMatrix.getValue();
  }
  public void setSkin(String zValue) {
    this.pSkin.setValue(zValue);
  }
  public void setSkinMobile(String zValue) {
    this.pSkinMobile.setValue(zValue);
  }
  public void setHomePage(String zValue) {
    this.pHomePage.setValue(zValue);
  }
  
  public String getHomePageAction(boolean login) throws Exception {
	  
	  String sHomePage = this.pHomePage.getValue();
	  
	  if (login) {
		  String loginHomePage = BizUsuario.getUsr().getObjBusiness().getAfterLoginChangeHomePage();
		  if (BizUsuario.getUsr().hasUsuarioTipo()) {
		  	if (!BizUsuario.getUsr().getObjUsuarioTipo().getHomePage().equals(""))
		  		loginHomePage = BizUsuario.getUsr().getObjUsuarioTipo().getHomePage();
		  	
		  }
		  if (loginHomePage!=null)
			  sHomePage = loginHomePage;
	  }
	  
	  if (sHomePage==null || sHomePage.trim().length() < 1) {
	    PssLogger.logError("No hay home page configurada, se toma la default");
	    return "pss.common.GuiModuloCommon_1";
	  }
	  return sHomePage;
  }

  public String getHomePageAction() throws Exception {
	  	return getHomePageAction(false);
	}
  
  
  public boolean read(String zUsuario) throws Exception {
  	this.addFilter("usuario", zUsuario);
  	return super.read();
  }
  
  public void readOrCreate(String zUsuario) throws Exception {
  	BizUsuario usr = BizUsuario.getUsr().getObjBusiness().getUserInstance();
    if (!usr.useWebUserProfile()) {
    	fill(zUsuario);
    	return;
    }
    this.addFilter("usuario", zUsuario.toUpperCase());
    this.dontThrowException(true);
    if (!this.read()) {
    	fill(zUsuario);
     	this.execProcessInsert();
			this.read();
		}
    
  }
  public void fill(String zUsuario) throws Exception {
   	BizUsuario usr = BizUsuario.getUsr().getObjBusiness().getUserInstance();
    BizUsuarioTipo tipo=null;
  	usr.dontThrowException(true);
  	if (!usr.Read(zUsuario)) {
  		if (usr.useTipoUsuario())	
  			tipo = usr.getObjUsuarioTipo();
  	}
  	
  	String sHomePage = "pss.common.GuiModuloCommon_12";

  	JMap<String,String> homePages =BizUsuario.getUsr().getObjBusiness().getBusinessHomePages();
  	if (homePages!=null) {
  		JIterator<String> ithomePage = homePages.getValueIterator();
  		while (ithomePage.hasMoreElements()) {
  			sHomePage = ithomePage.nextElement();
  		}
  	}
  	
		this.setUsuario(zUsuario.toUpperCase());
		if (tipo != null)
			this.setDefaultPagesize(tipo.getPageSize());
		else
			this.setDefaultPagesize(BizUsuario.getUsr().getObjBusiness().getPageSize());
		if (tipo != null)
			this.setHomePage(tipo.getHomePage());
		else
			this.setHomePage(sHomePage);
		if (tipo != null)
			this.setMaxMatrix(tipo.getMaxMatrix());
		else
			this.setMaxMatrix(5000);

		if (tipo != null)
			this.setSkin(tipo.getSkinWeb());
		else
			this.setSkin(BizPssConfig.getPssConfig().getSkinDefault());

  }  
	@Override
	public void processInsert() throws Exception {
		if (getDefaultPagesize()<=5 || getDefaultPagesize()>=1000) this.setDefaultPagesize(50);
		if (getMaxMatrix()<=500 || getMaxMatrix()>=10000) this.setDefaultPagesize(5000);
		if (getSkinName().equals("")) this.setSkin(BizPssConfig.getPssConfig().getSkinDefault());
		if (getSkinMobileName().equals("")) this.setSkinMobile(BizPssConfig.getPssConfig().getSkinMobileDefault());
		super.processInsert();
	}
	
  static JMap<String,String> skinsKnown = null;
  static JMap<String,String> getSkinsKnown() {
  	if( skinsKnown!=null) return skinsKnown;
  	JMap<String,String> cc = JCollectionFactory.createMap();
  	// comentado porque no es seguro que ande con el nuevo framework
  	cc.addElement("skin_default", "skin por defecto");
  	cc.addElement("skin_mobile", "skin mobile");
//  	cc.addElement("skin_baires", "skin Baires");
//  	cc.addElement("skin_spring", "skin Spring");
//  	cc.addElement("skin_sirac", "skin para SIRAC");
  	cc.addElement("skin_simple", "skin simple");
  	cc.addElement("skin_blue", "skin blue");
  	cc.addElement("skin_red", "skin red");
  	cc.addElement("skin_tkm", "skin TKM");
  	cc.addElement("skin_orange", "skin orange");
  	cc.addElement("skin_green", "skin green");
  	cc.addElement("skin_compact", "skin compact");
//  	cc.addElement("skin_siti", "skin SITI");
  	cc.addElement("skin_ypf", "skin YPF");
  	cc.addElement("skin_axion", "skin Axion");
  	cc.addElement("skin_siti", "skin Siti");
  	return cc;
  }


  public static JWins  getSkins() throws Exception {
    return  JWins.createVirtualWinsFromMap(getSkinsKnown());
  }

}
