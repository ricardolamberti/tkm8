package pss.core.data.interfaces.structure;

import java.io.Serializable;
import java.util.Iterator;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.tools.XML.JXMLElementFactory;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;

public class RStructure implements Serializable {

	private String sTabla;
	private String sTablaTemporal;
	private Class<JBaseRegistro> sInterfase;
	private String sSQL;
	private String lastSQL;
	private boolean bLock;
	private boolean bIgnoreLocks;
	private boolean bDirtyRead;
	private boolean bNoLock;
	private boolean bWithoutException;
	private String sIndexHint = "";
	private JList<RField> vFields;
	private JList<RFilter> vFilters;
	private JList<RFixedFilter> vFixedFilters;
	private JList<RHaving> vHaving;
	private JList<RFixedHaving> vFixedHaving;
	private JList<RFixedOrderBy> vFixedOrderBy;
	private JList<RJoins> vJoins;
	private JList<ROrderBy> vOrderBy;
	private JList<ROrderBy> vCorteControl;
	private JList<RGroupBy> vGroupBy;
	private RCatalog catalog = null;

	public RStructure() {
	}

	public boolean hasCorteControl() throws Exception {
		return vCorteControl != null && vCorteControl.size()!=0;
	}
	public boolean hasOrderBy() throws Exception {
		return vOrderBy != null;
	}

	public boolean hasGroupBy() throws Exception {
		return vGroupBy != null;
	}

	public boolean hasJoins() throws Exception {
		return vJoins != null;
	}

	public boolean hasFields() throws Exception {
		if (getFields().isEmpty()) return false;
		return true;
	}

	public boolean hasFilters() throws Exception {
		return vFilters != null;
	}

	public boolean hasFixedFilters() throws Exception {
		return vFixedFilters != null;
	}

	public boolean hasHaving() throws Exception {
		return vHaving != null;
	}

	public boolean hasFixedHaving() throws Exception {
		return vFixedHaving != null;
	}

	public boolean hasFixedOrderBy() throws Exception {
		return vFixedOrderBy != null;
	}

	public JList<RField> getFields() {
		if (vFields==null) vFields=JCollectionFactory.createList();
		return vFields;
	}

	public JList<RJoins> getJoins() {
		if (vJoins==null) 
			vJoins=JCollectionFactory.createList();
		return vJoins;
	}

	public JList<RGroupBy> getGroupBy() {
		if (vGroupBy==null) vGroupBy=JCollectionFactory.createList();
		return vGroupBy;
	}

	public JList<ROrderBy> getOrderBy() {
		if (vOrderBy==null) vOrderBy=JCollectionFactory.createList();
		return vOrderBy;
	}

	public JList<ROrderBy> getCorteControl() {
		if (vCorteControl==null) vCorteControl=JCollectionFactory.createList();
		return vCorteControl;
	}

	public JList<RFilter> getFilters() {
		if (vFilters==null) vFilters=JCollectionFactory.createList();
		return vFilters;
	}
	
	public void setFilters(JList<RFilter> f) {
		vFilters=f;
	}

	public JList<RFixedFilter> getFixedFilters() {
		if (vFixedFilters==null) vFixedFilters=JCollectionFactory.createList();
		return vFixedFilters;
	}

	public JList<RHaving> getHaving() {
		if (vHaving==null) vHaving=JCollectionFactory.createList();
		return vHaving;
	}

	public JList<RFixedHaving> getFixedHaving() {
		if (vFixedHaving==null) vFixedHaving=JCollectionFactory.createList();
		return vFixedHaving;
	}

	public JList<RFixedOrderBy> getFixedOrderBy() {
		if (vFixedOrderBy==null) vFixedOrderBy=JCollectionFactory.createList();
		return vFixedOrderBy;
	}

	public String getSQL() {
		return sSQL;
	}

	public String getLastSQL() {
		return lastSQL;
	}

	public void setLastSQL(String value) {
		lastSQL = value;
	}

	public String getTable() {
		return sTabla;
	}

	public String getTableForFrom() {
		if (sTablaTemporal!=null) return sTablaTemporal;
		return sTabla;
	}
	public String getTableTemporal() {
		return sTablaTemporal;
	}
	public boolean withLock() {
		return bLock;
	}

	public boolean withIgnoreLocks() {
		return bIgnoreLocks;
	}

