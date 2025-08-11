package pss.core.graph.implementations;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueTick;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.Range;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;

import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Value;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;

public class GraphXYLine  extends GraphMatrix {

	public String getSWF() {
		return "FCF_MSLine.swf";
	}
	
	public GraphXYLine() {
	}

	public GraphXYLine(String title) {
		super(title);
	}
  /* Day of the year values for month end days. */
  public static final Integer[] MONTH_LENGTHS = {
          31,29,31,30,31,30,31,31,30,31,30,31
  };
  public static final String[] MONTH_NAMES = {
      "Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"
};
	public class DayOfYearAxis extends NumberAxis {


    protected AxisState  drawTickMarksAndLabels(Graphics2D g2,double cursor,Rectangle2D plotArea,Rectangle2D dataArea,RectangleEdge edge) {
        AxisState state = new AxisState(cursor);

        g2.setFont(getTickLabelFont());

        double ol = getTickMarkOutsideLength();
        double il = getTickMarkInsideLength();
        int y = (int)(Math.round(cursor-ol));
        LineMetrics lineMetrics = g2.getFont().getLineMetrics("Ápr", g2.getFontRenderContext());        
        int h = (int) (lineMetrics.getHeight() + 6);

        List<ValueTick> ticks = refreshTicks(g2, state, dataArea, edge);
        state.setTicks(ticks);

        /* Last x point */
        ValueTick tick = ticks.get(ticks.size()-1);     
        float[] prevAnchorPoint = calculateAnchorPoint(tick, cursor,dataArea, edge);
        double xmax = prevAnchorPoint[0];
        double max_day = tick.getValue();

        /* First x point */
        tick = ticks.get(0);
        prevAnchorPoint = calculateAnchorPoint(tick, cursor,dataArea, edge);
        double xmin = Math.round(prevAnchorPoint[0]);
        double min_day = tick.getValue();       
        double days_visible = max_day - min_day + 1;
        /* 0.1 day horizontal gap. */
        double gap = 0.1*(xmax-xmin)/days_visible;

        
        g2.setFont(getTickLabelFont());
        g2.setColor(Color.BLACK);
        int start_day = 0;
        for (int month=0;month<12;month++) {
            int end_day = start_day + MONTH_LENGTHS[month] - 1;
            System.out.println("start-end "+start_day+" "+end_day);
            if ( (start_day>=min_day) && (start_day<=max_day) && (end_day>=min_day) && (end_day<=max_day) ) {
                double factor_x1 = (start_day - min_day) / days_visible;
                double x1 = xmin + (xmax-xmin)* factor_x1;
                double factor_x2 = (end_day - min_day) / days_visible;
                double x2 = xmin + (xmax-xmin)* factor_x2;
                System.out.println("month="+month+", start_day="+start_day+" end_day="+end_day+" x1="+x1+" x2="+x2);
                g2.setColor(Color.LIGHT_GRAY);
                g2.fill3DRect((int)(x1+gap),y,(int)(x2-x1-2*gap),h,true);
                g2.setColor(Color.BLACK);
                TextUtilities.drawAlignedString(MONTH_NAMES[month], g2, (float)((x1+x2)/2), (float)(y+ol), TextAnchor.TOP_CENTER);
            }           
            start_day += MONTH_LENGTHS[month];
        }
        return state;
    }

   }
	public String getImage(int pwidth, int pheight) throws Exception {
		int width = pwidth==-1?600:pwidth;
		int height = pheight==-1?600:pheight;
	   XYSeriesCollection dataset = new XYSeriesCollection();
	   double min=366;
	   double max=0; 
			JIterator<Dataset> ids = getDatasets().getValueIterator();
			while (ids.hasMoreElements()) {
				Dataset ds = ids.nextElement();
				String str = ds.getName().replaceAll("_DATASET", "");

			  XYSeries series1 = new XYSeries(str);
			 	JIterator<Value> iv = ds.getValues().getValueIterator();
				while (iv.hasMoreElements()) {
					Value v = iv.nextElement();
					Date fecha =null;
					if (JTools.IsDateTime(v.getCategorie(), "dd/MM/yyyy", false)) 
						fecha= (JDateTools.StringToDate(v.getCategorie(),"dd/MM/yyyy"));
					else	if (JTools.IsDateTime(v.getCategorie(), "dd-MM-yyyy", false)) 
						fecha= (JDateTools.StringToDate(v.getCategorie(),"dd-MM-yyyy"));
					else 	if (JTools.IsDateTime(v.getCategorie(), "yyyy-MM-dd", false)) 
						fecha= (JDateTools.StringToDate(v.getCategorie(),"yyyy-MM-dd"));
					else 	if (JTools.IsDateTime(v.getCategorie(), "yyyy/MM", false)) 
						fecha= (JDateTools.StringToDate(v.getCategorie()+"/01","yyyy/MM/dd"));
					else 	if (JTools.IsDateTime(v.getCategorie(), "yyyy-MM", false)) 
						fecha= (JDateTools.StringToDate(v.getCategorie()+"-01","yyyy-MM-dd"));
					else if (JTools.isNumber(v.getCategorie())) {
						Calendar c = Calendar.getInstance();
						c.set(Calendar.YEAR, (int)JDateTools.getAnioActual());
						c.set(Calendar.DAY_OF_YEAR,Integer.parseInt(v.getCategorie()));
						c.set(Calendar.HOUR, 0);
						c.set(Calendar.MINUTE, 0);
						c.set(Calendar.SECOND, 0);
						c.set(Calendar.MILLISECOND, 0);
						fecha=c.getTime();
					}
					

					double day;
					if (fecha!=null) {
						Calendar c = Calendar.getInstance();
						c.setTime(fecha);
						day = c.get(Calendar.DAY_OF_YEAR);
					} else
						day=(double)JTools.getLongNumberEmbedded(v.getCategorie());
					if (day<=min) min=day;
					if (day>=max) max=day;
					series1.add(day,v.getData().toString().equals("0.0")?null:Float.parseFloat(v.getData().toString()));
			  }		
				dataset.addSeries(series1);
			}
		  
			JFreeChart chart = ChartFactory.createXYLineChart(getTitle(), getAtributtes().getElement(GRAPH_ATTR_XAXISNAME), 
				getAtributtes().getElement(GRAPH_ATTR_YAXISNAME), dataset, PlotOrientation.VERTICAL , true,true,false);
		  chart.setBackgroundPaint(Color.WHITE);
		  chart.setBackgroundImageAlpha(0);
	    DayOfYearAxis doyAxis = new DayOfYearAxis();
	    doyAxis.setAutoRange(false);
	    doyAxis.setRange(new Range(min==366?1:min-1, max==0?365:max+30));
	    
	    chart.getXYPlot().setDomainAxis(doyAxis);     

	    ByteArrayOutputStream oOutput = new ByteArrayOutputStream();
	  ChartUtilities.writeChartAsJPEG(oOutput, chart, width, height, null);
	  oOutput.close();
	
	  
		return "data:image/jpg;base64,"+(java.util.Base64.getEncoder().encodeToString(oOutput.toByteArray()).replaceAll("\r\n", ""));
	}
	public String isTimeLine() throws Exception {
		return TIME_IN_CATEGORY;
	}
}
