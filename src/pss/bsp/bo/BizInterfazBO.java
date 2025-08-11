package  pss.bsp.bo;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

import pss.bsp.bo.data.GuiBODetalles;
import pss.bsp.bo.formato.BizFormato;
import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.consola.BizBSPConsola;
import pss.bsp.pais.BizPais;
import pss.bsp.parseo.IParseo;
import pss.bsp.parseo.JParseoFactory;
import pss.bsp.parseo.bspParseo.IBspParseo;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLOB;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;

public class BizInterfazBO extends JRecord {

  private JString pCompany = new JString();
  private JString pOwner = new JString();
  private JLong pIdinterfaz = new JLong();
  private JString pDescripcion = new JString();
  private JString pPais = new JString();
  private JString pEstado = new JString();
  private JDate pFechaDesde = new JDate();
  private JDate pFechaHasta = new JDate();
  private JLOB pInterfaz = new JLOB();
  private JString pTipoInterfaz = new JString();
  private JString pFormato = new JString();
  private JString pPdfFilename = new JString();
  private JString pIdParseo = new JString();
  private JString pIata = new JString();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setOwner(String zValue) throws Exception {    pOwner.setValue(zValue);  }
  public String getOwner() throws Exception {     return pOwner.getValue();  }
  public boolean isNullOwner() throws Exception { return  pOwner.isNull(); } 
  public void setNullToOwner() throws Exception {  pOwner.setNull(); } 
  public void setIdinterfaz(long zValue) throws Exception {    pIdinterfaz.setValue(zValue);  }
  public long getIdinterfaz() throws Exception {     return pIdinterfaz.getValue();  }
  public boolean isNullIdinterfaz() throws Exception { return  pIdinterfaz.isNull(); } 
  public void setNullToIdinterfaz() throws Exception {  pIdinterfaz.setNull(); } 
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public boolean isNullDescripcion() throws Exception { return  pDescripcion.isNull(); } 
  public void setNullToDescripcion() throws Exception {  pDescripcion.setNull(); } 
  public void setEstado(String zValue) throws Exception {    pEstado.setValue(zValue);  }
  public String getEstado() throws Exception {     return pEstado.getValue();  }
  public boolean isNullEstado() throws Exception { return  pEstado.isNull(); } 
  public void setNullToEstado() throws Exception {  pEstado.setNull(); } 
  public void setFechaDesde(Date zValue) throws Exception {    pFechaDesde.setValue(zValue);  }
  public Date getFechaDesde() throws Exception {     return pFechaDesde.getValue();  }
  public boolean isNullFechaDesde() throws Exception { return  pFechaDesde.isNull(); } 
  public void setNullToFechaDesde() throws Exception {  pFechaDesde.setNull(); } 
  public void setFechaHasta(Date zValue) throws Exception {    pFechaHasta.setValue(zValue);  }
  public Date getFechaHasta() throws Exception {     return pFechaHasta.getValue();  }
  public boolean isNullFechaHasta() throws Exception { return  pFechaHasta.isNull(); } 
  public void setNullToFechaHasta() throws Exception {  pFechaHasta.setNull(); } 
  public void setInterfaz(String zValue) throws Exception {    pInterfaz.setValue(zValue);  }
  public String getInterfaz() throws Exception {     return pInterfaz.getValue();  }
  public boolean isNullInterfaz() throws Exception { return  pInterfaz.isNull(); } 
  public void setNullToInterfaz() throws Exception {  pInterfaz.setNull(); } 
  public void setTipoInterfaz(String zValue) throws Exception {    pTipoInterfaz.setValue(zValue);  }
  public String getTipoInterfaz() throws Exception {     return pTipoInterfaz.getValue();  }
  public boolean isNullTipoInterfaz() throws Exception { return  pTipoInterfaz.isNull(); } 
  public void setNullToTipoInterfaz() throws Exception {  pTipoInterfaz.setNull(); } 
  public void setPais(String zValue) throws Exception {    pPais.setValue(zValue);  }
  public String getPais() throws Exception {     return pPais.getValue();  }
  public boolean isNullPais() throws Exception { return  pPais.isNull(); } 
  public void setNullToPais() throws Exception {  pPais.setNull(); } 
  public void setFilename(String zValue) throws Exception {    pPdfFilename.setValue(zValue);  }
  public String getFilename() throws Exception {     return pPdfFilename.getValue();  }
  public void setFormatoInterfaz(String zValue) throws Exception {    pFormato.setValue(zValue);  }
  public String getFormatoInterfaz() throws Exception {     return pFormato.getValue();  }
  public void setParseo(String zValue) throws Exception {    pIdParseo.setValue(zValue);  }
  public String getParseo() throws Exception {     return pIdParseo.getValue();  }
  public void setIata(String zValue) throws Exception {    pIata.setValue(zValue);  }
  public String getIata() throws Exception {     return pIata.getValue();  }

