package pss.core.graph.implementations;

import pss.core.graph.analize.Categories;
import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Ranking;
import pss.core.graph.analize.Value;
import pss.core.graph.model.ModelBullet;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JOrderedMap;

public class GraphScriptBullet  extends GraphMatrix {

	public String getSWF() {
		return "FCF_Column3D.swf";
	}

	public GraphScriptBullet() {
	}

	public GraphScriptBullet(String title) {
		super(title);
	}
	
	double zone1;
	double zone2;
	double zone3;
	double zone4;
	
	public double getZone1() {
		return zone1;
	}

	public void setZone1(double zone1) {
		this.zone1 = zone1;
	}

	public double getZone2() {
		return zone2;
	}

	public void setZone2(double zone2) {
		this.zone2 = zone2;
	}

	public double getZone3() {
		return zone3;
	}

	public void setZone3(double zone3) {
		this.zone3 = zone3;
	}

	public double getZone4() {
		return zone4;
	}

	public void setZone4(double zone4) {
		this.zone4 = zone4;
	}


	public String getScript(int width, int height) throws Exception {
		int c=0;
		String s="";
		String decla="";
		String defin="";
		String script="";
		int h=20;
		int h1=0;
		
		s+="<div class=\"graph_title\"  id=\"span_title_"+JTools.encodeString(this.getTitle())+"\" >"+JTools.encodeString2(this.getTitle())+"</div>\n";

		JOrderedMap<String,Ranking> valores = JCollectionFactory.createOrderedMap();
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
		//	decla+="<div id=\"span_title_"+code+"\" style=\"height:15px;text-align:center;\">"+(cate.equals(this.getTitle())?"":JTools.encodeString2(cate))+"</div>\n";
			decla+="<div id=\"span_bullet_"+code+"\"><svg style='height:80px'> </svg></div>";
			h1=0;
			JIterator<Dataset> ids = getDatasets().getValueIterator();
			while (ids.hasMoreElements()) { 
				Dataset	 ds = ids.nextElement();
				JIterator<Value> iv = ds.getValues().getValueIterator();
				while (iv.hasMoreElements()) {
					Value v = iv.nextElement();
					if (!v.getCategorie().equals(cate))
						continue;
					Ranking r = valores.getElement(cate);
					if (r==null) {
						r= new Ranking( cate,ds.getColor());
						r.setCode(code);
						valores.addElement(cate, r);
					}
					if (ds.getType()==null) continue;
					if (ds.getType().equals(ModelBullet.GRAPH_FUNCTION_DATA)) {
						r.setDato1(Double.parseDouble((v.getData().toString().equals("")?"0":v.getData().toString())));
						r.setOrden(r.getDato1());
					}
					else if (ds.getType().equals(ModelBullet.GRAPH_FUNCTION_OBJ)) 
						r.setDato2(Double.parseDouble((v.getData().toString().equals("")?"0":v.getData().toString())));
					else if (ds.getType().equals(ModelBullet.GRAPH_FUNCTION_ZONE1)) 
						r.setDato3(Double.parseDouble((v.getData().toString().equals("")?"0":v.getData().toString())));
					else if (ds.getType().equals(ModelBullet.GRAPH_FUNCTION_ZONE2)) 
						r.setDato4(Double.parseDouble((v.getData().toString().equals("")?"0":v.getData().toString())));
					else if (ds.getType().equals(ModelBullet.GRAPH_FUNCTION_ZONE3)) 
						r.setDato5(Double.parseDouble((v.getData().toString().equals("")?"0":v.getData().toString())));
					else if (ds.getType().equals(ModelBullet.GRAPH_FUNCTION_LABELZONE1)) 
						r.setDato6(v.getData().toString());
					else if (ds.getType().equals(ModelBullet.GRAPH_FUNCTION_LABELZONE2)) 
						r.setDato7(v.getData().toString());
					else if (ds.getType().equals(ModelBullet.GRAPH_FUNCTION_LABELZONE3)) 
						r.setDato8(v.getData().toString());
				}		
			}
		}
	
			
//			  {"title":"Satisfaction","subtitle":"out of 5","ranges":[3.5,4.25,5],"measures":[3.2,4.7],"markers":[4.4]}

			int i =1;
			JIterator<Ranking> it = valores.getValueIterator();
			while (it.hasMoreElements()) {
				Ranking rank=it.nextElement();
				defin= "{\"title\":'"+rank.cadena+"',\"subtitle\":'%',\"ranges\":["+(rank.dato3==0?"":rank.dato3+",")+(rank.dato4==0?"":rank.dato4+",")+(rank.dato5==0?"":rank.dato5+",")+(rank.dato3>100||rank.dato4>100||rank.dato5>100?"0":"100")+"],\"rangeLabels\":['"+rank.getDato8()+"','"+rank.getDato7()+"','"+rank.getDato6()+"'],\"measures\":["+rank.dato1+"],\"measureLabels\":['MS'],\"markers\":["+rank.dato2+"],\"markerLabels\":['FMS']}";
				script+="addBullet('span_bullet_"+rank.getCode()+"',"+defin+",0,80);\n";
				h+=80;
				i++;
			}
		

		s+="<div style=\""+extraStyle+"width:100%;height:"+h+"\">";
		s+=decla;
		s+="</div>";
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
