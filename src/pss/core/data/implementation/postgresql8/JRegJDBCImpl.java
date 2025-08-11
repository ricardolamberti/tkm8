package pss.core.data.implementation.postgresql8;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.postgresql.util.PGbytea;

import com.ibm.icu.util.Calendar;

import pss.common.customList.config.relation.JRelations;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.connections.JBaseJDBC;
import pss.core.data.interfaces.sentences.JRegJDBC;
import pss.core.data.interfaces.structure.RField;
import pss.core.data.interfaces.structure.RFilter;
import pss.core.data.interfaces.structure.RFixedFilter;
import pss.core.data.interfaces.structure.RFixedOrderBy;
import pss.core.data.interfaces.structure.RJoins;
import pss.core.data.interfaces.structure.ROrderBy;
import pss.core.services.fields.JIntervalDate;
import pss.core.services.fields.JIntervalDateTime;
import pss.core.services.fields.JObject;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;

public class JRegJDBCImpl extends JRegJDBC {

	// private int wherelen = 0;
	protected long pagesize = -1;
	protected long offset = -1;
	protected boolean withUse = false;

	public boolean isWithUse() {
		return withUse;
	}

	public void setWithUse(boolean useUse) {
		this.withUse = useUse;
	}

	public void setPageSize(long zRows) {
		pagesize = zRows;
	}

	public void setOffset(long zRows) {
		offset = zRows;
	}

	// public long selectCount() throws Exception {
	// QueryInit();
	// sSQL="select count(*) as cantidad "+buildFrom()+SPACE+this.Where();
	// QueryOpen();
	// if (!this.next()) return 0;
	// return this.CampoAsLong("cantidad").longValue();
	// }

	@Override
	public String getVirtualBDsField(String zFieldToEvaluate, JRecords<BizVirtual> vBDs, String zElse) throws Exception {
		String sConditionalString = "";
		String sConditionalStringEnd = "";
		vBDs.firstRecord();
		while (vBDs.nextRecord()) {
			BizVirtual oVirtual = vBDs.getRecord();
			sConditionalString = " decode(" + zFieldToEvaluate + "," + oVirtual.getValor() + "," + oVirtual.getDescrip() + ",";
			sConditionalStringEnd += ")";
		}
		if (!sConditionalString.equals("")) {
			sConditionalString += zElse + sConditionalStringEnd;
		} 
		return sConditionalString;
	}

	@Override
	protected String getConditionalFieldAux(String zFieldToEvaluate, JList<String> vCondition, String zTrue, String zFalse) throws Exception {
		String sConditionalString = "";
		String sConditionalStringEnd = "";
		JIterator<String> oIter = vCondition.getIterator();
		while (oIter.hasMoreElements()) {
			String zCondition = oIter.nextElement();
			sConditionalString += " decode(" + zFieldToEvaluate + "," + zCondition + "," + zTrue + ",";
			sConditionalStringEnd += ")";
		}
		if (!sConditionalString.equals(""))
			sConditionalString += zFalse + sConditionalStringEnd;
		return sConditionalString;
	}

	@Override
	public String getConcatenateOperator() throws Exception {
		return " || ";
	}

	public JRegJDBCImpl() {
		super();
	}

	// public String Where() throws Exception {
	// String where = super.Where();
	// this.wherelen = where.trim().length();
	// return where;
	// }

	public String getTop() {
		String ret = "";
		if (lTopRows < 0)
			return ret;
		return ret + " limit " + lTopRows + " ";
	}

	
	@Override
	public String ArmarSelect() throws Exception {
		StringBuffer sAux = new StringBuffer();
		boolean bFirst = true;
		boolean bFirstOrderBy = true;

		sAux.append(this.buildWith());

		sAux.append(SELECT);
		sAux.append(getDistinct());

		if (!oDato.getStructure().hasFields()) {
			sAux.append(getTable() + ".*");
		} else {
			JList<RField> aCampos = oDato.getFields();
			JIterator<RField> oIt = aCampos.getIterator();
			while (oIt.hasMoreElements()) {
				RField oCampo = oIt.nextElement();
				if (bFirst) {
					bFirst = false;
				} else {
					sAux.append(" , ");
				}
				if (oCampo.hasTable()) {
					sAux.append(oCampo.GetTabla() + ".");
				}
				sAux.append(getField(oCampo));
				
				if (oCampo.hasRename())
					sAux.append(" as "+oCampo.GetRename()+" ");

			}
		}
		sAux.append(this.buildFrom());
		sAux.append(this.Where());
		sAux.append(this.GroupBy());
		sAux.append(this.Having());

		// armo el Order by del Select
		if (oDato.getStructure().hasCorteControl()) {
			JList<ROrderBy> oEnum = oDato.getCorteControl();
			bFirst = true;
			JIterator<ROrderBy> oIt = oEnum.getIterator();
			while (oIt.hasMoreElements()) {
				ROrderBy oOrder = oIt.nextElement();
				if (bFirst && bFirstOrderBy) {
					bFirst = false;
					bFirstOrderBy = false;
					sAux.append(" order by ");
				} else {
					sAux.append(" , ");
				}
				if (oOrder.GetTabla() != null && !oOrder.GetTabla().equals(""))
					sAux.append(oOrder.GetTabla() + ".");
				sAux.append(oOrder.GetCampo() + " " + oOrder.GetOrden());
			}
		}
		if (oDato.getStructure().hasOrderBy()) {
			JList<ROrderBy> oEnum = oDato.getOrderBy();
			bFirst = true;
			JIterator<ROrderBy> oIt = oEnum.getIterator();
			while (oIt.hasMoreElements()) {
				ROrderBy oOrder = oIt.nextElement();
				if (bFirst && bFirstOrderBy) {
					bFirst = false;
					bFirstOrderBy = false;
					sAux.append(" order by ");
				} else {
					sAux.append(" , ");
				}
				if (oOrder.GetTabla() != null && !oOrder.GetTabla().equals(""))
					sAux.append(oOrder.GetTabla() + ".");
				sAux.append(oOrder.GetCampo() + " " + oOrder.GetOrden());
			}
		}
		// armo el Order by Fijo del Select
		if (oDato.getStructure().hasFixedOrderBy()) {
			JList<RFixedOrderBy> oEnum = oDato.getFixedOrderBy();
			bFirst = true;
			JIterator<RFixedOrderBy> oIt = oEnum.getIterator();
			while (oIt.hasMoreElements()) {
				RFixedOrderBy oOrder = oIt.nextElement();
				if (bFirst && bFirstOrderBy) {
					bFirst = false;
					bFirstOrderBy = false;
					sAux.append(" order by ");
				} else
					sAux.append(" , ");
				sAux.append(oOrder.GetOrder());
			}
		}

		if (oDato.ifLock()) {
			sAux.append(" for Update ");
		}

		if (!this.getTop().equals(""))sAux.append(this.getTop());
		else sAux.append(this.getLimitWithOffset());

		return sAux.toString();
	}

