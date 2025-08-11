package  pss.bsp.interfaces.copa.cabecera;

import java.awt.Dimension;
import java.awt.Rectangle;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;

public class FormCopaCabecera extends JBaseForm {


private static final long serialVersionUID = 1245253623694L;

  JPssEdit company = new JPssEdit  ();
JPssEdit owner = new JPssEdit  ();
JPssLabel lidPDF = new JPssLabel();
JPssEdit idPDF = new JPssEdit  ();
JPssLabel ldescripcion = new JPssLabel();
JPssEdit descripcion = new JPssEdit  ();
JPssLabel lcodigo_postal = new JPssLabel();
JPssEdit codigo_postal = new JPssEdit  ();


  /**
   * Constructor de la Clase
   */
  public FormCopaCabecera() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiCopaCabecera getWin() { return (GuiCopaCabecera) getBaseWin(); }

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



    lcodigo_postal.setText( "Aerolinea" );
    lcodigo_postal.setBounds(new Rectangle(303-20, 54, 98, 22)); 
    codigo_postal.setBounds(new Rectangle(403-20, 54, 143, 22)); 
    add(lcodigo_postal, null);
    add(codigo_postal , null);


  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" ).setVisible(false);
    AddItem( owner, CHAR, REQ, "owner" ).setVisible(false);
    AddItem( idPDF, CHAR, REQ, "idPDF" );
    AddItem( descripcion, CHAR, REQ, "descripcion" );
    AddItem( codigo_postal, CHAR, REQ, "aerolinea" );

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
