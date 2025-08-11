package pss.core.graph.implementations;

import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Value;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class GraphScriptLiquid extends GraphVector {

	long max=100;
	long valor=0;
	String leyenda="";
	long size=-1;
	boolean modeDataset= true;
	
	int minValue= 0;
	int maxValue= 100;
	double circleThickness= 0.05;
	double circleFillGap= 0.05;
	String circleColor="#178BCA";
	double waveHeight= 0.05;
	int waveCount= 1;
	int waveRiseTime= 1000;
	int waveAnimateTime= 18000;
	boolean waveRise= true;
	boolean waveHeightScaling= true;
	boolean waveAnimate= true;
	String waveColor= "#178BCA";
	int waveOffset= 0;
	double textVertPosition= .5;
	double textSize= 1;
	boolean valueCountUp= true;
	boolean displayPercent= true;
	String textColor= "#045681";
	String waveTextColor= "#A4DBf8";

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

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public int getMaxiValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public double getCircleThickness() {
		return circleThickness;
	}

	public void setCircleThickness(double circleThickness) {
		this.circleThickness = circleThickness;
	}

	public double getCircleFillGap() {
		return circleFillGap;
	}

	public void setCircleFillGap(double circleFillGap) {
		this.circleFillGap = circleFillGap;
	}

	public String getCircleColor() {
		return circleColor;
	}

	public void setCircleColor(String circleColor) {
		this.circleColor = circleColor;
	}

	public double getWaveHeight() {
		return waveHeight;
	}

	public void setWaveHeight(double waveHeight) {
		this.waveHeight = waveHeight;
	}

	public int getWaveCount() {
		return waveCount;
	}

	public void setWaveCount(int waveCount) {
		this.waveCount = waveCount;
	}

	public int getWaveRiseTime() {
		return waveRiseTime;
	}

	public void setWaveRiseTime(int waveRiseTime) {
		this.waveRiseTime = waveRiseTime;
	}

	public int getWaveAnimateTime() {
		return waveAnimateTime;
	}

	public void setWaveAnimateTime(int waveAnimateTime) {
		this.waveAnimateTime = waveAnimateTime;
	}

	public boolean isWaveRise() {
		return waveRise;
	}

	public void setWaveRise(boolean waveRise) {
		this.waveRise = waveRise;
	}

	public boolean isWaveHeightScaling() {
		return waveHeightScaling;
	}

	public void setWaveHeightScaling(boolean waveHeightScaling) {
		this.waveHeightScaling = waveHeightScaling;
	}

	public boolean isWaveAnimate() {
		return waveAnimate;
	}

	public void setWaveAnimate(boolean waveAnimate) {
		this.waveAnimate = waveAnimate;
	}

	public String getWaveColor() {
		return waveColor;
	}

	public void setWaveColor(String waveColor) {
		this.waveColor = waveColor;
	}

	public int getWaveOffset() {
		return waveOffset;
	}

	public void setWaveOffset(int waveOffset) {
		this.waveOffset = waveOffset;
	}

	public double getTextVertPosition() {
		return textVertPosition;
	}

	public void setTextVertPosition(double textVertPosition) {
		this.textVertPosition = textVertPosition;
	}

	public double getTextSize() {
		return textSize;
	}

	public void setTextSize(double textSize) {
		this.textSize = textSize;
	}

	public boolean isValueCountUp() {
		return valueCountUp;
	}

	public void setValueCountUp(boolean valueCountUp) {
		this.valueCountUp = valueCountUp;
	}

	public boolean isDisplayPercent() {
		return displayPercent;
	}

	public void setDisplayPercent(boolean displayPercent) {
		this.displayPercent = displayPercent;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public String getWaveTextColor() {
		return waveTextColor;
	}

	public void setWaveTextColor(String waveTextColor) {
		this.waveTextColor = waveTextColor;
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
			
		if (getSize()==-1) size = sizeW>height-15?(int)((height-15)*.9):(int)(sizeW*.9);
		
		int i =1;
		decla+="<div class=\"graph_title\" id=\"span_title_"+this.hashCode()+"\" >"+JTools.encodeString2(this.getTitle())+"</div>\n";
		if (!modeDataset) {
			defin+="	loadLiquidFillGauge('span_"+this.hashCode()+"','',"+getValor()+",";
			defin+="			{ ";
			defin+="	minValue: "+minValue+",  ";
			defin+="  maxValue: "+maxValue+",  ";
			defin+="  circleThickness:  "+circleThickness+",  ";
			defin+="  circleFillGap:  "+circleFillGap+",  ";
			defin+="  circleColor: \""+circleColor+"\",  ";
			defin+="  waveHeight:  "+waveHeight+",  ";
			defin+="  waveCount:  "+waveCount+",  ";
			defin+="  waveRiseTime:  "+waveRiseTime+",  ";
			defin+="  waveAnimateTime:  "+waveAnimateTime+",  ";
			defin+="  waveRise: "+(waveRise?"true":"false")+",  ";
			defin+="  waveHeightScaling: "+(waveHeightScaling?"true":"false")+",  ";
			defin+="  waveAnimate: "+(waveAnimate?"true":"false")+",  ";
			defin+="  waveColor: \""+waveColor+"\",  ";
			defin+="  waveOffset:  "+waveOffset+",  ";
			defin+="  textVertPosition:  "+textVertPosition+",  ";
			defin+="  textSize:  "+JTools.rd(textSize,2)+",  ";
			defin+="  valueCountUp: "+(valueCountUp?"true":"false")+",  ";
			defin+="  displayPercent: "+(displayPercent?"true":"false")+",  ";
			defin+="  textColor: \""+textColor+"\",  ";
			defin+="  waveTextColor: \""+waveTextColor+"\"  ";
			defin+="} ";
			defin+=");\n";
			decla+="<svg id=\"span_"+this.hashCode()+"\" width=\""+size+"\" height=\""+size+"\"></svg>\n";
		} else {
			JIterator<Dataset> ids = getDatasets().getValueIterator();
			while (ids.hasMoreElements()) {
				i=1;
				Dataset ds = ids.nextElement();
				JIterator<Value> iv = ds.getValues().getValueIterator();
				while (iv.hasMoreElements()) {
					//if (i>cantW) break;
					Value v = iv.nextElement();
					String name = getDatasetName(ds.getName().replaceAll("_DATASET", ""));
					String zone = JTools.getValidFilename(name);
					defin+="	loadLiquidFillGauge('span_"+zone+"','"+name+"',"+(v.getData().toString().equals("")?"0":v.getData().toString())+",";
					defin+="			{ ";
					defin+="	minValue: "+minValue+",  ";
					defin+="  maxValue: "+maxValue+",  ";
					defin+="  circleThickness:  "+circleThickness+",  ";
					defin+="  circleFillGap:  "+circleFillGap+",  ";
					defin+="  circleColor: \""+getMyColors(i,name)+"\",  ";
					defin+="  waveHeight:  "+waveHeight+",  ";
					defin+="  waveCount:  "+waveCount+",  ";
					defin+="  waveRiseTime:  "+waveRiseTime+",  ";
					defin+="  waveAnimateTime:  "+waveAnimateTime+",  ";
					defin+="  waveRise: "+(waveRise?"true":"false")+",  ";
					defin+="  waveHeightScaling: "+(waveHeightScaling?"true":"false")+",  ";
					defin+="  waveAnimate: "+(waveAnimate?"true":"false")+",  ";
					defin+="  waveColor: \""+getMyColors(i,name)+"\",  ";
					defin+="  waveOffset:  "+waveOffset+",  ";
					defin+="  textVertPosition:  "+textVertPosition+",  ";
					defin+="  textSize:  "+JTools.rd(textSize,2)+",  ";
					defin+="  valueCountUp: "+(valueCountUp?"true":"false")+",  ";
					defin+="  displayPercent: "+(displayPercent?"true":"false")+",  ";
					defin+="  textColor: \""+textColor+"\",  ";
					defin+="  waveTextColor: \""+waveTextColor+"\"  ";
					defin+="} ";
					defin+=");\n";
					decla+="<svg id=\"span_"+zone+"\" width=\""+size+"\" height=\""+size+"\"></svg>\n";
					i++;
				}		
			}
		}
		s+=decla;
	  s+="<script type=\"text/javascript\" >\n";
		s+=defin;
		s+="</script>\n";

		
		return s;
	}

	public String getDatasetName(String name) throws Exception {
		return name;
	}
	JMap<String,String> colors;
	public JMap<String,String> getMyColors() throws Exception {
		if (colors!=null) return colors;
		colors=JCollectionFactory.createMap();
		colors.addElement("1", "#1f77b4");
		colors.addElement("2", "#ff7f0e");
		colors.addElement("3", "#2ca02c");
		colors.addElement("4", "#d62728");
		colors.addElement("5", "#9467bd");
		colors.addElement("6", "#8c564b");
		colors.addElement("7", "#e377c2");
		colors.addElement("8", "#7f7f7f");
		colors.addElement("9", "#bcbd22");
		colors.addElement("10", "#17becf");
		return colors;
	}
	public void addMyColors(String dataset,String color) throws Exception {
		getMyColors().addElement(dataset, color);
	}
	public String getMyColors(int pos,String dataset) throws Exception {
		String color = getMyColors().getElement(dataset);
		if (color==null) {
			int i = ((pos-1)%getMyColors().size())+1;
			color = getMyColors().getElement(""+i);
		}
		return color;
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
