package  pss.bsp.pdf;

import pss.bsp.bo.BizInterfazBO;
import pss.bsp.bo.GuiInterfacesBO;
import pss.bsp.consolidador.IConsolidador;
import pss.bsp.consolidador.JConsolidador;
import pss.bsp.consolidador.consolidacion.detalle.GuiConsolidaciones;
import pss.bsp.consolidador.diferencia.GuiDiferencias;
import pss.bsp.consolidador.iva.GuiImpositivos;
import pss.bsp.consolidador.over.overAcum.GuiOverAcums;
import pss.bsp.parseo.JParseoFactory;
import pss.bsp.parseo.bspParseo.IBspParseo;
import pss.bsp.reembolsos.GuiReembolsos;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiPDF extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiPDF() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizPDF(); }
  public int GetNroIcono()   throws Exception { return 10056; }
  public String GetTitle()   throws Exception { return "Liquidacion BSP"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormPDF.class; }
  public Class<? extends JBaseForm> getFormNew() throws Exception { return FormNewPDF.class; }
  public String  getKeyField() throws Exception { return "idPDF"; }
  public String  getDescripField() { return "descripcion"; }
  public BizPDF GetcDato() throws Exception { return (BizPDF) this.getRecord(); }
   public void createActionMap() throws Exception {
		createActionQuery();
		createActionDelete();
// 		this.addAction(55, "Agregar Interfaz BO", null, 10058, true, true, true, "Group");
		BizAction  a=this.addAction(40, "Conciliar", null, 10045, true, true, true, "Group");
		a.setConfirmMessageDescription("Realizar la conciliacion puede insumir varios minutos");
		a.setMulti(true);
		
		
 		this.addAction(35, "Ver Conciliacion PNR", null, 10047, true, true, true, "Group");
		a=this.addAction(41, "Enviar datos a PNRs", null, 10006, true, true, true, "Group");
		a.setConfirmMessageDescription("Realizar la conciliacion puede insumir varios minutos");
		a.setMulti(true);
 	 	this.addAction(30, "Ver BO Conciliacion", null, 10047, true, true, true, "Group");
 		this.addAction(31, "Ver BO A.Diferencias", null, 10046, true, true, true, "Group");
 		this.addAction(32, "Ver BO A.Impositivo", null, 10029, true, true, true, "Group");
 	//	this.addAction(33, "Ver BO A.Com.Over", null, 10006, true, true, true, "Group");
 		this.addAction(10, "Cabecera", null, 10020, false, false, true, "Group");
 		this.addAction(20, "Detalle", null, 10020, false, false, true, "Group");
 		this.addAction(25, "Reembolsos", null, 10020, false, false, true, "Group");
 	  
		this.addAction(50, "Reporte CUF", null, 10029, true, true, true, "Group");
		 
   }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==30) return existsBO();
  	if (a.getId()==31) return existsBO();
  	if (a.getId()==32) return existsBO();
  	if (a.getId()==33) return existsBO();
  	if (a.getId()==50) return GetVision().equals("CUF");
    	return super.OkAction(a);
  }
  
  Boolean exists = null;
  public boolean existsBO() throws Exception {
  	if (exists!=null) return exists;
		JRecords<BizInterfazBO> bos= new JRecords<BizInterfazBO>(BizInterfazBO.class);
		bos.addFilter("company", this.GetcDato().getCompany());
		return exists=bos.exists();
  }
    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
