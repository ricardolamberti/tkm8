package pss.core.data.interfaces.sentences;

import java.util.Iterator;
import java.util.StringTokenizer;

import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.connections.JBaseJDBC;
import pss.core.data.interfaces.structure.RField;
import pss.core.data.interfaces.structure.RFilter;
import pss.core.data.interfaces.structure.RFixedFilter;
import pss.core.data.interfaces.structure.RFixedHaving;
import pss.core.data.interfaces.structure.RFixedOrderBy;
import pss.core.data.interfaces.structure.RGroupBy;
import pss.core.data.interfaces.structure.RHaving;
import pss.core.data.interfaces.structure.RJoins;
import pss.core.data.interfaces.structure.ROrderBy;
import pss.core.services.fields.JIntervalDate;
import pss.core.services.fields.JIntervalDateTime;
import pss.core.services.fields.JObject;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;

//========================================================================== //
// Clase para Registro SQL
//========================================================================== //
public class JRegSQL extends JBaseRegistro {

	// Futu evitando crear strings a lo loco
	protected static final String COMMA=" , ";
	protected static final String EQUAL=" = ";
	protected static final String SPACE=" ";
	protected static final String COMILLA="'";
	protected static final String DOT=".";
	protected static final String DOT_STAR=".*";
	protected static final String SELECT="select ";
	protected static final String DISTINCT=" DISTINCT ";
	protected static final String INSERT_INTO="insert into ";
	protected static final String UPDATE="update ";
	protected static final String SET=" set ";
	protected static final String DELETE_FROM="delete from ";
	protected static final String AND=" AND ";

	public static final String SQL="SQL";
	public static final String ORACLE9="Oracle9";
	private boolean updatingCatalog;

	protected String sSQL="";
	protected String lastSQL="";
  protected long   lTopRows = -1;
  protected boolean bDistinct = false;

	public void SetSQL(String zSQL) {
		sSQL=zSQL;
	}
	
  public void setTop(long zRows ) {
    lTopRows = zRows;
  }
  
	public String getTop() {
    return "";
  }
  public void setDistinct(boolean dist ) {
  	bDistinct = dist;
  }
 
  
	public String getDistinct() {
    return bDistinct?DISTINCT:"";
  }
	// -------------------------------------------------------------------------- //
	// Clases Base Genericas a sobreescribir
	// -------------------------------------------------------------------------- //
	public void QueryInit() throws Exception {
	}

	protected void QueryOpen() throws Exception {
	}

	protected int QueryExec() throws Exception {
		return -1;
	}

	// -------------------------------------------------------------------------- //
	// Ejecuto un select con cursor
	// -------------------------------------------------------------------------- //
	@Override
	public void openCursor() throws Exception {
		this.QueryInit();
		if (oDato.getStructure().getSQL()!=null)
			sSQL=oDato.getStructure().getSQL();
		else
			sSQL=this.ArmarSelect();
		
		sSQL=((JBaseJDBC) JBDatos.GetBases().getPrivateCurrentDatabase()).convertSqlToANSISql86(sSQL);
		oDato.getStructure().setLastSQL(sSQL);

		this.QueryOpen();
	}

	// -------------------------------------------------------------------------- //
	// Ejecuto un Update
	// -------------------------------------------------------------------------- //
	@Override
	public void update() throws Exception {
		QueryInit();
	  sSQL=this.ArmarUpdate();
	  if (sSQL==null) return; 
	  QueryExec();
	}

	@Override
	public void updateCatalog() throws Exception {
		if (isUpdatingCatalog()) return;
		setUpdatingCatalog(true);
		QueryInit();
		sSQL=this.ArmarUpdateCatalog();
		QueryExec();
	}

	// ------------------------------------------------------------------------- //
	// Updatea solo los campos establecidos, no todos
	// ------------------------------------------------------------------------- //
	public void UpdateOnlyEstablecidos() throws Exception {
		QueryInit();
	}

	// -------------------------------------------------------------------------- //
	// Ejecuto un Delete
	// -------------------------------------------------------------------------- //
	@Override
	public void delete() throws Exception {
		QueryInit();
		sSQL=this.ArmarDelete();
		QueryExec();
	}

