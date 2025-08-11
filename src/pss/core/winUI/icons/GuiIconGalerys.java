package pss.core.winUI.icons;

import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;

import pss.JPss;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;

public class GuiIconGalerys extends JWins {

  //////////////////////////////////////////////////////////////////////////////
  //
  //   STATIC VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////
  private static final String DEFAULT_ICON_FILE = "default.gif";
  private static final String DEFAULT_ICON_PATH = "/pss/core/ui/Images";
  private static GuiIconGalerys oGlobal;


  //////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////
  private String iconPath = DEFAULT_ICON_PATH;
  private JMap<String,BizIconGalery> oTabla;


  //////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////
  public GuiIconGalerys() throws Exception {
  }


  @Override
	public JRecords<BizIconGalery> ObtenerDatos() throws Exception { return new BizIconGalerys();}
  @Override
	public Class<? extends JWin> GetClassWin() { return GuiIconGalery.class; }
  @Override
	public int GetNroIcono() throws Exception { return GuiIcon.REFRESH_ICON; }
  @Override
	public String GetTitle() throws Exception { return "Iconos"; }


  //////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS
  //
  //////////////////////////////////////////////////////////////////////////////
  //
  //   icon gallery managing
  //
  public static synchronized GuiIconGalerys GetGlobal() throws Exception {
    if (oGlobal != null) return oGlobal;

    GuiIconGalerys.loadIconGallery();
    return oGlobal;
  }
  public ImageIcon getIcon(int zIconNumber) throws Exception {
    return getIcon(GuiIconos.GetGlobal().buscarIcono(zIconNumber).GetFile());
  }
  public ImageIcon getIcon(String zIconFile) throws Exception {
    if ( zIconFile == null ) return getDefaultIcon();
    BizIconGalery oIcon = (BizIconGalery) oTabla.getElement(zIconFile.toUpperCase());
    if ( oIcon == null ) return getDefaultIcon();
    return oIcon.GetImage();
  }
  public ImageIcon getDefaultIcon() throws Exception {
    return this.getIcon(DEFAULT_ICON_FILE);
  }
  private static synchronized void loadIconGallery() throws Exception {
    new GuiIconGalerys().loadIconsFromDir();
  }
  private synchronized void loadIconsFromDir() throws Exception {

    PssLogger.logWait("Cargando archivos de íconos...");

    try {
      oGlobal = this;
      oTabla  = JCollectionFactory.createMap(1024);
      URL uURL = JPss.class.getResource(this.iconPath);

      if ( uURL == null ) JExcepcion.SendError("No se encontró el directorio de íconos");

      File oFile = new File(uURL.getFile());
      File aFile[] = oFile.listFiles();
      String fileName;
      for ( int i=0; i < aFile.length ; i++ ) {
        fileName = aFile[i].getName();
        if( aFile[i].length() > 1024*5 ) {
          PssLogger.logDebug( "Icono no cargado: " + fileName );
          continue;
        }
        GuiIconGalery oIcon = createIconObjectFor(fileName);
        oTabla.addElement(fileName.toUpperCase(), oIcon.GetcDato());
        this.addRecord(oIcon);
      }
      oTabla.addElement(DEFAULT_ICON_FILE.toUpperCase(), createIconObjectFor(DEFAULT_ICON_FILE).GetcDato());
      this.SetEstatico(true);
      this.setCanConvertToURL(false);
      
      PssLogger.logWait("Iconos cargados");
    } catch (Exception ex) {
      PssLogger.logError(ex, "No se pudieron cargar los íconos");
      throw ex;
    }

  }

  private static GuiIconGalery createIconObjectFor(String zIconFile) throws Exception {
    GuiIconGalery oIcon = new GuiIconGalery();
    oIcon.GetcDato().pFile.setValue(zIconFile);
    return oIcon;
  }

  //
  //   superclass methods overridding
  //
  @Override
	public void createActionMap() throws Exception {
//    SetAccionAlta(1, "Nuevo Icono" ) ;
  }


  /**
   * @deprecated user GetGlobal().getIcon(...)
   * @param zIconFile
   * @return
   * @throws Exception
   */
  @Deprecated
	public static ImageIcon GetImage(String zIconFile) throws Exception {
    return GetGlobal().getIcon(zIconFile);
  }

}

