package  pss.common.customList.config.customlist;

import java.util.TreeMap;

import pss.common.customList.config.dynamic.BizDynamic;
import pss.common.customList.config.field.campo.BizCampo;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class JCorteControl {
	public class JAcumulador {
		
		public JAcumulador(String a,double b) {
			campo=a;
			acumulador=b;
		}
		String campo;
		double acumulador;
	}
	public class JValorCorte {
		
		public JValorCorte(String a) {
			valor=a;
			acumuladores = new TreeMap<String, JAcumulador>();
		}
		String valor;
		TreeMap<String,JAcumulador> acumuladores;
		public String getValor(String campo) {
			if (acumuladores.get(campo)==null) return "" ;
			return JTools.formatDouble(acumuladores.get(campo).acumulador);
		}
	}
	TreeMap<String,JValorCorte> map;
	
	private TreeMap<String, JValorCorte>  getMap() {
		if (map==null) map=new TreeMap<String, JValorCorte>();
		return map;
	}
	public JValorCorte addAcumulador(String corte, String valor, String campo, double val) {
		JValorCorte vs=null;
		JValorCorte vc=getMap().get(corte);
		if (vc==null) {
			vc = new JValorCorte(valor);
			getMap().put(corte,vc);
		}
		JAcumulador ac= vc.acumuladores.get(campo);
		if (ac==null)	{
			ac = new JAcumulador(campo, 0);
			vc.acumuladores.put(campo, ac);
		}
		else if (!vc.valor.equals(valor))	{
			vs=vc;
			vc = new JValorCorte(valor);
			vc.valor=valor;
			map.remove(corte);
			map.put(corte, vc);
			ac = new JAcumulador(campo, 0);
			vc.acumuladores.put(campo, ac);
		}
		ac.acumulador+=val;
		return vs;
	}
	public JValorCorte lastAcumulador(String corte) {
		JValorCorte vc=getMap().get(corte);
		return vc;
	}
	JMap<String,BizVirtual> secc;
	JMap<String,JRecords<BizDynamic>> mapa;
	public JMap<String,BizVirtual>  getSecciones() throws Exception {
		if (secc==null) 	secc = JCollectionFactory.createOrderedMap();
		return secc;
	}
	public JRecords<BizVirtual>  getObjSecciones() throws Exception {
		JRecords<BizVirtual> seccs= new JRecords<BizVirtual>(BizVirtual.class);
		seccs.setStatic(true);
		JIterator<BizVirtual> it =getSecciones().getValueIterator();
		while (it.hasMoreElements()) {
			seccs.getStaticItems().addElement(it.nextElement());
		}
		return seccs;
	}
	
	public JRecords<BizDynamic> getObjListadoPorSeccion(String filter) throws Exception {
		if (mapa==null) mapa = JCollectionFactory.createOrderedMap();
		return mapa.getElement(filter);
	}
	
	public void addSeccion(String secc,String descr) throws Exception {
		if (getSecciones().getElement(secc)!=null) return;
		BizVirtual v=new BizVirtual();
		v.setValor(secc);
		v.setDescripcion(descr);
		getSecciones().addElement(secc,v);
	}
	public void addListado(String secc,String descr,BizDynamic dyn) throws Exception {
		addSeccion(secc,descr);
		JRecords<BizDynamic> l = getObjListadoPorSeccion(secc);
		if (l==null ) {
			l=new JRecords<BizDynamic>(BizDynamic.class);
			l.setStatic(true);
			mapa.addElement(secc,l);
		}
		l.getStaticItems().addElement(dyn);
	}

	public void process(BizDynamic din,BizDynamic oldDin) throws Exception {
		if (din==null) {
			BizCustomList lista=oldDin.getCustomList();
			JRecords<BizCampo> ejes = lista.getObjEjesControl();
			JIterator<BizCampo> it = ejes.getStaticIterator();
			while (it.hasMoreElements()) {
				BizCampo eje = it.nextElement();
				oldDin.setValorCorte(lastAcumulador(eje.getNameField(eje.getCampo())));
			}
			return;
		}
		BizCustomList lista=din.getCustomList();
		JRecords<BizCampo> ejes = lista.getObjEjesControl();
		JIterator<BizCampo> it = ejes.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCampo eje = it.nextElement();
//			if (lista.hasEjesControl()){
//				if (!eje.isCorteControl()) continue;
//			}else{
//				if (eje.isFila()) continue;
//			}
			addListado(din.getPropAsString(eje.getNameField(eje.getCampo())), eje.getNombreColumna().equals("")?eje.getDescrCampo():eje.getNombreColumna(), din);
			JIterator<BizCampo> itc = lista.getCamposVisibles().getStaticIterator();
			while (itc.hasMoreElements()) {
				JValorCorte vc=null;
				BizCampo c = itc.nextElement();
				if (Number.class.isAssignableFrom(din.getPropType(c.getNameField(c.getCampo()))))
					vc = addAcumulador(eje.getNameField(eje.getCampo()), din.getPropAsString(eje.getNameField(eje.getCampo())), c.getNameField(c.getCampo()), Double.parseDouble(din.getPropAsString(c.getNameField(c.getCampo())).trim().equals("")?"0.0":din.getPropAsString(c.getNameField(c.getCampo()))));
				if (vc!=null && oldDin!=null) {
					oldDin.setValorCorte(vc);
				}
			}
		}
	}
}
