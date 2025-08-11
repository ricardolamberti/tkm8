package  pss.bsp.pdf;

import java.awt.Dimension;
import java.awt.Rectangle;

import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.ui.components.JPssLabelFile;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormNewPDF extends JBaseForm {


private static final long serialVersionUID = 1245253394589L;

  JPssEdit company = new JPssEdit  ();
JPssEdit owner = new JPssEdit  ();
JPssEdit idPDF = new JPssEdit  ();
JPssLabel ldescripcion = new JPssLabel();
JPssEdit descripcion = new JPssEdit  ();
JPssLabel lpdfFilename = new JPssLabel();
JPssLabelFile pdfFilename = new JPssLabelFile  ();


  /**
   * Constructor de la Clase
   */
  public FormNewPDF() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }
 
  public GuiPDF getWin() { return (GuiPDF) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(512, 117));


    company.setBounds(new Rectangle(3, 8, 14, 22)); 
    add(company , null);


    owner.setBounds(new Rectangle(3, 35, 14, 22)); 
    add(owner , null);


    idPDF.setBounds(new Rectangle(3, 62, 14, 22)); 
    add(idPDF , null);


    ldescripcion.setText( "Descripcion" );
    ldescripcion.setBounds(new Rectangle(29, 28, 123, 22)); 
    descripcion.setBounds(new Rectangle(157, 28, 336, 22)); 
    add(ldescripcion, null);
    add(descripcion , null);


    lpdfFilename.setText( "Archivo PDF o ZIP:" );
    lpdfFilename.setBounds(new Rectangle(29, 54, 123, 22)); 
    pdfFilename.setBounds(new Rectangle(157, 54, 337, 22)); 
    add(lpdfFilename, null);
    add(pdfFilename , null);
    
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, OPT, "company" ).setVisible(false);
    AddItem( owner, CHAR, OPT, "owner" ).setVisible(false);
    AddItem( idPDF, CHAR, OPT, "idPDF" ).setVisible(false);
    AddItem( descripcion, CHAR, OPT, "descripcion" );
    AddItem( pdfFilename, CHAR, OPT, "pdffilename" );

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
