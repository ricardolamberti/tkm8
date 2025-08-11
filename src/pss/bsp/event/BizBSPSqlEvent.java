package pss.bsp.event;

import java.util.Calendar;
import java.util.Date;

import pss.bsp.bspBusiness.BSPService;
import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.event.sql.BizSqlEvent;
import pss.common.event.sql.GuiSqlEvent;
import pss.common.event.sql.datos.BizSqlEventDato;
import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.JExec;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWins;
import pss.tourism.pnr.BizPNRTicket;

public class BizBSPSqlEvent extends BizSqlEvent {

	public BizBSPSqlEvent() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setEstado(String zValue) throws Exception {
		pEstado.setValue(zValue);
		if (zValue.equals(BizSqlEvent.REPROCESAR)) {
			setActivo(true);
			BSPService.addBandera(pCompany.getValue());
		}
	}

	// public double getValor(Calendar fechaDesde,Calendar fecha, String
	// extraCondicion) throws Exception {
	// if (getConsulta().equals("")) return 0;
	// if (fecha==null && extraCondicion==null) {
	// String sql = JTools.replace(getConsulta(), "::FECHA::", "");
	// if (BizBSPCompany.getObjBSPCompany(
	// BizUsuario.getUsr().getCompany()).isDependant())
	// sql = JTools.replace(sql, "::DK::", " AND
	// (customer_id_reducido='"+BizBSPCompany.getObjBSPCompany(
	// BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
	// else
	// sql = JTools.replace(sql, "::DK::", "");
	// return this.getSqlValue(sql,getCampo());
	// }
	//
	// String sql = "";
	// if (fecha!=null) {
	// if (getConsulta().toUpperCase().indexOf("::FECHA::")!=-1) {
	// if (getConsulta().toUpperCase().indexOf("TUR_PNR_BOOKING")!=-1) {
	// sql = ((extraCondicion!=null)?extraCondicion+"
	// ":"")+JTools.replace(getConsulta(), "::FECHA::", " AND fecha_emision<= '" +
	// JDateTools.DateToString(fecha.getTime()) +"' ");
	// } else {
	// sql = ((extraCondicion!=null)?extraCondicion+"
	// ":"")+JTools.replace(getConsulta(), "::FECHA::", " AND creation_date<= '" +
	// JDateTools.DateToString(fecha.getTime()) +"' ");
	//
	// }
	// } else {
	// if (extraCondicion!=null) {
	// sql = extraCondicion;
	// } else {
	// if (getConsulta().toUpperCase().indexOf("TUR_PNR_BOOKING")!=-1) {
	// sql = "with ";
	// sql += "TUR_PNR_BOOKING as (";
	// sql+= " Select * from TUR_PNR_BOOKING where fecha_emision<= '" +
	// JDateTools.DateToString(fecha.getTime()) +"' ";
	// if (BizBSPCompany.getObjBSPCompany(
	// BizUsuario.getUsr().getCompany()).isDependant()) {
	// sql += " and company in ("+ BizBSPCompany.getObjBSPCompany(
	// BizUsuario.getUsr().getCompany()).getTicketCompany()+") "
	// + " and ( customer_id_reducido = '"+BizBSPCompany.getObjBSPCompany(
	// BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ";
	// } else
	// sql += " and company='"+getCompany()+"' ";//,";
	// sql += " )";//,";
	//
	// } else {
	// sql = "with ";
	// sql += "TUR_PNR_BOLETO as (";
	// sql += " Select * from TUR_PNR_BOLETO where creation_date<= '" +
	// JDateTools.DateToString(fecha.getTime()) +"' ";
	// if (BizBSPCompany.getObjBSPCompany(
	// BizUsuario.getUsr().getCompany()).isDependant()) {
	// sql += " and company in ("+ BizBSPCompany.getObjBSPCompany(
	// BizUsuario.getUsr().getCompany()).getTicketCompany()+") ";
	// sql += " and ( customer_id_reducido = '"+BizBSPCompany.getObjBSPCompany(
	// BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ";
	// } else
	// sql += " and company='"+getCompany()+"' ";
	// sql += " )";
	//
	// }
	// }
	// sql += getConsulta();
	// }
	// } else {
	// if (getConsulta().toUpperCase().indexOf("::FECHA::")!=-1) {
	// sql = ((extraCondicion!=null)?extraCondicion+"
	// ":"")+JTools.replace(getConsulta(), "::FECHA::", "");
	// } else {
	// sql += getConsulta();
	//
	// }
	// }
	// sql=JTools.replace(sql, "now()",
	// "'"+JDateTools.DateToString(fecha.getTime())+"'::date ");
	// Calendar ayer = Calendar.getInstance();
	// ayer.setTime(fecha.getTime());
	// ayer.add(Calendar.DAY_OF_MONTH, -1);
	// sql=JTools.replace(sql, "'yesterday'",
	// "'"+JDateTools.DateToString(ayer.getTime())+"'::date ");
	// Calendar maniana = Calendar.getInstance();
	// maniana.setTime(fecha.getTime());
	// maniana.add(Calendar.DAY_OF_MONTH, 1);
	// sql=JTools.replace(sql, "'tomorrow'",
	// "'"+JDateTools.DateToString(maniana.getTime())+"'::date ");
	// if (BizBSPCompany.getObjBSPCompany(
	// BizUsuario.getUsr().getCompany()).isDependant())
	// sql = JTools.replace(sql, "::DK::", " AND
	// (customer_id_reducido='"+BizBSPCompany.getObjBSPCompany(
	// BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
	// else
	// sql = JTools.replace(sql, "::DK::", "");
	// double valor = this.getSqlValue(sql, getCampo());
	// return valor;
	// }

