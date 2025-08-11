package  pss.bsp.publicity;

import java.awt.Dimension;
import java.awt.Rectangle;

import pss.common.security.BizUsuario;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;
import pss.core.ui.components.JPssLabel;
import pss.core.ui.components.JPssCalendarEdit;

public class FormPublicity extends JBaseForm {


private static final long serialVersionUID = 1245258187718L;

  JPssEdit company = new JPssEdit  ();
JPssEdit owner = new JPssEdit  ();
JPssEdit idInterfaz = new JPssEdit  ();
JPssEdit linea = new JPssEdit  ();
JPssLabel ltipo_ruta = new JPssLabel();
JPssEdit tipo_ruta = new JPssEdit  ();
JPssLabel laerolinea = new JPssLabel();
JPssEdit aerolinea = new JPssEdit  ();
JPssLabel lfecha = new JPssLabel();
JPssCalendarEdit fecha = new JPssCalendarEdit  ();

/**
   * Constructor de la Clase
   */
  public FormPublicity() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiPublicity getWin() { return (GuiPublicity) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    this.setLayout(null);
    this.setSize(new Dimension(547, 146));
  

 


    ltipo_ruta.setText("Campaña");
    ltipo_ruta.setBounds(new Rectangle(15, 29, 123, 22)); 
    tipo_ruta.setBounds(new Rectangle(143, 29, 143, 22)); 
    add(ltipo_ruta, null);
    add(tipo_ruta , null);


    laerolinea.setText("Segmento");
    laerolinea.setBounds(new Rectangle(15, 56, 123, 22)); 
    aerolinea.setBounds(new Rectangle(143, 56, 378, 22)); 
    add(laerolinea, null);
    add(aerolinea , null);


    lfecha.setText("Fecha");
    lfecha.setBounds(new Rectangle(15, 87, 123, 22)); 
    fecha.setBounds(new Rectangle(143, 87, 143, 22)); 
    add(lfecha, null);
    add(fecha , null);


    
    
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" ).SetValorDefault(BizUsuario.getUsr().getCompany());
    AddItem( owner, CHAR, REQ, "owner" ).SetValorDefault(BizUsuario.getUsr().GetUsuario());
    AddItem( idInterfaz, UINT, REQ, "id_interfaz" ).SetValorDefault(1);
    AddItem( linea, UINT, OPT, "linea" );
    AddItem( tipo_ruta, CHAR, REQ, "campana" );
    AddItem( aerolinea, CHAR, REQ, "segmento" );
    AddItem( fecha, DATE, REQ, "fecha" );

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
