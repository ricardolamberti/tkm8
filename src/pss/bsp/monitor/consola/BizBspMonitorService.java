package pss.bsp.monitor.consola;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import pss.bsp.monitor.log.BizBspLog;
import pss.bsp.monitor.log.GuiBspLogs;
import pss.common.scheduler.BizScheduler;
import pss.core.graph.Graph;
import pss.core.graph.implementations.GraphScriptMultiChart;
import pss.core.graph.implementations.GraphScriptPie;
import pss.core.graph.model.ModelMatrix;
import pss.core.services.fields.JString;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.win.GuiVirtuals;
import pss.core.winUI.lists.JWinList;

public class BizBspMonitorService extends JRecord {


	private JString pStatus = new JString() {
		public void preset() throws Exception {
			pStatus.setValue(getStatus());
		};
	};

	private JString pImage = new JString() {
		public void preset() throws Exception {
			pImage.setValue(getImagen());
		};
	};
	private JString pImageComp = new JString() {
		public void preset() throws Exception {
			pImageComp.setValue(getImagenComp());
		};
	};
	private JString pImageFull = new JString() {
		public void preset() throws Exception {
			pImageFull.setValue(getImagenFull());
		};
	};
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getStatus() throws Exception {
		return BizScheduler.readStatus();
	}
	
	public String getImagen() throws Exception {
		GuiBspLogs w = new GuiBspLogs();

		w.getRecords().addFilter("company", "DEFAULT");
		w.getRecords().addFilter("log_type", "TIMES");
		w.getRecords().setTop(1);;
		w.getRecords().addOrderBy("log_date","DESC");
		JIterator<BizBspLog> it = w.getRecords().getStaticIterator();
		String json ="";
		if (it.hasMoreElements()) {
			BizBspLog log = it.nextElement();
			json = log.getLog();
		}
    ObjectMapper mapper = new ObjectMapper();

    if (json.equals("")) return "";
    // Parse JSON a lista de objetos con campos: task, ms, percent
    List<Map<String, Object>> list = mapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {});
    GuiVirtuals virt = new GuiVirtuals();
    JRecords<BizVirtual> datos = virt.getRecords();
    datos.setStatic(true);

    for (Map<String, Object> entry : list) {
        String task = (String) entry.get("task");
        Object percentObj = entry.get("percent");
        String percent = (percentObj != null) ? String.format("%.2f%%", ((Number) percentObj).doubleValue()) : "";

        BizVirtual dato = new BizVirtual();
        dato.setDescripcion(task);
        dato.setValor(""+((Number) percentObj).doubleValue());
        dato.setIconoString("");

        datos.addItem(dato);
    }

		JWinList wl = new JWinList(virt);
		virt.AddColumnasDefault(wl);
		Graph gr = new GraphScriptPie();
		gr.addAtributtes(Graph.GRAPH_ATTR_ROTATENAMES, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_DECIMALPRECISON, "2");
		gr.addAtributtes(Graph.GRAPH_ATTR_NUMBERPREFIX, "");
		gr.addAtributtes(Graph.GRAPH_ATTR_EXPORTENABLED, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_XAXISNAME, "Tareas");

		gr.addAtributtes(Graph.GRAPH_ATTR_YAXISNAME, "Uso");
		gr.setTitle("CPU");
		ModelMatrix mg=new ModelMatrix();
		
		mg.addColumn("icono_string", ModelMatrix.GRAPH_FUNCTION_CATEGORIE);
		mg.addColumn("descripcion", ModelMatrix.GRAPH_FUNCTION_DATASET);
		mg.addColumn("valor", ModelMatrix.GRAPH_FUNCTION_VALUE);
		gr.setModel(mg);

