package pss.core.graph.implementations;

import pss.common.security.BizUsuario;
import pss.core.graph.Graph;
import pss.core.graph.analize.Categories;
import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Value;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;

public class GraphScriptMultiChart extends GraphMatrix {
	public String isStacked = "false";
	public String showControls="true";
	public String showLegend="true";
	
	public void setStackedOn() {
		isStacked="true";
	}
	
	public void setShowControlsOff() {
		showControls="false";
	}
	
	public void setShowLegendOff() {
		showLegend="false";
	}
	public String getDatasetName(String name) throws Exception {

		return name;
		
	}
	public String getScript(int width, int height) throws Exception {
		String s="";
		String decla="";
		String defin="";
		String script="";

		decla+="<div class=\"graph_title\" id=\"span_title_"+this.hashCode()+"\" >"+JTools.encodeString2(this.getTitle())+"</div>\n";
		decla+="<div id=\"chart_"+this.hashCode()+"\" class=\"graph\"><svg></svg></div>";
		
		defin="[";
		int j=0;
		JIterator<Dataset> ids = getDatasets().getValueIterator();
		while (ids.hasMoreElements()) {
			int i=0;
			Dataset ds = ids.nextElement();
			String name = ds.getName().replaceFirst("_DATASET", "");
			name = getDatasetName(name);
			
			defin+=(j++==0?"":",")+"{key:'"+JTools.encodeString2(name)+"',area:false,values:[";
			JIterator<Categories> cts = getCategories().getValueIterator();
			while (cts.hasMoreElements()) {
				Categories cat = cts.nextElement();
				JIterator<Value> iv = ds.getValues().getValueIterator();
				Value vf =null;
				while (iv.hasMoreElements()) {
					Value v = iv.nextElement();
					if (cat.getName().equals(v.getCategorie())) {
						vf=v;
						break;
					}
				}
				if (vf!=null && JTools.isNumber(vf.getData().toString(), true))
					defin+=(i++==0?"":",")+"{x:'"+JTools.encodeString2(cat.getName())+"',y:"+(vf.getData().toString().equals("0.0")?null:Float.parseFloat(vf.getData().toString()))+"  }";
				else
					defin+=(i++==0?"":",")+"{x:'"+JTools.encodeString2(cat.getName())+"',y:0}";
			}		
			defin+="]}";
		}
		defin+="]";
		
		boolean rotateNames = getAtributtes().getElement(Graph.GRAPH_ATTR_ROTATENAMES).equals("S");
		String sMargin = getAtributtes().getElement(Graph.GRAPH_ATTR_SHOWAREABORDER);
		long margen = sMargin==null||sMargin.equals("")?70:JTools.getLongNumberEmbedded(sMargin);
			
		script = " addMultiBar('chart_"+this.hashCode()+"','"+JTools.encodeString2(getTitle())+"',"+defin+","+width+","+height+","+(rotateNames?"true":"false")+","+(!rotateNames&&width<500?"true":"false")+",["+ getMyColors() +"]," + isStacked + ", "+ showControls +  ", "+ showLegend + ", "+margen+");\n";
//					script = " addMultiBar('chart_"+this.hashCode()+"','"+JTools.encodeString(getTitle())+"',"+defin+","+width+","+height+","+(width<500?"true":"false")+");\n";
		
		s+=decla;
		s+="<script type=\"text/javascript\">\n";
		s+=script;
		s+="\n</script>\n";
		
		return s;
	}
	
	private String colorMap=null;
	public void setColorMap(String v) {
		this.colorMap=v;
	}
	public String getMyColors() throws Exception {
		if (colorMap==null) {
			this.colorMap=JTools.ArrayToString(BizUsuario.getSkin().createGenerator().getColorGraph());
		}
		// TODO Auto-generated method stub
		//return " \"#1f77b4\", \"#ff7f0e\", \"#2ca02c\", \"#d62728\", \"#9467bd\", \"#8c564b\", \"#e377c2\", \"#7f7f7f\", \"#bcbd22\", \"#17becf\" " ;
		return this.colorMap;
		
	}


	@Override
	public String getSWF() {
		return null;
	}
	
	public String getImage(int width, int height) throws Exception {
		return "script:"+getScript(width,height);
	}
	
	public String isTimeLine() throws Exception {
		return TIME_IN_CATEGORY;
	}
}
