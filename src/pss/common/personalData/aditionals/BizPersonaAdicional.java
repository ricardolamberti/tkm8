package pss.common.personalData.aditionals;

import pss.common.personalData.aditionals.types.BizTipoAdicional;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizPersonaAdicional extends JRecord {
	
  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
	private JString pCompany = new JString();
  private JLong pIdPersona = new JLong();
	private JString pTipo = new JString();
	private JString pDescrTipo = new JString() {
		public void preset() throws Exception {
			setValue(getObjTipoPreferencia().getDescripcion());
		};
	};
	private JString pObservacion = new JString();
  
  public void setCompany(String value) throws Exception { pCompany.setValue(value);}
  public void setIdPresona(long value) throws Exception { pIdPersona.setValue(value);}
  public void setTipo(String value) throws Exception { pTipo.setValue(value);}
  public void setObservacion(String value) throws Exception { pObservacion.setValue(value);}
  
  public String getCompany() throws Exception { return pCompany.getValue();}
  public long getIdPersona() throws Exception { return pIdPersona.getValue();}  
  public String getTipo() throws Exception { return pTipo.getValue();}
  public String getObservacion() throws Exception { return pObservacion.getValue();}
  

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizPersonaAdicional() throws Exception {}

  @Override
	public void createProperties() throws Exception {
    addItem("company", pCompany);
    addItem("id_persona", pIdPersona);
    addItem("tipo", pTipo);    
    addItem("observacion", pObservacion);
    addItem("descr_tipo", pDescrTipo);
  }
  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem(KEY, "id_persona", "Persona", true, true, 18);
    addFixedItem(KEY, "tipo", "Secuencia", true, true, 15);    
    addFixedItem(FIELD, "observacion", "Observacion", true, false, 250);
    addFixedItem(FIELD, "company", "Empresa", true, true, 15);
    addFixedItem(VIRTUAL, "descr_tipo", "Tipo", true, false, 100);
  }

  @Override
	public String GetTable() {
    return "PER_PERSONA_ADICIONAL";
  }

  public boolean Read(String company, String persona, String tipo) throws Exception {
  	this.addFilter("company", company);
  	this.addFilter("id_persona", persona);
  	this.addFilter("tipo", tipo);  	
  	return this.read();
  }
  
  
//  private BizCliente cliente;
//  public BizCliente getObjCliente() throws Exception {
//  	if (this.cliente!=null) return this.cliente;
//	  BizCliente record = new BizCliente();
//	  record.Read(this.pCompany.getValue(), this.pClientGroup.getValue(), this.pCliente.getValue());
//	  return (this.cliente=record);
//  }

	
	private BizTipoAdicional tipo;
	public BizTipoAdicional getObjTipoPreferencia() throws Exception {
		if (this.tipo!=null) return this.tipo;
		BizTipoAdicional t = new BizTipoAdicional(); 
		t.Read(this.pCompany.getValue(), this.pTipo.getValue());  
		return (this.tipo=t);
	}

}

