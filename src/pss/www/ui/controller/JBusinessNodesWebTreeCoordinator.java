/*
 * Created on 02-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.controller;

import pss.common.security.BizUsuario;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.www.platform.applications.JWebApplicationSession;
import pss.www.ui.processing.JWebTreeCoordinator;
import pss.www.ui.processing.JWebTreeSelection;
import pss.www.ui.processing.JWebTreeSelectionConsumer;

public class JBusinessNodesWebTreeCoordinator implements JWebSessionOwnedObject, JWebTreeCoordinator, JBusinessNodesWebTreeConstants {

	//
	// INSTANCE VARIABLES
	//
	private JBasicWebUICoordinator oCoordinator;
	private JWebTreeSelection oSelection;

	//
	// CONSTRUCTOR
	//
	public JBusinessNodesWebTreeCoordinator(JBasicWebUICoordinator zCoordinator) {
		this.oCoordinator=zCoordinator;
	}

	//
	//
	// API METHODS
	//
	//

	public JWebApplicationSession getSession() {
		return this.oCoordinator.getSession();
	}

	public String createFilters(JWebTreeSelectionConsumer zConsumer, String zTableName) throws Exception {
		if (!this.isSelectionAccepted(zConsumer)) {
			return null;
		}
		return this.createFiltersSQL(zConsumer, zTableName);
	}

	@SuppressWarnings("unchecked")
	public boolean applyFilters(JWebTreeSelectionConsumer zConsumer, JRecords zDataObject) throws Exception {
		if (!this.isSelectionAccepted(zConsumer)) {
			return false;
		}

		if (zConsumer.appliesTreeFiltersManually(this.oSelection.getTreeId())) {
			return true;
		}
		String sFixedFilter=this.createFiltersSQL(zConsumer, zDataObject.getStructure().getTable());
		if (sFixedFilter!=null&&sFixedFilter.length()>4) {
			zDataObject.addFixedFilter(sFixedFilter.substring(5));
		}
		return true;
	}

	public JWebTreeSelection getSelection() {
		return this.oSelection;
	}

	public void setSelection(JWebTreeSelection selection) {
		this.oSelection=selection;
	}

	//
	//
	// INTERNAL IMPLEMENTATION METHODS
	//
	//

	void cleanUp() {
		this.oCoordinator=null;
		this.oSelection=null;
	}

	private boolean isSelectionAccepted(JWebTreeSelectionConsumer zConsumer) throws Exception {
		if (this.oSelection==null||this.oSelection.getTreeId()==null||this.oSelection.getTreeId().length()<1) {
			return false; // to encourage the user to select a node for reports which apply the tree filters
		}
		// asks consumer if accepts selection
		if (!zConsumer.acceptsSelection(this.oSelection.getTreeId(), this.oSelection.getDeepestSelectedLevel())) {
			return false;
		}
		return true;
	}

	private String createFiltersSQL(JWebTreeSelectionConsumer zConsumer, String zTableName) throws Exception {
		String sFixedFilter=null;
		if (TREE_ID_BY_REGION.equals(this.oSelection.getTreeId())) {
			sFixedFilter=this.createFilterByRegion(zConsumer, zTableName);
		} else if (TREE_ID_USER_DEFINED.equals(this.oSelection.getTreeId())) {
			sFixedFilter=this.createFilterByUSerDefinedGrouping(zConsumer, zTableName);
		} else {
			JExcepcion.SendError("Selección del árbol no implementada, id: {"+this.oSelection.getTreeId()+"}");
		}
		return sFixedFilter;
	}

	private String createFilterByRegion(JWebTreeSelectionConsumer zConsumer, String zTableName) throws Exception {
		StringBuffer oSQL=new StringBuffer(100);
		this.appendFilter(zConsumer, TREE_ID_BY_REGION, TREE_BY_REGION_REGIONAL_LEVEL_1, oSQL, zTableName, false);
		this.appendFilter(zConsumer, TREE_ID_BY_REGION, TREE_BY_REGION_REGIONAL_LEVEL_2, oSQL, zTableName, false);
		this.appendFilter(zConsumer, TREE_ID_BY_REGION, TREE_BY_REGION_REGIONAL_LEVEL_3, oSQL, zTableName, false);
		this.appendFilter(zConsumer, TREE_ID_BY_REGION, TREE_BY_REGION_REGIONAL_LEVEL_4, oSQL, zTableName, false);
		this.appendFilter(zConsumer, TREE_ID_BY_REGION, TREE_BY_REGION_LEVEL_BUSINESS_NODE, oSQL, zTableName, false);
		return oSQL.toString();
	}

	private String createFilterByUSerDefinedGrouping(JWebTreeSelectionConsumer zConsumer, String zTableName) throws Exception {
		StringBuffer oSQL=new StringBuffer(100);
		// add the user filter
		this.appendFilter(zConsumer, TREE_ID_USER_DEFINED, TREE_USER_DEFINED_LEVEL_USER, oSQL, zTableName, BizUsuario.getCurrentUser(), false);
		// add the country/node filter
		if (this.oSelection.isLevelSelected(TREE_USER_DEFINED_LEVEL_BUSINESS_NODE)) {
			String sCountry=this.oSelection.getLevelValue(TREE_USER_DEFINED_LEVEL_BUSINESS_NODE_COUNTRY);
			String sNode=this.oSelection.getLevelValue(TREE_USER_DEFINED_LEVEL_BUSINESS_NODE);
			this.appendFilter(zConsumer, TREE_ID_USER_DEFINED, TREE_USER_DEFINED_LEVEL_BUSINESS_NODE_COUNTRY, oSQL, zTableName, sCountry, false);
			this.appendFilter(zConsumer, TREE_ID_USER_DEFINED, TREE_USER_DEFINED_LEVEL_BUSINESS_NODE, oSQL, zTableName, sNode, false);
		}
		this.appendFilter(zConsumer, TREE_ID_USER_DEFINED, TREE_USER_DEFINED_LEVEL_GROUPING_TYPE, oSQL, zTableName, false);
		this.appendFilter(zConsumer, TREE_ID_USER_DEFINED, TREE_USER_DEFINED_LEVEL_GROUPING, oSQL, zTableName, false);
		return oSQL.toString();
	}

	private String getFilterField(JWebTreeSelectionConsumer zConsumer, String zTreeId, int zLevelInSelection) throws Exception {
		String sField=zConsumer.getFilterField(zTreeId, zLevelInSelection);
		if (sField==null) {
			sField=this.getDefaultFilterField(zTreeId, zLevelInSelection);
			if (sField==null) {
				throw new RuntimeException("Unknown field for tree selection level: "+String.valueOf(zLevelInSelection)+", tree: "+zTreeId);
			}
		}
		return sField;
	}

	private void appendFilter(JWebTreeSelectionConsumer zConsumer, String zTreeId, int zLevelInSelection, StringBuffer zBuff, String sTable, boolean isNumeric) throws Exception {
		if (this.oSelection.isLevelSelected(zLevelInSelection)) {
			this.appendFilter(zConsumer, zTreeId, zLevelInSelection, zBuff, sTable, this.oSelection.getLevelValue(zLevelInSelection), isNumeric);
		}
	}

	private void appendFilter(JWebTreeSelectionConsumer zConsumer, String zTreeId, int zLevelInSelection, StringBuffer zBuff, String sTable, String sLevelValue, boolean isNumeric) throws Exception {
		zBuff.append(" AND ").append(sTable);
		zBuff.append(".").append(this.getFilterField(zConsumer, zTreeId, zLevelInSelection));
		if (sLevelValue==null||(sLevelValue=sLevelValue.trim()).length()<1) {
			zBuff.append(" IS NULL");
		} else {
			if (isNumeric) {
				zBuff.append(" = ").append(sLevelValue);
			} else {
				zBuff.append(" = '").append(sLevelValue).append("'");
			}
		}
	}

	private String getDefaultFilterField(String zTreeId, int zLevelInSelection) {
		if (TREE_ID_BY_REGION.equals(zTreeId)) {
			return TREE_FILTER_FIELDS_BY_REGION[zLevelInSelection];
		} else if (TREE_ID_USER_DEFINED.equals(zTreeId)) {
			return TREE_FILTER_FIELDS_USER_DEFINED[zLevelInSelection];
		} else {
			return null;
		}
	}

}
