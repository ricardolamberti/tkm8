package pss.core.graph.implementations;

import java.io.ByteArrayOutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Value;
import pss.core.tools.collections.JIterator;



public class GraphPie3D extends GraphVector {

	public String getSWF() {
		return "Pie3D.swf";
	}
	
	public GraphPie3D() {
	}
	
	public GraphPie3D(String title) {
		super(title);
	}


	public String getImage(int pwidth, int pheight) throws Exception {
		int width = pwidth==-1?600:pwidth;
		int height = pheight==-1?600:pheight;
    DefaultPieDataset dataset = new DefaultPieDataset();
		JIterator<Dataset> ids = getDatasets().getValueIterator();
		while (ids.hasMoreElements()) {
			Dataset ds = ids.nextElement();
			JIterator<Value> iv = ds.getValues().getValueIterator();
			while (iv.hasMoreElements()) {
				Value v = iv.nextElement();
		    dataset.setValue(ds.getColname(), Double.parseDouble(v.getData().toString().equals("")?"0":v.getData().toString()));
			}		
		}

		JFreeChart chart = ChartFactory.createPieChart3D(getTitle(), dataset, true, true, false); 

		ByteArrayOutputStream oOutput = new ByteArrayOutputStream();
    ChartUtilities.writeChartAsJPEG(oOutput, chart, width, height, null);
    oOutput.close();

    
		return "data:image/jpg;base64,"+(java.util.Base64.getEncoder().encodeToString(oOutput.toByteArray()).replaceAll("\r\n", ""));
	}
	public String isTimeLine() throws Exception {
		return TIME_NO;
	}
}
