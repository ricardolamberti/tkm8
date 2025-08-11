package  pss.common.calendarTools;

import java.util.Calendar;
import java.util.Date;

import pss.common.calendarTools.diasNoLaborales.BizDiaNoLaborable;
import pss.common.calendarTools.horarioLaboral.BizHorarioLaboral;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;

public class calendarTools {
	private static Date pBeginNextWorkingDay = null;
	private static Date pEndNextWorkingDay = null;
	private static String pCompany = new String("");
	private JRecords<BizDiaNoLaborable> noWorkingDays = null;
	private JRecords<BizHorarioLaboral> workingDateTime = null;

	public void clear() {
		pBeginNextWorkingDay = null;
		pEndNextWorkingDay = null;
		noWorkingDays = null;
		workingDateTime = null;
	}

	public String getCompany() {
		return pCompany;
	}

	public void setCompany(String zCompany) {
		pCompany = zCompany;
	}

	public Date getNextWorkingDay(String zCompany) throws Exception {
		this.setCompany(zCompany);
		return getNextWorkingDay();
	}

	public Date getNextWorkingDay() throws Exception {
		Date oDateNow = Calendar.getInstance().getTime();
		return this.getNextWorkingDay(oDateNow);
	}
	
	
	public Date getNextWorkingDay(Date zDateNow) throws Exception {
		if (calendarTools.pCompany.isEmpty()) {
			pCompany = new String("DEFAULT");
		}

		if (calendarTools.pBeginNextWorkingDay == null || calendarTools.pEndNextWorkingDay == null || 
				calendarTools.pEndNextWorkingDay.before(zDateNow) ||
				calendarTools.pBeginNextWorkingDay.after(zDateNow)) {
			searchNextWorkingDay();
		}

		if (pBeginNextWorkingDay.before(zDateNow)) {
			return zDateNow;
		}

		return pBeginNextWorkingDay;
	}

	public void searchNextWorkingDay() throws Exception {
		loadNoWorkingDays();
		loadWorkingTime();
		Date oLastWorkingDay;
		Date oNextWorkingDay;

		if (calendarTools.pEndNextWorkingDay == null) {
			Calendar auxCalendar = Calendar.getInstance();
			auxCalendar.roll(Calendar.DAY_OF_YEAR, -1);

			oLastWorkingDay = auxCalendar.getTime();
		} else {
			oLastWorkingDay = pEndNextWorkingDay;
		}

		while (true) {
			oNextWorkingDay = getNextWorkingDateTime(oLastWorkingDay);

			if (oNextWorkingDay.after(oLastWorkingDay) == false) {
				JExcepcion.SendError("Error calculando proximo dia laborable. Deadlock Detectado");
			}

			if (isThisDayLaborable(oNextWorkingDay)) {
				break;
			}

			oLastWorkingDay = oNextWorkingDay;
		} // end while

		this.noWorkingDays.closeRecord();
		this.workingDateTime.closeRecord();

		return;
	}

	private void loadNoWorkingDays() throws Exception {
		if (this.noWorkingDays == null) {
			noWorkingDays = new JRecords<BizDiaNoLaborable>(BizDiaNoLaborable.class);
		} else {
			noWorkingDays.clearFields();
			noWorkingDays.clearFilters();
		}

		noWorkingDays.addFilter("company", this.pCompany);
		noWorkingDays.endStatic();
		noWorkingDays.readAll();
		noWorkingDays.toStatic();

	}

	private void loadWorkingTime() throws Exception {
		if (this.workingDateTime == null) {
			workingDateTime = new JRecords<BizHorarioLaboral>(BizHorarioLaboral.class);
		} else {
			workingDateTime.clearFields();
			workingDateTime.clearFilters();
		}

		Date dateNow = Calendar.getInstance().getTime();

		workingDateTime.addFilter("company", this.pCompany);
		// workingDateTime.addFilter("vigente_desde", dateNow, "<=");
		// workingDateTime.addFilter("vigente_hasta", dateNow, ">=");
		workingDateTime.addOrderBy("vigente_desde", "DESC");
		workingDateTime.endStatic();
		workingDateTime.readAll();
		workingDateTime.toStatic();
	}

