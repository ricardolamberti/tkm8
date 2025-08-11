package pss.core.data.files;

import pss.core.tools.JTools;

public class JStreamCrypt extends JStreamGZip {

	//-------------------------------------------------------------------------- //
	// Propiedades publicas y privadas de la clase
	//-------------------------------------------------------------------------- //

	//-------------------------------------------------------------------------- //
	// Constructor
	//-------------------------------------------------------------------------- //
	public JStreamCrypt() {
		super();
		bLineMode = false;
	}

	//-------------------------------------------------------------------------- //
	// Read de un archivo
	// Recibo como parametros la cantidad de bytes a leer
	//-------------------------------------------------------------------------- //
	@Override
	public String Read(int zBytes) throws Exception {
		String sData = null;
		byte cBuf[];
		int iIdx;
		int iLen;

		cBuf = new byte[zBytes];
		if ((iLen = pInput.read(cBuf, 0, zBytes)) != -1) {
			for (iIdx = 0; iIdx < iLen; iIdx++) {
				cBuf[iIdx] = (byte)JTools.vs13((char)cBuf[iIdx]);
			}
			sData = new String(cBuf);
		}

		return sData;
	} // Read

	//-------------------------------------------------------------------------- //
	// Write de un archivo
	// Escribo un string en un archivo
	//-------------------------------------------------------------------------- //
	@Override
	public void Write(String zData) throws Exception {
		int iIdx = 0;
		int iLen = zData.length();
		byte[] aBytes = new byte[iLen];

		aBytes = zData.getBytes();
		for (iIdx = 0; iIdx < iLen; iIdx++) {
			aBytes[iIdx] = (byte)JTools.vs13((char)aBytes[iIdx]);
		}

		pOutput.write(aBytes);

		if (bAutoFlush)
			Flush();
	} // Write

}

/*class vs13InputStream extends FilterInputStream {

  public vs13InputStream( InputStream i ) {
    super( i );
  }

  public int read() throws IOException {
    return vs13( in.read() );

  }


}

abstract class CryptInputStream extends InputStream {
  InputStream  oIn;
  OutputStream oOut;
  abstract public void set( InputStream zIn, OutputStream zOut );
}

class vs13CryptInputStream extends CryptInputStream {
  public void set( InputStream zIn, OutputStream zOut ) {
    oIn = new vs13InputStream( zIn );
  }
  public int read() throws IOException {
    return oIn.read();
  }
}*/