	public boolean isDatabaseSupportsOffset() {
		return true;
	}

	private String getLimitWithOffset() {
		String ret = "";
		if (pagesize < 0)
			return ret;
		ret += " limit " + pagesize + " ";
		if (offset < 0)
			return ret;
		ret += " offset " + offset + " ";
		return ret;
	}
	public String WhereUse() throws Exception {
		StringBuffer sBuffer=new StringBuffer(128);
		String sOper;
		String sTipo;
		boolean bFirst=true;

		sBuffer.append(SPACE);

		if (oDato.getStructure().hasFixedFilters()) {
			JList<RFixedFilter> oEnum=oDato.getFixedFilters();
			Iterator<RFixedFilter> oIt=oEnum.iterator();
			while (oIt.hasNext()) {
				RFixedFilter oFiltroFijo=oIt.next();
				if (oFiltroFijo.getsTableAsoc()!=null&&!oFiltroFijo.getsTableAsoc().equalsIgnoreCase(this.getTable())) continue;
				String sFiltro= oFiltroFijo.getFiltro().trim();
				if (sFiltro.equals("")) continue;
				if (!bFirst) {
					if (sFiltro.toLowerCase().startsWith("where")) {
						sFiltro=AND+sFiltro.substring(5);
					}
					if (!sFiltro.toLowerCase().trim().startsWith("and")&&!sFiltro.toLowerCase().trim().startsWith("or")) {
						sFiltro=AND+sFiltro;
					}
				} else {
					if (sFiltro.toLowerCase().indexOf("where")==-1) {
						sFiltro=" WHERE "+sFiltro;
					}
				}
				sBuffer.append(SPACE);
				sBuffer.append(sFiltro);
				sBuffer.append(SPACE);
				bFirst=false;
			}
		}
		if (oDato.getStructure().hasFilters()) {
			JList<RFilter> eEnum=oDato.getFilters();
			Iterator<RFilter> oIt=eEnum.iterator();

			while (oIt.hasNext()) {
				RFilter oFiltro=oIt.next();
				if (oFiltro.isVirtual()) continue;
				if (!oFiltro.getTable().equalsIgnoreCase(this.getTable())) continue;
				if (bFirst) {
					bFirst=false;
					sBuffer.append(" where ");
				} else {

					// Seteo Relacion
					if (!oFiltro.hasRelation()) {
						sBuffer.append(AND);
					} else {
						sBuffer.append(SPACE);
						sBuffer.append(oFiltro.getRelation());
						sBuffer.append(SPACE);
					}
				}

				// Seteo Agrupamiento
				if (oFiltro.hasAgrup()) 
					sBuffer.append(oFiltro.getAgrup());
				sOper=oFiltro.getOperator();

				if (sOper.equalsIgnoreCase("CONTAINS")||sOper.equalsIgnoreCase("FREETEXT")) {
					sBuffer.append(SPACE+sOper.toUpperCase()+"(");
					if (oFiltro.getTable()==null||oFiltro.getTable().length()==0) 
						sBuffer.append(oFiltro.getField());
					else 
						sBuffer.append(oFiltro.getTable()).append(DOT).append(oFiltro.getField());
					sBuffer.append(COMMA+"'");
					sBuffer.append(oFiltro.getValue().equals("") ? "*" : oFiltro.getValue());
					sBuffer.append("') ");
				} else {

					this.armarCampoEnLike(sBuffer, oFiltro,sOper);
					
					// Seteo operador
					boolean bValorNull=oFiltro.getValue()==null||oFiltro.getValue().trim().equalsIgnoreCase("null")||oFiltro.getValue().trim().length()==0;
					boolean bSinOper=oFiltro.getOperator().trim().length()==0;
					if (bSinOper) sOper="=";
					if (bValorNull) 
						sOper=(sOper.equals("=")) ? "IS" : "IS NOT";

					// Seteo el filtro
					sTipo=oFiltro.getType();

					if (sOper.equalsIgnoreCase("ilike")) {
						ilike(sBuffer,sTipo,oFiltro.getValue());
					}
					else {
						sBuffer.append(SPACE);
						sBuffer.append(sOper);
						sBuffer.append(SPACE);
	
	
						if (sOper.equalsIgnoreCase("IN") || sOper.equalsIgnoreCase("NOT IN")) 
							sBuffer.append(ArmarIn(oDato.getStructure().getTable(), sTipo, oFiltro.getValue()));
						else if (sOper.equalsIgnoreCase("like") )
							armarLike(sBuffer, sTipo, oFiltro.getValue());
						else sBuffer.append(ArmarDato(oDato.getStructure().getTable(), sTipo, oFiltro.getValue()));
					}
				}
				// Seteo Agrupamiento
				if (oFiltro.hasAgrup()) 
					sBuffer.append(oFiltro.getAgrup());

			}
		}
		return sBuffer.toString();
	} // TRegSQL.Where

