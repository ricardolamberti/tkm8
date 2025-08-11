package pss.tourism.pnr;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JTabbedPane;

import pss.core.ui.components.JPssCalendarEdit;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.winUI.controls.JFormLista;
import pss.core.winUI.forms.JBaseForm;

public class FormPNROtro extends JBaseForm {


private static final long serialVersionUID = 1502224695647L;
JPssEdit secuence_id = new JPssEdit  ();
JPssLabel lproduct_code = new JPssLabel();
JPssEdit product_code = new JPssEdit  ();
JPssEdit control_data = new JPssEdit  ();
JPssEdit action = new JPssEdit  ();
JPssLabel lfecha_reserva = new JPssLabel();
JPssCalendarEdit fecha_reserva = new JPssCalendarEdit  ();
JPssLabel lproducto_secundario = new JPssLabel();
JPssEdit producto_secundario = new JPssEdit  ();
JPssLabel lcar_code = new JPssLabel();
JPssEdit car_code = new JPssEdit  ();
JPssLabel lconfirmacion = new JPssLabel();
JPssEdit confirmacion = new JPssEdit  ();
JPssLabel laeropuerto = new JPssLabel();
JPssEdit aeropuerto = new JPssEdit  ();
JPssLabel lfecha_salida = new JPssLabel();
JPssCalendarEdit fecha_salida = new JPssCalendarEdit  ();
JPssLabel lcodigo_producto = new JPssLabel();
JPssEdit codigo_producto = new JPssEdit  ();
JPssLabel limporte_base = new JPssLabel();
JPssEdit importe_base = new JPssEdit  ();
JPssEdit moneda_base = new JPssEdit  ();
JPssLabel limporte = new JPssLabel();
JPssEdit importe = new JPssEdit  ();
JPssEdit moneda = new JPssEdit  ();
JPssLabel ltasas = new JPssLabel();
JPssEdit tasas = new JPssEdit  ();
JPssLabel lprecio_total = new JPssLabel();
JPssEdit precio_total = new JPssEdit  ();
JPssEdit moneda_precio_total = new JPssEdit  ();
JPssLabel ldomicilio = new JPssLabel();
JPssEdit domicilio = new JPssEdit  ();
JPssLabel ltelefono = new JPssLabel();
JPssEdit telefono = new JPssEdit  ();
JPssLabel lfax = new JPssLabel();
JPssEdit fax = new JPssEdit  ();
JPssLabel lcodigo_confirmacion = new JPssLabel();
JPssEdit codigo_confirmacion = new JPssEdit  ();
JPssLabel lcomision_codigo = new JPssLabel();
JPssEdit comision_codigo = new JPssEdit  ();
JPssLabel lcomision_texto = new JPssLabel();
JPssEdit comision_texto = new JPssEdit  ();
JPssLabel lcomision_monto = new JPssLabel();
JPssEdit comision_monto = new JPssEdit  ();
JPssLabel ltipo_tarjeta = new JPssLabel();
JPssEdit tipo_tarjeta = new JPssEdit  ();
JPssLabel ltarjeta = new JPssLabel();
JPssEdit tarjeta = new JPssEdit  ();
JPssLabel ltax_breakdown1 = new JPssLabel();
JPssEdit tax_breakdown1 = new JPssEdit  ();
JPssLabel ltax_breakdown2 = new JPssLabel();
JPssEdit tax_breakdown2 = new JPssEdit  ();
JPssLabel ltax_breakdown3 = new JPssLabel();
JPssEdit tax_breakdown3 = new JPssEdit  ();
JPssLabel ltax_breakdown4 = new JPssLabel();
JPssEdit tax_breakdown4 = new JPssEdit  ();
JPssLabel lsurchange1 = new JPssLabel();
JPssEdit surchange1 = new JPssEdit  ();
JPssLabel lsurchange2 = new JPssLabel();
JPssEdit surchange2 = new JPssEdit  ();
JPssLabel lsurchange3 = new JPssLabel();
JPssEdit surchange3 = new JPssEdit  ();
JPssLabel lsurchange4 = new JPssLabel();
JPssEdit surchange4 = new JPssEdit  ();
JPssLabel ldisclaimer1 = new JPssLabel();
JPssEdit disclaimer1 = new JPssEdit  ();
JPssLabel ldisclaimer2 = new JPssLabel();
JPssEdit disclaimer2 = new JPssEdit  ();
JPssLabel lpickup_point = new JPssLabel();
JPssEdit pickup_point = new JPssEdit  ();
JPssLabel ldrop_off_date = new JPssLabel();
JPssEdit drop_off_date = new JPssEdit  ();
JPssLabel lcar_type = new JPssLabel();
JPssEdit car_type = new JPssEdit  ();
JPssLabel lupgrade = new JPssLabel();
JPssEdit upgrade = new JPssEdit  ();
JPssLabel larribo_hora = new JPssLabel();
JPssEdit arribo_hora = new JPssEdit  ();
JPssLabel lreorno_Arrivo = new JPssLabel();
JPssEdit reorno_Arrivo = new JPssEdit  ();
JPssLabel ldop_off_location = new JPssLabel();
JPssEdit dop_off_location = new JPssEdit  ();
JPssLabel ldrop_off_charge = new JPssLabel();
JPssEdit drop_off_charge = new JPssEdit  ();
JPssLabel lgarantia = new JPssLabel();
JPssEdit garantia = new JPssEdit  ();
JPssLabel lcorporate_discount = new JPssLabel();
JPssEdit corporate_discount = new JPssEdit  ();
JPssLabel ltourcode = new JPssLabel();
JPssEdit tourcode = new JPssEdit  ();
JPssLabel lequipamiento = new JPssLabel();
JPssEdit equipamiento = new JPssEdit  ();
JPssLabel lremarks = new JPssLabel();
JPssEdit remarks = new JPssEdit  ();
JPssLabel lpasajero_frecuente = new JPssLabel();
JPssEdit pasajero_frecuente = new JPssEdit  ();
JPssLabel lnombre_cliente = new JPssLabel();
JPssEdit nombre_cliente = new JPssEdit  ();
JPssLabel lcupon = new JPssLabel();
JPssEdit cupon = new JPssEdit  ();
JPssLabel ltasa_garantia = new JPssLabel();
JPssEdit tasa_garantia = new JPssEdit  ();
JPssLabel lbooking_source = new JPssLabel();
JPssEdit booking_source = new JPssEdit  ();
JPssLabel lrate_code = new JPssLabel();
JPssEdit rate_code = new JPssEdit  ();
JPssLabel lprecio = new JPssLabel();
JPssEdit precio = new JPssEdit  ();
JPssLabel lvehiculo_proveedor = new JPssLabel();
JPssEdit vehiculo_proveedor = new JPssEdit  ();
JPssLabel lvoucher_tipo = new JPssLabel();
JPssEdit voucher_tipo = new JPssEdit  ();
JPssLabel lvoucher_numero = new JPssLabel();
JPssEdit voucher_numero = new JPssEdit  ();
JPssLabel lvoucher_biling = new JPssLabel();
JPssEdit voucher_biling = new JPssEdit  ();
JPssLabel lvoucher_format = new JPssLabel();
JPssEdit voucher_format = new JPssEdit  ();
JPssLabel linfo = new JPssLabel();
JPssEdit info = new JPssEdit  ();
JPssLabel lequipamiento_confirmado = new JPssLabel();
JPssEdit equipamiento_confirmado = new JPssEdit  ();
JPssLabel lgarantia_info = new JPssLabel();
JPssEdit garantia_info = new JPssEdit  ();
JPssLabel lvendedor = new JPssLabel();
JPssEdit vendedor = new JPssEdit  ();
JPssLabel lcompany = new JPssLabel();
JPssEdit company = new JPssEdit  ();
private JTabbedPane tabbedPane;


