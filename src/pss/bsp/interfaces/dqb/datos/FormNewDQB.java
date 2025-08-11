package  pss.bsp.interfaces.dqb.datos;

import java.awt.Dimension;
import java.awt.Rectangle;

import pss.core.ui.components.JPssCalendarEdit;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.ui.components.JPssLabelFile;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormNewDQB extends JBaseForm {


private static final long serialVersionUID = 1245253394589L;

  JPssEdit company = new JPssEdit  ();
JPssEdit owner = new JPssEdit  ();
JPssEdit idPDF = new JPssEdit  ();
JPssLabel ldescripcion = new JPssLabel();
JPssEdit descripcion = new JPssEdit  ();
JPssLabel lpdfFilename = new JPssLabel();
JPssLabelFile pdfFilename = new JPssLabelFile  ();
JPssLabel lfecha_desde = new JPssLabel();
JPssCalendarEdit fecha_desde = new JPssCalendarEdit  ();
JPssLabel lfecha_hasta = new JPssLabel();
JPssCalendarEdit fecha_hasta = new JPssCalendarEdit  ();


  /**
   * Constructor de la Clase
   */
  public FormNewDQB() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }
 
  public GuiDQB getWin() { return (GuiDQB) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(624, 306));


    company.setBounds(new Rectangle(3, 8, 14, 22)); 
    add(company , null);


    owner.setBounds(new Rectangle(3, 35, 14, 22)); 
    add(owner , null);


    idPDF.setBounds(new Rectangle(3, 62, 14, 22)); 
    add(idPDF , null);


    ldescripcion.setText( "Descripcion" );
    ldescripcion.setBounds(new Rectangle(30, 61, 123, 22)); 
    descripcion.setBounds(new Rectangle(157, 61, 336, 22)); 
    add(ldescripcion, null);
    add(descripcion , null);
    lfecha_desde.setText( "Fecha desde" );
    lfecha_desde.setBounds(new Rectangle(29, 8, 123, 22)); 
    fecha_desde.setBounds(new Rectangle(157, 8, 143, 22)); 
    add(lfecha_desde, null);
    add(fecha_desde , null);


    lfecha_hasta.setText( "Fecha hasta" );
    lfecha_hasta.setBounds(new Rectangle(29, 35, 123, 22)); 
    fecha_hasta.setBounds(new Rectangle(157, 35, 143, 22)); 
    add(lfecha_hasta, null);
    add(fecha_hasta , null);


    lpdfFilename.setText( "Archivo Interfaz" );
    lpdfFilename.setBounds(new Rectangle(30, 87, 123, 22)); 
    pdfFilename.setBounds(new Rectangle(157, 87, 337, 22)); 
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
    AddItem( fecha_desde, DATE, REQ, "fecha_desde" );
    AddItem( fecha_hasta, DATE, REQ, "fecha_hasta" );
    AddItem( descripcion, CHAR, OPT, "descripcion" );
    AddItem( pdfFilename, CHAR, OPT, "pdffilename" );

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
