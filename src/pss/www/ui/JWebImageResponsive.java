package pss.www.ui;

import pss.core.graph.Graph;
import pss.core.tools.JTools;
import pss.core.ui.components.JPssImage;
import pss.core.ui.graphics.JImageIcon;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JActWins;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormImageResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;

public class JWebImageResponsive extends JWebViewFixedComponent implements JWebControlInterface, JWebActionable {

	JFormImageResponsive control;

	private long iSource;
	private String sURL;
	private String link;
	private boolean expand;
	private boolean conBorde;
	private BizAction actionSource;
	private BizAction actionLink;
	private Graph graph;
	private long idGraph;
	private boolean forceImage; // fuerza a que los script sean convertidos a jpg
	private boolean sinDIV;

	private int type;
	private String oBackgroundImageFile = null;
	private String sImageClass = null;

	private int labelFontSize;
	private String labelFontStyle;
	private int labelFontWeigth;

	public void setLabelFontSize(int v) {
		this.labelFontSize=v;
	}

	public int getLabelFontSize() {
		return this.labelFontSize;
	}

	public void setLabelFontStyle(String v) {
		this.labelFontStyle=v;
	}
	
	public String getLabelFontStyle() {
		return this.labelFontStyle;
	}

	public void setLabelFontWeigth(int v) {
		this.labelFontWeigth=v;
	}

	public int getLabelFontWeigth() {
		return this.labelFontWeigth;
	}

	private JImageIcon oBackground = null;
	private JWebIcon oIcon = null;
	private String styleIcon = "width:100%;";
	public String getStyleIcon() {
		return styleIcon;
	}

	public boolean isForceImage() {
		if (JWebActionFactory.isMobile()) return true;
		return forceImage;
	}

	public void setForceImage(boolean forceImage) {
		this.forceImage = forceImage;
	}

	public void setStyleIcon(String styleIcon) {
		this.styleIcon = styleIcon;
	}
	
	public boolean isSinDIV() {
		return sinDIV;
	}

	public void setSinDIV(boolean sinDIV) {
		this.sinDIV = sinDIV;
	}


	public JWebIcon getIcon() {
		return oIcon;
	}

