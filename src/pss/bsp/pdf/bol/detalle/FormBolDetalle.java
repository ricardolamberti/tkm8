package  pss.bsp.pdf.bol.detalle;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JTabbedPane;

import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.GridBagConstraints;

public class FormBolDetalle extends JBaseForm {


private static final long serialVersionUID = 1245253775814L;

  JPssEdit company = new JPssEdit  ();
JPssEdit owner = new JPssEdit  ();
JPssEdit idPDF = new JPssEdit  ();
JPssEdit linea = new JPssEdit  ();
JPssLabel ltipo_ruta = new JPssLabel();
JPssEdit tipo_ruta = new JPssEdit  ();
JPssLabel laerolinea = new JPssLabel();
JPssEdit aerolinea = new JPssEdit  ();
JPssLabel loperacion = new JPssLabel();
JPssEdit operacion = new JPssEdit  ();
JPssLabel lboleto = new JPssLabel();
JPssEdit boleto = new JPssEdit  ();
JPssLabel lfecha = new JPssLabel();
JPssEdit fecha = new JPssEdit  ();
JPssLabel lcontado = new JPssLabel();
JPssEdit contado = new JPssEdit  ();
JPssLabel ltarjeta = new JPssLabel();
JPssEdit tarjeta = new JPssEdit  ();
JPssLabel lbase_imponible = new JPssLabel();
JPssEdit base_imponible = new JPssEdit  ();
JPssLabel limpuesto1 = new JPssLabel();
JPssEdit impuesto1 = new JPssEdit  ();
JPssLabel limpuesto2 = new JPssLabel();
JPssEdit impuesto2 = new JPssEdit  ();
JPssLabel lcomision = new JPssLabel();
JPssEdit comision = new JPssEdit  ();
JPssLabel limp_sobre_com = new JPssLabel();
JPssEdit imp_sobre_com = new JPssEdit  ();
JPssLabel lcomision_over = new JPssLabel();
JPssEdit comision_over = new JPssEdit  ();
JPssLabel lobservaciones = new JPssLabel();
JPssEdit observaciones = new JPssEdit  ();
JPssLabel lnumero_tarjeta = new JPssLabel();
JPssEdit numero_tarjeta = new JPssEdit  ();
JPssLabel ltipo_tarjeta = new JPssLabel();
JPssEdit tipo_tarjeta = new JPssEdit  ();

private JTabbedPane jTabbedPane = null;

private JPanel jPanel = null;

private JPanel jPanel1 = null;

private JPssLabel ltarifa = null;

private JPssEdit tarifa = null;

private JPanel jPanel2 = null;

private JPanel jPanel3 = null;

/**
   * Constructor de la Clase
   */
  public FormBolDetalle() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiBolDetalle getWin() { return (GuiBolDetalle) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    numero_tarjeta.setBounds(new Rectangle(384, 47, 173, 22));
    contado.setBounds(new Rectangle(124, 47, 142, 22));
    tarjeta.setBounds(new Rectangle(125, 22, 142, 22));
    tipo_tarjeta.setBounds(new Rectangle(384, 22, 173, 22));
    comision_over.setBounds(new Rectangle(133, 71, 143, 22));
    imp_sobre_com.setBounds(new Rectangle(133, 46, 143, 22));
    comision.setBounds(new Rectangle(133, 21, 143, 22));
    base_imponible.setBounds(new Rectangle(130, 41, 126, 22));
    impuesto2.setBounds(new Rectangle(130, 91, 126, 22));
    impuesto1.setBounds(new Rectangle(130, 66, 126, 22));
    fecha.setBounds(new Rectangle(420, 19, 143, 22));
    boleto.setBounds(new Rectangle(89, 46, 227, 22));
    operacion.setBounds(new Rectangle(419, 71, 143, 22));
    aerolinea.setBounds(new Rectangle(88, 18, 229, 22));
    tipo_ruta.setBounds(new Rectangle(419, 46, 143, 22));
    ltipo_ruta.setText( "Tipo ruta" );
    ltipo_ruta.setBounds(new Rectangle(341, 44, 74, 22));
    getJPanel().add(ltipo_ruta, null);
    getJPanel().add(tipo_ruta , null);


    laerolinea.setText( "Aerolinea" );
    laerolinea.setBounds(new Rectangle(13, 17, 70, 22));
    getJPanel().add(laerolinea, null);
    getJPanel().add(aerolinea , null);


    loperacion.setText( "Operacion" );
    loperacion.setBounds(new Rectangle(341, 72, 74, 22));
    getJPanel().add(loperacion, null);
    getJPanel().add(operacion , null);


    lboleto.setText( "Boleto" );
    lboleto.setBounds(new Rectangle(13, 45, 70, 22));
    getJPanel().add(lboleto, null);
    getJPanel().add(boleto , null);

		ltarifa = new JPssLabel();
		ltarifa.setText("Tarifa");
		ltarifa.setBounds(new Rectangle(10, 16, 111, 23));
    lbase_imponible.setText( "Base imponible" );
    lbase_imponible.setBounds(new Rectangle(10, 41, 112, 22));
    limpuesto2.setText( "Impuesto2" );
    limpuesto2.setBounds(new Rectangle(10, 91, 112, 22));
    limpuesto1.setText( "Impuesto1" );
    limpuesto1.setBounds(new Rectangle(10, 66, 112, 22));
    getJPanel1().add(lbase_imponible, null);
    getJPanel1().add(base_imponible , null);


    getJPanel1().add(limpuesto1, null);
    getJPanel1().add(impuesto1 , null);


    getJPanel1().add(limpuesto2, null);
    getJPanel1().add(impuesto2 , null);


    lfecha.setText( "Fecha" );
    lfecha.setBounds(new Rectangle(341, 18, 74, 22));
    getJPanel().add(lfecha, null);
    getJPanel().add(fecha , null);
    setLayout(null);
    setSize(new Dimension(591, 624));





    lcontado.setText( "Contado" );
    lcontado.setBounds(new Rectangle(5, 47, 113, 22));
    getJPanel3().add(lcontado, null);
    getJPanel3().add(contado , null);


    ltarjeta.setText( "Tarjeta" );
    ltarjeta.setBounds(new Rectangle(5, 22, 113, 22));
    getJPanel3().add(ltarjeta, null);
    getJPanel3().add(tarjeta , null);




    lcomision.setText( "Comision" );
    lcomision.setBounds(new Rectangle(15, 21, 112, 22));
    getJPanel2().add(lcomision, null);
    getJPanel2().add(comision , null);


    limp_sobre_com.setText( "Imp sobre com" );
    limp_sobre_com.setBounds(new Rectangle(15, 46, 112, 22));
    getJPanel2().add(limp_sobre_com, null);
    getJPanel2().add(imp_sobre_com , null);


    lcomision_over.setText( "Comision over" );
    lcomision_over.setBounds(new Rectangle(15, 71, 112, 22));
    getJPanel2().add(lcomision_over, null);
    getJPanel2().add(comision_over , null);


    lobservaciones.setText( "Observaciones" );
    lobservaciones.setBounds(new Rectangle(15, 362, 123, 22)); 
    observaciones.setBounds(new Rectangle(143, 362, 435, 22)); 
    add(lobservaciones, null);
    add(observaciones , null);


    lnumero_tarjeta.setText( "Numero tarjeta" );
    lnumero_tarjeta.setBounds(new Rectangle(283, 47, 95, 22));
    getJPanel3().add(lnumero_tarjeta, null);
    getJPanel3(). add(numero_tarjeta , null);


    ltipo_tarjeta.setText( "Tipo tarjeta" );
    ltipo_tarjeta.setBounds(new Rectangle(283, 22, 95, 22));
    getJPanel3().add(ltipo_tarjeta, null);
    getJPanel3().add(tipo_tarjeta , null);
    this.add(getJTabbedPane(), null);
    this.add(getJPanel(), null);
    this.add(getJPanel1(), null);
    this.add(getJPanel2(), null);
    this.add(getJPanel3(), null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" ).setVisible(false);
    AddItem( owner, CHAR, REQ, "owner" ).setVisible(false);
    AddItem( idPDF, CHAR, REQ, "idPDF" ).setVisible(false);
    AddItem( linea, UINT, REQ, "linea" ).setVisible(false);
    AddItem( tipo_ruta, CHAR, REQ, "tipo_ruta" );
    AddItem( aerolinea, CHAR, REQ, "aerolinea" );
    AddItem( operacion, CHAR, REQ, "operacion" );
    AddItem( boleto, CHAR, REQ, "boleto" );
    AddItem( fecha, UINT, REQ, "fecha" );
    AddItem( tarifa, UFLOAT, REQ, "tarifa" );
    AddItem( contado, UFLOAT, REQ, "contado" );
    AddItem( tarjeta, UFLOAT, REQ, "tarjeta" );
    AddItem( base_imponible, UFLOAT, REQ, "base_imponible" );
    AddItem( impuesto1, UFLOAT, REQ, "impuesto1" );
    AddItem( impuesto2, UFLOAT, REQ, "impuesto2" );
    AddItem( comision, UFLOAT, REQ, "comision" );
    AddItem( imp_sobre_com, UFLOAT, REQ, "imp_sobre_com" );
    AddItem( comision_over, UFLOAT, REQ, "comision_over" );
    AddItem( observaciones, CHAR, REQ, "observaciones" );
    AddItem( numero_tarjeta, CHAR, REQ, "numero_tarjeta" );
    AddItem( tipo_tarjeta, CHAR, REQ, "tipo_tarjeta" );
    AddItem( getJTabbedPane() , 10);
  }

  /**
   * This method initializes jTabbedPane	
   * 	
   * @return javax.swing.JTabbedPane	
   */
  private JTabbedPane getJTabbedPane() {
  	if (jTabbedPane==null) {
  		jTabbedPane=new JTabbedPane();
  		jTabbedPane.setBounds(new Rectangle(12, 395, 568, 222));
  	}
  	return jTabbedPane;
  }

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(10, 13, 568, 99));
			jPanel.setBorder(BorderFactory.createTitledBorder(null, "Boleto", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));


