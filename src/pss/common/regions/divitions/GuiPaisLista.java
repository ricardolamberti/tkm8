package  pss.common.regions.divitions;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiPaisLista extends JWin {
  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiPaisLista() throws Exception {
  }

	public static String VISION_NACIONALIDAD = "VISION_NAC";
	
  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizPaisLista(); }
  @Override
	public int GetNroIcono()       throws Exception { return 1; }
  @Override
	public String GetTitle()       throws Exception { return "País"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormPaisLista.class; }
  @Override
  public Class<? extends JBaseForm> getFormEmbedded() throws Exception {
  	return FormPaisListaEmbedded.class;
  }
  @Override
	public String getKeyField()   throws Exception { return "pais"; }
  @Override
	public String getDescripField()                  { return (this.GetVision().equals(VISION_NACIONALIDAD))?"gentilicio": "descripcion"; }



  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionQuery( 1, "Consultar" );
    addActionUpdate( 2, "Modificar" );
    addActionDelete ( 3, "Eliminar" );


  }
  public BizPaisLista GetcDato() throws Exception {
    return (BizPaisLista) this.getRecord();
  }

  @Override
	public String GetIconFile() throws Exception {
    return GetcDato().GetIcono();
  }


	
}


