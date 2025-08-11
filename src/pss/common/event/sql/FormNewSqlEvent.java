package pss.common.event.sql;

import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.dynamic.GuiDynamics;
import pss.common.customList.config.field.campo.BizCampo;
import pss.common.customList.config.field.campo.GuiCampos;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;

public class FormNewSqlEvent  extends JBaseForm {


	  public FormNewSqlEvent() throws Exception {
	  }

	  public GuiSqlEvent getWin() { return (GuiSqlEvent) getBaseWin(); }

	  public void InicializarPanel( JWin zWin ) throws Exception {
	    AddItemEdit( "company", CHAR, OPT, "company" );
	    AddItemEdit( "descripcion", CHAR, REQ, "descripcion" );
	    AddItemArea( "Observacion", CHAR, OPT, "observacion" );
	    AddItemDateTime( "fecha_emergencia", DATE, OPT, "fecha_emergencia" );
	    AddItemDateTime( "fecha_aviso", DATE, OPT, "fecha_aviso" );
	    AddItemDateTime( "fecha_minimo", DATE, OPT, "fecha_minimo" );
	    AddItemEdit( "valor_emergencia", FLOAT, OPT, "valor_emergencia" );
	    AddItemEdit( "valor_aviso", FLOAT, OPT, "valor_aviso" );
	    AddItemEdit( "valor_minimo", FLOAT, OPT, "valor_minimo" );
	    AddItemArea( "consulta", CHAR, OPT, "consulta" ).setVisible(BizUsuario.IsAdminCompanyUser());
	    AddItemArea( "Detalle", CHAR, OPT, "consulta_detalle" ).setVisible(BizUsuario.IsAdminCompanyUser());
	    //AddItem( campo, CHAR, OPT, "campo" );
	    AddItemEdit( "id", ULONG, OPT, "id" );
	    AddItemEdit( "Custom list", ULONG, OPT, "custom_list" );
	    AddItemEdit( "clase", ULONG, OPT, "class_detalle" );
	    AddItemCombo( "Campo", CHAR, REQ, "id_campo" , new JControlCombo() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getCampos(one);
	    	}
	    });
	    getList();


	  }

		
		public void getList() throws Exception {
			BizCustomList list =  getWin().GetcDato().getObjCustomList();
			if (list==null ) return;
			JRecords<BizCampo> ejes = list.getEjes();
			ejes.clearOrderBy();
			ejes.addOrderBy("secuencia");
			JIterator<BizCampo> it = ejes.getStaticIterator();
			int pos = 145;
			int i=1;
			String smapa="";
			while (it.hasMoreElements()) {
				BizCampo eje = it.nextElement();
				this.AddItemLabel(eje.getDescrCampo()); 
		    smapa+=i+"="+eje.getSecuencia()+"|";
		    if (eje.getObjRelationEje()!=null) {
			  	AddItemWinLov( null, CHAR, OPT, "valor"+JTools.LPad(""+i, 2, "0"), getRecordsLOV(false, eje));	    	
		    } else {
		    	String tipo = eje.getTipoCampo();
		    	if (tipo.equalsIgnoreCase("JDATE")) {
				    AddItemDateTime( null, DATE, OPT, "valor"+JTools.LPad(""+i, 2, "0") );
		    	} else if (tipo.equalsIgnoreCase("JBOOLEAN")) {
				    AddItemCheck( null, OPT, "valor"+JTools.LPad(""+i, 2, "0") );
		    	} else if (tipo.equalsIgnoreCase("JFLOAT")) {
				    AddItemEdit( null, FLOAT, OPT, "valor"+JTools.LPad(""+i, 2, "0") );
		    	} else  if (tipo.equalsIgnoreCase("JLONG")) {
				    AddItemEdit( null, LONG, OPT, "valor"+JTools.LPad(""+i, 2, "0") );
		    	} else if (tipo.equalsIgnoreCase("JCURRENCY")) {
				    AddItemEdit( null, FLOAT, OPT, "valor"+JTools.LPad(""+i, 2, "0") );
		    	}else {
				    AddItemEdit( null, CHAR, OPT, "valor"+JTools.LPad(""+i, 2, "0") );
								    		
		    	}
		    }
		   	i++;
			}
	    AddItem( "Mapa", CHAR, OPT, "mapa" ).SetValorDefault(smapa);
	    getWin().GetcDato().setMapa(smapa);
			JRecords<BizCampo> filtros= getWin().GetcDato().getObjCustomList().getObjFiltros();
			JIterator<BizCampo> itF = filtros.getStaticIterator();
			while (itF.hasMoreElements()) {
				BizCampo filtro = itF.nextElement();
				if (!filtro.isDinamico()) continue;
				this.AddItemLabel(filtro.getDescrFiltro(true,null));
//				JPssLabel label = new JPssLabel();
//				pos += 24;
//				label.setBounds(new Rectangle(14, pos, 140, 22)); 
//				label.setText(filtro.getDescrFiltro(true,null));
//		    this.add(label);

		    if (filtro.getObjRelationFiltroTarget()!=null) {
//			    JPssLabelFormLov lov = new JPssLabelFormLov();
//					lov.setBounds(new Rectangle(155, pos, 176, 22)); 
//					this.add(lov);
			  	AddItemWinLov( null, CHAR, OPT, "filtro"+JTools.LPad(""+i, 2, "0"), getRecordsLOV(false, filtro));	    	
		    } else {
		    	String tipo = filtro.getTipoCampo();
		    	if (tipo.equalsIgnoreCase("JDATE")) {
//				    JPssCalendarEdit edit = new JPssCalendarEdit();
//						edit.setBounds(new Rectangle(105, pos, 76, 22)); 
//						this.add(edit);
				    AddItemEdit( null, DATE, OPT, "filtro"+JTools.LPad(""+i, 2, "0") );
		    	} else if (tipo.equalsIgnoreCase("JBOOLEAN")) {
//				    JCheckBox edit = new JCheckBox();
//						edit.setBounds(new Rectangle(155, pos, 76, 22)); 
//						this.add(edit);
				    AddItemCheck( null, OPT, "filtro"+JTools.LPad(""+i, 2, "0") );
		    	} else if (tipo.equalsIgnoreCase("JFLOAT")) {
//				    JPssEdit edit = new JPssEdit();
//						edit.setBounds(new Rectangle(155, pos, 76, 22)); 
//						this.add(edit);
				    AddItemEdit( null, FLOAT, OPT, "filtro"+JTools.LPad(""+i, 2, "0") );
		    	} else  if (tipo.equalsIgnoreCase("JLONG")) {
//				    JPssEdit edit = new JPssEdit();
//						edit.setBounds(new Rectangle(155, pos, 76, 22)); 
//						this.add(edit);
				    AddItemEdit( null, LONG, OPT, "filtro"+JTools.LPad(""+i, 2, "0") );
		    	} else if (tipo.equalsIgnoreCase("JCURRENCY")) {
//				    JPssEdit edit = new JPssEdit();
//						edit.setBounds(new Rectangle(155, pos, 76, 22)); 
//						this.add(edit);
				    AddItemEdit( null, FLOAT, OPT, "filtro"+JTools.LPad(""+i, 2, "0") );
		    	}else {
//				    JPssEdit edit = new JPssEdit();
//						edit.setBounds(new Rectangle(155, pos, 176, 22)); 
//						this.add(edit);
				    AddItemEdit( null, CHAR, OPT, "filtro"+JTools.LPad(""+i, 2, "0") );
		    	}
		    }
		   	i++;
			}
		}
		
		
	  private JWins getRecordsLOV(boolean one,BizCampo eje) throws Exception {
	  	return GuiDynamics.createWinsByEje(one, eje);
	  }

	  private JWins getCampos(boolean one) throws Exception {
	  	BizCustomList list = getWin().GetcDato().getObjCustomList();
	  	GuiCampos c = new GuiCampos();
	  	if (list!=null) 
	  		c.setRecords(list.getObjAllCampos());
	  	else 
	  		c.getRecords().addFixedFilter("true=false");
	  	return c;
	  }
	}  
