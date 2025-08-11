package pss.core.reports;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormReportPreview extends JBaseForm {

  public FormReportPreview() throws Exception {
  }

  public GuiReportPreview GetWin() {
    return (GuiReportPreview) this.getBaseWin();
  }

  @Override
	public void InicializarPanel(JWin zWin) throws Exception {
  }


}
