package pss.common.customDashboard.config;

import java.util.Date;

import pss.common.customDashboard.DashBoardInfo;
import pss.common.customDashboard.DashBoardModule;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BizDashBoardConfig extends JRecord {

	protected JLong pId = new JLong();
	protected JString pCompany = new JString();
	protected JString pPais = new JString();
	protected JString pUserid = new JString();
	protected JLong pDashOrder = new JLong();
	protected JInteger pDashCode = new JInteger();
	protected  JBoolean  pExcluded = new  JBoolean ();
	protected  JString  pHistorico = new  JString();
	private JString pDescrHistorico = new JString() {
		public void preset() throws Exception {
			setValue(BizDashBoardConfig.getHistoricoMap().getElement(pHistorico.getValue()));
		}
	};
	private JString pDescrSize = new JString() {
		public void preset() throws Exception {
			setValue(BizDashBoardConfig.getSizeMap().getElement(pDashSize.getValue()));
		}
	};
	
	private JString pDescr = new JString() {
		public boolean forcePresetForDefault() throws Exception {
			return true;
		};
		public void preset() throws Exception {
			pDescr.setValue(findInfo().getDescrip());
		}
	};
	protected  JString pDashSize = new JString();
	


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
	public long getId()	throws Exception {     return pId.getValue();  }
	public boolean isNullId() throws Exception { return  pId.isNull(); } 
	public void setNullToId() throws Exception {  pId.setNull(); } 
	public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
	public void setPais(String zValue) throws Exception {    pPais.setValue(zValue);  }
	public String getCompany()	throws Exception {     return pCompany.getValue();  }
	public String getPais()	throws Exception {     return pPais.getValue();  }
	public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
	public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
	public void setUserid(String zValue) throws Exception {    pUserid.setValue(zValue);  }
	public String getUserid()	throws Exception {     return pUserid.getValue();  }
	public boolean isNullUserid() throws Exception { return  pUserid.isNull(); } 
	public void setNullToUserid() throws Exception {  pUserid.setNull(); } 
	public void setDashOrder(long zValue) throws Exception {    pDashOrder.setValue(zValue);  }
	public long getDashOrder()	throws Exception {     return pDashOrder.getValue();  }
	public boolean isNullDashOrder() throws Exception { return  pDashOrder.isNull(); } 
	public void setNullToDashOrder() throws Exception {  pDashOrder.setNull(); } 
	public void setDashCode(int zValue) throws Exception {    pDashCode.setValue(zValue);  }
	public int getDashCode()	throws Exception {     return pDashCode.getValue();  }
	public void setExcluded( boolean  zValue) throws Exception {    pExcluded.setValue(zValue);  }
	public  boolean  getExcluded()	throws Exception {     return pExcluded.getValue();  }
	public void setDashSize( String zValue) throws Exception {    pDashSize.setValue(zValue);  }
	public  String getDashSize()	throws Exception {     return pDashSize.getValue();  }

	public void setHistorico(String zValue) throws Exception {    pHistorico.setValue(zValue);  }

  /**
   * Constructor de la Clase
   */
  public BizDashBoardConfig() throws Exception {
  }


	public void createProperties() throws Exception {
		addItem( "id", pId );
		addItem( "company", pCompany );
		addItem( "pais", pPais );
		addItem( "userid", pUserid );
		addItem( "dash_order", pDashOrder );
		addItem( "dash_description", pDashCode );
		addItem( "excluded", pExcluded );
		addItem( "historico", pHistorico );
		addItem( "dash_descr", pDescr);
		addItem( "dash_size", pDashSize);
		addItem( "descr_historico", pDescrHistorico);
		addItem( "descr_size", pDescrSize);
  }
  /**
   * Adds the fixed object properties
   */
	public void createFixedProperties() throws Exception {
		addFixedItem( KEY, "id", "Id", false,false, 18 );
		addFixedItem( FIELD, "company", "Empresa", true, true, 15 );
		addFixedItem( FIELD, "pais", "País", true, true, 2 );
		addFixedItem( FIELD, "userid", "Usuario", true,false, 20 );
		addFixedItem( FIELD, "dash_order", "Orden", true, true, 3 );
		addFixedItem( FIELD, "dash_description", "Descripcion", true, true, 10 );
		addFixedItem( FIELD, "excluded", "Excluded", true,false, 1 );
		addFixedItem( FIELD, "historico", "Historico", true,false, 2 );
		addFixedItem( FIELD, "dash_size", "Tamaño", true,false, 2 );
		addFixedItem( VIRTUAL, "dash_descr", "Descripcion", true, true, 500);
		addFixedItem( VIRTUAL, "descr_historico", "Período", true, true, 500);
		addFixedItem( VIRTUAL, "descr_size", "Tamaño %", true, true, 500);
  }
	
  /**
   * Returns the table name
   */
	public String GetTable() { return "UI_DASHBOARD_ORDER"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
	public boolean read( long id) throws Exception { 
		this.addFilter( "id",  id ); 
		return read(); 
  } 

	public boolean readByOrder( String zCompany, String zUserid, long zDashOrder ) throws Exception { 
		this.addFilter( "company",  zCompany ); 
		this.addFilter( "userid",  zUserid ); 
		this.addFilter( "dash_order",  zDashOrder ); 
		return read(); 
  } 

	public boolean readByDescription( String zCompany, String zUserid, long id ) throws Exception { 
		this.addFilter( "company",  zCompany ); 
		this.addFilter( "userid",  zUserid ); 
		this.addFilter( "dash_description",  id ); 
		return read(); 
  } 

	


	public void processExclude() throws Exception {
		BizDashBoardConfig c = (BizDashBoardConfig) this.getPreInstance();
		c.setExcluded(true);
		c.updateRecord();
	}

	public void processInclude() throws Exception {
		BizDashBoardConfig c = (BizDashBoardConfig) this.getPreInstance();
		c.setExcluded(false);
		c.updateRecord();
	}
	

	public void processUp() throws Exception {
		BizDashBoardConfig c = new BizDashBoardConfig();
		c.dontThrowException(true);
		
		if (c.readByOrder(this.getCompany(),this.getUserid(), this.getDashOrder()-1 )) {
			c.setDashOrder(this.getDashOrder());
			c.update();
			
			this.setDashOrder(getDashOrder() - 1);
			this.update();
		}
	}

	public void processDown() throws Exception {
		BizDashBoardConfig c = new BizDashBoardConfig();
		c.dontThrowException(true);

		if (c.readByOrder(this.getCompany(),this.getUserid(), this.getDashOrder()+1 )) {
			c.setDashOrder(this.getDashOrder());
			c.update();
			this.setDashOrder(getDashOrder() + 1);
			this.update();
		}
		
	}
	
	@Override
	public void processInsert() throws Exception {
		if (this.pDashOrder.getValue()==0) {
			BizDashBoardConfig max = new BizDashBoardConfig();
			max.addFilter("company", this.pCompany.getValue());
			max.addFilter("userid", this.pUserid.getValue());
			this.pDashOrder.setValue(max.SelectMaxInt("dash_order")+1);
		}
		super.insert();
		this.pId.setValue(this.getIdentity("id"));
	}

	
	
	public DashBoardInfo findInfo() throws Exception {
		return DashBoardModule.findInfo(this.pDashCode.getValue());
	}

//	public String findKey() throws Exception {
//		return this.findData().findKey();
//	}

	public static final String ULT_SEMANA_CORRIENTE = "SC";
	public static final String ULT_MES_CORRIENTE = "MC";
	public static final String ULT_ANIO_CORRIENTE = "AC";
	public static final String ULT_SEMANA_ATRAS = "SA";
	public static final String ULT_MES_ATRAS = "MA";
	public static final String ULT_ANIO_ATRAS = "AA";

	private static JMap<String, String> historicoMap;
	public static JMap<String, String> getHistoricoMap() throws Exception {
		if (historicoMap!=null) return historicoMap;
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement(ULT_SEMANA_CORRIENTE, "Semana Corriente");
		map.addElement(ULT_MES_CORRIENTE, "Mes Corriente");
		map.addElement(ULT_ANIO_CORRIENTE, "Año Corriente");
		map.addElement(ULT_SEMANA_ATRAS, "1 Semana Atrás");
		map.addElement(ULT_MES_ATRAS, "1 Mes Atrás");
		map.addElement(ULT_ANIO_ATRAS, "1 Año Atrás");
		return (historicoMap=map);
	}
	
	private static JMap<String, String> sizeMap;
	public static JMap<String, String> getSizeMap() throws Exception {
		if (sizeMap!=null) return sizeMap;
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement("1", "1/12");
		map.addElement("2", "2/12");
		map.addElement("3", "3/12");
		map.addElement("4", "4/12");
		map.addElement("5", "5/12");
		map.addElement("6", "6/12");
		map.addElement("7", "7/12");
		map.addElement("8", "8/12");
		map.addElement("9", "9/12");
		map.addElement("10", "10/12");
		map.addElement("11", "11/12");
		map.addElement("12", "12/12");
		return (sizeMap=map);
	}
	
	public boolean isSemanaCorriente() throws Exception {
		return this.pHistorico.getValue().equals(ULT_SEMANA_CORRIENTE);
	}
	
	public boolean isMesCorriente() throws Exception {
		return this.pHistorico.getValue().equals(ULT_MES_CORRIENTE);
	}
	
	public boolean isAnioCorriente() throws Exception {
		return this.pHistorico.getValue().equals(ULT_ANIO_CORRIENTE);
	}
	
	public boolean isSemanaAtras() throws Exception {
		return this.pHistorico.getValue().equals(ULT_SEMANA_ATRAS);
	}
	
	public boolean isMesAtras() throws Exception {
		return this.pHistorico.getValue().equals(ULT_MES_ATRAS);
	}

	public boolean isAnioAtras() throws Exception {
		return this.pHistorico.getValue().equals(ULT_ANIO_ATRAS);
	}

	public Date findDesde() throws Exception {
		if (this.isSemanaAtras())
			return JDateTools.SubDays(JDateTools.today(), 7);
		if (this.isMesAtras())
			return JDateTools.SubDays(JDateTools.today(), 30);
		if (this.isAnioAtras())
			return JDateTools.SubDays(JDateTools.today(), 565);
		
		if (this.isSemanaCorriente())
			JExcepcion.SendError("Error");
		if (this.isMesCorriente())
			return JDateTools.getFirstDayOfMonth(JDateTools.today());
		if (this.isAnioCorriente())
			return JDateTools.getFirstDayOfYear(JDateTools.today());

		return JDateTools.today();
	}
//
//	public String findIdCampo(String campo) throws Exception {
//		return this.findData().getModuleId()+"-"+campo;
//	}

}
