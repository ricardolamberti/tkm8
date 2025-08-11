package pss.bsp.consolid.model.feeDK;

import java.util.Date;

import pss.bsp.consolid.model.liquidacion.detail.BizLiqDetail;
import pss.common.customList.config.relation.JRelations;
import pss.common.regions.multilanguage.JLanguage;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;

public class BizFeeDK extends JRecord {

	private JLong pId = new JLong();
	private JString pCompany = new JString();
	private JLong pDk = new JLong();
	private JLong pPriority = new JLong();
	private JDate pFechaDesde = new JDate();
	private JDate pFechaHasta = new JDate();
	private JString pAerolineas = new JString();
	private JString pDomInt = new JString();
	private JString pOrigenPais = new JString();
	private JString pOrigenAirport = new JString();
	private JString pDestinoPais = new JString();
	private JString pDestinoAirport = new JString();
	private JString pTipoDoc = new JString();
	private JString pTipoTicket = new JString();

	private JBoolean pNegAerolineas = new JBoolean();
	private JBoolean pNegOrigenPais = new JBoolean();
	private JBoolean pNegOrigenAirport = new JBoolean();
	private JBoolean pNegDestinoPais = new JBoolean();
	private JBoolean pNegDestinoAirport = new JBoolean();
	private JBoolean pNegTipoDoc = new JBoolean();
	private JBoolean pNegTipoTicket = new JBoolean();

	private JString pTipoFee = new JString();
	private JCurrency pFee = new JCurrency();

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setPriority(long zValue) throws Exception { pPriority.setValue(zValue); }
	public long getPriority() throws Exception { return pPriority.getValue(); }

	public void setNegAerolineas(boolean zValue) throws Exception { pNegAerolineas.setValue(zValue); }
	public boolean getNegAerolineas() throws Exception { return pNegAerolineas.getValue(); }

	public void setAerolineas(String zValue) throws Exception { pAerolineas.setValue(zValue); }
	public String getAerolineas() throws Exception { return pAerolineas.getValue(); }

	public void setDomInt(String zValue) throws Exception { pDomInt.setValue(zValue); }
	public String getDomInt() throws Exception { return pDomInt.getValue(); }

	public void setNegOrigenPais(boolean zValue) throws Exception { pNegOrigenPais.setValue(zValue); }
	public boolean getNegOrigenPais() throws Exception { return pNegOrigenPais.getValue(); }
	
	public void setNegOrigenAirport(boolean zValue) throws Exception { pNegOrigenAirport.setValue(zValue); }
	public boolean getNegOrigenAirport() throws Exception { return pNegOrigenAirport.getValue(); }
	
	public void setNegDestinoPais(boolean zValue) throws Exception { pNegDestinoPais.setValue(zValue); }
	public boolean getNegDestinoPais() throws Exception { return pNegDestinoPais.getValue(); }
	
	public void setNegDestinoAirport(boolean zValue) throws Exception { pNegDestinoAirport.setValue(zValue); }
	public boolean getNegDestinoAirport() throws Exception { return pNegDestinoAirport.getValue(); }
	
	public void setNegTipoDoc(boolean zValue) throws Exception { pNegTipoDoc.setValue(zValue); }
	public boolean getNegTipoDoc() throws Exception { return pNegTipoDoc.getValue(); }
		
	public void setNegTipoTicket(boolean zValue) throws Exception { pNegTipoTicket.setValue(zValue); }
	public boolean getNegTipoTicket() throws Exception { return pNegTipoTicket.getValue(); }
	
	public void setOrigenPais(String zValue) throws Exception { pOrigenPais.setValue(zValue); }
	public String getOrigenPais() throws Exception { return pOrigenPais.getValue(); }

	public void setOrigenAirport(String zValue) throws Exception { pOrigenAirport.setValue(zValue); }
	public String getOrigenAirport() throws Exception { return pOrigenAirport.getValue(); }

