package pss.www.ui.skins;

import java.io.File;

import pss.JPath;
import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;

public class SkinTools {

	String logo = ""; 
	String manufacturerLogo="";
	String resource="pssdata_resource";
	String logoPath="files/logos/";
	
	public String getResource() {
		return resource;
	}
	
	/**
	 * @return the logo
	 */
	public String getManufacturerLogo() {
		return "logos/"+manufacturerLogo;
	}
	
	/**
	 * @return the logo
	 */
	public String getLogo() {
		return logoPath+logo;
	}

	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	String link = ""; 
	
	public void generateIndoorLogos() throws Exception {
		logo=BizPssConfig.getPssConfig().getLogo();
		link=BizPssConfig.getPssConfig().getLink();
		manufacturerLogo=BizPssConfig.getPssConfig().getManufacturerLogo();
		BizUsuario usr = BizUsuario.getUsr();
		if ( usr != null ) {
			if ( usr.getCompany().equalsIgnoreCase("") == false ) {
				if ( usr.getObjCompany().getLogo().equalsIgnoreCase("") == false ) {
				  logo = usr.getObjCompany().getLogo();
				  checkLogoLocation();
				}
				if ( usr.getObjCompany().getLink().equalsIgnoreCase("") == false )
					link = usr.getObjCompany().getLink();
				if ( usr.getObjCompany().getManufacturerLogo().equalsIgnoreCase("") == false ) 
					manufacturerLogo = usr.getObjCompany().getManufacturerLogo();
			}
		}
	}
	
	private void checkLogoLocation() throws Exception {
	  String sLogoPath=JPath.PssPathData()+"/"+logoPath+logo;
	  File oFile=new File(sLogoPath);
		if (oFile.exists() == false) {
		  logoPath="logos/";
		  resource="pss_icon";
		}
	}

	public void generateFrontDoorLogos() throws Exception {
		logo=BizPssConfig.getPssConfig().getLogo();
	  checkLogoLocation();
		link=BizPssConfig.getPssConfig().getLink();
		manufacturerLogo=BizPssConfig.getPssConfig().getManufacturerLogo();
	}

	public static String getStyle(int z,int x, int y,int b, int r, int w, int h){
		return getStyle(z, x, y, b, r, w, h, null);
	}
	public static String getStyle(int z,int x, int y,int b, int r, int w, int h, String p){
		String s = "position:absolute;";
		if (z!=-1) s+="z-index:"+z+";";
		if (x!=-1) s+="left:"+x+"px;";
		if (y!=-1) s+="top:"+y+"px;";
		if (w!=-1) s+="width:"+w+"px;";
		if (h!=-1) s+="height:"+h+"px;";
		if (b!=-1) s+="bottom:"+b+"px;";
		if (r!=-1) s+="right:"+r+"px;";
		if (p!=null) s+="padding:"+p;
		return s;
	}

	public static String getStyle(int z,int x, int y,int b, int r, String w, String h){
		String s = "position:absolute;";
		if (z!=-1) s+="z-index:"+z+";";
		if (x!=-1) s+="left:"+x+"px;";
		if (y!=-1) s+="top:"+y+"px;";
		if (!w.equals("")) s+="width:"+w+";";
		if (!h.equals("")) s+="height:"+h+";";
		if (b!=-1) s+="bottom:"+b+"px;";
		if (r!=-1) s+="right:"+r+"px;";
		return s;
	}
	
	

	
}
