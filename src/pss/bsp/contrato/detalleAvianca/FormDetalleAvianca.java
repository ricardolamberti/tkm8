package pss.bsp.contrato.detalleAvianca;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import pss.bsp.event.GuiBSPSqlEvents;
import pss.common.event.sql.GuiSqlEvents;
import pss.common.security.BizUsuario;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssImage;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormImage;
import pss.core.winUI.controls.JFormLista;
import pss.core.winUI.forms.JBaseForm;

public class FormDetalleAvianca extends JBaseForm {


private static final long serialVersionUID = 1446860278358L;

  /**
   * Propiedades de la Clase
   */
JPssLabel llinea = new JPssLabel();
JPssEdit linea = new JPssEdit  ();
JPssLabel lcompany = new JPssLabel();
JPssEdit company = new JPssEdit  ();
JPssLabel lid = new JPssLabel();
JPssEdit id = new JPssEdit  ();
JPssEdit variable = new JPssEdit  ();
JPssEdit variable1 = new JPssEdit  ();

private JPssImage jTextArea = null;

private JPssImage jTextArea1 = null;

private JCheckBox jCheckBox = null;

private JComboBox jComboBox = null;

private JPssLabel lvalor2 = null;

private JPanel jPanel = null;

private JTabbedPane jTabbedPane = null;

private JPanel jPanel1 = null;

private JPssLabel lvalor1 = null;

private JPssEdit evalua = null;

private JPssLabel lvalor11 = null;

private JPssLabel lvalor12 = null;
JPssEdit textFieldb = new JPssEdit();
JPssEdit textFieldb_1 = new JPssEdit();
JPssEdit textFieldb_2 = new JPssEdit();

private JPssEdit evalua2 = null;

private JPssLabel lvalor111 = null;

private JPssLabel lvalor112 = null;

private JPssEdit nivel2 = null;

private JPssLabel lvalor1111 = null;

private JPssLabel lvalor11111 = null;

private JPssLabel lvariable1 = null;

private JPssEdit valorObjetivoFC = null;

private JPssEdit valorActualFC = null;

private JPssLabel lvalor1112 = null;

private JPssLabel lvalor1113 = null;

private JCheckBox jCheckBox1 = null;

private JTextField jDescr = null;

private JPssLabel lvariable11 = null;
private JTextField textField;
private JTextField textField_1;
private JTextField textField_2;
private JTextField textField_3;

/**
   * Constructor de la Clase
   */
  public FormDetalleAvianca() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiDetalleAvianca getWin() { return (GuiDetalleAvianca) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    lvariable11 = new JPssLabel();
    lvariable11.setBounds(new Rectangle(15, 11, 72, 22));
    lvariable11.setText("Descripcion");
    lvalor2 = new JPssLabel();
    lvalor2.setText("Periodicidad");
    lvalor2.setBounds(new Rectangle(122, 14, 94, 22));
    setLayout(null);
    setSize(new Dimension(996, 665));


    this.add(getJTextArea(), null);
    this.add(getJTextArea1(), null);
//    getJPanel().add(getJCheckBox(), null);
//    getJPanel().add(getJComboBox(), null);
//    getJPanel().add(lvalor2, null);
//    this.add(getJPanel(), null);
    this.add(getJTabbedPane(), null);
    this.add(getJPanel1(), null);
    this.add(getJCheckBox1(), null);
    this.add(getJDescr(), null);
    this.add(lvariable11, null);
 
//    getJPanel().setBorder(new TitledBorder(BorderFactory.createEtchedBorder(),"Acumulaci�n"));
    getJPanel1().setBorder(new TitledBorder(BorderFactory.createEtchedBorder(),"Resultados"));
    
    JPssLabel pssLabel_2 = new JPssLabel();
    pssLabel_2.setText("M�n.Cantidad");
    pssLabel_2.setBounds(new Rectangle(10, 11, 110, 22));
    pssLabel_2.setBounds(15, 56, 72, 22);
    add(pssLabel_2);
    
//    textFieldb.setBounds(new Rectangle(76, 9, 242, 23));
//    textFieldb.setBounds(86, 43, 108, 23);
//    add(textFieldb);
//    
//    textFieldb_1.setBounds(new Rectangle(76, 9, 242, 23));
//    textFieldb_1.setBounds(269, 43, 108, 23);
//    add(textFieldb_1);
    
    textFieldb_2.setBounds(new Rectangle(76, 9, 242, 23));
    textFieldb_2.setBounds(86, 57, 108, 23);
    add(textFieldb_2);
    
    JPssLabel pssLabel = new JPssLabel();
    pssLabel.setText("período");
    pssLabel.setBounds(new Rectangle(15, 11, 72, 22));
    pssLabel.setBounds(15, 32, 72, 22);
    add(pssLabel);
    
    textField_3 = new JTextField();
    textField_3.setBounds(new Rectangle(87, 9, 231, 23));
    textField_3.setBounds(87, 32, 231, 23);
    add(textField_3);

 
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
		setAutoRefresh(true, 10000, "DASHBOARD");
		AddItem( company, CHAR, REQ, "company" ).SetValorDefault(BizUsuario.getUsr().getCompany());
    AddItem( id, UINT, OPT, "id" );
    AddItem( linea, UINT, OPT, "linea" );
    AddItem( variable, CHAR, OPT, "variable" );
    AddItem( variable1, CHAR, OPT, "variable_ganancia" );
    AddItem( textFieldb, CHAR, OPT, "fms_max" );
    AddItem( textFieldb_1, CHAR, OPT, "fms_min" );
    AddItem( textFieldb_2, CHAR, OPT, "Limite" );
    AddItem( textField_3, CHAR, OPT, "periodo_detalle" );
       
    JFormControl c=AddItem( getJCheckBox(), OPT,"acumulativo");
    c.SetValorDefault(false);
    AddItem( getJComboBox(), CHAR, OPT, "periodo" , JWins.createVirtualWinsFromMap(BizDetalleAvianca.getPeriodos()));
//    AddItem( getValorActual(), FLOAT, OPT, "valor_actual" ).SetReadOnly(true);
    AddItem( getValorActualFC(), FLOAT, OPT, "valor_fcontrato" ).SetReadOnly(true);
//    AddItem( getValorObjetivo(), FLOAT, OPT, "valor_total" ).setVisible(true);
    AddItem( getValorObjetivoFC(), FLOAT, OPT, "valor_totalcontrato" ).setVisible(true);
    AddItem( getEvalua(), CHAR, OPT, "conclusion" ).SetReadOnly(true);
    AddItem( getJDescr(), CHAR, OPT, "descripcion" ).SetReadOnly(true);
//    AddItem( getNivel1(), CHAR, OPT, "nivel_alcanzado" ).SetReadOnly(true);
//    AddItem( getEvalua1(), FLOAT, OPT, "ganancia" ).SetReadOnly(true);
    AddItem( getNivel2(), CHAR, OPT, "nivel_alcanzado_estimada" ).SetReadOnly(true);
    AddItem( getEvalua2(), FLOAT, OPT, "ganancia_estimada" ).SetReadOnly(true);
    JFormImage i=AddItem( getJTextArea(), "imagen1" );
    i.setSource(JPssImage.SOURCE_SCRIPT);


    i=AddItem( getJTextArea1(), "imagen2" );
    i.setSource(JPssImage.SOURCE_SCRIPT);

    AddItem(getJCheckBox1(),OPT,"kicker");
    JFormLista l=AddItem(getJTabbedPane(),32);
    l=AddItem(getJTabbedPane(),35);
    l=AddItem(getJTabbedPane(),33);
   }
  @Override
  	public void OnPostShow() throws Exception {
  		checkControls("");
  		super.OnPostShow();
  	}