  static JMap<String,String> tiposInterfaz;

  /**
   * Constructor de la Clase
   */
  public BizInterfazBO() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "owner", pOwner );
    this.addItem( "idInterfaz", pIdinterfaz );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "estado", pEstado );
    this.addItem( "fecha_desde", pFechaDesde );
    this.addItem( "fecha_hasta", pFechaHasta );
    this.addItem( "interfaz", pInterfaz );
    this.addItem( "tipo_interfaz", pTipoInterfaz );
    this.addItem( "filename", pPdfFilename );
    this.addItem( "id_formato", pFormato );
    this.addItem( "id_parseo", pIdParseo );
    this.addItem( "iata", pIata );
       
     }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 50 );
    this.addFixedItem( KEY, "idInterfaz", "Idinterfaz", true, true, 18 );
    this.addFixedItem( FIELD, "owner", "Owner", true, true, 50 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, true, 300 );
    this.addFixedItem( FIELD, "estado", "Estado", true, true, 50 );
    this.addFixedItem( FIELD, "fecha_desde", "Fecha desde", true, true, 18 );
    this.addFixedItem( FIELD, "fecha_hasta", "Fecha hasta", true, true, 18 );
    this.addFixedItem( FIELD, "interfaz", "Interfaz", true, false, 255 );
    this.addFixedItem( FIELD, "tipo_interfaz", "Tipo interfaz", true, true, 50 );
    this.addFixedItem( FIELD, "id_parseo", "ID Parseo", true, true, 50 );
    this.addFixedItem( FIELD, "id_formato", "Formato", true, true, 300 );
    this.addFixedItem( FIELD, "iata", "Iata", true, true, 300 );
    this.addFixedItem( VIRTUAL, "filename", "Nombre archivo", true, true, 300 );
     }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_INTERFAZ_BO"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String zCompany, long zIdinterfaz ) throws Exception { 
    addFilter( "company",  zCompany ); 
