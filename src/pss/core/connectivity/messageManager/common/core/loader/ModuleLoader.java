package pss.core.connectivity.messageManager.common.core.loader;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Vector;

import pss.JPath;
import pss.common.regions.multilanguage.JLanguage;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;

public class ModuleLoader {
	private boolean pLoadModuleDinamically = true;
	private Vector<String> pModuleClassFileName = null;
	private Vector<String> pModuleDirectories = null;
	IModuleLoader loader; // que hacer
	IConfigurationLoader configuration; // donde

	public ModuleLoader(IModuleLoader zLoader, IConfigurationLoader zConf) throws Exception {
		loader = zLoader;
		configuration = zConf;
	}

	private Vector<String> getModuleClassFileName() {
		if (this.pModuleClassFileName == null) {
			this.pModuleClassFileName = new Vector<String>();
		}
		return this.pModuleClassFileName;
	}

	private Vector<String> getModuleDirectories() {
		if (this.pModuleDirectories == null) {
			this.pModuleDirectories = new Vector<String>();
		}
		return this.pModuleDirectories;
	}

	private void loadDirectoriesPath() throws Exception {
		configuration.loadDirectoriesPath(this);
	}

	public void addDirectory(String directory) {
		getModuleDirectories().add(JPath.PssPath() + directory);
	}

	public void addClass(String classname) {
		getModuleClassFileName().add(classname);
	}

	public boolean isLoadModuleDinamically() {
		return loader.isLoadModuleDinamically();
	}

	public void setLoadModuleDinamically(boolean zLoadModuleDinamically) {
		this.pLoadModuleDinamically = zLoadModuleDinamically;
	}

	public void loadModules() throws Exception {
		if (this.pLoadModuleDinamically) {
			loader.loadClass(this);
			loadDirectoriesPath();
			Iterator<String> dirIterator = this.getModuleDirectories().iterator();
			while (dirIterator.hasNext()) {
				loadModuleModulesFromDirectory(dirIterator.next());
			}
		} else {
			loadModuleModulesStatically();
		}

		// Iterator<String> addinIterator = this.getModuleClassFileName().iterator();
		// while (addinIterator.hasNext()) {
		// loader.launchModule(addinIterator.next(),configuration);
		// } // end while

		for (String addinClassName : this.getModuleClassFileName()) {
			loader.launchModule(addinClassName, configuration);
		}

	}

	/**
	 * <p>
	 * Lee todas la clases que representan "Module Modules" buscando desde el directorio que se pasa como parametro y
	 * todos los subdirectorios.
	 * </p>
	 * 
	 * @param dirName
	 *          Especifica el directorio raiz desde donde se empieza a buscar los Module
	 * @throws Exception
	 *           Si el directorio pasado por parametro no existe
	 */
	private void loadModuleModulesFromDirectory(String dirName) throws Exception {
		if (dirName == null || dirName.isEmpty() == true) {
			return;
		}

		File javaDirObj = new File(dirName);

		if (javaDirObj.exists() == false) {
			PssLogger.logError("El directorio " + dirName + " no existe");
			return;
		}

		if (javaDirObj.isDirectory() == false) {
			PssLogger.logError(dirName + " no es un directorio valido");
			return;
		}

		File oFile = new File(dirName);
		File aFile[] = oFile.listFiles();

		if (aFile == null)
			JExcepcion.SendError("El directorio^ '" + dirName + JLanguage.translate("' no existe, pero esta configurado en el producto. Se deberá eliminarlo de la confiuración del producto."));

		String fileName;
		String packageName;
		String className;
		String classFullName;

		// Por cada archivo encontrado
		for (int i = 0; i < aFile.length; i++) {
			if (aFile[i].isDirectory()) { // Si es un directorio
				// Si la el archivo leido es un directorio lo proceso
				this.loadModuleModulesFromDirectory(oFile.getPath() + File.separatorChar + aFile[i].getName());
				continue;
			} // end if

			fileName = aFile[i].getName();

			// Proceso cada archivo (no directorio) encontrado.
			// Solamente proceso los .class
			if (fileName.lastIndexOf(".class") == -1)
				continue;

			// Descarto las inner classes
			if (fileName.indexOf('$') != -1)
				continue;

			// Formateo y guardo el nombre del package y de la clase.
			packageName = oFile.getPath().substring(JPath.PssPath().length() + 1).replace(File.separatorChar, '.');
			// packageName = dirName.substring(JPath.PssPath().length() + 1).replace(
			// File.separatorChar, '.');
			className = fileName.substring(0, fileName.length() - 6);
			classFullName = packageName + '.' + className;
			try {
				// x Ahora no hace falta
				// // Filtro las clases base
				// if (Class.forName(packageName + '.' + className) == JRecord.class ||
				// Class.forName(getClassFullName()) == JBaseRecord.class) {
				// return false;
				// }
				// Descarto las interfaces
				if (Class.forName(classFullName).isInterface()) {
					PssLogger.logDebug("Class " + classFullName + " is an inteface and will not be processed");
					continue;
				}

				if (Modifier.isAbstract(Class.forName(classFullName).getModifiers())) {
					PssLogger.logDebug("Class " + classFullName + " is abstract and will not be processed");
					continue;
				}

				if (!loader.isLoaderClass(classFullName))
					continue;

			} catch (Throwable t) {
				PssLogger.logDebug("Se detecto la siguiente error CRITICO chequeando la clase " + classFullName);
				t.printStackTrace();
				continue;
			}
			PssLogger.logDebug("Loading [" + classFullName + "] into ModuleLoader");
			getModuleClassFileName().add(classFullName);
		} // end for
	}

	private void loadModuleModulesStatically() throws Exception {
		this.getModuleClassFileName().add("pss");
	}

}
