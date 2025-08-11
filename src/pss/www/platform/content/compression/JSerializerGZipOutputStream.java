/*
 * Created on 27-ago-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.compression;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;

import org.apache.coyote.http11.filters.FlushableGZIPOutputStream;

public class JSerializerGZipOutputStream extends FlushableGZIPOutputStream {

  public JSerializerGZipOutputStream(OutputStream out) throws IOException {
    super(out);
  }

//  public JSerializerGZipOutputStream(OutputStream out, int size) throws IOException {
//    super(out, size);
//  }

  @Override
	public void flush() throws IOException {
    super.flush();
 //   super.finish();
  }

  @Override
  protected void deflate() throws IOException {
      // SYNC_FLUSH is the key here, because it causes writing to the output
      // stream in a streaming manner instead of waiting until the entire
      // contents of the response are known.  for a large 1 MB json example
      // this took the size from around 48k to around 50k, so the benefits
      // of sending data to the client sooner seem to far outweigh the
      // added data sent due to less efficient compression
      int len = def.deflate(buf, 0, buf.length, Deflater.SYNC_FLUSH);
      if (len > 0) {
          out.write(buf, 0, len);
      }
  }
}
