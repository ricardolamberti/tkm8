package  pss.bsp.consolidador;

import java.util.Date;

import pss.bsp.parseo.IFormato;


public interface IConciliable {
	public static final String NO_CHEQUEAR = "*";
	public static final String BOLETOS = "Boleto";
	public static final String AEROLINEA_BOLETOS = "Aerolinea Boleto";
	public static final String BOLETOS_LARGO = "Boletos F.Largo";
	public static final String FECHA = "Fecha";
	public static final String OPERACION = "Operacion";
	public static final String TARIFA = "Tarifa (COBL)";
	public static final String CONTADO = "Contado (CASH)";
	public static final String TARJETA = "Tarjeta (CARD)";
	public static final String CONTADO_BRUTO = "Contado Bruto";
	public static final String TARJETA_BRUTO = "Tarjeta Bruto";
	public static final String COMISION = "Comision";
	public static final String COMISION_OVER = "Comision Over";
	public static final String COMISION_PORC = "Comision Porc.";
	public static final String TIPO_RUTA = "Tipo Ruta (STAD)";
	public static final String AEROLINEA = "Aerolinea";
	public static final String ID_AEROLINEA = "id Aerolinea (TACN)";
	public static final String IMPUESTO_1 = "Impuesto 1 (TAX)";
	public static final String IMPUESTO_2 = "Impuesto 2";
	public static final String IMPUESTO_ACUM = "Impuestos Acumulados";
	public static final String NUMERO_TARJETA = "Numero Tarjeta";
	public static final String TIPO_TARJETA = "Tipo Tarjeta";
	public static final String OBSERVACION = "Observacion";
	public static final String TOTALAPAGAR = "Total A Pagar";
	public static final String TOTAL = "Total";
	public static final String NETO = "Neto";
	public static final String CLIENTE = "Cliente";
	public static final String IDCLIENTE = "Id Cliente";
	public static final String BASE_IMPONIBLE = "Base Imponible";
	public static final String IMP_SOBRE_COMISION = "Imp.sobre Comision";
	public static final String COMISION_INV = "Comision Inverso";
	public static final String COMISION_OVER_INV = "Comision Over Inverso";
	public static final String IMP_SOBRE_COMISION_INV = "Imp.sobre Comision Inverso";
	public static final String COMISION_ACUM = "Comision mas Over";
	public static final String COMISION_ACUM_INV = "Comision mas Over INV";
	public static final String TARIFA_SIN_COMISION = "Tarifa sin comision";
	public static final String MONEDA = "Moneda";
	public static final String CONTRATO = "Contrato";
	public void setFormato(IFormato formato); // optimizacion
	String getStringValue(String field, boolean check)throws Exception;
	Long getLongValue(String field, boolean check)throws Exception;
	Double getDoubleValue(String field, String moneda, boolean check)throws Exception;
	Date getDateValue(String field, boolean check) throws Exception;
	String getSituation(IConciliable other, double precision )throws Exception;
	String getContrato( )throws Exception;
	
}
