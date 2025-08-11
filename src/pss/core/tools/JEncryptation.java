package pss.core.tools;


public class JEncryptation {

   public class JByteVector {
      byte [] data = null;

      public JByteVector (){}
      public JByteVector (int len){ data = new byte[len];}

      public byte[] subData(int begin, int len){
        byte[] ret = new byte [len];

        for (int i = 0; i < len ; i++)ret[i] = data[i + begin];

        return ret;
      }

      public void SetData (byte[] val){
        data = new byte [val.length];
        for( int i= 0; i < val.length ; i++) data[i]=val[i];
      }

      public void SetData (byte[] val, int offset){
        for( int i= 0; i < val.length ; i++)data[i + offset]=val[i];
      }


      public void SetData (String val){
        data = new byte[val.length() / 2 + val.length() % 2];
        val = val.toUpperCase();
        for (int i = 0; i < val.length(); i++){
          data [i/2] *= 0x10;
          data [i/2] += (byte)(
                          (val.charAt(i)>='0' && val.charAt(i)<='9') ?
                            val.charAt(i) - '0' :
                              (val.charAt(i)>='A' && val.charAt(i)<='F') ?
                                val.charAt(i) - 'A' + 0x0A:
                                  0 );
        }
      }

      public void print(){
        for(int i = 0; i< data.length ; i++)
          System.out.print(Integer.toHexString(data[i] & 0xff) + " " );
        System.out.println("");
      }

      public String ToString (){
        String dev = new String();
        for(int i = 0; i< data.length ; i++){
          String aux = Integer.toHexString(data[i] & 0xff);
          dev += (aux.length() == 0) ? "00" : ((aux.length() == 1) ? "0" : "") + aux;
        }
        return dev;
      }

       public byte[] GetData () { return data; }
  }

  //Permuted Choice 1
  static byte PC1[] = { 57, 49, 41, 33, 25, 17,  9,
                         1, 58, 50, 42, 34, 26, 18,
                        10,  2, 59, 51, 43, 35, 27,
                        19, 11,  3, 60, 52, 44, 36,
                        63, 55, 47, 39, 31, 23, 15,
                         7, 62, 54, 46, 38, 30, 22,
                        14,  6, 61, 53, 45, 37, 29,
                        21, 13,  5, 28, 20, 12,  4
                      };


  //Permuted Choice 2
  static byte PC2[] = { 14, 17, 11, 24,  1,  5,
                        3, 28, 15,  6, 21, 10,
                       23, 19, 12,  4, 26,  8,
                       16,  7, 27, 20, 13,  2,
                       41, 52, 31, 37, 47, 55,
                       30, 40, 51, 45, 33, 48,
                       44, 49, 39, 56, 34, 53,
                       46, 42, 50, 36, 29, 32
                      };


  //Initial Permutation (IP)
  static byte PI[] =  { 58, 50, 42, 34, 26, 18, 10,  2,
                        60, 52, 44, 36, 28, 20, 12,  4,
                        62, 54, 46, 38, 30, 22, 14,  6,
                        64, 56, 48, 40, 32, 24, 16,  8,
                        57, 49, 41, 33, 25, 17,  9,  1,
                        59, 51, 43, 35, 27, 19, 11,  3,
                        61, 53, 45, 37, 29, 21, 13,  5,
                        63, 55, 47, 39, 31, 23, 15,  7
                      };

