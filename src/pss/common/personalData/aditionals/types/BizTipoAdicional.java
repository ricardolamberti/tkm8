package pss.common.personalData.aditionals.types;

import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizTipoAdicional extends JRecord {
	
  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
	private JString pCompany = new JString();
	private JString pTipo = new JString();
	private JString pDescripcion = new JString();
  
  public void setCompany(String value) throws Exception { pCompany.setValue(value);}
  public void setTipo(String value) throws Exception { pTipo.setValue(value);}
  public void setDescripcion(String value) throws Exception { pDescripcion.setValue(value);}
  
  public String getCompany() throws Exception { return pCompany.getValue();}
  public String getTipo() throws Exception { return pTipo.getValue();}
  public String getDescripcion() throws Exception { return pDescripcion.getValue();}
  

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizTipoAdicional() throws Exception {}

  @Override
	public void createProperties() throws Exception {
    addItem("company", pCompany);
    addItem("tipo", pTipo);    
    addItem("descripcion", pDescripcion);
  }
  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem(KEY, "company", "Empresa", true, true, 15);
    addFixedItem(KEY, "tipo", "Secuencia", true, true, 15);    
    addFixedItem(FIELD, "descripcion", "Descripción", true, false, 100);
  }

  @Override
	public String GetTable() {
    return "PER_TIPO_ADICIONAL";
  }

  public boolean Read(String company, String tipo) throws Exception {
  	this.addFilter("company", company);
  	this.addFilter("tipo", tipo);  	
  	return this.read();
  }
  
  
}

