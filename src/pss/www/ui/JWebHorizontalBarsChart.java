package pss.www.ui;

/*import com.jrefinery.chart.ChartFactory;
import com.jrefinery.chart.JFreeChart;
import com.jrefinery.chart.axis.HorizontalCategoryAxis;
import com.jrefinery.chart.plot.CategoryPlot;
import com.jrefinery.data.CategoryDataset; */

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;


/**
 * 17/07/2003
 * @author rasensio
 */
public class JWebHorizontalBarsChart extends JWebBarsChart {

	@Override
	protected JFreeChart createChart() {
    if (this.b3d) {
      return ChartFactory.createBarChart3D(this.getTitleForGeneration(), this.sCategoriesText, this.sValuesText, (CategoryDataset)this.oChartData,PlotOrientation.HORIZONTAL ,true, true, true );   
    } else {
      return ChartFactory.createBarChart(this.getTitleForGeneration(), this.sCategoriesText, this.sValuesText, (CategoryDataset)this.oChartData,PlotOrientation.HORIZONTAL, true, true, true); 
    }
	}
  
  @Override
	protected void configureChart(JFreeChart zChart) throws Exception {
    super.configureChart(zChart);
    CategoryPlot oPlot = oChart.getCategoryPlot();
    //((HorizontalCategoryAxis)oPlot.getDomainAxis()).setSkipCategoryLabelsToFit(true);
  }  

}
