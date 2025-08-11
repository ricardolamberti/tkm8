package pss.core.winUI.responsiveControls;

public class TableFilterManual {
	String field;

  String[] column;
  String operacion;
  String control;
	public String getControl() {
		return control;
	}
	public void setControl(String control) {
		this.control = control;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String[] getColumns() {
		return column;
	}
	public void setColumns(String[] column) {
		this.column = column;
	}
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
}
