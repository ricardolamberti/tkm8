package  pss.bsp.bo.arg.detalle;

import java.awt.Dimension;
import java.awt.Rectangle;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;

public class FormArgDetalle extends JBaseForm {


private static final long serialVersionUID = 1245258187718L;

  /**
   * Propiedades de la Clase
   */
JPssLabel lcompany = new JPssLabel();
JPssEdit company = new JPssEdit  ();
JPssLabel lowner = new JPssLabel();
JPssEdit owner = new JPssEdit  ();
JPssLabel lidInterfaz = new JPssLabel();
JPssEdit idInterfaz = new JPssEdit  ();
JPssLabel llinea = new JPssLabel();
JPssEdit linea = new JPssEdit  ();
JPssLabel ltipo_ruta = new JPssLabel();
JPssEdit tipo_ruta = new JPssEdit  ();
JPssLabel laerolinea = new JPssLabel();
JPssEdit aerolinea = new JPssEdit  ();
JPssLabel loperacion = new JPssLabel();
JPssEdit operacion = new JPssEdit  ();
JPssLabel lboleto = new JPssLabel();
JPssEdit boleto = new JPssEdit  ();
JPssLabel lfecha = new JPssLabel();
JPssEdit fecha = new JPssEdit  ();
JPssLabel ltarifa = new JPssLabel();
JPssEdit tarifa = new JPssEdit  ();
JPssLabel lcontado = new JPssLabel();
JPssEdit contado = new JPssEdit  ();
JPssLabel ltarjeta = new JPssLabel();
JPssEdit tarjeta = new JPssEdit  ();
JPssLabel lbase_imponible = new JPssLabel();
JPssEdit base_imponible = new JPssEdit  ();
JPssLabel limpuesto1 = new JPssLabel();
JPssEdit impuesto1 = new JPssEdit  ();
JPssLabel limpuesto2 = new JPssLabel();
JPssEdit impuesto2 = new JPssEdit  ();
JPssLabel lcomision = new JPssLabel();
JPssEdit comision = new JPssEdit  ();
JPssLabel limp_sobre_com = new JPssLabel();
JPssEdit imp_sobre_com = new JPssEdit  ();
JPssLabel lcomision_over = new JPssLabel();
JPssEdit comision_over = new JPssEdit  ();
JPssLabel lobservaciones = new JPssLabel();
JPssEdit observaciones = new JPssEdit  ();
JPssLabel lnumero_tarjeta = new JPssLabel();
JPssEdit numero_tarjeta = new JPssEdit  ();
JPssLabel ltipo_tarjeta = new JPssLabel();
JPssEdit tipo_tarjeta = new JPssEdit  ();


  /**
   * Constructor de la Clase
   */
  public FormArgDetalle() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiArgDetalle getWin() { return (GuiArgDetalle) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(357, 619));


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


    llinea.setText( "Linea" );
    llinea.setBounds(new Rectangle(40, 44+81, 123, 22)); 
    linea.setBounds(new Rectangle(168, 44+81, 143, 22)); 
    add(llinea, null);
    add(linea , null);


    ltipo_ruta.setText( "Tipo ruta" );
    ltipo_ruta.setBounds(new Rectangle(40, 44+108, 123, 22)); 
    tipo_ruta.setBounds(new Rectangle(168, 44+108, 143, 22)); 
    add(ltipo_ruta, null);
    add(tipo_ruta , null);


    laerolinea.setText( "Aerolinea" );
    laerolinea.setBounds(new Rectangle(40, 44+135, 123, 22)); 
    aerolinea.setBounds(new Rectangle(168, 44+135, 143, 22)); 
    add(laerolinea, null);
    add(aerolinea , null);


    loperacion.setText( "Operacion" );
    loperacion.setBounds(new Rectangle(40, 44+162, 123, 22)); 
    operacion.setBounds(new Rectangle(168, 44+162, 143, 22)); 
    add(loperacion, null);
    add(operacion , null);


    lboleto.setText( "Boleto" );
    lboleto.setBounds(new Rectangle(40, 44+189, 123, 22)); 
    boleto.setBounds(new Rectangle(168, 44+189, 143, 22)); 
    add(lboleto, null);
    add(boleto , null);


    lfecha.setText( "Fecha" );
    lfecha.setBounds(new Rectangle(40, 44+216, 123, 22)); 
    fecha.setBounds(new Rectangle(168, 44+216, 143, 22)); 
    add(lfecha, null);
    add(fecha , null);


    ltarifa.setText( "Tarifa" );
    ltarifa.setBounds(new Rectangle(40, 44+243, 123, 22)); 
    tarifa.setBounds(new Rectangle(168, 44+243, 143, 22)); 
    add(ltarifa, null);
    add(tarifa , null);