	public double getValor(Calendar fechaDesde, Calendar fecha, String extraCondicion) throws Exception {
		if (getConsulta().equals(""))
			return 0;

		String sql = "";

		if (fecha == null && fechaDesde == null && extraCondicion == null) {
			sql = JTools.replace(getConsulta(), "::FECHA::", "");
			if (BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isDependant())
				sql = JTools.replace(sql, "::DK::", " AND (customer_id_reducido='" + BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getCodigoCliente() + "') ");
			else
				sql = JTools.replace(sql, "::DK::", "");
			return this.getSqlValue(sql, getCampo());
		}

		if ((fecha != null || fechaDesde != null)) {
			if (getConsulta().toUpperCase().contains("::FECHA::")) {
				String fechaFiltro = "";
				if (getConsulta().indexOf("ES_BOOKING") != -1) {
					if (getConsulta().indexOf("ES_VOLADO") != -1) {
//						if (fechaDesde != null) {
//							fechaFiltro += " AND FechaDespegue >= '" + JDateTools.DateToString(fechaDesde.getTime()) + "' ";
//						}
//						if (fecha != null) {
//							fechaFiltro += " AND FechaDespegue <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
//						}
						if (fecha != null) {
							fechaFiltro += " AND fecha_emision <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
						}
					} else {
						if (fechaDesde != null) {
							fechaFiltro += " AND fecha_emision >= '" + JDateTools.DateToString(fechaDesde.getTime()) + "' ";
						}
						if (fecha != null) {
							fechaFiltro += " AND fecha_emision <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
						}

					}
				} else {
					if (getConsulta().indexOf("ES_VOLADO") != -1) {
//						if (fechaDesde != null) {
//							fechaFiltro += " AND departure_date >= '" + JDateTools.DateToString(fechaDesde.getTime()) + "' ";
//						}
//						if (fecha != null) {
//							fechaFiltro += " AND departure_date <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
//						}
						if (fecha != null) {
							fechaFiltro += " AND creation_date <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
						}
					} else {
						if (fechaDesde != null) {
							fechaFiltro += " AND creation_date >= '" + JDateTools.DateToString(fechaDesde.getTime()) + "' ";
						}
						if (fecha != null) {
							fechaFiltro += " AND creation_date <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
						}
					}
				}
				sql = ((extraCondicion != null) ? extraCondicion + " " : "") + JTools.replace(getConsulta(), "::FECHA::", fechaFiltro);

			} else {
				if (extraCondicion != null) {
					sql = extraCondicion;
				} else {
					if (getConsulta().toUpperCase().contains("TUR_PNR_BOOKING")) {
						sql = "with TUR_PNR_BOOKING as (";
						sql += " Select * from TUR_PNR_BOOKING where 1=1 ";
						if (fechaDesde != null) {
							sql += " AND fecha_emision >= '" + JDateTools.DateToString(fechaDesde.getTime()) + "' ";
						}
						if (fecha != null) {
							sql += " AND fecha_emision <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
						}
						if (BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isDependant()) {
							sql += " and company in (" + BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getTicketCompany() + ") ";
							sql += " and (customer_id_reducido = '" + BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getCodigoCliente() + "') ";
						} else {
							sql += " and company='" + getCompany() + "' ";
						}
						sql += " )";
					} else {
						sql = "with TUR_PNR_BOLETO as (";
						sql += " Select * from TUR_PNR_BOLETO where 1=1 ";
						if (fechaDesde != null) {
							sql += " AND creation_date >= '" + JDateTools.DateToString(fechaDesde.getTime()) + "' ";
						}
						if (fecha != null) {
							sql += " AND creation_date <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
						}
						if (BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isDependant()) {
							sql += " and company in (" + BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getTicketCompany() + ") ";
							sql += " and (customer_id_reducido = '" + BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getCodigoCliente() + "') ";
						} else {
							sql += " and company='" + getCompany() + "' ";
						}
						sql += " )";
					}
				}
				sql += getConsulta();
			}
		} else {

			if (getConsulta().toUpperCase().contains("::FECHA::")) {
				sql = ((extraCondicion != null) ? extraCondicion + " " : "") + JTools.replace(getConsulta(), "::FECHA::", "");
			} else {
				sql += getConsulta();
			}
		}

		sql = JTools.replace(sql, "now()", "'" + JDateTools.DateToString(fecha.getTime()) + "'::date ");
		Calendar ayer = Calendar.getInstance();
		ayer.setTime(fecha.getTime());
		ayer.add(Calendar.DAY_OF_MONTH, -1);
		sql = JTools.replace(sql, "'yesterday'", "'" + JDateTools.DateToString(ayer.getTime()) + "'::date ");
		Calendar manana = Calendar.getInstance();
		manana.setTime(fecha.getTime());
		manana.add(Calendar.DAY_OF_MONTH, 1);
		sql = JTools.replace(sql, "'tomorrow'", "'" + JDateTools.DateToString(manana.getTime()) + "'::date ");

		if (BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isDependant())
			sql = JTools.replace(sql, "::DK::", " AND (customer_id_reducido='" + BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getCodigoCliente() + "') ");
		else
			sql = JTools.replace(sql, "::DK::", "");

		double valor = this.getSqlValue(sql, getCampo());
		return valor;
	}

