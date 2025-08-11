package  pss.bsp.pdf.gua.cabecera;

import java.awt.Dimension;
import java.awt.Rectangle;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;

public class FormGuaCabecera extends JBaseForm {


private static final long serialVersionUID = 1245253623694L;

  JPssEdit company = new JPssEdit  ();
JPssEdit owner = new JPssEdit  ();
JPssLabel lidPDF = new JPssLabel();
JPssEdit idPDF = new JPssEdit  ();
JPssLabel ldescripcion = new JPssLabel();
JPssEdit descripcion = new JPssEdit  ();
JPssLabel ldireccion = new JPssLabel();
JPssEdit direccion = new JPssEdit  ();
JPssLabel lcodigo_postal = new JPssLabel();
JPssEdit codigo_postal = new JPssEdit  ();
JPssLabel lIATA = new JPssLabel();
JPssEdit IATA = new JPssEdit  ();
JPssLabel lCIF = new JPssLabel();
JPssEdit CIF = new JPssEdit  ();
JPssLabel llocalidad = new JPssLabel();
JPssEdit localidad = new JPssEdit  ();
JPssLabel lmoneda = new JPssLabel();
JPssEdit moneda = new JPssEdit  ();


  /**
   * Constructor de la Clase
   */
  public FormGuaCabecera() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiGuaCabecera getWin() { return (GuiGuaCabecera) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(549, 150));


    company.setBounds(new Rectangle(2, 5, 16, 22)); 
    add(company , null);


    owner.setBounds(new Rectangle(2, 31, 20, 22)); 
    add(owner , null);


    lidPDF.setText("ID Liquidacion");
    lidPDF.setBounds(new Rectangle(30-20, 5, 123, 22)); 
    idPDF.setBounds(new Rectangle(158-20, 5, 143, 22)); 
    add(lidPDF, null);
    add(idPDF , null);


    ldescripcion.setText( "Descripcion" );
    ldescripcion.setBounds(new Rectangle(30-20, 29, 123, 22)); 
    descripcion.setBounds(new Rectangle(158-20, 29, 387, 22)); 
    add(ldescripcion, null);
    add(descripcion , null);


    ldireccion.setText( "Direccion" );
    ldireccion.setBounds(new Rectangle(30-20, 54, 123, 22)); 
    direccion.setBounds(new Rectangle(158-20, 54, 143, 22)); 
    add(ldireccion, null);
    add(direccion , null);


    lcodigo_postal.setText( "Codigo postal" );
    lcodigo_postal.setBounds(new Rectangle(303-20, 54, 98, 22)); 
    codigo_postal.setBounds(new Rectangle(403-20, 54, 143, 22)); 
    add(lcodigo_postal, null);
    add(codigo_postal , null);


    lIATA.setText("IATA");
    lIATA.setBounds(new Rectangle(303-20, 5, 98, 22)); 
    IATA.setBounds(new Rectangle(403-20, 5, 143, 22)); 
    add(lIATA, null);
    add(IATA , null);


    lCIF.setText("Clave Fiscal");
    lCIF.setBounds(new Rectangle(303-20, 80, 98, 22)); 
    CIF.setBounds(new Rectangle(403-20, 80, 143, 22)); 
    add(lCIF, null);
    add(CIF , null);


    llocalidad.setText( "Localidad" );
    llocalidad.setBounds(new Rectangle(30-20, 80, 123, 22)); 
    localidad.setBounds(new Rectangle(158-20, 80, 143, 22)); 
    add(llocalidad, null);
    add(localidad , null);


    lmoneda.setText( "Moneda" );
    lmoneda.setBounds(new Rectangle(30-20, 106, 123, 22)); 
    moneda.setBounds(new Rectangle(158-20, 106, 143, 22)); 
    add(lmoneda, null);
    add(moneda , null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" ).setVisible(false);
    AddItem( owner, CHAR, REQ, "owner" ).setVisible(false);
    AddItem( idPDF, CHAR, REQ, "idPDF" );
    AddItem( descripcion, CHAR, REQ, "descripcion" );
    AddItem( direccion, CHAR, REQ, "direccion" );
    AddItem( codigo_postal, CHAR, REQ, "codigo_postal" );
    AddItem( IATA, CHAR, REQ, "IATA" );
    AddItem( CIF, CHAR, REQ, "CIF" );
    AddItem( localidad, CHAR, REQ, "localidad" );
    AddItem( moneda, CHAR, REQ, "moneda" );

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
