package pss.common.documentos;

import org.apache.cocoon.transformation.AbstractSAXTransformer;

import pss.common.documentos.docElectronico.GuiDocElectronico;
import pss.common.documentos.docEmail.GuiDocEmail;
import pss.common.documentos.docLocal.GuiDocLocal;
import pss.common.documentos.hitos.GuiHitos;
import pss.common.documentos.tipos.BizDocFisicoTipo;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiDocum extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiDocum() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizDocum(); }
  public int GetNroIcono()   throws Exception { return 10020; }
  public String GetTitle()   throws Exception { return "Documento Físico"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormDocum.class; }
  public String  getKeyField() throws Exception { return "id_doc"; }
  public String  getDescripField() { return "titulo"; }
  public BizDocum GetcDato() throws Exception { return (BizDocum) this.getRecord(); }

	public void createActionMap() throws Exception {
		this.createActionQuery();
		addAction(30, "Eventos", null, 10017, false, false);
		addAction(50, "Ver Imagen", null, 10051, true, true).setNuevaVentana(true);
		addAction(60, "Escanear", null, 10048, true, true);
		addAction(70, "Imprimir", null, 10048, true, true);
	}
	
	@Override
	public boolean OkAction(BizAction act) throws Exception {
		if (act.getId()==70) return isPrinteable();
		return super.OkAction(act);
	}
	
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==70) return getDocumento().getActionPrint();
		return super.getSubmitFor(a);
	}
	
	public JWins getOperaciones() throws Exception {
		JWins records=new GuiHitos();
		records.getRecords().addFilter("id_doc", GetcDato().getIddoc());
		records.setParent(this);
		records.addOrderAdHoc("fecha", "DESC");

		return records;
	}
	
  public static GuiDocumento virtualCreate(String type) throws Exception {
  	GuiDocumento doc= (BizUsuario.getUsr().getObjBusiness().documentWinCreate(type));
  	if (doc==null)
  		JExcepcion.SendError("Tipo Doc Invalido");
  	return doc;
  }
  public boolean isPrinteable() throws Exception {
  	return GetcDato().isPrinteable();
  }
 
  public GuiDocumento getDocumento() throws Exception {
  	GuiDocumento doc = virtualCreate(this.GetcDato().getTipoDoc());
  	doc.setRecord(this.GetcDato().getObjDocumento());
  	doc.setCanConvertToURL(false);
  	return doc;
	}

  public JWin getRelativeWin() throws Exception {
  	return this.getDocumento();
	}

}
