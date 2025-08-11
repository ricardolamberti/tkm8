package  pss.bsp.gds;

import java.awt.Dimension;
import java.awt.Rectangle;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;

public class FormGds extends JBaseForm {


private static final long serialVersionUID = 1446570743111L;

  /**
   * Propiedades de la Clase
   */
JPssLabel ltarifabase = new JPssLabel();
JPssEdit tarifabase = new JPssEdit  ();
JPssLabel ltarifa_codmoneda = new JPssLabel();
JPssEdit tarifa_codmoneda = new JPssEdit  ();
JPssLabel lcodigobasemoneda = new JPssLabel();
JPssEdit codigobasemoneda = new JPssEdit  ();
JPssLabel ltarifa_sec = new JPssLabel();
JPssEdit tarifa_sec = new JPssEdit  ();
JPssLabel ltipodecambio = new JPssLabel();
JPssEdit tipodecambio = new JPssEdit  ();
JPssLabel ltarifaconimpuestos = new JPssLabel();
JPssEdit tarifaconimpuestos = new JPssEdit  ();
JPssLabel ltarifa = new JPssLabel();
JPssEdit tarifa = new JPssEdit  ();
JPssLabel lfee_codmoneda = new JPssLabel();
JPssEdit fee_codmoneda = new JPssEdit  ();
JPssLabel lfee_importe = new JPssLabel();
JPssEdit fee_importe = new JPssEdit  ();
JPssLabel lfee_descripcion = new JPssLabel();
JPssEdit fee_descripcion = new JPssEdit  ();
JPssLabel lcodigoentretenimiento = new JPssLabel();
JPssEdit codigoentretenimiento = new JPssEdit  ();
JPssLabel lhoraarrivo = new JPssLabel();
JPssEdit horaarrivo = new JPssEdit  ();
JPssLabel ltipo_segmento = new JPssLabel();
JPssEdit tipo_segmento = new JPssEdit  ();
JPssLabel lhoradespegue = new JPssLabel();
JPssEdit horadespegue = new JPssEdit  ();
JPssLabel ldespegue = new JPssLabel();
JPssEdit despegue = new JPssEdit  ();
JPssLabel lduracionvuelo = new JPssLabel();
JPssEdit duracionvuelo = new JPssEdit  ();
JPssLabel larrivo = new JPssLabel();
JPssEdit arrivo = new JPssEdit  ();
JPssLabel lcarrier = new JPssLabel();
JPssEdit carrier = new JPssEdit  ();
JPssLabel lcodigosegmento = new JPssLabel();
JPssEdit codigosegmento = new JPssEdit  ();
JPssLabel lfechaarrivo = new JPssLabel();
JPssEdit fechaarrivo = new JPssEdit  ();
JPssLabel lestado = new JPssLabel();
JPssEdit estado = new JPssEdit  ();
JPssLabel lcodigocomida = new JPssLabel();
JPssEdit codigocomida = new JPssEdit  ();
JPssLabel lclase = new JPssLabel();
JPssEdit clase = new JPssEdit  ();
JPssLabel ltipoequipo = new JPssLabel();
JPssEdit tipoequipo = new JPssEdit  ();
JPssLabel lsegmento_ini = new JPssLabel();
JPssEdit segmento_ini = new JPssEdit  ();
JPssLabel lnumerovuelo = new JPssLabel();
JPssEdit numerovuelo = new JPssEdit  ();
JPssLabel lfechadespegue = new JPssLabel();
JPssEdit fechadespegue = new JPssEdit  ();
JPssLabel lnumero = new JPssLabel();
JPssEdit numero = new JPssEdit  ();
JPssLabel ldetalle = new JPssLabel();
JPssEdit detalle = new JPssEdit  ();
JPssLabel lcodigoaerolineafqt = new JPssLabel();
JPssEdit codigoaerolineafqt = new JPssEdit  ();
JPssLabel lnombrepasajero = new JPssLabel();
JPssEdit nombrepasajero = new JPssEdit  ();
JPssLabel lnumeropasajerofqt = new JPssLabel();
JPssEdit numeropasajerofqt = new JPssEdit  ();
JPssLabel ltipopasajero = new JPssLabel();
JPssEdit tipopasajero = new JPssEdit  ();
JPssLabel lpas_numeropasajero = new JPssLabel();
JPssEdit pas_numeropasajero = new JPssEdit  ();
JPssLabel lpago_codmoneda = new JPssLabel();
JPssEdit pago_codmoneda = new JPssEdit  ();
JPssLabel lformapago = new JPssLabel();
JPssEdit formapago = new JPssEdit  ();
JPssLabel lcodigoaprobacion = new JPssLabel();
JPssEdit codigoaprobacion = new JPssEdit  ();
JPssLabel lpago_importe = new JPssLabel();
JPssEdit pago_importe = new JPssEdit  ();
JPssLabel lnumerotarjeta = new JPssLabel();
JPssEdit numerotarjeta = new JPssEdit  ();
JPssLabel lpago_sec = new JPssLabel();
JPssEdit pago_sec = new JPssEdit  ();
JPssLabel lfechavencimiento = new JPssLabel();
JPssEdit fechavencimiento = new JPssEdit  ();
JPssLabel lcom_importe = new JPssLabel();
JPssEdit com_importe = new JPssEdit  ();
JPssLabel lsecuencia = new JPssLabel();
JPssEdit secuencia = new JPssEdit  ();
JPssLabel lcomision_iva = new JPssLabel();
JPssEdit comision_iva = new JPssEdit  ();
JPssLabel lporcentaje = new JPssLabel();
JPssEdit porcentaje = new JPssEdit  ();
JPssLabel limp_codmoneda = new JPssLabel();
JPssEdit imp_codmoneda = new JPssEdit  ();
JPssLabel lvirtual = new JPssLabel();
JPssEdit virtual = new JPssEdit  ();
JPssLabel limp_importe = new JPssLabel();
JPssEdit imp_importe = new JPssEdit  ();
JPssLabel limp_sec = new JPssLabel();
JPssEdit imp_sec = new JPssEdit  ();
JPssLabel lcodigoimpuesto = new JPssLabel();
JPssEdit codigoimpuesto = new JPssEdit  ();
JPssLabel limportecedido = new JPssLabel();
JPssEdit importecedido = new JPssEdit  ();
JPssLabel loff_line = new JPssLabel();
JPssEdit off_line = new JPssEdit  ();
JPssLabel ltarifanormal = new JPssLabel();
JPssEdit tarifanormal = new JPssEdit  ();
JPssLabel lneto = new JPssLabel();
JPssEdit neto = new JPssEdit  ();
JPssLabel ladditional_fee = new JPssLabel();
JPssEdit additional_fee = new JPssEdit  ();
JPssLabel lporcentajeover = new JPssLabel();
JPssEdit porcentajeover = new JPssEdit  ();
JPssLabel lvoid = new JPssLabel();
JPssEdit voids = new JPssEdit  ();
JPssLabel ldevolucion = new JPssLabel();
JPssEdit devolucion = new JPssEdit  ();
JPssLabel lboletocambio = new JPssLabel();
JPssEdit boletocambio = new JPssEdit  ();
JPssLabel liva_expense = new JPssLabel();
JPssEdit iva_expense = new JPssEdit  ();
JPssLabel lcodigoaerolinea = new JPssLabel();
JPssEdit codigoaerolinea = new JPssEdit  ();
JPssLabel lit = new JPssLabel();
JPssEdit it = new JPssEdit  ();
JPssLabel lventa = new JPssLabel();
JPssEdit venta = new JPssEdit  ();
JPssLabel lover_cedido_iva_retenido = new JPssLabel();
JPssEdit over_cedido_iva_retenido = new JPssEdit  ();
JPssLabel lreemision = new JPssLabel();
JPssEdit reemision = new JPssEdit  ();
JPssLabel lnumeroboleto = new JPssLabel();
JPssEdit numeroboleto = new JPssEdit  ();
JPssLabel lcantidadconectados = new JPssLabel();
JPssEdit cantidadconectados = new JPssEdit  ();
JPssLabel laplicacentro = new JPssLabel();
JPssEdit aplicacentro = new JPssEdit  ();
JPssLabel lajuste = new JPssLabel();
JPssEdit ajuste = new JPssEdit  ();
JPssLabel lboleto_sec = new JPssLabel();
JPssEdit boleto_sec = new JPssEdit  ();
JPssLabel linternacional = new JPssLabel();
JPssEdit internacional = new JPssEdit  ();
JPssLabel lreferencia = new JPssLabel();
JPssEdit referencia = new JPssEdit  ();
JPssLabel lboleto_nropasajero = new JPssLabel();
JPssEdit boleto_nropasajero = new JPssEdit  ();
JPssLabel ltarifaeconomica = new JPssLabel();
JPssEdit tarifaeconomica = new JPssEdit  ();
JPssLabel limporteover = new JPssLabel();
JPssEdit importeover = new JPssEdit  ();
JPssLabel livaover = new JPssLabel();
JPssEdit ivaover = new JPssEdit  ();
JPssLabel lboleto_descripcion = new JPssLabel();
JPssEdit boleto_descripcion = new JPssEdit  ();
JPssLabel lconsolidador = new JPssLabel();
JPssEdit consolidador = new JPssEdit  ();
JPssLabel lnumeroempleado = new JPssLabel();
JPssEdit numeroempleado = new JPssEdit  ();
JPssLabel lexpense = new JPssLabel();
JPssEdit expense = new JPssEdit  ();
JPssLabel lporc_expense = new JPssLabel();
JPssEdit porc_expense = new JPssEdit  ();
JPssLabel ltipoboleto = new JPssLabel();
JPssEdit tipoboleto = new JPssEdit  ();
JPssLabel loversobre = new JPssLabel();
JPssEdit oversobre = new JPssEdit  ();
JPssLabel laplicacomparativo = new JPssLabel();
JPssEdit aplicacomparativo = new JPssEdit  ();
JPssLabel lconsumed = new JPssLabel();
JPssEdit consumed = new JPssEdit  ();
JPssLabel lfechacreacion = new JPssLabel();
JPssEdit fechacreacion = new JPssEdit  ();
JPssLabel lorigen = new JPssLabel();
JPssEdit origen = new JPssEdit  ();
JPssLabel lcentrocosto = new JPssLabel();
JPssEdit centrocosto = new JPssEdit  ();
JPssLabel lfechasalida = new JPssLabel();
JPssEdit fechasalida = new JPssEdit  ();
JPssLabel lcodigopnr = new JPssLabel();
JPssEdit codigopnr = new JPssEdit  ();
JPssLabel lobservation = new JPssLabel();
JPssEdit observation = new JPssEdit  ();
JPssLabel ltipoair = new JPssLabel();
JPssEdit tipoair = new JPssEdit  ();
JPssLabel lretransmitido = new JPssLabel();
JPssEdit retransmitido = new JPssEdit  ();
JPssLabel lcantidadboletos = new JPssLabel();
JPssEdit cantidadboletos = new JPssEdit  ();
JPssLabel lvendedor = new JPssLabel();
JPssEdit vendedor = new JPssEdit  ();
JPssLabel lcompany = new JPssLabel();
JPssEdit company = new JPssEdit  ();
JPssLabel lregistrolocalizador = new JPssLabel();
JPssEdit registrolocalizador = new JPssEdit  ();
JPssLabel ltransactiontype = new JPssLabel();
JPssEdit transactiontype = new JPssEdit  ();
JPssLabel lfechacreacionair = new JPssLabel();
JPssEdit fechacreacionair = new JPssEdit  ();
JPssLabel lofficeid = new JPssLabel();
JPssEdit officeid = new JPssEdit  ();
JPssLabel lruta = new JPssLabel();
JPssEdit ruta = new JPssEdit  ();
JPssLabel lcomandocrs = new JPssLabel();
JPssEdit comandocrs = new JPssEdit  ();
JPssLabel lcodigocliente = new JPssLabel();
JPssEdit codigocliente = new JPssEdit  ();
JPssLabel lnombrecliente = new JPssLabel();
JPssEdit nombrecliente = new JPssEdit  ();
JPssLabel lversion = new JPssLabel();
JPssEdit version = new JPssEdit  ();
JPssLabel lfechamodificacion = new JPssLabel();
JPssEdit fechamodificacion = new JPssEdit  ();
JPssLabel lgds = new JPssLabel();
JPssEdit gds = new JPssEdit  ();
JPssLabel larchivo = new JPssLabel();
JPssEdit archivo = new JPssEdit  ();
JPssLabel linterface_id = new JPssLabel();
JPssEdit interface_id = new JPssEdit  ();
JPssLabel lindicadorventa = new JPssLabel();
JPssEdit indicadorventa = new JPssEdit  ();
JPssLabel ltipo_prestacion = new JPssLabel();
JPssEdit tipo_prestacion = new JPssEdit  ();
JPssLabel liata = new JPssLabel();
JPssEdit iata = new JPssEdit  ();


