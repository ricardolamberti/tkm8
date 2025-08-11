package pss.common.customList.config.informe;

import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.customlist.GuiCustomList;
import pss.common.customList.config.customlist.GuiCustomListResult;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JIterator;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActDelete;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActUpdate;
import pss.core.winUI.forms.JBaseForm;

public class GuiInforme extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiInforme() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizInforme(); }
  public int GetNroIcono()   throws Exception { return 10064; }
  public String GetTitle()   throws Exception { return "Informe"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormInforme.class; }
  public String  getKeyField() throws Exception { return "secuencia"; }
  public String  getDescripField() { return "secuencia"; }
  public BizInforme GetcDato() throws Exception { return (BizInforme) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		createActionQuery();
		createActionUpdate();
		createActionDelete();
		addAction(30, "Informe", null, 15056, false, false);
		addAction(40, "Informe Embbeded", null, 15056, false, false);
//		addAction(12, "Bajar", null, 15057, true, true);
  }
  @Override
  public boolean OkAction(BizAction a) throws Exception {
   	if (a.getId()==JWin.ACTION_UPDATE) return true;
  	if (a.getId()==JWin.ACTION_DELETE) return true;
  	return super.OkAction(a);
  }
  
  @Override
  public JAct getSubmit(BizAction a) throws Exception {
  	if (a.getId()==JWin.ACTION_UPDATE) return new JActUpdate(getCustomList(), 4);
  	if (a.getId()==JWin.ACTION_DELETE) return new JActDelete(this,BizAction.REMOVE);
  	return super.getSubmit(a);
  }
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
//  	if (a.getId()==10) return new JActSubmit(this, a.getId()) {
//  		public void submit() throws Exception {
//  			GetcDato().execSubir();
//  		}
//  	};
//  	if (a.getId()==12) return new JActSubmit(this, a.getId()) {
//  		public void submit() throws Exception {
//  			GetcDato().execBajar();
//  		}
//  	};
  	if (a.getId()==30) return new JActQuery(getResultado(false));
  	if (a.getId()==40) return new JActQuery(getResultado(true));
  	return super.getSubmitFor(a);
  }
  
  public JWin getCustomList() throws Exception {
  	GuiCustomList l = new GuiCustomList();
  	l.setRecord(GetcDato().getObjCustomList());
  	l.setDropListener(this);
  	return l;
  }
  public JWin getResultado(boolean embedded) throws Exception {
  	GuiCustomListResult l = new GuiCustomListResult();
  	l.setRecord(GetcDato().getObjCustomList());
  	BizCustomList parent = GetcDato().getObjParentCustomList();
  	if (parent!=null&&	GetcDato().getObjCustomList()!=null)
  		GetcDato().getObjCustomList().addExtraCampos(parent.getObjFiltrosReporte());
  	l.SetVision(this.GetVision());
  	if (embedded)
  		l.setMode(GuiCustomList.INFOEMBEDDED);
  	else
  		l.setMode( GuiCustomList.INFOONLY_TABLE);
  	return l;
  }
  
  @Override
  public JAct Drop(JBaseWin zBaseWin) throws Exception {
  	// TODO Auto-generated method stub
  	if (zBaseWin instanceof GuiCustomList) {
  		GuiCustomList cl = ((GuiCustomList) zBaseWin);
  		this.GetcDato().setObjCustomList(cl.GetcDato());
  		JIterator<BizInforme> it = this.GetcDato().getObjParentCustomList().getObjInformes().getStaticIterator();
  		while (it.hasMoreElements()) {
  			BizInforme inf = it.nextElement();
  			if (inf.getObjCustomList()==cl.GetcDato())
  				inf.setObjCustomList(cl.GetcDato());
  		}
  	}
  	return super.Drop(zBaseWin);
  }
  
  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }
  

 }
