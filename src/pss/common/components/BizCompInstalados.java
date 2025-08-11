package  pss.common.components;

import java.util.HashMap;

import pss.JPss;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;

public class BizCompInstalados extends JRecords<BizCompInstalado> {

//	public static final String MOD_STOCK="pss.erp.Stock.";
	public static final String MOD_PROV="pss.erp.supplier.";
	public static final String MOD_CORE_PERSONAS="pss.common.personalData.";
	public static final String MOD_CORE_MEASURERS="pss.common.regions.measureUnit";
	public static final String MOD_PAGOSDIF="pss.erp.payments.PagosDiferenciados.";
	public static final String MOD_ADDITIONAL_DATA="pss.erp.pos.additionaldata.";

	private static HashMap<String, Boolean> installedComponents=new HashMap<String, Boolean>(64);

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizCompInstalados() throws Exception {
	}

	@Override
	public Class<BizCompInstalado> getBasedClass() {
		return BizCompInstalado.class;
	}

	@Override
	public String GetTable() {
		return "";
	}

	@Override
	public boolean readAll() throws Exception {
		String sDirectorio=this.getFilterValue("comp_padre");
		boolean bDinamycModule=this.getFilterValue("dynamic_module").equals("S");
		// File oFile = new File(JTools.PssPath()+"/"+sDirectorio);

		this.setStatic(true);
		BizCompInstalado oComp=new BizCompInstalado();
		oComp.pComponente.setValue(sDirectorio);
		this.appendRecords(oComp.getSubComponentesModulos(bDinamycModule));
		return true;
		/*
		 * if ( subs == null ) return; JIterator iter = oComp.getSubModulos().getIterator(); while ( iter.hasMoreElements() ) { BizCompInstalado compModulo = (BizCompInstalado) iter.nextElement(); if ( !bDinamycModule && !compModulo.getModulo().isModuleComponent() ) continue; this.AddItem(compModulo); }
		 */
		// Pongo un filtro para acotar la busqueda de archivos
		/*
		 * FileFilter filter = new FileFilter() { public boolean accept(File zFile) { return BizCompInstalado.isDirectory(zFile); }}; File aFile[] = oFile.listFiles(filter) ; String sFileName; for ( int i=0; i < aFile.length ; i++ ) { sFileName = aFile[i].getName();
		 * 
		 * BizCompInstalado oComp = new BizCompInstalado(); oComp.pComponente.SetValor(sDirectorio + "/" + sFileName); oComp.pCompPadre.SetValor(sDirectorio); if ( ! oComp.isModule(sDinamycModule.equals("S")) ) continue; oComp.pClase.SetValor(oComp.ObtenerModulo()); this.AddItem(oComp); } this.SetEstatico(true); }
		 */

	}

	/*
	 * private boolean hasWellKnownExtension(String zFileName) { return zFileName.endsWith(".gif") || zFileName.endsWith(".jpg") || zFileName.endsWith(".wav") || zFileName.endsWith(".zip") || zFileName.endsWith(".jar") || zFileName.endsWith(".ini") || zFileName.endsWith(".dat"); }
	 */

	public static boolean ifInstallAdhesionVentas() throws Exception {
		return ifInstall("Adhesion/Ventas");
	}

	public static boolean ifInstallRetailFiscalPrinters() throws Exception {
		return ifInstall("erp/fiscalPrinters");
	}

	public static boolean ifInstallAdhesionMensajes() throws Exception {
		return ifInstall("Adhesion/Mensajes");
	}

	public static boolean ifInstallAdhesionRegalos() throws Exception {
		return ifInstall("Adhesion/Regalos");
	}

	public static boolean ifInstallAdhesionTags() throws Exception {
		return ifInstall("Adhesion/Tags");
	}

	public static boolean ifInstallAdhesionTagsVentas() throws Exception {
		return ifInstall("Adhesion/Tags/Ventas");
	}

	public static boolean ifInstallAdhesionVideos() throws Exception {
		return ifInstall("Adhesion/Videos");
	}

