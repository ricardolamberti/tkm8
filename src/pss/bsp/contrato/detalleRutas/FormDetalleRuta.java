package pss.bsp.contrato.detalleRutas;

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

public class FormDetalleRuta extends JBaseForm {


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
  public FormDetalleRuta() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiDetalleRuta getWin() { return (GuiDetalleRuta) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
		setAutoRefresh(true, 10000, "DASHBOARD");
		AddItemEdit("company", CHAR, REQ, "company").SetValorDefault(BizUsuario.getUsr().getCompany());
    AddItemEdit("id", UINT, OPT, "id");
    AddItemEdit("linea", UINT, OPT, "linea");
    AddItemEdit("variable", CHAR, OPT, "variable");
    AddItemEdit("variable_ganancia", CHAR, OPT, "variable_ganancia");
    AddItemEdit("fms_max", CHAR, OPT, "fms_max");
    AddItemEdit("fms_min", CHAR, OPT, "fms_min");
    AddItemEdit("Limite", CHAR, OPT, "Limite");
    AddItemEdit("periodo_detalle", CHAR, OPT, "periodo_detalle").SetReadOnly(true);
       
    JFormControl c=AddItemCheck("acumulativo", OPT, "acumulativo");
    c.SetValorDefault(false);
    AddItemCombo("periodo", CHAR, OPT, "periodo", JWins.createVirtualWinsFromMap(BizDetalleRuta.getPeriodos()));
//    AddItem( getValorActual(), FLOAT, OPT, "valor_actual" ).SetReadOnly(true);
    AddItemEdit("valor_fcontrato", FLOAT, OPT, "valor_fcontrato").SetReadOnly(true);
//    AddItem( getValorObjetivo(), FLOAT, OPT, "valor_total" ).setVisible(true);
    AddItemEdit("valor_totalcontrato", FLOAT, OPT, "valor_totalcontrato").setVisible(true);
    AddItemEdit("conclusion", CHAR, OPT, "conclusion").SetReadOnly(true);
    AddItemEdit("descripcion", CHAR, OPT, "descripcion").SetReadOnly(true);
//    AddItem( getNivel1(), CHAR, OPT, "nivel_alcanzado" ).SetReadOnly(true);
//    AddItem( getEvalua1(), FLOAT, OPT, "ganancia" ).SetReadOnly(true);
    AddItemEdit("nivel_alcanzado_estimada", CHAR, OPT, "nivel_alcanzado_estimada").SetReadOnly(true);
    AddItemEdit("ganancia_estimada", FLOAT, OPT, "ganancia_estimada").SetReadOnly(true);
    JFormImage i=AddItem( getJTextArea(), "imagen1" );
    i.setSource(JPssImage.SOURCE_SCRIPT);


    i=AddItem( getJTextArea1(), "imagen2" );
    i.setSource(JPssImage.SOURCE_SCRIPT);

    AddItemCheck("kicker", OPT, "kicker");
    JFormLista l=AddItem(getJTabbedPane(),34);
    l=AddItem(getJTabbedPane(),35);
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
			evalua2.setBounds(new Rectangle(333, 56, 74, 20));
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
			nivel2.setBounds(new Rectangle(333, 34, 74, 20));
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
			valorObjetivoFC.setBounds(new Rectangle(177, 56, 73, 20));
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
			valorActualFC.setBounds(new Rectangle(177, 34, 73, 20));
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
			jCheckBox1.setBounds(new Rectangle(322, 35, 108, 22));
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
			jDescr.setBounds(new Rectangle(87, 9, 325, 23));
		}
		return jDescr;
	} 
}  //  @jve:decl-index=0:visual-constraint="7,4"
