package pss.core.graph;

/**
 * Los trendLines son lineas en los graficos.
 * @author ricardo.lamberti
 *
 */
public class TrendLines {

  double startValue;
  double endValue;
  String color;
  String displayValue;
  int thickness;
  boolean isTrendZone;
  boolean showOnTop;
  int alpha;
  
	public TrendLines(double startValue, double endValue, String color, String displayValue, int thickness, boolean isTrendZone, boolean showOnTop, int alpha) {
		super();
		this.startValue=startValue;
		this.endValue=endValue;
		this.color=color;
		this.displayValue=displayValue;
		this.thickness=thickness;
		this.isTrendZone=isTrendZone;
		this.showOnTop=showOnTop;
		this.alpha=alpha;
	}

	
	public double getStartValue() {
		return startValue;
	}

	
	public void setStartValue(double startValue) {
		this.startValue=startValue;
	}

	
	public double getEndValue() {
		return endValue;
	}

	
	public void setEndValue(double endValue) {
		this.endValue=endValue;
	}

	
	public String getColor() {
		return color;
	}

	
	public void setColor(String color) {
		this.color=color;
	}

	
	public String getDisplayValue() {
		return displayValue;
	}

	
	public void setDisplayValue(String displayValue) {
		this.displayValue=displayValue;
	}

	
	public int getThickness() {
		return thickness;
	}

	
	public void setThickness(int thickness) {
		this.thickness=thickness;
	}

	
	public boolean isTrendZone() {
		return isTrendZone;
	}

	
	public void setTrendZone(boolean isTrendZone) {
		this.isTrendZone=isTrendZone;
	}

	
	public boolean isShowOnTop() {
		return showOnTop;
	}

	
	public void setShowOnTop(boolean showOnTop) {
		this.showOnTop=showOnTop;
	}

	
	public int getAlpha() {
		return alpha;
	}

	
	public void setAlpha(int alpha) {
		this.alpha=alpha;
	}

}
