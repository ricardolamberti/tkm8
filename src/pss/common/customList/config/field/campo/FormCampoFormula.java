package  pss.common.customList.config.field.campo;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import pss.common.customList.config.relation.GuiCamposGallery;
import pss.common.layout.JFieldSetWins;
import pss.core.ui.components.JPssColorPicker;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssHtmlTextArea;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.www.platform.actions.resolvers.JDoLayoutActionResolver;

public class FormCampoFormula extends JBaseForm {


private static final long serialVersionUID = 1226426905817L;

JPssEdit company = new JPssEdit  ();
JPssEdit listId = new JPssEdit  ();
JPssEdit secuencia = new JPssEdit  ();
  JPssLabel lcampo = new JPssLabel();

	private JPssHtmlTextArea jDescrCampo = new JPssHtmlTextArea();
	private JPssEdit jCampo = new JPssEdit();
	private JPssEdit recordOwner = new JPssEdit();
	private JPssEdit recordSource = new JPssEdit();
	private JPssEdit jSerialDeep = new JPssEdit();	
//	private JPssEdit recordSet = new JPssEdit();

	private JPssLabel lfunc = null;

	private JComboBox jFunc = null;

	private JPssLabel lRecordSet = null;

	private JPssEdit jRelId = new JPssEdit();

	private JPssLabel lFormat = null;

	private JComboBox jFormat = null;

	private JTextField jFormatParam = null;

	private JPssLabel lNombre = null;

	private JTextField jNombrecol = null;

	private JComboBox jClaveC = null;

	private JCheckBox jCheckBox = null;

	private JCheckBox jCheckBox1 = null;

	private JCheckBox jCheckBox11 = null;

	private JPssColorPicker JPssColorPicker = null;

	private JPssColorPicker JPssColorPicker1 = null;

	private JPssColorPicker JPssColorPicker2 = null;

	private JPssColorPicker JPssColorPicker3 = null;

	private JTextField jTextField1 = null;

	private JCheckBox jCheckBox12 = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JCheckBox jCheckBox13 = null;

	private JCheckBox jCheckBox131 = null;

	private JTextField jTextField = null;

	private JLabel jLabel11 = null;

	private JLabel jLabel111 = null;