  /**
   * Constructor de la Clase
   */
  public FormGds() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiGds getWin() { return (GuiGds) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(357, 110+2464));


    ltarifabase.setText( "Tarifabase" );
    ltarifabase.setBounds(new Rectangle(40, 44+0, 123, 22)); 
    tarifabase.setBounds(new Rectangle(168, 44+0, 143, 22)); 
    add(ltarifabase, null);
    add(tarifabase , null);


    ltarifa_codmoneda.setText( "Tarifa codmoneda" );
    ltarifa_codmoneda.setBounds(new Rectangle(40, 44+27, 123, 22)); 
    tarifa_codmoneda.setBounds(new Rectangle(168, 44+27, 143, 22)); 
    add(ltarifa_codmoneda, null);
    add(tarifa_codmoneda , null);


    lcodigobasemoneda.setText( "Codigobasemoneda" );
    lcodigobasemoneda.setBounds(new Rectangle(40, 44+54, 123, 22)); 
    codigobasemoneda.setBounds(new Rectangle(168, 44+54, 143, 22)); 
    add(lcodigobasemoneda, null);
    add(codigobasemoneda , null);


    ltarifa_sec.setText( "Tarifa sec" );
    ltarifa_sec.setBounds(new Rectangle(40, 44+81, 123, 22)); 
    tarifa_sec.setBounds(new Rectangle(168, 44+81, 143, 22)); 
    add(ltarifa_sec, null);
    add(tarifa_sec , null);


