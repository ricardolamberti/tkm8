package pss.tourism.carrier;

import pss.common.customList.config.relation.JRelations;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.tourism.carrier.logica.ICarrierLogica;
import pss.tourism.carrier.logica.JCarrierLogicaAeromexico;
import pss.tourism.carrier.logica.JCarrierLogicaCathay;
import pss.tourism.carrier.logica.JCarrierLogicaNada;

public class BizCarrier extends JRecord {
	
  private JString pCarrier = new JString();
  private JString pDescription = new JString();
  private JString pCodIata = new JString();
  private JString pCodAnalisis = new JString();
  private JString pDK = new JString();
  private JString pEmail = new JString();
  private JString pCfdi = new JString();
  private JString pFormaPago = new JString();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public static final String SIN_LOGICA = "DEFA";
  public static final String AEROMEXICO = "AEROMEXICO";
  public static final String CATHAY = "CATHAY";

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static JMap<String, String> logicas;

	public static JMap<String, String> getLogicas() throws Exception {
		if (logicas != null)
			return logicas;
		JMap<String, String> maps = JCollectionFactory.createMap();
		maps.addElement(BizCarrier.SIN_LOGICA, "DEFAULT");
		maps.addElement(BizCarrier.AEROMEXICO, "6 caracter");
		maps.addElement(BizCarrier.CATHAY, "3 y 4 caracter");
		return logicas = maps;
	}

	public static Class getLogicasClass(String id) throws Exception {
		if (id.equals(BizCarrier.SIN_LOGICA)) return JCarrierLogicaNada.class;
		if (id.equals(BizCarrier.AEROMEXICO)) return JCarrierLogicaAeromexico.class;
		if (id.equals(BizCarrier.CATHAY)) return JCarrierLogicaCathay.class;

		return null;
	}

	public ICarrierLogica getObjLogica() throws Exception {
		if (isNullLogica()) return null;
		return (ICarrierLogica)getLogicasClass(getCodAnalisis()).newInstance();
	}

  public void setCarrier(String zValue) throws Exception {    pCarrier.setValue(zValue);  }
  public String getCarrier() throws Exception {     return pCarrier.getValue();  }
  
  public void setDescription(String zValue) throws Exception {    pDescription.setValue(zValue);  }
  public String getDescription() throws Exception {     return pDescription.getValue();  }

  public String getCodIata() throws Exception {     return pCodIata.getValue();  }
  public void setCodIata(String zValue) throws Exception {    pCodIata.setValue(zValue);  }
  public String getDK() throws Exception {     return pDK.getValue();  }
  public void setDK(String zValue) throws Exception {    pDK.setValue(zValue);  }
  public String getEmail() throws Exception {     return pEmail.getValue();  }
  public void setEmail(String zValue) throws Exception {    pEmail.setValue(zValue);  }
  public String getCfdi() throws Exception {     return pCfdi.getValue();  }
  public void setCfdi(String zValue) throws Exception {    pCfdi.setValue(zValue);  }

  public String getFormaPago() throws Exception {     return pFormaPago.getValue();  }
  public void setFormaPago(String zValue) throws Exception {    pFormaPago.setValue(zValue);  }



  public String getCodAnalisis() throws Exception {     return pCodAnalisis.getValue();  }
  public void setCodAnalisis(String zValue) throws Exception {    pCodAnalisis.setValue(zValue);  }
  public boolean isNullLogica() throws Exception { return  pCodAnalisis.isNull(); } 
  

