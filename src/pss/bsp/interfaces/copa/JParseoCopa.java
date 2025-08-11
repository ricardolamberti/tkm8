package pss.bsp.interfaces.copa;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pss.bsp.parseo.IFinder;
import pss.bsp.parseo.IParseo;
import pss.common.security.BizUsuario;
import pss.bsp.bo.arg.detalle.BizArgDetalle;
import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.interfaces.copa.cabecera.BizCopaCabecera;
import pss.bsp.interfaces.copa.detalle.BizCopaDetalle;
import pss.core.tools.ExcelTextParser;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PDFTextParser;
import pss.core.tools.PssLogger;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.StringTokenizer;

public class JParseoCopa implements IParseo {

	IFinderCopa finder;

	InputStream input;

	String idPdf;


	Date fechaDesde;

	Date fechaHasta;

	boolean detectHeader = true;


	String conciliableKey;

	public String getConciliableKey() {
		return conciliableKey;
	}

	public void setConciliableKey(String conciliableKey) {
		this.conciliableKey = conciliableKey;
	}

	BizCopaCabecera cabecera;

	BizCopaDetalle detalle;

	long nextLinea = 0;

	String actual_aerolinea=null;
	String company;

	String owner;

	String idParser;

	boolean pendingDetail = false;

	public String getTableDetalle() throws Exception {
		return new BizCopaDetalle().GetTable();
	}

	public void setIdParseador(String idClase) throws Exception {
		idParser = idClase;
	}

	public String getIdPareador() {
		return idParser;
	}

	public void setCompany(String zCompany) throws Exception {
		company = zCompany;
	}

	public void setOwner(String zOwner) throws Exception {
		owner = zOwner;
	}

	public String getId() throws Exception {
		return idPdf;
	}

	public void setId(String zId) throws Exception {
		// ignorado, porque este parser genera su propio id
	}

	public void addListenerDetect(IFinder finder) {
		this.finder = (IFinderCopa) finder;
	}

	private String getContent() {
		
		ExcelTextParser parser=new ExcelTextParser();
		return parser.toCSV(input);
	}

	protected void privExecute() throws Exception {

		String content = getContent();

		if (content == null) throw new Exception("El archivo no parece ser una interfaz BSP");
		// implementar parseo aqui
		// cuando se descubra el encabezado llamar a finder.detectHeader(header)
		// cuando se descubra el detalle llamar a finder.detectDetail(detail)
		// en le camino llenar idPdf, fechadesde y fechahasta
		for (String linea : content.split("\r\n|\r|\n")) {
			PssLogger.logInfo(linea);
			parserLinea(linea);
		}
		closePendingDetail();
	}

	protected void parserLinea(String linea) throws Exception {
		if (detectHeader) detectHeader(linea);
		if(!detectLinea3(linea))
			if(!detectLinea(linea))
				detectLinea2(linea);
		readyHeader();
	}

	public boolean detectLinea3(String linea) throws Exception {
		// 5992039919 5 VVVV 21/02
		Pattern p = Pattern.compile("([A-Z]){3};([A-Z]){3};([A-Z]){3};([A-Z]){3};([A-Z]){6};*;");
		Matcher m = p.matcher(linea);
		boolean b = m.find();
		if (!b) {
			return false;
		}
		closePendingDetail();
		startNewDetail();
		int c=0;
		StringTokenizer toks = new StringTokenizer(linea,";");
		while (toks.hasMoreTokens()) {
			String sCont = toks.nextToken();
			switch (c) {
			case 2:
				detalle.setOrigen(sCont);
				break;// origen
			case 3:
				detalle.setDestino(sCont);
				break;// destino
			case 4:
				detalle.setMarketingID(sCont);
				break;// destino
			case 5:
				detalle.setFMS(getDouble(sCont));
				break;// fms
			}
			c++;
		}
		return true;
	}
	public boolean detectLinea(String linea) throws Exception {
		// 5992039919 5 VVVV 21/02
		Pattern p = Pattern.compile("([A-Z]){3};([A-Z]){3};([A-Z]){6};([A-Z]){2};");
		Matcher m = p.matcher(linea);
		boolean b = m.find();
		if (!b) {
			return false;
		}
		closePendingDetail();
		startNewDetail();
		int c=0;
		StringTokenizer toks = new StringTokenizer(linea,";");
		while (toks.hasMoreTokens()) {
			String sCont = toks.nextToken();
			switch (c) {
			case 0:
				detalle.setOrigen(sCont);
				break;// origen
			case 1:
				detalle.setDestino(sCont);
				break;// destino
			case 2:
				detalle.setMarketingID(sCont);
				break;// marketing
			case 3:
				detalle.setIdAerolinea(sCont);
				actual_aerolinea=sCont;
				break;// aerolinea
			case 4:
				detalle.setFMS(getDouble(sCont));
				break;// fms
			case 5:
				detalle.setMinimo(getDouble(sCont));
				break;// minimo
			}
			c++;
		}
		return true;
	}
	public boolean detectLinea2(String linea) throws Exception {
		// 5992039919 5 VVVV 21/02
		Pattern p2 = Pattern.compile("([A-Z]){3};([A-Z]){6};");
		Matcher m2 = p2.matcher(linea);
		boolean b2 = m2.find();
		if (!b2)
			return false;
		closePendingDetail();
		startNewDetail();
		int c=0;
		StringTokenizer toks = new StringTokenizer(linea,";");
		while (toks.hasMoreTokens()) {
			String sCont = toks.nextToken();
			switch (c) {
			case 0:
				detalle.setOrigen(sCont);
				break;// origen
			case 1:
				detalle.setDestino(sCont.substring(3));
				break;// destino
			case 2:
				//detalle.setFMS(getDouble(JTools.getNumberEmbeddedWithDecSigned(sCont)));
				break;// fms
			case 3:
				detalle.setMinimo(getDouble(sCont));
				break;// minimo
			}
			c++;
		}
		return b2;
	}
	public void startNewDetail() throws Exception {
		pendingDetail = true;
		nextLinea++;
	}

