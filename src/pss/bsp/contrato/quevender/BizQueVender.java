package pss.bsp.contrato.quevender;

import java.util.Calendar;
import java.util.Date;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.common.security.BizUsuario;
import pss.core.graph.Graph;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObjBD;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;
import pss.tourism.pnr.BizBooking;
import pss.tourism.pnr.BizPNRTicket;

public class BizQueVender extends JRecord {

	
  public JString pCompany = new JString();
  public JLong pIdContrato = new JLong();
  public JLong pIdDetalle = new JLong();
  public JLong pId = new JLong();
  public JLong pGrafico = new JLong();
  public JString pSql = new JString();
  public JString pSqlDetalle = new JString();
  public JString pClase = new JString();
  public JString pDescripcion = new JString();
  public JString pFieldname1 = new JString();
  public JString pFieldname2 = new JString();
  public JString pFieldname3 = new JString();
  public JString pFieldname4 = new JString();
  public JString pFieldname5 = new JString();
  public JString pFieldnameAgrupador1 = new JString();
  public JString pFieldnameAgrupador2 = new JString();

  public JObjBD pDetalle = new JObjBD() {
  	public void preset() throws Exception {
  		pDetalle.setValue(getObjDetalle());
  	};
  };
  
  public JString pImagen1 = new JString() {
  	public void preset() throws Exception {
  		pImagen1.setValue(getImagen1(false));
  	};
  	
  };
  
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public String getCompany() throws Exception {
  	return pCompany.getValue();
  }
  public long getIdContrato() throws Exception {
  	return pIdContrato.getValue();
  }
  public long getIdDetalle() throws Exception {
  	return pId.getValue();
  }
  public String getSql() throws Exception {
  	return pSql.getValue();
  }
  public String getSqlDetalle() throws Exception {
  	return pSqlDetalle.getValue();
  }
  public long getGrafico() throws Exception {
  	return pGrafico.getValue();
  }
  public String getDescripcion() throws Exception {
  	return pDescripcion.getValue();
  }
  public String getFieldname1() throws Exception {
  	return pFieldname1.getValue();
  }
  public String getFieldname2() throws Exception {
  	return pFieldname2.getValue();
  }
  public String getFieldname3() throws Exception {
  	return pFieldname3.getValue();
  }
  public String getFieldname4() throws Exception {
  	return pFieldname4.getValue();
  }
  public String getFieldname5() throws Exception {
  	return pFieldname5.getValue();
  }
  public String getFieldnameAgrupador1() throws Exception {
  	return pFieldnameAgrupador1.getValue();
  }  
  public String getFieldnameAgrupador2() throws Exception {
  	return pFieldnameAgrupador2.getValue();
  }
  public String getSqlCalculed() throws Exception {
  	String sql = this.getSql();
		Calendar fecha = Calendar.getInstance();
		fecha.setTime(getObjDetalle().getFHasta());
		Calendar fechaDesde = Calendar.getInstance();
		fechaDesde.setTime(getObjDetalle().getFDesde());

		if (sql.toUpperCase().contains("::FECHA::")) {
			String fechaFiltro = "";
			if (sql.indexOf("ES_BOOKING") != -1) {
				if (sql.indexOf("ES_VOLADO") != -1) {

					if (fecha != null) {
						fechaFiltro += " AND fecha_emision <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
					}
				} else {
					if (fechaDesde != null) {
						fechaFiltro += " AND fecha_emision >= '" + JDateTools.DateToString(fechaDesde.getTime()) + "' ";
					}
					if (fecha != null) {
						fechaFiltro += " AND fecha_emision <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
					}

				}
			} else {
				if (sql.indexOf("ES_VOLADO") != -1) {

					if (fecha != null) {
						fechaFiltro += " AND creation_date <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
					}
				} else {
					if (fechaDesde != null) {
						fechaFiltro += " AND creation_date >= '" + JDateTools.DateToString(fechaDesde.getTime()) + "' ";
					}
					if (fecha != null) {
						fechaFiltro += " AND creation_date <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
					}
				}
			}
			sql =  JTools.replace(sql, "::FECHA::", fechaFiltro);
		} else
			sql = JTools.replace(sql, "::FECHA::", "");
		
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) 
 			sql = JTools.replace(sql, "::DK::", " AND (customer_id_reducido='"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
		else
			sql = JTools.replace(sql, "::DK::", "");
				
		if (fecha!=null) {
			sql=JTools.replace(sql, "now()", "'"+JDateTools.DateToString(fecha.getTime())+"'::date ");
			Calendar ayer = Calendar.getInstance();
			ayer.setTime(fecha.getTime());
			ayer.add(Calendar.DAY_OF_MONTH, -1);
			sql=JTools.replace(sql, "'yesterday'",  "'"+JDateTools.DateToString(ayer.getTime())+"'::date ");
			Calendar maniana = Calendar.getInstance();
			maniana.setTime(fecha.getTime());
			maniana.add(Calendar.DAY_OF_MONTH, 1);
			sql=JTools.replace(sql, "'tomorrow'", "'"+JDateTools.DateToString(maniana.getTime())+"'::date ");
		
		}
	 	return sql;
	}
  
