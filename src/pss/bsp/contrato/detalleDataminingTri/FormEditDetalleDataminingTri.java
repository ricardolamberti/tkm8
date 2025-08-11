package pss.bsp.contrato.detalleDataminingTri;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;

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
import javax.swing.JTextField;

public class FormEditDetalleDataminingTri extends JBaseForm {


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

  JPssLabel pssLabel = new JPssLabel();
  JPssLabel ShareGapGLobal = new JPssLabel();
  JPssLabel pssLabel_1 = new JPssLabel();
	private JCheckBox jCheckBox = null;
  JPssEdit pssEdit = new JPssEdit();

	private JComboBox jComboBox = null;

	private JPssLabel lvalor2 = null;

	private JPanel jPanel = null;

	private JPssLabel lvalor3 = null;

	private JPssEdit valorActual = null;

	private JPssEdit valorObjetivo = null;  //  @jve:decl-index=0:visual-constraint="-97,34"

	private JTable jTable = null;

	private JPssLabel lvariable1 = null;

	private JComboBox variableGanancia = null;
	private JComboBox variableAuxiliar = null;

	private JPssLabel lvalor31 = null;

	private JPssEdit valorActual1 = null;

	private JCheckBox jCheckBox1 = null;

	private JPssLabel lvariable2 = null;

	private JTextField jDescr = null;

	/**
	   * Constructor de la Clase
	   */
	  public FormEditDetalleDataminingTri() throws Exception {
	    try { jbInit(); }
	    catch (Exception e) { e.printStackTrace(); } 
	  }

	  public GuiDetalleDataminingTri getWin() { return (GuiDetalleDataminingTri) getBaseWin(); }

