package  pss.bsp.interfaces.copa.detalle;

import java.util.Date;

import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.tourism.pnr.BizPNRTicket;

public class BizCopaDetalle extends JRecord  {

	private JString pCompany=new JString();
	private JString pOwner=new JString();
	private JString pIdpdf=new JString();
	private JLong pLinea=new JLong();
	private JString pIdAerolinea=new JString();
	private JString pOrigen=new JString();
	private JString pDestino=new JString();
	private JString pMarketingId=new JString();
	private JFloat pFMS=new JFloat();
	private JFloat pMinimo=new JFloat();
  private JDate   pPeriodoDesde = new JDate();
  private JDate   pPeriodoHasta = new JDate();

  public void setPeriodoDesde(Date zValue) throws Exception {    pPeriodoDesde.setValue(zValue);  }
  public Date getPeriodoDesde() throws Exception {     return pPeriodoDesde.getValue();  }
  public void setPeriodoHasta(Date zValue) throws Exception {    pPeriodoHasta.setValue(zValue);  }
  public Date getPeriodoHasta() throws Exception {     return pPeriodoHasta.getValue();  }
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public boolean isNullCompany() throws Exception {
		return pCompany.isNull();
	}

	public void setNullToCompany() throws Exception {
		pCompany.setNull();
	}
	public void setOwner(String zValue) throws Exception {
		pOwner.setValue(zValue);
	}

	public String getOwner() throws Exception {
		return pOwner.getValue();
	}

	public boolean isNullOwner() throws Exception {
		return pOwner.isNull();
	}

	public void setNullToOwner() throws Exception {
		pOwner.setNull();
	}

	public void setIdpdf(String zValue) throws Exception {
		pIdpdf.setValue(zValue);
	}

	public String getIdpdf() throws Exception {
		return pIdpdf.getValue();
	}

	public boolean isNullIdpdf() throws Exception {
		return pIdpdf.isNull();
	}

	public void setNullToIdpdf() throws Exception {
		pIdpdf.setNull();
	}

	public void setLinea(long zValue) throws Exception {
		pLinea.setValue(zValue);
	}

	public long getLinea() throws Exception {
		return pLinea.getValue();
	}

	public boolean isNullLinea() throws Exception {
		return pLinea.isNull();
	}

	public void setNullToLinea() throws Exception {
		pLinea.setNull();
	}

	public void setDestino(String zValue) throws Exception {
		pDestino.setValue(zValue);
	}

	public String getDestino() throws Exception {
		return pDestino.getValue();
	}

	public boolean isNullDestino() throws Exception {
		return pDestino.isNull();
	}

	public void setNullToDestino() throws Exception {
		pDestino.setNull();
	}

	public void setOrigen(String zValue) throws Exception {
		pOrigen.setValue(zValue);
	}

	public String getOrigen() throws Exception {
		return pOrigen.getValue();
	}

	public boolean isNullOrigen() throws Exception {
		return pOrigen.isNull();
	}

	public void setNullToOrigen() throws Exception {
		pOrigen.setNull();
	}

	public void setIdAerolinea(String zValue) throws Exception {
		pIdAerolinea.setValue(zValue);
	}

	public long getIdAerolinea() throws Exception {
		return Long.parseLong(pIdAerolinea.getValue());
	}

	public boolean isNullIdAerolinea() throws Exception {
		return pIdAerolinea.isNull();
	}

	public void setNullToIdAerolinea() throws Exception {
		pIdAerolinea.setNull();
	}

	public void setMarketingID(String zValue) throws Exception {
		pMarketingId.setValue(zValue);
	}

	public String getMarketingID() throws Exception {
		return pMarketingId.getValue();
	}

	public boolean isNullMarketingID() throws Exception {
		return pMarketingId.isNull();
	}

	public void setNullToMarketingID() throws Exception {
		pMarketingId.setNull();
	}
	
	public void setFMS(double zValue) throws Exception {
		pFMS.setValue(zValue);
	}

	public double getFMS() throws Exception {
		return pFMS.getValue();
	}
	public void setMinimo(double zValue) throws Exception {
		pMinimo.setValue(zValue);
	}

	public double getMinimo() throws Exception {
		return pMinimo.getValue();
	}


	public boolean isNullFMS() throws Exception {
		return pFMS.isNull();
	}

	public void setNullToFMS() throws Exception {
		pFMS.setNull();
	}

	public boolean isNullMinimo() throws Exception {
		return pMinimo.isNull();
	}

	public void setNullToMinimo() throws Exception {
		pMinimo.setNull();
	}


