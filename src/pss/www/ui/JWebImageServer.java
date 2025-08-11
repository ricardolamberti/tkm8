package pss.www.ui;

import pss.core.graph.Graph;
import pss.core.ui.components.JPssImage;
import pss.core.ui.graphics.JImageIcon;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JActWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormImageResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;

public class JWebImageServer  extends JWebViewFixedComponent implements JWebControlInterface, JWebActionable {

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
	private int type;
	private String oBackgroundImageFile = null;
	private JImageIcon oBackground = null;

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

	public static JWebImageResponsive create(JWebViewComposite parent, JFormImageResponsive zControl) throws Exception {
		JWebImageResponsive panel = new JWebImageResponsive();
		panel.initialize(zControl.getSource(), null, null);
		panel.control = zControl;
		panel.takeAttributesFormControlResponsive(zControl);
		panel.setIdGraph(zControl.getIdGraph());
		panel.setGraph(zControl.getGraph());
		panel.setActionSource(zControl.getActionSource());
		panel.setType(zControl.getType());
		panel.setBackgroundImageFile(zControl.getBackgroundImageFile());
		panel.setImageBackground(zControl.getImageBackground());
		panel.setActionLink(zControl.getActionLink());
		panel.setMinHeightResponsive(zControl.getHeight());
		if (parent != null && panel.hasImage())
			parent.addChild(zControl.getName(), panel);
		return panel;
	}

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


	public JWebImageServer() {
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
		zContent.startNode("image");
		if (actionSource != null) {
			String content = buildImage();
			if (content != null && content.startsWith("script:")) {
				zContent.setAttribute("source", "script");
				zContent.setAttribute("file", content.substring(7));
			} else {
				zContent.setAttribute("source", "webapp_url");
				zContent.setAttribute("file", content);
			}
		} else {
			if (this.iSource == JPssImage.SOURCE_PSS) {
				zContent.setAttribute("source", "pss_icon");
			} else if (this.iSource == JPssImage.SOURCE_URL) {
				zContent.setAttribute("source", "webapp_url");
			} else if (this.iSource == JPssImage.SOURCE_PSSDATA) {
				zContent.setAttribute("source", "pss_data");
			} else if (this.iSource == JPssImage.SOURCE_SKIN) {
				zContent.setAttribute("source", "skin_url");
			}
			if (this.iSource == JPssImage.SOURCE_SCRIPT) {
				zContent.setAttribute("source", "script");
			} else {
				zContent.setAttribute("source", "");
			}
			zContent.setAttribute("file", this.sURL);
		}

		if (this.getLink() != null)
			zContent.setAttribute("link", this.getLink());
//		if (this.isExpand())
//			zContent.setAttribute("expand", "S");
		if (this.isActionable()) {
			JWebAction oAction = ((JWebActionable) this).getWebAction();
			if (oAction != null) {
				oAction.toXML(zContent);
			}
		}
		zContent.endNode("image");
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
	
	public JWins getWinsClone() throws Exception {
		if (wins!=null)  return wins;
		JActWins act = (JActWins) actionSource.getObjSubmit();
		JWins w = act.getWinsResult();
		JWins w1 = w.createClone();
    w1.getRecords().setStructure(w.getRecords().cloneStructure());
		return wins=w1;
	}
	
	public boolean hasImage() throws Exception {
		if (actionSource == null)
			return true;
		JWinList wl = new JWinList(getWins());
		Graph g = wl.getGrafico((int) idGraph);
		return g!=null;
	}
	public String buildImage() throws Exception {
		if (actionSource == null)
			return null;
		JWins w = getWinsClone();
    
		JWinList wl = new JWinList(w);
		JFormFiltro filtros = new JFormFiltro(w);
		w.ConfigurarColumnasListaInternal(wl);
		w.ConfigurarFiltros(filtros);
		w.ConfigurarGraficos(wl);
		w.ConfigurarFiltrosLista(wl);

		filtros.asignDefaultData();
		filtros.applyFilterMap(actionSource, false);
		Graph g = null;
		if (graph != null)
			g = graph;
		else if (idGraph != 0)
			g = wl.getGrafico((int) idGraph);
		else
			return null;
		if (g == null)
			return null;
		if (getRealSize()==null) {
			setSize(800,600);
		} else{
			if (getRealSize().height < 50)
				getRealSize().height = 50;
			if (getRealSize().width < 50)
				getRealSize().width = 50;
		}
		g.localFill(wl, null, filtros);
		return g.getImage(getRealSize().width, getRealSize().height);
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
		if (act == null)
			return null;
		return JWebActionFactory.createViewAreaAndTitleAction(act, getObjectProvider(), true, null);
	}

}
