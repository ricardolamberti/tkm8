package  pss.bsp.bo.gen.detalle;

import java.awt.Dimension;
import java.awt.Rectangle;

import pss.bsp.bo.gen.cabecera.BizGenCabecera;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormGenDetalle extends JBaseForm {


private static final long serialVersionUID = 1245698750438L;

  JPssEdit company = new JPssEdit  ();
JPssEdit owner = new JPssEdit  ();
JPssEdit idInterfaz = new JPssEdit  ();
JPssEdit linea = new JPssEdit  ();
JPssLabel ld1 = new JPssLabel();
JPssEdit d1 = new JPssEdit  ();
JPssLabel ld2 = new JPssLabel();
JPssEdit d2 = new JPssEdit  ();
JPssLabel ld3 = new JPssLabel();
JPssEdit d3 = new JPssEdit  ();
JPssLabel ld4 = new JPssLabel();
JPssEdit d4 = new JPssEdit  ();
JPssLabel ld5 = new JPssLabel();
JPssEdit d5 = new JPssEdit  ();
JPssLabel ld6 = new JPssLabel();
JPssEdit d6 = new JPssEdit  ();
JPssLabel ld7 = new JPssLabel();
JPssEdit d7 = new JPssEdit  ();
JPssLabel ld8 = new JPssLabel();
JPssEdit d8 = new JPssEdit  ();
JPssLabel ld9 = new JPssLabel();
JPssEdit d9 = new JPssEdit  ();
JPssLabel ld10 = new JPssLabel();
JPssEdit d10 = new JPssEdit  ();
JPssLabel ld11 = new JPssLabel();
JPssEdit d11 = new JPssEdit  ();
JPssLabel ld12 = new JPssLabel();
JPssEdit d12 = new JPssEdit  ();
JPssLabel ld13 = new JPssLabel();
JPssEdit d13 = new JPssEdit  ();
JPssLabel ld14 = new JPssLabel();
JPssEdit d14 = new JPssEdit  ();
JPssLabel ld15 = new JPssLabel();
JPssEdit d15 = new JPssEdit  ();
JPssLabel ld16 = new JPssLabel();
JPssEdit d16 = new JPssEdit  ();
JPssLabel ld17 = new JPssLabel();
JPssEdit d17 = new JPssEdit  ();
JPssLabel ld18 = new JPssLabel();
JPssEdit d18 = new JPssEdit  ();
JPssLabel ld19 = new JPssLabel();
JPssEdit d19 = new JPssEdit  ();
JPssLabel ld20 = new JPssLabel();
JPssEdit d20 = new JPssEdit  ();


  /**
   * Constructor de la Clase
   */
  public FormGenDetalle() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiGenDetalle getWin() { return (GuiGenDetalle) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(572, 283));


    company.setBounds(new Rectangle(6, 7, 8, 22)); 
    add(company , null);


    owner.setBounds(new Rectangle(5, 35, 8, 22)); 
    add(owner , null);


    idInterfaz.setBounds(new Rectangle(6, 61, 8, 22)); 
    add(idInterfaz , null);


    linea.setBounds(new Rectangle(6, 88, 8, 22)); 
    add(linea , null);

    ld1.setBounds(new Rectangle(19, 6, 123, 22)); 
    d1.setBounds(new Rectangle(147, 6, 143, 22)); 
    add(ld1, null);
    add(d1 , null);


    ld2.setBounds(new Rectangle(19, 33, 123, 22)); 
    d2.setBounds(new Rectangle(147, 33, 143, 22)); 
    add(ld2, null);
    add(d2 , null);


    ld3.setBounds(new Rectangle(19, 60, 123, 22)); 
    d3.setBounds(new Rectangle(147, 60, 143, 22)); 
    add(ld3, null);
    add(d3 , null);


    ld4.setBounds(new Rectangle(19, 87, 123, 22)); 
    d4.setBounds(new Rectangle(147, 87, 143, 22)); 
    add(ld4, null);
    add(d4 , null);


    ld5.setBounds(new Rectangle(19, 114, 123, 22)); 
    d5.setBounds(new Rectangle(147, 114, 143, 22)); 
    add(ld5, null);
    add(d5 , null);


    ld6.setBounds(new Rectangle(19, 141, 123, 22)); 
    d6.setBounds(new Rectangle(147, 141, 143, 22)); 
    add(ld6, null);
    add(d6 , null);


    ld7.setBounds(new Rectangle(19, 168, 123, 22)); 
    d7.setBounds(new Rectangle(147, 168, 143, 22)); 
    add(ld7, null);
    add(d7 , null);


    ld8.setBounds(new Rectangle(19, 195, 123, 22)); 
    d8.setBounds(new Rectangle(147, 195, 143, 22)); 
    add(ld8, null);
    add(d8 , null);


    ld9.setBounds(new Rectangle(19, 222, 123, 22)); 
    d9.setBounds(new Rectangle(147, 222, 143, 22)); 
    add(ld9, null);
    add(d9 , null);


    ld10.setBounds(new Rectangle(19, 249, 123, 22)); 
    d10.setBounds(new Rectangle(147, 249, 143, 22)); 
    add(ld10, null);
    add(d10 , null);


    ld11.setBounds(new Rectangle(294, 7, 123, 22)); 
    d11.setBounds(new Rectangle(422, 7, 143, 22)); 
    add(ld11, null);
    add(d11 , null);


    ld12.setBounds(new Rectangle(294, 34, 123, 22)); 
    d12.setBounds(new Rectangle(422, 34, 143, 22)); 
    add(ld12, null);
    add(d12 , null);


    ld13.setBounds(new Rectangle(294, 61, 123, 22)); 
    d13.setBounds(new Rectangle(422, 61, 143, 22)); 
    add(ld13, null);
    add(d13 , null);


    ld14.setBounds(new Rectangle(294, 88, 123, 22)); 
    d14.setBounds(new Rectangle(422, 88, 143, 22)); 
    add(ld14, null);
    add(d14 , null);


    ld15.setBounds(new Rectangle(294, 115, 123, 22)); 
    d15.setBounds(new Rectangle(422, 115, 143, 22)); 
    add(ld15, null);
    add(d15 , null);


    ld16.setBounds(new Rectangle(294, 142, 123, 22)); 
    d16.setBounds(new Rectangle(422, 142, 143, 22)); 
    add(ld16, null);
    add(d16 , null);


    ld17.setBounds(new Rectangle(294, 169, 123, 22)); 
    d17.setBounds(new Rectangle(422, 169, 143, 22)); 
    add(ld17, null);
    add(d17 , null);


    ld18.setBounds(new Rectangle(294, 196, 123, 22)); 
    d18.setBounds(new Rectangle(422, 196, 143, 22)); 
    add(ld18, null);
    add(d18 , null);


    ld19.setBounds(new Rectangle(294, 223, 123, 22)); 
    d19.setBounds(new Rectangle(422, 223, 143, 22)); 
    add(ld19, null);
    add(d19 , null);


    ld20.setBounds(new Rectangle(294, 250, 123, 22)); 
    d20.setBounds(new Rectangle(422, 250, 143, 22)); 
    add(ld20, null);
    add(d20 , null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" ).setVisible(false);
    AddItem( owner, CHAR, REQ, "owner" ).setVisible(false);
    AddItem( idInterfaz, UINT, REQ, "idInterfaz" ).setVisible(false);
    AddItem( linea, UINT, REQ, "linea" ).setVisible(false);
    AddItem( d1, CHAR, REQ, "d1" );
    AddItem( d2, CHAR, REQ, "d2" );
    AddItem( d3, CHAR, REQ, "d3" );
    AddItem( d4, CHAR, REQ, "d4" );
    AddItem( d5, CHAR, REQ, "d5" );
    AddItem( d6, CHAR, REQ, "d6" );
    AddItem( d7, CHAR, REQ, "d7" );
    AddItem( d8, CHAR, REQ, "d8" );
    AddItem( d9, CHAR, REQ, "d9" );
    AddItem( d10, CHAR, REQ, "d10" );
    AddItem( d11, CHAR, REQ, "d11" );
    AddItem( d12, CHAR, REQ, "d12" );
    AddItem( d13, CHAR, REQ, "d13" );
    AddItem( d14, CHAR, REQ, "d14" );
    AddItem( d15, CHAR, REQ, "d15" );
    AddItem( d16, CHAR, REQ, "d16" );
    AddItem( d17, CHAR, REQ, "d17" );
    AddItem( d18, CHAR, REQ, "d18" );
    AddItem( d19, CHAR, REQ, "d19" );
    AddItem( d20, CHAR, REQ, "d20" );
    
    BizGenCabecera cabecera = getWin().GetcDato().getHeader();

    ld1.setText( cabecera.getTitulo(1) );
    ld2.setText( cabecera.getTitulo(2) );
    ld3.setText( cabecera.getTitulo(3) );
    ld4.setText( cabecera.getTitulo(4) );
    ld5.setText( cabecera.getTitulo(5) );
    ld6.setText( cabecera.getTitulo(6) );
    ld7.setText( cabecera.getTitulo(7) );
    ld8.setText( cabecera.getTitulo(8) );
    ld9.setText( cabecera.getTitulo(9) );
    ld10.setText( cabecera.getTitulo(10) );
    ld11.setText( cabecera.getTitulo(11) );
    ld12.setText( cabecera.getTitulo(12) );
    ld13.setText( cabecera.getTitulo(13) );
    ld14.setText( cabecera.getTitulo(14) );
    ld15.setText( cabecera.getTitulo(15) );
    ld16.setText( cabecera.getTitulo(16) );
    ld17.setText( cabecera.getTitulo(17) );
    ld18.setText(cabecera.getTitulo(18) );
    ld19.setText( cabecera.getTitulo(19) );
    ld20.setText( cabecera.getTitulo(20) );

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
