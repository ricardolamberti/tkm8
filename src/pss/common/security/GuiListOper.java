package  pss.common.security;

import pss.common.regions.company.BizCompany;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;

public class GuiListOper extends GuiOperacion {

  public GuiListOper() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizListOper(); }
//  @Override
//	public String GetTitle()       throws Exception { return "Operación"; }
//  @Override
//	public String getKeyField()   throws Exception { return "operacion"; }
//  @Override
//	public String getDescripField()                  { return "descripcion"; }
//	@Override
//	public Class<? extends JBaseForm> getFormBase() throws Exception {
//		return FormListOper.class;
//	}

  public BizListOper GetcDato() throws Exception {
    return (BizListOper) getRecord();
  }

  @Override
	public void createActionMap() throws Exception {
  	super.createActionMap();
//  	addActionQuery(1, "Consultar");
//    addAction(13, "Restringir", null, 48, true, true);
//    addAction(14, "Liberar Seguridad", null, 49, true, true).setConfirmMessage(true);
//    addAction(15, "Liberar/Restringir", null, 49, false, false).setConfirmMessage(true);
    this.addAcciones();
  }
  
  public void addAcciones() throws Exception {
  	if (GetcDato().getCompany().equals("")) return;
		JRecords<BizRol> cargos=new JRecords<BizRol>(BizRol.class);
		cargos.addFilter("company", GetcDato().getCompany());
		cargos.addFilter("tipo", BizCompany.getCompany(GetcDato().getCompany()).getObjBusiness().hasRolesAplicacion() ? BizRol.NORMAL : BizRol.JERARQUICO);
		cargos.readAll();
		JIterator<BizRol> iter=cargos.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizRol rol = iter.nextElement();
	    this.addAction("acc_"+rol.getRol(), "Cambiar permiso "+rol.GetDescrip(), null, 49, false, false);
		}

  }
    
  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }
  
//	@Override
//	public boolean ifAcceptRefresh(JWinEvent e, boolean zGlobal) throws Exception {
//    return ObtenerOperacion().ifAcceptRefresh(e, zGlobal);
//  }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
//  	if (a.getId()==1) return this.GetcDato().hasSecurity();
//    if (a.getId()==13 ) return !this.GetcDato().hasSecurity();
//    if (a.getId()==14 ) return this.GetcDato().hasSecurity();
  	return true;
  }
	
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
//  	if (a.getId()==13) return new JActSubmit(this, a.getId()) {
//  		public void submit() throws Exception {
//  			GetcDato().execHabilitarPermisos();
//  		}
//  	};
//  	if (a.getId()==14) return new JActSubmit(this, a.getId()) {
//  		public void submit() throws Exception {
//  			GetcDato().execDeshabilitarPermisos();
//  		}
//  	};
//  	if (a.getId()==15) return new JActSubmit(this, a.getId()) {
//  		public void submit() throws Exception {
//  			GetcDato().execHabilitarDeshabilitarPermisos();
//  		}
//  	};
  	JAct act= getSubmitAcciones(a);
  	if (act!=null)
  		return act;
   	return super.getSubmitFor(a);

   }
  
  public JAct getSubmitAcciones(BizAction a) throws Exception {
  	if (GetcDato().getCompany().equals("")) return null;
		JRecords<BizRol> cargos=new JRecords<BizRol>(BizRol.class);
		cargos.addFilter("company", GetcDato().getCompany());
		cargos.addFilter("tipo", BizCompany.getCompany(GetcDato().getCompany()).getObjBusiness().hasRolesAplicacion() ? BizRol.NORMAL : BizRol.JERARQUICO);
		cargos.readAll();
		JIterator<BizRol> iter=cargos.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizRol rol = iter.nextElement();
	  	if (a.getIdAction().equals("acc_"+rol.getRol())) {
	  		JAct act = new JActSubmit(this, a.getId()) {
		  		public void execSubmit() throws Exception {
		  			GetcDato().cambiarPermisos((BizRol)getDataLocal());
		  		}
		  	};
		  	act.setDataLocal(rol);
		  	return act;
	  	}
	  }
	 	return null;

  }
    
//	@Override
//	public JWin getRelativeWin() throws Exception {
//		return getObjOperacion();
//	}
  
//  public GuiOperacion getObjOperacion() throws Exception {
//    GuiOperacion oper = new GuiOperacion();
//    oper.setRecord(GetcDato().getObjOperacion());
//    return oper;
//  }
  
  @Override
	public int GetNroIcono() throws Exception { 
  	return GetcDato().getNroIcono(); 
	}


}



