package  pss.bsp.consola.dashBoard;

import pss.common.customList.config.customlist.BizCustomList;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormDashBoard extends JBaseForm {


	private static final long serialVersionUID = 1245260233503L;

	  /**
	   * Propiedades de la Clase
	   */
	//JPssLabel lusuario = new JPssLabel();
	//JPssEdit usuario = new JPssEdit  ();
	//JPssLabel lusos = new JPssLabel();


	

	  /**
	   * Constructor de la Clase
	   */
	  public FormDashBoard() throws Exception {
	  }

	  public GuiDashBoard getWin() { return (GuiDashBoard) getBaseWin(); }

	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
	  		setAutoRefresh(true, 10000, "DASHBOARD");
	  		
	  		

		  	JRecords<BizCustomList> grps= getWin().GetcDato().getObjFavoritos();
		  	JIterator<BizCustomList> it = grps.getStaticIterator();
		  	int size=grps.getStaticItems().size();
		  	int tama[]= new int[100];
		  	if (size==1) tama=(new int[]{12});
		  	if (size==2) tama=(new int[]{6,6});
		  	if (size==3) tama=(new int[]{12,6,6});
		  	if (size==4) tama=(new int[]{12,4,4,4});
		  	if (size==5) tama=(new int[]{12,6,6,6,6});
		  	if (size==6) tama=(new int[]{12,6,6,4,4,4});
		  	if (size==7) tama=(new int[]{12,6,6,6,6,6,6});
		  	if (size>7) for(int j=0;j<size;j++) tama[j]=4;
		  	int tamaMD[]= new int[100];
		  	if (size==1) tamaMD=(new int[]{12});
		  	if (size==2) tamaMD=(new int[]{6,6});
		  	if (size==3) tamaMD=(new int[]{12,6,6});
		  	if (size==4) tamaMD=(new int[]{12,6,6,12});
		  	if (size==5) tamaMD=(new int[]{12,6,6,6,6});
		  	if (size==6) tamaMD=(new int[]{12,6,6,6,6,12});
		  	if (size==7) tamaMD=(new int[]{12,6,6,6,6,6,6});
		  	if (size>7) for(int j=0;j<size;j++) tamaMD[j]=6;
		  	int i=0;
		  	while (it.hasMoreElements()) {
		  		BizCustomList list = it.nextElement();
//			  	AddImageCard(list.getDescripcion(),"Ver mas",100+i,1).setSizeColumns(tama[i]).setHeight(300);
		  		AddCardPanel(200+i).setDiferido(true).setComplexColumnClass("clearfix col-"+(tama[i])+" col-xl-"+(tama[i])+" col-lg-"+(tamaMD[i])+" col-md-"+(tamaMD[i])+" col-sm-12) col-xs-12");;
			  	i++;
		  	}


		  } 


	@Override
	public boolean isFullSize() throws Exception {
		return true;
	}
	public boolean isFixHight() throws Exception {
		return false;
	}
	public boolean isFixWidth() throws Exception {
		return false;
	}
	




	 
}