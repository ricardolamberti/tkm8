package pss.bsp.parseo.bspParseo;

import pss.bsp.bo.formato.BizFormato;
import pss.bsp.consolidador.IConciliable;
import pss.bsp.pdf.BizPDF;
import pss.bsp.pdf.arg.cabecera.BizArgCabecera;
import pss.bsp.pdf.arg.cabecera.GuiArgCabecera;
import pss.bsp.pdf.arg.detalle.BizArgDetalle;
import pss.bsp.pdf.arg.detalle.GuiArgDetalles;
import pss.bsp.pdf.arg.impuesto.BizArgImpuesto;
import pss.bsp.pdf.arg.impuesto.GuiArgImpuestos;
import pss.bsp.pdf.arg2.IFinderArg2;
import pss.bsp.pdf.arg2.JParseoArg2;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;

/// Aqui se como parsear y que hacer con los datos
/// es la clase mas especializada y lo que hace es parsear los datos y guardarlos en la base de datos

public class JParseoArgentina2 extends JParseoArg2 implements IFinderArg2, IBspSititaParser {

	
	public JParseoArgentina2() {
		this.addListenerDetect(this);
	}
	
	public void detectDetail(BizArgDetalle detail) throws Exception {
		detail.processInsert();
	}

	public void detectHeader(BizArgCabecera header) throws Exception {
		header.processInsert();
	}

	public void stop() throws Exception {
//		JBDatos.GetBases().commit();	
	}

	public void error(Exception e) throws Exception {
	//	JBDatos.GetBases().rollback();	
		throw e;
		
	}
	public void processDks(String zCompany) throws Exception {
		
	}

	public void start() throws Exception {
	//	JBDatos.GetBases().beginTransaction();	
	
	}

	public void detectTax(BizArgImpuesto tax) throws Exception {
		tax.processInsert();
	}

	public JWin getGuiHeader(String zCompany, String zOwner, String zIdPdf) throws Exception {
		GuiArgCabecera cabecera = new GuiArgCabecera();
		cabecera.GetcDato().addFilter("company", zCompany);
//		cabecera.GetcDato().addFilter("owner", zOwner);
		cabecera.GetcDato().addFilter("idPDF", zIdPdf);
		cabecera.GetcDato().read();
		return cabecera;
	}

	public JWins getGuisDetail(String zCompany, String zOwner, String zIdPdf) throws Exception {
		GuiArgDetalles detalle = new GuiArgDetalles();
		detalle.getRecords().addFilter("company", zCompany);
//		detalle.getRecords().addFilter("owner", zOwner);
		detalle.getRecords().addFilter("idPDF", zIdPdf);
		detalle.getRecords().addOrderBy("fecha");
		return detalle;
	}

	public void eliminar(String zCompany, String zOwner, String zIdPdf) throws Exception {
		GuiArgCabecera cabecera = new GuiArgCabecera();
		cabecera.GetcDato().addFilter("company", zCompany);
//		cabecera.GetcDato().addFilter("owner", zOwner);
		cabecera.GetcDato().addFilter("idPDF", zIdPdf);
		cabecera.GetcDato().dontThrowException(true);
		if (cabecera.GetcDato().read())
			cabecera.GetcDato().processDelete();
		
		GuiArgDetalles detalle = new GuiArgDetalles();
		detalle.getRecords().addFilter("company", zCompany);
//		detalle.getRecords().addFilter("owner", zOwner);
		detalle.getRecords().addFilter("idPDF", zIdPdf);
		detalle.getRecords().delete();
		
		GuiArgImpuestos impuestos = new GuiArgImpuestos();
		impuestos.getRecords().addFilter("company", zCompany);
//		impuestos.getRecords().addFilter("owner", zOwner);
		impuestos.getRecords().addFilter("idPDF", zIdPdf);
		impuestos.getRecords().delete();
	}
	
	public void refill(String zCompany, String zOwner, String zIdPdf) throws Exception {
	}

	public boolean fillFormat(BizPDF pdfBase,String[][] learningBase,BizFormato formato) throws Exception {
	  JRecords<BizArgDetalle> details = new JRecords<BizArgDetalle>(BizArgDetalle.class);
	  details.addFilter("company", pdfBase.getCompany());
//	  details.addFilter("owner", pdfBase.getOwner());
	  details.addFilter("idPdf", pdfBase.getIdpdf());
	  JIterator<BizArgDetalle> it = details.getStaticIterator();
	  int cant=0;
	  boolean algunaCoincidencia = false;
	  int cantCoincidenciasBoletos = 0;
	  int cantCoincidenciasBoletosLargo = 0;
	  int cantCoincidenciasAerolineasBoletos = 0;
	  while (it.hasMoreElements()) {
	  	BizArgDetalle detail = it.nextElement();
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
	@Override
	public boolean supportRefunds() {
		return false;
	}
}
