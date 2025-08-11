package pss.core.reports.jasper;

import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.MediaSize;

public interface JPrintConstants {

  // the dots per inch constant
  public static final int DOTS_PER_INCH = 72;

  // the possible paper sizes contants
  public static final int PAPER_SIZE_LETTER = 1;
  public static final int PAPER_SIZE_A4 = 9;
  public static final int PAPER_SIZE_LEGAL = 5;

  // the possible paper sizes, in inches
  public static final double PAPER_SIZE_LETTER_WIDTH  = MediaSize.NA.LETTER.getX(Size2DSyntax.INCH);
  public static final double PAPER_SIZE_LETTER_HEIGHT = MediaSize.NA.LETTER.getY(Size2DSyntax.INCH);
  public static final double PAPER_SIZE_A4_WIDTH      = MediaSize.ISO.A4.getX(Size2DSyntax.INCH);
  public static final double PAPER_SIZE_A4_HEIGHT     = MediaSize.ISO.A4.getY(Size2DSyntax.INCH);
  public static final double PAPER_SIZE_LEGAL_WIDTH   = MediaSize.NA.LEGAL.getX(Size2DSyntax.INCH);
  public static final double PAPER_SIZE_LEGAL_HEIGHT  = MediaSize.NA.LEGAL.getY(Size2DSyntax.INCH);


}