    ltipodecambio.setText( "Tipodecambio" );
    ltipodecambio.setBounds(new Rectangle(40, 44+108, 123, 22)); 
    tipodecambio.setBounds(new Rectangle(168, 44+108, 143, 22)); 
    add(ltipodecambio, null);
    add(tipodecambio , null);


    ltarifaconimpuestos.setText( "Tarifaconimpuestos" );
    ltarifaconimpuestos.setBounds(new Rectangle(40, 44+135, 123, 22)); 
    tarifaconimpuestos.setBounds(new Rectangle(168, 44+135, 143, 22)); 
    add(ltarifaconimpuestos, null);
    add(tarifaconimpuestos , null);


    ltarifa.setText( "Tarifa" );
    ltarifa.setBounds(new Rectangle(40, 44+162, 123, 22)); 
    tarifa.setBounds(new Rectangle(168, 44+162, 143, 22)); 
    add(ltarifa, null);
    add(tarifa , null);


    lfee_codmoneda.setText( "Fee codmoneda" );
    lfee_codmoneda.setBounds(new Rectangle(40, 44+189, 123, 22)); 
    fee_codmoneda.setBounds(new Rectangle(168, 44+189, 143, 22)); 
    add(lfee_codmoneda, null);
    add(fee_codmoneda , null);


    lfee_importe.setText( "Fee importe" );
    lfee_importe.setBounds(new Rectangle(40, 44+216, 123, 22)); 
    fee_importe.setBounds(new Rectangle(168, 44+216, 143, 22)); 
    add(lfee_importe, null);
    add(fee_importe , null);


    lfee_descripcion.setText( "Fee descripcion" );
    lfee_descripcion.setBounds(new Rectangle(40, 44+243, 123, 22)); 
    fee_descripcion.setBounds(new Rectangle(168, 44+243, 143, 22)); 
    add(lfee_descripcion, null);
    add(fee_descripcion , null);


    lcodigoentretenimiento.setText( "Codigoentretenimiento" );
    lcodigoentretenimiento.setBounds(new Rectangle(40, 44+270, 123, 22)); 
    codigoentretenimiento.setBounds(new Rectangle(168, 44+270, 143, 22)); 
    add(lcodigoentretenimiento, null);
    add(codigoentretenimiento , null);


    lhoraarrivo.setText( "Horaarrivo" );
    lhoraarrivo.setBounds(new Rectangle(40, 44+297, 123, 22)); 
    horaarrivo.setBounds(new Rectangle(168, 44+297, 143, 22)); 
    add(lhoraarrivo, null);
    add(horaarrivo , null);


    ltipo_segmento.setText( "Tipo segmento" );
    ltipo_segmento.setBounds(new Rectangle(40, 44+324, 123, 22)); 
    tipo_segmento.setBounds(new Rectangle(168, 44+324, 143, 22)); 
    add(ltipo_segmento, null);
    add(tipo_segmento , null);


    lhoradespegue.setText( "Horadespegue" );
    lhoradespegue.setBounds(new Rectangle(40, 44+351, 123, 22)); 
    horadespegue.setBounds(new Rectangle(168, 44+351, 143, 22)); 
    add(lhoradespegue, null);
    add(horadespegue , null);


    ldespegue.setText( "Despegue" );
    ldespegue.setBounds(new Rectangle(40, 44+378, 123, 22)); 
    despegue.setBounds(new Rectangle(168, 44+378, 143, 22)); 
    add(ldespegue, null);
    add(despegue , null);


    lduracionvuelo.setText( "Duracionvuelo" );
    lduracionvuelo.setBounds(new Rectangle(40, 44+405, 123, 22)); 
    duracionvuelo.setBounds(new Rectangle(168, 44+405, 143, 22)); 
    add(lduracionvuelo, null);
    add(duracionvuelo , null);


    larrivo.setText( "Arrivo" );
    larrivo.setBounds(new Rectangle(40, 44+432, 123, 22)); 
    arrivo.setBounds(new Rectangle(168, 44+432, 143, 22)); 
    add(larrivo, null);
    add(arrivo , null);


    lcarrier.setText( "Carrier" );
    lcarrier.setBounds(new Rectangle(40, 44+459, 123, 22)); 
    carrier.setBounds(new Rectangle(168, 44+459, 143, 22)); 
    add(lcarrier, null);
    add(carrier , null);


    lcodigosegmento.setText( "Codigosegmento" );
    lcodigosegmento.setBounds(new Rectangle(40, 44+486, 123, 22)); 
    codigosegmento.setBounds(new Rectangle(168, 44+486, 143, 22)); 
    add(lcodigosegmento, null);
    add(codigosegmento , null);


    lfechaarrivo.setText( "Fechaarrivo" );
    lfechaarrivo.setBounds(new Rectangle(40, 44+513, 123, 22)); 
    fechaarrivo.setBounds(new Rectangle(168, 44+513, 143, 22)); 
    add(lfechaarrivo, null);
    add(fechaarrivo , null);


    lestado.setText( "Estado" );
    lestado.setBounds(new Rectangle(40, 44+540, 123, 22)); 
    estado.setBounds(new Rectangle(168, 44+540, 143, 22)); 
    add(lestado, null);
    add(estado , null);


    lcodigocomida.setText( "Codigocomida" );
    lcodigocomida.setBounds(new Rectangle(40, 44+567, 123, 22)); 
    codigocomida.setBounds(new Rectangle(168, 44+567, 143, 22)); 
    add(lcodigocomida, null);
    add(codigocomida , null);


    lclase.setText( "Clase" );
    lclase.setBounds(new Rectangle(40, 44+594, 123, 22)); 
    clase.setBounds(new Rectangle(168, 44+594, 143, 22)); 
    add(lclase, null);
    add(clase , null);


    ltipoequipo.setText( "Tipoequipo" );
    ltipoequipo.setBounds(new Rectangle(40, 44+621, 123, 22)); 
    tipoequipo.setBounds(new Rectangle(168, 44+621, 143, 22)); 
    add(ltipoequipo, null);
    add(tipoequipo , null);


    lsegmento_ini.setText( "Segmento ini" );
    lsegmento_ini.setBounds(new Rectangle(40, 44+648, 123, 22)); 
    segmento_ini.setBounds(new Rectangle(168, 44+648, 143, 22)); 
    add(lsegmento_ini, null);
    add(segmento_ini , null);


