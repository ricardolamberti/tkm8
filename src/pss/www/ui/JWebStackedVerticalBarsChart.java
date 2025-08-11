/*
 * Created on 20/08/2003
 */
package pss.www.ui;

/*import com.jrefinery.chart.ChartFactory;
import com.jrefinery.chart.JFreeChart;
import com.jrefinery.data.CategoryDataset; */

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
/**
 * @author rasensio
 */
public class JWebStackedVerticalBarsChart extends JWebBarsChart {

  @Override
	protected JFreeChart createChart() {
    if (this.b3d) {
      return ChartFactory.createStackedBarChart3D(this.getTitleForGeneration(), this.sCategoriesText, this.sValuesText, (CategoryDataset)this.oChartData,PlotOrientation.VERTICAL , true, true, true);
    } else {
      return ChartFactory.createStackedBarChart(this.getTitleForGeneration(), this.sCategoriesText, this.sValuesText, (CategoryDataset)this.oChartData,PlotOrientation.VERTICAL, true, true, true); 
    }
  }

}
