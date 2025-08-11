package pss.common.customLogin;

import java.io.File;

import pss.JPath;
import pss.core.data.BizPssConfig;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;

public class FormCustomLogin extends JBaseForm {

  public FormCustomLogin() throws Exception {
  }

  public GuiCustomLogin GetWin() { return (GuiCustomLogin) getBaseWin(); }


  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, UINT, OPT, "id" ).setHide(true);
    AddItemEdit( "orden", UINT, OPT, "orden" ).setHide(true);
    AddItemEdit( "Descripción", CHAR, REQ, "description" );
    AddItemCheck("Activo", OPT, "Active").SetValorDefault(true);
    AddItemDateTime( "fecha desde", DATETIME, OPT, "date_from" ).setSizeColumns(6);
    AddItemDateTime( "fecha hasta", DATETIME, OPT, "date_to" ).setSizeColumns(6);
    AddItemDateTime( "hora desde", HOUR, OPT, "hour_from" ).setSizeColumns(6);
    AddItemDateTime( "hora hasta", HOUR, OPT, "hour_to" ).setSizeColumns(6);

    AddItemEdit( "Bienvenida", CHAR, OPT, "welcome" ).setSizeColumns(6).SetValorDefault("Bienvenido");
    AddItemEdit( "Logo titulo", LONG, OPT, "title_logo" ).setSizeColumns(6).SetValorDefault(15000);

    AddItemEdit( "Bienvenida texto", CHAR, OPT, "welcome_text" ).setSizeColumns(6).SetValorDefault("Ud ha ingresado al área privada del SITI. Ingrese los datos de su usuario y presione 'confirmar' para comenzar a administrar su Empresa");
    AddItemEdit( "Pie texto", CHAR, OPT, "footer_text" ).setSizeColumns(6).SetValorDefault("* Sitio optimizado para : Internet Explorer 7 o superior, Mozilla Firefox 3 o superior");
    AddItemEdit( "Link texto", CHAR, OPT, "text_link" ).setSizeColumns(6).SetValorDefault("Pentaware S.A.");
    AddItemEdit( "Link", CHAR, OPT, "link" ).setSizeColumns(6).SetValorDefault("http://www.pentaware.com.ar");
    AddItemCheck("Captcha", OPT, "captcha").setAlign(JFormCheckResponsive.LABEL_TOP).setSizeColumns(1).SetValorDefault(false);
    AddItemEdit( "Titulo secundario", CHAR, OPT, "secondary_title" ).setSizeColumns(9).SetValorDefault("Ticket Mining");
    AddItemColor( "Color", CHAR, OPT, "color_secondary" ).setSizeColumns(2);
    AddItemEdit( "Leyenda usuario", CHAR, OPT, "leyenda_usuario" ).setSizeColumns(4).SetValorDefault("Usuario");
    AddItemEdit( "Leyenda clave", CHAR, OPT, "leyenda_clave" ).setSizeColumns(4).SetValorDefault("Clave");
    AddItemEdit( "Leyenda boton", CHAR, OPT, "leyenda_boton" ).setSizeColumns(4).SetValorDefault("Ingresar");

    AddItemMultiple( "Logo", OPT, "logo"  ,getLogos()).setSizeColumns(12).SetValorDefault(BizPssConfig.getPssConfig().getLogo());
    AddItemMultiple("Fondo izq",  OPT, "background_a" ,getFondos()).setSizeColumns(6).SetValorDefault("pss_icon/backgrounds/fondotkm.png");
    AddItemMultiple( "Fondo der", OPT, "background_b" ,getFondos()).setSizeColumns(6);
    AddItemColor( "Color izq", CHAR, OPT, "color_a" ).setSizeColumns(4);
    AddItemColor( "Color der", CHAR, OPT, "color_b" ).setSizeColumns(4).SetValorDefault("FFFFFF");
    AddItemColor( "Color Item", CHAR, OPT, "color_item" ).setSizeColumns(4).SetValorDefault("FFFFFF");
    AddItemEdit( "estilo", CHAR, OPT, "style" );

    AddItemTable("Items", "textos", GuiCustomLoginComponents.class);
  } 
	public static JWins getFondos() throws Exception {
		JRecords<BizVirtual> records =JRecords.createVirtualBDs();
	  
		File dir = new File(JPath.PssPathFondos());
		if (!dir.exists()) dir = new File(JPath.PssPathFondos());
		String [] names = dir.list();
		if (names!=null) {
			for (String name:names) {
				records.addItem(JRecord.virtualBD("pssdata_resource/fondos/"+name,name, "pssdata_resource/fondos/"+name));
			}
		}
		File dirI = new File(JPath.PssImages().substring(5)+"/backgrounds/");
		String [] namesI = dirI.list();
		if (namesI!=null) {
			for (String name:namesI) {
				records.addItem(JRecord.virtualBD("pss_icon/backgrounds/"+name,name, "pss_icon/backgrounds/"+name));
			}
		}
		
		return JWins.CreateVirtualWins(records);
	}
	public static JWins getLogos() throws Exception {
		JRecords<BizVirtual> records =JRecords.createVirtualBDs();
	  
		File dir = new File(JPath.PssPathData()+"/"+JPath.PssPathLogos());
		if (!dir.exists()) dir = new File(JPath.PssPathData()+"/"+JPath.PssPathLogos());
		String [] names = dir.list();
		if (names!=null) {
			for (String name:names) {
				File f = new File(JPath.PssPathData()+"/"+JPath.PssPathLogos()+"/"+name);
				if (f.isDirectory()) continue;
				records.addItem(JRecord.virtualBD(name,name, "pssdata_resource/"+JPath.PssPathLogos()+name));
			}
		}
//		dir = new File(JPath.PssPathData()+"/"+JPath.PssPathLogos()+"/DEFAULT");
//		names = dir.list();
//		if (names!=null) {
//			for (String name:names) {
//				File f = new File(JPath.PssPathData()+"/"+JPath.PssPathLogos()+"/DEFAULT/"+name);
//				if (f.isDirectory()) continue;
//				records.addItem(JRecord.virtualBD(name,name, "pssdata_resource/"+JPath.PssPathLogos()+"/DEFAULT/"+name));
//			}
//		}
		File dirI = new File(JPath.PssImages().substring(5)+"/logos/");
		String [] namesI = dirI.list();
		if (namesI!=null) {
			for (String name:namesI) {
				records.addItem(JRecord.virtualBD(name,name, "pss_icon/logos/"+name));
			}
		}
		return JWins.CreateVirtualWins(records);
	}
}
