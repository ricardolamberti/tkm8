package pss.bsp.consola.datos.cadenas;

import java.util.Date;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.tourism.pnr.BizPNRTicket;

public class BizCadena extends JRecord {
	protected JLong pId = new JLong();
	protected JString pCompany = new JString();
	private JString pCodigopnr = new JString();
	private JString pNumeroboleto = new JString();
	private JString pCustomerIdReducido = new JString();
	private JDate pCreationDate = new JDate();
	private JDate pVoidDate = new JDate();
	private JString pTipoOperacion = new JString();
	private JDate pDepartureDate = new JDate();
	private JString pNombrePasajero = new JString();
	protected JString pGDS = new JString();
	protected JString pAeropuertoOrigen = new JString();
	protected JString pAeropuertoDestino = new JString();
	private JBoolean pVoid = new JBoolean();
	private JBoolean pInternacional = new JBoolean();
	private JBoolean pConReemisiones = new JBoolean();
		
	private JString pCodigoaerolinea = new JString();
	private JLong pRefOriginal1 = new JLong();
	private JLong pRefOriginal2 = new JLong();
	private JLong pRefOriginal3 = new JLong();
	private JLong pRefOriginal4 = new JLong();
	private JLong pRefOriginal5 = new JLong();
	private JLong pRefOriginal6 = new JLong();
	private JLong pRefOriginal7 = new JLong();
	private JLong pRefOriginal8 = new JLong();
	private JLong pRefOriginal9 = new JLong();
	private JLong pRefOriginal10 = new JLong();
	
	private JString pNumeroboleto1 = new JString();
	private JString pNumeroboleto2 = new JString();
	private JString pNumeroboleto3 = new JString();
	private JString pNumeroboleto4 = new JString();
	private JString pNumeroboleto5 = new JString();
	private JString pNumeroboleto6 = new JString();
	private JString pNumeroboleto7 = new JString();
	private JString pNumeroboleto8 = new JString();
	private JString pNumeroboleto9 = new JString();
	private JString pNumeroboleto10 = new JString();
	
	BizPNRTicket[] objReemitted = new BizPNRTicket[10];
	BizPNRTicket objBoleto;
	
	public boolean hasObjReemitted(int version) throws Exception {
		if (version==1) return pRefOriginal1.isNotNull();
		if (version==2) return pRefOriginal2.isNotNull();
		if (version==3) return pRefOriginal3.isNotNull();
		if (version==4) return pRefOriginal4.isNotNull();
		if (version==5) return pRefOriginal5.isNotNull();
		if (version==6) return pRefOriginal6.isNotNull();
		if (version==7) return pRefOriginal7.isNotNull();
		if (version==8) return pRefOriginal8.isNotNull();
		if (version==9) return pRefOriginal9.isNotNull();
		if (version==10) return pRefOriginal10.isNotNull();
		return false;
	}
	public BizPNRTicket getObjReemitted(int level) throws Exception {
		if (!hasObjReemitted(level))
			return null;
		if (this.objReemitted[level-1] != null)
			return this.objReemitted[level-1];
		BizPNRTicket a = new BizPNRTicket();
		a.dontThrowException(true);
		long ref = 0;
		if (level==1) ref = this.pRefOriginal1.getValue();
		if (level==2) ref = this.pRefOriginal2.getValue();
		if (level==3) ref = this.pRefOriginal3.getValue();
		if (level==4) ref = this.pRefOriginal4.getValue();
		if (level==5) ref = this.pRefOriginal5.getValue();
		if (level==6) ref = this.pRefOriginal6.getValue();
		if (level==7) ref = this.pRefOriginal7.getValue();
		if (level==8) ref = this.pRefOriginal8.getValue();
		if (level==9) ref = this.pRefOriginal9.getValue();
		if (level==10) ref = this.pRefOriginal10.getValue();
		
		if (!a.read(ref))
			return null;
		return (this.objReemitted[level-1] = a);
	}
	public BizPNRTicket getObjBoleto() throws Exception {
		if (this.objBoleto != null)
			return this.objBoleto;
		BizPNRTicket a = new BizPNRTicket();
		a.dontThrowException(true);
		long ref = this.pId.getValue();
		
		if (!a.read(ref))
			return null;
		return (this.objBoleto = a);
	}

	public void setCodigopnr(String zValue) throws Exception {
		pCodigopnr.setValue(zValue);
	}
	public void setNumeroboleto(String zValue) throws Exception {
		pNumeroboleto.setValue(zValue);
	}
	public void setDK(String zValue) throws Exception {
		pCustomerIdReducido.setValue(zValue);
	}
	public String getNumeroboleto() throws Exception {
		return pNumeroboleto.getValue();
	}
	public Date getCreationDate() throws Exception {
		return this.pCreationDate.getValue();
	}
	public Date getVoidDate() throws Exception {
		return this.pVoidDate.getValue();
	}

	public String getCodigopnr() throws Exception {
		return pCodigopnr.getValue();
	}

	
	public long getId() throws Exception {
		return pId.getValue();
	}

