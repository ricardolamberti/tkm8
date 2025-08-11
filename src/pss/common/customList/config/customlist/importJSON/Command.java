package pss.common.customList.config.customlist.importJSON;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Command {
    
    @JsonProperty("object")
    private String object;

    @JsonProperty("field")
    private String field;

    @JsonProperty("accion")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String accion;
    
    @JsonProperty("function")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String function;

    @JsonProperty("operator")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String operator;

    @JsonProperty("value")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String value;

    @JsonProperty("porcentaje")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String porcentaje;

    @JsonProperty("value_to")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String valueTo;

    @JsonProperty("value_from")
    private String valueFrom;

    @JsonProperty("orden")
    private int orden;

    @JsonProperty("sentido_orden")
    private String sentidoOrden;

    @JsonProperty("limite")
    private int limite;

    // Getters and setters
    
    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueTo() {
        return valueTo;
    }

    public void setValueTo(String valueTo) {
        this.valueTo = valueTo;
    }

    public String getValueFrom() {
        return valueFrom;
    }

    public void setValueFrom(String valueFrom) {
        this.valueFrom = valueFrom;
    }

		public int getOrden() {
			return orden;
		}

		public void setOrden(int orden) {
			this.orden = orden;
		}

		public String getSentidoOrden() {
			return sentidoOrden;
		}

		public void setSentidoOrden(String sentidoOrden) {
			this.sentidoOrden = sentidoOrden;
		}

		public int getLimite() {
			return limite;
		}

		public void setLimite(int limite) {
			this.limite = limite;
		}

		public String getAccion() {
			return accion;
		}

		public void setAccion(String accion) {
			this.accion = accion;
		}

		public String getPorcentaje() {
			return porcentaje;
		}

		public void setPorcentaje(String porcentaje) {
			this.porcentaje = porcentaje;
		}
    
}
