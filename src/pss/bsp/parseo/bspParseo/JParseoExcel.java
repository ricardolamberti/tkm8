package pss.bsp.parseo.bspParseo;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import pss.bsp.bo.formato.BizFormato;
import pss.bsp.consolidador.IConciliable;
import pss.bsp.excel.JParseoExcelVinc;
import pss.bsp.parseo.IFinder;
import pss.bsp.pdf.BizPDF;
import pss.bsp.pdf.mex.cabecera.BizMexCabecera;
import pss.bsp.pdf.mex.cabecera.GuiMexCabecera;
import pss.bsp.pdf.mex.detalle.BizMexDetalle;
import pss.bsp.pdf.mex.detalle.GuiMexDetalles;
import pss.bsp.pdf.mex.impuesto.BizMexImpuesto;
import pss.bsp.pdf.mex.impuesto.GuiMexImpuestos;
import pss.bsp.pdf.mex2.IFinderMex2;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.tourism.pnr.BizPNRTicket;

public class JParseoExcel extends JParseoExcelVinc implements IFinderMex2, IBspSititaParser {

	
	public JParseoExcel() {
		this.addListenerDetect(this);
	}
	
	public void detectDetail(BizMexDetalle detail) throws Exception {
		detail.processInsert();
	}

	public void detectHeader(BizMexCabecera header) throws Exception {
		header.processInsert();
	}

	public void stop() throws Exception {
//		JBDatos.GetBases().commit();	
	}

	public void error(Exception e) throws Exception {
	//	JBDatos.GetBases().rollback();	
		throw e;
		
	}

	public void start() throws Exception {
	//	JBDatosv.GetBases().beginTransaction();	
	
	}


	public void detectTax(BizMexImpuesto tax) throws Exception {
		tax.processInsert();
	}

	public JWin getGuiHeader(String zCompany, String zOwner, String zIdPdf) throws Exception {
		GuiMexCabecera cabecera = new GuiMexCabecera();
		cabecera.GetcDato().addFilter("company", zCompany);
//		cabecera.GetcDato().addFilter("owner", zOwner);
		cabecera.GetcDato().addFilter("idPDF", zIdPdf);
		cabecera.GetcDato().read();
		return cabecera;
	}

	public JWins getGuisDetail(String zCompany, String zOwner, String zIdPdf) throws Exception {
		GuiMexDetalles detalle = new GuiMexDetalles();
		detalle.getRecords().addFilter("company", zCompany);
//		detalle.getRecords().addFilter("owner", zOwner);
		detalle.getRecords().addFilter("idPDF", zIdPdf);
		detalle.getRecords().addOrderBy("fecha");
		return detalle;
	}

	public void eliminar(String zCompany, String zOwner, String zIdPdf) throws Exception {
		GuiMexCabecera cabecera = new GuiMexCabecera();
		cabecera.GetcDato().addFilter("company", zCompany);
//		cabecera.GetcDato().addFilter("owner", zOwner);
		cabecera.GetcDato().addFilter("idPDF", zIdPdf);
		cabecera.GetcDato().dontThrowException(true);
		if (cabecera.GetcDato().read())
			cabecera.GetcDato().processDelete();
		
		GuiMexDetalles detalle = new GuiMexDetalles();
		detalle.getRecords().addFilter("company", zCompany);
//		detalle.getRecords().addFilter("owner", zOwner);
		detalle.getRecords().addFilter("idPDF", zIdPdf);
		detalle.getRecords().delete();
		
		GuiMexImpuestos impuestos = new GuiMexImpuestos();
		impuestos.getRecords().addFilter("company", zCompany);
//		impuestos.getRecords().addFilter("owner", zOwner);
		impuestos.getRecords().addFilter("idPDF", zIdPdf);
		impuestos.getRecords().delete();
	}
	
	public void processDks(String zCompany) throws Exception {
		
//		JRecords<BizMexDetalle> dets = new JRecords<BizMexDetalle>(BizMexDetalle.class);
//		dets.addFilter("DK","null","=");
//		dets.readAll();
//		while (dets.nextRecord()) {
//			BizMexDetalle det = dets.getRecord();
//			BizPNRTicket tkt = new BizPNRTicket();
//			tkt.dontThrowException(true);
//			if (!tkt.ReadByBoleto(zCompany, det.getBoleto())) continue;
//			det.setDK(tkt.getCustomerIdReducido());
//			det.update();
//		} 
//		dets.closeRecord();

	}
	
	public void refill(String zCompany, String zOwner, String zIdPdf) throws Exception {
	}