  private JWins getIndicador(boolean one) throws Exception {
  	GuiSqlEvents g = new GuiBSPSqlEvents();
  	if (one) {
  		g.getRecords().addFilter("id", getWin().GetcDato().getVariable());
  	} else {
  		g.getRecords().addFilter("company", getControles().findControl("company").getValue());
  	}
  	return g;
  }
  private JWins getIndicadorGanancia(boolean one) throws Exception {
  	GuiSqlEvents g = new GuiBSPSqlEvents();
  	if (one) {
  		g.getRecords().addFilter("id", getWin().GetcDato().getVariableGanancia());
  	} else {
  		g.getRecords().addFilter("company", getControles().findControl("company").getValue());
  	}
  	return g;
  }

  @Override
  	public void checkControls(String sControlId) throws Exception {
  		if (sControlId.equals("variable")) {
  			getWin().GetcDato().clean();
  		}
//  		if (GetControles().findControl("acumulativo").getValue().equals("S")) {
//  			GetControles().findControl("periodo").edit(GetModo());
//  			//GetControles().findControl("valor").edit(GetModo());
//  		} else {
//  			GetControles().findControl("periodo").disable();
//  			//GetControles().findControl("valor").disable();
//  			GetControles().findControl("periodo").setValue("");
//  			//GetControles().findControl("valor").setValue(""+getWin().GetcDato().getValor());
//  		}
//  		if (sControlId.equals("")) {
//  			getWin().GetcDato().calcule();
//  			this.GetControles().findControl("valor").setValue(""+getWin().GetcDato().getObjEvent().getValor());
//  		}
  		super.checkControls(sControlId);
  	}
	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JPssImage getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JPssImage();
			jTextArea.setBounds(new Rectangle(15, 358, 349, 296));
		}
		return jTextArea;
	}

	/**
	 * This method initializes jTextArea1	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JPssImage getJTextArea1() {
		if (jTextArea1 == null) {
			jTextArea1 = new JPssImage();
			jTextArea1.setBounds(new Rectangle(15, 101, 349, 255));
		}
		return jTextArea1;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
//			jCheckBox.setText("Acumulativa");
//			jCheckBox.setBounds(new Rectangle(11, 14, 108, 22));
		}
		return jCheckBox;
	}

	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
//			jComboBox.setBounds(new Rectangle(218, 14, 161, 22));
		}
		return jComboBox;
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBounds(new Rectangle(372, 101, 614, 553));
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			lvalor1113 = new JPssLabel();
			lvalor1113.setBounds(new Rectangle(7, 60, 146, 20));
			lvalor1113.setText("Valor Base comisionable");
			lvalor11111 = new JPssLabel();
			lvalor11111.setBounds(new Rectangle(237, 37, 35, 20));
			lvalor11111.setText("Nivel");
			lvalor112 = new JPssLabel();
			lvalor112.setBounds(new Rectangle(7, 37, 146, 20));
			lvalor112.setText("Puntos alcanzados");
			lvalor12 = new JPssLabel();
			lvalor12.setText("Comisi\u00F3n");
			lvalor12.setBounds(new Rectangle(237, 60, 63, 20));
			lvalor1 = new JPssLabel();
			lvalor1.setText("Evaluación");
			lvalor1.setBounds(new Rectangle(7, 13, 72, 20));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(438, 9, 552, 87));
			jPanel1.add(lvalor12, null);
			jPanel1.add(getEvalua2(), null);
			jPanel1.add(lvalor112, null);
			jPanel1.add(getNivel2(), null);
			jPanel1.add(lvalor11111, null);
			jPanel1.add(getValorObjetivoFC(), null);
			jPanel1.add(getValorActualFC(), null);
			jPanel1.add(lvalor1113, null);
			jPanel1.add(getEvalua(), null);
			jPanel1.add(lvalor1, null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes evalua	
	 * 	
	 * @return pss.core.ui.components.JPssEdit	
	 */
	private JPssEdit getEvalua() {
		if (evalua == null) {
			evalua = new JPssEdit();
			evalua.setBounds(new Rectangle(80, 13, 464, 20));
		}
		return evalua;
	}

	/**
	 * This method initializes evalua2	
	 * 	
	 * @return pss.core.ui.components.JPssEdit	
	 */
	private JPssEdit getEvalua2() {
		if (evalua2 == null) {
			evalua2 = new JPssEdit();
			evalua2.setBounds(new Rectangle(316, 60, 127, 20));
		}
		return evalua2;
	}

	/**
	 * This method initializes nivel2	
	 * 	
	 * @return pss.core.ui.components.JPssEdit	
	 */
	private JPssEdit getNivel2() {
		if (nivel2 == null) {
			nivel2 = new JPssEdit();
			nivel2.setBounds(new Rectangle(316, 38, 127, 20));
		}
		return nivel2;
	}

	/**
	 * This method initializes valorObjetivoFC	
	 * 	
	 * @return pss.core.ui.components.JPssEdit	
	 */
	private JPssEdit getValorObjetivoFC() {
		if (valorObjetivoFC == null) {
			valorObjetivoFC = new JPssEdit();
			valorObjetivoFC.setBounds(new Rectangle(129, 60, 103, 20));
		}
		return valorObjetivoFC;
	}

	/**
	 * This method initializes valorActualFC	
	 * 	
	 * @return pss.core.ui.components.JPssEdit	
	 */
	private JPssEdit getValorActualFC() {
		if (valorActualFC == null) {
			valorActualFC = new JPssEdit();
			valorActualFC.setBounds(new Rectangle(129, 37, 103, 20));
		}
		return valorActualFC;
	}

	/**
	 * This method initializes jCheckBox1	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox1() {
		if (jCheckBox1 == null) {
			jCheckBox1 = new JCheckBox();
			jCheckBox1.setBounds(new Rectangle(324, 32, 108, 22));
			jCheckBox1.setText("Objetivo extra");
		}
		return jCheckBox1;
	}

	/**
	 * This method initializes jDescr	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextField getJDescr() {
		if (jDescr == null) {
			jDescr = new JTextField();
			jDescr.setBounds(new Rectangle(87, 9, 341, 23));
		}
		return jDescr;
	} 
}  //  @jve:decl-index=0:visual-constraint="7,4"