	public String Where() throws Exception {
		StringBuffer sBuffer=new StringBuffer(128);
		String sOper;
		String sTipo;
		boolean bFirst=true;

		sBuffer.append(SPACE);

		if (oDato.getStructure().hasFixedFilters()) {
			JList<RFixedFilter> oEnum=oDato.getFixedFilters();
			Iterator<RFixedFilter> oIt=oEnum.iterator();
			while (oIt.hasNext()) {
				RFixedFilter oFiltroFijo=oIt.next();
				if (isWithUse() &&  oFiltroFijo.getsTableAsoc()!=null&&oFiltroFijo.getsTableAsoc().equalsIgnoreCase(this.getTable())) continue;
				String sFiltro= oFiltroFijo.getFiltro().trim();
				if (sFiltro.equals("")) continue;
				if (!bFirst) {
					if (sFiltro.toLowerCase().startsWith("where")) {
						sFiltro=AND+sFiltro.substring(5);
					}
					if (!sFiltro.toLowerCase().trim().startsWith("and")&&!sFiltro.toLowerCase().trim().startsWith("or")) {
						sFiltro=AND+sFiltro;
					}
				} else {
					if (sFiltro.toLowerCase().indexOf("where")==-1) {
						sFiltro=" WHERE "+sFiltro;
					}
				}
				sBuffer.append(SPACE);
				sBuffer.append(sFiltro);
				sBuffer.append(SPACE);
				bFirst=false;
			}
		}
		if (oDato.getStructure().hasFilters()) {
			JList<RFilter> eEnum=oDato.getFilters();
			Iterator<RFilter> oIt=eEnum.iterator();

			while (oIt.hasNext()) {
				RFilter oFiltro=oIt.next();
				if (oFiltro.isVirtual()) continue;
				if (isWithUse() && oFiltro.getTable().equalsIgnoreCase(this.getTable())) continue;
				if (bFirst) {
					bFirst=false;
					sBuffer.append(" where ");
				} else {

					// Seteo Relacion
					if (!oFiltro.hasRelation()) {
						sBuffer.append(AND);
					} else {
						sBuffer.append(SPACE);
						sBuffer.append(oFiltro.getRelation());
						sBuffer.append(SPACE);
					}
				}

				// Seteo Agrupamiento
				if (oFiltro.isParentesis()) {
					if (oFiltro.isStartParentesis())
						sBuffer.append(oFiltro.getAgrup());
				} else if (oFiltro.hasAgrup()) 
					sBuffer.append(oFiltro.getAgrup());
				sOper=oFiltro.getOperator();

				if (sOper.equalsIgnoreCase("CONTAINS")||sOper.equalsIgnoreCase("FREETEXT")) {
					sBuffer.append(SPACE+sOper.toUpperCase()+"(");
					if (oFiltro.getTable()==null||oFiltro.getTable().length()==0) 
						sBuffer.append(oFiltro.getField());
					else 
						sBuffer.append(oFiltro.getTable()).append(DOT).append(oFiltro.getField());
					sBuffer.append(COMMA+"'");
					sBuffer.append(oFiltro.getValue().equals("") ? "*" : oFiltro.getValue());
					sBuffer.append("') ");
				} else if (oFiltro.getType().equals(JObject.JINTERVALDATETIME)) {
					String field = "";
					if (!oFiltro.hasTable()) 
						field = oFiltro.getField();
					else 
						field = oFiltro.getTable()+DOT+oFiltro.getField();
					  String valueFrom =  ((JIntervalDateTime)oFiltro.getObjValue()).getStartDateValueAsString();
					  String valueTo =  ((JIntervalDateTime)oFiltro.getObjValue()).getEndDateValueAsString();
					  if (valueFrom!=null && valueTo!=null && !valueFrom.equals("") && !valueTo.equals(""))
					  	sBuffer.append(fintervalo(field, ArmarDato(oDato.getStructure().getTable(), JObject.JDATETIME, valueFrom,false), ArmarDato(oDato.getStructure().getTable(), JObject.JDATETIME, valueTo,false)));
					  else 
					  	sBuffer.append(" true ");
				} else if (oFiltro.getType().equals(JObject.JINTERVALDATE)) {
					String field = "";
					if (!oFiltro.hasTable()) 
						field = oFiltro.getField();
					else 
						field = oFiltro.getTable()+DOT+oFiltro.getField();
					  String valueFrom =  ((JIntervalDate)oFiltro.getObjValue()).getStartDateValueAsString();
					  String valueTo =  ((JIntervalDate)oFiltro.getObjValue()).getEndDateValueAsString();
					  if (valueFrom!=null && valueTo!=null && !valueFrom.equals("") && !valueTo.equals(""))
					  	sBuffer.append(fintervalo(field, ArmarDato(oDato.getStructure().getTable(), JObject.JDATE, valueFrom,false), ArmarDato(oDato.getStructure().getTable(), JObject.JDATE, valueTo,false)));
					  else 
					  	sBuffer.append(" true ");
				} else {
	
						this.armarCampoEnLike(sBuffer, oFiltro,sOper);
						
						// Seteo operador
						boolean bValorNull=oFiltro.getValue()==null||oFiltro.getValue().trim().equalsIgnoreCase("null")||oFiltro.getValue().trim().length()==0;
						boolean bSinOper=oFiltro.getOperator().trim().length()==0;
						if (bSinOper) sOper="=";
						if (bValorNull) 
							sOper=(sOper.equals("=")) ? "IS" : "IS NOT";
	
						// Seteo el filtro
						sTipo=oFiltro.getType();

						if (sOper.equalsIgnoreCase("ilike")) {
							ilike(sBuffer,sTipo,oFiltro.getValue());
						}
						else {
							sBuffer.append(SPACE);
							sBuffer.append(sOper);
							sBuffer.append(SPACE);
		
		
							if (sOper.equalsIgnoreCase("IN") || sOper.equalsIgnoreCase("NOT IN")) 
								sBuffer.append(ArmarIn(oDato.getStructure().getTable(), sTipo, oFiltro.getValue()));
							else if (sOper.equalsIgnoreCase("like") )
								armarLike(sBuffer, sTipo, oFiltro.getValue());
							else sBuffer.append(ArmarDato(oDato.getStructure().getTable(), sTipo, oFiltro.getValue()));
					}
				}
				
				// Seteo Agrupamiento
				if (oFiltro.isParentesis()) {
					if (oFiltro.isEndParentesis())
						sBuffer.append(oFiltro.getAgrup());
				} else if (oFiltro.hasAgrup()) 
					sBuffer.append(oFiltro.getAgrup());

			}
		}
		return sBuffer.toString();
	} // TRegSQL.Where