	public boolean fillFormat(BizPDF pdfBase,String[][] learningBase,BizFormato formato) throws Exception {
	  JRecords<BizMexDetalle> details = new JRecords<BizMexDetalle>(BizMexDetalle.class);
	  details.addFilter("company", pdfBase.getCompany());
//	  details.addFilter("owner", pdfBase.getOwner());
	  details.addFilter("idPdf", pdfBase.getIdpdf());
	  JIterator<BizMexDetalle> it = details.getStaticIterator();
	  int cant=0;
	  boolean algunaCoincidencia = false;
	  int cantCoincidenciasBoletos = 0;
	  int cantCoincidenciasBoletosLargo = 0;
	  int cantCoincidenciasAerolineasBoletos = 0;
	  while (it.hasMoreElements()) {
	  	BizMexDetalle detail = it.nextElement();
	  	int[] candidatos = formato.determinarColumna(learningBase,detail.getBoleto(),IConciliable.BOLETOS);
	  	if (candidatos[0]==-1) { // no hay coincidencia
		  	candidatos = formato.determinarColumna(learningBase,detail.getAerolineaBoleto(),IConciliable.AEROLINEA_BOLETOS);
		  	if (candidatos[0]==-1) { // no hay coincidencia
			  	candidatos = formato.determinarColumna(learningBase,detail.getBoletoLargo(),IConciliable.BOLETOS_LARGO);
			   	if (candidatos[0]==-1) continue; // no hay coincidencia
			   	cantCoincidenciasBoletosLargo++;
		  	} else cantCoincidenciasAerolineasBoletos++;
	  	}
	  	else cantCoincidenciasBoletos++;
	  	algunaCoincidencia = true;
	  	formato.determinarColumna(candidatos,learningBase,detail.getFecha(),IConciliable.FECHA);
	  	formato.determinarColumna(candidatos,learningBase,detail.getOperacion(),IConciliable.OPERACION);
	  	formato.determinarColumna(candidatos,learningBase,detail.getTarifa(),IConciliable.TARIFA);
	  	formato.determinarColumna(candidatos,learningBase,detail.getComision(),IConciliable.COMISION);
	  	formato.determinarColumna(candidatos,learningBase,detail.getComisionOver(),IConciliable.COMISION_OVER);
	  	formato.determinarColumna(candidatos,learningBase,detail.getComisionPorc(),IConciliable.COMISION_PORC);
	  	formato.determinarColumna(candidatos,learningBase,detail.getContado(),IConciliable.CONTADO);
	  	formato.determinarColumna(candidatos,learningBase,detail.getTarjeta(),IConciliable.TARJETA);
	  	formato.determinarColumna(candidatos,learningBase,detail.getContadoBruto(),IConciliable.CONTADO_BRUTO);
	  	formato.determinarColumna(candidatos,learningBase,detail.getTarjetaBruto(),IConciliable.TARJETA_BRUTO);
	  	formato.determinarColumna(candidatos,learningBase,detail.getTipoRuta(),IConciliable.TIPO_RUTA);
	  	formato.determinarColumna(candidatos,learningBase,detail.getAerolinea(),IConciliable.AEROLINEA);
	  	formato.determinarColumna(candidatos,learningBase,detail.getIdAerolinea(),IConciliable.ID_AEROLINEA);
	  	formato.determinarColumna(candidatos,learningBase,detail.getImpuesto1(),IConciliable.IMPUESTO_1);
	  	formato.determinarColumna(candidatos,learningBase,detail.getImpuesto2(),IConciliable.IMPUESTO_2);
	  	formato.determinarColumna(candidatos,learningBase,detail.getNumeroTarjeta(),IConciliable.NUMERO_TARJETA);
	  	formato.determinarColumna(candidatos,learningBase,detail.getTipoTarjeta(),IConciliable.TIPO_TARJETA);
	  	formato.determinarColumna(candidatos,learningBase,detail.getObservaciones(),IConciliable.OBSERVACION);
	  	formato.determinarColumna(candidatos,learningBase,detail.getTotal(),IConciliable.TOTALAPAGAR);
	  	formato.determinarColumna(candidatos,learningBase,detail.getNeto(),IConciliable.NETO);
	  	formato.determinarColumna(candidatos,learningBase,detail.getBaseImponible(),IConciliable.BASE_IMPONIBLE);
	  	formato.determinarColumna(candidatos,learningBase,detail.getImpSobrecom(),IConciliable.IMP_SOBRE_COMISION);
	  	formato.determinarColumna(candidatos,learningBase,detail.getComisionInv(),IConciliable.COMISION_INV);
	  	formato.determinarColumna(candidatos,learningBase,detail.getComisionOverInv(),IConciliable.COMISION_OVER_INV);
	  	formato.determinarColumna(candidatos,learningBase,detail.getImpSobrecomInv(),IConciliable.IMP_SOBRE_COMISION_INV);
	  	formato.determinarColumna(candidatos,learningBase,detail.getImpuestoAcum(),IConciliable.IMPUESTO_ACUM);
	  	formato.determinarColumna(candidatos,learningBase,detail.getComisionAcum(),IConciliable.COMISION_ACUM);
	  	formato.determinarColumna(candidatos,learningBase,detail.getComisionAcumInv(),IConciliable.COMISION_ACUM_INV);
	  	formato.determinarColumna(candidatos,learningBase,detail.getTarifaSinComision(),IConciliable.TARIFA_SIN_COMISION);
   }
	  if (!algunaCoincidencia) return false;
	  if (cantCoincidenciasBoletosLargo>cantCoincidenciasBoletos && cantCoincidenciasBoletosLargo>cantCoincidenciasAerolineasBoletos)
	  	setConciliableKey(IConciliable.BOLETOS_LARGO);
	  else if (cantCoincidenciasAerolineasBoletos>cantCoincidenciasBoletos)
	  	setConciliableKey(IConciliable.AEROLINEA_BOLETOS);
	  else
	  	setConciliableKey(IConciliable.BOLETOS);
	  
	  return formato.determineColumns();
	}
	
	String conciliableKey;

	public String getConciliableKey() {
		return conciliableKey;
	}

	public void setConciliableKey(String conciliableKey) {
		this.conciliableKey = conciliableKey;
	}

	@Override
	public boolean supportRefunds() {
		return true;
	}
}
