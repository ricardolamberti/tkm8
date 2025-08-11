/*
 * Created on 07/11/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pss.core.tools.depureFiles;

import java.io.File;
import java.util.Calendar;
import java.util.StringTokenizer;

import pss.JPath;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;

/**
 * Clase para depurar archivos
 * @author agimenez
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class JDepureFiles {
	private static final long MILLISECONDS_IN_A_HOUR = 3600000;
	private static JDepureFiles oDepureFiles;
	private int iQtyDepureDays=10;
	private long lPeriodicity;
	private String sPath;
	private boolean Active = false;
	
	/**
	 * Devuelve JDepureFiles
	 * @return
	 */
	public static JDepureFiles getJDepureFiles()
	{
		if (oDepureFiles == null)
			oDepureFiles = new JDepureFiles();
		return oDepureFiles;
	}
	
	/**
	 * Comienza la depuración
	 *
	 */
	public void depure() throws Exception {
		if (sPath==null) {
			PssLogger.logError("NO Depurate directory undefined");
			return;

		}
		StringTokenizer toks = new StringTokenizer(sPath,",;");
		while (toks.hasMoreTokens()) {
			String path = toks.nextToken();
			if (path.equalsIgnoreCase("tempfiles"))
				path=JPath.PssPathTempFiles();
			depure(path);
		}
			
	}
	public void depure(String path) throws Exception
	{
			File oDirectory = new File(path);
			if (oDirectory==null) return;
			String[] sFiles = oDirectory.list(); 
			if (sFiles==null) return;
			PssLogger.logInfo("Depurate directory: ["+path+"] days ["+iQtyDepureDays+"]");
			for (int i=0; i < sFiles.length; i++)
			{
				File oFile = new File(path + "//" + sFiles[i]);				
				if (oFile.isDirectory()) {
					depure(path+"/"+sFiles[i]);
					continue;
				}

				Calendar oCalendar = Calendar.getInstance();
				oCalendar.add(Calendar.DATE, -1 * iQtyDepureDays);
								
				if ( oFile.lastModified() < oCalendar.getTimeInMillis() )
				{
					oFile.delete();
				}					
			}
	}

	public void startToDepure() throws Exception {
		do {
			depure();
			Thread.sleep(getWaitTime());
		} while (Active);
	}
			
	/**
	 * Obtiene en milisegundos.
	 * @return
	 */
	private long getWaitTime()throws Exception
	{
		return this.lPeriodicity * MILLISECONDS_IN_A_HOUR; 
	}
	
	/**
	 * main para el PssDispatcher
	 * @param args
	 * 0 - Cantidad de días a partir de la fecha de donde se borraran los archivos
	 * anteriores.
	 * 1 - path a partir de JPss de donde se borraran los archivos.
	 * 2 - perodicidad
	 */
	public static void main(String[] args) throws Exception
	{
		int integer = 0;
		long lperiodicity =0;

		@SuppressWarnings("unused")
		JDepureFiles oDepure = getJDepureFiles();			
		integer=(int)JTools.getLongNumberEmbedded(args[0]);
		oDepureFiles.setIQtyDepureDays(integer);
		oDepureFiles.setPath(args[1]);
		lperiodicity=JTools.getLongNumberEmbedded(args[2]);
		oDepureFiles.setIPeriodicity(lperiodicity);
		oDepureFiles.setActive(true);
		oDepureFiles.startToDepure();
	}
	
	/**
	 * @param b
	 */
	public void setActive(boolean b) {
		Active = b;
	}

	/**
	 * @param integer
	 */
	public void setIQtyDepureDays(int integer) {
		iQtyDepureDays = integer;
	}

	/**
	 * @param url
	 */
	public void setPath(String path) {
		sPath = path;
	}
	/**
	 * @param integer
	 */
	public void setIPeriodicity(long integer) {
		lPeriodicity = integer;
	}

}
