package pss.core.winUI.icons;

import javax.swing.ImageIcon;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiIconGalery extends JWin {


  public GuiIconGalery() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizIconGalery(); }
  @Override
	public int GetNroIcono()       throws Exception { return GuiIcon.REFRESH_ICON; }
  @Override
	public String GetTitle()       throws Exception { return "Datos del Icono"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return null; }
  @Override
	public String getKeyField()   throws Exception { return "nombre_file"; }
  @Override
	public String getDescripField()                  { return "nombre_file"; }

  @Override
	public void createActionMap() throws Exception {
//    SetAccionConsultar(1, "Consultar" );
//    SetAccionModificar(2, "Modificar" );
//    SetAccionEliminar (3, "Eliminar"  );
  }

  public BizIconGalery GetcDato() throws Exception {
   return (BizIconGalery) this.getRecord();
  }

  public ImageIcon GetImage() throws Exception {
    return GetcDato().GetImage();
  }

  @Override
	public String GetIconFile() throws Exception {
    return GetcDato().pFile.getValue();
  }

}


