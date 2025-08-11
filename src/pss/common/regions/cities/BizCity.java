package pss.common.regions.cities;

import pss.common.regions.divitions.BizPaisLista;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JMap;


public class BizCity extends JRecord {
	
  private JLong pCity = new JLong();
  private JString pCountry = new JString();
  private JString pCodigo = new JString();
  private JString pDescripcion = new JString();
  private JString pDescrCountry = new JString() {@Override
	public void preset() throws Exception {pDescrCountry.setValue(getDescrCountry());}};

  private BizPaisLista paisLista;
	private static JMap<String, BizCity> cache=null; 

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCountry(String zValue) throws Exception {    pCountry.setValue(zValue);  }
  public String getCountry() throws Exception {     return pCountry.getValue();  }
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizCity() throws Exception {
  }
  
  @Override
  public void createProperties() throws Exception {
    this.addItem( "city", pCity );
    this.addItem( "country", pCountry );
    this.addItem( "codigo", pCodigo );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "descr_country", pDescrCountry );
  }
  
  @Override
	public void createFixedProperties() throws Exception {
//    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    // this.addFixedItem( KEY, "region", BizUsuario.getUsr().getObjBusiness().getLabelRegionOrigen(), true, true, 15 );
    this.addFixedItem( KEY, "city", "Ciudad", true, true, 15 );
    this.addFixedItem( FIELD, "country", "Country", true, true, 2 );
    this.addFixedItem( FIELD, "codigo", "Código", true, false, 15 );
    this.addFixedItem( FIELD, "descripcion", "Descripción", true, true, 100 );
    this.addFixedItem( VIRTUAL, "descr_country", "País", true, true, 100 );
  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return "PER_CITY"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default Read() method
   */
  public boolean Read(long city) throws Exception { 
    addFilter( "city",  city ); 
    return this.read(); 
  } 
  
  public String getDescrCountry() throws Exception {
  	return this.getObjPaisLista().GetDescrip();
  }
  
  public BizPaisLista getObjPaisLista() throws Exception {
  	if (this.paisLista!=null) return this.paisLista;
  	BizPaisLista record = new BizPaisLista();
  	record.Read(pCountry.getValue());
  	return (this.paisLista=record);
  }
//	@Override
//	public void processDelete() throws Exception {
//		if (JRecords.existsComplete(BizArticulo.class, "company", getCompany(), "region_origen", getRegion()))
//			JExcepcion.SendError("La región está asociado a un "+BizUsuario.getUsr().getObjBusiness().getLabelArticle());
//		super.processDelete();
//	}
  
  @Override
  public void processInsert() throws Exception {
  	if (pCity.isNull()) {
  		BizCity max = new BizCity();
  		pCity.setValue(max.SelectMaxLong("city")+1);
  	}
  	super.processInsert();
  }
  
  @Override
  public void validateConstraints() throws Exception {
  	if (!pCodigo.isEmpty()) {
			BizCity reg = new BizCity();
			reg.addFilter("codigo", pCodigo.getValue());
			reg.dontThrowException(true);
			if (reg.read()) 
				JExcepcion.SendError("Código utilizado en ciudad: "+reg.getDescripcion());
  	}
	}

	public static String getDescription(long city) throws Exception {
		if (city==0L) return "";
		return BizCity.findCity(String.valueOf(city)).getDescripcion();
	}

	public static synchronized BizCity findCity(String city) throws Exception {
		if (cache==null) {
			JRecords<BizCity> r = new JRecords<BizCity>(BizCity.class);
			r.readAll();
			cache=r.convertToHash("city");
		}
		return cache.getElement(city);
	}

	
}
