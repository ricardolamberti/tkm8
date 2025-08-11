package  pss.bsp.bo.arg.cabecera;

import java.awt.Dimension;
import java.awt.Rectangle;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;

public class FormArgCabecera extends JBaseForm {


private static final long serialVersionUID = 1245254600107L;

  /**
   * Propiedades de la Clase
   */
JPssLabel lcompany = new JPssLabel();
JPssEdit company = new JPssEdit  ();
JPssLabel lowner = new JPssLabel();
JPssEdit owner = new JPssEdit  ();
JPssLabel lidInterfaz = new JPssLabel();
JPssEdit idInterfaz = new JPssEdit  ();
JPssLabel lfecha_desde = new JPssLabel();
JPssEdit fecha_desde = new JPssEdit  ();
JPssLabel lfecha_hasta = new JPssLabel();
JPssEdit fecha_hasta = new JPssEdit  ();
JPssLabel ldescripcion = new JPssLabel();
JPssEdit descripcion = new JPssEdit  ();


  /**
   * Constructor de la Clase
   */
  public FormArgCabecera() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiArgCabecera getWin() { return (GuiArgCabecera) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(357, 110+132));


    lcompany.setText( "Company" );
    lcompany.setBounds(new Rectangle(40, 44+0, 123, 22)); 
    company.setBounds(new Rectangle(168, 44+0, 143, 22)); 
    add(lcompany, null);
    add(company , null);


    lowner.setText( "Owner" );
    lowner.setBounds(new Rectangle(40, 44+27, 123, 22)); 
    owner.setBounds(new Rectangle(168, 44+27, 143, 22)); 
    add(lowner, null);
    add(owner , null);


    lidInterfaz.setText( "Idinterfaz" );
    lidInterfaz.setBounds(new Rectangle(40, 44+54, 123, 22)); 
    idInterfaz.setBounds(new Rectangle(168, 44+54, 143, 22)); 
    add(lidInterfaz, null);
    add(idInterfaz , null);


    lfecha_desde.setText( "Fecha desde" );
    lfecha_desde.setBounds(new Rectangle(40, 44+81, 123, 22)); 
    fecha_desde.setBounds(new Rectangle(168, 44+81, 143, 22)); 
    add(lfecha_desde, null);
    add(fecha_desde , null);


    lfecha_hasta.setText( "Fecha hasta" );
    lfecha_hasta.setBounds(new Rectangle(40, 44+108, 123, 22)); 
    fecha_hasta.setBounds(new Rectangle(168, 44+108, 143, 22)); 
    add(lfecha_hasta, null);
    add(fecha_hasta , null);


    ldescripcion.setText( "Descripcion" );
    ldescripcion.setBounds(new Rectangle(40, 44+135, 123, 22)); 
    descripcion.setBounds(new Rectangle(168, 44+135, 143, 22)); 
    add(ldescripcion, null);
    add(descripcion , null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" );
    AddItem( owner, CHAR, REQ, "owner" );
    AddItem( idInterfaz, UINT, REQ, "idInterfaz" );
    AddItem( fecha_desde, UINT, REQ, "fecha_desde" );
    AddItem( fecha_hasta, UINT, REQ, "fecha_hasta" );
    AddItem( descripcion, CHAR, REQ, "descripcion" );

  } 
} 