  static byte LEFTSHIFTS [] = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};

  static byte EXP[] = { 32,  1,  2,  3,  4,  5,
                         4,  5,  6,  7,  8,  9,
                         8,  9, 10, 11, 12, 13,
                        12, 13, 14, 15, 16, 17,
                        16, 17, 18, 19, 20, 21,
                        20, 21, 22, 23, 24, 25,
                        24, 25, 26, 27, 28, 29,
                        28, 29, 30, 31, 32,  1
                      };

   static byte SUS [][] =  {
                       { 14,  4, 13,  1,  2, 15, 11,  8,  3, 10,  6, 12,  5,  9,  0,  7,
                          0, 15,  7,  4, 14,  2, 13,  1, 10,  6, 12, 11,  9,  5,  3,  8,
                          4,  1, 14,  8, 13,  6,  2, 11, 15, 12,  9,  7,  3, 10,  5,  0,
                         15, 12,  8,  2,  4,  9,  1,  7,  5, 11,  3, 14, 10,  0,  6, 13
                       },{ 15,  1,  8, 14,  6, 11,  3,  4,  9,  7,  2, 13, 12,  0,  5, 10,
                          3, 13,  4,  7, 15,  2,  8, 14, 12,  0,  1, 10,  6,  9, 11,  5,
                          0, 14,  7, 11, 10,  4, 13,  1,  5,  8, 12,  6,  9,  3,  2, 15,
                          13,  8, 10,  1,  3, 15,  4,  2, 11,  6,  7, 12,  0,  5, 14,  9
                       },{ 10,  0,  9, 14,  6,  3, 15,  5,  1, 13, 12,  7, 11,  4,  2,  8,
                           13,  7,  0,  9,  3,  4,  6, 10,  2,  8,  5, 14, 12, 11, 15,  1,
                           13,  6,  4,  9,  8, 15,  3,  0, 11,  1,  2, 12,  5, 10, 14,  7,
                            1, 10, 13,  0,  6,  9,  8,  7,  4, 15, 14,  3, 11,  5,  2, 12
                       },{  7, 13, 14,  3,  0,  6,  9, 10,  1,  2,  8,  5, 11, 12,  4, 15,
                           13,  8, 11,  5,  6, 15,  0,  3,  4,  7,  2, 12,  1, 10, 14,  9,
                           10,  6,  9,  0, 12, 11,  7, 13, 15,  1,  3, 14,  5,  2,  8,  4,
                            3, 15,  0,  6, 10,  1, 13,  8,  9,  4,  5, 11, 12,  7,  2, 14
                       },{  2, 12,  4,  1,  7, 10, 11,  6,  8,  5,  3, 15, 13,  0, 14,  9,
                           14, 11,  2, 12,  4,  7, 13,  1,  5,  0, 15, 10,  3,  9,  8,  6,
                            4,  2,  1, 11, 10, 13,  7,  8, 15,  9, 12,  5,  6,  3,  0, 14,
                           11,  8, 12,  7,  1, 14,  2, 13,  6, 15,  0,  9, 10,  4,  5,  3
                       },{ 12,  1, 10, 15,  9,  2,  6,  8,  0, 13,  3,  4, 14,  7,  5, 11,
                           10, 15,  4,  2,  7, 12,  9,  5,  6,  1, 13, 14,  0, 11,  3,  8,
                            9, 14, 15,  5,  2,  8, 12,  3,  7,  0,  4, 10,  1, 13, 11,  6,
                            4,  3,  2, 12,  9,  5, 15, 10, 11, 14,  1,  7,  6,  0,  8, 13
                       },{  4, 11,  2, 14, 15,  0,  8, 13,  3, 12,  9,  7,  5, 10,  6,  1,
                           13,  0, 11,  7,  4,  9,  1, 10, 14,  3,  5, 12,  2, 15,  8,  6,
                            1,  4, 11, 13, 12,  3,  7, 14, 10, 15,  6,  8,  0,  5,  9,  2,
                            6, 11, 13,  8,  1,  4, 10,  7,  9,  5,  0, 15, 14,  2,  3, 12
                       },{ 13,  2,  8,  4,  6, 15, 11,  1, 10,  9,  3, 14,  5,  0, 12,  7,
                            1, 15, 13,  8, 10,  3,  7,  4, 12,  5,  6, 11,  0, 14,  9,  2,
                            7, 11,  4,  1,  9, 12, 14,  2,  0,  6, 10, 13, 15,  3,  5,  8,
                            2,  1, 14,  7,  4, 10,  8, 13, 15, 12,  9,  0,  3,  5,  6, 11
                       }
                    };

  static byte PERM[] = { 16,  7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26,
                          5, 18, 31, 10,  2,  8, 24, 14, 32, 27,  3,  9,
			 19, 13, 30,  6, 22, 11,  4, 25
                        };

  static byte PI1_INV[] = { 40,  8, 48, 16, 56, 24, 64, 32, 39,  7, 47, 15,
                            55, 23, 63, 31, 38,  6, 46, 14, 54, 22, 62, 30,
                            37,  5, 45, 13, 53, 21, 61, 29, 36,  4, 44, 12,
                            52, 20, 60, 28, 35,  3, 43, 11, 51, 19, 59, 27,
  			    34,  2, 42, 10, 50, 18, 58, 26, 33,  1, 41,  9,
                            49, 17, 57, 25
                          };


  static char BITVALUE[]    = { 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80 };
  static char BITVALUE_I[]  = { 0x80, 0x40, 0x20, 0x10, 0x08, 0x04, 0x02, 0x01 };
  static char MASKVALUE_L[] = { 0x80, 0xC0, 0xE0, 0xF0, 0xF8, 0xFC, 0xFE, 0xFF };
  static char MASKVALUE_R[] = { 0x01, 0x03, 0x07, 0x0F, 0x1F, 0x3F, 0x7F, 0xFF };

  JByteVector[] desKeys   = new JByteVector[]{
                            new JByteVector(7),new JByteVector(7),
                            new JByteVector(7),new JByteVector(7),
                            new JByteVector(7),new JByteVector(7),
                            new JByteVector(7),new JByteVector(7),
                            new JByteVector(7),new JByteVector(7),
                            new JByteVector(7),new JByteVector(7),
                            new JByteVector(7),new JByteVector(7),
                            new JByteVector(7),new JByteVector(7),
                            new JByteVector(7)};

  JByteVector[] desKeysAux = new JByteVector[] {
                             new JByteVector(7),new JByteVector(7),
                             new JByteVector(7),new JByteVector(7),
                             new JByteVector(7),new JByteVector(7),
                             new JByteVector(7),new JByteVector(7),
                             new JByteVector(7),new JByteVector(7),
                             new JByteVector(7),new JByteVector(7),
                             new JByteVector(7),new JByteVector(7),
                             new JByteVector(7),new JByteVector(7),
                             new JByteVector(7)};


  //** CONSTRUCTOR **//
  public JEncryptation() { }


  public void DoPermutation(JByteVector dest, JByteVector src, byte[] table, int tableLen){
   int i;
   @SuppressWarnings("unused")
	int lastByte;

    lastByte = tableLen / 8 + (tableLen % 8 > 0 ? 1 : 0);

    for(i=0;i < tableLen; i++)
      dest.GetData()[i/8] |= (src.GetData()[(table[i]-1) / 8] & BITVALUE_I[ (table[i]-1) % 8 ])!=0 ? BITVALUE_I[i % 8] : 0;
    return;
  }

  public void DoPermutationInv(JByteVector dest, JByteVector src, byte[] table, int tableLen){
   int i;
   @SuppressWarnings("unused")
	int lastByte;

    lastByte = tableLen / 8 + (tableLen % 8 > 0 ? 1 : 0);

    for(i=0;i < tableLen; i++)
      dest.GetData()[(table[i]-1) / 8] |= (src.GetData()[i/8] & BITVALUE_I[ i % 8 ])!=0 ? BITVALUE_I[(table[i]-1) % 8 ] : 0;
    return;
  }

  public void ShiftLeftKey(JByteVector dest, JByteVector src, int pos){
    int aux =(((src.GetData()[0]&0xff) << 24) |
              ((src.GetData()[1]&0xff) << 16) |
              ((src.GetData()[2]&0xff) << 8) |
              (src.GetData()[3]&0xf0) ) & 0xffffffff;

    aux = aux << pos | (((aux >> (32 - pos)) & MASKVALUE_R[pos - 1]) << 4 ) ;

    dest.GetData()[0] = (byte)(((aux & 0xffffffff)>> 24) & 0xff);
    dest.GetData()[1] = (byte)(((aux & 0xffffffff)>> 16)&0xff);
    dest.GetData()[2] = (byte)(((aux & 0xffffffff)>> 8)&0xff);
    dest.GetData()[3] = (byte)(((aux & 0xffffffff)    ) &0xff);

    aux =    (((src.GetData()[3]&0x0f) << 28) |
              ((src.GetData()[4]&0xff) << 20) |
              ((src.GetData()[5]&0xff) << 12) |
               (src.GetData()[6]&0xff) << 4 ) & 0xfffffff0;

    aux = aux << pos | (((aux >> (32 - pos)) & MASKVALUE_R[pos - 1]) << 4 ) ;

    dest.GetData()[3] = (byte)((dest.GetData()[3] & 0xf0) | ((aux >> 28)&0x0f));
    dest.GetData()[4] = (byte)((aux >> 20)&0xff);
    dest.GetData()[5] = (byte)((aux >> 12)&0xff);
    dest.GetData()[6] = (byte)((aux >> 4)&0xff);
  }

  public void DoSubstitutions(JByteVector dest, JByteVector src){
     int i;
     int offset[] = new int[8];

	for(i=0; i < 48 ; i+=6){
            offset[i/6] =  ((src.GetData()[(i+0)/8] & BITVALUE_I[(i+0)%8])!=0 ? 0x20 : 0) +
                           ((src.GetData()[(i+5)/8] & BITVALUE_I[(i+5)%8])!=0 ? 0x10 : 0) +
                           ((src.GetData()[(i+1)/8] & BITVALUE_I[(i+1)%8])!=0 ? 0x08 : 0) +
                           ((src.GetData()[(i+2)/8] & BITVALUE_I[(i+2)%8])!=0 ? 0x04 : 0) +
                           ((src.GetData()[(i+3)/8] & BITVALUE_I[(i+3)%8])!=0 ? 0x02 : 0) +
                           ((src.GetData()[(i+4)/8] & BITVALUE_I[(i+4)%8])!=0 ? 0x01 : 0) ;
	}

	for(i=0;  i < 8 ; i+=2)
	  dest.GetData()[i/2] = (byte)(( SUS[i][offset[i]] * 0x10 ) | SUS[i+1][offset[i+1]]);
   }


  public void HDesSetKey(String key){
    int i;
    JByteVector key_ = new JByteVector();
    key_.SetData(key);

    DoPermutation(desKeysAux[0], key_ , PC1, 56);
    DoPermutation(desKeys[0], desKeysAux[0], PC2, 48);

    for(i=1; i <= 16; i++){
        ShiftLeftKey(desKeysAux[i], desKeysAux[i-1], LEFTSHIFTS[i-1]);
        DoPermutation(desKeys[i], desKeysAux[i], PC2, 48);
    }

  }


  public String HEncrypt(String sbuff){

    @SuppressWarnings("unused")
		String keyEnd = new String();
    JByteVector buff = new JByteVector();
    buff.SetData(sbuff);

    JByteVector [] buffer = new JByteVector[]{
                  new JByteVector(8), new JByteVector(8), new JByteVector(8),
                  new JByteVector(8), new JByteVector(8), new JByteVector(8),
                  new JByteVector(8), new JByteVector(8), new JByteVector(8),
                  new JByteVector(8), new JByteVector(8), new JByteVector(8),
                  new JByteVector(8), new JByteVector(8), new JByteVector(8),
                  new JByteVector(8), new JByteVector(8)};
    int i, j;

    DoPermutation(buffer[0], buff, PI, 64);


    for(i=1; i <= 16;i++ ){ //Por cada una de las 16 claves
          //L(i) = R(i-1)
          buffer[i].SetData(buffer[i-1].subData(4,4),0);
          //System.out.print("L("+i+")=");buffer[i].print();

          //Expandimos el lado derecho
          //EXP(R(i-1))
          JByteVector expandedR = new JByteVector(6);
          JByteVector buffAux = new JByteVector();
          buffAux.SetData(buffer[i-1].subData(4,4));
          DoPermutation(expandedR, buffAux, EXP, 48);

          //XOR del bloque derecho con la clave correspondiente
          // EXP(R(i-1)) ^ K(i)
          for(j=0; j<6 ; j++){
            expandedR.GetData()[j] ^= desKeys[i].GetData()[j];
          }

          //Hago las substituciones correspondientes a R(i)
           DoSubstitutions(expandedR,expandedR);


          JByteVector buffR = new JByteVector(4);
          DoPermutation(buffR, expandedR, PERM, 32);
          buffer[i].SetData(buffR.GetData(),4);


          //L(i-1) ^ f(R(i),K(i))
          for(j=0; j < 4 ; j++)
            buffer[i].GetData()[j+4] ^= buffer[i-1].GetData()[j];
      }

      //Hago la permutacion final de R[16]L[16]
      buff = new JByteVector(8);
      JByteVector buff16 = new JByteVector(8);
      buff16.SetData(buffer[16].subData(0,4),4);
      buff16.SetData(buffer[16].subData(4,4),0);
      DoPermutation(buff, buff16, PI1_INV, 64);

      return buff.ToString();
    }

  public String HDesEncrypt(String sbuff){

    @SuppressWarnings("unused")
		String keyEnd = new String();
    JByteVector buff = new JByteVector();
    buff.SetData(sbuff);

    JByteVector [] buffer = new JByteVector[]{
                  new JByteVector(8), new JByteVector(8), new JByteVector(8),
                  new JByteVector(8), new JByteVector(8), new JByteVector(8),
                  new JByteVector(8), new JByteVector(8), new JByteVector(8),
                  new JByteVector(8), new JByteVector(8), new JByteVector(8),
                  new JByteVector(8), new JByteVector(8), new JByteVector(8),
                  new JByteVector(8), new JByteVector(8)};
    int i, j;

    JByteVector buff16 = new JByteVector(8);
    DoPermutationInv(buff16, buff, PI1_INV, 64);
    buffer[16].SetData(buff16.subData(4,4),0);
    buffer[16].SetData(buff16.subData(0,4),4);

   for(i=15; i >= 0; i-- ){
      buffer[i].SetData(buffer[i+1].subData(0,4),4);

      JByteVector expandedR = new JByteVector(6);
      JByteVector buffAux = new JByteVector();
      buffAux.SetData(buffer[i+1].subData(0,4));
      DoPermutation(expandedR, buffAux, EXP, 48);

      for(j=0; j<6 ; j++){
          expandedR.GetData()[j] ^= desKeys[i+1].GetData()[j];
      }

      DoSubstitutions(expandedR,expandedR);


      JByteVector buffR = new JByteVector(4);
      DoPermutation(buffR, expandedR, PERM, 32);
      buffer[i].SetData(buffR.GetData(),0);

      buffR = new JByteVector(4);
      buffR.SetData(buffer[i+1].subData(4,4),0);

      for(j=0; j < 4 ; j++)
            buffer[i].GetData()[j] ^= buffR.GetData()[j];

   }

   buff16 = new JByteVector(8);
   DoPermutationInv(buff16, buffer[0], PI, 64);

  return buff16.ToString();

  }


  public String EncryptString(String src){
    String ret = "";
    String aux = JTools.LPad("" + src.length() ,4,"0") + JTools.BinToHexString(src, src.length());

    aux = JTools.RPad(aux, aux.length() + 16 - (aux.length()%16), "0");
    for(int i = 0;  i <= aux.length() - 16 ; i+=16){
      ret += HEncrypt(aux.substring(i,i+16));
    }

    return JTools.HexStringToBin(ret, ret.length());
  }

  public String DesEncryptString(String src){
    String ret = "";
    String aux = JTools.BinToHexString(src, src.length());

    aux = JTools.RPad(aux, 16 - (aux.length()%16), "0");

    for(int i = 0;  i < aux.length() ; i+=16){
      ret += HDesEncrypt(aux.substring(i,i+16));
    }

    int finalLen = Integer.valueOf(ret.substring(0,4)).intValue();
    ret = ret.substring(4,ret.length());

    return JTools.HexStringToBin(ret, finalLen * 2);
  }



}
