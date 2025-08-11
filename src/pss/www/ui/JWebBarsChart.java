package pss.www.ui;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

import pss.common.regions.multilanguage.JLanguage;

/**
 * 16/07/2003
 * @author rasensio
 */
public abstract class JWebBarsChart extends JWebPlotChart {


  protected String sValuesText = "";
  protected String sCategoriesText = "";


  @Override
	public void destroy() {
    this.sValuesText = null;
    this.sCategoriesText = null;
    super.destroy();
  }


  public void setValuesText(String zText) {    
    this.sValuesText = JLanguage.translate(zText);
  }
  public void setCategoriesText(String zText) {
    this.sCategoriesText = JLanguage.translate(zText);
  }
	@Override
	protected Dataset createDataset() {
		return new DefaultCategoryDataset();
	}
	public void addValue(String zCategory, String zSerie, double zValue) {
		((DefaultCategoryDataset)this.oChartData).addValue(zValue, JLanguage.translate(zSerie), JLanguage.translate(zCategory));
	}

}
