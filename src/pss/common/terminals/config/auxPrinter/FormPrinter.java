package pss.common.terminals.config.auxPrinter;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pss.core.ui.components.JPssCalendarEdit;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormPrinter extends JBaseForm {


private static final long serialVersionUID = 1L;

  /**
   * Propiedades de la Clase
   */
JPssLabel lcomany = new JPssLabel();
JPssEdit comany = new JPssEdit  ();
JPssLabel lnodo = new JPssLabel();
JPssEdit nodo = new JPssEdit  ();
JPssLabel lterminal_id = new JPssLabel();
JPssEdit terminal_id = new JPssEdit  ();
JPssLabel lnro_rollo = new JPssLabel();
JPssEdit nro_rollo = new JPssEdit  ();

private JCheckBox jCierrePart = null;

private JPanel jPanel = null;

private JPssLabel lFechaInicio = null;

private JPssLabel lHoraInicio = null;

private JPssCalendarEdit jFechaInicio = null;

private JPssEdit jHoraInicio = null;


  /**
   * Constructor de la Clase
   */
  public FormPrinter() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiPrinter GetWin() { return (GuiPrinter) GetBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new java.awt.Dimension(357,200));

//    this.add(getJTieneRollo(), null);

    lnro_rollo.setText("Nro Rollo");
    lnro_rollo.setBounds(new java.awt.Rectangle(22,20,67,22)); 
    nro_rollo.setBounds(new java.awt.Rectangle(101,20,136,22)); 
    add(lnro_rollo, null);
    add(nro_rollo , null);


    this.add(getJPanel(), null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( comany, CHAR, REQ, "comany" );
    AddItem( nodo, CHAR, REQ, "nodo" );
    AddItem( terminal_id, UINT, REQ, "terminal_id" );
//    AddItem( jTieneRollo, CHAR, REQ, "tiene_rollo", "S", "N" );
    AddItem( nro_rollo, CHAR, OPT, "nro_rollo" );
    AddItem( jCierrePart, CHAR, REQ, "cierre_informe_particular", "S", "N" );
    AddItem( jFechaInicio, DATE, OPT, "fecha_inicio_fiscal" );
    AddItem( jHoraInicio, HOUR, OPT, "hora_inicio_fiscal" );

  }

/**
 * This method initializes jTieneRollo	
 * 	
 * @return javax.swing.JCheckBox	
 */
//private JCheckBox getJTieneRollo() {
//	if (jTieneRollo == null) {
//		jTieneRollo = new JCheckBox();
//		jTieneRollo.setBounds(new java.awt.Rectangle(17,22,96,21));
//		jTieneRollo.setText("Tiene Rollo");
//	}
//	return jTieneRollo;
//}

/**
 * This method initializes jCierrePart	
 * 	
 * @return javax.swing.JCheckBox	
 */
private JCheckBox getJCierrePart() {
	if (jCierrePart == null) {
		jCierrePart = new JCheckBox();
		jCierrePart.setText("Cierres X y Z solo de movimientos propios");
		jCierrePart.setBounds(new java.awt.Rectangle(18,27,263,24));
	}
	return jCierrePart;
}

/**
 * This method initializes jPanel	
 * 	
 * @return javax.swing.JPanel	
 */
private JPanel getJPanel() {
	if (jPanel == null) {
		lHoraInicio = new JPssLabel();
		lHoraInicio.setBounds(new java.awt.Rectangle(22,85,112,22));
		lHoraInicio.setText("Hora Inicio Fiscal");
		lFechaInicio = new JPssLabel();
		lFechaInicio.setBounds(new java.awt.Rectangle(22,61,112,22));
		lFechaInicio.setText("Fecha Inicio Fiscal");
		jPanel = new JPanel();
		jPanel.setLayout(null);
		jPanel.setBounds(new java.awt.Rectangle(12,58,319,125));
		jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Impresora No Fiscal pero con Rollo Fiscal", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
		jPanel.add(getJCierrePart(), null);
		jPanel.add(lFechaInicio, null);
		jPanel.add(lHoraInicio, null);
		jPanel.add(getJFechaInicio(), null);
		jPanel.add(getJHoraInicio(), null);
	}
	return jPanel;
}

/**
 * This method initializes jFechaInicio	
 * 	
 * @return javax.swing.JTextField	
 */
private JPssCalendarEdit getJFechaInicio() {
	if (jFechaInicio == null) {
		jFechaInicio = new JPssCalendarEdit();
		jFechaInicio.setBounds(new java.awt.Rectangle(149,58,106,20));
	}
	return jFechaInicio;
}

/**
 * This method initializes jHoraInicio	
 * 	
 * @return javax.swing.JTextField	
 */
private JTextField getJHoraInicio() {
	if (jHoraInicio == null) {
		jHoraInicio = new JPssEdit();
		jHoraInicio.setBounds(new java.awt.Rectangle(149,87,84,20));
	}
	return jHoraInicio;
} 
}  //  @jve:decl-index=0:visual-constraint="10,9"
