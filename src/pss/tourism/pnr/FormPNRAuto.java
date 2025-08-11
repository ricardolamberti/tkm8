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
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class FormPNRAuto  extends JBaseForm {


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
JPssLabel lpasajeros = new JPssLabel();
JPssEdit pasajeros = new JPssEdit  ();
JPssLabel lhabitaciones = new JPssLabel();
JPssEdit habitaciones = new JPssEdit  ();
JPssLabel lnro_autos = new JPssLabel();
JPssEdit nro_autos = new JPssEdit  ();
JPssLabel lcar_code = new JPssLabel();
JPssEdit car_code = new JPssEdit  ();
JPssLabel lconfirmacion = new JPssLabel();
JPssEdit confirmacion = new JPssEdit  ();
JPssLabel laeropuerto = new JPssLabel();
JPssEdit aeropuerto = new JPssEdit  ();
JPssLabel lhotel_codigo = new JPssLabel();
JPssEdit hotel_codigo = new JPssEdit  ();
JPssLabel lfecha_salida = new JPssLabel();
JPssCalendarEdit fecha_salida = new JPssCalendarEdit  ();
JPssLabel lnoches = new JPssLabel();
JPssEdit noches = new JPssEdit  ();
JPssLabel ltour_name = new JPssLabel();
JPssEdit tour_name = new JPssEdit  ();
JPssLabel lhotel_name = new JPssLabel();
JPssEdit hotel_name = new JPssEdit  ();
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
private JPssLabel pslblFechaEntrega;
private JPssCalendarEdit pssCalendarEdit;
private final JPanel panel = new JPanel();
private final JPanel panel_1 = new JPanel();
private final JPanel panel_2 = new JPanel();
private JPssLabel pslblInfo;
private JPssEdit pssEdit;


  /**
   * Constructor de la Clase
   */
  public FormPNRAuto() throws Exception {
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
    panel_2.setBorder(new TitledBorder(null, "Auto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panel_2.setBounds(4, -5, 642, 136);
    
    add(panel_2);
    panel_2.setLayout(null);
        lproduct_code.setBounds(6, 16, 123, 22);
        panel_2.add(lproduct_code);
    
    
        lproduct_code.setText( "Producto" );
        product_code.setBounds(80, 16, 197, 22);
        panel_2.add(product_code);
    lfecha_reserva.setBounds(6, 70, 123, 22);
    panel_2.add(lfecha_reserva);


    lfecha_reserva.setText( "Fecha reserva" );
    fecha_reserva.setBounds(80, 70, 197, 22);
    panel_2.add(fecha_reserva);
        lhotel_codigo.setBounds(287, 70, 75, 22);
        panel_2.add(lhotel_codigo);
    
    
    
        lhotel_codigo.setText( "Tipo Auto" );
        hotel_codigo.setBounds(361, 70, 197, 22);
        panel_2.add(hotel_codigo);
            lfecha_salida.setBounds(6, 97, 75, 22);
            panel_2.add(lfecha_salida);
        
        
            lfecha_salida.setText( "Fecha salida" );
            fecha_salida.setBounds(80, 97, 197, 22);
            panel_2.add(fecha_salida);
                    lhotel_name.setBounds(287, 99, 75, 22);
                    panel_2.add(lhotel_name);
                
                
                    lhotel_name.setText( "Proveedor" );
                    hotel_name.setBounds(361, 99, 260, 22);
                    panel_2.add(hotel_name);
                        lgarantia_info.setBounds(6, 43, 123, 22);
                        panel_2.add(lgarantia_info);
                    
                    
                    
                    
                    
                        lgarantia_info.setText( "Pasajero" );
                        garantia_info.setBounds(80, 43, 556, 22);
                        panel_2.add(garantia_info);
    panel.setBorder(new TitledBorder(null, "Datos entrega", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panel.setBounds(6, 141, 347, 210);
    
    add(panel);
    panel.setLayout(null);
        ldrop_off_charge.setBounds(170, 45, 71, 22);
        panel.add(ldrop_off_charge);
    
        ldrop_off_charge.setText( "Cargo entrega" );
        drop_off_charge.setBounds(251, 45, 86, 22);
        panel.add(drop_off_charge);
    ldop_off_location.setBounds(6, 45, 51, 22);
    panel.add(ldop_off_location);

    ldop_off_location.setText( "Lugar" );
    dop_off_location.setBounds(79, 44, 53, 22);
    panel.add(dop_off_location);
    lpasajeros.setBounds(170, 71, 63, 22);
    panel.add(lpasajeros);



    lpasajeros.setText( "Retorno" );
    pasajeros.setBounds(251, 71, 51, 22);
    panel.add(pasajeros);
    lhabitaciones.setBounds(7, 96, 75, 22);
    panel.add(lhabitaciones);


    lhabitaciones.setText( "Ciudad" );
    habitaciones.setBounds(81, 96, 51, 22);
    panel.add(habitaciones);
    lnro_autos.setBounds(170, 96, 63, 22);
    panel.add(lnro_autos);


    lnro_autos.setText( "Nro autos" );
    nro_autos.setBounds(251, 96, 51, 22);
    panel.add(nro_autos);
    lcar_code.setBounds(6, 122, 75, 22);
    panel.add(lcar_code);


    lcar_code.setText( "Equipamiento" );
    car_code.setBounds(80, 122, 257, 22);
    panel.add(car_code);
        lnoches.setBounds(7, 71, 75, 22);
        panel.add(lnoches);
    
    
        lnoches.setText( "Hora Arr." );
        noches.setBounds(81, 71, 51, 22);
        panel.add(noches);
        
        pslblFechaEntrega = new JPssLabel();
        pslblFechaEntrega.setBounds(6, 16, 75, 22);
        panel.add(pslblFechaEntrega);
        pslblFechaEntrega.setText("Fecha entrega");
        
        pssCalendarEdit = new JPssCalendarEdit();
        pssCalendarEdit.setBounds(80, 16, 197, 22);
        panel.add(pssCalendarEdit);
        tour_name.setBounds(80, 148, 257, 22);
        panel.add(tour_name);
            ltour_name.setBounds(6, 148, 75, 22);
            panel.add(ltour_name);
        
        
            ltour_name.setText( "Garantia" );
            
            pslblInfo = new JPssLabel();
            pslblInfo.setText("Info");
            pslblInfo.setBounds(6, 177, 75, 22);
            panel.add(pslblInfo);
            
            pssEdit = new JPssEdit();
            pssEdit.setBounds(80, 177, 257, 22);
            panel.add(pssEdit);
    panel_1.setBorder(new TitledBorder(null, "Montos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    panel_1.setBounds(363, 142, 283, 208);
    
    add(panel_1);
    panel_1.setLayout(null);
        limporte_base.setBounds(7, 16, 123, 22);
        panel_1.add(limporte_base);
    
    
        limporte_base.setText( "Importe base" );
        importe_base.setBounds(134, 16, 143, 22);
        panel_1.add(importe_base);
    limporte.setBounds(7, 43, 123, 22);
    panel_1.add(limporte);


    limporte.setText( "Importe" );
    importe.setBounds(134, 44, 143, 22);
    panel_1.add(importe);
    ltasas.setBounds(7, 75, 123, 22);
    panel_1.add(ltasas);


    ltasas.setText( "Tasas" );
    tasas.setBounds(134, 73, 143, 22);
    panel_1.add(tasas);
    lprecio_total.setBounds(7, 100, 123, 22);
    panel_1.add(lprecio_total);


    lprecio_total.setText( "Precio total" );
    precio_total.setBounds(134, 100, 143, 22);
    panel_1.add(precio_total);
    lcomision_codigo.setBounds(7, 128, 123, 22);
    panel_1.add(lcomision_codigo);


    lcomision_codigo.setText( "Comision codigo" );
    comision_codigo.setBounds(134, 128, 143, 22);
    panel_1.add(comision_codigo);
    lcomision_texto.setBounds(6, 152, 123, 22);
    panel_1.add(lcomision_texto);


    lcomision_texto.setText( "Comision texto" );
    comision_texto.setBounds(134, 152, 143, 22);
    panel_1.add(comision_texto);
    lcomision_monto.setBounds(6, 179, 123, 22);
    panel_1.add(lcomision_monto);


    lcomision_monto.setText( "Comision monto" );
    comision_monto.setBounds(134, 179, 143, 22);
    panel_1.add(comision_monto);
    
    tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    tabbedPane.setBounds(10, 362, 651, 199);
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
    AddItem( pssCalendarEdit, DATE, REQ, "drop_off_date" );
    AddItem( producto_secundario, CHAR, REQ, "producto_secundario" );
    AddItem( pasajeros, UINT, REQ, "retorno" );
    AddItem( habitaciones, UINT, REQ, "pickup_point" );
    AddItem( nro_autos, UINT, REQ, "nro_autos" );
    AddItem( car_code, CHAR, REQ, "equipamiento" );
    AddItem( confirmacion, CHAR, REQ, "confirmacion" );
    AddItem( aeropuerto, CHAR, REQ, "aeropuerto" );
    AddItem( hotel_codigo, CHAR, REQ, "car_type" );
    AddItem( fecha_salida, DATE, REQ, "fecha_salida" );
    AddItem( noches, UINT, REQ, "arribo_hora" );
    AddItem( tour_name, CHAR, REQ, "tasa_garantia" );
    AddItem( hotel_name, CHAR, REQ, "vehiculo_proveedor" );
    AddItem( codigo_producto, CHAR, REQ, "codigo_producto" );
    AddItem( importe_base, UFLOAT, REQ, "importe_base" );
    AddItem( moneda_base, CHAR, REQ, "moneda_base" );
    AddItem( importe, UFLOAT, REQ, "importe" );
    AddItem( pssEdit, CHAR, REQ, "info" );
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
    AddItem( upgrade, CHAR, REQ, "upgrade" );
    AddItem( dop_off_location, CHAR, REQ, "dop_off_location" );
    AddItem( drop_off_charge, CHAR, REQ, "drop_off_charge" );
    AddItem( corporate_discount, CHAR, REQ, "corporate_discount" );
    AddItem( tourcode, CHAR, REQ, "tourcode" );
    AddItem( remarks, CHAR, REQ, "remarks" );
    AddItem( pasajero_frecuente, CHAR, REQ, "pasajero_frecuente" );
    AddItem( nombre_cliente, CHAR, REQ, "nombre_cliente" );
    AddItem( cupon, CHAR, REQ, "cupon" );
    AddItem( booking_source, CHAR, REQ, "booking_source" );
    AddItem( precio, UFLOAT, REQ, "importe" );
    AddItem( voucher_tipo, CHAR, REQ, "voucher_tipo" );
    AddItem( voucher_numero, CHAR, REQ, "voucher_numero" );
    AddItem( voucher_biling, CHAR, REQ, "voucher_biling" );
    AddItem( voucher_format, CHAR, REQ, "voucher_format" );
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