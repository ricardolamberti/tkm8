package pss.tourism.pnr;

import pss.common.customList.config.relation.JRelations;
import pss.common.regions.currency.history.BizMonedaCotizacion;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;

public class BizPNRTax extends JRecord {
  private JString pCodigopnr = new JString();
  private JString pCodigomoneda = new JString();
  private JString pCodigoMonedaLocal = new JString();
  private JString pCodigoimpuesto = new JString();
  private JString pNumeroBoleto = new JString();
  private JCurrency pImporte =new JCurrency() {
  	public void preset() throws Exception {
  		setSimbolo(pCodigomoneda.isNotNull());
  		setMoneda(pCodigomoneda.getValue());
  	};
  }; 
  private JCurrency pImporteUsa =new JCurrency() {
  	public void preset() throws Exception {
			setSimbolo(true);
			setMoneda("USD");
  	};
  };
  private JCurrency pImporteLocal =new JCurrency() {
  	public void preset() throws Exception {
  		setSimbolo(pCodigoMonedaLocal.isNotNull());
  		setMoneda(pCodigoMonedaLocal.getValue());
  	};
  };
  protected JString pCompany=new JString();
  private JBoolean pVirtual = new JBoolean();
	protected JInteger pSecuencia=new JInteger();


	public void setCompany(String zValue)  {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public void setVirtual(boolean zValue)  {
		pVirtual.setValue(zValue);
	}

	public boolean isVirtual() throws Exception {
		return pVirtual.getValue();
	}


  //private BizTaxMap taxMap=null;
	protected JLong pId=new JLong();

	public long getId() throws Exception {
		return pId.getValue();
	}
	public void setId(long val){
		pId.setValue(val);
	}



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCodigopnr(String zValue) throws Exception {    pCodigopnr.setValue(zValue);  }
  public String getCodigopnr() throws Exception {     return pCodigopnr.getValue();  }
  public void setCodigomoneda(String zValue) throws Exception {    pCodigomoneda.setValue(zValue);  }
  public String getCodigomoneda() throws Exception {     return pCodigomoneda.getValue();  }
  public void setCodigomonedaLocal(String zValue) throws Exception {    pCodigoMonedaLocal.setValue(zValue);  }
  public String getCodigomonedaLocal() throws Exception {     return pCodigoMonedaLocal.getValue();  }
  public void setCodigoimpuesto(String zValue) throws Exception {    pCodigoimpuesto.setValue(zValue);  }
  public String getCodigoimpuesto() throws Exception {     return pCodigoimpuesto.getValue();  }
  public void setImporte(double zValue) throws Exception {    pImporte.setValue(zValue);  }
  public double getImporte() throws Exception {     return pImporte.getValue();  }
  public void setImporteUsa(double zValue) throws Exception {    pImporteUsa.setValue(zValue);  }
  public double getImporteUsa() throws Exception {     return pImporteUsa.getValue();  }
  public void setImporteLocal(double zValue) throws Exception {    pImporteLocal.setValue(zValue);  }
  public double getImporteLocal() throws Exception {     return pImporteLocal.getValue();  }
  public void setNumeroBoleto(String zValue) throws Exception {    pNumeroBoleto.setValue(zValue);  }
  public String getNumeroBoleto() throws Exception {     return pNumeroBoleto.getValue();  }
  public void setSecuencia(int zValue) throws Exception {    pSecuencia.setValue(zValue);  }
  public int getSecuencia() throws Exception {     return pSecuencia.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizPNRTax() throws Exception {
    this.addItem( "company", pCompany);
  	this.addItem( "interface_id", pId);
    this.addItem( "codigoPNR", pCodigopnr );
    this.addItem( "CodigoMoneda", pCodigomoneda );
    this.addItem( "CodigoMoneda_local", pCodigoMonedaLocal );
    this.addItem( "numeroboleto", pNumeroBoleto );
    this.addItem( "secuencia", pSecuencia );
    this.addItem( "CodigoImpuesto", pCodigoimpuesto );
    this.addItem( "Importe", pImporte );
    this.addItem( "Importe_usa", pImporteUsa );
    this.addItem( "Importe_local", pImporteLocal );
    this.addItem( "virtual", pVirtual);

  }
  /**
   * Adds the fixed object properties
   */
  @Override
	public void createFixedProperties() throws Exception {
  	this.addFixedItem( KEY, "interface_id", "Id Interfaz", true, true, 50);
    this.addFixedItem( KEY, "secuencia", "Secuencia", true, true, 64 );
  	this.addFixedItem( FIELD, "company", "empresa", true, false, 50);
    this.addFixedItem( FIELD, "codigoPNR", "PNR", true, true, 30 );
    this.addFixedItem( FIELD, "numeroboleto", "Número Boleto", true, true, 50 );
    this.addFixedItem( FIELD, "CodigoImpuesto", "Código Impuesto", true, true, 50 );
    this.addFixedItem( FIELD, "CodigoMoneda", "Código Moneda", true, true, 50 );
    this.addFixedItem( FIELD, "CodigoMoneda_local", "Código Moneda local", true, true, 50 );
    this.addFixedItem( FIELD, "Importe", "Importe", true, true, 18,2 );
    this.addFixedItem( FIELD, "Importe_usa", "Importe USD", true, true, 18,2 );
    this.addFixedItem( FIELD, "Importe_local", "Importe local", true, true, 18,2 );
    this.addFixedItem( FIELD, "Virtual", "virtual", true, false, 1 );
  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return "TUR_PNR_IMPUESTOS"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.tourism.pnr.GuiPNRTaxs");
  	rels.hideField("interface_id");
  	//rels.hideField("company");
  	rels.hideField("secuencia");  	
  	rels.hideField("Virtual");
  	rels.hideField("codigoPNR");
  	rels.hideField("numeroboleto");
  }

  
  public boolean read( String company, String zCodigopnr, String ticket, String zCodigoimpuesto ) throws Exception { 
    clearFilters(); 
    addFilter( "company",  company ); 
    addFilter( "codigoPNR",  zCodigopnr ); 
    addFilter( "numeroboleto",  ticket ); 
    addFilter( "secuencia",  1); 
    addFilter( "CodigoImpuesto",  zCodigoimpuesto ); 
    return Read(); 
  } 
  /*
  public BizTaxMap getObjTaxMap() throws Exception {
  	if (this.taxMap!=null) return this.taxMap;
  	BizTaxMap record = new BizTaxMap();
  	record.SetNoExcSelect(true);
  	if (!record.Read(BizUsuario.getUsr().getCountry(), pCodigoimpuesto.getValue()))
  		JExcepcion.SendError("Tipo de Impuesto no mapeado: ^"+pCodigoimpuesto.getValue());
  	return (this.taxMap=record);
  }
  */
  public boolean hasTaxCode() throws Exception {
  	return this.pCodigoimpuesto.isNotNull();
  }
  
	public String getRecordName() throws Exception {
		return "Impuestos";
	}
	
	public void setTarifasEnDolares(BizPNRTicket tk) throws Exception {
		double cot = BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD", getCodigomoneda(), tk.getCreationDate());
		if (cot == 0 && getCodigomoneda().equals("USD"))
			cot = 1;

		if (getCodigomoneda().equals("USD")) {
			setImporteUsa(getImporte());
		} else if (!getCodigomoneda().equals("USD") && cot!=0) {
			setImporteUsa(JTools.rd(getImporte() / cot, 2));
		} else {
			if (cot==0) {
				String curr = getCodigomoneda();
				String currLocal = getCodigomonedaLocal();
				double cotBase = curr.equals("USD")?1:BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), curr,currLocal, tk.getCreationDate());
			
				if (cotBase==0) {
					cot= BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), curr, currLocal, tk.getCreationDate());
				} else {
					cot = cotBase;
					
				}

			}
			if (cot != 0) {
				setImporteUsa(JTools.rd(getImporte() / cot, 2));
			} 
			

		}

		if (getCodigomonedaLocal()!=null && !getCodigomonedaLocal().equals("") && !getCodigomonedaLocal().equals(getCodigomoneda())) {
			double cotLocal = BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD",tk.getCodigoMonedaLocal(), tk.getCreationDate());
			if (cotLocal == 0)
				cotLocal = 1;
			else 
				cotLocal = 1f/cotLocal;

			setImporteLocal(JTools.rd(getImporteUsa() / cotLocal, 2));
		} else {
			setImporteLocal(getImporte() );
			
		}
		
		

	}

}
