/**
 * 
 */
package pss.tourism.interfaceGDS;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import pss.JPath;
import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.tools.PssLogger;
import pss.core.tools.WinServiceTools;
import pss.tourism.interfaceGDS.amadeus.AmadeusFileProcessor;
import pss.tourism.interfaceGDS.galileo.GalileoFileProcessor;
import pss.tourism.interfaceGDS.log.EchoFileProcessor;
import pss.tourism.interfaceGDS.log.ZipFileProcessor;
import pss.tourism.interfaceGDS.sabre.SabreFileProcessor;
import pss.tourism.interfaceGDS.travelport.TravelPortFileProcessor;

/**
 * @author sgalli
 * 
 */
public class Manager {
	private static int normalFileCounter = 0;
	private static final int BATCH_THRESHOLD = 500; 
	private static final int PENDING_BATCH_SIZE = 100; 
	static boolean first = true;

	Vector<String> companies;
	int filesProc;
	

	public Vector<String> getCompanies() {
		return companies;
	}

	public void setCompanies(Vector<String> companies) {
		this.companies = companies;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			if (args.length >= 1) {
				WinServiceTools w = new WinServiceTools();
				w.setClassName("pss.tourism.interfaceGDS.Manager");
				w.setServiceName("TKM Ticket Manager");
				w.install();
				System.exit(0);
			}
		} catch (Exception ee) {

		}
//		cleanDoubleLines();
		JPath.setPssPathInput("C:\\production\\tkm7\\pssData\\input");
		Manager.process();
	}
	
	public static void process() {
		
		Manager.process(null);
	}
		


	public static void process(String dir) {
		while (Thread.currentThread().isInterrupted() == false) {
			try {

				JAplicacion.openSession();
				JAplicacion.GetApp().openApp("interfaceGDS", "process", true);
				JBDatos.GetBases().beginTransaction();
				if (BizUsuario.getUsr().GetUsuario().equals("")) {
					BizUsuario usuario = new BizUsuario();
					usuario.Read(BizUsuario.C_ADMIN_USER);
					BizUsuario.SetGlobal(usuario);
				}

				Manager mgr = new Manager();
				Vector<String> companies = new Vector<String>();
				mgr.processFiles(companies);

				JBDatos.GetBases().commit();
				JAplicacion.GetApp().closeApp();
				JAplicacion.closeSession();
				PssLogger.logInfo("Durmiendo 1 minuto");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// falta rollback?
				PssLogger.logDebug("Interruption received");
				break;
			} catch (Exception e) {
				// falta rollback?
				PssLogger.logError(e);
			}
		}
	}

	public int processFiles( Vector<String> companies) throws Exception {
		filesProc = 0;
		setCompanies(companies);
		// mgr.depurePNR();
		processZipFiles();
	
		startEchoCheck();
	
	
		processSabreFiles();
		processAmadeusFiles();
		processTravelPortFiles();
		processGalileoFiles();
		normalFileCounter += filesProc;
		if (filesProc==0 || normalFileCounter >= BATCH_THRESHOLD) {
		    processPendingBatch(PENDING_BATCH_SIZE);
		    normalFileCounter = 0; 
		}
		return filesProc;
	}
	private void processPendingBatch(int maxFiles) throws Exception {
    File pendingDir = new File(JPath.PssPathInputPending());
    File[] files = pendingDir.listFiles();

    if (files == null || files.length == 0) return;

    int moved = 0;
    for (File file : files) {
        if (moved >= maxFiles) break;
        Path target = Paths.get(JPath.PssPathInputOriginal(), file.getName());
        Files.move(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
        moved++;
        PssLogger.logInfo("Archivo movido desde pendientes: " + file.getName());
    }
}

	public synchronized Thread startEchoCheck() {
		if (!first) return null;
		if (first) first = false;
			
		// Transit SSL Transaction Server
		Thread oth1 = new Thread() {
			public void run() {
				Manager mgr = new Manager();
				mgr.processEchoFiles();

			}
		};
		oth1.start();
		return oth1;
	}

	private void deleteRecords(String table, String id, String cpy) throws Exception {
		JBaseRegistro reg = JBaseRegistro.recordsetFactory();
		reg.Execute("delete " + table + " where codigopnr='" + id + "' and company='" + cpy + "'");
		reg.close();
	}

	private void depurePNR() throws Exception {

		// JRecords<BizTxAirTicket> rec1 = new
		// JRecords<BizTxAirTicket>(BizTxAirTicket.class);
		// rec1.addFilter("COMPANY", "PAT" );
		// rec1.toStatic();
		// int iii = 0;
		// while (rec1.nextRecord()) {
		// BizTxAirTicket tk = rec1.getRecord();
		// BizTxAirTax tax = new BizTxAirTax();
		// tax.dontThrowException(true);
		// if ( tax.Read(tk.getTx(), "DL") ) {
		// if ( tk.isInternacional() ) {
		// tk.setInternational(false);
		// tk.update();
		// }
		// } else {
		// if ( tk.isInternacional()==false ) {
		// tk.setInternational(true);
		// tk.update();
		// }
		// }
		// iii++;
		// if (iii >= 100) {
		// JBDatos.GetBases().commit();
		// JBDatos.GetBases().beginTransaction();
		// iii = 0;
		// }
		// }
		// JBDatos.GetBases().commit();
		// JBDatos.GetBases().beginTransaction();

		// JRecords<BizPNR> rec = new JRecords<BizPNR>(BizPNR.class);
		// rec.addFilter("FECHACREACION", JDateTools.SubDays(new Date(), 150),
		// "<=");
		// rec.addFilter("consumed", "S");
		// rec.setTop(500);
		// rec.toStatic();
		// int i = 0;
		// while (rec.nextRecord()) {
		// BizPNR pnr = rec.getRecord();
		// String id = pnr.getCodigoPNR();
		// String cpy = pnr.getCompany();
		// deleteRecords("tur_pnr", id, cpy);
		// deleteRecords("tur_pnr_pagos", id, cpy);
		// deleteRecords("tur_pnr_remarks", id, cpy);
		// deleteRecords("tur_pnr_boleto", id, cpy);
		// deleteRecords("tur_pnr_impuestos", id, cpy);
		// deleteRecords("tur_pnr_comision", id, cpy);
		// deleteRecords("tur_pnr_tarifas", id, cpy);
		// deleteRecords("tur_pnr_pasajeros", id, cpy);
		// deleteRecords("tur_pnr_segmentoaereo", id, cpy);
		// i++;
		// if (i >= 50) {
		// JBDatos.GetBases().commit();
		// JBDatos.GetBases().beginTransaction();
		// i = 0;
		// }
		// }
		// JBDatos.GetBases().commit();
		// JBDatos.GetBases().beginTransaction();

	}

	private void processEchoFiles() {

		while (Thread.currentThread().isInterrupted() == false) {
			try {
				JAplicacion.openSession();
				JAplicacion.GetApp().openApp("interface.echo", "process", true);

				JBDatos.GetBases().beginTransaction();

				EchoFileProcessor sfp = new EchoFileProcessor();
				sfp.addFileFilter("echo");
				sfp.setSuffixLen(4);
				sfp.processFiles();

				JBDatos.GetBases().commit();

			} catch (Exception ee) {
				try {
					PssLogger.logError(ee);
					JBDatos.GetBases().rollback();
				} catch (Exception eee) {
				}

			} finally {
				try {
					JAplicacion.GetApp().closeApp();
					JAplicacion.closeSession();
					Thread.sleep(60000);
				} catch (Exception eee) {
				}
				
			}
		}
	}

	private void processZipFiles() {
		ZipFileProcessor sfp = new ZipFileProcessor();
		sfp.addFileFilter("zip");
		filesProc += sfp.processFiles();
	}

	private void processSabreFiles() {
		SabreFileProcessor sfp = new SabreFileProcessor();
		sfp.addFileFilter("pnr");
		sfp.addFileFilter("fil");
		sfp.addFileFilter("iur");
		sfp.addFileFilter("1s");
		filesProc += sfp.processFiles(true);
		getCompanies().addAll(sfp.getCompanies());
	}

	private void processTravelPortFiles() {
		TravelPortFileProcessor sfp = new TravelPortFileProcessor();
		sfp.addFileFilter("prt");
		sfp.addFileFilter("dat");
		filesProc += sfp.processFiles();
		getCompanies().addAll(sfp.getCompanies());
	}

	private void processGalileoFiles() {
		GalileoFileProcessor sfp = new GalileoFileProcessor();
		sfp.addFileFilter("mir");
		filesProc += sfp.processFiles();
		getCompanies().addAll(sfp.getCompanies());
	}

	private void processAmadeusFiles() {
		AmadeusFileProcessor sfp = new AmadeusFileProcessor();
		sfp.addFileFilter("txt");
		sfp.addFileFilter("pro");
		sfp.addFileFilter("jan");
		sfp.addFileFilter("feb");
		sfp.addFileFilter("mar");
		sfp.addFileFilter("apr");
		sfp.addFileFilter("may");
		sfp.addFileFilter("jun");
		sfp.addFileFilter("jul");
		sfp.addFileFilter("aug");
		sfp.addFileFilter("sep");
		sfp.addFileFilter("oct");
		sfp.addFileFilter("nov");
		sfp.addFileFilter("dec");
		sfp.addFileFilter("000");
		sfp.addFileFilter("001");
		sfp.addFileFilter("002");
		sfp.addFileFilter("003");
		sfp.addFileFilter("004");
		sfp.addFileFilter("000000");
		sfp.addFileFilter("000001");
		sfp.addFileFilter("000002");
		sfp.addFileFilter("000003");
		sfp.addFileFilter("000005");
		sfp.addFileFilter("000006");
		sfp.addFileFilter("000007");
		sfp.addFileFilter("000008");
		sfp.addFileFilter("000009");
		sfp.addFileFilter("000010");
		sfp.addFileFilter("000011");
		sfp.addFileFilter("000012");
		sfp.addFileFilter("000013");
		sfp.addFileFilter("000014");
		sfp.addFileFilter("000015");
		sfp.addFileFilter("000016");
		sfp.addFileFilter("000017");
		sfp.addFileFilter("000018");
		sfp.addFileFilter("000019");
		sfp.addFileFilter("000020");
		sfp.addFileFilter("000021");
		sfp.addFileFilter("000022");
		sfp.addFileFilter("000023");
		sfp.addFileFilter("000024");
		sfp.addFileFilter("000025");
		sfp.addFileFilter("air");

		filesProc += sfp.processFiles();
		getCompanies().addAll(sfp.getCompanies());
	}


  public static void cleanDoubleLines() throws Exception {
    String inputDirPath = JPath.PssPathInput()+"\\processed\\CNS\\SABRE"; // Cambiar por la ruta del directorio de entrada
  //  String inputDirPath = "C:\\production\\tkm7\\test"; // Cambiar por la ruta del directorio de entrada
      String outputDirPath = JPath.PssPathInputOriginal();   // Cambiar por la ruta del directorio de salida

      try {
          // Crear el directorio de salida si no existe
          Files.createDirectories(Paths.get(outputDirPath));

          // Procesar todos los archivos en el directorio de entrada
          Files.walk(Paths.get(inputDirPath))
                  .filter(Files::isRegularFile)
                  .forEach(file -> {
                      try {
                          processFile(file, outputDirPath);
                      } catch (IOException e) {
                          System.err.println("Error procesando el archivo: " + file + ", " + e.getMessage());
                      }
                  });
      } catch (IOException e) {
          System.err.println("Error al procesar los archivos: " + e.getMessage());
      }
  }

  private static void processFile(Path file, String outputDirPath) throws IOException {
    Path outputFile = Paths.get(outputDirPath, file.getFileName().toString());

    // Verificar si el archivo ya existe
    if (Files.exists(outputFile)) {
        System.out.println("El archivo ya existe en el directorio de salida, no se sobrescribirá: " + outputFile.getFileName());
        return;
    }
    
    List<String> lines = Files.readAllLines(file);
    List<String> fixedLines = fixDoubleNewlines(lines);

    // Si no se detectaron dobles saltos de línea, se salta el archivo
    if (fixedLines == null) {
//        System.out.println("No se detectaron problemas en el archivo: " + file.getFileName());
        return;
    }

    // Crear el archivo de salida


    // Escribir el archivo corregido en el directorio de salida
    Files.write(outputFile, fixedLines);
    System.out.println("Archivo corregido y guardado: " + outputFile.getFileName());
}


  private static List<String> fixDoubleNewlines(List<String> lines) {
      List<String> fixedLines = new ArrayList<>();
      boolean foundDoubleNewline = false;
      if (lines.size()<10) return null;
      for (int i = 0; i < lines.size(); i++) {
          String currentLine = lines.get(i);

          // Si la línea actual y la siguiente son vacías, salta una
          if (i + 1 < lines.size() && 
          		lines.get(i + 1).trim().isEmpty()) {
          	foundDoubleNewline=true;
          	          	i++;
          } else {
          	if (i + 1 < lines.size())
          		foundDoubleNewline=false;
          	break;
          }

          fixedLines.add(currentLine);
      }

      // Retorna null si no se detectaron dobles saltos de línea
      return foundDoubleNewline ? fixedLines : null;
  }
  
  public static boolean analyzeAndFixFile(File inputFile, String zoriginalBackupDir, String zfixedOutputDir) throws Exception {
    // Configurar rutas de directorios
    try {
        Path originalBackupDir;
        Path fixedOutputDir;

        if (zoriginalBackupDir == null) {
            originalBackupDir = Paths.get(JPath.PssPathInput()+"\\originalErrors");
        } else {
            originalBackupDir = Paths.get(zoriginalBackupDir);
        }

        if (zfixedOutputDir == null) {
            fixedOutputDir = Paths.get(JPath.PssPathInput()+"\\fixed");
        } else {
            fixedOutputDir = Paths.get(zfixedOutputDir);
        }

        // Leer todas las líneas del archivo
        List<String> lines = Files.readAllLines(inputFile.toPath());

        // Intentar corregir las líneas
        List<String> fixedLines = fixDoubleNewlines(lines);

        if (fixedLines == null) {
            return true;
        }

        // Crear directorios si no existen
        if (!Files.exists(originalBackupDir)) {
            Files.createDirectories(originalBackupDir);
        }
        if (!Files.exists(fixedOutputDir)) {
            Files.createDirectories(fixedOutputDir);
        }

        // Copiar el archivo original al directorio de respaldo
        File backupFile = new File(originalBackupDir.toFile(), inputFile.getName());
        Files.copy(inputFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        // Generar el nuevo nombre para el archivo corregido
        String inputFileName = inputFile.getName();
        int dotIndex = inputFileName.lastIndexOf(".");
        String newFileName = (dotIndex == -1) ? inputFileName + "_changed" : inputFileName.substring(0, dotIndex) + "_changed" + inputFileName.substring(dotIndex);

        // Escribir el archivo corregido en el directorio de salida
        File fixedFile = new File(fixedOutputDir.toFile(), newFileName);
        Files.write(fixedFile.toPath(), fixedLines);

        // Mensajes informativos
        System.out.println("Archivo corregido: " + inputFile.getName());
        System.out.println("Copia original guardada en: " + backupFile.getAbsolutePath());
        System.out.println("Archivo corregido guardado en: " + fixedFile.getAbsolutePath());
        inputFile.delete();
        // Copiar el archivo corregido al directorio del archivo original con el nuevo nombre
        File finalFile = new File(inputFile.getParent(), newFileName);
        Files.copy(fixedFile.toPath(), finalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return false;
    } catch (IOException e) {
        PssLogger.logError(e);
        return true;
    }
}
}