	public boolean withDirtyRead() {
		return bDirtyRead;
	}

	public boolean hasIndexHint() {
		return !sIndexHint.equals("");
	}

	public String getIndexHint() throws Exception {
		return sIndexHint;
	}

	public boolean ifNoLock() {
		return bNoLock;
	}

	public boolean withoutException() {
		return bWithoutException;
	}

	public Class<JBaseRegistro> getInterfase() {
		return sInterfase;
	}

	public void setTable(String zValue) {
		sTabla = zValue;
	}

	public void setTableTemporal(String zValue) {
		sTablaTemporal = zValue;
	}
	public void setSQL(String zValue) {
		sSQL = zValue;
	}

	public void clearSQL() {
		sSQL = null;
	}

	public void setWithLock(boolean zValue) {
		bLock = zValue;
	}

	public void setWithIgnoreLocks(boolean zValue) {
		bIgnoreLocks = zValue;
	}

	public void setWithDirtyRead(boolean zValue) {
		bDirtyRead = zValue;
	}

	public void SetIndexHint(String zValue) {
		sIndexHint = zValue;
	}

	public void setNoLock(boolean zValue) {
		bNoLock = zValue;
	}

	public void setInterfase(Class<JBaseRegistro> zValue) {
		sInterfase = zValue;
	}

	public void setWithoutException(boolean zValue) {
		bWithoutException = zValue;
	}

	public void clearAll() throws Exception {
		clearJoins();
		clearGroupBy();
		clearOrderBy();
		clearFields();
		clearFilters();
	}
	
	public boolean hasDynamicFilters(boolean withDefault) throws Exception {
		if (this.hasFilters()) {
			JIterator<RFilter> iter = this.getFilters().getIterator();
			while (iter.hasMoreElements()) {
				RFilter filter = iter.nextElement();
				if (!filter.isDynamic())
					continue;
				if (!withDefault && !filter.isUserdefined())
					continue;
				if (filter.hasValue()) 
					return true;
			}
		}
		return false;
	}

	public void clearDynamicFilters() throws Exception {
		if (this.hasFilters()) {
			JIterator<RFilter> iter = this.getFilters().getIterator();
			while (iter.hasMoreElements()) {
				RFilter filter = iter.nextElement();
				if (!filter.isDynamic())
					continue;
				iter.remove();
			}
		}
		if (this.hasFixedFilters()) {
			JIterator<RFixedFilter> iter = this.getFixedFilters().getIterator();
			while (iter.hasMoreElements()) {
				RFixedFilter filter = iter.nextElement();
				if (!filter.isDynamic())
					continue;
				iter.remove();
			}
		}
		if (this.hasHaving()) {
			JIterator<RHaving> iter = this.getHaving().getIterator();
			while (iter.hasMoreElements()) {
				RHaving filter = iter.nextElement();
				if (!filter.isDynamic())
					continue;
				iter.remove();
			}
		}
		if (this.hasFixedHaving()) {
			JIterator<RFixedHaving> iter = this.getFixedHaving().getIterator();
			while (iter.hasMoreElements()) {
				RFixedHaving filter = iter.nextElement();
				if (!filter.isDynamic())
					continue;
				iter.remove();
			}
		}
		if (this.hasJoins()) {
			JIterator<RJoins> iter = this.getJoins().getIterator();
			while (iter.hasMoreElements()) {
				RJoins j = iter.nextElement();
				if (!j.isDynamic())
					continue;
				iter.remove();
			}
		}
		if (this.hasOrderBy()) {
			JIterator<ROrderBy> iter = this.getOrderBy().getIterator();
			while (iter.hasMoreElements()) {
				ROrderBy order = iter.nextElement();
				if (!order.isDynamic())
					continue;
				iter.remove();
			}
		}
		if (this.hasCorteControl()) {
			JIterator<ROrderBy> iter = this.getCorteControl().getIterator();
			while (iter.hasMoreElements()) {
				ROrderBy order = iter.nextElement();
				if (!order.isDynamic())
					continue;
				iter.remove();
			}
		}
	}

	public void clearOrderBy() throws Exception {
		getCorteControl().removeAllElements();
		getOrderBy().removeAllElements();
		getFixedOrderBy().removeAllElements();
	}

