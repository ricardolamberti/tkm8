package  pss.common.components;

import java.io.File;

import pss.JPath;
import pss.core.services.records.JRecords;

public class BizClases extends JRecords<BizClase> {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizClases() throws Exception {
	}

	@Override
	public Class<BizClase> getBasedClass() {
		return BizClase.class;
	}

	@Override
	public String GetTable() {
		return "";
	}

	@Override
	public boolean readAll() throws Exception {

		String sDirectorio=this.getFilterValue("directorio");
		File oFile=new File(JPath.PssPath()+"/"+sDirectorio);
		File aFile[]=oFile.listFiles();
		for(int i=0; i<aFile.length; i++) {
			if (aFile[i].isDirectory()) {
				/*
				 * if ( this.GetVision().equals("Recursivo") ) { BizClases oClases = new BizClases(); oClases.SetFiltros("directorio", aFile[i].getName()); oClases.SetVision(this.GetVision()); oClases.ReadAll(); this.AppendDatos(oClases); }
				 */
				continue;
			}

			if (aFile[i].toString().lastIndexOf(".class")==-1) continue;

			BizClase oClase=new BizClase();
			oClase.pComponente.setValue(sDirectorio);
			oClase.pArchivo.setValue(aFile[i].getName());
			oClase.pClase.setValue(BizCompInstalado.FileToClass(sDirectorio, aFile[i].getName()));
			this.addItem(oClase);
		}
		this.setStatic(true);
		return true;
	}

}
