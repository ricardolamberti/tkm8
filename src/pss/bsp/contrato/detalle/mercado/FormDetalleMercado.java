package pss.bsp.contrato.detalle.mercado;

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

public class FormDetalleMercado extends JBaseForm {


private static final long serialVersionUID = 1245253775814L;

  JPssEdit company = new JPssEdit  ();
JPssEdit owner = new JPssEdit  ();
JPssEdit idPDF = new JPssEdit  ();
JPssEdit linea = new JPssEdit  ();
JPssLabel ltipo_ruta = new JPssLabel();
JPssEdit marketingid = new JPssEdit  ();
JPssLabel laerolinea = new JPssLabel();
JPssEdit aerolinea = new JPssEdit  ();
JPssLabel loperacion = new JPssLabel();
JPssEdit origen = new JPssEdit  ();
JPssLabel lboleto = new JPssLabel();
JPssEdit destino = new JPssEdit  ();

private JTabbedPane jTabbedPane = null;

private JPanel jPanel = null;

private JPssLabel ltarifa = null;

private JPssEdit fms = null;

/**
   * Constructor de la Clase
   */
  public FormDetalleMercado() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiDetalleMercado getWin() { return (GuiDetalleMercado) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    destino.setBounds(new Rectangle(319, 51, 143, 22));
    origen.setBounds(new Rectangle(88, 51, 143, 22));
    aerolinea.setBounds(new Rectangle(88, 18, 143, 22));
    marketingid.setBounds(new Rectangle(319, 19, 143, 22));
    ltipo_ruta.setText( "Marketing ID" );
    ltipo_ruta.setBounds(new Rectangle(241, 17, 74, 22));
    getJPanel().add(ltipo_ruta, null);
    getJPanel().add(marketingid , null);


    laerolinea.setText( "Aerolinea" );
    laerolinea.setBounds(new Rectangle(13, 17, 70, 22));
    getJPanel().add(laerolinea, null);
    getJPanel().add(aerolinea , null);


    loperacion.setText( "Origen" );
    loperacion.setBounds(new Rectangle(13, 51, 74, 22));
    getJPanel().add(loperacion, null);
    getJPanel().add(origen , null);


    lboleto.setText( "Destino" );
    lboleto.setBounds(new Rectangle(241, 51, 70, 22));
    getJPanel().add(lboleto, null);
    getJPanel().add(destino , null);
    setLayout(null);
    setSize(new Dimension(591, 155));
    this.add(getJPanel(), null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" ).setVisible(false);
    AddItem( owner, CHAR, REQ, "owner" ).setVisible(false);
    AddItem( idPDF, CHAR, REQ, "idPDF" ).setVisible(false);
    AddItem( linea, UINT, REQ, "linea" ).setVisible(false);
    AddItem( aerolinea, CHAR, REQ, "id_aerolinea" );
    AddItem( origen, CHAR, REQ, "origen" );
    AddItem( destino, CHAR, REQ, "destino" );
    AddItem( marketingid, CHAR, REQ, "marketing_id" );
    AddItem( fms, UFLOAT, REQ, "fms" );
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
			jPanel.setBounds(new Rectangle(10, 13, 568, 135));
			jPanel.setBorder(BorderFactory.createTitledBorder(null, "INFO", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));


			jPanel.add(ltipo_ruta, null);
			jPanel.add(marketingid, null);
			jPanel.add(laerolinea, null);
			jPanel.add(aerolinea, null);
			jPanel.add(loperacion, null);
			jPanel.add(origen, null);
			jPanel.add(lboleto, null);
			jPanel.add(destino, null);
			jPanel.add(getTarifa());
			
					ltarifa = new JPssLabel();
					ltarifa.setBounds(13, 84, 63, 23);
					jPanel.add(ltarifa);
					ltarifa.setText("FMS");
		}
		return jPanel;
	}

	/**
	 * This method initializes tarifa	
	 * 	
	 * @return pss.core.ui.components.JPssEdit	
	 */
	private JPssEdit getTarifa() {
		if (fms == null) {
			fms = new JPssEdit();
			fms.setBounds(88, 84, 126, 23);
		}
		return fms;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10" 