	// -------------------------------------------------------------------------- //
	// Ejecuto un Insert
	// -------------------------------------------------------------------------- //
	@Override
	public void insert() throws Exception {
		QueryInit();
		sSQL=this.ArmarInsert();
		QueryExec();
	}


	@Override
	public Object selectObjectMax(String zCampo, boolean isNumber) throws Exception {
		QueryInit();
		String value=(!isNumber) ? this.toNumber(zCampo) : zCampo;
		sSQL="select max("+value+") as max_"+zCampo+this.buildFrom()+this.Where();
		QueryOpen();
		if (!this.next()) return null;
		return CampoAsObject("max_"+zCampo);
	}

	@Override
	public Object selectObjectAvg(String zCampo, boolean isNumber) throws Exception {
		QueryInit();
		String value=(!isNumber) ? this.toNumber(zCampo) : zCampo;
		sSQL="select avg("+value+") as avg_"+zCampo+this.buildFrom()+this.Where();
		QueryOpen();
		if (!this.next()) return null;
		return CampoAsObject("avg_"+zCampo);
	}
	@Override
	public Object selectObjectMin(String zCampo) throws Exception {
		QueryInit();
		sSQL="select min("+zCampo+") as min_"+zCampo+this.buildFrom()+this.Where();
		QueryOpen();
		if (!this.next()) return null;
		return CampoAsObject("min_"+zCampo);
	}

	public String SelectMax(String zCampo, boolean isNumber) throws Exception {
		return (String) selectObjectMax(zCampo, isNumber);
	}

	@Override
	public String selectMin(String zCampo) throws Exception {
		return (String) selectObjectMin(zCampo);
	}
	
	public boolean hasSQL() throws Exception {
		if (this.sSQL==null) return false;
		return !this.sSQL.equals("");
	}

	@Override
	public long selectSupraCount(String sql) throws Exception {
		QueryInit();
//		String internalSQL = this.getdgethasSQL()?this.sSQL : "select 1 as a "+buildFrom()+SPACE+this.Where()+SPACE+this.GroupBy();
//		sSQL="select count(*) as cantidad from ("+internalSQL+") regs";
		sSQL="select count(*) as cantidad from ("+sql+") regs";
		QueryOpen();
		if (!this.next()) return 0;
		return this.CampoAsLong("cantidad").longValue();
	}

	
	@Override
	public long selectCount() throws Exception {
		QueryInit();
//		sSQL="select count(*) as cantidad "+buildFrom()+SPACE+this.Where();
		sSQL="select count(*) as cantidad from (";
		if (oDato.getStructure().getSQL()!=null)
			sSQL+=oDato.getStructure().getSQL();
		else
			sSQL+=this.ArmarSelect();
		sSQL+=") s";
		
		
		
		QueryOpen();
		if (!this.next()) return 0;
		return this.CampoAsLong("cantidad").longValue();
	}

	@Override
	public String selectSum(String zCampo) throws Exception {
		QueryInit();
//		sSQL="select sum("+zCampo+") "+"suma "+buildFrom()+SPACE+this.Where();

		sSQL="select sum("+zCampo+") as suma from (";
		if (oDato.getStructure().getSQL()!=null)
			sSQL+=oDato.getStructure().getSQL();
		else
			sSQL+=this.ArmarSelect();
		sSQL+=") s";
		
		
		QueryOpen();
		if (!this.next()) return "0";
		String res=CampoAsStr("suma");
		if (res==null) return "0";
		return res;
	}

