package pss.core.graph.implementations;

import pss.core.graph.analize.Categories;
import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Value;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;

public class GraphScriptRadialBar  extends GraphMatrix {

	public String getScript(int width, int height) throws Exception {
		int c=0;
		String s="";
		String decla="";
		String defin="";
		String script="";
		
		double proporcion = (double)width/(double)height;
		int cant = getCategories().size();
		double distrib = Math.sqrt((double)cant);
		double ajuste = distrib * proporcion;
		
		int cantW=(int)ajuste>cant?cant:(int)(cant/Math.ceil((cant/ajuste)));
		int cantH=cantW==0?1:cant/cantW;
		int sizeW1 = cantW==0?width:(width/cantW);
		int sizeH1 = cantH==0?height:(height/cantH)-35;
		int sizeW = sizeW1<sizeH1?sizeW1:sizeH1;
		
//				s+="<div class=\"graph_title\"  id=\"span_title_"+JTools.encodeString2(this.getTitle())+"\" >"+JTools.encodeString2(this.getTitle())+"</div>\n";
		String extraStyle = getCategories().size()>1?"float: left;":"";

		JIterator<Categories> icat = getCategories().getValueIterator();
		while (icat.hasMoreElements()) {
			int i =1;
			Categories cat = icat.nextElement();
			JIterator<Dataset> ids = getDatasets().getValueIterator();
			decla+="<div style=\""+extraStyle+"\">";
			if (!cat.getName().equals(this.getTitle())) 
				decla+="<div id=\"span_title_"+cat.hashCode()+"\" style=\"height:15px;text-align:center;\">"+JTools.encodeString2(cat.getName())+"</div>\n";
			else 
				decla+="<div id=\"span_title_"+cat.hashCode()+"\" style=\"text-align:center;\"></div>\n";
			decla+="<div id=\"span_aster_"+cat.hashCode()+"\"  style=\"width:"+sizeW+"px;height:"+sizeW+"px;\"></div></div>";
			defin="[";
			while (ids.hasMoreElements()) { 
				Dataset	 ds = ids.nextElement();
				JIterator<Value> iv = ds.getValues().getValueIterator();
				while (iv.hasMoreElements()) {
					Value v = iv.nextElement();
					if (!v.getCategorie().equals(cat.getName()))
						continue;
				  defin+= (i==1?"":",")+"{\"id\":"+i+",\"order\":"+i+",\"color\":'#"+ds.getColor()+"',\"value\":"+(v.getData().toString().equals("")?"0":v.getData().toString())+",\"label\":'"+JTools.encodeString2(ds.getColname())+"'}";
					i++;
				}		
			}
			defin+="]";
			
			script+="addRadialBar('span_aster_"+cat.hashCode()+"',"+i+","+defin+");\n";
			c++;
			if (c>9) break;//maximo 10;
		}

		s+=decla;
		s+="<script type=\"text/javascript\" >\n";
		s+=script;
		s+="\n</script>\n";
		
		return s;
	}


	@Override
	public String getSWF() {
		return null;
	}
	public String getImage(int width, int height) throws Exception {
		

		return "script:"+getScript(width,height);
	}
	public String isTimeLine() throws Exception {
		return TIME_NO;
	}
}
