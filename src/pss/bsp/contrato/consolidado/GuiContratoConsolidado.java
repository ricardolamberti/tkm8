package pss.bsp.contrato.consolidado;

import java.util.Calendar;

import pss.bsp.contrato.GuiContrato;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.FormDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.contrato.logica.ILogicaContrato;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.winUI.forms.JBaseForm;

public class GuiContratoConsolidado extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiContratoConsolidado() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizContratoConsolidado(); }
  public int GetNroIcono()   throws Exception { return 15014; }
  public String GetTitle()   throws Exception { return "Contrato"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormDetalle.class; }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "descripcion"; }
  public BizContratoConsolidado GetcDato() throws Exception { return (BizContratoConsolidado) this.getRecord(); }

  public void createActionMap() throws Exception {
   	this.addAction(10, "Contrato", null, 5012, true, true, true, "Group" );
 //  	this.addAction(20, "Detalle", null, 5012, true, true, true, "Group" );
		this.createActionQuery();
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	 	return super.OkAction(a);
  }
    
	@Override
	public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==1) return new JActQuery(this.getDetail(a));
		if (a.getId() == 10)  return new JActQuery(getContrato(a));

		return super.getSubmit(a);
	}

	@Override
	public int GetDobleClick(String zColName,boolean eje) throws Exception {
		if (zColName==null) return 1;
		if (zColName.indexOf("descr_contrato")!=-1) return 10;
		return 1;
	}

	public int getFieldColspan(String zColName) throws Exception {
		if (zColName.equals("descr_variable")) return 1;
		if (zColName.equals("descripcion")) return 1;
		if (zColName.equals("descr_contrato")) return 1;
		if (GetcDato().getFHasta()==null) return 1;
		if (GetcDato().getFDesde()==null) return 1;

		Calendar fDesde= Calendar.getInstance();
		fDesde.setTime(GetcDato().getFDesde());
		Calendar fHasta= Calendar.getInstance();
		fHasta.setTime(GetcDato().getFHasta());
		if (fDesde.get(Calendar.YEAR)==fHasta.get(Calendar.YEAR)) {
			return (GetcDato().getFHasta().getMonth()-GetcDato().getFDesde().getMonth())+1;
		}
		if (GetcDato().getAnio()==fHasta.get(Calendar.YEAR)) {
			return fHasta.get(Calendar.MONTH)+1;
		}
		return 12-(fDesde.get(Calendar.MONTH));
	}
	public GuiDetalle getDetail(BizAction a) throws Exception {
		GuiDetalle win = new GuiDetalle();
		
		win.GetcDato().read(GetcDato().getLinea());
  	ILogicaContrato logica = win.GetcDato().getObjLogicaInstance();
  	GuiDetalle winD = logica.getWin();
  	BizDetalle biz = logica.getBiz();
  	biz.copyProperties(win.GetcDato());
  	winD.setRecord(biz);
  	return winD;
	}
	public GuiContrato getContrato(BizAction a) throws Exception {
		GuiContrato win = new GuiContrato();
		win.GetcDato().read(GetcDato().getId());
		return win;
	}
	
	@Override
	public String getFieldTooltip(String zColName) throws Exception {
		
		if (GetcDato().getObjContrato()!=null && GetcDato().getObjContrato().hasAtencion())
			return GetcDato().getObjContrato().getAtencion();
		return super.getFieldTooltip(zColName);
	}



 }