  public String getSqlDetalleCalculed(String agrupado1,String agrupado2) throws Exception {
  	String sql = this.getSqlDetalle();
		Calendar fecha = Calendar.getInstance();
		fecha.setTime(getObjDetalle().getFHasta());
		Calendar fechaDesde = Calendar.getInstance();
		fechaDesde.setTime(getObjDetalle().getFDesde());

		if (sql.toUpperCase().contains("::FECHA::")) {
			String fechaFiltro = "";
			if (sql.indexOf("ES_BOOKING") != -1) {
				if (sql.indexOf("ES_VOLADO") != -1) {

					if (fecha != null) {
						fechaFiltro += " AND fecha_emision <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
					}
				} else {
					if (fechaDesde != null) {
						fechaFiltro += " AND fecha_emision >= '" + JDateTools.DateToString(fechaDesde.getTime()) + "' ";
					}
					if (fecha != null) {
						fechaFiltro += " AND fecha_emision <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
					}

				}
			} else {
				if (sql.indexOf("ES_VOLADO") != -1) {

					if (fecha != null) {
						fechaFiltro += " AND creation_date <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
					}
				} else {
					if (fechaDesde != null) {
						fechaFiltro += " AND creation_date >= '" + JDateTools.DateToString(fechaDesde.getTime()) + "' ";
					}
					if (fecha != null) {
						fechaFiltro += " AND creation_date <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
					}
				}
			}
			sql =  JTools.replace(sql, "::FECHA::", fechaFiltro);

		} else
			sql = JTools.replace(sql, "::FECHA::", "");
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) 
 			sql = JTools.replace(sql, "::DK::", " AND (customer_id_reducido='"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
		else
			sql = JTools.replace(sql, "::DK::", "");
			
		if (fecha!=null) {
				
			sql=JTools.replace(sql, "now()", "'"+JDateTools.DateToString(fecha.getTime())+"'::date ");
			Calendar ayer = Calendar.getInstance();
			ayer.setTime(fecha.getTime());
			ayer.add(Calendar.DAY_OF_MONTH, -1);
			sql=JTools.replace(sql, "'yesterday'",  "'"+JDateTools.DateToString(ayer.getTime())+"'::date ");
			Calendar maniana = Calendar.getInstance();
			maniana.setTime(fecha.getTime());
			maniana.add(Calendar.DAY_OF_MONTH, 1);
			sql=JTools.replace(sql, "'tomorrow'", "'"+JDateTools.DateToString(maniana.getTime())+"'::date ");
				
		}
		sql=JTools.replace(sql, "::agrupado1::", agrupado1);
		sql=JTools.replace(sql, "::agrupado2::", agrupado2);
	 	return sql;
	}
  