	private String getField(RField campo) {
		return (campo.GetCampo());
	}

	private String getTable() throws Exception {
		return (oDato.getStructure().getTable());
	}
	private String getTableForFrom() throws Exception {
		return (oDato.getStructure().getTableForFrom());
	}

	@Override
	public String toNumber(String zFieldname) throws Exception {
		// return "to_number(" + (zFieldname) + ")";
		return "cast (" + zFieldname + " as numeric)";
	}

	@Override
	public String fsum(String zFieldname) throws Exception {
		return "coalesce(sum(" + (zFieldname) + "),0)";
	}
	@Override
	public String fnulo(String zFieldname) throws Exception {
		return "(" + (zFieldname) + " is null)";
	}
	@Override
	public String fnonulo(String zFieldname) throws Exception {
		return "(" + (zFieldname) + " is not null)";
	}
	@Override
	public String fsumover(String zFieldname) throws Exception {
		return "sum(" + (zFieldname) + ") over()"; // se usa para por porcentajes
	}
	@Override
	public String fmax(String zFieldname) throws Exception {
		return "max(" + (zFieldname) + ")";
	}
	@Override
	public String fmin(String zFieldname) throws Exception {
		return "min(" + (zFieldname) + ")";
	}

	@Override
	public String favg(String zFieldname) throws Exception {
		return "avg(" + (zFieldname) + ")";
	}

	@Override
	public String fcount(String zFieldname) throws Exception {
		return "count( " + (zFieldname) + ")";
	}

	public String fsubstring(String zFieldname, int desde, int hasta) throws Exception {
		return "substr(" + (zFieldname) + "," + desde + "," + hasta + ")";
	}

	public String ftoDate(String zFieldname, String format) throws Exception {
		return "to_date(" + (zFieldname) + ", '" + format + "')";
	}
	
	@Override
	public String fmes(String zFieldname) throws Exception {
		return "date_part('month'::text," + (zFieldname) + ")";
	}
	public String fbooleanValue(String zFieldname,String strue,String sfalse) throws Exception {
		return "(case " + (zFieldname) + " when true then '"+strue+"' else '"+sfalse+"' end)";
	}
	@Override
	public String fanio(String zFieldname) throws Exception {
		return "date_part('year'," + (zFieldname) + ")";
	}
	public String fbimestre(String zFieldname) throws Exception {
		return "floor(((extract (month from " + (zFieldname) + "))-1 ) / 2) +1";
	}
	public String ftrimestre(String zFieldname) throws Exception {
		return "floor(((extract (month from " + (zFieldname) + "))-1 ) / 3) +1";
	}
	public String fcuatrimestre(String zFieldname) throws Exception {
		return "extract(QUARTER  from " + (zFieldname) + ")";
	}
	public String fsemestre(String zFieldname) throws Exception {
		return "floor(((extract (month from  " + (zFieldname) + "))-1 ) / 6) +1";
	}
	public String fdiasemana(String zFieldname) throws Exception {
		return "case extract(dow from " + (zFieldname) + ") when 1 then '1.Lunes' when 2 then '2.Martes' when 3 then '3.Miercoles' when 4 then '4.Jueves' when 5 then '5.Viernes' when 6 then '6.Sabado' else '7.Domingo' end";
	}
	public String fdiames(String zFieldname) throws Exception {
		return "extract('day' from " + (zFieldname) + ")";
	}
	@Override
	public String faniomes(String zFieldname) throws Exception {
		return "(CAST(date_part('year'," + (zFieldname) + ") as Text) || '/' || lpad(CAST(date_part('month'::text," + (zFieldname) + ") as text),2,'0'))";
	}
	@Override
	public String faniosem(String zFieldname) throws Exception {
		return "(CAST(date_part('year'," + (zFieldname) + ") as Text) || '/' || lpad(CAST(date_part('week'::text," + (zFieldname) + ") as text),2,'0'))";
	}
	public String fporcWithOver(String zFieldname,JList<String> partition) throws Exception {
		String campos ="";
		if (partition!=null) {
			JIterator<String> it = partition.getIterator();
			while (it.hasMoreElements()) {
				campos += (campos.equals("")?"PARTITION BY ":",")+it.nextElement();
			}
		}
		return "case sum( " + (zFieldname) + ") over() when 0 then 0 else ((100* " + (zFieldname) + ") / sum( " + (zFieldname) + ") over("+campos+") ) end ";
	}
	public String fporc(String zFieldname,String zTotalize) throws Exception {
		return "case ("+zTotalize+") when 0 then 0 else ((100.0* " + (zFieldname) + ") / ("+zTotalize+") ) end ";
	}