	public void clearGroupBy() throws Exception {
		getGroupBy().removeAllElements();
		getFixedOrderBy().removeAllElements();
		vGroupBy=null;
		vFixedOrderBy=null;
	}

	public void clearJoins() throws Exception {
		getJoins().removeAllElements();
		vJoins=null;
	}

	public void clearInnerJoins() throws Exception {
		if (!this.hasJoins()) return;
		JIterator<RJoins> iter = this.getJoins().getIterator();
		while (iter.hasMoreElements()) {
			RJoins j = iter.nextElement();
			if (!j.isInnerJoin())
				continue;
			iter.remove();
		}
	}

	public void clearFields() throws Exception {
		getFields().removeAllElements();
		vFields=null;
	}

	public void clearFilters() throws Exception {
		getFilters().removeAllElements();
		getFixedFilters().removeAllElements();
		getJoins().removeAllElements();
		vFilters=null;
		vFixedFilters=null;
		vJoins=null;
		
	}

	public void clearBasicFilters() throws Exception {
		getFilters().removeAllElements();
		vFilters=null;
	}
	public void clearFixedFilters() throws Exception {
		getFixedFilters().removeAllElements();
		vFixedFilters=null;
	}
	public void clearFixedHaving() throws Exception {
		getFixedHaving().removeAllElements();
		vFixedHaving=null;
	}
	public RField addField(String zTabla, String zCampo, String zValor, String zObjectType, boolean zAuto, boolean zOnlySelects) throws Exception {
		return addField(zTabla, zCampo, zValor, zObjectType, zAuto, zOnlySelects,null);
	}
	public RField addField(String zTabla, String zCampo, String zValor, String zObjectType, boolean zAuto, boolean zOnlySelects, String zRename) throws Exception {
		RField campo = new RField();
		campo.sTabla = zTabla;
		campo.sCampo = zCampo;
		campo.sRename = zRename;
		campo.sValor = zValor;
		campo.sTipo = zObjectType;
		campo.bAuto = zAuto;
		campo.bOnlySelects = zOnlySelects;
		getFields().addElement(campo);
		return campo;
	}

	public RFilter addFilter(String zTable, String zCampo, String zOper, String zValor, String zTipo, String zRel, String zAgrup) throws Exception {
		RFilter filtro = new RFilter();
		filtro.sTabla = zTable;
		filtro.sCampo = zCampo;
		filtro.sOperador = zOper;
		filtro.sTipo = zTipo;
		filtro.sValor = zValor;
		filtro.sRelacion = zRel;
		filtro.sAgrup = zAgrup;
		this.addFilter(filtro);
//		if (filtro.sCampo.equals("TUR_PNR_BOLETO_CodigoAerolinea"))
//			PssLogger.logDebug("check point");
		return filtro;
	}

	public RFilter addFilter(String zTable, String zCampo, String zOper, Serializable zValor, String zTipo, String zRel, String zAgrup) throws Exception {
		RFilter filtro = new RFilter();
		filtro.sTabla = zTable;
		filtro.sCampo = zCampo;
		filtro.sOperador = zOper;
		filtro.sTipo = zTipo;
		filtro.sValor = zValor;
		filtro.sRelacion = zRel;
		filtro.sAgrup = zAgrup;
		this.addFilter(filtro);
//		if (filtro.sCampo.equals("TUR_PNR_BOLETO_CodigoAerolinea"))
//			PssLogger.logDebug("check point");
		return filtro;
	}
	public RHaving addHaving(String zTable, String zCampo, String zOper, String zValor, String zTipo, String zRel, String zAgrup) throws Exception {
		RHaving filtro = new RHaving();
		filtro.sTabla = zTable;
		filtro.sCampo = zCampo;
		filtro.sOperador = zOper;
		filtro.sTipo = zTipo;
		filtro.sValor = zValor;
		filtro.sRelacion = zRel;
		filtro.sAgrup = zAgrup;
		this.addHaving(filtro);
		return filtro;
	}
	public void addFilter(RFilter filter) {
		getFilters().addElement(filter);
	}

	public void addHaving(RHaving filter) {
		getHaving().addElement(filter);
	}

