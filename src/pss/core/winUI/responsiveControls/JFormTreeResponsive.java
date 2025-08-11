package pss.core.winUI.responsiveControls;

import pss.core.winUI.controls.JFormLista;

public class JFormTreeResponsive extends JFormLista  {

	String expanderTemplate="<span class=\"treetable-expander\"></span>";
	String indentTemplate="<span class=\"treetable-indent\"></span>";
	String expanderExpandedClass="fa fa-angle-down";
	String expanderCollapsedClass="fa fa-angle-right";
	long treeColumn=0;
	String initStatusClass="treetable-collapsed";

	public String getExpanderTemplate() {
		return expanderTemplate;
	}

	public JFormTreeResponsive setExpanderTemplate(String expanderTemplate) {
		this.expanderTemplate = expanderTemplate;
		return this;
	}

	public String getIndentTemplate() {
		return indentTemplate;
	}

	public JFormTreeResponsive setIndentTemplate(String indentTemplate) {
		this.indentTemplate = indentTemplate;
		return this;
	}

	public String getExpanderExpandedClass() {
		return expanderExpandedClass;
	}

	public JFormTreeResponsive setExpanderExpandedClass(String expanderExpandedClass) {
		this.expanderExpandedClass = expanderExpandedClass;
		return this;
	}

	public String getExpanderCollapsedClass() {
		return expanderCollapsedClass;
	}

	public JFormTreeResponsive setExpanderCollapsedClass(String expanderCollapsedClass) {
		this.expanderCollapsedClass = expanderCollapsedClass;
		return this;
	}

	public long getTreeColumn() {
		return treeColumn;
	}

	public JFormTreeResponsive setTreeColumn(long treeColumn) {
		this.treeColumn = treeColumn;
		return this;
	}

	public String getInitStatusClass() {
		return initStatusClass;
	}

	public JFormTreeResponsive setInitStatusClass(String initStatusClass) {
		this.initStatusClass = initStatusClass;
		return this;
	}

}