  /**
   * Constructor de la Clase
   */
  public FormPNROtro() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiPNROtro getWin() { return (GuiPNROtro) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(671, 572));


    lproduct_code.setText( "Producto" );
    lproduct_code.setBounds(new Rectangle(10, 11, 123, 22)); 
    product_code.setBounds(new Rectangle(84, 11, 197, 22)); 
    add(lproduct_code, null);
    add(product_code , null);


    lfecha_reserva.setText( "Fecha reserva" );
    lfecha_reserva.setBounds(new Rectangle(10, 65, 123, 22)); 
    fecha_reserva.setBounds(new Rectangle(84, 65, 197, 22)); 
    add(lfecha_reserva, null);
    add(fecha_reserva , null);


    lcar_code.setText( "Tipo producto" );
    lcar_code.setBounds(new Rectangle(10, 123, 75, 22)); 
    car_code.setBounds(new Rectangle(84, 123, 143, 22)); 
    add(lcar_code, null);
    add(car_code , null);


    lfecha_salida.setText( "Fecha salida" );
    lfecha_salida.setBounds(new Rectangle(10, 92, 123, 22)); 
    fecha_salida.setBounds(new Rectangle(84, 92, 197, 22)); 
    add(lfecha_salida, null);
    add(fecha_salida , null);