	private JCheckBox jCheckBox2 = null;
	
/**
   * Constructor de la Clase
   */
  public FormCampoFormula() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiCampo getWin() { return (GuiCampo) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    jLabel111 = new JLabel();
    jLabel111.setBounds(new Rectangle(223, 532, 92, 21));
    jLabel111.setText("Menores a");
    jLabel11 = new JLabel();
    jLabel11.setBounds(new Rectangle(222, 475, 92, 21));
    jLabel11.setText("Mayores a");
    jLabel1 = new JLabel();
    jLabel1.setBounds(new Rectangle(224, 409, 76, 18));
    jLabel1.setText("Color letras");
    jLabel = new JLabel();
    jLabel.setBounds(new Rectangle(44, 409, 76, 18));
    jLabel.setText("Color Fondo");
    lNombre = new JPssLabel();
    lNombre.setBounds(new Rectangle(16, 284, 71, 22));
    lNombre.setText("Nombre Col.");
    lFormat = new JPssLabel();
    lFormat.setBounds(new Rectangle(11, 104, 71, 22));
    lFormat.setText("Formato");
    lRecordSet = new JPssLabel();
    lRecordSet.setBounds(new Rectangle(11, 2, 85, 22));
    lRecordSet.setText("Fuente Datos");
    lfunc = new JPssLabel();
    lfunc.setBounds(new Rectangle(16, 336, 71, 22));
    lfunc.setText("Función");
    setLayout(null);
    this.setSize(new Dimension(564, 564));


//    if (!BizUsuario.getUsr().getObjBusiness().isCustomListSimplify())
//    	this.add(lFormat, null);
    this.add(lfunc, null);
    this.add(getJFunc(), null);
    this.add(getJFormat(), null);
    this.add(getJFormatParam(), null);
    this.add(lNombre, null);
    this.add(getJNombrecol(), null);
    this.add(getJCheckBox(), null);
    this.add(getJCheckBox1(), null);
    this.add(getJCheckBox11(), null);
    this.add(getJPssColorPicker(), null);
    this.add(getJPssColorPicker1(), null);
    this.add(getJPssColorPicker2(), null);
    this.add(getJPssColorPicker3(), null);
    this.add(getJTextField1(), null);
    this.add(getJCheckBox12(), null);
    this.add(jLabel, null);
    this.add(jLabel1, null);
    this.add(getJCheckBox13(), null);
    this.add(getJCheckBox131(), null);
    this.add(getJTextField(), null);
    this.add(jLabel11, null);
    this.add(jLabel111, null);
    this.add(getJCheckBox2(), null);
    
    JPssLabel pslblCampo = new JPssLabel();
    pslblCampo.setText("Campo");
    pslblCampo.setBounds(new Rectangle(11, 53, 71, 22));
    pslblCampo.setBounds(11, 11, 71, 22);
    add(pslblCampo);
    
    jDescrCampo = new JPssHtmlTextArea();
    jDescrCampo.setBounds(new Rectangle(94, 53, 298, 22));
    jDescrCampo.setBounds(94, 11, 460, 262);
    add(jDescrCampo);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" );
    AddItem( listId, UINT, REQ, "list_id" );
    AddItem( secuencia, UINT, OPT, "secuencia" );
//    AddItem( jOrden, UINT, OPT, "orden" );
		AddItem(jSerialDeep, CHAR, OPT, "serial_deep");
    AddItem( recordOwner, CHAR, REQ, "record_owner" );
    AddItem( recordSource, CHAR, REQ, "record_source" );
    AddItem( jRelId, CHAR, REQ, "rel_id");
//    , new JControlCombo() {
//    	public JWins getRecords(boolean one) throws Exception {
//    		return getRecordSets(one);
//    	}
//    }).setRefreshForm(true);
    
    AddItem( jCampo, CHAR, OPT, "campo");
    JFormControl c = AddItem( jDescrCampo, CHAR, OPT, "formato_param" , false, JDoLayoutActionResolver.getDatosPlantilla(getWin().GetcDato().getObjCustomList().getObjRelation().getClassTarget().getName(), JFieldSetWins.SECTOR_MAIN),
    		getWin().GetcDato().getObjCustomList().getObjRelation().getClassTarget().getName(),400,
    		0,0,0,0,null, 
    		false);

//    JFormControl c = AddItem( jDescrCampo, CHAR, OPT, "descr_campo" );
//    c.SetValorDefault(this.getWin().GetcDato().getDescrCampo());
//    c.SetReadOnly(true);

//    AddItem( jFormat, CHAR, OPT, "formato", new JControlCombo() {
//    	public JWins getRecords(boolean one) throws Exception {
//    		return JWins.createVirtualWinsFromMap(BizCampo.getFormatMap());
//    	}
//    }).setVisible(!BizUsuario.getUsr().getObjBusiness().isCustomListSimplify());
    AddItem( jNombrecol, CHAR, OPT, "nombre_columna");
    //AddItem( jFormatParam, CHAR, OPT, "formato_param").setVisible(!BizUsuario.getUsr().getObjBusiness().isCustomListSimplify());
    AddItem( getJCheckBox(), CHAR, OPT, "visible", "S","N").SetValorDefault(true);
    AddItem( getJCheckBox2(), CHAR, OPT, "porcentaje", "S","N").SetValorDefault(false);
    c = AddItem( jFunc, CHAR, OPT, "funcion", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return getFunciones(one);
    	}
    });
    c.SetValorDefault(BizCampo.FUNTION_FORMULA);
    
    if (this.getWin().GetcDato().getObjCustomList().isLista()) { 
    	c.setVisible(false);
    	lfunc.setVisible(false);
    }
    c=AddItem( getJCheckBox12(),  OPT, "colores");
    c.setRefreshForm(true);
    c.SetValorDefault(false);
    c=AddItem( getJPssColorPicker(), CHAR, OPT, "color_background");
    c=AddItem( getJPssColorPicker1(), CHAR, OPT, "color_foreground");
    c=AddItem( getJCheckBox1(),  OPT, "marca_mayores");
    c.setRefreshForm(true);
    c.SetValorDefault(false);
    c=AddItem( getJPssColorPicker2(), CHAR, OPT, "color_topbackground");
    c.SetValorDefault("FF0000");
    c=AddItem( getJCheckBox13(),  OPT, "marca_top");
    c.setRefreshForm(true);
    c.SetValorDefault(false);
    c.setVisible(getWin().GetcDato().getObjCustomList().isMatriz()?true:false);
    c=AddItem( getJTextField(), CHAR, OPT, "mayores_a");
    c=AddItem( getJCheckBox11(),  OPT, "marca_menores");
    c.setRefreshForm(true);
    c.SetValorDefault(false);
    c=AddItem( getJPssColorPicker3(), CHAR, OPT, "color_bottombackground");
    c.SetValorDefault("00AA00");
    c=AddItem( getJCheckBox131(),  OPT, "marca_bottom");
    c.setRefreshForm(true);
    c.SetValorDefault(false);
    c.setVisible(getWin().GetcDato().getObjCustomList().isMatriz()?true:false);
    c=AddItem( getJTextField1(), CHAR, OPT, "menores_a");

  }