	public String ftoChar(String zFieldname, String format) throws Exception {
		return "to_char(" + (zFieldname) + ", '" + format + "')";
	}
	public boolean hoyEsAhora(Date hoy) throws Exception  {
		if (BizUsuario.getUsr()==null)
			return (hoy==null||JDateTools.getDateStartDay(hoy).equals(JDateTools.getDateStartDay(new Date())));
		return (hoy==null||JDateTools.getDateStartDay(hoy).equals(JDateTools.getDateStartDay(BizUsuario.getUsr().todayGMT())));

	}
	public String fanioactual(String zFieldname,Date hoy) throws Exception {
		if (hoyEsAhora(hoy))
			return  "date_part('year'," + (zFieldname) + ") = date_part('year', now())";
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		return  "date_part('year'," + (zFieldname) + ") = "+c.get(Calendar.YEAR);
	}
	public String fmesactual(String zFieldname,Date hoy) throws Exception {
		if (hoyEsAhora(hoy))
			return  "date_part('year'," + (zFieldname) + ") = date_part('year', now()) and date_part('month'," + (zFieldname) + ") = date_part('month', now()) ";
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		return  "date_part('year'," + (zFieldname) + ") = "+c.get(Calendar.YEAR)+" and date_part('month'," + (zFieldname) + ") = "+(c.get(Calendar.MONTH)+1)+" ";
	}
	public String fhoy(String zFieldname,Date hoy) throws Exception {
		if (hoyEsAhora(hoy))
			return " "+zFieldname + "::date = 'today'::date ";
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		return " "+zFieldname + "::date = '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date ";
	}
	public String ffuturo(String zFieldname,Date hoy) throws Exception {
		if (hoyEsAhora(hoy))
			return " "+zFieldname + "::date > 'today'::date ";
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		return " "+zFieldname + "::date > '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date ";
	}
	public String fpasado(String zFieldname,Date hoy) throws Exception {
		if (hoyEsAhora(hoy))
			return " "+zFieldname + "::date < 'today'::date ";
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		return " "+zFieldname + "::date < '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date ";
	}
	public String fayer(String zFieldname,Date hoy) throws Exception {
		if (hoyEsAhora(hoy))
			return " "+zFieldname + "::date = 'yesterday'::date ";
		if (hoy==null)
			hoy=new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		c.add(Calendar.DAY_OF_MONTH, -1);
		return " "+zFieldname + "::date = '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date ";
	}
	public String fmaniana(String zFieldname,Date hoy) throws Exception {
		if (hoyEsAhora(hoy))
			return " "+zFieldname + "::date = 'tomorrow'::date ";
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		c.add(Calendar.DAY_OF_MONTH, 1);
		return " "+zFieldname + "::date = '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date ";
	}
	public String fultimos(String zFieldname,Date hoy,long valorDias) throws Exception {
		if (hoyEsAhora(hoy))
			return " ("+zFieldname + "::date > now() - '"+valorDias+" days'::interval and "+zFieldname + "::date < now()::date) ";
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		return " ("+zFieldname + "::date > '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date - '"+valorDias+" days'::interval and "+zFieldname + "::date < '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date) ";
	}
	public String fproximos(String zFieldname,Date hoy,long valorDias) throws Exception {
		if (hoyEsAhora(hoy))
			return " ("+zFieldname + "::date < now() + '"+valorDias+" days'::interval and "+zFieldname + "::date > now()::date) ";
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		return " ("+zFieldname + "::date < '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date + '"+valorDias+" days'::interval and "+zFieldname + "::date > '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date) ";
	}
//	public String fintervalo(String zFieldname,String valorDias1,String valorDias2) throws Exception {
//		return " ("+zFieldname + "::date > now() + '"+valorDias1+" days'::interval and "+zFieldname + "::date < now() + '"+valorDias2+" days'::interval) ";
//	}
	public String fintervalo(String zFieldname,String valor1,String valor2) throws Exception {
		return " ("+zFieldname + " between "+valor1+" and "+valor2+" )";
	}
	public String fbimestreactual(String zFieldname,Date hoy) throws Exception {
		if (hoyEsAhora(hoy))
			return " (floor(((extract (month from " + (zFieldname) + "))-1 ) / 2) +1 = floor(((extract (month from now()))-1 ) / 2) +1) and "+fanioactual(zFieldname,hoy);
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		return " (floor(((extract (month from " + (zFieldname) + "))-1 ) / 2) +1 = floor(((extract (month from '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date))-1 ) / 2) +1) and "+fanioactual(zFieldname,hoy);
	}
	public String ftrimestreactual(String zFieldname,Date hoy) throws Exception {
		if (hoyEsAhora(hoy))
			return " (floor(((extract (month from " + (zFieldname) + "))-1 ) / 3) +1 = floor(((extract (month from now()))-1 ) / 3) +1) and "+fanioactual(zFieldname,hoy);
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		return " (floor(((extract (month from " + (zFieldname) + "))-1 ) / 3) +1 = floor(((extract (month from '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date))-1 ) / 3) +1) and "+fanioactual(zFieldname,hoy);
	}
	public String fcuatrimestreactual(String zFieldname,Date hoy) throws Exception {
		if (hoyEsAhora(hoy))
			return " (extract(QUARTER  from " + (zFieldname) + ")  = extract(QUARTER  from now())) and "+fanioactual(zFieldname,hoy);
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		return " (extract(QUARTER  from " + (zFieldname) + ")  = extract(QUARTER  from '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date)) and "+fanioactual(zFieldname,hoy);
	}
	public String fsemestreactual(String zFieldname,Date hoy) throws Exception {
		if (hoyEsAhora(hoy))
			return " (floor(((extract (month from " + (zFieldname) + "))-1 ) / 6) +1 = floor(((extract (month from now()))-1 ) / 6) +1) and "+fanioactual(zFieldname,hoy);
		Calendar c = Calendar.getInstance();
		c.setTime(hoy);
		return " (floor(((extract (month from " + (zFieldname) + "))-1 ) / 6) +1 = floor(((extract (month from '"+JDateTools.DateToString(c.getTime(),"dd/MM/yyyy")+"'::date))-1 ) / 6) +1) and "+fanioactual(zFieldname,hoy);
	}
	@Override
	public String ArmarInsert() throws Exception {
		String sAux;
		String sValor;
		String sCampos;
		String sValores;
		boolean bFirst = true;

		sAux = "insert into " + getTable() + " ";
		sCampos = "";
		sValores = "";

		if (!oDato.getStructure().hasFields())
			JExcepcion.SendError("Debe especificar algun campo");
		JList<RField> oEnum = oDato.getFields();
		JIterator<RField> oIt = oEnum.getIterator();
		while (oIt.hasMoreElements()) {
			RField oCampo = oIt.nextElement();
			if (oCampo.ifAutonumerico())
				continue;
			if (oCampo.ifOnlySelects())
				continue;
			if (bFirst) {
				bFirst = false;
			} else {
				sCampos = sCampos + " , ";
				sValores = sValores + " , ";
			}
			sCampos = sCampos + (oCampo.GetCampo());
			sValor = oCampo.GetValor();
			if (oCampo.GetTipo().equals(JObject.JSTRING)) {
				if (sValor == null || sValor.trim().equalsIgnoreCase("null") || sValor.length() == 0) {
					sValores = sValores + "null";
				} else {
//					String a = PGbytea.toPGString(sValor.getBytes("Latin1"));
//					sValores = sValores + "E" + ArmarDato(oCampo.GetTabla(), oCampo.GetTipo(), a);
					sValores = sValores + ArmarDato(oCampo.GetTabla(), oCampo.GetTipo(), sValor);
				}
			} else if (oCampo.GetTipo().equals(JObject.JBLOB)) {
				if (sValor == null || sValor.trim().equalsIgnoreCase("null") || sValor.length() == 0) {
					sValores = sValores + "null";
				} else {
					// GAP - 2009-03-25
					// En postgres los campos bytea (BLOB) necesitan doble caracter de escape
					byte[] b =JTools.stringToByteVector(sValor);
					String a = PGbytea.toPGString(b);
					StringBuffer c=new StringBuffer(a.length());
					for (int iIndex = 0; iIndex < a.length(); ++iIndex) {
						char curr = a.charAt(iIndex);
						if (curr == '\\')
						  c.append('\\');
						else 
							if ( curr == '\'' )
								c.append('\'');
						c.append(curr);
					}

					sValores = sValores + "E" + ArmarDato(oCampo.GetTabla(), oCampo.GetTipo(), c.toString(), false) ;  
				}
			} else {
				sValores = sValores + ArmarDato(oCampo.GetTabla(), oCampo.GetTipo(), sValor);
			}
		}
		sAux = sAux + "(" + sCampos + ") values (" + sValores + ")";
		return sAux;
	}

