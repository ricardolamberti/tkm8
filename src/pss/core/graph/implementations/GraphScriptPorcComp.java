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

public class GraphScriptPorcComp extends GraphMatrix {

	public String getSWF() {
		return "FCF_Pie2D.swf";
	}

	public GraphScriptPorcComp() {
	}

	public GraphScriptPorcComp(String title) {
		super(title);
	}
	
	float porcentaje;
	
	

// este dato,hay que incluirlo en la estructura de categoria y dataset
	public float getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(float porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getScript(int width, int height) throws Exception {
		int c=0;
		String s="";
		String decla="";
		String defin="";
		String script="";
		if (height*3<width) width=height*3;
		double proporcion = (double)width/(double)height;
		int cant = getCategories().size();
		int sizeW =0;
		if (cant>1) {
			double distrib = Math.sqrt((double)cant);
			double ajuste = distrib * proporcion;
			
			int cantW=(int)ajuste>cant?cant:(int)(cant/Math.ceil((cant/ajuste)));
			int cantH=cantW==0?1:cant/cantW;
			int sizeW1 = cantW==0?width:(width/cantW)-10;
			int sizeH1 = cantH==0?height:(height/cantH)-35;
			sizeW = sizeW1<sizeH1?sizeW1:sizeH1;
		} else {
			sizeW = width;
		}
		
		s+="<div class=\"graph_title\"  id=\"span_title_"+JTools.encodeString2(this.getTitle())+"\" >"+JTools.encodeString2(this.getTitle())+"</div>\n";

		ArrayList<Ranking> valores = new ArrayList<Ranking>();
		int j=0;
		String extraStyle = getCategories().size()>1?"float: left;":"";
		JIterator<Categories> icat = getCategories().getValueIterator();
		while (icat.hasMoreElements()||j==0) {
			j++;
			String cate="";
			long code=0;
			if (icat.hasMoreElements()) {
				Categories cat = icat.nextElement();
				cate= cat.getName();
				code= cat.hashCode();
				
			}else{
				cate="";
				code=getCategories().hashCode();
			}
			decla+="<div style=\""+extraStyle+"width:"+(sizeW+5)+"px;\">";
			decla+="<div id=\"span_title_"+code+"\" style=\"height:15px;text-align:center;\">"+(cate.equals(this.getTitle())?"":JTools.encodeString2(cate))+"</div>\n";
			decla+="<svg id=\"span_pie_"+code+"\" style=\"width:"+sizeW+"px;height:"+sizeW+"px;\"></svg></div>";
			JIterator<Dataset> ids = getDatasets().getValueIterator();
			while (ids.hasMoreElements()) { 
				Dataset	 ds = ids.nextElement();
				JIterator<Value> iv = ds.getValues().getValueIterator();
				while (iv.hasMoreElements()) {
					Value v = iv.nextElement();
					if (!cate.equals("")&&!v.getCategorie().equals(cate))
						continue;
					valores.add(new Ranking( Double.parseDouble((v.getData().toString().equals("")?"0":v.getData().toString())),ds.getColname().trim().equals("")?"[sin valor]":JTools.encodeString2(ds.getColname())));
				}		
			}
		
			Collections.sort(valores, new Comparator<Ranking>() {
				@Override
				public int compare(Ranking p1, Ranking p2) {
					return new Double(p2.orden).compareTo(new Double(p1.orden));
				}
			});;
			/* 
			var porcJSON = {
					overview: {
					"london": 100,
					"southwark": 25
					},
					breakdown: {
					"burglary": 45,
					"violent": 35,
					"domestic": 10,
					"rape": 3
					}
					};*/
			
			int i =1;
			double acum=0;
			int k=5;
			defin="{";
				defin+= "\"overview\": { \"Total\":100,\"Alcanzado\":"+JTools.rd(getPorcentaje(),2)+"}, \"breakdown\": {";
			for (Ranking rank:valores) {
				if (i<10 || valores.size()==10) 
					defin+= (i==1?"":",")+"\""+rank.cadena+"\":"+(rank.orden);
				else 
					acum+=rank.orden;
				i++;
			}
			if (acum>0)
				defin+= (i==1?"":",")+"\""+BizUsuario.getMessage("Otros", null)+"\":"+acum;
			defin+="}}";
			
			script+="	createPorcComp('span_pie_"+code+"',"+defin+",{\"width\":"+(sizeW)+",\"height\":"+(sizeW/3)+"});\n";
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