public JWins getFunciones(boolean one) throws Exception {
	if (one) {
		return JWins.createVirtualWinsFromMap(BizCampo.getFunctionAllMap());
	}
	return JWins.createVirtualWinsFromMap(BizCampo.getFunctionAllMap());
	
}
  @Override
  	public void OnPostShow() throws Exception {
  		checkControls(null);
  		super.OnPostShow();
  	}
  @Override
  	public void checkControls(String sControlId) throws Exception {
  		if (this.getControles().findControl("colores").getValue().equals("S")) {
  			this.getControles().findControl("color_background").edit(this.GetModo());
  			this.getControles().findControl("color_foreground").edit(this.GetModo());
  		} else {
  			this.getControles().findControl("color_background").disable();
  			this.getControles().findControl("color_foreground").disable();
  		}
  		if (this.getControles().findControl("marca_mayores").getValue().equals("S")) {
  			this.getControles().findControl("color_topbackground").edit(this.GetModo());
  			this.getControles().findControl("marca_top").edit(this.GetModo());
    		if (this.getControles().findControl("marca_top").getValue().equals("S")) 
    			this.getControles().findControl("mayores_a").disable();
    		else
    			this.getControles().findControl("mayores_a").edit(this.GetModo());
  		} else {
  			this.getControles().findControl("color_topbackground").disable();
  			this.getControles().findControl("marca_top").disable();
  			this.getControles().findControl("mayores_a").disable();
  		}
  		if (this.getControles().findControl("marca_menores").getValue().equals("S")) {
  			this.getControles().findControl("color_bottombackground").edit(this.GetModo());
  			this.getControles().findControl("marca_bottom").edit(this.GetModo());
    		if (this.getControles().findControl("marca_bottom").getValue().equals("S")) 
    			this.getControles().findControl("menores_a").disable();
    		else
    			this.getControles().findControl("menores_a").edit(this.GetModo());
  		} else {
  			this.getControles().findControl("color_bottombackground").disable();
  			this.getControles().findControl("marca_bottom").disable();
  			this.getControles().findControl("menores_a").disable();
  		}
  		super.checkControls(sControlId);
  	}
  
//  private JWins getCampos(boolean one) throws Exception {
//  	GuiCamposGallery g = new GuiCamposGallery();
//  	if (one) {
//  		g.setRecords(this.getWin().GetcDato().getCamposGallery());
//  	} else {
//  		if (!this.findControl("rel_id").hasValue()) return null;
//  		this.getWin().GetcDato().setRecordOwner(this.findControl("record_owner").getValue());
//  		this.getWin().GetcDato().setRelId(this.findControl("rel_id").getValue());
//  		g.setRecords(this.getWin().GetcDato().getCamposGallery());
//  	}
//  	return g;
//  }
  
  private JWins getCamposAll(boolean one) throws Exception {
  	GuiCamposGallery g = new GuiCamposGallery();
 		g.setRecords(this.getWin().GetcDato().getObjCustomList().getAllCampos((BizAction)null, true));
  	return g;
  }

//  private JWins getGeoCampos(boolean one) throws Exception {
//  	GuiCamposGallery g = new GuiCamposGallery();
//  	if (one) {
//  		return JWins.createVirtualWinsFromMap(this.getWin().GetcDato().getCamposGallery());
//  	} else {
//  		if (!this.findControl("rel_id").hasValue()) return null;
//  		this.getWin().GetcDato().setRecordOwner(this.findControl("record_owner").getValue());
//  		this.getWin().GetcDato().setRelId(this.findControl("rel_id").getValue());
//  		return JWins.createVirtualWinsFromMap(this.getWin().GetcDato().getCamposGallery());
//  	}
//  }

//  private JWins getRecordSets(boolean one) throws Exception {
//  	return JWins.createVirtualWinsFromMap(this.getWin().GetcDato().getObjCustomList().getRelationGallery(false));
//  }

