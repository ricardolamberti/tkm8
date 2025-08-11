package pss.bsp.contrato.detalle.prorrateo.cliente;

import pss.bsp.contrato.detalle.prorrateo.tickets.BizTicketProrrateo;
import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JIntervalDate;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizClienteProrrateo extends JRecord {

  protected JString pCompany = new JString();
  protected JIntervalDate pPeriodo = new JIntervalDate();
  protected JString pCliente = new JString();
  protected JString pDescripcion = new JString();
  protected JString pNumero = new JString();
  protected JString pCodigoMoneda = new JString();
  protected JCurrency pComision = new JCurrency()	{
  	public void preset() throws Exception {
		setSimbolo(pCodigoMoneda.isNotNull());
		setMoneda(pCodigoMoneda.getValue());
  	}
	};
  protected JCurrency pMonto = new JCurrency()	{
  	public void preset() throws Exception {
		setSimbolo(pCodigoMoneda.isNotNull());
		setMoneda(pCodigoMoneda.getValue());
  	}
	};
  
 protected JObjBDs<BizTicketProrrateo> pDetalle = new JObjBDs();
	  
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Constructor de la Clase
   */
  public BizClienteProrrateo() throws Exception {
  }

  public void processInsertClon() throws Exception {
		super.processInsert();
	}

  public String getCompany() throws Exception {
  	return pCompany.getValue();
  }
  public void setCompany(String value) throws Exception {
  	pCompany.setValue(value);
  }
  public String getCliente() throws Exception {
  	return pCliente.getValue();
  }
  public void setCliente(String value) throws Exception {
  	pCliente.setValue(value);
  }
  public String getClienteDescripcion() throws Exception {
  	return pDescripcion.getValue();
  }
  public void setClienteDescripcion(String value) throws Exception {
  	pDescripcion.setValue(value);
  }
  public String getClienteNumero() throws Exception {
  	return pNumero.getValue();
  }
  public void setClienteNumero(String value) throws Exception {
  	pNumero.setValue(value);
  }
  public String getCodigoMoneda() throws Exception {
  	return pCodigoMoneda.getValue();
  }
  public void setCodigoMoneda(String value) throws Exception {
  	pCodigoMoneda.setValue(value);
  }
  public String getPeriodo() throws Exception {
  	return pPeriodo.getValue();
  }
  public void setPeriodo(String value) throws Exception {
  	pPeriodo.setValue(value);
  }
  public String getNumero() throws Exception {
  	return pNumero.getValue();
  }
  public void setNumero(String value) throws Exception {
  	pNumero.setValue(value);
  }
  public JRecords<BizTicketProrrateo> getDetalle() throws Exception {
  	return pDetalle.getValue();
  }
  public void setDetalle(JRecords<BizTicketProrrateo> value) throws Exception {
  	pDetalle.setValue(value);
  }
  public double getMonto() throws Exception {
  	return pMonto.getValue();
  }
  public void setMonto(double value) throws Exception {
  	pMonto.setValue(value);
  }
  public double getComision() throws Exception {
  	return pComision.getValue();
  }
  public void setComision(double value) throws Exception {
  	pComision.setValue(value);
  }
  public void createProperties() throws Exception {
     this.addItem( "company", pCompany );
   	 this.addItem( "periodo", pPeriodo );
     this.addItem( "cliente", pCliente );
     this.addItem( "descripcion", pDescripcion );
     this.addItem( "numero", pNumero );
     this.addItem( "detalle", pDetalle );
     this.addItem( "moneda", pCodigoMoneda );
     this.addItem( "monto", pMonto );
     this.addItem( "comision", pComision );
       
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
  	 this.addFixedItem( KEY, "company", "company", true, true, 18 );
  	 this.addFixedItem( FIELD, "periodo", "periodo", true, true, 200 );
  	 this.addFixedItem( FIELD, "descripcion", "descripcion", true, true, 200 );
  	 this.addFixedItem( FIELD, "cliente", "cliente", true, true, 200 );
  	 this.addFixedItem( FIELD, "numero", "numero", true, true, 200 );
  	 this.addFixedItem( FIELD, "moneda", "moneda", true, true, 10 );
  	 this.addFixedItem( FIELD, "monto", "Tarifa", true, true, 18, 2 );
  	 this.addFixedItem( FIELD, "comision", "Comision", true, true, 18, 2 );
     this.addFixedItem( RECORDS, "detalle", "detalle", true, true, 18 ).setClase(BizTicketProrrateo.class);

  }
  
  /**
   * Returns the table name
   */
  public String GetTable() { return ""; }


}
