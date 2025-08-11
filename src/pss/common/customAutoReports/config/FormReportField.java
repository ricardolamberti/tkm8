package pss.common.customAutoReports.config;

import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormCDatetimeResponsive;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormComboResponsive;
import pss.core.winUI.responsiveControls.JFormEditResponsive;
import pss.core.winUI.responsiveControls.JFormWinLOVResponsive;

public class FormReportField extends JBaseForm {

  public FormReportField() throws Exception {
  }

  public GuiReportField getWin() { return (GuiReportField) getBaseWin(); }


  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "company", CHAR, REQ, "company" );
    AddItemEdit( "reportId", ULONG, REQ, "report_id" );
    AddItemCombo( "Campo", CHAR, REQ, "campo", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return getCampos(one);
    	}
    }).setRefreshForm(true);
    AddItemCombo( "combo_value", CHAR, OPT, "combo_value", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return getValues();
    	}
    }).setRefreshForm(true);
    JFormControl oCtrl;
    oCtrl = AddItemCheck("check_value", OPT, "check_value");
    oCtrl.SetValorDefault(true);
    oCtrl.setRefreshForm(true);
    AddItemEdit("edit_value", CHAR, OPT, "edit_value" );
    AddItemCombo( "lov_value", CHAR, OPT, "lov_value", new JControlWin() {
    	public JWins getRecords(boolean one) throws Exception {
    		return getValues();
    	}
    }).setRefreshForm(true);
    AddItemEdit("screen_value", CHAR, OPT, "screen_value" );
  }
  
  @Override
  	public void OnPostShow() throws Exception {
  		// TODO Auto-generated method stub
  		this.checkControls("campo");
  	}
  
  
	@Override
	public void checkControls(String controlId) throws Exception {
		JFormControl oCtrl = null;
		if (controlId.equals("campo")) {
			oCtrl =this.getWin().GetcDato().getFieldControl(this.findControl("campo").getValue());
			if (oCtrl instanceof JFormComboResponsive ||	oCtrl instanceof JFormCDatetimeResponsive ) 
			{
				this.findControl("edit_value").setVisible(false);
				this.findControl("check_value").setVisible(false);
				this.findControl("combo_value").setVisible(true);
				this.findControl("lov_value").setVisible(false);
				return;
			}
			if (oCtrl instanceof JFormEditResponsive ) {
				this.findControl("edit_value").setVisible(true);
				this.findControl("check_value").setVisible(false);
				this.findControl("combo_value").setVisible(false);
				this.findControl("lov_value").setVisible(false);
				return;
			}
			if (oCtrl instanceof JFormCheckResponsive ) {
				this.findControl("edit_value").setVisible(false);
				this.findControl("check_value").setVisible(true);
				this.findControl("combo_value").setVisible(false);
				this.findControl("lov_value").setVisible(false);
				return;
			}
			if (oCtrl instanceof JFormWinLOVResponsive ) {
				this.findControl("edit_value").setVisible(false);
				this.findControl("check_value").setVisible(false);
				this.findControl("combo_value").setVisible(false);
				this.findControl("lov_value").setVisible(true);
				return;
			}
		}
		if (controlId.equals("combo_value")) {
			 JRecords recs = this.getWin().GetcDato().getWinsFromConrol(this.findControl("campo").getValue()).getRecords();
			 recs.toStatic();
			 recs.firstRecord();
			 while (recs.nextRecord()) {
				 JRecord rec = recs.getRecord();
				 String b = rec.getPropAsString("valor");
				 if (b.equals(this.findControl("combo_value").getValue())) {
					 String a = rec.getPropAsString("descripcion");
					 this.findControl("screen_value").setValue(a);
				 }
			 }
		}
		if (controlId.equals("lov_value")) {
			 JRecords recs = this.getWin().GetcDato().getWinsFromConrol(this.findControl("campo").getValue()).getRecords();
			 recs.toStatic();
			 recs.firstRecord();
			 while (recs.nextRecord()) {
				 JRecord rec = recs.getRecord();
				 JIterator iter =  rec.getProperties().getValueIterator();
				 while (iter.hasMoreElements()) {
					 Object valor = iter.nextElement();
					 Object clave = iter.nextElement();
					 if (clave.toString().equals(this.findControl("lov_value").getValue())) {
						 String a = valor.toString();
						 this.findControl("screen_value").setValue(a);
					 }
					 break;
				 }
			 }
		}
			
	}
  
  private JWins getCampos(boolean one) throws Exception {
  		return JWins.createVirtualWinsFromMap(this.getWin().GetcDato().getCamposGallery());
  }

  private JWins getValues() throws Exception {
  	return this.getWin().GetcDato().getWinsFromConrol(this.findControl("campo").getValue() );
  }
 
}  
