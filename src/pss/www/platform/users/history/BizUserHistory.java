package pss.www.platform.users.history;

import java.lang.ref.SoftReference;
import java.util.Date;

import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JStringTokenizer;
import pss.core.win.JBaseWin;
import pss.core.win.actions.BizAction;
import pss.www.platform.users.stadistics.GuiStadistics;

public class BizUserHistory extends JRecord {

		
  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
	private JLong pIdReq = new JLong();
  private JString pUser = new JString();
  private JString pClase = new JString();
  private JString pAccion = new JString();
  private JString pStadistics = new JString();
  private JLong pTimeStart = new JLong();
  private JLong pTimeAjax = new JLong();
  private JLong pTimeBuild = new JLong();
  private JLong pTimeJS = new JLong();
  private JLong pTimeDT = new JLong();
  private JLong pTimePT = new JLong();
  private JLong pTimeTransformer = new JLong();
  private JFloat pSize = new JFloat();
  private JBoolean pFinalize = new JBoolean();
  private JLong pTotal = new JLong() {
  	public void preset() throws Exception {
  		pTotal.setValue(getTotal());
  	}
  };
  private JDateTime pFechaHora = new JDateTime();
  private JLong pTimeResolver = new JLong();
  private JLong pTimeGenerator = new JLong();
  private JLong pTimeUnknow = new JLong() {
  	public void preset() throws Exception {
  		pTimeUnknow.setValue(getTimeUnknow());
  	}
  };
  private boolean bError = false;
  private JString pErrorMsg = new JString();

  transient SoftReference<JBDatos> referenceDB;
  

  

	public long getIdReq() throws Exception {return pIdReq.getValue();}
  public String getClase() throws Exception {return pClase.getValue();}
  public String getAccion() throws Exception {return pAccion.getValue();}
  public String getStadistics() throws Exception {return pStadistics.getValue();}
  public Date getFechaHora() throws Exception {return pFechaHora.getValue();}
  public long getTimeResolver() throws Exception {return pTimeResolver.getValue();}
  public long getTimeGenerator() throws Exception {return pTimeGenerator.getValue();}

  public void setUser(String value) throws Exception {pUser.setValue(value);}
  public void setClase(String value) throws Exception {pClase.setValue(value);}
  public void setAccion(String value) throws Exception {pAccion.setValue(value);}
  public void setStadistics(String value) throws Exception {pStadistics.setValue(value);}
  public void setFechaHora(Date value) throws Exception {pFechaHora.setValue(value);}
  public void setTimeResolver(long value)  {pTimeResolver.setValue(value);}
  public void setTimeTransformer(long value) throws Exception {pTimeTransformer.setValue(value);}
  public void setTimeGenerator(long value) throws Exception {pTimeGenerator.setValue(value);}
  public void setTimeStart(long value) throws Exception {pTimeStart.setValue(value);}
 
  public void setFinalize(boolean value) throws Exception {pFinalize.setValue(value);}
  public boolean isFinalize() throws Exception {return pFinalize.getValue();}
  public boolean isError() throws Exception {return this.bError;}
  public boolean isInProgress() throws Exception {return !this.isFinalize();}

  public void setReferenceThread(JBDatos bdato) throws Exception {referenceDB = new SoftReference<JBDatos>(bdato);}
  public JBDatos getReferenceThread() throws Exception { if (referenceDB==null) return null; return referenceDB.get();}



  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizUserHistory() throws Exception {}

  @Override
	public void createProperties() throws Exception {
  	addItem("id_req", pIdReq);
    addItem("user", pUser);
    addItem("clase", pClase);
    addItem("accion", pAccion);
    addItem("stad", pStadistics);
    addItem("time_start", pTimeStart);
    addItem("time_ajax", pTimeAjax);
    addItem("time_build", pTimeBuild);
    addItem("time_js", pTimeJS);
    addItem("time_dt", pTimeDT);
    addItem("time_pt", pTimePT);
    addItem("time_tr", pTimeTransformer);
    addItem("total", pTotal);
    addItem("fecha_hora", pFechaHora);
    addItem("time_resolver", pTimeResolver);
    addItem("time_generator", pTimeGenerator);
    addItem("time_unknow", pTimeUnknow);
    addItem("size", pSize);
    addItem("finalize", pFinalize);
    addItem("error_msg", pErrorMsg);
  }
  