	public void setDestinoPais(String zValue) throws Exception { pDestinoPais.setValue(zValue); }
	public String getDestinoPais() throws Exception { return pDestinoPais.getValue(); }

	public void setDestinoAirport(String zValue) throws Exception { pDestinoAirport.setValue(zValue); }
	public String getDestinoAirport() throws Exception { return pDestinoAirport.getValue(); }
	
	public void setTipoDoc(String zValue) throws Exception { pTipoDoc.setValue(zValue); }
	public String getTipoDoc() throws Exception { return pTipoDoc.getValue(); }

	public void setTipoTicket(String zValue) throws Exception { pTipoTicket.setValue(zValue); }
	public String getTipoTicket() throws Exception { return pTipoTicket.getValue(); }

	public void setTipoFee(String zValue) throws Exception { pTipoFee.setValue(zValue); }
	public String getTipoFee() throws Exception { return pTipoFee.getValue(); }

	public void setFee(double zValue) throws Exception { pFee.setValue(zValue); }
	public double getFee() throws Exception { return pFee.getValue(); }
	
	public void setFechaDesde(Date zValue) throws Exception { pFechaDesde.setValue(zValue); }
	public Date getFechaDesde() throws Exception { return pFechaDesde.getValue(); }

	public void setFechaHasta(Date zValue) throws Exception { pFechaHasta.setValue(zValue); }
	public Date getFechaHasta() throws Exception { return pFechaHasta.getValue(); }

