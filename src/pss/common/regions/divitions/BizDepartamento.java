package  pss.common.regions.divitions;

import pss.core.services.JExec;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizDepartamento extends JRecord {

  private JString pPais = new JString();
  private JString pProvincia = new JString();
  private JString pNombre = new JString();
  private JLong pDptoId = new JLong();
  private JString pDescrPais = new JString() {
  	public void preset() throws Exception {
  		pDescrPais.setValue(getDescrPais());
  	};
  };
  private JString pDescrProvincia =  new JString() {
  	public void preset() throws Exception {
  		pDescrProvincia.setValue(getDescrProvincia());
  	};
  };


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setPais(String zValue) throws Exception {    pPais.setValue(zValue);  }
  public String getPais() throws Exception {     return pPais.getValue();  }
  public boolean isNullPais() throws Exception { return  pPais.isNull(); } 
  public void setNullToPais() throws Exception {  pPais.setNull(); } 
  public void setProvincia(String zValue) throws Exception {    pProvincia.setValue(zValue);  }
  public String getProvincia() throws Exception {     return pProvincia.getValue();  }
  public boolean isNullProvincia() throws Exception { return  pProvincia.isNull(); } 
  public void setNullToProvincia() throws Exception {  pProvincia.setNull(); } 
  public void setNombre(String zValue) throws Exception {    pNombre.setValue(zValue);  }
  public String getNombre() throws Exception {     return pNombre.getValue();  }
  public boolean isNullNombre() throws Exception { return  pNombre.isNull(); } 
  public void setNullToNombre() throws Exception {  pNombre.setNull(); } 
  public void setDptoId(long zValue) throws Exception {    pDptoId.setValue(zValue);  }
  public long getDptoId() throws Exception {     return pDptoId.getValue();  }
  public boolean isNullDptoId() throws Exception { return  pDptoId.isNull(); } 
  public void setNullToDptoId() throws Exception {  pDptoId.setNull(); } 
  public String getDescrProvincia() throws Exception {     return getObjProvincia()==null?"":getObjProvincia().GetDescrip();  }
  public String getDescrPais() throws Exception {     return getObjPais()==null?"":getObjPais().GetDescrip();  }

  
  BizPaisLista objPais;
	public BizPaisLista getObjPais() throws Exception {
		if (objPais!=null) return objPais;
		BizPaisLista d = new BizPaisLista();
		d.dontThrowException(true);
		if (!d.Read(getPais())) return null;
		return objPais=d;
	}
	BizProvincia objProvincia;
	public BizProvincia getObjProvincia() throws Exception {
		if (objProvincia!=null) return objProvincia;
		BizProvincia d = new BizProvincia();
		d.dontThrowException(true);
		if (!d.Read(getPais(),getProvincia())) return null;
		return objProvincia=d;
	}

  /**
   * Constructor de la Clase
   */
  public BizDepartamento() throws Exception {
  }

	@Override
	public void processDelete() throws Exception {
		JRecords<BizCiudadDepartamento> d = new JRecords<BizCiudadDepartamento>(BizCiudadDepartamento.class);
		d.addFilter("pais", getPais());
		d.addFilter("provincia", getProvincia());
		d.addFilter("departamento_id", getDptoId());
		d.toStatic();
		d.processDeleteAll();
		super.processDelete();
	}

  public void createProperties() throws Exception {
    this.addItem( "pais", pPais );
    this.addItem( "provincia", pProvincia );
    this.addItem( "nombre", pNombre );
    this.addItem( "dpto_id", pDptoId );
    this.addItem( "descr_pais", pDescrPais );
    this.addItem( "descr_provincia", pDescrProvincia );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( FIELD, "pais", "Pais", true, true, 15 );
    this.addFixedItem( FIELD, "provincia", "Provincia", true, true, 15 );
    this.addFixedItem( FIELD, "nombre", "Nombre", true, true, 100 );
    this.addFixedItem( KEY, "dpto_id", "Dpto id", true, true, 3 );
    this.addFixedItem( VIRTUAL, "descr_pais", "Pais", true, true, 15 );
    this.addFixedItem( VIRTUAL, "descr_provincia", "Provincia", true, true, 15 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "reg_departamentos"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( long zDptoId ) throws Exception { 
    addFilter( "dpto_id",  zDptoId ); 
    return read(); 
  } 

  @Override
  public void processInsert() throws Exception {
  	if (pDptoId.isNull()) {
  		BizDepartamento max = new BizDepartamento();
  		pDptoId.setValue(max.SelectMaxLong("dpto_id")+1);
  	}
  	super.processInsert();
  }
  
	public void execVincularCiudad(final BizCiudad ciudad) throws Exception {
		JExec oExec = new JExec(this, "execVincularExpediente") {

			@Override
			public void Do() throws Exception {
				processVincularCiudad(ciudad);
			}
		};
		oExec.execute();
	}

  public void processVincularCiudad(BizCiudad ciudad) throws Exception {
	  JRecords<BizCiudadDepartamento> cd = new  JRecords<BizCiudadDepartamento>(BizCiudadDepartamento.class);
	  cd.addFilter("pais", getPais());
	  cd.addFilter("provincia", getProvincia());
	  cd.addFilter("ciudad_id", ciudad.getCiudad());
	  cd.toStatic();
	  cd.deleteAll();
	  BizCiudadDepartamento cdn = new BizCiudadDepartamento();
	  cdn.setPais(getPais());
		cdn.setProvincia(getProvincia());
		cdn.setCiudadId(ciudad.getCiudad());
		cdn.setDepartamentoId(getDptoId());
		cdn.processInsert();
  }
}