  @Override
	public void createFixedProperties() throws Exception {
  	addFixedItem(FIELD, "id_req", "id req", true, true, 18);
    addFixedItem(FIELD, "user", "Usuario", true, true, 30);
    addFixedItem(FIELD, "clase", "Clase", true, true, 250);
    addFixedItem(FIELD, "accion", "Acción", true, true, 250);
    addFixedItem(FIELD, "stad", "Estadistica", true, true, 200);
    addFixedItem(FIELD, "time_start", "Time Start", true, true, 20);
    addFixedItem(FIELD, "time_ajax", "Time Ajax", true, true, 20);
    addFixedItem(FIELD, "time_build", "Time Build", true, true, 20);
    addFixedItem(FIELD, "time_js", "Time JS", true, true, 20);
    addFixedItem(FIELD, "time_dt", "Time Download", true, true, 20);
    addFixedItem(FIELD, "time_pt", "Time Server", true, true, 20);
    addFixedItem(FIELD, "time_tr", "Time Transf", true, true, 20);
    addFixedItem(FIELD, "total", "Total", true, true, 20);
    addFixedItem(FIELD, "fecha_hora", "Fecha/Hora", true, true, 20);
    addFixedItem(FIELD, "time_resolver", "Time Resolver", true, true, 20);
    addFixedItem(FIELD, "time_generator", "Time Generator", true, true, 20);
    addFixedItem(FIELD, "time_unknow", "Limbo", true, true, 20);
    addFixedItem(FIELD, "size", "Size", true, true, 20);
    addFixedItem(FIELD, "finalize", "Finalizó?", true, false, 1);
    addFixedItem(FIELD, "error_msg", "Error Msg", true, false, 1000);    
  }

  @Override
	public String GetTable() {
    return "";
  }

  public void assignStadistics(String value) throws Exception {
  	String k;
  	JStringTokenizer tk = JCollectionFactory.createStringTokenizer(value, '|');
  	while (tk.hasMoreTokens()) {
  		k = tk.nextToken();
  		this.pIdReq.setValue(JTools.getLongNumberEmbedded(k.substring(k.indexOf('=')+1)));
  		k = tk.nextToken();
  		this.pTimeAjax.setValue(k.substring(k.indexOf('=')+1));
  		k = tk.nextToken();
  		this.pTimeBuild.setValue(k.substring(k.indexOf('=')+1));
  		k = tk.nextToken();
  		this.pTimeJS.setValue(k.substring(k.indexOf('=')+1));
  		k = tk.nextToken();
   		this.pTimeDT.setValue(k.substring(k.indexOf('=')+1));
  		k = tk.nextToken();
   		this.pTimePT.setValue(k.substring(k.indexOf('=')+1));
  		k = tk.nextToken();
  		this.pSize.setValue(k.substring(k.indexOf('=')+1));
  		GuiStadistics.getStat().GetcDato().addTotByt((long)pSize.getValue());
  	}
    this.setStadistics(value);
	}
  
  public double getTotal() throws Exception {
  	return this.pTimeAjax.getValue()+this.pTimeBuild.getValue()+this.pTimeJS.getValue();
  }
 
  public static BizUserHistory createStad(long id) throws Exception {
		BizUserHistory uh = new BizUserHistory();
		uh.setFechaHora(new Date());
		uh.pIdReq.setValue(id);
		return uh;
  }

  public void storeAction(JBaseWin owner, BizAction action) throws Exception {
  	this.setClase(owner==null?"Menu":owner.GetTitle());
  	this.setAccion(action==null?"":action.GetDescr());
  	PssLogger.logDebug("**Accion:" + this.getClase() + " -> "+ this.getAccion());
  }


  public long getDelta() throws Exception {
  	return new Date().getTime()-(this.getFechaHora().getTime()+this.getServerTimeAcum());
  }

  public long getServerTimeAcum() throws Exception {
  	return this.pTimeStart.getValue()+this.pTimeResolver.getValue()+
  				 this.pTimeGenerator.getValue()+this.pTimeTransformer.getValue();
  }
  
  public double getTimeUnknow() throws Exception {
  	if(this.pTimeAjax.getValue()==0) return 0d;
  	return this.pTimeAjax.getValue()-getServerTimeAcum();
  }
  
  public void processError(Throwable e) throws Exception {
  	this.bError=true;
  	this.setFinalize(true);
  	this.pErrorMsg.setValue(e.getMessage());
  }

}