package pss.common.restJason.api;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import pss.common.restJason.JServiceApiProcess;
import pss.common.restJason.apiRestBase.apiBasicRequest;
import pss.core.services.fields.JObject;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JColumnaLista;
import pss.core.winUI.lists.JWinList;

public class JApiRecordsGet extends JServiceApiProcess {   

  public Response process() throws Exception {
  	JApiRecordsResponse response = new JApiRecordsResponse();
  	apiBasicRequest request = (apiBasicRequest)this.getApiRequest();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		String gui = ""; 
		gui = request.getAction();
  	JWins wins = (JWins) Class.forName(gui).newInstance();
  	if (!request.getVision().equals(""))
  		wins.SetVision(request.getVision());
  	
  	boolean winsRecordHasCompany = wins.GetBaseDato().getRecordRef().getProperties().getElement("company")!=null;
  	if (!winsRecordHasCompany) {
  		if (request.getFilters().containsKey("company"))
  		request.getFilters().remove("company");
  	}
  	for (Map.Entry<String,String> entry : request.getFilters().entrySet())  { 
  		wins.getRecords().addFilter(entry.getKey(), entry.getValue());
  	}

  	JIterator<JRecord> iter = wins.getRecords().getStaticIterator();
		while (iter.hasMoreElements()) {
			JRecord rec = iter.nextElement();
	    Map<String,Object> map= new LinkedHashMap<String, Object>();
	 		JWinList collist = new JWinList(wins); 
	 		wins.ConfigurarColumnasJSonApiService(collist);
			JList<JColumnaLista> cl = collist.GetColumnasLista();
			map = this.getMapFromCols(cl, rec);
			if (wins.hasSubDetail() && request.getShowDetails()) {
				List<Map<String,Object>> listDetail= new ArrayList<Map<String,Object>>();
				listDetail = this.getListFromDetailRecords(wins, rec);
				map.put("details", listDetail);
			}
			data.add(map);
		}	
		response.setRecords(data); 
		return JServiceApiProcess.buildOk(data);
  }
  	
	private List<Map<String,Object>> getListFromDetailRecords(JWins wins, JRecord rec) throws Exception {
		List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
		JWin win = wins.getWinRef();
		win.setRecord(rec);
		JWins details = win.getWinsDetail();
		
		JWinList collist = new JWinList(details); 
		details.ConfigurarColumnasJSonApiService(collist);
		JList<JColumnaLista> cl = collist.GetColumnasLista();
		
  	JIterator<JRecord> iter = details.getRecords().getStaticIterator();
		while (iter.hasMoreElements()) {
			JRecord detail = iter.nextElement();
			Map<String,Object> map= new LinkedHashMap<String, Object>();
			map = this.getMapFromCols(cl, detail);
			list.add(map);
		}
		
		return list;
	}
  	
  	
  	private Map<String,Object> getMapFromCols(JList<JColumnaLista> cl, JRecord rec ) throws Exception {
  		Map<String,Object> map=   new LinkedHashMap<String, Object>();
		for (int i = 0; i < cl.size(); i++) {
			String campo = cl.getElementAt(i).GetCampo();
			String titulo = cl.getElementAt(i).GetTitulo();
			if (titulo.equals("") || titulo.equals("-"))
				continue;
			if (campo.equals(""))
				continue;
			try {
				JObject<?> obj = rec.getPropDeep(campo);
				String valor = obj.toString();
				if (!valor.equals(""))
					map.put(titulo,valor);
				else
					map.put(titulo,"");
			} catch (Exception e) {
				
			}
			
		}
		return map;
  	}
  	
  	
    
}