  /**
   * Constructor de la Clase
   */
  public BizCarrier() throws Exception {
    this.addItem( "carrier", pCarrier );
    this.addItem( "description", pDescription );
    this.addItem( "cod_iata", pCodIata );
    this.addItem( "cod_analisis", pCodAnalisis );
    this.addItem( "dk", pDK );
    this.addItem( "email", pEmail );
    this.addItem( "cfdi", pCfdi );
    this.addItem( "forma_pago", pFormaPago );
     }
  /**
   * Adds the fixed object properties
   */
  @Override
	public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "carrier", "Aerolínea Cod.", true, true, 10 );
    this.addFixedItem( FIELD, "description", "Aerolínea", true, true, 255 );
    this.addFixedItem( FIELD, "cod_iata", "Aerolínea IATA Cod.", true, false, 4 );
    this.addFixedItem( FIELD, "cod_analisis", "Analisis tarifario", true, false, 50 );
    this.addFixedItem( FIELD, "dk", "DK", true, false, 50 );
    this.addFixedItem( FIELD, "email", "eMail", true, false, 400 );
    this.addFixedItem( FIELD, "cfdi", "Uso cfdi", true, false, 10 );
    this.addFixedItem( FIELD, "forma_pago", "Forma Pago", true, false, 10 );
  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return "TUR_CARRIER"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.tourism.carrier.GuiCarriers");
  	
  }

  /**
   * Default Read() method
   */
  public boolean Read( String zCarrier ) throws Exception { 
    clearFilters(); 
    addFilter( "carrier",  zCarrier ); 
    return Read(); 
  } 
  public boolean ReadIata( String zCarrier ) throws Exception { 
    clearFilters(); 
    addFilter( "cod_iata",  zCarrier ); 
    return Read(); 
  } 

//  public void execCreateArticle() throws Exception {
//    JExec oExec = new JExec(null, null) {
//      @Override
//			public void Do() throws Exception {
//        createArticle();
//      }
//    };
//    oExec.execute();
//  }
  
//  public void createArticle() throws Exception {
//  	BizNewArticleAir article = new BizNewArticleAir();
//  	article.setCompany(BizTourismUser.getUsr().getCompany());
//  	article.setPais(BizTourismUser.getUsr().getCountry());
//  	article.setNombre(this.getDescription());
//  	article.setApellido(this.getDescription());
//  	article.setCodigoIdent(this.getCarrier());
//  	article.processInsert();
//  	
//  	BizSupplierProduct c = new BizSupplierProduct();
//  	c.setCompany(article.getObjNewArticulo().getCompany());
//  	c.setSupplier(BizTourismConfig.getLocalConfig().getAirSupplier());
//  	c.setArticulo(article.getObjNewArticulo().GetArticulo());
//  	c.setMargen(1d);
//  	c.setConciliaComision(true);
//  	c.processInsert();
//  }

//  public BizArticleAir getObjArticle(boolean createIfNotFound) throws Exception {
//  	BizArticleAir oArticulo = new BizArticleAir();
//  	oArticulo.setFilterValue("company", BizUsuario.getUsr().getCompany());
//  	oArticulo.setFilterValue("tipo_articulo", BizArticulo.ART_AIR);
//  	oArticulo.setFilterValue("codigo_ident", this.getCarrier());
//  	oArticulo.SetNoExcSelect(true);
//  	if (oArticulo.Read()) return oArticulo; 
// 		if (!createIfNotFound) return null;
//		this.execCreateArticle();
// 		return getObjArticle(false);
//  }
  
//  public boolean isExistsArticle() throws Exception {
//  	return getObjArticle(false)!=null;
//  }
  
  public String getCodigoDescription() throws Exception {     
  	return "("+this.pCarrier.getValue()+") "+pDescription.getValue();  
  }
  
  public static BizCarrier findCarrierByCod(String codIata) throws Exception {
  	BizCarrier c = new BizCarrier();
  	c.addFilter("cod_iata", codIata);
  	c.dontThrowException(true);
  	if (!c.read()) return null;
  	return c;
  }
  
  
	public static String GetDescripcionReporte(String zValue) throws Exception {
		String sDesc="";
		sDesc="Aerolínea: ^";
		if (zValue.equals("")) sDesc+="< Todas >";
		else {
			BizCarrier reg =new BizCarrier();
			reg.Read(zValue);
			sDesc+=reg.getCodigoDescription();
		}
		return sDesc;
	}
	
	@Override
	public String getRecordName() throws Exception {
		return "Línea Aérea";
	}
	
}
