package pss.tourism.pnr;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JCheckBox;

import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.ui.components.JPssLabelFormLov;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.airports.GuiAirports;
import pss.tourism.carrier.GuiCarriers;
import javax.swing.JTabbedPane;

public class FormPNRSegmentoAereo extends JBaseForm {


private static final long serialVersionUID = 1446641998059L;

  /**
   * Propiedades de la Clase
   */
JCheckBox chckbxEmitido = new JCheckBox("Emitido");
JPssLabel lcodigoentretenimiento = new JPssLabel();
JPssEdit codigoentretenimiento = new JPssEdit  ();
JPssLabel lhoraarrivo = new JPssLabel();
JPssEdit horaarrivo = new JPssEdit  ();
JPssLabel ltipo_segmento = new JPssLabel();
JPssEdit tipo_segmento = new JPssEdit  ();
JPssLabel lhoradespegue = new JPssLabel();
JPssEdit horadespegue = new JPssEdit  ();
JPssLabel ldespegue = new JPssLabel();
JPssLabelFormLov despegue = new JPssLabelFormLov  ();
JPssLabel lduracionvuelo = new JPssLabel();
JPssEdit duracionvuelo = new JPssEdit  ();
JPssLabel larrivo = new JPssLabel();
JPssLabelFormLov arrivo = new JPssLabelFormLov  ();
JPssLabel lcarrier = new JPssLabel();
JPssLabelFormLov carrier = new JPssLabelFormLov  ();
JPssLabel lcodigosegmento = new JPssLabel();
JPssEdit codigosegmento = new JPssEdit  ();
JPssLabel lfechaarrivo = new JPssLabel();
JPssEdit fechaarrivo = new JPssEdit  ();
JPssLabel lestado = new JPssLabel();
JPssEdit estado = new JPssEdit  ();
JPssLabel lcodigopnr = new JPssLabel();
JPssEdit codigopnr = new JPssEdit  ();
JPssLabel lcodigocomida = new JPssLabel();
JPssEdit codigocomida = new JPssEdit  ();
JPssLabel lclase = new JPssLabel();
JPssEdit clase = new JPssEdit  ();
JPssLabel ltipoequipo = new JPssLabel();
JPssEdit tipoequipo = new JPssEdit  ();
JPssLabel lnumerovuelo = new JPssLabel();
JPssEdit numerovuelo = new JPssEdit  ();
JPssLabel lfechadespegue = new JPssLabel();
JPssEdit fechadespegue = new JPssEdit  ();
JPssEdit interface_id = new JPssEdit  ();
JPssEdit company = new JPssEdit  ();
JPssEdit segmento_ini = new JPssEdit  ();
private JTabbedPane tabbedPane;
/**
   * Constructor de la Clase
   */
  public FormPNRSegmentoAereo() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiPNRSegmentoAereo getWin() { return (GuiPNRSegmentoAereo) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(891, 415));


    lcodigoentretenimiento.setText("Cod.entretenimiento");
    lcodigoentretenimiento.setBounds(new Rectangle(262, 180, 123, 22)); 
    codigoentretenimiento.setBounds(new Rectangle(388, 180, 53, 22)); 
    add(lcodigoentretenimiento, null);
    add(codigoentretenimiento , null);


    lhoraarrivo.setText("Hora ");
    lhoraarrivo.setBounds(new Rectangle(534, 152, 39, 22)); 
    horaarrivo.setBounds(new Rectangle(575, 152, 63, 22)); 
    add(lhoraarrivo, null);
    add(horaarrivo , null);


    ltipo_segmento.setText( "Tipo segmento" );
    ltipo_segmento.setBounds(new Rectangle(6, 51, 112, 22)); 
    tipo_segmento.setBounds(new Rectangle(117, 51, 143, 22)); 
    add(ltipo_segmento, null);
    add(tipo_segmento , null);


    lhoradespegue.setText("Hora ");
    lhoradespegue.setBounds(new Rectangle(535, 101, 39, 22)); 
    horadespegue.setBounds(new Rectangle(576, 101, 63, 22)); 
    add(lhoradespegue, null);
    add(horadespegue , null);


    ldespegue.setText( "Despegue" );
    ldespegue.setBounds(new Rectangle(6, 101, 112, 22)); 
    despegue.setBounds(new Rectangle(117, 101, 231, 22)); 
    add(ldespegue, null);
    add(despegue , null);


    lduracionvuelo.setText("Duracion vuelo");
    lduracionvuelo.setBounds(new Rectangle(6, 127, 112, 22)); 
    duracionvuelo.setBounds(new Rectangle(117, 127, 143, 22)); 
    add(lduracionvuelo, null);
    add(duracionvuelo , null);


    larrivo.setText( "Arrivo" );
    larrivo.setBounds(new Rectangle(6, 152, 112, 22)); 
    arrivo.setBounds(new Rectangle(117, 152, 231, 22)); 
    add(larrivo, null);
    add(arrivo , null);


    lcarrier.setText("Aerolinea");
    lcarrier.setBounds(new Rectangle(6, 76, 112, 22)); 
    carrier.setBounds(new Rectangle(117, 76, 231, 22)); 
    add(lcarrier, null);
    add(carrier , null);


    lcodigosegmento.setText("Codigo segmento");
    lcodigosegmento.setBounds(new Rectangle(6, 181, 112, 22)); 
    codigosegmento.setBounds(new Rectangle(117, 181, 143, 22)); 
    add(lcodigosegmento, null);
    add(codigosegmento , null);