	public void closePendingDetail() throws Exception {
		if (pendingDetail) {
//			if (actual_operacion.indexOf("TICKET")!=-1 && actual_aerolinea.indexOf("IATA")==-1) {
				readyDetail();
		}
		pendingDetail = false;
	}



	private void readyDetail() throws Exception {
		detalle.setCompany(company);
		detalle.setOwner(owner);
		detalle.setIdpdf(idPdf);
		detalle.setLinea(nextLinea);
		detalle.setPeriodoDesde(fechaDesde);
		detalle.setPeriodoHasta(fechaHasta);
		finder.detectDetail(detalle);
		detalle = new BizCopaDetalle();
	}

	private double getDouble(String linea) throws Exception {
		if (linea.trim().length() == 0) return 0;
		String num = linea.replaceAll("\\,", "");
		try {
			boolean neg = linea.indexOf("-") != -1;
			num = num.replace(",", ".");
			num = JTools.getNumberEmbeddedWithSep(num);
			return (neg ? -1 : 1) * Double.parseDouble(num);
		} catch (Exception e) {
		  PssLogger.logError("Parser Error "+num+" en "+linea);
			throw e;
		}
	}


	public void execute() throws Exception {
		cabecera = new BizCopaCabecera();
		detalle = new BizCopaDetalle();
		try {
			finder.start();
			privExecute();
			finder.stop();
		} catch (Exception e) {
			finder.error(e);
		}
	}
	String filename;
	public void setFilename(String input) throws Exception {
		this.filename = input;
	}
	
	public void setInput(InputStream input) throws Exception {
		this.input = input;
	}

	public void setTypeFile(String zTypeFile) throws Exception {
		// no es de interes para este parseador
	}

	public String getFormat(String zFormat) throws Exception {
		return null; // no es de interes para este parseador
	}

	public void setFormat(String zFormat) throws Exception {
		// no es de interes para este parseador
	}

	public void setFechaDesde(Date zFecha) throws Exception {
		fechaDesde=zFecha;
	}

	public void setFechaHasta(Date zFecha) throws Exception {
		fechaHasta=zFecha;
	}

	public String getFormat() throws Exception {
		// no es de interes para este parseador
		return null;
	}



	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// HEADER
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	private void detectHeader(String linea) throws Exception {
		detectHeaderSingle(linea);
	}
	private void detectHeaderSingle(String linea) throws Exception {
		// QO 31,10

//			int mes=0;
//			if (linea.toLowerCase().indexOf("ene")!=-1) mes=1;  
//			if (linea.toLowerCase().indexOf("feb")!=-1) mes=2;  
//			if (linea.toLowerCase().indexOf("mar")!=-1) mes=3;  
//			if (linea.toLowerCase().indexOf("abr")!=-1) mes=4;  
//			if (linea.toLowerCase().indexOf("may")!=-1) mes=5;  
//			if (linea.toLowerCase().indexOf("jun")!=-1) mes=6;  
//			if (linea.toLowerCase().indexOf("jul")!=-1) mes=7;  
//			if (linea.toLowerCase().indexOf("ago")!=-1) mes=8;  
//			if (linea.toLowerCase().indexOf("sep")!=-1) mes=9;  
//			if (linea.toLowerCase().indexOf("set")!=-1) mes=9;  
//			if (linea.toLowerCase().indexOf("oct")!=-1) mes=10;  
//			if (linea.toLowerCase().indexOf("nov")!=-1) mes=11;  
//			if (linea.toLowerCase().indexOf("dic")!=-1) mes=12;
//			if (mes==0) return;
//			
//			String sanio=JTools.getNumberEmbedded(linea);
//			if (sanio.length()==0) return;
//			int anio = Integer.parseInt(sanio);
//			
//			if (anio==0) return;
//			if (anio<100) anio+=2000;
//			Calendar fecha = Calendar.getInstance();
//			fecha.set(Calendar.DAY_OF_MONTH, 1);
//			fecha.set(Calendar.MONTH, mes-1);
//			fecha.set(Calendar.YEAR, anio);
			cabecera.setPeriodoDesde(fechaDesde);
			cabecera.setPeriodoHasta(fechaHasta);
			return;
	}

	
	private void readyHeader() throws Exception {
		if (!detectHeader) return;


		

		idPdf=BizBSPUser.getUsrBSP().getObjBSPPais().getPais()+"_"+actual_aerolinea+"_"+JDateTools.DateToString(cabecera.getPeriodoDesde());

		cabecera.setCompany(company);
		cabecera.setOwner(owner);
		cabecera.setIdpdf(idPdf);
		cabecera.setAerolinea(actual_aerolinea);
		cabecera.setDescripcion("Interfaz de "+BizBSPUser.getUsrBSP().getObjBSPPais().getPais()+" aerolínea "+actual_aerolinea+" del "+JDateTools.DateToString(cabecera.getPeriodoDesde()));
		finder.detectHeader(cabecera);
		detectHeader = false;
	}

	@Override
	public Date getPeriodoDesde() throws Exception {
		return cabecera.getPeriodoDesde();
	}

	@Override
	public Date getPeriodoHasta() throws Exception {
		return cabecera.getPeriodoHasta();
	}

	@Override
	public String getIATA() throws Exception {
		return null;
	}



}