			jPanel.add(ltipo_ruta, null);
			jPanel.add(tipo_ruta, null);
			jPanel.add(laerolinea, null);
			jPanel.add(aerolinea, null);
			jPanel.add(loperacion, null);
			jPanel.add(operacion, null);
			jPanel.add(lboleto, null);
			jPanel.add(boleto, null);
			jPanel.add(lfecha, null);
			jPanel.add(fecha, null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			
	    jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(10, 120, 271, 132));
			jPanel1.add(ltarifa, null);
			jPanel1.add(getTarifa(), null);
			jPanel1.setBorder(BorderFactory.createTitledBorder(null, "Tarifa", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));

			jPanel1.add(lbase_imponible, null);
			jPanel1.add(limpuesto1, null);
			jPanel1.add(limpuesto2, null);
			jPanel1.add(impuesto1, null);
			jPanel1.add(impuesto2, null);
			jPanel1.add(base_imponible, null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes tarifa	
	 * 	
	 * @return pss.core.ui.components.JPssEdit	
	 */
	private JPssEdit getTarifa() {
		if (tarifa == null) {
			tarifa = new JPssEdit();
			tarifa.setBounds(new Rectangle(130, 16, 126, 23));
		}
		return tarifa;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(new Rectangle(288, 120, 292, 132));
			jPanel2.setBorder(BorderFactory.createTitledBorder(null, "Comisión", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));

			jPanel2.add(lcomision, null);
			jPanel2.add(limp_sobre_com, null);
			jPanel2.add(lcomision_over, null);
			jPanel2.add(comision, null);
			jPanel2.add(imp_sobre_com, null);
			jPanel2.add(comision_over, null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.setBounds(new Rectangle(10, 261, 571, 86));
			jPanel3.setBorder(BorderFactory.createTitledBorder(null, "Totales", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel3.add(lnumero_tarjeta, null);
			jPanel3.add(ltipo_tarjeta, null);
			jPanel3.add(ltarjeta, null);
			jPanel3.add(lcontado, null);
			jPanel3.add(tipo_tarjeta, null);
			jPanel3.add(tarjeta, null);
			jPanel3.add(contado, null);
			jPanel3.add(numero_tarjeta, null);
		}
		return jPanel3;
	} 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