	public String getSQLDetalles(Calendar fechaDesde, Calendar fecha, String extraCondition) throws Exception {
		if (getClassDetalle() == null)
			throw new Exception("No se puede visualizar detalles de consultas basadas en SQL");
		String sql = this.getConsultaDetalle();
		if (fechaDesde != null) {
			String fechaFiltro = "";
			if (sql.indexOf("ES_BOOKING") != -1 && sql.indexOf("ES_VOLADO") != -1) {
//				if (fechaDesde != null) {
//					fechaFiltro += " AND fechadespegue >= '" + JDateTools.DateToString(fechaDesde.getTime()) + "' ";
//				}
//				if (fecha != null) {
//					fechaFiltro += " AND fechadespegue <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
//				}
				if (fecha != null) {
					fechaFiltro += " AND creation_date <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
				}
			} else if (sql.indexOf("ES_BOOKING") == -1 && sql.indexOf("ES_VOLADO") != -1) {
//				if (fechaDesde != null) {
//					fechaFiltro += " AND departure_date >= '" + JDateTools.DateToString(fechaDesde.getTime()) + "' ";
//				}
//				if (fecha != null) {
//					fechaFiltro += " AND departure_date <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
//				}
				if (fecha != null) {
					fechaFiltro += " AND creation_date <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
				}
			} else {
				if (fechaDesde != null) {
					fechaFiltro += " AND creation_date >= '" + JDateTools.DateToString(fechaDesde.getTime()) + "' ";
				}
				if (fecha != null) {
					fechaFiltro += " AND creation_date <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
				}

			}
			sql = JTools.replace(sql, "::FECHA::", fechaFiltro + " " + extraCondition);
		} else
			sql = JTools.replace(sql, "::FECHA::", extraCondition);
		if (BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isDependant())
			sql = JTools.replace(sql, "::DK::", " AND (customer_id_reducido='" + BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getCodigoCliente() + "') ");
		else
			sql = JTools.replace(sql, "::DK::", "");

		if (fecha != null) {
			sql = JTools.replace(sql, "now()", "'" + JDateTools.DateToString(fecha.getTime()) + "'::date ");
			Calendar ayer = Calendar.getInstance();
			ayer.setTime(fecha.getTime());
			ayer.add(Calendar.DAY_OF_MONTH, -1);
			sql = JTools.replace(sql, "'yesterday'", "'" + JDateTools.DateToString(ayer.getTime()) + "'::date ");
			Calendar maniana = Calendar.getInstance();
			maniana.setTime(fecha.getTime());
			maniana.add(Calendar.DAY_OF_MONTH, 1);
			sql = JTools.replace(sql, "'tomorrow'", "'" + JDateTools.DateToString(maniana.getTime()) + "'::date ");

		}
		return sql;
	}

