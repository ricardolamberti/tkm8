package  pss.bsp.pdf.ecu.detalle;

import java.util.Date;

import pss.bsp.consolidador.IConciliable;
import pss.bsp.parseo.IFormato;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.tourism.pnr.BizPNRTicket;

public class BizEcuDetalle extends JRecord implements IConciliable {

	private JString pCompany=new JString();
	private JString pOwner=new JString();
	private JString pIdpdf=new JString();
	private JLong   pLinea=new JLong();
	private JString pTipoRuta=new JString();
	private JString pIdAerolinea=new JString();
	private JString pAerolinea=new JString();
	private JString pOperacion=new JString();
	private JString pAerolineaBoleto=new JString();
	private JString pBoleto=new JString();
	private JString pBoletoLargo=new JString();
	private JDate   pFecha=new JDate();
	private JBoolean pAnulado=new JBoolean();
	private JFloat pTarifa=new JFloat();
	private JFloat pContado=new JFloat();
	private JFloat pTarjeta=new JFloat();
	private JFloat pContadoBruto=new JFloat();
	private JFloat pImpuestoAcum=new JFloat();
	private JFloat pTarjetaBruto=new JFloat();
	private JFloat pBaseImponible=new JFloat();
	private JFloat pImpuesto1=new JFloat();
	private JFloat pImpuesto2=new JFloat();
	private JFloat pComisionPorc=new JFloat();
	private JFloat pComision=new JFloat();
	private JFloat pImpSobrecom=new JFloat();
	private JFloat pComisionAcum=new JFloat() {
		public void preset() throws Exception {
			pComisionAcum.setValue(pComision.getValue()+pComisionOver.getValue());
			}	};
	private JFloat pComisionAcumInv=new JFloat() {
				public void preset() throws Exception {
					pComisionAcumInv.setValue(-(pComision.getValue()+pComisionOver.getValue()));
	  	}	};
	private JFloat pTarifaSinComision=new JFloat() {
		public void preset() throws Exception {
			pTarifaSinComision.setValue(pTarifa.getValue()+(pComision.getValue()+pComisionOver.getValue()));
			}	};
	private JFloat pComisionOver=new JFloat();
	private JFloat pComisionInv=new JFloat() {
		public void preset() throws Exception {
			pComisionInv.setValue(-1*pComision.getValue());
			}	};
	private JFloat pImpSobrecomInv=new JFloat() {
		public void preset() throws Exception {
			pImpSobrecomInv.setValue(-1*pImpSobrecom.getValue());
			}	};
	private JFloat pComisionOverInv=new JFloat() {
		public void preset() throws Exception {
			pComisionOverInv.setValue(-1*pComisionOver.getValue());
		}	};
	private JFloat pNeto=new JFloat()  {
		public void preset() throws Exception {
			pNeto.setValue(JTools.rd(getTarifa()+getImpuestoAcum()-pImpSobrecom.getValue(),2));
			}	};
	private JFloat pTotal=new JFloat();
	private JString pObservaciones=new JString();
	private JString pNumeroTarjeta=new JString();
	private JString pTipoTarjeta=new JString();

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

	public void setTipoRuta(String zValue) throws Exception {
		pTipoRuta.setValue(zValue);
	}

	public String getTipoRuta() throws Exception {
		return pTipoRuta.getValue();
	}

	public boolean isNullTipoRuta() throws Exception {
		return pTipoRuta.isNull();
	}

	public void setNullToTipoRuta() throws Exception {
		pTipoRuta.setNull();
	}

	public void setAerolinea(String zValue) throws Exception {
		pAerolinea.setValue(zValue);
	}

	public String getAerolinea() throws Exception {
		return pAerolinea.getValue();
	}

	public boolean isNullAerolinea() throws Exception {
		return pAerolinea.isNull();
	}