    lcontado.setText( "Contado" );
    lcontado.setBounds(new Rectangle(40, 44+270, 123, 22)); 
    contado.setBounds(new Rectangle(168, 44+270, 143, 22)); 
    add(lcontado, null);
    add(contado , null);


    ltarjeta.setText( "Tarjeta" );
    ltarjeta.setBounds(new Rectangle(40, 44+297, 123, 22)); 
    tarjeta.setBounds(new Rectangle(168, 44+297, 143, 22)); 
    add(ltarjeta, null);
    add(tarjeta , null);


    lbase_imponible.setText( "Base imponible" );
    lbase_imponible.setBounds(new Rectangle(40, 44+324, 123, 22)); 
    base_imponible.setBounds(new Rectangle(168, 44+324, 143, 22)); 
    add(lbase_imponible, null);
    add(base_imponible , null);


    limpuesto1.setText( "Impuesto1" );
    limpuesto1.setBounds(new Rectangle(40, 44+351, 123, 22)); 
    impuesto1.setBounds(new Rectangle(168, 44+351, 143, 22)); 
    add(limpuesto1, null);
    add(impuesto1 , null);


    limpuesto2.setText( "Impuesto2" );
    limpuesto2.setBounds(new Rectangle(40, 44+378, 123, 22)); 
    impuesto2.setBounds(new Rectangle(168, 44+378, 143, 22)); 
    add(limpuesto2, null);
    add(impuesto2 , null);


    lcomision.setText( "Comision" );
    lcomision.setBounds(new Rectangle(40, 44+405, 123, 22)); 
    comision.setBounds(new Rectangle(168, 44+405, 143, 22)); 
    add(lcomision, null);
    add(comision , null);


    limp_sobre_com.setText( "Imp sobre com" );
    limp_sobre_com.setBounds(new Rectangle(40, 44+432, 123, 22)); 
    imp_sobre_com.setBounds(new Rectangle(168, 44+432, 143, 22)); 
    add(limp_sobre_com, null);
    add(imp_sobre_com , null);


    lcomision_over.setText( "Comision over" );
    lcomision_over.setBounds(new Rectangle(40, 44+459, 123, 22)); 
    comision_over.setBounds(new Rectangle(168, 44+459, 143, 22)); 
    add(lcomision_over, null);
    add(comision_over , null);


    lobservaciones.setText( "Observaciones" );
    lobservaciones.setBounds(new Rectangle(40, 44+486, 123, 22)); 
    observaciones.setBounds(new Rectangle(168, 44+486, 143, 22)); 
    add(lobservaciones, null);
    add(observaciones , null);


    lnumero_tarjeta.setText( "Numero tarjeta" );
    lnumero_tarjeta.setBounds(new Rectangle(40, 44+513, 123, 22)); 
    numero_tarjeta.setBounds(new Rectangle(168, 44+513, 143, 22)); 
    add(lnumero_tarjeta, null);
    add(numero_tarjeta , null);


    ltipo_tarjeta.setText( "Tipo tarjeta" );
    ltipo_tarjeta.setBounds(new Rectangle(40, 44+540, 123, 22)); 
    tipo_tarjeta.setBounds(new Rectangle(168, 44+540, 143, 22)); 
    add(ltipo_tarjeta, null);
    add(tipo_tarjeta , null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" );
    AddItem( owner, CHAR, REQ, "owner" );
    AddItem( idInterfaz, UINT, REQ, "idInterfaz" );
    AddItem( linea, UINT, REQ, "linea" );
    AddItem( tipo_ruta, CHAR, REQ, "tipo_ruta" );
    AddItem( aerolinea, CHAR, REQ, "aerolinea" );
    AddItem( operacion, CHAR, REQ, "operacion" );
    AddItem( boleto, CHAR, REQ, "boleto" );
    AddItem( fecha, UINT, REQ, "fecha" );
    AddItem( tarifa, UFLOAT, REQ, "tarifa" );
    AddItem( contado, UFLOAT, REQ, "contado" );
    AddItem( tarjeta, UFLOAT, REQ, "tarjeta" );
    AddItem( base_imponible, UFLOAT, REQ, "base_imponible" );
    AddItem( impuesto1, UFLOAT, REQ, "impuesto1" );
    AddItem( impuesto2, UFLOAT, REQ, "impuesto2" );
    AddItem( comision, UFLOAT, REQ, "comision" );
    AddItem( imp_sobre_com, UFLOAT, REQ, "imp_sobre_com" );
    AddItem( comision_over, UFLOAT, REQ, "comision_over" );
    AddItem( observaciones, CHAR, REQ, "observaciones" );
    AddItem( numero_tarjeta, CHAR, REQ, "numero_tarjeta" );
    AddItem( tipo_tarjeta, CHAR, REQ, "tipo_tarjeta" );

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