	public RFixedFilter addFixedFilter(String zFiltro) {
		return addFixedFilter(zFiltro,null);
	}
	public RFixedFilter addFixedFilter(String zFiltro,String tablaAsoc) {
		if (zFiltro==null) return null;
		RFixedFilter oFiltroFijo = new RFixedFilter();
		oFiltroFijo.setFixedFilter(zFiltro);
		getFixedFilters().addElement(oFiltroFijo);
		return oFiltroFijo;
	}

	public RFixedHaving addFixedHaving(String zFiltro) {
		if (zFiltro==null) return null;
		RFixedHaving oFiltroFijo = new RFixedHaving();
		oFiltroFijo.setFixedFilter(zFiltro);
		getFixedHaving().addElement(oFiltroFijo);
		return oFiltroFijo;
	}

	public void addFixedOrderBy(String zOrder) {
		RFixedOrderBy oOrderFijo = new RFixedOrderBy();
		oOrderFijo.sOrder = zOrder;
		getFixedOrderBy().addElement(oOrderFijo);
	}

	public ROrderBy addCorteControl(ROrderBy orderBy) throws Exception {
		if (this.hasCorteControl(orderBy.GetCampo())) return this.getCorteControl(orderBy.GetCampo());
		getCorteControl().addElement(orderBy);
		removeOrderBy(orderBy.GetCampo());
		return orderBy;
	}

	public ROrderBy addCorteControl(String zTabla, String zCampo, String zOrden,IOrderByControl zControl) throws Exception {
		if (this.hasCorteControl(zCampo)) return this.getCorteControl(zCampo);
		ROrderBy orderBy = new ROrderBy();
		orderBy.sTabla = zTabla;
		orderBy.sCampo = zCampo;
		orderBy.sOrden = zOrden;
		orderBy.control= zControl;
		getCorteControl().addElement(orderBy);
		removeOrderBy(zTabla,zCampo,zOrden);
		return orderBy;
	}
	public ROrderBy addOrderBy(String zTabla, String zCampo, String zOrden) throws Exception {
		if (this.hasOrderBy(zCampo)) return this.getOrderBy(zCampo);
		ROrderBy orderBy = new ROrderBy();
		orderBy.sTabla = zTabla;
		orderBy.sCampo = zCampo;
		orderBy.sOrden = zOrden;
		getOrderBy().addElement(orderBy);
		return orderBy;
	}
	public void removeOrderBy(String zTabla, String zCampo, String zOrden) throws Exception {
		if (!this.hasOrderBy(zTabla,zCampo,zOrden)) return;
		getOrderBy().removeElement(this.getOrderBy(zTabla,zCampo,zOrden));
	}
	public void removeOrderBy(ROrderBy orderby) throws Exception {
		getOrderBy().removeElement(orderby);
	}
	
	
	public void removeOrderBy(String zCampo) throws Exception {
		if (!this.hasOrderBy(zCampo)) return;
		getOrderBy().removeElement(this.getOrderBy(zCampo));
	}
	

	public void addGroupBy(String zTabla, String zCampo) {
		RGroupBy groupby = new RGroupBy();
		groupby.sTabla = zTabla;
		groupby.sCampo = zCampo;
		getGroupBy().addElement(groupby);
	}

	public RJoins addJoin(RJoins join) throws Exception {
		getJoins().addElement(join);
		return join;
	}

