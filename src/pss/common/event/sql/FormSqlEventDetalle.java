package pss.common.event.sql;

import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.dynamic.GuiDynamics;
import pss.common.customList.config.field.campo.BizCampo;
import pss.common.customList.config.field.campo.GuiCampos;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormSqlEventDetalle  extends JBaseForm {


	  public FormSqlEventDetalle() throws Exception {
	  }

	  public GuiSqlEvent getWin() { return (GuiSqlEvent) getBaseWin(); }


	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
	    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
	    AddItemEdit( null, ULONG, OPT, "id" ).setHide(true);
	    AddItemEdit( null, ULONG, OPT, "custom_list" ).setHide(true);
	    AddItemEdit( null, ULONG, OPT, "class_detalle" ).setHide(true);
	    JFormPanelResponsive row1 = (JFormPanelResponsive)AddItemRow().setSizeColumns(3);
	    JFormPanelResponsive row2 = (JFormPanelResponsive)AddItemRow().setSizeColumns(9);
	    
	    row1.AddItemEdit( "Descripción", CHAR, REQ, "descripcion" );
	    row1.AddItemArea( "Observación", CHAR, OPT, "observacion" );
	    row1.AddItemCombo( "Campo", CHAR, REQ, "id_campo" , new JControlCombo() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getCampos(one);
	    	}
	    });
	    getList(row1);
	    row2.AddCardPanel(60);


	  }
	
		public void getList(JFormPanelResponsive panel) throws Exception {
			BizCustomList list =  getWin().GetcDato().getObjCustomList();
			if (list==null ) return;
			getWin().GetcDato().getObjVariables();
			JRecords<BizCampo> ejes = list.getEjes();
			ejes.clearOrderBy();
			ejes.addOrderBy("secuencia");
			JIterator<BizCampo> it = ejes.getStaticIterator();
			int i=1;
			String smapa="";
			while (it.hasMoreElements()) {
				BizCampo eje = it.nextElement();
		    smapa+=i+"="+eje.getSecuencia()+"|";
		    if (eje.getObjRelationEje()!=null) {
			  	panel.AddItemWinLov( eje.getDescrCampo(), CHAR, OPT, "valor"+JTools.LPad(""+i, 2, "0"), getRecordsLOV(false, eje));	    	
		    } else {
		    	String tipo = eje.getTipoCampo();
		    	if (tipo.equalsIgnoreCase("JDATE")) {
		    		panel.AddItemDateTime( eje.getDescrCampo(), DATE, OPT, "valor"+JTools.LPad(""+i, 2, "0") );
						
		    	} else if (tipo.equalsIgnoreCase("JBOOLEAN")) {
		    		panel.AddItemCheck(  eje.getDescrCampo(), OPT, "valor"+JTools.LPad(""+i, 2, "0") );
						
		    	} else if (tipo.equalsIgnoreCase("JFLOAT")) {
		    		panel.AddItemEdit( eje.getDescrCampo(), FLOAT, OPT, "valor"+JTools.LPad(""+i, 2, "0") );
		    	} else  if (tipo.equalsIgnoreCase("JLONG")) {
		    		panel.AddItemEdit( eje.getDescrCampo(), LONG, OPT, "valor"+JTools.LPad(""+i, 2, "0") );
		    	} else if (tipo.equalsIgnoreCase("JCURRENCY")) {
		    		panel.AddItemEdit( eje.getDescrCampo(), FLOAT, OPT, "valor"+JTools.LPad(""+i, 2, "0") );
		    	}else {
		    		panel.AddItemEdit( eje.getDescrCampo(), CHAR, OPT, "valor"+JTools.LPad(""+i, 2, "0") );
								    		
		    	}
		    }
		   	i++;
			}
			panel.AddItemEdit( null, CHAR, OPT, "mapa" ).SetValorDefault(smapa).setHide(true);
			getWin().GetcDato().setMapa(smapa);
			JRecords<BizCampo> filtros= getWin().GetcDato().getObjCustomList().getObjFiltros();
			JIterator<BizCampo> itF = filtros.getStaticIterator();
			while (itF.hasMoreElements()) {
				BizCampo filtro = itF.nextElement();
				if (!filtro.isDinamico()) continue;

		    if (filtro.getObjRelationFiltroTarget()!=null) {
		    	panel.AddItemWinLov( filtro.getDescrFiltro(true,null), CHAR, OPT, "filtro"+JTools.LPad(""+i, 2, "0"), getRecordsLOV(false, filtro));	    	
		    } else {
		    	String tipo = filtro.getTipoCampo();
		    	if (tipo.equalsIgnoreCase("JDATE")) {
		    		panel.AddItemDateTime( filtro.getDescrFiltro(true,null), DATE, OPT, "filtro"+JTools.LPad(""+i, 2, "0") );
		    	} else if (tipo.equalsIgnoreCase("JBOOLEAN")) {
		    		panel.AddItemCheck( filtro.getDescrFiltro(true,null), OPT, "filtro"+JTools.LPad(""+i, 2, "0") );
		    	} else if (tipo.equalsIgnoreCase("JFLOAT")) {
		    		panel.AddItemEdit( filtro.getDescrFiltro(true,null), FLOAT, OPT, "filtro"+JTools.LPad(""+i, 2, "0") );
		    	} else  if (tipo.equalsIgnoreCase("JLONG")) {
		    		panel.AddItemEdit( filtro.getDescrFiltro(true,null), LONG, OPT, "filtro"+JTools.LPad(""+i, 2, "0") );
		    	} else if (tipo.equalsIgnoreCase("JCURRENCY")) {
		    		panel.AddItemEdit( filtro.getDescrFiltro(true,null), FLOAT, OPT, "filtro"+JTools.LPad(""+i, 2, "0") );
		    	}else {
		    		panel.AddItemEdit( filtro.getDescrFiltro(true,null), CHAR, OPT, "filtro"+JTools.LPad(""+i, 2, "0") );
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
	  		c.setRecords(list.getCampos());
	  	else 
	  		c.getRecords().addFixedFilter("true=false");
	  	return c;
	  }

	}  //  @jve:decl-index=0:visual-constraint="9,10"
