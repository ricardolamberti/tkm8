package pss.core.graph.implementations;

import java.util.Calendar;
import java.util.Iterator;
import java.util.TreeMap;

import pss.core.graph.analize.Categories;
import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Value;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;

public class GraphScriptStacked extends GraphMatrix {

	public String getSWF() {
		return "FCF_MSArea2D.swf";
	}

	public GraphScriptStacked() {
	}

	public GraphScriptStacked(String title) {
		super(title);
	}

	public String getScript(int width, int height) throws Exception {
		String s="";
		String decla="";
		String defin="";
		String script="";

		decla+="<div class=\"graph_title\" id=\"span_title_"+this.hashCode()+"\" >"+JTools.encodeString2(this.getTitle())+"</div>\n";
		decla+="<svg id=\"chart_stack_"+this.hashCode()+"\"></svg>";
		
		defin="[";
		int j=0;
		TreeMap<String,Categories>  categoriasOrdenadas = new TreeMap<String,Categories>(); 
		JIterator<Dataset> ids2 = getDatasets().getValueIterator();
		while (ids2.hasMoreElements()) {
			Dataset ds = ids2.nextElement();
			String str = ds.getName().replaceAll("_DATASET", "");
			if (str.equals(""))
				continue;
			JIterator<Categories> icat2 = getCategories().getValueIterator();
			while (icat2.hasMoreElements()) {
				Categories cat = icat2.nextElement();
				if (categoriasOrdenadas.get(cat.getName())!=null) continue;
				categoriasOrdenadas.put(cat.getName(), cat);
			}		
		}
		
		JIterator<Dataset> ids = getDatasets().getValueIterator();
		while (ids.hasMoreElements()) {
			Dataset ds = ids.nextElement();
			String str = ds.getName().replaceAll("_DATASET", "");
			if (str.equals(""))
				continue;
			defin += (j == 0 ? "" : ",") + "{key:'" + str + "',values:[";
			int i = 0;

			Iterator<Categories> icat = categoriasOrdenadas.values().iterator();
			while (icat.hasNext()) {
				Categories cat = icat.next();
				JIterator<Value> iv = ds.getValues().getValueIterator();
				boolean find=false;
				while (iv.hasMoreElements()) {
					Value v = iv.nextElement();
					if (!v.getCategorie().equals(cat.getName()))
						continue;
					if (v.getCategorie().trim().equals(""))
						continue;
					find=true;
					if (JTools.IsDateTime(v.getCategorie(), "dd/MM/yyyy", false))
						defin += (i == 0 ? "" : ",") + "[ " + JDateTools.StringToDate(v.getCategorie(), "dd/MM/yyyy").getTime() + ", " + (v.getData().toString().equals("0.0") ? null : JTools.toFloatSafe(v.getData().toString())) + "  ]";
					else if (JTools.isNumber(v.getCategorie()))
						defin += (i == 0 ? "" : ",") + "[ " + v.getCategorie() + ", " + (v.getData().toString().equals("0.0") ? null :JTools.toFloatSafe(v.getData().toString())) + "  ]";
					else if (v.getCategorie().length() == 7 && v.getCategorie().charAt(4) == '/') {
						Calendar c = Calendar.getInstance();
						c.set(Integer.parseInt(v.getCategorie().substring(0, 4)), Integer.parseInt(v.getCategorie().substring(5, 7)), 1);
						defin += (i == 0 ? "" : ",") + "[ " + c.getTime().getTime() + ", " + (v.getData().toString().equals("0.0") ? null : JTools.toFloatSafe((v.getData().toString()))) + "  ]";
					} else
						defin += (i == 0 ? "" : ",") + "[" + i + ", " + (v.getData().toString().equals("0.0") ? null : JTools.toFloatSafe(v.getData().toString())) + " ]";
					i++;
				}
				if (!find && !cat.getName().equals("")) {
					if (JTools.IsDateTime(cat.getName(), "dd/MM/yyyy", false))
						defin += (i == 0 ? "" : ",") + "[" + JDateTools.StringToDate(cat.getName(), "dd/MM/yyyy").getTime() + ", 0 ]";
					else if (JTools.isNumber(cat.getName()))
						defin += (i == 0 ? "" : ",") + "[" + cat.getName() + ", 0 ]";
					else if (cat.getName().length() == 7 && cat.getName().charAt(4) == '/') {
						Calendar c = Calendar.getInstance();
						c.set(Integer.parseInt(cat.getName() .substring(0, 4)), Integer.parseInt(cat.getName() .substring(5, 7)), 1);
						defin += (i == 0 ? "" : ",") + "[" +c.getTime().getTime()+ ", 0 ]";
					} else
						defin += (i == 0 ? "" : ",") + "[" + i + ", 0 ]";
					i++;
				}
			}
			

			defin += "]}";
			j++;
		}		
		
		defin+="]";
		script = " Stacked('chart_stack_"+this.hashCode()+"',"+width+","+height+","+defin+");\n";

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
		return TIME_IN_CATEGORY;
	}
}
