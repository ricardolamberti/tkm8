package  pss.common.calendarTools.horarioLaboral;

import java.util.Date;

import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JHour;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;

public class BizHorarioLaboral extends JRecord {

	private JString pCompany = new JString();
	private JLong pIdhorario_laboral = new JLong();
	private JDate pVigenteDesde = new JDate();
	private JDate pVigenteHasta = new JDate();
	private JBoolean pTrabajaLunes = new JBoolean();
	private JBoolean pTrabajaMartes = new JBoolean();
	private JBoolean pTrabajaMiercoles = new JBoolean();
	private JBoolean pTrabajaJueves = new JBoolean();
	private JBoolean pTrabajaViernes = new JBoolean();
	private JBoolean pTrabajaSabado = new JBoolean();
	private JBoolean pTrabajaDomingo = new JBoolean();
	private JHour pHorarioEntrada = new JHour();
	private JHour pHorarioSalida = new JHour();
	private JString pDescripcion = new JString();

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public void setIdhorario_laboral(long zValue) throws Exception {
		pIdhorario_laboral.setValue(zValue);
	}

	public long getIdhorario_laboral() throws Exception {
		return pIdhorario_laboral.getValue();
	}

	public void setVigenteDesde(Date zValue) throws Exception {
		pVigenteDesde.setValue(zValue);
	}

	public Date getVigenteDesde() throws Exception {
		if (pVigenteDesde.isNull()) 
			return null;
		return pVigenteDesde.getValue();
	}

	public void setVigenteHasta(Date zValue) throws Exception {
		pVigenteHasta.setValue(zValue);
	}

	public Date getVigenteHasta() throws Exception {
		if (this.pVigenteHasta.isNull())
			return null;
		return pVigenteHasta.getValue();
	}

	public void setTrabajaLunes(String zValue) throws Exception {
		pTrabajaLunes.setValue(zValue);
	}

	public boolean getTrabajaLunes() throws Exception {
		return pTrabajaLunes.getValue();
	}

	public void setTrabajaMartes(String zValue) throws Exception {
		pTrabajaMartes.setValue(zValue);
	}

	public boolean getTrabajaMartes() throws Exception {
		return pTrabajaMartes.getValue();
	}

	public void setTrabajaMiercoles(String zValue) throws Exception {
		pTrabajaMiercoles.setValue(zValue);
	}

	public boolean getTrabajaMiercoles() throws Exception {
		return pTrabajaMiercoles.getValue();
	}

	public void setTrabajaJueves(String zValue) throws Exception {
		pTrabajaJueves.setValue(zValue);
	}

	public boolean getTrabajaJueves() throws Exception {
		return pTrabajaJueves.getValue();
	}

	public void setTrabajaViernes(String zValue) throws Exception {
		pTrabajaViernes.setValue(zValue);
	}

	public boolean getTrabajaViernes() throws Exception {
		return pTrabajaViernes.getValue();
	}

	public void setTrabajaSabado(String zValue) throws Exception {
		pTrabajaSabado.setValue(zValue);
	}

	public boolean getTrabajaSabado() throws Exception {
		return pTrabajaSabado.getValue();
	}

	public void setTrabajaDomingo(String zValue) throws Exception {
		pTrabajaDomingo.setValue(zValue);
	}

	public boolean getTrabajaDomingo() throws Exception {
		return pTrabajaDomingo.getValue();
	}

	public void setHorarioEntrada(String zValue) throws Exception {
		pHorarioEntrada.setValue(zValue);
	}

	public String getHorarioEntrada() throws Exception {
		return pHorarioEntrada.getValue();
	}

	public int getHoraEntrada() throws Exception {
		return Integer.valueOf(pHorarioEntrada.GetHour()).intValue();
	}

	public int getMinutosEntrada() throws Exception {
		return Integer.valueOf(pHorarioEntrada.GetMinute()).intValue();
	}

	public int getSegundosEntrada() throws Exception {
		return Integer.valueOf(pHorarioEntrada.GetSecond()).intValue();
	}

	public void setHorarioSalida(String zValue) throws Exception {
		pHorarioSalida.setValue(zValue);
	}

	public String getHorarioSalida() throws Exception {
		return pHorarioSalida.getValue();
	}

	public int getHoraSalida() throws Exception {
		return Integer.valueOf(pHorarioSalida.GetHour()).intValue();
	}

	public int getMinutosSalida() throws Exception {
		return Integer.valueOf(pHorarioSalida.GetMinute()).intValue();
	}

	public int getSegundosSalida() throws Exception {
		return Integer.valueOf(pHorarioSalida.GetSecond()).intValue();
	}

	public void setDescripcion(String zValue) throws Exception {
		pDescripcion.setValue(zValue);
	}