	  /**
	   * Inicializacion Grafica
	   */
	  protected void jbInit() throws Exception {
	    lvariable2 = new JPssLabel();
	    lvariable2.setBounds(new Rectangle(14, 85, 112, 23));
	    lvariable2.setText("Descripcion");
	    lvalor31 = new JPssLabel();
	    lvalor31.setBounds(new Rectangle(553, 32, 41, 23));
	    lvalor31.setText("Valor");
	    lvariable1 = new JPssLabel();
	    lvariable1.setBounds(new Rectangle(14, 32, 112, 23));
	    lvariable1.setText("Indicador Base Com.");
	    lvalor3 = new JPssLabel();
	    lvalor3.setBounds(new Rectangle(553, 6, 41, 22));
	    lvalor3.setText("Valor");
	    lvalor2 = new JPssLabel();
	    lvalor2.setText("Periodicidad");
	    lvalor2.setBounds(new Rectangle(122, 13, 94, 22));
	    setLayout(null);
	    setSize(new Dimension(1006, 328));



	   // getJPanel().add(valor , null);


	    lvariable.setText("Indicador Obj.");
	    lvariable.setBounds(new Rectangle(14, 6, 112, 23)); 
	    variable.setBounds(new Rectangle(125, 6, 424, 23)); 
	    add(lvariable, null);
	    add(variable , null);


//	    getJPanel().add(lvalor1, null);
//	    getJPanel().add(getJComboBox(), null);
//	    getJPanel().add(lvalor2, null);
//	    this.add(getJPanel(), null);
	    this.add(lvalor3, null);
	    this.add(getValorActual(), null);
	    this.add(getJTable(), null);
	    this.add(lvariable1, null);
	    this.add(getVariableGanancia(), null);
	    this.add(getVariableAuxiliar(), null);
	    this.add(lvalor31, null);
	    this.add(getValorActual1(), null);
	    this.add(getJCheckBox1(), null);
	    this.add(lvariable2, null);
	    this.add(getJDescr(), null);
	    pssLabel.setText("FMS global");
	    pssLabel.setBounds(new Rectangle(14, 57, 112, 23));
	    pssLabel.setBounds(725, 6, 112, 23);
	    add(pssLabel);
	    
//	    getJPanel().setBorder(BorderFactory.createEtchedBorder());
	    FMSGlobal.setBounds(new Rectangle(597, 32, 100, 23));
	    FMSGlobal.setBounds(818, 6, 100, 23);
	    add(FMSGlobal);
	    
	    ShareGapGLobal.setText("Target Share gap");
	    ShareGapGLobal.setBounds(new Rectangle(14, 57, 112, 23));
	    ShareGapGLobal.setBounds(725, 32, 94, 23);
	    add(ShareGapGLobal);
	    
	    pssSharegapglobal.setBounds(new Rectangle(597, 32, 100, 23));
	    pssSharegapglobal.setBounds(818, 32, 100, 23);
	    add(pssSharegapglobal);
	    
	    pssLabel_1.setText("Valor ref. global");
	    pssLabel_1.setBounds(new Rectangle(14, 57, 112, 23));
	    pssLabel_1.setBounds(725, 57, 94, 23);
	    add(pssLabel_1);
	    
	    pssValorRegGlobal.setBounds(new Rectangle(597, 32, 100, 23));
	    pssValorRegGlobal.setBounds(818, 57, 100, 23);
	    add(pssValorRegGlobal);

	    
	    pssValorReembolso.setBounds(818, 85, 100, 23);
	    add(pssValorReembolso);
	    
	    JPssLabel pslblReembolso = new JPssLabel();
	    pslblReembolso.setText("Reembolso");
	    pslblReembolso.setBounds(new Rectangle(14, 57, 112, 23));
	    pslblReembolso.setBounds(725, 85, 94, 23);
	    add(pslblReembolso);
	    
	    JPssLabel pslblIndicadorauxiliar = new JPssLabel();
	    pslblIndicadorauxiliar.setText("IndicadorAuxiliar");
	    pslblIndicadorauxiliar.setBounds(new Rectangle(14, 32, 112, 23));
	    pslblIndicadorauxiliar.setBounds(14, 58, 112, 23);
	    add(pslblIndicadorauxiliar);
	    
	    JPssLabel pssLabel_2 = new JPssLabel();
	    pssLabel_2.setText("Valor");
	    pssLabel_2.setBounds(new Rectangle(553, 32, 41, 23));
	    pssLabel_2.setBounds(553, 59, 41, 23);
	    add(pssLabel_2);
	    
	    pssEdit.setBounds(new Rectangle(597, 32, 100, 23));
	    pssEdit.setBounds(597, 60, 100, 23);
	    add(pssEdit);
 
	  }
	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
	    AddItem( company, CHAR, REQ, "company" ).SetValorDefault(BizUsuario.getUsr().getCompany());
	    AddItem( id, UINT, OPT, "id" );
	    AddItem( linea, UINT, OPT, "linea" );
	    JFormControl c = AddItem( variable, CHAR, REQ, "variable" , new JControlCombo() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getIndicador(one);
	    	}
	    });
	    c.setRefreshForm(true);
	    c.setPlaceHolder("Indicador para determinar si se alcanza objetivo");
	    c=AddItem( variableGanancia, CHAR, OPT, "variable_ganancia" , new JControlCombo() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getIndicadorGanancia(one);
	    	}
	    });
	    c.setRefreshForm(true);
	    c.setPlaceHolder("Indicador para determinar el premio");
	    c=AddItem( variableAuxiliar, CHAR, OPT, "variable_aux" , new JControlCombo() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getIndicadorAuxiliar(one);
	    	}
	    });
	    c.setRefreshForm(true);
	    c.setPlaceHolder("Indicador para determinar el valor auxiliar");
	   
	    AddItem( getJCheckBox1(),OPT, "kicker" );
	    c=AddItem( getJCheckBox(), OPT,"acumulativo");
	    c.SetValorDefault(false);