	public static boolean ifInstallAdhesionLoyalty() throws Exception {
		return ifInstall("Adhesion/Loyalty");
	}

	public static boolean ifInstallAdhesionTagsLoyalty() throws Exception {
		return ifInstall("Adhesion/Tags/Loyalty");
	}

	public static boolean ifInstallTagsMaestros() throws Exception {
		return ifInstall("Adhesion/TagMaestros");
	}

//	public static JWins GuiAutomaticsPos() throws Exception {
//		return (JWins) Class.forName("pss.erp.addIns.AddInPump.pumpAutomaticPos.GuiAutomaticsPos").newInstance();
//	}

	public static JWin GuiReporteVentasXTarjeta() throws Exception {
		return (JWin) Class.forName("pss.erp.addIns.AddInTarj.GuiReporteVentasXTarjeta").newInstance();
	}

	public static boolean ifInstallTourism() throws Exception {
		return ifInstall("tourism");
	}

	public static boolean ifInstallSupplier() throws Exception {
		return ifInstall("erp/supplier");
	}

	public static boolean ifInstallRetailDifLista() throws Exception {
		return ifInstall("erp/ListasDiferenciadas");
	}

	public static JRecord BizDifLista() throws Exception {
		return (JRecord) Class.forName("pss.erp.specialPriceList.BizDifLista").newInstance();
	}

//	public static JRecord BizRMpcConfig() throws Exception {
//		return (JRecord) Class.forName("pss.erp.addIns.AddInMPC.BizRMpcConfig").newInstance();
//	}

	public static JRecord BizCtaCteConfigRetail() throws Exception {
		return (JRecord) Class.forName("pss.erp.ctacte.AddInCtacte.BizCtaCteConfigRetail").newInstance();
	}

	public static JRecord BizPOS() throws Exception {
		return (JRecord) Class.forName("pss.erp.pos.config.BizPosConfig").newInstance();
	}

	public static JWins GuiDifListas() throws Exception {
		return (JWins) Class.forName("pss.erp.specialPriceList.GuiDifListas").newInstance();
	}

	public static JWin GuiTourismCountry() throws Exception {
		return (JWin) Class.forName("pss.tourism.company.country.GuiTourismCountry").newInstance();
	}

	public static JWin GuiDifLista() throws Exception {
		return (JWin) Class.forName("pss.erp.specialPriceList.GuiDifLista").newInstance();
	}

	public static JWins GuiDifListasCompania() throws Exception {
		return (JWins) Class.forName("pss.erp.specialPriceList.GuiDifListasCompania").newInstance();
	}

	public static JWin GuiReporteListas() throws Exception {
		return (JWin) Class.forName("pss.erp.specialPriceList.Reportes.GuiReporteListas").newInstance();
	}

	public static JWin GuiRendicionCajaRetail() throws Exception {
		return (JWin) Class.forName("pss.erp.cashDrawer.AddinRetail.GuiRendicionCajaRetail").newInstance();
	}

	public static boolean ifInstallRetailComprobantes() throws Exception {
		return ifInstall("erp/Comprobantes");
	}

	public static JRecord BizTipoComprobante() throws Exception {
		return (JRecord) Class.forName("pss.erp.documentType.BizTipoComprobante").newInstance();
	}

	public static JWins GuiTipoComprobantes() throws Exception {
		return (JWins) Class.forName("pss.erp.documentType.GuiTipoComprobantes").newInstance();
	}

	public static boolean ifInstallRetailAddInCtacte() throws Exception {
		return ifInstall("erp/ctacte/AddInCtacte");
	}

	public static boolean ifInstallRetailAddInCardDiscount() throws Exception {
		return ifInstall("erp/ctacte/AddInCardDiscount");
	}

	public static boolean ifInstallPump() throws Exception {
		return ifInstall("fc");
	}

	public static JWin GuiFactEntrega() throws Exception {
		return (JWin) Class.forName("pss.erp.ctacte.AddInCtacte.GuiFactEntrega").newInstance();
	}

