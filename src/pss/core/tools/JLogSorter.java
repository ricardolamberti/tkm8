//Title:        I AM YOUR FATHER
//Version:      1.0
//Copyright:    Copyright (c) 1998
//Author:       James T. Kirk
//Company:      Perez with Bread
//Description:  JLogSorter: Clase para Administrar y ordenar los logs.

package pss.core.tools;

import java.io.File;

import pss.JPath;

public class JLogSorter {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	String oPathLogs; // Path del directorio de log
	String oFilesNames[]; // Nombres de los posibles archivos de logs

	public JLogSorter() throws Exception {
		// oPathLogs = JTools.PssPath() + "/Log/";
		oPathLogs=JPath.PssPathLog()+"/";
	}

	public static void ProcessLogFiles() throws Exception {
		JLogSorter oLog=new JLogSorter();
		oLog.MainLoop();
	}

	// ------------------------------------------------------------- //
	// LOOP DONDE SE PROCESAN LOS LOGS Y SE ALMACENAN EN DIRECTORIOS //
	// ------------------------------------------------------------- //
	private void MainLoop() throws Exception {
		try {
			while (!Thread.interrupted()) {
				if (IsAnyFile()) {
					ProcessLogfiles();
				}
				RunDepuradorLogs();
				Thread.sleep(5000);
			}// end while

			// --------------------------- //
			// FIN //
			// --------------------------- //

		}// end try
		catch (Exception e) {
			return;
		}
	}// end method

	private void ProcessLogfiles() throws Exception {
		try {

			String oLogUnrepeatedNames[];
			boolean Found=false;
			int j=0;
			oLogUnrepeatedNames=new String[this.oFilesNames.length];

			for(int i=0; i<this.oFilesNames.length; i++) {
				File oFile=new File(this.oPathLogs+this.oFilesNames[i]);
				if (oFile.isDirectory()) {
					oFile=null;
					continue;
				}
				Found=false;
				for(j=0; j<=oLogUnrepeatedNames.length; j++) {
					if (oLogUnrepeatedNames[j]==null) {
						break;
					}
					if (NombresSonIguales(this.oFilesNames[i], oLogUnrepeatedNames[j])) {
						Found=true;
						break;
					}
				}
				if (!Found) {
					if (GetNombre(oFilesNames[i])!=null) {
						oLogUnrepeatedNames[j]=oFilesNames[i];
					}
				}
			}// end for

			CheckDirectorios(oLogUnrepeatedNames);

			MoverArchivos();
		} catch (Exception e) {
			return;
		}

	}// end method

	private boolean NombresSonIguales(String Nombre1, String Nombre2) throws Exception {

		String Name1=GetNombre(Nombre1);
		String Name2=GetNombre(Nombre2);

		if (Name1==null||Name2==null) {
			return true;
		}
		// Fecha + Hora = 14
		return Name1.equalsIgnoreCase(Name2);
	}

	// ----------------------------------------------------------------------------------------- //
	// SUPUESTAMENTE EL NOMBRE POR PARAMETRO TIENE LA FECHA Y LA HORA FRONTWED20010828152557.LOG //
	// LO QUE HACE ES REMOVERLE LA EXTENCION, LA FECHA Y LA HORA, DEVUELVE "FRONTWED" //
	// ----------------------------------------------------------------------------------------- //
	private String GetNombre(String Name) throws Exception {

		if (Name==null) {
			return null;
		}

		int Punto1=Name.indexOf('.');
		if (Punto1==-1) {
			Punto1=Name.length();
		}

		if (Punto1<14) {
			return null;
		}

		return Name.substring(0, Punto1-14);
	}// end method

	// ---------------------------------------------------------------------------------- //
	// ---------------------------------------------------------------------------------- //
	private void CheckDirectorios(String UnrepeatedNames[]) throws Exception {
		String Directory;

		for(int i=0; UnrepeatedNames[i]!=null; i++) {
			if (GetNombre(UnrepeatedNames[i])==null) {
				continue;
			}
			Directory=this.oPathLogs+GetNombre(UnrepeatedNames[i]);

			File oFile=new File(Directory);
			if (!oFile.isDirectory()) {
				oFile.mkdir();
				CrearDirAnoMes(Directory);
			} else {
				if (!ExsiteDirAnoMes(Directory)) {
					CrearDirAnoMes(Directory);
				}
			}
			oFile=null;
		}
	}// end for

	private boolean ExsiteDirAnoMes(String Dir) throws Exception {
		String DirAnoMes=Dir+"/"+JDateTools.CurrentDate("yyyyMM");
		File oFile=new File(DirAnoMes);
		return oFile.isDirectory();
	}

	private void CrearDirAnoMes(String Dir) throws Exception {
		String DirAnoMes=Dir+"/"+JDateTools.CurrentDate("yyyyMM");
		File oFile=new File(DirAnoMes);
		oFile.mkdir();
	}

	private void RunDepuradorLogs() {

	}// end method

	private void MoverArchivos() throws Exception {
		String PathSource;
		String PathDestiny;

		for(int i=0; i<this.oFilesNames.length; i++) {
			if (GetNombre(this.oFilesNames[i])==null) {
				continue;
			}
			PathSource=this.oPathLogs+oFilesNames[i];
			PathDestiny=this.oPathLogs+GetNombre(oFilesNames[i])+"/"+JDateTools.CurrentDate("yyyyMM")+"/"+oFilesNames[i];
			JTools.MoveFile(PathSource, PathDestiny);
		}// end for
	}// end method

	private boolean IsAnyFile() {
		if (oPathLogs.startsWith("/")) {
			this.oPathLogs=this.oPathLogs.substring(1, this.oPathLogs.length());
		}

		File oFile=new File(this.oPathLogs);
		oFilesNames=oFile.list();

		return oFilesNames.length!=0;

	}// end method

}// end class
