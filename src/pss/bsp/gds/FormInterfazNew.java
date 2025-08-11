package  pss.bsp.gds;

import java.awt.Dimension;
import java.awt.Rectangle;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;

public class FormInterfazNew extends JBaseForm {


private static final long serialVersionUID = 1448371202115L;

  /**
   * Propiedades de la Clase
   */
JPssLabel llastupdate = new JPssLabel();
JPssEdit lastupdate = new JPssEdit  ();
JPssLabel lcompany = new JPssLabel();
JPssEdit company = new JPssEdit  ();


  /**
   * Constructor de la Clase
   */
  public FormInterfazNew() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiInterfazNew getWin() { return (GuiInterfazNew) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(357, 110+44));


    llastupdate.setText( "Lastupdate" );
    llastupdate.setBounds(new Rectangle(40, 44+0, 123, 22)); 
    lastupdate.setBounds(new Rectangle(168, 44+0, 143, 22)); 
    add(llastupdate, null);
    add(lastupdate , null);


    lcompany.setText( "Company" );
    lcompany.setBounds(new Rectangle(40, 44+27, 123, 22)); 
    company.setBounds(new Rectangle(168, 44+27, 143, 22)); 
    add(lcompany, null);
    add(company , null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( lastupdate, UINT, REQ, "lastupdate" );
    AddItem( company, CHAR, REQ, "company" );

  } 
} 