//		if (a.getId() == 55)	return new JActNew(GuiModuloBSP.getBSPConsolaUsuario().getNewBO(),0);
		if (a.getId() == 10)	return new JActQuery(this.getHeader());
		if (a.getId() == 20)	return new JActWins(this.getDetail());
		if (a.getId() == 25)	return new JActWins(this.getDetailReembolsos());
		if (a.getId() == 30)	return new JActWins(this.getConciliacion(false));
		if (a.getId() == 35)	return new JActWins(this.getConciliacionPNR(false));
		if (a.getId() == 31)	return new JActWins(this.getDiferencia(false));
		if (a.getId() == 32)	return new JActWins(this.getImpositivo(false));
		if (a.getId() == 33)	return new JActWins(this.getOver(false));
		if (a.getId() == 40)	return new JActSubmit(this) {
			public void submit() throws Exception {
				getConciliacion(true);
			}  
		};		
		if (a.getId() == 41)	return new JActSubmit(this) {
			public void submit() throws Exception {
				Igualar();
			}  
		};
		if (a.getId() == 50 )	return new JActWins(this.getCuf());
		
		return null;
	}

	public JWin getHeader() throws Exception {
		IBspParseo parseo = (IBspParseo)JParseoFactory.getInstanceFromClass(GetcDato().getParseo());
		return parseo.getGuiHeader(GetcDato().getCompany(), GetcDato().getOwner(), GetcDato().getIdpdf());
	}
	public JWins getDetail() throws Exception {
		IBspParseo parseo = (IBspParseo)JParseoFactory.getInstanceFromClass(GetcDato().getParseo());
		return parseo.getGuisDetail(GetcDato().getCompany(), GetcDato().getOwner(), GetcDato().getIdpdf());
	}
	public JWins getCuf() throws Exception {
		IBspParseo parseo = (IBspParseo)JParseoFactory.getInstanceFromClass(GetcDato().getParseo());
		return parseo.getGuisDetail(GetcDato().getCompany(), GetcDato().getOwner(), GetcDato().getIdpdf());
	}
	public JWins getDetailReembolsos() throws Exception {
		GuiReembolsos reems = new GuiReembolsos();
		reems.getRecords().addFilter("company",  GetcDato().getCompany());
		reems.getRecords().addFilter("origen", GetcDato().getIdpdf());
		
		return reems;
	}
	public void Igualar() throws Exception {
		GetcDato().execProcessToPNRs();
		GetcDato().execProcessRepoblate();
	}
	public JWins getConciliacion(boolean force) throws Exception {
		generateConciliancion(force);
		GuiConsolidaciones con =	new GuiConsolidaciones();
		con.getRecords().addFilter("company", GetcDato().getCompany());
//		con.getRecords().addFilter("owner", GetcDato().getOwner());
		con.getRecords().addFilter("tipo", IConsolidador.BO);
		con.getRecords().addFilter("idPDF", GetcDato().getIdpdf());
		return con;
	}
	public JWins getConciliacionPNR(boolean force) throws Exception {
		generateConciliancion(force);
		GuiConsolidaciones con =	new GuiConsolidaciones();
		con.getRecords().addFilter("company", GetcDato().getCompany());
//		con.getRecords().addFilter("owner", GetcDato().getOwner());
		con.getRecords().addFilter("tipo", IConsolidador.PNR);
		con.getRecords().addFilter("idPDF", GetcDato().getIdpdf());
		return con;
	}
	public JWins getDiferencia(boolean force) throws Exception {
		generateConciliancion(force);
		GuiDiferencias con =	new GuiDiferencias();
		con.getRecords().addFilter("company", GetcDato().getCompany());
//		con.getRecords().addFilter("owner", GetcDato().getOwner());
		con.getRecords().addFilter("idPDF", GetcDato().getIdpdf());
		return con;
	}
	public JWins getOver(boolean force) throws Exception {
		generateConciliancion(force);
		GuiOverAcums con =	new GuiOverAcums();
		con.getRecords().addFilter("company", GetcDato().getCompany());
//		con.getRecords().addFilter("owner", GetcDato().getOwner());
		con.getRecords().addFilter("idPDF", GetcDato().getIdpdf());
		return con;
	}
	public JWins getImpositivo(boolean force) throws Exception {
		generateConciliancion(force);
		GuiImpositivos con =	new GuiImpositivos();
		con.getRecords().addFilter("company", GetcDato().getCompany());
//		con.getRecords().addFilter("owner", GetcDato().getOwner());
		con.getRecords().addFilter("idPDF", GetcDato().getIdpdf());
		return con;
	}
	private void generateConciliancion(boolean force) throws Exception {
		JConsolidador cons = new JConsolidador();
		cons.setPdf(this);
		cons.consolidar(force);
	}
	
 }
