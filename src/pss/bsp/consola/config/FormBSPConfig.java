package pss.bsp.consola.config;

import java.io.File;

import org.apache.commons.io.filefilter.SuffixFileFilter;

import pss.JPath;
import pss.bsp.bo.BizInterfazBO;
import pss.bsp.bo.formato.GuiFormatos;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormBSPConfig extends JBaseForm {


	private static final long serialVersionUID = 1245260233503L;

	
	/**
	   * Constructor de la Clase
	   */
	  public FormBSPConfig() throws Exception {
	    try { jbInit(); }
	    catch (Exception e) { e.printStackTrace(); } 
	  }

	  public GuiBSPConfig getWin() { return (GuiBSPConfig) getBaseWin(); }

	  /**
	   * Inicializacion Grafica
	   */
	  protected void jbInit() throws Exception {

	  }
	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
	    setOrganizeColumns(3);
	  	AddItemEdit( "Companía", CHAR, REQ, "company" ).setVisible(false);
	    AddItemEdit( "Usuario", CHAR, REQ, "usuario" ).setVisible(false);
	    
//	  	JFormPanelResponsive panelA = AddItemPanel("panel");
//	  	JFormPanelResponsive panelB = AddItemPanel("panel");
//	  	JFormPanelResponsive panelC = AddItemPanel("panel");
//	  	panelA.setOrganizeColumns(1).setFormatFields(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_LEFT);
//	  	panelB.setOrganizeColumns(2).setFormatFields(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_RIGHT);
//	  	panelC.setOrganizeColumns(3).setFormatFields(JFormPanelResponsive.FIELD_LABEL_VERTICAL);
	    
//	    JFormDropDownButtonResponsive b= AddItemDropDownButton("boton");
//	    b.setSizeColumns(2);
//	    b.AddItemButton("boton 1", 120, false);
//	    b.AddItemButton("boton 2", 130, false);
//	    b.AddItemButton("boton 3", 110, false);
	    AddItemEdit( "Factor", INT, REQ, "factor" ).setFormatFields(JFormPanelResponsive.FIELD_LABEL_VERTICAL).SetValorDefault(1).setPopupIcon(true);
	    AddItemEdit( "Dias en expirar reservas", INT, REQ, "expire_reservas" ).setFormatFields(JFormPanelResponsive.FIELD_LABEL_VERTICAL).SetValorDefault(10).setPopupIcon(true);
	    AddItemEdit( "precisión", FLOAT, REQ, "precision" ).setFormatFields(JFormPanelResponsive.FIELD_LABEL_VERTICAL).SetValorDefault(1);
	    AddItemCombo( "Formato Fecha", CHAR, REQ, "formato_fecha" , getFormatosFecha()).SetValorDefault(BizBSPConfig.FORMATO_FECHA_DD);
	    AddItemCombo( "Interfaz por defecto", CHAR, OPT, "defa_interfaz" , getTipoInterfaz());
	    AddItemCombo( "Formato por defecto", CHAR, OPT, "defa_formato",  new JControlCombo() {@Override
	  		public JWins getRecords(boolean one) throws Exception {return getTipoFormatos(one);}} );
	    AddItemEdit( "Impuesto local", CHAR, REQ, "tipo_imp" ).SetValorDefault("DL");
	    AddItemEdit( "E-mail", CHAR, OPT, "email" );
	    AddItemCombo( "Fondo", CHAR, OPT, "fondo",  new JControlCombo() {@Override
	  		public JWins getRecords(boolean one) throws Exception {return getFondos(one);}} );

	    JFormTabPanelResponsive panel = AddItemTabPanel();
	    panel.AddItemListOnDemand(10);
	    panel.AddItemListOnDemand(20);
	    panel.AddItemListOnDemand(25);
	    panel.AddItemListOnDemand(26);
	    panel.AddItemListOnDemand(30);
	    panel.AddItemListOnDemand(40);
	    panel.AddItemListOnDemand(50);	
	    panel.AddItemListOnDemand(60);	
	    panel.AddItemListOnDemand(70);	
	    panel.AddItemListOnDemand(71);	
	    panel.AddItemListOnDemand(75);	
	    panel.AddItemListOnDemand(76);	
	    panel.AddItemListOnDemand(80);	
	    panel.AddItemListOnDemand(90);	
	    panel.AddItemListOnDemand(100);	
	    panel.AddItemListOnDemand(105);	
	    panel.AddItemListOnDemand(107);
	    panel.AddItemListOnDemand(115);	    
	    panel.AddItemListOnDemand(200);
	    panel.AddItemListOnDemand(210);
	    panel.AddItemListOnDemand(220);
	    panel.AddItemListOnDemand(230);
	    panel.AddItemListOnDemand(240);
			  panel.AddItemForm(130);
	  }	  
	  private JWins getTipoFormatos(boolean one) throws Exception {
	  	GuiFormatos guis = new GuiFormatos();
	  	if (one) {
		  	guis.getRecords().addFilter("company", this.getWin().GetcDato().getCompany());
//		  	guis.getRecords().addFilter("owner", this.getWin().GetcDato().getUsuario());
		  	guis.getRecords().addFilter("id_formato", this.getWin().GetcDato().getDefaultFormato());
	  	} else {
		  	guis.getRecords().addFilter("company", this.findControl("company").getValue());
//		  	guis.getRecords().addFilter("owner", this.findControl("usuario").getValue());
	  	}
	  	return guis;
	 
	  }  
	  private JWins getFondos(boolean one) throws Exception {
			JMap<String,String> map = JCollectionFactory.createOrderedMap();
			File dir = new File(JPath.PssImages()+"/backgrounds");
			String [] names = dir.list(new SuffixFileFilter(".jpg"));
			if (names==null) return JWins.createVirtualWinsFromMap(map);
			for (String name:names) {
				map.addElement(name, name);
			}
			return JWins.createVirtualWinsFromMap(map);
	  
	  }  
	  private JWins getTipoInterfaz() throws Exception {
	  	return JWins.createVirtualWinsFromMap(BizInterfazBO.getTiposInterfaz());
	  }
	  
	  private JWins getFormatosFecha() throws Exception {
	  	return JWins.createVirtualWinsFromMap(BizBSPConfig.getFormatosFecha());
	  }


	}  //  @jve:decl-index=0:visual-constraint="10,10" 