	public void setId(long val) {
		pId.setValue(val);
	}

	
	public void createProperties() throws Exception {
		this.addItem("interface_id", pId);
		this.addItem("company", pCompany);
		this.addItem("CodigoPNR", pCodigopnr);
		this.addItem("creation_date", pCreationDate);
		this.addItem("void_date", pVoidDate);
		this.addItem("CodigoAerolinea", pCodigoaerolinea);
		this.addItem("NumeroBoleto", pNumeroboleto);
		this.addItem("customer_id_reducido", pCustomerIdReducido);
		this.addItem("tipo_operacion", pTipoOperacion);
		this.addItem("departure_date", pDepartureDate);
		this.addItem("nombre_pasajero", pNombrePasajero);
		this.addItem("gds", pGDS);
		this.addItem("aeropuerto_origen", pAeropuertoOrigen);
		this.addItem("aeropuerto_destino", pAeropuertoDestino);
		this.addItem("void", pVoid);
		this.addItem("Internacional", pInternacional);
		this.addItem("con_reemisiones", pConReemisiones);

		
		this.addItem("numeroboleto_1", pNumeroboleto1);
		this.addItem("numeroboleto_2", pNumeroboleto2);
		this.addItem("numeroboleto_3", pNumeroboleto3);
		this.addItem("numeroboleto_4", pNumeroboleto4);
		this.addItem("numeroboleto_5", pNumeroboleto5);
		this.addItem("numeroboleto_6", pNumeroboleto6);
		this.addItem("numeroboleto_7", pNumeroboleto7);
		this.addItem("numeroboleto_8", pNumeroboleto8);
		this.addItem("numeroboleto_9", pNumeroboleto9);
		this.addItem("numeroboleto_10", pNumeroboleto10);
		this.addItem("ref_original_1", pRefOriginal1);
		this.addItem("ref_original_2", pRefOriginal2);
		this.addItem("ref_original_3", pRefOriginal3);
		this.addItem("ref_original_4", pRefOriginal4);
		this.addItem("ref_original_5", pRefOriginal5);
		this.addItem("ref_original_6", pRefOriginal6);
		this.addItem("ref_original_7", pRefOriginal7);
		this.addItem("ref_original_8", pRefOriginal8);
		this.addItem("ref_original_9", pRefOriginal9);
		this.addItem("ref_original_10", pRefOriginal10);
		
		
	}
	
