package  pss.bsp.pdf;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JTabbedPane;

import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormPDF extends JBaseForm {


private static final long serialVersionUID = 1245253394589L;

  JPssEdit company = new JPssEdit  ();
JPssEdit owner = new JPssEdit  ();
JPssLabel lidPDF = new JPssLabel();
JPssEdit idPDF = new JPssEdit  ();
JPssLabel ldescripcion = new JPssLabel();
JPssEdit descripcion = new JPssEdit  ();
JPssLabel lestado = new JPssLabel();
JPssEdit estado = new JPssEdit  ();
JPssLabel lfecha_desde = new JPssLabel();
JPssEdit fecha_desde = new JPssEdit  ();
JPssLabel lfecha_hasta = new JPssLabel();
JPssEdit fecha_hasta = new JPssEdit  ();

private JTabbedPane jTabbedPane = null;


  /**
   * Constructor de la Clase
   */
  public FormPDF() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiPDF getWin() { return (GuiPDF) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(583, 515));


    company.setBounds(new Rectangle(3, 29, 14, 22)); 
    add(company , null);


    owner.setBounds(new Rectangle(2, 2, 16, 22)); 
    add(owner , null);


    lidPDF.setText("Id.Liquidacion");
    lidPDF.setBounds(new Rectangle(29, 15, 123, 22)); 
    idPDF.setBounds(new Rectangle(157, 15, 143, 22)); 
    add(lidPDF, null);
    add(idPDF , null);


    ldescripcion.setText( "Descripcion" );
    ldescripcion.setBounds(new Rectangle(29, 41, 123, 22)); 
    descripcion.setBounds(new Rectangle(157, 41, 418, 22)); 
    add(ldescripcion, null);
    add(descripcion , null);


    lestado.setText( "Estado" );
    lestado.setBounds(new Rectangle(304, 15, 123, 22)); 
    estado.setBounds(new Rectangle(432, 15, 143, 22)); 
    add(lestado, null);
    add(estado , null);


    lfecha_desde.setText( "Fecha desde" );
    lfecha_desde.setBounds(new Rectangle(29, 68, 123, 22)); 
    fecha_desde.setBounds(new Rectangle(157, 68, 143, 22)); 
    add(lfecha_desde, null);
    add(fecha_desde , null);


    lfecha_hasta.setText( "Fecha hasta" );
    lfecha_hasta.setBounds(new Rectangle(304, 68, 123, 22)); 
    fecha_hasta.setBounds(new Rectangle(432, 68, 143, 22)); 
    add(lfecha_hasta, null);
    add(fecha_hasta , null);

    this.add(getJTabbedPane(), null);
    
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" ).setVisible(false);
    AddItem( owner, CHAR, REQ, "owner" ).setVisible(false);;
    AddItem( idPDF, CHAR, REQ, "idPDF" );
    AddItem( descripcion, CHAR, REQ, "descripcion" );
    AddItem( estado, CHAR, REQ, "estado" );
    AddItem( fecha_desde, UINT, REQ, "fecha_desde" );
    AddItem( fecha_hasta, UINT, REQ, "fecha_hasta" );
    AddItem( getJTabbedPane(),20);
    AddItem( getJTabbedPane(),25);
    AddItemForm( getJTabbedPane(),10);
 
  }

  /**
   * This method initializes jTabbedPane	
   * 	
   * @return javax.swing.JTabbedPane	
   */
  private JTabbedPane getJTabbedPane() {
  	if (jTabbedPane==null) {
  		jTabbedPane=new JTabbedPane();
  		jTabbedPane.setBounds(new Rectangle(26, 103, 547, 399));
  	}
  	return jTabbedPane;
  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