	// public double getAcumulado(Calendar fecha, String campo) throws Exception {
	// String sql ="";
	// if (fecha!=null) {
	// sql = "with ";
	// if (BizBSPCompany.getObjBSPCompany(
	// BizUsuario.getUsr().getCompany()).isDependant()) {
	// sql += "TUR_PNR_BOLETO as (Select * from TUR_PNR_BOLETO where
	// creation_date<= '" + JDateTools.DateToString(fecha.getTime()) ;
	// sql += " and company in ("+ BizBSPCompany.getObjBSPCompany(
	// BizUsuario.getUsr().getCompany()).getTicketCompany()+") ";
	// sql += " and ( customer_id_reducido = '"+BizBSPCompany.getObjBSPCompany(
	// BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') )";
	// } else
	// sql += "TUR_PNR_BOLETO as (Select * from TUR_PNR_BOLETO where
	// creation_date<= '" + JDateTools.DateToString(fecha.getTime()) + "' and
	// company='"+getCompany()+"')";//,";
	//
	// // sql += "TUR_PNR_SEGMENTOAEREO as (Select TUR_PNR_SEGMENTOAEREO.* from
	// TUR_PNR_SEGMENTOAEREO JOIN TUR_PNR_BOLETO ON
	// TUR_PNR_SEGMENTOAEREO.interface_id=TUR_PNR_BOLETO.interface_id),";
	//// sql += "TUR_PNR_IMPUESTOS as (Select TUR_PNR_IMPUESTOS.* from
	// TUR_PNR_IMPUESTOS JOIN TUR_PNR_BOLETO ON
	// TUR_PNR_IMPUESTOS.interface_id=TUR_PNR_BOLETO.interface_id)";
	// }
	// sql += getConsultaDetalle();
	// sql=JTools.replace(sql, "now()",
	// "'"+JDateTools.DateToString(fecha.getTime())+"'::date ");
	// Calendar ayer = Calendar.getInstance();
	// ayer.setTime(fecha.getTime());
	// ayer.add(Calendar.DAY_OF_MONTH, -1);
	// sql=JTools.replace(sql, "'yesterday'",
	// "'"+JDateTools.DateToString(ayer.getTime())+"'::date ");
	// Calendar maniana = Calendar.getInstance();
	// maniana.setTime(fecha.getTime());
	// maniana.add(Calendar.DAY_OF_MONTH, 1);
	// sql=JTools.replace(sql, "'tomorrow'",
	// "'"+JDateTools.DateToString(maniana.getTime())+"'::date ");
	// JWins wins= (JWins) Class.forName(getClassDetalle()).newInstance();
	//
	// JRecords s = new JRecords(wins.getWinRef().getRecord().getClass());
	// s.SetSQL(sql);
	// return s.selectSum(campo);
	//
	// }
	public double getAcumulado(Calendar fechaDesde, Calendar fecha, String campo) throws Exception {
		String sql = "";
		if (fecha != null || fechaDesde != null) {
			sql = "with ";
			if (BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isDependant()) {
				sql += "TUR_PNR_BOLETO as (Select * from TUR_PNR_BOLETO where 1=1 ";
				if (fechaDesde != null) {
					sql += " AND creation_date >= '" + JDateTools.DateToString(fechaDesde.getTime()) + "' ";
				}
				if (fecha != null) {
					sql += " AND creation_date <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
				}
				sql += " and company in (" + BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getTicketCompany() + ") ";
				sql += " and (customer_id_reducido = '" + BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getCodigoCliente() + "') )";
			} else {
				sql += "TUR_PNR_BOLETO as (Select * from TUR_PNR_BOLETO where 1=1 ";
				if (fechaDesde != null) {
					sql += " AND creation_date >= '" + JDateTools.DateToString(fechaDesde.getTime()) + "' ";
				}
				if (fecha != null) {
					sql += " AND creation_date <= '" + JDateTools.DateToString(fecha.getTime()) + "' ";
				}
				sql += " and company='" + getCompany() + "')";
			}
		}
		sql += getConsultaDetalle();
		sql = JTools.replace(sql, "now()", "'" + JDateTools.DateToString(fecha.getTime()) + "'::date ");
		Calendar ayer = Calendar.getInstance();
		ayer.setTime(fecha.getTime());
		ayer.add(Calendar.DAY_OF_MONTH, -1);
		sql = JTools.replace(sql, "'yesterday'", "'" + JDateTools.DateToString(ayer.getTime()) + "'::date ");
		Calendar manana = Calendar.getInstance();
		manana.setTime(fecha.getTime());
		manana.add(Calendar.DAY_OF_MONTH, 1);
		sql = JTools.replace(sql, "'tomorrow'", "'" + JDateTools.DateToString(manana.getTime()) + "'::date ");
		JWins wins = (JWins) Class.forName(getClassDetalle()).newInstance();

		JRecords s = new JRecords(wins.getWinRef().getRecord().getClass());
		s.SetSQL(sql);
		return s.selectSum(campo);
	}

