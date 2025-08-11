package pss.core.graph.implementations;

import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Value;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;

public class GraphScriptMedioReloj  extends GraphVector {

	long max=100;
	long valor=0;
	String leyenda=null;
	long size=-1;
	boolean modeDataset= true;
	boolean incrementWidth = false;

	public boolean isIncrementWidth() {
		return incrementWidth;
	}

	public void setIncrementWidth(boolean incrementWidth) {
		this.incrementWidth = incrementWidth;
	}

	public String getLeyenda() {
		return leyenda;
	}

	public void setLeyenda(String title) {
		this.leyenda = title;
	}

	public long getValor() {
		return valor;
	}

	public void setValor(long vaue) {
		modeDataset=false;
		this.valor = vaue;
	}
	
	public long getMax() {
		return max;
	}

	public void setMax(long max) {
		this.max = max;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getScript(int width, int height) throws Exception {
		String s="";
		String decla="";
		String defin="";
		
		size = getDatasets().size();
		if (size==0) size=1;
		int i =1;
		decla+="<div class=\"graph_title\" id=\"span_title_"+this.hashCode()+"\" >"+JTools.encodeString2(this.getTitle())+"</div>\n";
		if (!modeDataset) {
			String leyenda = getLeyenda();
			if (leyenda==null)
				leyenda = "";
			defin+="	middleGauge('span_"+this.hashCode()+"','"+leyenda+"',"+getValor()+",1);\n";
			decla+="<span id=\"span_"+this.hashCode()+"\"></span>\n";
		} else {
			JIterator<Dataset> ids = getDatasets().getValueIterator();
			while (ids.hasMoreElements()) {
				i=1;
				Dataset ds = ids.nextElement();
				JIterator<Value> iv = ds.getValues().getValueIterator();
				while (iv.hasMoreElements()) {
					//if (i>cantW) break;
					Value v = iv.nextElement();
					String name = JTools.getValidFilename(ds.getName());
					defin+="	middleGauge('span_"+name+"','"+( ds.getName().replaceAll("_DATASET", ""))+"',"+(v.getData().toString().equals("")?"0":v.getData().toString())+","+ getDatasets().size()+");\n";
				//	decla+="<div id=\"span_title_"+name+"\" style=\"height:15px;text-align:center;\">"+( ds.getName().replaceAll("_DATASET", ""))+"</div>\n";
					decla+="<span id=\"span_"+name+"\" ></span>";
					i++;
				}		
			}
		}
		s+=decla;
		
		s+="<script type=\"text/javascript\" >\n";
		s+="function middleGauge(placeholderName, zlabel, value, psize)\n";
		s+="{\n";
		s+="	var powerGauge = MiddleGauge('#'+placeholderName,{";
		s+="		size						: psize,";
		s+="		clipWidth					: 100,";
		s+="		clipHeight					: 100,";
		s+="		ringInset					: 20,";
		s+="		ringWidth					: 20,";
		s+="		incrementWidth					: "+(isIncrementWidth()?"true":"false")+",";
		s+="		pointerWidth				: 5,";
		s+="		pointerTailLength			: 5,";
		s+="		pointerHeadLengthPercent	: 0.9,";
		s+="		minValue					: 0,";
		s+="		maxValue					: "+getMax()+",";
		s+="		minAngle					: -90,";
		s+="		maxAngle					: 90,";
		s+="		transitionMs				: 750,";
		s+="		majorTicks					: 5,";
		s+="		labelFormat					: d3.format(',g'),";
		s+="		labelInset					: 10,";
		s+="		label					      : zlabel,";
		s+="		arcColorFn					: d3.interpolateHsl(d3.rgb('#b5e4e1'), d3.rgb('#275599'))";
		s+="	});\n";
		s+="	powerGauge.render();\n";
		s+="	powerGauge.update(value);\n";
		s+=" }\n";
		s+=defin;
		s+="</script>\n";
		
		
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
