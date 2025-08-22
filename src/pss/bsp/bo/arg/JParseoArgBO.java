package  pss.bsp.bo.arg;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import pss.bsp.bo.arg.cabecera.BizArgCabecera;
import pss.bsp.bo.arg.detalle.BizArgDetalle;
import pss.bsp.parseo.IFinder;
import pss.bsp.parseo.IParseo;
import pss.core.tools.PDFTextParser;


public class JParseoArgBO implements IParseo {

	IFinderArgBO finder;
	InputStream input;
	String idPdf;
	Date fechaDesde;
	Date fechaHasta;
	BizArgCabecera cabecera;
	BizArgDetalle detalle;
	String company;
	String owner;
	String idParser;
	
	public void setIdParseador(String idClase) throws Exception {
		idParser = idClase;
	}
	
	public String getIdPareador() {
		return idParser;
	}
	
	public void setCompany(String zCompany) throws Exception {
		company=zCompany;
	}
	public void setOwner(String zOwner) throws Exception {
		owner=zOwner;
	}

	public String getTableDetalle() throws Exception {
		return new BizArgDetalle().GetTable();
	}
	public void addListenerDetect(IFinder finder) {
		this.finder=(IFinderArgBO) finder;
	}

	private String getContent() {
		PDFTextParser parser=new PDFTextParser();
		return parser.pdftoText(input);
	}


	protected void privExecute() throws Exception {

		String content=getContent();

		// implementar parseo aqui
		// cuando se descubra el encabezado llamar a finder.detectHeader(header)
		// cuando se descubra el detalle llamar a finder.detectDetail(detail)
		// en le camino llenar idPdf, fechadesde y fechahasta
		for(String linea : content.split("\r\n|\r|\n")) {
			parserLinea(linea);
		}
	}
  
	protected void parserLinea(String linea) throws Exception {
		String superTrim = linea.replaceAll("(\\p{Blank}){2,1000}", ";");
		superTrim = superTrim.replaceAll("\\p{Digit}\\p{Blank}\\p{Digit}", ";");
		superTrim = superTrim.replaceAll("\\p{Alpha}\\p{Blank}\\p{Digit}", ";");
		superTrim = superTrim.replaceAll("\\p{Digit}\\p{Blank}\\p{Alpha}", ";");
	  System.out.println(superTrim);
	}

	public void execute() throws Exception {
		cabecera=new BizArgCabecera();
		detalle=new BizArgDetalle();
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
		this.input=input;
	}

	public String getIdPdf() throws Exception {
		if (idPdf==null) throw new Exception("id indeterminado");
		return idPdf;
	}

	public Date getPeriodoDesde() throws Exception {
		return fechaDesde;
	}

	public Date getPeriodoHasta() throws Exception {
		return fechaHasta;
	}


	public void setFechaDesde(Date zFecha) throws Exception {
		fechaDesde = zFecha;
	}

	public void setFechaHasta(Date zFecha) throws Exception {
		fechaHasta = zFecha;
	}
	
	public static void main(String[] args) {
		JParseoArgBO parser=new JParseoArgBO();
		IFinderArgBO finder=new IFinderArgBO() {

			public void detectDetail(BizArgDetalle detail) throws Exception {
				System.out.print("ID: "+detail.getIdinterfaz());
				System.out.print("| Tipo Ruta: "+detail.getTipoRuta());
				System.out.print("| Aerolinea: "+detail.getAerolinea());
				System.out.print("| Operacion: "+detail.getOperacion());
				System.out.print("| Tarifa: "+detail.getTarifa());
				System.out.print("| Contado: "+detail.getContado());
				System.out.print("| Tarjeta: "+detail.getTarjeta());
				System.out.print("| Boleto: "+detail.getBoleto());
				System.out.print("| Base Imponible: "+detail.getBaseImponible());
				System.out.print("| Impuesto1: "+detail.getImpuesto1());
				System.out.print("| Impuesto2: "+detail.getImpuesto2());
				System.out.print("| Comision: "+detail.getComision());
				System.out.print("| Over: "+detail.getComisionOver());
				System.out.print("| Imp.s/com: "+detail.getImpSobrecom());
				System.out.print("| Tipo Tarjeta: "+detail.getTipoTarjeta());
				System.out.println("| Numero Tarjeta: "+detail.getNumeroTarjeta());

			}

			public void detectHeader(BizArgCabecera header) throws Exception {
				System.out.print("ID: "+header.getIdinterfaz());
				System.out.println("| Descripcion: "+header.getDescripcion());
			}

			public void error(Exception e) throws Exception {
				System.out.println("Error"+e.getMessage());
				e.printStackTrace();

			}

			public void start() throws Exception {
				System.out.println("inicio");

			}

			public void stop() throws Exception {
				System.out.println("fin");

			}

		};
		try {
			parser.setInput(new FileInputStream("c:\\a3.pdf"));
			parser.addListenerDetect(finder);
			parser.execute();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public String getId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void setFormat(String zFormat) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void setId(String zId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void setTypeFile(String zTypeFile) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public String getFormat() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getConciliableKey() throws Exception {
		return "";
	}

	@Override
	public String getIATA() throws Exception {
		return null;
	}



}
