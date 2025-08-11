package pss.bsp.contrato.detalle;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import pss.bsp.contrato.detalle.nivel.GuiNiveles;
import pss.bsp.event.GuiBSPSqlEvents;
import pss.common.event.sql.GuiSqlEvents;
import pss.common.security.BizUsuario;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormTable;
import pss.core.winUI.forms.JBaseForm;

public class FormEditDetalle extends JBaseForm {


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
  JPssEdit pssSharegapglobal = new JPssEdit();
  JPssEdit FMSGlobal = new JPssEdit();
  JPssEdit pssValorRegGlobal = new JPssEdit();
  JPssEdit pssValorReembolso = new JPssEdit();

	private JPssEdit evalua = null;  //  @jve:decl-index=0:visual-constraint="292,13"

	private JCheckBox jCheckBox = null;

	private JComboBox jComboBox = null;

	private JPssLabel lvalor2 = null;

	private JPanel jPanel = null;

	private JPssLabel lvalor3 = null;

	private JPssEdit valorActual = null;

	private JPssEdit valorObjetivo = null;  //  @jve:decl-index=0:visual-constraint="-97,34"

	private JTable jTable = null;

	private JPssLabel lvariable1 = null;

	private JComboBox variableGanancia = null;

	private JPssLabel lvalor31 = null;

	private JPssEdit valorActual1 = null;

	private JCheckBox jCheckBox1 = null;

	private JPssLabel lvariable2 = null;

	private JTextField jDescr = null;
	private JPssLabel pslblReembolso;

	/**
	   * Constructor de la Clase
	   */
	  public FormEditDetalle() throws Exception {
	    try { jbInit(); }
	    catch (Exception e) { e.printStackTrace(); } 
	  }

	  public GuiDetalle getWin() { return (GuiDetalle) getBaseWin(); }

	  /**
	   * Inicializacion Grafica
	   */
	  protected void jbInit() throws Exception {
  }
	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit("company", CHAR, REQ, "company").SetValorDefault(BizUsuario.getUsr().getCompany());
    AddItemEdit("id", UINT, OPT, "id");
    AddItemEdit("linea", UINT, OPT, "linea");
    JFormControl c = AddItemCombo("variable", CHAR, REQ, "variable", new JControlCombo() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getIndicador(one);
	    	}
	    });
	    c.setRefreshForm(true);
	    c.setPlaceHolder("Indicador para determinar si se alcanza objetivo");
    c=AddItemCombo("variable_ganancia", CHAR, OPT, "variable_ganancia", new JControlCombo() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getIndicadorGanancia(one);
	    	}
	    });
	    c.setRefreshForm(true);
	    c.setPlaceHolder("Indicador para determinar el premio");
    AddItemCheck("kicker", OPT, "kicker");
    c=AddItemCheck("acumulativo", OPT, "acumulativo");
	    c.SetValorDefault(false);
//	    c.setRefreshForm(true);
//	    AddItem( getJComboBox(), CHAR, OPT, "periodo" , JWins.createVirtualWinsFromMap(BizDetalle.getPeriodos()));
    JFormTable t=AddItem( getJTable(), "niveles", GuiNiveles.class);
    t.setKeepHeight(true);
    t.setKeepWidth(true);
    AddItemEdit("valor_fcontrato", UFLOAT, OPT, "valor_fcontrato").SetReadOnly(true);
    AddItemEdit("valor_totalcontrato", UFLOAT, OPT, "valor_totalcontrato").SetReadOnly(true);
//	    AddItem( getValorObjetivo(), UFLOAT, OPT, "valor_objetivo" ).setVisible(false);
    AddItemEdit("conclusion", CHAR, OPT, "conclusion").SetReadOnly(true);
    AddItemEdit("descripcion", CHAR, OPT, "descripcion");
    AddItemEdit("fms_global", FLOAT, OPT, "fms_global");
    AddItemEdit("sharegap_global", FLOAT, OPT, "sharegap_global");
    AddItemEdit("valor_global", FLOAT, OPT, "valor_global");
    AddItemEdit("valor_reembolso", FLOAT, OPT, "valor_reembolso");
		   
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
	  		if (sControlId.equals("variable")||sControlId.equals("variable_ganancia")) {
	  			getWin().GetcDato().clean();
	  			if (getWin().GetcDato().getObjEvent()!=null)
	  				getControles().findControl("descripcion").setValue(getWin().GetcDato().getObjEvent().getDescripcion());
	  		}
