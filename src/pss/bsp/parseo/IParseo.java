package  pss.bsp.parseo;

import java.io.InputStream;
import java.util.Date;

/// el que implenta esta interfaz sabe como resolver el parseo de los datos
public interface IParseo {
	// entrada
	public void setCompany(String zCompany) throws Exception;
	public void setOwner(String zOwner) throws Exception;
	public void setId(String zId) throws Exception;
	public void setFilename(String input) throws Exception;
	public void setInput(InputStream input) throws Exception;
	public void setIdParseador(String idClase) throws Exception;
	public void setTypeFile(String zTypeFile) throws Exception;
	public void setFormat(String zFormat) throws Exception;
	public void setFechaDesde(Date zFecha) throws Exception;
	public void setFechaHasta(Date zFecha) throws Exception;
  // configuracion salida
	public void addListenerDetect(IFinder finder);
	// proceso
	public void execute() throws Exception;
	public String getTableDetalle() throws Exception;
	
	// salida
	public String getIdPareador();
	public String getId()  throws Exception;
	public Date getPeriodoDesde()  throws Exception;
	public Date getPeriodoHasta()  throws Exception;
	public String getFormat()  throws Exception;
	public String getConciliableKey()  throws Exception;
	public String getIATA()  throws Exception;
}
