package pss.common.restJason.apiRestBase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pss.common.restJason.JServiceApiResolver;
import pss.core.services.fields.JObject;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JList;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JColumnaLista;
import pss.core.winUI.lists.JWinList;


@Path("/records")
public class apiJasonService {   

	@GET
    @Path("/getDataFromWins")
    @Produces(MediaType.APPLICATION_JSON)
    //@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getDataFromWins(apiBasicRequest request) throws Exception {

		apiBasicResponse uModel = new apiBasicResponse();
		uModel.setAction(request.getAction());
    	uModel.setFilters(request.getFilters());
    	List<Map<String,Object>> records = null;
    	
		JServiceApiResolver resolver = new JServiceApiResolver();
		try {
				apiStatus resp = resolver.openAppAndLogin(request.getToken(),this.getClass());
				records = this.getAllData(request);
		    	uModel.setStatus(apiStatus.STATUS_CODE_OK, "TODO OK");
				resolver.closeApp();
				
		} catch (Exception e) {
			uModel.setStatus(apiStatus.STATUS_CODE_ERROR,e.getMessage());
			resolver.closeApp();
		}
		
    	uModel.setRecords(records);
        return Response.ok().entity(uModel).build();
    }
    
    
  	public List<Map<String,Object>> getAllData(apiBasicRequest request) throws Exception {
  		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
  		String gui = ""; 
  		gui = request.getAction();
  	  	JWins wins = (JWins) Class.forName(gui).newInstance();
  	  	if (!request.getVision().equals(""))
  	  		wins.SetVision(request.getVision());
  	  	
  	  	for (Map.Entry<String,String> entry : request.getFilters().entrySet())  { 
  	  		wins.getRecords().addFilter(entry.getKey(), entry.getValue());
  	  	}

    	JRecords recs = wins.getRecords();
    	recs.readAll();
    	recs.toStatic();
    	recs.setStatic(true);
    	
		recs.firstRecord();
		
		while (recs.nextRecord()) {
			JRecord rec = recs.getRecord();
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
		
		return data;
  		
  	}
  	
  	private List<Map<String,Object>> getListFromDetailRecords(JWins wins, JRecord rec) throws Exception {
  		List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
		JWin win = wins.getWinRef();
		win.setRecord(rec);
		JWins details = win.getWinsDetail();
	
 		JWinList collist = new JWinList(details); 
 		details.ConfigurarColumnasJSonApiService(collist);
		JList<JColumnaLista> cl = collist.GetColumnasLista();
		
		JRecords recs = details.getRecords();
    	recs.setStatic(true);
    	recs.readAll();
		recs.firstRecord();

		while (details.nextRecord()) {
			JRecord detail = recs.getRecord();
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
