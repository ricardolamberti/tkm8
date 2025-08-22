package pss.tourism.pnr;

import java.io.File;

import pss.JPath;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiPNRFiles  extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiPNRFiles() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15006; } 
  public String  GetTitle()    throws Exception  { return "Archivos interfaces"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiPNRFile.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
 //   addActionNew( 1, "Nuevo Registro" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
  	zLista.AddColumnaLista("descripcion");  	
  }
	public void readAll(GuiPNRReserva ticket) throws Exception {
		setRecords(ticket.GetcDato().getArchivos());

	} 
	public void readAll(GuiPNRTicket ticket) throws Exception {
		setRecords(ticket.GetcDato().getArchivos());

	}
	public void readAll(GuiPNROtro ticket) throws Exception {
		setRecords(ticket.GetcDato().getArchivos());

	}
	public void readAllErrors() throws Exception {
		setRecords(getErrors());

	}
	JRecords<BizPNRFile> objErrores;
	public JRecords<BizPNRFile> getErrors() throws Exception {
			if (objErrores!=null) return objErrores;
			String directory = "";
			if (!BizUsuario.IsAdminUser()) 
				directory=BizUsuario.getUsr().getCompany().toUpperCase();
			
			JRecords<BizPNRFile> files = new JRecords<BizPNRFile>(BizPNRFile.class);

			files.setStatic(true);
			files.clearStaticItems();
			files.SetVision(GetVision());
			getErrors(files,directory);
			return objErrores=files;
	}
	public void getErrors(JRecords<BizPNRFile> files,String directory) throws Exception {
		File dir = new File(JPath.PssPathInputError() + "/" + directory);
		File fnames[] = dir.listFiles();

		if (fnames == null) return;
		
		for (int i = 0; i < fnames.length; i++) {
				if (fnames[i].isDirectory()) {
					getErrors(files,directory+"/"+fnames[i].getName());
					continue;
				}

				String fname = fnames[i].getName();
				BizPNRFile f = new BizPNRFile();
				f.setPNR("");
				f.setCompany(directory);
				f.setArchivo(fname);
				f.setDirectory(directory);
				f.setDescripcion(fname);
				f.SetVision(GetVision());
				files.getStaticItems().addElement(f);

			}

	}
}
