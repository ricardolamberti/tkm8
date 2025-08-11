package pss.bsp.interfaces.dqb;

import java.io.InputStream;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.icu.util.StringTokenizer;

import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.interfaces.dqb.cabecera.BizDQBCabecera;
import pss.bsp.interfaces.dqb.detalle.BizDQBDetalle;
import pss.bsp.parseo.IFinder;
import pss.bsp.parseo.IParseo;
import pss.core.tools.ExcelTextParser;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;

public class JParseoDQB implements IParseo {

	IFinderDQB finder;

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

	BizDQBCabecera cabecera;

	BizDQBDetalle detalle;

	long nextLinea = 0;

	String actual_aerolinea=null;
	String company;

	String owner;

	String idParser;

	boolean pendingDetail = false;

	public String getTableDetalle() throws Exception {
		return new BizDQBDetalle().GetTable();
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
		this.finder = (IFinderDQB) finder;
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
		detectLinea(linea);
		readyHeader();
	}


	public boolean detectLinea(String linea) throws Exception {
    // La línea es del formato CSV con las columnas especificadas
    // FECHA;GLOBALIZADOR;TIPO DE DOCUMENTO;PLACA;BOLETO;PNR;STATUS
		if (!JTools.isNumber(linea.charAt(0))&&!linea.trim().startsWith(";")) {
			return false;
		}
		StringTokenizer toks = new StringTokenizer(linea, ";");
    int c = 0;

    // Verificamos que tenga suficientes tokens para evitar errores
    int tokenCount = toks.countTokens();
    if (tokenCount < 5) {
        return false;
    }

    closePendingDetail(); // Procesar cualquier detalle pendiente antes de iniciar uno nuevo
    startNewDetail();     // Iniciar un nuevo detalle

    while (toks.hasMoreTokens()) {
        String sCont = toks.nextToken();
        switch (c) {
            case 0: // Columna FECHA
                detalle.setFecha(JDateTools.StringToDate(sCont.trim(),"dd/MM/yyyy"));
                break;
            case 1: // Columna GLOBALIZADOR
                detalle.setGds(sCont.trim());
                break;
            case 2: // Columna TIPO DE DOCUMENTO
                detalle.setTipoDoc(sCont.trim());
                break;
            case 3: // Columna PLACA
                detalle.setIdAerolinea(sCont.trim());
                actual_aerolinea = sCont.trim();
                break;
            case 4: // Columna BOLETO
                detalle.setBoleto((sCont.trim()));
                break;
            case 5: // Columna PNR
                detalle.setPnr(sCont.trim().equalsIgnoreCase("SIN PNR")?"":sCont.trim());
                break;
            case 6: // Columna STATUS
                     detalle.setStatus((sCont.trim()));
          
                break;
            default:
                PssLogger.logInfo("Columna no procesada en línea: " + c + " - " + sCont);
        }
        c++;
    }
    return true;
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
//		detalle.setPeriodoDesde(fechaDesde);
//		detalle.setPeriodoHasta(fechaHasta);
		finder.detectDetail(detalle);
		detalle = new BizDQBDetalle();
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
		cabecera = new BizDQBCabecera();
		detalle = new BizDQBDetalle();
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
			if (!JTools.isNumber(linea.charAt(0))) detectHeader=true;

				return;
	}

	
	private void readyHeader() throws Exception {
		if (!detectHeader) return;

		cabecera.setPeriodoDesde(fechaDesde);
		cabecera.setPeriodoHasta(fechaHasta);
		idPdf=BizBSPUser.getUsrBSP().getObjBSPPais().getPais()+"_"+JDateTools.DateToString(cabecera.getPeriodoDesde());

		cabecera.setCompany(company);
		cabecera.setOwner(owner);
		cabecera.setIdpdf(idPdf);

		cabecera.setDescripcion("Interfaz de DQB del "+JDateTools.DateToString(cabecera.getPeriodoDesde())+" al "+JDateTools.DateToString(cabecera.getPeriodoHasta()));
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
