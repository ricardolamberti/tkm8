package pss.core.graph.implementations;

import pss.core.graph.analize.Categories;
import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Value;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;


public class GraphScriptWorldArc extends GraphMatrix {

	public String getScript(int width, int height) throws Exception {
		String s="";
		String data= "";
		String links= "";
		s+="<div class=\"graph_title\" id=\"span_title_"+this.hashCode()+"\" >"+JTools.encodeString2(this.getTitle())+"</div>\n";
		int i =0,j=0;
		data = "[";
		links = "[";
		JIterator<Categories> icat = getCategories().getValueIterator();

		icat = getCategories().getValueIterator();
		while (icat.hasMoreElements()) {
			Categories cat = icat.nextElement();
			Float[] values = new Float[getDatasets().size()];
			for (int y=0;y<getDatasets().size();y++) values[y]=0.0f;
			if (cat.getGeoPosition()==null) continue;
			JIterator<Dataset> ids = getDatasets().getValueIterator();
			while (ids.hasMoreElements()) {
				Dataset ds = ids.nextElement();
				JIterator<Value> iv = ds.getValues().getValueIterator();
				if (ds.getGeoPosition()==null) break;
				boolean find = false;
				while (iv.hasMoreElements()) {
					Value v = iv.nextElement(); 
					if (!v.getCategorie().equals(cat.getName()))
						continue;
					values[(int)ds.getRefNumerica()]+= Float.parseFloat(v.getData().toString().equals("") ? "0" : v.getData().toString());
					find=true;
				}
				if (!find) continue;
				if (cat.getGeoPosition()==null) continue;
				if (ds.getGeoPosition().getLatitude()==0 && ds.getGeoPosition().getLongitude()==0) continue;
				if (cat.getGeoPosition().getLatitude()==0 && cat.getGeoPosition().getLongitude()==0) continue;
				data += (i++==0?"":",")+"{'original address': '"+ds.getName()+"','returned address': '"+ds.getName()+"','latitude': '"+ds.getGeoPosition().getLatitude()+"','longitude': '"+ds.getGeoPosition().getLongitude()+"'}";
				data += (i++==0?"":",")+"{'original address': '"+cat.getName()+"','returned address': '"+cat.getName()+"','latitude': '"+cat.getGeoPosition().getLatitude()+"','longitude': '"+cat.getGeoPosition().getLongitude()+"'}";
				links += (j++==0?"":",")+"{'source':{0:"+cat.getGeoPosition().getLongitude()+",1:"+cat.getGeoPosition().getLatitude()+"},'target':{0:"+ds.getGeoPosition().getLongitude()+",1:"+ds.getGeoPosition().getLatitude()+"}}";
			}
		}
		data += "]";
		links += "]";
		
		s+="<div id=\"globearc_"+this.hashCode()+"\" class=\"graph\" /><script>addGlobeArc(\"globearc_"+this.hashCode()+"\","+width+","+height+","+data+","+links+");</script>";
//		s+="<iframe id=\"iframe_"+this.hashCode()+"\" src=\"html/globeArc.html\" onload=\"document.getElementById('iframe_"+this.hashCode()+"').contentWindow.initializeGlobeArc($(this).width(),$(this).height(),"+data+","+links+");\" seamless=\"seamless\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"width:100%;height:100%;visibility:visible;\"></iframe>";
		return s;
	}

	@Override
	public String getSWF() {
		return null;
	}
	public String getImage(int width, int height) throws Exception {
		

		return "script:"+getScript(width,height);
	}
	
	public int getGeoRequest() throws Exception {
		return 2;
	}
	public String isTimeLine() throws Exception {
		return TIME_NO;
	}
}