	public RJoins addJoin(String zTabla, boolean bInnerJoin) throws Exception {
		this.clearJoin(zTabla);
		RJoins join = new RJoins();
		join.sTablaJoin = zTabla;
		join.bInnerJoin = bInnerJoin;
		getJoins().addElement(join);
		return join;
	}
	public RJoins addJoin(String typeJoin,String zTabla, String alias,boolean bInnerJoin,String condicion) throws Exception {
		this.clearJoin(zTabla,alias);
		RJoins join = new RJoins();
		join.sTablaJoin = zTabla;
		join.sAlias = alias;
		join.bInnerJoin = bInnerJoin;
		join.sTypeJoin = typeJoin;
		join.addCondicion(condicion);
		getJoins().addElement(join);
		return join;
	}
	public RJoins addJoin(String zTabla, String alias, boolean bInnerJoin) throws Exception {
		this.clearJoin(zTabla,alias);
		RJoins join = new RJoins();
		join.sTablaJoin = zTabla;
		join.sAlias = alias;
		join.bInnerJoin = bInnerJoin;
		getJoins().addElement(join);
		return join;
	}
	public RFilter getFiltro(String zFiltro, String operator) throws Exception {
		if (operator == null)
			return getFiltro(zFiltro);
		if (vFilters == null)
			return null;
		JList<RFilter> oEnum = vFilters;
		Iterator<RFilter> oIt = oEnum.iterator();
		while (oIt.hasNext()) {
			RFilter oFiltro = oIt.next();
			if (!(oFiltro.getField().trim().equalsIgnoreCase(zFiltro.trim()) && oFiltro.getOperator().trim().equalsIgnoreCase(operator.trim())))
				continue;
			return oFiltro;
		}
		return null;
	}
	public RFilter getFiltro(String zFiltro) throws Exception {
		if (vFilters == null)
			return null;
		JList<RFilter> oEnum = vFilters;
		Iterator<RFilter> oIt = oEnum.iterator();
		while (oIt.hasNext()) {
			RFilter oFiltro = oIt.next();
			if (!oFiltro.getField().trim().equalsIgnoreCase(zFiltro.trim()))
				continue;
			return oFiltro;
		}
		return null;
	}
	public RJoins getFirstJoin() throws Exception {
		return getJoin(null,null);
	}
	public RJoins getJoin(String zJoin) throws Exception {
		return getJoin(zJoin,null);
	}
	public RJoins getJoin(String zJoin, String alias) throws Exception {
		return getJoin(zJoin,alias,true);
	}
	public RJoins getJoin(String zJoin, String alias, boolean recursivo) throws Exception {
		Iterator<RJoins> oIt = getJoins().iterator();
		while (oIt.hasNext()) {
			RJoins oJoin = oIt.next();
			if (zJoin==null) return oJoin;
			
			if (oJoin.hasJoins() && recursivo) {
				RJoins child = oJoin.getJoin(zJoin, alias);
				if (child!=null) return child;
			}
			if (oJoin.GetTablaJoin()!=null && !oJoin.GetTablaJoin().trim().toLowerCase().equals(zJoin.trim().toLowerCase()))
				continue;
			if (oJoin.GetAliasJoin()==null && alias!=null)
				continue;
			if (alias!=null && !alias.trim().toLowerCase().equals(oJoin.GetAliasJoin().trim().toLowerCase()))
				continue;
//			if (oJoin.GetAliasJoin()!=null && alias==null)
//				continue;
//			if (oJoin.GetAliasJoin()!=null && alias!=null && !oJoin.GetAliasJoin().trim().toLowerCase().equals(alias.trim().toLowerCase()))
//				continue;
			return oJoin;
		}
		return null;
	}

	public ROrderBy getOrderBy(String ztable,String zOrderBy,String orderby) throws Exception {
		Iterator<ROrderBy> oIt = getOrderBy().iterator();
		while (oIt.hasNext()) {
			ROrderBy oby = oIt.next();
			if (!oby.GetTabla().trim().toLowerCase().equals(ztable.trim().toLowerCase()))
				continue;
			if (!oby.GetCampo().trim().toLowerCase().equals(zOrderBy.trim().toLowerCase()))
				continue;
			if (!oby.GetOrden().trim().toLowerCase().equals(orderby.trim().toLowerCase()))
				continue;
			return oby;
		}
		return null;
	}


	public ROrderBy getOrderBy(String zOrderBy) throws Exception {
		Iterator<ROrderBy> oIt = getOrderBy().iterator();
		while (oIt.hasNext()) {
			ROrderBy oby = oIt.next();
			if (!oby.GetCampo().trim().toLowerCase().equals(zOrderBy.trim().toLowerCase()))
				continue;
			return oby;
		}
		return null;
	}

	public ROrderBy getCorteControl(String zOrderBy) throws Exception {
		Iterator<ROrderBy> oIt = getCorteControl().iterator();
		while (oIt.hasNext()) {
			ROrderBy oby = oIt.next();
			if (!oby.GetCampo().trim().toLowerCase().equals(zOrderBy.trim().toLowerCase()))
				continue;
			
			return oby;
		}
		return null;
	}
	public Object getFilterObjValue(String zFilter) throws Exception {
		RFilter oFiltro = this.getFiltro(zFilter);
		if (oFiltro == null)
			return null;
		return oFiltro.getObjValue();
	}
	public String getFilterValue(String zFilter) throws Exception {
		RFilter oFiltro = this.getFiltro(zFilter);
		if (oFiltro == null)
			return null;
		return oFiltro.getValue();
	}
	public String getFilterValue(String zFilter,String op) throws Exception {
		RFilter oFiltro = this.getFiltro(zFilter,op);
		if (oFiltro == null)
			return null;
		return oFiltro.getValue();
	}
	public void setFilter(RFilter filter) throws Exception {
		this.clearFilter(filter.getField(),filter.getOperator());
		this.addFilter(filter);
	}
	