	/**
	 * Armo el Select de una sentencia SQL segun los campos seteados
	 */
	public String ArmarSelect() throws Exception {
		StringBuffer sBuffer=new StringBuffer(256);
		boolean bFirst=true;
		boolean bFirstOrderBy=true;

		sBuffer.append(SELECT);
		sBuffer.append(getDistinct());
		
		if (!oDato.getStructure().hasFields()) {
			sBuffer.append(getTop());
		  sBuffer.append(oDato.getStructure().getTable());
			sBuffer.append(DOT_STAR);
		} else {
			Iterator<RField> oIt=oDato.getFields().iterator();
			while (oIt.hasNext()) {
				RField oCampo=oIt.next();
				
				if (bFirst) bFirst=false;
				else sBuffer.append(COMMA);

				if ( oCampo.hasTable()) {
					sBuffer.append(oCampo.GetTabla() + ".");
				}

				sBuffer.append(oCampo.GetCampo());

				if (oCampo.hasRename())
					sBuffer.append(" as "+oCampo.GetRename());

			}
		}

		sBuffer.append(this.buildFrom());
		sBuffer.append(this.Where());
		sBuffer.append(this.GroupBy());
		sBuffer.append(this.Having());


		// armo el Order by del Select
		if (oDato.getStructure().hasCorteControl()) {
			JList<ROrderBy> oEnum=oDato.getCorteControl();
			bFirst=true;
			Iterator<ROrderBy> oIt=oEnum.iterator();
			while (oIt.hasNext()) {
				ROrderBy oOrder=oIt.next();
				if (bFirst&&bFirstOrderBy) {
					bFirst=false;
					bFirstOrderBy=false;
					sBuffer.append(" order by ");
				} else {
					sBuffer.append(COMMA);
				}
				
				if ( oOrder.GetTabla()!=null && !oOrder.GetTabla().equals("")) 
					sBuffer.append( oOrder.GetTabla() + ".");

				sBuffer.append(oOrder.GetCampo());
				sBuffer.append(SPACE);
				sBuffer.append(oOrder.GetOrden());
			}
		}
		if (oDato.getStructure().hasOrderBy()) {
			JList<ROrderBy> oEnum=oDato.getOrderBy();
			bFirst=true;
			Iterator<ROrderBy> oIt=oEnum.iterator();
			while (oIt.hasNext()) {
				ROrderBy oOrder=oIt.next();
				if (bFirst&&bFirstOrderBy) {
					bFirst=false;
					bFirstOrderBy=false;
					sBuffer.append(" order by ");
				} else {
					sBuffer.append(COMMA);
				}
				
				if ( oOrder.GetTabla()!=null && !oOrder.GetTabla().equals("")) 
					sBuffer.append( oOrder.GetTabla() + ".");

				sBuffer.append(oOrder.GetCampo());
				sBuffer.append(SPACE);
				sBuffer.append(oOrder.GetOrden());
			}
		}

		// armo el Order by Fijo del Select
		if (oDato.getStructure().hasFixedOrderBy()) {
			JList<RFixedOrderBy> oEnum=oDato.getFixedOrderBy();
			bFirst=true;
			Iterator<RFixedOrderBy> oIt=oEnum.iterator();
			while (oIt.hasNext()) {
				RFixedOrderBy oOrder=oIt.next();
				if (bFirst&&bFirstOrderBy) {
					bFirst=false;
					bFirstOrderBy=false;
					sBuffer.append(" order by ");
				} else {
					sBuffer.append(COMMA);
				}
				sBuffer.append(oOrder.GetOrder());
			}
		}

		return sBuffer.toString();

	}

	// -------------------------------------------------------------------------- //
	// Armo el delete de una sentencia SQL segun los campos seteados
	// -------------------------------------------------------------------------- //
	public String ArmarDelete() throws Exception {
		String sAux;
		sAux=DELETE_FROM+oDato.getStructure().getTable()+Where();
		return sAux;
	}

/*	public String ArmarUpdateEstablecidos() throws Exception {
		StringBuffer sBuffer=new StringBuffer(512);
		String sTipo;
		// boolean saltear = false;
		boolean bFirst=true;

		sBuffer.append(UPDATE);
		sBuffer.append(oDato.getStructure().getTable());
		sBuffer.append(SET);

		if (!oDato.getStructure().hasFields()) throw new JNoFieldException();

		JList<RField> aCampos=oDato.getFields();
		Iterator<RField> oIt=aCampos.iterator();
		while (oIt.hasNext()) {
			RField oCampo=oIt.next();

			// if (oCampo.GetAuto() && this.oBase.ifSQLServer())
			// continue;
			if (bFirst) bFirst=false;
			else {
				sBuffer.append(COMMA);
				// }
			}

			// if ( saltear ) continue;

			sBuffer.append(oCampo.GetCampo());
			sBuffer.append(EQUAL);

			sTipo=oCampo.GetTipo();

			sBuffer.append(ArmarDato(oDato.getStructure().getTable(), sTipo, oCampo.GetValor()));

		}

		sBuffer.append(Where());

		return sBuffer.toString();
	}*/

