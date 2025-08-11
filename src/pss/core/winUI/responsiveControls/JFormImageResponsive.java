package pss.core.winUI.responsiveControls;

import pss.core.graph.Graph;
import pss.core.ui.graphics.JImageIcon;
import pss.core.win.actions.BizAction;
import pss.www.ui.JWebIcon;


public class JFormImageResponsive extends JFormControlResponsive {
	 
	
	private boolean expand;
	private boolean conBorde;
	private int type;
	private int source;
	private long lIdGraph=0;
	
	private Graph graph;
	private String oBackgroundImageFile = null;
	private JImageIcon oBackground = null;
	private JWebIcon oIcon = null;
	private JWebIcon oMaximixeIcon = null;

	private String link;
	
	private int labelFontSize;
	private String labelFontStyle;
	private int labelFontWeigth;

	public JFormControlResponsive setLabelFontSize(int v) {
		this.labelFontSize=v;
		return this;
	}

	public int getLabelFontSize() {
		return this.labelFontSize;
	}

	public JFormControlResponsive setLabelFontStyle(String v) {
		this.labelFontStyle=v;
		return this;
	}
	
	public String getLabelFontStyle() {
		return this.labelFontStyle;
	}

	public JFormControlResponsive setLabelFontWeigth(int v) {
		this.labelFontWeigth=v;
		return this;
	}

	public int getLabelFontWeigth() {
		return this.labelFontWeigth;
	}


	BizAction actionLink;
	private boolean forceImage;
	private String sImageClass = null;
	public String getImageClass() {
		return sImageClass;
	}

	public void setImageClass(String sImageClass) {
		this.sImageClass = sImageClass;
	}
	public boolean isForceImage() {
		return forceImage;
	}

	public JFormImageResponsive setForceImage(boolean forceImage) {
		this.forceImage = forceImage;
		return this;
	}
	public JWebIcon getIcon() {
		return oIcon;
	}

	public String getLink() {
		return link;
	}

	public JFormImageResponsive setLink(String link) {
		this.link = link;
		return this;
	}
	
	public JFormImageResponsive setIcon(JWebIcon oIcon) {
		this.oIcon = oIcon;
		return this;
	}
	public JWebIcon getMaximizeIcon() {
		return oMaximixeIcon;
	}

	public JFormImageResponsive setMaximizeIcon(JWebIcon oMaximixeIcon) {
		this.oMaximixeIcon = oMaximixeIcon;
		return this;
	}
	public String getBackgroundImageFile() {
		return oBackgroundImageFile;
	}

	public void setBackgroundImageFile(String oBackgroundImageFile) {
		this.oBackgroundImageFile = oBackgroundImageFile;
	}

	public JImageIcon getImageBackground() {
		return oBackground;
	}

	public JFormImageResponsive setImageBackground(JImageIcon oBackground) {
		this.oBackground = oBackground;
		return this;
	}

	public Graph getGraph() {
		return graph;
	}

	public JFormImageResponsive setGraph(Graph graph) {
		this.graph = graph;
		return this;
	}

	public long getIdGraph() {
		return lIdGraph;
	}

	public JFormImageResponsive setIdGraph(long lGraph) {
		this.lIdGraph = lGraph;
		return this;
	}
	BizAction actionSource;
	
	public BizAction getActionSource() {
		return actionSource;
	}

	public JFormImageResponsive setActionSource(BizAction action) {
		this.actionSource = action;
		return this;
	}

	
	public BizAction getActionLink() {
		return actionLink;
	}

	public JFormImageResponsive setActionLink(BizAction action) {
		this.actionLink = action;
		return this;
	}
	
	public int getSource() {
		return source;
	}

	public JFormImageResponsive setSource(int source) {
		this.source = source;
		return this;
	}

	public boolean isExpand() {
		return expand;
	}

	public JFormImageResponsive setExpand(boolean expand) {
		this.expand = expand;
		return this;
	}

	public int getType() {
		return type;
	}
	public boolean isConBorde() {
		return conBorde;
	}

	public JFormImageResponsive setConBorde(boolean conBorde) {
		this.conBorde = conBorde;
		return this;
	}
	public JFormImageResponsive setType(int type) {
		this.type = type;
		return this;
	}

	@Override
	public String getSpecificValue() throws Exception {
		return super.getSpecificValue();
	}
	
  
}
