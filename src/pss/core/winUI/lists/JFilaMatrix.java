package pss.core.winUI.lists;

import pss.core.services.fields.JString;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;

public class JFilaMatrix extends Object {

	//
	// alignment costants
	//
	private String clave;
	private String raiz;
	private boolean subtotal=false;
	
	public boolean isSubtotal() {
		return subtotal;
	}
	public void setSubtotal(boolean subtotal) {
		this.subtotal = subtotal;
	}

	JMap<Long,JOrdenEjeMatrix> gruposEjes;
	String rankOrder;

	

	private JMap<String,JDataMatrix> data;
	
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getRaiz() {
		return raiz;
	}
	public void setRaiz(String raiz) {
		this.raiz = raiz;
	}

	public JMap<String, JDataMatrix> getData() {
		if (data==null) return data=JCollectionFactory.createOrderedMap();
		return data;
	}
	public void setData(JMap<String, JDataMatrix> data) {
		this.data = data;
	}
	public JDataMatrix getData( String index) {
		return getData().getElement(index);
	}
	
	public void  addData( String index,JDataMatrix value) {
		getData().addElement(index,value);
	}
	
	public JMap<Long, JOrdenEjeMatrix> getGruposEjes() {
		if (gruposEjes==null) return gruposEjes=JCollectionFactory.createOrderedMap();
		return gruposEjes;
	}
	public void setGruposEjes(JMap<Long, JOrdenEjeMatrix> grupos) {
		this.gruposEjes = grupos;
	}

	public String getRankOrder() {
		return rankOrder;
	}
	public void setRankOrder(String order) {
		 rankOrder=order;
	}	
	public void setRankOrder(JWin win,JColumnaLista dato,JDataMatrix data, double actualValue)  throws Exception {
		JIterator<JOrdenEjeMatrix> it = this.getGruposEjes().getValueIterator();
		while (it.hasMoreElements()) {
			String orden="";
			JOrdenEjeMatrix eje = it.nextElement();
			//String title=eje.getEje().getValue(win).toString();
			if ( (eje.getEje().getRankCampo()!=null) && (eje.getEje().getRankCampo().equals(dato.GetCampo()))) {
				orden = eje.getEje().getOneOrdenValue(new JString(JTools.formatDouble(eje.acumValueOrden(actualValue))),eje.getEje().getRankCampo(),eje.getEje().isAsc());
				eje.setOrder(  orden+JTools.LPad(eje.getEje().GetCampo(),60,"0"));
			}
			else {
				orden = eje.getEje().getOrdenValue(win);
				eje.setOrder(  orden+JTools.LPad(eje.getEje().GetCampo(),60,"0"));
//				eje.setOrder( getClave()+ JTools.LPad(""+eje.hashCode(),10,"0"));
			}
		}
		
	}
	public void setRankOrder(JWin win,JColumnaLista dato,JDataMatrix data, String actualValue)  throws Exception {
		JIterator<JOrdenEjeMatrix> it = this.getGruposEjes().getValueIterator();
		while (it.hasMoreElements()) {
			String orden="";
			JOrdenEjeMatrix eje = it.nextElement();
			//String title=eje.getEje().getValue(win).toString();
			if ( (eje.getEje().getRankCampo()!=null) && (eje.getEje().getRankCampo().equals(dato.GetCampo()))) {
				orden = eje.getEje().getOneOrdenValue(new JString(actualValue),eje.getEje().getRankCampo(),eje.getEje().isAsc());
				eje.setOrder(  orden+JTools.LPad(eje.getEje().GetCampo(),60,"0"));
			}
			else {
				orden = eje.getEje().getOrdenValue(win);
				eje.setOrder(  orden+JTools.LPad(eje.getEje().GetCampo(),60,"0"));
//				eje.setOrder( getClave()+ JTools.LPad(""+eje.hashCode(),10,"0"));
			}
		}
		
		
	}
	
//	if ( (eje.getEje().getRankCampo()!=null) && (eje.getEje().getRankCampo().equals(dato.GetCampo()))) {
//		String title=eje.getEje().getValue(win).toString();
//		orden = eje.getEje().getOneOrdenValue(new JString(JTools.formatDouble(eje.acumValueOrden(actualValue))),eje.getEje().getRankCampo(),eje.getEje().isAsc());
//						
//		eje.setOrder(  orden + JTools.LPad(""+title,60,"0"));
//	}

