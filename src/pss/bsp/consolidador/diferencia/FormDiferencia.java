package pss.bsp.consolidador.diferencia;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComboBox;

import pss.bsp.consolidador.consolidacion.detalle.BizConsolidacion;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;

public class FormDiferencia extends JBaseForm {


private static final long serialVersionUID = 1247604035440L;

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
JPssLabel lcontado_bsp = new JPssLabel();
JPssEdit contado_bsp = new JPssEdit  ();
JPssLabel lcredito_bsp = new JPssLabel();
JPssEdit credito_bsp = new JPssEdit  ();
JPssLabel lcomision_bsp = new JPssLabel();
JPssEdit comision_bsp = new JPssEdit  ();
JPssLabel limpuesto_bsp = new JPssLabel();
JPssEdit impuesto_bsp = new JPssEdit  ();
JPssLabel ltarifa_bo = new JPssLabel();
JPssEdit tarifa_bo = new JPssEdit  ();
JPssLabel lcontado_bo = new JPssLabel();
JPssEdit contado_bo = new JPssEdit  ();
JPssLabel lcredito_bo = new JPssLabel();
JPssEdit credito_bo = new JPssEdit  ();
JPssEdit comision_bo = new JPssEdit  ();
JPssEdit impuesto_bo = new JPssEdit  ();
JPssEdit tarifa_dif = new JPssEdit  ();
JPssEdit contado_dif = new JPssEdit  ();
JPssEdit credito_dif = new JPssEdit  ();
JPssEdit comision_dif = new JPssEdit  ();
JPssEdit impuesto_dif = new JPssEdit  ();
JPssLabel lobservacion = new JPssLabel();
JPssEdit observacion = new JPssEdit  ();


  /**
   * Constructor de la Clase
   */
  public FormDiferencia() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiDiferencia getWin() { return (GuiDiferencia) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(622, 358));


    company.setBounds(new Rectangle(9, 9, 11, 22)); 
    add(company , null);


    owner.setBounds(new Rectangle(9, 36, 11, 22)); 
    add(owner , null);


    lidpdf.setText( "Id BSP" );
    lidpdf.setBounds(new Rectangle(33, 6, 123, 22)); 
    idpdf.setBounds(new Rectangle(161, 6, 143, 22)); 
    add(lidpdf, null);
    add(idpdf , null);


    llinea.setText( "Linea" );
    llinea.setBounds(new Rectangle(33, 33, 123, 22)); 
    linea.setBounds(new Rectangle(161, 33, 143, 22)); 
    add(llinea, null);
    add(linea , null);


    lstatus.setText( "Status" );
    lstatus.setBounds(new Rectangle(33, 60, 123, 22)); 
    status.setBounds(new Rectangle(161, 60, 143, 22)); 
    add(lstatus, null);
    add(status , null);


    lboleto.setText( "Boleto" );
    lboleto.setBounds(new Rectangle(33, 87, 123, 22)); 
    boleto.setBounds(new Rectangle(161, 87, 143, 22)); 
    add(lboleto, null);
    add(boleto , null);


    loperacion.setText( "Operacion" );
    loperacion.setBounds(new Rectangle(33, 114, 123, 22)); 
    operacion.setBounds(new Rectangle(161, 114, 143, 22)); 
    add(loperacion, null);
    add(operacion , null);


    ltipo_ruta.setText( "Tipo ruta" );
    ltipo_ruta.setBounds(new Rectangle(308, 88, 123, 22)); 
    tipo_ruta.setBounds(new Rectangle(436, 88, 143, 22)); 
    add(ltipo_ruta, null);
    add(tipo_ruta , null);


    lid_aerolinea.setText( "Aerolinea" );
    lid_aerolinea.setBounds(new Rectangle(308, 115, 123, 22)); 
    id_aerolinea.setBounds(new Rectangle(436, 115, 143, 22)); 
    add(lid_aerolinea, null);
    add(id_aerolinea , null);


    ltarifa_bsp.setText("Tarifa");
    ltarifa_bsp.setBounds(new Rectangle(35, 175, 123, 22)); 
    tarifa_bsp.setBounds(new Rectangle(163, 175, 143, 22)); 
    add(ltarifa_bsp, null);
    add(tarifa_bsp , null);


    lcontado_bsp.setText( "Contado" );
    lcontado_bsp.setBounds(new Rectangle(35, 202, 123, 22)); 
    contado_bsp.setBounds(new Rectangle(163, 202, 143, 22)); 
    add(lcontado_bsp, null);
    add(contado_bsp , null);


    lcredito_bsp.setText( "Credito" );
    lcredito_bsp.setBounds(new Rectangle(35, 229, 123, 22)); 
    credito_bsp.setBounds(new Rectangle(163, 229, 143, 22)); 
    add(lcredito_bsp, null);
    add(credito_bsp , null);


