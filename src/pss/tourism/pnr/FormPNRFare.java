package pss.tourism.pnr;

import java.awt.Dimension;
import java.awt.Rectangle;

import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.ui.components.JPssLabelFormLov;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.airports.GuiAirports;

public class FormPNRFare extends JBaseForm {


	private static final long serialVersionUID = 1446642143959L;

	  /**
	   * Propiedades de la Clase
	   */
	JPssLabel lcodigomoneda = new JPssLabel();
	JPssEdit codigomoneda = new JPssEdit  ();
	JPssLabel limporte = new JPssLabel();
	JPssEdit importe = new JPssEdit  ();
	JPssEdit secuencia = new JPssEdit  ();
	JPssEdit interface_id = new JPssEdit  ();
	JPssLabel lcodigoimpuesto = new JPssLabel();
	JPssEdit codigoimpuesto = new JPssEdit  ();
	JPssLabel lcodigopnr = new JPssLabel();
	JPssEdit codigopnr = new JPssEdit  ();
	JPssEdit company = new JPssEdit  ();

	private JPssLabel lcodigomoneda1 = null;

	private JPssLabel lcodigomoneda2 = null;

	private JPssLabel lcodigomoneda3 = null;

	private JPssLabelFormLov depegue = null;

	private JPssLabelFormLov arrivo = null;

	private JPssEdit ruta = null;


	  /**
	   * Constructor de la Clase
	   */
	  public FormPNRFare() throws Exception {
	    try { jbInit(); }
	    catch (Exception e) { e.printStackTrace(); } 
	  }

	  public GuiPNRFare getWin() { return (GuiPNRFare) getBaseWin(); }

	  /**
	   * Inicializacion Grafica
	   */
	  protected void jbInit() throws Exception {
	    lcodigomoneda3 = new JPssLabel();
	    lcodigomoneda3.setBounds(new Rectangle(16, 123, 123, 22));
	    lcodigomoneda3.setText("Ruta");
	    lcodigomoneda2 = new JPssLabel();
	    lcodigomoneda2.setBounds(new Rectangle(16, 96, 123, 19));
	    lcodigomoneda2.setText("Arrivo");
	    lcodigomoneda1 = new JPssLabel();
	    lcodigomoneda1.setBounds(new Rectangle(16, 68, 123, 22));
	    lcodigomoneda1.setText("Despegue");
	    setLayout(null);
	    setSize(new Dimension(472, 308));


	    lcodigomoneda.setText("Impuesto");
	    lcodigomoneda.setBounds(new Rectangle(15, 147, 123, 22)); 
	    codigomoneda.setBounds(new Rectangle(144, 147, 143, 22)); 
	    add(lcodigomoneda, null);
	    add(codigomoneda , null);


	    limporte.setText( "Importe" );
	    limporte.setBounds(new Rectangle(16, 174, 123, 22)); 
	    importe.setBounds(new Rectangle(144, 174, 143, 22)); 
	    add(limporte, null);
	    add(importe , null);


	    lcodigoimpuesto.setText("Tipo pasajero");
	    lcodigoimpuesto.setBounds(new Rectangle(16, 15, 123, 22)); 
	    codigoimpuesto.setBounds(new Rectangle(144, 15, 75, 22)); 
	    add(lcodigoimpuesto, null);
	    add(codigoimpuesto , null);


	    lcodigopnr.setText( "Codigopnr" );
	    lcodigopnr.setBounds(new Rectangle(16, 43, 123, 22)); 
	    codigopnr.setBounds(new Rectangle(144, 43, 94, 22)); 
	    add(lcodigopnr, null);
	    add(codigopnr , null);


	    this.add(lcodigomoneda1, null);
	    this.add(lcodigomoneda2, null);
	    this.add(lcodigomoneda3, null);
	    this.add(getDepegue(), null);
	    this.add(getArrivo(), null);
	    this.add(getRuta(), null);
	  }
	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
	    //AddItem( codigomoneda, CHAR, REQ, "codigomoneda" );
	    AddItem( codigomoneda, FLOAT, REQ, "impuesto" );
	    AddItem( importe, FLOAT, REQ, "importe" );
	    AddItem( secuencia, UINT, REQ, "secuencia" );
	    AddItem( interface_id, UINT, REQ, "interface_id" );
	    AddItem( codigoimpuesto, CHAR, REQ, "fare" );
	    AddItem( codigopnr, CHAR, REQ, "codigopnr" );
	    AddItem( company, CHAR, REQ, "company" );
	    AddItem( getRuta(), CHAR, REQ, "ruta" );
	    AddItem( getDepegue(), CHAR, REQ, "airport_from" , new JControlWin() {
	    	@Override
	    	public JWins getRecords(boolean bOneRow) throws Exception {
	    		return getDespegue(bOneRow);
	    	}
	    });
	    AddItem( getArrivo(), CHAR, REQ, "airport_to" , new JControlWin() {
	    	@Override
	    	public JWins getRecords(boolean bOneRow) throws Exception {
	    		return getArrivo(bOneRow);
	    	}
	    });
	  }

		/**
		 * This method initializes depegue	
		 * 	
		 * @return pss.core.ui.components.JPssEdit	
		 */
		private JPssLabelFormLov getDepegue() {
			if (depegue == null) {
				depegue = new JPssLabelFormLov();
				depegue.setBounds(new Rectangle(144, 68, 190, 22));
			}
			return depegue;
		}
    public JWins getDespegue(boolean one) throws Exception {
    	GuiAirports wins = new GuiAirports();
    	if (one) {
    		wins.getRecords().addFilter("code", getWin().GetcDato().getAirportFrom());
    	}
    	return wins;
    }
    public JWins getArrivo(boolean one) throws Exception {
    	GuiAirports wins = new GuiAirports();
    	if (one) {
    		wins.getRecords().addFilter("code", getWin().GetcDato().getAirportTo());
    	}
    	return wins;
    }

		/**
		 * This method initializes arrivo	
		 * 	
		 * @return pss.core.ui.components.JPssEdit	
		 */
		private JPssLabelFormLov getArrivo() {
			if (arrivo == null) {
				arrivo = new JPssLabelFormLov();
				arrivo.setBounds(new Rectangle(144, 96, 193, 22));
			}
			return arrivo;
		}

		/**
		 * This method initializes ruta	
		 * 	
		 * @return pss.core.ui.components.JPssEdit	
		 */
		private JPssEdit getRuta() {
			if (ruta == null) {
				ruta = new JPssEdit();
				ruta.setBounds(new Rectangle(144, 123, 289, 22));
			}
			return ruta;
		} 
	}  //  @jve:decl-index=0:visual-constraint="10,10" 