	public RFilter setFilterValue(String table, String zFilter, String zValue,String type) throws Exception {
		RFilter oFiltro = this.getFiltro(zFilter);
		if (oFiltro == null) {
			oFiltro = this.addFilter(table, zFilter, "=", zValue, JObject.JSTRING, "AND", "");
		} else {
			oFiltro.sValor = new JString(zValue);
		}
		return oFiltro;
	}
	public RFilter setFilterObjValue(String table, String zFilter, Serializable zValue,String type) throws Exception {
		RFilter oFiltro = this.getFiltro(zFilter);
		if (oFiltro == null) {
			oFiltro = this.addFilter(table, zFilter, "=", zValue, JObject.JBD, "AND", "");
		} else {
			oFiltro.sValor = zValue;
		}
		return oFiltro;
	}
	public void clearFilter(String zFilter,String operator) throws Exception {
		while (true) {
			RFilter oFiltro = this.getFiltro(zFilter, operator);
			if (oFiltro == null)
				return;
			getFilters().removeElement(oFiltro);
		}
	}
	public void clearFilter(String zFilter) throws Exception {
		while (true) {
			RFilter oFiltro = this.getFiltro(zFilter);
			if (oFiltro == null)
				return;
			getFilters().removeElement(oFiltro);
		}
	}

	public void clearJoin(String zJoin) throws Exception {
		clearJoin(zJoin,null);
	}
	public void clearJoin(String zJoin,String alias) throws Exception {
		while (true) {
			RJoins oJoin = getJoin(zJoin,alias);
			if (oJoin == null)
				return;
			if (!getJoins().removeElement(oJoin))
				return;
		}
	}

	public boolean hasAnyJoin(String table) throws Exception {
		return this.hasJoin(table,null);
	}

	public boolean hasAnyJoin(String table,boolean recursivo) throws Exception {
		return this.hasJoin(table,null,recursivo);
	}
	public boolean hasJoin(String table) throws Exception {
		return this.hasJoin(table,null);
	}
	public boolean hasJoin(String table,String alias) throws Exception {
		return this.hasJoin(table,alias,true);
	}
	
	public boolean hasJoin(String table,String alias,boolean recursivo) throws Exception {
		return this.getJoin(table,alias,recursivo)!=null;
	}
	
	public boolean hasOrderBy(String field) throws Exception {
		return this.getOrderBy(field)!=null || this.getCorteControl(field)!=null;
	}
	public boolean hasOrderBy(	String zTabla, String zCampo, String zOrden) throws Exception {
		return this.getOrderBy(zTabla,zCampo,zOrden)!=null || this.getCorteControl(zCampo)!=null;
	}
	

	public boolean hasCorteControl(String field) throws Exception {
		return this.getCorteControl(field)!=null;
	}

	public String serializeStructure() throws Exception {
		JXMLElementFactory factory = JXMLElementFactory.getInstance();
		Element root = factory.createElement("root");

		Element table = root.getOwnerDocument().createElement("table");
		table.setAttribute("name", this.getTable());
		table.setAttribute("no_exc_select", this.withoutException() ? "S" : "N");
		root.appendChild(table);

		root.appendChild(this.serializeFilters(root));
		root.appendChild(this.serializeOrderBy(root));
		root.appendChild(this.serializeGroupBy(root));
		root.appendChild(this.serializeFixedFilters(root));

		return root.toString();
	}

