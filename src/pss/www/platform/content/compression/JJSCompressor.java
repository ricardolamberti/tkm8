/*
 * Created on 21-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.compression;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.Result;
import com.google.javascript.jscomp.SourceFile;

import pss.core.tools.JTools;

public class JJSCompressor extends JClientContentCompressor {


  //
  // compressor implementation
  //
  @Override
  protected int compress(char[] code, char[] t_buffer) throws Exception {
 
      ByteArrayOutputStream err = new ByteArrayOutputStream();

      CompilerOptions opt = new CompilerOptions();

      CompilationLevel.WHITESPACE_ONLY.setOptionsForCompilationLevel( opt );

      Compiler.setLoggingLevel( Level.OFF );
      Compiler compiler = new Compiler( new PrintStream( err ) );
      compiler.disableThreads();

      List<SourceFile> externs = Collections.emptyList();
      List<SourceFile> inputs = Arrays.asList( SourceFile.fromCode( "javascript-code.js",  JTools.byteVectorToString(JTools.charArrayToByteArray(code)) ));

      Result result = compiler.compile( externs, inputs, opt );

      compiler.toSource().getChars( 0,  compiler.toSource().length(), t_buffer, 0 );
      return compiler.toSource().length();
  }
}
