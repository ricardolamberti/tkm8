package pss.bsp.contrato.total;

import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizTotal extends JRecord {
	
  public JLong pId = new JLong();
  public JString pMoneda = new JString();
  public JString pGrupo = new JString();
  public JFloat pBaseComisionable = new JFloat() {
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JFloat pGanancia = new JFloat() {
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Constructor de la Clase
   */
  public BizTotal() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "moneda", pMoneda);
    this.addItem( "grupo", pGrupo );
    this.addItem( "base_comisionable", pBaseComisionable );
    this.addItem( "ganancia", pGanancia );
     }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( FIELD, "id", "Id", true, true, 250 );
    this.addFixedItem( FIELD, "moneda", "Moneda", true, true, 250 );
    this.addFixedItem( FIELD, "grupo", "Grupo", true, true, 250 );
    this.addFixedItem( FIELD, "base_comisionable", "Base Comisionable", true, true, 18,2 );
    this.addFixedItem( FIELD, "ganancia", "Ganancia", true, true, 18,2 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "total_contrato"; }

  public String GetTableTemporal() { 
  	String signo = "local";
  	try {
			signo =BizCompany.getObjCompany(BizUsuario.getUsr().getCompany()).getCurrency();
		} catch (Exception e) {

		}
  
  	String sql= "SELECT ";
  	sql += "    id,";
  	sql += "    CASE ";
  	sql += "        WHEN dolares = 'N' THEN '"+signo+"'";
  	sql += "        WHEN dolares = 'S' THEN 'USD'";
  	sql += "        ELSE dolares";
  	sql += "    END AS moneda,";
  	sql += "    grupo,";
  	sql += "    SUM(CASE WHEN ganancia_estimada <> 0 THEN valor_totalcontrato ELSE 0 END) AS base_comisionable, ";
  	sql += "    SUM(ganancia_estimada) AS ganancia ";
  	sql += " FROM bsp_contrato_detalle";
  	sql += " GROUP BY ";
  	sql += "    id, ";
  	sql += "    grupo,";
  	sql += "    dolares";

  	 
  	 return "("+sql+ ") as "+GetTable();
  }
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