	public static JRecord BizFactEntrega() throws Exception {
		return (JRecord) Class.forName("pss.erp.ctacte.AddInCtacte.BizFactEntrega").newInstance();
	}

	public static JWin GuiCtaCteConfigRetail() throws Exception {
		return (JWin) Class.forName("pss.erp.ctacte.AddInCtacte.GuiCtaCteConfigRetail").newInstance();
	}

	public static JWins GuiCardsDiscountConfigRetail() throws Exception {
		return (JWins) Class.forName("pss.erp.ctacte.AddInCardDiscount.GuiCardsDiscountConfig").newInstance();
	}

	public static JWins GuiCardsDiscountRetail() throws Exception {
		return (JWins) Class.forName("pss.erp.ctacte.AddInCardDiscount.GuiCardsDiscount").newInstance();
	}

//	public static JRecord RetailStockBizStockBlender() throws Exception {
//		return (JRecord) Class.forName(MOD_STOCK+"BizStockBlender").newInstance();
//	}
//
//	public static JWins RetailStockGuiStockBlenders() throws Exception {
//		return (JWins) Class.forName(MOD_STOCK+"GuiStockBlenders").newInstance();
//	}

	public static JWins RetailProveedoresGuiProvArticulos() throws Exception {
		return (JWins) Class.forName(MOD_PROV+"cost.GuiSupplierProducts").newInstance();
	}

//	public static JWins RetailStockGuiTelemedidores() throws Exception {
//		return (JWins) Class.forName(MOD_STOCK+"Telemedidor.GuiStockTelemedidores").newInstance();
//	}

//	public static JWins CoreMeasurersGuiMeasurers() throws Exception {
//		return (JWins) Class.forName(MOD_CORE_MEASURERS+"GuiMeasurers").newInstance();
//	}

//	public static JRecord RetailStockBizStock() throws Exception {
//		return (JRecord) Class.forName(MOD_STOCK+"BizStock").newInstance();
//	}

//	public static JRecord RetailPumpBizMapeoProducto() throws Exception {
//		return (JRecord) Class.forName("pss.erp.addIns.AddInPump.articulos.BizMapeoProducto").newInstance();
//	}

	public static JRecord RetailVentasBizTransaccion() throws Exception {
		return (JRecord) Class.forName("pss.erp.transactions.BizTransaccion").newInstance();
	}

	public static JWin RetailVentasGuiTransaccion() throws Exception {
		return (JWin) Class.forName("pss.erp.transactions.GuiTransaccion").newInstance();
	}

	public static JWin RetailGuiCardTransaction() throws Exception {
		return (JWin) Class.forName("pss.erp.payments.cardExtern.transaction.GuiCardTransaction").newInstance();
	}

	public static JWin RetailGuiCheque() throws Exception {
		return (JWin) Class.forName("pss.erp.payments.checks.GuiCheque").newInstance();
	}
	
	public static JWins CoreMeasurersGuiMeasurers() throws Exception {
		return (JWins) Class.forName(MOD_CORE_MEASURERS+"GuiMeasurers").newInstance();
	}



//	public static JRecord BizMapeoDescuentoProducto() throws Exception {
//		return (JRecord) Class.forName("pss.erp.addIns.AddInPump.interfaces.BizMapeoDescuentoProducto").newInstance();
//	}

//	public static JRecord BizMapeoArticuloServipag() throws Exception {
//		return (JRecord) Class.forName("pss.erp.Servipag.BizServipagMapeoArt").newInstance();
//	}

//	public static JRecord RetailStockBizDeposito() throws Exception {
//		return (JRecord) Class.forName(MOD_STOCK+"Deposito.BizDeposito").newInstance();
//	}

//	public static Class<?> RetailTelemedidorBizStockTelemedidorClass() throws Exception {
//		return Class.forName("pss.erp.Stock.Telemedidor.BizStockTelemedidor");
//	}
//
//	public static JRecord RetailTelemedidorBizStockTelemedidor() throws Exception {
//		return (JRecord) RetailTelemedidorBizStockTelemedidorClass().newInstance();
//	}

//	public static JWin RetailStockGuiStock() throws Exception {
//		return (JWin) Class.forName(MOD_STOCK+"GuiStock").newInstance();
//	}
//
//	public static JWins RetailStockGuiStocks() throws Exception {
//		return (JWins) Class.forName(MOD_STOCK+"GuiStocks").newInstance();
//	}
//
//	public static JWins RetailStockGuiDepositos() throws Exception {
//		return (JWins) Class.forName(MOD_STOCK+"Deposito.GuiDepositos").newInstance();
//	}


//	public static JRecord BizServipagMapeoArt() throws Exception {
//		return (JRecord) Class.forName("pss.erp.Servipag.BizServipagMapeoArt").newInstance();
//	}
//
//	public static JWins GuiServipagMapeoArts() throws Exception {
//		return (JWins) Class.forName("pss.erp.Servipag.GuiServipagMapeoArts").newInstance();
//	}