	@Override
	public String ArmarUpdate() throws Exception {
		String sAux;
		String sTipo;
		boolean bFirst = true;
		sAux = "update " + getTable() + " set ";
		if (!oDato.getStructure().hasFields()) {
			return null;
			// throw new JNoFieldException();
		}
		JList<RField> aCampos = oDato.getFields();
		JIterator<RField> oIt = aCampos.getIterator();
		while (oIt.hasMoreElements()) {
			RField oCampo = oIt.nextElement();
			if (oCampo.ifAutonumerico())
				continue;
			if (oCampo.ifOnlySelects())
				continue;

			if (bFirst) {
				bFirst = false;
			} else {
				sAux = sAux + " , ";
			}
			sAux = sAux + (oCampo.GetCampo()) + " = ";
			sTipo = oCampo.GetTipo();
			String sValor = oCampo.GetValor();

			if (oCampo.GetTipo().equals(JObject.JSTRING)) {
				sAux = sAux + ArmarDato(getTable(), sTipo, oCampo.GetValor());
			} else if (oCampo.GetTipo().equals(JObject.JBLOB)) {
				if (sValor == null || sValor.trim().equalsIgnoreCase("null") || sValor.length() == 0) {
					sAux = sAux + ArmarDato(getTable(), sTipo, oCampo.GetValor());
			} else {
					byte[] b =JTools.stringToByteVector(sValor);
					String a = PGbytea.toPGString(b);
					StringBuffer c=new StringBuffer(a.length());
					for (int iIndex = 0; iIndex < a.length(); ++iIndex) {
						char curr = a.charAt(iIndex);
						if (curr == '\\')
						  c.append('\\');
						else 
							if ( curr == '\'' )
								c.append('\'');
						c.append(curr);
					}

					sAux = sAux + "E" + ArmarDato(getTable(), sTipo, c.toString(),false);
				}
			} else {
				sAux = sAux + ArmarDato(getTable(), sTipo, oCampo.GetValor());
			}
		}
		sAux = sAux + Where();
		return sAux;
	}

	
	@Override
	public String buildWith() throws Exception {
		if (!isWithUse() || this.WhereUse().trim().equals("")) return "";
		String sAux = " with " + getTable() + " as (";
		sAux+= "select "+getTable()+".* ";//from "+ getTableForFrom() + " ";
		sAux+= this.buildFrom();
		sAux+= this.WhereUse();
		if (pagesize!=-1) sAux+= " limit "+pagesize;
		sAux+=") ";
		
		return sAux;
	} 

