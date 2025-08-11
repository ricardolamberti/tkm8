package pss.tourism.interfaceGDS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

import org.codehaus.groovy.util.ManagedReference;

import com.ibm.icu.util.StringTokenizer;

import pss.JPath;
import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;
import pss.tourism.interfaceGDS.event.BizGDSEvent;
import pss.tourism.interfaceGDS.log.BizInterfaceLog;
import pss.tourism.pnr.BizPNRTicket;

public class FileProcessor {
	private String CURRENTYEAR = "CURRENTYEAR";
	private String paramDir=null;

	protected JMap<String, Object> mRecords = JCollectionFactory.createMap(10);

	protected String subdir = null;

	protected JMap<String, String> filters = JCollectionFactory.createMap();

	protected String company = null;
	protected String origen = null;
	protected long anio = 0;
	protected String pnrLocator = null;
	protected int ronda=0;
	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	protected int segments = 0;

	protected String branch = null;

	protected boolean noSubDirectory = false;
	protected String currfile = null;

	public boolean isNoSubDirectory() {
		return noSubDirectory;
	}

	public void setNoSubDirectory(boolean noSubDirectory) {
		this.noSubDirectory = noSubDirectory;
	}

	protected String IATA = null;

	public String getIATA() {
		return IATA;
	}

	public void setIATA(String iATA) {
		IATA = iATA;
	}

	protected String getGds() {
		return null;
	}

	protected int suffixlen = 3;

	protected void addFileFilter(String end) {
		this.filters.addElement(end, end);
	}

	public void setSuffixLen(int len) {
		suffixlen = len;
	}

	public String getCompany() {
		return company;
	}

	protected void setSubDirectory(String val) {
		subdir = val;
	}