	/**
	 * Adds the fixed object properties
	 */
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "interface_id", "Id Interfaz", false, false, 18);
		this.addFixedItem(FIELD, "company", "Empresa", true, false, 15);
		this.addFixedItem(FIELD, "creation_date", "Fecha emisión", true, false, 10);
		this.addFixedItem(FIELD, "void_date", "Fecha Anulación", true, false, 10);
		this.addFixedItem(FIELD, "CodigoPNR", "Codigo PNR", true, true, 10);
		this.addFixedItem(FIELD, "CodigoAerolinea", "Aerolínea Cod.", true, false, 2);
		this.addFixedItem(FIELD, "NumeroBoleto", "Nro.Boleto", true, false, 50);
		this.addFixedItem(FIELD, "customer_id_reducido", "DK", true, false, 50);
		this.addFixedItem(FIELD, "tipo_operacion", "Tipo Operacion", true, false, 300);
		this.addFixedItem(FIELD, "departure_date", "Fecha Salida", true, false, 10);
		this.addFixedItem(FIELD, "nombre_pasajero", "Nombre Pasajero", true, false, 150);
		this.addFixedItem(FIELD, "gds", "GDS", true, false, 10);
		this.addFixedItem(FIELD, "aeropuerto_origen", "Cod.Aeropuerto Origen", true, false, 10);
		this.addFixedItem(FIELD, "aeropuerto_destino", "Cod.Aeropuerto Destino", true, false, 10);
		this.addFixedItem(FIELD, "void", "Anulado?", true, true, 1);
		this.addFixedItem(FIELD, "Internacional", "Internacional", true, true, 1);
		this.addFixedItem(VIRTUAL, "con_reemisiones", "con_reemisiones", true, true, 1);


		
		this.addFixedItem(FIELD, "ref_original_1", "referencia 1", true, false, 18, 0);
		this.addFixedItem(FIELD, "ref_original_2", "referencia 2", true, false, 18, 0);
		this.addFixedItem(FIELD, "ref_original_3", "referencia 3", true, false, 18, 0);
		this.addFixedItem(FIELD, "ref_original_4", "referencia 4", true, false, 18, 0);
		this.addFixedItem(FIELD, "ref_original_5", "referencia 5", true, false, 18, 0);
		this.addFixedItem(FIELD, "ref_original_6", "referencia 6", true, false, 18, 0);
		this.addFixedItem(FIELD, "ref_original_7", "referencia 7", true, false, 18, 0);
		this.addFixedItem(FIELD, "ref_original_8", "referencia 8", true, false, 18, 0);
		this.addFixedItem(FIELD, "ref_original_9", "referencia 9", true, false, 18, 0);
		this.addFixedItem(FIELD, "ref_original_10", "referencia 10", true, false, 18, 0);
		this.addFixedItem(FIELD, "numeroboleto_1", "Revisado 1", true, false, 18, 0);
		this.addFixedItem(FIELD, "numeroboleto_2", "Revisado 2", true, false, 18, 0);
		this.addFixedItem(FIELD, "numeroboleto_3", "Revisado 3", true, false, 18, 0);
		this.addFixedItem(FIELD, "numeroboleto_4", "Revisado 4", true, false, 18, 0);
		this.addFixedItem(FIELD, "numeroboleto_5", "Revisado 5", true, false, 18, 0);
		this.addFixedItem(FIELD, "numeroboleto_6", "Revisado 6", true, false, 18, 0);
		this.addFixedItem(FIELD, "numeroboleto_7", "Revisado 7", true, false, 18, 0);
		this.addFixedItem(FIELD, "numeroboleto_8", "Revisado 8", true, false, 18, 0);
		this.addFixedItem(FIELD, "numeroboleto_9", "Revisado 9", true, false, 18, 0);
		this.addFixedItem(FIELD, "numeroboleto_10", "Revisado 10", true, false, 18, 0);
	}

  public BizCadena() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
   * Returns the table name
   */
  public String GetTable() { return "tickets_cadenas"; }
  public String GetTableTemporal() throws Exception  { 
  	String sql="";

  	sql+=" select b.interface_id as interface_id,";
  	sql+="        b.company as company,";
  	sql+="        b.creation_date as creation_date,";
  	sql+="        b.departure_date as departure_date,";
  	sql+="        b.customer_id_reducido as customer_id_reducido,";
  	sql+="        b.void_date as void_date,";
  	sql+="        b.CodigoPNR as CodigoPNR,";
  	sql+="        b.Internacional as Internacional,";
  	sql+="        b.tipo_operacion as tipo_operacion,";
  	sql+="        b.nombre_pasajero as nombre_pasajero,";
  	sql+="        b.aeropuerto_origen as aeropuerto_origen,";
  	sql+="        b.aeropuerto_destino as aeropuerto_destino,";
  	sql+="        b.gds as gds,";
  	sql+="        b.void as void,";
   	sql+="        b.NumeroBoleto as NumeroBoleto,";
   	sql+="        b.CodigoAerolinea as CodigoAerolinea,";
  	sql+="       obtener_numeroboleto_a_distancia( b.interface_id, 1) as numeroboleto_1,";
  	sql+="       obtener_numeroboleto_a_distancia( b.interface_id, 2) as numeroboleto_2,";
  	sql+="       obtener_numeroboleto_a_distancia( b.interface_id, 3) as numeroboleto_3,";
  	sql+="       obtener_numeroboleto_a_distancia( b.interface_id, 4) as numeroboleto_4,";
  	sql+="       obtener_numeroboleto_a_distancia( b.interface_id, 5) as numeroboleto_5,";
  	sql+="       obtener_numeroboleto_a_distancia( b.interface_id, 6) as numeroboleto_6,";
  	sql+="       obtener_numeroboleto_a_distancia( b.interface_id, 7) as numeroboleto_7,";
  	sql+="       obtener_numeroboleto_a_distancia( b.interface_id, 8) as numeroboleto_8,";
  	sql+="       obtener_numeroboleto_a_distancia( b.interface_id, 9) as numeroboleto_9,";
  	sql+="       obtener_numeroboleto_a_distancia( b.interface_id, 10) as numeroboleto_10,";
  	sql+="       obtener_interface_id_a_distancia( b.interface_id, 1) as ref_original_1,";
  	sql+="       obtener_interface_id_a_distancia( b.interface_id, 2) as ref_original_2,";
  	sql+="       obtener_interface_id_a_distancia( b.interface_id, 3) as ref_original_3,";
  	sql+="       obtener_interface_id_a_distancia( b.interface_id, 4) as ref_original_4,";
  	sql+="       obtener_interface_id_a_distancia( b.interface_id, 5) as ref_original_5,";
  	sql+="       obtener_interface_id_a_distancia( b.interface_id, 6) as ref_original_6,";
  	sql+="       obtener_interface_id_a_distancia( b.interface_id, 7) as ref_original_7,";
  	sql+="       obtener_interface_id_a_distancia( b.interface_id, 8) as ref_original_8,";
  	sql+="       obtener_interface_id_a_distancia( b.interface_id, 9) as ref_original_9,";
  	sql+="       obtener_interface_id_a_distancia( b.interface_id, 10) as ref_original_10";
  	sql+=" from tur_pnr_boleto b ";
  	sql+=" where b.reemitted='N' ";


  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
  		sql+=(" AND ( b.company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and b.customer_id_reducido = '"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
  	} else	if (BizUsuario.getUsr().isAdminUser())
  		sql+=" AND b.company = '"+BizUsuario.getUsr().getCompany()+"'";
  	
		return "("+sql+ ") as "+GetTable(); 
  }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