	public void setIcon(JWebIcon oIcon) {
		this.oIcon = oIcon;
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

	public void setImageBackground(JImageIcon oBackground) {
		this.oBackground = oBackground;
	}

	public BizAction getActionSource() {
		return actionSource;
	}

	public void setActionSource(BizAction action) {
		this.actionSource = action;
	}

	public BizAction getActionLink() {
		return actionLink;
	}

	public void setActionLink(BizAction action) {
		this.actionLink = action;
	}

	public void setType(int v) {
		this.type = v;
	}

	public int getType() {
		return this.type;
	}

	public long getIdGraph() {
		return idGraph;
	}

	public void setIdGraph(long graph) {
		this.idGraph = graph;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public boolean isConBorde() {
		return conBorde;
	}

	public void setConBorde(boolean conBorde) {
		this.conBorde = conBorde;
	}

	public boolean isExpand() {
		return expand;
	}

	public void setExpand(boolean expand) {
		this.expand = expand;
	}

	public String getDisplayValue() throws Exception {
		return this.getSpecificValue();
	}
	public String getImageClass() {
		return sImageClass;
	}

	public void setImageClass(String sImageClass) {
		this.sImageClass = sImageClass;
	}
	public static JWebImageResponsive create(JWebViewComposite parent, JFormImageResponsive zControl) throws Exception {
		JWebImageResponsive panel = new JWebImageResponsive();
		panel.initialize(zControl.getSource(), null, null);
		panel.control = zControl;
		panel.takeAttributesFormControlResponsive(zControl);
		if (zControl.getIcon()!=null)
			panel.setIcon(zControl.getIcon());
		panel.setIdGraph(zControl.getIdGraph());
		panel.setGraph(zControl.getGraph());
		panel.setActionSource(zControl.getActionSource());
		panel.setType(zControl.getType());
		panel.setLink(zControl.getLink());
		panel.setImageClass(zControl.getImageClass());
		panel.setForceImage(zControl.isForceImage());
		panel.setBackgroundImageFile(zControl.getBackgroundImageFile());
		panel.setImageBackground(zControl.getImageBackground());
		panel.setActionLink(zControl.getActionLink());
		panel.setHeightResponsive(zControl.getHeight());
		panel.setLabelFontSize(zControl.getLabelFontSize());
		panel.setLabelFontStyle(zControl.getLabelFontStyle());
		panel.setLabelFontWeigth(zControl.getLabelFontWeigth());
		if (parent != null && panel.hasImage())
			parent.addChild(zControl.getName(), panel);
		return panel;
	}
//
//	public static JWebImage getPssIcon(long size, int zIconNumber) throws Exception {
//		String sFileName = GuiIconos.GetGlobal().buscarIcono(size, zIconNumber).GetFile();
//		return new JWebImage(JPssImage.SOURCE_PSS, sFileName.toLowerCase(), null);
//	}
//
//	public static JWebImage getPssIcon(String zIconFile) throws Exception {
//		return new JWebImage(JPssImage.SOURCE_PSS, zIconFile.toLowerCase(), null);
//	}
//
//	public static JWebImage getIcon(String zWebAppRelativeURL) throws Exception {
//		return new JWebImage(JPssImage.SOURCE_URL, JPath.normalizePath(zWebAppRelativeURL), null);
//	}
//
//	public static JWebImage getSkinIcon(String zSkinRelativeURL) throws Exception {
//		return new JWebImage(JPssImage.SOURCE_SKIN, JPath.normalizePath(zSkinRelativeURL), null);
//	}

	public JWebImageResponsive() {
	}

	public JWebImageResponsive(long zSource, String zURL, String zLink) {
		initialize(zSource, zURL, zLink);
	}

	public void initialize(long zSource, String zURL, String zLink) {
		this.iSource = zSource;
		this.sURL = zURL;
		this.link = zLink;
	}

	@Override
	public void destroy() {
		this.sURL = null;
	}

	@Override
	public String getComponentType() {
		return "image_responsive";
	}

	@Override
	public String getSkinStereotype() {
		if (sSkinStereotype != null)
			return sSkinStereotype;
		return conBorde ? "image" : "image_sin_borde";
	}

	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		boolean diferido = actionSource != null;
		if (sinDIV)
			zContent.setAttribute("sinDIV", true);
		zContent.setAttribute("diferido", diferido);
		
		if (getLabelFontSize()!=0) zContent.setAttribute("label_fontsize", getLabelFontSize());
		if (getLabelFontStyle()!=null) zContent.setAttribute("label_fontstyle", getLabelFontStyle());
		if (getLabelFontWeigth()!=0) zContent.setAttribute("label_fontweigth", getLabelFontWeigth());
	  
		String imagen = this.oBackgroundImageFile!=null&&!this.oBackgroundImageFile.equals("")?this.oBackgroundImageFile:this.sURL;
		if (oIcon != null) {
			oIcon.setStyleImage(getStyleIcon());
			oIcon.setClassImage(getImageClass());
			oIcon.toXML(zContent);
		} else {

			zContent.startNode("image");
			if (diferido) {
				String sgraph = "";
				String sidGraph = "";
				String name = "graph_" + this.hashCode();
				if (graph != null)
					sgraph = zContent.addObjectObjTemp(graph);
				else
					sidGraph = "" + idGraph;

			
				zContent.setAttribute("source", "ajax");
				zContent.setAttribute("graph_content", name);

				JWebAction oAction = JWebActionFactory.createBuildGraphAction(actionSource, getObjectProvider(), name, sgraph, sidGraph);
				if (oAction != null) {
					zContent.startNode("diferido");
					oAction.toXML(zContent);
					zContent.endNode("diferido");
				}
			} else {
				if (imagen==null)
					return;
				if ((imagen.startsWith("script:") || this.iSource == JPssImage.SOURCE_SCRIPT) && isForceImage()) {
					imagen= JTools.convertScript2Image(imagen.startsWith("script:")?imagen.substring(7):imagen,600,600);
				} 
	
				if (imagen.startsWith("data:"))
					this.iSource = JPssImage.SOURCE_URL;
				else if (imagen.startsWith("script:")) {
					this.iSource = JPssImage.SOURCE_SCRIPT;
					imagen = imagen.substring(7);
				}
				if (this.iSource == JPssImage.SOURCE_PSS) {
					zContent.setAttribute("source", "pss_icon");
				} else if (this.iSource == JPssImage.SOURCE_URL) {
					zContent.setAttribute("source", "webapp_url");
				} else if (this.iSource == JPssImage.SOURCE_PSSDATA) {
					zContent.setAttribute("source", "pss_data");
				} else if (this.iSource == JPssImage.SOURCE_SKIN) {
					zContent.setAttribute("source", "skin_url");
				} else if (this.iSource == JPssImage.SOURCE_SCRIPT) {
					zContent.setAttribute("source", "script");
				} else {
					zContent.setAttribute("source", "");
				}
				zContent.setAttribute("file", imagen);
				if (getImageClass()!=null)
					zContent.setAttribute("class_image", getImageClass());
				zContent.setAttribute("type", getType());
				if (this.getResponsiveSize()!=null) {
					 if (this.getResponsiveSize().width!=0) zContent.setAttribute("width", this.getResponsiveSize().width);
					 if (this.getResponsiveSize().height!=0) zContent.setAttribute("height", this.getResponsiveSize().height);
					
				}
				if (this.getLink() != null)
					zContent.setAttribute("link", this.getLink());
				if (this.isActionable()) {
					JWebAction oAction = ((JWebActionable) this).getWebAction();
					if (oAction != null) {
						oAction.toXML(zContent);
					}
				}
			}
			zContent.endNode("image");
		}
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void clear() throws Exception {

	}

	public String getSpecificValue() throws Exception {
		return sURL;
	}

	public void setController(JWebFormControl control) {

	}

	public void setEditable(boolean editable) throws Exception {

	}
	
	JWins wins;
	
	public JWins getWins() throws Exception {
		if (wins!=null)  return wins;
		JActWins act = (JActWins) actionSource.getObjSubmit();
		JWins w = act.getWinsResult();
		return wins=w;
	}
	
	public boolean hasImage() throws Exception {
		if (actionSource == null)
			return true;
		JWinList wl = new JWinList(getWins());
		Graph g = wl.getGrafico((int) idGraph);
		return g!=null;
	}

	public void setValueFromUIString(String val) throws Exception {
		this.setValue(val);
	}

	public void setValue(String val) throws Exception {
		sURL = val;
	}

	public void onShow(String mode) throws Exception {
	}

	@Override
	public JWebAction getWebAction() throws Exception {
		BizAction act = this.actionLink;
		// if (act==null) act = this.actionSource;
		if (act == null)
			return null;
		return JWebActionFactory.createViewAreaAndTitleAction(act, getObjectProvider(), true, null);
	}

}
