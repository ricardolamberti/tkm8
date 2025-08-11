package pss.core.graph.implementations;

import java.awt.Color;
import java.io.ByteArrayOutputStream;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;

import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Value;
import pss.core.tools.collections.JIterator;

public class GraphStCol3DLineDY extends GraphMatrix {

	public String getSWF() {
		return "FCF_StackedColumn3D.swf";
	}
	
	public GraphStCol3DLineDY() {
	}

	public GraphStCol3DLineDY(String title) {
		super(title);
	}
	
	public String getImage(int width, int height) throws Exception {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		DefaultCategoryDataset datasetLine = new DefaultCategoryDataset();

		JIterator<Dataset> ids = getDatasets().getValueIterator();
		while (ids.hasMoreElements()) {
			Dataset ds = ids.nextElement();
			JIterator<Value> iv = ds.getValues().getValueIterator();
			while (iv.hasMoreElements()) {
				Value v = iv.nextElement();
				if (ds.getParentYAxis().equals("S"))
					datasetLine.setValue(Float.parseFloat(v.getData().toString().equals("") ? "0" : v.getData().toString()), ds.getColname(), v.getCategorie());
				else
					dataset.setValue(Float.parseFloat(v.getData().toString().equals("") ? "0" : v.getData().toString()), ds.getColname(), v.getCategorie());
			}
		}
		final CategoryItemRenderer renderer = new StackedBarRenderer3D();
		renderer.setItemLabelsVisible(true);

		final CategoryPlot plot = new CategoryPlot();
		plot.setDataset(dataset);
		plot.setRenderer(renderer);
		
		plot.setDomainAxis(new CategoryAxis(getAtributtes().getElement(GRAPH_ATTR_XAXISNAME)));
		plot.setRangeAxis(new NumberAxis(getAtributtes().getElement(GRAPH_ATTR_YAXISNAME)));

		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setRangeGridlinesVisible(true);
		plot.setDomainGridlinesVisible(true);

		final CategoryItemRenderer renderer2 = new LineAndShapeRenderer();
		plot.setDataset(1, datasetLine);
		plot.setRenderer(1,renderer2);
	
		// create the third dataset and renderer...
		final ValueAxis rangeAxis2 = new NumberAxis(getAtributtes().getElement(GRAPH_ATTR_SYAXISNAME));
		plot.setRangeAxis(1, rangeAxis2);
		// change the rendering order so the primary dataset appears "behind" the
		// other datasets...
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

		plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
	   
  	final JFreeChart chart = new JFreeChart(plot);
	  chart.setBackgroundPaint(Color.WHITE);
		chart.setTitle(getTitle());

		ByteArrayOutputStream oOutput = new ByteArrayOutputStream();
		ChartUtilities.writeChartAsJPEG(oOutput, chart, width, height, null);
		oOutput.close();

	return "data:image/jpg;base64," + (java.util.Base64.getEncoder().encodeToString(oOutput.toByteArray()).replaceAll("\r\n", ""));
	}
	public String isTimeLine() throws Exception {
		return TIME_IN_CATEGORY;
	}
}
