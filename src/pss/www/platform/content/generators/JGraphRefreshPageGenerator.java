package pss.www.platform.content.generators;

import java.io.IOException;
import java.util.Map;

import org.apache.avalon.framework.parameters.Parameters;
import org.apache.cocoon.ProcessingException;
import org.apache.cocoon.environment.SourceResolver;
import org.xml.sax.SAXException;

import pss.core.graph.Graph;
import pss.core.tools.JTools;
import pss.core.win.JBaseWin;
import pss.core.win.submits.JAct;
import pss.www.ui.JWebDivResponsive;
import pss.www.ui.JWebImageServer;
import pss.www.ui.JWebPage;
import pss.www.ui.JWebView;
import pss.www.ui.processing.JWebCanvas;
import pss.www.ui.skins.JLayoutGenerator;
import pss.www.ui.views.JCanvasResponsiveEmptyView;

public class JGraphRefreshPageGenerator extends JIndoorsPageGenerator {

	@Override
	protected String getBaseContentName() {
		return "console.winlist.refresh";
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setup(SourceResolver resolver, Map objectModel, String src, Parameters par) throws ProcessingException, SAXException, IOException {
		super.setup(resolver, objectModel, src, par);
		getRequest().setAjax(true);
	}



	@Override
	protected JWebPage createPage() throws Exception {
		JWebPage oPage=new JWebPage();
		oPage.setLayoutStereotype(this.getPageLayoutStereotype());
		oPage.addHeaderComponent(new JSessionInfoGenerator());
		oPage.addHeaderComponent(new JLayoutGenerator());
		return oPage;
	}
	
	
//
//	public String buildImage(BizAction actionSource,int idGraph, Graph graph, int width, int height) throws Exception {
//		if (actionSource==null) return null;
//		JActWins act = (JActWins) actionSource.getObjSubmit();
//		JWins w = act.getWinsResult();
//		JWinList wl = new JWinList(w);
//		JFormFiltro filtros = new JFormFiltro(w);
//    w.ConfigurarColumnasLista(wl);
//    w.ConfigurarFiltros(filtros);
//    w.ConfigurarGraficos(wl);
//    w.ConfigurarFiltrosLista(wl);
//    
//    filtros.asignDefaultData();
//    filtros.applyFilterMap(actionSource,false);
//    Graph g=null;
//    if (graph!=null) 
//    	g=graph;
//    else  if (idGraph!=0)
//    	g=wl.getGrafico((int)idGraph);
//    else
//    	return null;
//		if (g==null) return null;
//		g.localFill(wl,null,filtros);
//		return g.getImage(width,height);
//	}

	@Override
	protected JWebView createView() throws Exception {
		JAct submit = this.getAct();

		
		JWebImageServer image = new JWebImageServer();
		image.setActionSource(submit.getActionSource());
		String graph =getRequest().getData("graficos").get("graph");
		String idgraph=getRequest().getData("graficos").get("id_graph");
		if (graph!=null && !graph.equals("")) {
			image.setGraph((Graph)getRequest().getRegisterObject(graph));
		} else
			image.setIdGraph(JTools.getLongNumberEmbedded(idgraph));
		image.setSize(getRequest().getContainerWidth(), getRequest().getContainerHeight());
		JWebDivResponsive div = new JWebDivResponsive();
		div.setName("canvas_image");
		div.addChild("image",image);
		JWebView oWebView = this.embedInView(null, div);

		return oWebView;
	}

	protected JWebView embedInView(JBaseWin win, JWebCanvas canvas) throws Exception {
		return new JCanvasResponsiveEmptyView(canvas);
	}
	@Override
	protected String getSkinStereotypeView() throws Exception {
		return null;
	}

}