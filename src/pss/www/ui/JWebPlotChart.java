package pss.www.ui;

import java.io.File;
import java.io.FileOutputStream;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;


public abstract class JWebPlotChart extends JWebChart {
  
  protected Dataset oChartData;
  protected JFreeChart oChart;
  protected boolean b3d = true;
  
  public JWebPlotChart() {
    this.setSize(300,200);
    this.oChartData = this.createDataset();    
  }
  
  @Override
	public void destroy() {
    this.oChartData = null;
    this.oChart = null;
    super.destroy();
  }
  
  public void set2dStyle() {
    this.b3d = false;
  }
  public void set3dStyle() {
    this.b3d = true;
  }
  public boolean is3dStyle() {
    return this.b3d;
  }
  
  public JFreeChart getChart() {
    if (this.oChart==null) {
      this.oChart = this.createChart();
    }
    return this.oChart;
  }
  
  
  /**
   * Exports the image as jpg 
   */ 
  @Override
	protected void doExportAsImage(File zFile) throws Exception {
    // creates the charts and chart data
    this.getChart();
    this.configureChart(this.oChart);
    FileOutputStream oOutput = new FileOutputStream(zFile);
    ChartUtilities.writeChartAsJPEG(oOutput, this.oChart, this.getSize().width, this.getSize().height, null);
    oOutput.close();
  }


//////////////////////////////////////////////////////////////////////////////
// methods to override
//////////////////////////////////////////////////////////////////////////////
    
  protected abstract Dataset createDataset();
  protected abstract JFreeChart createChart();
  protected void configureChart(JFreeChart zChart) throws Exception {
    //this.oChart.setBackgroundPaint(Color.white);
  }
  

}