	private Element serializeFilters(Element parent) throws Exception {
		Element filters = parent.getOwnerDocument().createElement("filters");
		JList<RFilter> oEnum = this.getFilters();
		if (oEnum == null)
			return filters;
		Iterator<RFilter> oIt = oEnum.iterator();
		while (oIt.hasNext()) {
			RFilter oFiltro = oIt.next();
			Element filter = filters.getOwnerDocument().createElement("filter");
			filter.setAttribute("table", oFiltro.getTable());
			filter.setAttribute("field", oFiltro.getField());
			filter.setAttribute("operator", oFiltro.getOperator());
			filter.setAttribute("value", oFiltro.getValue());
			filter.setAttribute("type", oFiltro.getType());
			filter.setAttribute("relation", oFiltro.getRelation());
			filter.setAttribute("group", oFiltro.getAgrup());
			filters.appendChild(filter);
		}
		return filters;
	}

	private Element serializeFixedFilters(Element parent) throws Exception {
		Element fixedFilters = parent.getOwnerDocument().createElement("fixed_filters");
		return fixedFilters;
	}

	private Element serializeGroupBy(Element parent) throws Exception {
		Element groupBy = parent.getOwnerDocument().createElement("group_by");
		return groupBy;
	}

	private Element serializeOrderBy(Element parent) throws Exception {
		Element orderBy = parent.getOwnerDocument().createElement("order_by");
		return orderBy;
	}

	private void unSerializeFilters(Element filterList) throws Exception {
		NodeList filterNodeList = filterList.getElementsByTagName("filter");
		for (int i = 0; i < filterNodeList.getLength(); i++) {
			Element filter = (Element) filterNodeList.item(i);
			String sTabla = filter.getAttribute("table");
			String sCampo = filter.getAttribute("field");
			String sOper = filter.getAttribute("operator");
			String sValor = filter.getAttribute("value");
			String sTipo = filter.getAttribute("type");
			String sRel = filter.getAttribute("relation");
			String sAgrup = filter.getAttribute("group");
			this.addFilter(sTabla, sCampo, sOper, sValor, sTipo, sRel, sAgrup);
		}
	}

	private void unSerializeFixedFilters(Element zFiltrosFijos) throws Exception {
	}

	private void unSerializeGroupBy(Element zGroupBy) throws Exception {
	}

	private void unSerializeOrderBy(Element zOrderBy) throws Exception {
	}

	public void unSerializeStructure(Element root) throws Exception {

		Element table = (Element) root.getElementsByTagName("table").item(0);
		this.setTable(table.getAttribute("name"));
		this.setWithoutException(table.getAttribute("no_exc_select").equals("S") ? true : false);

		NodeList filterList = root.getElementsByTagName("filters");
		for (int i = 0; i < filterList.getLength(); i++) {
			Element filter = (Element) filterList.item(i);
			this.unSerializeFilters(filter);
		}

		NodeList orderByList = root.getElementsByTagName("order_by");
		for (int i = 0; i < orderByList.getLength(); i++) {
			Element orderBy = (Element) orderByList.item(i);
			this.unSerializeOrderBy(orderBy);
		}

		NodeList groupByList = root.getElementsByTagName("group_by");
		for (int i = 0; i < groupByList.getLength(); i++) {
			Element groupBy = (Element) groupByList.item(i);
			this.unSerializeGroupBy(groupBy);
		}

		NodeList fixedFiltersList = root.getElementsByTagName("fixed_filters");
		for (int i = 0; i < fixedFiltersList.getLength(); i++) {
			Element fixFilter = (Element) fixedFiltersList.item(i);
			this.unSerializeFixedFilters(fixFilter);
		}

	}

	public RStructure cloneStructure() throws Exception {
		RStructure clone = new RStructure();
		clone.setTable(getTable());
		if (this.hasFilters()) {
			Iterator<RFilter> oIt = this.getFilters().iterator();
			while (oIt.hasNext()) {
				RFilter filter = oIt.next();
				clone.addFilter(filter.getTable(), filter.getField(), filter.getOperator(), filter.getObjValue(), filter.getType(), filter.getRelation(), filter.getAgrup());
			}
		}

		if (this.hasFixedFilters()) {
			Iterator<RFixedFilter> oIt = this.getFixedFilters().iterator();
			while (oIt.hasNext()) {
				RFixedFilter oFiltro = oIt.next();
				clone.addFixedFilter(oFiltro.getFiltro(),oFiltro.getsTableAsoc());
			}
		}

		if (this.hasJoins()) {
			Iterator<RJoins> oIt = this.getJoins().iterator();
			while (oIt.hasNext()) {
				RJoins join = oIt.next();
				clone.addJoin(join.GetTablaJoin(), join.GetAliasJoin(),join.isInnerJoin());
			}
		}

		return clone;
	}

