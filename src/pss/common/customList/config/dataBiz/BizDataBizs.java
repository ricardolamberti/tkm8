package  pss.common.customList.config.dataBiz;

import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;

public class BizDataBizs extends JRecords<BizDataBiz> {

//-------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizDataBizs() throws Exception {
	}

	
	@Override
	public Class<BizDataBiz> getBasedClass() {
		return BizDataBiz.class;
	}

	private JWin winClass;
	public void setObjWinClass(JWin zwinClass) throws Exception {
		winClass=zwinClass;
	}
	
	private boolean cumpleFiltro(BizDataBiz data,String filtro) throws Exception {
  	if (filtro==null || filtro.equals("")) return true;
		if (JTools.limpiarSimbolos(data.getDescripcion().toUpperCase()).toLowerCase().indexOf(filtro)!=-1) return true;
		if (JTools.limpiarSimbolos(data.getValor().toUpperCase()).toLowerCase().indexOf(filtro)!=-1) return true;
		return false;
	}
	@Override
	public boolean readAll() throws Exception {
		this.endStatic();
    this.setStatic(true);
    String filtro = this.getFilterValue("descripcion");
    if (filtro!=null) filtro = JTools.limpiarSimbolos(filtro.toUpperCase()).toLowerCase();
    JRecord rec = winClass.getRecord();
    JMap<String, String> allFields= rec.getAllFixedProperties(true);
    JIterator<String> it=allFields.getKeyIterator();
    while (it.hasMoreElements()) {
    	String key=it.nextElement();
    	BizDataBiz newData = new BizDataBiz();
    	newData.setCampo(key);
    	newData.setDescripcion(allFields.getElement(key));
    	newData.setValor(rec.getPropAsString(key));
  		if (!cumpleFiltro(newData,filtro)) continue;
  		if (!rec.canViewField(key)) continue;
    	addItem(newData);
    };
    
    
    this.Ordenar("descripcion");
    return true;
		
	}
	
	

}