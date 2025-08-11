package pss.bsp.contrato.detalle;

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
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormImage;
import pss.core.winUI.controls.JFormLista;
import pss.core.winUI.forms.JBaseForm;

public class FormDetalle extends JBaseForm {


private static final long serialVersionUID = 1446860278358L;

  /**
   * Propiedades de la Clase
   */
JPssLabel llinea = new JPssLabel();
JPssEdit linea = new JPssEdit  ();
JPssLabel lvariable = new JPssLabel();
JComboBox variable = new JComboBox  ();
JPssLabel lcompany = new JPssLabel();
JPssEdit company = new JPssEdit  ();
JPssLabel lid = new JPssLabel();
JPssEdit id = new JPssEdit  ();

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

private JPssEdit evalua2 = null;

private JPssLabel lvalor111 = null;

private JPssLabel lvalor112 = null;

private JPssEdit nivel2 = null;

private JPssLabel lvalor1111 = null;

private JPssLabel lvalor11111 = null;

private JComboBox variable1 = null;

private JPssLabel lvariable1 = null;

private JPssEdit valorObjetivoFC = null;

private JPssEdit valorActualFC = null;

private JPssLabel lvalor1112 = null;

private JPssLabel lvalor1113 = null;

private JCheckBox jCheckBox1 = null;

private JTextField jDescr = null;

private JPssLabel lvariable11 = null;

/**
   * Constructor de la Clase
   */
  public FormDetalle() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiDetalle getWin() { return (GuiDetalle) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    lvariable11 = new JPssLabel();
    lvariable11.setBounds(new Rectangle(4, 63, 110, 22));
    lvariable11.setText("Descripcion");
    lvariable1 = new JPssLabel();
    lvariable1.setBounds(new Rectangle(4, 35, 110, 25));
    lvariable1.setText("Indicador ganancia");
    lvalor2 = new JPssLabel();
    lvalor2.setText("Periodicidad");
    lvalor2.setBounds(new Rectangle(122, 14, 94, 22));
    setLayout(null);
    setSize(new Dimension(996, 665));



   // getJPanel().add(valor , null);


    lvariable.setText( "Indicador" );
    lvariable.setBounds(new Rectangle(4, 8, 110, 22)); 
    variable.setBounds(new Rectangle(117, 8, 318, 22)); 
    add(lvariable, null);
    add(variable , null);


    this.add(getJTextArea(), null);
    this.add(getJTextArea1(), null);
//    getJPanel().add(getJCheckBox(), null);
//    getJPanel().add(getJComboBox(), null);
//    getJPanel().add(lvalor2, null);
//    this.add(getJPanel(), null);
    this.add(getJTabbedPane(), null);
    this.add(getJPanel1(), null);
    this.add(getVariable1(), null);
    this.add(lvariable1, null);
    this.add(getJCheckBox1(), null);
    this.add(getJDescr(), null);
    this.add(lvariable11, null);
 
//    getJPanel().setBorder(new TitledBorder(BorderFactory.createEtchedBorder(),"Acumulaci�n"));
    getJPanel1().setBorder(new TitledBorder(BorderFactory.createEtchedBorder(),"Resultados"));

 
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
		setAutoRefresh(true, 10000, "DASHBOARD");
		AddItem( company, CHAR, REQ, "company" ).SetValorDefault(BizUsuario.getUsr().getCompany());
    AddItem( id, UINT, OPT, "id" );
    AddItem( linea, UINT, OPT, "linea" );
    AddItem( variable, CHAR, REQ, "variable" , new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getIndicador(one);
    	}
    }).setRefreshForm(true);
    AddItem( variable1, CHAR, REQ, "variable_ganancia" , new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getIndicadorGanancia(one);
    	}
    }).setRefreshForm(true);
    
    JFormControl c=AddItem( getJCheckBox(), OPT,"acumulativo");
    c.SetValorDefault(false);
//    c.setRefreshForm(true);
    AddItem( getJComboBox(), CHAR, OPT, "periodo" , JWins.createVirtualWinsFromMap(BizDetalle.getPeriodos()));
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
	  i.setKeepHeight(true);
	  i.setKeepWidth(true);

    AddItem(getJCheckBox1(),OPT,"kicker");
    JFormLista l=AddItem(getJTabbedPane(),35);
//    l.setKeepHeight(false);
    l.setKeepWidth(false);
//    l.setToolBar(false);
    l=AddItem(getJTabbedPane(),30);
//    l.setKeepHeight(false);
    l.setKeepWidth(false);
    l.setToolBar(JBaseWin.TOOLBAR_NONE);
//    l=AddItem(getJTabbedPane(),50);
//    l.setKeepHeight(false);
//    l.setKeepWidth(false);
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
  		if (sControlId.equals("fecha_calculo"))
  			getWin().GetcDato().calcule(false);
  		if (sControlId.equals("fecha_desde_calculo"))
  			getWin().GetcDato().calcule(false);
   	
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
			jTextArea.setBounds(new Rectangle(367, 100, 623, 557));
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
			jTabbedPane.setBounds(new Rectangle(15, 355, 356, 303));
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
			lvalor1113.setBounds(new Rectangle(182, 60, 61, 20));
			lvalor1113.setText("Ind.Gan");
			lvalor11111 = new JPssLabel();
			lvalor11111.setBounds(new Rectangle(318, 60, 35, 20));
			lvalor11111.setText("Nivel");
			lvalor112 = new JPssLabel();
			lvalor112.setBounds(new Rectangle(8, 60, 113, 20));
			lvalor112.setText("Al fin del contrato:");
			lvalor12 = new JPssLabel();
			lvalor12.setText("Ganancia");
			lvalor12.setBounds(new Rectangle(405, 60, 63, 20));
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
			evalua2.setBounds(new Rectangle(472, 60, 74, 20));
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
			nivel2.setBounds(new Rectangle(352, 60, 50, 20));
		}
		return nivel2;
	}

	/**
	 * This method initializes variable1	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getVariable1() {
		if (variable1 == null) {
			variable1 = new JComboBox();
			variable1.setBounds(new Rectangle(117, 35, 318, 25));
		}
		return variable1;
	}

	/**
	 * This method initializes valorObjetivoFC	
	 * 	
	 * @return pss.core.ui.components.JPssEdit	
	 */
	private JPssEdit getValorObjetivoFC() {
		if (valorObjetivoFC == null) {
			valorObjetivoFC = new JPssEdit();
			valorObjetivoFC.setBounds(new Rectangle(245, 60, 69, 20));
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
			valorActualFC.setBounds(new Rectangle(112, 60, 69, 20));
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
			jCheckBox1.setBounds(new Rectangle(327, 63, 108, 22));
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
			jDescr.setBounds(new Rectangle(116, 63, 211, 23));
		}
		return jDescr;
	} 
}  //  @jve:decl-index=0:visual-constraint="7,4"