    lcomision_bsp.setText( "Comision" );
    lcomision_bsp.setBounds(new Rectangle(35, 256, 123, 22)); 
    comision_bsp.setBounds(new Rectangle(163, 256, 143, 22)); 
    add(lcomision_bsp, null);
    add(comision_bsp , null);


    limpuesto_bsp.setText( "Impuesto" );
    limpuesto_bsp.setBounds(new Rectangle(35, 283, 123, 22)); 
    impuesto_bsp.setBounds(new Rectangle(163, 283, 143, 22)); 
    add(limpuesto_bsp, null);
    add(impuesto_bsp , null);


    ltarifa_bo.setText("BSP");
    ltarifa_bo.setBounds(new Rectangle(163, 146, 123, 22)); 
    tarifa_bo.setBounds(new Rectangle(313, 175, 143, 22)); 
    add(ltarifa_bo, null);
    add(tarifa_bo , null);


    lcontado_bo.setText("BackOffice");
    lcontado_bo.setBounds(new Rectangle(313, 145, 123, 22)); 
    contado_bo.setBounds(new Rectangle(313, 202, 143, 22)); 
    add(lcontado_bo, null);
    add(contado_bo , null);


    lcredito_bo.setText("Diferencia");
    lcredito_bo.setBounds(new Rectangle(466, 144, 123, 22)); 
    credito_bo.setBounds(new Rectangle(313, 229, 143, 22)); 
    add(lcredito_bo, null);
    add(credito_bo , null);


    comision_bo.setBounds(new Rectangle(313, 256, 143, 22)); 
    add(comision_bo , null);


    impuesto_bo.setBounds(new Rectangle(313, 283, 143, 22)); 
    add(impuesto_bo , null);


    tarifa_dif.setBounds(new Rectangle(463, 175, 143, 22)); 
    add(tarifa_dif , null);


    contado_dif.setBounds(new Rectangle(463, 202, 143, 22)); 
    add(contado_dif , null);


    credito_dif.setBounds(new Rectangle(463, 229, 143, 22)); 
    add(credito_dif , null);


    comision_dif.setBounds(new Rectangle(463, 256, 143, 22)); 
    add(comision_dif , null);


    impuesto_dif.setBounds(new Rectangle(463, 283, 143, 22)); 
    add(impuesto_dif , null);


    lobservacion.setText( "Observacion" );
    lobservacion.setBounds(new Rectangle(35, 318, 123, 22)); 
    observacion.setBounds(new Rectangle(163, 318, 441, 22)); 
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
    AddItem( status, CHAR, OPT, "status" , JWins.CreateVirtualWins(BizConsolidacion.ObtenerResultadosConsolidacion())).SetReadOnly(true);
    AddItem( boleto, CHAR, OPT, "boleto" ).SetReadOnly(true);
    AddItem( operacion, CHAR, OPT, "operacion" ).SetReadOnly(true);
    AddItem( tipo_ruta, CHAR, OPT, "tipo_ruta" ).SetReadOnly(true);
    AddItem( id_aerolinea, CHAR, OPT, "id_aerolinea" ).SetReadOnly(true);
    AddItem( tarifa_bsp, UFLOAT, OPT, "tarifa_bsp" ).SetReadOnly(true);
    AddItem( contado_bsp, UFLOAT, OPT, "contado_bsp" ).SetReadOnly(true);
    AddItem( credito_bsp, UFLOAT, OPT, "credito_bsp" ).SetReadOnly(true);
    AddItem( comision_bsp, UFLOAT, OPT, "comision_bsp" ).SetReadOnly(true);
    AddItem( impuesto_bsp, UFLOAT, OPT, "impuesto_bsp" ).SetReadOnly(true);
    AddItem( tarifa_bo, UFLOAT, OPT, "tarifa_bo" ).SetReadOnly(true);
    AddItem( contado_bo, UFLOAT, OPT, "contado_bo" ).SetReadOnly(true);
    AddItem( credito_bo, UFLOAT, OPT, "credito_bo" ).SetReadOnly(true);
    AddItem( comision_bo, UFLOAT, OPT, "comision_bo" ).SetReadOnly(true);
    AddItem( impuesto_bo, UFLOAT, OPT, "impuesto_bo" ).SetReadOnly(true);
    AddItem( tarifa_dif, UFLOAT, OPT, "tarifa_dif" ).SetReadOnly(true);
    AddItem( contado_dif, UFLOAT, OPT, "contado_dif" ).SetReadOnly(true);
    AddItem( credito_dif, UFLOAT, OPT, "credito_dif" ).SetReadOnly(true);
    AddItem( comision_dif, UFLOAT, OPT, "comision_dif" ).SetReadOnly(true);
    AddItem( impuesto_dif, UFLOAT, OPT, "impuesto_dif" ).SetReadOnly(true);
    AddItem( observacion, CHAR, OPT, "observacion" );

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
