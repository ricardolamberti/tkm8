package pss.bsp.consolid.model.liquidacion.acumulado;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pss.JPath;
import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.consolid.model.liquidacion.acumulado.invoice.GuiInvoices;
import pss.bsp.consolid.model.liquidacion.detail.GuiLiqDetails;
import pss.bsp.consolid.model.liquidacion.header.BizLiqHeader;
import pss.bsp.consolidador.IConciliable;
import pss.bsp.parseo.IFormato;
import pss.bsp.pdf.BizPDF;
import pss.bsp.pdf.mex.detalle.BizMexDetalle;
import pss.bsp.pdf.mex.detalle.GuiMexDetalles;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.win.JWin;
import pss.core.win.JWinsExcel;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFileGenerate;
import pss.core.win.submits.JActReport;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiLiqAcum extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiLiqAcum() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizLiqAcum(); }
  public int GetNroIcono()   throws Exception { return 10003; }
  public String GetTitle()   throws Exception { return "Liquidacion"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormLiqAcum.class; }
  public String  getKeyField() throws Exception { return "liquidacion_id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizLiqAcum GetcDato() throws Exception { return (BizLiqAcum) this.getRecord(); }

	public void createActionMap() throws Exception {
		this.addAction(10, "Detalles", null, 10020, false, false, true, "Group");		
		this.addAction(40, "Invoices", null, 10020, false, false, true, "Group");		
		this.addAction(20, "Imprimir PDF", null, 10020, true, true, true, "Group").setNuevaVentana(true);		
		this.addAction(30, "Exportar Excel", null, 10020, true, true, true, "Group");		
		this.addAction(31, "Interfaz Excel 1", null, 10020, true, true, true, "Group");		
		this.addAction(32, "Interfaz Excel 2", null, 10020, true, true, true, "Group");		
		this.addAction(33, "Interfaz Excel 3", null, 10020, true, true, true, "Group");		
		this.addAction(34, "Interfaz Excel 4", null, 10020, true, true, true, "Group");		
		this.addAction(35, "Interfaz Excel 5", null, 10020, true, true, true, "Group");		
		this.addAction(36, "Interfaz Excel 6", null, 10020, true, true, true, "Group");		
		this.addAction(37, "Interfaz Excel 7", null, 10020, true, true, true, "Group");		
		this.addAction(38, "Interfaz Excel 8", null, 10020, true, true, true, "Group");		
		BizAction a=this.addAction(80, "Publicar", null, 10045, true, true, true, "Group").setInToolbar(true);
		a.setConfirmMessageDescription("Se publicará la Liquidación definitiva");
		a=this.addAction(90, "Des-Publicar", null, 10045, true, true, true, "Group").setInToolbar(true);
		a.setConfirmMessageDescription("Se des-publicará la Liquidación");
		
		this.addAction(100, "Marcar pendiente cobro", null, 10020, true, true, true, "Group").setMulti(true);		
		this.addAction(110, "Desmarcar pendiente cobro", null, 10020, true, true, true, "Group").setMulti(true);
		
		this.createActionQuery();

	}

	@Override
	public boolean OkAction(BizAction a) throws Exception {
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
			if (a.getId()==10) return true;
			if (a.getId()==20) return true;
			if (a.getId()==30) return true;
			return false;
		}
		if (a.getId()==90) return !GetVision().equals("DK_ADMIN") && GetcDato().getEstado().equals(BizLiqHeader.PUBLICADO);
		if (a.getId()==80) return !GetVision().equals("DK_ADMIN") &&!GetcDato().getEstado().equals(BizLiqHeader.PUBLICADO);
		if (a.getId()==100) return !GetVision().equals("DK_ADMIN")&&!GetcDato().getPendiente();
		if (a.getId()==110) return !GetVision().equals("DK_ADMIN")&&GetcDato().getPendiente();
		if (a.getId()==31) return !GetVision().equals("DK_ADMIN") && GetcDato().getObjMailing().getReporte1();
		if (a.getId()==32) return !GetVision().equals("DK_ADMIN") && GetcDato().getObjMailing().getReporte2();
		if (a.getId()==33) return !GetVision().equals("DK_ADMIN") && GetcDato().getObjMailing().getReporte3();
		if (a.getId()==34) return !GetVision().equals("DK_ADMIN") && GetcDato().getObjMailing().getReporte4();
		if (a.getId()==35) return !GetVision().equals("DK_ADMIN") && GetcDato().getObjMailing().getReporte5();
		if (a.getId()==36) return !GetVision().equals("DK_ADMIN") && GetcDato().getObjMailing().getReporte6();
		if (a.getId()==37) return !GetVision().equals("DK_ADMIN") && GetcDato().getObjMailing().getReporte7();
		if (a.getId()==38) return !GetVision().equals("DK_ADMIN") && GetcDato().getObjMailing().getReporte8();

		return super.OkAction(a);
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)
			return new JActWins(this.getDetail());
		if (a.getId() == 40)
			return new JActWins(this.getInvoices());
		if (a.getId() == 20)
			return new JActReport(this.GetcDato().createDataSource(),20) ;
		if (a.getId() == 30)	return new JActFileGenerate(this, a.getId()) {
			@Override
			public String generate() throws Exception {
				return GetcDato().exportarAExcel();
			}
		};	
		if (a.getId() == 31)	return new JActFileGenerate(this) {
  		public String generate() throws Exception {
  			return getInterfaz1();
  		}
  	};
		if (a.getId() == 32)	return new JActFileGenerate(this) {
  		public String generate() throws Exception {
  			return getInterfaz2();
  		}
  	};
		if (a.getId() == 33)	return new JActFileGenerate(this) {
  		public String generate() throws Exception {
  			return getInterfaz3();
  		}
  	};
		if (a.getId() == 34)	return new JActFileGenerate(this) {
  		public String generate() throws Exception {
  			return getInterfaz4();
  		}
  	};
		if (a.getId() == 35)	return new JActFileGenerate(this) {
  		public String generate() throws Exception {
  			return getInterfaz5();
  		}
  	};
		if (a.getId() == 36)	return new JActFileGenerate(this) {
  		public String generate() throws Exception {
  			return getInterfaz6();
  		}
  	};
		if (a.getId() == 37)	return new JActFileGenerate(this) {
  		public String generate() throws Exception {
  			return getInterfaz7();
  		}
  	};
		if (a.getId() == 38)	return new JActFileGenerate(this) {
  		public String generate() throws Exception {
  			return getInterfaz8();
  		}
  	};
		if (a.getId() == 80)	return new JActSubmit(this) {
			public void submit() throws Exception {
				GetcDato().execPublicar();
			}  
		};	
		if (a.getId() == 90)	return new JActSubmit(this) {
			public void submit() throws Exception {
				GetcDato().execDespublicar();
			}  
		};	
		if (a.getId() == 100)	return new JActSubmit(this) {
			public void submit() throws Exception {
				setMessage(GetcDato().execMarcar());
			}  
		};
		if (a.getId() == 110)	return new JActSubmit(this) {
			public void submit() throws Exception {
				GetcDato().execDesmarcar();
			}  
		};
	
		return super.getSubmitFor(a);
	}
	

	public String getInterfaz1() throws Exception {
		GuiLiqDetails details = getDetail();
		details.SetVision("INTERFAZ_1");
		return details.toExcel();	
	}
	public String getInterfaz2() throws Exception {
		GuiLiqDetails details = getDetailAir();
		details.SetVision("INTERFAZ_2");
		return details.toExcel();	
	}
	public String getInterfaz3() throws Exception {
		GuiLiqDetails details = getDetailAir();
		details.SetVision("INTERFAZ_3");
		return details.toExcel();	
	}

	public String getInterfaz6() throws Exception {
		new BizMexDetalle().completeDK();
		new BizPDF().procPendientes(GetcDato().getFechaDesde(),GetcDato().getFechaHasta());

		GuiLiqDetails details = getDetail();
		details.SetVision("INTERFAZ_6");
		byte[] excel1 = details.toExcelBytes();
		GuiMexDetalles details2 = new GuiMexDetalles();
		details2.getRecords().addFilter("periodo",JDateTools.buildPeriod(GetcDato().getFechaDesde()),">=");
		details2.getRecords().addFilter("periodo",JDateTools.buildPeriod(GetcDato().getFechaHasta()),"<=");
		details2.getRecords().addFilter("dk",GetcDato().getDK());
		//details2.getRecords().addJoin(JRelations.JOIN, "tur_pnr_boleto", " tur_pnr_boleto.company=BSP_PDF_MEX_DETALLE.company and tur_pnr_boleto.numeroboleto=BSP_PDF_MEX_DETALLE.boleto ");
		details2.SetVision("INTERFAZ_6");
		byte[] excel2 = details2.toExcelBytes();

		List<SimpleEntry<String, byte[]>> hojas = Arrays.asList(new SimpleEntry<>("RPTE", excel1), new SimpleEntry<>("SABANA", excel2));

		byte[] mergedExcel = JWinsExcel.mergeExcels(hojas);

		String outputPath = "interfaz6_"+JDateTools.dateToStr(new Date(), "ddMMyyyyhhmmss")+".xlsx";
		try (FileOutputStream out = new FileOutputStream(JPath.PssPathTempFiles()+ "/"+outputPath)) {
			out.write(mergedExcel);
		}

		return outputPath;
	}

	public String getInterfaz7() throws Exception {
		new BizMexDetalle().completeDK();
		new BizPDF().procPendientes(GetcDato().getFechaDesde(),GetcDato().getFechaHasta());

	
		GuiLiqDetails details = getDetail();
		details.SetVision("INTERFAZ_6");
		byte[] excel1 = details.toExcelBytes();
		GuiMexDetalles details2 = new GuiMexDetalles();
		details2.getRecords().addFilter("period",JDateTools.buildPeriod(GetcDato().getFechaDesde()),">=");
		details2.getRecords().addFilter("period",JDateTools.buildPeriod(GetcDato().getFechaHasta()),"<=");
		details2.getRecords().addFilter("dk",GetcDato().getDK());
		//details2.getRecords().addJoin(JRelations.JOIN, "tur_pnr_boleto", " tur_pnr_boleto.company=BSP_PDF_MEX_DETALLE.company and tur_pnr_boleto.numeroboleto=BSP_PDF_MEX_DETALLE.boleto ");
		details2.SetVision("INTERFAZ_6");
		byte[] excel2 = details2.toExcelBytes();

		List<SimpleEntry<String, byte[]>> hojas = Arrays.asList(new SimpleEntry<>("RPTE", excel1), new SimpleEntry<>("SABANA", excel2));

		byte[] mergedExcel = JWinsExcel.mergeExcels(hojas);

		String outputPath = "interfaz7_"+JDateTools.dateToStr(new Date(), "ddMMyyyyhhmmss")+".xlsx";
		try (FileOutputStream out = new FileOutputStream(JPath.PssPathTempFiles()+ "/"+outputPath)) {
			out.write(mergedExcel);
		}

		return outputPath;
	}

	public String getInterfaz8() throws Exception {
		GuiLiqDetails details = getDetail();
		details.SetVision("INTERFAZ_8_A");
		byte[] excel1 = details.toExcelBytes();
	
	
		GuiLiqDetails details2 = getDetail();
		details2.getRecords().addFilter("forma_pago", "NI","<>");
		details2.SetVision("INTERFAZ_8_B");
		byte[] excel2 = details2.toExcelBytes();

		byte[] excel4 = generateCuadroResumenFromFormula();
		
		List<SimpleEntry<String, byte[]>> hojas = Arrays.asList(
				new SimpleEntry<>("REPORTE_VENTA_"+GetcDato().getDK()+"_"+JDateTools.DateToString(GetcDato().getFechaHasta(),"dd-MMM"), excel1), 
				new SimpleEntry<>("DETALLE_POR_BOLETO", excel2),
		  	new SimpleEntry<>("REPORTE_VENTA_BSP_"+GetcDato().getDK(), excel4)
				);

		byte[] mergedExcel = JWinsExcel.mergeExcels(hojas);

		String outputPath = "interfaz8_"+JDateTools.dateToStr(new Date(), "ddMMyyyyhhmmss")+".xlsx";
		try (FileOutputStream out = new FileOutputStream(JPath.PssPathTempFiles()+ "/"+outputPath)) {
			out.write(mergedExcel);
		}

		return outputPath;
	}
	public static byte[] generateCuadroResumenFromFormula() throws Exception {

    Workbook wb = new XSSFWorkbook();
    Sheet sheet = wb.createSheet("CUADRO_RESUMEN");

    // Estilos
    CellStyle boldStyle = wb.createCellStyle();
    Font boldFont = wb.createFont();
    boldFont.setBold(true);
    boldStyle.setFont(boldFont);

    CellStyle redBoldStyle = wb.createCellStyle();
    Font redBoldFont = wb.createFont();
    redBoldFont.setBold(true);
    redBoldFont.setColor(IndexedColors.RED.getIndex());
    redBoldStyle.setFont(redBoldFont);

    CellStyle redNumberStyle = wb.createCellStyle();
    Font redFont = wb.createFont();
    redFont.setColor(IndexedColors.RED.getIndex());
    redNumberStyle.setFont(redFont);
    redNumberStyle.setDataFormat(wb.createDataFormat().getFormat("#,##0.00"));

    CellStyle numberStyle = wb.createCellStyle();
    numberStyle.setDataFormat(wb.createDataFormat().getFormat("#,##0.00"));

    // Headers
    String[] headers = {
        "Transactions", "Types", "Credit Fare", "Credit Tax", "Cash Fare", "Cash Tax",
        "Comm", "Total Fare", "Settlement Amount"
    };
    Row headerRow = sheet.createRow(0);
    for (int i = 0; i < headers.length; i++) {
        Cell cell = headerRow.createCell(i);
        cell.setCellValue(headers[i]);
        cell.setCellStyle(boldStyle);
    }

    // Filas de contenido (fila 3 a fila 18)
    String[][] contenido = {
        {"Sale", "Agency Transaction"},
        {"Sale", "Merchant Transaction"},
        {"", ""},
        {"Refunds", "Agency Transaction"},
        {"", "Merchant Transaction"},
        {"", ""},
        {"Agency Debit Memo", "Merchant Transaction"},
        {"Agency Credit Memo", "Merchant Transaction"},
        {"VMPD", "Same as Sale"},
    };

    int rowIndex = 2;
    for (String[] fila : contenido) {
        Row row = sheet.createRow(rowIndex++);
        row.createCell(0).setCellValue(fila[0]);
        row.createCell(1).setCellValue(fila[1]);
        for (int i = 2; i <= 8; i++) {
            row.createCell(i).setCellValue(0);
            row.getCell(i).setCellStyle(numberStyle);
        }
    }

    // Fila de totales (fila 3)
    Row totalRow = sheet.getRow(2); // ya fue creada
    String[] columnas = { "D", "E", "F", "G", "H" };
    for (int i = 0; i < columnas.length; i++) {
        Cell cell = totalRow.createCell(i + 2);
        String formula = String.format(
            "SUMPRODUCT((ISNUMBER(DETALLE_POR_BOLETO!%1$s3:%1$s10000))*(DETALLE_POR_BOLETO!%1$s3:%1$s10000))", columnas[i]);
        cell.setCellFormula(formula);
    }

    // Total Fare = SUM(C3:G3)
    Cell totalFareCell = totalRow.createCell(7);
    totalFareCell.setCellFormula("SUM(C3:G3)");

    // Settlement Amount = -G3
    Cell settleCell = totalRow.createCell(8);
    settleCell.setCellFormula("-G3");
    settleCell.setCellStyle(redNumberStyle);

    // Footer (Net Remittance)
    Row netRow = sheet.createRow(19); // Fila 20
    Cell netLabel = netRow.createCell(0);
    netLabel.setCellValue("Net Remittance");
    netLabel.setCellStyle(redBoldStyle);

    Cell netComm = netRow.createCell(6);
    netComm.setCellFormula("G3");
    netComm.setCellStyle(redNumberStyle);

    Cell netSettle = netRow.createCell(8);
    netSettle.setCellFormula("-G3");
    netSettle.setCellStyle(redNumberStyle);

    // Autosize columns
    for (int i = 0; i < headers.length; i++) {
        sheet.autoSizeColumn(i);
    }

    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    wb.write(bos);
    wb.close();
    return bos.toByteArray();
}



	public String getInterfaz5() throws Exception {
		GuiLiqDetails details = getDetailAir();
		details.SetVision("INTERFAZ_5");
		return details.toExcel();	
	}
	
	public String getInterfaz4() throws Exception {
		GuiLiqDetails details = getDetailAir();
		details.SetVision("INTERFAZ_4");
		return details.toExcel();	
	}
	
	public GuiLiqDetails getDetail() throws Exception {
		GuiLiqDetails wins = new GuiLiqDetails();
		wins.getRecords().addFilter("liquidacion_id", GetcDato().getLiquidacionId());
		wins.getRecords().addFilter("dk", GetcDato().getDK());
	 if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
			wins.getRecords().addFixedFilter("where company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and ( bsp_liquidation_detail.dk = '"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
		} else
			wins.getRecords().addFilter("company", GetcDato().getCompany());
			
		wins.getRecords().addOrderBy("linea","asc");
		wins.SetVision(GetVision());
		wins.setParent(this);
		return wins;
	}
	public GuiInvoices getInvoices() throws Exception {
		GuiInvoices wins = new GuiInvoices();
		wins.getRecords().addFilter("liquidacion_id", GetcDato().getLiquidacionId());
		wins.getRecords().addFilter("liq_acum_id", GetcDato().getId());
		wins.getRecords().addFilter("dk", GetcDato().getDK());
		wins.getRecords().addFilter("organization", GetcDato().getOrganization());
		 if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
			wins.getRecords().addFixedFilter("where company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and ( bsp_liquidation_detail.dk = '"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
		} else
			wins.getRecords().addFilter("company", GetcDato().getCompany());
			wins.getRecords().addFilter("fecha_desde", GetcDato().getFechaDesde());
			wins.getRecords().addFilter("fecha_hasta", GetcDato().getFechaHasta());
				
		 wins.getRecords().addOrderBy("numero","desc");
			wins.SetVision(GetVision());
		wins.setParent(this);
		return wins;
	}


	public GuiLiqDetails getDetailAir() throws Exception {
		GuiLiqDetails wins = new GuiLiqDetails();
		wins.getRecords().addFilter("liquidacion_id", GetcDato().getLiquidacionId());
		wins.getRecords().addFilter("dk", GetcDato().getDK());
		wins.getRecords().addFilter("interface_id", "null","<>");
		 if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
			wins.getRecords().addFixedFilter("where company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and ( bsp_liquidation_detail.dk = '"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
		} else
			wins.getRecords().addFilter("company", GetcDato().getCompany());
			
		wins.getRecords().addOrderBy("linea","asc");
		wins.SetVision(GetVision());
		wins.setParent(this);
		return wins;
	}
	




 }