	// -------------------------------------------------------------------------- //
	// Armo el update de una sentencia SQL segun los campos seteados
	// -------------------------------------------------------------------------- //
	public String ArmarUpdate() throws Exception {
		StringBuffer sBuffer=new StringBuffer(512);
		String sTipo;
		// boolean saltear = false;
		boolean bFirst=true;
		
		if (!oDato.getStructure().hasFields())  
//			throw new JNoFieldException();
			return null;

		sBuffer.append(UPDATE);
		sBuffer.append(oDato.getStructure().getTable());
		sBuffer.append(SET);


		JList<RField> aCampos=oDato.getFields();
		Iterator<RField> oIt=aCampos.iterator();
		while (oIt.hasNext()) {
			RField oCampo=oIt.next();

			/*
			 * if (oCampo.GetAuto() && this.oBase.ifSQLServer()) continue;
			 */

			if (bFirst) bFirst=false;
			else {
				/*
				 * if ( saltear ) saltear = false; else {
				 */
				sBuffer.append(COMMA);
				// }
			}

			// if ( saltear ) continue;

			sBuffer.append(oCampo.GetCampo());
			sBuffer.append(EQUAL);

			sTipo=oCampo.GetTipo();

			sBuffer.append(ArmarDato(oDato.getStructure().getTable(), sTipo, oCampo.GetValor()));

		}

		sBuffer.append(Where());

		return sBuffer.toString();

	}

	public String ArmarUpdateCatalog() throws Exception {
		return null;
	}

	/**
	 * Armo el insert de una sentencia SQL segun los campos seteados
	 */
	public String ArmarInsert() throws Exception {
		StringBuffer sAux=new StringBuffer(512);
		StringBuffer sCampos=new StringBuffer(256);
		StringBuffer sValores=new StringBuffer(256);
		String sValor;
		boolean bFirst=true;

		sAux.append(INSERT_INTO);
		sAux.append(oDato.getStructure().getTable());
		sAux.append(SPACE);

		if (!oDato.getStructure().hasFields()) JExcepcion.SendError("Debe especificar algun campo");
		JList<RField> oEnum=oDato.getFields();
		Iterator<RField> oIt=oEnum.iterator();
		while (oIt.hasNext()) {
			RField oCampo=oIt.next();

			// if (oCampo.GetAuto() && this.oBase.ifSQLServer())
			if (oCampo.ifAutonumerico()) {
				continue;
			}

			if (bFirst) bFirst=false;
			else {
				sCampos.append(COMMA);
				sValores.append(COMMA);
			}

			sCampos.append(oCampo.GetCampo());

			// if (oCampo.GetAuto() && this.oBase.ifOracle())
			if (oCampo.ifAutonumerico()) { // ) && this.oBase.ifOracle())
				sValor=oCampo.GetTabla()+"_"+oCampo.GetCampo()+".nextval";
			} else {
				sValor=oCampo.GetValor();
			}

			sValores.append(ArmarDato(oCampo.GetTabla(), oCampo.GetTipo(), sValor));
		}

		sAux.append("(");
		sAux.append(sCampos);
		sAux.append(") values (");
		sAux.append(sValores);
		sAux.append(")");

		return sAux.toString();
	}
	public String buildWith() throws Exception {
		return "";
	}
	public String buildFrom() throws Exception {
		StringBuffer sBuffer=new StringBuffer(128);
		sBuffer.append(" from ");
		sBuffer.append(oDato.getStructure().getTableForFrom());

		/*
		 * if (oDato.ifLock() && this.oBase.ifSQLServer()) { sAux = sAux + " with (ROWLOCK, UPDLOCK) "; }
		 */

		// Tablas vinculadas con joins
		if (oDato.getStructure().hasJoins()) {
			Iterator<RJoins> oIt=oDato.getJoins().iterator();
			while (oIt.hasNext()) { // primero los inner joins
				RJoins oJoin=oIt.next();
				if (!oJoin.isInnerJoin()) continue;
				sBuffer.append(oJoin.GetTablaJoin());
				if (oJoin.GetAliasJoin()==null) continue;
				sBuffer.append(" "+oJoin.GetAliasJoin());
			}
			oIt=oDato.getJoins().iterator();
			while (oIt.hasNext()) { // primero los inner joins
				RJoins oJoin=oIt.next();
				if (oJoin.isInnerJoin()) continue;
				sBuffer.append(COMMA);
				sBuffer.append(oJoin.GetTablaJoin());
				if (oJoin.GetAliasJoin()==null) continue;
				sBuffer.append(" "+oJoin.GetAliasJoin());
			}
		}
		return sBuffer.toString();
	}

