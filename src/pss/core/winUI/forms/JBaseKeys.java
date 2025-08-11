package pss.core.winUI.forms;

import java.awt.event.InputEvent;

public class JBaseKeys {

  //-------------------------------------------------------------------------- //
  // Constantes de Pss que representan los caracteres de control
  //-------------------------------------------------------------------------- //
  private final static int  MASK_OFFSET = 16;
  public final static int   Pss_CTRL_MASK   = InputEvent.CTRL_MASK << MASK_OFFSET;
  public final static int   Pss_SHIFT_MASK  = InputEvent.SHIFT_MASK << MASK_OFFSET;
  public final static int   Pss_ALT_MASK    = InputEvent.ALT_MASK << MASK_OFFSET;

  //-------------------------------------------------------------------------- //
  // Constructor
  //-------------------------------------------------------------------------- //
//  public JBaseKeys( Component source, int id, long when, int modifiers,
//                    int keyCode, char keyChar) {
//    super( source, id, when, modifiers, keyCode, keyChar );
//  }

//  //-------------------------------------------------------------------------- //
//  // Comparo dos teclas incluyendo las teclas de control
//  //-------------------------------------------------------------------------- //
//  public static boolean CompareKeys( KeyEvent e, int zPssCode ) {
//    if (e.getKeyCode() != getAWTKeyCode(zPssCode)) {
//      return false;
//    }
//
//    int requiredAWTModifiers = getAWTModifiers(zPssCode);
//    boolean isCtrlDown = (requiredAWTModifiers & InputEvent.CTRL_MASK) == InputEvent.CTRL_MASK;
//    boolean isAltDown = (requiredAWTModifiers & InputEvent.ALT_MASK) == InputEvent.ALT_MASK;
//    boolean isShiftDown = (requiredAWTModifiers & InputEvent.SHIFT_MASK) == InputEvent.SHIFT_MASK;
//    return
//        (isCtrlDown ? e.isControlDown() : true) &&
//        (isAltDown ? e.isAltDown() : true) &&
//        (isShiftDown ? e.isShiftDown() : true);
//  }
//
//  public static int getAWTKeyCode(int zPssFullKeyCode) {
//    return ((short)zPssFullKeyCode);
//  }
//  public static int getAWTModifiers(int zPssFullKeyCode) {
//    return zPssFullKeyCode >> MASK_OFFSET;
//  }
//
//  public static int getPssFullKeyCode(int zKeyCode, int zModifiers) {
//    return (zModifiers << MASK_OFFSET) | zKeyCode;
//  }
//  /**
//   * Creates a keystroke from a keycode and a modifiers values
//   */
//  public static KeyStroke createKeyStroke(int zKeyCode, int zModifiers) {
//    return KeyStroke.getKeyStroke(zKeyCode, zModifiers);
//  }
//
//  public static KeyStroke createKeyStrokeFor(BizAction zAction) {
//    int zeisFullKeyCode = zAction.GetKeyAction();
//    return KeyStroke.getKeyStroke(
//              getAWTKeyCode(zeisFullKeyCode),
//              getAWTModifiers(zeisFullKeyCode)
//              );
//  }
//
///*
//  public static void main(String[] args) {
//
//    int keyCode = VK_F5;
//    int modifiers = Pss_CTRL_MASK | Pss_ALT_MASK;
//
//    int PssKeyCode = modifiers | keyCode;
//
//    System.out.println();
//
//    System.out.println("AWT SHIFT Modifier:   " + JTools.LPad(Integer.toBinaryString(SHIFT_MASK), 32, "0"));
//    System.out.println("AWT CTRL Modifier:    " + JTools.LPad(Integer.toBinaryString(CTRL_MASK), 32, "0"));
//    System.out.println("AWT ALT Modifier:     " + JTools.LPad(Integer.toBinaryString(ALT_MASK), 32, "0"));
//
//    System.out.println();
//
//    System.out.println("Pss SHIFT Modifier:  " + JTools.LPad(Integer.toBinaryString(Pss_SHIFT_MASK), 32, "0"));
//    System.out.println("Pss CTRL Modifier:   " + JTools.LPad(Integer.toBinaryString(Pss_CTRL_MASK), 32, "0"));
//    System.out.println("Pss ALT Modifier:    " + JTools.LPad(Integer.toBinaryString(Pss_ALT_MASK), 32, "0"));
//
//    System.out.println();
//
//    System.out.println("Original Key Code:    " + JTools.LPad(Integer.toBinaryString(keyCode), 32, "0"));
//    System.out.println("Original Modifiers:   " + JTools.LPad(Integer.toBinaryString(modifiers), 32, "0"));
//    System.out.println("Pss full Key Code:   " + JTools.LPad(Integer.toBinaryString(PssKeyCode), 32, "0"));
//
//    System.out.println();
//
//    System.out.println("Parsed AWT Key Code:  " + JTools.LPad(Integer.toBinaryString(getAWTKeyCode(PssKeyCode)), 32, "0"));
//    System.out.println("Parsed AWT Modifiers: " + JTools.LPad(Integer.toBinaryString(getAWTModifiers(PssKeyCode)), 32, "0"));
//  }
//*/
//
//
///*  public static void main(String[] args) {
//
//    int keyCode = KeyEvent.VK_F5;
//    int modifiers = KeyEvent.ALT_MASK | KeyEvent.CTRL_MASK;
//
//    int PssKeyCode = JBaseKeys.getPssFullKeyCode(keyCode, modifiers);
//
//    System.out.println();
//
//    System.out.println("AWT SHIFT Modifier:   " + JTools.LPad(Integer.toBinaryString(SHIFT_MASK), 32, "0"));
//    System.out.println("AWT CTRL Modifier:    " + JTools.LPad(Integer.toBinaryString(CTRL_MASK), 32, "0"));
//    System.out.println("AWT ALT Modifier:     " + JTools.LPad(Integer.toBinaryString(ALT_MASK), 32, "0"));
//
//    System.out.println();
//
//    System.out.println("Pss SHIFT Modifier:  " + JTools.LPad(Integer.toBinaryString(Pss_SHIFT_MASK), 32, "0"));
//    System.out.println("Pss CTRL Modifier:   " + JTools.LPad(Integer.toBinaryString(Pss_CTRL_MASK), 32, "0"));
//    System.out.println("Pss ALT Modifier:    " + JTools.LPad(Integer.toBinaryString(Pss_ALT_MASK), 32, "0"));
//
//    System.out.println();
//
//    System.out.println("Original Key Code:    " + JTools.LPad(Integer.toBinaryString(keyCode), 32, "0"));
//    System.out.println("Original Modifiers:   " + JTools.LPad(Integer.toBinaryString(modifiers), 32, "0"));
//    System.out.println("Pss full Key Code:   " + JTools.LPad(Integer.toBinaryString(PssKeyCode), 32, "0"));
//
//    System.out.println();
//
//    System.out.println("Parsed AWT Key Code:  " + JTools.LPad(Integer.toBinaryString(getAWTKeyCode(PssKeyCode)), 32, "0"));
//    System.out.println("Parsed AWT Modifiers: " + JTools.LPad(Integer.toBinaryString(getAWTModifiers(PssKeyCode)), 32, "0"));
//  }



}