	public int getFieldIndex(String zField) throws Exception {
		JList<RField> aCampos = this.getFields();
		int iIdx = 0;
		if (aCampos == null)
			return -1;

		Iterator<RField> oIt = aCampos.iterator();
		while (oIt.hasNext()) {
			RField field = oIt.next();
			if (field.GetCampo().equalsIgnoreCase(zField))
				return iIdx;
			iIdx++;
		}
		return -1;
	}
	
	public Object getFielValue(String zField) throws Exception {
		JList<RField> aCampos = this.getFields();
		int iIdx = 0;
		if (aCampos == null)
			return -1;

		Iterator<RField> oIt = aCampos.iterator();
		while (oIt.hasNext()) {
			RField field = oIt.next();
			if (field.GetCampo().equalsIgnoreCase(zField))
				return iIdx;
			iIdx++;
		}
		return -1;
	}

	// --------------------------------------------------------------------------
	// //

	public void copyFiltersFrom(RStructure zBase) throws Exception {
		if (!zBase.hasFilters())
			return;
		JList<RFilter> oEnum = zBase.getFilters();
		Iterator<RFilter> oIt = oEnum.iterator();
		while (oIt.hasNext()) {
			RFilter filter = oIt.next();
			this.addFilter(filter.getTable(), filter.getField(), filter.getOperator(), filter.getValue(), filter.getType(), filter.getRelation(), filter.getAgrup()).setDynamic(filter.isDynamic());
		}
	}

	public void copyFixedFiltersForm(RStructure zBase) throws Exception {
		if (!zBase.hasFixedFilters())
			return;
		JList<RFixedFilter> oEnum = zBase.getFixedFilters();
		Iterator<RFixedFilter> oIt = oEnum.iterator();
		while (oIt.hasNext()) {
			RFixedFilter filter = oIt.next();
			this.addFixedFilter(filter.getFiltro(),filter.getsTableAsoc());
		}
	}

	public void copyFixedOrderByFrom(RStructure zBase) throws Exception {
		if (!zBase.hasOrderBy())
			return;
		JList<RFixedOrderBy> oEnum = zBase.getFixedOrderBy();
		Iterator<RFixedOrderBy> oIt = oEnum.iterator();
		while (oIt.hasNext()) {
			RFixedOrderBy order = oIt.next();
			this.addFixedOrderBy(order.GetOrder());
		}
	}

	public void copyJoinsFrom(RStructure zBase) throws Exception {
		if (!zBase.hasJoins())
			return;
		JList<RJoins> oEnum = zBase.getJoins();
		Iterator<RJoins> oIt = oEnum.iterator();
		while (oIt.hasNext()) {
			RJoins join = oIt.next();
			this.addJoin(join.GetTablaJoin(), join.GetAliasJoin(), join.isInnerJoin());
		}
	}

	public void copyOrderByFrom(RStructure zBase) throws Exception {
		if (!zBase.hasOrderBy())
			return;
		JList<ROrderBy> oEnum = zBase.getOrderBy();
		Iterator<ROrderBy> oIt = oEnum.iterator();
		while (oIt.hasNext()) {
			ROrderBy order = oIt.next();
			this.addOrderBy(order.GetTabla(), order.GetCampo(), order.GetOrden());
		}
		JList<ROrderBy> oEnumCC = zBase.getCorteControl();
		Iterator<ROrderBy> oItCC = oEnumCC.iterator();
		while (oItCC.hasNext()) {
			ROrderBy order = oItCC.next();
			this.addCorteControl(order.GetTabla(), order.GetCampo(), order.GetOrden(), order.getControl());
		}
	}

	public void copyStructureFrom(RStructure zStructure) throws Exception {
		this.copyFiltersFrom(zStructure);
		this.copyFixedFiltersForm(zStructure);
		this.copyFixedOrderByFrom(zStructure);
		this.copyJoinsFrom(zStructure);
	}

	public RCatalog getCatalog() {
		return catalog;
	}

	public void setCatalog(RCatalog catalog) {
		this.catalog = catalog;
	}

}
