package pss.bsp.contrato.quevender.ms;

import java.util.Date;

import pss.bsp.contrato.detalle.nivel.BizNivel;
import pss.bsp.contrato.quevender.BizQueVender;
import pss.bsp.contrato.series.calculado.GuiSerieCalculadas;
import pss.core.graph.Graph;
import pss.core.graph.analize.Dataset;
import pss.core.graph.implementations.GraphScriptSerieTemporal;
import pss.core.graph.model.ModelGrid;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JGeoPosition;
import pss.core.services.fields.JImage;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.winUI.lists.JWinList;
import pss.tourism.pnr.BizBooking;
import pss.tourism.pnr.BizPNRTicket;

public class BizMS extends JRecord {
	
  public JString pAerolinea = new JString();
  public JString pRuta = new JString();
  public JGeoPosition pGeo1 = new JGeoPosition();
  public JGeoPosition pGeo2 = new JGeoPosition();
  public JFloat pCantidad1 = new JFloat() {
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JFloat pCantidad2 = new JFloat() {
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JFloat pCantidad3 = new JFloat() {
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JFloat pCantidad4 = new JFloat() {
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JFloat pCantidad5 = new JFloat() {
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JFloat pPorcentaje = new JFloat();
  public JLong pFactor = new JLong();
  


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Constructor de la Clase
   */
  public BizMS() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "agrupador1", pRuta );
    this.addItem( "agrupador2", pAerolinea);
    this.addItem( "agrupador3", pGeo1 );
    this.addItem( "agrupador4", pGeo2);
    this.addItem( "cantidad1", pCantidad1 );
    this.addItem( "cantidad2", pCantidad2 );
    this.addItem( "cantidad3", pCantidad3 );
    this.addItem( "cantidad4", pCantidad4 );
    this.addItem( "cantidad5", pCantidad5 );
    this.addItem( "porcentaje", pPorcentaje );
    this.addItem( "factor", pFactor );
   }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( FIELD, "agrupador1", "Agrupador 1", true, true, 250 );
    this.addFixedItem( FIELD, "agrupador2", "Agrupador 2", true, true, 250 );
    this.addFixedItem( FIELD, "agrupador3", "Agrupador 3", true, true, 250 );
    this.addFixedItem( FIELD, "agrupador4", "Agrupador 4", true, true, 250 );
    this.addFixedItem( FIELD, "cantidad1", "Cantidad tkt", true, true, 18,2 );
    this.addFixedItem( FIELD, "cantidad2", "Cantidad tkt", true, true, 18,2 );
    this.addFixedItem( FIELD, "cantidad3", "Cantidad tkt", true, true, 18,2 );
    this.addFixedItem( FIELD, "cantidad4", "Cantidad tkt", true, true, 18,2 );
    this.addFixedItem( FIELD, "cantidad5", "Cantidad tkt", true, true, 18,2 );
    this.addFixedItem( FIELD, "porcentaje", "Porcentaje", true, true, 18,2 );
    this.addFixedItem( FIELD, "factor", "factor", true, true, 18 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return ""; }

 

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public JRecords<BizPNRTicket> getDetalleTickets() throws Exception {
  	JRecords<BizPNRTicket> records = new JRecords<BizPNRTicket>(BizPNRTicket.class);
  	records.SetSQL(getObjQueVender().getSqlDetalleCalculed(pRuta.getValue(),pAerolinea.getValue()));
  	return records;
  }
  public JRecords<BizBooking> getDetalleBooking() throws Exception {
  	JRecords<BizBooking> records = new JRecords<BizBooking>(BizBooking.class);
  	records.SetSQL(getObjQueVender().getSqlDetalleCalculed(pRuta.getValue(),pAerolinea.getValue()));
  	return records;
  }
  public BizQueVender getObjQueVender() throws Exception {
  	return (BizQueVender) getParent().getParent();
  }
}