    lnumerovuelo.setText( "Numerovuelo" );
    lnumerovuelo.setBounds(new Rectangle(40, 44+675, 123, 22)); 
    numerovuelo.setBounds(new Rectangle(168, 44+675, 143, 22)); 
    add(lnumerovuelo, null);
    add(numerovuelo , null);


    lfechadespegue.setText( "Fechadespegue" );
    lfechadespegue.setBounds(new Rectangle(40, 44+702, 123, 22)); 
    fechadespegue.setBounds(new Rectangle(168, 44+702, 143, 22)); 
    add(lfechadespegue, null);
    add(fechadespegue , null);


    lnumero.setText( "Numero" );
    lnumero.setBounds(new Rectangle(40, 44+729, 123, 22)); 
    numero.setBounds(new Rectangle(168, 44+729, 143, 22)); 
    add(lnumero, null);
    add(numero , null);


    ldetalle.setText( "Detalle" );
    ldetalle.setBounds(new Rectangle(40, 44+756, 123, 22)); 
    detalle.setBounds(new Rectangle(168, 44+756, 143, 22)); 
    add(ldetalle, null);
    add(detalle , null);


    lcodigoaerolineafqt.setText( "Codigoaerolineafqt" );
    lcodigoaerolineafqt.setBounds(new Rectangle(40, 44+783, 123, 22)); 
    codigoaerolineafqt.setBounds(new Rectangle(168, 44+783, 143, 22)); 
    add(lcodigoaerolineafqt, null);
    add(codigoaerolineafqt , null);


    lnombrepasajero.setText( "Nombrepasajero" );
    lnombrepasajero.setBounds(new Rectangle(40, 44+810, 123, 22)); 
    nombrepasajero.setBounds(new Rectangle(168, 44+810, 143, 22)); 
    add(lnombrepasajero, null);
    add(nombrepasajero , null);


    lnumeropasajerofqt.setText( "Numeropasajerofqt" );
    lnumeropasajerofqt.setBounds(new Rectangle(40, 44+837, 123, 22)); 
    numeropasajerofqt.setBounds(new Rectangle(168, 44+837, 143, 22)); 
    add(lnumeropasajerofqt, null);
    add(numeropasajerofqt , null);


    ltipopasajero.setText( "Tipopasajero" );
    ltipopasajero.setBounds(new Rectangle(40, 44+864, 123, 22)); 
    tipopasajero.setBounds(new Rectangle(168, 44+864, 143, 22)); 
    add(ltipopasajero, null);
    add(tipopasajero , null);


    lpas_numeropasajero.setText( "Pas numeropasajero" );
    lpas_numeropasajero.setBounds(new Rectangle(40, 44+891, 123, 22)); 
    pas_numeropasajero.setBounds(new Rectangle(168, 44+891, 143, 22)); 
    add(lpas_numeropasajero, null);
    add(pas_numeropasajero , null);


    lpago_codmoneda.setText( "Pago codmoneda" );
    lpago_codmoneda.setBounds(new Rectangle(40, 44+918, 123, 22)); 
    pago_codmoneda.setBounds(new Rectangle(168, 44+918, 143, 22)); 
    add(lpago_codmoneda, null);
    add(pago_codmoneda , null);


    lformapago.setText( "Formapago" );
    lformapago.setBounds(new Rectangle(40, 44+945, 123, 22)); 
    formapago.setBounds(new Rectangle(168, 44+945, 143, 22)); 
    add(lformapago, null);
    add(formapago , null);


    lcodigoaprobacion.setText( "Codigoaprobacion" );
    lcodigoaprobacion.setBounds(new Rectangle(40, 44+972, 123, 22)); 
    codigoaprobacion.setBounds(new Rectangle(168, 44+972, 143, 22)); 
    add(lcodigoaprobacion, null);
    add(codigoaprobacion , null);


    lpago_importe.setText( "Pago importe" );
    lpago_importe.setBounds(new Rectangle(40, 44+999, 123, 22)); 
    pago_importe.setBounds(new Rectangle(168, 44+999, 143, 22)); 
    add(lpago_importe, null);
    add(pago_importe , null);


    lnumerotarjeta.setText( "Numerotarjeta" );
    lnumerotarjeta.setBounds(new Rectangle(40, 44+1026, 123, 22)); 
    numerotarjeta.setBounds(new Rectangle(168, 44+1026, 143, 22)); 
    add(lnumerotarjeta, null);
    add(numerotarjeta , null);


    lpago_sec.setText( "Pago sec" );
    lpago_sec.setBounds(new Rectangle(40, 44+1053, 123, 22)); 
    pago_sec.setBounds(new Rectangle(168, 44+1053, 143, 22)); 
    add(lpago_sec, null);
    add(pago_sec , null);


    lfechavencimiento.setText( "Fechavencimiento" );
    lfechavencimiento.setBounds(new Rectangle(40, 44+1080, 123, 22)); 
    fechavencimiento.setBounds(new Rectangle(168, 44+1080, 143, 22)); 
    add(lfechavencimiento, null);
    add(fechavencimiento , null);


    lcom_importe.setText( "Com importe" );
    lcom_importe.setBounds(new Rectangle(40, 44+1107, 123, 22)); 
    com_importe.setBounds(new Rectangle(168, 44+1107, 143, 22)); 
    add(lcom_importe, null);
    add(com_importe , null);


    lsecuencia.setText( "Secuencia" );
    lsecuencia.setBounds(new Rectangle(40, 44+1134, 123, 22)); 
    secuencia.setBounds(new Rectangle(168, 44+1134, 143, 22)); 
    add(lsecuencia, null);
    add(secuencia , null);


    lcomision_iva.setText( "Comision iva" );
    lcomision_iva.setBounds(new Rectangle(40, 44+1161, 123, 22)); 
    comision_iva.setBounds(new Rectangle(168, 44+1161, 143, 22)); 
    add(lcomision_iva, null);
    add(comision_iva , null);


    lporcentaje.setText( "Porcentaje" );
    lporcentaje.setBounds(new Rectangle(40, 44+1188, 123, 22)); 
    porcentaje.setBounds(new Rectangle(168, 44+1188, 143, 22)); 
    add(lporcentaje, null);
    add(porcentaje , null);


    limp_codmoneda.setText( "Imp codmoneda" );
    limp_codmoneda.setBounds(new Rectangle(40, 44+1215, 123, 22)); 
    imp_codmoneda.setBounds(new Rectangle(168, 44+1215, 143, 22)); 
    add(limp_codmoneda, null);
    add(imp_codmoneda , null);


    lvirtual.setText( "Virtual" );
    lvirtual.setBounds(new Rectangle(40, 44+1242, 123, 22)); 
    virtual.setBounds(new Rectangle(168, 44+1242, 143, 22)); 
    add(lvirtual, null);
    add(virtual , null);