	public double getRealFee(double total) throws Exception { 
		if (getTipoFee().equals(TF_FIJO)) {
			return getFee();
		}
		return JTools.rd((getFee()/100f)*total,2);
	}
	
	
	public BizFeeDK() throws Exception {
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// createProperties
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("id", pId);
		this.addItem("id_dk", pDk);
		this.addItem("fecha_desde", pFechaDesde);
		this.addItem("fecha_hasta", pFechaHasta);
		this.addItem("fee", pFee);
		this.addItem("priority", pPriority);
		this.addItem("no_aerolineas", pNegAerolineas);
		this.addItem("aerolineas", pAerolineas);
		this.addItem("dom_int", pDomInt);
		this.addItem("origen_pais", pOrigenPais);
		this.addItem("origen_airport", pOrigenAirport);
		this.addItem("destino_pais", pDestinoPais);
		this.addItem("destino_airport", pDestinoAirport);
		this.addItem("tipo_doc", pTipoDoc);
		this.addItem("tipo_ticket", pTipoTicket);
		this.addItem("no_origen_pais", pNegOrigenPais);
		this.addItem("no_origen_airport", pNegOrigenAirport);
		this.addItem("no_destino_pais", pNegDestinoPais);
		this.addItem("no_destino_airport", pNegDestinoAirport);
		this.addItem("no_tipo_doc", pNegTipoDoc);
		this.addItem("no_tipo_ticket", pNegTipoTicket);
		this.addItem("tipo_fee", pTipoFee);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// createFixedProperties
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "id", "Id", false, false, 64);
		this.addFixedItem(FIELD, "company", "Company", true, true, 50);
		this.addFixedItem(FIELD, "id_dk", "Dk", true, false, 64);
		this.addFixedItem(FIELD, "fecha_desde", "Fecha desde", true, false, 18);
		this.addFixedItem(FIELD, "fecha_hasta", "Fecha hasta", true, false, 18);
		this.addFixedItem(FIELD, "priority", "Prioridad", true, false, 10);
		this.addFixedItem(FIELD, "aerolineas", "Aerolíneas", true, false, 1000);
		this.addFixedItem(FIELD, "no_aerolineas", "Neg.Aerolíneas", true, false, 1000);
		this.addFixedItem(FIELD, "dom_int", "Dom/Int", true, false, 100);
		this.addFixedItem(FIELD, "origen_pais", "Origen País", true, false, 1000);
		this.addFixedItem(FIELD, "origen_airport", "Origen Aeropuerto", true, false, 1000);
		this.addFixedItem(FIELD, "destino_pais", "Destino País", true, false, 1000);
		this.addFixedItem(FIELD, "destino_airport", "Destino Aeropuerto", true, false, 1000);
		this.addFixedItem(FIELD, "tipo_doc", "Tipo doc", true, false, 200);
		this.addFixedItem(FIELD, "tipo_ticket", "Tipo ticket", true, false, 200);
		this.addFixedItem(FIELD, "no_origen_pais", "Neg.Origen País", true, false, 1000);
		this.addFixedItem(FIELD, "no_origen_airport", "Neg.Origen Aeropuerto", true, false, 1000);
		this.addFixedItem(FIELD, "no_destino_pais", "Neg.Destino País", true, false, 1000);
		this.addFixedItem(FIELD, "no_destino_airport", "Neg.Destino Aeropuerto", true, false, 1000);
		this.addFixedItem(FIELD, "no_tipo_doc", "Neg.Tipo doc", true, false, 200);
		this.addFixedItem(FIELD, "no_tipo_ticket", "Neg.Tipo ticket", true, false, 200);
		this.addFixedItem(FIELD, "tipo_fee", "Tipo Fee", true, false, 20);
		this.addFixedItem(FIELD, "fee", "Fee", true, false, 18, 2);
	}

  @Override
  public void createControlProperties() throws Exception {
  	this.addControlsProperty("tipo_fee", createControlCombo(JWins.createVirtualWinsFromMap(BizFeeDK.getTiposFee()),null, null) );
  	super.createControlProperties();
  }
  public final static String TF_FIJO = "FIJO";
  public final static String TF_PORC = "PORC";
  
	protected static JMap<String,String> getTiposFee() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement(BizFeeDK.TF_FIJO, JLanguage.translate("Fijo"));
		map.addElement(BizFeeDK.TF_PORC, JLanguage.translate("Porcentaje"));
		return map;
	}
	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "BSP_FEE_DK";
	}

	public void processInsert() throws Exception {
		super.processInsert();
	}

	public void processUpdate() throws Exception {
		super.processUpdate();
	};

	/**
	 * Default Read() method
	 */
	public boolean Read(long zIdtipo) throws Exception {
		this.addFilter("id_cot", zIdtipo);
		return this.read();
	}

	public boolean ReadByDk(String company, BizLiqDetail ticket) throws Exception {

	
		this.addFilter("company", company);
		this.addJoin(JRelations.JOIN, "BSP_DK", "BSP_DK.id=BSP_FEE_DK.id_dk");
		this.addFilter("BSP_DK", "dk", ticket.getDk(), "=");
		this.addFilter("fecha_desde", ticket.getFechaDoc(), "<=");
		if (ticket.getObjTicket()==null) {
			String fechaHastaCond = "     ( BSP_FEE_DK.fecha_hasta is null or BSP_FEE_DK.fecha_hasta >= '" + JDateTools.DateToString(ticket.getFechaDoc(), "dd/MM/yyyy") + "') ";

			String aerolineasCond = 
				" AND ( BSP_FEE_DK.aerolineas is null or BSP_FEE_DK.aerolineas = '' " +
				" OR (BSP_FEE_DK.no_aerolineas = 'S' AND BSP_FEE_DK.aerolineas NOT LIKE '%" + ticket.getPrestador() + "%') " +
				" OR (BSP_FEE_DK.no_aerolineas = 'N' AND BSP_FEE_DK.aerolineas LIKE '%" + ticket.getPrestador() + "%') )";

			String tipoDocCond = 
				" AND ( BSP_FEE_DK.tipo_doc is null or BSP_FEE_DK.tipo_doc = '' " +
				" OR (BSP_FEE_DK.no_tipo_doc = 'S' AND BSP_FEE_DK.tipo_doc NOT LIKE '%" + ticket.getTipoDoc() + "%') " +
				" OR (BSP_FEE_DK.no_tipo_doc = 'N' AND BSP_FEE_DK.tipo_doc LIKE '%" + ticket.getTipoDoc() + "%') )";

			String tipoTicketCond = (ticket.getObjTicket() == null ? "" :
				" AND ( BSP_FEE_DK.tipo_ticket is null or BSP_FEE_DK.tipo_ticket = '')");

			String origenPaisCond = (ticket.getObjTicket() == null ?
				" AND ( BSP_FEE_DK.origen_pais is null or BSP_FEE_DK.origen_pais = '') " :
				" AND ( BSP_FEE_DK.origen_pais is null or BSP_FEE_DK.origen_pais = '')");

			String origenAirportCond = (ticket.getObjTicket() == null ?
				" AND ( BSP_FEE_DK.origen_airport is null or BSP_FEE_DK.origen_airport = '') " :
				" AND ( BSP_FEE_DK.origen_airport is null or BSP_FEE_DK.origen_airport = '')");

			String destinoPaisCond = (ticket.getObjTicket() == null ?
				" AND ( BSP_FEE_DK.destino_pais is null or BSP_FEE_DK.destino_pais = '') " :
				" AND ( BSP_FEE_DK.destino_pais is null or BSP_FEE_DK.destino_pais = '')");

			String destinoAirportCond = (ticket.getObjTicket() == null ?
				" AND ( BSP_FEE_DK.destino_airport is null or BSP_FEE_DK.destino_airport = '') " :
				" AND ( BSP_FEE_DK.destino_airport is null or BSP_FEE_DK.destino_airport = '')");

			String domIntCond = " AND ( BSP_FEE_DK.dom_int is null or BSP_FEE_DK.dom_int = '' or BSP_FEE_DK.dom_int = '" +
				(ticket.getObjTicket() == null ? ticket.getTipoServicio() : (ticket.getTipoServicio().equals("LI") ? "I" : "D")) + "')";
			String s = "where " + fechaHastaCond + aerolineasCond + tipoDocCond + tipoTicketCond + domIntCond +
					origenPaisCond + origenAirportCond + destinoPaisCond + destinoAirportCond;
			this.addFixedFilter(s);
		} else {
			String fechaHastaCond = "     ( BSP_FEE_DK.fecha_hasta is null or BSP_FEE_DK.fecha_hasta >= '" + JDateTools.DateToString(ticket.getFechaDoc(), "dd/MM/yyyy") + "') ";

			String aerolineasCond = 
				" AND ( BSP_FEE_DK.aerolineas is null or BSP_FEE_DK.aerolineas = '' " +
				" OR (BSP_FEE_DK.no_aerolineas = 'S' AND BSP_FEE_DK.aerolineas NOT LIKE '%" + ticket.getPrestador() + "%') " +
				" OR (BSP_FEE_DK.no_aerolineas = 'N' AND BSP_FEE_DK.aerolineas LIKE '%" + ticket.getPrestador() + "%') )";

			String tipoDocCond = 
				" AND ( BSP_FEE_DK.tipo_doc is null or BSP_FEE_DK.tipo_doc = '' " +
				" OR (BSP_FEE_DK.no_tipo_doc = 'S' AND BSP_FEE_DK.tipo_doc NOT LIKE '%" + ticket.getTipoDoc() + "%') " +
				" OR (BSP_FEE_DK.no_tipo_doc = 'N' AND BSP_FEE_DK.tipo_doc LIKE '%" + ticket.getTipoDoc() + "%') )";

			String tipoTicketCond = (ticket.getObjTicket() == null ? "" :
				" AND ( BSP_FEE_DK.tipo_ticket is null or BSP_FEE_DK.tipo_ticket = '' " +
				" OR (BSP_FEE_DK.no_tipo_ticket = 'S' AND BSP_FEE_DK.tipo_ticket NOT LIKE '%" + ticket.getObjTicket().getTipoboleto() + "%') " +
				" OR (BSP_FEE_DK.no_tipo_ticket = 'N' AND BSP_FEE_DK.tipo_ticket LIKE '%" + ticket.getObjTicket().getTipoboleto() + "%') )");

			String origenPaisCond = (ticket.getObjTicket() == null ?
				" AND ( BSP_FEE_DK.origen_pais is null or BSP_FEE_DK.origen_pais = '') " :
				" AND ( BSP_FEE_DK.origen_pais is null or BSP_FEE_DK.origen_pais = '' " +
				" OR (BSP_FEE_DK.no_origen_pais = 'S' AND BSP_FEE_DK.origen_pais NOT LIKE '%" + ticket.getObjTicket().getPaisOrigen() + "%') " +
				" OR (BSP_FEE_DK.no_origen_pais = 'N' AND BSP_FEE_DK.origen_pais LIKE '%" + ticket.getObjTicket().getPaisOrigen() + "%') )");

			String origenAirportCond = (ticket.getObjTicket() == null ?
				" AND ( BSP_FEE_DK.origen_airport is null or BSP_FEE_DK.origen_airport = '') " :
				" AND ( BSP_FEE_DK.origen_airport is null or BSP_FEE_DK.origen_airport = '' " +
				" OR (BSP_FEE_DK.no_origen_airport = 'S' AND BSP_FEE_DK.origen_airport NOT LIKE '%" + ticket.getObjTicket().getAeropuertoOrigen() + "%') " +
				" OR (BSP_FEE_DK.no_origen_airport = 'N' AND BSP_FEE_DK.origen_airport LIKE '%" + ticket.getObjTicket().getAeropuertoOrigen() + "%') )");

			String destinoPaisCond = (ticket.getObjTicket() == null ?
				" AND ( BSP_FEE_DK.destino_pais is null or BSP_FEE_DK.destino_pais = '') " :
				" AND ( BSP_FEE_DK.destino_pais is null or BSP_FEE_DK.destino_pais = '' " +
				" OR (BSP_FEE_DK.no_destino_pais = 'S' AND BSP_FEE_DK.destino_pais NOT LIKE '%" + ticket.getObjTicket().getPaisDestino() + "%') " +
				" OR (BSP_FEE_DK.no_destino_pais = 'N' AND BSP_FEE_DK.destino_pais LIKE '%" + ticket.getObjTicket().getPaisDestino() + "%') )");

			String destinoAirportCond = (ticket.getObjTicket() == null ?
				" AND ( BSP_FEE_DK.destino_airport is null or BSP_FEE_DK.destino_airport = '') " :
				" AND ( BSP_FEE_DK.destino_airport is null or BSP_FEE_DK.destino_airport = '' " +
				" OR (BSP_FEE_DK.no_destino_airport = 'S' AND BSP_FEE_DK.destino_airport NOT LIKE '%" + ticket.getObjTicket().getAeropuertoDestino() + "%') " +
				" OR (BSP_FEE_DK.no_destino_airport = 'N' AND BSP_FEE_DK.destino_airport LIKE '%" + ticket.getObjTicket().getAeropuertoDestino() + "%') )");

			String domIntCond = " AND ( BSP_FEE_DK.dom_int is null or BSP_FEE_DK.dom_int = '' or BSP_FEE_DK.dom_int = '" +
				(ticket.getObjTicket() == null ? ticket.getTipoServicio() : (ticket.getTipoServicio().equals("LI") ? "I" : "D")) + "')";
			String s = "where " + fechaHastaCond + aerolineasCond + tipoDocCond + tipoTicketCond + domIntCond +
					origenPaisCond + origenAirportCond + destinoPaisCond + destinoAirportCond;
			
			this.addFixedFilter(s);
		}

		this.addOrderBy("priority", "asc");

		return this.read();
	}

}
