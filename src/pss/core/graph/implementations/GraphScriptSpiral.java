package pss.core.graph.implementations;

import java.util.Calendar;
import java.util.Date;

import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Value;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;

public class GraphScriptSpiral extends GraphMatrix {
  int windowsStart=-1;
  int windowsEnd=-1;
  double min=-1;
  boolean withFocus=true;

	String leyendaMax="Max";
  String leyendaMin="Min";
  String image;
  
  public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	class JMultiRef {
  	JMultiRef(String l,double v,String c) {
  		leyenda =l;
  		valor=v;
  		color=c;
  	}
  	String leyenda;
  	double valor;
  	String color;
  }
  JList<JMultiRef> refs;
	public String getLeyendaMax() {
		return leyendaMax;
	}

	public void setLeyendaMax(String leyendaMax) {
		this.leyendaMax = leyendaMax;
	}

	public String getLeyendaMin() {
		return leyendaMin;
	}

	public void setLeyendaMin(String leyendaMin) {
		this.leyendaMin = leyendaMin;
	}
  public boolean isWithFocus() {
		return withFocus;
	}

	public void setWithFocus(boolean withFocus) {
		this.withFocus = withFocus;
	}
	double max=-1;
	String colorMax;
	public String getColorMax() {
		return colorMax==null?"#ff0000":colorMax;
	}

	public void clearRefs() {
		refs = JCollectionFactory.createList();
	}
	public void addRef(String leyenda, double value, String color) {
		refs.addElement(new JMultiRef(leyenda, value, color));
	}
	public void setColorMax(String colorMax) {
		this.colorMax = colorMax;
	}

	public String getColorMin() {
		return colorMin==null?"#0000ff":colorMin;
	}

	public void setColorMin(String colorMin) {
		this.colorMin = colorMin;
	}
	String colorMin;

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}
  public int getWindowsStart() {
		return windowsStart;
	}


	public void setWindowsStart(int windowsStart) {
		this.windowsStart = windowsStart;
	}


	public int getWindowsEnd() {
		return windowsEnd;
	}


	public void setWindowsEnd(int windowsEnd) {
		this.windowsEnd = windowsEnd;
	}
	
	public String getScript(int width, int height) throws Exception {
		return this.getScript(width, height, false, null);

	}

	public String getDatasetName(String prefixCateg, String name) throws Exception {

		if (prefixCateg != null)
			name = prefixCateg + " " + name;

		return name;

	}

	public boolean canUseWithinCategory() throws Exception {
		return true;
	}
	public String getScript(int width, int height, boolean xdate, String prefixCateg) throws Exception {
		String s = "";
		String decla = "";
		String defin = "";
		String script = "";

		decla += "<div class=\"graph_title\" id=\"span_title_" + this.hashCode() + "\" >" + JTools.encodeString2(this.getTitle()) + "</div>\n";

		Date fechaIni = null;
		Date fechaFin = null;
		// debo csalcular fecha fin y fecha ini y luego hacer bien las lineas de min
		// y max
		int j = 0,i=0;
		defin = "[";
		JIterator<Dataset> ids = getDatasets().getValueIterator();
		while (ids.hasMoreElements()) {
			Dataset ds = ids.nextElement();
			JIterator<Value> iv = ds.getValues().getValueIterator();
			String name = "";
			// someData.push({
			// date: currentDate,
			// value: Math.random(),
			// group: currentDate.getMonth()
			// });
			while (iv.hasMoreElements()) {
				Value v = iv.nextElement();
				Date fecha = null;
				String strfecha = v.getCategorie();
				String group = ds.getColname();
				if (!JDateTools.CheckDatetime(strfecha,false)) {
					defin += "";
					strfecha = ds.getColname();
					group=null;
				}
				if (strfecha.equals(""))
					defin += "";
				else if (JTools.IsDateTime(strfecha, "dd/MM/yyyy", false))
					fecha = (JDateTools.StringToDate(strfecha, "dd/MM/yyyy"));
				else if (xdate) {
					Calendar c = Calendar.getInstance();
					c.setTimeInMillis(Long.parseLong(v.getCategorie()));
					fecha = c.getTime();

				} else if (JTools.isNumber(v.getCategorie())) {
					Calendar c = Calendar.getInstance();
					c.set(Calendar.YEAR, (int) JDateTools.getAnioActual());
					c.set(Calendar.DAY_OF_YEAR, Integer.parseInt(v.getCategorie()));
					c.set(Calendar.HOUR, 0);
					c.set(Calendar.MINUTE, 0);
					c.set(Calendar.SECOND, 0);
					c.set(Calendar.MILLISECOND, 0);
					fecha = c.getTime();
				} else {
					Calendar c = Calendar.getInstance();
					c.set(Calendar.YEAR, (int) JDateTools.getAnioActual());
					c.set(Calendar.DAY_OF_YEAR, i);
					c.set(Calendar.HOUR, 0);
					c.set(Calendar.MINUTE, 0);
					c.set(Calendar.SECOND, 0);
					c.set(Calendar.MILLISECOND, 0);
					fecha = c.getTime();
				}

				if (fecha != null) {
					if (fechaIni == null || fechaIni.compareTo(fecha) >= 0)
						fechaIni = fecha;
					if (fechaFin == null || fechaFin.compareTo(fecha) <= 0)
						fechaFin = fecha;
					// System.out.println((i==0?"":",")+"{x:"+fecha.getTime()+",y:"+(v.getData().toString().equals("0.0")?null:Float.parseFloat(v.getData().toString()))+"
					// }");
					Calendar d = Calendar.getInstance();
					d.setTime(fecha);
					
					if (group==null) 
						group = d.get(Calendar.YEAR)+JTools.LPad(""+d.get(Calendar.MONTH),2,"0");

					defin += (i == 0 ? "" : ",") + "{date: " +fecha.getTime()+ ",value:" + (v.getData().toString().equals("0.0") ? null : Float.parseFloat(v.getData().toString())) + ", group:'" + group + "'  }";
				}
				i++;
			}

			j++;
		}
		defin += "]";
		defin = JTools.encodeString2(defin);
		decla += "<div id=\"chart_" + j + this.hashCode() + "\" class='with-3d-shadow with-transitions'><svg></svg></div>";
		script += " addSpiral('chart_" + j + this.hashCode() + "'," + defin + "," + (xdate ? "true" : "false") + ");\n";

		s += decla;
		s += "<script type=\"text/javascript\" >\n";
		s += script;
		s += "\n</script>\n";
		return s;
	}

	@Override
	public String getSWF() {
		return null;
	}
	public String getImage(int width, int height) throws Exception {
		

		return "script:"+getScript(width,height);
	}
	public String getImage(int width, int height, boolean xdate) throws Exception {
		return "script:"+getScript(width,height,xdate,null);
	}	
	public String getImage(int width, int height, boolean xdate, String prefixCateg) throws Exception {
		return "script:"+getScript(width,height,xdate,prefixCateg);
	}
	

}
