package pss.common.customList;

import pss.common.customList.config.BizCustomListModules;
import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.customlist.GuiCustomLists;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;


public class GuiModuloCustomList extends GuiModulo {

  public GuiModuloCustomList() throws Exception {
    super();
    SetModuleName( "Listas Dinámicas" );
    SetNroIcono  ( 204 );
  }

  @Override
	public BizAction createDynamicAction() throws Exception {
    return this.addAction( 1, "Listas Dinámicas", null , 724 , true, false, true, "Group");
  }

  @Override
	public void createActionMap() throws Exception {
    BizAction a =this.addAction( 25, "Listas" , null , 716 , true, false, true, "Group");
    a.setBackAction(false);
    a.setRefreshAction(false);
    this.addAction( 40, "Eliminar repetidos" , null , 716 , true, false, true, "Group");
     }
  
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==25) return new JActWins(this.getListas());

		if (a.getId()==40) return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				execEliminarRepetidos();
			}
		};
		return super.getSubmitFor(a);
	}

	public void execProcessMigrate() throws Exception {
		JRecords<BizCustomList> l = new JRecords<BizCustomList>(BizCustomList.class);
		l.addFilter("company", "TEST");
		JIterator<BizCustomList> it = l.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCustomList l2 = it.nextElement();
			l2.execProcessMigrate2();
		}
	}
  public JWins getListas() throws Exception {
  	GuiCustomLists wins = new GuiCustomLists();
  	wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
  	wins.getRecords().addFilter("record_owner", BizCustomListModules.class.getName());
  	wins.getRecords().addOrderBy("description");
  	return wins;
  }
  
  
  public void  execEliminarRepetidos() throws Exception {
  	
  	JRecords<BizCustomList> l = new JRecords<BizCustomList>(BizCustomList.class);
  	l.addFilter("company", "BIBAM");
  		JIterator<BizCustomList> it = l.getStaticIterator();
  		while (it.hasMoreElements()) {
  			BizCustomList l2 = it.nextElement();
  			l2.execProcessEliminarRepetidos();
  		}
  	}
  	
  	
  	//eliminar repetidos
/*		JRecords<BizCustomList> l = new JRecords<BizCustomList>(BizCustomList.class);
	//	l.addFilter("company", "ALL");
		JIterator<BizCustomList> it = l.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCustomList l2 = it.nextElement();
			JRecords<BizCustomList> l1= new JRecords<BizCustomList>(BizCustomList.class);
			l1.addFilter("company", l2.getCompany());
			l1.addFilter("description", l2.getDescripcion());
			l1.addOrderBy("list_id","DESC");
			JIterator<BizCustomList> itl = l1.getStaticIterator();
			int i=0;
			while (itl.hasMoreElements()) {
				BizCustomList c = itl.nextElement();
				i++;
				if (i<=1) continue;
				c.execProcessDelete();
			}
		}*/
  	/*
  	JList<Long> ids = JCollectionFactory.createList();
  	//eliminar campos repetidos
		JRecords<BizCustomList> lr = new JRecords<BizCustomList>(BizCustomList.class);
	//	l.addFilter("company", "ALL");
		JIterator<BizCustomList> itr = lr.getStaticIterator();
		while (itr.hasMoreElements()) {
			BizCustomList l2 = itr.nextElement();
			JRecords<BizCampo> lc= new JRecords<BizCampo>(BizCampo.class);
			lc.addFilter("list_id", l2.getListId());
			JIterator<BizCampo> itr2 = lc.getStaticIterator();
			while (itr2.hasMoreElements()) {
				BizCampo c2 = itr2.nextElement();
				c2.setObjCustomList(l2);
				JRecords<BizCampo> cr= new JRecords<BizCampo>(BizCampo.class);
				cr.addFilter("company", c2.getCompany());
				cr.addFilter("list_id", c2.getListId());
				cr.addFilter("campo", c2.getCampo());
				cr.addFilter("funcion", c2.getFuncion());
				cr.addFilter("operador", c2.getOperador());
				cr.addFilter("operacion", c2.getOperacion());
				cr.addFilter("has_filter", c2.hasFiltro());
				cr.addFilter("visible", c2.getVisible());
				cr.addFilter("orientacion", c2.getOrientacion());
				cr.addFilter("porcentaje", c2.isPorcentaje());
				cr.addOrderBy("secuencia","ASC");
				JIterator<BizCampo> itlrr = cr.getStaticIterator();
				int i=0;
				try {
					String value = c2.findValue();
					String value2 = c2.findValue2();
					while (itlrr.hasMoreElements()) {
						BizCampo c = itlrr.nextElement();
						i++;
						c.setObjCustomList(l2);
						try {
							if (!(c.findValue().equals(value)))
								continue;
							if (!(c.findValue2().equals(value2)))
								continue;
						} catch (Exception e) {
							continue;
						}
						if (i <= 1)
							continue;
						ids.addElement(c.getSecuencia());
						//					c.execProcessDelete();
					} 
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}	
		JIterator<Long> itl=ids.getIterator();
		while(itl.hasMoreElements()) {
			Long l = itl.nextElement();
			BizCampo c =new BizCampo();
			c.dontThrowException(true);
			if (!c.read(l)) continue;
			c.execProcessDeleteTable();;
			
	}
		
  }  */
}