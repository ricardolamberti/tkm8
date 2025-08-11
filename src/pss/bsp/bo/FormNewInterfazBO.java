package  pss.bsp.bo;

import pss.bsp.bo.formato.GuiFormatos;
import pss.bsp.bspBusiness.BizBSPUser;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormComboResponsive;


public class FormNewInterfazBO extends JBaseForm {


	private static final long serialVersionUID = 1245253394589L;

	  
//	JComboBox iata = new JComboBox();
	  /**
	   * Constructor de la Clase
	   */
	  public FormNewInterfazBO() throws Exception {
	  }

	  public GuiInterfazBO getWin() { return (GuiInterfazBO) getBaseWin(); }

	

		private String getDefaultFormato() throws Exception {
			return BizBSPUser.getUsrBSP().getBspConsola().getConfigView().getDefaultFormato();
		}
		private String getDefaultInterfaz() throws Exception {
			String val= BizBSPUser.getUsrBSP().getBspConsola().getConfigView().getDefaultInterfaz();
			if (val.equals("")) return "NEW";
			return val;
		}
	  
	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
	    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
	    AddItemEdit( null, CHAR, OPT, "owner" ).setHide(true);
	    AddItemEdit( null, CHAR, OPT, "idInterfaz" ).setHide(true);
	    AddItemEdit( "Descripcion", CHAR, OPT, "descripcion" );
	    
	    JFormComboResponsive combo = AddItemCombo( "Tipo interfaz", CHAR, OPT, "tipo_interfaz" , getTipoInterfaz());
	    combo.SetValorDefault(getDefaultInterfaz());
	    combo = AddItemCombo( "Formato", CHAR, OPT, "id_formato",  new JControlCombo() {@Override
	  		public JWins getRecords(boolean one) throws Exception {return getFormatos(one);}} );
	    combo.SetValorDefault(getDefaultFormato());
      AddItemFile( "Archivo Interfaz", CHAR, OPT, "filename" );
      AddItemDateTime( "Periodo Desde", DATE, REQ, "fecha_desde" ).setSizeColumns(6);
      AddItemDateTime( " Hasta", DATE, REQ, "fecha_hasta" ).setSizeColumns(6);
  //    AddItem( iata, CHAR, REQ, "iata" , ((ISitita)BizUsuario.getUsr().getObjLicense().getLicense()).getIATAs());
      AddItemEdit( "Iata", CHAR, REQ, "iata" ).SetValorDefault("-");;

	  } 
	  
	  private JWins getFormatos(boolean one) throws Exception {
	  	GuiFormatos guis = new GuiFormatos();
	  	if (one) {
		  	guis.getRecords().addFilter("company", this.getWin().GetcDato().getCompany());
//		  	guis.getRecords().addFilter("owner", this.getWin().GetcDato().getOwner());
		  	guis.getRecords().addFilter("id_formato", this.getWin().GetcDato().getFormatoInterfaz());
	  	} else {
		  	guis.getRecords().addFilter("company", this.findControl("company").getValue());
//		  	guis.getRecords().addFilter("owner", this.findControl("owner").getValue());
	  	}
	  	return guis;
	 
	  }
	  private JWins getTipoInterfaz() throws Exception {
	  	return JWins.createVirtualWinsFromMap(BizInterfazBO.getTiposInterfaz());
	  }
	}  //  @jve:decl-index=0:visual-constraint="10,10" 