    lfechaarrivo.setText("Fecha");
    lfechaarrivo.setBounds(new Rectangle(349, 152, 39, 22)); 
    fechaarrivo.setBounds(new Rectangle(391, 152, 143, 22)); 
    add(lfechaarrivo, null);
    add(fechaarrivo , null);


    lestado.setText( "Estado" );
    lestado.setBounds(new Rectangle(263, 50, 73, 22)); 
    estado.setBounds(new Rectangle(337, 50, 143, 22)); 
    add(lestado, null);
    add(estado , null);


    lcodigopnr.setText("Codigo pnr");
    lcodigopnr.setBounds(new Rectangle(263, 25, 72, 22)); 
    codigopnr.setBounds(new Rectangle(337, 25, 143, 22)); 
    add(lcodigopnr, null);
    add(codigopnr , null);


    lcodigocomida.setText("Codigo comida");
    lcodigocomida.setBounds(new Rectangle(442, 180, 95, 22)); 
    codigocomida.setBounds(new Rectangle(537, 180, 53, 22)); 
    add(lcodigocomida, null);
    add(codigocomida , null);


    lclase.setText( "Clase" );
    lclase.setBounds(new Rectangle(6, 209, 112, 22)); 
    clase.setBounds(new Rectangle(117, 209, 143, 22)); 
    add(lclase, null);
    add(clase , null);


    ltipoequipo.setText("Tipo equipo");
    ltipoequipo.setBounds(new Rectangle(262, 208, 102, 22)); 
    tipoequipo.setBounds(new Rectangle(372, 208, 68, 22)); 
    add(ltipoequipo, null);
    add(tipoequipo , null);


    lnumerovuelo.setText("Numero vuelo");
    lnumerovuelo.setBounds(new Rectangle(6, 25, 112, 22)); 
    numerovuelo.setBounds(new Rectangle(117, 25, 143, 22)); 
    add(lnumerovuelo, null);
    add(numerovuelo , null);


    lfechadespegue.setText("Fecha ");
    lfechadespegue.setBounds(new Rectangle(350, 101, 39, 22)); 
    fechadespegue.setBounds(new Rectangle(392, 101, 143, 22)); 
    add(lfechadespegue, null);
    add(fechadespegue , null);
    

    chckbxEmitido.setBounds(117, 3, 102, 23);
    add(chckbxEmitido);
    
    tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    tabbedPane.setBounds(6, 242, 875, 162);
    add(tabbedPane);


  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( codigoentretenimiento, CHAR, REQ, "codigoentretenimiento" );
    AddItem( horaarrivo, CHAR, REQ, "horaarrivo" );
    AddItem( tipo_segmento, CHAR, REQ, "tipo_segmento" );
    AddItem( horadespegue, CHAR, REQ, "horadespegue" );
    AddItem( despegue, CHAR, REQ, "despegue" , new JControlWin() {
    	@Override
    	public JWins getRecords(boolean bOneRow) throws Exception {
    		return getDespegue(bOneRow);
    	}
    });
    
    AddItem( duracionvuelo, CHAR, REQ, "duracionvuelo" );
    AddItem( arrivo, CHAR, REQ, "arrivo" , new JControlWin() {
    	@Override
    	public JWins getRecords(boolean bOneRow) throws Exception {
    		return getArrivo(bOneRow);
    	}
    });
    AddItem( carrier, CHAR, REQ, "carrier" , new JControlWin() {
    	@Override
    	public JWins getRecords(boolean bOneRow) throws Exception {
    		return getAerolinea(bOneRow);
    	}
    });
    AddItem( codigosegmento, CHAR, REQ, "codigosegmento" );
    AddItem( fechaarrivo, CHAR, REQ, "fechaarrivo" );
    AddItem( estado, CHAR, REQ, "estado" );
    AddItem( codigopnr, CHAR, REQ, "codigopnr" );
    AddItem( codigocomida, CHAR, REQ, "codigocomida" );
    AddItem( clase, CHAR, REQ, "clase" );
    AddItem( tipoequipo, CHAR, REQ, "tipoequipo" );
    AddItem( segmento_ini, CHAR, REQ, "segmento_ini" );
    AddItem( numerovuelo, CHAR, REQ, "numerovuelo" );
    AddItem( fechadespegue, CHAR, REQ, "fechadespegue" );
    AddItem( interface_id, UINT, REQ, "interface_id" );
    AddItem( company, CHAR, REQ, "company" );
    AddItem( chckbxEmitido, OPT, "emitido" );
    
    AddItem( tabbedPane, 120);
  } 
  
  public JWins getDespegue(boolean one) throws Exception {
  	GuiAirports wins = new GuiAirports();
  	if (one) {
  		wins.getRecords().addFilter("code", getWin().GetcDato().getDespegue());
  	}
  	return wins;
  }
  public JWins getArrivo(boolean one) throws Exception {
  	GuiAirports wins = new GuiAirports();
  	if (one) {
  		wins.getRecords().addFilter("code", getWin().GetcDato().getArrivo());
  	}
  	return wins;
  }
  public JWins getAerolinea(boolean one) throws Exception {
  	GuiCarriers wins = new GuiCarriers();
  	if (one) {
  		wins.getRecords().addFilter("carrier", getWin().GetcDato().getCarrier());
  	}
  	return wins;
  }
}  //  @jve:decl-index=0:visual-constraint="10,10" 
