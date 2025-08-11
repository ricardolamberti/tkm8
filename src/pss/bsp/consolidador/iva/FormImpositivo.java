package pss.bsp.consolidador.iva;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComboBox;

import pss.bsp.consolidador.consolidacion.detalle.BizConsolidacion;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;

public class FormImpositivo extends JBaseForm {


private static final long serialVersionUID = 1247604198764L;

  JPssEdit company = new JPssEdit  ();
JPssEdit owner = new JPssEdit  ();
JPssLabel lidpdf = new JPssLabel();
JPssEdit idpdf = new JPssEdit  ();
JPssLabel llinea = new JPssLabel();
JPssEdit linea = new JPssEdit  ();
JPssLabel lstatus = new JPssLabel();
JComboBox status = new JComboBox  ();
JPssLabel lboleto = new JPssLabel();
JPssEdit boleto = new JPssEdit  ();
JPssLabel loperacion = new JPssLabel();
JPssEdit operacion = new JPssEdit  ();
JPssLabel ltipo_ruta = new JPssLabel();
JPssEdit tipo_ruta = new JPssEdit  ();
JPssLabel lid_aerolinea = new JPssLabel();
JPssEdit id_aerolinea = new JPssEdit  ();
JPssLabel ltarifa_bsp = new JPssLabel();
JPssEdit tarifa_bsp = new JPssEdit  ();
JPssLabel liva_bsp = new JPssLabel();
JPssEdit iva_bsp = new JPssEdit  ();
JPssLabel livacomision_bsp = new JPssLabel();
JPssEdit ivacomision_bsp = new JPssEdit  ();
JPssLabel lcomision_bsp = new JPssLabel();
JPssEdit comision_bsp = new JPssEdit  ();
JPssLabel ltarifa_bo = new JPssLabel();
JPssEdit tarifa_bo = new JPssEdit  ();
JPssLabel liva_bo = new JPssLabel();
JPssEdit iva_bo = new JPssEdit  ();
JPssLabel livacomision_bo = new JPssLabel();
JPssEdit ivacomision_bo = new JPssEdit  ();
JPssEdit comision_bo = new JPssEdit  ();
JPssEdit tarifa_dif = new JPssEdit  ();
JPssEdit iva_dif = new JPssEdit  ();
JPssEdit ivacomision_dif = new JPssEdit  ();
JPssEdit comision_dif = new JPssEdit  ();
JPssLabel lobservacion = new JPssLabel();
JPssEdit observacion = new JPssEdit  ();


  /**
   * Constructor de la Clase
   */
  public FormImpositivo() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiImpositivo getWin() { return (GuiImpositivo) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(609, 400));


    company.setBounds(new Rectangle(1, 4, 13, 22)); 
    add(company , null);


    owner.setBounds(new Rectangle(1, 31, 13, 22)); 
    add(owner , null);


    lidpdf.setText("id BSP");
    lidpdf.setBounds(new Rectangle(25, 4, 123, 22)); 
    idpdf.setBounds(new Rectangle(153, 4, 143, 22)); 
    add(lidpdf, null);
    add(idpdf , null);


    llinea.setText( "Linea" );
    llinea.setBounds(new Rectangle(25, 31, 123, 22)); 
    linea.setBounds(new Rectangle(153, 31, 143, 22)); 
    add(llinea, null);
    add(linea , null);


    lstatus.setText( "Status" );
    lstatus.setBounds(new Rectangle(25, 58, 123, 22)); 
    status.setBounds(new Rectangle(153, 58, 143, 22)); 
    add(lstatus, null);
    add(status , null);


    lboleto.setText( "Boleto" );
    lboleto.setBounds(new Rectangle(25, 84, 123, 22)); 
    boleto.setBounds(new Rectangle(153, 84, 143, 22)); 
    add(lboleto, null);
    add(boleto , null);


    loperacion.setText( "Operacion" );
    loperacion.setBounds(new Rectangle(307, 84, 123, 22)); 
    operacion.setBounds(new Rectangle(435, 84, 143, 22)); 
    add(loperacion, null);
    add(operacion , null);


    ltipo_ruta.setText( "Tipo ruta" );
    ltipo_ruta.setBounds(new Rectangle(24, 110, 123, 22)); 
    tipo_ruta.setBounds(new Rectangle(152, 110, 143, 22)); 
    add(ltipo_ruta, null);
    add(tipo_ruta , null);


    lid_aerolinea.setText("Aerolinea");
    lid_aerolinea.setBounds(new Rectangle(306, 110, 123, 22)); 
    id_aerolinea.setBounds(new Rectangle(434, 110, 143, 22)); 
    add(lid_aerolinea, null);
    add(id_aerolinea , null);


    ltarifa_bsp.setText("Tarifa");
    ltarifa_bsp.setBounds(new Rectangle(25, 192, 123, 22)); 
    tarifa_bsp.setBounds(new Rectangle(152, 192, 143, 22)); 
    add(ltarifa_bsp, null);
    add(tarifa_bsp , null);


