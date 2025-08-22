package pss.bsp.consolid.model.clientesView;

import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.records.JRecords;

public class BizClienteViews extends JRecords<BizClienteView> {
;
	public Class<BizClienteView> getBasedClass() {
		return BizClienteView.class;
	}
	
	
	

	@Override
	public boolean readAll() throws Exception {
		this.endStatic();
    this.setStatic(true);
	
    String customerNumber = getFilterValue("customer_number");
    String customerName = getFilterValue("customer_name");
   
    String ant = JBDatos.GetBases().GetDatabaseDefault();
    try {
			JBDatos.GetBases().SetDatabaseDefault("CONSOLID_SERVER");
			JBaseRegistro regs = JBaseRegistro.recordsetFactory();
			String sql = "";
			sql += " select customer_number , MAX(customer_name) as customer_name,max(reg_fiscal) as reg_fiscal,max(ref_bancaria) as ref_bancaria";
			sql += " from apps.CNS_CLIENTES_V ";
			sql += " where 1=1  ";
			if (customerNumber != null && !customerNumber.isEmpty()) {
				sql += " and customer_number like '%" + customerNumber + "%'  ";
			}
			if (customerName != null && !customerName.isEmpty()) {
				sql += " and customer_name like '%" + customerName + "%'  ";
			}
			sql += " group by customer_number ";
			sql += " order by customer_number asc ";
			if (offset!=-1)
				sql += " FETCH NEXT " +  ((this.offset+1)*this.pagesize) + " ROWS ONLY ";
			regs.ExecuteQuery(sql);
			while (regs.next()) {
				BizClienteView obj = new BizClienteView();
				obj.setCustomerNumber(regs.CampoAsStr("customer_number"));
				obj.setCustomerName(regs.CampoAsStr("customer_name"));
				obj.setRegFiscal(regs.CampoAsStr("reg_fiscal"));
				obj.setRefBancaria(regs.CampoAsStr("ref_bancaria"));

	

				addItem(obj);
			}
			regs.close();
		} finally {
			// TODO: handle finally clause
		}
		JBDatos.GetBases().SetDatabaseDefault(ant);
		
		return true;
		
	}
	
	@Override
	public long selectSupraCount() throws Exception {
    String ant = JBDatos.GetBases().GetDatabaseDefault();
    long cantidad;
		try {
			String customerNumber = getFilterValue("customer_number");
			String customerName = getFilterValue("customer_name");
			JBDatos.GetBases().SetDatabaseDefault("CONSOLID_SERVER");
			String sql = "";
			sql += " SELECT COUNT(*) as contar FROM ( select customer_number ";
			sql += " from apps.CNS_CLIENTES_V ";
			sql += " where 1=1  ";
			if (customerNumber != null && !customerNumber.isEmpty()) {
				sql += " and customer_number like '%" + customerNumber + "%'  ";
			}
			if (customerName != null && !customerName.isEmpty()) {
				sql += " and customer_name like '%" + customerName + "%'  ";
			}
			sql += " group by customer_number ) t ";
			JBaseRegistro regs = JBaseRegistro.recordsetFactory();
			regs.ExecuteQuery(sql);
			cantidad = 0;
			while (regs.next()) {
				cantidad = regs.CampoAsLong("contar");
			}
			regs.close();
		} finally {
				}
		JBDatos.GetBases().SetDatabaseDefault(ant);
		return cantidad;
	}

  
}