	public String getDescripcion() throws Exception {
		return pDescripcion.getValue();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizHorarioLaboral() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("id_horario_laboral", pIdhorario_laboral);
		this.addItem("vigente_desde", pVigenteDesde);
		this.addItem("vigente_hasta", pVigenteHasta);
		this.addItem("trabaja_lunes", pTrabajaLunes);
		this.addItem("trabaja_martes", pTrabajaMartes);
		this.addItem("trabaja_miercoles", pTrabajaMiercoles);
		this.addItem("trabaja_jueves", pTrabajaJueves);
		this.addItem("trabaja_viernes", pTrabajaViernes);
		this.addItem("trabaja_sabado", pTrabajaSabado);
		this.addItem("trabaja_domingo", pTrabajaDomingo);
		this.addItem("horario_entrada", pHorarioEntrada);
		this.addItem("horario_salida", pHorarioSalida);
		this.addItem("descripcion", pDescripcion);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Company", true, true, 15);
		this.addFixedItem(KEY, "id_horario_laboral", "Id horario laboral", true, true, 10);
		this.addFixedItem(FIELD, "vigente_desde", "Vigente desde", true, false, 10);
		this.addFixedItem(FIELD, "vigente_hasta", "Vigente hasta", true, false, 10);
		this.addFixedItem(FIELD, "trabaja_lunes", "Trabaja lunes", true, true, 1);
		this.addFixedItem(FIELD, "trabaja_martes", "Trabaja martes", true, true, 1);
		this.addFixedItem(FIELD, "trabaja_miercoles", "Trabaja miercoles", true, true, 1);
		this.addFixedItem(FIELD, "trabaja_jueves", "Trabaja jueves", true, true, 1);
		this.addFixedItem(FIELD, "trabaja_viernes", "Trabaja viernes", true, true, 1);
		this.addFixedItem(FIELD, "trabaja_sabado", "Trabaja sabado", true, true, 1);
		this.addFixedItem(FIELD, "trabaja_domingo", "Trabaja domingo", true, true, 1);
		this.addFixedItem(FIELD, "horario_entrada", "Horario entrada", true, true, 8);
		this.addFixedItem(FIELD, "horario_salida", "Horario salida", true, true, 8);
		this.addFixedItem(FIELD, "descripcion", "Descripcion", true, true, 200);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "tool_horario_laboral";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(String zCompany, long zIdhorario_laboral) throws Exception {
		addFilter("company", zCompany);
		addFilter("id_horario_laboral", zIdhorario_laboral);
		return read();
	}

	@Override
	public void processInsert() throws Exception {
		if (this.pIdhorario_laboral.isNull()) {
			BizHorarioLaboral oMax = new BizHorarioLaboral();
			oMax.addFilter("company", pCompany.getValue());
			pIdhorario_laboral.setValue(oMax.SelectMaxLong("id_horario_laboral") + 1);
		}

		// * Por ahora lo dejo asi pero la idea es dejarlo en blanco cuando las fechas
		// desde y hasta son para siempre. Hay que tocar tambien la funcionalidad para
		// interpretar correctamente las fechas.
		/*
		if (this.pVigenteDesde.isNull()) {
			Calendar fechaDesde = Calendar.getInstance();
			fechaDesde.clear();
			fechaDesde.set(2009, 00, 01);
			this.pVigenteDesde.setValue(fechaDesde.getTime());
		}

		if (this.pVigenteHasta.isNull()) {
			Calendar fechaHasta = Calendar.getInstance();
			fechaHasta.clear();
			fechaHasta.set(2999, 11, 31);
			this.pVigenteHasta.setValue(fechaHasta.getTime());
		}
		*/

		insertRecord();
	}

	@Override
	public void validateRecord() throws Exception {
		if (this.pVigenteDesde.getValue() != null && this.pVigenteHasta.getValue() != null) {
			if (this.pVigenteHasta.getValue().before(this.pVigenteDesde.getValue())) {
				JExcepcion.SendError("La fecha hasta tiene que ser mayor o igual que la fecha desde");
			}
		}

		if (this.pHorarioEntrada.isNull()) {
			JExcepcion.SendError("La hora de entrada no puede ser vacio");
		}

		if (this.pHorarioSalida.isNull()) {
			JExcepcion.SendError("La hora de Salida no puede ser vacio");
		}
		
		long a = this.pHorarioEntrada.getLongValue();
		long b = this.pHorarioSalida.getLongValue();
		
		if (a >= b ) {
	// 	if (this.pHorarioEntrada.getLongValue() > this.pHorarioSalida.getLongValue()) {
			JExcepcion.SendError("La hora de Salida tiene que ser mayor que la hora de entrada");
		}
		
		if ((b-a) < 10000) {
			JExcepcion.SendError("La unidad minima de horario laboral es de una hora. Entre la hora de entrada y de salida debe haber al menos una hora de diferencia.");
		}
		
		super.validateRecord();
	}
}