	/**
	 * Constructor de la Clase
	 */
	public BizCopaDetalle() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("owner", pOwner);
		this.addItem("idPDF", pIdpdf);
		this.addItem("linea", pLinea);
		this.addItem("origen", pOrigen);
		this.addItem("destino", pDestino);
		this.addItem("marketing_id", pMarketingId);
		this.addItem("id_aerolinea", pIdAerolinea);
		this.addItem("fms", pFMS);
		this.addItem("minimo", pMinimo);
	  this.addItem( "periodo_desde", pPeriodoDesde );
	  this.addItem( "periodo_hasta", pPeriodoHasta );
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Company", true, true, 50);
		this.addFixedItem(KEY, "idPDF", "Idpdf", true, true, 300);
		this.addFixedItem(KEY, "linea", "Linea", true, true, 18);
		this.addFixedItem(FIELD, "owner", "Owner", true, true, 50);
		this.addFixedItem(FIELD, "origen", "Origen", true, false, 50);
		this.addFixedItem(FIELD, "destino", "destino", true, false, 50);
		this.addFixedItem(FIELD, "marketing_id", "Marketing ID", true, false, 50);
		this.addFixedItem(FIELD, "id_aerolinea", "ID Aerolinea", true, false, 50);
		this.addFixedItem(FIELD, "fms", "FMS", true, false, 18, 2);
		this.addFixedItem(FIELD, "minimo", "Minimo", true, false, 18, 2);
	  this.addFixedItem( FIELD, "periodo_desde", "Periodo Desde", true, false, 50 );
	  this.addFixedItem( FIELD, "periodo_hasta", "Periodo Hasta", true, false, 50 );
	}

	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.bsp.interfaces.copa.detalle.GuiCopaDetalles");
  	JRelation rel; 
  	
  	rel = rels.addRelationForce(10, "restriccion usuario");
   	rel.addFilter(" (BSP_COPA_DETALLE.company is null or BSP_COPA_DETALLE.company in (COMPANY_CUSTOMLIST)) ");
// 	rel.addJoin("BSP_COPA_DETALLE.company", BizUsuario.getUsr().getCompany());
//  	rel.addJoin("BSP_COPA_DETALLE.owner", BizUsuario.getUsr().GetUsuario());
	  
   	rel=rels.addRelationParent(40, "PNRs", BizPNRTicket.class, "boleto");
  	rel.addJoin("BSP_COPA_DETALLE.company", "TUR_PNR_BOLETO.company");
  	rel.addJoin("BSP_COPA_DETALLE.marketing_id", "TUR_PNR_BOLETO.mercado");
  	rel.setTypeJoin(JRelations.JOIN_LEFT);

//   	rel=rels.addRelationParent(50, "BOs", BizBODetalle.class, "boleto");
//  	rel.addJoin("BSP_COPA_DETALLE.company", "BSP_BO_DETALLE.company");
//  	rel.addJoin("BSP_COPA_DETALLE.boleto", "BSP_BO_DETALLE.boleto");
//  	rel.setTypeJoin(JRelations.JOIN_LEFT);

  	//rels.hideField("company");
  	rels.hideField("owner");
  }


	
	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "BSP_COPA_DETALLE";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Default read() method
	 */
	public boolean read(String zCompany,  String zIdpdf, long zLinea) throws Exception {
		addFilter("company", zCompany);
//		addFilter("owner", zOwner);
		addFilter("idPDF", zIdpdf);
		addFilter("linea", zLinea);
		return read();
	}

	public boolean read(String zCompany, Date zDate, String idAerolinea, String mercado) throws Exception {
		addFilter("company", zCompany);
		addFilter("periodo_desde", zDate, ">=");
		addFilter("periodo_hasta", zDate, "<=");
		addFilter("id_aerolinea", idAerolinea);
		addFilter("marketing_id", mercado);
		return read();
	}

		public boolean read(String zCompany,  String zIdpdf, String origen,String destino) throws Exception {
			addFilter("company", zCompany);
//			addFilter("owner", zOwner);
			addFilter("idPDF", zIdpdf);
			addFilter("origen", origen);
			addFilter("destino", destino);
			return read();
		}

	
	@Override
	public void processInsert() throws Exception {
		if (pOwner.isNull()) pOwner.setValue(BizUsuario.getUsr().GetUsuario());
		super.processInsert();
	}

	@Override
	public void processUpdate() throws Exception {
		if (pOwner.isNull()) pOwner.setValue(BizUsuario.getUsr().GetUsuario());
		super.processUpdate();
	}

	

}
