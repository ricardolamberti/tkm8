/*
 * Created on 21-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.compression;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public abstract class JClientContentCompressor {

  public void compress(String zInputFilePath, String zOutputFilePath) throws Exception {
    FileInputStream infile = null;
    FileOutputStream outfile = null;
    try {
      infile = new FileInputStream(zInputFilePath);
      outfile = new FileOutputStream(zOutputFilePath);
      int iInSize = infile.available();
      int iOutSize = 0;
      char[] inBuffer = new char[iInSize];
      char c;
      for (int i = 0; i < iInSize; i++) {
        c = (char)infile.read();
        inBuffer[iOutSize++] = c;
      }
      char[] outBuffer = new char[iOutSize*2];
      int iWrittenChars = this.compress(inBuffer, outBuffer);
      for (int i = 0; i < iWrittenChars; i++) {
        outfile.write(outBuffer[i]);
      }
      outfile.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      if (infile != null) {
        try {infile.close();} catch(Exception e) {}
      }
      if (outfile != null) {
        try {outfile.close();} catch(Exception e) {}
      }
    }
  }


  //
  //  METHODS TO OVERRIDE IN SUBCLASSES
  //

  protected int compress(char[] inBuffer, char[] outBuffer) throws Exception {
    return 0;
  }

 
}
