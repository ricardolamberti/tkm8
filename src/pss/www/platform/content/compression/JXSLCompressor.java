/*
 * Created on 21-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.compression;

public class JXSLCompressor extends JClientContentCompressor {

  // flags
  private boolean bNodeDef;
  private boolean bAttrValue;
 // private boolean bNodeText;
  private boolean bComment;
  private boolean bStartedNodeText;
  private boolean bStartedLineInText;
  
  
  private boolean isEditFill(char c) {
    return c=='\t' || isNewLine(c);
  }
  private boolean isAnyFill(char c) {
    return isEditFill(c) || c==' ';
  }
  private boolean isNewLine(char c) {
    return c=='\n' || c=='\r';
  }

  @Override
	protected int compress(char[] inBuffer, char[] outBuffer) throws Exception {
    int iOutSize = inBuffer.length;
    int iWrittenCount = 0;
    char c;
    boolean bMustWrite = false;
    for (int i = 0; i < iOutSize; i++) {
      c = inBuffer[i];
      if (bNodeDef && !bComment) {
        if (bAttrValue) {
          bMustWrite = true;
        } else if (c=='\"') {
          bAttrValue = true;
          bMustWrite = true;
        } else if (!bAttrValue && i >= 0 && inBuffer[i-1]=='\"') {
          c = ' ';
          bMustWrite = true;
        } else if (isEditFill(c)) {
          bMustWrite = false;
        } else {
          bMustWrite = true;
        }
        if (c=='>') {
          bNodeDef = false;
          bStartedNodeText = false;
          bStartedLineInText = false;
        }
      } else if (bComment) {
        bMustWrite = false;
        if (c=='-' && inBuffer[i+1]=='-' && inBuffer[i+2]=='>') {
          bComment = false;
          i = i + 2;
        }
      } else {
        if (c=='<' && (i+3 < iOutSize) && inBuffer[i+1]=='!' && inBuffer[i+2]=='-' && inBuffer[i+2]=='-') {
          bComment = true;
          i = i + 3;
          bMustWrite = false;
        } else if (c=='<') {
          bNodeDef = true;
          bMustWrite = true;
        } else {
          // no estoy en node def y no estoy en comentatio => estoy en texto de un nodo
          if (bStartedNodeText) {
            if (!bStartedLineInText) {
              if (!isAnyFill(c)) {
                bStartedLineInText = true;
                bMustWrite = true;
              } else {
                bMustWrite = false;
              }
            } else {
              if (isNewLine(c)) {
                c = ' ';
                bMustWrite = true;
                bStartedLineInText = false;
              } else {
                if (isAnyFill(c)) {
                  c = ' ';
                  bStartedLineInText = false;
                }
                bMustWrite = true;
              }
            }
          } else {
            if (!isAnyFill(c)) {
              bStartedNodeText = true;
              bStartedLineInText = true;
              bMustWrite = true;
            } else {
              bMustWrite = false;
            }
          }
          
        }
      }
      if (bMustWrite) {
        outBuffer[iWrittenCount] = c;
        iWrittenCount++;
      }
    }
    return iWrittenCount;
  }


  public static void main(String[] args) throws Exception {
    String sFileFrom = "c:/dev/tree.js.xsl";
    String sFileTo = "c:/dev/tree.js.new.xsl";
    new JXSLCompressor().compress(sFileFrom, sFileTo);
  }

}
