package pss.core.graph.implementations;

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
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Value;
import pss.core.tools.collections.JIterator;



public class GraphCol2DLineDY extends GraphMatrixAndLine {
	public String getSWF() {
		return "FCF_MSColumn2DLineDY.swf";
	}
	public GraphCol2DLineDY() {
	}
	public GraphCol2DLineDY(String title) {
		super(title);
	}
	public String getImage(int pwidth, int pheight) throws Exception {
		int width = pwidth==-1?600:pwidth;
		int height = pheight==-1?600:pheight;
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		DefaultCategoryDataset datasetLine = new DefaultCategoryDataset();

		JIterator<Dataset> ids = getDatasets().getValueIterator();
		while (ids.hasMoreElements()) {
			Dataset ds = ids.nextElement();
			JIterator<Value> iv = ds.getValues().getValueIterator();
			while (iv.hasMoreElements()) {
				Value v = iv.nextElement();
				if (ds.getParentYAxis().equals("S"))
					datasetLine.setValue(Float.parseFloat(v.getData().toString().equals("") ? "0" : v.getData().toString()), ds.getColname(),  v.getCategorie()==null?"":v.getCategorie());
				else
					dataset.setValue(Float.parseFloat(v.getData().toString().equals("") ? "0" : v.getData().toString()), ds.getColname(), v.getCategorie()==null?"":v.getCategorie());
			}
		}
		final CategoryItemRenderer renderer = new BarRenderer();
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

		// JFreeChart chart = ChartFactory.createStackedBarChart3D(getTitle(),
		// getAtributtes().getElement(GRAPH_ATTR_XAXISNAME),
		// getAtributtes().getElement(GRAPH_ATTR_YAXISNAME), dataset,
		// PlotOrientation.VERTICAL , true,true,false);
		final JFreeChart chart = new JFreeChart(plot);
		// chart.setLegend(new StandardLegend());

		chart.setTitle(getTitle());
		// Plot plot = chart.getPlot();
		// JIterator<TrendLines> its = getMapTrendLines().getValueIterator();

			ByteArrayOutputStream oOutput = new ByteArrayOutputStream();
	  ChartUtilities.writeChartAsJPEG(oOutput, chart, width, height, null);
	  oOutput.close();
	
	  
		return "data:image/jpg;base64,"+(java.util.Base64.getEncoder().encodeToString(oOutput.toByteArray()).replaceAll("\r\n", ""));
	}
	public String isTimeLine() throws Exception {
		return TIME_IN_CATEGORY;
	}



}
