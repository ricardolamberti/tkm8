package pss.www.ui;

/*import com.jrefinery.chart.ChartFactory;
import com.jrefinery.chart.JFreeChart;
import com.jrefinery.data.CategoryDataset; */
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;

/**
 * 17/07/2003
 * @author rasensio
 */
public class JWebVerticalBarsChart extends JWebBarsChart {

	@Override
	protected JFreeChart createChart() {
    if (this.b3d) {
      return ChartFactory.createBarChart3D(this.getTitleForGeneration(), this.sCategoriesText, this.sValuesText, (CategoryDataset)this.oChartData,PlotOrientation.VERTICAL ,true, true, true );   
    } else {
      return ChartFactory.createBarChart(this.getTitleForGeneration(), this.sCategoriesText, this.sValuesText, (CategoryDataset)this.oChartData,PlotOrientation.VERTICAL, true, true, true); 
    }
	}

}
