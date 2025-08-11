package  pss.common.layoutWysiwyg.parametros;

import pss.common.customList.config.field.campo.BizCampo;
import pss.common.customList.config.field.filtro.BizFiltroSQL;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizDocListadoParam extends JRecord {
 
  private JLong pIdDoc = new JLong();
  private JString pDescripcion = new JString() {
  	public void preset() throws Exception {
  		pDescripcion.setValue(getDescrParam());
  	};
  };
	private JString pCompany = new JString();
  private JLong pListId = new JLong();
  private JLong pOrden = new JLong();
  private JString pValue = new JString();
 
  
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setIdDoc(long zValue) throws Exception {    pIdDoc.setValue(zValue);  }
  public long getIdDoc() throws Exception {     return pIdDoc.getValue();  }
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setListId(long zValue) throws Exception {    pListId.setValue(zValue);  }
  public long getListId() throws Exception {     return pListId.getValue();  }
  public long getOrden() throws Exception {     return pOrden.getValue();  }
  public void setOrden(long value) throws Exception {     pOrden.setValue(value);  }
  public void setValue(String zValue) throws Exception {    pValue.setValue(zValue);  }
  public String getValue() throws Exception {     return pValue.getValue();  }

  /**
   * Constructor de la Clase
   */
  public BizDocListadoParam() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id_doc", pIdDoc);
    this.addItem( "company", pCompany );
    this.addItem( "list_id", pListId );
    this.addItem( "orden", pOrden );
    this.addItem( "value", pValue );
    this.addItem( "descripcion", pDescripcion );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_doc", "Id doc", true, true, 18 );
    this.addFixedItem( KEY, "orden", "Orden", true, true, 5 );
    this.addFixedItem( FIELD, "company", "Company", true, true, 15 );
    this.addFixedItem( FIELD, "list_id", "List Id", true, true, 5 );
    this.addFixedItem( FIELD, "value", "Valor", true, false, 500);
    this.addFixedItem( VIRTUAL, "descripcion", "Descripcion", true, true, 250 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "JUD_DOC_FISICO_LISTADO_PARAM"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default Read() method
   */
  public boolean read( long zIdDoc, long zOrden) throws Exception { 
    this.addFilter( "id_doc",  zIdDoc ); 
    this.addFilter( "orden",  zOrden ); 
    return this.read(); 
  } 
  
	BizCampo objFiltro;
	public BizCampo getObjFiltro() throws Exception {
		if (objFiltro!=null) return objFiltro;
		if (pOrden.isNull()) return null;
		BizCampo t = new BizCampo();
		t.dontThrowException(true);
		if (!t.read(pCompany.getValue(),pListId.getValue(),pOrden.getValue())) return null;
		return objFiltro=t;
	}
	BizFiltroSQL objFiltroSQL;
	public BizFiltroSQL getObjFiltroSQL() throws Exception {
		if (objFiltroSQL!=null) return objFiltroSQL;
		if (pOrden.isNull()) return null;
		BizFiltroSQL t = new BizFiltroSQL();
		t.read(pCompany.getValue(),pListId.getValue(),pOrden.getValue()-1000);
		return objFiltroSQL=t;
	}
  

  public String getDescrParam() throws Exception {
  	if (pOrden.getValue()>1000)
  		return getObjFiltroSQL().getFilterName();
  	if (getObjFiltro()==null) return "";
  	return getObjFiltro().getDescrCampo();
  }

}