	private Date getNextWorkingDateTime(Date zNextWorkingDay) throws Exception {
		Calendar dateAux = Calendar.getInstance();
		boolean ifTodayIsWorkingDay = false;

		dateAux.clear();
		dateAux.setTime(zNextWorkingDay);

		boolean getNextWorkingDayByDefault = (this.workingDateTime.ifRecordFound() == false);

		while (true) {

			dateAux.add(Calendar.DAY_OF_YEAR, 1);

			// Si no hay nada configurado asumo que se trabaja de 9 a 18 los dias de semana
			if (getNextWorkingDayByDefault) {

				switch (dateAux.get(Calendar.DAY_OF_WEEK)) {
				case Calendar.MONDAY:
				case Calendar.TUESDAY:
				case Calendar.WEDNESDAY:
				case Calendar.FRIDAY:
				case Calendar.THURSDAY:
					ifTodayIsWorkingDay = true;
					break;
				} // end switch

				if (ifTodayIsWorkingDay == false) {
					continue;
				}

				dateAux.set(Calendar.HOUR_OF_DAY, 9);
				dateAux.set(Calendar.MINUTE, 0);
				dateAux.set(Calendar.SECOND, 0);
				dateAux.set(Calendar.MILLISECOND, 0);
				calendarTools.pBeginNextWorkingDay = dateAux.getTime();

				dateAux.set(Calendar.HOUR_OF_DAY, 18);
				this.pEndNextWorkingDay = dateAux.getTime();
				break; // Salgo del while(true)
			} // end if

			// Si llego a este punto es porque existen registros con hora configurada
			BizHorarioLaboral workingTime = null;
			this.workingDateTime.firstRecord();

			while (this.workingDateTime.nextRecord()) {
				workingTime = this.workingDateTime.getRecord();

				if (workingTime.getVigenteDesde() != null) {
					if (workingTime.getVigenteDesde().after(dateAux.getTime())) {
						break;
					}
				}

				if (workingTime.getVigenteHasta() != null) {
					if (workingTime.getVigenteHasta().before(dateAux.getTime())) {
						continue;
					}
				}

				switch (dateAux.get(Calendar.DAY_OF_WEEK)) {
				case Calendar.MONDAY:
					ifTodayIsWorkingDay = workingTime.getTrabajaLunes();
					break;
				case Calendar.TUESDAY:
					ifTodayIsWorkingDay = workingTime.getTrabajaMartes();
					break;
				case Calendar.WEDNESDAY:
					ifTodayIsWorkingDay = workingTime.getTrabajaMiercoles();
					break;
				case Calendar.FRIDAY:
					ifTodayIsWorkingDay = workingTime.getTrabajaViernes();
					break;
				case Calendar.THURSDAY:
					ifTodayIsWorkingDay = workingTime.getTrabajaJueves();
					break;
				case Calendar.SATURDAY:
					ifTodayIsWorkingDay = workingTime.getTrabajaSabado();
					break;
				case Calendar.SUNDAY:
					ifTodayIsWorkingDay = workingTime.getTrabajaDomingo();
					break;
				} // end switch

				if (ifTodayIsWorkingDay) {
					break;
				}
			} // end while

			if (ifTodayIsWorkingDay == false) {
				continue;
			}

			dateAux.set(Calendar.HOUR_OF_DAY, workingTime.getHoraEntrada());
			dateAux.set(Calendar.MINUTE, workingTime.getMinutosEntrada());
			dateAux.set(Calendar.SECOND, workingTime.getSegundosEntrada());
			this.pBeginNextWorkingDay = dateAux.getTime();

			dateAux.set(Calendar.HOUR_OF_DAY, workingTime.getHoraSalida());
			dateAux.set(Calendar.MINUTE, workingTime.getMinutosSalida());
			dateAux.set(Calendar.SECOND, workingTime.getSegundosSalida());
			this.pEndNextWorkingDay = dateAux.getTime();

			break;
		} // end while

		return pEndNextWorkingDay;
	}

	public boolean isThisDayLaborable(Date oNextWorkingDay) throws Exception {
		if (this.noWorkingDays.ifRecordFound() == false) {
			return true;
		}

		BizDiaNoLaborable oDiaNoLaborable = null;

		this.noWorkingDays.firstRecord();
		while (this.noWorkingDays.nextRecord()) {
			oDiaNoLaborable = this.noWorkingDays.getRecord();

			if (oDiaNoLaborable.ifDiaNoLaborable(oNextWorkingDay)) {
				return false;
			}
		} // end while

		return true;
	}

}
