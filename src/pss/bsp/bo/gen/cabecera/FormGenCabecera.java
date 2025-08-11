package  pss.bsp.bo.gen.cabecera;

import java.awt.Dimension;
import java.awt.Rectangle;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;

public class FormGenCabecera extends JBaseForm {


private static final long serialVersionUID = 1245698643769L;

  /**
   * Propiedades de la Clase
   */
JPssLabel lcompany = new JPssLabel();
JPssEdit company = new JPssEdit  ();
JPssLabel lowner = new JPssLabel();
JPssEdit owner = new JPssEdit  ();
JPssLabel lidInterfaz = new JPssLabel();
JPssEdit idInterfaz = new JPssEdit  ();
JPssLabel lid_formato = new JPssLabel();
JPssEdit id_formato = new JPssEdit  ();


  /**
   * Constructor de la Clase
   */
  public FormGenCabecera() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiGenCabecera getWin() { return (GuiGenCabecera) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(357, 110+88));


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


    lid_formato.setText( "Id formato" );
    lid_formato.setBounds(new Rectangle(40, 44+81, 123, 22)); 
    id_formato.setBounds(new Rectangle(168, 44+81, 143, 22)); 
    add(lid_formato, null);
    add(id_formato , null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" );
    AddItem( owner, CHAR, REQ, "owner" );
    AddItem( idInterfaz, UINT, REQ, "idInterfaz" );
    AddItem( id_formato, CHAR, REQ, "id_formato" );

  } 
} 
