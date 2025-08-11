package  pss.bsp.pdf.col.impuesto;

import java.awt.Dimension;
import java.awt.Rectangle;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;

public class FormColImpuesto extends JBaseForm {


private static final long serialVersionUID = 1245351577697L;

  JPssEdit company = new JPssEdit  ();
JPssEdit owner = new JPssEdit  ();
JPssEdit idPDF = new JPssEdit  ();
JPssEdit linea = new JPssEdit  ();
JPssLabel liso = new JPssLabel();
JPssEdit iso = new JPssEdit  ();
JPssLabel lcontado = new JPssLabel();
JPssEdit contado = new JPssEdit  ();
JPssLabel ltarjeta = new JPssLabel();
JPssEdit tarjeta = new JPssEdit  ();


  /**
   * Constructor de la Clase
   */
  public FormColImpuesto() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiColImpuesto getWin() { return (GuiColImpuesto) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(296, 127));


    company.setBounds(new Rectangle(3, 2, 8, 22)); 
    add(company , null);


    owner.setBounds(new Rectangle(3, 29, 8, 22)); 
    add(owner , null);


    idPDF.setBounds(new Rectangle(3, 56, 8, 22)); 
    add(idPDF , null);


    linea.setBounds(new Rectangle(3, 83, 8, 22)); 
    add(linea , null);


    liso.setText( "Iso" );
    liso.setBounds(new Rectangle(10, 12, 123, 22)); 
    iso.setBounds(new Rectangle(138, 12, 143, 22)); 
    add(liso, null);
    add(iso , null);


    lcontado.setText( "Contado" );
    lcontado.setBounds(new Rectangle(10, 39, 123, 22)); 
    contado.setBounds(new Rectangle(138, 39, 143, 22)); 
    add(lcontado, null);
    add(contado , null);


    ltarjeta.setText( "Tarjeta" );
    ltarjeta.setBounds(new Rectangle(10, 66, 123, 22)); 
    tarjeta.setBounds(new Rectangle(138, 66, 143, 22)); 
    add(ltarjeta, null);
    add(tarjeta , null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" ).setVisible(false);
    AddItem( owner, CHAR, REQ, "owner" ).setVisible(false);
    AddItem( idPDF, CHAR, REQ, "idPDF" ).setVisible(false);
    AddItem( linea, UINT, REQ, "linea" ).setVisible(false);
    AddItem( iso, CHAR, REQ, "iso" );
    AddItem( contado, UFLOAT, REQ, "contado" );
    AddItem( tarjeta, UFLOAT, REQ, "tarjeta" );

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