//	  		if (GetControles().findControl("acumulativo").getValue().equals("S")) {
//	  			GetControles().findControl("periodo").edit(GetModo());
//	  			//GetControles().findControl("valor").edit(GetModo());
//	  		} else {
//	  			GetControles().findControl("periodo").disable();
//	  			//GetControles().findControl("valor").disable();
//	  			GetControles().findControl("periodo").setValue("");
//	  			//GetControles().findControl("valor").setValue(""+getWin().GetcDato().getValor());
//	  		}
	  		if (sControlId.equals("")) {
	  			if (getWin().GetcDato().getObjEvent()!=null)
	  				this.getControles().findControl("valor_fcontrato").setValue(""+getWin().GetcDato().getObjEvent().getValor());
	  			else
	  				this.getControles().findControl("valor_fcontrato").setValue("");
	  			if (getWin().GetcDato().getObjEventGanancia()!=null)
	  				this.getControles().findControl("valor_totalcontrato").setValue(""+getWin().GetcDato().getObjEventGanancia().getValor());
	  			else
	  				this.getControles().findControl("valor_totalcontrato").setValue("");
	  		}
	  		super.checkControls(sControlId);
	  	}
		/**
		 * This method initializes evalua	
		 * 	
		 * @return pss.core.ui.components.JPssEdit	
		 */
		private JPssEdit getEvalua() {
			if (evalua == null) {
				evalua = new JPssEdit();
			}
			return evalua;
		}

		/**
		 * This method initializes jCheckBox	
		 * 	
		 * @return javax.swing.JCheckBox	
		 */
		private JCheckBox getJCheckBox() {
			if (jCheckBox == null) {
				jCheckBox = new JCheckBox();
//				jCheckBox.setText("Acumulativa");
//				jCheckBox.setBounds(new Rectangle(11, 14, 108, 22));
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
				jComboBox.setBounds(new Rectangle(218, 13, 161, 22));
			}
			return jComboBox;
		}

		/**
		 * This method initializes jPanel	
		 * 	
		 * @return javax.swing.JPanel	
		 */
//		private JPanel getJPanel() {
//			if (jPanel == null) {
//				jPanel = new JPanel();
//				jPanel.setLayout(null);
//				jPanel.setBounds(new Rectangle(564, 5, 428, 42));
//				jPanel.add(getJCheckBox(), null);
//				jPanel.add(lvalor2, null);
//				jPanel.add(getJComboBox(), null);
//			}
//			return jPanel;
//		}

		/**
		 * This method initializes valorActual	
		 * 	
		 * @return pss.core.ui.components.JPssEdit	
		 */
		private JPssEdit getValorActual() {
			if (valorActual == null) {
				valorActual = new JPssEdit();
				valorActual.setBounds(new Rectangle(597, 6, 100, 22));
			}
			return valorActual;
		}

		/**
		 * This method initializes valorObjetivo	
		 * 	
		 * @return pss.core.ui.components.JPssEdit	
		 */
		private JPssEdit getValorObjetivo() {
			if (valorObjetivo == null) {
				valorObjetivo = new JPssEdit();
			}
			return valorObjetivo;
		}

		/**
		 * This method initializes jTable	
		 * 	
		 * @return javax.swing.JTable	
		 */
		private JTable getJTable() {
			if (jTable == null) {
				jTable = new JTable();
				jTable.setBounds(new Rectangle(13, 121, 977, 195));
			}
			return jTable;
		}

		/**
		 * This method initializes variableGanancia	
		 * 	
		 * @return javax.swing.JComboBox	
		 */
		private JComboBox getVariableGanancia() {
			if (variableGanancia == null) {
				variableGanancia = new JComboBox();
				variableGanancia.setBounds(new Rectangle(125, 32, 424, 23));
			}
			return variableGanancia;
		}

		/**
		 * This method initializes valorActual1	
		 * 	
		 * @return pss.core.ui.components.JPssEdit	
		 */
		private JPssEdit getValorActual1() {
			if (valorActual1 == null) {
				valorActual1 = new JPssEdit();
				valorActual1.setBounds(new Rectangle(597, 32, 100, 23));
			}
			return valorActual1;
		}

		/**
		 * This method initializes jCheckBox1	
		 * 	
		 * @return javax.swing.JCheckBox	
		 */
		private JCheckBox getJCheckBox1() {
			if (jCheckBox1 == null) {
				jCheckBox1 = new JCheckBox();
				jCheckBox1.setBounds(new Rectangle(597, 57, 106, 22));
				jCheckBox1.setText("Objetivo extra");
			}
			return jCheckBox1;
		}

		/**
		 * This method initializes jDescr	
		 * 	
		 * @return javax.swing.JTextField	
		 */
		private JTextField getJDescr() {
			if (jDescr == null) {
				jDescr = new JTextField();
				jDescr.setBounds(new Rectangle(125, 57, 469, 23));
			}
			return jDescr;
		} 
	private JPssLabel getPslblReembolso() {
		if (pslblReembolso == null) {
			pslblReembolso = new JPssLabel();
			pslblReembolso.setText(" Reembolso");
			pslblReembolso.setBounds(new Rectangle(14, 57, 112, 23));
			pslblReembolso.setBounds(725, 83, 94, 23);
		}
		return pslblReembolso;
	}
	}  //  @jve:decl-index=0:visual-constraint="24,55"