	public static JWins CorePersonasGuiEstadosCiviles() throws Exception {
		return (JWins) Class.forName(MOD_CORE_PERSONAS+"GuiEstadosCiviles").newInstance();
	}

	public static JWins RetailAdditionalDataGuiAdditionalDataHeaders() throws Exception {
		return (JWins) Class.forName(MOD_ADDITIONAL_DATA+"GuiAdditionalDataHeaders").newInstance();
	}

//	public static JWins GuiMapeoDescuentoProductos() throws Exception {
//		return (JWins) Class.forName("pss.erp.addIns.AddInPump.interfaces.GuiMapeoDescuentoProductos").newInstance();
//	}

//	public static boolean ifInstallPssAdmin() throws Exception {
//		return ifInstall("common/security/PssAdmin");
//	}

	public static boolean ifInstallCoreInterfaces() throws Exception {
		return ifInstall("core/Interfaces");
	}

//	public static boolean ifInstallPumpInterfaces() throws Exception {
//		return ifInstall("erp/AddIns/AddInPump/interfaces");
//	}

	public static JRecord BizInterfazConfig() throws Exception {
		return (JRecord) Class.forName("pss.core.Interfaces.BizInterfazConfig").newInstance();
	}

	public static boolean ifInstallRetailVentas() throws Exception {
		return ifInstall("erp/Ventas");
	}

	public static JWins GuiTransacciones() throws Exception {
		return (JWins) Class.forName("pss.erp.transactions.GuiTransacciones").newInstance();
	}

	public static JRecord BizInterfazCierre() throws Exception {
		return (JRecord) Class.forName("pss.core.Interfaces.BizInterfazCierre").newInstance();
	}

	public static boolean ifInstallRetailPos() throws Exception {
		return ifInstall("erp/pos");
	}


	public static boolean ifInstallCorePersonas() throws Exception {
		return ifInstall("common/personalData");
	}

	public static boolean ifInstallEvents() throws Exception {
		return ifInstall("common/event");
	}

	public static JRecord BizEvent() throws Exception {
		return (JRecord) Class.forName("Pss.common.event.BizEvent").newInstance();
	}

	public static boolean ifInstallRetailProveedores() throws Exception {
		return ifInstall("erp/supplier");
	}

//	public static boolean ifInstallRetailStock() throws Exception {
//		return ifInstall("erp/Stock");
//	}

//	public static boolean ifInstallRetailStockTelemedidor() throws Exception {
//		return ifInstall("erp/Stock/Telemedidor");
//	}

	public static boolean ifInstallRetailCategorias() throws Exception {
		return ifInstall("erp/Articulos/Categorias");
	}

	public static boolean ifInstallCashDrop() throws Exception {
		return ifInstall("erp/Caja/CashDrop");
	}

//	public static boolean ifInstallRetailPagoFacil() throws Exception {
//		return ifInstall("erp/AddIns/AddInPagoFacil");
//	}

	public static boolean ifInstallRetailCtacte() throws Exception {
		return ifInstall("erp/ctacte");
	}

