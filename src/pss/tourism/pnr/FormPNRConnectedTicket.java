package pss.tourism.pnr;

import java.awt.Dimension;
import java.awt.Rectangle;

import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormPNRConnectedTicket extends JBaseForm {


private static final long serialVersionUID = 1446642225174L;

  /**
   * Propiedades de la Clase
   */
JPssLabel lnumeroboleto = new JPssLabel();
JPssEdit numeroboleto = new JPssEdit  ();
JPssLabel lnumeroboletoconectado = new JPssLabel();
JPssEdit numeroboletoconectado = new JPssEdit  ();
JPssLabel lcompany = new JPssLabel();
JPssEdit company = new JPssEdit  ();


  /**
   * Constructor de la Clase
   */
  public FormPNRConnectedTicket() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiPNRConnectedTicket getWin() { return (GuiPNRConnectedTicket) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(357, 110+66));


    lnumeroboleto.setText( "Numeroboleto" );
    lnumeroboleto.setBounds(new Rectangle(40, 44+0, 123, 22)); 
    numeroboleto.setBounds(new Rectangle(168, 44+0, 143, 22)); 
    add(lnumeroboleto, null);
    add(numeroboleto , null);


    lnumeroboletoconectado.setText( "Numeroboletoconectado" );
    lnumeroboletoconectado.setBounds(new Rectangle(40, 44+27, 123, 22)); 
    numeroboletoconectado.setBounds(new Rectangle(168, 44+27, 143, 22)); 
    add(lnumeroboletoconectado, null);
    add(numeroboletoconectado , null);


    lcompany.setText( "Company" );
    lcompany.setBounds(new Rectangle(40, 44+54, 123, 22)); 
    company.setBounds(new Rectangle(168, 44+54, 143, 22)); 
    add(lcompany, null);
    add(company , null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( numeroboleto, CHAR, REQ, "numeroboleto" );
    AddItem( numeroboletoconectado, CHAR, REQ, "numeroboletoconectado" );
    AddItem( company, CHAR, REQ, "company" );

  } 
} 