    limporte_base.setText( "Importe base" );
    limporte_base.setBounds(new Rectangle(370, 65, 123, 22)); 
    importe_base.setBounds(new Rectangle(497, 65, 143, 22)); 
    add(limporte_base, null);
    add(importe_base , null);


    limporte.setText( "Importe" );
    limporte.setBounds(new Rectangle(370, 92, 123, 22)); 
    importe.setBounds(new Rectangle(497, 93, 143, 22)); 
    add(limporte, null);
    add(importe , null);


    ltasas.setText( "Tasas" );
    ltasas.setBounds(new Rectangle(370, 124, 123, 22)); 
    tasas.setBounds(new Rectangle(497, 122, 143, 22)); 
    add(ltasas, null);
    add(tasas , null);


    lprecio_total.setText( "Precio total" );
    lprecio_total.setBounds(new Rectangle(370, 149, 123, 22)); 
    precio_total.setBounds(new Rectangle(497, 149, 143, 22)); 
    add(lprecio_total, null);
    add(precio_total , null);


    lcomision_codigo.setText( "Comision codigo" );
    lcomision_codigo.setBounds(new Rectangle(370, 177, 123, 22)); 
    comision_codigo.setBounds(new Rectangle(497, 177, 143, 22)); 
    add(lcomision_codigo, null);
    add(comision_codigo , null);


    lcomision_texto.setText( "Comision texto" );
    lcomision_texto.setBounds(new Rectangle(369, 201, 123, 22)); 
    comision_texto.setBounds(new Rectangle(497, 201, 143, 22)); 
    add(lcomision_texto, null);
    add(comision_texto , null);


    lcomision_monto.setText( "Comision monto" );
    lcomision_monto.setBounds(new Rectangle(369, 228, 123, 22)); 
    comision_monto.setBounds(new Rectangle(497, 228, 143, 22)); 
    add(lcomision_monto, null);
    add(comision_monto , null);





    lgarantia_info.setText( "Pasajero" );
    lgarantia_info.setBounds(new Rectangle(10, 38, 123, 22)); 
    garantia_info.setBounds(new Rectangle(84, 38, 556, 22)); 
    add(lgarantia_info, null);
    add(garantia_info , null);
    
    tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    tabbedPane.setBounds(10, 288, 651, 273);
    add(tabbedPane);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( secuence_id, UINT, REQ, "secuence_id" );
    AddItem( product_code, CHAR, REQ, "product_desc" );
    AddItem( control_data, CHAR, REQ, "control_data" );
    AddItem( action, CHAR, REQ, "action" );
    AddItem( fecha_reserva, DATE, REQ, "fecha_reserva" );
    AddItem( producto_secundario, CHAR, REQ, "producto_secundario" );
    AddItem( car_code, CHAR, REQ, "car_code" );
    AddItem( confirmacion, CHAR, REQ, "confirmacion" );
    AddItem( aeropuerto, CHAR, REQ, "aeropuerto" );
    AddItem( fecha_salida, DATE, REQ, "fecha_salida" );
    AddItem( codigo_producto, CHAR, REQ, "codigo_producto" );
    AddItem( importe_base, UFLOAT, REQ, "importe_base" );
    AddItem( moneda_base, CHAR, REQ, "moneda_base" );
    AddItem( importe, UFLOAT, REQ, "importe" );
    AddItem( moneda, CHAR, REQ, "moneda_local" );
    AddItem( tasas, UFLOAT, REQ, "tasas" );
    AddItem( precio_total, UFLOAT, REQ, "precio_total" );
    AddItem( moneda_precio_total, CHAR, REQ, "moneda_precio_total" );
    AddItem( domicilio, CHAR, REQ, "domicilio" );
    AddItem( telefono, CHAR, REQ, "telefono" );
    AddItem( fax, CHAR, REQ, "fax" );
    AddItem( codigo_confirmacion, CHAR, REQ, "codigo_confirmacion" );
    AddItem( comision_codigo, CHAR, REQ, "comision_codigo" );
    AddItem( comision_texto, CHAR, REQ, "comision_texto" );
    AddItem( comision_monto, UFLOAT, REQ, "comision_monto" );
    AddItem( tipo_tarjeta, CHAR, REQ, "tipo_tarjeta" );
    AddItem( tarjeta, CHAR, REQ, "tarjeta" );
    AddItem( tax_breakdown1, CHAR, REQ, "tax_breakdown1" );
    AddItem( tax_breakdown2, CHAR, REQ, "tax_breakdown2" );
    AddItem( tax_breakdown3, CHAR, REQ, "tax_breakdown3" );
    AddItem( tax_breakdown4, CHAR, REQ, "tax_breakdown4" );
    AddItem( surchange1, CHAR, REQ, "surchange1" );
    AddItem( surchange2, CHAR, REQ, "surchange2" );
    AddItem( surchange3, CHAR, REQ, "surchange3" );
    AddItem( surchange4, CHAR, REQ, "surchange4" );
    AddItem( disclaimer1, CHAR, REQ, "disclaimer1" );
    AddItem( disclaimer2, CHAR, REQ, "disclaimer2" );
    AddItem( pickup_point, CHAR, REQ, "pickup_point" );
    AddItem( drop_off_date, DATE, REQ, "drop_off_date" );
    AddItem( car_type, CHAR, REQ, "car_type" );
    AddItem( upgrade, CHAR, REQ, "upgrade" );
    AddItem( arribo_hora, CHAR, REQ, "arribo_hora" );
    AddItem( reorno_Arrivo, CHAR, REQ, "retorno" );
    AddItem( dop_off_location, CHAR, REQ, "dop_off_location" );
    AddItem( drop_off_charge, CHAR, REQ, "drop_off_charge" );
    AddItem( garantia, CHAR, REQ, "garantia" );
    AddItem( corporate_discount, CHAR, REQ, "corporate_discount" );
    AddItem( tourcode, CHAR, REQ, "tourcode" );
    AddItem( equipamiento, CHAR, REQ, "equipamiento" );
    AddItem( remarks, CHAR, REQ, "remarks" );
    AddItem( pasajero_frecuente, CHAR, REQ, "pasajero_frecuente" );
    AddItem( nombre_cliente, CHAR, REQ, "nombre_cliente" );
    AddItem( cupon, CHAR, REQ, "cupon" );
    AddItem( tasa_garantia, CHAR, REQ, "tasa_garantia" );
    AddItem( booking_source, CHAR, REQ, "booking_source" );
    AddItem( rate_code, CHAR, REQ, "rate_code" );
    AddItem( precio, UFLOAT, REQ, "importe" );
    AddItem( vehiculo_proveedor, CHAR, REQ, "vehiculo_proveedor" );
    AddItem( voucher_tipo, CHAR, REQ, "voucher_tipo" );
    AddItem( voucher_numero, CHAR, REQ, "voucher_numero" );
    AddItem( voucher_biling, CHAR, REQ, "voucher_biling" );
    AddItem( voucher_format, CHAR, REQ, "voucher_format" );
    AddItem( info, CHAR, REQ, "info" );
    AddItem( equipamiento_confirmado, CHAR, REQ, "equipamiento_confirmado" );
    AddItem( garantia_info, CHAR, REQ, "nombre_pasajero" );
    AddItem( vendedor, CHAR, REQ, "vendedor" );
    AddItem( company, CHAR, REQ, "company" );

		JFormLista l =AddItem(tabbedPane, 30);
		l.setKeepHeight(true);
		l.setKeepWidth(true);
		AddItem(tabbedPane, 120);
		l.setKeepHeight(true);
		l.setKeepWidth(true);

  } 
} 