    limp_importe.setText( "Imp importe" );
    limp_importe.setBounds(new Rectangle(40, 44+1269, 123, 22)); 
    imp_importe.setBounds(new Rectangle(168, 44+1269, 143, 22)); 
    add(limp_importe, null);
    add(imp_importe , null);


    limp_sec.setText( "Imp sec" );
    limp_sec.setBounds(new Rectangle(40, 44+1296, 123, 22)); 
    imp_sec.setBounds(new Rectangle(168, 44+1296, 143, 22)); 
    add(limp_sec, null);
    add(imp_sec , null);


    lcodigoimpuesto.setText( "Codigoimpuesto" );
    lcodigoimpuesto.setBounds(new Rectangle(40, 44+1323, 123, 22)); 
    codigoimpuesto.setBounds(new Rectangle(168, 44+1323, 143, 22)); 
    add(lcodigoimpuesto, null);
    add(codigoimpuesto , null);


    limportecedido.setText( "Importecedido" );
    limportecedido.setBounds(new Rectangle(40, 44+1350, 123, 22)); 
    importecedido.setBounds(new Rectangle(168, 44+1350, 143, 22)); 
    add(limportecedido, null);
    add(importecedido , null);


    loff_line.setText( "Off line" );
    loff_line.setBounds(new Rectangle(40, 44+1377, 123, 22)); 
    off_line.setBounds(new Rectangle(168, 44+1377, 143, 22)); 
    add(loff_line, null);
    add(off_line , null);


    ltarifanormal.setText( "Tarifanormal" );
    ltarifanormal.setBounds(new Rectangle(40, 44+1404, 123, 22)); 
    tarifanormal.setBounds(new Rectangle(168, 44+1404, 143, 22)); 
    add(ltarifanormal, null);
    add(tarifanormal , null);


    lneto.setText( "Neto" );
    lneto.setBounds(new Rectangle(40, 44+1431, 123, 22)); 
    neto.setBounds(new Rectangle(168, 44+1431, 143, 22)); 
    add(lneto, null);
    add(neto , null);


    ladditional_fee.setText( "Additional fee" );
    ladditional_fee.setBounds(new Rectangle(40, 44+1458, 123, 22)); 
    additional_fee.setBounds(new Rectangle(168, 44+1458, 143, 22)); 
    add(ladditional_fee, null);
    add(additional_fee , null);


    lporcentajeover.setText( "Porcentajeover" );
    lporcentajeover.setBounds(new Rectangle(40, 44+1485, 123, 22)); 
    porcentajeover.setBounds(new Rectangle(168, 44+1485, 143, 22)); 
    add(lporcentajeover, null);
    add(porcentajeover , null);


    lvoid.setText( "Void" );
    lvoid.setBounds(new Rectangle(40, 44+1512, 123, 22)); 
    voids.setBounds(new Rectangle(168, 44+1512, 143, 22)); 
    add(lvoid, null);
    add(voids , null);


    ldevolucion.setText( "Devolucion" );
    ldevolucion.setBounds(new Rectangle(40, 44+1539, 123, 22)); 
    devolucion.setBounds(new Rectangle(168, 44+1539, 143, 22)); 
    add(ldevolucion, null);
    add(devolucion , null);


    lboletocambio.setText( "Boletocambio" );
    lboletocambio.setBounds(new Rectangle(40, 44+1566, 123, 22)); 
    boletocambio.setBounds(new Rectangle(168, 44+1566, 143, 22)); 
    add(lboletocambio, null);
    add(boletocambio , null);


    liva_expense.setText( "Iva expense" );
    liva_expense.setBounds(new Rectangle(40, 44+1593, 123, 22)); 
    iva_expense.setBounds(new Rectangle(168, 44+1593, 143, 22)); 
    add(liva_expense, null);
    add(iva_expense , null);


    lcodigoaerolinea.setText( "Codigoaerolinea" );
    lcodigoaerolinea.setBounds(new Rectangle(40, 44+1620, 123, 22)); 
    codigoaerolinea.setBounds(new Rectangle(168, 44+1620, 143, 22)); 
    add(lcodigoaerolinea, null);
    add(codigoaerolinea , null);


    lit.setText( "It" );
    lit.setBounds(new Rectangle(40, 44+1647, 123, 22)); 
    it.setBounds(new Rectangle(168, 44+1647, 143, 22)); 
    add(lit, null);
    add(it , null);


    lventa.setText( "Venta" );
    lventa.setBounds(new Rectangle(40, 44+1674, 123, 22)); 
    venta.setBounds(new Rectangle(168, 44+1674, 143, 22)); 
    add(lventa, null);
    add(venta , null);


    lover_cedido_iva_retenido.setText( "Over cedido iva retenido" );
    lover_cedido_iva_retenido.setBounds(new Rectangle(40, 44+1701, 123, 22)); 
    over_cedido_iva_retenido.setBounds(new Rectangle(168, 44+1701, 143, 22)); 
    add(lover_cedido_iva_retenido, null);
    add(over_cedido_iva_retenido , null);


    lreemision.setText( "Reemision" );
    lreemision.setBounds(new Rectangle(40, 44+1728, 123, 22)); 
    reemision.setBounds(new Rectangle(168, 44+1728, 143, 22)); 
    add(lreemision, null);
    add(reemision , null);


    lnumeroboleto.setText( "Numeroboleto" );
    lnumeroboleto.setBounds(new Rectangle(40, 44+1755, 123, 22)); 
    numeroboleto.setBounds(new Rectangle(168, 44+1755, 143, 22)); 
    add(lnumeroboleto, null);
    add(numeroboleto , null);


    lcantidadconectados.setText( "Cantidadconectados" );
    lcantidadconectados.setBounds(new Rectangle(40, 44+1782, 123, 22)); 
    cantidadconectados.setBounds(new Rectangle(168, 44+1782, 143, 22)); 
    add(lcantidadconectados, null);
    add(cantidadconectados , null);


    laplicacentro.setText( "Aplicacentro" );
    laplicacentro.setBounds(new Rectangle(40, 44+1809, 123, 22)); 
    aplicacentro.setBounds(new Rectangle(168, 44+1809, 143, 22)); 
    add(laplicacentro, null);
    add(aplicacentro , null);


    lajuste.setText( "Ajuste" );
    lajuste.setBounds(new Rectangle(40, 44+1836, 123, 22)); 
    ajuste.setBounds(new Rectangle(168, 44+1836, 143, 22)); 
    add(lajuste, null);
    add(ajuste , null);


    lboleto_sec.setText( "Boleto sec" );
    lboleto_sec.setBounds(new Rectangle(40, 44+1863, 123, 22)); 
    boleto_sec.setBounds(new Rectangle(168, 44+1863, 143, 22)); 
    add(lboleto_sec, null);
    add(boleto_sec , null);