//	    c.setRefreshForm(true);
//	    AddItem( getJComboBox(), CHAR, OPT, "periodo" , JWins.createVirtualWinsFromMap(BizDetalle.getPeriodos()));
	    JFormTable t=AddItem( getJTable(), "niveles", GuiNiveles.class);
	    t.setKeepHeight(true);
	    t.setKeepWidth(true);
	    AddItem( getValorActual(), UFLOAT, OPT, "valor_fcontrato" ).SetReadOnly(true);
	    AddItem( getValorActual1(), UFLOAT, OPT, "valor_totalcontrato" ).SetReadOnly(true);
	    AddItem( pssEdit, UFLOAT, OPT, "ganancia_auxiliar" ).SetReadOnly(true);
//	    AddItem( getValorObjetivo(), UFLOAT, OPT, "valor_objetivo" ).setVisible(false);
	    AddItem( getEvalua(), CHAR, OPT, "conclusion" ).SetReadOnly(true);
	    AddItem( getJDescr(), CHAR, OPT, "descripcion" );
	    AddItem( FMSGlobal, FLOAT, OPT, "fms_global" );
	    AddItem( pssSharegapglobal, FLOAT, OPT, "sharegap_global" );
	    AddItem( pssValorRegGlobal, FLOAT, OPT, "valor_global" );
	    AddItem( pssValorReembolso, FLOAT, OPT, "valor_reembolso" );
	
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
	  	g.addOrderAdHoc("descripcion", "ASC");

	  	return g;
	  }
	  private JWins getIndicadorGanancia(boolean one) throws Exception {
	  	GuiSqlEvents g = new GuiBSPSqlEvents();
	  	if (one) {
	  		g.getRecords().addFilter("id", getWin().GetcDato().getVariableGanancia());
	  	} else {
	  		g.getRecords().addFilter("company", getControles().findControl("company").getValue());
	  	}
	  	g.addOrderAdHoc("descripcion", "ASC");
	  	return g;
	  }
	  private JWins getIndicadorAuxiliar(boolean one) throws Exception {
	  	GuiSqlEvents g = new GuiBSPSqlEvents();
	  	if (one) {
	  		g.getRecords().addFilter("id", getWin().GetcDato().getVariableAuxiliar());
	  	} else {
	  		g.getRecords().addFilter("company", getControles().findControl("company").getValue());
	  	}
	  	g.addOrderAdHoc("descripcion", "ASC");
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
	  		
	  		if (getWin().GetccDato().needFMSGLobal()) {
	  			getControles().findControl("fms_global").edit();
	  			pssLabel.setVisible(true);
	  		} else {
	  			getControles().findControl("fms_global").hide();
	  			pssLabel.setVisible(false);
	  		}
	   		if (getWin().GetccDato().needShareGapGLobal()) {
	  			getControles().findControl("sharegap_global").edit();
	  			ShareGapGLobal.setVisible(true);
	  		} else {
	  			getControles().findControl("sharegap_global").hide();
	  			ShareGapGLobal.setVisible(false);
	  		}
	   		if (getWin().GetccDato().needValorRefGLobal()) {
	  			getControles().findControl("valor_global").edit();
	  			pssLabel_1.setVisible(true);
	  		} else {
	  			getControles().findControl("valor_global").hide();
	  			pssLabel_1.setVisible(false);
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
				jTable.setBounds(new Rectangle(13, 119, 977, 197));
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
		private JComboBox getVariableAuxiliar() {
			if (variableAuxiliar == null) {
				variableAuxiliar = new JComboBox();
				variableAuxiliar.setBounds(new Rectangle(125, 59, 424, 23));
			}
			return variableAuxiliar;
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
				jCheckBox1.setBounds(new Rectangle(597, 85, 115, 22));
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
				jDescr.setBounds(new Rectangle(125, 85, 469, 23));
			}
			return jDescr;
		} 
	}  //  @jve:decl-index=0:visual-constraint="24,55"
