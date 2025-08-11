package pss.common.training.level3;

import pss.common.customList.config.relation.GuiCamposGallery;
import pss.common.training.level3.sub.GuiSubLevel3;
import pss.common.training.level3.sub.GuiSubLevels3;
import pss.core.services.records.JRecord;
import pss.core.tools.JToolsDB;
import pss.core.win.GuiVirtuals;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFieldSwapWins;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiLevel3 extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiLevel3() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizLevel3(); }
  public int GetNroIcono()   throws Exception { return 208; }
  public String GetTitle()   throws Exception { return "Nivel 3 - Entrenamiento"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormLevel3.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizLevel3 GetcDato() throws Exception { return (BizLevel3) this.getRecord(); }

  public void createActionMap() throws Exception {
		super.createActionMap();
		addAction(10,   "Sub-detalle", null, 2003, false, false);
		addAction(15,   "Agregar detalle", null, 2003, true, true).setOnlyInForm(true);
		addAction(530, "Ejemplo Swap", null, 206, true, true).setModal(true);
	}
  
	
	
	public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==10) return new JActWins(this.getDetalle());
		if (a.getId()==15) return new JActNew(this.getNewDetalle(),0);
		if (a.getId()==530) return new JActFieldSwapWins(getDetalle(),JToolsDB.getYesOrNotOrCancel(""),this,"id","valor");
		return super.getSubmit(a);
	}
	
	public JWins getDetalle() throws Exception {
		JWins records=new GuiSubLevels3();
		records.getRecords().addFilter("id", GetcDato().getId());
		records.setParent(this);
		return records;
	}
	public JWin getNewDetalle() throws Exception {
		JWin w=new GuiSubLevel3();
		w.getRecord().addFilter("id", GetcDato().getId());
		return w;
	}

 }
