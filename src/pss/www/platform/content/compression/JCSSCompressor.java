/*
 * Created on 21-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.compression;

public class JCSSCompressor extends JClientContentCompressor {

  //
  // internally used flags
  //
  private boolean css_relevant = true;
  private boolean css_comment = false;
  private boolean css_block_comment = false;

  private boolean css_class_decl = false;
  private boolean css_body = false;
  private boolean css_double = false;
  private boolean css_single = false;
  
  private boolean css_started_attr_def = false;
  private boolean css_attr_def = false;


  //
  //  compressor implementation
  //
  
  @Override
	protected int compress(char[] buffer, char[] t_buffer) throws Exception {
    int iOutSize = buffer.length;
    char c;
    int j = 0; // written chars count
    for (int i = 0; i < iOutSize; i++) {
      css_relevant = !css_comment && !css_block_comment;
      c = buffer[i];
      if (css_relevant) {
        // if it is starting comment
        if (c == '/' && buffer[i + 1] == '/') {
          css_comment = true;
        } else if (c == '/' && buffer[i + 1] == '*') {
          css_block_comment = true;
        }
        // if it is bewtween quotes
        else if (css_double) {
          t_buffer[j++] = c;
          if (c == '"' && buffer[i - 1] != '\\') {
            css_double = false;
          }
        } else if (css_single) {
          t_buffer[j++] = c;
          if (c == '\'' && buffer[i - 1] != '\\') {
            css_single = false;
          }
        }
        // if starts quotes
        else if (css_body && c == '"') {
          t_buffer[j++] = c;
          css_double = true;
        } else if (css_body && c == '\'') {
          t_buffer[j++] = c;
          css_single = true;
        }
        // if starts an attribute def
        else if (css_body && c==':') {
          t_buffer[j++] = c;
          css_attr_def = true;
        }
        // if it is a class decl start
        else if (!css_body && !css_class_decl && !this.isFillChar(c)) {
          css_class_decl = true;
          t_buffer[j++] = c;
        }
        // if it is writing an attribute def
        else if (css_attr_def) {
          if (!css_started_attr_def && !this.isFillChar(c)) {
            css_started_attr_def = true;
          }
          boolean bWrt = css_started_attr_def || c==';';
          if (bWrt) {
            t_buffer[j++] = c;
            if (c==';') {
              css_attr_def = false;
              css_started_attr_def = false;
            }
          }
        }
        // if it is writing a class decl
        else if (css_class_decl) {
          t_buffer[j++] = c;
          if (c=='{') {
            css_class_decl = false;
            css_body = true;
          }
        }
        // if it is a suitable body char
        else if (!this.css_class_decl && !this.isFillChar(c)) {
          t_buffer[j++] = c;
          if (c=='}') {
            t_buffer[j++] = ' ';
            css_body = false;
            css_double = false;
            css_single = false;
          }
        }
      } else if (css_comment) {
        if (c == '\n') {
          css_comment = false;
        }
      } else if (css_block_comment) {
        if (c == '*' && buffer[i + 1] == '/') {
          css_block_comment = false;
          i++;
        }
      }
    }
    return j;
  }
  
  private boolean isFillChar(char c) {
    return c==' ' || c=='\n' || c=='\r' || c=='\t' || c==0x09 || c=='\u2573' || c=='\u3338';
  }


  public static void main(String[] args) throws Exception {
//    JCSSCompressor oComp = new JCSSCompressor();
//    oComp.compress(
//      "C:/Dev/Projects/java/Pss.main.cemho/resources/Pss/webapps/cemho/js/calendar-win2k-cold-1.css",
//    "C:/Dev/Projects/java/Pss.main.cemho/resources/Pss/webapps/cemho/js/calendar-win2k-cold-1.compressed.css"
//    );
  }


}
