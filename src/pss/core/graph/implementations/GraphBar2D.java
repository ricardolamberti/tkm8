package pss.core.graph.implementations;

import java.io.ByteArrayOutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Value;
import pss.core.tools.collections.JIterator;



public class GraphBar2D extends GraphVector {
	public String getSWF() {
		return "FCF_Bar2D.swf";
	}
	public GraphBar2D() {
	}
	
	public GraphBar2D(String title) {
		super(title);
	}
	

	public String getImage(int pwidth, int pheight) throws Exception {
		int width = pwidth==-1?600:pwidth;
		int height = pheight==-1?600:pheight;
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		boolean largo = false;
		JIterator<Dataset> ids = getDatasets().getValueIterator();
		while (ids.hasMoreElements()) {
			Dataset ds = ids.nextElement();
			JIterator<Value> iv = ds.getValues().getValueIterator();
			while (iv.hasMoreElements()) {
				Value v = iv.nextElement();
				dataset.setValue(Float.parseFloat(v.getData().toString().equals("")?"0":v.getData().toString()),"",ds.getColname());
				largo |= ds.getColname().length()>4;
		  }		
		}
		
		JFreeChart chart = ChartFactory.createBarChart(getTitle(), getAtributtes().getElement(GRAPH_ATTR_XAXISNAME), 
				getAtributtes().getElement(GRAPH_ATTR_YAXISNAME), dataset, PlotOrientation.VERTICAL , true,true,false);
		if (largo && getDatasets().size()>2) {
			CategoryPlot myPlot = chart.getCategoryPlot();
			CategoryAxis axis = (CategoryAxis)myPlot.getDomainAxis();
			axis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		}
//		File url = new File("/dev/regtur/regtur/bin/WEB-INF/classes/pss/core/ui/Images/logos/tm.fondo.gif");
//	  if (url!=null) {
//	  	BufferedImage image = ImageIO.read(url);
//			chart.getPlot().setBackgroundImage(image);
//	  }
		 

		ByteArrayOutputStream oOutput = new ByteArrayOutputStream();
    ChartUtilities.writeChartAsJPEG(oOutput, chart, width, height, null);
    oOutput.close();

    
		return "data:image/jpg;base64,"+(java.util.Base64.getEncoder().encodeToString(oOutput.toByteArray()).replaceAll("\r\n", ""));
	}

}
