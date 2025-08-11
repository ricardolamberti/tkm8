package pss.core.data.interfaces.structure;

import java.io.Serializable;
import java.util.Iterator;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;

public class RJoins implements Serializable {
	
  String sTablaJoin;
  String sAlias;
  String sCondicion;
  String sCondicionSegundo;
  boolean bInnerJoin;
  String sTypeJoin;
  boolean bDynamic;
  JList<RJoins> joins;
  


  public RJoins() {}

  public String GetTablaJoin() { return sTablaJoin; }
  public String GetAliasJoin() { return sAlias; }
  public String GetCondicion() { return sCondicion; }
  public String GetCondicionSegundo() { return sCondicionSegundo; }
  public boolean isInnerJoin() { return bInnerJoin; }
  public String getTypeJoin() { return sTypeJoin; }
  public boolean isDynamic() { return bDynamic; }
  public boolean hasTypeJoin() { return sTypeJoin!=null && !sTypeJoin.isEmpty(); }
  public boolean hasAliasJoin() { return sAlias!=null; }
  public boolean hasCondicion() { return sCondicion!=null && !sCondicion.isEmpty(); }  
  public boolean hasCondicionSegunda() { return sCondicionSegundo!=null && !sCondicionSegundo.isEmpty(); }  
  public void addCondicion(String s) { 
  	if (s==null)
  		return;
  	if (s.indexOf(GetTablaJoin())==-1 && (GetAliasJoin()==null || s.indexOf(GetAliasJoin())==-1)) {
    	sCondicion= (GetCondicion()==null||GetCondicion().equals("")?"":GetCondicion()+" AND ")+s; 
  	} else {
  		sCondicionSegundo= (GetCondicionSegundo()==null||GetCondicionSegundo().equals("")?"":GetCondicionSegundo()+" AND ")+s; 
  		
  	}
  }
	public RJoins getJoin(String zJoin, String alias) throws Exception {
		Iterator<RJoins> oIt = getJoins().iterator();
		while (oIt.hasNext()) {
			RJoins oJoin = oIt.next();
			if (zJoin==null) return oJoin;
			if (oJoin.hasJoins()) {
				RJoins child = oJoin.getJoin(zJoin, alias);
				if (child!=null) return child;
			}
			if (oJoin.GetTablaJoin()!=null && !oJoin.GetTablaJoin().trim().toLowerCase().equals(zJoin.trim().toLowerCase()))
				continue;
			if (oJoin.GetAliasJoin()==null && alias!=null)
				continue;
			if (alias!=null && !alias.trim().toLowerCase().equals(oJoin.GetAliasJoin().trim().toLowerCase()))
				continue;
//			if (oJoin.GetAliasJoin()!=null && alias==null)
//				continue;
//			if (oJoin.GetAliasJoin()!=null && alias!=null && !oJoin.GetAliasJoin().trim().toLowerCase().equals(alias.trim().toLowerCase()))
//				continue;
			return oJoin;
		}
		return null;
	}
	public void clearJoin(String zJoin,String alias) throws Exception {
		while (true) {
			RJoins oJoin = getJoin(zJoin,alias);
			if (oJoin == null)
				return;
			getJoins().removeElement(oJoin);
		}
	}
	public RJoins addJoin(String typeJoin,String zTabla, String alias,boolean bInnerJoin,String condicion) throws Exception {
		this.clearJoin(zTabla,alias);
		RJoins join = new RJoins();
		join.sTablaJoin = zTabla;
		join.sAlias = alias;
		join.bInnerJoin = bInnerJoin;
		join.sTypeJoin = typeJoin;
		join.addCondicion(condicion);
		getJoins().addElement(join);
		return join;
	}
	public void addJoin(RJoins joins) {
		getJoins().addElement(joins);
		
	}

	public void addJoins(JList<RJoins> joins) {
		JIterator<RJoins> it = getJoins().getIterator();
		while (it.hasMoreElements())
			this.addJoin(it.nextElement());
		
	}
	public boolean hasJoins() {
		return joins!=null;
	}
  public JList<RJoins> getJoins() {
		if (this.joins==null) this.joins = JCollectionFactory.createList();
		return joins;
	}

	public void setDynamic(boolean value) {
  	bDynamic=value;
  }

}
