package  pss.bsp.market.detalle;

import java.awt.Dimension;
import java.awt.Rectangle;

import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.tourism.carrier.GuiCarriers;
import pss.core.ui.components.JPssLabel;
import javax.swing.JTextArea;
import pss.core.ui.components.JPssEdit;

public class FormMarketDetail extends JBaseForm {


private static final long serialVersionUID = 1245253307166L;

  /**
   * Propiedades de la Clase
   */
JPssLabel lpais = new JPssLabel();
JScrollableTextArea carrier = new JScrollableTextArea  ();
JPssEdit id = new JPssEdit  ();
JPssEdit idClase = new JPssEdit  ();
JPssEdit company = new JPssEdit  ();

private JPssLabel lpais1 = null;

private JPssLabel lpais11 = null;

private JPssEdit jTextArea1 = null;

private JPssLabel lpais111 = null;

private JPssLabel lpais1111 = null;
/**
   * Constructor de la Clase
   */
  public FormMarketDetail() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiMarketDetail getWin() { return (GuiMarketDetail) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    lpais11 = new JPssLabel();
    lpais11.setBounds(new Rectangle(16, 260, 58, 21));
    lpais11.setText("Prioridad");
    setLayout(null);
    setSize(new Dimension(484, 359));


    lpais.setText("Ruta");
    lpais.setBounds(new Rectangle(16, 23, 40, 22)); 
    carrier.setBounds(new Rectangle(66, 23, 408, 222)); 
    add(lpais, null);
    add(carrier , null);
    this.add(lpais11, null);
    this.add(getJTextArea1(), null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, OPT, "company" );
    AddItem( id, CHAR, OPT, "id" );
    AddItem( idClase, CHAR, OPT, "id_market" );
    AddItem( carrier.getTextArea(), CHAR, REQ, "ruta").setKeepWidth(true);;
    AddItem( getJTextArea1(), CHAR, REQ, "prioridad" );

  }

	/**
	 * This method initializes jTextArea1	
	 * 	
	 * @return pss.core.ui.components.JPssEdit	
	 */
	private JPssEdit getJTextArea1() {
		if (jTextArea1 == null) {
			jTextArea1 = new JPssEdit();
			jTextArea1.setBounds(new Rectangle(66, 260, 74, 20));
		}
		return jTextArea1;
	} 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
