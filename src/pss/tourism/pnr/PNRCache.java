package pss.tourism.pnr;

import pss.bsp.clase.BizClase;
import pss.bsp.clase.detalle.BizClaseDetail;
import pss.bsp.familia.BizFamilia;
import pss.bsp.familia.detalle.BizFamiliaDetail;
import pss.bsp.market.BizMarket;
import pss.bsp.market.detalle.BizMarketDetail;
import pss.common.regions.currency.BizMoneda;
import pss.common.regions.divitions.BizPaisLista;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.tourism.airports.BizAirport;
import pss.tourism.carrier.BizCarrier;
import pss.tourism.dks.BizDk;

public class PNRCache {
	
	public static void clean() {
		hCacheMoneda=null;
		hCachePaisLista=null;
		hCacheAirport=null;
		hCacheCarrier=null;
		hCacheFamiliaDetail=null;
		hCacheClaseDetail=null;
		hCacheMercados=null;
		hCacheMercadosDetail=null;
		hCacheDkFind = null;
		hCacheDkFind2 = null;

	}
	public static JMap<String, BizMoneda> hCacheMoneda=null;

	public static synchronized JMap<String, BizMoneda> getMonedaCache() throws Exception {
		if (hCacheMoneda != null)
			return hCacheMoneda;
		JMap<String, BizMoneda> map = JCollectionFactory.createMap();
		JRecords<BizMoneda> recs = new JRecords<BizMoneda>(BizMoneda.class);
		recs.readAll();
		recs.firstRecord();
		while (recs.nextRecord()) {
			BizMoneda c = recs.getRecord();
			map.addElement(c.GetCode(), c);
		}
		return (hCacheMoneda = map);
	}
	
	public static JMap<String, BizPaisLista> hCachePaisLista=null;

	public static synchronized JMap<String, BizPaisLista> getPaisListaCache() throws Exception {
		if (hCachePaisLista != null)
			return hCachePaisLista;
		JMap<String, BizPaisLista> map = JCollectionFactory.createMap();
		JRecords<BizPaisLista> recs = new JRecords<BizPaisLista>(BizPaisLista.class);
		recs.readAll();
		recs.firstRecord();
		while (recs.nextRecord()) {
			BizPaisLista c = recs.getRecord();
			map.addElement(c.GetPais(), c);
		}
		return (hCachePaisLista = map);
	}
	
		
	
	
	public static JMap<String, BizAirport> hCacheAirport=null;

	public static BizAirport createTemporaryAirport(String code) throws Exception {
		BizAirport airport = new BizAirport();
		airport.setCode(code);
		airport.setDescription(code);
		airport.setCountry("AR");
		airport.execIfNeededprocessInsert();
		hCacheAirport.addElement(code,airport);
		return airport;
	}

	public static synchronized JMap<String, BizAirport> getAirportCache() throws Exception {
		if (hCacheAirport != null)
			return hCacheAirport;
		JMap<String, BizAirport> map = JCollectionFactory.createMap();
		JRecords<BizAirport> recs = new JRecords<BizAirport>(BizAirport.class);
		recs.readAll();
		recs.firstRecord();
		while (recs.nextRecord()) {
			BizAirport c = recs.getRecord();
			map.addElement(c.getCode(), c);
		}
		return (hCacheAirport = map);
	}
	
	public static JMap<String, BizCarrier> hCacheCarrier=null;

	public static BizCarrier createTemporaryCarrier(String code) throws Exception {
		BizCarrier carrier = new BizCarrier();
		carrier.setCarrier(code);
		carrier.setDescription(code);
		carrier.setCodAnalisis(BizCarrier.SIN_LOGICA);
		carrier.execIfNeededprocessInsert();
		hCacheCarrier=null;
		return carrier;
	}