    linternacional.setText( "Internacional" );
    linternacional.setBounds(new Rectangle(40, 44+1890, 123, 22)); 
    internacional.setBounds(new Rectangle(168, 44+1890, 143, 22)); 
    add(linternacional, null);
    add(internacional , null);


    lreferencia.setText( "Referencia" );
    lreferencia.setBounds(new Rectangle(40, 44+1917, 123, 22)); 
    referencia.setBounds(new Rectangle(168, 44+1917, 143, 22)); 
    add(lreferencia, null);
    add(referencia , null);


    lboleto_nropasajero.setText( "Boleto nropasajero" );
    lboleto_nropasajero.setBounds(new Rectangle(40, 44+1944, 123, 22)); 
    boleto_nropasajero.setBounds(new Rectangle(168, 44+1944, 143, 22)); 
    add(lboleto_nropasajero, null);
    add(boleto_nropasajero , null);


    ltarifaeconomica.setText( "Tarifaeconomica" );
    ltarifaeconomica.setBounds(new Rectangle(40, 44+1971, 123, 22)); 
    tarifaeconomica.setBounds(new Rectangle(168, 44+1971, 143, 22)); 
    add(ltarifaeconomica, null);
    add(tarifaeconomica , null);


    limporteover.setText( "Importeover" );
    limporteover.setBounds(new Rectangle(40, 44+1998, 123, 22)); 
    importeover.setBounds(new Rectangle(168, 44+1998, 143, 22)); 
    add(limporteover, null);
    add(importeover , null);


    livaover.setText( "Ivaover" );
    livaover.setBounds(new Rectangle(40, 44+2025, 123, 22)); 
    ivaover.setBounds(new Rectangle(168, 44+2025, 143, 22)); 
    add(livaover, null);
    add(ivaover , null);


    lboleto_descripcion.setText( "Boleto descripcion" );
    lboleto_descripcion.setBounds(new Rectangle(40, 44+2052, 123, 22)); 
    boleto_descripcion.setBounds(new Rectangle(168, 44+2052, 143, 22)); 
    add(lboleto_descripcion, null);
    add(boleto_descripcion , null);


    lconsolidador.setText( "Consolidador" );
    lconsolidador.setBounds(new Rectangle(40, 44+2079, 123, 22)); 
    consolidador.setBounds(new Rectangle(168, 44+2079, 143, 22)); 
    add(lconsolidador, null);
    add(consolidador , null);


    lnumeroempleado.setText( "Numeroempleado" );
    lnumeroempleado.setBounds(new Rectangle(40, 44+2106, 123, 22)); 
    numeroempleado.setBounds(new Rectangle(168, 44+2106, 143, 22)); 
    add(lnumeroempleado, null);
    add(numeroempleado , null);


    lexpense.setText( "Expense" );
    lexpense.setBounds(new Rectangle(40, 44+2133, 123, 22)); 
    expense.setBounds(new Rectangle(168, 44+2133, 143, 22)); 
    add(lexpense, null);
    add(expense , null);


    lporc_expense.setText( "Porc expense" );
    lporc_expense.setBounds(new Rectangle(40, 44+2160, 123, 22)); 
    porc_expense.setBounds(new Rectangle(168, 44+2160, 143, 22)); 
    add(lporc_expense, null);
    add(porc_expense , null);


    ltipoboleto.setText( "Tipoboleto" );
    ltipoboleto.setBounds(new Rectangle(40, 44+2187, 123, 22)); 
    tipoboleto.setBounds(new Rectangle(168, 44+2187, 143, 22)); 
    add(ltipoboleto, null);
    add(tipoboleto , null);


    loversobre.setText( "Oversobre" );
    loversobre.setBounds(new Rectangle(40, 44+2214, 123, 22)); 
    oversobre.setBounds(new Rectangle(168, 44+2214, 143, 22)); 
    add(loversobre, null);
    add(oversobre , null);


    laplicacomparativo.setText( "Aplicacomparativo" );
    laplicacomparativo.setBounds(new Rectangle(40, 44+2241, 123, 22)); 
    aplicacomparativo.setBounds(new Rectangle(168, 44+2241, 143, 22)); 
    add(laplicacomparativo, null);
    add(aplicacomparativo , null);


    lconsumed.setText( "Consumed" );
    lconsumed.setBounds(new Rectangle(40, 44+2268, 123, 22)); 
    consumed.setBounds(new Rectangle(168, 44+2268, 143, 22)); 
    add(lconsumed, null);
    add(consumed , null);


    lfechacreacion.setText( "Fechacreacion" );
    lfechacreacion.setBounds(new Rectangle(40, 44+2295, 123, 22)); 
    fechacreacion.setBounds(new Rectangle(168, 44+2295, 143, 22)); 
    add(lfechacreacion, null);
    add(fechacreacion , null);


    lorigen.setText( "Origen" );
    lorigen.setBounds(new Rectangle(40, 44+2322, 123, 22)); 
    origen.setBounds(new Rectangle(168, 44+2322, 143, 22)); 
    add(lorigen, null);
    add(origen , null);


    lcentrocosto.setText( "Centrocosto" );
    lcentrocosto.setBounds(new Rectangle(40, 44+2349, 123, 22)); 
    centrocosto.setBounds(new Rectangle(168, 44+2349, 143, 22)); 
    add(lcentrocosto, null);
    add(centrocosto , null);


    lfechasalida.setText( "Fechasalida" );
    lfechasalida.setBounds(new Rectangle(40, 44+2376, 123, 22)); 
    fechasalida.setBounds(new Rectangle(168, 44+2376, 143, 22)); 
    add(lfechasalida, null);
    add(fechasalida , null);


    lcodigopnr.setText( "Codigopnr" );
    lcodigopnr.setBounds(new Rectangle(40, 44+2403, 123, 22)); 
    codigopnr.setBounds(new Rectangle(168, 44+2403, 143, 22)); 
    add(lcodigopnr, null);
    add(codigopnr , null);


    lobservation.setText( "Observation" );
    lobservation.setBounds(new Rectangle(40, 44+2430, 123, 22)); 
    observation.setBounds(new Rectangle(168, 44+2430, 143, 22)); 
    add(lobservation, null);
    add(observation , null);


    ltipoair.setText( "Tipoair" );
    ltipoair.setBounds(new Rectangle(40, 44+2457, 123, 22)); 
    tipoair.setBounds(new Rectangle(168, 44+2457, 143, 22)); 
    add(ltipoair, null);
    add(tipoair , null);


    lretransmitido.setText( "Retransmitido" );
    lretransmitido.setBounds(new Rectangle(40, 44+2484, 123, 22)); 
    retransmitido.setBounds(new Rectangle(168, 44+2484, 143, 22)); 
    add(lretransmitido, null);
    add(retransmitido , null);


    lcantidadboletos.setText( "Cantidadboletos" );
    lcantidadboletos.setBounds(new Rectangle(40, 44+2511, 123, 22)); 
    cantidadboletos.setBounds(new Rectangle(168, 44+2511, 143, 22)); 
    add(lcantidadboletos, null);
    add(cantidadboletos , null);


