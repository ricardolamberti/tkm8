package pss.common.issueTracker.reportes ;

import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;


public class BizIssueSummaries extends JRecords<BizIssueSummary>  {

	
	public BizIssueSummaries() {
	}

	@Override
	public String GetTable() {return ""; }
	@Override
	public Class<BizIssueSummary> getBasedClass() { return BizIssueSummary.class;}
	
	
	public boolean readAll() throws Exception {
		this.endStatic();
		this.setStatic(true);
		JBaseRegistro regs=JBaseRegistro.recordsetFactory();
	  
		String fdesde = this.getFilterValue("fechadesde") ;
		String fhasta = this.getFilterValue("fechahasta") ;
		String sUsuario = this.getFilterValue("usuario");
		String sCompany = this.getFilterValue("company");
		String sStatus = this.getFilterValue("status");
		String sEvento = this.getFilterValue("evento");
		String sPeriodo =this.getFilterValue("periodo");

		boolean agrupaUsr ="S".equals(this.getFilterValue("agrupa_usuario"));
		boolean agrupaEvento ="S".equals(this.getFilterValue("agrupa_evento"));
		boolean agrupaStatus ="S".equals(this.getFilterValue("agrupa_status"));
		boolean agrupaCompany ="S".equals(this.getFilterValue("agrupa_company"));

		String sGrupoFecha=null;//sPeriodo==null? null:BizTransSumary.getCampoPeriodo(sPeriodo, "date_submitted_plain");
		boolean hasGroups = false;
		
		if(agrupaUsr||agrupaEvento||agrupaStatus||agrupaCompany||sPeriodo!=null)
			hasGroups=true;

		
		// VENTAS 
		String SQL=" Select count(*) cantidad "+
				(agrupaCompany?", it.company ":"")+
				(agrupaUsr?", it.usuario ":"")+
				(agrupaStatus?", it.status ":"")+
				(sPeriodo!=null?", "+sGrupoFecha+" periodo":"");
		
		SQL += " from issue_track it ";
		SQL += " where 1 = 1";
		if(fdesde!=null) SQL += " and it.date_submitted_plain  >= '"+ fdesde.trim()+"'";
		if(fhasta!=null) SQL += " and it.date_submitted_plain  <= '"+ fhasta.trim()+"'";
		if(sUsuario != null ) SQL += " and it.usuario = '" + sUsuario + "' " ;
		if(sCompany != null ) SQL += " and it.company = '" + sCompany + "' " ;
		//if(sEvento != null ) SQL += " and tf.file_group="+sEvento+" " ;
		if (hasGroups) {
			SQL += " group by  "+
					(agrupaCompany?" it.company,":"")+ 
					(agrupaUsr?"it.usuario,":"")+
					(agrupaStatus?"it.status,":"")+
					(sPeriodo!=null?""+sGrupoFecha:"");
			if (SQL.endsWith(","))
				SQL = JTools.Replace(SQL, " ", SQL.length()-1);
			SQL += " order by "+ 
					(agrupaCompany?" it.company,":"")+
					(agrupaUsr?"it.usuario,":"")+
					(agrupaStatus?"it.status,":"")+
					(sPeriodo!=null?sGrupoFecha:"");
			if (SQL.endsWith(","))
				SQL = JTools.Replace(SQL, " ", SQL.length()-1);
		}
		regs.ExecuteQuery(SQL);
		regs.first();
		while (regs.next()) {
			BizIssueSummary recCom = new BizIssueSummary() ; 
			recCom.setCompany(sCompany);
			recCom.setCantidad(regs.CampoAsLong("cantidad")) ;
			if (fdesde!=null) recCom.setFechaDesde(JDateTools.StringToDate(fdesde));
			if (fhasta!=null) recCom.setFechaHasta(JDateTools.StringToDate(fhasta));
			if (sUsuario!=null) recCom.setUsuario(sUsuario);
			if (sStatus!=null) recCom.setStatus(regs.CampoAsStr("status"));
			if (sPeriodo!=null) recCom.setPeriodo(sPeriodo);
			if (sPeriodo!=null) recCom.setFecha(regs.CampoAsStr("periodo"));
			
			if (agrupaUsr) recCom.setUsuario(regs.CampoAsStr("usuario"));
			if (agrupaCompany) recCom.setCompany(regs.CampoAsStr("company"));
			if (agrupaStatus) recCom.setStatus(regs.CampoAsStr("status"));
			this.addItem(recCom) ;
		}
		return true;
	}
	

	public long selectCount() throws Exception {
		return -1L;
	}




}