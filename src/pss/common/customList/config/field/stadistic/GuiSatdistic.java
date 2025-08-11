package  pss.common.customList.config.field.stadistic;

import pss.common.customList.config.customlist.GuiCustomList;
import pss.common.customList.config.relation.BizCampoGallery;
import pss.common.customList.config.relation.GuiCampoGallery;
import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiSatdistic extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiSatdistic() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizStadistic(); }
  public int GetNroIcono()   throws Exception { return 5060; }
  public String GetTitle()   throws Exception { return "Estadisitico"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { 
	 return FormStadistic.class; 
  }
  @Override
  public Class<? extends JBaseForm> getFormEmbedded() throws Exception {
	 	return FormStadistic.class;
  }
  public String  getKeyField() throws Exception { return "campo"; }
  public String  getDescripField() { return "campo"; }
  public BizStadistic GetcDato() throws Exception { return (BizStadistic) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		createActionQuery();
		createActionUpdate();
		createActionDelete();

  }
  
  
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	return super.getSubmitFor(a);
  }
  
 
  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }
 }