	@Override
	public String buildFrom() throws Exception {
		String sAux = " from " + getTableForFrom();
		if (oDato.getStructure().hasJoins()) {
			JList<RJoins> oEnum = oDato.getJoins();
			sAux += buildFrom(oEnum);
		}
		return sAux;
	}

	public String buildFrom(JList<RJoins> oJoins) throws Exception {
			String sAux="";
			JIterator<RJoins> oIt = oJoins.getIterator();
			while (oIt.hasMoreElements()) {
				RJoins oJoin = oIt.nextElement();
				if (!oJoin.isInnerJoin())
					sAux = sAux + " , ";
				if (oJoin.hasTypeJoin() && oJoin.getTypeJoin().equals(JRelations.JOIN_ONE)) {
					sAux +=  " JOIN LATERAL (select * ";
					
					if (oJoin.hasJoins()) {
						sAux += "	FROM "+oJoin.GetTablaJoin();
						sAux += buildFrom(oJoin.getJoins());
						sAux += " where "; 
					} else {
						sAux +=  "	FROM "+oJoin.GetTablaJoin()+" where "; 
					}
					sAux +=  "  "+ (oJoin.GetCondicionSegundo()==null?"":oJoin.GetCondicionSegundo().replace(oJoin.GetAliasJoin(), oJoin.GetTablaJoin()))+" "; 
					sAux +=  " LIMIT 1 ";
					sAux +=  " ) " +oJoin.GetAliasJoin()+" ON "+(oJoin.GetCondicion()==null?" true ":oJoin.GetCondicion())+" ";

				} else if (oJoin.hasTypeJoin() && oJoin.getTypeJoin().equals(JRelations.JOIN_TEN)) {
					sAux +=  " JOIN LATERAL (select * ";
					if (oJoin.hasJoins()) {
						sAux += "	FROM "+oJoin.GetTablaJoin();
						sAux += buildFrom(oJoin.getJoins());
						sAux += " where "; 
					} else {
						sAux += "FROM "+oJoin.GetTablaJoin()+" where "; 
					}
					sAux +=  "  "+ (oJoin.GetCondicionSegundo()==null?"":oJoin.GetCondicionSegundo().replace(oJoin.GetAliasJoin(), oJoin.GetTablaJoin()))+" "; 
					sAux +=  " LIMIT 10 ";
					sAux +=  " ) " +oJoin.GetAliasJoin()+" ON "+(oJoin.GetCondicion()==null?" true ":oJoin.GetCondicion())+" ";

				} else {
					sAux +=  " "+(oJoin.hasTypeJoin()?oJoin.getTypeJoin():"");
					sAux +=  " " + oJoin.GetTablaJoin();
					if (oJoin.hasAliasJoin())	
						sAux +=  " " +oJoin.GetAliasJoin()+" ";
					if (oJoin.hasCondicion() || oJoin.hasCondicionSegunda()) {
						String cond = "";
						cond+=(oJoin.GetCondicion()==null?"":" "+oJoin.GetCondicion());
						cond+=(oJoin.GetCondicionSegundo()==null)?"":(cond.equals("")?"":" AND ")+oJoin.GetCondicionSegundo();
						sAux +=  " ON "+cond;
						
					}
				}
			}
			return sAux;
	}

	@Override
	protected Statement getQueryOpenStatement(JBaseJDBC oDatabaseImpl) throws Exception {
		Statement st = oDatabaseImpl.GetConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		// postgres driver put all the records in memory, so we have to change that to avoid
		// using all the memory of the server
		st.setFetchSize(200);
		return st;
	}

	@Override
	protected String getStringValueAux(String zField, int zSize) throws Exception {
		return " to_char(" + (zField) + ") ";
	}

	@Override
	protected String getLeftStringAux(String zField, int zSize) throws Exception {
		return " substr( " + (zField) + ",1," + zSize + ") ";
	}

	@Override
	protected void checkSpecialErrors(SQLException zSQLExe) throws Exception {
		if (zSQLExe.getMessage().indexOf("La conversión del tipo de datos char a datetime " + "produjo un valor datetime fuera de intervalo") != -1)
			JExcepcion.SendError("Fecha fuera de rango");
		if ((zSQLExe.getMessage().indexOf("value larger than specified precision allows for this column") != -1))
			JExcepcion.SendError("Importe fuera de rango");
		if (zSQLExe.getErrorCode() == 54)
			JExcepcion.SendError("Error de Datos Bloqueados, reintente mas tarde.");

	}

