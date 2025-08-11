package pss.tourism.pnr;

import java.awt.Dimension;
import java.awt.Rectangle;

import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormPNRTax extends JBaseForm {


private static final long serialVersionUID = 1446642143959L;

  /**
   * Propiedades de la Clase
   */
JPssLabel lcodigomoneda = new JPssLabel();
JPssEdit codigomoneda = new JPssEdit  ();
JPssLabel lnumeroboleto = new JPssLabel();
JPssEdit numeroboleto = new JPssEdit  ();
JPssLabel lvirtual = new JPssLabel();
JPssEdit virtual = new JPssEdit  ();
JPssLabel limporte = new JPssLabel();
JPssEdit importe = new JPssEdit  ();
JPssLabel lsecuencia = new JPssLabel();
JPssEdit secuencia = new JPssEdit  ();
JPssLabel linterface_id = new JPssLabel();
JPssEdit interface_id = new JPssEdit  ();
JPssLabel lcodigoimpuesto = new JPssLabel();
JPssEdit codigoimpuesto = new JPssEdit  ();
JPssLabel lcodigopnr = new JPssLabel();
JPssEdit codigopnr = new JPssEdit  ();
JPssLabel lcompany = new JPssLabel();
JPssEdit company = new JPssEdit  ();


  /**
   * Constructor de la Clase
   */
  public FormPNRTax() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiPNRTax getWin() { return (GuiPNRTax) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(357, 110+198));


    lcodigomoneda.setText( "Codigomoneda" );
    lcodigomoneda.setBounds(new Rectangle(40, 44+0, 123, 22)); 
    codigomoneda.setBounds(new Rectangle(168, 44+0, 143, 22)); 
    add(lcodigomoneda, null);
    add(codigomoneda , null);


    lnumeroboleto.setText( "Numeroboleto" );
    lnumeroboleto.setBounds(new Rectangle(40, 44+27, 123, 22)); 
    numeroboleto.setBounds(new Rectangle(168, 44+27, 143, 22)); 
    add(lnumeroboleto, null);
    add(numeroboleto , null);


    lvirtual.setText( "Virtual" );
    lvirtual.setBounds(new Rectangle(40, 44+54, 123, 22)); 
    virtual.setBounds(new Rectangle(168, 44+54, 143, 22)); 
    add(lvirtual, null);
    add(virtual , null);


    limporte.setText( "Importe" );
    limporte.setBounds(new Rectangle(40, 44+81, 123, 22)); 
    importe.setBounds(new Rectangle(168, 44+81, 143, 22)); 
    add(limporte, null);
    add(importe , null);


    lsecuencia.setText( "Secuencia" );
    lsecuencia.setBounds(new Rectangle(40, 44+108, 123, 22)); 
    secuencia.setBounds(new Rectangle(168, 44+108, 143, 22)); 
    add(lsecuencia, null);
    add(secuencia , null);


    linterface_id.setText( "Interface id" );
    linterface_id.setBounds(new Rectangle(40, 44+135, 123, 22)); 
    interface_id.setBounds(new Rectangle(168, 44+135, 143, 22)); 
    add(linterface_id, null);
    add(interface_id , null);


    lcodigoimpuesto.setText( "Codigoimpuesto" );
    lcodigoimpuesto.setBounds(new Rectangle(40, 44+162, 123, 22)); 
    codigoimpuesto.setBounds(new Rectangle(168, 44+162, 143, 22)); 
    add(lcodigoimpuesto, null);
    add(codigoimpuesto , null);


    lcodigopnr.setText( "Codigopnr" );
    lcodigopnr.setBounds(new Rectangle(40, 44+189, 123, 22)); 
    codigopnr.setBounds(new Rectangle(168, 44+189, 143, 22)); 
    add(lcodigopnr, null);
    add(codigopnr , null);


    lcompany.setText( "Company" );
    lcompany.setBounds(new Rectangle(40, 44+216, 123, 22)); 
    company.setBounds(new Rectangle(168, 44+216, 143, 22)); 
    add(lcompany, null);
    add(company , null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( codigomoneda, CHAR, REQ, "codigomoneda" );
    AddItem( numeroboleto, CHAR, REQ, "numeroboleto" );
    AddItem( virtual, CHAR, REQ, "virtual" );
    AddItem( importe, CHAR, REQ, "importe" );
    AddItem( secuencia, UINT, REQ, "secuencia" );
    AddItem( interface_id, UINT, REQ, "interface_id" );
    AddItem( codigoimpuesto, CHAR, REQ, "codigoimpuesto" );
    AddItem( codigopnr, CHAR, REQ, "codigopnr" );
    AddItem( company, CHAR, REQ, "company" );

  } 
} 
