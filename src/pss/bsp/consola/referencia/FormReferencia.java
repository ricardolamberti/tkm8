package  pss.bsp.consola.referencia;

import java.awt.Dimension;
import java.awt.Rectangle;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;

public class FormReferencia extends JBaseForm {


private static final long serialVersionUID = 1256842199400L;

  /**
   * Propiedades de la Clase
   */
JPssLabel lid = new JPssLabel();
JPssEdit id = new JPssEdit  ();
JPssLabel licono = new JPssLabel();
JPssEdit icono = new JPssEdit  ();
JPssLabel ldescripcion = new JPssLabel();
JPssEdit descripcion = new JPssEdit  ();


  /**
   * Constructor de la Clase
   */
  public FormReferencia() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiReferencia getWin() { return (GuiReferencia) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(357, 110+66));


    lid.setText( "Id" );
    lid.setBounds(new Rectangle(40, 44+0, 123, 22)); 
    id.setBounds(new Rectangle(168, 44+0, 143, 22)); 
    add(lid, null);
    add(id , null);


    licono.setText( "Icono" );
    licono.setBounds(new Rectangle(40, 44+27, 123, 22)); 
    icono.setBounds(new Rectangle(168, 44+27, 143, 22)); 
    add(licono, null);
    add(icono , null);


    ldescripcion.setText( "Descripcion" );
    ldescripcion.setBounds(new Rectangle(40, 44+54, 123, 22)); 
    descripcion.setBounds(new Rectangle(168, 44+54, 143, 22)); 
    add(ldescripcion, null);
    add(descripcion , null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( id, UINT, REQ, "id" );
    AddItem( icono, UINT, REQ, "icono" );
    AddItem( descripcion, CHAR, REQ, "descripcion" );

  } 
} 
