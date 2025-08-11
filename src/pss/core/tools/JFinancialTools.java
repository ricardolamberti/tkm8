package pss.core.tools;


public class JFinancialTools {


  public JFinancialTools() {
  }

  public static String VerificarRUT(String sInputRUT) throws Exception{
    String sRUT = sInputRUT.trim();
    if (sRUT.length() < 2 ) {
      sendInvalidFiscalIdError();
    }
    String sVerifierDigit = sRUT.substring(sRUT.length() - 1).toUpperCase();
    sRUT = JTools.getNumberWithOutSymbols(sRUT.substring(0, sRUT.length()-1));
    if (sRUT.length() > 8 || !JTools.isNumber(sRUT)) {
      sendInvalidFiscalIdError();
    }

    int[] weights = {2, 3, 4, 5, 6, 7};
    int iSum = 0;
    int iWeightIndex = 0;
    for (int i = sRUT.length()-1; i >= 0; i--) {
      iSum += Integer.parseInt(String.valueOf(sRUT.charAt(i))) * weights[iWeightIndex];
      iWeightIndex++;
      if (iWeightIndex == weights.length) {
        iWeightIndex = 0;
      }
    }

    int iRemainder = iSum % 11;
    String sCalculatedDigit;
    if (iRemainder==0) {
      sCalculatedDigit = "0";
    } else if (iRemainder==1) {
      sCalculatedDigit = "K";
    } else {
      sCalculatedDigit = String.valueOf(11 - iRemainder);
    }

    if (!sVerifierDigit.equals(sCalculatedDigit)) {
      sendInvalidFiscalIdError();
    }

    return sRUT + sVerifierDigit;
  }

  public static String VerificarCuit(String pNroCui) throws Exception {

    String mCuit = "";
    int mSuma;
    boolean mError;
    int mDigitoVerificador;

    mError = true;
    mCuit = pNroCui.trim();

    if (mCuit.length()==13 && mCuit.substring(2,3).equals("-") && mCuit.substring(11,12).equals("-"))
      mCuit =  mCuit.substring(0,2)+mCuit.substring(3,11)+mCuit.substring(12,13);

    if (mCuit.length()==11 && JTools.isNumberPure(mCuit) &&  mCuit.lastIndexOf(".")==-1) {
      mDigitoVerificador = Integer.parseInt(mCuit.substring(10,11) );
      mSuma = Integer.parseInt(mCuit.substring(0,1)) * 5;
      mSuma = mSuma + Integer.parseInt(mCuit.substring(1,2)) * 4;
      mSuma = mSuma + Integer.parseInt(mCuit.substring(2,3)) * 3;
      mSuma = mSuma + Integer.parseInt(mCuit.substring(3,4)) * 2;
      mSuma = mSuma + Integer.parseInt(mCuit.substring(4,5)) * 7;
      mSuma = mSuma + Integer.parseInt(mCuit.substring(5,6)) * 6;
      mSuma = mSuma + Integer.parseInt(mCuit.substring(6,7)) * 5;
      mSuma = mSuma + Integer.parseInt(mCuit.substring(7,8)) * 4;
      mSuma = mSuma + Integer.parseInt(mCuit.substring(8,9)) * 3;
      mSuma = mSuma + Integer.parseInt(mCuit.substring(9,10)) * 2;
      mSuma = mSuma % 11;
      mSuma = 11 - mSuma;
      if (mSuma==11) mSuma=0;
      if (mSuma==10) mSuma=9;
      if (mDigitoVerificador==mSuma) mError = false;
    }

    if (mError) {
      sendInvalidFiscalIdError();
    }

    return mCuit.toString();
  }

  public static String VerificarRUC(String zNroCui) throws Exception {
    String sRUC = zNroCui.trim();
    boolean bValidNumber = true;
    if (JTools.isNumberPure(sRUC) && sRUC.length() == 11) {
      if (Long.parseLong(sRUC) < 1L) {
        sendFiscalIdAbsentError();
      } else {
        sRUC = JTools.LPad(sRUC, 11, "0");
        int iLastDigit = Integer.parseInt(sRUC.substring(10));
        int iCalculatedDigit = calculateRUCVerifierDigit(sRUC.substring(0, 10));
        bValidNumber = (iLastDigit == iCalculatedDigit);
      }
    } else {
      bValidNumber = false;
    }

    if (!bValidNumber) {
      sendInvalidFiscalIdError();
    }

    return sRUC;
  }
 
  private static void sendInvalidFiscalIdError() throws Exception {
    JExcepcion.SendError("El Nro. de Identificación Fiscal es inválido");
  }
  private static void sendFiscalIdAbsentError() throws Exception {
    JExcepcion.SendError("El Nro. de Identificación Fiscal está incompleto");
  }

  private static int calculateRUCVerifierDigit(String zRUCWithoutLastDigit) {
    // create an array with the digits
    int[] dig = new int[10];
    for (int i = 0; i < dig.length; i++) {
      dig[i] = Integer.parseInt(String.valueOf(zRUCWithoutLastDigit.charAt(i)));
    }
    // define the weight per digit array
    int[] weights = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2};

    // multiply each digit by its weight and sum all the products
    int iSum = 0;
    for (int i = 0; i < dig.length; i++) {
      iSum += (dig[i] * weights[i]);
    }

    // calculate the verifier digit, as follows:
    //
    //   iVerifierDigit = 11 - ( iSum - ( (iSum // 11) * 11 ) )
    //
    //      where // means integer division
    //
    int iDivBy11 = iSum / 11;
    int iVerifierDigit = 11 - ( iSum - (iDivBy11 * 11) );
    return iVerifierDigit > 9 ? (iVerifierDigit - 10) : iVerifierDigit;
  }


  public static void main(String[] args) throws Exception {
    VerificarRUT("77740010K");
    VerificarRUT("969732300");

  }

  public static String VerificarRFC(String zNroCui) throws Exception {
    String sRFC = zNroCui.trim();
    return sRFC;
  }

}


