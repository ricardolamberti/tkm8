package  pss.bsp.parseo.bspParseo;

import pss.bsp.bo.formato.BizFormato;
import pss.bsp.pdf.BizPDF;

public interface IBspSititaParser extends IBspParseo {
	public boolean fillFormat(BizPDF pdfBase,String[][] learningBase,BizFormato formato) throws Exception;

}
