package  pss.common.customList.config.field.filtro;

import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.field.IItem;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BizFiltroSQL extends JRecord implements IItem{


	private JString pCompany = new JString();
  private JLong pListId = new JLong();
  private JLong pOrden = new JLong();
  private JLong pSecuencia = new JLong();
	private JString pIdKey = new JString();

  private JString pFiltro = new JString();
  private JString pTipoFiltro = new JString();
  private JString pNombreFiltro = new JString();
  private JString pValor = new JString();
  private JString pDescrFiltro = new JString() {
  	public void preset() throws Exception {
  		pDescrFiltro.setValue(getDescrFiltro());
  	}
  };



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setListId(long zValue) throws Exception {    pListId.setValue(zValue);  }
  public long getListId() throws Exception {     return pListId.getValue();  }
  public long getOrden() throws Exception {     return pOrden.getValue();  }
  public void setFiltro(String zValue) throws Exception {    pFiltro.setValue(zValue);  }
  public String getFiltro() throws Exception {     return pFiltro.getValue();  }
  public void setTipoFiltro(String zValue) throws Exception {    pTipoFiltro.setValue(zValue);  }
  public String getTipoFiltro() throws Exception {     return pTipoFiltro.getValue();  }
  public void setValor(String zValue) throws Exception {    pValor.setValue(zValue);  }
  public String getValor() throws Exception {     return pValor.getValue();  }
	public String getCampoDefecto() throws Exception {
		return pIdKey.getValue();
	}
	public void setCampoKey(String zValue) throws Exception {
		pIdKey.setValue(zValue);
	}
	public String getCampoKey() throws Exception {
		return pIdKey.getValue();
	}
	public void setOrden(long zValue) throws Exception {
		pOrden.setValue(zValue);
	}

  private BizCustomList customList;
	public void setObjCustomList(BizCustomList zCustomList) throws Exception {
		customList=zCustomList;
	}
  /**
   * Constructor de la Clase
   */
  public BizFiltroSQL() throws Exception {
  }

  public boolean isDateInput() throws Exception {
  	return this.getTipoFiltro().equals(JObject.JDATE);
  }

  public boolean isDateTimeInput() throws Exception {
  	return this.getTipoFiltro().equals(JObject.JDATETIME);
  }

  public boolean isCheckInput() throws Exception {
  	return this.getTipoFiltro().equals(JObject.JBOOLEAN);
  }

  public boolean isTextInput() throws Exception {
  	return this.getTipoFiltro().equals(JObject.JSTRING);
  }
  

  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "list_id", pListId );
    this.addItem( "secuencia", pSecuencia );
    this.addItem( "orden", pOrden );
  	this.addItem("campo_key", pIdKey);
  	 
    this.addItem( "filtro", pFiltro );
    this.addItem( "nombre_filtro", pNombreFiltro );
    this.addItem( "tipo_filtro", pTipoFiltro );
    
    this.addItem( "descr_filtro", pDescrFiltro );
    this.addItem( "valor", pValor );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "list_id", "List Id", true, true, 564 );
    this.addFixedItem( KEY, "secuencia", "Secuencia", false, false, 64 );
    this.addFixedItem( FIELD, "orden", "Orden", true, true, 5 );

    this.addFixedItem( FIELD, "filtro", "Filtro", true, false, 150 );
    this.addFixedItem( FIELD, "nombre_filtro", "Nombre Filtro", true, false, 200 );
    this.addFixedItem( FIELD, "tipo_filtro", "Tipo Filtro", true, false, 200 );
  	this.addFixedItem( FIELD, "campo_key", "valor por defacto", true, false, 50);

    this.addFixedItem( VIRTUAL, "descr_filtro", "Filtro", true, true, 150 );
    this.addFixedItem( VIRTUAL, "valor", "Valor", true, true, 150 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "LST_FILTROSQL"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public long getSecuencia() throws Exception {
		return pSecuencia.getValue();
	}
	public void setNullToSecuencia() throws Exception { pSecuencia.setNull();}


  
  public boolean read( String zCompany, long zListId, long zOrden ) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "list_id",  zListId); 
    addFilter( "orden",  zOrden ); 
    return read(); 
  } 
  public void processInsert() throws Exception {
  	if (this.pOrden.isNull()) {
  		BizFiltroSQL max = new BizFiltroSQL();
  		max.addFilter("company", pCompany.getValue());
  		max.addFilter("list_id", pListId.getValue());
  		this.pOrden.setValue(max.SelectMaxLong("orden")+1);
  	}

  	super.processInsert();
		pSecuencia.setValue(getIdentity("secuencia"));

  }
  
	public void processUpdate() throws Exception {
		super.processUpdate();
	}