    lvendedor.setText( "Vendedor" );
    lvendedor.setBounds(new Rectangle(40, 44+2538, 123, 22)); 
    vendedor.setBounds(new Rectangle(168, 44+2538, 143, 22)); 
    add(lvendedor, null);
    add(vendedor , null);


    lcompany.setText( "Company" );
    lcompany.setBounds(new Rectangle(40, 44+2565, 123, 22)); 
    company.setBounds(new Rectangle(168, 44+2565, 143, 22)); 
    add(lcompany, null);
    add(company , null);


    lregistrolocalizador.setText( "Registrolocalizador" );
    lregistrolocalizador.setBounds(new Rectangle(40, 44+2592, 123, 22)); 
    registrolocalizador.setBounds(new Rectangle(168, 44+2592, 143, 22)); 
    add(lregistrolocalizador, null);
    add(registrolocalizador , null);


    ltransactiontype.setText( "Transactiontype" );
    ltransactiontype.setBounds(new Rectangle(40, 44+2619, 123, 22)); 
    transactiontype.setBounds(new Rectangle(168, 44+2619, 143, 22)); 
    add(ltransactiontype, null);
    add(transactiontype , null);


    lfechacreacionair.setText( "Fechacreacionair" );
    lfechacreacionair.setBounds(new Rectangle(40, 44+2646, 123, 22)); 
    fechacreacionair.setBounds(new Rectangle(168, 44+2646, 143, 22)); 
    add(lfechacreacionair, null);
    add(fechacreacionair , null);


    lofficeid.setText( "Officeid" );
    lofficeid.setBounds(new Rectangle(40, 44+2673, 123, 22)); 
    officeid.setBounds(new Rectangle(168, 44+2673, 143, 22)); 
    add(lofficeid, null);
    add(officeid , null);


    lruta.setText( "Ruta" );
    lruta.setBounds(new Rectangle(40, 44+2700, 123, 22)); 
    ruta.setBounds(new Rectangle(168, 44+2700, 143, 22)); 
    add(lruta, null);
    add(ruta , null);


    lcomandocrs.setText( "Comandocrs" );
    lcomandocrs.setBounds(new Rectangle(40, 44+2727, 123, 22)); 
    comandocrs.setBounds(new Rectangle(168, 44+2727, 143, 22)); 
    add(lcomandocrs, null);
    add(comandocrs , null);


    lcodigocliente.setText( "Codigocliente" );
    lcodigocliente.setBounds(new Rectangle(40, 44+2754, 123, 22)); 
    codigocliente.setBounds(new Rectangle(168, 44+2754, 143, 22)); 
    add(lcodigocliente, null);
    add(codigocliente , null);


    lnombrecliente.setText( "Nombrecliente" );
    lnombrecliente.setBounds(new Rectangle(40, 44+2781, 123, 22)); 
    nombrecliente.setBounds(new Rectangle(168, 44+2781, 143, 22)); 
    add(lnombrecliente, null);
    add(nombrecliente , null);


    lversion.setText( "Version" );
    lversion.setBounds(new Rectangle(40, 44+2808, 123, 22)); 
    version.setBounds(new Rectangle(168, 44+2808, 143, 22)); 
    add(lversion, null);
    add(version , null);


    lfechamodificacion.setText( "Fechamodificacion" );
    lfechamodificacion.setBounds(new Rectangle(40, 44+2835, 123, 22)); 
    fechamodificacion.setBounds(new Rectangle(168, 44+2835, 143, 22)); 
    add(lfechamodificacion, null);
    add(fechamodificacion , null);


    lgds.setText( "Gds" );
    lgds.setBounds(new Rectangle(40, 44+2862, 123, 22)); 
    gds.setBounds(new Rectangle(168, 44+2862, 143, 22)); 
    add(lgds, null);
    add(gds , null);


    larchivo.setText( "Archivo" );
    larchivo.setBounds(new Rectangle(40, 44+2889, 123, 22)); 
    archivo.setBounds(new Rectangle(168, 44+2889, 143, 22)); 
    add(larchivo, null);
    add(archivo , null);


    linterface_id.setText( "Interface id" );
    linterface_id.setBounds(new Rectangle(40, 44+2916, 123, 22)); 
    interface_id.setBounds(new Rectangle(168, 44+2916, 143, 22)); 
    add(linterface_id, null);
    add(interface_id , null);


    lindicadorventa.setText( "Indicadorventa" );
    lindicadorventa.setBounds(new Rectangle(40, 44+2943, 123, 22)); 
    indicadorventa.setBounds(new Rectangle(168, 44+2943, 143, 22)); 
    add(lindicadorventa, null);
    add(indicadorventa , null);


    ltipo_prestacion.setText( "Tipo prestacion" );
    ltipo_prestacion.setBounds(new Rectangle(40, 44+2970, 123, 22)); 
    tipo_prestacion.setBounds(new Rectangle(168, 44+2970, 143, 22)); 
    add(ltipo_prestacion, null);
    add(tipo_prestacion , null);