	protected void processUpdateDatosInternal() throws Exception {
		boolean updated = false;
		BizSqlEventDato datoUltimo = new BizSqlEventDato();
		datoUltimo.addFilter("id_evento", getId());
		Date fechaUltimoDato = datoUltimo.SelectMaxDate("fecha");
		if (fechaUltimoDato == null) {
			PssLogger.logInfo("log point");
			fechaUltimoDato = JDateTools.getDateStartDay(new Date());
		}
		if (fechaUltimoDato.after(new Date()))
			fechaUltimoDato = JDateTools.getFirstDayOfYear(new Date());

		BizPNRTicket ultimoTicket = new BizPNRTicket();
		ultimoTicket.addFilter("company", getCompany());
		Date fechaUltimoPNR = ultimoTicket.SelectMaxDate("creation_date");
		if (fechaUltimoPNR.after(new Date()))
			fechaUltimoPNR = JDateTools.getDateStartDay(new Date());
		if (fechaUltimoPNR == null)
			return;

		Calendar fdesde = Calendar.getInstance();
		fdesde.setTime(fechaUltimoDato != null ? fechaUltimoDato : JDateTools.getFirstDayOfYear(new Date()));
		Calendar fhasta = Calendar.getInstance();
		fhasta.setTime(fechaUltimoPNR);
		for (int day = 1; fdesde.before(fhasta); day++) {
			updated |= agregarASerie(true, fdesde, false, null) != null;
			fdesde.add(Calendar.DAY_OF_YEAR, 1);
		}
		updated |= agregarASerie(true, fhasta, true, null) != null;// siempre que se
																																// ejecute una
																																// vez, para
																																// actualizar el
																																// dia actual

		if (updated) {
			BizDetalle.processRePopulate(getCompany(), getId(), fechaUltimoPNR, true);
		}
		setFechaUpdate(new Date());
		update();

	}

	public void processPopulate(Date fechaDesde, Date fechaHasta) throws Exception {
		super.processPopulate(fechaDesde, fechaHasta);
	}

	@Override
	public void processUpdate() throws Exception {
		fillSqlAnalisisFromCustomList();
		super.processUpdate();
	}

	@Override
	protected void internalServiceProcessPopulate(Date fechaDesde, Date fechaHasta) throws Exception {
		Calendar fechaInicial = Calendar.getInstance();
		if (fechaHasta == null) {
			BizPNRTicket ultimoTicket = new BizPNRTicket();
			ultimoTicket.addFilter("company", getCompany());
			Date fechaUltimoPNR = ultimoTicket.SelectMaxDate("creation_date");

			if (fechaUltimoPNR != null)
				fechaHasta = fechaUltimoPNR;

			if (fechaHasta == null)
				fechaHasta = new Date();
			else if (fechaHasta.after(new Date()))
				fechaHasta = new Date();// sucede cuando hay archivos mal parseados
		}

		Calendar fdesde = Calendar.getInstance();
		Calendar fhasta = Calendar.getInstance();
		fhasta.setTime(fechaHasta);
		if (fechaDesde == null) {
			BizPNRTicket primerTicket = new BizPNRTicket();
			primerTicket.addFilter("company", getCompany());
			Date fechaI = primerTicket.SelectMinDate("creation_date");

			if (fechaI == null)
				fechaI = new Date();
			Calendar unoAtras = Calendar.getInstance();
			unoAtras.setTime(new Date());
			unoAtras.add(Calendar.YEAR, -1);

			fechaInicial.setTime(fechaI);

			if (fechaInicial.before(unoAtras))
				fechaInicial = unoAtras;

			fdesde = fechaInicial;
		} else
			fdesde.setTime(fechaDesde);
		fechaDesde = fdesde.getTime();
		super.internalServiceProcessPopulate(fechaDesde, fechaHasta);
		BizDetalle.processRePopulate(this.getCompany(), this.getId(), fechaHasta, false);
	}