	// --------------------------------------------------------------------------
	// //
	// Obtengo el valor de una columna como BLob
	// --------------------------------------------------------------------------
	// //
	@Override
	public String CampoAsBlob(String zCampo) throws Exception {
		String strRta;
		int iIndex;
		byte[] bytesAux;

		bytesAux = oResultSet.getBytes(zCampo);

		if (bytesAux == null) {
			return "";
		}

		strRta = new String(JTools.byteToChar(bytesAux));

		return strRta;
	}

	@Override
	public long GetIdentity(String zCampo) throws Exception {
		String sequenceName = this.getTable() + '_' + zCampo + "_seq";
		QueryInit();
		sSQL = "select currval('"+sequenceName.toLowerCase()+"')";
		QueryOpen();
		this.next();
		String strRta = this.CampoAsStr("currval");
		this.close();
		return Integer.valueOf(strRta);
	}

	public void ilike(StringBuffer sBuffer,String sTipo,String value) throws Exception {
		sBuffer.append(SPACE);
		sBuffer.append("~*");
		sBuffer.append(SPACE);

		sBuffer.append(ArmarDato(oDato.getStructure().getTable(), sTipo, value));
	}
	
	public  String ArmarDato(String zTabla, String zTipo, String zValor) throws Exception {
		return ArmarDato(zTabla, zTipo, zValor,true);
	}
	
	public static String ArmarDato(String zTabla, String zTipo, String zValor, boolean escape) throws Exception {
		String sTipo;

		if (zValor==null) return "null";
		sTipo=zTipo;

		if ( zTabla.length()<=6 && (zValor.trim().equalsIgnoreCase("null")||zValor.length()==0)) {
			return "''";
		}
		
		if (zValor.trim().equalsIgnoreCase("null")||zValor.length()==0) {
			return "NULL";
		}
			
		if (sTipo.equals(JObject.JDATETIME)) {
			if ( zValor.charAt(zValor.length()-4) == '.' )
				zValor = zValor.substring(0, zValor.length()-4);
			return COMILLA+zValor+COMILLA;
		}

		if (sTipo.equals(JObject.JSTRING)) return COMILLA+JTools.escapeQuote(zValor)+COMILLA;
		else if (sTipo.equals(JObject.JINTEGER)) return zValor;
		else if (sTipo.equals(JObject.JFLOAT)) return zValor;
		else if (sTipo.equals(JObject.JCURRENCY)) return zValor;
		else if (sTipo.equals(JObject.JLONG)) return zValor;
		else if (sTipo.equals(JObject.JPASSWORD)) return COMILLA+JTools.escapeQuote(JTools.StringToPassword(zValor))+"' ";
		else if (sTipo.equals(JObject.JHTML)) return COMILLA+JTools.escapeQuote(zValor)+COMILLA;
		else if (sTipo.equals("campo")) return zTabla+DOT+zValor;
		else if (sTipo.equals(JObject.JDATE)) return COMILLA+zValor+COMILLA;
		else if (sTipo.equals(JObject.JINTERVALDATETIME)) return COMILLA+zValor+COMILLA;
		else if (sTipo.equals(JObject.JINTERVALDATE)) return COMILLA+zValor+COMILLA;
		else if (sTipo.equals(JObject.JHOUR)) return COMILLA+JTools.HourToString(zValor)+COMILLA;
		else if (sTipo.equals(JObject.JCOLOUR)) return COMILLA+zValor+COMILLA;
		else if (sTipo.equals(JObject.JIMAGE)) return COMILLA+zValor+COMILLA;
		else if (sTipo.equals(JObject.JBOOLEAN)) return COMILLA+zValor+COMILLA;
//		else if (sTipo.equals(JObject.JDATETIME)) return COMILLA+zValor+COMILLA;
		else if (sTipo.equals(JObject.JGEOPOSITION)) return COMILLA+zValor+COMILLA;
		else if (sTipo.equals(JObject.JMULTIPLE)) return COMILLA+zValor+COMILLA;
		else if (sTipo.equals(JObject.JBLOB)) {
			if ( escape )
			  return COMILLA+JTools.escapeQuote(zValor)+COMILLA;
			else
				return COMILLA+zValor+COMILLA;
		}
		return "";

	}

	public long selectSupraCount(String sql) throws Exception {
		QueryInit();
		int idx = sql.lastIndexOf("limit");
		if (idx!=-1) sql=sql.substring(0, idx);
		sSQL="select count(*) as cantidad from ("+sql+") regs";
		QueryOpen();
		if (!this.next()) return 0;
		return this.CampoAsLong("cantidad").longValue();
	}
	public  String ArmarIn(String zTabla, String zTipo, String zValor) throws Exception {
		if (zValor.startsWith("(")) return zValor;
		StringTokenizer toks = new StringTokenizer(zValor,",");
		String s = "";
		while (toks.hasMoreTokens()) {
			String ss = toks.nextToken();
			s+=(s.equals("")?"":",")+ArmarDato(zTabla, zTipo, ss);
		}
		return "("+s+")";
	}	
}

// -------------------------------------------------------------------------- // // // Obtengo el
// valor de una columna como BLob //
// -------------------------------------------------------------------------- // // @Override public
// String CampoAsBlob(String zCampo) throws Exception { String oStr = new
// String(oResultSet.getBytes(zCampo)); //System.out.println("Longitud Leida: " + oStr.length());
// System.out.println(oStr); return oStr; } }
