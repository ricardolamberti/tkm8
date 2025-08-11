package pss.common.components;

import java.io.File;

import pss.JPath;
import pss.common.regions.multilanguage.JLanguage;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;

public class BizDirectorios extends JRecords<BizDirectorio> {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizDirectorios() throws Exception {
	}

	@Override
	public Class<BizDirectorio> getBasedClass() {
		return BizDirectorio.class;
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
		if (aFile==null) JExcepcion.SendError("El directorio^ '"+JPath.PssPath()+"/"+sDirectorio+JLanguage.translate("' no existe, pero esta configurado en el producto. Se deberá eliminarlo de la confiuración del producto."));
		for(int i=0; i<aFile.length; i++) {
			if (!aFile[i].isDirectory()) continue;

			BizCompInstalado oComp=new BizCompInstalado();
			oComp.pComponente.setValue(sDirectorio+"/"+aFile[i].getName());
			if (oComp.isDynamicModule()) continue;

			BizDirectorio oDirPadre=new BizDirectorio();
			oDirPadre.pDirectorio.setValue(sDirectorio+"/"+aFile[i].getName());
			this.addItem(oDirPadre);

		}
		this.setStatic(true);
		return true;

	}

}
