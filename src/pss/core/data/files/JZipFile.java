package pss.core.data.files;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class JZipFile {
	
	private JMap<String, String> map = null;
	private String zipName = null;
	
	public JZipFile() {
	}
	
	public void setZipName(String value) throws Exception {
		this.zipName=value;
	}
	
	public void addEntry(String entryName, String absolutePath) throws Exception {
		if (map==null) map = JCollectionFactory.createMap();
		map.addElement(entryName, absolutePath);
	}
	
	public void compress() throws Exception {
	
		if (this.zipName==null) 
			JExcepcion.SendError("Debe asignar un file destino");
		
		ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(this.zipName));
	
		byte[] buffer = new byte[1024];
		
		JIterator<String> iter = map.getKeyIterator();
		while (iter.hasMoreElements()) {
			String entry = iter.nextElement();
			String path = map.getElement(entry);
			FileInputStream in = new FileInputStream(path);
			zip.putNextEntry(new ZipEntry(entry));
			int len;
			while ((len = in.read(buffer)) > 0) {
				zip.write(buffer, 0, len);
			}
			in.close();
		}
	
		zip.closeEntry();
		zip.close();
	}


}