//  public JProperty createFixedProp() throws Exception {
//		JProperty p = this.getObjRecordTarget().getFixedProp(this.getFiltro());
//		JProperty p1 = new JProperty(p.getType(), this.getNameField(), p.GetDescripcion(), null, "", true, true, p.getSize(), p.GetPrecision(), null, null, null);
//		return p1;
//	}
//
//  public JObject<?> createProp() throws Exception {
//  	return this.getObjRecordTarget().getProp(this.getFiltro());
//  }
//  
	public String getDescripcion() throws Exception {
		return getDescrFiltro();
	}
  public String getDescrFiltro() throws Exception {
  	StringBuffer s = new StringBuffer();
		s.append(this.getFilterName());
		return s.toString();
  }
  
  public String getFilterName() throws Exception {
  	if (this.pNombreFiltro.isNotNull()) return this.pNombreFiltro.getValue();
  	return "Filtro manual";
  }
  
  static JMap<String, String> mapa;
	
	static public JMap<String, String> getTipos() throws Exception {
  	if (mapa!=null) return mapa;
  	mapa = JCollectionFactory.createMap();
  	mapa.addElement(JObject.JDATE, "Fecha ");
  	mapa.addElement(JObject.JDATETIME, "Fecha y hora");
  	mapa.addElement(JObject.JBOOLEAN, "Check");
  	mapa.addElement(JObject.JSTRING, "Cadena");
  	return mapa;
  }
	
 
  public JProperty createFixedProp() throws Exception {
		JProperty p1 = new JProperty(VIRTUAL, this.getFiltro(), this.getFilterName(), null, "", true, true, 200, 0, null, null, null);
		return p1;
	}

  public JObject<?> createProp() throws Exception {
  	if (getTipoFiltro().equals(JObject.JDATE)) return new JDate();
  	if (getTipoFiltro().equals(JObject.JDATETIME)) return new JDateTime();
  	if (getTipoFiltro().equals(JObject.JBOOLEAN)) return new JBoolean();
  	return new JString();
  }
  

  public void processDelete() throws Exception {
  	super.processDelete();
  }
  
  
  public void execSubir(final long pos) throws Exception {
  	JExec exec = new JExec(null,null) {
  		public void Do() throws Exception {
  			subir(pos);
  		}
  	};
  	exec.execute();
  }
  
  public void subir(long pos) throws Exception {
  	BizFiltroSQL max = new BizFiltroSQL();
  	long orden = pos;
		if (pos == -1) {
			max.addFilter("company", this.getCompany());
			max.addFilter("list_id", this.getListId());
			max.addFilter("orden", this.pOrden.getValue(), "<");
			orden = max.SelectMaxLong("orden");
		}
  	max = new BizFiltroSQL();
  	max.addFilter("company", this.getCompany());
  	max.addFilter("list_id", this.getListId());
  	max.addFilter("orden", orden);
  	max.dontThrowException(true);
  	if (!max.read()) return;
  	max.pOrden.setValue(this.pOrden.getValue());
  	this.pOrden.setValue(orden);
  	max.update();
  	this.update();
  }

  public void execBajar(final long pos) throws Exception {
  	JExec exec = new JExec(null,null) {
  		public void Do() throws Exception {
  			bajar(pos);
  		}
  	};
  	exec.execute();
  }
  
  public void bajar(long pos) throws Exception {
  	BizFiltroSQL min = new BizFiltroSQL();
  	long orden = pos;
  	if (pos==-1) {
	  	min.addFilter("company", this.getCompany());
	  	min.addFilter("list_id", this.getListId());
	  	min.addFilter("orden", this.pOrden.getValue(), ">");
	  	orden = min.SelectMinLong("orden");
  	}
  	min = new BizFiltroSQL();
  	min.addFilter("company", this.getCompany());
  	min.addFilter("list_id", this.getListId());
  	min.addFilter("orden", orden);
  	min.dontThrowException(true);
  	if (!min.read()) return;
  	min.pOrden.setValue(this.pOrden.getValue());
  	this.pOrden.setValue(orden);
  	min.update();
  	this.update();
  }
  
	public IItem getClon(String company,long listId) throws Exception {
		BizFiltroSQL campo = new BizFiltroSQL();
		campo.copyProperties(this);
		campo.setCompany(company);
		campo.setListId(listId);
		campo.pSecuencia.setNull();
		campo.processInsert();
		return campo;
	}
  


}