	public static boolean ifInstallRetailCajaRetail() throws Exception {
		return ifInstall("erp/Caja/AddinRetail");
	}

	public static boolean ifInstallRetailPagosDiferenciados() throws Exception {
		return ifInstall("erp/MedioPago/PagosDiferenciados");
	}

	public static boolean ifInstallRetailArticulos() throws Exception {
		return ifInstall("erp/Articulos");
	}

	public static boolean ifInstallRetailArticulosBasicos() throws Exception {
		return ifInstall("erp/Articulos/Basicos");
	}

	public static boolean ifInstallRetailArticulosAgrupamientos() throws Exception {
		return ifInstall("erp/Articulos/Agrupamientos");
	}

	public static boolean ifInstallRetailArticulosCombustibles() throws Exception {
		return ifInstall("erp/Articulos/Combustibles");
	}

	public static boolean ifInstallRetailArticulosElaborados() throws Exception {
		return ifInstall("erp/Articulos/Elaborados");
	}

	public static boolean ifInstallRetailArticulosPaidInOut() throws Exception {
		return ifInstall("erp/products/PaidInOut");
	}

	public static boolean ifInstallRetailArticulosVentaFinanciera() throws Exception {
		return ifInstall("erp/products/VentaFinanciera");
	}

	public static boolean ifInstallRetailMedioPago() throws Exception {
		return ifInstall("erp/MedioPago");
	}

//	public static boolean ifInstallRetailAddInMPC() throws Exception {
//		return ifInstall("erp/AddIns/AddInMPC");
//	}

//	public static boolean ifInstallRetailServipag() throws Exception {
//		return ifInstall("erp/Servipag");
//	}

	public static boolean ifInstallRetailAddInPump() throws Exception {
		return ifInstall("erp/AddIns/AddInPump");
	}

	public static boolean ifInstallRetailAddInEpay() throws Exception {
		return ifInstall("erp/AddIns/AddInTarj");
	}

//	public static boolean ifInstallRetailAddInPagoFacil() throws Exception {
//		return ifInstall("erp/AddIns/AddInPagoFacil");
//	}


	public static boolean ifInstallTurism() throws Exception {
		return ifInstall("tourism");
	}

	public static boolean ifInstallRetailPluginAdditionalData() throws Exception {
		return ifInstall("erp/pos/additionaldata");
	}

//	public static boolean ifInstallRetailAddinGNC() throws Exception {
//		return ifInstall("erp/AddIns/AddInGNC");
//	}

//	public static boolean ifInstallYPFPlugIns() throws Exception {
//		return ifInstall("YPFPlugins");
//	}

	private static synchronized boolean ifInstall(String zComp) throws Exception {
		Boolean oValue=installedComponents.get(zComp);
		if (oValue==null) {
			oValue=new Boolean(JPss.class.getResource(zComp)!=null);
			installedComponents.put(zComp, oValue);
		}
		return oValue.booleanValue();
	}

//	public static boolean ifInstallRetailAutomaticPOS() throws Exception {
//		return ifInstall("erp/AddIns/AddInPump/pumpAutomaticPos");
//	}
//
//	public static boolean ifInstallRetailAddInSellout() throws Exception {
//		return ifInstall("erp/AddIns/AddInPump/SellOut");
//	}

//	public static JRecord BizSellOutConfig() throws Exception {
//		return (JRecord) Class.forName("pss.erp.addIns.AddInPump.SellOut.BizSellOutConfig").newInstance();
//	}

	// RM verifica si esta instalado Pss/erp/Clientes
	public static boolean ifInstallRetailClientes() throws Exception {
		return ifInstall("erp/Clientes");
	}

	// RM Instancia la clase BizCliente por refletion
	public static JRecord BizCliente() throws Exception {
		return (JRecord) Class.forName("pss.erp.customers.BizCliente").newInstance();
	}

	public static JWins GuiTablasDeConversionDeTanques() throws Exception {
		return (JWins) Class.forName("pss.ho.tank.configuration.GuiTankConversionTables").newInstance();
	}
	
}