  	wl.addGrafico(gr);
    w.ConfigurarFiltrosLista(wl);
    gr.setWithDownload(true);
    gr.setWithZoom(true);
		Graph g=wl.getGrafico(1);
		if (g!=null) {
			g.localFill(wl,null,null);
			g.setRefresh(1);
			return g.getImage(400,400).replace("script:", "");
		}
		return null;
	}	
	public String getImagenComp() throws Exception {
		GuiBspLogs w = new GuiBspLogs();

		w.getRecords().addFilter("company", "DEFAULT");
		w.getRecords().addFilter("log_type", BizBspLog.BSPLOG_MODULO_TIMESCOMPANY);
		w.getRecords().setTop(1);;
		w.getRecords().addOrderBy("log_date","DESC");
		JIterator<BizBspLog> it = w.getRecords().getStaticIterator();
		String json ="";
		if (it.hasMoreElements()) {
			BizBspLog log = it.nextElement();
			json = log.getLog();
		}
    ObjectMapper mapper = new ObjectMapper();

    if (json.equals("")) return "";
    // Parse JSON a lista de objetos con campos: task, ms, percent
    List<Map<String, Object>> list = mapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {});
    GuiVirtuals virt = new GuiVirtuals();
    JRecords<BizVirtual> datos = virt.getRecords();
    datos.setStatic(true);

    for (Map<String, Object> entry : list) {
        String task = (String) entry.get("company");
        Object percentObj = entry.get("percent");
        String percent = (percentObj != null) ? String.format("%.2f%%", ((Number) percentObj).doubleValue()) : "";

        BizVirtual dato = new BizVirtual();
        dato.setDescripcion(task);
        dato.setValor(""+((Number) percentObj).doubleValue());
        dato.setIconoString("");

        datos.addItem(dato);
    }

		JWinList wl = new JWinList(virt);
		virt.AddColumnasDefault(wl);
		Graph gr = new GraphScriptPie();
		gr.addAtributtes(Graph.GRAPH_ATTR_ROTATENAMES, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_DECIMALPRECISON, "2");
		gr.addAtributtes(Graph.GRAPH_ATTR_NUMBERPREFIX, "");
		gr.addAtributtes(Graph.GRAPH_ATTR_EXPORTENABLED, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_XAXISNAME, "Company");

		gr.addAtributtes(Graph.GRAPH_ATTR_YAXISNAME, "Uso");
		gr.setTitle("CPU");
		ModelMatrix mg=new ModelMatrix();
		
		mg.addColumn("icono_string", ModelMatrix.GRAPH_FUNCTION_CATEGORIE);
		mg.addColumn("descripcion", ModelMatrix.GRAPH_FUNCTION_DATASET);
		mg.addColumn("valor", ModelMatrix.GRAPH_FUNCTION_VALUE);
		gr.setModel(mg);

  	wl.addGrafico(gr);
    w.ConfigurarFiltrosLista(wl);
    gr.setWithDownload(true);
    gr.setWithZoom(true);
		Graph g=wl.getGrafico(1);
		if (g!=null) {
			g.localFill(wl,null,null);
			g.setRefresh(1);
			return g.getImage(400, 400).replace("script:", "");
		}
		return null;
	}
	public String getImagenFull() throws Exception {
		GuiBspLogs w = new GuiBspLogs();

		w.getRecords().addFilter("company", "DEFAULT");
		w.getRecords().addFilter("log_type", BizBspLog.BSPLOG_MODULO_TIMESFULL);
		w.getRecords().setTop(1);;
		w.getRecords().addOrderBy("log_date","DESC");
		JIterator<BizBspLog> it = w.getRecords().getStaticIterator();
		String json ="";
		if (it.hasMoreElements()) {
			BizBspLog log = it.nextElement();
			json = log.getLog();
		}
    ObjectMapper mapper = new ObjectMapper();

    Map<String, Object> root = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
    List<Map<String, Object>> threads = (List<Map<String, Object>>) root.get("threads");

    GuiVirtuals virt = new GuiVirtuals();
    JRecords<BizVirtual> datos = virt.getRecords();
    datos.setStatic(true);

    for (Map<String, Object> threadEntry : threads) {
        String threadName = (String) threadEntry.get("thread");
        List<Map<String, Object>> tasks = (List<Map<String, Object>>) threadEntry.get("tasks");

        for (Map<String, Object> taskEntry : tasks) {
            String taskName = (String) taskEntry.get("task");
            Object percentObj = taskEntry.get("percent");

            BizVirtual dato = new BizVirtual();
            dato.setDescripcion(taskName);
            dato.setValor("" + ((Number) percentObj).doubleValue());
            dato.setIconoString(threadName);

            datos.addItem(dato);
        }
    }

		JWinList wl = new JWinList(virt);
		virt.AddColumnasDefault(wl);
		GraphScriptMultiChart gr = new GraphScriptMultiChart();
		gr.setStackedOn();
		gr.addAtributtes(Graph.GRAPH_ATTR_ROTATENAMES, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_DECIMALPRECISON, "2");
		gr.addAtributtes(Graph.GRAPH_ATTR_NUMBERPREFIX, "");
		gr.addAtributtes(Graph.GRAPH_ATTR_EXPORTENABLED, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_XAXISNAME, "Company");

		gr.addAtributtes(Graph.GRAPH_ATTR_YAXISNAME, "Uso");
		gr.setTitle("CPU");
		ModelMatrix mg=new ModelMatrix();
		
		mg.addColumn("icono_string", ModelMatrix.GRAPH_FUNCTION_CATEGORIE);
		mg.addColumn("descripcion", ModelMatrix.GRAPH_FUNCTION_DATASET);
		mg.addColumn("valor", ModelMatrix.GRAPH_FUNCTION_VALUE);
		gr.setModel(mg);

  	wl.addGrafico(gr);
    w.ConfigurarFiltrosLista(wl);
    gr.setWithDownload(true);
    gr.setWithZoom(true);
		Graph g=wl.getGrafico(1);
		if (g!=null) {
			g.localFill(wl,null,null);
			g.setRefresh(1);
			return g.getImage(1200, 400).replace("script:", "");
		}
		return null;
	}

	/**
	 * Constructor de la Clase
	 */
	public BizBspMonitorService() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("status", pStatus);
		this.addItem("image", pImage);
		this.addItem("image_comp", pImageComp);
		this.addItem("image_full", pImageFull);

	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(FIELD, "status", "Estado", true, false, 2000);
		this.addFixedItem(FIELD, "image", "Image", true, false, 2000);
		this.addFixedItem(FIELD, "image_comp", "Image company", true, false, 2000);
		this.addFixedItem(FIELD, "image_full", "Image full", true, false, 2000);
		}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "";
	}


	

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(long id) throws Exception {
		addFilter("id", id);
		return read();
	}
	

}