	@Override
	public void processDelete() throws Exception {
		BizSqlEvent detS = new BizSqlEvent();// error de paquete
		detS.dontThrowException(true);
		detS.addFilter("system_protect", getId());
		if (detS.read())
			throw new Exception("Elimine primero la referencia en hijos: " + detS.getDescripcion());

		BizDetalle det = new BizDetalle();// error de paquete
		det.dontThrowException(true);
		det.addFilter("variable", getId());
		if (det.read())
			throw new Exception("Elimine primero la referencia en Contrato: " + det.getObjContrato().getDescripcion());

		det = new BizDetalle();// error de paquete
		det.dontThrowException(true);
		det.addFilter("variable_ganancia", getId());
		if (det.read())
			throw new Exception("Elimine primero la referencia en Contrato: " + det.getObjContrato().getDescripcion());

		det = new BizDetalle();// error de paquete
		det.dontThrowException(true);
		det.addFilter("variable_aux", getId());
		if (det.read())
			throw new Exception("Elimine primero la referencia en Contrato: " + det.getObjContrato().getDescripcion());

		super.processDelete();
	}

	public void fillSqlAnalisisFromCustomList() throws Exception {
		BizCustomList biz = this.getObjCustomList();
		if (biz == null)
			return;
		if (getObjCampo() == null)
			return;
		JWins wins = (JWins) Class.forName(biz.getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()).newInstance();
		biz.clean();
		JRecords r = wins.getRecords();

		String name = getCampoVirtual();
		if (name.startsWith("VIRTUAL:")) {
			// setConsultaDetalle2(null);
			return;
		}
		biz.getLogica().setWithgeo(false);
		name = biz.getLogica().prepareOneFields(getObjCampo(), r, getSqlTotalize(getObjCampo(), getEjesValor(), getFiltrosValor()), null);
		biz.getLogica().prepareTables(wins.getRecords());
		biz.getLogica().prepareJoinsDetail(wins.getRecords(), false);
		biz.getLogica().prepareFilters(wins.getRecords(), false);
		biz.getLogica().prepareFiltersDetail(wins.getRecords(), getEjesValor(), getFiltrosValor(), false);
		biz.getLogica().prepareLiteralFilters(wins.getRecords());
		r.addField("TUR_PNR_BOLETO", "market", "");
		r.addGroupBy("TUR_PNR_BOLETO", "market");

		JBaseRegistro reg = JBaseRegistro.recordsetFactory(r);
		reg.setDistinct(true);

		String sql = reg.ArmarSelect();
		// setConsultaDetalle2(sql);
		// setClassDetalle2(biz.getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass());
		// setConsulta(sql);
		//
		// setCustomList(biz.getListId());
	}

	public void execProcessInactivar() throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				processInactivar();
			}
		};
		oExec.execute();
	}

	public void processInactivar() throws Exception {
		boolean active = false;// no anda bien
		BizDetalle det = new BizDetalle();// error de paquete
		det.addFilter("variable", this.getId());
		det.addJoin(JRelations.JOIN, "BSP_CONTRATO", "BSP_CONTRATO.id=bsp_contrato_detalle.id");
		det.dontThrowException(true);
		if (det.read())
			active |= true;

		det = new BizDetalle();// error de paquete
		det.dontThrowException(true);
		det.addFilter("variable_ganancia", this.getId());
		det.addJoin(JRelations.JOIN, "BSP_CONTRATO", "BSP_CONTRATO.id=bsp_contrato_detalle.id");
		det.dontThrowException(true);
		if (det.read())
			active |= true;

		det = new BizDetalle();// error de paquete
		det.dontThrowException(true);
		det.addFilter("variable_aux", this.getId());
		det.addJoin(JRelations.JOIN, "BSP_CONTRATO", "BSP_CONTRATO.id=bsp_contrato_detalle.id");
		det.dontThrowException(true);
		if (det.read())
			active |= true;

		if (!active && this.isActivo()) {
			this.setActivo(false);
			this.processUpdate();
		} else {
			if (active && !this.isActivo()) {
				this.setActivo(true);
				this.processUpdate();
			}

		}

	}
}
