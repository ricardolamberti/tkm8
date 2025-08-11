package pss.core.graph.implementations;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import pss.common.security.BizUsuario;
import pss.core.graph.analize.Categories;
import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Ranking;
import pss.core.graph.analize.Value;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;

public class GraphScriptPie extends GraphMatrix {

	public String getSWF() {
		return "FCF_Pie2D.swf";
	}

	public GraphScriptPie() {
	}

	public GraphScriptPie(String title) {
		super(title);
	}
	
	public String getLink(Categories categoria, Dataset ds, Value v) throws Exception {
		if (categoria!=null)
			return categoria.getName() +";"+ds.getColname()+"="+v.getData();
		return ds.getColname()+"="+v.getData();
	}


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
		int sizeW1 = cantW==0?width:(width/cantW)-10;
		int sizeH1 = cantH==0?height:(height/cantH)-35;
		int sizeW = sizeW1<sizeH1?sizeW1:sizeH1;
		
		s+="<div class=\"graph_title\"  id=\"span_title_"+JTools.encodeString2(this.getTitle())+"\" >"+JTools.encodeString2(this.getTitle())+"</div>\n";

		ArrayList<Ranking> valores = new ArrayList<Ranking>();
		int j=0;
		String extraStyle = getCategories().size()>1?"float: left;":"";
		JIterator<Categories> icat = getCategories().getValueIterator();
		while (icat.hasMoreElements()||j==0) {
			j++;
			String cate="";
			long code=0;
			Categories cat=null;
			if (icat.hasMoreElements()) {
				cat = icat.nextElement();
				cate= cat.getName();
				code= cat.hashCode();
				
			}else{
				cate="";
				code=getCategories().hashCode();
			}
	//		decla+="<div id=\"span_title_"+code+"\" style=\"height:15px;text-align:center;\">"+(cate.equals(this.getTitle())?"":JTools.encodeString2(cate))+"</div>";
			String leyenda = (cate.equals(this.getTitle())?"":JTools.encodeString2(cate));
			decla+="<div style=\""+extraStyle+"\">";
			decla+="<svg id=\"span_pie_"+code+"\" style=\"width:100%;height:100%;\"></svg></div>";
			JIterator<Dataset> ids = getDatasets().getValueIterator();
			while (ids.hasMoreElements()) { 
				Dataset	 ds = ids.nextElement();
				JIterator<Value> iv = ds.getValues().getValueIterator();
				while (iv.hasMoreElements()) {
					Value v = iv.nextElement();
					if (!cate.equals("")&&!v.getCategorie().equals(cate))
						continue;
					valores.add(new Ranking( Double.parseDouble((v.getData().toString().equals("")?"0":v.getData().toString())),ds.getColname().trim().equals("")?"[sin valor]":ds.getColname(),null,getLink(cat,ds,v)));
				}		
			}
		
			Collections.sort(valores, new Comparator<Ranking>() {
				@Override
				public int compare(Ranking p1, Ranking p2) {
					return new Double(p2.orden).compareTo(new Double(p1.orden));
				}
			});;
			
			
			
			int i =1;
			double acum=0;
			defin="[";
			for (Ranking rank:valores) {
				if (i<10 || valores.size()==10) 
					defin+= (i==1?"":",")+"{key:\""+rank.cadena+"\",y:"+rank.orden+",link:\""+rank.link+"\"}";
				else 
					acum+=rank.orden;
				i++;
			}
			if (acum>0)
				defin+= (i==1?"":",")+"{key:\""+BizUsuario.getMessage("Otros", null)+"\",y:"+acum+",link:\"\"}";
			defin+="]";
			
			script+="Pie('span_pie_"+code+"','"+leyenda+"',"+sizeW+","+sizeW+","+defin+");\n";
			c++;
			if (c>9) break;//maximo 10;
		}

		s+=decla;
		s+="<script type=\"text/javascript\" >\n";
		s+=script;
		s+="\n</script>\n";
		
		return s;
  }

	public String getImage(int width, int height) throws Exception {

		return "script:" + getScript(width, height);
	}
	public String isTimeLine() throws Exception {
		return TIME_NO;
	}
}