	public void setNullToAerolinea() throws Exception {
		pAerolinea.setNull();
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

	public void setOperacion(String zValue) throws Exception {
		pOperacion.setValue(zValue);
	}

	public String getOperacion() throws Exception {
		return pOperacion.getValue();
	}

	public boolean isNullOperacion() throws Exception {
		return pOperacion.isNull();
	}

	public void setNullToOperacion() throws Exception {
		pOperacion.setNull();
	}
	public void setAerolineaBoleto(String zValue) throws Exception {
		pAerolineaBoleto.setValue(zValue);
	}

	public String getAerolineaBoleto() throws Exception {
		return pAerolineaBoleto.getValue();
	}
	public void setBoletoLargo(String zValue) throws Exception {
		pBoletoLargo.setValue(zValue);
	}

	public String getBoletoLargo() throws Exception {
		return pBoletoLargo.getValue();
	}	
	public void setBoleto(String zValue) throws Exception {
		pBoleto.setValue(zValue);
	}

	public String getBoleto() throws Exception {
		return pBoleto.getValue();
	}

	public boolean isNullBoleto() throws Exception {
		return pBoleto.isNull();
	}

	public void setNullToBoleto() throws Exception {
		pBoleto.setNull();
	}

	public void setFecha(Date zValue) throws Exception {
		pFecha.setValue(zValue);
	}

	public Date getFecha() throws Exception {
		return pFecha.getValue();
	}

	public boolean isNullFecha() throws Exception {
		return pFecha.isNull();
	}

	public void setNullToFecha() throws Exception {
		pFecha.setNull();
	}

	public void setAnulado(boolean zValue) throws Exception {
		pAnulado.setValue(zValue);
	}
	public boolean getAnulado() throws Exception {
		return pAnulado.getValue();
	}
	public boolean hasAnulado() throws Exception {
		return pAnulado.hasValue();
	}
	public void setTarifa(double zValue) throws Exception {
		pTarifa.setValue(zValue);
	}

	public double getTarifa() throws Exception {
		return pTarifa.getValue();
	}

	public boolean isNullTarifa() throws Exception {
		return pTarifa.isNull();
	}

	public void setNullToTarifa() throws Exception {
		pTarifa.setNull();
	}

	public void setContado(double zValue) throws Exception {
		pContado.setValue(zValue);
	}

	public double getContado() throws Exception {
		return pContado.getValue();
	}

	public boolean isNullContado() throws Exception {
		return pContado.isNull();
	}

	public void setNullToContado() throws Exception {
		pContado.setNull();
	}

	public void setTarjeta(double zValue) throws Exception {
		pTarjeta.setValue(zValue);
	}

	public double getTarjeta() throws Exception {
		return pTarjeta.getValue();
	}

	public boolean isNullTarjeta() throws Exception {
		return pTarjeta.isNull();
	}

	public void setNullToTarjeta() throws Exception {
		pTarjeta.setNull();
	}
	public void setImpuestoAcum(double zValue) throws Exception {
		pImpuestoAcum.setValue(zValue);
	}

	public double getImpuestoAcum() throws Exception {
		return pImpuestoAcum.getValue();
	}
	
	public void setContadoBruto(double zValue) throws Exception {
		pContadoBruto.setValue(zValue);
	}

	public double getContadoBruto() throws Exception {
		return pContadoBruto.getValue();
	}

	public boolean isNullContadoBruto() throws Exception {
		return pContadoBruto.isNull();
	}

	public void setNullToContadoBruto() throws Exception {
		pContadoBruto.setNull();
	}

	public void setTarjetaBruto(double zValue) throws Exception {
		pTarjetaBruto.setValue(zValue);
	}

	public double getTarjetaBruto() throws Exception {
		return pTarjetaBruto.getValue();
	}

	public boolean isNullTarjetaBruto() throws Exception {
		return pTarjetaBruto.isNull();
	}

	public void setNullToTarjetaBruto() throws Exception {
		pTarjetaBruto.setNull();
	}

	
	
	public void setBaseImponible(double zValue) throws Exception {
		pBaseImponible.setValue(zValue);
	}

	public double getBaseImponible() throws Exception {
		return pBaseImponible.getValue();
	}

	public boolean isNullBaseImponible() throws Exception {
		return pBaseImponible.isNull();
	}

	public void setNullToBaseImponible() throws Exception {
		pBaseImponible.setNull();
	}

	public void setImpuesto1(double zValue) throws Exception {
		pImpuesto1.setValue(zValue);
	}

	public double getImpuesto1() throws Exception {
		return pImpuesto1.getValue();
	}

	public boolean isNullImpuesto1() throws Exception {
		return pImpuesto1.isNull();
	}

	public void setNullToImpuesto1() throws Exception {
		pImpuesto1.setNull();
	}

	public void setImpuesto2(double zValue) throws Exception {
		pImpuesto2.setValue(zValue);
	}

	public double getImpuesto2() throws Exception {
		return pImpuesto2.getValue();
	}

	public boolean isNullImpuesto2() throws Exception {
		return pImpuesto2.isNull();
	}

	public void setNullToImpuesto2() throws Exception {
		pImpuesto2.setNull();
	}

	public void setComision(double zValue) throws Exception {
		pComision.setValue(zValue);
	}

	public double getComision() throws Exception {
		return pComision.getValue();
	}
	public double getComisionInv() throws Exception {
		return pComisionInv.getValue();
	}

	public boolean isNullComision() throws Exception {
		return pComision.isNull();
	}

	public void setNullToComision() throws Exception {
		pComision.setNull();
	}
	public void setComisionAcum(double zValue) throws Exception {
		pComisionAcum.setValue(zValue);
	}

	public double getComisionAcum() throws Exception {
		return pComisionAcum.getValue();
	}
	public void setComisionAcumInv(double zValue) throws Exception {
		pComisionAcumInv.setValue(zValue);
	}

	public double getComisionAcumInv() throws Exception {
		return pComisionAcumInv.getValue();
	}

	public void setTarifaSinComision(double zValue) throws Exception {
		pTarifaSinComision.setValue(zValue);
	}

	public double getTarifaSinComision() throws Exception {
		return pTarifaSinComision.getValue();
	}
public void setImpSobrecom(double zValue) throws Exception {
		pImpSobrecom.setValue(zValue);
	}

	public double getImpSobrecom() throws Exception {
		return pImpSobrecom.getValue();
	}
	public double getImpSobrecomInv() throws Exception {
		return pImpSobrecomInv.getValue();
	}

	public boolean isNullImpSobrecom() throws Exception {
		return pImpSobrecom.isNull();
	}

	public void setNullToImpSobrecom() throws Exception {
		pImpSobrecom.setNull();
	}

	public void setComisionOver(double zValue) throws Exception {
		pComisionOver.setValue(zValue);
	}

	public double getComisionOver() throws Exception {
		return pComisionOver.getValue();
	}
	public double getComisionOverInv() throws Exception {
		return pComisionOverInv.getValue();
	}

	public boolean isNullComisionOver() throws Exception {
		return pComisionOver.isNull();
	}

	public void setNullToComisionOver() throws Exception {
		pComisionOver.setNull();
	}

	public void setObservaciones(String zValue) throws Exception {
		pObservaciones.setValue(zValue);
	}

	public String getObservaciones() throws Exception {
		return pObservaciones.getValue();
	}

	public boolean isNullObservaciones() throws Exception {
		return pObservaciones.isNull();
	}

	public void setNullToObservaciones() throws Exception {
		pObservaciones.setNull();
	}

	public void setNumeroTarjeta(String zValue) throws Exception {
		pNumeroTarjeta.setValue(zValue);
	}

	public String getNumeroTarjeta() throws Exception {
		return pNumeroTarjeta.getValue();
	}

	public boolean isNullNumeroTarjeta() throws Exception {
		return pNumeroTarjeta.isNull();
	}

	public void setNullToNumeroTarjeta() throws Exception {
		pNumeroTarjeta.setNull();
	}

	public void setTipoTarjeta(String zValue) throws Exception {
		pTipoTarjeta.setValue(zValue);
	}

	public String getTipoTarjeta() throws Exception {
		return pTipoTarjeta.getValue();
	}

	public boolean isNullTipoTarjeta() throws Exception {
		return pTipoTarjeta.isNull();
	}

	public void setNullToTipoTarjeta() throws Exception {
		pTipoTarjeta.setNull();
	}

	public void setTotal(double zValue) throws Exception {
		pTotal.setValue(zValue);
	}

	public double getTotal() throws Exception {
		return pTotal.getValue();
	}
	public double getNeto() throws Exception {
		return pNeto.getValue();
	}

	public void setComisionPorc(double zValue) throws Exception {
		pComisionPorc.setValue(zValue);
	}

	public double getComisionPorc() throws Exception {
		return pComisionPorc.getValue();
	}
	
	public double getTotalComision() throws Exception {
		return this.getImpSobrecom()+this.getComisionOver()+this.getComision();
	}


	public double getIva() throws Exception {
		return this.getImpuesto1()+this.getImpuesto2();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizEcuDetalle() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("owner", pOwner);
		this.addItem("idPDF", pIdpdf);
		this.addItem("linea", pLinea);
		this.addItem("tipo_ruta", pTipoRuta);
		this.addItem("id_aerolinea", pIdAerolinea);
		this.addItem("aerolinea", pAerolinea);
		this.addItem("operacion", pOperacion);
		this.addItem("aerolinea_boleto", pAerolineaBoleto);
		this.addItem("boleto", pBoleto);
		this.addItem("boleto_largo", pBoletoLargo);
		this.addItem("fecha", pFecha);
		this.addItem("anulado", pAnulado);
		this.addItem("tarifa", pTarifa);
		this.addItem("contado", pContado);
		this.addItem("tarjeta", pTarjeta);
		this.addItem("contado_bruto", pContadoBruto);
		this.addItem("impuesto_acum", pImpuestoAcum);
		this.addItem("tarjeta_bruto", pTarjetaBruto);
		this.addItem("base_imponible", pBaseImponible);
		this.addItem("impuesto1", pImpuesto1);
		this.addItem("impuesto2", pImpuesto2);
		this.addItem("comision", pComision);
		this.addItem("imp_sobre_com", pImpSobrecom);
		this.addItem("comision_over", pComisionOver);
		this.addItem("comision_porc", pComisionPorc);
		this.addItem("imp_sobre_com_inv", pImpSobrecomInv);
		this.addItem("comision_over_inv", pComisionOverInv);
		this.addItem("comision_inv", pComisionInv);
		this.addItem("neto", pNeto);
		this.addItem("total", pTotal);
		this.addItem("observaciones", pObservaciones);
		this.addItem("numero_tarjeta", pNumeroTarjeta);
		this.addItem("tipo_tarjeta", pTipoTarjeta);
		this.addItem("comision_acum", pComisionAcum);
		this.addItem("comision_acum_inv", pComisionAcumInv);
		this.addItem("tarifa_sin_comision", pTarifaSinComision);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Company", true, true, 50);
		this.addFixedItem(KEY, "idPDF", "Idpdf", true, true, 300);
		this.addFixedItem(KEY, "linea", "Linea", true, true, 18);
		this.addFixedItem(FIELD, "owner", "Owner", true, true, 50);
		this.addFixedItem(FIELD, "tipo_ruta", "Tipo ruta", true, false, 50);
		this.addFixedItem(FIELD, "id_aerolinea", "ID Aerolinea", true, false, 50);
		this.addFixedItem(FIELD, "aerolinea", "Aerolinea", true, false, 50);
		this.addFixedItem(FIELD, "operacion", "Operacion", true, false, 50);
		this.addFixedItem(FIELD, "aerolinea_boleto", "Aerolinea y Boleto", true, false, 50);
		this.addFixedItem(FIELD, "boleto", "Boleto", true, false, 50);
		this.addFixedItem(FIELD, "fecha", "Fecha", true, false, 16);
		this.addFixedItem(FIELD, "anulado", "Anulado", true, false, 1);
		this.addFixedItem(FIELD, "tarifa", "Tarifa", true, false, 18, 2);
		this.addFixedItem(FIELD, "contado", "Contado Neto", true, false, 18, 2);
		this.addFixedItem(FIELD, "tarjeta", "Tarjeta Neto", true, false, 18, 2);
		this.addFixedItem(FIELD, "contado_bruto", "Contado Bruto", true, false, 18, 2);
		this.addFixedItem(FIELD, "tarjeta_bruto", "Tarjeta Bruto", true, false, 18, 2);
		this.addFixedItem(FIELD, "base_imponible", "Base imponible", true, false, 18, 2);
		this.addFixedItem(FIELD, "impuesto1", "Impuesto1", true, false, 18, 2);
		this.addFixedItem(FIELD, "impuesto2", "Impuesto2", true, false, 18, 2);
		this.addFixedItem(FIELD, "impuesto_acum", "Impuesto Acumulado", true, false, 18, 2);
		this.addFixedItem(FIELD, "comision", "Comision", true, false, 18, 2);
		this.addFixedItem(FIELD, "imp_sobre_com", "Imp sobre com", true, false, 18, 2);
		this.addFixedItem(FIELD, "comision_over", "Comision over", true, false, 18, 2);
		this.addFixedItem(FIELD, "comision_porc", "Comision porc", true, false, 18, 2);
		this.addFixedItem(FIELD, "comision", "Comision", true, false, 18, 2);
		this.addFixedItem(FIELD, "total", "Total a pagar", true, false, 18, 2);
		this.addFixedItem(FIELD, "observaciones", "Observaciones", true, false, 50);
		this.addFixedItem(FIELD, "numero_tarjeta", "Numero tarjeta", true, false, 50);
		this.addFixedItem(FIELD, "tipo_tarjeta", "Tipo tarjeta", true, false, 50);
		this.addFixedItem(FIELD, "boleto_largo", "Boleto", true, false, 50);
		this.addFixedItem(VIRTUAL, "imp_sobre_com_inv", "Imp sobre com Inverso", true, false, 18, 2);
		this.addFixedItem(VIRTUAL, "comision_over_inv", "Comision over Inverso", true, false, 18, 2);
		this.addFixedItem(VIRTUAL, "comision_inv", "Comision Inverso", true, false, 18, 2);
		this.addFixedItem(VIRTUAL, "neto", "Neto", true, false, 18, 2);
		this.addFixedItem(VIRTUAL, "comision_acum", "Comision Mas Over", true, false, 18, 2);
		this.addFixedItem(VIRTUAL, "comision_acum_inv", "Comision Mas Over INV", true, false, 18, 2);
		this.addFixedItem(VIRTUAL, "tarifa_sin_comision", "Tarifa Sin Comision", true, false, 18, 2);
	}

	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.bsp.pdf.arg.detalle.GuiArgDetalles");
  	JRelation rel; 
  	
  	rel = rels.addRelationForce(10, "restriccion usuario");
   	rel.addFilter(" (BSP_PDF_ARG_DETALLE.company is null or BSP_PDF_ARG_DETALLE.company= '"+ BizUsuario.getUsr().getCompany()+"') ");
// 	rel.addJoin("BSP_PDF_ARG_DETALLE.company", BizUsuario.getUsr().getCompany());
//  	rel.addJoin("BSP_PDF_ARG_DETALLE.owner", BizUsuario.getUsr().GetUsuario());
	  
   	rel=rels.addRelationParent(40, "PNRs", BizPNRTicket.class, "boleto");
  	rel.addJoin("BSP_PDF_ARG_DETALLE.company", "TUR_PNR_BOLETO.company");
  	rel.addJoin("BSP_PDF_ARG_DETALLE.boleto", "TUR_PNR_BOLETO.NumeroBoleto");
  	rel.setTypeJoin(JRelations.JOIN_LEFT);

//   	rel=rels.addRelationParent(50, "BOs", BizBODetalle.class, "boleto");
//  	rel.addJoin("BSP_PDF_ARG_DETALLE.company", "BSP_BO_DETALLE.company");
//  	rel.addJoin("BSP_PDF_ARG_DETALLE.boleto", "BSP_BO_DETALLE.boleto");
//  	rel.setTypeJoin(JRelations.JOIN_LEFT);

  	//rels.hideField("company");
  	rels.hideField("owner");
  	rels.addFolderGroup("BSP", null);
		rels.addFolderGroup("Original", null);
		String s = "2";// ,"7_10"};
		rels.addFieldGroup(s , "*", "*", "BSP");

		rels.addFieldGroup(s + "_40", "*", "*", "Original");

		rels.hideField("interface_id");
		//rels.hideField("company");
		rels.hideField("internacional");
		// rels.hideField("codigo_cliente");
		rels.hideField("tipo_pasajero2");
		// rels.hideField("centro_costos");
		rels.hideField("codigo_base_moneda");
		// rels.hideField("additional_fee");
		// rels.hideField("void");
		rels.hideField("expense");
		rels.hideField("porc_expense");
		rels.hideField("importecedido");
		rels.hideField("porcentajeover");
		rels.hideField("oversobre");
		rels.hideField("archivo");
		rels.hideField("origen");
		rels.hideField("it");
		rels.hideField("creation_date_date");
		rels.hideField("CantidadConectados");
		rels.hideField("migrated");
		rels.hideField("numero_tarjeta");
		rels.hideField("transaction_type");
		rels.hideField("ref_original");

		
		// rels.hideField("tarifa_base");
		// rels.hideField("remarks");
		// rels.hideField("observacion");
		rels.hideField("descripcion");
		rels.hideField("order_str");
		// rels.hideField("customer_id");
		rels.hideField("tipo_prestacion");
		rels.hideField("pais");
		rels.hideField("imagen1");
		rels.hideField("is_complete");
		rels.hideField("impuestos");
		rels.hideField("impuesto_factura");
			// rels.hideField("aeropuerto_origen");
		// rels.hideField("aeropuerto_destino");

		rels.onlyLista("NumeroBoleto");
		rels.onlyLista("archivo");
		rels.onlyLista("imagen1");

		rels.addFilter("void", "=", "N");
		rels.addFilter("reemitted", "=", "N");
		rels.addFilter("refund", "=", "N");
		rels.addFilter("is_interlineal", "=", "S");
		rels.addFilter("is_emision", "=", "S");
		rels.addFilter("is_ticket", "=", "S");

		rels.addFolderGroup("Boleto", "Original");
		rels.addFolderGroup("Fechas", "Boleto");
		rels.addFolderGroup("Aerolínea", "Boleto");
		rels.addFolderGroup("Aeropuertos", "Boleto");
		rels.addFolderGroup("A.Origen", "Aeropuertos");
		rels.addFolderGroup("A.Destino", "Aeropuertos");
		rels.addFolderGroup("Intinerarios", "Boleto");
		rels.addFolderGroup("Pasajero", "Boleto");
		rels.addFolderGroup("Tarifas", "Boleto");
		rels.addFolderGroup("Tarifa (Moneda PNR)", "Tarifas");
		rels.addFolderGroup("Tarifa (Moneda USD)", "Tarifas");
		rels.addFolderGroup("Tarifa (Moneda local)", "Tarifas");
		rels.addFolderGroup("Tarifa (Moneda Base)", "Tarifas");
		rels.addFolderGroup("Comisiones", "Boleto");
		rels.addFolderGroup("Tarjeta", "Boleto");
		rels.addFolderGroup("Impuestos", "Boleto");
		rels.addFolderGroup("Segmento", null);
		rels.addFolderGroup("Todos los Segmentos", null);
		rels.addFolderGroup("Origen", "Segmento");
		rels.addFolderGroup("Destino", "Segmento");
		rels.addFolderGroup("Seg.Origen", "Todos los Segmentos");
		rels.addFolderGroup("Seg.Destino", "Todos los Segmentos");
		// rels.addFolderGroup("Seg.precios", null);
		// rels.addFolderGroup("Origen Precio", "Seg.precios");
		// rels.addFolderGroup("Destino Precio", "Seg.precios");
		rels.addFolderGroup("Remarks", null);
		rels.addFolderGroup("Vendedor", "Remarks");
		rels.addFolderGroup("Centro de Costo", "Remarks");
		rels.addFolderGroup("Cliente remark", "Remarks");
		rels.addFolderGroup("Sucursal", "Boleto");
		rels.addFolderGroup("IATA", "Boleto");
		rels.addFolderGroup("Cliente", "Boleto");
		rels.addFolderGroup("Cliente(Reducido)", "Boleto");
		rels.addFolderGroup("Otros Datos", "Boleto");
		rels.addFolderGroup("Agente Emision", "Otros Datos");
		rels.addFolderGroup("Agente Reserva", "Otros Datos");
		s+="_40";
		
		rels.addFieldGroup(s, "highfare", "*", "Remarks");
		rels.addFieldGroup(s, "lowfare", "*", "Remarks");
		rels.addFieldGroup(s, "corporativo", "*", "Remarks");
		rels.addFieldGroup(s, "proposito", "*", "Remarks");
		rels.addFieldGroup(s, "cuenta", "*", "Remarks");
		rels.addFieldGroup(s, "clase_tarifa", "*", "Remarks");
		rels.addFieldGroup(s, "autorizante", "*", "Remarks");
		rels.addFieldGroup(s, "fare_savings", "*", "Remarks");
		rels.addFieldGroup(s, "codigo_negocio", "*", "Remarks");
		rels.addFieldGroup(s, "region", "*", "Remarks");
		rels.addFieldGroup(s, "departamento", "*", "Remarks");
		rels.addFieldGroup(s, "autoriz_cc", "*", "Remarks");
		rels.addFieldGroup(s, "seg_cc", "*", "Remarks");
		rels.addFieldGroup(s, "solicitante", "*", "Remarks");
		rels.addFieldGroup(s, "reserva", "*", "Remarks");
		rels.addFieldGroup(s, "centro_costos", "*", "Centro de Costo");
		rels.addFieldGroup(s, "additional_fee", "*", "Remarks");
		rels.addFieldGroup(s, "costo_fee", "*", "Remarks");
		rels.addFieldGroup(s, "pago_fee", "*", "Remarks");
		rels.addFieldGroup(s, "orden_fee", "*", "Remarks");
		rels.addFieldGroup(s, "consumo", "*", "Remarks");
		rels.addFieldGroup(s, "consumo_num", "*", "Remarks");
		rels.addFieldGroup(s, "cliente", "*", "Remarks");
		rels.addFieldGroup(s, "employee", "*", "Remarks");
		rels.addFieldGroup(s, "email_id", "*", "Remarks");
		rels.addFieldGroup(s, "av_rmk", "*", "Remarks");
		rels.addFieldGroup(s, "bg_rmk", "*", "Remarks");
		rels.addFieldGroup(s, "fa_rmk", "*", "Remarks");
		rels.addFieldGroup(s, "fc_rmk", "*", "Remarks");
		rels.addFieldGroup(s, "rc_rmk", "*", "Remarks");
		rels.addFieldGroup(s, "rch_rmk", "*", "Remarks");
		rels.addFieldGroup(s, "remarks", "*", "Remarks");
		rels.addFieldGroup(s, "tarifa_real", "*", "Remarks");

		rels.addFieldGroup(s, "void", "*", "Boleto");
		rels.addFieldGroup(s, "clase", "*", "Boleto");
		rels.addFieldGroup(s, "tipo_clase", "*", "Boleto");
		rels.addFieldGroup(s, "refund", "*", "Boleto");
		rels.addFieldGroup(s, "reemitted", "*", "Boleto");
		rels.addFieldGroup(s, "is_complete", "*", "Boleto");
		rels.addFieldGroup(s, "is_interlineal", "*", "Boleto");
		rels.addFieldGroup(s, "is_emision", "*", "Boleto");
		rels.addFieldGroup(s, "is_ticket", "*", "Boleto");
		rels.addFieldGroup(s, "refund", "*", "Boleto");
		rels.addFieldGroup(s, "directory", "*", "Boleto");
		rels.addFieldGroup(s, "internacional_descr", "*", "Boleto");
//		rels.addFieldGroup(s, "ref_original", "*", "Boleto");

		rels.addFieldGroup(s, "creation_date", "*", "Fechas");
		rels.addFieldGroup(s, "departure_date", "*", "Fechas");
		rels.addFieldGroup(s, "arrive_date", "*", "Fechas");
		rels.addFieldGroup(s, "fecha_proc", "*", "Fechas");
		rels.addFieldGroup(s, "insert_date", "*", "Fechas");
		rels.addFieldGroup(s, "pnr_date", "*", "Fechas");
		rels.addFieldGroup(s, "fecha_proc", "*", "Fechas");
		rels.addFieldGroup(s, "fecha_proc", "*", "Fechas");
		rels.addFieldGroup(s, "endtravel_date", "*", "Fechas");
		rels.addFieldGroup(s, "void_date", "*", "Fechas");

		rels.addFieldGroup(s, "fecha_intinerary", "*", "Intinerarios");
		rels.addFieldGroup(s, "air_intinerary", "*", "Intinerarios");
		rels.addFieldGroup(s, "air_pais_intinerary", "*", "Intinerarios");
		rels.addFieldGroup(s, "air_gen_intinerary", "*", "Intinerarios");
		rels.addFieldGroup(s, "carrier_intinerary", "*", "Intinerarios");
		rels.addFieldGroup(s, "class_intinerary", "*", "Intinerarios");

		rels.addFieldGroup(s, "pais_origen", "*", "Boleto");
		rels.addFieldGroup(s, "pais_destino", "*", "Boleto");
		rels.addFieldGroup(s, "market", "*", "Boleto");
		rels.addFieldGroup(s, "tipo_operacion", "*", "Boleto");
		rels.addFieldGroup(s, "boleto_original", "*", "Otros Datos");
		rels.addFieldGroup(s, "imagen1", "*", "Otros Datos");
		rels.addFieldGroup(s, "company", "*", "Boleto");

		rels.addFieldGroup(s, "NumeroBoleto", "*", "Boleto");
		rels.addFieldGroup(s, "CodigoPNR", "*", "Boleto");
		rels.addFieldGroup(s, "nro_iata", "*", "IATA");
		rels.addFieldGroup(s, "pre_dias_compra", "*", "Otros Datos");
		rels.addFieldGroup(s, "dias_compra", "*", "Otros Datos");
		rels.addFieldGroup(s, "dias_viajados", "*", "Otros Datos");
		rels.addFieldGroup(s, "route", "*", "Boleto");
		rels.addFieldGroup(s, "mini_route", "*", "Boleto");
		rels.addFieldGroup(s, "vendedor", "*", "Vendedor");
		rels.addFieldGroup(s, "observacion", "*", "Otros Datos");
		rels.addFieldGroup(s, "endoso", "*", "Otros Datos");
		
		rels.addFieldGroup(s, "gds", "*", "Boleto");
		// rels.addFieldGroup(s, "tipo_prestacion", "*", "Boleto");
		rels.addFieldGroup(s, "office_id", "*", "Sucursal");
		rels.addFieldGroup(s, "agente_reserva", "*", "Agente Emision");
		rels.addFieldGroup(s, "agente_emision", "*", "Agente Reserva");
//		rels.addFieldGroup(s, "cliente", "*", "Cliente");
		rels.addFieldGroup(s, "codigo_cliente", "*", "Cliente remark");
		rels.addFieldGroup(s, "city_code", "*", "Otros Datos");
		rels.addFieldGroup(s, "tipo_cambio", "*", "Otros Datos");
		rels.addFieldGroup(s, "customer_id", "*", "Cliente");
		rels.addFieldGroup(s, "customer_id_reducido", "*", "Cliente(Reducido)");
		rels.addFieldGroup(s, "TipoBoleto", "*", "Boleto");
		rels.addFieldGroup(s, "codigo_aerolinea_intern", "*", "Boleto");
		rels.addFieldGroup(s, "tour_code", "*", "Boleto");
		rels.addFieldGroup(s, "net_remit", "*", "Boleto");
		rels.addFieldGroup(s, "cant_segmentos", "*", "Boleto");
		rels.addFieldGroup(s, "cant_roundtrip", "*", "Boleto");

		rels.addFieldGroup(s + "_98", "*", "*", "");
		rels.addFieldGroup(s + "_98", "descripcion", "*", "Cliente");
		rels.addFieldGroup(s + "_98", "mail", "*", "Cliente");
		rels.addFieldGroup(s + "_98", "numero", "*", "Cliente");

		rels.addFieldGroup(s + "_90", "*", "*", "");
		rels.addFieldGroup(s + "_90", "descripcion", "*", "Cliente(Reducido)");
		rels.addFieldGroup(s + "_90", "mail", "*", "Cliente(Reducido)");
		rels.addFieldGroup(s + "_90", "numero", "*", "Cliente(Reducido)");

		rels.addFieldGroup(s + "_91", "*", "*", "");
		rels.addFieldGroup(s + "_91", "descripcion", "*", "Vendedor");
		rels.addFieldGroup(s + "_91", "mail", "*", "Vendedor");
		rels.addFieldGroup(s + "_91", "numero", "*", "Vendedor");

		rels.addFieldGroup(s + "_92", "*", "*", "");
		rels.addFieldGroup(s + "_92", "descripcion", "*", "Sucursal");
		rels.addFieldGroup(s + "_92", "mail", "*", "Sucursal");
		rels.addFieldGroup(s + "_92", "numero", "*", "Sucursal");

		rels.addFieldGroup(s + "_93", "*", "*", "");
		rels.addFieldGroup(s + "_93", "descripcion", "*", "Centro de Costo");
		rels.addFieldGroup(s + "_93", "mail", "*", "Centro de Costo");
		rels.addFieldGroup(s + "_93", "numero", "*", "Centro de Costo");

		rels.addFieldGroup(s + "_94", "*", "*", "");
		rels.addFieldGroup(s + "_94", "descripcion", "*", "IATA");
		rels.addFieldGroup(s + "_94", "mail", "*", "IATA");
		rels.addFieldGroup(s + "_94", "numero", "*", "IATA");

		rels.addFieldGroup(s + "_95", "*", "*", "");
		rels.addFieldGroup(s + "_95", "descripcion", "*", "Cliente remark");
		rels.addFieldGroup(s + "_95", "mail", "*", "Cliente remark");
		rels.addFieldGroup(s + "_95", "numero", "*", "Cliente remark");

		rels.addFieldGroup(s + "_96", "*", "*", "");
		rels.addFieldGroup(s + "_96", "descripcion", "*", "Agente Emision");
		rels.addFieldGroup(s + "_96", "mail", "*", "Agente Emision");
		rels.addFieldGroup(s + "_96", "numero", "*", "Agente Emision");

		rels.addFieldGroup(s + "_97", "*", "*", "");
		rels.addFieldGroup(s + "_97", "descripcion", "*", "Agente Reserva");
		rels.addFieldGroup(s + "_97", "mail", "*", "Agente Reserva");
		rels.addFieldGroup(s + "_97", "numero", "*", "Agente Reserva");

		rels.addFieldGroup(s + "_21", "*", "*", "Aerolínea");
		rels.addFieldGroup(s + "_21", "carrier", "*", "");
		rels.addFieldGroup(s + "_22", "*", "*", "");
		rels.addFieldGroup(s + "_70", "*", "*", "");
		rels.addFieldGroup(s + "_70_70", "*", "*", "");
		rels.addFieldGroup(s, "CodigoAerolinea", "*", "Aerolínea");
		rels.addFieldGroup(s + "_70", "id_group", "*", "Aerolínea");
		rels.addFieldGroup(s + "_70_70", "descripcion", "*", "Aerolínea");

		rels.addFieldGroup(s, "codigo_moneda", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "codigo_base_moneda", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "pivatefareindicator", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa_yq", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa_facturada_yq", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa_usa", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "neto_usa", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "tarifa_facturada_yq_usa", "*", "Tarifa (Moneda USD)");

		rels.addFieldGroup(s, "tarifa_base_contax", "*", "Tarifa (Moneda Base)");
		rels.addFieldGroup(s, "tarifa_base", "*", "Tarifa (Moneda Base)");

		rels.addFieldGroup(s, "codigo_moneda_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "tarifa_factura_total_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "tarifa_factura_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "neto_factura_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "netoyq_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "baseyq_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "tarifa_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "neto_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "tarifa_facturada_yq_local", "*", "Tarifa (Moneda local)");

		rels.addFieldGroup(s, "neto", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "iva", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa_factura", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "neto_factura", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "iva_factura", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa_base_factura", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa_factura_total", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa_factura_total_usa", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "tarifa_factura_usa", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "neto_factura_usa", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "netoyq_usa", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "baseyq_usa", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "ahorro", "*", "Tarifa (Moneda PNR)");

		rels.addFieldGroup(s, "nombre_pasajero", "*", "Pasajero");
		rels.addFieldGroup(s, "ident_fiscal", "*", "Pasajero");
		rels.addFieldGroup(s, "NumeroPasajero", "*", "Pasajero");
		rels.addFieldGroup(s, "tipo_pasajero", "*", "Pasajero");

		rels.addFieldGroup(s, "comision_amount", "*", "Comisiones");
		rels.addFieldGroup(s, "comision_perc", "*", "Comisiones");
		rels.addFieldGroup(s, "importeover", "*", "Comisiones");
		rels.addFieldGroup(s, "comision_factura", "*", "Comisiones");

		rels.addFieldGroup(s + "_30", "*", "*", "A.Origen");
		rels.addFieldGroup(s + "_30", "geo_position", "*", "");
		rels.addFieldGroup(s + "_30_80", "*", "*", "");
		rels.addFieldGroup(s + "_30_60", "*", "*", "");
		rels.addFieldGroup(s + "_30_80_70", "*", "*", "");
		rels.addFieldGroup(s + "_32_80", "*", "*", "");
		rels.addFieldGroup(s + "_32_80_70", "*", "*", "");
		rels.addFieldGroup(s + "_80", "*", "*", "");
		rels.addFieldGroup(s + "_80", "descripcion", "*", "A.Origen");
		rels.addFieldGroup(s + "_80", "continente", "*", "A.Origen");
		rels.addFieldGroup(s + "_80", "region", "*", "A.Origen");
		rels.addFieldGroup(s + "_30_80", "id_group", "*", "A.Origen");
		// rels.addFieldGroup(s + "_30_80_70", "descripcion", "*", "Aeropuerto
		// Origen");
		rels.addFieldGroup(s, "aeropuerto_origen", "*", "A.Origen");

		rels.addFieldGroup(s + "_32", "*", "*", "A.Destino");
		rels.addFieldGroup(s + "_32", "geo_position", "*", "");
		rels.addFieldGroup(s + "_32_60", "*", "*", "");
		rels.addFieldGroup(s + "_32_80", "*", "*", "");
		rels.addFieldGroup(s + "_81", "*", "*", "");
		rels.addFieldGroup(s + "_81", "descripcion", "*", "A.Destino");
		rels.addFieldGroup(s + "_81", "continente", "*", "A.Destino");
		rels.addFieldGroup(s + "_81", "region", "*", "A.Destino");
		rels.addFieldGroup(s + "_32_80", "id_group", "*", "A.Destino");
		// rels.addFieldGroup(s + "_32_80_70", "descripcion", "*", "Aeropuerto
		// Destino");
		rels.addFieldGroup(s, "aeropuerto_destino", "*", "A.Destino");

		rels.addFieldGroup(s, "nombre_tarjeta", "*", "Tarjeta");
		rels.addFieldGroup(s, "monto_tarjeta", "*", "Tarjeta");
		rels.addFieldGroup(s, "numero_tarjeta_mask", "*", "Tarjeta");

		rels.addFieldGroup(s + "_1", "*", "*", "Impuestos");
		rels.addFieldGroup(s, "impuestos_total", "*", "Impuestos");
		rels.addFieldGroup(s, "impuestos_total_factura", "*", "Impuestos");
		rels.addFieldGroup(s, "yq", "*", "Impuestos");
		rels.addFieldGroup(s, "q", "*", "Impuestos");
		rels.addFieldGroup(s, "q_usd", "*", "Impuestos");
		rels.addFieldGroup(s, "q_local", "*", "Impuestos");

		rels.addFieldGroup(s + "_2", "*", "*", "Segmento");
		rels.addFieldGroup(s + "_4", "*", "*", "Todos los Segmentos");

		rels.addFieldGroup(s + "_2", "Despegue", "*", "Origen");
		rels.addFieldGroup(s + "_2", "FechaDespegue", "*", "Origen");
		rels.addFieldGroup(s + "_2", "HoraDespegue", "*", "Origen");
		rels.addFieldGroup(s + "_2_51", "*", "*", "Origen");
		rels.addFieldGroup(s + "_2_51", "geo_position", "*", "");
		rels.addFieldGroup(s + "_2_51_60", "*", "*", "");
		rels.addFieldGroup(s + "_2_51_60", "descripcion", "*", "Origen");
		rels.addFieldGroup(s + "_2_51_60", "continente", "*", "Origen");
		rels.addFieldGroup(s + "_2_51_60", "region", "*", "Origen");

		rels.addFieldGroup(s + "_2_70", "*", "*", "");
		rels.addFieldGroup(s + "_2_21", "*", "*", "");
		rels.addFieldGroup(s + "_2_70_70", "*", "*", "");
		rels.addFieldGroup(s + "_2", "carrier", "*", "Segmento");
		rels.addFieldGroup(s + "_2_70", "id_group", "*", "Segmento");
		rels.addFieldGroup(s + "_2_70_70", "descripcion", "*", "Segmento");

		rels.addFieldGroup(s + "_2_51_80", "*", "*", "");
		rels.addFieldGroup(s + "_2_51_80_70", "*", "*", "");
		rels.addFieldGroup(s + "_2_51_80", "id_group", "*", "Origen");
		rels.addFieldGroup(s + "_2_51_80_70", "descripcion", "*", "Origen");
		rels.addFieldGroup(s + "_2", "Origen", "*", "Origen");
		rels.addFieldGroup(s + "_2", "Origen_pais", "*", "Origen");

		rels.addFieldGroup(s + "_2_52_80", "*", "*", "");
		rels.addFieldGroup(s + "_2_52_80_70", "*", "*", "");
		rels.addFieldGroup(s + "_2_52_80", "id_group", "*", "Destino");
		rels.addFieldGroup(s + "_2_52_80_70", "descripcion", "*", "Destino");
		rels.addFieldGroup(s + "_2", "Destino", "*", "Destino");
		rels.addFieldGroup(s + "_2", "Destino_pais", "*", "Destino");

		rels.addFieldGroup(s + "_2", "Arrivo", "*", "Destino");
		rels.addFieldGroup(s + "_2", "FechaArrivo", "*", "Destino");
		rels.addFieldGroup(s + "_2", "HoraArrivo", "*", "Destino");
		rels.addFieldGroup(s + "_2_52", "*", "*", "Destino");
		rels.addFieldGroup(s + "_2_52", "geo_position", "*", "");
		rels.addFieldGroup(s + "_2_52_60", "*", "*", "");
		rels.addFieldGroup(s + "_2_52_60", "descripcion", "*", "Destino");
		rels.addFieldGroup(s + "_2_52_60", "continente", "*", "Destino");
		rels.addFieldGroup(s + "_2_52_60", "region", "*", "Destino");

		// rels.addFieldGroup(s + "_3", "*", "*", "Seg.precios");
		// rels.addFieldGroup(s + "_3_70", "*", "*", "");
		// rels.addFieldGroup(s + "_3_70_70", "*", "*", "");
		// rels.addFieldGroup(s + "_3_70", "id_group", "*", "Seg.precios");
		// rels.addFieldGroup(s + "_3_70_70", "descripcion", "*", "Seg.precios");

		rels.addFieldGroup(s + "_3_51_60", "*", "*", "");
		rels.addFieldGroup(s + "_3_51_80", "*", "*", "");
		rels.addFieldGroup(s + "_3_51_80_70", "*", "*", "");
		rels.addFieldGroup(s + "_3_52_60", "*", "*", "");
		rels.addFieldGroup(s + "_3_52_80", "*", "*", "");
		rels.addFieldGroup(s + "_3_52_80_70", "*", "*", "");
		rels.addFieldGroup(s + "_3_52", "geo_position", "*", "");

		rels.addFieldGroup(s + "_3", "Despegue", "*", "Origen Precio");
		rels.addFieldGroup(s + "_3", "FechaDespegue", "*", "Origen Precio");
		rels.addFieldGroup(s + "_3", "HoraDespegue", "*", "Origen Precio");
		rels.addFieldGroup(s + "_3_51", "*", "*", "Origen Precio");
		rels.addFieldGroup(s + "_3_51", "geo_position", "*", "");
		rels.addFieldGroup(s + "_3_51_60", "descripcion", "*", "Origen Precio");
		rels.addFieldGroup(s + "_3_51_60", "continente", "*", "Origen Precio");
		rels.addFieldGroup(s + "_3_51_60", "region", "*", "Origen Precio");
		rels.addFieldGroup(s + "_3_51_80", "id_group", "*", "Origen Precio");
		rels.addFieldGroup(s + "_3_51_80_70", "descripcion", "*", "Origen Precio");

		rels.addFieldGroup(s + "_3", "Arrivo", "*", "Destino Precio");
		rels.addFieldGroup(s + "_3", "FechaArrivo", "*", "Destino Precio");
		rels.addFieldGroup(s + "_3", "HoraArrivo", "*", "Destino Precio");
		rels.addFieldGroup(s + "_3", "carrier", "*", "Destino Precio");
		rels.addFieldGroup(s + "_3_52", "*", "*", "Destino Precio");
		rels.addFieldGroup(s + "_3_52_60", "descripcion", "*", "Destino Precio");
		rels.addFieldGroup(s + "_3_52_60", "continente", "*", "Destino Precio");
		rels.addFieldGroup(s + "_3_52_60", "region", "*", "Destino Precio");
		rels.addFieldGroup(s + "_3_52_80", "id_group", "*", "Destino Precio");
		rels.addFieldGroup(s + "_3_52_80_70", "descripcion", "*", "Destino Precio");

		rels.addFieldGroup(s + "_38", "*", "*", "");
		rels.addFieldGroup(s + "_38_49", "*", "*", "");
		rels.addFieldGroup(s + "_38_49", "descripcion", "*", "A.Destino");// region
		rels.addFieldGroup(s + "_2_54_49", "*", "*", "");
		rels.addFieldGroup(s + "_2_54", "*", "*", "");
		rels.addFieldGroup(s + "_2_54_49", "descripcion", "*", "Destino");// region
		rels.addFieldGroup(s + "_3_54_49", "*", "*", "");
		rels.addFieldGroup(s + "_3_54", "*", "*", "");
		rels.addFieldGroup(s + "_3_54_49", "descripcion", "*", "Destino Precio");// region

		rels.addFieldGroup(s + "_4", "Despegue", "*", "Seg.Origen");
		rels.addFieldGroup(s + "_4", "FechaDespegue", "*", "Seg.Origen");
		rels.addFieldGroup(s + "_4", "HoraDespegue", "*", "Seg.Origen");
		rels.addFieldGroup(s + "_4_51", "*", "*", "Seg.Origen");
		rels.addFieldGroup(s + "_4_51", "geo_position", "*", "");
		rels.addFieldGroup(s + "_4_51_60", "*", "*", "");
		rels.addFieldGroup(s + "_4_51_60", "descripcion", "*", "Seg.Origen");
		rels.addFieldGroup(s + "_4_51_60", "continente", "*", "Seg.Origen");
		rels.addFieldGroup(s + "_4_51_60", "region", "*", "Seg.Origen");

		rels.addFieldGroup(s + "_4_70", "*", "*", "");
		rels.addFieldGroup(s + "_4_21", "*", "*", "");
		rels.addFieldGroup(s + "_4_70_70", "*", "*", "");
		rels.addFieldGroup(s + "_4", "carrier", "*", "Todos los Segmentos");
		rels.addFieldGroup(s + "_4_70", "id_group", "*", "Todos los Segmentos");
		rels.addFieldGroup(s + "_4_70_70", "descripcion", "*", "Todos los Segmentos");
		rels.addFieldGroup(s + "_4", "Destino", "*", "Todos los Segmentos");
		rels.addFieldGroup(s + "_4", "Destino_pais", "*", "Todos los Segmentos");
		rels.addFieldGroup(s + "_4", "Origen", "*", "Todos los Segmentos");
		rels.addFieldGroup(s + "_4", "Origen_pais", "*", "Todos los Segmentos");

		rels.addFieldGroup(s + "_4_51_80", "*", "*", "");
		rels.addFieldGroup(s + "_4_51_80_70", "*", "*", "");
		rels.addFieldGroup(s + "_4_51_80", "id_group", "*", "Seg.Origen");
		// rels.addFieldGroup(s + "_4_51_80_70", "descripcion", "*", "Seg.Origen");

		rels.addFieldGroup(s + "_4_52_80", "*", "*", "");
		rels.addFieldGroup(s + "_4_52_80_70", "*", "*", "");
		rels.addFieldGroup(s + "_4_52_80", "id_group", "*", "Seg.Destino");
		// rels.addFieldGroup(s + "_4_52_80_70", "descripcion", "*", "Seg.Destino");

		rels.addFieldGroup(s + "_4", "Arrivo", "*", "Seg.Destino");
		rels.addFieldGroup(s + "_4", "FechaArrivo", "*", "Seg.Destino");
		rels.addFieldGroup(s + "_4", "HoraArrivo", "*", "Seg.Destino");
		rels.addFieldGroup(s + "_4_52", "*", "*", "Seg.Destino");
		rels.addFieldGroup(s + "_4_52", "geo_position", "*", "");
		rels.addFieldGroup(s + "_4_52_60", "*", "*", "");
		rels.addFieldGroup(s + "_4_52_60", "descripcion", "*", "Seg.Destino");
		rels.addFieldGroup(s + "_4_52_60", "continente", "*", "Seg.Destino");
		rels.addFieldGroup(s + "_4_52_60", "region", "*", "Seg.Destino");
		rels.addFieldGroup(s + "_4_54_49", "*", "*", "");
		rels.addFieldGroup(s + "_4_54", "*", "*", "");
  	//rels.hideField("company");
  	rels.hideField("owner");
  }



	
	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "BSP_PDF_ECU_DETALLE";
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

	public Date getDateValue(String field, boolean check) throws Exception {
		if (field.equals(IConciliable.FECHA)) return getFecha();
		return null;
	}
	public Long getLongValue(String field, boolean check) throws Exception {
		if (field.equals(IConciliable.ID_AEROLINEA)) return getIdAerolinea();
		return null;
	}
	public Double getDoubleValue(String field, String moneda, boolean check) throws Exception {
		if (field.equals(IConciliable.COMISION)) return new Double(getComision());
		if (field.equals(IConciliable.COMISION_OVER)) return new Double(getComisionOver());
		if (field.equals(IConciliable.COMISION_PORC)) return new Double(getComisionPorc());
		if (field.equals(IConciliable.COMISION_INV)) return new Double(getComisionInv());;
		if (field.equals(IConciliable.COMISION_OVER_INV)) return new Double(getComisionOverInv());;
		if (field.equals(IConciliable.IMP_SOBRE_COMISION_INV)) return null;
		if (field.equals(IConciliable.TOTALAPAGAR)) return new Double(getTotal());
		if (field.equals(IConciliable.TOTAL)) return new Double(JTools.rd(getTarifa()+getImpuestoAcum(),2));
		if (field.equals(IConciliable.BASE_IMPONIBLE)) return new Double(getBaseImponible());
		if (field.equals(IConciliable.CONTADO)) return null;
		if (field.equals(IConciliable.IMP_SOBRE_COMISION)) return new Double(getImpSobrecom());
		if (field.equals(IConciliable.IMPUESTO_1)) return new Double(getImpuesto1());
		if (field.equals(IConciliable.IMPUESTO_2)) return new Double(getImpuesto2());
		if (field.equals(IConciliable.IMPUESTO_ACUM)) return new Double(getImpuestoAcum());
		if (field.equals(IConciliable.TARIFA)) return new Double(getTarifa());
		if (field.equals(IConciliable.TARJETA)) return null;
		if (field.equals(IConciliable.TARJETA_BRUTO)) return null;
		if (field.equals(IConciliable.CONTADO_BRUTO)) return null;
		if (field.equals(IConciliable.NETO)) return new Double(getNeto());
		if (field.equals(IConciliable.COMISION_ACUM)) return new Double(getComisionAcum());
		if (field.equals(IConciliable.COMISION_ACUM_INV)) return new Double(getComisionAcumInv());
		if (field.equals(IConciliable.TARIFA_SIN_COMISION)) return new Double(getTarifaSinComision());
		return null;
	}

	public String getStringValue(String field, boolean check) throws Exception {
		if (field.equals(IConciliable.BOLETOS)) return getBoleto();
		if (field.equals(IConciliable.AEROLINEA_BOLETOS)) return getAerolineaBoleto();
		if (field.equals(IConciliable.ID_AEROLINEA)) return ""+getIdAerolinea();
		if (field.equals(IConciliable.BOLETOS_LARGO)) return getBoletoLargo();
		if (field.equals(IConciliable.AEROLINEA)) return getAerolinea();
		if (field.equals(IConciliable.NUMERO_TARJETA)) return getNumeroTarjeta();
		if (field.equals(IConciliable.OBSERVACION)) return getObservaciones();
		if (field.equals(IConciliable.OPERACION)) return getOperacion();
		if (field.equals(IConciliable.TIPO_RUTA)) return getTipoRuta();
		if (field.equals(IConciliable.TIPO_TARJETA)) return getTipoTarjeta();
		if (field.equals(IConciliable.FECHA)) return JDateTools.DateToString(getFecha());
		if (field.equals(IConciliable.COMISION)) return new Double(getComision()).toString();
		if (field.equals(IConciliable.COMISION_OVER)) return new Double(getComisionOver()).toString();
		if (field.equals(IConciliable.COMISION_PORC)) return new Double(getComisionPorc()).toString();
		if (field.equals(IConciliable.TOTALAPAGAR)) return new Double(getTotal()).toString();
		if (field.equals(IConciliable.BASE_IMPONIBLE)) return new Double(getBaseImponible()).toString();
		if (field.equals(IConciliable.CONTADO)) return new Double(getContado()).toString();
		if (field.equals(IConciliable.IMP_SOBRE_COMISION)) return new Double(getImpSobrecom()).toString();
		if (field.equals(IConciliable.IMPUESTO_1)) return new Double(getImpuesto1()).toString();
		if (field.equals(IConciliable.IMPUESTO_2)) return new Double(getImpuesto2()).toString();
		if (field.equals(IConciliable.IMPUESTO_ACUM)) return new Double(getImpuestoAcum()).toString();
		if (field.equals(IConciliable.TARIFA)) return new Double(getTarifa()).toString();
		if (field.equals(IConciliable.TARJETA)) return new Double(getTarjeta()).toString();
		if (field.equals(IConciliable.COMISION_INV)) return new Double(getComisionInv()).toString();
		if (field.equals(IConciliable.COMISION_OVER_INV)) return new Double(getComisionOverInv()).toString();
		if (field.equals(IConciliable.IMP_SOBRE_COMISION_INV)) return new Double(getImpSobrecomInv()).toString();
		if (field.equals(IConciliable.TARJETA_BRUTO)) return new Double(getTarjetaBruto()).toString();
		if (field.equals(IConciliable.CONTADO_BRUTO)) return new Double(getContadoBruto()).toString();
		if (field.equals(IConciliable.NETO)) return new Double(getNeto()).toString();
		if (field.equals(IConciliable.COMISION_ACUM)) return new Double(getComisionAcum()).toString();
		if (field.equals(IConciliable.COMISION_ACUM_INV)) return new Double(getComisionAcumInv()).toString();
		if (field.equals(IConciliable.TARIFA_SIN_COMISION)) return new Double(getTarifaSinComision()).toString();
	return null;
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

	@Override
	public void setFormato(IFormato formato) {

	}
	@Override
	public String getSituation(IConciliable other, double precision) throws Exception {
			return "";
	}

	@Override
	public String getContrato() throws Exception {
		return "";
	}
}