	private FilenameFilter filter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			try {
			if 	(name.toUpperCase().endsWith("1S"))
				System.out.println(name);
			if (name.length() <= suffixlen)
				return false;
			int idx = name.indexOf(".");
			int lastidx = idx;
			String extension=name;
			while (idx>=0) {
				extension =  extension.substring(lastidx+1);
				idx =  extension.indexOf(".");
				if (idx>=0)
				  lastidx = idx;
			}
			String fname = extension.toLowerCase();  //.substring(0,suffixlen);
	//				name.length() - suffixlen);
			return filters.containsKey(fname);
			} catch (Exception eee) {
				return false;
			}
		}
	};

	
	
	protected File[] getFiles(boolean byname) throws Exception {
		PssLogger.logInfo("choosing files to process ...");
	
			
		
		File dir = new File(JPath.PssPathInputOriginal());
		File files[] = dir.listFiles(filter);
		if (files == null)
			return null;
		File f2[];
		if (byname)
			f2 = sortFilesByName(files);
		else 
		  f2 = sortFilesByLastModDate(files);
		return f2;
	}
	public void setInputOriginal(String dir) {
		paramDir = dir;
	}

	public String getInputOriginal() {
		// TODO Auto-generated method stub
		return paramDir;
	}

	public static void exploreDir(String dirs) throws Exception {
		File dir = new File(dirs);
		File files[] = dir.listFiles();
		if (files == null)
			return ;
		for (File f:files) {
			if (f.isDirectory()) exploreDir(f.getAbsolutePath());
			StringTokenizer s = new StringTokenizer(f.getName(),".");
			String a="";
			String c="";
			if (s.hasMoreTokens()) c=s.nextToken();
			else continue;
			if (s.hasMoreTokens()) s.nextToken();
			else continue;
			if (s.hasMoreTokens()) s.nextToken();
			else continue;
			if (s.hasMoreTokens()) a=s.nextToken();
			else continue;
			BizPNRTicket t = new BizPNRTicket();
			if (a.equals("echo")) continue;
			t.addFilter("company",c.toUpperCase());
			if (a.toUpperCase().length()<6) continue;
			t.addFilter("codigopnr", a.toUpperCase().substring(0, 6));
			t.dontThrowException(true);
			if (t.read()) 
				continue;
		JTools.MoveFile(f.getAbsolutePath(), JPath.PssPathInputOriginal()+"/"+f.getName());
			
		}
	}
	public static void main(String[] args) {
		try {
			JAplicacion.openSession();
			JAplicacion.GetApp().openApp("interfaceGDS", "process", true);
			if (BizUsuario.getUsr().GetUsuario().equals("")) {
				BizUsuario usuario = new BizUsuario();
				usuario.Read(BizUsuario.C_ADMIN_USER);
				BizUsuario.SetGlobal(usuario);
			}
			exploreDir(JPath.PssPathInputProcessed()+"/bar");
			
			
			
			JAplicacion.GetApp().closeApp();
			JAplicacion.closeSession();
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public File[] sortFilesByLastModDate(File[] fList) {
		try {
			Arrays.sort(fList, new Comparator<File>() {
				public int compare(File file1, File file2) {
					return Long.valueOf(file1.lastModified()).compareTo(
							file2.lastModified());
				}
			});
		} catch (Exception ee) {
			System.out.println(ee.getMessage());
		}
		return fList;
	}
	
	public File[] sortFilesByName(File[] fList) {
		try {
			Arrays.sort(fList, new Comparator<File>() {
				public int compare(File file1, File file2) {
					return Integer.valueOf(file1.getName().compareTo(file2.getName()));
				}
			});
		} catch (Exception ee) {
			System.out.println(ee.getMessage());
		}
		return fList;
	}
	
	private String checkDirectory(String path, String subdir) {
		if (subdir != null) {
			if (subdir.equals("") == false) {
				path += "/" + subdir;
				makeDirectory(path);
			}
		}
		return path;
	}
	
	public void moveFile(File f, String basePath, String subdirIATA,
			String subdirGDS, String subdirAnioMes) throws Exception {
		this.moveFile(f, basePath, subdirIATA, subdirGDS, subdirAnioMes, false );
	}


	// IATA , GDS , FECHA
	public void moveFile(File f, String basePath, String subdirIATA,
			String subdirGDS, String subdirAnioMes, boolean error) throws Exception {

		File oTarget = new File(f.getAbsolutePath());

		String path = basePath + "/" + company;
		makeDirectory(path);
		if (noSubDirectory == false) {
			//path = checkDirectory(path, subdirIATA);
			
			// CREO DIRECTORIO PARA EL GDS 
			path = checkDirectory(path, subdirGDS);
			
			//path = checkDirectory(path, subdirAnioMes);
			if (pnrLocator==null) pnrLocator = "";
			// CREO EL SUBDIRECTORIO CON LAS 2 PRIMERAS LETRAS DEL PNR

			if (pnrLocator.equals(""))
				path = checkDirectory(path, "" );
			else
				if (error==false)
				  path = checkDirectory(path, pnrLocator.substring(0, 2) );
		}
		
		String filenew = f.getName();
		File oDestiny = new File(path + "/" + filenew);

		PssLogger.logWait("moving file : " + f.getName());
		// Antes de renombrarlo lo borro, por si ya existe
		if (oDestiny.getName().indexOf("echo") >= 0)
			oDestiny.delete();
		else {
			while (oDestiny.exists()) {
				String number = filenew.substring(filenew.length() - 6,
						filenew.length() - 4);
				long num = 1;
				try {
				 num = Long.parseLong(number);
				} catch(Exception ee){
				}
				num++;
				filenew = filenew.substring(0, filenew.length() - 6)
						+ JTools.LPad(num + "", 2, "0")
						+ filenew.substring(filenew.length() - 4, filenew.length());
				oDestiny = new File(path + "/" + filenew);
			}	
		}
		

		// oDestiny.delete();
		if (oTarget.renameTo(oDestiny))
			PssLogger.logInfo("file :" + filenew + " moved OK");

		wholeSalerCopyToRetailer(oDestiny.getAbsolutePath(), oDestiny.getName());

	}

	private void wholeSalerCopyToRetailer(String source, String name)
			throws Exception {

		if (branch == null)
			return;

		PssLogger.logWait("looking for branch : " + branch);
		/*
		 * BizRetailWholeSaleLink rws = new BizRetailWholeSaleLink();
		 * rws.dontThrowException(true); rws.addFilter("whole_sale_id",
		 * company); rws.addFilter("retail_branch_id", branch.toUpperCase()); if
		 * (rws.read()==false) return ;
		 */
		// JTools.copyFile(source,
		// JPath.PssPathInputOriginal()+"/"+rws.getRetailCompanyId().toLowerCase()+"."+name
		// );
	}

	/**
	 * @param path
	 */
	private void makeDirectory(String path) {
		File oDir = new File(path);
		if (!oDir.isDirectory()) {
			oDir.mkdir();
		}
	}

	public String processMultiBaseRecord(BufferedReader input, String line,String rec, int count) throws Exception {
		// obtain the number of passengers in the interface
		for (int i = 0; i < count; i++) {
			line = processBaseRecord(input, line, rec, i, false);
		}
		return line;
	}

	public int processMultiBaseRecord(BufferedReader input, String rec,	String id) throws Exception {
		// obtain the number of passengers in the interface
		int i = 0;
		String line;
		while (true) {
			input.mark(1000);
			line = input.readLine();
			line = convertLine(line);;
			if (line==null)
				break;
			if (line.startsWith("%%%%"))
				continue;

			if (line.startsWith(id) == false)
				break;
			input.reset();
			line = processBaseRecord(input, line, rec, i++, false);
		}
		input.reset();
		return i;
	}

	private String convertLine(String line) {
		// TODO Auto-generated method stub
		return line==null?line: line.replace('\u00A0',' ');
	}

	public String processMultiBaseRecordEmptyLine(BufferedReader input, String line,String rec) throws Exception {
		BaseRecord m1;
		m1 = getBaseRecord(rec);

		int i = 0;
		while (true) {
			input.mark(1000);
			line = input.readLine();
			line = convertLine(line);
			if (line==null)
				break;
			if (line.startsWith("%%%%"))
				continue;

			if (line.equals(""))
				continue;
			
			if (line.startsWith(m1.getID()) == false) {
				if (line.startsWith("A")&&JTools.isNumber(line.substring(1,2))) {
					input.reset();
					break;
				} else 
					continue;
			}
			input.reset();


			line = processBaseRecord(input, line, rec, i++, false);
		}
		return line;
	}
	/**
	 * @param input
	 * @param rec
	 * @param i
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws Exception
	 */
	private String processBaseRecord(BufferedReader input, String line,String rec, int i, boolean checkline)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, IOException, Exception {
		String line2 = "";
		BaseRecord m1;
		m1 = getBaseRecord(rec);
		m1.setRonda(ronda);
		m1.setInput(input);
		if (m1.hasSpecialSubRecord()) {
			return null;
		}
		input.mark(10000);
		while (true) {
			line2 = input.readLine();
			line2 = convertLine(line2);
			if (line2==null)
				break;
			if (line2.startsWith("%%%%"))
				continue;
			if (line2.trim().equals("") == false)
				break;
		}
		if (line2 == null)
			return null;
	//	m1.addFieldValue(CURRENTYEAR, anio);
		return m1.process(mRecords, line2, i + 1);
	}

	private BaseRecord getBaseRecord(String rec) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		BaseRecord m1, maux;
		m1 = (BaseRecord) Class.forName(rec).newInstance();
		String id = m1.getID();
		maux = (BaseRecord) mRecords.getElement(id);
		if (maux != null)
			m1 = maux;
		return m1;
	}

	public String processBaseRecord(BufferedReader input, String line,
			String rec, boolean dontread) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, IOException,
			Exception {
		return processBaseRecord(input, line, rec, -1, false);

	}

	public String processBaseRecord(BufferedReader input, String line,
			String rec) throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, IOException, Exception {
		return processBaseRecord(input, line, rec, false);
	}
	
	public int processFiles() {
		// CAMBIAR CAMBIAR !!!!
		return processFiles(false);
	}

	
	public int processFiles(boolean byname) {
		int proc=0;
		try {
			File files[] = getFiles(byname);
			if (files==null) return 0;
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				PssLogger.logDebug("*******************************************************************************************");
				PssLogger.logDebug("* Processing File " + file.getName()+" Date "+new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(file.lastModified()));
				PssLogger.logDebug("*******************************************************************************************");
				mRecords = JCollectionFactory.createMap(10);
				processFile(file);
				Thread.yield();
				PssLogger.logDebug("*******************************************************************************************");
				PssLogger.logDebug("* End Processing File " + file.getName());
				PssLogger.logDebug("*******************************************************************************************");
				proc++;
			}
		} catch (Exception ee) {
			PssLogger.logError(ee, "Error reading files");
		}
		return proc;
	}

	static int filesTest = 20;
	static Date lastTest = null;
	public void processFile(File file) throws Exception {
		try {
			if (lastTest==null || JDateTools.getDaysBetween(lastTest, new Date())>0) {
				filesTest=20;
				lastTest = new Date();
			} 

			if (filesTest>0 && file.getName().indexOf("echo")==-1) {// para testing
				int pos = file.getName().indexOf(".");
				if (pos!=-1) {
					String name = file.getName().substring(0,pos)+".";
		//					String testCopy = JTools.replace(file.getName(),name,"test.");
		//					JTools.copyFile(JPath.PssPathInputOriginal()+"/"+file.getName(), JPath.PssPathInputOriginal()+"/"+testCopy);
		//					String testCopy2 = JTools.replace(file.getName(),name,"training.");
		//					JTools.copyFile(JPath.PssPathInputOriginal()+"/"+file.getName(), JPath.PssPathInputOriginal()+"/"+testCopy2);
					
							filesTest--;
					}
			} 
			if ( file.getName().toLowerCase().indexOf("tradytec")!=-1) {// robot mal nomenclado que hay que corregir
				int pos = file.getName().indexOf(".");
				if (pos!=-1) {
					String name = file.getName().substring(0,pos)+".";
					String testCopy2 = JTools.replace(file.getName(),name,"ytec.");
					boolean result = JTools.MoveFile(JPath.PssPathInputOriginal()+"/"+file.getName(), JPath.PssPathInputOriginal()+"/"+testCopy2);
				//	JTools.DeleteFile(file.getName());
			//		System.out.print(result);
					return;
				}
			}		
			if (!Manager.analyzeAndFixFile(file, null, null)) {
				throw new Exception("Se detecto doble breakline");
			}
			processFileInternal(file, false);
			moveFile(file, JPath.PssPathInputProcessed(), subdir, getGds(),
					JDateTools.CurrentDate("yyyyMM"));
			
			updateInterfaceLog(file.getName(), company, origen, false);
			JBDatos.GetBases().commit();
			JBDatos.GetBases().beginTransaction();

		} catch (Exception ee) {
			try {
				PssLogger.logDebug("FILE ERROR: "+file.getName());
				PssLogger.logError(ee);
				registerEventFile(BizGDSEvent.EVT_PROCESS_ERROR, file, ee);
				JBDatos.GetBases().rollback();
				JBDatos.GetBases().beginTransaction();
			} catch (Exception eee) {
			}
			moveFile(file, JPath.PssPathInputError(), "", "", "", true);
			PssLogger.logError(ee, "Error reading file: " + file.getName());
		
			updateInterfaceLog(file.getName(), company, origen, true);
			JBDatos.GetBases().commit();
			JBDatos.GetBases().beginTransaction();

		}
	}
	public void processFileInternal(File file, boolean reprocess) throws Exception {
		BufferedReader input = null;

			try {
				int i = 0;
				obtainCompany(file);
				addCompanies(company);
				if (company.equals("DEMO")) return;

				if (!file.exists()) 
					throw new Exception("El archivo "+file.getAbsolutePath()+ " no existe " );
				
				preProcessFile(file);
				
				//FileReader fr = new FileReader(file);
				input = new BufferedReader(new InputStreamReader(
            new FileInputStream(file), "UTF-8"));

				noSubDirectory = false;

				currfile = file.getName();

				internalProcessFile(file, input, reprocess);

				subdir = this.getIATA();
				input.close();
			} catch (Exception e) {
				input.close();
				throw e;
			}


	}
	
	private void updateInterfaceLog(String filename, String company, String origen, boolean rejected) {
		try {
			BizInterfaceLog log = new BizInterfaceLog();
			log.dontThrowException(true);
			log.addFilter("company", company);
			log.addFilter("id", origen.toLowerCase());
			if (log.read()) {
				if (rejected) {
					log.setLastRejected(new Date());
				} else {
					log.setLastTransfer(new Date());
					log.setLastFile(filename);
				}
				log.update();
			}
		} catch (Exception ee) {
			PssLogger.logError(ee);
		}
	}


	/**
	 * @throws Exception
	 */
	private void registerEventFile(long event, File file, Exception ee)
			throws Exception {
		try {
			if (company != null) {
				if (company.trim().length() == 0)
					company = null;
			}
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ee.printStackTrace(pw);
			String sStackMsg = sw.toString();
			// BizEvent.registerEvent(BizEventCode.MODULE_GDS, company, "0",
			// event, file
			// .getName(), BizEventCode.getEventModuleDescription(
			// BizEventCode.MODULE_GDS, event)
			// + ":" + file.getName() + " en " + company, sStackMsg);
		} catch (Exception eee) {
			PssLogger.logError(eee, eee.getMessage());
		}
	}

	Vector<String> companies;
	public Vector<String> getCompanies() {
		if (companies==null) companies = new Vector<String>();
		return companies;
	}

	public void addCompanies(String zcompany) {
		if (!getCompanies().contains(zcompany))
			getCompanies().add(zcompany);
	}

	private void obtainCompany(File file) throws Exception {

		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(
				file.getName(), '.');
		if (tok.countTokens() > 3) {
			company = tok.nextToken().toUpperCase();
			if (company.equals("TRAINING")||company.equals("DEFAULT")||company.equals("ARG")||company.equals("ECU")||company.equals("PANAMA")
					||company.equals("PARAGUAY")||company.equals("BOLIVIA")||company.equals("BRASIL")||company.equals("URUGUAY")
					||company.equals("CHILE")||company.equals("VENEZUELA")||company.equals("GUATEMALA")
					||company.equals("PERU")||company.equals("COLOMBIA")||company.equals("MEXICO")||company.equals("TEST")) tok.nextToken().toUpperCase();
			origen = tok.nextToken().toUpperCase();
			String year = tok.nextToken().toUpperCase();
			if (JTools.isNumber(year))
				anio = Long.parseLong(year);
			return;
		} 
		if (tok.countTokens() > 3) {
			company = tok.nextToken().toUpperCase();
			origen = tok.nextToken().toUpperCase();
			anio = JDateTools.getAnioActual();
			return;
		}
		if (tok.countTokens() > 2) {
			company = tok.nextToken().toUpperCase();
			anio = JDateTools.getAnioActual();
			return;
		}

		JExcepcion.SendError("no company received with file name: "
				+ file.getName());

	}

	protected void internalProcessFile(File file, BufferedReader input, boolean reprocess)
			throws Exception {
	}
	protected void preProcessFile(File file)
			throws Exception {
	}

	protected void processFileInformation(boolean reprocess) throws Exception {
		BaseTransaction tt;
		if (this.processVoidTicketTransaction()) {
			return;
		}
		tt = this.processTicketTransaction(reprocess); 
		tt.postProcessTransaction(tt.getCreationDateAIR());
		if (company == null)
			company = tt.getCompany();
		pnrLocator = tt.getPnrLocator();
		setIATA(tt.getIATA());
	}
	
	
	
	protected BaseTransaction processTicketTransaction(boolean reprocess) throws Exception {
		BaseTransaction tt = createTransaction();
		tt.setReprocess(reprocess);
		tt.setCompany(company);
		tt.setYear(anio);
		tt.setOrigen(origen);
		tt.setPnrFile(currfile);
		tt.setGds(currfile.toLowerCase().indexOf("copa.")!=-1?"NDC":getGds());
		tt.setSegments(segments);
		tt.save(mRecords);
		// tt.checkTaxes();
		return tt;
	}
	

	protected BaseTransaction createTransaction() throws Exception {
		JExcepcion.SendError("No GDS transaction defined");
		return null;
	}

	protected boolean processVoidTicketTransaction() throws Exception {
		return false;
	}

}