//  /**
//	 * This method initializes jCampo	
//	 * 	
//	 * @return javax.swing.JComboBox	
//	 */
//	private JComboBox getJCampo() {
//		if (jCampo == null) {
//			jCampo = new JComboBox();
//			jCampo.setBounds(new Rectangle(94, 28, 298, 22));
//		}
//		return jCampo;
//	}

	/**
	 * This method initializes jFunc	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJFunc() {
		if (jFunc == null) {
			jFunc = new JComboBox();
			jFunc.setBounds(new Rectangle(99, 336, 298, 22));
		}
		return jFunc;
	}

//	/**
//	 * This method initializes jRecordSet	
//	 * 	
//	 * @return javax.swing.JComboBox	
//	 */
//	private JComboBox getJRecordSet() {
//		if (jRelId == null) {
//			jRelId = new JComboBox();
//			jRelId.setBounds(new Rectangle(94, 2, 298, 22));
//		}
//		return jRelId;
//	}

	/**
	 * This method initializes jFormat	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJFormat() {
		if (jFormat == null) {
			jFormat = new JComboBox();
			jFormat.setBounds(new Rectangle(99, 310, 170, 22));
		}
		return jFormat;
	}

	/**
	 * This method initializes jFormatParam	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJFormatParam() {
		if (jFormatParam == null) {
			jFormatParam = new JTextField();
			jFormatParam.setBounds(new Rectangle(275, 310, 123, 22));
		}
		return jFormatParam;
	}

	/**
	 * This method initializes jNombrecol	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJNombrecol() {
		if (jNombrecol == null) {
			jNombrecol = new JTextField();
			jNombrecol.setBounds(new Rectangle(99, 284, 298, 22));
		}
		return jNombrecol;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(11, 365, 127, 21));
			jCheckBox.setText("Visible");
		}
		return jCheckBox;
	}

	/**
	 * This method initializes jCheckBox1	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox1() {
		if (jCheckBox1 == null) {
			jCheckBox1 = new JCheckBox();
			jCheckBox1.setBounds(new Rectangle(11, 449, 254, 21));
			jCheckBox1.setText("Marcar valores superiores");
		}
		return jCheckBox1;
	}

	/**
	 * This method initializes jCheckBox11	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox11() {
		if (jCheckBox11 == null) {
			jCheckBox11 = new JCheckBox();
			jCheckBox11.setBounds(new Rectangle(11, 504, 254, 21));
			jCheckBox11.setText("Marcar valores inferiores");
		}
		return jCheckBox11;
	}

	/**
	 * This method initializes JPssColorPicker	
	 * 	
	 * @return javax.swing.JPssColorPicker	
	 */
	private JPssColorPicker getJPssColorPicker() {
		if (JPssColorPicker == null) {
			JPssColorPicker = new JPssColorPicker();
			JPssColorPicker.setBounds(new Rectangle(123, 409, 55, 21));
		}
		return JPssColorPicker;
	}

	/**
	 * This method initializes JPssColorPicker1	
	 * 	
	 * @return javax.swing.JPssColorPicker	
	 */
	private JPssColorPicker getJPssColorPicker1() {
		if (JPssColorPicker1 == null) {
			JPssColorPicker1 = new JPssColorPicker();
			JPssColorPicker1.setBounds(new Rectangle(303, 409, 55, 21));
		}
		return JPssColorPicker1;
	}

	/**
	 * This method initializes JPssColorPicker2	
	 * 	
	 * @return javax.swing.JPssColorPicker	
	 */
	private JPssColorPicker getJPssColorPicker2() {
		if (JPssColorPicker2 == null) {
			JPssColorPicker2 = new JPssColorPicker();
			JPssColorPicker2.setBounds(new Rectangle(42, 475, 55, 21));
		}
		return JPssColorPicker2;
	}

	/**
	 * This method initializes JPssColorPicker3	
	 * 	
	 * @return javax.swing.JPssColorPicker	
	 */
	private JPssColorPicker getJPssColorPicker3() {
		if (JPssColorPicker3 == null) {
			JPssColorPicker3 = new JPssColorPicker();
			JPssColorPicker3.setBounds(new Rectangle(42, 532, 55, 21));
		}
		return JPssColorPicker3;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new Rectangle(325, 532, 56, 20));
		}
		return jTextField1;
	}

	/**
	 * This method initializes jCheckBox12	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox12() {
		if (jCheckBox12 == null) {
			jCheckBox12 = new JCheckBox();
			jCheckBox12.setBounds(new Rectangle(11, 389, 254, 22));
			jCheckBox12.setText("Configuración especial colores");
		}
		return jCheckBox12;
	}

	/**
	 * This method initializes jCheckBox13	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox13() {
		if (jCheckBox13 == null) {
			jCheckBox13 = new JCheckBox();
			jCheckBox13.setBounds(new Rectangle(143, 475, 73, 21));
			jCheckBox13.setText("Top 10");
		}
		return jCheckBox13;
	}

	/**
	 * This method initializes jCheckBox131	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox131() {
		if (jCheckBox131 == null) {
			jCheckBox131 = new JCheckBox();
			jCheckBox131.setBounds(new Rectangle(143, 532, 73, 21));
			jCheckBox131.setText("Top 10");
		}
		return jCheckBox131;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(324, 475, 56, 20));
		}
		return jTextField;
	}
	/**
	 * This method initializes jCheckBox2	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox2() {
		if (jCheckBox2 == null) {
			jCheckBox2 = new JCheckBox();
			jCheckBox2.setBounds(new Rectangle(152, 366, 238, 21));
			jCheckBox2.setText("Expresar en porcentaje del total");
		}
		return jCheckBox2;
	}
}  //  @jve:decl-index=0:visual-constraint="-1,16"
