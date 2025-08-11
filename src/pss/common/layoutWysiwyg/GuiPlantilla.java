package  pss.common.layoutWysiwyg;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang.CharEncoding;

import pss.JPath;
import pss.common.layoutWysiwyg.permisos.GuiOwnerPlantillas;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFileGenerate;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActUpdate;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIcon;

public class GuiPlantilla extends JWin {
	public static final String PLANTILLA = "PLANTILLA";


  /**
   * Constructor de la Clase
   */
  public GuiPlantilla() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizPlantilla(); }
  public int GetNroIcono()   throws Exception { return 10036; }
  public String GetTitle()   throws Exception { 
  	if (GetVision().equals(PLANTILLA) )
  		return "Plantilla"; 
  	return "Tipo documento"; 
  }
  public Class<? extends JBaseForm> getFormBase() throws Exception { 
  	if (GetVision().equals(PLANTILLA) )
  		return FormPlantilla.class; 
  	return FormPlantillaGeneral.class; 
  }
  public String  getKeyField() throws Exception { return "id_tipo"; }
  public String  getDescripField() { return "descripcion"; }
  public BizPlantilla GetcDato() throws Exception { return (BizPlantilla) this.getRecord(); }

  public void createActionMap() throws Exception {
		addAction(200	, "Editar Plantilla" ,null, GuiIcon.MODIFICAR_ICON, true, true);
		BizAction a =addAction(300	, "Limpiar Plantilla" ,null, GuiIcon.REMOVE_ALL_ICON, true, true);
		a.setOnlyInForm(true);
		a.setConfirmMessage(true);
		addAction(401	, "Usuarios" ,null, 10074, false, false);
		addAction(500	, "Clonar" ,null, 963, true, true);
		addAction(600	, "Habilitar a Todos" ,null, 3, true, true).setMulti(true);
		addAction(700,   "A PDF", null, 10050, true, true).setNuevaVentana(true);
		addAction(701,   "A HTML", null, 10050, true, true).setImprimir(true);
		super.createActionMap();
  }


  @Override
  public boolean OkAction(BizAction a) throws Exception {
		if (a.getId()==2) return GetcDato().canModificar();
		if (a.getId()==3) return GetcDato().canBorrar();
		if (a.getId()==200) return !isVisionPlantilla() && GetcDato().canModificar();
		if (a.getId()==300) return !isVisionPlantilla() && GetcDato().canBorrar();
		return super.OkAction(a);
  }
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==200) return new JActUpdate(getVisionPlantilla(),2);
		if (a.getId()==300) return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				GetcDato().setPlantilla("");
				GetcDato().execProcessUpdate();
				super.submit();
			}
		};
		if (a.getId()==401) return new JActWins(getUsuarios());
		if (a.getId()==500) return new JActNew(getClon(),4,true,false,true);
		if (a.getId()==600) return getActProcessATodos();
		if (a.getId()==700) return getPrintAction();
		if (a.getId()==701) return getPrintAction2();

		return super.getSubmitFor(a);
	}
	
	public JAct getPrintAction2() throws Exception {
	  return new JActFileGenerate(this, 0) {
			public String generate() throws Exception {
				String filename = "test.html";
				String fullfilename= JPath.PssPathTempFiles() + "/" + filename;
				File f = new File(fullfilename);
				FileOutputStream o = new FileOutputStream(f);
				o.write(JTools.stringToByteVector(new URLCodec().decode(GetcDato().fillDatos(null, 0), CharEncoding.ISO_8859_1)));
				o.close();
				return filename;
			}
		};
	}
	public JAct getPrintAction() throws Exception {
	  return new JActFileGenerate(this, 0) {
			public String generate() throws Exception {
				
				return GetcDato().htmlToPdf("test.pdf", true, GetcDato().fillDatos(null, 0));
			}
		};
	}
  public JAct getActProcessATodos() throws Exception {
  	return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				 GetcDato().execProcessATodos();
			}
		};
  } 
 
  
	boolean isVisionPlantilla() throws Exception {
		return GetVision().equals(PLANTILLA);
	}
	GuiPlantilla getVisionPlantilla() throws Exception {
		GuiPlantilla g = new GuiPlantilla();
		g.setRecord(this.getRecord());
		g.SetVision(PLANTILLA);
		return g;
	}
	
	public JWin getClon() throws Exception {
		GuiPlantilla newDoc = new GuiPlantilla();
		newDoc.setRecord(GetcDato().getClon());
		newDoc.setDropListener(this);
		return newDoc;
	}

	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}
	
	public JWins getUsuarios() throws Exception {
		GuiOwnerPlantillas r = new GuiOwnerPlantillas();
		r.getRecords().addFilter("id_plantilla", GetcDato().getIdtipo());
		return r;
	}
		
	
	@Override
	public JAct Drop(JBaseWin zBaseWin) throws Exception {
		GuiPlantilla lt = (GuiPlantilla) zBaseWin;
		this.GetcDato().execProcessClon(lt.GetcDato());
		GuiPlantilla nlt = new GuiPlantilla();
		nlt.GetcDato().Read(lt.GetcDato().getIdtipo());
		return new JActQuery(nlt);
	}
 }
