package pss.bsp.contrato.consolidado;

import java.util.Date;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.contrato.BizContrato;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.nivel.BizNivel;
import pss.bsp.contrato.logica.ILogicaContrato;
import pss.common.event.sql.BizSqlEvent;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;

public class BizContratoConsolidado extends JRecord {


  private JLong pLinea = new JLong();
  private JLong pValor = new JLong();
  private JString pLogica = new JString();
  private JString pDescripcion = new JString();
  private JLong pVariable = new JLong();
  private JLong pVariableGanancia = new JLong();
  private JString pCompany = new JString();
  private JBoolean pAcumulativo = new JBoolean();
  private JString pPeriodo = new JString();
  private JString pActivo = new JString();
  private JLong pId = new JLong();
  private JLong pModelo = new JLong();
  private JDate pFDesde = new JDate();
  private JInteger pAnio = new JInteger();
  private JInteger pMes = new JInteger();
  private JString pMesLiteral = new JString() {
  	public void preset() throws Exception {
  		if (pMes.getValue()==1)       pMesLiteral.setValue(JTools.LPad(""+pMes.getValue(), 2, "0")+" - Enero     ");
  		else if (pMes.getValue()==2)  pMesLiteral.setValue(JTools.LPad(""+pMes.getValue(), 2, "0")+" - Febrero   ");
  		else if (pMes.getValue()==3)  pMesLiteral.setValue(JTools.LPad(""+pMes.getValue(), 2, "0")+" - Marzo     ");
  		else if (pMes.getValue()==4)  pMesLiteral.setValue(JTools.LPad(""+pMes.getValue(), 2, "0")+" - Abril     ");
  		else if (pMes.getValue()==5)  pMesLiteral.setValue(JTools.LPad(""+pMes.getValue(), 2, "0")+" - Mayo      ");
  		else if (pMes.getValue()==6)  pMesLiteral.setValue(JTools.LPad(""+pMes.getValue(), 2, "0")+" - Junio     ");
  		else if (pMes.getValue()==7)  pMesLiteral.setValue(JTools.LPad(""+pMes.getValue(), 2, "0")+" - Julio     ");
  		else if (pMes.getValue()==8)  pMesLiteral.setValue(JTools.LPad(""+pMes.getValue(), 2, "0")+" - Agosto    ");
  		else if (pMes.getValue()==9)  pMesLiteral.setValue(JTools.LPad(""+pMes.getValue(), 2, "0")+" - Septiembre");
  		else if (pMes.getValue()==10) pMesLiteral.setValue(JTools.LPad(""+pMes.getValue(), 2, "0")+" - Octubre   ");
  		else if (pMes.getValue()==11) pMesLiteral.setValue(JTools.LPad(""+pMes.getValue(), 2, "0")+" - Noviembre ");
  		else if (pMes.getValue()==12) pMesLiteral.setValue(JTools.LPad(""+pMes.getValue(), 2, "0")+" - Diciembre ");
  	};
  };
  private JDate pFHasta  = new JDate();
	private JString pHtmlData = new JString() {
	  	public void preset() throws Exception {pHtmlData.setValue(getHtmlData());}
	  	public double getFloatValue() throws Exception {
	  		if (getObjContrato().getFechaHasta().getMonth()+1!=pMes.getValue()) return 0;
	  		return getGanancia();
	  	};
	};
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setLinea(long zValue) throws Exception {    pLinea.setValue(zValue);  }
  public long getLinea() throws Exception {     return pLinea.getValue();  }
  public boolean isNullLinea() throws Exception { return  pLinea.isNull(); } 
  public void setNullToLinea() throws Exception {  pLinea.setNull(); } 
  public void setValor(long zValue) throws Exception {    pValor.setValue(zValue);  }
  public long getValor() throws Exception {   return pValor.getValue();  }
  public Date getFDesde() throws Exception { return  pFDesde.getValue(); } 
  public void setFDesde(Date value) throws Exception {  pFDesde.setValue(value); } 
  public Date getFHasta() throws Exception { return  pFHasta.getValue(); } 
  public void setFHasta(Date value) throws Exception {  pFHasta.setValue(value); } 
  public boolean isNullValor() throws Exception { return  pValor.isNull(); } 
  public void setNullToValor() throws Exception {  pValor.setNull(); } 
  public void setVariable(long zValue) throws Exception {    pVariable.setValue(zValue);  }
  public long getVariable() throws Exception {     return pVariable.getValue();  }
  public boolean isNullVariable() throws Exception { return  pVariable.isNull(); } 
  public void setNullToVariable() throws Exception {  pVariable.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 
  public void setModelo(long zValue) throws Exception {    pModelo.setValue(zValue);  }
  public long getModelo() throws Exception {     return pModelo.getValue();  }
  public void setVariableGanancia(long zValue) throws Exception {    pVariableGanancia.setValue(zValue);  }
  public long getVariableGanancia() throws Exception {  return pVariableGanancia.getValue();  }
  public boolean isNullModelo() throws Exception { return  pModelo.isNull(); } 
  public void setNullToModelo() throws Exception {  pModelo.setNull(); } 
  public void setAcumulativo(boolean zValue) throws Exception {    pAcumulativo.setValue(zValue);  }
  public boolean getAcumulativo() throws Exception {     return pAcumulativo.getValue();  }
  public void setPeriodo(String zValue) throws Exception {    pPeriodo.setValue(zValue);  }
  public String getPeriodo() throws Exception {     return pPeriodo.getValue();  }
  public long getAnio() throws Exception {     return pAnio.getValue();  }
  public long getMes() throws Exception {     return pMes.getValue();  }
  public String getMesLiteral() throws Exception {     return pMesLiteral.getValue();  }
  public String getDescrContrato() throws Exception {     return pDescripcion.getValue();  }
    
 private JString pDescrVariable = new JString() {
  	public void preset() throws Exception {pDescrVariable.setValue(getDescrVariable());}
  };
  private JString pDescrContrato = new JString();
  public double getSigValorObjetivo() throws Exception {    
  	if (getObjDetalle()==null) return 0;
  	return getObjDetalle().getSigValorObjetivo();  
  }
  public String getNivelAlcanzado() throws Exception {    
  	if (getObjDetalle()==null) return "";
  	return getObjDetalle().getNivelAlcanzado();  
  }
  public String getStringCumplido() throws Exception {    
  		return JTools.rd(getCumplido(),0)+"%";  
  }
  public double getCumplido() throws Exception {    
  	if (getObjDetalle()==null) return 0;
  	return getObjDetalle().getNivelCumplimiento();  
  }
  public double getValorActual() throws Exception {    
  	if (getObjDetalle()==null) return 0;
  	return getObjDetalle().getValorActual();  
  }
  public double getGanancia() throws Exception {    
  	if (getObjDetalle()==null) return 0;
  	return getObjDetalle().getGanancia();  
  }
  public String getDescrVariable() throws Exception {
  	if (getObjEvent()==null) return "";
  	return getObjEvent().getDescripcion();
  }  
 
 BizContrato objContrato;
  
  public BizContrato getObjContrato() throws Exception {
  	if (objContrato!=null) return objContrato;
  	BizContrato contrato = new BizContrato();
  	contrato.dontThrowException(true);
  	if (!contrato.read(getId())) return null;
  	return objContrato=contrato;
  }
  public JRecords<BizNivel> getObjNiveles() throws Exception {
	  JRecords<BizNivel> records = new JRecords<BizNivel>(BizNivel.class);
		records.addFilter("company", this.getCompany());
		records.addFilter("id", this.getId());
		records.addFilter("linea", this.getLinea());
		return records;
	}
  public String getHtmlData() throws Exception {
  	String s;
		try {
			s = "";
			s+="<div style=\"display:block\">";
			s+="<div style=\"width: "+getStringCumplido()+";background-color: "+((getCumplido()>=100)?"#AAFFAA":getCumplido()>90?"#FFAAAA":"#AAAAFF") +";position:relative;overflow:hidden;display:block;\">&nbsp;"+getStringCumplido()+"</div>";
			s+="<table width=\"100%\">";
			s+="<tr>";
			s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: left; color: rgb(0, 0, 0); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">"+(getSigValorObjetivo()==0?"Obj.Cumplidos":JTools.toPrinteableNumber(getSigValorObjetivo(),2))+"</font></td>";
			s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: right; color: rgb(0, 200, 0); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">"+getNivelAlcanzado()+"</font></td>";
			s+="</tr>";
			s+="<tr>";
			s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: left; color: rgb(0, 0, 0); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">"+JTools.toPrinteableNumber(getValorActual(),2)+"</font></td>";
			s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: right; color: rgb(0, 0, 200); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">"+JTools.toPrinteableNumber(getGanancia(),2)+"</font></td>";
			s+="</tr>";
			s+="</table>";
			s+="</div>";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
  	return s;
  }

  
  public String getHtmlTitulo() throws Exception {
  	String s="";
  	
  	s+="<table width=\"100%\">";
  	s+="<tr>";
  	s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: left; color: rgb(255, 255, 255); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">Obj.</font></td>";
  	s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: right; color: rgb(0, 200, 0); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">Nivel</font></td>";
  	s+="</tr>";
  	s+="<tr>";
  	s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: left; color: rgb(255, 255, 255); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">Valor</font></td>";
  	s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: right; color: rgb(125, 128, 200); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">$</font></td>";
  	s+="</tr>";
  	s+="</table>";
  	return s;
  }

	
	BizSqlEvent objEvent;
	
	BizSqlEvent getObjEvent() throws Exception {
		if (objEvent!=null) return objEvent;
		BizSqlEvent e = BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance().GetcDato();
		e.dontThrowException(true);
		if (!e.read(getVariable())) return null;
		return objEvent=e;
	}
	BizSqlEvent objEventGanancia;
	
	BizSqlEvent getObjEventGanancia() throws Exception {
		if (objEventGanancia!=null) return objEventGanancia;
		BizSqlEvent e = BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance().GetcDato();
		e.dontThrowException(true);
		if (!e.read(getVariableGanancia())) return null;
		return objEventGanancia=e;
	}
  /**
   * Constructor de la Clase
   */
  public BizContratoConsolidado() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id", pId );
    this.addItem( "linea", pLinea );
    this.addItem( "valor", pValor );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "logica", pLogica );
    this.addItem( "variable", pVariable );
    this.addItem( "variable_ganancia", pVariableGanancia );
    this.addItem( "modelo", pModelo );
    this.addItem( "acumulativo", pAcumulativo );
    this.addItem( "periodo", pPeriodo );
    this.addItem( "fecha_desde", pFDesde );
    this.addItem( "fecha_hasta", pFHasta );
    this.addItem( "anio", pAnio );
    this.addItem( "mes", pMes );
    this.addItem( "activo", pActivo );
    this.addItem( "mes_literal", pMesLiteral );
    this.addItem( "html_data", pHtmlData );
    this.addItem( "descr_variable", pDescrVariable );
    this.addItem( "descr_contrato", pDescrContrato );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "id", "Id", true, true, 64 );
    this.addFixedItem( KEY, "linea", "Linea", false, false, 32 );
    this.addFixedItem( FIELD, "valor", "Valor", true, false, 18 );
    this.addFixedItem( FIELD, "logica", "Logica", true, false, 500 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, false, 200 );
    this.addFixedItem( FIELD, "variable", "Variable", true, true, 18 );
    this.addFixedItem( FIELD, "variable_ganancia", "variable_ganancia", true, false, 18 );
    this.addFixedItem( FIELD, "modelo", "modelo", true, false, 18 );
    this.addFixedItem( FIELD, "acumulativo", "acumulativo", true, false, 1 );
    this.addFixedItem( FIELD, "periodo", "periodo", true, false, 50 );
    this.addFixedItem( FIELD, "mes", "Mes", true, false, 18 );
    this.addFixedItem( FIELD, "activo", "Activo", true, false, 1 );
    this.addFixedItem( FIELD, "anio", "Anio", true, false, 18 );
    this.addFixedItem( FIELD, "fecha_desde", "Fecha desde", true, false, 18 );
    this.addFixedItem( FIELD, "fecha_hasta", "Fecha hasta", true, false, 18 );
    this.addFixedItem( VIRTUAL, "html_data", getHtmlTitulo(), true, false, 400 );
    this.addFixedItem( VIRTUAL, "mes_literal", getHtmlTitulo(), true, false, 200 );
    this.addFixedItem( FIELD, "descr_contrato", "Descr.Contrato", true, false, 200 );
    this.addFixedItem( VIRTUAL, "descr_variable", "Descr.Variable", true, false, 200 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "contrato_consolidado"; }
  public String GetTableTemporal() throws Exception  { 
  	String sql="";
//  	sql+=" select (date_part('year',dd)) as anio,(date_part('month',dd)) as mes,bsp_contrato.fecha_desde as fecha_desde,bsp_contrato.fecha_hasta as fecha_hasta,";
//  	sql+=" bsp_contrato_detalle.company, bsp_contrato_detalle.id, bsp_contrato_detalle.linea, bsp_contrato_detalle.valor, "; 
//  	sql+=" bsp_contrato_detalle.variable, bsp_contrato_detalle.variable_ganancia, bsp_contrato_detalle.modelo, bsp_contrato_detalle.periodo, bsp_contrato_detalle.acumulativo "; 
//		sql+=" from";
//		sql+=" generate_series( '1900-01-01'::timestamp , '2100-12-31'::timestamp, '1 month'::interval) dd";   
//		sql+=" left JOIN bsp_contrato on date_part('month',dd)=date_part('month',bsp_contrato.fecha_desde) and date_part('year',dd)=date_part('year',bsp_contrato.fecha_desde)  AND bsp_contrato.company = '"+BizUsuario.getUsr().getCompany()+"'"; 
//		sql+=" left join bsp_contrato_detalle ON bsp_contrato.id=bsp_contrato_detalle.id ";
//		sql+=" order by dd";
  	sql+=" select (date_part('year',dd)) as anio,(date_part('month',dd)) as mes,bsp_contrato.fecha_desde as fecha_desde,bsp_contrato.active as activo,bsp_contrato.fecha_hasta as fecha_hasta,";
  	sql+=" bsp_contrato.descripcion as descr_contrato,bsp_contrato_detalle.company, bsp_contrato_detalle.id, bsp_contrato_detalle.linea, bsp_contrato_detalle.valor,  "; 
  	sql+=" bsp_contrato_detalle.descripcion,bsp_contrato_detalle.logica as logica,bsp_contrato_detalle.variable, bsp_contrato_detalle.variable_ganancia, bsp_contrato_detalle.modelo, bsp_contrato_detalle.periodo, bsp_contrato_detalle.acumulativo "; 
		sql+=" from";
		sql+=" generate_series( '1900-01-01'::timestamp , '2100-12-31'::timestamp, '1 month'::interval) dd";   
		sql+=" JOIN bsp_contrato on (dd between bsp_contrato.fecha_desde and bsp_contrato.fecha_hasta) ";
	
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) 
  		sql+=" AND bsp_contrato.company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+") ";
  	else	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) 
  		sql+=(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and ( bsp_contrato.id in (select contrato from bsp_prorrateo where contrato = bsp_contrato.id and cliente like '%"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"%')) ");
		else
			sql+=" AND bsp_contrato.company = '"+BizUsuario.getUsr().getCompany()+"'"; 
		
		sql+=" join bsp_contrato_detalle ON bsp_contrato.id=bsp_contrato_detalle.id ";
		sql+=" order by dd";
  	
		return "("+sql+ ") as "+GetTable(); 
  }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( long zLinea ) throws Exception { 
    addFilter( "linea",  zLinea ); 
    return read(); 
  } 
  
  public boolean read() throws Exception {
  	if (getFilter("mes_literal")!=null) {
  		addFilter("mes", JTools.getNumberEmbedded(getFilter("mes_literal").getValue()));
  		clearFilter("mes_literal");
  	}
  	return super.read();
  };
  BizDetalle objDetalle;
  public BizDetalle getObjDetalle() throws Exception {
  	if (objDetalle!=null) return objDetalle;
  	BizDetalle det = new BizDetalle();
  	det.dontThrowException(true);
  	if (!det.read(getLinea())) return null;
  	ILogicaContrato logica = det.getObjLogicaInstance();
  	BizDetalle biz = logica.getBiz();
  	biz.copyProperties(det);
  	return objDetalle=biz;
  }
}