	// -------------------------------------------------------------------------- //
	// Armo el Where de una sentencia SQL segun los filtros seteados //
	// -------------------------------------------------------------------------- //
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
					  	sBuffer.append(fintervalo(field, ArmarDato(oDato.getStructure().getTable(), JObject.JDATETIME, valueFrom), ArmarDato(oDato.getStructure().getTable(), JObject.JDATETIME, valueTo)));
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
					  	sBuffer.append(fintervalo(field, ArmarDato(oDato.getStructure().getTable(), JObject.JDATE, valueFrom), ArmarDato(oDato.getStructure().getTable(), JObject.JDATE, valueTo)));
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

	public String Having() throws Exception {
		StringBuffer sBuffer=new StringBuffer(128);
		String sOper;
		String sTipo;
		boolean bFirst=true;

		sBuffer.append(SPACE);

		if (oDato.getStructure().hasFixedHaving()) {
			JList<RFixedHaving> oEnum=oDato.getFixedHaving();
			Iterator<RFixedHaving> oIt=oEnum.iterator();
			while (oIt.hasNext()) {
				RFixedHaving oFiltroFijo=oIt.next();
				String sFiltro= oFiltroFijo.getFiltro().trim();
				if (sFiltro.equals("")) continue;
				if (!bFirst) {
					if (sFiltro.toLowerCase().startsWith("having")) {
						sFiltro=AND+sFiltro.substring(5);
					}
					if (!sFiltro.toLowerCase().trim().startsWith("and")&&!sFiltro.toLowerCase().trim().startsWith("or")) {
						sFiltro=AND+sFiltro;
					}
				} else {
					if (sFiltro.toLowerCase().indexOf("having")==-1) {
						sFiltro=" having "+sFiltro;
					}
				}
				sBuffer.append(SPACE);
				sBuffer.append(sFiltro);
				sBuffer.append(SPACE);
				bFirst=false;
			}
		}
		if (oDato.getStructure().hasHaving()) {
			JList<RHaving> eEnum=oDato.getHaving();
			Iterator<RHaving> oIt=eEnum.iterator();

			while (oIt.hasNext()) {
				RHaving oFiltro=oIt.next();
				if (oFiltro.isVirtual()) continue;
				if (bFirst) {
					bFirst=false;
					sBuffer.append(" having ");
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
	
	
						if (sOper.equalsIgnoreCase("IN") || sOper.equalsIgnoreCase("NOT IN")) sBuffer.append(oFiltro.getValue());
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

	
	protected void armarCampoEnLike(StringBuffer sBuffer, RFilter oFiltro, String sOper) {
		String field = "";
		if (!oFiltro.hasTable()) 
			field = oFiltro.getField();
		else 
			field = oFiltro.getTable()+DOT+oFiltro.getField();
		
		field = changeFieldValueInLike(field, sOper);
		
		sBuffer.append(field);	
	}
	protected void armarCampoEnLike(StringBuffer sBuffer, RHaving oFiltro, String sOper) {
		String field = "";
		if (!oFiltro.hasTable()) 
			field = oFiltro.getField();
		else 
			field = oFiltro.getTable()+DOT+oFiltro.getField();
		
		field = changeFieldValueInLike(field, sOper);
		
		sBuffer.append(field);	
	}
	
	protected String changeFieldValueInLike(String field, String sOper) {
		return field;
	}

	
	protected void armarLike(StringBuffer sBuffer, String sTipo, String value)
			throws Exception {
		sBuffer.append(ArmarDato(oDato.getStructure().getTable(), sTipo, "%"+value+"%" ));
	}

	public void ilike(StringBuffer sBuffer,String sTipo,String value) throws Exception {
		sBuffer.append(SPACE);
		sBuffer.append("like");
		sBuffer.append(SPACE);

		sBuffer.append(ArmarDato(oDato.getStructure().getTable(), sTipo, "%"+value.toLowerCase()+"%"));
	}
	
	public String GroupBy() throws Exception {
		StringBuffer sBuffer=new StringBuffer(128);
		// Armo el Group by del Select
		if (!oDato.getStructure().hasGroupBy()) return"";
		JList<RGroupBy> oEnum=oDato.getGroupBy();
		boolean bFirst=true;
		Iterator<RGroupBy> oIt=oEnum.iterator();
		while (oIt.hasNext()) {
			RGroupBy oGroup=oIt.next();
			if (bFirst) {
				bFirst=false;
				sBuffer.append(" group by ");
			} else {
				sBuffer.append(COMMA);
			}
			if ( oGroup.hasTable()) { 
				sBuffer.append(oGroup.GetTabla());
				sBuffer.append(DOT);
			}
			sBuffer.append(oGroup.GetCampo());
		}
		return sBuffer.toString();
	}



	// -------------------------------------------------------------------------- //
	// Voy armando el String
	// -------------------------------------------------------------------------- //
	public  String ArmarDato(String zTabla, String zTipo, String zValor) throws Exception {
		String sTipo;

		if (zValor==null) return "null";
		sTipo=zTipo;

		if (zValor.trim().equalsIgnoreCase("null")||zValor.trim().length()==0) {
			return "NULL";
		}

		if (sTipo.equals(JObject.JSTRING)) return COMILLA+JTools.escapeQuote(zValor)+COMILLA;
		else if (sTipo.equals(JObject.JINTEGER)) return zValor;
		else if (sTipo.equals(JObject.JFLOAT)) return zValor;
		else if (sTipo.equals(JObject.JCURRENCY)) return zValor;
		else if (sTipo.equals(JObject.JLONG)) return zValor;
		else if (sTipo.equals(JObject.JPASSWORD)) return COMILLA+JTools.escapeQuote(JTools.StringToPassword(zValor))+"' ";
		else if (sTipo.equals("campo")) return zTabla+DOT+zValor;
		else if (sTipo.equals(JObject.JDATE)) return COMILLA+zValor+COMILLA;
		else if (sTipo.equals(JObject.JHOUR)) return COMILLA+JTools.HourToString(zValor)+COMILLA;
		else if (sTipo.equals(JObject.JBOOLEAN)) return COMILLA+zValor+COMILLA;
		else if (sTipo.equals(JObject.JHTML)) return COMILLA+zValor+COMILLA;
		else if (sTipo.equals(JObject.JMULTIPLE)) return COMILLA+zValor+COMILLA;
		else if (sTipo.equals(JObject.JCOLOUR)) return COMILLA+zValor+COMILLA;
		else if (sTipo.equals(JObject.JIMAGE)) return COMILLA+zValor+COMILLA;
		else if (sTipo.equals(JObject.JDATETIME)) return COMILLA+zValor+COMILLA;
		else if (sTipo.equals(JObject.JGEOPOSITION)) return COMILLA+zValor+COMILLA;
		else if (sTipo.equals(JObject.JBLOB)) return COMILLA+JTools.escapeQuote(zValor)+COMILLA;
		return "";

	}


	@Override
	public long GetIdentity(String zCampo) throws Exception {
		/*
		 * if (oBase.ifOracle()) { QueryInit(); String sCampo = this.oDato.GetrBase().GetTabla() + "_" + zCampo + ".currval"; sSQL = "Select " + sCampo + " sec from dual"; QueryOpen(); this.Next(); return this.CampoAsLong("sec").longValue(); } else if (oBase.ifSQLServer()) { QueryInit(); sSQL = "Select @@identity "; QueryOpen(); this.Next(); return this.CampoAsLong("").longValue(); } else JExcepcion.SendError("Tipo de Base de Datos Invalido");
		 */
		return 0;
	}

	public String getVirtualBDsField(String zFieldToEvaluate, JRecords<BizVirtual> vBDs, String zElse) throws Exception {
		String sConditionalString="";
		// String sConditionalStringEnd = "";
		/*
		 * if (JBDatos.GetBases().GetBaseDefault().ifSQLServer()) { sConditionalString = " case " + zFieldToEvaluate; vBDs.FirstRecord(); while (vBDs.NextRecord()) { BizVirtual oVirtual = (BizVirtual)vBDs.GetRecord(); sConditionalString += " when '" + oVirtual.getValor() + "' then '" + oVirtual.getDescrip() + "' "; } sConditionalString += " else " + zElse + " end "; } else { vBDs.FirstRecord(); while (vBDs.NextRecord()) { BizVirtual oVirtual = (BizVirtual)vBDs.GetRecord(); sConditionalString = " decode(" + zFieldToEvaluate + "," + oVirtual.getValor() + "," + oVirtual.getDescrip() + ","; sConditionalStringEnd += ")"; } if (!sConditionalString.equals("")) sConditionalString += zElse + sConditionalStringEnd; }
		 */
		return sConditionalString;
	}

	protected String getConditionalFieldAux(String zFieldToEvaluate, JList<String> vCondition, String zTrue, String zFalse) throws Exception {
		return "";
	}

	protected String getStringValueAux(String zField, int zSize) throws Exception {
		return "";
	}

	protected String getIgnoreLocksHintAux() throws Exception {
		return "";
	}

	protected String getDirtyReadHintAux() throws Exception {
		return "";
	}
	
	protected String getReadUncommitedHintAux() throws Exception {
		return "";
	}

	static public String getStringValue(String zField, int zSize) throws Exception {
		JBaseJDBC oDatabase=(JBaseJDBC) JBDatos.GetBases().getPrivateCurrentDatabase();
		JRegSQL oRegistro=oDatabase.createRowImpl();
		return oRegistro.getStringValueAux(zField, zSize);
	}

	static public String getConditionalField(String zFieldToEvaluate, JList<String> vCondition, String zTrue, String zFalse) throws Exception {
		JBaseJDBC oDatabase=(JBaseJDBC) JBDatos.GetBases().getPrivateCurrentDatabase();
		JRegSQL oRegistro=oDatabase.createRowImpl();
		return oRegistro.getConditionalFieldAux(zFieldToEvaluate, vCondition, zTrue, zFalse);
	}

	static public String getConditionalField(String zFieldToEvaluate, String zCondition, String zTrue, String zFalse) throws Exception {
		JList<String> oCondition=JCollectionFactory.createList();
		oCondition.addElement(zCondition);
		return getConditionalField(zFieldToEvaluate, oCondition, zTrue, zFalse);
	}

	static public String getIgnoreLocksHint() throws Exception {
		JBaseJDBC oDatabase=(JBaseJDBC) JBDatos.GetBases().getPrivateCurrentDatabase();
		JRegSQL oRegistro=oDatabase.createRowImpl();
		return oRegistro.getIgnoreLocksHintAux();
	}

	static public String getDirtyReadHint() throws Exception {
		JBaseJDBC oDatabase=(JBaseJDBC) JBDatos.GetBases().getPrivateCurrentDatabase();
		JRegSQL oRegistro=oDatabase.createRowImpl();
		return oRegistro.getDirtyReadHintAux();
	}

	public static String getToNumber(String zField) throws Exception {
		JBaseJDBC oImplementation=(JBaseJDBC) JBDatos.GetBases().getPrivateCurrentDatabase();
		return oImplementation.createRowImpl().toNumber(zField);
	}
	
	public static String getDateDiff(String field, String d2) throws Exception {
		JBaseJDBC oImplementation=(JBaseJDBC) JBDatos.GetBases().getPrivateCurrentDatabase();
		return oImplementation.createRowImpl().dateDiff(field, d2);
	}
	
	

	public void executeQuery(String zSQL) throws Exception {
		QueryInit();
		SetSQL(zSQL);
		QueryOpen();
	}

	@Override
	public JMap<String, String> ExecuteQueryOneRow(JList<String> zReturnFieldList, String zSQL) throws Exception {
		QueryInit();
		SetSQL(zSQL);
		QueryOpen();
		if (!this.next()) return null;
		return getFieldList(zReturnFieldList);
	}

	@Override
	public boolean ExecuteQueryLookForExistence(String zSQL) throws Exception {
		QueryInit();
		SetSQL(zSQL);
		QueryOpen();
		if (!this.next()) return false;
		return true;
	}

	private JMap<String, String> getFieldList(JList<String> zReturnFieldList) throws Exception {
		JMap<String, String> oReturnList=JCollectionFactory.createMap();
		Iterator<String> oIter=zReturnFieldList.iterator();
		while (oIter.hasNext()) {
			String sField=oIter.next();
			if (CampoAsStr(sField)!=null) oReturnList.addElement(sField, CampoAsStr(sField));
			else oReturnList.addElement(sField, "0");
		}
		return oReturnList;
	}

	static public String GetConcatenateOperator() throws Exception {
		JBaseJDBC oDatabase=(JBaseJDBC) JBDatos.GetBases().getPrivateCurrentDatabase();
		JRegSQL oRegistro=oDatabase.createRowImpl();
		return oRegistro.getConcatenateOperator();
	}

	protected String getConcatenateOperator() throws Exception {
		return "";
	}

	protected String getLeftStringAux(String zField, int zSize) throws Exception {
		return "";
	}

	static public String getLeftString(String zField, int zSize) throws Exception {
		JBaseJDBC oDatabase=(JBaseJDBC) JBDatos.GetBases().getPrivateCurrentDatabase();
		JRegSQL oRegistro=oDatabase.createRowImpl();
		return oRegistro.getLeftStringAux(zField, zSize);
	}

	static public String GetConditionalFieldIsNull(String zFieldToEvaluate, String zIsNull) throws Exception {
		JBaseJDBC oDatabase=(JBaseJDBC) JBDatos.GetBases().getPrivateCurrentDatabase();
		JRegSQL oRegistro=oDatabase.createRowImpl();
		return oRegistro.getConditionalFieldIsNull(zFieldToEvaluate, zIsNull);
	}

	protected String getConditionalFieldIsNull(String zFieldToEvaluate, String zIsNull) throws Exception {
		// IF el campo NOT NULL: devuelve el campo
		// ELSE: devuelve zTrue
		return getConditionalField(zFieldToEvaluate, "null", zFieldToEvaluate, zIsNull);
	}

	static public String GetBackupCommand() throws Exception {
		JBaseJDBC oDatabase=(JBaseJDBC) JBDatos.GetBases().getPrivateCurrentDatabase();
		JRegSQL oRegistro=oDatabase.createRowImpl();
		return oRegistro.getBackupCommand();
	}

	protected String getBackupCommand() throws Exception {
		return "";
	}

	/*
	 * Función implementada para identificar la implementación de la base de datos, en algunos casos es necesario identificarla para determinar la sintaxis de la sentencia SQL a ejecutar.
	 */

	/*
	 * static public String GetDBImplementation() throws Exception { JBaseJDBC oDatabase = (JBaseJDBC)JBDatos.GetBases().GetBaseDefault(); JRegSQL oRegistro = oDatabase.createRowImpl(); return oRegistro.getDBImplementation(); } protected String getDBImplementation() throws Exception { return ""; }
	 */

	public static String serializaIN(JMap<String, String> map) throws Exception {
		return serializaIN(map.getKeyIterator());
	}

	public static String serializaIN(JList<String> list) throws Exception {
		return serializaIN(list.getIterator());
	}

	private static String serializaIN(JIterator<String> iter) throws Exception {
		boolean first=true;
		StringBuffer s=new StringBuffer();
		s.append("(");
		while (iter.hasMoreElements()) {
			if (!first) s.append(",");
			first=false;
			s.append("'").append(iter.nextElement()).append("'");
		}
		s.append(")");
		return s.toString();
	}

	public boolean isUpdatingCatalog() {
		return updatingCatalog;
	}

	public void setUpdatingCatalog(boolean updatingCatalog) {
		this.updatingCatalog=updatingCatalog;
	}
	
	
	@Override
	public String selectAvg(String zCampo) throws Exception {
		QueryInit();
		sSQL="select avg("+zCampo+") "+" promedio "+buildFrom()+SPACE+this.Where();
		if (!this.GroupBy().equals(""))
			sSQL+=this.GroupBy();
		QueryOpen();
		if (!this.next()) return "0";
		String res=CampoAsStr("promedio");
		if (res==null) return "0";
		return res;
	}

	public  String ArmarIn(String zTabla, String zTipo, String zValor) throws Exception {
		if (zValor.trim().startsWith("(")) return zValor;
		StringTokenizer toks = new StringTokenizer(zValor,",");
		String s = "";
		while (toks.hasMoreTokens()) {
			String ss = toks.nextToken();
			s+=(s.equals("")?"":",")+ArmarDato(zTabla, zTipo, ss);
		}
		return "("+s+")";
	}	
	
}
