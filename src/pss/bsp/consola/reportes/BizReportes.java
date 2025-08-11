package  pss.bsp.consola.reportes;

import pss.bsp.consolid.model.cuf.BizCuf;
import pss.bsp.consolid.model.mit.BizMit;
import pss.bsp.consolid.model.repoCarrier.BizRepoCarrier;
import pss.bsp.consolid.model.repoDK.BizRepoDK;
import pss.bsp.consolid.model.report.BizReportDk;
import pss.bsp.consolid.model.rfnd.BizRfnd;
import pss.bsp.interfaces.dqb.datos.BizDQB;
import pss.common.event.action.BizSqlEventAction;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizReportes extends JRecord {

	JString pCompany = new JString();

	public long getReportesProgramados() throws Exception {

		JRecords<BizSqlEventAction> t= new JRecords<BizSqlEventAction>(BizSqlEventAction.class);
		t.addFilter("company", BizUsuario.getUsr().getCompany());
		t.addFilter("tipo_periodicidad", BizSqlEventAction.SOLOUNAVEZ, "<>");
		t.addFilter("action", BizSqlEventAction.NOTIF, "<>");
		return t.selectCount();

	}
	public long getReportesDQB() throws Exception {

		JRecords<BizDQB> t= new JRecords<BizDQB>(BizDQB.class);
		t.addFilter("company", BizUsuario.getUsr().getCompany());
		return t.selectCount();

	}
	public long getReportesCtr() throws Exception {

		JRecords<BizReportDk> t= new JRecords<BizReportDk>(BizReportDk.class);
		t.addFilter("company", BizUsuario.getUsr().getCompany());
		return t.selectCount();

	}
	public long getReportesMit() throws Exception {

		JRecords<BizMit> t= new JRecords<BizMit>(BizMit.class);
		t.addFilter("company", BizUsuario.getUsr().getCompany());
		return t.selectCount();

	}
	public long getReportesCuf() throws Exception {

		JRecords<BizCuf> t= new JRecords<BizCuf>(BizCuf.class);
		t.addFilter("company", BizUsuario.getUsr().getCompany());
		return t.selectCount();

	}
	public long getReportesRfnd() throws Exception {

		JRecords<BizRfnd> t= new JRecords<BizRfnd>(BizRfnd.class);
		t.addFilter("company", BizUsuario.getUsr().getCompany());
		return t.selectCount();

	}
	public long getReportesDKs() throws Exception {

		JRecords<BizRepoDK> t= new JRecords<BizRepoDK>(BizRepoDK.class);
		t.addFilter("company", BizUsuario.getUsr().getCompany());
		return t.selectCount();

	}
	public long getReportesCarriers() throws Exception {

		JRecords<BizRepoCarrier> t= new JRecords<BizRepoCarrier>(BizRepoCarrier.class);
		t.addFilter("company", BizUsuario.getUsr().getCompany());
		return t.selectCount();

	}
	JCurrency pReportesProgramados = new JCurrency() {
		public void preset() throws Exception {
			pReportesProgramados.setValue(getReportesProgramados());
		};
		public int getCustomPrecision() throws Exception {
			return 0;
		}
		
		public boolean hasCustomPrecision() throws Exception {
			return true;
		}
	};
	JCurrency pReportesDQB = new JCurrency() {
		public void preset() throws Exception {
			pReportesDQB.setValue(getReportesDQB());
		};
		public int getCustomPrecision() throws Exception {
			return 0;
		}
		
		public boolean hasCustomPrecision() throws Exception {
			return true;
		}
	};
	JCurrency pReportesCTR = new JCurrency() {
		public void preset() throws Exception {
			pReportesCTR.setValue(getReportesCtr());
		};
		public int getCustomPrecision() throws Exception {
			return 0;
		}
		
		public boolean hasCustomPrecision() throws Exception {
			return true;
		}
	};
	JCurrency pReportesMIT = new JCurrency() {
		public void preset() throws Exception {
			pReportesMIT.setValue(getReportesMit());
		};
		public int getCustomPrecision() throws Exception {
			return 0;
		}
		
		public boolean hasCustomPrecision() throws Exception {
			return true;
		}
	};
	JCurrency pCuf = new JCurrency() {
		public void preset() throws Exception {
			pCuf.setValue(getReportesCuf());
		};
		public int getCustomPrecision() throws Exception {
			return 0;
		}
		
		public boolean hasCustomPrecision() throws Exception {
			return true;
		}
	};
	JCurrency pRfnd = new JCurrency() {
		public void preset() throws Exception {
			pRfnd.setValue(getReportesRfnd());
		};
		public int getCustomPrecision() throws Exception {
			return 0;
		}
		
		public boolean hasCustomPrecision() throws Exception {
			return true;
		}
	};
	JCurrency pDks = new JCurrency() {
		public void preset() throws Exception {
			pDks.setValue(getReportesDKs());
		};
		public int getCustomPrecision() throws Exception {
			return 0;
		}
		
		public boolean hasCustomPrecision() throws Exception {
			return true;
		}
	};
	JCurrency pCarriers = new JCurrency() {
		public void preset() throws Exception {
			pCarriers.setValue(getReportesCarriers());
		};
		public int getCustomPrecision() throws Exception {
			return 0;
		}
		
		public boolean hasCustomPrecision() throws Exception {
			return true;
		}
	};
	public void setCompany(String value) throws Exception {
		this.pCompany.setValue(value);
	}
	
	public String getCompany() throws Exception {
		return this.pCompany.getValue();
	}
	
	
	



	/**
   * Constructor de la Clase
   */
  public BizReportes() throws Exception {
  }
  
  
  
  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "repprog", pReportesProgramados );
    this.addItem( "dqb", pReportesDQB );
    this.addItem( "mit", pReportesMIT );
    this.addItem( "ctr", pReportesCTR );
    this.addItem( "cuf", pCuf );
    this.addItem( "rfnd", pRfnd );
    this.addItem( "dks", pDks );
    this.addItem( "carriers", pCarriers );

  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( FIELD, "company", "company", true, false, 50 );
    this.addFixedItem( FIELD, "repprog", "reportes programados", true, false, 15 );
    this.addFixedItem( FIELD, "dqb", "reportes dqb", true, false, 15 );
    this.addFixedItem( FIELD, "mit", "reportes mit", true, false, 15 );
    this.addFixedItem( FIELD, "cuf", "reportes cuf", true, false, 15 );
    this.addFixedItem( FIELD, "rfnd", "reportes rfnd", true, false, 15 );
    this.addFixedItem( FIELD, "dks", "reportes dks", true, false, 15 );
    this.addFixedItem( FIELD, "carriers", "reportes carriers", true, false, 15 );
    this.addFixedItem( FIELD, "ctr", "reportes CTR917", true, false, 15 );

  }

  
  
}

