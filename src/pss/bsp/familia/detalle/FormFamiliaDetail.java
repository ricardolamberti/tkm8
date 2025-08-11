package  pss.bsp.familia.detalle;

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

public class FormFamiliaDetail extends JBaseForm {


private static final long serialVersionUID = 1245253307166L;

  /**
   * Propiedades de la Clase
   */
JPssLabel lpais = new JPssLabel();
JPssLabelWinLov carrier = new JPssLabelWinLov  ();
JPssEdit id = new JPssEdit  ();
JPssEdit idClase = new JPssEdit  ();
JPssEdit company = new JPssEdit  ();

private JPssLabel lpais1 = null;

private JPssEdit jTextArea = null;

private JPssLabel lpais11 = null;

private JPssEdit jTextArea1 = null;

private JPssLabel lpais111 = null;

private JPssLabel lpais1111 = null;
/**
   * Constructor de la Clase
   */
  public FormFamiliaDetail() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiFamiliaDetail getWin() { return (GuiFamiliaDetail) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    lpais1111 = new JPssLabel();
    lpais1111.setBounds(new Rectangle(176, 95, 283, 16));
    lpais1111.setText("el sistema tomará el de prioridad mas alta");
    lpais111 = new JPssLabel();
    lpais111.setBounds(new Rectangle(176, 71, 289, 22));
    lpais111.setText("SI una ruta esta compuesta por varios segmentos");
    lpais11 = new JPssLabel();
    lpais11.setBounds(new Rectangle(14, 71, 83, 21));
    lpais11.setText("Prioridad");
    lpais1 = new JPssLabel();
    lpais1.setBounds(new Rectangle(16, 48, 80, 22));
    lpais1.setText("Letra");
    setLayout(null);
    setSize(new Dimension(484, 132));


    lpais.setText("Aerolinea");
    lpais.setBounds(new Rectangle(16, 23, 80, 22)); 
    carrier.setBounds(new Rectangle(98, 23, 289, 22)); 
    add(lpais, null);
    add(carrier , null);


    this.add(lpais1, null);
    this.add(getJText(), null);
    this.add(lpais11, null);
    this.add(getJTextArea1(), null);
    this.add(lpais111, null);
    this.add(lpais1111, null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( carrier, CHAR, REQ, "aerolinea" , new JControlWin() {
    	@Override
    	public JWins getRecords(boolean bOneRow) throws Exception {
    		return getAerolineas(bOneRow);
    	}
    });
    AddItem( id, CHAR, OPT, "id" );
    AddItem( idClase, CHAR, OPT, "id_familia" );
    AddItem( company, CHAR, OPT, "company" );
    AddItem( getJText(), CHAR, REQ, "letra" );
    AddItem( getJTextArea1(), CHAR, REQ, "prioridad" );

  }

  public JWins getAerolineas(boolean one) throws Exception {
  	GuiCarriers carrs= new GuiCarriers();
  	if (one) {
  		carrs.getRecords().addFilter("carrier", getWin().GetcDato().getCarrier());
  	}
  	carrs.setModeWinLov(true);
  	return carrs;
  }
	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JPssEdit getJText() {
		if (jTextArea == null) {
			jTextArea = new JPssEdit();
			jTextArea.setBounds(new Rectangle(98, 48, 62, 23));
		}
		return jTextArea;
	}

	/**
	 * This method initializes jTextArea1	
	 * 	
	 * @return pss.core.ui.components.JPssEdit	
	 */
	private JPssEdit getJTextArea1() {
		if (jTextArea1 == null) {
			jTextArea1 = new JPssEdit();
			jTextArea1.setBounds(new Rectangle(98, 71, 74, 20));
		}
		return jTextArea1;
	} 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
