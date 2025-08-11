package pss.core.graph.implementations;

import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Value;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;

public class GraphScriptReloj extends GraphVector {

	long max=100;
	long valor=0;
	String leyenda="";
	String colorRed="#109618";
	String colorYellow="#FF9900";
	String colorGreen="#DC3912";
	double yellowZoneStart = 0.75;
	double yellowZoneEnd = 0.9;
	double redZoneStart = 0.9;
	double redZoneEnd = 1;
	boolean animated=true;
		long size=-1;
	boolean modeDataset= true;

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
	public String getColorRed() {
		return colorRed;
	}

	public void setColorRed(String colorRed) {
		this.colorRed = colorRed;
	}

	public String getColorYellow() {
		return colorYellow;
	}

	public void setColorYellow(String colorYellow) {
		this.colorYellow = colorYellow;
	}

	public String getColorGreen() {
		return colorGreen;
	}

	public void setColorGreen(String colorGreen) {
		this.colorGreen = colorGreen;
	}

	public double getYellowZoneStart() {
		return yellowZoneStart;
	}

	public void setYellowZoneStart(double yellowZoneStart) {
		this.yellowZoneStart = yellowZoneStart;
	}

	public double getYellowZoneEnd() {
		return yellowZoneEnd;
	}

	public void setYellowZoneEnd(double yellowZoneEnd) {
		this.yellowZoneEnd = yellowZoneEnd;
	}

	public double getRedZoneStart() {
		return redZoneStart;
	}

	public void setRedZoneStart(double redZoneStart) {
		this.redZoneStart = redZoneStart;
	}

	public double getRedZoneEnd() {
		return redZoneEnd;
	}

	public void setRedZoneEnd(double redZoneEnd) {
		this.redZoneEnd = redZoneEnd;
	}

	public boolean isAnimated() {
		return animated;
	}

	public void setAnimated(boolean animated) {
		this.animated = animated;
	}

	public String getScript(int width, int height) throws Exception {
		String s="";
		String decla="";
		String defin="";
		double proporcion = (double)width/(double)height;
		int cant = getDatasets().size();
		double distrib = Math.sqrt((double)cant);
		double ajuste = distrib * proporcion;
		
//		int cantW=(int)ajuste>cant?cant:(int)(cant/Math.ceil((cant/ajuste)));
		int cantW=Math.ceil(ajuste)>cant?cant:(int)Math.ceil(ajuste);
		int cantH=cantW==0?1:cant-cantW;
		int sizeW1 = cantW==0?width:(width/cantW);
		int sizeH1 = cantH==0?height:(height/cantH);
		//sizeH1*=.85;
		int sizeW = sizeW1<sizeH1?sizeW1:sizeH1;
			
		if (getSize()==-1) size = sizeW>height-15?height-15:sizeW;
		
		int i =1;
		decla+="<div class=\"graph_title\" id=\"span_title_"+this.hashCode()+"\" >"+JTools.encodeString2(this.getTitle())+"</div>\n";
		if (!modeDataset) {
			defin+="	createGauge"+this.hashCode()+"('span_"+this.hashCode()+"','"+getLeyenda()+"', '"+getLeyenda()+"',"+getValor()+");\n";
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
					defin+="	createGauge"+this.hashCode()+"('span_"+name+"','"+ds.getColname()+"', '"+ds.getColname()+"',"+(v.getData().toString().equals("")?"0":v.getData().toString())+");\n";
					decla+="<span id=\"span_"+name+"\"></span>\n";
					i++;
				}		
			}
		}
		s+=decla;

		s+="<script type=\"text/javascript\" >\n";
  	s+="var gauges"+this.hashCode()+" = [];\n";
		
		s+="function createGauge"+this.hashCode()+"(canvas,name, label, value,min, max)\n";
		s+="{\n";
		s+="	var config =\n"; 
		s+="	{\n";
		s+="		size: "+getSize()+",\n";
		s+="		value: undefined != value ? value : 0,\n";
		s+="		label: label,\n";
		s+="		min: undefined != min ? min : 0,\n";
		s+="		max: undefined != max ? max : "+getMax()+",\n";
		s+="		minorTicks: 5\n";
		s+="	}\n";
			
		s+="	var range = config.max - config.min;";
		s+="	config.yellowZones = [{ from: config.min + "+(yellowZoneStart==1?"config.max ":" range*"+yellowZoneStart)+", to: config.min + "+(yellowZoneStart==1?"config.max ":" range*"+yellowZoneEnd)+" }];\n";
		s+="	config.redZones = [{ from: config.min + "+(redZoneStart==1?"config.max ":" range*"+redZoneStart)+ ", to: "+(redZoneEnd==1?"config.max ":" range*"+redZoneEnd)+" }];\n";
		s+="	config.redColor = '"+colorRed+"';\n";
		s+="	config.yellowColor = '"+colorYellow+"';\n";
		s+="	config.greenColor = '"+colorGreen+"';\n";
			
		s+="	gauges"+this.hashCode()+"[name] = new Gauge(canvas, config);\n";
		s+="	gauges"+this.hashCode()+"[name].render();\n";
		s+="}\n";
		
		s+="function createGauges"+this.hashCode()+"()\n";
		s+="{\n";
		s+=defin;
		s+="}\n";
		
		s+="function updateGauges"+this.hashCode()+"()\n";
		s+="	{\n";
		s+="		for (var key in gauges"+this.hashCode()+")\n";
		s+="		{\n";
		s+="			var v = gauges"+this.hashCode()+"[key].getValue();\n";
		s+="			gauges"+this.hashCode()+"[key].redraw(v);\n";
		s+="	}\n";
		s+="}\n";
		
		
		s+="	createGauges"+this.hashCode()+"();\n";
		if (animated)
			s+="	setInterval(updateGauges"+this.hashCode()+", 5000);\n";
		else
			s+="	updateGauges"+this.hashCode()+"();\n";
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