    liata.setText( "Iata" );
    liata.setBounds(new Rectangle(40, 44+2997, 123, 22)); 
    iata.setBounds(new Rectangle(168, 44+2997, 143, 22)); 
    add(liata, null);
    add(iata , null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( tarifabase, CHAR, REQ, "tarifabase" );
    AddItem( tarifa_codmoneda, CHAR, REQ, "tarifa_codmoneda" );
    AddItem( codigobasemoneda, CHAR, REQ, "codigobasemoneda" );
    AddItem( tarifa_sec, UINT, REQ, "tarifa_sec" );
    AddItem( tipodecambio, CHAR, REQ, "tipodecambio" );
    AddItem( tarifaconimpuestos, CHAR, REQ, "tarifaconimpuestos" );
    AddItem( tarifa, CHAR, REQ, "tarifa" );
    AddItem( fee_codmoneda, CHAR, REQ, "fee_codmoneda" );
    AddItem( fee_importe, CHAR, REQ, "fee_importe" );
    AddItem( fee_descripcion, CHAR, REQ, "fee_descripcion" );
    AddItem( codigoentretenimiento, CHAR, REQ, "codigoentretenimiento" );
    AddItem( horaarrivo, CHAR, REQ, "horaarrivo" );
    AddItem( tipo_segmento, CHAR, REQ, "tipo_segmento" );
    AddItem( horadespegue, CHAR, REQ, "horadespegue" );
    AddItem( despegue, CHAR, REQ, "despegue" );
    AddItem( duracionvuelo, CHAR, REQ, "duracionvuelo" );
    AddItem( arrivo, CHAR, REQ, "arrivo" );
    AddItem( carrier, CHAR, REQ, "carrier" );
    AddItem( codigosegmento, CHAR, REQ, "codigosegmento" );
    AddItem( fechaarrivo, CHAR, REQ, "fechaarrivo" );
    AddItem( estado, CHAR, REQ, "estado" );
    AddItem( codigocomida, CHAR, REQ, "codigocomida" );
    AddItem( clase, CHAR, REQ, "clase" );
    AddItem( tipoequipo, CHAR, REQ, "tipoequipo" );
    AddItem( segmento_ini, CHAR, REQ, "segmento_ini" );
    AddItem( numerovuelo, CHAR, REQ, "numerovuelo" );
    AddItem( fechadespegue, CHAR, REQ, "fechadespegue" );
    AddItem( numero, CHAR, REQ, "numero" );
    AddItem( detalle, CHAR, REQ, "detalle" );
    AddItem( codigoaerolineafqt, CHAR, REQ, "codigoaerolineafqt" );
    AddItem( nombrepasajero, CHAR, REQ, "nombrepasajero" );
    AddItem( numeropasajerofqt, CHAR, REQ, "numeropasajerofqt" );
    AddItem( tipopasajero, CHAR, REQ, "tipopasajero" );
    AddItem( pas_numeropasajero, CHAR, REQ, "pas_numeropasajero" );
    AddItem( pago_codmoneda, CHAR, REQ, "pago_codmoneda" );
    AddItem( formapago, CHAR, REQ, "formapago" );
    AddItem( codigoaprobacion, CHAR, REQ, "codigoaprobacion" );
    AddItem( pago_importe, CHAR, REQ, "pago_importe" );
    AddItem( numerotarjeta, CHAR, REQ, "numerotarjeta" );
    AddItem( pago_sec, UINT, REQ, "pago_sec" );
    AddItem( fechavencimiento, CHAR, REQ, "fechavencimiento" );
    AddItem( com_importe, CHAR, REQ, "com_importe" );
    AddItem( secuencia, UINT, REQ, "secuencia" );
    AddItem( comision_iva, UFLOAT, REQ, "comision_iva" );
    AddItem( porcentaje, CHAR, REQ, "porcentaje" );
    AddItem( imp_codmoneda, CHAR, REQ, "imp_codmoneda" );
    AddItem( virtual, CHAR, REQ, "virtual" );
    AddItem( imp_importe, CHAR, REQ, "imp_importe" );
    AddItem( imp_sec, UINT, REQ, "imp_sec" );
    AddItem( codigoimpuesto, CHAR, REQ, "codigoimpuesto" );
    AddItem( importecedido, CHAR, REQ, "importecedido" );
    AddItem( off_line, CHAR, REQ, "off_line" );
    AddItem( tarifanormal, CHAR, REQ, "tarifanormal" );
    AddItem( neto, CHAR, REQ, "neto" );
    AddItem( additional_fee, CHAR, REQ, "additional_fee" );
    AddItem( porcentajeover, UINT, REQ, "porcentajeover" );
    AddItem( voids, CHAR, REQ, "void" );
    AddItem( devolucion, CHAR, REQ, "devolucion" );
    AddItem( boletocambio, CHAR, REQ, "boletocambio" );
    AddItem( iva_expense, UFLOAT, REQ, "iva_expense" );
    AddItem( codigoaerolinea, CHAR, REQ, "codigoaerolinea" );
    AddItem( it, CHAR, REQ, "it" );
    AddItem( venta, CHAR, REQ, "venta" );
    AddItem( over_cedido_iva_retenido, CHAR, REQ, "over_cedido_iva_retenido" );
    AddItem( reemision, CHAR, REQ, "reemision" );
    AddItem( numeroboleto, CHAR, REQ, "numeroboleto" );
    AddItem( cantidadconectados, CHAR, REQ, "cantidadconectados" );
    AddItem( aplicacentro, CHAR, REQ, "aplicacentro" );
    AddItem( ajuste, CHAR, REQ, "ajuste" );
    AddItem( boleto_sec, UINT, REQ, "boleto_sec" );
    AddItem( internacional, CHAR, REQ, "internacional" );
    AddItem( referencia, CHAR, REQ, "referencia" );
    AddItem( boleto_nropasajero, CHAR, REQ, "boleto_nropasajero" );
    AddItem( tarifaeconomica, CHAR, REQ, "tarifaeconomica" );
    AddItem( importeover, CHAR, REQ, "importeover" );
    AddItem( ivaover, UFLOAT, REQ, "ivaover" );
    AddItem( boleto_descripcion, CHAR, REQ, "boleto_descripcion" );
    AddItem( consolidador, CHAR, REQ, "consolidador" );
    AddItem( numeroempleado, CHAR, REQ, "numeroempleado" );
    AddItem( expense, UFLOAT, REQ, "expense" );
    AddItem( porc_expense, UFLOAT, REQ, "porc_expense" );
    AddItem( tipoboleto, CHAR, REQ, "tipoboleto" );
    AddItem( oversobre, CHAR, REQ, "oversobre" );
    AddItem( aplicacomparativo, CHAR, REQ, "aplicacomparativo" );
    AddItem( consumed, CHAR, REQ, "consumed" );
    AddItem( fechacreacion, DATE, REQ, "fechacreacion" );
    AddItem( origen, CHAR, REQ, "origen" );
    AddItem( centrocosto, CHAR, REQ, "centrocosto" );
    AddItem( fechasalida, CHAR, REQ, "fechasalida" );
    AddItem( codigopnr, CHAR, REQ, "codigopnr" );
    AddItem( observation, CHAR, REQ, "observation" );
    AddItem( tipoair, CHAR, REQ, "tipoair" );
    AddItem( retransmitido, CHAR, REQ, "retransmitido" );
    AddItem( cantidadboletos, CHAR, REQ, "cantidadboletos" );
    AddItem( vendedor, CHAR, REQ, "vendedor" );
    AddItem( company, CHAR, REQ, "company" );
    AddItem( registrolocalizador, CHAR, REQ, "registrolocalizador" );
    AddItem( transactiontype, CHAR, REQ, "transactiontype" );
    AddItem( fechacreacionair, DATE, REQ, "fechacreacionair" );
    AddItem( officeid, CHAR, REQ, "officeid" );
    AddItem( ruta, CHAR, REQ, "ruta" );
    AddItem( comandocrs, CHAR, REQ, "comandocrs" );
    AddItem( codigocliente, CHAR, REQ, "codigocliente" );
    AddItem( nombrecliente, CHAR, REQ, "nombrecliente" );
    AddItem( version, CHAR, REQ, "version" );
    AddItem( fechamodificacion, DATE, REQ, "fechamodificacion" );
    AddItem( gds, CHAR, REQ, "gds" );
    AddItem( archivo, CHAR, REQ, "archivo" );
    AddItem( interface_id, UINT, REQ, "interface_id" );
    AddItem( indicadorventa, CHAR, REQ, "indicadorventa" );
    AddItem( tipo_prestacion, CHAR, REQ, "tipo_prestacion" );
    AddItem( iata, CHAR, REQ, "iata" );

  } 
} 
