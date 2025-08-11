package pss.common.event;

import pss.common.event.manager.BizEventCode;

public class BizCoreEvent extends BizEventCode {


	public BizCoreEvent() throws Exception {
	}

//	public static final String EVT_MAIL_RECIVE="CORE_MAIL_RECIVE";

	@Override
	public String getEventModule() throws Exception {
		return BizEventCode.MODULE_CORE;
	}
	
	@Override
	public String getModuleDescription() throws Exception {
		return "Core";
	}
	
	@Override
	public void generateModuleEventCodeList() throws Exception {
//		this.create(EVT_MAIL_RECIVE, "Mail Recibido");
	}

	/*public JRecords<BizEvent> getEventCodeList() throws Exception {
		JRecords<BizEvent> oBDs=new JRecords<BizEvent>(BizEvent.class);
		oBDs.addItem(create(MODULO_RETAIL, EVT_FINALIZACION_TRANSACCION, "Ventas", "trans"));
		oBDs.addItem(create(MODULO_RETAIL, EVT_ANULACION_TRX, "Anulación de Venta", "voided"));
		oBDs.addItem(create(MODULO_RETAIL, EVT_DEVOLUCION_TRX, "Devolución de Venta", "refund"));
		oBDs.addItem(create(MODULO_RETAIL, EVT_ELIMINACION_ITEM, "Eliminación de Item en Venta", "items_deleted"));
		oBDs.addItem(create(MODULO_RETAIL, EVT_DESCUENTO_MANUAL, "Descuento Manual", "manual_discounts"));
		oBDs.addItem(create(MODULO_RETAIL, EVT_SOBREPRECIO_MANUAL, "Sobreprecio Manual", "manual_overprice"));
		oBDs.addItem(create(MODULO_RETAIL, EVT_APERTURA_CAJON, "Apertura de Cajón", "drawer_open"));
		oBDs.addItem(create(MODULO_RETAIL, EVT_CASH_DROP, "Tirada de Buzón", "safe_drop"));
		oBDs.addItem(create(MODULO_RETAIL, EVT_FALTANTE_CAJA, "Faltante de Caja", "cash_short"));
		oBDs.addItem(create(MODULO_RETAIL, EVT_SOBRANTE_CAJA, "Sobrante de Caja", "cash_over"));
		oBDs.addItem(create(MODULO_RETAIL, EVT_ANULACION_VTA_EN_CURSO, "Anulación Venta en Curso", "sale_cancellation"));
		oBDs.addItem(create(MODULO_RETAIL, EVT_AUTHORIZE_DUPLICATE_CASH_DROP, "Autoriza Sobre Duplicado", "authorize_duplicate_safe_drop"));
		return oBDs;
	} */

/*	public Class<BizRetailEvent> getAbsoluteClass() {
		return BizRetailEvent.class;
	} */

/*	public String getCampoExportado() {
		return "exportado_evento";
	} */

/*	public static void generarEventoVentaActiva(BizSale zVentaActiva, int zEvento, String[] sOperaciones) throws Exception {

		try {
			String sRef2=null;
			if (zVentaActiva.hasTransaccionSource()) {
				sRef2="Comp.: "+zVentaActiva.getObjTransaccionSource().getNroCompuesto();
			}

			JEventFactory.createEvent(BizRetailEvent.MODULO_RETAIL, zEvento, null, "Cliente: "+zVentaActiva.getObjCustomer().getDescription(), sRef2, null, null, new Double(zVentaActiva.getMontoTotal()), zVentaActiva.getObjPos().getPosConfig().obtenerGrupoCierre().GetGrupo(), sOperaciones);

		} catch (Throwable ex) {
			PssLogger.logDebug(ex);
			// no puedo interrumpir la finalización de una venta por culpa de un maldito evento
			// el problema es que esto va despues de la impresión porque sino bloquea otras
			// ventas hasta que finaliza la impresión. El bloqueo se porque un evento acumulado
			// hace un select en toda la tabla y queda bloqueado por el insert de otra venta.
		}

	}

	public static void generarEventoVentaPos(BizItem zVentaPos, int zEvento, String[] sOperaciones) throws Exception {
		JEventFactory.createEvent(BizRetailEvent.MODULO_RETAIL, zEvento, null, "Item: "+zVentaPos.getObjArticulo().GetDescrip(), zVentaPos.getSale().getObjCustomer().getDescription(), null, null, new Double(zVentaPos.getMontoTotal()), zVentaPos.getSale().getObjPos().getPosConfig().obtenerGrupoCierre().GetGrupo(), sOperaciones);
	}

	public static void generarEventoCambioPrecio(BizItem zVentaPos, int zEvento, double zMonto, String[] sOperaciones) throws Exception {
		JEventFactory.createEvent(BizRetailEvent.MODULO_RETAIL, zEvento, null, "Item: "+zVentaPos.getObjArticulo().GetDescrip(), zVentaPos.getSale().getObjCustomer().getDescription(), null, null, new Double(zMonto), zVentaPos.getSale().getObjPos().getPosConfig().obtenerGrupoCierre().GetGrupo(), sOperaciones);
	} */

}
