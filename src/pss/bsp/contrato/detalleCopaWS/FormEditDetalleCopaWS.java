package pss.bsp.contrato.detalleCopaWS;

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
import pss.tourism.carrier.GuiCarriers;

public class FormEditDetalleCopaWS extends JBaseForm {


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
  JCheckBox chckbxNewCheckBox = new JCheckBox();
	
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

	private JPssLabel lvalor31 = null;

	private JPssEdit valorActual1 = null;

	private JCheckBox jCheckBox1 = null;

	private JPssLabel lvariable2 = null;

	private JTextField jDescr = null;
	private JPssLabel pssLabel_1;
	private JPssEdit pssEdit;
	private JPssLabel pssLabel_2;
	private JPssEdit pssEdit_1;
	private JPssLabel pssLabel_3;
	private JPssEdit pssEdit_2;

	/**
	   * Constructor de la Clase
	   */
	  public FormEditDetalleCopaWS() throws Exception {
	    try { jbInit(); }
	    catch (Exception e) { e.printStackTrace(); } 
	  }

	  public GuiDetalleCopaWS getWin() { return (GuiDetalleCopaWS) getBaseWin(); }

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
	    JFormControl c = AddItemCombo("id_aerolinea", CHAR, REQ, "id_aerolinea", new JControlCombo() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getAerolineas(one);
	    	}
	    });
	    c.setPlaceHolder("Aerolinea del contrato");
	    AddItemCheck("kicker", OPT, "kicker");
	    JFormTable t=AddItem( getJTable(), "niveles", GuiNiveles.class);
	    t.setKeepHeight(true);
	    t.setKeepWidth(true);
	    AddItemEdit("conclusion", CHAR, OPT, "conclusion").SetReadOnly(true);
	    AddItemEdit("descripcion", CHAR, OPT, "descripcion");
	    AddItemEdit("tourcode_excluded", CHAR, OPT, "tourcode_excluded");
	    AddItemEdit("class_excluded", CHAR, OPT, "class_excluded");
	    AddItemCheck("Tickets", CHAR, OPT, "pax", "S", "N");
	  }
	  @Override
	  	public void OnPostShow() throws Exception {
	  		checkControls("");
	  		super.OnPostShow();
	  	}

	  private JWins getAerolineas(boolean one) throws Exception {
	  	GuiCarriers g = new GuiCarriers();
	  	if (one) {
	  		g.getRecords().addFilter("carrier", getWin().GetcDato().getIdAerolinea());
	  	} 
	  	g.addOrderAdHoc("description", "ASC");

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
				valorActual.setBounds(new Rectangle(90, 33, 89, 22));
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
				valorObjetivo.setBounds(new Rectangle(444, 33, 100, 22));
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
				jTable.setBounds(new Rectangle(13, 156, 531, 160));
			}
			return jTable;
		}

		/**
		 * This method initializes valorActual1	
		 * 	
		 * @return pss.core.ui.components.JPssEdit	
		 */
		private JPssEdit getValorActual1() {
			if (valorActual1 == null) {
				valorActual1 = new JPssEdit();
				valorActual1.setBounds(new Rectangle(244, 33, 100, 23));
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
				jCheckBox1.setBounds(new Rectangle(319, 6, 115, 22));
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
				jDescr.setBounds(new Rectangle(90, 57, 454, 23));
			}
			return jDescr;
		} 
	}  //  @jve:decl-index=0:visual-constraint="24,55"