  public void setSql(String zValue) throws Exception {
  	pSql.setValue(zValue);
  }
  public void setSqlDetalle(String zValue) throws Exception {
  	pSqlDetalle.setValue(zValue);
  }
  public void setClase(String zValue) throws Exception {
  	pClase.setValue(zValue);
  }
  public void setGrafico(long zValue) throws Exception {
  	pGrafico.setValue(zValue);
  }  
  public void setId(long zValue) throws Exception {
  	pId.setValue(zValue);
  }
  public void setDescripcion(String zValue) throws Exception {
  	pDescripcion.setValue(zValue);
  }  
  public void setFieldname1(String zValue) throws Exception {
  	pFieldname1.setValue(zValue);
  } 
  public void setFieldname2(String zValue) throws Exception {
  	pFieldname2.setValue(zValue);
  } 
  public void setFieldname3(String zValue) throws Exception {
  	pFieldname3.setValue(zValue);
  } 
  public void setFieldname4(String zValue) throws Exception {
  	pFieldname4.setValue(zValue);
  } 
  public void setFieldname5(String zValue) throws Exception {
  	pFieldname5.setValue(zValue);
  } 
  
  public void setFieldnameAgrupador1(String zValue) throws Exception {
  	pFieldnameAgrupador1.setValue(zValue);
  }  
  public void setFieldnameAgrupador2(String zValue) throws Exception {
  	pFieldnameAgrupador2.setValue(zValue);
  }  
  /**
   * Constructor de la Clase
   */
  public BizQueVender() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id_contrato", pIdContrato );
    this.addItem( "id_detalle", pIdDetalle );
    this.addItem( "id", pId );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "sql", pSql );
    this.addItem( "sql_detalle", pSqlDetalle );
    this.addItem( "clase", pClase );
    this.addItem( "grafico", pGrafico );
    this.addItem( "fieldname1", pFieldname1 );
    this.addItem( "fieldname2", pFieldname2 );
    this.addItem( "fieldname3", pFieldname3 );
    this.addItem( "fieldname4", pFieldname4 );
    this.addItem( "fieldname5", pFieldname5 );
    this.addItem( "fieldname_agrupador1", pFieldnameAgrupador1 );
    this.addItem( "fieldname_agrupador2", pFieldnameAgrupador2 );
    this.addItem( "imagen1", pImagen1 );
    this.addItem( "obj_detalle", pDetalle);
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "id_contrato", "Id contrato", true, false, 32 );
    this.addFixedItem( KEY, "id_detalle", "Id_detalle", true, false, 32 );
    this.addFixedItem( KEY, "id", "Id", true, false, 32 );
    this.addFixedItem( FIELD, "sql", "sql", true, false, 4000 );
    this.addFixedItem( FIELD, "sql_detalle", "sql detalle", true, false, 4000 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, false, 4000 );
    this.addFixedItem( FIELD, "clase", "clase", true, false, 1000 );
    this.addFixedItem( FIELD, "grafico", "grafico", true, false, 18 );
    this.addFixedItem( FIELD, "fieldname1", "fieldname1", true, false, 200 );
    this.addFixedItem( FIELD, "fieldname2", "fieldname2", true, false, 200 );
    this.addFixedItem( FIELD, "fieldname3", "fieldname3", true, false, 200 );
    this.addFixedItem( FIELD, "fieldname4", "fieldname4", true, false, 200 );
    this.addFixedItem( FIELD, "fieldname5", "fieldname5", true, false, 200 );
    this.addFixedItem( FIELD, "fieldname_agrupador1", "fieldname ag1", true, false, 200 );
    this.addFixedItem( FIELD, "fieldname_agrupador2", "fieldname a2", true, false, 200 );
    this.addFixedItem( VIRTUAL, "imagen1", "Imagen", true, true, 250 );
    this.addFixedItem( RECORD, "obj_detalle", "Detalle", true, true, 250 ).setClase(BizDetalle.class);
   }
  /**
   * Returns the table name
   */
  public String GetTable() { return "bsp_extrainfo"; }

	public String getImagen1(boolean printerVersion) throws Exception {
		JWins w = getGuis();
		String sql=getSqlCalculed();
		w.getRecords().SetSQL(sql);
		w.getRecords().setParent(this);
		JWinList l = new JWinList(w);
		w.ConfigurarColumnasLista(l);
		Graph g=l.getGrafico((int)pGrafico.getValue());
		if (g!=null) {
			g.localFill(l,null,null);
			return g.getImage(615, 552);
		}
		return "";
	}
	
	BizDetalle objDetalle;

	public BizDetalle getObjDetalle() throws Exception {
		if (objDetalle!=null) return objDetalle;
		BizDetalle detalle = new BizDetalle();
		detalle.dontThrowException(true);
		if (!detalle.read(pIdDetalle.getValue())) return null;
		BizDetalle detAgr=detalle.getObjLogicaInstance().getBiz();
		detAgr.copyProperties(detalle);
		return objDetalle=detAgr;
	}
	public void setObjDetalle(BizDetalle detalle) throws Exception {
		this.objDetalle = detalle;
		pCompany.setValue(detalle.getCompany());
		pIdContrato.setValue(detalle.getId());
		pIdDetalle.setValue(detalle.getLinea());
		
	}
	
	JWins objWins=null;
	public JWins getGuis() throws Exception {
		if (objWins!=null) return objWins;
		JWins wins=(JWins)Class.forName(pClase.getValue()).newInstance();
		return objWins=wins;
	}
	public boolean isTicket() throws Exception {
		return pClase.getValue().equals(BizPNRTicket.class.getName());
	}
	public boolean isBooking() throws Exception {
		return pClase.getValue().equals(BizBooking.class.getName());
	}
  public String getSqlWithDate() throws Exception {
		Calendar hoy = Calendar.getInstance();
		hoy.setTime(new Date());
		Calendar hasta = Calendar.getInstance();
		hasta.setTime(getObjDetalle().hasFechaCalculo()?getObjDetalle().getFechaCalculo():getObjDetalle().getObjContrato().getFechaHasta());
		if (hoy.after(hasta))
			hoy = hasta;
	 	String sql = this.getSql();
		Calendar fechaDesde = Calendar.getInstance();
		fechaDesde.setTime(getObjDetalle().getFDesde());

		if (sql.toUpperCase().contains("::FECHA::")) {
			String fechaFiltro = "";
			if (sql.indexOf("ES_BOOKING") != -1) {
				if (sql.indexOf("ES_VOLADO") != -1) {

					if (hoy != null) {
						fechaFiltro += " AND fecha_emision <= '" + JDateTools.DateToString(hoy.getTime()) + "' ";
					}
				} else {
					if (fechaDesde != null) {
						fechaFiltro += " AND fecha_emision >= '" + JDateTools.DateToString(fechaDesde.getTime()) + "' ";
					}
					if (hoy != null) {
						fechaFiltro += " AND fecha_emision <= '" + JDateTools.DateToString(hoy.getTime()) + "' ";
					}

				}
			} else {
				if (sql.indexOf("ES_VOLADO") != -1) {

					if (hoy != null) {
						fechaFiltro += " AND creation_date <= '" + JDateTools.DateToString(hoy.getTime()) + "' ";
					}
				} else {
					if (fechaDesde != null) {
						fechaFiltro += " AND creation_date >= '" + JDateTools.DateToString(fechaDesde.getTime()) + "' ";
					}
					if (hoy != null) {
						fechaFiltro += " AND creation_date <= '" + JDateTools.DateToString(hoy.getTime()) + "' ";
					}
				}
			}
			sql =  JTools.replace(sql, "::FECHA::", fechaFiltro);
		} else
			sql = JTools.replace(sql, "::FECHA::", "");
		
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) 
 			sql = JTools.replace(sql, "::DK::", " AND (customer_id_reducido='"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
		else
			sql = JTools.replace(sql, "::DK::", "");
		if (hoy!=null) {
			sql=JTools.replace(sql, "now()", "'"+JDateTools.DateToString(hoy.getTime())+"'::date ");
			Calendar ayer = Calendar.getInstance();
			ayer.setTime(hoy.getTime());
			ayer.add(Calendar.DAY_OF_MONTH, -1);
			sql=JTools.replace(sql, "'yesterday'",  "'"+JDateTools.DateToString(ayer.getTime())+"'::date ");
			Calendar maniana = Calendar.getInstance();
			maniana.setTime(hoy.getTime());
			maniana.add(Calendar.DAY_OF_MONTH, 1);
			sql=JTools.replace(sql, "'tomorrow'", "'"+JDateTools.DateToString(maniana.getTime())+"'::date ");
		
		}

		return sql;
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public boolean read(String company, long idcontrato,long iddetalle,long reporte) throws Exception {
	addFilter("company", company);
	addFilter("id_contrato", idcontrato);
	addFilter("id_detalle", iddetalle);
	addFilter("id", reporte);


	return super.read();
}

}