//    addFilter( "owner",  zOwner ); 
    addFilter( "idInterfaz",  zIdinterfaz ); 
    return read(); 
  }
	
	public static JMap<String,String> getTiposInterfaz() {
		if (tiposInterfaz!=null) return tiposInterfaz;
		tiposInterfaz = JCollectionFactory.createMap();
		tiposInterfaz.addElement("AUTO", "Detectar");
		tiposInterfaz.addElement("TAB", "Archivo separado por TABs");
		tiposInterfaz.addElement("COMA", "Archivo separado por comas");
		tiposInterfaz.addElement("XLSX", "Archivo XML Excel");
		tiposInterfaz.addElement("XLS", "Archivo Excel");
		tiposInterfaz.addElement("CSV", "Archivo CSV");
		tiposInterfaz.addElement("PDF", "Archivo PDF");
		tiposInterfaz.addElement("TXT", "Archivo TXT");
		tiposInterfaz.addElement("RTF", "Archivo RTF");
		return tiposInterfaz;
	} 


	
	BizPais pais;
	BizFormato objformato;

	public BizPais getObjPais() throws Exception {
		if (pais!=null) return pais;
		BizPais p=new BizPais();
		p.read(getPais());
		return (pais=p);
	}

	public BizFormato getObjFormato() throws Exception {
		if (objformato!=null) return objformato;
		BizFormato p=new BizFormato();
		p.read(this.getCompany(), this.getFormatoInterfaz());
		p.setExampleIdInterfaz(""+getIdinterfaz());
		p.setExampleParseo(getParseo());
		
		return (objformato=p);
	}
	
	private String determineTipoInterfaz(String idType,String filename) throws Exception {
		if (!idType.equals("AUTO")) return idType;
		if (filename.toLowerCase().indexOf(".pdf")!=-1) return "PDF";
		if (filename.toLowerCase().indexOf(".csv")!=-1) return "CSV";
		if (filename.toLowerCase().indexOf(".tab")!=-1) return "TAB";
		if (filename.toLowerCase().indexOf(".comma")!=-1) return "COMA";
		if (filename.toLowerCase().indexOf(".txt")!=-1) return "TXT";
		if (filename.toLowerCase().indexOf(".xlsx")!=-1) return "XLSX";
		if (filename.toLowerCase().indexOf(".xls")!=-1) return "XLS";
		if (filename.toLowerCase().indexOf(".rtf")!=-1) return "RTF";
		throw new Exception("Tipo de archivo deconocido");
	}
	
	public IParseo tryParser(long idInterfaz,String filename) throws Exception {
		setTipoInterfaz(determineTipoInterfaz(getTipoInterfaz(),filename));
		BizPais pais=BizBSPUser.getUsrBSP().getObjBSPPais();
		setPais(pais.getPais());
		IParseo[] parseos=JParseoFactory.getInstance(pais.getIdparserHo(),getTipoInterfaz(),getFormatoInterfaz());
		IParseo lastParser=null;
		for(IParseo parseo:parseos) {
			try {
				lastParser=parseo;
				parseo.setCompany(getCompany());
				parseo.setOwner(getOwner());
				parseo.setFormat(getFormatoInterfaz());
				parseo.setId(""+idInterfaz);
				parseo.setFechaDesde(getFechaDesde());
				parseo.setFechaHasta(getFechaHasta());
				FileInputStream file=new FileInputStream(filename);
				try {
					parseo.setFilename(filename);
					parseo.setInput(file);
					parseo.execute();
				} finally {
					file.close();
				}
				break;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}

		return lastParser;
	}

	@Override
	public void processDelete() throws Exception {
		IBspParseo parseo = (IBspParseo)JParseoFactory.getInstanceFromClass(this.getParseo());
		parseo.eliminar(getCompany(), getOwner(), ""+getIdinterfaz());
		
		super.processDelete();
	}
	
	void setConciliableKey(String sKey) throws Exception {
		if (sKey==null || sKey.equals("")) return;
		BizBSPConsola consola = BizBSPUser.getUsrBSP().getBspConsola();
		consola.getConfigView().setConciliableKey(sKey);
		consola.processUpdate();
	}
	
	@Override
	public void processInsert() throws Exception {
		if (pOwner.isNull()) pOwner.setValue(BizUsuario.getUsr().GetUsuario());
		String filename=getFilename();
		if (filename.equals("")) throw new Exception("No se especifico archivo");
		filename=filename.replace("%3A", ":");
		filename=filename.replace("%20", " ");
		this.clearFilter("idinterfaz");
		long idInterfaz = this.SelectMaxLong("idInterfaz")+1;
		if (this.isNullDescripcion()) {
			File f = new File(filename);
			this.setDescripcion(f.getName());
		}
		IParseo parseo=tryParser(idInterfaz, filename);
		setConciliableKey(parseo.getConciliableKey());
		setParseo(parseo.getIdPareador());
		setIdinterfaz(idInterfaz);
		setFormatoInterfaz(parseo.getFormat());
		setEstado("PENDIENTE");
		super.processInsert();
	}
	@Override
	public void processUpdate() throws Exception {
		if (pOwner.isNull()) pOwner.setValue(BizUsuario.getUsr().GetUsuario());
		super.processUpdate();
	}

	
	public JWin getHeader() throws Exception {
		IBspParseo parseo = getObjParseo();
		return parseo.getGuiHeader(getCompany(), getOwner(),""+getIdinterfaz());
	}
	IBspParseo objparseo;
	IBspParseo getObjParseo() throws Exception {
		if (objparseo!=null) return objparseo;
		IBspParseo p = (IBspParseo)JParseoFactory.getInstanceFromClass(getParseo());
		return objparseo=p;
	}
	public JWins getDetail() throws Exception {
		IBspParseo parseo = getObjParseo();
		return parseo.getGuisDetail(getCompany(), getOwner(),""+getIdinterfaz());
	}

	public JWins getDetailConsolidado() throws Exception {
		GuiBODetalles detalle = new GuiBODetalles();
		detalle.getRecords().addFilter("company", getCompany());
		//detalle.getRecords().addFilter("owner", getOwner());
		detalle.getRecords().addFilter("id_interfaz", getIdinterfaz());
		return detalle;
	}

	public void refillConsolidado() throws Exception {
		IBspParseo parseo = getObjParseo();
		JWins wins = parseo.getGuisDetail(getCompany(), getOwner(),""+getIdinterfaz());
		parseo.refill(getCompany(), getOwner(),""+getIdinterfaz());
	}

}