	public static synchronized JMap<String, BizCarrier> getCarrierCache() throws Exception {
		if (hCacheCarrier != null)
			return hCacheCarrier;
		JMap<String, BizCarrier> map = JCollectionFactory.createMap();
		JRecords<BizCarrier> recs = new JRecords<BizCarrier>(BizCarrier.class);
		recs.readAll();
		recs.firstRecord();
		while (recs.nextRecord()) {
			BizCarrier c = recs.getRecord();
			map.addElement(c.getCarrier(), c);
		}
		return (hCacheCarrier = map);
	}
	public static JMap<String, BizClaseDetail> hCacheClaseDetail=null;

	
	static public synchronized BizClaseDetail getTipoClaseCache(String company,String carrier,String tipo, boolean internacional) throws Exception {
		if (hCacheClaseDetail == null)
			hCacheClaseDetail= JCollectionFactory.createMap();
		BizClaseDetail clase=hCacheClaseDetail.getElement(company+"_"+carrier+"_"+tipo+"_"+internacional);
		if (clase!=null) return clase;
		clase = BizClase.getTipoClase(company, carrier, tipo, internacional);
		if (clase==null) return null;
		hCacheClaseDetail.addElement(company+"_"+carrier+"_"+tipo+"_"+internacional, clase);
		return clase;
	}
	public static JMap<String, BizFamiliaDetail> hCacheFamiliaDetail=null;

	
	static public synchronized BizFamiliaDetail getTipoFamiliaCache(String company,String carrier,String tipo) throws Exception {
		if (hCacheFamiliaDetail == null)
			hCacheFamiliaDetail= JCollectionFactory.createMap();
		BizFamiliaDetail Familia=hCacheFamiliaDetail.getElement(company+"_"+carrier+"_"+tipo);
		if (Familia!=null) return Familia;
		Familia = BizFamilia.getTipoFamilia(company, carrier, tipo);
		if (Familia==null) return null;
		hCacheFamiliaDetail.addElement(company+"_"+carrier+"_"+tipo, Familia);
		return Familia;
	}
	
	public static JMap<String, JList<BizMarket> > hCacheMercados=null;
	
	static public synchronized JList<BizMarket>  getMercadosCache(String company) throws Exception {
		if (hCacheMercados == null)
			hCacheMercados= JCollectionFactory.createMap();
		JList<BizMarket> mercado=hCacheMercados.getElement(company);
		if (mercado!=null) return mercado;
		JRecords<BizMarket> markets = new JRecords<BizMarket>(BizMarket.class);
		markets.addFilter("company", company);
		markets.toStatic();
		JList<BizMarket> list = markets.getStaticItems();
		if (list==null) return null;
		hCacheMercados.addElement(company, list);
		return list;
	}
	
	public static JMap<Long, JList<BizMarketDetail> > hCacheMercadosDetail=null;
	
	
	static public synchronized JList<BizMarketDetail>  getMercadosDetailCache(Long idMarket) throws Exception {
		if (hCacheMercadosDetail == null)
			hCacheMercadosDetail= JCollectionFactory.createMap();
		JList<BizMarketDetail> mercado=hCacheMercadosDetail.getElement(idMarket);
		if (mercado!=null) return mercado;
		JRecords<BizMarketDetail> r=new JRecords<BizMarketDetail>(BizMarketDetail.class);
		r.addFilter("id_market", idMarket);
		r.addOrderBy("prioridad","ASC");
		r.toStatic();
		JList<BizMarketDetail> list = r.getStaticItems();
		if (list==null) return null;
		hCacheMercadosDetail.addElement(idMarket, list);
		return list;
	}

	public static JMap<String, String> hCacheDkFind = null;
	public static JMap<String, String> hCacheDkFind2 = null;

	public static synchronized String getDkCached(String company, String gds, String officeId, String agEmision, String sDk) throws Exception {
		if (hCacheDkFind == null)
			hCacheDkFind = JCollectionFactory.createMap();

		String key = company + "|" + gds + "|" + officeId + "|" + agEmision + "|" + sDk;
		String cached = hCacheDkFind.getElement(key);
		if (cached != null) return cached;

		String result =  BizDk.find(company, gds, officeId, agEmision, sDk);

		hCacheDkFind.addElement(key, result);
		return result;
	}

	public static synchronized String getDkCachedSinSinonimo(String company, String gds, String officeId, String agEmision, String sDk) throws Exception {
		if (hCacheDkFind2 == null)
			hCacheDkFind2 = JCollectionFactory.createMap();

		String key = company + "|" + gds + "|" + officeId + "|" + agEmision + "|" + sDk;
		String cached = hCacheDkFind2.getElement(key);
		if (cached != null) return cached;

		String result =  BizDk.find2(company, gds, officeId, agEmision, sDk);
		hCacheDkFind2.addElement(key, result);
		return result;
	}

	

}