    liva_bsp.setText("Iva");
    liva_bsp.setBounds(new Rectangle(25, 219, 123, 22)); 
    iva_bsp.setBounds(new Rectangle(152, 219, 143, 22)); 
    add(liva_bsp, null);
    add(iva_bsp , null);


    livacomision_bsp.setText("Comision");
    livacomision_bsp.setBounds(new Rectangle(25, 246, 123, 22)); 
    ivacomision_bsp.setBounds(new Rectangle(152, 273, 143, 22)); 
    add(livacomision_bsp, null);
    add(ivacomision_bsp , null);


    lcomision_bsp.setText("Iva Comision");
    lcomision_bsp.setBounds(new Rectangle(25, 273, 123, 22)); 
    comision_bsp.setBounds(new Rectangle(152, 246, 143, 22)); 
    add(lcomision_bsp, null);
    add(comision_bsp , null);


    ltarifa_bo.setText("BSP");
    ltarifa_bo.setBounds(new Rectangle(152, 163, 123, 22)); 
    tarifa_bo.setBounds(new Rectangle(300, 192, 143, 22)); 
    add(ltarifa_bo, null);
    add(tarifa_bo , null);


    liva_bo.setText("Backoffice");
    liva_bo.setBounds(new Rectangle(300, 163, 123, 22)); 
    iva_bo.setBounds(new Rectangle(300, 219, 143, 22)); 
    add(liva_bo, null);
    add(iva_bo , null);


    livacomision_bo.setText("Diferencia");
    livacomision_bo.setBounds(new Rectangle(450, 163, 123, 22)); 
    ivacomision_bo.setBounds(new Rectangle(300, 273, 143, 22)); 
    add(livacomision_bo, null);
    add(ivacomision_bo , null);


    comision_bo.setBounds(new Rectangle(300, 246, 143, 22)); 
    add(comision_bo , null);


    tarifa_dif.setBounds(new Rectangle(450, 192, 143, 22)); 
    add(tarifa_dif , null);


    iva_dif.setBounds(new Rectangle(450, 219, 143, 22)); 
    add(iva_dif , null);


    ivacomision_dif.setBounds(new Rectangle(450, 273, 143, 22)); 
    add(ivacomision_dif , null);


    comision_dif.setBounds(new Rectangle(450, 246, 143, 22)); 
    add(comision_dif , null);


    lobservacion.setText( "Observacion" );
    lobservacion.setBounds(new Rectangle(28, 315, 123, 22)); 
    observacion.setBounds(new Rectangle(156, 315, 437, 67)); 
    add(lobservacion, null);
    add(observacion , null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" ).setVisible(false);
    AddItem( owner, CHAR, REQ, "owner" ).setVisible(false);
    AddItem( idpdf, CHAR, REQ, "idpdf" ).SetReadOnly(true);
    AddItem( linea, UINT, REQ, "linea" ).SetReadOnly(true);
    AddItem( status, CHAR, REQ, "status" , JWins.CreateVirtualWins(BizConsolidacion.ObtenerResultadosConsolidacion())).SetReadOnly(true);
    AddItem( boleto, CHAR, OPT, "boleto" ).SetReadOnly(true);
    AddItem( operacion, CHAR, OPT, "operacion" ).SetReadOnly(true);
    AddItem( tipo_ruta, CHAR, OPT, "tipo_ruta" ).SetReadOnly(true);
    AddItem( id_aerolinea, CHAR, OPT, "id_aerolinea" ).SetReadOnly(true);
    AddItem( tarifa_bsp, UFLOAT, OPT, "tarifa_bsp" ).SetReadOnly(true);
    AddItem( iva_bsp, UFLOAT, OPT, "iva_bsp" ).SetReadOnly(true);
    AddItem( ivacomision_bsp, UFLOAT, OPT, "ivacomision_bsp" ).SetReadOnly(true);
    AddItem( comision_bsp, UFLOAT, OPT, "comision_bsp" ).SetReadOnly(true);
    AddItem( tarifa_bo, UFLOAT, OPT, "tarifa_bo" ).SetReadOnly(true);
    AddItem( iva_bo, UFLOAT, OPT, "iva_bo" ).SetReadOnly(true);
    AddItem( ivacomision_bo, UFLOAT, OPT, "ivacomision_bo" ).SetReadOnly(true);
    AddItem( comision_bo, UFLOAT, OPT, "comision_bo" ).SetReadOnly(true);
    AddItem( tarifa_dif, UFLOAT, OPT, "tarifa_dif" ).SetReadOnly(true);
    AddItem( iva_dif, UFLOAT, OPT, "iva_dif" ).SetReadOnly(true);
    AddItem( ivacomision_dif, UFLOAT, OPT, "ivacomision_dif" ).SetReadOnly(true);
    AddItem( comision_dif, UFLOAT, OPT, "comision_dif" ).SetReadOnly(true);
    AddItem( observacion, CHAR, OPT, "observacion" );

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
