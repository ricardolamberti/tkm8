package pss.bsp.consolidador.consolidacion.detalle;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComboBox;

import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;

public class FormConsolidacion extends JBaseForm {


private static final long serialVersionUID = 1246906123540L;

  JPssEdit company = new JPssEdit  ();
JPssEdit owner = new JPssEdit  ();
JPssLabel lidpdf = new JPssLabel();
JPssEdit idpdf = new JPssEdit  ();
JPssEdit linea = new JPssEdit  ();
JPssLabel lstatus = new JPssLabel();
JComboBox status = new JComboBox  ();
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
JPssLabel ld21 = new JPssLabel();
JPssEdit d21 = new JPssEdit  ();

JPssLabel lobservaciones = new JPssLabel();
JPssEdit observaciones = new JPssEdit  ();


  /**
   * Constructor de la Clase
   */
  public FormConsolidacion() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiConsolidacion getWin() { return (GuiConsolidacion) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    this.setSize(new Dimension(577, 421));


    company.setBounds(new Rectangle(2, 4, 10, 22)); 
    add(company , null);


    owner.setBounds(new Rectangle(2, 31, 10, 22)); 
    add(owner , null);


    lidpdf.setText("Id BSP");
    lidpdf.setBounds(new Rectangle(15, 7, 123, 22)); 
    idpdf.setBounds(new Rectangle(143, 7, 143, 22)); 
    add(lidpdf, null);
    add(idpdf , null);


    linea.setBounds(new Rectangle(1, 55, 11, 22)); 
    add(linea , null);


    lstatus.setText( "Status" );
    lstatus.setBounds(new Rectangle(292, 7, 123, 22)); 
    status.setBounds(new Rectangle(420, 7, 143, 22)); 
    add(lstatus, null);
    add(status , null);


    ld1.setText("Boletos");
    ld1.setBounds(new Rectangle(15, 35, 123, 22)); 
    d1.setBounds(new Rectangle(143, 35, 143, 22)); 
    add(ld1, null);
    add(d1 , null);


    ld2.setText("Fecha");
    ld2.setBounds(new Rectangle(15, 62, 123, 22)); 
    d2.setBounds(new Rectangle(143, 62, 143, 22)); 
    add(ld2, null);
    add(d2 , null);


    ld3.setText("Operacion");
    ld3.setBounds(new Rectangle(15, 89, 123, 22)); 
    d3.setBounds(new Rectangle(143, 89, 143, 22)); 
    add(ld3, null);
    add(d3 , null);


    ld4.setText("Id Aerolinea");
    ld4.setBounds(new Rectangle(15, 116, 123, 22)); 
    d4.setBounds(new Rectangle(143, 116, 143, 22)); 
    add(ld4, null);
    add(d4 , null);


    ld5.setText("Aerolinea");
    ld5.setBounds(new Rectangle(15, 143, 123, 22)); 
    d5.setBounds(new Rectangle(143, 143, 143, 22)); 
    add(ld5, null);
    add(d5 , null);


    ld6.setText("Tarifa");
    ld6.setBounds(new Rectangle(15, 170, 123, 22)); 
    d6.setBounds(new Rectangle(143, 170, 143, 22)); 
    add(ld6, null);
    add(d6 , null);


    ld7.setText("Contado");
    ld7.setBounds(new Rectangle(15, 197, 123, 22)); 
    d7.setBounds(new Rectangle(143, 197, 143, 22)); 
    add(ld7, null);
    add(d7 , null);


    ld8.setText("Tarjeta");
    ld8.setBounds(new Rectangle(15, 224, 123, 22)); 
    d8.setBounds(new Rectangle(143, 224, 143, 22)); 
    add(ld8, null);
    add(d8 , null);


    ld9.setText("Tipo Ruta");
    ld9.setBounds(new Rectangle(15, 251, 123, 22)); 
    d9.setBounds(new Rectangle(143, 251, 143, 22)); 
    add(ld9, null);
    add(d9 , null);


    ld10.setText("Base Imponible");
    ld10.setBounds(new Rectangle(15, 278, 123, 22)); 
    d10.setBounds(new Rectangle(143, 278, 143, 22)); 
    add(ld10, null);
    add(d10 , null);


    ld11.setText("Comision");
    ld11.setBounds(new Rectangle(292, 34, 123, 22)); 
    d11.setBounds(new Rectangle(420, 34, 143, 22)); 
    add(ld11, null);
    add(d11 , null);


    ld12.setText("Comision Over");
    ld12.setBounds(new Rectangle(292, 61, 123, 22)); 
    d12.setBounds(new Rectangle(420, 61, 143, 22)); 
    add(ld12, null);
    add(d12 , null);


    ld13.setText("Comision Porcentaje");
    ld13.setBounds(new Rectangle(292, 88, 123, 22)); 
    d13.setBounds(new Rectangle(420, 88, 143, 22)); 
    add(ld13, null);
    add(d13 , null);


    ld14.setText("Impuesto Comision");
    ld14.setBounds(new Rectangle(292, 115, 123, 22)); 
    d14.setBounds(new Rectangle(420, 115, 143, 22)); 
    add(ld14, null);
    add(d14 , null);


    ld15.setText("Impuesto 1");
    ld15.setBounds(new Rectangle(292, 142, 123, 22)); 
    d15.setBounds(new Rectangle(420, 142, 143, 22)); 
    add(ld15, null);
    add(d15 , null);


    ld16.setText("Impuesto 2");
    ld16.setBounds(new Rectangle(292, 169, 123, 22)); 
    d16.setBounds(new Rectangle(420, 169, 143, 22)); 
    add(ld16, null);
    add(d16 , null);


    ld17.setText("Total");
    ld17.setBounds(new Rectangle(292, 196, 123, 22)); 
    d17.setBounds(new Rectangle(420, 196, 143, 22)); 
    add(ld17, null);
    add(d17 , null);


    ld18.setText("Tipo Tarjeta");
    ld18.setBounds(new Rectangle(292, 223, 123, 22)); 
    d18.setBounds(new Rectangle(420, 223, 143, 22)); 
    add(ld18, null);
    add(d18 , null);


    ld19.setText("Nro.Tarjeta");
    ld19.setBounds(new Rectangle(292, 250, 123, 22)); 
    d19.setBounds(new Rectangle(420, 250, 143, 22)); 
    add(ld19, null);
    add(d19 , null);


    ld20.setText("Observacion BSP");
    ld20.setBounds(new Rectangle(292, 277, 123, 22)); 
    d20.setBounds(new Rectangle(420, 277, 142, 22)); 
    add(ld20, null);
    add(d20 , null);
    ld21.setText("Contrato");
    ld21.setBounds(new Rectangle(292, 277, 123, 22)); 
    d21.setBounds(new Rectangle(420, 277, 142, 22)); 
    add(ld21, null);
    add(d21 , null);

    lobservaciones.setText( "Observaciones" );
    lobservaciones.setBounds(new Rectangle(15, 306, 123, 22)); 
    observaciones.setBounds(new Rectangle(143, 306, 421, 106)); 
    add(lobservaciones, null);
    add(observaciones , null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" ).setVisible(false);
    AddItem( owner, CHAR, REQ, "owner" ).setVisible(false);
    AddItem( idpdf, CHAR, REQ, "idpdf" );
    AddItem( linea, UINT, OPT, "linea" ).setVisible(false);
    AddItem( status, CHAR, OPT, "status" , JWins.CreateVirtualWins(BizConsolidacion.ObtenerResultadosConsolidacion())).SetReadOnly(true);
    AddItem( d1, CHAR, OPT, "d1" ).SetReadOnly(true);
    AddItem( d2, CHAR, OPT, "d2" ).SetReadOnly(true);
    AddItem( d3, CHAR, OPT, "d3" ).SetReadOnly(true);
    AddItem( d4, CHAR, OPT, "d4" ).SetReadOnly(true);
    AddItem( d5, CHAR, OPT, "d5" ).SetReadOnly(true);
    AddItem( d6, CHAR, OPT, "d6" ).SetReadOnly(true);
    AddItem( d7, CHAR, OPT, "d7" ).SetReadOnly(true);
    AddItem( d8, CHAR, OPT, "d8" ).SetReadOnly(true);
    AddItem( d9, CHAR, OPT, "d9" ).SetReadOnly(true);
    AddItem( d10, CHAR, OPT, "d10" ).SetReadOnly(true);
    AddItem( d11, CHAR, OPT, "d11" ).SetReadOnly(true);
    AddItem( d12, CHAR, OPT, "d12" ).SetReadOnly(true);
    AddItem( d13, CHAR, OPT, "d13" ).SetReadOnly(true);
    AddItem( d14, CHAR, OPT, "d14" ).SetReadOnly(true);
    AddItem( d15, CHAR, OPT, "d15" ).SetReadOnly(true);
    AddItem( d16, CHAR, OPT, "d16" ).SetReadOnly(true);
    AddItem( d17, CHAR, OPT, "d17" ).SetReadOnly(true);
    AddItem( d18, CHAR, OPT, "d18" ).SetReadOnly(true);
    AddItem( d19, CHAR, OPT, "d19" ).SetReadOnly(true);
    AddItem( d20, CHAR, OPT, "d20" ).SetReadOnly(true);
    AddItem( d21, CHAR, OPT, "d21" ).SetReadOnly(true);
     AddItem( observaciones, CHAR, OPT, "observaciones" );

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