	public JOrdenEjeMatrix findByEje(JEjeMatrix eje)  throws Exception {
		String order = "";
		JIterator<JOrdenEjeMatrix> it = getGruposEjes().getValueIterator();
		while (it.hasMoreElements()) {
			JOrdenEjeMatrix orden = it.nextElement();
			if (orden.getEje().equals(eje)) return orden;
		}
		return null;
	}
	public String rebuildRankOrder()  throws Exception {
		String order = "";
		JIterator<JOrdenEjeMatrix> it = getGruposEjes().getValueIterator();
		while (it.hasMoreElements()) {
			JOrdenEjeMatrix eje = it.nextElement();
			order += eje.getOrder()+"_"+eje.getClave()+"__";
		}
//	  order+=getClave();
		PssLogger.logDebug("ORDEN FILA "+ order);
		
		if (isSubtotal())
			order+="_zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzsubtotal";
		this.rankOrder = order;
		return this.rankOrder;
	}

	public void rebuildSpan()  throws Exception {
		boolean noChange=false;
		JIterator<JDataMatrix> itd = getData().getValueIterator();
		while (itd.hasMoreElements()) {
			JDataMatrix data = itd.nextElement();
			if (data.getEje()==null) continue;
			JEjeMatrix eje=data.getEje();
			JOrdenEjeMatrix ejeOrden = findByEje(eje);
			if (ejeOrden==null) continue;

			if (eje.getOldFila()!=null && eje.getOldFila().getValue()!=null && !noChange && eje.getOldFila()!=null && eje.getOldFila().getValue().equals(data.getValue())) {
			  data.setVisible(false);
				eje.getOldFila().setRowspan(eje.getOldFila().getRowspan()+1);
			}	else {
				eje.setOldFila(data);
				noChange=true;
			}
		}
	
	}
	
	public JList<JFilaMatrix> limitar(JWinMatrix matrix)  throws Exception {
		String order = "";
		boolean otros = false;
		JList<JFilaMatrix> out = JCollectionFactory.createList();
		JOrdenEjeMatrix lastOrden = null;
	  Object[] keys = getGruposEjes().getKeys();
		for (int l=0;l<keys.length;l++) {
			JOrdenEjeMatrix eje = getGruposEjes().getElement((Long)keys[l]);
			if (eje.getEje().getLimite()==0) {
				lastOrden= eje;
				continue;
			}
			long filas = 0;
			boolean controlOtros = false;
			if (lastOrden==null) {
				matrix.addFilas(eje);
				filas=matrix.getFilas();
				controlOtros=matrix.hasOtros();
			} else {
				lastOrden.addFilas(eje);
				filas=lastOrden.getFilas();
				controlOtros=lastOrden.hasOtros();
			}
			if ( isSubtotal()) {
				if (!controlOtros) {
					lastOrden = eje;
					break;
				} else {
					otros = true;
					break;
				}
			}
			if (controlOtros || eje.getEje().getLimite()<filas) {
				JFilaMatrix filaOtros = null;
				otros=true;
				if (!controlOtros) {
					if (lastOrden==null) 
						filaOtros = matrix.buildOtros(this);
					else
						filaOtros = lastOrden.buildOtros(this,eje.getEje());
		//			filaOtros.rebuildRankOrder();
					filaOtros.setRankOrder(getRankOrder()+"_zzzzzzzzzzzzzzzzzzotros");
					out.addElement(filaOtros);
				} else {
					if (lastOrden==null) 
						eje.addOtros(matrix.getOtros(),this);
					else
						eje.addOtros(lastOrden.getOtros(),this);
				}
			}
			lastOrden= eje;
				
		}
		if (!otros) {
			out.addElement(this);
		}

		return out;
	}
}
